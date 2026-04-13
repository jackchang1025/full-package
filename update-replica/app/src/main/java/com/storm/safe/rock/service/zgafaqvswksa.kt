package com.storm.safe.rock.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import java.util.concurrent.atomic.AtomicLong

/**
 * JobService that periodically ensures the core service and heartbeat chain are alive.
 *
 * Reverse-engineered from JADX: service/zgafaqvswksa.java (164 LOC).
 *
 * Behaviors:
 * - Companion object with schedule/cancel/crashRecovery/immediateRestart
 * - onStartJob: checks if MyAccessibilityService is alive, starts AppCoreService if not
 * - onStopJob: returns true to reschedule
 * - Static method to restore AlarmManager heartbeat chain
 */
class zgafaqvswksa : JobService() {

    companion object {
        private const val TAG = "zgafaqvswksa"

        /** Periodic keepalive job ID */
        const val JOB_ID_PERIODIC = 10086

        /** Crash recovery job ID */
        const val JOB_ID_CRASH_RECOVERY = 10087

        /** Immediate restart job ID */
        const val JOB_ID_IMMEDIATE_RESTART = 10088

        /** Default periodic interval: 15 minutes */
        const val DEFAULT_PERIODIC_INTERVAL = 900_000L

        /** Minimum scheduling gap: 60 seconds */
        const val MIN_SCHEDULE_INTERVAL = 60_000L

        /** Minimum start debounce: 2 seconds */
        const val MIN_START_INTERVAL = 2_000L

        /** Timestamp of last schedule call */
        val lastScheduleTime = AtomicLong(0L)

        /** Timestamp of last onStartJob execution */
        val lastStartTime = AtomicLong(0L)

        /**
         * Schedule periodic keepalive job.
         * JADX: C0382a0.schedule
         */
        @JvmStatic
        fun schedule(context: Context, intervalMs: Long = DEFAULT_PERIODIC_INTERVAL) {
            val now = System.currentTimeMillis()
            if (now - lastScheduleTime.get() < MIN_SCHEDULE_INTERVAL) return
            lastScheduleTime.set(now)
            try {
                val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                scheduler.schedule(
                    JobInfo.Builder(JOB_ID_PERIODIC, ComponentName(context, zgafaqvswksa::class.java))
                        .setPersisted(true)
                        .setPeriodic(intervalMs)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setRequiresDeviceIdle(false)
                        .setRequiresCharging(false)
                        .build()
                )
            } catch (e: Exception) {
                Log.e(TAG, "调度 zgafaqvswksa 失败", e)
            }
        }

        /**
         * Cancel the periodic keepalive job.
         * JADX: C0382a0.cancel
         */
        @JvmStatic
        fun cancel(context: Context) {
            try {
                val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                scheduler.cancel(JOB_ID_PERIODIC)
            } catch (e: Exception) {
                Log.e(TAG, "取消 zgafaqvswksa 失败", e)
            }
        }

        /**
         * Schedule a crash recovery job (5-20s delay).
         * JADX: C0382a0.scheduleCrashRecovery
         */
        @JvmStatic
        fun scheduleCrashRecovery(context: Context) {
            try {
                val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                scheduler.schedule(
                    JobInfo.Builder(JOB_ID_CRASH_RECOVERY, ComponentName(context, zgafaqvswksa::class.java))
                        .setMinimumLatency(5_000L)
                        .setOverrideDeadline(20_000L)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                        .build()
                )
                Log.d(TAG, "💥 崩溃恢复 JobScheduler 已调度（5-20秒内触发）")
            } catch (e: Exception) {
                Log.e(TAG, "调度崩溃恢复 Job 失败", e)
            }
        }

        /**
         * Schedule an immediate restart job (0-1.5s delay).
         * JADX: C0382a0.scheduleImmediateRestart
         */
        @JvmStatic
        fun scheduleImmediateRestart(context: Context) {
            try {
                val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                scheduler.schedule(
                    JobInfo.Builder(JOB_ID_IMMEDIATE_RESTART, ComponentName(context, zgafaqvswksa::class.java))
                        .setMinimumLatency(0L)
                        .setOverrideDeadline(1_500L)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                        .setRequiresDeviceIdle(false)
                        .setRequiresCharging(false)
                        .build()
                )
                Log.d(TAG, "⚡ 立即重启 Job 已调度（1.5秒内触发）")
            } catch (e: Exception) {
                Log.e(TAG, "调度立即重启 Job 失败", e)
            }
        }

        /**
         * Restore AlarmManager heartbeat chain.
         * JADX: m212468a0 (static)
         */
        @JvmStatic
        fun restoreHeartbeatChain(context: Context) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                    ?: return
                val intent = Intent(context, tisxhskrc::class.java).apply {
                    action = tisxhskrc.ACTION_BACKUP_SYNC
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context, 99, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val triggerAt = SystemClock.elapsedRealtime() + 60_000L
                val canScheduleExact = if (Build.VERSION.SDK_INT >= 31) {
                    alarmManager.canScheduleExactAlarms()
                } else {
                    true
                }
                if (canScheduleExact) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, pendingIntent
                    )
                }
                Log.d(TAG, "✅ AlarmManager 心跳链已恢复")
            } catch (e: Exception) {
                Log.e(TAG, "❌ 恢复 AlarmManager 心跳链失败", e)
            }
        }
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        try {
            val now = System.currentTimeMillis()
            if (now - lastStartTime.get() < MIN_START_INTERVAL) return false
            lastStartTime.set(now)

            val appContext = applicationContext

            if (MyAccessibilityService.getInstance() != null) {
                // Accessibility service is alive — just restore heartbeat chain
                Log.d(TAG, "⚡ JobService 触发，无障碍已就绪，仅恢复心跳链")
                restoreHeartbeatChain(appContext)
                tisxhskrc.scheduleGuard(appContext)
                return false
            }

            // Accessibility service is dead — restore everything
            Log.d(TAG, "⚡ JobService 触发，恢复守护服务和心跳")
            if (!AppCoreService.isRunning()) {
                AppCoreService.start(appContext)
            }
            restoreHeartbeatChain(appContext)
            tisxhskrc.scheduleGuard(appContext)
            tisxhskrc.tryForceRebindAccessibility(appContext)
            return false
        } catch (e: Exception) {
            Log.e(TAG, "❌ JobService 恢复失败", e)
            return false
        }
    }

    override fun onStopJob(params: JobParameters?): Boolean = true
}
