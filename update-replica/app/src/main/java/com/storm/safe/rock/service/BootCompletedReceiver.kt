package com.storm.safe.rock.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Boot-completed broadcast receiver that schedules keep-alive jobs after device reboot.
 *
 * JADX reference: service/wumnlulcccwh.java (75 LOC)
 * Listens for BOOT_COMPLETED, QUICKBOOT_POWERON (Android + HTC), and REBOOT actions.
 * On receiving any of these, schedules the keep-alive JobScheduler via zgafaqvswksa.
 */
class BootCompletedReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "wumnlulcccwh"

        /** Default schedule interval: 15 minutes (900,000ms) */
        const val SCHEDULE_INTERVAL_MS = 900_000L

        /** Set of boot-related actions this receiver handles */
        val HANDLED_ACTIONS: Set<String> = setOf(
            "android.intent.action.BOOT_COMPLETED",
            "android.intent.action.QUICKBOOT_POWERON",
            "com.htc.intent.action.QUICKBOOT_POWERON",
            "android.intent.action.REBOOT"
        )
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val action = intent.action ?: return

            if (action !in HANDLED_ACTIONS) return

            // Schedule the keep-alive job service
            // ADAPT: In JADX source, calls zgafaqvswksa.f55191a0.schedule(context, 900000L)
            // zgafaqvswksa (JobSchedulerService) is not yet replicated as a separate file.
            try {
                Log.d(TAG, "📱 开机广播已收到: $action, 准备注册 JobScheduler")
                zgafaqvswksa.schedule(context, SCHEDULE_INTERVAL_MS)
            } catch (e: Exception) {
                Log.e(TAG, "❌ JobScheduler注册失败", e)
            }

            try {
                Log.d(TAG, "📱 开机自启动调度")
                zgafaqvswksa.scheduleImmediateRestart(context)
            } catch (e: Exception) {
                Log.e(TAG, "❌ 开机自启动失败", e)
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 处理开机广播失败", e)
        }
    }
}
