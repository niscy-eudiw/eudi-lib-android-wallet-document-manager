//[document-manager](../../../../index.md)/[eu.europa.ec.eudi.wallet.document.metadata](../../index.md)/[DocumentMetaData](../index.md)/[Claim](index.md)

# Claim

[androidJvm]\
@Serializable

data class [Claim](index.md)(val
mandatory: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? =
false, val
valueType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null,
val
display: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)
&lt;[DocumentMetaData.Claim.Display](-display/index.md)&gt; =
emptyList()) : [Serializable](https://developer.android.com/reference/kotlin/java/io/Serializable.html)

## Constructors

|                    |                                                                                                                                                                                                                                                                                                                                                                                                                      |
|--------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Claim](-claim.md) | [androidJvm]<br>constructor(mandatory: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = false, valueType: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, display: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[DocumentMetaData.Claim.Display](-display/index.md)&gt; = emptyList()) |

## Types

| Name                         | Summary                                                                                                                                                                                                                                                                                                                                                                                                       |
|------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Display](-display/index.md) | [androidJvm]<br>@Serializable<br>data class [Display](-display/index.md)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, val locale: [Locale](https://developer.android.com/reference/kotlin/java/util/Locale.html)? = null) : [Serializable](https://developer.android.com/reference/kotlin/java/io/Serializable.html)<br>Display properties of a Claim. |

## Properties

| Name                       | Summary                                                                                                                                                                                                                                    |
|----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [display](display.md)      | [androidJvm]<br>@SerialName(value = &quot;display&quot;)<br>val [display](display.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[DocumentMetaData.Claim.Display](-display/index.md)&gt; |
| [mandatory](mandatory.md)  | [androidJvm]<br>@SerialName(value = &quot;mandatory&quot;)<br>val [mandatory](mandatory.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)? = false                                                   |
| [valueType](value-type.md) | [androidJvm]<br>@SerialName(value = &quot;value_type&quot;)<br>val [valueType](value-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null                                                    |