/*
 * Copyright (c) 2023-2025 European Commission
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
package eu.europa.ec.eudi.wallet.document.internal

import COSE.AlgorithmID.ECDSA_256
import COSE.HeaderKeys.Algorithm
import COSE.OneKey
import COSE.Sign1Message
import com.upokecenter.cbor.CBORObject
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.io.pem.PemReader
import org.multipaz.crypto.EcPublicKey
import org.multipaz.mdoc.mso.MobileSecurityObjectGenerator
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec
import kotlin.time.Clock
import kotlin.time.Instant

private val bc = BouncyCastleProvider()

@JvmSynthetic
internal fun parsePrivateKeyFromPem(pemPrivateKey: String): PrivateKey =
    PemReader(pemPrivateKey.reader())
        .use { reader -> reader.readPemObject().content }
        .let { privateKeyBytes ->
            KeyFactory.getInstance("EC", bc)
                .generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes))
        }

@JvmSynthetic
internal fun parseCertificateFromPem(pemCertificate: String): X509Certificate =
    PemReader(pemCertificate.reader())
        .use { reader -> reader.readPemObject().content }
        .let { certificateBytes ->
            CertificateFactory.getInstance("X.509", bc)
                .generateCertificate(certificateBytes.inputStream())
        } as X509Certificate

@get:JvmSynthetic
internal val PrivateKey.oneKey
    get() = OneKey(null, this)

@JvmSynthetic
internal fun generateMso(
    digestAlg: String,
    docType: String,
    authKey: EcPublicKey,
    nameSpaces: CBORObject,
) =
    MobileSecurityObjectGenerator(org.multipaz.crypto.Algorithm.fromHashAlgorithmIdentifier(digestAlg), docType, authKey)
        .apply {
            val now = Clock.System.now()
            val validUntil =
                Instant.fromEpochMilliseconds(now.toEpochMilliseconds() + 1000L * 60L * 60L * 24L * 365L)
            setValidityInfo(now, now, validUntil, null)

            val digestIds = nameSpaces.entries.associate { (nameSpace, issuerSignedItems) ->
                nameSpace.AsString() to calculateDigests(digestAlg, issuerSignedItems)
            }
            digestIds.forEach { (nameSpace, digestIds) ->
                addDigestIdsForNamespace(nameSpace, digestIds)
            }
        }
        .generate()

@JvmSynthetic
internal fun calculateDigests(
    digestAlg: String,
    issuerSignedItems: CBORObject
): Map<Long, ByteArray> {
    return issuerSignedItems.values.associate { issuerSignedItemBytes ->
        val issuerSignedItem = issuerSignedItemBytes.getEmbeddedCBORObject()
        val digest = MessageDigest.getInstance(digestAlg)
            .digest(issuerSignedItemBytes.EncodeToBytes())
        issuerSignedItem["digestID"].AsInt32().toLong() to digest
    }
}

@JvmSynthetic
internal fun signMso(
    mso: ByteArray,
    issuerPrivateKey: PrivateKey,
    issuerCertificate: X509Certificate
) = Sign1Message(false, true).apply {
    protectedAttributes.Add(Algorithm.AsCBOR(), ECDSA_256.AsCBOR())
    unprotectedAttributes.Add(33L, issuerCertificate.encoded)
    SetContent(mso.withTag24())
    sign(issuerPrivateKey.oneKey)
}.EncodeToCBORObject()

@JvmSynthetic
internal fun generateData(
    issuerNameSpaces: CBORObject,
    issuerAuth: CBORObject,
): ByteArray {
    return mapOf(
        "nameSpaces" to issuerNameSpaces,
        "issuerAuth" to issuerAuth,
    ).let { CBORObject.FromObject(it).EncodeToBytes() }
}
