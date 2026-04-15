package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import kotlinx.coroutines.delay

/**
 * Dispatch real tap gestures via GestureDescription.
 *
 * Replaces the broken pattern `performSwipeGesture(x, y, x, y)` which produces
 * a zero-distance gesture that MIUI silently drops. Adds 1px jitter so the path
 * has non-zero length (passes ROM's "real gesture" validation) while still
 * appearing to the target as a tap.
 *
 * Usage:
 *   val ok = GestureTapHelper.performTap(service, x = 900f, y = 678f)
 */
object GestureTapHelper {
    private const val TAG = "GestureTapHelper"
    const val TAP_DURATION_MS: Long = 50L
    const val TAP_START_DELAY_MS: Long = 0L
    private const val JITTER_PX: Float = 1f

    /**
     * Build a Path for a tap gesture at (x, y) with 1px jitter to satisfy ROMs
     * that reject zero-distance gestures.
     */
    fun buildTapPath(fromX: Float, fromY: Float): Path {
        val path = Path()
        path.moveTo(fromX, fromY)
        path.lineTo(fromX + JITTER_PX, fromY + JITTER_PX)
        return path
    }

    /**
     * Dispatch a tap gesture at screen coordinates (x, y).
     * Returns true on completion, false on cancellation or timeout.
     */
    suspend fun performTap(service: AccessibilityService, x: Float, y: Float): Boolean {
        return try {
            val path = buildTapPath(x, y)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, TAP_START_DELAY_MS, TAP_DURATION_MS))
                .build()

            var completed = false
            var cancelled = false
            val callback = object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) { completed = true }
                override fun onCancelled(gestureDescription: GestureDescription?) { cancelled = true }
            }
            if (!service.dispatchGesture(gesture, callback, null)) {
                Log.w(TAG, "⚠️ dispatchGesture returned false for tap ($x,$y)")
                return false
            }
            var elapsed = 0
            while (!completed && !cancelled && elapsed < 600) {
                delay(50)
                elapsed += 50
            }
            if (cancelled) Log.w(TAG, "⚠️ tap cancelled at ($x,$y)")
            completed
        } catch (e: Exception) {
            Log.e(TAG, "❌ performTap failed at ($x,$y)", e)
            false
        }
    }
}
