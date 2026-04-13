package com.storm.safe.rock.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue

/**
 * JADX: PackageVerifyActivity.java (84 lines)
 * Displays a package verification screen to user. onCreate is heavily decompiled
 * with JADX errors (1182 instructions, code dump skipped). We replicate the
 * companion launch/shouldShow logic and the utility methods faithfully.
 *
 * The actual UI layout in onCreate could not be decompiled by JADX.
 */
class PackageVerifyActivity : Activity() {

    companion object {
        private const val TAG = "PkgVerify"
        private const val PREFS_NAME = "pkg_verify_state"
        private const val KEY_DONE = "v_done"

        /**
         * Launch the verify activity if it hasn't been completed.
         * JADX: C0244a0.launch()
         * FLAGS = 335544320 = FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP
         */
        fun launch(context: Context) {
            if (shouldShow(context)) {
                try {
                    val intent = Intent(context, PackageVerifyActivity::class.java)
                    intent.addFlags(335544320)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Log.e(TAG, "launch err", e)
                }
            }
        }

        /**
         * Check if the verify screen should be shown.
         * JADX: C0244a0.shouldShow()
         */
        fun shouldShow(context: Context): Boolean {
            return !context.getSharedPreferences(PREFS_NAME, 0)
                .getBoolean(KEY_DONE, false)
        }
    }

    /**
     * Convert dp to px (int).
     * JADX: m211184a0(float)
     */
    fun dpToPxInt(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
        ).toInt()
    }

    /**
     * Convert dp to px (float).
     * JADX: m211185a1(float)
     */
    fun dpToPxFloat(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ADAPT: VENDOR_VERIFY — JADX failed to decompile onCreate (1182 instructions).
        // The method builds a verification UI programmatically.
        // Marked as stub pending manual analysis of smali/instruction dump.
        Log.d(TAG, "PackageVerifyActivity.onCreate")
    }

    override fun onBackPressed() {
        // JADX: empty — back button disabled
    }
}
