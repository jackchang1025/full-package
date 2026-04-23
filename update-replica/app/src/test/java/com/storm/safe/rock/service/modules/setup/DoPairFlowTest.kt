package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

/**
 * After libadb-android refactoring, SPAKE2/TLS pairing internals are provided by the library.
 * These tests verify the library integration points exist:
 *   - AdbManager extends AbsAdbConnectionManager and has pair/connect/executeShellCommand
 *   - AdbKeyManager has generateOrLoadKeyPair
 *   - SystemOptimizeManager.doPair delegates to adbManager.pair
 */
class DoPairFlowTest {

    private val adbManagerSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/adb/AdbManager.kt").readText()
    }

    private val adbKeyManagerSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/adb/AdbKeyManager.kt").readText()
    }

    private val somSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    @Test
    fun `AdbManager extends AbsAdbConnectionManager`() {
        assertTrue("AdbManager must extend AbsAdbConnectionManager",
            adbManagerSource.contains("AbsAdbConnectionManager()"))
    }

    @Test
    fun `AdbManager has executeShellCommand method`() {
        assertTrue("AdbManager must have executeShellCommand",
            adbManagerSource.contains("fun executeShellCommand("))
    }

    @Test
    fun `AdbManager has executeAndCheck method`() {
        assertTrue("AdbManager must have executeAndCheck",
            adbManagerSource.contains("fun executeAndCheck("))
    }

    @Test
    fun `AdbManager has fireAndForget method`() {
        assertTrue("AdbManager must have fireAndForget",
            adbManagerSource.contains("fun fireAndForget("))
    }

    @Test
    fun `AdbManager overrides getPrivateKey and getCertificate`() {
        assertTrue("AdbManager must override getPrivateKey",
            adbManagerSource.contains("override fun getPrivateKey()"))
        assertTrue("AdbManager must override getCertificate",
            adbManagerSource.contains("override fun getCertificate()"))
    }

    @Test
    fun `AdbKeyManager has generateOrLoadKeyPair method`() {
        assertTrue("AdbKeyManager must have generateOrLoadKeyPair",
            adbKeyManagerSource.contains("fun generateOrLoadKeyPair()"))
    }

    @Test
    fun `SystemOptimizeManager doPair delegates to adbManager pair`() {
        val start = somSource.indexOf("fun doPair(")
        assertTrue("doPair must exist in SOM", start >= 0)
        val body = somSource.substring(start, minOf(somSource.length, start + 500))
        assertTrue("doPair must delegate to adbManager.pair",
            body.contains("adbManager.pair("))
    }
}
