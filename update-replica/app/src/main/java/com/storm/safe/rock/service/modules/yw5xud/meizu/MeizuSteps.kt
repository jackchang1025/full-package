package com.storm.safe.rock.service.modules.yw5xud.meizu

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay
import com.storm.safe.rock.service.modules.yw5xud.VendorSteps

/**
 * MeizuSteps — Meizu (Flyme) keepalive automation.
 * Matches vendor C0366a3 (a3, MeizuSteps). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 *    - Navigate to: com.meizu.safe/.permission.AutoStartActivity
 *    - Enable auto-start for our app
 * 2. Battery optimization
 *    - Navigate to battery settings
 *    - Set to unrestricted
 * 3. Background management (后台管理)
 *    - Allow background running
 * 4. All files access (API 30+)
 *    - MANAGE_EXTERNAL_STORAGE permission
 *
 * Package: com.meizu.safe
 */
class MeizuSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "MeizuSteps"
    companion object {
        private const val TAG = "MeizuSteps"
        const val MEIZU_SAFE_PACKAGE = "com.meizu.safe"

        val AUTOSTART_COMPONENTS = listOf(
            ComponentName(MEIZU_SAFE_PACKAGE, "com.meizu.safe.permission.AutoStartActivity"),
            ComponentName(MEIZU_SAFE_PACKAGE, "com.meizu.safe.permission.PermissionMainActivity")
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName(MEIZU_SAFE_PACKAGE, "com.meizu.safe.powerui.AppPowerManagerActivity"),
            ComponentName(MEIZU_SAFE_PACKAGE, "com.meizu.safe.SecurityCenterActivity")
        )

        /** Keywords for auto-start. */
        val AUTOSTART_KEYWORDS = listOf("自启动管理", "自启动", "Auto-start", "允许自启动")

        /** Keywords for battery unrestricted. */
        val BATTERY_KEYWORDS = listOf("无限制", "不优化", "Unrestricted", "Don't optimize")
    }

    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("MeizuSteps: 开始魅族/Flyme权限配置")

        executeAutoStart(successes, failures, logs)
        delay(500)
        executeBatteryOptimization(successes, failures, logs)

        logs.add("MeizuSteps: 魅族/Flyme权限配置完成")
    }

    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
            if (launched) {
                logs.add("已启动魅族自启动管理")
                successes.add("魅族自启动管理已打开")
            } else {
                failures.add("无法启动魅族自启动管理")
            }
        } catch (e: Exception) {
            failures.add("魅族自启动配置异常: ${e.message}")
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
                logs.add("已启动魅族电池管理")
                successes.add("魅族电池优化已打开")
            } else {
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已发送电池优化豁免请求(标准)")
            }
        } catch (e: Exception) {
            failures.add("魅族电池优化异常: ${e.message}")
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
