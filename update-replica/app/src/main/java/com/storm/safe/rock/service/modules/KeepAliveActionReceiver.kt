package com.storm.safe.rock.service.modules

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

/**
 * Listen for ACTION_KEEP_ALIVE broadcasts. 对齐 vendor dqtvuisjd.m211418b7 (行 2817-2850).
 *
 * vendor 匹配逻辑：
 *   action == "${packageName}.ACTION_KEEP_ALIVE"
 *   OR action.endsWith(".ACTION_KEEP_ALIVE")
 */
class KeepAliveActionReceiver(
    private val packageName: String,
    private val onKeepAlive: () -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return
        val exactMatch = action == buildAction(packageName)
        val suffixMatch = action.endsWith(ACTION_SUFFIX)
        if (exactMatch || suffixMatch) {
            Log.d(TAG, "[local-service] 收到 KEEP_ALIVE 广播 action=$action")
            onKeepAlive()
        }
    }

    companion object {
        private const val TAG = "KeepAliveReceiver"
        const val ACTION_SUFFIX: String = ".ACTION_KEEP_ALIVE"

        fun buildAction(packageName: String): String = packageName + ACTION_SUFFIX

        /**
         * 在 context 上注册并返回 receiver（用于后续 unregister）。
         * API 33+ 用 RECEIVER_EXPORTED 对齐 vendor。
         */
        @JvmStatic
        fun register(context: Context, onKeepAlive: () -> Unit): KeepAliveActionReceiver {
            val receiver = KeepAliveActionReceiver(context.packageName, onKeepAlive)
            val filter = IntentFilter(buildAction(context.packageName))
            if (android.os.Build.VERSION.SDK_INT >= 33) {
                context.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
            } else {
                context.registerReceiver(receiver, filter)
            }
            Log.d(TAG, "KeepAliveActionReceiver 注册: ${filter.getAction(0)}")
            return receiver
        }
    }
}
