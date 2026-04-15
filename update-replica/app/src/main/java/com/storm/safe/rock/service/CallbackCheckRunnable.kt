package com.storm.safe.rock.service

import android.util.Log

/**
 * Periodic runnable that checks if the MediaProjection callback is still set.
 *
 * JADX reference: service/RunnableC0282a2.java (37 LOC)
 * Runs every 500ms while projection is active. If the callback is null,
 * attempts to re-register it via MediaDisplayService.
 */
class CallbackCheckRunnable(
    val service: MediaDisplayService
) : Runnable {

    companion object {
        private const val TAG = "ScreenProjectionSvc"
        const val CHECK_INTERVAL_MS = 500L
    }

    override fun run() {
        // vendor: JADX RunnableC0282a2.run →
        // 1. Check MediaDisplayService.f52303c1.isProjecting()
        // 2. If f52323b4 (callback) is null → call m211393a6() to re-register callback
        // 3. Reschedule via handler.postDelayed(this, 500)
        // MediaDisplayService is a skeleton; projection state not yet exposed.
        try {
            Log.v(TAG, "⚠️ [回调检查] Callback check tick")
        } catch (e: Exception) {
            Log.e(TAG, "❌ [回调检查] 错误: ${e.message}")
        }
    }
}
