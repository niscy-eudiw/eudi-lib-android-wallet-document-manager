//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document.format](../index.md)/[DocumentData](index.md)

# DocumentData

sealed interface [DocumentData](index.md)

Container for the document data. This interface is used to represent the document data and it is
implemented according to the document format. The document data contains the format, the metadata
and the claims of the document

#### See also

|                                                                                                  |
|--------------------------------------------------------------------------------------------------|
| [DocumentFormat](../-document-format/index.md)                                                   |
| [DocumentMetadata](../../eu.europa.ec.eudi.wallet.document.metadata/-document-metadata/index.md) |

#### Inheritors

|                                           |
|-------------------------------------------|
| [MsoMdocData](../-mso-mdoc-data/index.md) |

## Types

| Name                     | Summary                                                                                                                                                                                                                                                                                                                                                                                        |
|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Claim](-claim/index.md) | [androidJvm]<br>open class [Claim](-claim/index.md)(val identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val value: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val metadata: [DocumentMetadata.Claim](../../eu.europa.ec.eudi.wallet.document.metadata/-document-metadata/-claim/index.md)?)<br>Document claim. |

## Properties

| Name                    | Summary                                                                                                                                                                                                                 |
|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [claims](claims.md)     | [androidJvm]<br>abstract val [claims](claims.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[DocumentData.Claim](-claim/index.md)&gt;<br>The list of document claims. |
| [format](format.md)     | [androidJvm]<br>abstract val [format](format.md): [DocumentFormat](../-document-format/index.md)<br>The document format.                                                                                                |
| [metadata](metadata.md) | [androidJvm]<br>abstract val [metadata](metadata.md): [DocumentMetadata](../../eu.europa.ec.eudi.wallet.document.metadata/-document-metadata/index.md)?<br>The document metadata.                                       |
