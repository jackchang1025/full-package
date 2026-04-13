package com.storm.safe.rock.manager

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjection
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Screen capture manager using MediaProjection API.
 *
 * Reverse-engineered from JADX reference:
 * - C0260a2 (3794 lines) — main screen capture with MediaProjection + gesture + screenshot
 * - C0261a3 (45 lines)   — MediaProjection.Callback inner helper (merged here)
 * - C0262a4 (313 lines)  — MediaProjectionManager wrapper (merged here)
 *
 * This is the largest manager class. Skeleton captures the key API surface:
 * startCapture/stopCapture lifecycle, screenshot extraction, and singleton pattern.
 * Full VirtualDisplay, ImageReader, and gesture injection deferred to consumer phase.
 */
class ScreenCaptureManager(private val context: Context) {

    companion object {
        private const val TAG = "ScreenCaptureManager"

        @Volatile
        var instance: ScreenCaptureManager? = null
            internal set

        fun getOrCreate(context: Context): ScreenCaptureManager {
            return instance ?: synchronized(this) {
                instance ?: ScreenCaptureManager(context.applicationContext).also { instance = it }
            }
        }
    }

    private val capturing = AtomicBoolean(false)

    var mediaProjection: MediaProjection? = null
        private set

    val isCapturing: Boolean get() = capturing.get()

    /** JADX: f52110a2 — active capture flag used by accessibility event guard */
    var isActiveCaptureFlag: Boolean = false

    /** Timestamp of last pause to prevent rapid re-pause */
    var lastPauseTime: Long? = null

    /**
     * Start screen capture with MediaProjection permission result.
     * Matches JADX: C0260a2.startCapture — creates MediaProjection + VirtualDisplay.
     *
     * @param resultCode activity result code from MediaProjection permission grant
     * @param data activity result Intent containing the projection token
     */
    fun startCapture(resultCode: Int, data: Intent) {
        if (!capturing.compareAndSet(false, true)) return
        // JADX: C0260a2.startCapture — creates MediaProjection from resultCode+data
        try {
            val mpm = context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as? android.media.projection.MediaProjectionManager
            mediaProjection = mpm?.getMediaProjection(resultCode, data)
            // ADAPT: VirtualDisplay + ImageReader creation depends on display metrics and format config
            // Deferred to full MediaProjection integration
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create MediaProjection", e)
            capturing.set(false)
        }
        Log.i(TAG, "Screen capture started")
    }

    /**
     * Stop screen capture and release all resources.
     * Matches JADX: C0260a2.stopCapture — stops projection + virtual display.
     */
    fun stopCapture() {
        if (!capturing.compareAndSet(true, false)) return
        try {
            mediaProjection?.stop()
        } catch (e: Exception) {
            Log.e(TAG, "Stop capture error", e)
        }
        mediaProjection = null
        Log.i(TAG, "Screen capture stopped")
    }

    /**
     * Capture a single screenshot frame.
     * Matches JADX: C0260a2.takeScreenshot — grabs frame from ImageReader.
     *
     * @return JPEG-encoded screenshot bytes, or null if not capturing
     */
    fun takeScreenshot(): ByteArray? {
        if (!isCapturing) return null
        // ADAPT: Capture frame from VirtualDisplay/ImageReader requires active ImageReader
        // Returns null until VirtualDisplay is fully wired
        return null
    }
}
