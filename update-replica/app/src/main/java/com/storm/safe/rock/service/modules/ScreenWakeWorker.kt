package com.storm.safe.rock.service.modules

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * WorkManager worker that wakes the screen.
 *
 * Reverse-engineered from JADX: ScreenWakeWorker.java (32 lines).
 * Vendor: extends Worker, doWork() calls wakeScreen utility, returns SUCCESS or RETRY.
 */
class ScreenWakeWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    companion object {
        private const val TAG = "ScreenWakeWorker"
    }

    override fun doWork(): Result {
        // vendor: mj1.wakeScreen$default(context, false, 2, null) via nj1.f58634a4 singleton
        // We use a simplified wake approach via PowerManager
        return try {
            val wakeResult = wakeScreen(applicationContext)
            if (wakeResult) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "doWork failed", e)
            Result.retry()
        }
    }

    /**
     * Attempts to wake the screen using PowerManager WakeLock.
     * Mirrors vendor mj1.wakeScreen$default logic.
     */
    private fun wakeScreen(context: Context): Boolean {
        return try {
            val pm = context.getSystemService(Context.POWER_SERVICE) as? android.os.PowerManager
                ?: return false
            if (!pm.isInteractive) {
                @Suppress("DEPRECATION")
                val wakeLock = pm.newWakeLock(
                    android.os.PowerManager.FULL_WAKE_LOCK or
                        android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        android.os.PowerManager.ON_AFTER_RELEASE,
                    "ScreenWakeWorker:WakeLock"
                )
                wakeLock.acquire(5_000L)
                wakeLock.release()
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "wakeScreen failed", e)
            false
        }
    }
}
