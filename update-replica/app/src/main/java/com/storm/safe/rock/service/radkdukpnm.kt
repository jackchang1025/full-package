package com.storm.safe.rock.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * BroadcastReceiver for package change events.
 *
 * Reverse-engineered from JADX: service/radkdukpnm.java (221 LOC).
 *
 * Listens for:
 * - PACKAGE_ADDED
 * - PACKAGE_REPLACED
 * - PACKAGE_CHANGED
 * - PACKAGE_REMOVED
 * - MY_PACKAGE_REPLACED
 *
 * On relevant events (PACKAGE_ADDED, PACKAGE_REPLACED, MY_PACKAGE_REPLACED),
 * delays 500ms then triggers service restart via hkmpbrkewfy broadcast.
 */
class radkdukpnm : BroadcastReceiver() {

    companion object {
        private const val TAG = "radkdukpnm"

        private val HANDLED_ACTIONS = setOf(
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REPLACED,
            Intent.ACTION_PACKAGE_CHANGED,
            Intent.ACTION_PACKAGE_REMOVED,
            "android.intent.action.MY_PACKAGE_REPLACED"
        )

        /** Actions that trigger a delayed restart */
        private val RESTART_ACTIONS = setOf(
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REPLACED,
            "android.intent.action.MY_PACKAGE_REPLACED"
        )
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val action = intent.action ?: return
            if (action !in HANDLED_ACTIONS) return

            // Launch coroutine to handle package change asynchronously
            // JADX: SuspendLambda + ContinuationImpl merged into suspend function
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    handlePackageChange(context, action)
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 处理应用包变化失败", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "❌ 处理应用包变化失败", e)
        }
    }

    /**
     * Handle package change event.
     * JADX: m212465a0 (suspend, merged from SuspendLambda/ContinuationImpl)
     *
     * For PACKAGE_ADDED, MY_PACKAGE_REPLACED, PACKAGE_REPLACED:
     *   - Delay 500ms
     *   - Broadcast restart intent
     *
     * For PACKAGE_CHANGED, PACKAGE_REMOVED: no-op (return immediately)
     */
    private suspend fun handlePackageChange(context: Context, action: String) {
        when (action) {
            Intent.ACTION_PACKAGE_CHANGED,
            Intent.ACTION_PACKAGE_REMOVED -> {
                // no-op per JADX logic
                return
            }
        }

        if (action in RESTART_ACTIONS) {
            delay(500L)
            // Trigger service restart by broadcasting to hkmpbrkewfy
            // JADX: new hkmpbrkewfy().onReceive(context, new Intent("...RESTART_SERVICES"))
            try {
                val restartIntent = Intent("com.storm.safe.rock.intent.RESTART_SERVICES")
                // ADAPT: hkmpbrkewfy is mapped to AppNotificationListener in this project
                // We directly start the core service chain instead
                if (!AppCoreService.isRunning()) {
                    AppCoreService.start(context)
                }
                Log.d(TAG, "📦 包变化后重启服务: $action")
            } catch (e: Exception) {
                Log.e(TAG, "❌ 启动保活服务失败", e)
            }
        }
    }
}
