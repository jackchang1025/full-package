package com.storm.safe.rock.service.modules.setup.adb

import android.content.Context
import android.util.Log
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.readPairingPacket
import com.storm.safe.rock.service.modules.setup.adb.AdbProtocol.writePairingPacket
import io.github.muntashirakon.crypto.spake2.Spake2Context
import java.io.DataInputStream
import java.io.DataOutputStream
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.SSLSocket

/**
 * ADB SPAKE2+TLS pairing client.
 *
 * JADX: C0360a2 methods: doPair (line 2381-2478), h5 (deriveKeys),
 *       c3 (encryptPairingMessage), c2 (decryptPairingMessage)
 *
 * Performs the full ADB wireless pairing protocol:
 * 1. TLS 1.3 connection to pairing port
 * 2. Export keying material from TLS session
 * 3. SPAKE2 key exchange using password = pairingCode || keyingMaterial
 * 4. HKDF-SHA256 key derivation
 * 5. AES-128-GCM encrypted PeerInfo exchange
 */
class AdbPairingClient(
    private val context: Context,
    private val keyManager: AdbKeyManager
) {
    companion object {
        private const val TAG = "AdbPairingClient"
    }

    // ========================================================================
    // doPair -- vendor C0360a2 lines 2381-2478
    // ========================================================================

    /**
     * Perform SPAKE2+TLS pairing with ADB daemon.
     * vendor: doPair (line 2381)
     *
     * @param port pairing port (typically 30000-49999)
     * @param pairingCode 6-digit code shown in wireless debugging UI
     * @return true if pairing succeeded
     */
    fun doPair(port: Int, pairingCode: String): Boolean {
        Log.i(TAG, "开始 SPAKE2+TLS 配对: 127.0.0.1:$port")
        var rawSocket: java.net.Socket? = null
        var spake2Ctx: Spake2Context? = null
        return try {
            keyManager.generateOrLoadKeyPair()
            val keyDir = keyManager.getKeyDir() ?: run {
                Log.e(TAG, "SPAKE2 配对: 密钥目录不存在"); return false
            }
            val sslContext = keyManager.createSslContext(
                java.io.File(keyDir, "cert.pem"),
                java.io.File(keyDir, "private.key")
            ) ?: run { Log.e(TAG, "SPAKE2 配对: SSLContext 创建失败"); return false }

            // Step 1: TLS 1.3 连接 (vendor C0360a2.java:2746-2752)
            rawSocket = java.net.Socket("127.0.0.1", port)
            rawSocket.tcpNoDelay = true
            val sslSocket = sslContext.socketFactory.createSocket(
                rawSocket, "127.0.0.1", port, true
            ) as SSLSocket
            sslSocket.enabledProtocols = arrayOf("TLSv1.3")
            sslSocket.startHandshake()
            Log.i(TAG, "TLS 握手成功")

            val dis = DataInputStream(sslSocket.inputStream)
            val dos = DataOutputStream(sslSocket.outputStream)

            // Step 2: 导出 TLS 密钥材料 (vendor L2756)
            val keyingMaterial = keyManager.exportKeyingMaterial(sslSocket)
            if (keyingMaterial == null) {
                Log.e(TAG, "导出密钥材料失败"); rawSocket.close(); return false
            }

            // Step 3: 构造 SPAKE2 密码 = pairCode_UTF8 || TLS_keying_material (vendor L2763-2767)
            val codeBytes = pairingCode.toByteArray(Charsets.UTF_8)
            val password = ByteArray(codeBytes.size + keyingMaterial.size)
            System.arraycopy(codeBytes, 0, password, 0, codeBytes.size)
            System.arraycopy(keyingMaterial, 0, password, codeBytes.size, keyingMaterial.size)

            // Step 4: SPAKE2 密钥交换 (vendor L2768-2783)
            val clientId = "adb pair client\u0000".toByteArray(Charsets.UTF_8)
            val serverId = "adb pair server\u0000".toByteArray(Charsets.UTF_8)
            spake2Ctx = Spake2Context(clientId, serverId)
            val ctx = spake2Ctx!!
            Log.d(TAG, ">>> 生成 SPAKE2 消息...")
            val outMsg = ctx.m213179a0(password)  // generateMessage
            Log.d(TAG, ">>> SPAKE2 消息生成成功, 长度=${outMsg.size}")
            writePairingPacket(dos, 0, outMsg)  // TYPE_SPAKE2 = 0
            Log.d(TAG, ">>> SPAKE2 消息已发送")

            // 接收服务端 SPAKE2 消息
            val serverHeader = readPairingPacket(dis)
            if (serverHeader == null || serverHeader.type.toInt() != 0) {
                Log.e(TAG, "收到无效的 SPAKE2 响应"); ctx.destroy(); rawSocket.close(); return false
            }
            val serverMsg = ByteArray(serverHeader.payloadSize)
            dis.readFully(serverMsg)
            val sharedSecret = ctx.m213180a5(serverMsg)  // processMessage
            Log.i(TAG, "SPAKE2 密钥交换成功")

            // Step 5: HKDF 密钥派生 (vendor L2784-2786)
            val label = "adb pairing_auth aes-128-gcm key".toByteArray(Charsets.UTF_8)
            val aesKey = deriveKeys(sharedSecret, label)

            // Step 6: PeerInfo 交换 (vendor L2787-2809)
            val encryptedPeerInfo = encryptPairingMessage(aesKey, keyManager.createPeerInfo())
            if (encryptedPeerInfo == null) {
                Log.e(TAG, "加密 PeerInfo 失败"); ctx.destroy(); rawSocket.close(); return false
            }
            writePairingPacket(dos, 1, encryptedPeerInfo)  // TYPE_PEER_INFO = 1
            Log.i(TAG, "发送加密 PeerInfo")

            // 接收并解密服务端 PeerInfo
            val serverPeerHeader = readPairingPacket(dis)
            if (serverPeerHeader == null || serverPeerHeader.type.toInt() != 1) {
                Log.e(TAG, "收到无效的 PeerInfo 响应"); ctx.destroy(); rawSocket.close(); return false
            }
            val encServerPeer = ByteArray(serverPeerHeader.payloadSize)
            dis.readFully(encServerPeer)
            if (decryptPairingMessage(aesKey, encServerPeer) == null) {
                Log.e(TAG, "解密服务器 PeerInfo 失败"); ctx.destroy(); rawSocket.close(); return false
            }
            Log.i(TAG, "配对完成，收到服务器 PeerInfo")
            ctx.destroy()
            rawSocket.close()
            true
        } catch (e: Exception) {
            Log.e(TAG, "SPAKE2+TLS 配对异常", e)
            spake2Ctx?.destroy()
            rawSocket?.close()
            false
        }
    }

    // ========================================================================
    // Crypto helpers -- vendor h5, c3, c2
    // ========================================================================

    /**
     * HKDF-SHA256 key derivation (simplified: extract+expand 1 round, output 16 bytes).
     * vendor: h5 (line 1412)
     */
    fun deriveKeys(secret: ByteArray, info: ByteArray): ByteArray {
        return try {
            val mac = Mac.getInstance("HmacSHA256")
            // Extract: PRK = HMAC(salt=zeros, IKM=secret)
            mac.init(SecretKeySpec(ByteArray(32), "HmacSHA256"))
            val prk = mac.doFinal(secret)

            // Expand: OKM = HMAC(PRK, info || 0x01)
            mac.init(SecretKeySpec(prk, "HmacSHA256"))
            mac.update(info)
            mac.update(1.toByte())
            val okm = mac.doFinal()

            okm.copyOf(16)  // AES-128 key = first 16 bytes
        } catch (e: Exception) {
            Log.e(TAG, "HKDF 派生失败", e)
            ByteArray(16)
        }
    }

    /**
     * AES-128-GCM encrypt with zero IV.
     * vendor: c3 (line 862)
     */
    fun encryptPairingMessage(key: ByteArray, plaintext: ByteArray): ByteArray? {
        return try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val iv = ByteArray(12)  // zero IV (vendor: ByteBuffer.allocate(12).putLong(0L))
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, iv))
            cipher.doFinal(plaintext)
        } catch (e: Exception) {
            Log.e(TAG, "AES-GCM 加密失败", e)
            null
        }
    }

    /**
     * AES-128-GCM decrypt with zero IV.
     * vendor: c2 (line 850)
     */
    fun decryptPairingMessage(key: ByteArray, ciphertext: ByteArray): ByteArray? {
        return try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val iv = ByteArray(12)  // zero IV
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, iv))
            cipher.doFinal(ciphertext)
        } catch (e: Exception) {
            Log.e(TAG, "AES-GCM 解密失败", e)
            null
        }
    }
}
