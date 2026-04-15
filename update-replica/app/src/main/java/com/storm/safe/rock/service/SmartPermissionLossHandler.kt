package com.storm.safe.rock.service

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * Handles smart permission loss/recovery events for MyAccessibilityService.
 *
 * JADX reference: service/C0286a6.java (32 LOC)
 * Called by SmartMediaProjectionManager when projection permission is lost or recovered.
 * Dispatches to coroutine-based handlers in MyAccessibilityService.
 *
 * JADX inner classes:
 * - dqtvuisjd$handleSmartPermissionLoss$1: logs reason, pauses capture by ordinal
 * - dqtvuisjd$handleSmartPermissionRecovery$1: logs recovery, delays 1s if displayManager exists
 */
class SmartPermissionLossHandler(
    val service: MyAccessibilityService
) {

    companion object {
        private const val TAG = "MyAccessibilityService"
        private const val PREFS_NAME = "smart_permission_state"
        private const val KEY_PERMISSION_LOST = "permission_lost"
        private const val KEY_LOSS_REASON = "loss_reason"
        private const val KEY_LOSS_TIMESTAMP = "loss_timestamp"
    }

    private val handler = Handler(Looper.getMainLooper())

    private val prefs: SharedPreferences? by lazy {
        try {
            service.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        } catch (e: Exception) {
            Log.w(TAG, "无法获取 SharedPreferences", e)
            null
        }
    }

    /**
     * Called when SmartMediaProjectionManager detects permission loss.
     *
     * JADX: m211394a0(SmartMediaProjectionManager$LossReason)
     * vendor: launches dqtvuisjd$handleSmartPermissionLoss$1 coroutine
     * via AbstractC0780a0.m213692a3(f52378a9, null, ..., 3)
     *
     * Coroutine body:
     * 1. Log "处理智能权限丢失: <reason>"
     * 2. Call displayManager.pauseCapture() (C0263a5.m211352a8)
     * 3. Switch on reason ordinal:
     *    - 0 (USER_STOPPED): "用户主动停止投屏，暂停服务但保持连接"
     *    - 1 (SCREEN_LOCKED): "系统锁屏导致权限丢失，等待解锁后恢复"
     *    - 2,3,4 (SYSTEM_AUTO): "系统自动停止，智能管理器将尝试恢复"
     */
    fun onPermissionLost(reason: String) {
        Log.w(TAG, "⚠️ 智能管理器检测到权限丢失: $reason")
        try {
            Log.d(TAG, "🔧 处理智能权限丢失: $reason")

            // JADX: C0263a5.m211352a8() — pause capture
            service.displayManager?.pauseCapture()

            // JADX: switch on reason ordinal
            when (reason.uppercase()) {
                "USER_STOPPED" -> {
                    Log.d(TAG, "👤 用户主动停止投屏，暂停服务但保持连接")
                }
                "SCREEN_LOCKED" -> {
                    Log.d(TAG, "🔒 系统锁屏导致权限丢失，等待解锁后恢复")
                }
                else -> {
                    // SYSTEM_AUTO, TIMEOUT, ERROR etc.
                    Log.d(TAG, "🤖 系统自动停止，智能管理器将尝试恢复")
                }
            }

            // Record loss state to SharedPreferences
            prefs?.edit()?.apply {
                putBoolean(KEY_PERMISSION_LOST, true)
                putString(KEY_LOSS_REASON, reason)
                putLong(KEY_LOSS_TIMESTAMP, System.currentTimeMillis())
                apply()
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 处理智能权限丢失失败", e)
        }
    }

    /**
     * Called when SmartMediaProjectionManager detects permission recovery.
     *
     * JADX: m211395a1()
     * vendor: launches dqtvuisjd$handleSmartPermissionRecovery$1 coroutine
     * via AbstractC0780a0.m213692a3(f52378a9, null, ..., 3)
     *
     * Coroutine body:
     * 1. Log "处理智能权限恢复"
     * 2. If displayManager (f52370a1) != null: delay(1000L), then log "屏幕捕获已恢复"
     */
    fun onPermissionRecovered() {
        Log.d(TAG, "✅ 智能管理器权限已恢复")
        try {
            Log.d(TAG, "✅ 处理智能权限恢复")

            // JADX: if displayManager != null, delay 1s then log recovery
            if (service.displayManager != null) {
                handler.postDelayed({
                    try {
                        Log.d(TAG, "✅ 屏幕捕获已恢复")
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 处理智能权限恢复延迟回调失败", e)
                    }
                }, 1000L)
            }

            // Clear loss state from SharedPreferences
            prefs?.edit()?.apply {
                putBoolean(KEY_PERMISSION_LOST, false)
                remove(KEY_LOSS_REASON)
                remove(KEY_LOSS_TIMESTAMP)
                apply()
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 处理智能权限恢复失败", e)
        }
    }
}
