//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document.format](../index.md)/[MsoMdocClaim](index.md)

# MsoMdocClaim

[androidJvm]\
data class [MsoMdocClaim](index.md)(val
nameSpace: [NameSpace](../../eu.europa.ec.eudi.wallet.document/-name-space/index.md), val
elementIdentifier: [ElementIdentifier](../../eu.europa.ec.eudi.wallet.document/-element-identifier/index.md),
val
elementValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) : [DocumentClaim](../-document-claim/index.md)

A document claim that is part of a MsoMdoc document.

## Constructors

|                                    |                                                                                                                                                                                                                                                                                                                            |
|------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [MsoMdocClaim](-mso-mdoc-claim.md) | [androidJvm]<br>constructor(nameSpace: [NameSpace](../../eu.europa.ec.eudi.wallet.document/-name-space/index.md), elementIdentifier: [ElementIdentifier](../../eu.europa.ec.eudi.wallet.document/-element-identifier/index.md), elementValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |

## Properties

| Name                                           | Summary                                                                                                                                                                                   |
|------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [elementIdentifier](element-identifier.md)     | [androidJvm]<br>val [elementIdentifier](element-identifier.md): [ElementIdentifier](../../eu.europa.ec.eudi.wallet.document/-element-identifier/index.md)<br>The identifier of the claim. |
| [elementValue](element-value.md)               | [androidJvm]<br>val [elementValue](element-value.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?<br>The value of the claim.                              |
| [identifier](../-document-claim/identifier.md) | [androidJvm]<br>val [identifier](../-document-claim/identifier.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>The claim identifier.             |
| [nameSpace](name-space.md)                     | [androidJvm]<br>val [nameSpace](name-space.md): [NameSpace](../../eu.europa.ec.eudi.wallet.document/-name-space/index.md)<br>The namespace of the claim.                                  |
| [value](../-document-claim/value.md)           | [androidJvm]<br>val [value](../-document-claim/value.md): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?<br>The claim value.                                 |
