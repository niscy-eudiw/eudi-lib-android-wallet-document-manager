//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document.format](../index.md)/[MsoMdocClaim](index.md)

# MsoMdocClaim

[androidJvm]\
data class [MsoMdocClaim](index.md)(val
nameSpace: [NameSpace](../../eu.europa.ec.eudi.wallet.document/-name-space/index.md), val
elementIdentifier: [ElementIdentifier](../../eu.europa.ec.eudi.wallet.document/-element-identifier/index.md),
val elementValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, val
metadata: [DocumentMetaData.Claim](../../eu.europa.ec.eudi.wallet.document.metadata/-document-meta-data/-claim/index.md)? =
null) : [DocumentData.Claim](../-document-data/-claim/index.md)

A document claim that is part of a MsoMdoc document.

## Constructors

|                                    |                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
|------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [MsoMdocClaim](-mso-mdoc-claim.md) | [androidJvm]<br>constructor(nameSpace: [NameSpace](../../eu.europa.ec.eudi.wallet.document/-name-space/index.md), elementIdentifier: [ElementIdentifier](../../eu.europa.ec.eudi.wallet.document/-element-identifier/index.md), elementValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?, metadata: [DocumentMetaData.Claim](../../eu.europa.ec.eudi.wallet.document.metadata/-document-meta-data/-claim/index.md)? = null) |

## Properties

| Name                                                 | Summary                                                                                                                                                                                                         |
|------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [elementIdentifier](element-identifier.md)           | [androidJvm]<br>val [elementIdentifier](element-identifier.md): [ElementIdentifier](../../eu.europa.ec.eudi.wallet.document/-element-identifier/index.md)<br>The identifier of the claim.                       |
| [elementValue](element-value.md)                     | [androidJvm]<br>val [elementValue](element-value.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?<br>The value of the claim.                                                    |
| [identifier](../-document-data/-claim/identifier.md) | [androidJvm]<br>val [identifier](../-document-data/-claim/identifier.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The claim identifier.                             |
| [metadata](metadata.md)                              | [androidJvm]<br>open override val [metadata](metadata.md): [DocumentMetaData.Claim](../../eu.europa.ec.eudi.wallet.document.metadata/-document-meta-data/-claim/index.md)? = null<br>The metadata of the claim. |
| [nameSpace](name-space.md)                           | [androidJvm]<br>val [nameSpace](name-space.md): [NameSpace](../../eu.europa.ec.eudi.wallet.document/-name-space/index.md)<br>The namespace of the claim.                                                        |
| [value](../-document-data/-claim/value.md)           | [androidJvm]<br>val [value](../-document-data/-claim/value.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?<br>The claim value.                                                 |
