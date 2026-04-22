package com.storm.safe.rock.service.modules.setup.adb

import android.util.Base64
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.InvalidKeyException
import java.security.interfaces.RSAPublicKey

/**
 * ADB wire protocol constants, packet I/O, and RSA key format utilities.
 *
 * JADX: C0360a2.java companion constants (f53861e6..f53871f6) +
 *       static methods: c7 (buildAdbPacket), b5 (readAdbPacket),
 *       i8 (readPairingPacket), j9 (writePairingPacket),
 *       e5 (toAndroidRsaPublicKey), c5 (reverseBytes), b2 (toPeerInfo)
 */
object AdbProtocol {

    private const val TAG = "AdbProtocol"

    // ========================================================================
    // ADB protocol constants -- vendor f53861e6..f53871f6
    // ========================================================================

    /** CNXN -- vendor f53861e6 = 0x4E584E43 */
    const val ADB_CMD_CNXN: Int = 0x4E584E43

    /** OPEN -- vendor f53862e7 = 0x4E45504F */
    const val ADB_CMD_OPEN: Int = 0x4E45504F

    /** WRTE -- vendor f53863e8 = 0x45545257 */
    const val ADB_CMD_WRTE: Int = 0x45545257

    /** CLSE -- vendor f53864e9 = 0x45534C43 */
    const val ADB_CMD_CLSE: Int = 0x45534C43

    /** OKAY -- vendor f53865f0 = 0x59414B4F */
    const val ADB_CMD_OKAY: Int = 0x59414B4F

    /** AUTH -- vendor f53866f1 = 0x48545541 */
    const val ADB_CMD_AUTH: Int = 0x48545541

    /** STLS -- vendor f53867f2 = 0x534C5453 */
    const val ADB_CMD_STLS: Int = 0x534C5453

    /** ADB protocol version -- vendor f53868f3 = 0x01000001 */
    const val ADB_VERSION: Int = 0x01000001

    /** ADB max data size -- vendor f53869f4 = 256*1024 */
    const val ADB_MAX_DATA: Int = 256 * 1024

    /** ADB STLS version -- vendor f53870f5 = 0x01000000 */
    const val ADB_STLS_VERSION: Int = 0x01000000

    /** Host identifier -- vendor f53871f6 = "host::\0".bytes(UTF_8) */
    @JvmField
    val HOST_IDENTIFIER: ByteArray = "host::\u0000".toByteArray(Charsets.UTF_8)

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
    // Packet I/O -- vendor c7, b5, i8, j9
    // ========================================================================

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

    // ========================================================================
    // RSA key format -- vendor e5, c5, b2
    // ========================================================================

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
        val result = ByteArray(256)
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
}
