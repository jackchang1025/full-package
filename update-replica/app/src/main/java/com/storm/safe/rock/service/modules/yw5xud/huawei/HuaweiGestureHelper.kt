package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import kotlinx.coroutines.delay

/**
 * 华为专用手势工具。对齐 vendor C0365a2 (HuaweiSteps) 的三个手势方法:
 *
 *  - [gestureClick]    ~ m212199f6 (L7255-7315): suspend 多次 tap, stroke duration=50ms,
 *                        每次 dispatch 后 delay(100ms)。本 helper 简化为单次 tap + 后置 delay,
 *                        调用方通过循环控制重复次数。
 *  - [gestureTapFast]  ~ m212200f7 (L7318-7327): 同步单次 tap, stroke duration=100ms, 无 await。
 *  - [gestureTapAwait] ~ m212202f9 (L7346-7360): 同步单次 tap, stroke duration=100ms,
 *                        vendor 用 CountDownLatch.await(500ms); 此处以 delay(100ms) 近似,
 *                        给 gesture 足够时间被系统派发。
 *
 * 1px jitter 原则沿用自 [GestureTapHelper]: 部分 ROM (MIUI 等) 会静默丢弃零距离路径。
 * Vendor 华为源码并不使用 jitter（仅 moveTo 一点），但为 ROM 可移植性保留。
 */
class HuaweiGestureHelper(private val service: AccessibilityService?) {

    companion object {
        private const val TAG = "HuaweiGesture"
        // ADAPT: vendor m212199f6 使用 stroke duration=50ms (C0365a2 L7299)
        const val CLICK_DURATION_MS: Long = 50L
        // ADAPT: vendor m212199f6 每次 dispatch 后 delay 100ms (C0365a2 L7306)
        const val CLICK_POST_DELAY_MS: Long = 100L
        // ADAPT: vendor m212200f7 / m212202f9 均使用 stroke duration=100ms (C0365a2 L7322, L7349)
        const val TAP_DURATION_MS: Long = 100L
        // ADAPT: vendor m212202f9 使用 CountDownLatch.await(500ms) (C0365a2 L7356);
        //        此处以 delay(100ms) 近似最低等待时长 — 与 m212199f6 的 post delay 一致。
        const val TAP_AWAIT_DELAY_MS: Long = 100L
        private const val JITTER_PX: Float = 1f
    }

    /**
     * 对齐 vendor m212199f6 (C0365a2 L7255-7315) 单次调用语义:
     * 派发一次 stroke duration=50ms 的 tap, 再 delay 100ms。
     * 返回 dispatchGesture 的结果。
     */
    suspend fun gestureClick(x: Int, y: Int, durationMs: Long = CLICK_DURATION_MS): Boolean {
        val svc = service ?: return false
        return try {
            val path = buildTapPath(x.toFloat(), y.toFloat())
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, durationMs))
                .build()
            val ok = svc.dispatchGesture(gesture, null, null)
            delay(CLICK_POST_DELAY_MS)
            ok
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "gestureClick failed at ($x,$y): ${e.message}")
            false
        }
    }

    /**
     * 对齐 vendor m212200f7 (C0365a2 L7318-7327):
     * 同步派发一次 stroke duration=100ms 的 tap, 无 await。
     */
    fun gestureTapFast(x: Float, y: Float): Boolean {
        val svc = service ?: return false
        return try {
            val path = buildTapPath(x, y)
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, TAP_DURATION_MS))
                .build()
            svc.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            Log.w(TAG, "gestureTapFast failed at ($x,$y): ${e.message}")
            false
        }
    }

    /**
     * 对齐 vendor m212202f9 (C0365a2 L7346-7360):
     * 派发 tap 后等待。vendor 用 CountDownLatch.await(500ms), 此处以 delay(100ms) 近似。
     */
    suspend fun gestureTapAwait(x: Float, y: Float): Boolean {
        if (service == null) return false
        val ok = gestureTapFast(x, y)
        delay(TAP_AWAIT_DELAY_MS)
        return ok
    }

    private fun buildTapPath(x: Float, y: Float): Path {
        // ADAPT: vendor 仅 moveTo 单点 (零距离 path); 此处追加 1px lineTo 与 GestureTapHelper 保持一致,
        //        规避部分 ROM 对零距离手势的静默丢弃 (MIUI 等)。华为 ROM 对零距离路径容忍,
        //        但 1px jitter 不影响华为派发行为。
        return Path().apply {
            moveTo(x, y)
            lineTo(x + JITTER_PX, y + JITTER_PX)
        }
    }
}
