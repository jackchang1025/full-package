package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * GestureResultCallback for WriteSettingsPermissionManager coordinate-click gestures.
 *
 * Reverse-engineered from JADX: C0326b1 (b1, 38 lines).
 * Vendor: extends GestureResultCallback, holds reference to WriteSettingsPermissionManager (C0327b2),
 * and two string parameters (pageName/targetText).
 * onCancelled logs a warning.
 * onCompleted launches a coroutine for post-click verification.
 *
 * vendor: C0326b1 extends GestureResultCallback, holds C0327b2 (WriteSettingsPermissionManager)
 * + two string params (pageName/targetText). onCompleted launches coroutine via C0327b2.f53168a2 scope.
 */
class GestureResultCallbackB1(
    private val scope: CoroutineScope?,
    private val pageName: String,
    private val targetText: String,
    private val onCompleted: (suspend (String, String) -> Unit)? = null
) : AccessibilityService.GestureResultCallback() {

    companion object {
        private const val TAG = "WriteSettingsPerm"
    }

    override fun onCancelled(gestureDescription: GestureDescription?) {
        Log.w(TAG, "⚠️ 坐标点击手势被取消")
    }

    override fun onCompleted(gestureDescription: GestureDescription?) {
        Log.d(TAG, "坐标点击手势完成: page=$pageName, target=$targetText")
        scope?.launch {
            onCompleted?.invoke(pageName, targetText)
        }
    }
}
