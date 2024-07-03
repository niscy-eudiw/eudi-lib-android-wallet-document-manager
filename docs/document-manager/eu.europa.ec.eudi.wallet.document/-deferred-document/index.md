//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document](../index.md)/[DeferredDocument](index.md)

# DeferredDocument

[androidJvm]\
class [DeferredDocument](index.md) : [UnsignedDocument](../-unsigned-document/index.md)

A [DeferredDocument](index.md) is as [UnsignedDocument](../-unsigned-document/index.md) with
extra [relatedData](related-data.md) that can be used later on by the issuing process. To store
the [DeferredDocument](index.md) and its related data, use
the [DocumentManager.storeDeferredDocument](../-document-manager/store-deferred-document.md)

## Functions

| Name                                                           | Summary                                                                                                                                                                                                                                                                                                                                                                                                              |
|----------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [signWithAuthKey](../-unsigned-document/sign-with-auth-key.md) | [androidJvm]<br>fun [signWithAuthKey](../-unsigned-document/sign-with-auth-key.md)(data: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), alg: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = Algorithm.SHA256withECDSA): [SignedWithAuthKeyResult](../-signed-with-auth-key-result/index.md)<br>Sign given data with authentication key |

## Properties

| Name                                                                    | Summary                                                                                                                                                                                                                                                                                                                                                                   |
|-------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [certificatesNeedAuth](../-unsigned-document/certificates-need-auth.md) | [androidJvm]<br>val [certificatesNeedAuth](../-unsigned-document/certificates-need-auth.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[X509Certificate](https://developer.android.com/reference/kotlin/java/security/cert/X509Certificate.html)&gt;<br>list of certificates that will be used for issuing the document |
| [createdAt](../-document/created-at.md)                                 | [androidJvm]<br>open override val [createdAt](../-document/created-at.md): [Instant](https://developer.android.com/reference/kotlin/java/time/Instant.html)<br>the creation date of the document                                                                                                                                                                          |
| [docType](../-document/doc-type.md)                                     | [androidJvm]<br>open override val [docType](../-document/doc-type.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the document type                                                                                                                                                                                              |
| [id](../-document/id.md)                                                | [androidJvm]<br>open override val [id](../-document/id.md): [DocumentId](../index.md#659369697%2FClasslikes%2F1351694608)<br>the identifier of the document                                                                                                                                                                                                               |
| [isDeferred](../-document/is-deferred.md)                               | [androidJvm]<br>open override val [isDeferred](../-document/is-deferred.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document is deferred                                                                                                                                                                       |
| [isIssued](../-document/is-issued.md)                                   | [androidJvm]<br>open override val [isIssued](../-document/is-issued.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document is issued                                                                                                                                                                             |
| [isUnsigned](../-document/is-unsigned.md)                               | [androidJvm]<br>open override val [isUnsigned](../-document/is-unsigned.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document is unsigned                                                                                                                                                                       |
| [name](../-unsigned-document/name.md)                                   | [androidJvm]<br>open override var [name](../-unsigned-document/name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>the name of the document. This name can be updated before the document is issued                                                                                                                             |
| [publicKey](../-unsigned-document/public-key.md)                        | [androidJvm]<br>val [publicKey](../-unsigned-document/public-key.md): [PublicKey](https://developer.android.com/reference/kotlin/java/security/PublicKey.html)<br>public key of the first certificate in [certificatesNeedAuth](../-unsigned-document/certificates-need-auth.md) list to be included in mobile security object that it will be signed from issuer         |
| [relatedData](related-data.md)                                          | [androidJvm]<br>val [relatedData](related-data.md): [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html)<br>the related data                                                                                                                                                                                                           |
| [requiresUserAuth](../-document/requires-user-auth.md)                  | [androidJvm]<br>open override val [requiresUserAuth](../-document/requires-user-auth.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document requires user authentication                                                                                                                                         |
| [state](../-document/state.md)                                          | [androidJvm]<br>open override val [state](../-document/state.md): [Document.State](../-document/-state/index.md)<br>the state of the document                                                                                                                                                                                                                             |
| [usesStrongBox](../-document/uses-strong-box.md)                        | [androidJvm]<br>open override val [usesStrongBox](../-document/uses-strong-box.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>whether the document's keys are in strongBox                                                                                                                                                    |