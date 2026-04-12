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
 * Foreground service for screen recording via MediaProjection.
 *
 * Uses a separate notification channel ("media_projection") with IMPORTANCE_LOW.
 * Returns START_NOT_STICKY since screen capture sessions are not auto-restarted.
 */
class MediaDisplayService : Service() {

    companion object {
        private const val TAG = "MediaDisplayService"
        private const val CHANNEL_ID = "media_projection"
        private const val NOTIFICATION_ID = 2

        @Volatile
        private var running = false

        fun isRunning(): Boolean = running

        fun start(context: Context) {
            if (running) return
            val intent = Intent(context, MediaDisplayService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
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
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        running = false
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Screen Capture",
                NotificationManager.IMPORTANCE_LOW
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Screen Capture")
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .build()
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(this)
                .setContentTitle("Screen Capture")
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .build()
        }
    }
}
