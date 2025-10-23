/*
 *  Copyright (c) 2025 European Commission
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.europa.ec.eudi.wallet.document.internal

import eu.europa.ec.eudi.wallet.document.CreateDocumentSettings
import eu.europa.ec.eudi.wallet.document.format.DocumentFormat
import eu.europa.ec.eudi.wallet.document.format.MsoMdocFormat
import eu.europa.ec.eudi.wallet.document.format.SdJwtVcFormat
import eu.europa.ec.eudi.wallet.document.metadata.IssuerMetadata
import kotlinx.io.bytestring.ByteString
import org.multipaz.cbor.DataItem
import org.multipaz.cbor.annotation.CborSerializable
import org.multipaz.cbor.toDataItemDateTimeString
import org.multipaz.document.AbstractDocumentMetadata
import org.multipaz.document.DocumentMetadata
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Interface for application-specific document metadata management.
 *
 * This interface extends [AbstractDocumentMetadata] to provide functionality for storing and
 * managing metadata related to documents in the EUDI wallet application. It handles document format,
 * credentials, issuer information, and the document lifecycle from creation through issuance.
 */
internal interface ApplicationMetadata : AbstractDocumentMetadata {

    /**
     * The unique identifier of the document manager handling this document.
     * @throws IllegalStateException if the document manager ID is not set
     */
    val documentManagerId: String

    /**
     * The format of the document (e.g., MSO MDL or SD-JWT VC).
     * @throws IllegalStateException if the document format is not set
     */
    val format: DocumentFormat

    /**
     * The initial number of credentials in the document.
     * @throws IllegalStateException if the initial credentials count is not set
     */
    val initialCredentialsCount: Int

    /**
     * The policy that determines how credentials are handled for this document.
     * @throws IllegalStateException if the credential policy is not set
     */
    val credentialPolicy: CreateDocumentSettings.CredentialPolicy

    /**
     * The timestamp when the document was created.
     * @throws IllegalStateException if the creation timestamp is not set
     */
    val createdAt: Instant

    /**
     * The display name of the document.
     * Falls back to format-specific identifiers if no display name is set.
     */
    val documentName: String

    /**
     * Optional key attestation data in JSON format.
     */
    val keyAttestation: String?

    /**
     * Optional metadata about the issuer of the document.
     */
    val issuerMetadata: IssuerMetadata?

    /**
     * Optional data provided by the issuer during document issuance.
     */
    val issuerProvidedData: ByteArray?

    /**
     * Optional timestamp when the document was issued.
     */
    val issuedAt: Instant?

    /**
     * Optional data related to deferred issuance.
     */
    val deferredRelatedData: ByteArray?

    /**
     * Initializes the document metadata with essential information.
     *
     * @param documentManagerId Identifier of the document manager
     * @param format Format specification of the document
     * @param initialCredentialsCount Number of credentials initially in the document
     * @param credentialPolicy Policy for handling credentials
     * @param createdAt Timestamp of document creation
     * @param documentName Display name for the document
     * @param issuerMetadata Optional metadata about the document issuer
     * @param keyAttestation Optional key attestation data
     */
    suspend fun initialize(
        documentManagerId: String,
        format: DocumentFormat,
        createSettings: CreateDocumentSettings,
        documentName: String,
        issuerMetadata: IssuerMetadata?,
        keyAttestation: String?
    )

    /**
     * Issues the document with provided issuer data.
     *
     * This marks the document as fully provisioned and updates its metadata.
     *
     * @param issuerProvidedData Data provided by the issuer
     * @param documentName Optional new name for the document
     */
    suspend fun issue(
        issuerProvidedData: ByteString,
        documentName: String? = null
    )

    /**
     * Sets up the document for deferred issuance.
     *
     * @param deferredRelatedData Data related to deferred issuance
     * @param documentName Optional new name for the document
     */
    suspend fun issueDeferred(
        deferredRelatedData: ByteString,
        documentName: String? = null
    )

    /**
     * Updates the key attestation data for the document.
     *
     * @param keyAttestation Key attestation data in JSON format
     */
    suspend fun setKeyAttestation(keyAttestation: String)


    companion object {
        /**
         * The factory for [ApplicationMetadata].
         *
         * @param documentId the document to create metadata for.
         * @param serializedData the serialized metadata.
         * @param saveFn a function to serialize the instance into serialized metadata.
         * @return the created [ApplicationMetadata]
         */
        suspend fun create(
            documentId: String,
            serializedData: ByteString?,
            saveFn: suspend (data: ByteString) -> Unit
        ): ApplicationMetadata = ApplicationMetadataImpl.create(documentId, serializedData, saveFn)
    }

}


/**
 * Implementation of [ApplicationMetadata] that delegates core functionality to [AbstractDocumentMetadata].
 *
 * This class manages application-specific document metadata by storing it in a serializable
 * [Data] object and handling the document lifecycle.
 *
 * @property delegate The underlying document metadata implementation
 */
internal class ApplicationMetadataImpl private constructor(
    private val delegate: AbstractDocumentMetadata
) : ApplicationMetadata, AbstractDocumentMetadata by delegate {

    private var data: Data =
        delegate.other?.let { Data.fromCbor(it.toByteArray()) } ?: Data()

    override val documentManagerId: String
        get() = data.documentManagerId ?: error("Document manager ID not set")
    override val format: DocumentFormat
        get() = data.format?.let {
            DocumentFormat.fromDataItem(it)
        } ?: error("Document format not set")

    override val initialCredentialsCount: Int
        get() = data.initialCredentialsCount.takeIf { it > 0 }
            ?: error("Initial credential count not set")

    override val credentialPolicy: CreateDocumentSettings.CredentialPolicy
        get() = data.credentialPolicyType?.let {
            CreateDocumentSettings.CredentialPolicy.fromDataItem(it)
        } ?: error("Credential policy not set")
    override val documentName: String
        get() = displayName ?: when (format) {
            is MsoMdocFormat -> (format as MsoMdocFormat).docType
            is SdJwtVcFormat -> (format as SdJwtVcFormat).vct
        }
    override val keyAttestation: String?
        get() = data.keyAttestation
    override val issuerMetadata: IssuerMetadata?
        get() = data.issuerMetadata?.let {
            IssuerMetadata.fromJson(
                it
            ).getOrNull()
        }
    override val issuerProvidedData: ByteArray? get() = data.issuerProvidedData?.toByteArray()
    override val createdAt: Instant
        get() = data.createdAt?.asDateTimeString ?: error("Created at not set")
    override val issuedAt: Instant? get() = data.issuedAt?.asDateTimeString
    override val deferredRelatedData: ByteArray? get() = data.deferredRelatedData?.toByteArray()


    /**
     * Internal data class for storing metadata fields in a serializable format.
     *
     * This class provides methods for CBOR serialization and deserialization of metadata.
     * Note: We store format and policy as strings to avoid CBOR processor issues with type aliases.
     *
     * @property documentManagerId The unique identifier of the document manager handling this document
     * @property formatType The type of document format ("mso_mdoc" or "sd_jwt_vc")
     * @property formatValue The format-specific value (docType for MSO MDoc or vct for SD-JWT VC)
     * @property initialCredentialsCount The number of credentials initially created for the document
     * @property credentialPolicyType The credential policy type identifier ("OneTimeUse" or "RotateUse")
     * @property keyAttestation Key attestation data in JSON format
     * @property issuerMetadata Metadata about the document issuer in JSON format
     * @property issuerProvidedData Data provided by the issuer during document issuance
     * @property createdAt Timestamp in epoch milliseconds when the document was created
     * @property issuedAt Timestamp in epoch milliseconds when the document was issued
     * @property deferredRelatedData Data related to deferred issuance workflow
     */
    @CborSerializable
    internal data class Data(
        val documentManagerId: String? = null,
        val format: DataItem? = null,
        val initialCredentialsCount: Int = 0,
        val credentialPolicyType: DataItem? = null,
        val keyAttestation: String? = null,
        val issuerMetadata: String? = null,
        val issuerProvidedData: ByteString? = null,
        val createdAt: DataItem? = null,
        val issuedAt: DataItem? = null,
        val deferredRelatedData: ByteString? = null,
    ) {

        companion object
    }

    /**
     * Initializes document metadata with the provided information.
     *
     * This method sets up the basic metadata required for a document and stores it
     * in serialized form.
     */
    override suspend fun initialize(
        documentManagerId: String,
        format: DocumentFormat,
        createSettings: CreateDocumentSettings,
        documentName: String,
        issuerMetadata: IssuerMetadata?,
        keyAttestation: String?
    ) {

        data = Data(
            documentManagerId = documentManagerId,
            format= format.toDataItem(),
            createdAt = Clock.System.now().toDataItemDateTimeString(),
            initialCredentialsCount = createSettings.numberOfCredentials,
            credentialPolicyType = createSettings.credentialPolicy.toDataItem(),
            issuerMetadata = issuerMetadata?.toJson(),
            keyAttestation = keyAttestation
        )

        setMetadata(
            displayName = documentName,
            typeDisplayName = typeDisplayName,
            cardArt = cardArt,
            issuerLogo = issuerLogo,
            other = ByteString(data.toCbor())
        )
    }

    /**
     * Issues the document with provided issuer data.
     *
     * This method:
     * 1. Updates the metadata with issuer-provided data
     * 2. Clears any deferred issuance data
     * 3. Sets the issuance timestamp
     * 4. Marks the document as provisioned
     *
     * If the document is already provisioned, this method does nothing.
     */
    override suspend fun issue(
        issuerProvidedData: ByteString,
        documentName: String?,
    ) {
        if (provisioned) return
        data = data.copy(
            issuerProvidedData = issuerProvidedData,
            deferredRelatedData = null,
            issuedAt = Clock.System.now().toDataItemDateTimeString()
        )
        setMetadata(
            displayName = documentName ?: displayName,
            typeDisplayName = typeDisplayName,
            cardArt = cardArt,
            issuerLogo = issuerLogo,
            other = ByteString(data.toCbor())
        )
        markAsProvisioned()
    }

    /**
     * Sets up the document for deferred issuance.
     *
     * This stores related data needed for completing the issuance process later.
     * If the document is already provisioned, this method does nothing.
     */
    override suspend fun issueDeferred(deferredRelatedData: ByteString, documentName: String?) {
        if (provisioned) return

        data = data.copy(
            deferredRelatedData = deferredRelatedData
        )

        setMetadata(
            displayName = documentName ?: displayName,
            typeDisplayName = typeDisplayName,
            cardArt = cardArt,
            issuerLogo = issuerLogo,
            other = ByteString(data.toCbor())
        )
    }

    /**
     * Updates the key attestation data for the document.
     *
     * If the document is already provisioned, this method does nothing.
     */
    override suspend fun setKeyAttestation(keyAttestation: String) {
        if (provisioned) return
        data = data.copy(
            keyAttestation = keyAttestation
        )
        setMetadata(
            displayName = displayName,
            typeDisplayName = typeDisplayName,
            cardArt = cardArt,
            issuerLogo = issuerLogo,
            other = ByteString(data.toCbor())
        )
    }


    companion object {
        /**
         * Factory method for creating [ApplicationMetadataImpl] instances.
         *
         * @param documentId The document ID to associate with this metadata
         * @param serializedData Optional previously serialized metadata
         * @param saveFn A function to call when the metadata needs to be saved
         * @return A new or restored [ApplicationMetadataImpl] instance
         */
        suspend fun create(
            documentId: String,
            serializedData: ByteString?,
            saveFn: suspend (data: ByteString) -> Unit
        ): ApplicationMetadataImpl {
            val delegate = DocumentMetadata.Companion.create(documentId, serializedData, saveFn)
            return ApplicationMetadataImpl(delegate)
        }
    }
}