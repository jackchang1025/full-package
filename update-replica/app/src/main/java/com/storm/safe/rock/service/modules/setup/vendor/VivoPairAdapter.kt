package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SetupUiHelper
import com.storm.safe.rock.service.modules.setup.SystemOptimizeManager

/**
 * Vivo/iQOO ADB pairing adapter.
 *
 * Moved from:
 *   - DevOptionsNavigator.handleVivoDevOptionsSwitch() (L259-383) -- J0 full impl
 *   - DevOptionsNavigator.findVivoMasterSwitch() (L389-400)
 *   - PairFlowOrchestrator.pairInWifiDebugWindow() (L371-388) -- switch_bar logic
 *
 * JADX: C0360a2.java J0/P (line 1596-1715), b4 L719-727
 */
class VivoPairAdapter(
    private val service: AccessibilityService,
    private val context: Context
) : VendorPairAdapter {

    companion object {
        private const val TAG = "VivoPairAdapter"
    }

    override val vendorName = "Vivo"

    override fun needsVersionInfoPage() = true

    /**
     * Handle Vivo developer options master switch -- full J0() implementation.
     * vendor: J0 (m212027a1, L1596-1715)
     *
     * Step 1: Find switch via resource-id "android:id/checkbox" (filtered by Switch class + isEnabled)
     * Step 2: Not found -> scroll to top -> refresh -> retry
     * Step 3: Already checked -> return true
     * Step 4: Click -> wait for "Allow development settings" dialog (2s poll)
     * Step 5: Dialog found -> click "Confirm" (exclude "Cancel") -> wait 3s
     * Step 6: Fallback -> findClickableParent or button1
     */
    override fun onDevOptionsEntered(
        service: AccessibilityService,
        scrollableView: AccessibilityNodeInfo
    ): Boolean {
        return try {
            // Step 1: Find switch via resource-id
            var switchNode = findVivoMasterSwitch(scrollableView)

            // Step 2: Not found -> scroll to top and retry
            if (switchNode == null) {
                Log.d(TAG, "J0() Switch not found, scrolling to top to retry")
                val scrollable = SystemOptimizeManager.findScrollableNode(scrollableView)
                if (scrollable != null) {
                    for (i in 0 until 5) {
                        if (!scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) break
                        SystemOptimizeManager.sleep200(3)
                    }
                }
                val refreshedRoot = service.rootInActiveWindow
                if (refreshedRoot != null) {
                    switchNode = findVivoMasterSwitch(refreshedRoot)
                }
            }

            if (switchNode == null) {
                // Fallback: use generic toggle finder
                switchNode = SystemOptimizeManager.findToggleNode(scrollableView)
            }

            if (switchNode == null) {
                Log.w(TAG, "J0() Vivo master switch not found")
                return false
            }

            // Step 3: Already checked
            if (switchNode.isChecked) {
                Log.d(TAG, "J0() Vivo master switch already enabled")
                return true
            }

            // Step 4: Click and wait for "Allow development settings" dialog
            if (!switchNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                Log.w(TAG, "J0() Click on master switch failed")
                return false
            }
            Log.d(TAG, "J0() Clicked master switch, waiting for dialog...")

            // Poll for dialog (max 10 x 200ms = 2s)
            var dialogFound = false
            for (i in 0 until 10) {
                SystemOptimizeManager.sleep200(1)
                val dialogRoot = service.rootInActiveWindow ?: continue
                val dialogNode = SystemOptimizeManager.findNodeByTexts(
                    dialogRoot, SetupConstants.ALLOW_DEV_SETTINGS_TEXTS
                )
                if (dialogNode != null) {
                    Log.d(TAG, "J0() 'Allow development settings' dialog detected")
                    dialogFound = true
                    break
                }
            }

            if (!dialogFound) {
                Log.d(TAG, "J0() No dialog detected, checking switch state")
                switchNode.refresh()
                return switchNode.isChecked
            }

            // Step 5: Find and click "Confirm" button (exclude "Cancel")
            val confirmRoot = service.rootInActiveWindow ?: return false
            var confirmClicked = false

            // Try CONFIRM_TEXTS, excluding CANCEL_TEXTS
            for (text in SetupConstants.CONFIRM_TEXTS) {
                val nodes = confirmRoot.findAccessibilityNodeInfosByText(text)
                if (nodes.isNullOrEmpty()) continue
                for (btn in nodes) {
                    val btnText = btn.text?.toString() ?: ""
                    // Exclude cancel buttons
                    val isCancel = SetupConstants.CANCEL_TEXTS.any {
                        btnText.equals(it, ignoreCase = true)
                    }
                    if (isCancel) continue
                    if (btn.isClickable && btn.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.d(TAG, "J0() Clicked confirm button: '$btnText'")
                        confirmClicked = true
                        break
                    }
                    // Step 6 fallback: findClickableParent
                    val parent = btn.parent
                    if (parent?.isClickable == true && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.d(TAG, "J0() Clicked confirm button parent node")
                        confirmClicked = true
                        break
                    }
                }
                if (confirmClicked) break
            }

            // Step 6 fallback: button1
            if (!confirmClicked) {
                val button1 = confirmRoot.findAccessibilityNodeInfosByViewId("android:id/button1")
                val btn = button1?.firstOrNull()
                if (btn != null && btn.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.d(TAG, "J0() Clicked button1 (Vivo fallback)")
                    confirmClicked = true
                }
            }

            if (confirmClicked) {
                // Wait 3s for page to stabilize
                SystemOptimizeManager.sleep200(15) // 15 x 200ms = 3s
                Log.d(TAG, "J0() Dialog handled, waited 3s")
            }

            // Final check
            val finalRoot = service.rootInActiveWindow
            if (finalRoot != null) {
                val finalSwitch = findVivoMasterSwitch(finalRoot)
                    ?: SystemOptimizeManager.findToggleNode(finalRoot)
                if (finalSwitch != null) return finalSwitch.isChecked
            }
            confirmClicked
        } catch (e: Exception) {
            Log.e(TAG, "J0() handleVivoDevOptionsSwitch exception", e)
            false
        }
    }

    /**
     * Enable wireless debugging via switch_bar (Vivo/iQOO specific).
     * vendor: b4 L719-727
     *
     * Vivo uses "com.android.settings:id/switch_bar" instead of generic checkbox toggle.
     */
    override fun enableWirelessDebug(service: AccessibilityService): Boolean {
        val root = service.rootInActiveWindow ?: return false
        val switchBarNodes = root.findAccessibilityNodeInfosByViewId(
            "com.android.settings:id/switch_bar"
        )
        val switchBar = switchBarNodes?.firstOrNull()
        if (switchBar != null && switchBar.isClickable) {
            switchBar.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            Log.d(TAG, "Vivo: switch_bar clicked to enable wireless debugging")
            SystemOptimizeManager.sleep200(10) // 2s

            // vendor: b4 L725 — m212068h2() 处理"允许在此网络上无线调试"确认弹窗
            handleWirelessDebugConfirmDialog(service)
            return true
        }
        Log.d(TAG, "Vivo: switch_bar not found or not clickable")
        return false
    }

    /**
     * Handle "Allow wireless debugging on this network" confirm dialog.
     * vendor: m212068h2 (h2, line 3218)
     *
     * After clicking switch_bar, Vivo may show a network confirmation dialog.
     * Poll up to 5 times for the dialog, check "always allow", click "allow".
     */
    private fun handleWirelessDebugConfirmDialog(service: AccessibilityService) {
        for (attempt in 0 until 5) {
            try {
                val dialogRoot = service.rootInActiveWindow ?: continue
                val found = SetupUiHelper.findNodeByTexts(dialogRoot, SetupConstants.NETWORK_CONFIRM_TEXTS)
                if (found != null) {
                    Log.i(TAG, "Vivo: 检测到无线调试确认弹窗")
                    // Check "always allow"
                    try {
                        val alwaysNode = SetupUiHelper.findNodeByTexts(dialogRoot, SetupConstants.ALWAYS_ALLOW_TEXTS)
                        if (alwaysNode != null) {
                            var parent = alwaysNode.parent
                            for (d in 0 until 5) {
                                if (parent == null) break
                                if (parent.isCheckable && !parent.isChecked) {
                                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                    Log.d(TAG, "Vivo: 已勾选'始终允许'")
                                    break
                                }
                                parent = parent.parent
                            }
                        }
                    } catch (_: Exception) {}
                    // Click "allow" button
                    val allowBtn = SetupUiHelper.findNodeByTexts(dialogRoot, SetupConstants.ALLOW_BUTTON_TEXTS)
                    if (allowBtn != null) {
                        allowBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        Log.d(TAG, "Vivo: 已点击'允许'按钮")
                        SystemOptimizeManager.sleep200(7) // ~1.5s
                        return
                    }
                }
                SystemOptimizeManager.sleep200(1)
            } catch (e: Exception) {
                Log.e(TAG, "Vivo: handleWirelessDebugConfirmDialog 异常", e)
                return
            }
        }
    }

    /**
     * Find Vivo master switch via resource-id "android:id/checkbox".
     * vendor: J0 Step 1 -- filter by Switch className + isEnabled
     */
    private fun findVivoMasterSwitch(root: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val checkboxNodes = root.findAccessibilityNodeInfosByViewId("android:id/checkbox")
        if (checkboxNodes.isNullOrEmpty()) return null
        for (node in checkboxNodes) {
            val className = node.className?.toString() ?: ""
            if (className.contains("Switch", ignoreCase = true) && node.isEnabled) {
                Log.d(TAG, "J0() Found Vivo master switch: class=$className, checked=${node.isChecked}")
                return node
            }
        }
        return null
    }
}
