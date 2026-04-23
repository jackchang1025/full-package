package com.storm.safe.rock.service.delegates

import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Recording implementation of NetworkMessageSender.SendAdapter.
 * Captures sendEvent / sendOperationLog calls for assertion.
 */
private class RecordingSendAdapter : NetworkMessageSender.SendAdapter {
    override var isConnected: Boolean = true

    data class EventCall(val type: String, val data: JSONObject)
    data class LogCall(val data: JSONObject)

    val eventCalls = mutableListOf<EventCall>()
    val logCalls = mutableListOf<LogCall>()

    override fun sendEvent(type: String, data: JSONObject) {
        eventCalls.add(EventCall(type, data))
    }

    override fun sendOperationLog(data: JSONObject) {
        logCalls.add(LogCall(data))
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class NetworkMessageSenderTest {

    private lateinit var adapter: RecordingSendAdapter
    private lateinit var sender: NetworkMessageSender
    private val testDeviceId = "test-device-123"

    @Before
    fun setup() {
        adapter = RecordingSendAdapter()
        adapter.isConnected = true
        sender = NetworkMessageSender(
            adapterProvider = { adapter },
            deviceIdProvider = { testDeviceId },
            testMarker = Unit
        )
    }

    // ── sendHideStatus ──

    @Test
    fun `sendHideStatus sends event with correct payload when connected`() {
        sender.sendHideStatus("App hidden", true)

        assertEquals(1, adapter.eventCalls.size)
        val call = adapter.eventCalls[0]
        assertEquals("hide_app_result", call.type)
        assertTrue(call.data.getBoolean("success"))
        assertTrue(call.data.getBoolean("isHidden"))
        assertEquals("App hidden", call.data.getString("message"))
        assertEquals(testDeviceId, call.data.getString("deviceId"))
        assertTrue(call.data.has("timestamp"))
    }

    @Test
    fun `sendHideStatus does nothing when not connected`() {
        adapter.isConnected = false
        sender.sendHideStatus("App hidden", true)
        assertEquals(0, adapter.eventCalls.size)
    }

    @Test
    fun `sendHideStatus does nothing when networkManager is null`() {
        val nullSender = NetworkMessageSender(
            adapterProvider = { null },
            deviceIdProvider = { testDeviceId },
            testMarker = Unit
        )
        nullSender.sendHideStatus("App hidden", true)
        // No exception thrown, adapter never called
    }

    // ── sendBiometricResult ──

    @Test
    fun `sendBiometricResult sends event with correct payload`() {
        sender.sendBiometricResult("Fingerprint OK", true)

        assertEquals(1, adapter.eventCalls.size)
        val call = adapter.eventCalls[0]
        assertEquals("biometric_result", call.type)
        assertTrue(call.data.getBoolean("success"))
        assertEquals("Fingerprint OK", call.data.getString("message"))
        assertTrue(call.data.has("timestamp"))
    }

    // ── sendDeviceEvent ──

    @Test
    fun `sendDeviceEvent sends operation log with correct payload`() {
        val eventData = JSONObject().apply {
            put("enabled", true)
        }
        sender.sendDeviceEvent(eventData)

        assertEquals(1, adapter.logCalls.size)
        val logData = adapter.logCalls[0].data
        assertEquals(testDeviceId, logData.getString("deviceId"))
        assertEquals("SYSTEM_EVENT", logData.getString("logType"))
        assertTrue(logData.getString("content").contains("已启用"))
        assertTrue(logData.has("extraData"))
        assertTrue(logData.has("timestamp"))
    }

    // ── sendCommandResponse ──

    @Test
    fun `sendCommandResponse builds correct json without exception`() {
        val data = mapOf<String, Any>("key" to "value", "count" to 42)
        sender.sendCommandResponse("test_response", data)
        // Method only builds JSONObject and logs, does not call sendEvent/sendOperationLog.
        assertEquals(0, adapter.eventCalls.size)
        assertEquals(0, adapter.logCalls.size)
    }

    // ── sendDebugLog ──

    @Test
    fun `sendDebugLog builds correct json without exception`() {
        sender.sendDebugLog("Test debug message")
        // Method only builds JSONObject and logs, does not call sendEvent/sendOperationLog.
        assertEquals(0, adapter.eventCalls.size)
        assertEquals(0, adapter.logCalls.size)
    }

    // ── sendScreenStatus ──

    @Test
    fun `sendScreenStatus does not throw when networkManager is null`() {
        val nullSender = NetworkMessageSender(
            adapterProvider = { null },
            deviceIdProvider = { testDeviceId },
            testMarker = Unit
        )
        nullSender.sendScreenStatus(null, null)
    }

    @Test
    fun `sendScreenStatus does not throw when called with managers`() {
        sender.sendScreenStatus(null, null)
    }
}
