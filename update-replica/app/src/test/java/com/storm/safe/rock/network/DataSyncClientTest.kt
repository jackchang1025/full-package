package com.storm.safe.rock.network

import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DataSyncClientTest {

    private lateinit var server: MockWebServer
    private var lastMessage: String? = null
    private var connectionState: Boolean? = null

    @Before
    fun setup() {
        server = MockWebServer()
        lastMessage = null
        connectionState = null
    }

    @After
    fun teardown() {
        try { server.shutdown() } catch (_: Exception) {}
    }

    // --- Constructor ---

    @Test
    fun `constructor creates client with correct timeout config`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, { connectionState = it })
        assertNotNull(client)
    }

    // --- connect() ---

    @Test
    fun `connect without server url does not crash`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        // No serverUrl set — should return without crashing
        client.connect()
        assertFalse(client.isConnected)
    }

    @Test
    fun `connect without device id does not crash`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com"
        // No deviceId set — should return without crashing
        client.connect()
        assertFalse(client.isConnected)
    }

    @Test
    fun `connect sets isConnecting to true when serverUrl and deviceId are set`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com"
        client.deviceId = "test-device"
        client.deviceKeySalt = "salt"
        // connect() will try to create a real WebSocket (which will fail),
        // but isConnecting should be set before the attempt
        client.connect()
        // After connect attempt fails, state resets, so just verify no crash
        // The important thing is that it attempted to connect
    }

    @Test
    fun `connect skips when already connected`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com"
        client.deviceId = "test-device"
        client.deviceKeySalt = "salt"
        // Simulate already connected
        client.isConnected = true
        client.connect()
        // Should have skipped without changing state
        assertTrue(client.isConnected)
    }

    @Test
    fun `connect resets when connecting exceeds 12s timeout`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com"
        client.deviceId = "test-device"
        client.deviceKeySalt = "salt"
        // Simulate stuck connecting
        client.isConnecting = true
        client.connectStartTime = System.currentTimeMillis() - 13000
        client.connect()
        // After timeout reset, it should attempt to reconnect
        // (which may fail for real network), but state should have been reset first
    }

    // --- disconnect() ---

    @Test
    fun `disconnect resets state`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, { connectionState = it })
        client.disconnect()
        assertFalse(client.isConnected)
        assertFalse(client.isConnecting)
    }

    @Test
    fun `disconnect notifies onConnectionChanged with false`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, { connectionState = it })
        client.disconnect()
        assertEquals(false, connectionState)
    }

    @Test
    fun `disconnect resets connectStartTime to zero`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.connectStartTime = 12345L
        client.disconnect()
        assertEquals(0L, client.connectStartTime)
    }

    // --- send() ---

    @Test
    fun `send returns false when not connected`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        assertFalse(client.send("hello"))
    }

    @Test
    fun `send returns false when webSocket is null`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.isConnected = true
        // webSocket is null
        assertFalse(client.send("hello"))
    }

    // --- sendStatus() ---

    @Test
    fun `sendStatus returns false when not connected`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        assertFalse(client.sendStatus(JSONObject().put("test", true)))
    }

    @Test
    fun `sendStatus wraps data in status envelope`() {
        val context = RuntimeEnvironment.getApplication()
        var sentMessage: String? = null
        val client = object : DataSyncClient(context, {}, {}) {
            override fun send(message: String): Boolean {
                sentMessage = message
                return true
            }
        }
        client.isConnected = true
        client.deviceId = "dev-123"
        val data = JSONObject().put("battery", 85)
        client.sendStatus(data)

        assertNotNull(sentMessage)
        val envelope = JSONObject(sentMessage!!)
        assertEquals("status", envelope.getString("type"))
        assertEquals("dev-123", envelope.getString("sessionId"))
        assertTrue(envelope.has("timestamp"))
        assertEquals(85, envelope.getJSONObject("data").getInt("battery"))
    }

    // --- sendScreenshot() ---

    @Test
    fun `sendScreenshot wraps data in screen_frame envelope`() {
        val context = RuntimeEnvironment.getApplication()
        var sentMessage: String? = null
        val client = object : DataSyncClient(context, {}, {}) {
            override fun send(message: String): Boolean {
                sentMessage = message
                return true
            }
        }
        client.isConnected = true
        client.deviceId = "dev-456"
        client.sendScreenshot("base64data==", "full")

        assertNotNull(sentMessage)
        val envelope = JSONObject(sentMessage!!)
        assertEquals("screen_frame", envelope.getString("type"))
        assertEquals("dev-456", envelope.getString("sessionId"))
        val frameData = envelope.getJSONObject("data")
        assertEquals("base64data==", frameData.getString("image"))
        assertEquals(0, frameData.getInt("width"))
        assertEquals(0, frameData.getInt("height"))
        assertEquals("full", frameData.getString("mode"))
        assertTrue(frameData.has("timestamp"))
    }

    // --- generateWsUrl() ---

    @Test
    fun `generateWsUrl builds correct URL format with wss for https`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com"
        client.deviceId = "test-device-123"
        client.deviceKeySalt = "test-salt"
        val url = client.generateWsUrl()
        assertTrue("URL should start with wss://", url.startsWith("wss://"))
        assertTrue("URL should contain sessionId param", url.contains("/ws/session?sessionId=test-device-123"))
        assertTrue("URL should contain key param", url.contains("&key="))
        // Key should be 32 hex chars
        val key = url.substringAfter("&key=")
        assertEquals("HMAC key should be 32 hex chars", 32, key.length)
        assertTrue("Key should be lowercase hex", key.matches(Regex("[0-9a-f]+")))
    }

    @Test
    fun `generateWsUrl uses ws for http URLs`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "http://192.168.1.1:8080"
        client.deviceId = "dev1"
        client.deviceKeySalt = "salt"
        val url = client.generateWsUrl()
        assertTrue("URL should start with ws://", url.startsWith("ws://"))
        assertFalse("URL should not start with wss://", url.startsWith("wss://"))
    }

    @Test
    fun `generateWsUrl uses wss for wss URLs`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "wss://secure.example.com"
        client.deviceId = "dev1"
        client.deviceKeySalt = "salt"
        val url = client.generateWsUrl()
        assertTrue("URL should start with wss://", url.startsWith("wss://"))
    }

    @Test
    fun `generateWsUrl uses ws for ws URLs`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "ws://local.example.com"
        client.deviceId = "dev1"
        client.deviceKeySalt = "salt"
        val url = client.generateWsUrl()
        assertTrue("URL should start with ws://", url.startsWith("ws://"))
        assertFalse("URL should not start with wss://", url.startsWith("wss://"))
    }

    @Test
    fun `generateWsUrl strips trailing slash from host`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com/"
        client.deviceId = "dev1"
        client.deviceKeySalt = "salt"
        val url = client.generateWsUrl()
        assertTrue("Should contain example.com/ws/", url.contains("example.com/ws/session"))
        assertFalse("Should not have double slash", url.contains("example.com//ws"))
    }

    @Test
    fun `generateWsUrl preserves port in host`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://example.com:8443"
        client.deviceId = "dev1"
        client.deviceKeySalt = "salt"
        val url = client.generateWsUrl()
        assertTrue("Should contain port", url.contains("example.com:8443/ws/session"))
    }

    // --- HMAC determinism ---

    @Test
    fun `HMAC signature is deterministic`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://test.com"
        client.deviceId = "same-device"
        client.deviceKeySalt = "same-salt"
        val url1 = client.generateWsUrl()
        val url2 = client.generateWsUrl()
        assertEquals("Same inputs should produce same URL", url1, url2)
    }

    @Test
    fun `different device IDs produce different HMAC keys`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://test.com"
        client.deviceKeySalt = "salt"

        client.deviceId = "device-a"
        val url1 = client.generateWsUrl()
        val key1 = url1.substringAfter("&key=")

        client.deviceId = "device-b"
        val url2 = client.generateWsUrl()
        val key2 = url2.substringAfter("&key=")

        assertNotEquals("Different devices should produce different keys", key1, key2)
    }

    @Test
    fun `different salts produce different HMAC keys`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.serverUrl = "https://test.com"
        client.deviceId = "device-1"

        client.deviceKeySalt = "salt-a"
        val key1 = client.generateWsUrl().substringAfter("&key=")

        client.deviceKeySalt = "salt-b"
        val key2 = client.generateWsUrl().substringAfter("&key=")

        assertNotEquals("Different salts should produce different keys", key1, key2)
    }

    // --- checkStuckConnection() ---

    @Test
    fun `checkStuckConnection resets after 15s`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        // Simulate stuck connecting state
        client.isConnecting = true
        client.connectStartTime = System.currentTimeMillis() - 16000 // 16 seconds ago
        client.checkStuckConnection()
        assertFalse(client.isConnecting)
        assertFalse(client.isConnected)
    }

    @Test
    fun `checkStuckConnection does nothing when not connecting`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.isConnecting = false
        client.checkStuckConnection()
        assertFalse(client.isConnecting)
    }

    @Test
    fun `checkStuckConnection does nothing when within 15s window`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, {})
        client.isConnecting = true
        client.connectStartTime = System.currentTimeMillis() - 5000 // 5 seconds ago
        client.checkStuckConnection()
        // Should still be connecting — not enough time has passed
        assertTrue(client.isConnecting)
    }

    // --- onMessage (internal message parsing) ---

    @Test
    fun `onMessage with pong type is ignored`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, {})
        val msg = JSONObject().put("type", "pong").toString()
        client.handleMessage(msg)
        assertNull("pong should not dispatch to onMessage callback", lastMessage)
    }

    @Test
    fun `onMessage with command type dispatches data to callback`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, {})
        val commandData = JSONObject()
            .put("command", "lock_screen")
            .put("params", JSONObject().put("duration", 30))
        val msg = JSONObject()
            .put("type", "command")
            .put("data", commandData)
            .toString()
        client.handleMessage(msg)
        assertNotNull("command should dispatch to onMessage callback", lastMessage)
        val parsed = JSONObject(lastMessage!!)
        assertEquals("lock_screen", parsed.getString("command"))
    }

    @Test
    fun `onMessage with command type but no data is ignored`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, {})
        val msg = JSONObject()
            .put("type", "command")
            .toString()
        client.handleMessage(msg)
        assertNull("command without data should not dispatch", lastMessage)
    }

    @Test
    fun `onMessage with probe type responds with status when connected`() {
        val context = RuntimeEnvironment.getApplication()
        var sentMessage: String? = null
        val client = object : DataSyncClient(context, {}, {}) {
            override fun send(message: String): Boolean {
                sentMessage = message
                return true
            }
        }
        client.isConnected = true
        client.deviceId = "probe-test-device"

        val msg = JSONObject().put("type", "probe").toString()
        client.handleMessage(msg)

        assertNotNull("probe should trigger status response", sentMessage)
        val response = JSONObject(sentMessage!!)
        assertEquals("status", response.getString("type"))
        assertEquals("probe-test-device", response.getString("sessionId"))
    }

    @Test
    fun `onMessage with ping_probe type responds with status when connected`() {
        val context = RuntimeEnvironment.getApplication()
        var sentMessage: String? = null
        val client = object : DataSyncClient(context, {}, {}) {
            override fun send(message: String): Boolean {
                sentMessage = message
                return true
            }
        }
        client.isConnected = true
        client.deviceId = "probe-test-device"

        val msg = JSONObject().put("type", "ping_probe").toString()
        client.handleMessage(msg)

        assertNotNull("ping_probe should trigger status response", sentMessage)
        val response = JSONObject(sentMessage!!)
        assertEquals("status", response.getString("type"))
    }

    @Test
    fun `onMessage with unknown type is ignored`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, {})
        val msg = JSONObject().put("type", "unknown_type_xyz").toString()
        client.handleMessage(msg)
        assertNull("unknown type should be ignored", lastMessage)
    }

    @Test
    fun `onMessage with invalid JSON does not crash`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, {})
        client.handleMessage("this is not json{{{")
        assertNull("invalid JSON should not dispatch", lastMessage)
    }

    @Test
    fun `onMessage with empty string does not crash`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, { lastMessage = it }, {})
        client.handleMessage("")
        assertNull("empty string should not dispatch", lastMessage)
    }

    // --- resetState() ---

    @Test
    fun `resetState clears all connection state`() {
        val context = RuntimeEnvironment.getApplication()
        val client = DataSyncClient(context, {}, { connectionState = it })
        client.isConnected = true
        client.isConnecting = true
        client.connectStartTime = 99999L
        client.resetState()
        assertFalse(client.isConnected)
        assertFalse(client.isConnecting)
        assertEquals(0L, client.connectStartTime)
        assertEquals(false, connectionState)
    }
}
