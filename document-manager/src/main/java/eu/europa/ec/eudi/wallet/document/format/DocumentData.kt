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

import eu.europa.ec.eudi.wallet.document.metadata.DocumentMetaData

/**
 * Container for the document data.
 * This interface is used to represent the document data and it is implemented
 * according to the document format.
 * The document data contains the format, the metadata and the claims of the document
 * @see [DocumentFormat]
 * @see [DocumentMetaData]
 *
 * @property format The document format.
 * @property claims The list of document claims.
 * @property metadata The document metadata.
 */
sealed interface DocumentData {
    val format: DocumentFormat
    val claims: List<Claim>
    val metadata: DocumentMetaData?

    /**
     * Document claim.
     *
     * @property identifier The claim identifier.
     * @property value The claim value.
     * @property metadata The claim metadata.
     */
    open class Claim(
        val identifier: String,
        val value: Any?,
        open val metadata: DocumentMetaData.Claim?
    )
}

