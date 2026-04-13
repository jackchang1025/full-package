package com.storm.safe.rock.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.storm.safe.rock.keepalive.KeepAliveWorker

/**
 * JADX: BackgroundTaskActivity.java (58 lines)
 * Activity that triggers keepalive service, then finishes.
 *
 * JADX references:
 * - zk1 / al1.f43714a5 → keepalive service singleton
 * - t60.m214714d6 → Log.d
 * - t60.m214705c6 → Log.e with exception
 */
class BackgroundTaskActivity : Activity() {

    companion object {
        private const val TAG = "BackgroundTaskActivity"
    }

    /**
     * Trigger keepalive service.
     * JADX: m211183a0()
     */
    private fun triggerKeepalive() {
        try {
            val context = applicationContext
            // JADX: al1.f43714a5.getInstance(context).m209821a1()
            // Triggers keepalive service via WorkManager
            val request = OneTimeWorkRequestBuilder<KeepAliveWorker>().build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork("keepalive_trigger", ExistingWorkPolicy.KEEP, request)
            Log.d(TAG, "保活服务触发")
        } catch (e: Exception) {
            Log.e(TAG, "启动保活服务失败", e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "BackgroundTaskActivity.onCreate - 保活触发")
        triggerKeepalive()
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "BackgroundTaskActivity.onNewIntent - 保活触发")
        triggerKeepalive()
        finish()
    }
}
