/*
 *  Copyright (c) 2024-2025 European Commission
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

package eu.europa.ec.eudi.wallet.document.metadata

import kotlinx.serialization.SerializationException
import java.net.URI
import java.util.*
import kotlin.test.*

class IssuerMetadataTest {

    @Test
    fun `create IssuerMetadata with all properties`() {
        val metadata = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = listOf(
                IssuerMetadata.Display(
                    name = "Example Document",
                    locale = Locale.ENGLISH,
                    logo = IssuerMetadata.Logo(
                        uri = URI.create("https://example.com/logo.png"),
                        alternativeText = "Example Logo"
                    ),
                    description = "An example document",
                    backgroundColor = "#FFFFFF",
                    textColor = "#000000",
                    backgroundImageUri = URI.create("https://example.com/bg.png")
                )
            ),
            claims = listOf(
                IssuerMetadata.Claim(
                    path = listOf("namespace", "element"),
                    mandatory = true,
                    display = listOf(
                        IssuerMetadata.Claim.Display(
                            name = "Claim Name",
                            locale = Locale.ENGLISH
                        )
                    )
                )
            ),
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = listOf(
                IssuerMetadata.IssuerDisplay(
                    name = "Example Issuer",
                    locale = Locale.ENGLISH,
                    logo = IssuerMetadata.Logo(
                        uri = URI.create("https://issuer.example.com/logo.png"),
                        alternativeText = "Issuer Logo"
                    )
                )
            )
        )

        assertEquals("org.example.document", metadata.documentConfigurationIdentifier)
        assertEquals("https://issuer.example.com", metadata.credentialIssuerIdentifier)
        assertEquals(1, metadata.display.size)
        assertEquals(1, metadata.claims?.size)
        assertEquals(1, metadata.issuerDisplay?.size)
    }

    @Test
    fun `create IssuerMetadata with minimal properties`() {
        val metadata = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = emptyList(),
            claims = null,
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = null
        )

        assertEquals("org.example.document", metadata.documentConfigurationIdentifier)
        assertEquals("https://issuer.example.com", metadata.credentialIssuerIdentifier)
        assertTrue(metadata.display.isEmpty())
        assertNull(metadata.claims)
        assertNull(metadata.issuerDisplay)
    }

    @Test
    fun `toJson serializes IssuerMetadata correctly`() {
        val metadata = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = listOf(
                IssuerMetadata.Display(
                    name = "Example Document",
                    locale = Locale.ENGLISH
                )
            ),
            claims = null,
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = null
        )

        val json = metadata.toJson()

        assertNotNull(json)
        assertTrue(json.contains("\"documentConfigurationIdentifier\":\"org.example.document\""))
        assertTrue(json.contains("\"credentialIssuerIdentifier\":\"https://issuer.example.com\""))
        assertTrue(json.contains("\"name\":\"Example Document\""))
    }

    @Test
    fun `fromJson deserializes IssuerMetadata correctly`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [
                    {
                        "name": "Example Document",
                        "locale": "en"
                    }
                ],
                "claims": null,
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals("org.example.document", metadata.documentConfigurationIdentifier)
        assertEquals("https://issuer.example.com", metadata.credentialIssuerIdentifier)
        assertEquals("Example Document", metadata.display[0].name)
        assertEquals(Locale.ENGLISH, metadata.display[0].locale)
    }

    @Test
    fun `fromJson with complete Display object`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [
                    {
                        "name": "Example Document",
                        "locale": "en-US",
                        "logo": {
                            "uri": "https://example.com/logo.png",
                            "alternativeText": "Logo"
                        },
                        "description": "Description",
                        "backgroundColor": "#FFFFFF",
                        "textColor": "#000000",
                        "backgroundImageUri": "https://example.com/bg.png"
                    }
                ],
                "claims": null,
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        val display = metadata.display[0]
        assertEquals("Example Document", display.name)
        assertEquals(Locale.forLanguageTag("en-US"), display.locale)
        assertEquals(URI.create("https://example.com/logo.png"), display.logo?.uri)
        assertEquals("Logo", display.logo?.alternativeText)
        assertEquals("Description", display.description)
        assertEquals("#FFFFFF", display.backgroundColor)
        assertEquals("#000000", display.textColor)
        assertEquals(URI.create("https://example.com/bg.png"), display.backgroundImageUri)
    }

    @Test
    fun `fromJson with Claims`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [],
                "claims": [
                    {
                        "path": ["namespace", "element"],
                        "mandatory": true,
                        "display": [
                            {
                                "name": "Claim Name",
                                "locale": "en"
                            }
                        ]
                    }
                ],
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals(1, metadata.claims?.size)
        val claim = metadata.claims?.get(0)
        assertNotNull(claim)
        assertEquals(listOf("namespace", "element"), claim.path)
        assertEquals(true, claim.mandatory)
        assertEquals("Claim Name", claim.display[0].name)
        assertEquals(Locale.ENGLISH, claim.display[0].locale)
    }

    @Test
    fun `fromJson with IssuerDisplay`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [],
                "claims": null,
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": [
                    {
                        "name": "Example Issuer",
                        "locale": "en",
                        "logo": {
                            "uri": "https://issuer.example.com/logo.png",
                            "alternativeText": "Issuer Logo"
                        }
                    }
                ]
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals(1, metadata.issuerDisplay?.size)
        val issuerDisplay = metadata.issuerDisplay?.get(0)
        assertNotNull(issuerDisplay)
        assertEquals("Example Issuer", issuerDisplay.name)
        assertEquals(Locale.ENGLISH, issuerDisplay.locale)
        assertEquals(URI.create("https://issuer.example.com/logo.png"), issuerDisplay.logo?.uri)
        assertEquals("Issuer Logo", issuerDisplay.logo?.alternativeText)
    }

    @Test
    fun `fromJson with invalid JSON returns failure`() {
        val json = "{ invalid json }"

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is SerializationException)
    }

    @Test
    fun `fromJson with missing required fields returns failure`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document"
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isFailure)
    }

    @Test
    fun `fromJson ignores unknown keys`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [],
                "claims": null,
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null,
                "unknownField": "value",
                "anotherUnknownField": 123
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals("org.example.document", metadata.documentConfigurationIdentifier)
    }

    @Test
    fun `toByteArray converts to byte array`() {
        val metadata = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = emptyList(),
            claims = null,
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = null
        )

        val byteArray = metadata.toByteArray()

        assertNotNull(byteArray)
        assertTrue(byteArray.isNotEmpty())

        // Verify it can be converted back
        val result = IssuerMetadata.fromByteArray(byteArray)
        assertTrue(result.isSuccess)
        assertEquals(metadata, result.getOrNull())
    }

    @Test
    fun `fromByteArray deserializes correctly`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [],
                "claims": null,
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val byteArray = json.toByteArray()
        val result = IssuerMetadata.fromByteArray(byteArray)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals("org.example.document", metadata.documentConfigurationIdentifier)
    }

    @Test
    fun `roundtrip toJson and fromJson preserves data`() {
        val original = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = listOf(
                IssuerMetadata.Display(
                    name = "Example Document",
                    locale = Locale.forLanguageTag("en-US"),
                    logo = IssuerMetadata.Logo(
                        uri = URI.create("https://example.com/logo.png"),
                        alternativeText = "Logo"
                    ),
                    description = "Description",
                    backgroundColor = "#FFFFFF",
                    textColor = "#000000",
                    backgroundImageUri = URI.create("https://example.com/bg.png")
                )
            ),
            claims = listOf(
                IssuerMetadata.Claim(
                    path = listOf("namespace", "element"),
                    mandatory = true,
                    display = listOf(
                        IssuerMetadata.Claim.Display(
                            name = "Claim Name",
                            locale = Locale.ENGLISH
                        )
                    )
                )
            ),
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = listOf(
                IssuerMetadata.IssuerDisplay(
                    name = "Example Issuer",
                    locale = Locale.ENGLISH,
                    logo = IssuerMetadata.Logo(
                        uri = URI.create("https://issuer.example.com/logo.png"),
                        alternativeText = "Issuer Logo"
                    )
                )
            )
        )

        val json = original.toJson()
        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val deserialized = result.getOrNull()
        assertNotNull(deserialized)
        assertEquals(original.documentConfigurationIdentifier, deserialized.documentConfigurationIdentifier)
        assertEquals(original.credentialIssuerIdentifier, deserialized.credentialIssuerIdentifier)
        assertEquals(original.display.size, deserialized.display.size)
        assertEquals(original.claims?.size, deserialized.claims?.size)
        assertEquals(original.issuerDisplay?.size, deserialized.issuerDisplay?.size)
    }

    @Test
    fun `Display with null optional fields`() {
        val display = IssuerMetadata.Display(
            name = "Example Document",
            locale = null,
            logo = null,
            description = null,
            backgroundColor = null,
            textColor = null,
            backgroundImageUri = null
        )

        assertEquals("Example Document", display.name)
        assertNull(display.locale)
        assertNull(display.logo)
        assertNull(display.description)
        assertNull(display.backgroundColor)
        assertNull(display.textColor)
        assertNull(display.backgroundImageUri)
    }

    @Test
    fun `Logo with all properties`() {
        val logo = IssuerMetadata.Logo(
            uri = URI.create("https://example.com/logo.png"),
            alternativeText = "Logo Text"
        )

        assertEquals(URI.create("https://example.com/logo.png"), logo.uri)
        assertEquals("Logo Text", logo.alternativeText)
    }

    @Test
    fun `Logo with null properties`() {
        val logo = IssuerMetadata.Logo(
            uri = null,
            alternativeText = null
        )

        assertNull(logo.uri)
        assertNull(logo.alternativeText)
    }

    @Test
    fun `Claim with default mandatory value`() {
        val claim = IssuerMetadata.Claim(
            path = listOf("namespace", "element"),
            display = emptyList()
        )

        assertEquals(listOf("namespace", "element"), claim.path)
        assertEquals(false, claim.mandatory)
        assertTrue(claim.display.isEmpty())
    }

    @Test
    fun `Claim Display with null fields`() {
        val display = IssuerMetadata.Claim.Display(
            name = null,
            locale = null
        )

        assertNull(display.name)
        assertNull(display.locale)
    }

    @Test
    fun `LocaleSerializer serializes and deserializes Locale correctly`() {
        val metadata = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = listOf(
                IssuerMetadata.Display(
                    name = "Example",
                    locale = Locale.forLanguageTag("en-US")
                )
            ),
            claims = null,
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = null
        )

        val json = metadata.toJson()
        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val deserialized = result.getOrNull()
        assertNotNull(deserialized)
        assertEquals(Locale.forLanguageTag("en-US"), deserialized.display[0].locale)
    }

    @Test
    fun `URISerializer serializes and deserializes URI correctly`() {
        val metadata = IssuerMetadata(
            documentConfigurationIdentifier = "org.example.document",
            display = listOf(
                IssuerMetadata.Display(
                    name = "Example",
                    logo = IssuerMetadata.Logo(
                        uri = URI.create("https://example.com/logo.png")
                    ),
                    backgroundImageUri = URI.create("https://example.com/bg.png")
                )
            ),
            claims = null,
            credentialIssuerIdentifier = "https://issuer.example.com",
            issuerDisplay = null
        )

        val json = metadata.toJson()
        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val deserialized = result.getOrNull()
        assertNotNull(deserialized)
        assertEquals(URI.create("https://example.com/logo.png"), deserialized.display[0].logo?.uri)
        assertEquals(URI.create("https://example.com/bg.png"), deserialized.display[0].backgroundImageUri)
    }

    @Test
    fun `fromJson handles multiple displays`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [
                    {
                        "name": "English Document",
                        "locale": "en"
                    },
                    {
                        "name": "French Document",
                        "locale": "fr"
                    }
                ],
                "claims": null,
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals(2, metadata.display.size)
        assertEquals("English Document", metadata.display[0].name)
        assertEquals(Locale.ENGLISH, metadata.display[0].locale)
        assertEquals("French Document", metadata.display[1].name)
        assertEquals(Locale.FRENCH, metadata.display[1].locale)
    }

    @Test
    fun `fromJson handles multiple claims`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [],
                "claims": [
                    {
                        "path": ["namespace1", "element1"],
                        "mandatory": true
                    },
                    {
                        "path": ["namespace2", "element2"],
                        "mandatory": false
                    }
                ],
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals(2, metadata.claims?.size)
        assertEquals(listOf("namespace1", "element1"), metadata.claims?.get(0)?.path)
        assertEquals(true, metadata.claims?.get(0)?.mandatory)
        assertEquals(listOf("namespace2", "element2"), metadata.claims?.get(1)?.path)
        assertEquals(false, metadata.claims?.get(1)?.mandatory)
    }

    @Test
    fun `fromJson handles sd-jwt-vc claim path`() {
        val json = """
            {
                "documentConfigurationIdentifier": "org.example.document",
                "display": [],
                "claims": [
                    {
                        "path": ["given_name"],
                        "mandatory": true
                    },
                    {
                        "path": ["address", "street_address"],
                        "mandatory": false
                    }
                ],
                "credentialIssuerIdentifier": "https://issuer.example.com",
                "issuerDisplay": null
            }
        """.trimIndent()

        val result = IssuerMetadata.fromJson(json)

        assertTrue(result.isSuccess)
        val metadata = result.getOrNull()
        assertNotNull(metadata)
        assertEquals(2, metadata.claims?.size)
        assertEquals(listOf("given_name"), metadata.claims?.get(0)?.path)
        assertEquals(listOf("address", "street_address"), metadata.claims?.get(1)?.path)
    }
}

