//[document-manager](../../../../../index.md)/[eu.europa.ec.eudi.wallet.document.metadata](../../../index.md)/[DocumentMetadata](../../index.md)/[Claim](../index.md)/[Name](index.md)

# Name

@Serializable

sealed interface [Name](index.md)

Claim name.

#### See also

|                                                            |
|------------------------------------------------------------|
| [DocumentMetadata.Claim.Name.MsoMdoc](-mso-mdoc/index.md)  |
| [DocumentMetadata.Claim.Name.SdJwtVc](-sd-jwt-vc/index.md) |

#### Inheritors

|                                |
|--------------------------------|
| [MsoMdoc](-mso-mdoc/index.md)  |
| [SdJwtVc](-sd-jwt-vc/index.md) |

## Types

| Name                           | Summary                                                                                                                                                                                                                                                                                                                                 |
|--------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [MsoMdoc](-mso-mdoc/index.md)  | [androidJvm]<br>@Serializable<br>data class [MsoMdoc](-mso-mdoc/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val nameSpace: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [DocumentMetadata.Claim.Name](index.md)<br>MsoMdoc claim name. |
| [SdJwtVc](-sd-jwt-vc/index.md) | [androidJvm]<br>@Serializable<br>data class [SdJwtVc](-sd-jwt-vc/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [DocumentMetadata.Claim.Name](index.md)<br>SdJwtVc claim name.                                                                                                 |

## Properties

| Name            | Summary                                                                                                                                                 |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| [name](name.md) | [androidJvm]<br>abstract val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the name of the claim |
