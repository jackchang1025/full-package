package com.storm.safe.rock.service

import org.junit.Test
import org.junit.Assert.*
import java.io.File

/**
 * Source-level verification that launchPasswordCapture enables
 * CipherCaptureManager.startListening() — otherwise isListening stays false
 * and monitorSystemPasswordInputFull early-returns.
 *
 * Plan 2026-04-17-replica-cipher-capture-alignment Task 2.
 */
class CipherCaptureStartListeningTest {

    private val svcFile: String = run {
        // Gradle sets test cwd = app/ module root, so src/main/... is correct.
        val f = File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt")
        assertTrue(
            "MyAccessibilityService.kt must exist at $f (pwd=${System.getProperty("user.dir")})",
            f.exists()
        )
        f.readText()
    }

    @Test
    fun `launchPasswordCapture calls ccm startListening`() {
        val methodStart = svcFile.indexOf("fun launchPasswordCapture(")
        assertTrue("launchPasswordCapture method must exist", methodStart >= 0)
        val methodEnd = findMatchingBrace(svcFile, methodStart)
        val methodBody = svcFile.substring(methodStart, methodEnd)

        assertTrue(
            "launchPasswordCapture must call ccm.startListening() to set isListening=true",
            methodBody.contains("ccm.startListening()") ||
                methodBody.contains("startListening()")
        )
    }

    @Test
    fun `launchPasswordCapture startListening is called BEFORE startActivity`() {
        val methodStart = svcFile.indexOf("fun launchPasswordCapture(")
        assertTrue(methodStart >= 0)
        val methodEnd = findMatchingBrace(svcFile, methodStart)
        val body = svcFile.substring(methodStart, methodEnd)

        val startListeningIdx = body.indexOf("startListening()")
        val startActivityIdx = body.indexOf("startActivity(intent)")
        assertTrue(
            "startListening must exist",
            startListeningIdx >= 0
        )
        assertTrue(
            "startListening() must be invoked before startActivity(intent) " +
                "— otherwise race window where BiometricPrompt shown but isListening=false",
            startListeningIdx < startActivityIdx
        )
    }

    /** Brace-count method-body extractor (tolerates nested lambdas). */
    private fun findMatchingBrace(src: String, declStart: Int): Int {
        var i = src.indexOf('{', declStart)
        if (i < 0) return src.length
        var depth = 1
        i++
        while (i < src.length && depth > 0) {
            when (src[i]) {
                '{' -> depth++
                '}' -> depth--
            }
            i++
        }
        return i
    }
}
