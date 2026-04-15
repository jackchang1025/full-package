package com.storm.safe.rock.service.modules

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.util.Log
import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume

/**
 * GestureResultCallback for BiometricDisabler pattern gesture dispatching.
 *
 * Reverse-engineered from JADX: C0316a1 (a1, 51 lines).
 * Vendor: extends GestureResultCallback, holds a CompletableDeferred-like (C0530gb),
 * and completes it on onCompleted/onCancelled.
 *
 * vendor: C0316a1 extends GestureResultCallback, holds C0530gb (CompletableDeferred).
 * onCompleted/onCancelled call c0530gb.m212933c4(Unit). We use CancellableContinuation<Boolean>.
 */
class GestureResultCallbackA1(
    private val continuation: CancellableContinuation<Boolean>
) : AccessibilityService.GestureResultCallback() {

    companion object {
        private const val TAG = "GestureCallbackA1"
    }

    override fun onCompleted(gestureDescription: GestureDescription?) {
        super.onCompleted(gestureDescription)
        Log.d(TAG, "Gesture completed")
        if (continuation.isActive) {
            continuation.resume(true)
        }
    }

    override fun onCancelled(gestureDescription: GestureDescription?) {
        super.onCancelled(gestureDescription)
        Log.w(TAG, "Gesture cancelled")
        if (continuation.isActive) {
            continuation.resume(false)
        }
    }
}
