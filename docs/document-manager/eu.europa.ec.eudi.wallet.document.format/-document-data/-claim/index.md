//[document-manager](../../../../index.md)/[eu.europa.ec.eudi.wallet.document.format](../../index.md)/[DocumentData](../index.md)/[Claim](index.md)

# Claim

open class [Claim](index.md)(val
identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val
value: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val
metadata: [DocumentMetaData.Claim](../../../eu.europa.ec.eudi.wallet.document.metadata/-document-meta-data/-claim/index.md)?)

Document claim.

#### Inheritors

|                                                |
|------------------------------------------------|
| [MsoMdocClaim](../../-mso-mdoc-claim/index.md) |

## Constructors

|                    |                                                                                                                                                                                                                                                                                                                                             |
|--------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Claim](-claim.md) | [androidJvm]<br>constructor(identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, metadata: [DocumentMetaData.Claim](../../../eu.europa.ec.eudi.wallet.document.metadata/-document-meta-data/-claim/index.md)?) |

## Properties

| Name                        | Summary                                                                                                                                                                                     |
|-----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [identifier](identifier.md) | [androidJvm]<br>val [identifier](identifier.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The claim identifier.                                  |
| [metadata](metadata.md)     | [androidJvm]<br>open val [metadata](metadata.md): [DocumentMetaData.Claim](../../../eu.europa.ec.eudi.wallet.document.metadata/-document-meta-data/-claim/index.md)?<br>The claim metadata. |
| [value](value.md)           | [androidJvm]<br>val [value](value.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?<br>The claim value.                                                      |
