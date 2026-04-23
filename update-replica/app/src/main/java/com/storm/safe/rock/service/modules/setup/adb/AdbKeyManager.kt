package com.storm.safe.rock.service.modules.setup.adb

import android.content.Context
import android.util.Base64
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Date
import java.util.Random

/**
 * ADB RSA key pair and TLS certificate management.
 * Handles key generation, persistence, and loading.
 * SSLContext/TLS/PeerInfo/AUTH are delegated to libadb-android.
 */
class AdbKeyManager(private val context: Context) {

    companion object {
        private const val TAG = "AdbKeyManager"
    }

    var tlsKeyPair: KeyPair? = null
    var tlsCertificate: X509Certificate? = null

    fun getKeyDir(): File? = context.getExternalFilesDir(null)

    @Suppress("DEPRECATION")
    fun generateCert(keyPair: KeyPair): X509Certificate {
        val now = Date()
        val tenYears = Date(now.time + 315360000000L)
        val subject = "CN=${context.packageName}"
        val serialNumber = BigInteger.valueOf((Random().nextInt().toLong() and 0x7FFFFFFFL))
        val certGen = org.bouncycastle.x509.X509V3CertificateGenerator()
        val x500Principal = javax.security.auth.x500.X500Principal(subject)
        certGen.setSerialNumber(serialNumber)
        certGen.setIssuerDN(x500Principal)
        certGen.setNotBefore(now)
        certGen.setNotAfter(tenYears)
        certGen.setSubjectDN(x500Principal)
        certGen.setPublicKey(keyPair.public)
        certGen.setSignatureAlgorithm("SHA256withRSA")
        val pubKeyDigest = MessageDigest.getInstance("SHA-1").digest(keyPair.public.encoded)
        certGen.addExtension(
            org.bouncycastle.asn1.x509.X509Extensions.SubjectKeyIdentifier,
            false, org.bouncycastle.asn1.DEROctetString(pubKeyDigest)
        )
        return certGen.generate(keyPair.private)
    }

    fun loadCert(file: File): X509Certificate? {
        return try {
            if (!file.exists()) return null
            CertificateFactory.getInstance("X.509").generateCertificate(FileInputStream(file)) as X509Certificate
        } catch (e: Exception) { Log.e(TAG, "加载证书失败", e); null }
    }

    fun loadPrivateKey(file: File): PrivateKey? {
        return try {
            if (!file.exists()) return null
            KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(file.readBytes()))
        } catch (e: Exception) { Log.e(TAG, "加载私钥失败", e); null }
    }

    fun saveCert(cert: X509Certificate) {
        try {
            val keyDir = getKeyDir() ?: return
            keyDir.mkdirs()
            FileOutputStream(File(keyDir, "cert.pem")).use { fos ->
                fos.write("-----BEGIN CERTIFICATE-----\n".toByteArray(Charsets.UTF_8))
                fos.write(Base64.encodeToString(cert.encoded, Base64.DEFAULT).toByteArray(Charsets.UTF_8))
                fos.write("-----END CERTIFICATE-----\n".toByteArray(Charsets.UTF_8))
            }
        } catch (e: Exception) { Log.e(TAG, "保存证书失败", e) }
    }

    fun savePrivateKey(privateKey: PrivateKey) {
        try {
            val keyDir = getKeyDir() ?: return
            keyDir.mkdirs()
            FileOutputStream(File(keyDir, "private.key")).use { it.write(privateKey.encoded) }
        } catch (e: Exception) { Log.e(TAG, "保存私钥失败", e) }
    }

    fun generateOrLoadKeyPair() {
        if (tlsKeyPair != null && tlsCertificate != null) return
        val keyDir = getKeyDir()
        if (keyDir != null) {
            val loadedKey = loadPrivateKey(File(keyDir, "private.key"))
            val loadedCert = loadCert(File(keyDir, "cert.pem"))
            if (loadedKey != null && loadedCert != null) {
                tlsKeyPair = KeyPair(loadedCert.publicKey, loadedKey)
                tlsCertificate = loadedCert
                Log.d(TAG, "已从本地文件加载 TLS 密钥对"); return
            }
        }
        Log.d(TAG, "生成新密钥对")
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        val newKeyPair = keyGen.generateKeyPair()
        val newCert = generateCert(newKeyPair)
        tlsKeyPair = newKeyPair
        tlsCertificate = newCert
        savePrivateKey(newKeyPair.private)
        saveCert(newCert)
    }
}
