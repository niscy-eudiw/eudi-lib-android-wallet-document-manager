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

package eu.europa.ec.eudi.wallet.document.sample

import com.android.identity.crypto.Algorithm
import com.android.identity.crypto.Crypto
import com.android.identity.securearea.KeyLockedException
import com.android.identity.securearea.PassphraseConstraints
import com.android.identity.securearea.software.SoftwareCreateKeySettings
import com.android.identity.securearea.software.SoftwareKeyUnlockData
import com.android.identity.securearea.software.SoftwareSecureArea
import com.android.identity.storage.EphemeralStorageEngine
import eu.europa.ec.eudi.wallet.document.DocumentManagerImpl
import eu.europa.ec.eudi.wallet.document.IssuedDocument
import eu.europa.ec.eudi.wallet.document.format.MsoMdocFormat
import eu.europa.ec.eudi.wallet.document.getResourceAsText
import eu.europa.ec.eudi.wallet.document.secureArea
import eu.europa.ec.eudi.wallet.document.storageEngine
import io.mockk.every
import io.mockk.spyk
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SampleDocumentManagerImplTest {

    companion object {
        lateinit var documentManager: SampleDocumentManagerImpl

        @OptIn(ExperimentalEncodingApi::class)
        val sampleDocuments
            get() = getResourceAsText("sample_documents.txt").let {
                Base64.Default.decode(it)
            }

        @BeforeClass
        @JvmStatic
        fun setUp() {
            documentManager = SampleDocumentManagerImpl(
                DocumentManagerImpl(
                    storageEngine = storageEngine,
                    secureArea = secureArea
                )
            )
            val createKeySettings = SoftwareCreateKeySettings.Builder().build()
            val loadResult = documentManager.loadMdocSampleDocuments(
                sampleDocuments, createKeySettings, mapOf(
                    "eu.europa.ec.eudi.pid.1" to "EU PID",
                    "org.iso.18013.5.1.mDL" to "mDL"
                )
            )
            assertTrue(loadResult.isSuccess)
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            documentManager.getDocuments().forEach { documentManager.deleteDocumentById(it.id) }
        }
    }

    @Test
    fun `getDocuments should return the two sample documents`() {
        val documents = documentManager.getDocuments()
        assertEquals(2, documents.size)
    }

    @Test
    fun `documents should have their names set according to namesMap given to load method`() {
        val documents = documentManager.getDocuments()
        assertTrue(documents.any { it.name == "EU PID" })
        assertTrue(documents.any { it.name == "mDL" })
    }

    @Test
    fun `getDocuments using query with docType should return the appropriate document`() {
        val documents = documentManager.getDocuments { document ->
            (document.format as MsoMdocFormat).docType == "eu.europa.ec.eudi.pid.1"
        }
        assertEquals(1, documents.size)
        val document = documents.first()
        assertEquals("EU PID", document.name)
    }

    @Test
    fun `document sign method should create a valid signature`() {
        val document = documentManager.getDocuments().first()
        val dataToSign = byteArrayOf(1, 2, 3)
        val signResult = document.sign(dataToSign)
        assertTrue(signResult.isSuccess)
        assertNotNull(signResult.getOrNull())
        val signature = signResult.getOrThrow()

        // Verify the signature
        val publicKey = document.keyInfo.publicKey
        val isValid = Crypto.checkSignature(publicKey, dataToSign, Algorithm.ES256, signature)
        assertTrue(isValid)
    }

    @Test
    fun `document sign method should return failure`() {
        val document = spyk(documentManager.getDocuments().first())
        val dataToSign = byteArrayOf(1, 2, 3)
        every {
            document.secureArea.sign(
                document.keyAlias,
                Algorithm.ES256,
                dataToSign,
                null
            )
        } throws Exception()
        val signResult = document.sign(dataToSign)
        assertTrue(signResult.isFailure)
    }

    @Test
    fun `document sign method should return key locked result if key usage requires unlocking`() {
        // reload sample document with createKeySettings that require PIN
        val storageEngine = EphemeralStorageEngine()
        val documentManager = SampleDocumentManagerImpl(
            DocumentManagerImpl(
                storageEngine = storageEngine,
                secureArea = SoftwareSecureArea(storageEngine)
            )
        )
        val createKeySettings = SoftwareCreateKeySettings.Builder()
            .setPassphraseRequired(true, "1234", PassphraseConstraints.PIN_FOUR_DIGITS)
            .build()
        documentManager.loadMdocSampleDocuments(
            sampleDocuments, createKeySettings, mapOf(
                "eu.europa.ec.eudi.pid.1" to "EU PID",
                "org.iso.18013.5.1.mDL" to "mDL"
            )
        )

        val document = documentManager.getDocuments().first()
        val dataToSign = byteArrayOf(1, 2, 3)
        val signResult = document.sign(dataToSign)
        assertTrue(signResult.isFailure)
        val failure = signResult.exceptionOrNull()
        assertIs<KeyLockedException>(failure)

    }

    @Test
    fun `deleteDocumentById should delete the document`() {
        // reload sample document with createKeySettings that require PIN
        val storageEngine = EphemeralStorageEngine()
        val documentManager = SampleDocumentManagerImpl(
            DocumentManagerImpl(
                storageEngine = storageEngine,
                secureArea = SoftwareSecureArea(storageEngine)
            )
        )
        val createKeySettings = SoftwareCreateKeySettings.Builder().build()
        documentManager.loadMdocSampleDocuments(
            sampleDocuments, createKeySettings, mapOf(
                "eu.europa.ec.eudi.pid.1" to "EU PID",
                "org.iso.18013.5.1.mDL" to "mDL"
            )
        )

        val document = documentManager.getDocuments().first()
        val documentId = document.id

        val deleteResult = documentManager.deleteDocumentById(documentId)

        assertTrue(deleteResult.isSuccess)
        val documents = documentManager.getDocuments()
        assertEquals(1, documents.size)

    }

    @Test
    fun `document sign method should return result success when locked key and passing the keyUnlockData`() {
        // reload sample document with createKeySettings that require PIN
        val storageEngine = EphemeralStorageEngine()
        val documentManager = SampleDocumentManagerImpl(
            DocumentManagerImpl(
                storageEngine = storageEngine,
                secureArea = SoftwareSecureArea(storageEngine)
            )
        )
        val createKeySettings = SoftwareCreateKeySettings.Builder()
            .setPassphraseRequired(true, "1234", PassphraseConstraints.PIN_FOUR_DIGITS)
            .build()
        val loadResult = documentManager.loadMdocSampleDocuments(
            sampleDocuments, createKeySettings, mapOf(
                "eu.europa.ec.eudi.pid.1" to "EU PID",
                "org.iso.18013.5.1.mDL" to "mDL"
            )
        )
        assertTrue(loadResult.isSuccess)

        val document = documentManager.getDocuments().first()
        val dataToSign = byteArrayOf(1, 2, 3)
        val signResult = document.sign(dataToSign, keyUnlockData = SoftwareKeyUnlockData("1234"))
        assertTrue(signResult.isSuccess)
    }

    @Test
    fun `issued document nameSpacedDataValues parses correctly the cbor values`() {
        val document = documentManager.getDocuments {
            (it.format as MsoMdocFormat).docType == "org.iso.18013.5.1.mDL"
        }.first()

        assertIs<IssuedDocument>(document)
        val data = document.nameSpacedDataDecoded
        assertEquals(1, data.size)
        assertEquals("org.iso.18013.5.1", data.keys.first())
    }

    @Test
    fun `getDocumentById should return the document`() {
        val documents = documentManager.getDocuments()
        val firstDocument = documents.first()
        val documentId = firstDocument.id
        val document = documentManager.getDocumentById(documentId)

        assertEquals(firstDocument, document)
    }
}