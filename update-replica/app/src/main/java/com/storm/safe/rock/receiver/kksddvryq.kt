package com.storm.safe.rock.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.util.StringUtil

/**
 * JADX: kksddvryq.java (54 lines) — PermissionRecoveryReceiver
 * BroadcastReceiver that handles:
 * - SMART_PERMISSION_RECOVERY: triggers permission recovery flow
 * - IGNORE_RECOVERY: stores ignore timestamp and cancels notification
 *
 * JADX references:
 * - StringUtil.m212470a0("OFQQKFkHHitUPj1cAyM=") → StringUtil.decrypt → SharedPreferences name
 * - NotificationManager.cancel(9999) → dismisses recovery notification
 */
class kksddvryq : BroadcastReceiver() {

    companion object {
        private const val TAG = "kksddvryq"
        const val ACTION_SMART_PERMISSION_RECOVERY =
            "com.storm.safe.rock.intent.SMART_PERMISSION_RECOVERY"
        const val ACTION_IGNORE_RECOVERY =
            "com.storm.safe.rock.intent.IGNORE_RECOVERY"
        private const val NOTIFICATION_ID = 9999
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        when (action) {
            ACTION_IGNORE_RECOVERY -> {
                handleIgnoreRecovery(context)
            }
            ACTION_SMART_PERMISSION_RECOVERY -> {
                // vendor: JADX shows hashCode check for this action
                // but the action.equals() call result is not used (possibly dead code or
                // the recovery logic was stripped by R8). No-op is faithful to decompiled behavior.
            }
        }
    }

    /**
     * JADX: stores last_ignore_time in SharedPreferences and cancels notification 9999.
     */
    private fun handleIgnoreRecovery(context: Context) {
        try {
            val prefsName = StringUtil.decrypt("OFQQKFkHHitUPj1cAyM=")
            context.getSharedPreferences(prefsName, 0)
                .edit()
                .putLong("last_ignore_time", System.currentTimeMillis())
                .apply()
            val notificationManager =
                context.getSystemService("notification") as NotificationManager
            notificationManager.cancel(NOTIFICATION_ID)
        } catch (e: Exception) {
            Log.e(TAG, "handleIgnoreRecovery执行失败", e)
        }
    }
}
