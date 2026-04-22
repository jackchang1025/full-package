package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

/**
 * Source-level tests for pairInWifiDebugWindow() implementation.
 * Verifies the method exists with correct structure matching vendor C0360a2 L731-791.
 */
class PairInWifiDebugWindowTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private fun methodBody(): String {
        val start = source.indexOf("fun pairInWifiDebugWindow(")
        assertTrue("pairInWifiDebugWindow must exist", start >= 0)
        return source.substring(start, minOf(source.length, start + 5000))
    }

    @Test
    fun `pairInWifiDebugWindow method exists`() {
        assertTrue("pairInWifiDebugWindow must exist", source.contains("fun pairInWifiDebugWindow("))
    }

    @Test
    fun `loops up to 20 times for pairing button`() {
        val body = methodBody()
        assertTrue("must loop 20 times (vendor L731)", body.contains("0 until 20") || body.contains("< 20"))
    }

    @Test
    fun `searches for pairing button using PAIR_DEVICE_BUTTON_TEXTS`() {
        val body = methodBody()
        assertTrue(
            "must use PAIR_DEVICE_BUTTON_TEXTS constant (vendor dh0.f55790e0)",
            body.contains("PAIR_DEVICE_BUTTON_TEXTS")
        )
    }

    @Test
    fun `uses findNodeByTexts to locate pairing button`() {
        val body = methodBody()
        assertTrue("must call findNodeByTexts (vendor f9)", body.contains("findNodeByTexts("))
    }

    @Test
    fun `uses findClickableParentCompat for click target`() {
        val body = methodBody()
        assertTrue("must call findClickableParentCompat (vendor a9)", body.contains("findClickableParentCompat("))
    }

    @Test
    fun `has 1500ms sleep between button search iterations`() {
        val body = methodBody()
        assertTrue("must sleep 1500ms per iteration (vendor L738)", body.contains("1500"))
    }

    @Test
    fun `sets state to PAIRING after clicking button`() {
        val body = methodBody()
        assertTrue("must set PAIR_DEPT_PAIRING (vendor L755)", body.contains("PAIR_DEPT_PAIRING"))
    }

    @Test
    fun `has 10 second timeout for pairing code poll`() {
        val body = methodBody()
        assertTrue("must have 10s timeout (vendor L756)", body.contains("10000") || body.contains("10_000"))
    }

    @Test
    fun `calls extractPairingCodeAndPort in polling loop`() {
        val body = methodBody()
        assertTrue("must call extractPairingCodeAndPort (vendor k8)", body.contains("extractPairingCodeAndPort("))
    }

    @Test
    fun `calls doPair on success`() {
        val body = methodBody()
        assertTrue("must call doPair (vendor e2)", body.contains("doPair("))
    }

    @Test
    fun `sets PAIR_SUCCESS on successful pair`() {
        val body = methodBody()
        assertTrue("must set PAIR_DEPT_PAIR_SUCCESS (vendor L773)", body.contains("PAIR_DEPT_PAIR_SUCCESS"))
    }

    @Test
    fun `sets PAIR_FAIL on failed pair or timeout`() {
        val body = methodBody()
        assertTrue("must set PAIR_DEPT_PAIR_FAIL (vendor L766/786)", body.contains("PAIR_DEPT_PAIR_FAIL"))
    }

    @Test
    fun `calls uploadAdbKeys on success`() {
        val body = methodBody()
        assertTrue("must call uploadAdbKeys (vendor l0)", body.contains("uploadAdbKeys("))
    }

    @Test
    fun `calls syncADBConfig after successful pair`() {
        val body = methodBody()
        assertTrue("must sync ADB config (vendor L780)", body.contains("syncADBConfig") || body.contains("syncAdbConfig") || body.contains("postToLocalService"))
    }

    @Test
    fun `removes processedActions entry on completion`() {
        val body = methodBody()
        assertTrue(
            "must remove pairInWifiDebugWindow from processedActions (vendor L791)",
            body.contains("processedActions.remove(\"pairInWifiDebugWindow\")")
        )
    }

    @Test
    fun `pairInDevOption calls pairInWifiDebugWindow`() {
        val pairInDevOptionStart = source.indexOf("fun pairInDevOption()")
        assertTrue("pairInDevOption must exist", pairInDevOptionStart >= 0)
        val pairInDevOptionBody = source.substring(pairInDevOptionStart, minOf(source.length, pairInDevOptionStart + 8000))
        assertTrue(
            "pairInDevOption must call pairInWifiDebugWindow",
            pairInDevOptionBody.contains("pairInWifiDebugWindow()")
        )
    }

    @Test
    fun `PAIR_DEVICE_BUTTON_TEXTS constant exists in SetupConstants`() {
        val constantsSource = java.io.File(
            "src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt"
        ).readText()
        assertTrue(
            "SetupConstants must have PAIR_DEVICE_BUTTON_TEXTS (vendor dh0.f55790e0)",
            constantsSource.contains("PAIR_DEVICE_BUTTON_TEXTS")
        )
    }
}
