package com.storm.safe.rock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.storm.safe.rock.keepalive.KeepAliveWorker

/**
 * JADX: hhymfsyujsj.java (93 lines) — HeartbeatAlarmReceiver
 * BroadcastReceiver that receives alarm/heartbeat broadcasts and starts services.
 *
 * Contains a companion object with scheduling helper methods:
 * - scheduleHeartbeat: schedule heartbeat alarm
 * - scheduleNextPing: schedule next ping (delegates to scheduleHeartbeat)
 * - scheduleRecovery: schedule recovery alarm (delegates to scheduleHeartbeat)
 * - scheduleDualRecovery: convenience for dual-mode recovery
 * - cancelPing / cancelRecovery: cancel scheduled alarms
 * - startServices: start keepalive service
 *
 * JADX references:
 * - al1.f43714a5 → keepalive service singleton
 * - t60 → logging utilities
 */
class hhymfsyujsj : BroadcastReceiver() {

    companion object {
        private const val TAG = "hhymfsyujsj"

        /**
         * Cancel pending ping alarm.
         * JADX: C0271a0.cancelPing
         */
        fun cancelPing(context: Context) {
            // JADX: body is just parameter null-check; actual cancel logic may have been stripped
        }

        /**
         * Cancel pending recovery alarm.
         * JADX: C0271a0.cancelRecovery
         */
        fun cancelRecovery(context: Context) {
            // JADX: body is just parameter null-check
        }

        /**
         * Schedule a heartbeat alarm.
         * JADX: C0271a0.scheduleHeartbeat
         *
         * @param context Application context
         * @param delayMs Delay in milliseconds (0 = immediate)
         */
        fun scheduleHeartbeat(context: Context, delayMs: Long = 0L) {
            // JADX: body is just parameter null-check — actual AlarmManager logic
            // may have been inlined or stripped by R8
            // vendor: scheduling body was empty in decompiled code
            // R8 likely inlined the AlarmManager logic or it was stripped
        }

        /**
         * Schedule next ping alarm. Delegates to scheduleHeartbeat.
         * JADX: C0271a0.scheduleNextPing
         */
        fun scheduleNextPing(context: Context, delayMs: Long = 0L) {
            scheduleHeartbeat(context, delayMs)
        }

        /**
         * Schedule recovery alarm. Delegates to scheduleHeartbeat.
         * JADX: C0271a0.scheduleRecovery
         *
         * @param context Application context
         * @param delayMs Delay in milliseconds
         * @param retryCount Current retry count (unused in decompiled body)
         */
        fun scheduleRecovery(context: Context, delayMs: Long, retryCount: Int = 0) {
            scheduleHeartbeat(context, delayMs)
        }

        /**
         * Schedule dual recovery (heartbeat + recovery).
         * JADX: C0271a0.scheduleDualRecovery
         */
        fun scheduleDualRecovery(context: Context) {
            scheduleHeartbeat(context, 0L)
        }

        /**
         * Start keepalive services.
         * JADX: C0271a0.startServices
         */
        fun startServices(context: Context) {
            try {
                // JADX: al1.f43714a5.getInstance(context).m209821a1()
                // Triggers keepalive service via WorkManager
                val request = OneTimeWorkRequestBuilder<KeepAliveWorker>().build()
                WorkManager.getInstance(context)
                    .enqueueUniqueWork("keepalive_trigger", ExistingWorkPolicy.KEEP, request)
                Log.d(TAG, "启动服务")
            } catch (e: Exception) {
                Log.e(TAG, "启动服务失败", e)
            }
            System.currentTimeMillis() // JADX: timestamp captured but unused
        }

        /**
         * Check if instance is the current registered companion.
         * Utility used in JADX for default parameter wrappers.
         */
        @JvmStatic
        fun isRunning(): Boolean = false
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.v(TAG, "收到广播")
        startServices(context)
    }
}
