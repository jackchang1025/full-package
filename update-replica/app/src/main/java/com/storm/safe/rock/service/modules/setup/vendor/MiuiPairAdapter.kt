package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SetupUiHelper

/**
 * MiuiPairAdapter -- MIUI/HyperOS-specific ADB pairing behavior.
 *
 * Extracted from:
 *   - DialogHandler.handleMiuiSecurityDialog() (L324-370)
 *   - DialogHandler.isInMiuiSecurityCenter() (L376-378)
 *   - DialogHandler.isInMiuiAdbInputWindow() (L384-387)
 *   - PairFlowOrchestrator.pairInDevOption() SDK<=30 pre-check (L320-329)
 *   - DevOptionsNavigator.isXiaomiNeedsSpecialHandling() SDK>=35 su check
 *
 * JADX: C0360a2.java (method n0 L6053 + b0 L452)
 */
class MiuiPairAdapter(
    private val service: AccessibilityService,
    private val context: Context
) : VendorPairAdapter {

    companion object {
        private const val TAG = "MiuiPairAdapter"
    }

    override val vendorName = "MIUI"

    // ========================================================================
    // Phase 0: Developer Options -- MIUI does not need version info sub-page
    // ========================================================================

    override fun needsVersionInfoPage(): Boolean = false

    // ========================================================================
    // Phase 3: Security Dialog -- MIUI security center detection + handling
    // ========================================================================

    /**
     * Detect MIUI security center package.
     * vendor: n0 (L6053) -- package check against encrypted "com.miui.securitycenter"
     *
     * Returns true if packageName matches MIUI_SECURITY_CENTER_PKG.
     */
    override fun isVendorSecurityDialog(packageName: String?, className: String?): Boolean {
        return packageName == SetupConstants.MIUI_SECURITY_CENTER_PKG
    }

    /**
     * Check if current window is specifically the MIUI ADB input apply activity.
     * vendor: n0 (L6053) -- AdbInputApplyActivity class name check
     */
    fun isInMiuiAdbInputWindow(packageName: String?, className: String?): Boolean {
        if (packageName != SetupConstants.MIUI_SECURITY_CENTER_PKG) return false
        return className != null && className.contains(SetupConstants.MIUI_ADB_INPUT_ACTIVITY)
    }

    /**
     * Handle MIUI security center dialog -- clicks "Next"/"Allow" buttons and waits
     * for "security settings opening" progress text to disappear.
     *
     * vendor: n0 (L6053) -- MIUI ADB install permission flow
     *
     * Steps:
     *   1. Try clicking "Next step" button -> return true
     *   2. Try clicking "Allow" button -> loop up to 20x waiting for
     *      security-opening text to disappear -> return true
     *   3. Try clicking generic dialog accept button -> return true
     *   4. No match -> return false
     */
    override fun handleSecurityDialog(service: AccessibilityService): Boolean {
        try {
            val root = service.rootInActiveWindow ?: return false

            // Step 1: Try "Next step" button
            val nextBtn = findConfirmButtonRecursive(root, SetupConstants.NEXT_STEP_TEXTS)
            if (nextBtn != null) {
                nextBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "handleSecurityDialog: clicked 'Next step' button")
                return true
            }

            // Step 2: Try "Allow" button, then wait for security-opening progress
            val allowBtn = findConfirmButtonRecursive(root, SetupConstants.ALLOW_BUTTON_TEXTS)
            if (allowBtn != null) {
                allowBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "handleSecurityDialog: clicked 'Allow' button, waiting for security opening")
                for (i in 0 until 20) {
                    SystemClock.sleep(1500L)
                    val currentRoot = service.rootInActiveWindow ?: break
                    val openingNode = SetupUiHelper.findNodeByTexts(
                        currentRoot, SetupConstants.SECURITY_SETTING_OPENING_TEXTS
                    )
                    if (openingNode == null) {
                        Log.d(TAG, "handleSecurityDialog: security opening text gone after ${i + 1} iterations")
                        break
                    }
                    Log.d(TAG, "handleSecurityDialog: still opening... iteration ${i + 1}/20")
                }
                return true
            }

            // Step 3: Try generic dialog accept button
            val acceptBtn = findConfirmButtonRecursive(root, SetupConstants.DIALOG_ACCEPT_TEXTS)
            if (acceptBtn != null) {
                acceptBtn.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "handleSecurityDialog: clicked dialog accept button")
                return true
            }

            Log.d(TAG, "handleSecurityDialog: no matching button found")
            return false
        } catch (e: Exception) {
            Log.e(TAG, "handleSecurityDialog error", e)
            return false
        }
    }

    // ========================================================================
    // Phase 1: Before wireless debug click -- SDK<=30 pre-check toggle
    // ========================================================================

    /**
     * MIUI SDK<=30: pre-check the wireless debugging toggle before clicking
     * the wireless debugging entry row.
     *
     * vendor: b0 (L452) -- Xiaomi branch at PairFlowOrchestrator L320-329
     */
    override fun onBeforeWirelessDebugClick(
        service: AccessibilityService,
        clickableNode: AccessibilityNodeInfo
    ) {
        if (Build.VERSION.SDK_INT <= 30) {
            val toggleNode = SetupUiHelper.findToggleNode(clickableNode)
            if (toggleNode != null && !toggleNode.isChecked) {
                toggleNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                Log.d(TAG, "SDK<=30: pre-checked wireless debugging toggle")
                SetupUiHelper.sleep200(5)
            }
        }
    }

    // ========================================================================
    // SDK>=35 special handling -- su binary check
    // ========================================================================

    /**
     * Xiaomi SDK>=35 (Android 15+ / HyperOS 2.0+): check if su binary exists.
     * This affects wireless debugging availability detection.
     *
     * vendor: C0360a2.java L3958-3961
     */
    fun isXiaomiNeedsSpecialHandling(): Boolean {
        if (Build.VERSION.SDK_INT < 35) return false
        return try {
            val suPaths = listOf("/system/bin/su", "/system/xbin/su", "/sbin/su")
            val hasSu = suPaths.any { java.io.File(it).exists() }
            Log.d(TAG, "SDK>=35: su check = $hasSu")
            hasSu
        } catch (e: Exception) {
            Log.w(TAG, "SDK>=35: su check failed: ${e.message}")
            false
        }
    }

    // ========================================================================
    // Private helpers
    // ========================================================================

    /**
     * Recursively find a confirm/allow button in the accessibility tree.
     * vendor: f7 (line ~1250)
     *
     * Matches vendor traversal: checks Button class name + text match.
     */
    private fun findConfirmButtonRecursive(
        node: AccessibilityNodeInfo,
        texts: List<String>
    ): AccessibilityNodeInfo? {
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
}
