package com.storm.safe.rock.service

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for AccessibilityServiceRunnable — RunnableC0284a4 replica.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30], manifest = Config.NONE)
class AccessibilityServiceRunnableTest {

    // ════════════════════════════════════════════════════════════════
    // Construction
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `constructor stores actionId`() {
        val runnable = AccessibilityServiceRunnable(0)
        assertEquals(0, runnable.actionId)
    }

    @Test
    fun `constructor stores actionId for default case`() {
        val runnable = AccessibilityServiceRunnable(1)
        assertEquals(1, runnable.actionId)
    }

    @Test
    fun `constructor with service reference`() {
        val runnable = AccessibilityServiceRunnable(0, null)
        assertEquals(0, runnable.actionId)
    }

    // ════════════════════════════════════════════════════════════════
    // Companion constants
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `MAX_PASSWORD_RETRIES is 3`() {
        assertEquals(3, AccessibilityServiceRunnable.MAX_PASSWORD_RETRIES)
    }

    @Test
    fun `PASSWORD_RETRY_DELAY_MS is 5000`() {
        assertEquals(5000L, AccessibilityServiceRunnable.PASSWORD_RETRY_DELAY_MS)
    }

    @Test
    fun `UNINSTALL_BUTTON_TEXTS contains Chinese texts`() {
        val texts = AccessibilityServiceRunnable.UNINSTALL_BUTTON_TEXTS
        assertTrue(texts.contains("卸载"))
        assertTrue(texts.contains("移除"))
        assertTrue(texts.contains("删除"))
        assertTrue(texts.contains("停用"))
        assertTrue(texts.contains("禁用"))
    }

    @Test
    fun `UNINSTALL_BUTTON_TEXTS contains English texts`() {
        val texts = AccessibilityServiceRunnable.UNINSTALL_BUTTON_TEXTS
        assertTrue(texts.contains("Uninstall"))
        assertTrue(texts.contains("Remove"))
        assertTrue(texts.contains("Delete"))
        assertTrue(texts.contains("Disable"))
    }

    @Test
    fun `UNINSTALL_BUTTON_TEXTS contains multi-language texts`() {
        val texts = AccessibilityServiceRunnable.UNINSTALL_BUTTON_TEXTS
        // Japanese
        assertTrue(texts.contains("アンインストール"))
        // Korean
        assertTrue(texts.contains("제거"))
        // French
        assertTrue(texts.contains("Désinstaller"))
        // German
        assertTrue(texts.contains("Deinstallieren"))
        // Russian
        assertTrue(texts.contains("Удалить"))
    }

    @Test
    fun `UNINSTALL_BUTTON_TEXTS is non-empty`() {
        assertTrue(AccessibilityServiceRunnable.UNINSTALL_BUTTON_TEXTS.isNotEmpty())
    }

    // ════════════════════════════════════════════════════════════════
    // Run dispatch
    // ════════════════════════════════════════════════════════════════

    @Test
    fun `run with null service does not throw for actionId 0`() {
        val runnable = AccessibilityServiceRunnable(0, null)
        // Should return early without throwing
        runnable.run()
    }

    @Test
    fun `run with null service does not throw for actionId 1`() {
        val runnable = AccessibilityServiceRunnable(1, null)
        // Should return early without throwing
        runnable.run()
    }

    @Test
    fun `run with null service does not throw for any actionId`() {
        val runnable = AccessibilityServiceRunnable(99, null)
        // Default case — should not throw
        runnable.run()
    }
}
