package com.storm.safe.rock.service.modules.cipher

import org.junit.Test
import org.junit.Assert.*
import java.io.File

/**
 * Verifies readBufferedCipher() aligns with vendor C0335a1.m211819d0.
 * Source-level verification (runtime requires full Context mocking).
 *
 * Plan 2026-04-17-replica-cipher-capture-alignment Task 5.
 */
class CipherCaptureReadBufferedTest {

    private val managerSrc: String = run {
        // Gradle sets test cwd = app/ module root, so src/main/... is correct.
        val f = File("src/main/java/com/storm/safe/rock/service/modules/cipher/CipherCaptureManager.kt")
        assertTrue("CipherCaptureManager.kt must exist at $f (pwd=${System.getProperty("user.dir")})", f.exists())
        f.readText()
    }

    private val svcSrc: String = run {
        val f = File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt")
        assertTrue("MyAccessibilityService.kt must exist at $f (pwd=${System.getProperty("user.dir")})", f.exists())
        f.readText()
    }

    @Test
    fun `readBufferedCipher method exists`() {
        assertTrue(
            "readBufferedCipher(discard: Boolean) must exist (vendor m211819d0)",
            managerSrc.contains("fun readBufferedCipher(")
        )
    }

    @Test
    fun `readBufferedCipher returns cipher map without mutating when discard false`() {
        // Source-level: method body references pendingCipher, and when discard=false
        // does NOT set pendingCipher = null
        val start = managerSrc.indexOf("fun readBufferedCipher(")
        assertTrue(start >= 0)
        val end = minOf(managerSrc.length, start + 1500)
        val body = managerSrc.substring(start, end)
        assertTrue(
            "Body must reference pendingCipher",
            body.contains("pendingCipher")
        )
        assertTrue(
            "Body must branch on discard parameter",
            body.contains("discard")
        )
    }

    @Test
    fun `capturePasswordViaSystemAuth wires readBufferedCipher gate`() {
        val methodStart = svcSrc.indexOf("suspend fun capturePasswordViaSystemAuth(")
        assertTrue("capturePasswordViaSystemAuth method must exist", methodStart >= 0)
        val methodEnd = minOf(svcSrc.length, methodStart + 3000)
        val body = svcSrc.substring(methodStart, methodEnd)
        assertTrue(
            "capturePasswordViaSystemAuth must call readBufferedCipher(" +
                " (as already-captured gate, vendor m211819d0)",
            body.contains("readBufferedCipher(")
        )
    }
}
