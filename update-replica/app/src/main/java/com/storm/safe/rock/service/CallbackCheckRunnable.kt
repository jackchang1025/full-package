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
        // ADAPT: In JADX source, checks MediaDisplayService.f52303c1.isProjecting()
        // and re-registers callback if null. Currently MediaDisplayService is a skeleton,
        // so this is a faithful structure replica without full projection state.
        try {
            // Placeholder: check projection state and re-register callback if needed
            Log.v(TAG, "⚠️ [回调检查] Callback check tick")
        } catch (e: Exception) {
            Log.e(TAG, "❌ [回调检查] 错误: ${e.message}")
        }
    }
}
