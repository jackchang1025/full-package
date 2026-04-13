package com.storm.safe.rock.service

import android.util.Log

/**
 * Handles smart permission loss/recovery events for MyAccessibilityService.
 *
 * JADX reference: service/C0286a6.java (32 LOC)
 * Called by SmartMediaProjectionManager when projection permission is lost or recovered.
 * Dispatches to coroutine-based handlers in MyAccessibilityService.
 */
class SmartPermissionLossHandler(
    val service: MyAccessibilityService
) {

    companion object {
        private const val TAG = "MyAccessibilityService"
    }

    /**
     * Called when SmartMediaProjectionManager detects permission loss.
     *
     * JADX: m211394a0(SmartMediaProjectionManager$LossReason)
     */
    fun onPermissionLost(reason: String) {
        Log.w(TAG, "⚠️ 智能管理器检测到权限丢失: $reason")
        // ADAPT: In JADX source, this launches a coroutine via
        // dqtvuisjd$handleSmartPermissionLoss$1 to handle the loss.
        // Coroutine dispatch deferred to later integration phase.
    }

    /**
     * Called when SmartMediaProjectionManager detects permission recovery.
     *
     * JADX: m211395a1()
     */
    fun onPermissionRecovered() {
        Log.d(TAG, "✅ 智能管理器权限已恢复")
        // ADAPT: In JADX source, this launches a coroutine via
        // dqtvuisjd$handleSmartPermissionRecovery$1 to handle recovery.
        // Coroutine dispatch deferred to later integration phase.
    }
}
