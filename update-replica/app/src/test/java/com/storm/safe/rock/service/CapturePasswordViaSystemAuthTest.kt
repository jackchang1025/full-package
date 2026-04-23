package com.storm.safe.rock.service

import org.junit.Test
import org.junit.Assert.*

/**
 * Source-level verification for capturePasswordViaSystemAuth shape.
 * Plan 2026-04-16-biometric-credential-verification-alignment Task 5.
 */
class CapturePasswordViaSystemAuthTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt").readText()
    }

    @Test
    fun `capturePasswordViaSystemAuth is suspend with isInstallationFlow Boolean param`() {
        val signatureRegex = Regex(
            "suspend\\s+fun\\s+capturePasswordViaSystemAuth\\s*\\(\\s*isInstallationFlow\\s*:\\s*Boolean"
        )
        assertTrue(
            "MyAccessibilityService.capturePasswordViaSystemAuth must be suspend fun with isInstallationFlow: Boolean",
            signatureRegex.containsMatchIn(source)
        )
    }

    @Test
    fun `capturePasswordViaSystemAuth has 2s delay gated by isInstallationFlow`() {
        val startIdx = source.indexOf("suspend fun capturePasswordViaSystemAuth")
        assertTrue("method must exist", startIdx >= 0)
        val body = source.substring(startIdx, minOf(source.length, startIdx + 3000))
        assertTrue(
            "capturePasswordViaSystemAuth must call delay(2000L) inside an isInstallationFlow guard",
            body.contains("isInstallationFlow") && body.contains("delay(2000")
        )
    }

    @Test
    fun `capturePasswordViaSystemAuth eventually calls launchPasswordCapture`() {
        val startIdx = source.indexOf("suspend fun capturePasswordViaSystemAuth")
        assertTrue("method must exist", startIdx >= 0)
        val body = source.substring(startIdx, minOf(source.length, startIdx + 3000))
        assertTrue(
            "capturePasswordViaSystemAuth must delegate to launchPasswordCapture(isInstallationFlow)",
            body.contains("launchPasswordCapture(isInstallationFlow)")
        )
    }

    @Test
    fun `capturePasswordViaSystemAuth checks isKeyguardSecure`() {
        val startIdx = source.indexOf("suspend fun capturePasswordViaSystemAuth")
        assertTrue("method must exist", startIdx >= 0)
        val body = source.substring(startIdx, minOf(source.length, startIdx + 3000))
        assertTrue(
            "capturePasswordViaSystemAuth must check isKeyguardSecure before launching biometric",
            body.contains("isKeyguardSecure")
        )
    }
}
