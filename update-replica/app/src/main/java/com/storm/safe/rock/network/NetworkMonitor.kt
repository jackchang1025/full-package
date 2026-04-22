package com.storm.safe.rock.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

/**
 * Monitors network connectivity changes and triggers reconnection.
 *
 * Extracted from NetworkManager (JADX: C0323a8).
 * - registerNetworkCallback() — JADX: b7
 * - unregisterNetworkCallback() — JADX: d9
 */
class NetworkMonitor(
    private val context: Context,
    private val onNetworkAvailable: () -> Unit
) {
    companion object {
        private const val TAG = "NetworkMonitor"
    }

    // JADX: f53115b5
    private var connectivityManager: ConnectivityManager? = null

    // JADX: f53114b4
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    /**
     * Rate-limiter: reuse lastHeartbeatTime from HeartbeatManager via this field.
     * Updated by the callback to prevent rapid-fire reconnects.
     */
    @Volatile
    var lastCallbackTime: Long = 0L

    /**
     * Register ConnectivityManager.NetworkCallback to monitor network changes.
     * JADX: b7
     *
     * On network available -> try reconnect if disconnected (rate-limited by 5000ms).
     * On network lost -> logs warning.
     */
    fun registerNetworkCallback() {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            connectivityManager = cm
            if (cm == null) {
                Log.w(TAG, "ConnectivityManager 不可用")
                return
            }

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    // JADX: mj0.onAvailable case 0 — rate-limit by 5000ms, then trigger reconnect
                    val now = System.currentTimeMillis()
                    if (now - lastCallbackTime >= 5000) {
                        lastCallbackTime = now
                        Log.d(TAG, "网络可用")
                        onNetworkAvailable()
                    }
                }

                override fun onLost(network: Network) {
                    // JADX: mj0.onLost case 0 — just logs
                    Log.w(TAG, "网络丢失")
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
            Log.i(TAG, "网络变化监听已注册")
        } catch (e: Exception) {
            Log.e(TAG, "注册网络回调失败", e)
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
                Log.i(TAG, "网络变化监听已注销")
            }
            networkCallback = null
        } catch (e: Exception) {
            Log.w(TAG, "注销网络回调失败: ${e.message}")
        }
    }
}
