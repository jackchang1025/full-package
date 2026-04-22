package com.storm.safe.rock.service.modules.unlock

import android.app.KeyguardManager
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.command.CommandContext
import kotlinx.coroutines.delay

/**
 * Shared unlock operations extracted from UnlockCommandHandler companion object
 * and internal methods.
 *
 * This is a pure refactor -- all method implementations are identical to their
 * original locations in UnlockCommandHandler.kt.
 */
object ScreenUnlockHelper {

    private const val TAG = "UnlockCmdHandler"

    // -----------------------------------------------------------------------
    // Methods originally in UnlockCommandHandler companion object
    // -----------------------------------------------------------------------

    /**
     * Send smart unlock result to the service for forwarding.
     * Vendor: m211888b0
     */
    fun sendUnlockResult(context: CommandContext, success: Boolean, message: String) {
        try {
            context.emitLocalEvent("smart_unlock_result", mapOf(
                "success" to success,
                "message" to message
            ))
            Log.d(TAG, "[智能解锁] 发送结果: success=$success, message=$message")
        } catch (e: Exception) {
            Log.e(TAG, "[智能解锁] 发送结果失败", e)
        }
    }

    /**
     * Click a confirm button by searching for known button texts.
     * Vendor: m211885a3
     */
    fun clickConfirmButton(service: android.accessibilityservice.AccessibilityService?) {
        try {
            val root = service?.rootInActiveWindow ?: run {
                sendEnterKey()
                return
            }

            // 1. Search by text
            val confirmTexts = listOf("确认", "确定", "OK", "ok", "好的", "Enter", "完成", "done")
            for (text in confirmTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (!nodes.isNullOrEmpty()) {
                    val lastNode = nodes.last()
                    if (lastNode.isClickable) {
                        lastNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "[混合解锁] 点击确认按钮(text): $text")
                        root.recycle()
                        return
                    }
                }
            }

            // 2. Search by contentDescription (MIUI uses desc="回车" for enter key)
            if (findAndClickByContentDesc(root, listOf("回车", "enter", "Enter", "确认", "确定"))) {
                root.recycle()
                return
            }

            root.recycle()
            Log.w(TAG, "[混合解锁] 未找到确认按钮，回退发送回车键")
            sendEnterKey()
        } catch (_: Exception) {
            sendEnterKey()
        }
    }

    /**
     * Recursively search accessibility tree for a node with matching contentDescription
     * and click it.
     */
    fun findAndClickByContentDesc(
        node: AccessibilityNodeInfo,
        descs: List<String>
    ): Boolean {
        val desc = node.contentDescription?.toString() ?: ""
        if (desc.isNotEmpty() && descs.any { desc.contains(it, ignoreCase = true) } && node.isClickable) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            Log.d(TAG, "[混合解锁] 点击确认按钮(desc): $desc")
            return true
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            if (findAndClickByContentDesc(child, descs)) {
                child.recycle()
                return true
            }
            child.recycle()
        }
        return false
    }

    /**
     * Send KEYCODE_ENTER via shell command as fallback.
     */
    fun sendEnterKey() {
        try {
            Runtime.getRuntime().exec("input keyevent 66")
            Log.d(TAG, "[混合解锁] 已发送回车键(KEYCODE_ENTER)")
        } catch (e: Exception) {
            Log.w(TAG, "[混合解锁] 回车键发送失败: ${e.message}")
        }
    }

    /**
     * Recursively find all editable nodes in the accessibility tree.
     * Vendor: m211886a4
     */
    fun findEditableNodes(node: AccessibilityNodeInfo, result: MutableList<AccessibilityNodeInfo>) {
        if (node.isEditable) {
            result.add(node)
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            findEditableNodes(child, result)
        }
    }

    /**
     * Fill a password field via ACTION_SET_TEXT on the accessibility tree.
     * Vendor: m211887a9
     */
    fun fillPasswordField(
        service: android.accessibilityservice.AccessibilityService?,
        password: String
    ): Boolean {
        try {
            val root = service?.rootInActiveWindow ?: return false
            val editables = mutableListOf<AccessibilityNodeInfo>()
            findEditableNodes(root, editables)

            // Find password field: prefer fields with password/number input type
            var targetNode: AccessibilityNodeInfo? = null
            for (node in editables) {
                val inputType = node.inputType
                if ((inputType and 0x80) != 0 || (inputType and 0x10) != 0) {
                    targetNode = node
                    break
                }
            }
            // Fallback to last editable field
            if (targetNode == null) {
                targetNode = editables.lastOrNull()
            }

            if (targetNode == null) {
                Log.w(TAG, "[混合解锁] 未找到密码输入框节点")
                return false
            }

            val bundle = Bundle().apply {
                putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", password)
            }
            val result = targetNode.performAction(
                AccessibilityNodeInfo.ACTION_SET_TEXT, bundle
            )
            Log.d(TAG, "[混合解锁] ACTION_SET_TEXT 结果: $result")
            return result
        } catch (_: Exception) {
            return false
        }
    }

    // -----------------------------------------------------------------------
    // Methods originally as private/internal in UnlockCommandHandler handle methods
    // -----------------------------------------------------------------------

    /**
     * Convert pattern indices (0-8 on 3x3 grid) to screen coordinates and dispatch
     * a continuous swipe gesture through all points.
     *
     * Grid layout (index -> row,col):
     *   0(0,0)  1(0,1)  2(0,2)
     *   3(1,0)  4(1,1)  5(1,2)
     *   6(2,0)  7(2,1)  8(2,2)
     *
     * Strategy:
     * 1. Try to find actual pattern view bounds from accessibility tree
     * 2. Fall back to screen-ratio estimation
     */
    fun dispatchPatternGesture(
        service: android.accessibilityservice.AccessibilityService,
        indices: List<Int>
    ) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            Log.w(TAG, "[UNLOCK_DEVICE] API < 24, dispatchGesture 不可用")
            return
        }
        if (indices.size < 4) return

        val metrics = service.resources.displayMetrics
        val screenW = metrics.widthPixels.toFloat()
        val screenH = metrics.heightPixels.toFloat()

        // Try to detect pattern view bounds from accessibility tree
        var gridLeft: Float
        var gridTop: Float
        var cellSize: Float
        val detected = detectPatternViewBounds(service)

        if (detected != null) {
            val viewLeft = detected[0]
            val viewTop = detected[1]
            val viewRight = detected[2]
            val viewBottom = detected[3]
            val viewW = viewRight - viewLeft
            val viewH = viewBottom - viewTop
            cellSize = viewW / 3f
            // Dot centers: offset by half-cell from view edge
            gridLeft = viewLeft + cellSize / 2f
            gridTop = viewTop + (viewH / 3f) / 2f
            Log.d(TAG, "[UNLOCK_DEVICE] 检测到图案区域: ($viewLeft,$viewTop)-($viewRight,$viewBottom), cellSize=$cellSize, dotStart=($gridLeft,$gridTop)")
        } else {
            // Fallback: estimate grid at center of screen
            val gridW = screenW * 0.50f
            cellSize = gridW / 3f
            gridLeft = (screenW - gridW) / 2f + cellSize / 2f
            gridTop = screenH * 0.45f + cellSize / 2f
            Log.d(TAG, "[UNLOCK_DEVICE] 使用估算坐标: dotStart=($gridLeft,$gridTop), cellSize=$cellSize")
        }

        fun indexToXY(idx: Int): Pair<Float, Float> {
            val row = idx / 3
            val col = idx % 3
            val x = gridLeft + col * cellSize
            val y = gridTop + row * cellSize
            return Pair(x, y)
        }

        val path = android.graphics.Path()
        val first = indexToXY(indices[0])
        path.moveTo(first.first, first.second)
        Log.d(TAG, "[UNLOCK_DEVICE] 图案起点: idx=${indices[0]} -> (${first.first}, ${first.second})")
        for (i in 1 until indices.size) {
            val (x, y) = indexToXY(indices[i])
            path.lineTo(x, y)
            Log.d(TAG, "[UNLOCK_DEVICE] 图案点[$i]: idx=${indices[i]} -> ($x, $y)")
        }

        val duration = (indices.size * 150L).coerceAtLeast(600L)
        val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, duration)
        val gesture = android.accessibilityservice.GestureDescription.Builder()
            .addStroke(stroke)
            .build()

        val dispatched = service.dispatchGesture(gesture, null, null)
        Log.d(TAG, "[UNLOCK_DEVICE] 图案手势已分发: ${indices.size}个点, duration=${duration}ms, dispatched=$dispatched")
    }

    /**
     * Try to find the pattern lock view bounds from the accessibility tree.
     * Looks for views with class name containing "PatternView", "LockPattern", or "lock_pattern".
     *
     * @return FloatArray [left, top, right, bottom] or null if not found
     */
    fun detectPatternViewBounds(
        service: android.accessibilityservice.AccessibilityService
    ): FloatArray? {
        try {
            val root = service.rootInActiveWindow ?: return null
            val result = findPatternViewBounds(root)
            root.recycle()
            return result
        } catch (e: Exception) {
            Log.w(TAG, "[UNLOCK_DEVICE] 检测图案视图失败", e)
            return null
        }
    }

    /**
     * Recursively search for a pattern view node in the accessibility tree.
     */
    fun findPatternViewBounds(node: android.view.accessibility.AccessibilityNodeInfo): FloatArray? {
        val className = node.className?.toString()?.lowercase() ?: ""
        val viewId = node.viewIdResourceName?.lowercase() ?: ""

        if (className.contains("patternview") ||
            className.contains("lockpattern") ||
            viewId.contains("lock_pattern") ||
            viewId.contains("patternview")) {
            val rect = android.graphics.Rect()
            node.getBoundsInScreen(rect)
            if (rect.width() > 100 && rect.height() > 100) {
                Log.d(TAG, "[UNLOCK_DEVICE] 找到图案视图: class=$className, id=$viewId, bounds=$rect")
                return floatArrayOf(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            }
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findPatternViewBounds(child)
            child.recycle()
            if (result != null) return result
        }
        return null
    }

    /**
     * Poll KeyguardManager.isKeyguardLocked() until unlocked or timeout.
     * Vendor: C0352a9.m211894b2
     * @return true if unlocked within timeout, false if still locked
     */
    suspend fun waitForUnlockResult(context: CommandContext, timeoutMs: Long): Boolean {
        val service = context.service ?: return false
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                val km = service.getSystemService("keyguard") as? KeyguardManager
                if (km != null && !km.isKeyguardLocked) return true
            } catch (_: Exception) {}
            delay(200L)
        }
        return false
    }

    /**
     * Poll accessibility tree for numeric keypad presence.
     * Vendor: C0352a9.m211893b1
     * @return true if keypad detected (5+ digit buttons), false if timeout
     */
    suspend fun waitForNumericKeypad(context: CommandContext, timeoutMs: Long): Boolean {
        val service = context.service ?: return false
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeoutMs) {
            try {
                val root = service.rootInActiveWindow
                if (root != null) {
                    var found = 0
                    for (d in 0..9) {
                        val nodes = root.findAccessibilityNodeInfosByText(d.toString())
                        if (!nodes.isNullOrEmpty()) found++
                    }
                    root.recycle()
                    if (found >= 5) {
                        Log.d(TAG, "[键盘检测] 找到${found}个数字按钮")
                        return true
                    }
                }
            } catch (_: Exception) {}
            delay(200L)
        }
        return false
    }

    /**
     * Perform a swipe-up gesture from bottom to top of screen.
     * Convenience method used by multiple unlock handlers.
     */
    fun performSwipeUp(service: android.accessibilityservice.AccessibilityService) {
        val metrics = service.resources.displayMetrics
        val w = metrics.widthPixels.toFloat()
        val h = metrics.heightPixels.toFloat()
        // ADAPT: performSwipe is a method on MyAccessibilityService, so we cast
        (service as? com.storm.safe.rock.service.MyAccessibilityService)
            ?.performSwipe(w / 2f, h * 0.8f, w / 2f, h * 0.3f, 300L)
    }
}
