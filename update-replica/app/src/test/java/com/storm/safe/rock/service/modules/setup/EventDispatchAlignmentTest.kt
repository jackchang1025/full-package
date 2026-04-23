package com.storm.safe.rock.service.modules.setup

import org.junit.Test
import org.junit.Assert.*

class EventDispatchAlignmentTest {

    private val source by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SystemOptimizeManager.kt").readText()
    }

    private val constantsSource by lazy {
        java.io.File("src/main/java/com/storm/safe/rock/service/modules/setup/SetupConstants.kt").readText()
    }

    @Test
    fun `mainAccessibilityEventHandler dispatches pairInWifiDebugWindow via isInWifiDebugWindow`() {
        assertTrue("mainAccessibilityEventHandler must exist",
            source.contains("mainAccessibilityEventHandler("))
        assertTrue("must call isInWifiDebugWindow",
            source.contains("isInWifiDebugWindow()"))
        assertTrue("must add pairInWifiDebugWindow to queue",
            source.contains("\"pairInWifiDebugWindow\""))
    }

    @Test
    fun `mainAccessibilityEventHandler dispatches pairInPairFailDialog`() {
        assertTrue("must call isInPairFailDialog",
            source.contains("isInPairFailDialog()"))
        assertTrue("must add pairInPairFailDialog to queue",
            source.contains("\"pairInPairFailDialog\""))
    }

    @Test
    fun `Scene A removes conflicting queue entries per vendor`() {
        // After refactoring, devOptions scene removes pairInWifiDebugWindow + pairInPairFailDialog
        // and wifiDebug scene removes pairInDevOption
        assertTrue("wifiDebug scene must remove pairInDevOption",
            source.contains("remove(\"pairInDevOption\")"))
        assertTrue("devOptions scene must remove pairInWifiDebugWindow",
            source.contains("remove(\"pairInWifiDebugWindow\")"))
        assertTrue("devOptions scene must remove pairInPairFailDialog",
            source.contains("remove(\"pairInPairFailDialog\")"))
    }

    @Test
    fun `onAccessibilityEventInternal delegates to filterAccessibilityEvent`() {
        val start = source.indexOf("fun onAccessibilityEventInternal(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1200))
        assertTrue("must delegate to filterAccessibilityEvent",
            body.contains("filterAccessibilityEvent("))
        assertFalse("must NOT contain duplicate isPairRunning dispatch",
            body.contains("isPairRunning.get() && !isFinished.get()"))
    }

    @Test
    fun `handlePairFailDialog resets pairState to UNKNOWN for retry`() {
        // handlePairFailDialog now lives in DialogHandler
        val dialogSource = java.io.File(
            "src/main/java/com/storm/safe/rock/service/modules/setup/flow/DialogHandler.kt"
        ).readText()
        val start = dialogSource.indexOf("fun handlePairFailDialog(")
        assertTrue("handlePairFailDialog must exist in DialogHandler", start >= 0)
        val body = dialogSource.substring(start, minOf(dialogSource.length, start + 1200))
        assertTrue("must reset pairState to UNKNOWN",
            body.contains("PAIR_DEPT_UNKNOWN"))
    }

    @Test
    fun `PAIR_FAIL_DIALOG_TEXTS constant exists`() {
        assertTrue("PAIR_FAIL_DIALOG_TEXTS must exist",
            constantsSource.contains("PAIR_FAIL_DIALOG_TEXTS"))
        assertTrue("must contain Pairing failed",
            constantsSource.contains("Pairing failed"))
    }
}
