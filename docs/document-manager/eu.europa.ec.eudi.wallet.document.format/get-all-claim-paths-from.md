//[document-manager](../../index.md)/[eu.europa.ec.eudi.wallet.document.format](index.md)/[getAllClaimPathsFrom](get-all-claim-paths-from.md)

# getAllClaimPathsFrom

[androidJvm]\
fun [IssuedDocument](../eu.europa.ec.eudi.wallet.document/-issued-document/index.md).[getAllClaimPathsFrom](get-all-claim-paths-from.md)(rootPath: [ClaimPath](-claim-path/index.md)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ClaimPath](-claim-path/index.md)&gt;&gt;

Retrieves all claim paths starting from a specified root path in the given `IssuedDocument`.

For SdJwtVcFormat: Returns all nested paths under the specified rootPath. For MsoMdocFormat: If rootPath contains only one element (namespace), returns all claims in that namespace.

#### Receiver

IssuedDocument The document from which to extract claim paths.

#### Return

A `Result` containing a list of all claim paths starting from the root path.     For MsoMdocFormat, returns paths of form namespace, identifier.

#### Parameters

androidJvm

| | |
|---|---|
| rootPath | The root path from which to start extracting claim paths.     For MsoMdocFormat, this should be a single element containing the namespace. |

[androidJvm]\
fun [getAllClaimPathsFrom](get-all-claim-paths-from.md)(claims: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[SdJwtVcClaim](-sd-jwt-vc-claim/index.md)&gt;, rootPath: [ClaimPath](-claim-path/index.md)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[ClaimPath](-claim-path/index.md)&gt;

Returns all child claim paths from a given root path in a list of SdJwtVcClaims. This function is specific to SdJwtVcClaim type.

#### Return

List of paths (where each path is a list of identifiers) to all child claims, excluding the rootPath itself

#### Parameters

androidJvm

| | |
|---|---|
| claims | The list of SdJwtVcClaim objects to search through |
| rootPath | The path to the root claim from which to find children (empty list for root) |
