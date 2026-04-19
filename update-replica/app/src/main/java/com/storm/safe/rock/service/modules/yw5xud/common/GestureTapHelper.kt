package com.storm.safe.rock.service.modules.yw5xud.common

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import kotlinx.coroutines.delay

/**
 * Dispatch real tap gestures via GestureDescription.
 *
 * Vendor duration map:
 *  - ALL_FILES (C0367a4.m212277e2): 50ms
 *  - WRITE_SETTINGS (C0327b2.m211753f9): 100ms
 *  - 10 候选坐标 (C0327b2.m211716a5): 100ms
 *
 * 1px jitter keeps path non-zero (MIUI silently drops zero-distance gestures).
 */
object GestureTapHelper {
    private const val TAG = "GestureTapHelper"
    const val TAP_DURATION_MS_SHORT: Long = 50L   // vendor ALL_FILES default
    const val TAP_DURATION_MS_LONG: Long = 100L   // vendor WRITE_SETTINGS default
    const val TAP_START_DELAY_MS: Long = 0L
    private const val JITTER_PX: Float = 1f

    fun buildTapPath(fromX: Float, fromY: Float): Path {
        return Path().apply {
            moveTo(fromX, fromY)
            lineTo(fromX + JITTER_PX, fromY + JITTER_PX)
        }
    }

    /**
     * Dispatch a tap gesture at (x, y).
     * @param durationMs 持续时间。ALL_FILES 用 50L，WRITE_SETTINGS 用 100L。默认 50L 兼容旧调用。
     */
    suspend fun performTap(
        service: AccessibilityService,
        x: Float,
        y: Float,
        durationMs: Long = TAP_DURATION_MS_SHORT
    ): Boolean {
        return try {
            val path = buildTapPath(x, y)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, TAP_START_DELAY_MS, durationMs))
                .build()

            var completed = false
            var cancelled = false
            val callback = object : AccessibilityService.GestureResultCallback() {
                override fun onCompleted(g: GestureDescription?) { completed = true }
                override fun onCancelled(g: GestureDescription?) { cancelled = true }
            }
            if (!service.dispatchGesture(gesture, callback, null)) {
                Log.w(TAG, "⚠️ dispatchGesture returned false for tap ($x,$y) dur=${durationMs}ms")
                return false
            }
            val deadline = System.currentTimeMillis() + 600L
            while (!completed && !cancelled && System.currentTimeMillis() < deadline) {
                delay(50)
            }
            if (cancelled) Log.w(TAG, "⚠️ tap cancelled at ($x,$y) dur=${durationMs}ms")
            completed
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "❌ performTap failed at ($x,$y) dur=${durationMs}ms", e)
            false
        }
    }
}
