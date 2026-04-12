package com.storm.safe.rock.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder

/**
 * Foreground Service that keeps the app alive.
 *
 * Reverse-engineered from JADX reference `AppCoreService.java`.
 * Key behaviors:
 * - Companion object with isRunning() and start(context)
 * - onCreate() creates foreground notification and sets running = true
 * - onStartCommand() returns START_STICKY for auto-restart
 * - Creates notification channel "core_service" with IMPORTANCE_MIN
 */
class AppCoreService : Service() {

    companion object {
        private const val TAG = "AppCoreService"
        private const val CHANNEL_ID = "core_service"
        private const val NOTIFICATION_ID = 1

        @Volatile
        private var running = false

        fun isRunning(): Boolean = running

        fun start(context: Context) {
            if (running) return
            try {
                val intent = Intent(context, AppCoreService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            } catch (e: Exception) {
                android.util.Log.e(TAG, "启动失败", e)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        running = true
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        running = false
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Core Service",
                NotificationManager.IMPORTANCE_MIN
            ).apply { setShowBadge(false) }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .build()
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this)
                .setContentTitle("")
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .build()
        }
    }
}
