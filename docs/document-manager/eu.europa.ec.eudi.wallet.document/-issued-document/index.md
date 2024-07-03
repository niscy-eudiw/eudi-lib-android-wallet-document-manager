//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document](../index.md)/[IssuedDocument](index.md)

# IssuedDocument

[androidJvm]\
class [IssuedDocument](index.md) : [Document](../-document/index.md)

An [IssuedDocument](index.md) is a document that has been issued. It contains the data that was issued. To store
the [IssuedDocument](index.md), use
the [DocumentManager.storeIssuedDocument](../-document-manager/store-issued-document.md) method.

## Properties

| Name                                                              | Summary                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|-------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [createdAt](../-document/created-at.md)                           | [androidJvm]<br>open override val [createdAt](../-document/created-at.md): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)<br>the creation date of the document                                                                                                                                                                                                                                                                                                                                                                                |
| [docType](../-document/doc-type.md)                               | [androidJvm]<br>open override val [docType](../-document/doc-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the document type                                                                                                                                                                                                                                                                                                                                                                                                    |
| [id](../-document/id.md)                                          | [androidJvm]<br>open override val [id](../-document/id.md): [DocumentId](../index.md#659369697%2FClasslikes%2F1351694608)<br>the identifier of the document                                                                                                                                                                                                                                                                                                                                                                                                                     |
| [isDeferred](../-document/is-deferred.md)                         | [androidJvm]<br>open override val [isDeferred](../-document/is-deferred.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document is deferred                                                                                                                                                                                                                                                                                                                                                                             |
| [isIssued](../-document/is-issued.md)                             | [androidJvm]<br>open override val [isIssued](../-document/is-issued.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document is issued                                                                                                                                                                                                                                                                                                                                                                                   |
| [issuedAt](issued-at.md)                                          | [androidJvm]<br>val [issuedAt](issued-at.md): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)<br>document's issuance date                                                                                                                                                                                                                                                                                                                                                                                                                      |
| [isUnsigned](../-document/is-unsigned.md)                         | [androidJvm]<br>open override val [isUnsigned](../-document/is-unsigned.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document is unsigned                                                                                                                                                                                                                                                                                                                                                                             |
| [name](../-document/name.md)                                      | [androidJvm]<br>open override val [name](../-document/name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the name of the document                                                                                                                                                                                                                                                                                                                                                                                                    |
| [nameSpacedData](name-spaced-data.md)                             | [androidJvm]<br>val [nameSpacedData](name-spaced-data.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[NameSpace](../index.md#1862659344%2FClasslikes%2F1351694608), [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[ElementIdentifier](../index.md#-190936378%2FClasslikes%2F1351694608), [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)&gt;&gt;<br>retrieves the document's data, grouped by nameSpace. Values are in CBOR bytes           |
| [nameSpacedDataJSONObject](../name-spaced-data-j-s-o-n-object.md) | [androidJvm]<br>@get:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;nameSpacedDataAsJSONObject&quot;)<br>val [IssuedDocument](index.md).[nameSpacedDataJSONObject](../name-spaced-data-j-s-o-n-object.md): [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)<br>Extension function to convert [IssuedDocument](index.md)'s nameSpacedData to [JSONObject](https://developer.android.com/reference/kotlin/org/json/JSONObject.html)                                                    |
| [nameSpacedDataValues](name-spaced-data-values.md)                | [androidJvm]<br>val [nameSpacedDataValues](name-spaced-data-values.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[NameSpace](../index.md#1862659344%2FClasslikes%2F1351694608), [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[ElementIdentifier](../index.md#-190936378%2FClasslikes%2F1351694608), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt;&gt;<br>retrieves the document's data, grouped by nameSpace. Values are in their original type |
| [nameSpaces](name-spaces.md)                                      | [androidJvm]<br>val [nameSpaces](name-spaces.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[NameSpace](../index.md#1862659344%2FClasslikes%2F1351694608), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ElementIdentifier](../index.md#-190936378%2FClasslikes%2F1351694608)&gt;&gt;<br>retrieves the document's nameSpaces and elementIdentifiers                                                                                                                              |
| [requiresUserAuth](../-document/requires-user-auth.md)            | [androidJvm]<br>open override val [requiresUserAuth](../-document/requires-user-auth.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document requires user authentication                                                                                                                                                                                                                                                                                                                                               |
| [state](../-document/state.md)                                    | [androidJvm]<br>open override val [state](../-document/state.md): [Document.State](../-document/-state/index.md)<br>the state of the document                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| [usesStrongBox](../-document/uses-strong-box.md)                  | [androidJvm]<br>open override val [usesStrongBox](../-document/uses-strong-box.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document's keys are in strongBox                                                                                                                                                                                                                                                                                                                                                          |