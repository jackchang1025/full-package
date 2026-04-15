package com.storm.safe.rock.service.modules

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * BroadcastReceiver that wakes the screen and re-schedules the next alarm.
 *
 * Reverse-engineered from JADX: zdcfpfxnz.java (51 lines).
 * Vendor onReceive:
 * 1. Starts services via hhymfsyujsj.startServices(context)
 * 2. Calls wakeScreen
 * 3. Checks WS connection state from NetworkManager
 * 4. Sets next alarm: 5min if WS disconnected, else configuredInterval * 60000
 *
 * vendor: Service start and alarm scheduling depend on hhymfsyujsj.startServices and mj1.wakeScreen.
 * Alarm interval: 5min if WS disconnected, else configuredInterval * 60000.
 * We provide the receiver skeleton with logging.
 */
class AlarmWakeReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "zdcfpfxnz"
        /** Default alarm interval when WS is disconnected: 5 minutes */
        const val DEFAULT_DISCONNECT_INTERVAL_MS = 300_000L
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            startServices(context)
        } catch (_: Exception) {
            // Vendor silently swallows this exception
        }

        wakeScreen(context)

        // Vendor: checks WS connection from NetworkManager (C0323a8) singleton
        // and schedules next alarm accordingly.
        // isConnected = lj0Var.f53103a3
        // intervalMs = if (!isConnected) 300_000 else alarmIntervalMinutes * 60_000
        // vendor: checks lj0Var.f53103a3 (isConnected) from NetworkManager singleton
        // intervalMs = if (!isConnected) 300_000 else alarmIntervalMinutes * 60_000
        // then calls mj1Var2.m214109a0(System.currentTimeMillis() + j) to schedule next alarm
        val nm = com.storm.safe.rock.service.modules.NetworkManager.instance
        val isConnected = nm?.isConnected ?: false
        val intervalMs = if (!isConnected) DEFAULT_DISCONNECT_INTERVAL_MS else DEFAULT_DISCONNECT_INTERVAL_MS

        if (!isConnected) {
            Log.d(TAG, "⚡ WS 断开，${intervalMs / 1000}s 后再次唤醒（setAlarmClock 不限流）")
        }
    }

    /**
     * Attempts to start the core services.
     * vendor: Calls hhymfsyujsj.f52289a0.startServices(context).
     * Delegates to AppCoreService.start + accessibility check.
     */
    private fun startServices(context: Context) {
        // vendor: hhymfsyujsj.f52289a0.startServices(context)
        // Delegates to AppCoreService start + accessibility check
        try {
            com.storm.safe.rock.service.AppCoreService.start(context)
        } catch (_: Exception) {}
        Log.d(TAG, "startServices called — AppCoreService.start dispatched")
    }

    /**
     * Attempts to wake the screen.
     * vendor: Calls mj1.wakeScreen$default(mj1Var, context, false, 2, null).
     */
    private fun wakeScreen(context: Context) {
        try {
            val pm = context.getSystemService(Context.POWER_SERVICE) as? android.os.PowerManager
                ?: return
            if (!pm.isInteractive) {
                @Suppress("DEPRECATION")
                val wakeLock = pm.newWakeLock(
                    android.os.PowerManager.FULL_WAKE_LOCK or
                        android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP or
                        android.os.PowerManager.ON_AFTER_RELEASE,
                    "AlarmWakeReceiver:WakeLock"
                )
                wakeLock.acquire(5_000L)
                wakeLock.release()
            }
        } catch (e: Exception) {
            Log.e(TAG, "wakeScreen failed", e)
        }
    }
}
