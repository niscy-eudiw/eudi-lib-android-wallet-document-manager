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

import com.android.identity.cbor.Cbor
import com.android.identity.document.NameSpacedData
import eu.europa.ec.eudi.wallet.document.DocType
import eu.europa.ec.eudi.wallet.document.NameSpacedValues
import eu.europa.ec.eudi.wallet.document.NameSpaces
import eu.europa.ec.eudi.wallet.document.internal.toObject

/**
 * Represents a document data in the MsoMdoc format.
 *
 * @property format the format of the document data
 * @property claims the claims of the document
 * @property docType the type of the document
 * @property nameSpacedData the name spaced data of the document
 * @property nameSpacedDataInBytes the name spaced data of the document in bytes
 * @property nameSpacedDataDecoded the name spaced data of the document decoded
 * @property nameSpaces the name spaces of the document
 */
data class MsoMdocData(
    override val format: MsoMdocFormat,
    val nameSpacedData: NameSpacedData
) : DocumentData {
    val docType: DocType
        get() = format.docType

    override val claims: List<MsoMdocClaim>
        get() = nameSpacedDataDecoded.flatMap { (nameSpace, elements) ->
            elements.map { (elementName, elementValue) ->
                MsoMdocClaim(nameSpace, elementName, elementValue)
            }
        }

    val nameSpacedDataInBytes: NameSpacedValues<ByteArray>
        get() = nameSpacedData.nameSpaceNames.associateWith { nameSpace ->
            nameSpacedData.getDataElementNames(nameSpace)
                .associateWith { elementIdentifier ->
                    nameSpacedData.getDataElement(nameSpace, elementIdentifier)
                }
        }

    val nameSpacedDataDecoded: NameSpacedValues<Any?>
        get() = nameSpacedDataInBytes.mapValues {
            it.value.mapValues {
                it.value.toObject()
            }
        }

    val nameSpaces: NameSpaces
        get() = nameSpacedDataInBytes.mapValues { it.value.keys.toList() }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MsoMdocData) return false

        if (format != other.format) return false
        if (!Cbor.encode(nameSpacedData.toDataItem())
                .contentEquals(Cbor.encode(other.nameSpacedData.toDataItem()))
        ) return false

        return true
    }

    override fun hashCode(): Int {
        var result = format.hashCode()
        result = 31 * result + Cbor.encode(nameSpacedData.toDataItem()).contentHashCode()
        return result
    }
}