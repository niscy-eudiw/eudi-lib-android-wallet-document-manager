/*
 * Copyright (c) 2024-2025 European Commission
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.europa.ec.eudi.wallet.document

import eu.europa.ec.eudi.wallet.document.format.DocumentFormat
import eu.europa.ec.eudi.wallet.document.metadata.IssuerMetadata
import java.time.Instant

/**
 * Base interface for all document types in the EUDI Wallet ecosystem.
 *
 * The Document interface defines common properties and behaviors shared by all document types:
 * unsigned documents, documents in the process of being issued, and fully issued documents.
 * Documents are identified by a unique ID and have associated metadata and credentials.
 *
 * @property id The unique identifier of the document
 * @property name The human-readable name of the document
 * @property format The format specification of the document (e.g., MsoMdoc, SdJwtVc)
 * @property documentManagerId The identifier of the DocumentManager that manages this document
 * @property createdAt The timestamp when the document was created in the wallet
 * @property issuerMetadata The document metadata provided by the issuer, if available
 */
sealed interface Document {
    val id: DocumentId
    val name: String
    val format: DocumentFormat
    val documentManagerId: String
    val createdAt: Instant
    val issuerMetadata: IssuerMetadata?

    /**
     * Returns the number of valid credentials associated with this document.
     *
     * For UnsignedDocument, this counts credentials that can be used for proof of possession.
     * For IssuedDocument, this counts valid credentials according to the credential policy.
     *
     * @return The number of valid credentials available for this document
     */
    suspend fun credentialsCount(): Int
}

