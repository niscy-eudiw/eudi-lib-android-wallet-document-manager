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

package eu.europa.ec.eudi.wallet.document.mock_data

import eu.europa.ec.eudi.wallet.document.metadata.DocumentMetadata
import java.net.URI
import java.util.Locale

object DocumentMetaDataMockData {

    fun getData(): DocumentMetadata {
        // Initialize displays
        val displays = listOf(
            DocumentMetadata.Display(
                name = "Example Display",
                locale = Locale.ENGLISH,
                logo = DocumentMetadata.Display.Logo(
                    uri = URI.create("https://example.com/logo.png"),
                    alternativeText = "Example Logo"
                ),
                description = "This is a sample description",
                backgroundColor = "#FFFFFF",
                textColor = "#000000"
            )
        )

        // Create claims
        val msoClaimName = DocumentMetadata.Claim.Name.MsoMdoc(
            name = "MsoClaim",
            nameSpace = "namespace.mso"
        )
        val sdJwtClaimName = DocumentMetadata.Claim.Name.SdJwtVc(
            name = "SdJwtClaim"
        )

        val claims: List<DocumentMetadata.Claim> = listOf(
            DocumentMetadata.Claim(
                name = msoClaimName,
                mandatory = true,
                valueType = "string",
                display = listOf(
                    DocumentMetadata.Claim.Display(
                        name = "Mso Claim Display",
                        locale = Locale.ENGLISH
                    )
                )
            ),
            DocumentMetadata.Claim(
                name = sdJwtClaimName,
                mandatory = false,
                valueType = "integer",
                display = listOf(
                    DocumentMetadata.Claim.Display(
                        name = "SdJwt Claim Display",
                        locale = Locale.FRENCH
                    )
                )
            )
        )

        // Create and return DocumentMetaData
        return DocumentMetadata(
            display = displays,
            claims = claims
        )
    }
}