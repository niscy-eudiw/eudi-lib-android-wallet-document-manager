//[document-manager](../../../index.md)/[eu.europa.ec.eudi.wallet.document](../index.md)/[CreateIssuanceRequestResult](index.md)

# CreateIssuanceRequestResult

interface [CreateIssuanceRequestResult](index.md)

Create issuance request result sealed interface

#### Inheritors

| |
|---|
| [Success](-success/index.md) |
| [Failure](-failure/index.md) |

## Types

| Name | Summary |
|---|---|
| [Failure](-failure/index.md) | [androidJvm]<br>data class [Failure](-failure/index.md)(val throwable: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) : [CreateIssuanceRequestResult](index.md)<br>Failure while creating the issuance request. Contains the throwable that caused the failure |
| [Success](-success/index.md) | [androidJvm]<br>data class [Success](-success/index.md)(val issuanceRequest: [IssuanceRequest](../-issuance-request/index.md)) : [CreateIssuanceRequestResult](index.md)<br>Success result containing the issuance request. The issuance request can be then used to issue the document from the issuer. The issuance request contains the certificate chain that must be sent to the issuer. |