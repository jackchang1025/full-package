package com.storm.safe.rock.service.delegates

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Build
import android.util.Log

/**
 * Delegate that wraps gesture-dispatch operations (tap, swipe, long-press).
 * Extracted from MyAccessibilityService to reduce class size.
 *
 * JADX methods: j1 (performTap), j3 (performSwipe), j2 (performLongPress)
 */
class GestureController(private val service: AccessibilityService) {

    companion object {
        private const val TAG = "GestureController"
    }

    /**
     * Perform tap gesture at given coordinates.
     * JADX method: m211497j1 (j1), line 7070
     */
    fun performTap(x: Float, y: Float) {
        Log.d(TAG, "远程点击: ($x, $y)")
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Log.w(TAG, "⚠️ API < 24, dispatchGesture 不可用")
                return
            }
            val path = Path()
            path.moveTo(x, y)
            val stroke = GestureDescription.StrokeDescription(path, 0L, 100L)
            val gesture = GestureDescription.Builder()
                .addStroke(stroke)
                .build()
            service.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            Log.e(TAG, "❌ performTap 失败", e)
        }
    }

    /**
     * Perform swipe gesture between two points.
     * JADX method: m211499j3 (j3), line 7146
     */
    fun performSwipe(startX: Float, startY: Float, endX: Float, endY: Float, durationMs: Long = 300L) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                Log.w(TAG, "⚠️ API < 24, dispatchGesture 不可用")
                return
            }
            val path = Path()
            path.moveTo(startX, startY)
            path.lineTo(endX, endY)
            val stroke = GestureDescription.StrokeDescription(path, 0L, durationMs)
            val gesture = GestureDescription.Builder()
                .addStroke(stroke)
                .build()
            service.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            Log.e(TAG, "❌ performSwipe 失败", e)
        }
    }

    /**
     * Perform long press gesture at given coordinates.
     * JADX method: m211498j2 (j2), line 7106
     */
    fun performLongPress(x: Float, y: Float) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return
            val path = Path()
            path.moveTo(x, y)
            val stroke = GestureDescription.StrokeDescription(path, 0L, 1000L)
            val gesture = GestureDescription.Builder()
                .addStroke(stroke)
                .build()
            service.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            Log.e(TAG, "❌ performLongPress 失败", e)
        }
    }
}
