//[document-manager](../../index.md)/[eu.europa.ec.eudi.wallet.document.format](index.md)/[findClaimAtPath](find-claim-at-path.md)

# findClaimAtPath

[androidJvm]\
fun [IssuedDocument](../eu.europa.ec.eudi.wallet.document/-issued-document/index.md).[findClaimAtPath](find-claim-at-path.md)(path: [ClaimPath](-claim-path/index.md)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[DocumentClaim](-document-claim/index.md)?&gt;

Finds a specific claim at the given path in the `IssuedDocument`.

For SdJwtVcFormat: Path represents nested object structure with each element being a key. For MsoMdocFormat: Path must be exactly 2 elements - namespace, identifier.

#### Receiver

IssuedDocument The document from which to find the claim.

#### Return

A `Result` containing the claim at the specified path, or `null` if not found.

#### Parameters

androidJvm

| | |
|---|---|
| path | The path to the claim. For MsoMdocFormat, must be namespace, identifier. |

[androidJvm]\
fun [findClaimAtPath](find-claim-at-path.md)(claims: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[SdJwtVcClaim](-sd-jwt-vc-claim/index.md)&gt;, path: [ClaimPath](-claim-path/index.md)): [SdJwtVcClaim](-sd-jwt-vc-claim/index.md)?

Finds the SdJwtVcClaim at the specified path. This function is specific to SdJwtVcClaim type.

#### Return

The SdJwtVcClaim at the path, or null if not found

#### Parameters

androidJvm

| | |
|---|---|
| claims | The list of SdJwtVcClaim objects to search through |
| path | The path to the claim |
