package com.storm.safe.rock.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

/**
 * Foreground service for screen recording via MediaProjection.
 *
 * JADX: MediaDisplayService.java (550 LOC).
 *
 * Companion object (C0279a0) provides:
 * - getInstance() — returns current service instance
 * - isProjecting — whether projection is active
 * - stop(context) — stops projection and service
 * - start(context, resultCode, data, quality) — starts projection service
 * - targetFps, quality, scale — configurable parameters
 * - frameIntervalMs — derived from targetFps
 *
 * Instance fields:
 * - isPaused (f52321b2) — pause flag for frame delivery
 * - frameCallback (f52323b4) — callback lambda for frame data
 */
class MediaDisplayService : Service() {

    companion object {
        private const val TAG = "ScreenProjectionSvc"
        private const val CHANNEL_ID = "OFF"
        private const val NOTIFICATION_ID = 10086

        /** Current service instance backing field. JADX: f52305c3 */
        @Volatile
        private var _instance: MediaDisplayService? = null

        /** Whether projection is active. JADX: f52306c4 */
        @Volatile
        @JvmStatic
        var isProjecting: Boolean = false

        /** Target FPS. JADX: f52304c2 */
        @Volatile
        @JvmStatic
        var targetFps: Int = 20

        /** Compression quality. JADX: f52307c5 */
        @Volatile
        @JvmStatic
        var quality: Int = 80

        /** Scale factor. JADX: f52308c6 */
        @Volatile
        @JvmStatic
        var scale: Float = 0.8f

        /** Derived frame interval in ms. JADX: getFrameIntervalMs() */
        @JvmStatic
        fun getFrameIntervalMs(): Long {
            return 1000L / targetFps.coerceIn(5, 30)
        }

        /**
         * Get current service instance.
         * JADX: C0279a0.getInstance() → f52305c3
         */
        @JvmStatic
        fun getInstance(): MediaDisplayService? = _instance

        /**
         * Convenience check — mirrors legacy isRunning().
         */
        @JvmStatic
        fun isRunning(): Boolean = isProjecting || _instance != null

        /**
         * Start projection service.
         * JADX: C0279a0.start(context, resultCode, data, quality)
         */
        @JvmStatic
        fun start(context: Context, resultCode: Int, data: Intent, quality: Int = 80) {
            val intent = Intent(context, MediaDisplayService::class.java).apply {
                putExtra("action", "start")
                putExtra("resultCode", resultCode)
                putExtra("data", data)
                putExtra("quality", quality)
            }
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        /**
         * Stop projection and service.
         * JADX: C0279a0.stop(context) — calls stopImmediate() then sends stop intent.
         */
        @JvmStatic
        fun stop(context: Context) {
            stopImmediate()
            val intent = Intent(context, MediaDisplayService::class.java).apply {
                putExtra("action", "stop")
            }
            context.startService(intent)
        }

        /**
         * Immediately stop projection without sending stop intent.
         * JADX: C0279a0.stopImmediate()
         */
        @JvmStatic
        fun stopImmediate() {
            isProjecting = false
            getInstance()?.frameCallback = null
            // JADX: also clears ImageReader listener and cancels coroutine scope
            // Full cleanup happens in service onDestroy
        }
    }

    /** Pause flag — when true, frames are not delivered. JADX: f52321b2 */
    @Volatile
    var isPaused: Boolean = false

    /** Whether cleanup is in progress. JADX: f52322b3 */
    @Volatile
    var isCleaningUp: Boolean = false
        private set

    /**
     * Frame callback — receives compressed frame data (byte[]).
     * JADX: f52323b4 (Lambda/h10)
     * Set by C0263a5.startMediaProjectionCapture to forward frames via sendFrameData.
     */
    var frameCallback: ((ByteArray) -> Unit)? = null

    override fun onCreate() {
        super.onCreate()
        _instance = this
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getStringExtra("action")
        if (action == "stop") {
            stopSelf()
            return START_NOT_STICKY
        }
        // "start" or other — handled by projection setup (not yet fully replicated)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        frameCallback = null
        isProjecting = false
        _instance = null
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

    /**
     * Deliver frame data via callback.
     * JADX: m211388a1 — invokes f52323b4.invoke(frameData)
     */
    fun deliverFrame(frameData: ByteArray) {
        try {
            val callback = frameCallback
            if (callback != null) {
                callback(frameData)
            } else {
                Log.w(TAG, "⚠️ [发送] onScreenshotCallback 未设置!")
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ [发送] 失败: ${e.message}")
        }
    }
}
