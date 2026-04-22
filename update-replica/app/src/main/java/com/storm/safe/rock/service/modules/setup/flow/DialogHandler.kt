package com.storm.safe.rock.service.modules.setup.flow

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager
import java.util.concurrent.atomic.AtomicInteger

/**
 * DialogHandler -- Automatic dialog handling for ADB pairing automation.
 *
 * Extracted from SystemOptimizeManager.kt:
 *   - handleUsbDebugDialog() (L1988-2033) vendor: h0 (line 3084)
 *   - isInPairFailDialog() (L1870-1890) vendor: a4
 *   - handlePairFailDialog() (L1891-1927) vendor: dispatched via pairInPairFailDialog
 *   - handleNetworkConfirmDialog() (L3310-3375) vendor: h2 (line 3218)
 *   - findConfirmButtonRecursive() (L5068-5092) vendor: f7 (line ~1250)
 *   - findScrollableViewWithRetry() (L1928-1987) vendor: d6 (line 2401)
 *
 * Vendor-specific methods moved to vendor/ adapters:
 *   - handleDisablePermissionMonitor() -> OppoPairAdapter
 *   - handleMiuiSecurityDialog() / isInMiuiSecurityCenter() / isInMiuiAdbInputWindow() -> MiuiPairAdapter
 *
 * JADX: C0360a2.java (methods h0, a4, h2, h3, f7, d6)
 */
class DialogHandler(
    private val service: AccessibilityService,
    private val context: Context
) {

    companion object {
        private const val TAG = "DialogHandler"
    }

    /**
     * Handle USB debug authorization dialog (always allow + click OK).
     * vendor: h0 (line 3084)
     */
    fun handleUsbDebugDialog(lastUsbDebugDialogTime: Long): Long {
        var updatedTime = lastUsbDebugDialogTime
        val root = service.rootInActiveWindow ?: return updatedTime
        val usbDebugTexts = listOf(
            "允许USB调试", "Allow USB debugging", "USB 调试",
            "USB debugging", "USBデバッグ", "USB 디버깅"
        )
        for (text in usbDebugTexts) {
            val nodes = root.findAccessibilityNodeInfosByText(text)
            if (nodes != null && nodes.isNotEmpty()) {
                Log.i(TAG, "检测到 USB 调试弹窗（包含相关文本）")
                val now = System.currentTimeMillis()
                if (now - lastUsbDebugDialogTime < 5000) return updatedTime

                // Try to check "always allow" checkbox
                try {
                    val compoundButton = SystemOptimizeManager.findCompoundButton(root)
                    if (compoundButton != null) {
                        if (!compoundButton.isChecked) {
                            compoundButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.d(TAG, "已勾选 CompoundButton (一律允许)")
                            SystemClock.sleep(300L)
                        }
                    }
                } catch (_: Exception) {}

                // Click OK button
                try {
                    val button1Nodes = root.findAccessibilityNodeInfosByViewId("android:id/button1")
                    var okButton = button1Nodes?.firstOrNull()
                    if (okButton == null) {
                        val altNodes = root.findAccessibilityNodeInfosByViewId("com.android.settings:id/btn_positive")
                        okButton = altNodes?.firstOrNull()
                    }
                    if (okButton != null) {
                        okButton.performAction(AccessibilityNodeInfo.ACTION_CLICK)
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
     * Check if pairing failure dialog is showing.
     * vendor: a4 (referenced in i4 dispatch)
     */
    fun isInPairFailDialog(): Boolean {
        return try {
            val root = service.rootInActiveWindow ?: return false
            for (text in SetupConstants.PAIR_FAIL_DIALOG_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (!nodes.isNullOrEmpty()) {
                    Log.d(TAG, "isInPairFailDialog: 找到'$text'")
                    return true
                }
            }
            false
        } catch (e: Exception) {
            Log.e(TAG, "isInPairFailDialog 异常", e)
            false
        }
    }

    /**
     * Handle pairing failure dialog -- dismiss and reset state for retry.
     * vendor: dispatched via pairInPairFailDialog queue entry
     */
    fun handlePairFailDialog(
        processedActions: java.util.concurrent.ConcurrentLinkedQueue<String>,
        pairState: java.util.concurrent.atomic.AtomicReference<PairState>
    ) {
        try {
            val root = service.rootInActiveWindow ?: return
            val dismissTexts = listOf(
                "确定", "关闭", "OK", "Close", "취소", "Cancel",
                "Tutup", "Dong", "Fermer", "Cerrar", "Schliessen", "Закрыть"
            )
            for (text in dismissTexts) {
                val nodes = root.findAccessibilityNodeInfosByText(text)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (node.isClickable) {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "handlePairFailDialog: 点击 '$text' 关闭失败弹窗")
                        processedActions.remove("pairInPairFailDialog")
                        pairState.set(PairState.PAIR_DEPT_UNKNOWN)
                        Log.d(TAG, "handlePairFailDialog: pairState 重置为 UNKNOWN，等待重试")
                        return
                    }
                    val parent = node.parent
                    if (parent?.isClickable == true) {
                        parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "handlePairFailDialog: 点击父节点关闭失败弹窗")
                        processedActions.remove("pairInPairFailDialog")
                        pairState.set(PairState.PAIR_DEPT_UNKNOWN)
                        return
                    }
                }
            }
            Log.w(TAG, "handlePairFailDialog: 未找到可点击的关闭按钮")
        } catch (e: Exception) {
            Log.e(TAG, "handlePairFailDialog 异常", e)
        }
    }

    /**
     * Handle network confirmation dialog -- check "always allow" and click OK.
     * vendor: h2 (line 3218)
     */
    fun handleNetworkConfirmDialog() {
        for (attempt in 0 until 5) {
            try {
                val root = service.rootInActiveWindow ?: continue

                val networkTexts = SetupConstants.NETWORK_CONFIRM_TEXTS
                val found = SystemOptimizeManager.findNodeByTexts(root, networkTexts)
                if (found != null) {
                    Log.i(TAG, "检测到网络确认弹窗")
                    // Try checking "always allow"
                    try {
                        val alwaysAllowTexts = SetupConstants.ALWAYS_ALLOW_TEXTS
                        val alwaysNode = SystemOptimizeManager.findNodeByTexts(root, alwaysAllowTexts)
                        if (alwaysNode != null) {
                            var parent = alwaysNode.parent
                            var checkableNode: AccessibilityNodeInfo? = null
                            for (d in 0 until 5) {
                                if (parent == null) break
                                if (parent.isCheckable) {
                                    checkableNode = parent
                                    break
                                }
                                for (c in 0 until parent.childCount) {
                                    val child = parent.getChild(c)
                                    if (child != null && child.isCheckable) {
                                        checkableNode = child
                                        break
                                    }
                                }
                                if (checkableNode != null) break
                                parent = parent.parent
                            }
                            if (checkableNode != null && checkableNode.isCheckable && !checkableNode.isChecked) {
                                checkableNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                Log.i(TAG, "已勾选网络确认弹窗的始终允许选项")
                            }
                        } else {
                            val checkBox = SystemOptimizeManager.findCheckBoxNode(root)
                            if (checkBox != null && !checkBox.isChecked) {
                                checkBox.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                Log.i(TAG, "已勾选网络确认弹窗的 CheckBox")
                            }
                        }
                    } catch (_: Exception) {}

                    // Click allow button
                    val allowTexts = SetupConstants.ALLOW_BUTTON_TEXTS
                    val allowBtn = SystemOptimizeManager.findNodeByTexts(root, allowTexts)
                    if (allowBtn != null) {
                        allowBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.i(TAG, "已点击网络确认弹窗的允许按钮")
                        SystemOptimizeManager.sleep200(7) // ~1500ms
                        return
                    }
                }
                SystemOptimizeManager.sleep200(1)
            } catch (e: Exception) {
                Log.e(TAG, "handleNetworkConfirmDialog 异常", e)
                return
            }
        }
    }

    /**
     * Recursively find a confirm/allow button in the accessibility tree.
     * vendor: f7 (line ~1250)
     *
     * Matches vendor traversal: checks Button/TextView/clickable nodes.
     */
    fun findConfirmButtonRecursive(node: AccessibilityNodeInfo, texts: List<String>): AccessibilityNodeInfo? {
        val className = node.className?.toString() ?: ""
        val nodeText = node.text?.toString() ?: ""
        if (className.contains("Button", ignoreCase = true)) {
            for (text in texts) {
                if (nodeText.contains(text, ignoreCase = true)) {
                    return node
                }
            }
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val result = findConfirmButtonRecursive(child, texts)
            if (result != null) return result
        }
        return null
    }

    /**
     * Find scrollable view with retry logic.
     * vendor: d6 (line 2401)
     */
    fun findScrollableViewWithRetry(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (root == null) {
            Log.w(TAG, "d0(): root 为 null")
            return null
        }
        return try {
            val attempts = AtomicInteger(0)
            var currentRoot: AccessibilityNodeInfo = root
            var result: AccessibilityNodeInfo? = null

            while (attempts.incrementAndGet() < 10) {
                Log.i(TAG, "d0(): 第 ${attempts.get()} 次尝试查找滚动视图")

                val recyclerView = SystemOptimizeManager.findNodeByClassName(currentRoot, "androidx.recyclerview.widget.RecyclerView")
                if (recyclerView != null && recyclerView.isScrollable) {
                    Log.i(TAG, "d0(): 找到 RecyclerView 滚动视图")
                    return recyclerView
                }
                val listView = SystemOptimizeManager.findNodeByClassName(currentRoot, "android.widget.ListView")
                if (listView != null && listView.isScrollable) {
                    Log.i(TAG, "d0(): 找到 ListView 滚动视图")
                    return listView
                }
                val scrollView = SystemOptimizeManager.findNodeByClassName(currentRoot, "android.widget.ScrollView")
                if (scrollView != null && scrollView.isScrollable) {
                    Log.i(TAG, "d0(): 找到 ScrollView 滚动视图")
                    return scrollView
                }

                result = SystemOptimizeManager.findScrollableNode(currentRoot)
                if (result != null) {
                    Log.i(TAG, "d0(): 找到通用滚动视图 (${result?.className})")
                    return result
                }

                Log.i(TAG, "d0(): 第 ${attempts.get()} 次未找到，等待后重试")
                SystemOptimizeManager.sleep200(5)
                val newRoot = service.rootInActiveWindow
                if (newRoot != null) {
                    currentRoot = newRoot
                } else {
                    Log.w(TAG, "d0(): rootInActiveWindow 返回 null")
                    currentRoot.refresh()
                }
            }

            Log.w(TAG, "d0(): 10次尝试均未找到滚动视图")
            result
        } catch (e: Exception) {
            Log.e(TAG, "d0() 异常", e)
            null
        }
    }
}
