package com.storm.safe.rock.service.modules

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.Socket
import java.util.concurrent.TimeUnit

class LocalHttpServerTest {

    private lateinit var server: LocalHttpServer

    @Before
    fun setup() {
        server = LocalHttpServer(0) // random port
    }

    @After
    fun teardown() {
        server.stop()
    }

    // ---------------------------------------------------------------
    // Layer 1: Parser unit tests (no socket needed)
    // ---------------------------------------------------------------

    @Test
    fun `parseRequestLine extracts method and path from GET`() {
        val (method, path) = server.parseRequestLine("GET /test HTTP/1.1")
        assertEquals("GET", method)
        assertEquals("/test", path)
    }

    @Test
    fun `parseRequestLine extracts method and path from POST`() {
        val (method, path) = server.parseRequestLine("POST /api/data HTTP/1.1")
        assertEquals("POST", method)
        assertEquals("/api/data", path)
    }

    @Test
    fun `parseRequestLine handles malformed line gracefully`() {
        val (method, path) = server.parseRequestLine("BADLINE")
        assertEquals("BADLINE", method)
        assertEquals("/", path)
    }

    @Test
    fun `parseRequestLine handles empty string`() {
        val (method, path) = server.parseRequestLine("")
        assertEquals("", method)
        assertEquals("/", path)
    }

    @Test
    fun `parsePathAndQuery separates path from query params`() {
        val (path, params) = server.parsePathAndQuery("/test?foo=bar&baz=123")
        assertEquals("/test", path)
        assertEquals("bar", params["foo"])
        assertEquals("123", params["baz"])
    }

    @Test
    fun `parsePathAndQuery returns empty map when no query`() {
        val (path, params) = server.parsePathAndQuery("/test")
        assertEquals("/test", path)
        assertTrue(params.isEmpty())
    }

    @Test
    fun `parsePathAndQuery handles path with trailing question mark`() {
        val (path, params) = server.parsePathAndQuery("/test?")
        assertEquals("/test", path)
        assertTrue(params.isEmpty())
    }

    @Test
    fun `parseQueryString decodes URL-encoded params`() {
        val params = server.parseQueryString("name=hello%20world&key=a%26b")
        assertEquals("hello world", params["name"])
        assertEquals("a&b", params["key"])
    }

    @Test
    fun `parseQueryString returns empty map for empty string`() {
        val params = server.parseQueryString("")
        assertTrue(params.isEmpty())
    }

    @Test
    fun `parseQueryString handles param without value`() {
        val params = server.parseQueryString("flag")
        assertEquals("", params["flag"])
    }

    @Test
    fun `parseQueryString handles multiple equals signs`() {
        val params = server.parseQueryString("expr=a=b")
        assertEquals("a=b", params["expr"])
    }

    // ---------------------------------------------------------------
    // Layer 2: Handler registration & routing
    // ---------------------------------------------------------------

    @Test
    fun `registered handler is called with correct params`() {
        var receivedParams: Map<String, String>? = null
        server.registerHandler("/myEndpoint") { params ->
            receivedParams = params
            JSONObject().put("success", true)
        }

        val request = "GET /myEndpoint?key=val HTTP/1.1\r\nHost: localhost\r\n\r\n"
        val input = ByteArrayInputStream(request.toByteArray())
        val output = ByteArrayOutputStream()
        val socket = MockSocket(input, output)

        server.handleClient(socket)

        assertNotNull("Handler should have been called", receivedParams)
        assertEquals("val", receivedParams!!["key"])

        val responseStr = output.toString()
        assertTrue("Response should contain HTTP 200", responseStr.contains("200"))
        assertTrue("Response should contain success", responseStr.contains("\"success\":true") || responseStr.contains("\"success\": true"))
    }

    @Test
    fun `unregistered path returns error response`() {
        val request = "GET /nonexistent HTTP/1.1\r\nHost: localhost\r\n\r\n"
        val input = ByteArrayInputStream(request.toByteArray())
        val output = ByteArrayOutputStream()
        val socket = MockSocket(input, output)

        server.handleClient(socket)

        val responseStr = output.toString()
        assertTrue("Response should contain 200 status", responseStr.contains("200"))
        assertTrue("Response body should indicate not found", responseStr.contains("Not found"))
    }

    @Test
    fun `handler exception returns error response`() {
        server.registerHandler("/crash") { _ ->
            throw RuntimeException("Boom!")
        }

        val request = "GET /crash HTTP/1.1\r\nHost: localhost\r\n\r\n"
        val input = ByteArrayInputStream(request.toByteArray())
        val output = ByteArrayOutputStream()
        val socket = MockSocket(input, output)

        server.handleClient(socket)

        val responseStr = output.toString()
        assertTrue("Response should contain error", responseStr.contains("Boom!"))
    }

    @Test
    fun `POST with JSON body merges params`() {
        var receivedParams: Map<String, String>? = null
        server.registerHandler("/data") { params ->
            receivedParams = params
            JSONObject().put("received", true)
        }

        val body = """{"name":"test","value":"42"}"""
        val request = "POST /data?extra=yes HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: ${body.length}\r\n" +
                "\r\n" +
                body
        val input = ByteArrayInputStream(request.toByteArray())
        val output = ByteArrayOutputStream()
        val socket = MockSocket(input, output)

        server.handleClient(socket)

        assertNotNull("Handler should have been called", receivedParams)
        assertEquals("test", receivedParams!!["name"])
        assertEquals("42", receivedParams!!["value"])
        assertEquals("yes", receivedParams!!["extra"])
    }

    @Test
    fun `POST with form-encoded body parses params`() {
        var receivedParams: Map<String, String>? = null
        server.registerHandler("/form") { params ->
            receivedParams = params
            JSONObject().put("ok", true)
        }

        val body = "username=admin&password=secret"
        val request = "POST /form HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: ${body.length}\r\n" +
                "\r\n" +
                body
        val input = ByteArrayInputStream(request.toByteArray())
        val output = ByteArrayOutputStream()
        val socket = MockSocket(input, output)

        server.handleClient(socket)

        assertNotNull("Handler should have been called", receivedParams)
        assertEquals("admin", receivedParams!!["username"])
        assertEquals("secret", receivedParams!!["password"])
    }

    // ---------------------------------------------------------------
    // Layer 3: Full integration — start/stop lifecycle + HTTP
    // ---------------------------------------------------------------

    @Test
    fun `start and stop lifecycle`() {
        server.start()
        Thread.sleep(100)

        assertTrue("Server should be running", server.isRunning())
        val port = server.getPort()
        assertTrue("Port should be positive", port > 0)

        server.stop()
        Thread.sleep(100)

        assertFalse("Server should be stopped", server.isRunning())
    }

    @Test
    fun `getPort returns 0 before start`() {
        assertEquals(0, server.getPort())
    }

    @Test
    fun `start is idempotent`() {
        server.start()
        Thread.sleep(100)
        val port1 = server.getPort()

        server.start() // second call should be no-op
        Thread.sleep(50)
        val port2 = server.getPort()

        assertEquals("Port should not change on double start", port1.toLong(), port2.toLong())
    }

    @Test
    fun `real HTTP GET request returns handler response`() {
        server.registerHandler("/test") { params ->
            JSONObject().put("success", true).put("echo", params["msg"] ?: "none")
        }
        server.start()
        Thread.sleep(200)
        val port = server.getPort()

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val response = client.newCall(
            Request.Builder().url("http://127.0.0.1:$port/test?msg=hello").build()
        ).execute()

        assertEquals(200, response.code)
        val body = JSONObject(response.body!!.string())
        assertTrue(body.getBoolean("success"))
        assertEquals("hello", body.getString("echo"))
    }

    @Test
    fun `real HTTP POST with JSON body`() {
        server.registerHandler("/api") { params ->
            JSONObject()
                .put("success", true)
                .put("name", params["name"] ?: "missing")
        }
        server.start()
        Thread.sleep(200)
        val port = server.getPort()

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val jsonBody = """{"name":"LocalHttpServer"}"""
        val response = client.newCall(
            Request.Builder()
                .url("http://127.0.0.1:$port/api")
                .post(jsonBody.toRequestBody("application/json".toMediaType()))
                .build()
        ).execute()

        assertEquals(200, response.code)
        val body = JSONObject(response.body!!.string())
        assertTrue(body.getBoolean("success"))
        assertEquals("LocalHttpServer", body.getString("name"))
    }

    @Test
    fun `real HTTP request to unregistered path returns error`() {
        server.start()
        Thread.sleep(200)
        val port = server.getPort()

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val response = client.newCall(
            Request.Builder().url("http://127.0.0.1:$port/doesNotExist").build()
        ).execute()

        assertEquals(200, response.code)
        val body = JSONObject(response.body!!.string())
        assertFalse(body.getBoolean("success"))
        assertTrue(body.getString("error").contains("Not found"))
    }

    @Test
    fun `response contains correct Content-Type header`() {
        server.registerHandler("/ping") { _ ->
            JSONObject().put("pong", true)
        }
        server.start()
        Thread.sleep(200)
        val port = server.getPort()

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val response = client.newCall(
            Request.Builder().url("http://127.0.0.1:$port/ping").build()
        ).execute()

        val contentType = response.header("Content-Type")
        assertNotNull("Content-Type should be set", contentType)
        assertTrue(
            "Content-Type should be application/json",
            contentType!!.contains("application/json")
        )
    }

    @Test
    fun `response contains correct Content-Length header`() {
        server.registerHandler("/length") { _ ->
            JSONObject().put("data", "test")
        }
        server.start()
        Thread.sleep(200)
        val port = server.getPort()

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val response = client.newCall(
            Request.Builder().url("http://127.0.0.1:$port/length").build()
        ).execute()

        val bodyStr = response.body!!.string()
        val contentLength = response.header("Content-Length")
        assertNotNull("Content-Length should be set", contentLength)
        assertEquals(bodyStr.toByteArray().size, contentLength!!.toInt())
    }

    // ---------------------------------------------------------------
    // Placeholder endpoint groups
    // ---------------------------------------------------------------

    @Test
    fun `default placeholder endpoints are registered`() {
        val serverWithDefaults = LocalHttpServer.createWithDefaultHandlers(0)
        serverWithDefaults.start()
        Thread.sleep(200)
        val port = serverWithDefaults.getPort()

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

        val endpoints = listOf(
            "/accessibilityState",
            "/dumpHierarchy",
            "/global/lockScreen",
            "/blockView",
            "/syncLockCipher",
            "/loadConfig"
        )

        for (endpoint in endpoints) {
            val response = client.newCall(
                Request.Builder().url("http://127.0.0.1:$port$endpoint").build()
            ).execute()

            assertEquals("$endpoint should return 200", 200, response.code)
            val body = JSONObject(response.body!!.string())
            assertTrue(
                "$endpoint should return success=true",
                body.getBoolean("success")
            )
        }

        serverWithDefaults.stop()
    }
}

/**
 * Minimal mock Socket for unit-testing handleClient() without real networking.
 */
class MockSocket(
    private val inputData: ByteArrayInputStream,
    private val outputData: ByteArrayOutputStream
) : Socket() {
    private var closed = false

    override fun getInputStream() = inputData
    override fun getOutputStream() = outputData
    override fun close() { closed = true }
    override fun isClosed() = closed
    override fun setSoTimeout(timeout: Int) { /* no-op */ }
}
