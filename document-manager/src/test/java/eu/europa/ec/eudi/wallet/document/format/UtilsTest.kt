/*
 * Copyright (c) 2025 European Commission
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

import com.android.identity.document.NameSpacedData
import com.android.identity.securearea.SecureArea
import eu.europa.ec.eudi.wallet.document.IssuedDocument
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant

class UtilsTest {

    // Test data - a sample tree of claims
    private val testClaims = listOf(
        SdJwtVcClaim(
            identifier = "person",
            value = mapOf(
                "name" to "John",
                "age" to 30,
                "address" to mapOf("street" to "Main St", "city" to "Anytown")
            ),
            rawValue = """{"name":"John","age":30,"address":{"street":"Main St","city":"Anytown"}}""",
            metadata = null,
            selectivelyDisclosable = true,
            children = listOf(
                SdJwtVcClaim(
                    identifier = "name",
                    value = "John",
                    rawValue = "John",
                    metadata = null,
                    selectivelyDisclosable = true,
                    children = emptyList()
                ),
                SdJwtVcClaim(
                    identifier = "age",
                    value = 30,
                    rawValue = "30",
                    metadata = null,
                    selectivelyDisclosable = false,
                    children = emptyList()
                ),
                SdJwtVcClaim(
                    identifier = "address",
                    value = mapOf("street" to "Main St", "city" to "Anytown"),
                    rawValue = """{"street":"Main St","city":"Anytown"}""",
                    metadata = null,
                    selectivelyDisclosable = true,
                    children = listOf(
                        SdJwtVcClaim(
                            identifier = "street",
                            value = "Main St",
                            rawValue = "Main St",
                            metadata = null,
                            selectivelyDisclosable = true,
                            children = emptyList()
                        ),
                        SdJwtVcClaim(
                            identifier = "city",
                            value = "Anytown",
                            rawValue = "Anytown",
                            metadata = null,
                            selectivelyDisclosable = true,
                            children = emptyList()
                        )
                    )
                )
            )
        ),
        SdJwtVcClaim(
            identifier = "issuanceDate",
            value = "2023-01-01",
            rawValue = "2023-01-01",
            metadata = null,
            selectivelyDisclosable = false,
            children = emptyList()
        )
    )

    // Sample MsoMdocClaims for testing
    private val testMsoMdocClaims = listOf(
        MsoMdocClaim(
            nameSpace = "org.iso.18013.5.1",
            identifier = "family_name",
            value = "Doe",
            rawValue = "Doe".toByteArray(),
            metadata = null
        ),
        MsoMdocClaim(
            nameSpace = "org.iso.18013.5.1",
            identifier = "given_name",
            value = "John",
            rawValue = "John".toByteArray(),
            metadata = null
        ),
        MsoMdocClaim(
            nameSpace = "org.iso.18013.5.1",
            identifier = "birth_date",
            value = "1990-01-01",
            rawValue = "1990-01-01".toByteArray(),
            metadata = null
        ),
        MsoMdocClaim(
            nameSpace = "org.iso.18013.5.1.1",
            identifier = "document_number",
            value = "123456789",
            rawValue = "123456789".toByteArray(),
            metadata = null
        )
    )

    // Sample SdJwtVc IssuedDocument for extension method tests
    private val testSdJwtDocument = IssuedDocument(
        id = "test-doc-id",
        name = "Test Document",
        documentManagerId = "test-manager-id",
        isCertified = true,
        keyAlias = "test-key-alias",
        secureArea = mockk<SecureArea>(),
        createdAt = Instant.now(),
        validFrom = Instant.now(),
        validUntil = Instant.now().plusSeconds(3600),
        issuedAt = Instant.now(),
        issuerProvidedData = ByteArray(0),
        data = mockk<SdJwtVcData> {
            every { format } returns SdJwtVcFormat(vct = "euPID")
            every { claims } returns testClaims
        }
    )

    // Sample MsoMdoc IssuedDocument for extension method tests
    private val testMsoMdocDocument = IssuedDocument(
        id = "test-mdoc-id",
        name = "Test Mdoc Document",
        documentManagerId = "test-manager-id",
        isCertified = true,
        keyAlias = "test-key-alias",
        secureArea = mockk<SecureArea>(),
        createdAt = Instant.now(),
        validFrom = Instant.now(),
        validUntil = Instant.now().plusSeconds(3600),
        issuedAt = Instant.now(),
        issuerProvidedData = ByteArray(0),
        data = mockk<MsoMdocData> {
            every { format } returns MsoMdocFormat(docType = "org.iso.18013.5.1.mDL")
            every { claims } returns testMsoMdocClaims
            every { nameSpacedData } returns mockk<NameSpacedData>()
        }
    )

    @Test
    fun `getAllClaimPathsFrom with empty path returns all paths`() {
        val paths = getAllClaimPathsFrom(testClaims, emptyList())

        val expected = listOf(
            listOf("person"),
            listOf("person", "name"),
            listOf("person", "age"),
            listOf("person", "address"),
            listOf("person", "address", "street"),
            listOf("person", "address", "city"),
            listOf("issuanceDate")
        )

        assertEquals(expected, paths)
    }

    @Test
    fun `getAllClaimPathsFrom with specific path returns child paths`() {
        val paths = getAllClaimPathsFrom(testClaims, listOf("person"))

        val expected = listOf(
            listOf("person", "name"),
            listOf("person", "age"),
            listOf("person", "address"),
            listOf("person", "address", "street"),
            listOf("person", "address", "city")
        )

        assertEquals(expected, paths)
    }

    @Test
    fun `getAllClaimPathsFrom with specific nested path returns child paths`() {
        val paths = getAllClaimPathsFrom(testClaims, listOf("person", "address"))

        val expected = listOf(
            listOf("person", "address", "street"),
            listOf("person", "address", "city")
        )

        assertEquals(expected, paths)
    }

    @Test
    fun `getAllClaimPathsFrom with leaf node path returns empty list`() {
        val paths = getAllClaimPathsFrom(testClaims, listOf("person", "name"))
        assertEquals(emptyList<List<String>>(), paths)
    }

    @Test
    fun `getAllClaimPathsFrom with non-existent path returns empty list`() {
        val paths = getAllClaimPathsFrom(testClaims, listOf("nonexistent"))
        assertEquals(emptyList<List<String>>(), paths)
    }

    @Test
    fun `collectAllPaths collects all paths recursively`() {
        val paths = collectAllPaths(testClaims, emptyList())

        val expected = listOf(
            listOf("person"),
            listOf("person", "name"),
            listOf("person", "age"),
            listOf("person", "address"),
            listOf("person", "address", "street"),
            listOf("person", "address", "city"),
            listOf("issuanceDate")
        )

        assertEquals(expected, paths)
    }

    @Test
    fun `collectAllPaths with base path prepends to all paths`() {
        val paths = collectAllPaths(testClaims, listOf("base", "path"))

        val expected = listOf(
            listOf("base", "path", "person"),
            listOf("base", "path", "person", "name"),
            listOf("base", "path", "person", "age"),
            listOf("base", "path", "person", "address"),
            listOf("base", "path", "person", "address", "street"),
            listOf("base", "path", "person", "address", "city"),
            listOf("base", "path", "issuanceDate")
        )

        assertEquals(expected, paths)
    }

    @Test
    fun `findClaimAtPath returns correct claim`() {
        val claim = findClaimAtPath(testClaims, listOf("person"))
        assertEquals("person", claim?.identifier)
    }

    @Test
    fun `findClaimAtPath with nested path returns correct claim`() {
        val claim = findClaimAtPath(testClaims, listOf("person", "address", "city"))
        assertEquals("city", claim?.identifier)
        assertEquals("Anytown", claim?.value)
    }

    @Test
    fun `findClaimAtPath with non-existent path returns null`() {
        val claim = findClaimAtPath(testClaims, listOf("person", "nonexistent"))
        assertNull(claim)
    }

    @Test
    fun `findClaimAtPath with empty path returns null`() {
        val claim = findClaimAtPath(testClaims, emptyList())
        assertNull(claim)
    }

    @Test
    fun `findClaimAtPath with partial matching path returns null`() {
        val claim = findClaimAtPath(testClaims, listOf("person", "address", "nonexistent"))
        assertNull(claim)
    }

    @Test
    fun `getAllClaimPathsFrom with empty claims returns empty list`() {
        val paths = getAllClaimPathsFrom(emptyList(), emptyList())
        assertEquals(emptyList<List<String>>(), paths)
    }

    @Test
    fun `collectAllPaths with empty claims returns empty list`() {
        val paths = collectAllPaths(emptyList(), listOf("base"))
        assertEquals(emptyList<List<String>>(), paths)
    }

    // Tests for SdJwtVc IssuedDocument extension methods

    @Test
    fun `findClaimAtPath extension for SdJwtVc IssuedDocument returns correct claim`() {
        val result = testSdJwtDocument.findClaimAtPath(listOf("person", "address", "city"))
        assertTrue(result.isSuccess)

        val claim = result.getOrNull()
        assertEquals("city", claim?.identifier)
        assertEquals("Anytown", claim?.value)
    }

    @Test
    fun `findClaimAtPath extension for SdJwtVc IssuedDocument with non-existent path returns null`() {
        val result = testSdJwtDocument.findClaimAtPath(listOf("person", "nonexistent"))
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun `getAllClaimPathsFrom extension for SdJwtVc IssuedDocument returns all paths from root`() {
        val result = testSdJwtDocument.getAllClaimPathsFrom(listOf("person"))
        assertTrue(result.isSuccess)

        val paths = result.getOrNull()
        val expected = listOf(
            listOf("person", "name"),
            listOf("person", "age"),
            listOf("person", "address"),
            listOf("person", "address", "street"),
            listOf("person", "address", "city")
        )

        assertEquals(expected, paths)
    }

    @Test
    fun `getAllClaimPathsFrom extension for SdJwtVc IssuedDocument with non-existent path returns empty list`() {
        val result = testSdJwtDocument.getAllClaimPathsFrom(listOf("nonexistent"))
        assertTrue(result.isSuccess)
        assertEquals(emptyList<List<String>>(), result.getOrNull())
    }
    
    // Tests for MsoMdoc IssuedDocument extension methods
    
    @Test
    fun `findClaimAtPath extension for MsoMdoc IssuedDocument returns correct claim`() {
        val result = testMsoMdocDocument.findClaimAtPath(
            listOf("org.iso.18013.5.1", "family_name")
        )
        assertTrue(result.isSuccess)
        
        val claim = result.getOrNull()
        assertEquals("family_name", claim?.identifier)
        assertEquals("Doe", claim?.value)
    }
    
    @Test
    fun `findClaimAtPath extension for MsoMdoc IssuedDocument with non-existent claim returns null`() {
        val result = testMsoMdocDocument.findClaimAtPath(
            listOf("org.iso.18013.5.1", "nonexistent")
        )
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun `findClaimAtPath extension for MsoMdoc IssuedDocument with path length not equal to 2 throws exception`() {
        testMsoMdocDocument.findClaimAtPath(
            listOf("org.iso.18013.5.1", "family_name", "extra")
        ).getOrThrow()
    }
    
    @Test
    fun `getAllClaimPathsFrom extension for MsoMdoc IssuedDocument returns claims from namespace`() {
        val result = testMsoMdocDocument.getAllClaimPathsFrom(
            listOf("org.iso.18013.5.1")
        )
        assertTrue(result.isSuccess)
        
        val paths = result.getOrNull()
        val expected = listOf(
            listOf("org.iso.18013.5.1", "family_name"),
            listOf("org.iso.18013.5.1", "given_name"),
            listOf("org.iso.18013.5.1", "birth_date")
        )
        
        assertEquals(expected, paths)
    }
    
    @Test
    fun `getAllClaimPathsFrom extension for MsoMdoc IssuedDocument with non-existent namespace returns empty list`() {
        val result = testMsoMdocDocument.getAllClaimPathsFrom(
            listOf("nonexistent.namespace")
        )
        assertTrue(result.isSuccess)
        assertEquals(emptyList<List<String>>(), result.getOrNull())
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun `getAllClaimPathsFrom extension for MsoMdoc IssuedDocument with path length not equal to 1 throws exception`() {
        testMsoMdocDocument.getAllClaimPathsFrom(
            listOf("org.iso.18013.5.1", "extra")
        ).getOrThrow()
    }
}
