package com.storm.safe.rock.service.modules.setup.flow

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityWindowInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager

/**
 * DevOptionsNavigator -- Developer Options page UI navigation.
 *
 * Extracted from SystemOptimizeManager.kt:
 *   - openDevOptionsSettings() (L2160-2208) vendor: i5 (line 4652) -- standard Intent only
 *   - openDevOptionsSettingsV2() (L3841-3891) vendor: i5 V2 -- standard Intent only
 *   - isInDevOptionsWindow() (L1815-1842) vendor: a2/K() (line 1798)
 *   - handleRevokeUsbAuth() (L4574-4599) vendor: a8/Q (line 1997)
 *   - clearProcessedDevOpts() (L4774-4789) vendor: k9 (line 5378)
 *   - findWirelessDebugNode() (L4847-4904) vendor: l2 (line 5556)
 *
 * Vendor-specific methods moved to vendor/ adapters:
 *   - handleVivoDevOptionsSwitch() / findVivoMasterSwitch() -> VivoPairAdapter
 *   - isXiaomiNeedsSpecialHandling() -> MiuiPairAdapter
 *   - Huawei ComponentName chains -> HuaweiPairAdapter
 *
 * JADX: C0360a2.java (methods i5, i6, K, J0/P, Q, k9, l2)
 */
class DevOptionsNavigator(
    private val service: AccessibilityService,
    private val context: Context
) {

    companion object {
        private const val TAG = "DevOptionsNavigator"
    }

    /**
     * Open developer options settings activity.
     * vendor: i5 (line 4652)
     */
    fun openDevOptionsSettings() {
        Log.d(TAG, "打开开发者选项页面 (标准 Intent)")

        // Vendor-specific openDevOptions is now handled by VendorPairAdapter before calling this.
        // This method only contains the standard Intent path.
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            context.startActivity(intent)
            Log.d(TAG, "openDevOptionsSettings() 标准 Intent 启动成功")
            SystemOptimizeManager.sleep200(5)
        } catch (e: Exception) {
            Log.e(TAG, "打开开发者选项失败", e)
        }
    }

    /**
     * Open developer options settings page (V2 -- used by pairing flow).
     * vendor: i5 V2 (line 4653)
     *
     * Same intent logic as openDevOptionsSettings() but also calls onFailure on failure.
     */
    fun openDevOptionsSettingsV2(onFailure: (() -> Unit)? = null) {
        Log.d(TAG, "打开开发者选项页面 V2 (标准 Intent)")

        // Vendor-specific openDevOptions is now handled by VendorPairAdapter before calling this.
        // This method only contains the standard Intent path.
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            context.startActivity(intent)
            Log.d(TAG, "openDevOptionsSettings() 标准 Intent 启动成功")
            SystemOptimizeManager.sleep200(5)
        } catch (e: Exception) {
            Log.e(TAG, "打开开发者选项失败", e)
            onFailure?.invoke()
        }
    }

    /**
     * Check if current window is developer options page.
     * vendor: a2/K() (line 1798)
     */
    fun isInDevOptionsWindow(): Boolean {
        return try {
            // ADAPT: MIUI 上 rootInActiveWindow 和 window.root 对 settings 返回 null/桌面
            // 策略 1: 检查 getWindows() 中是否有 title 匹配开发者选项的窗口
            if (hasDevOptionsWindowByTitle()) {
                Log.i(TAG, "K() 通过窗口 title 匹配到开发者选项")
                return true
            }

            // 策略 2: 标准方式 — rootInActiveWindow + findText
            val root = service.rootInActiveWindow ?: return false
            try {
                val pkg = root.packageName?.toString()
                if (pkg != "com.android.settings") return false
                for (text in SetupConstants.DEVELOPER_OPTIONS_TEXTS) {
                    val nodes = root.findAccessibilityNodeInfosByText(text)
                    if (nodes != null && nodes.isNotEmpty()) {
                        Log.i(TAG, "K() 找到'$text' via activeWindow")
                        return true
                    }
                }
                false
            } finally {
                try { root.recycle() } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "K() 异常", e)
            false
        }
    }

    private val DEV_OPTIONS_TITLES = listOf(
        "开发者选项", "开发人员选项", "Developer options",
        "开発者オプション", "개발자 옵션", "Tùy chọn nhà phát triển",
        "Opções do desenvolvedor", "Options de développement",
        "Opciones para desarrolladores", "Entwickleroptionen"
    )

    private fun hasDevOptionsWindowByTitle(): Boolean {
        return try {
            val windows = service.windows ?: return false
            for (window in windows) {
                if (window.type != AccessibilityWindowInfo.TYPE_APPLICATION) continue
                val title = window.title?.toString() ?: continue
                if (DEV_OPTIONS_TITLES.any { title.contains(it, ignoreCase = true) }) {
                    return true
                }
            }
            false
        } catch (_: Exception) { false }
    }

    fun findSettingsWindowRoot(): AccessibilityNodeInfo? {
        return try {
            val windows = service.windows
            if (windows == null) {
                Log.w(TAG, "findSettingsWindowRoot: windows=null")
                return null
            }
            for (window in windows) {
                if (window.type != AccessibilityWindowInfo.TYPE_APPLICATION) continue
                val title = window.title?.toString()
                val root = window.root
                val pkg = root?.packageName?.toString()
                // ADAPT: MIUI 的 settings 窗口 pkg 可能为 null，用 title 辅助匹配
                val matched = pkg == "com.android.settings" ||
                    (title != null && isSettingsTitle(title))
                if (matched) {
                    if (root != null) {
                        Log.d(TAG, "findSettingsWindowRoot: 命中 id=${window.id} title=$title pkg=$pkg")
                        return root
                    }
                    // root 为 null 时尝试通过 window 直接获取
                    Log.d(TAG, "findSettingsWindowRoot: 窗口命中但 root=null, id=${window.id} title=$title")
                }
                if (root != null) try { root.recycle() } catch (_: Exception) {}
            }
            null
        } catch (e: Exception) {
            Log.d(TAG, "findSettingsWindowRoot: ${e.message}")
            null
        }
    }

    private fun isSettingsTitle(title: String): Boolean {
        val devTexts = listOf("开发者选项", "开发人员选项", "Developer options",
            "开発者オプション", "개발자 옵션", "Tùy chọn nhà phát triển")
        val debugTexts = listOf("无线调试", "無線偵錯", "Wireless debugging",
            "ワイヤレスデバッグ", "무선 디버깅")
        return devTexts.any { title.contains(it, ignoreCase = true) } ||
            debugTexts.any { title.contains(it, ignoreCase = true) } ||
            title.contains("设置", ignoreCase = true) ||
            title.contains("Settings", ignoreCase = true)
    }

    /**
     * Handle revoke USB authorization node -- click past it to wireless debugging.
     * vendor: a8/Q (line 1997)
     *
     * When the wireless debugging text is below a "revoke USB auth" toggle,
     * scroll past it by clicking on a nearby node.
     */
    fun handleRevokeUsbAuth(clickableNode: AccessibilityNodeInfo): Boolean {
        return try {
            val rect = Rect()
            clickableNode.getBoundsInScreen(rect)
            val root = service.rootInActiveWindow ?: return false
            val allNodes = ArrayList<AccessibilityNodeInfo>()
            SystemOptimizeManager.collectAllNodes(root, allNodes)
            for (node in allNodes) {
                if (node == clickableNode) continue
                val nodeRect = Rect()
                node.getBoundsInScreen(nodeRect)
                if (nodeRect.top > rect.bottom && node.isClickable) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    Log.d(TAG, "Q() 点击下方可点击节点")
                    SystemOptimizeManager.sleep200(5)
                    return true
                }
            }
            false
        } catch (e: Exception) {
            Log.e(TAG, "handleRevokeUsbAuth 异常", e)
            false
        }
    }

    /**
     * Clear processed dev options tasks from queue.
     * vendor: k9 (line 5378)
     */
    fun clearProcessedDevOpts(processedActions: java.util.concurrent.ConcurrentLinkedQueue<String>) {
        try {
            processedActions.remove("openDevOptions")
            processedActions.remove("clickBuildNumber")
            processedActions.remove("confirmDevMode")
        } catch (e: Exception) {
            Log.e(TAG, "u() 异常", e)
        }
    }

    /**
     * Find wireless debugging node in developer options by scrolling.
     * vendor: l2 (line 5556)
     *
     * Searches for wireless debugging text in developer options page.
     * Scrolls down up to 14 times, then back up if not found.
     */
    fun findWirelessDebugNode(scrollableNode: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        try {
            scrollableNode.refresh()
            Log.i(TAG, "开始滚动查找无线调试栏目")

            val wirelessDebugTexts = SetupConstants.WIRELESS_DEBUG_TEXTS
            val wirelessDebugTitleTexts = SetupConstants.WIRELESS_DEBUG_TITLE_TEXTS
            val adbWifiTexts = SetupConstants.ADB_WIFI_TEXTS

            // First check without scrolling
            var found = SystemOptimizeManager.findNodeByTexts(scrollableNode, wirelessDebugTexts)
            if (found == null) found = SystemOptimizeManager.findNodeByTexts(scrollableNode, wirelessDebugTitleTexts)
            if (found == null) found = SystemOptimizeManager.findNodeByTexts(scrollableNode, adbWifiTexts)
            if (found != null) return found

            // Scroll down to find
            for (i in 0 until 14) {
                Log.i(TAG, "向下滚动查找无线调试栏目 (第${i + 1}次)")
                val scrolled = scrollableNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                if (!scrolled) {
                    Log.i(TAG, "无法继续向下滚动")
                    break
                }
                SystemOptimizeManager.sleep200(5)
                val root = service.rootInActiveWindow ?: break
                found = SystemOptimizeManager.findNodeByTexts(root, wirelessDebugTexts)
                if (found == null) found = SystemOptimizeManager.findNodeByTexts(root, wirelessDebugTitleTexts)
                if (found == null) found = SystemOptimizeManager.findNodeByTexts(root, adbWifiTexts)
                if (found != null) return found
            }

            // Scroll back up to find
            val scrollable2 = SystemOptimizeManager.findScrollableNode(
                service.rootInActiveWindow ?: return null
            ) ?: return null
            for (i in 0 until 14) {
                Log.i(TAG, "向上滚动查找无线调试栏目 (第${i + 1}次)")
                val scrolled = scrollable2.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
                if (!scrolled) {
                    Log.i(TAG, "无法继续向上滚动")
                    return null
                }
                SystemOptimizeManager.sleep200(5)
                val root = service.rootInActiveWindow ?: break
                found = SystemOptimizeManager.findNodeByTexts(root, wirelessDebugTexts)
                if (found == null) found = SystemOptimizeManager.findNodeByTexts(root, wirelessDebugTitleTexts)
                if (found == null) found = SystemOptimizeManager.findNodeByTexts(root, adbWifiTexts)
                if (found != null) return found
            }
            return null
        } catch (e: Exception) {
            Log.e(TAG, "w0() 异常", e)
            return null
        }
    }
}
