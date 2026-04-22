package com.storm.safe.rock.service.modules.setup.adb

import android.content.Context
import android.os.Build
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
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Date
import java.util.Random
import javax.crypto.Cipher
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket

/**
 * ADB RSA key pair and TLS certificate management.
 * JADX: C0360a2 methods d5, g3, h9, i0, j1, j2, b1, b6, d4, g8, f0
 * Companion fields: f53812g1 (cachedSslContext), f53813g2 (cachedPrivateKey), f53814g3 (cachedCertificate)
 */
class AdbKeyManager(private val context: Context) {

    companion object {
        private const val TAG = "AdbKeyManager"

        /** vendor f53812g1 */
        @Volatile @JvmStatic var cachedSslContext: SSLContext? = null
            private set

        /** vendor f53813g2 */
        @Volatile @JvmStatic var cachedPrivateKey: PrivateKey? = null
            private set

        /** vendor f53814g3 */
        @Volatile @JvmStatic var cachedCertificate: X509Certificate? = null
            private set

        @JvmStatic fun clearKeyCache() { cachedPrivateKey = null; cachedCertificate = null }
        @JvmStatic fun clearSslCache() { cachedSslContext = null }
        @JvmStatic fun clearAllCaches() { cachedSslContext = null; cachedPrivateKey = null; cachedCertificate = null }
    }

    /** vendor f53843c8 */
    var tlsKeyPair: KeyPair? = null
    /** vendor f53844c9 */
    var tlsCertificate: X509Certificate? = null

    /** Get key storage directory. vendor: g8 (line 3079) */
    fun getKeyDir(): File? = context.getExternalFilesDir(null)

    /** Generate X.509 self-signed certificate. vendor: g3 (line 3039) */
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

    /** Load certificate from PEM file. vendor: h9 */
    fun loadCert(file: File): X509Certificate? {
        return try {
            if (!file.exists()) return null
            val cert = CertificateFactory.getInstance("X.509").generateCertificate(FileInputStream(file))
            Log.d(TAG, "从本地加载证书成功")
            cert as X509Certificate
        } catch (e: Exception) { Log.e(TAG, "加载证书失败", e); null }
    }

    /** Load private key from PKCS8 DER file. vendor: i0 */
    fun loadPrivateKey(file: File): PrivateKey? {
        return try {
            if (!file.exists()) return null
            val key = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(file.readBytes()))
            Log.d(TAG, "从本地加载私钥成功")
            key
        } catch (e: Exception) { Log.e(TAG, "加载私钥失败", e); null }
    }

    /** Save certificate in PEM format. vendor: j1 (line 4759) */
    fun saveCert(cert: X509Certificate) {
        try {
            val keyDir = getKeyDir() ?: return
            keyDir.mkdirs()
            val file = File(keyDir, "cert.pem")
            FileOutputStream(file).use { fos ->
                fos.write("-----BEGIN CERTIFICATE-----\n".toByteArray(Charsets.UTF_8))
                fos.write(Base64.encodeToString(cert.encoded, Base64.DEFAULT).toByteArray(Charsets.UTF_8))
                fos.write("-----END CERTIFICATE-----\n".toByteArray(Charsets.UTF_8))
            }
            Log.d(TAG, "证书已保存到: ${file.absolutePath}")
        } catch (e: Exception) { Log.e(TAG, "保存证书失败", e) }
    }

    /** Save private key in PKCS8 DER format. vendor: j2 (line 4792) */
    fun savePrivateKey(privateKey: PrivateKey) {
        try {
            val keyDir = getKeyDir() ?: return
            keyDir.mkdirs()
            val file = File(keyDir, "private.key")
            FileOutputStream(file).use { fos -> fos.write(privateKey.encoded) }
            Log.d(TAG, "私钥已保存到: ${file.absolutePath}")
        } catch (e: Exception) { Log.e(TAG, "保存私钥失败", e) }
    }

    /** Generate or load RSA 2048 key pair + certificate. vendor: d5 (line 2286) */
    fun generateOrLoadKeyPair() {
        if (tlsKeyPair != null && tlsCertificate != null) {
            Log.d(TAG, "复用已有密钥对进行配对"); return
        }
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
        Log.d(TAG, "生成新密钥对进行配对")
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048)
        val newKeyPair = keyGen.generateKeyPair()
        val newCert = generateCert(newKeyPair)
        tlsKeyPair = newKeyPair
        tlsCertificate = newCert
        savePrivateKey(newKeyPair.private)
        saveCert(newCert)
        clearSslCache()
        cachedPrivateKey = newKeyPair.private
        cachedCertificate = newCert
    }

    /** Create SSLContext with client certificate for ADB TLS. vendor: b1 (line 600) */
    fun createSslContext(certFile: File, keyFile: File): SSLContext? {
        cachedSslContext?.let { return it }
        return try {
            val cert = loadCert(certFile)
            val key = loadPrivateKey(keyFile)
            if (cert == null || key == null) return null
            Log.i(TAG, "私钥加载成功: ${key.algorithm}, 证书: ${cert.subjectDN}")
            val keyPair = KeyPair(cert.publicKey, key)
            val sslContext = try {
                Class.forName("org.conscrypt.Conscrypt")
                SSLContext.getInstance("TLSv1.3", org.conscrypt.Conscrypt.newProvider())
            } catch (_: ClassNotFoundException) {
                SSLContext.getInstance("TLSv1.3")
            }
            sslContext.init(arrayOf(SimpleKeyManager(cert, keyPair)), arrayOf(TrustAllManager()), SecureRandom())
            cachedSslContext = sslContext
            Log.d(TAG, "SSLContext 创建并缓存成功")
            sslContext
        } catch (e: Exception) { Log.e(TAG, "创建 ADB TLS Context 失败", e); null }
    }

    /** Sign ADB authentication token. vendor: b6 (line 827). RSA/ECB/NoPadding + PKCS1 v1.5 prefix. */
    fun signAdbToken(token: ByteArray, keyFile: File): ByteArray? {
        return try {
            val privateKey = loadPrivateKey(keyFile) ?: return null
            val cipher = Cipher.getInstance("RSA/ECB/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, privateKey)
            val digestInfo = byteArrayOf(
                0x00, 0x01, *ByteArray(216) { 0xFF.toByte() },
                0x00, 0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2b,
                0x0e, 0x03, 0x02, 0x1a, 0x05, 0x00, 0x04, 0x14
            )
            cipher.update(digestInfo)
            cipher.doFinal(token)
        } catch (e: Exception) { Log.e(TAG, "signAdbToken 失败", e); null }
    }

    /** Create PeerInfo payload (8192 bytes) for ADB pairing. vendor: d4 (line 2243) */
    fun createPeerInfo(): ByteArray {
        val result = ByteArray(8192)
        result[0] = 0
        try {
            val keyPair = tlsKeyPair ?: throw IllegalStateException("tlsKeyPair 未初始化")
            val pubKey = keyPair.public as RSAPublicKey
            val rawKey = AdbProtocol.toAndroidRsaPublicKey(pubKey)
            val base64Key = Base64.encode(rawKey, Base64.DEFAULT)
            val suffix = " ${context.packageName}\u0000".toByteArray(Charsets.UTF_8)
            val peerInfoData = ByteArray(base64Key.size + suffix.size)
            System.arraycopy(base64Key, 0, peerInfoData, 0, base64Key.size)
            System.arraycopy(suffix, 0, peerInfoData, base64Key.size, suffix.size)
            System.arraycopy(peerInfoData, 0, result, 1, minOf(peerInfoData.size, 8191))
            val fp = MessageDigest.getInstance("SHA-256").digest(pubKey.encoded)
            Log.d(TAG, ">>> PeerInfo 公钥指纹: ${fp.joinToString(":") { String.format("%02X", it) }}")
        } catch (e: Exception) { Log.e(TAG, "生成 PeerInfo 失败", e) }
        return result
    }

    /**
     * Export TLS keying material via Conscrypt reflection.
     * vendor: f0 (line 1064) -- 3 fallback paths: org.conscrypt, system conscrypt, instance method.
     */
    fun exportKeyingMaterial(sslSocket: SSLSocket): ByteArray? {
        Log.d(TAG, ">>> 开始导出密钥材料, socket类型=${sslSocket.javaClass.name}")
        // Path 1: org.conscrypt.Conscrypt
        try {
            Class.forName("org.conscrypt.Conscrypt")
            val result = org.conscrypt.Conscrypt.exportKeyingMaterial(
                sslSocket as javax.net.ssl.SSLSocket, "adb-label\u0000", null, 64
            )
            if (result != null && result.size == 64) { Log.d(TAG, "org.conscrypt 导出成功"); return result }
        } catch (_: ClassNotFoundException) {
        } catch (e: Throwable) { Log.w(TAG, "org.conscrypt 异常: ${e.javaClass.name}: ${e.message}") }

        // Path 2: system Conscrypt reflection (Android 10+)
        if (Build.VERSION.SDK_INT >= 29) {
            try {
                val method = Class.forName("com.android.org.conscrypt.Conscrypt").getMethod(
                    "exportKeyingMaterial", SSLSocket::class.java, String::class.java, ByteArray::class.java, Integer.TYPE
                )
                val result = method.invoke(null, sslSocket, "adb-label\u0000", null, 64)
                if (result is ByteArray && result.size == 64) { Log.i(TAG, "系统 Conscrypt 导出成功"); return result }
            } catch (e: Throwable) { Log.w(TAG, "系统 Conscrypt 反射失败: ${e.message}") }
        }

        // Path 3: SSLSocket instance method
        try {
            val method = sslSocket.javaClass.getMethod("exportKeyingMaterial", String::class.java, ByteArray::class.java, Integer.TYPE)
            val result = method.invoke(sslSocket, "adb-label\u0000", null, 64)
            if (result is ByteArray && result.size == 64) { Log.i(TAG, "SSLSocket 实例导出成功"); return result }
        } catch (e: Throwable) { Log.w(TAG, "所有 exportKeyingMaterial 失败: ${e.message}") }

        Log.w(TAG, "exportKeyingMaterial 最终失败")
        return null
    }

    /** Minimal X509KeyManager for ADB client cert. vendor: f41 */
    private class SimpleKeyManager(
        private val cert: X509Certificate, private val keyPair: KeyPair
    ) : javax.net.ssl.X509KeyManager {
        override fun getClientAliases(keyType: String?, issuers: Array<out java.security.Principal>?) = arrayOf("adb-client")
        override fun chooseClientAlias(keyType: Array<out String>?, issuers: Array<out java.security.Principal>?, socket: java.net.Socket?) = "adb-client"
        override fun getServerAliases(keyType: String?, issuers: Array<out java.security.Principal>?) = null
        override fun chooseServerAlias(keyType: String?, issuers: Array<out java.security.Principal>?, socket: java.net.Socket?) = null
        override fun getCertificateChain(alias: String?) = arrayOf(cert)
        override fun getPrivateKey(alias: String?) = keyPair.private
    }

    /** TrustManager that trusts all peers (for ADB's self-signed cert). vendor: m41 */
    private class TrustAllManager : javax.net.ssl.X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }
}
