//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document.format](../index.md)/[DocumentData](index.md)

# DocumentData

sealed interface [DocumentData](index.md)

Container for the document data. This interface is used to represent the document data and it is
implemented according to the document format. The document data contains the format and the claims
of the document.

#### See also

|                                                |
|------------------------------------------------|
| [DocumentFormat](../-document-format/index.md) |

#### Inheritors

|                                           |
|-------------------------------------------|
| [MsoMdocData](../-mso-mdoc-data/index.md) |

## Properties

| Name                | Summary                                                                                                                                                                                                                        |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [claims](claims.md) | [androidJvm]<br>abstract val [claims](claims.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[DocumentClaim](../-document-claim/index.md)&gt;<br>The list of document claims. |
| [format](format.md) | [androidJvm]<br>abstract val [format](format.md): [DocumentFormat](../-document-format/index.md)<br>The document format.                                                                                                       |
