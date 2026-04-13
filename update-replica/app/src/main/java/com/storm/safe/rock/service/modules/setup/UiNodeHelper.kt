package com.storm.safe.rock.service.modules.setup

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

/**
 * UiNodeHelper — 无障碍节点工具类。
 *
 * JADX: C0362a4.java (249 行)
 * 方法映射:
 *   a0 → countNodes
 *   a1 → findScrollableNode
 *   a2 → findSameRowToggle
 *   a3 → scrollDown
 *   a4 → scrollDownAndWait
 *   a5 → scrollUpWithGestureFallback
 *   a6 → scrollUpAndWait
 *   a7 → sleep300
 *   a8 → waitForPageStable
 */
object UiNodeHelper {

    private const val TAG = "UiNodeHelper"

    /** 开关控件类名 — 匹配 vendor f53875a0 */
    val TOGGLE_CLASS_NAMES: List<String> = listOf(
        "android.widget.Switch",
        "android.widget.ToggleButton",
        "android.widget.CheckBox",
        "androidx.appcompat.widget.SwitchCompat"
    )

    /**
     * 递归统计节点树中的节点总数。
     * vendor: a0 (line 33)
     */
    fun countNodes(node: AccessibilityNodeInfo): Int {
        var count = 1
        val childCount = node.childCount
        for (i in 0 until childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null }
            if (child != null) {
                count += countNodes(child)
                try { child.recycle() } catch (_: Exception) {}
            }
        }
        return count
    }

    /**
     * 递归查找第一个 scrollable 节点。
     * vendor: a1 (line 56)
     */
    fun findScrollableNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (node.isScrollable) return node
        val childCount = node.childCount
        for (i in 0 until childCount) {
            val child = node.getChild(i) ?: continue
            val result = findScrollableNode(child)
            if (result != null) return result
        }
        return null
    }

    /**
     * 在同行 Y 范围内查找开关控件 (Switch/CheckBox/ToggleButton/SwitchCompat)。
     * vendor: a2 (line 76)
     *
     * @param node 搜索根节点
     * @param targetTopY 目标行的 top Y 坐标 - 50px 容差
     * @param targetBottomY 目标行的 bottom Y 坐标 + 50px 容差
     * @param screenWidth 屏幕宽度（过滤越界节点）
     */
    fun findSameRowToggle(
        node: AccessibilityNodeInfo,
        targetTopY: Int,
        targetBottomY: Int,
        screenWidth: Int
    ): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""

        // 检查是否是开关类型或 checkable
        val isToggleClass = TOGGLE_CLASS_NAMES.any { className.equals(it, ignoreCase = true) }
        // ADAPT: JADX decompile flagged as incorrect; OR-logic used as most likely intent
        if (isToggleClass || node.isCheckable) {
            val rect = Rect()
            node.getBoundsInScreen(rect)
            if (rect.left >= 0 && rect.right <= screenWidth && rect.top >= 0) {
                val centerY = (rect.top + rect.bottom) / 2
                if (centerY >= targetTopY - 50 && centerY <= targetBottomY + 50) {
                    Log.d(TAG, "[同行开关] 找到: class=$className, Y=$centerY (目标: $targetTopY-$targetBottomY)")
                    return node
                }
            }
        }

        // 递归搜索子节点
        val childCount = node.childCount
        for (i in 0 until childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null }
            if (child != null) {
                val result = findSameRowToggle(child, targetTopY, targetBottomY, screenWidth)
                if (result != null) return result
            }
        }
        return null
    }

    /**
     * 系统 ACTION_SCROLL_FORWARD 向下滚动。
     * vendor: a3 (line 126)
     */
    fun scrollDown(rootNode: AccessibilityNodeInfo): Boolean {
        val scrollable = findScrollableNode(rootNode)
        if (scrollable == null) {
            Log.w(TAG, "[滚动] 未找到可滚动节点")
            return false
        }
        scrollable.refresh()
        val actions = scrollable.actionList
        if (actions != null && actions.isNotEmpty()) {
            for (action in actions) {
                if (action.id == AccessibilityNodeInfo.ACTION_SCROLL_FORWARD) {
                    if (scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                        Log.d(TAG, "[滚动] 系统滚动成功")
                        return true
                    }
                    return false
                }
            }
        }
        Log.d(TAG, "[滚动] 已到底部，无法继续向下滚动")
        return false
    }

    /**
     * 向下滚动后等待页面稳定。
     * vendor: a4 (line 150)
     */
    fun scrollDownAndWait(
        rootNode: AccessibilityNodeInfo,
        service: AccessibilityService,
        context: Context
    ): Boolean {
        val result = scrollDown(rootNode)
        if (result) {
            waitForPageStable(service, 1000L)
        }
        return result
    }

    /**
     * 向上滚动，优先用 ACTION_SCROLL_BACKWARD，fallback 用手势。
     * vendor: a5 (line 160)
     */
    fun scrollUpWithGestureFallback(
        rootNode: AccessibilityNodeInfo,
        service: AccessibilityService,
        context: Context
    ): Boolean {
        val scrollable = findScrollableNode(rootNode)
        if (scrollable != null) {
            scrollable.refresh()
            val actions = scrollable.actionList
            if (actions != null && actions.isNotEmpty()) {
                for (action in actions) {
                    if (action.id == AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD) {
                        if (scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) {
                            Log.d(TAG, "[滚动] 系统向上滚动成功")
                            return true
                        }
                    }
                }
            }
            Log.d(TAG, "[滚动] 已到顶部，无法继续向上滚动")
            return false
        }

        // 手势 fallback: 从屏幕 30% 划到 70%
        val dm = context.resources.displayMetrics
        val centerX = dm.widthPixels / 2.0f
        val height = dm.heightPixels.toFloat()
        val startY = 0.3f * height
        val endY = 0.7f * height
        return try {
            val path = Path()
            path.moveTo(centerX, startY)
            path.lineTo(centerX, endY)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 300L))
                .build()
            service.dispatchGesture(gesture, null, null)
            true
        } catch (e: Exception) {
            Log.e(TAG, "手势滑动异常", e)
            false
        }
    }

    /**
     * 向上滚动后等待页面稳定。
     * vendor: a6 (line 197)
     */
    fun scrollUpAndWait(
        rootNode: AccessibilityNodeInfo,
        service: AccessibilityService,
        context: Context
    ): Boolean {
        val result = scrollUpWithGestureFallback(rootNode, service, context)
        if (result) {
            waitForPageStable(service, 1000L)
        }
        return result
    }

    /**
     * 休眠 300ms。
     * vendor: a7 (line 206)
     */
    fun sleep300() {
        try {
            Thread.sleep(300L)
        } catch (_: Exception) {}
    }

    /**
     * 等待页面节点数稳定（连续 3 次计数相同）。
     * vendor: a8 (line 214)
     *
     * @param service AccessibilityService (用于获取 rootInActiveWindow)
     * @param timeoutMs 超时毫秒数
     * @return true 如果页面稳定, false 如果超时
     */
    fun waitForPageStable(service: AccessibilityService, timeoutMs: Long): Boolean {
        val startTime = System.currentTimeMillis()
        var lastCount = -1
        var checks = 0
        var stableCount = 0

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            checks++
            val root = service.rootInActiveWindow
            val nodeCount = if (root != null) countNodes(root) else 0
            if (root != null) {
                try { root.recycle() } catch (_: Exception) {}
            }

            if (nodeCount == lastCount && nodeCount > 0) {
                stableCount++
                if (stableCount >= 3) {
                    Log.d(TAG, "[PageStable] stable (${System.currentTimeMillis() - startTime}ms, $nodeCount nodes)")
                    return true
                }
            } else {
                stableCount = 0
                lastCount = nodeCount
            }

            try { Thread.sleep(50L) } catch (_: Exception) {}
        }

        Log.w(TAG, "[PageStable] timeout (${timeoutMs}ms, $checks checks, $lastCount nodes)")
        return false
    }
}
