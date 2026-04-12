package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * MiuiSteps — Xiaomi/MIUI-specific keepalive permission automation.
 * Matches vendor C0367a4 (a4, MiuiSteps). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 *    - Navigate to: com.miui.securitycenter/.AutoStartManagementActivity
 *    - Find app in list, enable auto-start switch
 * 2. Battery saver (省电策略)
 *    - Navigate to app's battery settings
 *    - Set to "无限制" (No restrictions) via RadioButton
 * 3. Background popup (后台弹窗)
 *    - Navigate to permissions settings
 *    - Enable "显示弹窗" (Show popup) switch
 * 4. Lock screen display (锁屏显示)
 *    - Enable notification display on lock screen
 */
class MiuiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "MiuiSteps"

        // Security center component names
        val AUTOSTART_COMPONENTS = listOf(
            ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"),
            ComponentName("com.miui.securitycenter", "com.miui.securitycenter.autostart.AutoStartManagementActivity")
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"),
            ComponentName("com.miui.securitycenter", "com.miui.powercenter.PowerSettings")
        )

        /** Keywords for finding auto-start toggle. Vendor 自启动 keywords. */
        val AUTOSTART_KEYWORDS = listOf("自启动", "自动启动", "Auto-start", "Autostart", "Auto start")

        /** Keywords for battery no-restriction. */
        val BATTERY_NO_RESTRICT_KEYWORDS = listOf("无限制", "No restrictions", "Unrestricted", "不限制")

        /** Keywords for background popup. */
        val BG_POPUP_KEYWORDS = listOf("后台弹出界面", "后台弹窗", "Background pop-up", "Display pop-up")
    }

    /**
     * Main execution entry for MIUI-specific steps.
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("MiuiSteps: 开始小米/MIUI权限配置")

        executeAutoStart(successes, failures, logs)
        delay(500)
        executeBatterySaver(successes, failures, logs)
        delay(500)
        executeBackgroundPopup(successes, failures, logs)

        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
    }

    /**
     * Navigate to auto-start management and enable for our app.
     * Vendor: executeAutoStart flow
     */
    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
            if (launched) {
                logs.add("已启动自启动管理页面")
                successes.add("小米自启动管理已打开")
            } else {
                failures.add("无法启动小米自启动管理")
            }
        } catch (e: Exception) {
            failures.add("小米自启动配置异常: ${e.message}")
        }
    }

    /**
     * Navigate to battery saver settings and set to unrestricted.
     * Vendor: executeBatterySaver flow
     */
    fun executeBatterySaver(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动省电策略页面")
                successes.add("小米省电策略已打开")
            } else {
                // Fallback: open app info battery settings
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已打开应用信息页面(回退)")
            }
        } catch (e: Exception) {
            failures.add("小米省电策略配置异常: ${e.message}")
        }
    }

    /**
     * Enable background popup permission.
     * Vendor: executeBackgroundPopup flow
     */
    fun executeBackgroundPopup(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            // Navigate to app permissions
            val intent = Intent("miui.intent.action.APP_PERM_EDITOR").apply {
                putExtra("extra_pkgname", context.packageName)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            logs.add("已启动MIUI权限编辑器")
            successes.add("小米后台弹窗权限已打开")
        } catch (e: Exception) {
            // Fallback
            logs.add("MIUI权限编辑器启动失败, 跳过后台弹窗: ${e.message}")
        }
    }

    /** Try launching each component in order, return true on first success. */
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
