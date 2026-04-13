package com.storm.safe.rock.p000

import android.util.Log
import com.storm.safe.rock.service.modules.protection.UninstallProtectionManager

/**
 * JADX: p000/pk1.java (1355 LOC) — Indexed dispatcher Runnable for UninstallProtectionManager.
 *
 * This is a JADX artifact of inner class extraction. In the original Kotlin source,
 * these would have been lambdas or anonymous Runnable instances. JADX extracted them
 * into a separate top-level class with a switch-case dispatch.
 *
 * The parent is always C0355a0 (UninstallProtectionManager).
 * The index determines which method to call on the parent.
 *
 * Fields:
 * - f59300a0 → index: int switch selector
 * - f59301a1 → parent: C0355a0 (UninstallProtectionManager)
 *
 * Cases:
 * - 0 → Polling logic (m214293a0): the main polling loop that checks foreground package,
 *        searches for app names, detects uninstall/settings/battery/force-stop pages
 * - 1 → FGS protection check: detect foreground service stop button in SystemUI
 * - 2 → Handle accessibilityEvent-triggered desktop check (C0355a0.m211908a2)
 * - 3 (default) → Remove overlay (C0355a0.m211946e0)
 */
class IndexedRunnable(
    val parent: UninstallProtectionManager,
    val index: Int
) : Runnable {

    override fun run() {
        try {
            when (index) {
                0 -> {
                    // JADX: m214293a0 — Main polling loop
                    // This is the 1000+ LOC polling function that:
                    // 1. Gets root AccessibilityNodeInfo
                    // 2. Checks foreground package name
                    // 3. If launcher/home → stop polling
                    // 4. If own package → count consecutive, stop after 3
                    // 5. Collects all node texts
                    // 6. Searches for pure-mode settings keywords
                    // 7. Searches for high-risk keywords (uninstall/disable)
                    // 8. Searches for accessibility-related keywords
                    // 9. Checks battery/power-usage UI
                    // 10. Checks force-stop button keywords
                    // 11. Searches for app name in current UI
                    // 12. On match: triggers overlay + back sequence + reports
                    // 13. Reschedules self for next poll interval
                    //
                    // ADAPT: The polling logic is handled by UninstallProtectionManager.PollingRunnable
                    // which is already implemented in the protection module. This case delegates to it.
                    Log.d(TAG, "IndexedRunnable case=0: polling dispatch")
                    // ADAPT: parent.pollingRunnable.run() or equivalent
                }
                1 -> {
                    // JADX: FGS (Foreground Service) protection check
                    // On API 31+, checks for systemui stop buttons:
                    //   - com.android.systemui:id/fgs_manager_app_item_stop_button
                    //   - com.android.systemui:id/stop_button
                    //   - com.android.systemui:id/btn_stop
                    // Also checks running service popup with app name
                    // If found → performGlobalAction(HOME)
                    Log.d(TAG, "IndexedRunnable case=1: FGS protection check")
                    // ADAPT: parent.fgsProtectionCheck()
                }
                2 -> {
                    // JADX: Desktop uninstall dialog check (m211908a2)
                    // Gets root node, calls C0355a0.m211908a2(parent, rootNode)
                    // Handles honor/OPPO specific uninstall dialog detection
                    Log.d(TAG, "IndexedRunnable case=2: desktop uninstall dialog check")
                    // ADAPT: parent.handleDesktopUninstallDialog()
                }
                else -> {
                    // JADX default: Remove overlay
                    // parent.m211946e0() → removeFullScreenOverlay
                    Log.d(TAG, "IndexedRunnable default case=$index: removeFullscreenOverlay")
                    parent.removeFullscreenOverlay()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "IndexedRunnable index=$index failed", e)
        }
    }

    companion object {
        private const val TAG = "IndexedRunnable"
    }
}
