package com.storm.safe.rock.p000

import android.content.Context
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.LinkedHashMap

/**
 * JADX: p000/AbstractC1408xb.java (91 LOC) — Encrypted config file reader.
 *
 * Reads asset files, optionally decrypting them with XOR cipher.
 * Used by hkdrkgzsfs (MyApplication) during startup to load config files.
 *
 * Static fields:
 * - f61060a0 → encryptionEnabled: whether encryption is active
 * - f61061a1 → encryptionKey: XOR key bytes
 * - f61062a2 → fileMapping: logical name → encrypted filename map
 * - f61063a3 → MAGIC_BYTES: [90, 77, 50, 54] = "ZM26" file signature
 *
 * Static method:
 * - m215154a0(context, key) → readAsset: reads asset, decrypts if needed, returns String
 */
abstract class EncryptedConfigStore {

    companion object {
        /**
         * JADX: f61060a0 — Whether encryption is enabled.
         * Set to true by MyApplication.onCreate when config encryption is active.
         */
        @JvmStatic
        var encryptionEnabled: Boolean = false

        /**
         * JADX: f61061a1 — XOR encryption key bytes.
         * Set by MyApplication.onCreate with the config encryption key.
         */
        @JvmStatic
        var encryptionKey: ByteArray = byteArrayOf()

        /**
         * JADX: f61062a2 — Maps logical config filenames to encrypted asset filenames.
         * e.g. "config.json" → "a1b2c3.dat"
         */
        @JvmStatic
        val fileMapping: LinkedHashMap<String, String> = LinkedHashMap()

        /**
         * JADX: f61063a3 — Magic bytes header for encrypted files: [90, 77, 50, 54] = "ZM26".
         * Files starting with these bytes use the extended key format (key + nonce).
         */
        @JvmStatic
        val MAGIC_BYTES: ByteArray = byteArrayOf(90, 77, 50, 54)

        /** Buffer size for reading — matches okio.Segment.SIZE = 8192 */
        private const val BUFFER_SIZE = 8192

        /**
         * JADX: m215154a0 — Read an asset file, decrypting if encryption is enabled.
         *
         * Logic:
         * 1. If encryption disabled or key empty → read plain asset
         * 2. If encryption enabled but no mapping for this name → read plain asset
         * 3. If mapping exists → read mapped file, check magic bytes:
         *    a. If magic "ZM26" present: extract 12-byte nonce from offset 4..16,
         *       build combinedKey = encryptionKey + nonce, XOR payload from offset 20
         *    b. If no magic: XOR entire file with encryptionKey
         * 4. Wrap decrypted bytes as InputStream, read to String via BufferedReader
         *
         * @param context Android context for asset access
         * @param originalName Logical asset filename
         * @return Decrypted/plain file content as String
         * @throws IOException if asset cannot be read
         */
        @JvmStatic
        @Throws(IOException::class)
        fun readAsset(context: Context, originalName: String): String {
            val inputStream: InputStream

            if (!encryptionEnabled || encryptionKey.isEmpty()) {
                // No encryption → read original file directly
                inputStream = context.assets.open(originalName)
            } else {
                val mappedName = fileMapping[originalName]
                if (mappedName == null) {
                    // No mapping → read original file directly
                    inputStream = context.assets.open(originalName)
                } else {
                    // Read mapped (encrypted) file
                    val encryptedStream = context.assets.open(mappedName)
                    val rawBytes = encryptedStream.readBytes()

                    val decrypted: ByteArray
                    if (rawBytes.size > 20 &&
                        rawBytes[0] == MAGIC_BYTES[0] &&
                        rawBytes[1] == MAGIC_BYTES[1] &&
                        rawBytes[2] == MAGIC_BYTES[2] &&
                        rawBytes[3] == MAGIC_BYTES[3]
                    ) {
                        // Extended format: magic(4) + salt(8) + reserved(8) + payload
                        val salt = rawBytes.copyOfRange(4, 12) // 8 bytes (salt from zm26_meta)
                        val combinedKey = encryptionKey + salt
                        val payload = rawBytes.copyOfRange(20, rawBytes.size)
                        decrypted = ByteArray(payload.size) { i ->
                            (payload[i].toInt() xor combinedKey[i % combinedKey.size].toInt()).toByte()
                        }
                    } else {
                        // Simple format: XOR entire content with key
                        decrypted = ByteArray(rawBytes.size) { i ->
                            (rawBytes[i].toInt() xor encryptionKey[i % encryptionKey.size].toInt()).toByte()
                        }
                    }
                    inputStream = ByteArrayInputStream(decrypted)
                }
            }

            // Read InputStream to String (JADX: charset = UTF-8, buffer = Segment.SIZE)
            val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8), BUFFER_SIZE)
            return reader.use { it.readText() }
        }
    }
}
