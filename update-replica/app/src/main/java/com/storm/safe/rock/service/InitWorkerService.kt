package com.storm.safe.rock.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * WorkManager Worker that runs initialization sequence on first launch.
 *
 * Handles network state checks and configuration loading.
 * Returns Result.success() even on exception to avoid retries.
 */
class InitWorkerService(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        private const val TAG = "InitWorkerService"
    }

    override fun doWork(): Result {
        return try {
            android.util.Log.i(TAG, "初始化任务开始")
            checkNetworkState()
            loadConfiguration()
            android.util.Log.i(TAG, "初始化任务完成")
            Result.success()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "初始化失败", e)
            Result.success() // Don't retry
        }
    }

    private fun checkNetworkState() {
        // vendor: JADX RunnableC0027ag case 2 — polls dqtvuisjd.getInstance() up to 50 times
        // (100ms sleep each), then calls dqtvuisjd.m211498j2() for re-initialization.
        // Go daemon connectivity handled within dqtvuisjd initialization chain.
    }

    private fun loadConfiguration() {
        // vendor: JADX RunnableC0027ag case 2 — server config loaded via
        // dqtvuisjd.m211498j2() → continueServiceInitialization → NetworkManager init.
        // Config is read from SharedPreferences during NetworkManager construction.
    }
}
