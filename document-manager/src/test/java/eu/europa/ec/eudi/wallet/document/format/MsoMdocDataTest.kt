/*
 * Copyright (c) 2024 European Commission
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

package eu.europa.ec.eudi.wallet.document.format

import com.android.identity.securearea.SecureArea
import com.android.identity.securearea.SecureAreaRepository
import com.android.identity.securearea.software.SoftwareCreateKeySettings
import com.android.identity.securearea.software.SoftwareSecureArea
import com.android.identity.storage.EphemeralStorageEngine
import com.android.identity.storage.StorageEngine
import eu.europa.ec.eudi.wallet.document.CreateDocumentSettings
import eu.europa.ec.eudi.wallet.document.CreateDocumentSettings.Companion.invoke
import eu.europa.ec.eudi.wallet.document.DocumentManagerImpl
import eu.europa.ec.eudi.wallet.document.IssuedDocument
import eu.europa.ec.eudi.wallet.document.getResourceAsText
import kotlinx.datetime.LocalDate
import java.time.ZonedDateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MsoMdocDataTest {
    lateinit var documentManager: DocumentManagerImpl
    lateinit var storageEngine: StorageEngine
    lateinit var secureArea: SecureArea
    lateinit var secureAreaRepository: SecureAreaRepository
    lateinit var issuedDocument: IssuedDocument

    @OptIn(ExperimentalStdlibApi::class)
    @BeforeTest
    fun setUp() {
        storageEngine = EphemeralStorageEngine()
        secureArea = SoftwareSecureArea(storageEngine)
        secureAreaRepository = SecureAreaRepository()
            .apply { addImplementation(secureArea) }
        documentManager = DocumentManagerImpl(
            identifier = "document_manager",
            storageEngine = EphemeralStorageEngine(),
            secureAreaRepository = secureAreaRepository,
        )

        // set checkMsoKey to false to avoid checking the MSO key
        // since we are using fixed issuer data
        documentManager.checkMsoKey = false
        val createKeySettings = SoftwareCreateKeySettings.Builder().build()
        val createDocumentResult = documentManager.createDocument(
            format = MsoMdocFormat(docType = "eu.europa.ec.eudi.pid.1"),
            createSettings = CreateDocumentSettings(
                secureAreaIdentifier = secureArea.identifier,
                createKeySettings = createKeySettings
            )
        )
        assertTrue(createDocumentResult.isSuccess)
        val unsignedDocument = createDocumentResult.getOrThrow()
        assertFalse(unsignedDocument.isCertified)

        // change document name
        unsignedDocument.name = "EU PID"

        assertIs<MsoMdocFormat>(unsignedDocument.format)
        val documentFormat = unsignedDocument.format as MsoMdocFormat
        assertEquals("eu.europa.ec.eudi.pid.1", documentFormat.docType)
        assertFalse(unsignedDocument.isKeyInvalidated)
        assertEquals(documentManager.identifier, unsignedDocument.documentManagerId)

        val issuerData = getResourceAsText("eu_pid.hex").hexToByteArray(HexFormat.Default)

        val storeDocumentResult = documentManager.storeIssuedDocument(unsignedDocument, issuerData)
        assertTrue(storeDocumentResult.isSuccess)
        issuedDocument = storeDocumentResult.getOrThrow()
    }

    @AfterTest
    fun tearDown() {
        documentManager.getDocuments().forEach {
            documentManager.deleteDocumentById(it.id)
        }
    }

    @Test
    fun `MsoMdocData should have the expected properties`() {
        val msoMdocData = issuedDocument.data as MsoMdocData
        assertEquals(MsoMdocFormat(docType = "eu.europa.ec.eudi.pid.1"), msoMdocData.format)
        assertEquals(33, msoMdocData.claims.size)
        assertEquals("eu.europa.ec.eudi.pid.1", msoMdocData.docType)
        assertEquals(1, msoMdocData.nameSpacedDataInBytes.size)
        assertEquals(1, msoMdocData.nameSpacedDataDecoded.size)
        assertEquals(1, msoMdocData.nameSpaces.size)

        assertIs<ZonedDateTime>(msoMdocData.claims.firstOrNull { it.nameSpace == "eu.europa.ec.eudi.pid.1" && it.elementIdentifier == "issuance_date" }?.elementValue)
        assertIs<ZonedDateTime>(msoMdocData.claims.firstOrNull { it.nameSpace == "eu.europa.ec.eudi.pid.1" && it.elementIdentifier == "expiry_date" }?.elementValue)
        assertIs<LocalDate>(msoMdocData.claims.firstOrNull { it.nameSpace == "eu.europa.ec.eudi.pid.1" && it.elementIdentifier == "birth_date" }?.elementValue)
    }
}