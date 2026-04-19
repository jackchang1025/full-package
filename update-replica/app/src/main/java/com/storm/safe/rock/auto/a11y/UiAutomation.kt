package com.storm.safe.rock.auto.a11y

import android.accessibilityservice.AccessibilityService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import li.songe.selector.Selector

class UiAutomation(
    private val service: AccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "UiAutomation"
        private const val MAX_PARENT_WALK = 10
    }

    private val a11yContext = A11yContext()
    private val selectorCache = HashMap<String, Selector>()

    fun cachedSelector(selectorStr: String): Selector {
        return selectorCache.getOrPut(selectorStr) { Selector.parse(selectorStr) }
    }

    // ━━━━━━━━━ 查询 ━━━━━━━━━

    fun root(): AccessibilityNodeInfo? {
        return try { service?.rootInActiveWindow } catch (_: Exception) { null }
    }

    fun query(selectorStr: String): AccessibilityNodeInfo? {
        val root = root() ?: return null
        return a11yContext.querySelector(root, cachedSelector(selectorStr))
    }

    fun queryAll(selectorStr: String): List<AccessibilityNodeInfo> {
        val root = root() ?: return emptyList()
        return a11yContext.querySelectorAll(root, cachedSelector(selectorStr))
    }

    fun clearCache() = a11yContext.clearNodeCache()

    // ━━━━━━━━━ 操作 ━━━━━━━━━

    fun click(node: AccessibilityNodeInfo): Boolean {
        if (node.isClickable) {
            return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
        var parent = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < MAX_PARENT_WALK) {
            if (parent.isClickable) {
                return parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
            parent = try { parent.parent } catch (_: Exception) { null }
            depth++
        }
        return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    fun pressBack(): Boolean {
        return try {
            service?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK) ?: false
        } catch (_: Exception) { false }
    }

    fun pressHome(): Boolean {
        return try {
            service?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME) ?: false
        } catch (_: Exception) { false }
    }

    fun scrollForward(): Boolean {
        val scrollable = query("[scrollable=true]") ?: return false
        return scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
    }

    fun scrollBackward(): Boolean {
        val scrollable = query("[scrollable=true]") ?: return false
        return scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
    }

    // ━━━━━━━━━ 组合操作 ━━━━━━━━━

    fun clickSelector(selectorStr: String): Boolean {
        val node = query(selectorStr) ?: return false
        return click(node)
    }

    suspend fun clickSelectorWithScroll(selectorStr: String, scrollLimit: Int = 3): Boolean {
        repeat(scrollLimit + 1) { attempt ->
            val node = query(selectorStr)
            if (node != null) return click(node)
            if (attempt < scrollLimit) {
                scrollForward()
                delay(400)
            }
        }
        return false
    }

    fun toggleSwitch(selectorStr: String, targetChecked: Boolean): Boolean {
        val node = query(selectorStr) ?: return false
        if (node.isChecked == targetChecked) return true
        return click(node)
    }

    suspend fun waitForSelector(selectorStr: String, timeoutMs: Long = 5000): AccessibilityNodeInfo? {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            val node = query(selectorStr)
            if (node != null) return node
            delay(300)
        }
        return null
    }

    suspend fun waitForPackage(packageName: String, timeoutMs: Long = 5000): Boolean {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            val pkg = try { root()?.packageName?.toString() } catch (_: Exception) { null }
            if (pkg == packageName) return true
            delay(400)
        }
        return false
    }

    // ━━━━━━━━━ 开关操作 ━━━━━━━━━

    fun openSwitch(labelText: String): Boolean {
        return toggleSwitchByLabel(labelText, desiredChecked = true)
    }

    fun closeSwitch(labelText: String): Boolean {
        return toggleSwitchByLabel(labelText, desiredChecked = false)
    }

    private fun toggleSwitchByLabel(labelText: String, desiredChecked: Boolean): Boolean {
        val label = query("[text*=\"$labelText\"][visibleToUser=true]") ?: return false
        val row = findClickableParent(label) ?: label
        val sw = findSwitchInSubtree(row)
        if (sw != null) {
            if (sw.isChecked == desiredChecked) return true
            return click(sw)
        }
        return false
    }

    private fun findClickableParent(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        var p = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (p != null && depth < 5) {
            if (p.isClickable) return p
            p = try { p.parent } catch (_: Exception) { null }
            depth++
        }
        return null
    }

    private fun findSwitchInSubtree(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val cls = node.className?.toString() ?: ""
        if (cls.endsWith("Switch") || cls.endsWith("CheckBox") || cls.endsWith("CompoundButton")
            || cls.endsWith("ToggleButton") || node.isCheckable) {
            return node
        }
        for (i in 0 until node.childCount.coerceAtMost(20)) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findSwitchInSubtree(child)
            if (found != null) return found
        }
        return null
    }

    // ━━━━━━━━━ Intent 工具 ━━━━━━━━━

    fun openSettings() {
        try {
            val i = Intent(Settings.ACTION_SETTINGS)
                .addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            Log.w(TAG, "openSettings: ${e.message}")
        }
    }

    fun openAppDetails() {
        try {
            val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:${context.packageName}"))
                .addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            Log.w(TAG, "openAppDetails: ${e.message}")
        }
    }

    fun launchComponent(components: List<ComponentName>, useService: Boolean = false): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                if (useService) {
                    (service ?: context).startActivity(intent)
                } else {
                    context.startActivity(intent)
                }
                return true
            } catch (_: Exception) { continue }
        }
        return false
    }

    // ━━━━━━━━━ Gesture ━━━━━━━━━

    suspend fun tapAtCoordinate(x: Float, y: Float, durationMs: Long = 100): Boolean {
        val svc = service ?: return false
        return com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper.performTap(
            svc, x, y, durationMs
        )
    }

    // ━━━━━━━━━ 延迟 ━━━━━━━━━

    /**
     * Delay in small chunks to stay responsive to cancellation.
     * Matches vendor interruptibleDelay pattern (m212272d6).
     */
    suspend fun interruptibleDelay(totalMs: Long, chunkMs: Long = 100) {
        var remaining = totalMs
        while (remaining > 0) {
            val chunk = remaining.coerceAtMost(chunkMs)
            kotlinx.coroutines.delay(chunk)
            remaining -= chunk
        }
    }

    // ━━━━━━━━━ 步骤执行 ━━━━━━━━━

    suspend fun runStep(
        name: String,
        failures: MutableList<String>,
        block: suspend () -> Unit
    ) {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "$name 异常", e)
            failures.add("$name 异常: ${e.message}")
        }
    }

    // ━━━━━━━━━ 多级导航 ━━━━━━━━━

    suspend fun navigateByHashPath(path: String, scrollLimit: Int = 3) {
        for (segment in path.split("#")) {
            clickSelectorWithScroll("[text*=\"$segment\"][visibleToUser=true]", scrollLimit)
            delay(500)
        }
    }

    // ━━━━━━━━━ 滚动到顶部 ━━━━━━━━━

    suspend fun scrollToTop(maxAttempts: Int = 10) {
        repeat(maxAttempts) {
            if (!scrollBackward()) return
            delay(150)
        }
    }

    // ━━━━━━━━━ 权限状态检查（跨厂商共享）━━━━━━━━━

    fun isBatteryOptimized(): Boolean {
        return try {
            val pm = context.getSystemService(Context.POWER_SERVICE) as? android.os.PowerManager
                ?: return false
            pm.isIgnoringBatteryOptimizations(context.packageName)
        } catch (_: Exception) { false }
    }

    fun canDrawOverlays(): Boolean {
        return try {
            android.provider.Settings.canDrawOverlays(context)
        } catch (_: Exception) { false }
    }

    fun isExternalStorageManager(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= 30) {
            try { android.os.Environment.isExternalStorageManager() } catch (_: Exception) { false }
        } else true
    }

    // ━━━━━━━━━ 页面稳定等待（跨厂商共享）━━━━━━━━━

    suspend fun waitForPageStable(
        requiredStableCount: Int = 3,
        pollIntervalMs: Long = 300,
        timeoutMs: Long = 5000
    ): Boolean {
        val startTime = System.currentTimeMillis()
        var lastNodeCount = -1
        var stableHits = 0
        while (System.currentTimeMillis() - startTime < timeoutMs) {
            val root = root()
            val nodeCount = if (root != null) countNodes(root) else 0
            if (nodeCount == lastNodeCount && nodeCount > 0) {
                stableHits++
                if (stableHits >= requiredStableCount) return true
            } else {
                lastNodeCount = nodeCount
                stableHits = 0
            }
            kotlinx.coroutines.delay(pollIntervalMs)
        }
        return false
    }

    private fun countNodes(node: android.view.accessibility.AccessibilityNodeInfo): Int {
        var count = 1
        for (i in 0 until node.childCount.coerceAtMost(50)) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            count += countNodes(child)
            if (count > 500) return count
        }
        return count
    }
}
