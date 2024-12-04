//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document.format](../index.md)/[MsoMdocData](index.md)

# MsoMdocData

[androidJvm]\
data class [MsoMdocData](index.md)(val format: [MsoMdocFormat](../-mso-mdoc-format/index.md), val
nameSpacedData: NameSpacedData) : [DocumentData](../-document-data/index.md)

Represents a document data in the MsoMdoc format.

## Constructors

|                                  |                                                                                                                    |
|----------------------------------|--------------------------------------------------------------------------------------------------------------------|
| [MsoMdocData](-mso-mdoc-data.md) | [androidJvm]<br>constructor(format: [MsoMdocFormat](../-mso-mdoc-format/index.md), nameSpacedData: NameSpacedData) |

## Properties

| Name                                                  | Summary                                                                                                                                                                                                                                                                                                             |
|-------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [claims](claims.md)                                   | [androidJvm]<br>open override val [claims](claims.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[MsoMdocClaim](../-mso-mdoc-claim/index.md)&gt;<br>the claims of the document                                                                                    |
| [docType](doc-type.md)                                | [androidJvm]<br>val [docType](doc-type.md): [DocType](../../eu.europa.ec.eudi.wallet.document/-doc-type/index.md)<br>the type of the document                                                                                                                                                                       |
| [format](format.md)                                   | [androidJvm]<br>open override val [format](format.md): [MsoMdocFormat](../-mso-mdoc-format/index.md)<br>the format of the document data                                                                                                                                                                             |
| [nameSpacedData](name-spaced-data.md)                 | [androidJvm]<br>val [nameSpacedData](name-spaced-data.md): NameSpacedData<br>the name spaced data of the document                                                                                                                                                                                                   |
| [nameSpacedDataDecoded](name-spaced-data-decoded.md)  | [androidJvm]<br>val [nameSpacedDataDecoded](name-spaced-data-decoded.md): [NameSpacedValues](../../eu.europa.ec.eudi.wallet.document/-name-spaced-values/index.md)&lt;[Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt;<br>the name spaced data of the document decoded               |
| [nameSpacedDataInBytes](name-spaced-data-in-bytes.md) | [androidJvm]<br>val [nameSpacedDataInBytes](name-spaced-data-in-bytes.md): [NameSpacedValues](../../eu.europa.ec.eudi.wallet.document/-name-spaced-values/index.md)&lt;[ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)&gt;<br>the name spaced data of the document in bytes |
| [nameSpaces](name-spaces.md)                          | [androidJvm]<br>val [nameSpaces](name-spaces.md): [NameSpaces](../../eu.europa.ec.eudi.wallet.document/-name-spaces/index.md)<br>the name spaces of the document                                                                                                                                                    |

## Functions

| Name                     | Summary                                                                                                                                                                                                                                |
|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [equals](equals.md)      | [androidJvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [hashCode](hash-code.md) | [androidJvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)                                                                                               |
