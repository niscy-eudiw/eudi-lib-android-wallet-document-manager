//[document-manager](../../index.md)/[eu.europa.ec.eudi.wallet.document.format](index.md)/[collectAllPaths](collect-all-paths.md)

# collectAllPaths

[androidJvm]\
fun [collectAllPaths](collect-all-paths.md)(claims: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[SdJwtVcClaim](-sd-jwt-vc-claim/index.md)&gt;, basePath: [ClaimPath](-claim-path/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ClaimPath](-claim-path/index.md)&gt;

Recursively collects all paths from the given SdJwtVcClaims. This function is specific to SdJwtVcClaim type.

#### Return

List of all paths

#### Parameters

androidJvm

| | |
|---|---|
| claims | The SdJwtVcClaim objects to process |
| basePath | The base path to prefix to each claim |
