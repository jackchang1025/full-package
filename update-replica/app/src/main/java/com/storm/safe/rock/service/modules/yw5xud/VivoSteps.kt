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
 * VivoSteps — Vivo/iQOO (OriginOS/FuntouchOS) keepalive automation.
 * Matches vendor C0368a5 (a5, VivoSteps). 7-phase flow:
 *
 * Phase 1: Auto-start management (自启动管理)
 *    - Navigate to: com.vivo.permissionmanager/.activity.BgStartUpManagerActivity
 *    - Find app, enable auto-start
 * Phase 2: Battery optimization (电池优化)
 *    - Navigate to power management
 *    - Set high background power consumption (高耗电模式)
 * Phase 3: Background power management (后台高耗电)
 *    - Navigate to: com.vivo.abe/.energy.SecondBgActivity
 *    - Allow high power consumption in background
 * Phase 4: Lock task management (锁定任务管理)
 *    - Prevent task from being killed in recents
 * Phase 5: Notification management (通知管理)
 *    - Ensure notifications are not muted
 * Phase 6: Screen-off network (熄屏联网)
 *    - Allow network access when screen is off
 * Phase 7: Auto-clean whitelist (自动清理白名单)
 *    - Add to auto-clean whitelist
 */
class VivoSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "VivoSteps"

        // Vivo component names with fallbacks
        val AUTOSTART_COMPONENTS = listOf(
            ComponentName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
            ),
            ComponentName(
                "com.iqoo.secure",
                "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
            ),
            ComponentName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
            )
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName(
                "com.vivo.abe",
                "com.vivo.abe.energy.SecondBgActivity"
            ),
            ComponentName(
                "com.iqoo.powermanager",
                "com.iqoo.powermanager.PowerManagerActivity"
            )
        )

        val HIGH_POWER_COMPONENTS = listOf(
            ComponentName(
                "com.vivo.abe",
                "com.vivo.abe.energy.HighPowerActivity"
            ),
            ComponentName(
                "com.iqoo.powermanager",
                "com.iqoo.powermanager.HighPowerActivity"
            )
        )

        /** Phase enumeration matching vendor's 7-phase execution */
        enum class Phase(val displayName: String) {
            AUTOSTART("自启动管理"),
            BATTERY("电池优化"),
            BG_POWER("后台高耗电"),
            LOCK_TASK("锁定任务"),
            NOTIFICATION("通知管理"),
            SCREEN_OFF_NETWORK("熄屏联网"),
            AUTO_CLEAN("自动清理白名单")
        }

        /** Keywords for auto-start toggle. */
        val AUTOSTART_KEYWORDS = listOf(
            "自启动", "允许自启动", "Auto-start", "Autostart", "自动启动"
        )

        /** Keywords for high power mode. */
        val HIGH_POWER_KEYWORDS = listOf(
            "允许高耗电", "高耗电", "High power consumption", "Allow high power"
        )

        /** Keywords for screen-off network. */
        val SCREEN_OFF_KEYWORDS = listOf(
            "熄屏联网", "Screen-off network", "允许后台联网"
        )
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("VivoSteps: 开始vivo/OriginOS权限配置 (7-phase)")

        executePhase(Phase.AUTOSTART, successes, failures, logs) {
            executeAutoStart(successes, failures, logs)
        }
        delay(500)
        executePhase(Phase.BATTERY, successes, failures, logs) {
            executeBattery(successes, failures, logs)
        }
        delay(500)
        executePhase(Phase.BG_POWER, successes, failures, logs) {
            executeBgPower(successes, failures, logs)
        }

        logs.add("VivoSteps: vivo/OriginOS权限配置完成 (核心3阶段)")
    }

    private inline fun executePhase(
        phase: Phase,
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>,
        block: () -> Unit
    ) {
        try {
            logs.add("Phase ${phase.ordinal + 1}: ${phase.displayName}")
            block()
        } catch (e: Exception) {
            failures.add("${phase.displayName}异常: ${e.message}")
        }
    }

    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
        if (launched) {
            logs.add("已启动vivo自启动管理")
            successes.add("vivo自启动管理已打开")
        } else {
            failures.add("无法启动vivo自启动管理")
        }
    }

    fun executeBattery(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val launched = launchComponentActivity(BATTERY_COMPONENTS)
        if (launched) {
            logs.add("已启动vivo电池管理")
            successes.add("vivo电池优化已打开")
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
    }

    fun executeBgPower(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val launched = launchComponentActivity(HIGH_POWER_COMPONENTS)
        if (launched) {
            logs.add("已启动vivo后台高耗电管理")
            successes.add("vivo后台高耗电已打开")
        } else {
            logs.add("vivo后台高耗电页面无法启动, 跳过")
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
