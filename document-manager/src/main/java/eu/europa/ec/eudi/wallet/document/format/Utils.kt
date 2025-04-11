/*
 * Copyright (c) 2025 European Commission
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.europa.ec.eudi.wallet.document.format

import eu.europa.ec.eudi.wallet.document.IssuedDocument

typealias ClaimPath = List<String>

/**
 * Retrieves all claim paths starting from a specified root path in the given `IssuedDocument`.
 * 
 * For SdJwtVcFormat: Returns all nested paths under the specified rootPath.
 * For MsoMdocFormat: If rootPath contains only one element (namespace), returns all claims in that namespace.
 *
 * @receiver IssuedDocument The document from which to extract claim paths.
 * @param rootPath The root path from which to start extracting claim paths. 
 *                 For MsoMdocFormat, this should be a single element containing the namespace.
 * @return A `Result` containing a list of all claim paths starting from the root path.
 *         For MsoMdocFormat, returns paths of form [namespace, identifier].
 */
fun IssuedDocument.getAllClaimPathsFrom(rootPath: ClaimPath): Result<List<ClaimPath>> {
    return runCatching {
        when (format) {
            is SdJwtVcFormat -> {
                val claims = data.claims.filterIsInstance<SdJwtVcClaim>()
                getAllClaimPathsFrom(claims, rootPath)
            }

            is MsoMdocFormat -> {
                require(rootPath.size == 1) {
                    "MsoMdocFormat requires a path of size 1 with the namespace)"
                }
                val claims = data.claims.filterIsInstance<MsoMdocClaim>()
                claims.filter { it.nameSpace == rootPath[0] }
                    .map { listOf(it.nameSpace, it.identifier) }
            }
        }
    }
}

/**
 * Finds a specific claim at the given path in the `IssuedDocument`.
 *
 * For SdJwtVcFormat: Path represents nested object structure with each element being a key.
 * For MsoMdocFormat: Path must be exactly 2 elements - [namespace, identifier].
 *
 * @receiver IssuedDocument The document from which to find the claim.
 * @param path The path to the claim. For MsoMdocFormat, must be [namespace, identifier].
 * @return A `Result` containing the claim at the specified path, or `null` if not found.
 */
fun IssuedDocument.findClaimAtPath(path: ClaimPath): Result<DocumentClaim?> {
    return runCatching {
        when (format) {
            is SdJwtVcFormat -> {
                val claims = data.claims.filterIsInstance<SdJwtVcClaim>()
                findClaimAtPath(claims, path)
            }

            is MsoMdocFormat -> {
                require(path.size == 2) {
                    "MsoMdocFormat requires a path of size 2 (namespace, identifier)"
                }
                data.claims.filterIsInstance<MsoMdocClaim>()
                    .find {
                        it.nameSpace == path[0] && it.identifier == path[1]
                    }
            }
        }

    }
}

/**
 * Returns all child claim paths from a given root path in a list of SdJwtVcClaims.
 * This function is specific to SdJwtVcClaim type.
 *
 * @param claims The list of SdJwtVcClaim objects to search through
 * @param rootPath The path to the root claim from which to find children (empty list for root)
 * @return List of paths (where each path is a list of identifiers) to all child claims, excluding the rootPath itself
 */
fun getAllClaimPathsFrom(
    claims: List<SdJwtVcClaim>,
    rootPath: ClaimPath
): List<ClaimPath> {
    // If the path is empty, return all paths from the root
    if (rootPath.isEmpty()) {
        return collectAllPaths(claims, emptyList())
    }

    // Find the claim at the rootPath
    val rootClaim = findClaimAtPath(claims, rootPath)

    // If the root claim is not found, return an empty list
    return rootClaim?.let {
        // Get all paths including the rootPath
        val allPaths = collectAllPaths(listOf(it), rootPath.dropLast(1))
        // Filter out the rootPath itself
        allPaths.filterNot { path -> path == rootPath }
    } ?: emptyList()
}

/**
 * Recursively collects all paths from the given SdJwtVcClaims.
 * This function is specific to SdJwtVcClaim type.
 *
 * @param claims The SdJwtVcClaim objects to process
 * @param basePath The base path to prefix to each claim
 * @return List of all paths
 */
fun collectAllPaths(
    claims: List<SdJwtVcClaim>,
    basePath: ClaimPath
): List<ClaimPath> {
    val result = mutableListOf<List<String>>()

    for (claim in claims) {
        val currentPath = basePath + claim.identifier
        result.add(currentPath)

        if (claim.children.isNotEmpty()) {
            result.addAll(collectAllPaths(claim.children, currentPath))
        }
    }

    return result
}

/**
 * Finds the SdJwtVcClaim at the specified path.
 * This function is specific to SdJwtVcClaim type.
 *
 * @param claims The list of SdJwtVcClaim objects to search through
 * @param path The path to the claim
 * @return The SdJwtVcClaim at the path, or null if not found
 */
fun findClaimAtPath(claims: List<SdJwtVcClaim>, path: ClaimPath): SdJwtVcClaim? {
    if (path.isEmpty()) return null

    var currentClaims = claims
    var targetClaim: SdJwtVcClaim? = null

    for (i in path.indices) {
        val segment = path[i]
        targetClaim = currentClaims.find { it.identifier == segment }

        if (targetClaim == null) {
            return null
        }

        if (i < path.size - 1) {
            currentClaims = targetClaim.children
        }
    }

    return targetClaim
}
