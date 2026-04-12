package com.storm.safe.rock.util

import android.util.Base64

object StringUtil {

    private val KEY1 = byteArrayOf(
        74, 127, 43, 94, 28, (-115).toByte(), 58, 111,
        (-98).toByte(), 13, 76, 123, 42, 95, 30, (-116).toByte()
    )

    private val KEY2 = byteArrayOf(
        59, 110, 26, 77, 12, 124, 43, 94,
        (-113).toByte(), 14, 61, 108, 27, 78, 15, 125
    )

    fun decrypt(cipherText: String): String {
        if (cipherText.isEmpty()) return ""
        return try {
            val decoded = Base64.decode(cipherText, Base64.NO_WRAP)
            val swapped = decoded.copyOf()
            // Byte-pair swap: iterate from 0 to length-2 with step 2
            var i = 0
            while (i < swapped.size - 1) {
                val tmp = swapped[i]
                swapped[i] = swapped[i + 1]
                swapped[i + 1] = tmp
                i += 2
            }
            // XOR with KEY2
            val xor1 = ByteArray(swapped.size) { idx ->
                (swapped[idx].toInt() xor KEY2[idx % 16].toInt()).toByte()
            }
            // XOR with KEY1
            val xor2 = ByteArray(xor1.size) { idx ->
                (xor1[idx].toInt() xor KEY1[idx % 16].toInt()).toByte()
            }
            String(xor2, Charsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
    }

    fun encrypt(plainText: String): String {
        if (plainText.isEmpty()) return ""
        val bytes = plainText.toByteArray(Charsets.UTF_8)
        // Reverse operations: XOR KEY1 -> XOR KEY2 -> swap pairs -> Base64
        val xor1 = ByteArray(bytes.size) { idx ->
            (bytes[idx].toInt() xor KEY1[idx % 16].toInt()).toByte()
        }
        val xor2 = ByteArray(xor1.size) { idx ->
            (xor1[idx].toInt() xor KEY2[idx % 16].toInt()).toByte()
        }
        val swapped = xor2.copyOf()
        var i = 0
        while (i < swapped.size - 1) {
            val tmp = swapped[i]
            swapped[i] = swapped[i + 1]
            swapped[i + 1] = tmp
            i += 2
        }
        return Base64.encodeToString(swapped, Base64.NO_WRAP)
    }
}
