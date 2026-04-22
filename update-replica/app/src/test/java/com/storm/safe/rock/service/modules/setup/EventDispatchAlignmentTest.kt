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
        val start = source.indexOf("fun mainAccessibilityEventHandler(")
        assertTrue("mainAccessibilityEventHandler must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must call isInWifiDebugWindow",
            body.contains("isInWifiDebugWindow()"))
        assertTrue("must add pairInWifiDebugWindow to queue",
            body.contains("\"pairInWifiDebugWindow\""))
    }

    @Test
    fun `mainAccessibilityEventHandler dispatches pairInPairFailDialog`() {
        val start = source.indexOf("fun mainAccessibilityEventHandler(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        assertTrue("must call isInPairFailDialog",
            body.contains("isInPairFailDialog()"))
        assertTrue("must add pairInPairFailDialog to queue",
            body.contains("\"pairInPairFailDialog\""))
    }

    @Test
    fun `Scene A removes 6 conflicting queue entries per vendor`() {
        val start = source.indexOf("fun mainAccessibilityEventHandler(")
        assertTrue(start >= 0)
        val body = source.substring(start, minOf(source.length, start + 5000))
        val requiredRemovals = listOf(
            "pairInWifiDebugWindow", "pairInPairCodeDialog",
            "pairInPairFailDialog", "pairInConfirmLock",
            "pairInSecurityCenter", "pairInPairSuccess"
        )
        for (entry in requiredRemovals) {
            assertTrue("Scene A must remove '$entry'",
                body.contains("remove(\"$entry\")"))
        }
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
        val start = source.indexOf("fun handlePairFailDialog()")
        assertTrue("handlePairFailDialog must exist", start >= 0)
        val body = source.substring(start, minOf(source.length, start + 1200))
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
