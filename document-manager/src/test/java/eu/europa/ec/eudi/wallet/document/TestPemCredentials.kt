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
package eu.europa.ec.eudi.wallet.document

import org.bouncycastle.asn1.nist.NISTNamedCurves
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECNamedCurveSpec
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemWriter
import java.io.StringWriter
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.Security
import java.util.*

// Lazy default credentials for tests
private val defaultTestCredentials by lazy { generateTestPemCredentials() }

val TEST_ISSUER_PRIVATE_KEY_PEM: String get() = defaultTestCredentials.first
val TEST_ISSUER_CERTIFICATE_PEM: String get() = defaultTestCredentials.second

/**
 * Generates a test EC key pair and self-signed certificate in PEM format for testing.
 * Returns a Pair of (privateKeyPem, certificatePem)
 */
private fun generateTestPemCredentials(): Pair<String, String> {
    Security.addProvider(BouncyCastleProvider())

    // Use NIST P-256 curve from BouncyCastle's NISTNamedCurves
    val curveParams = NISTNamedCurves.getByName("P-256")
    val ecSpec = ECNamedCurveSpec(
        "P-256",
        curveParams.curve,
        curveParams.g,
        curveParams.n,
        curveParams.h
    )

    // Generate EC key pair using BouncyCastle provider
    val keyGen = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME)
    keyGen.initialize(ecSpec)
    val keyPair = keyGen.generateKeyPair()

    // Generate self-signed certificate
    val subject = X500Name("CN=Test Issuer, O=Test Organization, C=EU")
    val serial = BigInteger.valueOf(System.currentTimeMillis())
    val notBefore = Date()
    val notAfter = Date(System.currentTimeMillis() + 365L * 86400000L)

    val certBuilder = JcaX509v3CertificateBuilder(
        subject,
        serial,
        notBefore,
        notAfter,
        subject,
        keyPair.public
    )

    // Use standard signature algorithm name
    val signer = JcaContentSignerBuilder("SHA256withECDSA")
        .setProvider(BouncyCastleProvider.PROVIDER_NAME)
        .build(keyPair.private)

    val certificate = JcaX509CertificateConverter()
        .setProvider(BouncyCastleProvider.PROVIDER_NAME)
        .getCertificate(certBuilder.build(signer))

    // Convert to PEM strings
    val privateKeyPem = StringWriter().use { writer ->
        PemWriter(writer).use { it.writeObject(PemObject("PRIVATE KEY", keyPair.private.encoded)) }
        writer.toString()
    }

    val certificatePem = StringWriter().use { writer ->
        PemWriter(writer).use { it.writeObject(PemObject("CERTIFICATE", certificate.encoded)) }
        writer.toString()
    }

    return Pair(privateKeyPem, certificatePem)
}

