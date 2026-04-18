package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * OppoSteps — OPPO/Realme/OnePlus (ColorOS/RealmeUI/OxygenOS) keepalive automation.
 * Matches vendor C0370a7 (a7, OppoStepsSimplified). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 *    - Navigate to: com.coloros.safecenter/.startupapp.StartupAppListActivity
 *    - Find app, enable auto-start switch (Switch→R()+finish pattern)
 * 2. Battery optimization
 *    - Navigate to power management settings
 *    - Disable battery optimization for our app
 * 3. Background management (后台管理)
 *    - Prevent system from killing our app in background
 * 4. All files access (MANAGE_EXTERNAL_STORAGE)
 *    - API 30+ file access permission
 */
open class OppoSteps(
    protected val service: MyAccessibilityService?,
    private val context: Context
) {
    companion object {
        private const val TAG = "OppoSteps"

        // ColorOS component names (with fallbacks for Realme/OnePlus)
        val AUTOSTART_COMPONENTS = listOf(
            ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.startupapp.StartupAppListActivity"
            ),
            ComponentName(
                "com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"
            ),
            ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"
            )
        )

        val BATTERY_COMPONENTS = listOf(
            ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
            ),
            ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerSaverModeActivity"
            ),
            ComponentName(
                "com.oplus.battery",
                "com.oplus.powermanager.fuelgaue.PowerUsageModelActivity"
            )
        )

        /**
         * Step 1 permission dialog allow-button resource-ids,按优先级 3 个(Android 12+ permissioncontroller)。
         * PERMISSION_ALLOW_IDS 是更宽泛的 8-id fallback 列表(含 packageinstaller 等老 Android 版本);
         * STEP1_ALLOW_IDS 是华为真机 25/26 validated 的最小稳定集。
         */
        val STEP1_ALLOW_IDS = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button"
        )

        /** Permission allow button IDs for ColorOS. */
        val PERMISSION_ALLOW_IDS = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button",
            "com.android.packageinstaller:id/permission_allow_button",
            "com.google.android.packageinstaller:id/permission_allow_button",
            "android:id/button1",
            "android:id/button2",
            "com.android.settings:id/action_button"
        )

        /** Keywords for auto-start toggle. */
        val AUTOSTART_KEYWORDS = listOf(
            "允许自启动", "自启动", "Allow auto-start", "Auto-start", "Autostart"
        )

        /** Keywords for battery no-restriction. */
        val BATTERY_KEYWORDS = listOf(
            "不优化", "无限制", "Don't optimize", "Unrestricted", "允许后台运行"
        )
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("OppoSteps: 开始OPPO/ColorOS权限配置")

        executeAutoStart(successes, failures, logs)
        delay(500)
        executeBatteryOptimization(successes, failures, logs)

        logs.add("OppoSteps: OPPO/ColorOS权限配置完成")
    }

    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(AUTOSTART_COMPONENTS)
            if (launched) {
                logs.add("已启动ColorOS自启动管理")
                successes.add("OPPO自启动管理已打开")
            } else {
                failures.add("无法启动OPPO自启动管理")
            }
        } catch (e: Exception) {
            failures.add("OPPO自启动配置异常: ${e.message}")
        }
    }

    fun executeBatteryOptimization(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = launchComponentActivity(BATTERY_COMPONENTS)
            if (launched) {
                logs.add("已启动ColorOS电池管理")
                successes.add("OPPO电池优化已打开")
            } else {
                val intent = Intent(
                    Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                ).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                logs.add("已发送电池优化豁免请求(标准)")
            }
        } catch (e: Exception) {
            failures.add("OPPO电池优化异常: ${e.message}")
        }
    }

    internal fun launchComponentActivity(components: List<ComponentName>): Boolean {
        for (component in components) {
            try {
                val intent = Intent().apply {
                    this.component = component
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return true
            } catch (_: Exception) {
                continue
            }
        }
        return false
    }

    /**
     * Step 1 — 基础运行时权限(umrkmgrri 子模块 + resource-id 驱动)。
     *
     * vendor `OppoStepsSimplified.m212323c1` 委托 umrkmgrri.f55158a3.start(context) + 独立 Thread 轮询 20×500ms。
     * replica 采用与 HuaweiSteps.executeStep1BasicPermissions 一致的 resource-id 驱动:
     *  1. 启动 umrkmgrri Activity(等同于华为的 HuaweiPermissionRequestActivity)
     *  2. 10s 轮询主循环:优先 permissioncontroller resource-id 点击,失败降级 text 点击
     *
     * ADAPT: 不用 vendor 的坐标 fallback(华为 FIN-AL60 真机证明 coord 必然 miss)。
     */
    open suspend fun executeStep1BasicPermissions(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val svc = service ?: run {
            failures.add("[Step 1/10] service=null,跳过")
            return
        }
        Log.i(TAG, "[Step1/9] enter executeStep1BasicPermissions")
        logs.add("[Step 1/9] ▶ 基础权限开始(超时10秒) | vendor m212323c1")

        // 启动批量权限 Activity(复用现有 umrkmgrri)
        try {
            val intent = Intent(context, umrkmgrri::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            }
            svc.startActivity(intent)
            logs.add("[Step 1/9] ✓ 已启动 umrkmgrri")
            delay(800L)
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.w(TAG, "[Step1] launch umrkmgrri failed: ${e.message}")
        }

        val timeoutMs = 10_000L
        val start = System.currentTimeMillis()
        var clickCount = 0
        val maxClicksGuard = 40

        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null }
            if (root == null) { delay(300L); continue }

            // 主路径:resource-id 驱动点击
            val clickedViaId = clickPermissionControllerAllowButton(root)
            if (clickedViaId != null) {
                Log.d(TAG, "[Step1] allow-by-id = $clickedViaId")
                logs.add("[Step 1/9] 🔍 allow-by-id = $clickedViaId")
                clickCount++
                delay(300L)
                if (clickCount >= maxClicksGuard) break
                continue
            }

            // fallback:文本点击
            val textClicked = clickTextOnRoot(root, "始终允许") ||
                clickTextOnRoot(root, "允许") ||
                clickTextOnRoot(root, "仅使用期间允许")
            if (textClicked) {
                clickCount++
                delay(300L)
            } else {
                delay(500L)
            }
            if (clickCount >= maxClicksGuard) break
        }

        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        logs.add("[Step 1/9] 完成,用时 ${elapsedSec}s,点击 $clickCount 次")
        if (clickCount > 0) successes.add("[Step 1/9] 基础权限处理 $clickCount 次")
    }

    /** 按 3 个 permissioncontroller resource-id 优先级查 + 点击,返回命中 id 短名或 null. */
    private fun clickPermissionControllerAllowButton(root: android.view.accessibility.AccessibilityNodeInfo): String? {
        for (id in STEP1_ALLOW_IDS) {
            val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null } ?: continue
            for (n in nodes) {
                try { if (!n.isVisibleToUser) continue } catch (_: Exception) { /* mock may throw */ }
                if (performClickOrAncestor(n)) return id.substringAfterLast('/')
            }
        }
        return null
    }

    private fun performClickOrAncestor(node: android.view.accessibility.AccessibilityNodeInfo): Boolean {
        try {
            if (node.isClickable && node.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
            var p = try { node.parent } catch (_: Exception) { null }
            var depth = 0
            while (p != null && depth < 10) {
                if (p.isClickable && p.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
                p = try { p.parent } catch (_: Exception) { null }
                depth++
            }
        } catch (_: Exception) { /* ignore */ }
        return false
    }

    private fun clickTextOnRoot(root: android.view.accessibility.AccessibilityNodeInfo, text: String): Boolean {
        val matches = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { return false } ?: return false
        for (n in matches) {
            try { if (!n.isVisibleToUser) continue } catch (_: Exception) {}
            val nodeText = try { n.text?.toString()?.trim() ?: "" } catch (_: Exception) { "" }
            if (nodeText == text && performClickOrAncestor(n)) return true
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ SubBrand & appLabel(Task 2 引入)━━━━━━━━━━━━━━━━━

    /** 当前设备 SubBrand(vendor SubBrand ordinal 对齐) */
    open val subBrand: OppoSubBrand = OppoSubBrand.detect()

    /** App label(R.string.app_name 或 packageName fallback) */
    val appLabel: String = try {
        context.applicationInfo.loadLabel(context.packageManager).toString()
    } catch (_: Throwable) { context.packageName ?: "app" }

    // ━━━━━━━━━━━━━━━━━ Step 2 — 电池优化 ━━━━━━━━━━━━━━━━━

    /**
     * Step 2 — 电池优化豁免(SubBrand 分发)。
     *
     * vendor `m212337e1` dispatcher:
     *   REALME → mRealme
     *   ONEPLUS → mOnePlus
     *   OPPO/OPLUS → mOppo
     */
    open suspend fun executeStep2Battery(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)) {
            logs.add("[Step 2/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs.add("[Step 2/9] ▶ 电池优化豁免开始(subBrand=$subBrand)")

        when (subBrand) {
            OppoSubBrand.REALME -> executeBatteryRealme(successes, failures, logs)
            OppoSubBrand.ONEPLUS -> executeBatteryOnePlus(successes, failures, logs)
            OppoSubBrand.OPPO, OppoSubBrand.OPLUS -> executeBatteryOppo(successes, failures, logs)
        }
    }

    /** OPPO/OPLUS 路径(文档 2a) — 4 级菜单 + 5 个目标开关 */
    open suspend fun executeBatteryOppo(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        logs.add("[Step 2/9] mOppo 4 级菜单路径")
        openSettings()
        kotlinx.coroutines.delay(800L)

        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        navigateByHashPath(OppoBatteryPaths.OPPO_OPLUS_PATH, scrollLimit = 5)

        closeSwitch("睡眠待机优化") || closeSwitch("待机耗电优化")
        kotlinx.coroutines.delay(400L)
        clickTextWithScroll("耗电异常优化", scrollLimit = 3)
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll(appLabel, scrollLimit = 25)
        kotlinx.coroutines.delay(400L)
        clickText("不优化")
        kotlinx.coroutines.delay(400L)
        pressBack(); pressBack()
        closeSwitch("省电模式")

        successes.add("[Step 2/9] OPPO 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** Realme 路径(文档 2c) — 按 SDK 分支 */
    open suspend fun executeBatteryRealme(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT
        logs.add("[Step 2/9] mRealme SDK=$sdk")
        openSettings()
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] Realme SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                clickTextWithScroll("省电设置", scrollLimit = 3)
                kotlinx.coroutines.delay(400L)
                closeSwitch("睡眠待机优化")
                closeSwitch("自动进入省电模式")
                pressBack(); pressBack(); pressBack()
            }
            sdk == 29 -> {
                closeSwitch("省电模式")
                clickTextWithScroll("智能省电场景", scrollLimit = 3)
                kotlinx.coroutines.delay(400L)
                closeSwitch("睡眠待机优化")
            }
            else -> {
                navigateByHashPath(OppoBatteryPaths.REALME_LEGACY_PATH, scrollLimit = 5)
                clickTextWithScroll("耗电异常优化", scrollLimit = 3)
                clickTextWithScroll(appLabel, scrollLimit = 25)
                clickText("不优化") || (clickText("待机优化") && clickText("关闭"))
                pressBack(); pressBack()
            }
        }
        successes.add("[Step 2/9] Realme 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** OnePlus 路径(文档 2b) — 按 SDK 分支 */
    open suspend fun executeBatteryOnePlus(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT
        logs.add("[Step 2/9] mOnePlus SDK=$sdk")
        openSettings()
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] OnePlus SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                clickTextWithScroll("电池模式", scrollLimit = 3)
                kotlinx.coroutines.delay(400L)
                clickText("均衡模式")
                kotlinx.coroutines.delay(400L)
                clickTextWithScroll("省电设置", scrollLimit = 3)
                closeSwitch("自动进入省电模式")
                closeSwitch("睡眠待机优化")
                pressBack(); pressBack()
            }
            else -> {
                openAppDetails()
                closeSwitch("省电模式")
                clickText("立即关闭")
                pressBack()
                clickTextWithScroll("耗电管理", scrollLimit = 3) || clickText("电池")
                navigateByHashPath(OppoBatteryPaths.ONEPLUS_LEGACY_PATH, scrollLimit = 3)
                closeSwitch("睡眠待机优化")
                clickTextWithScroll("耗电异常优化", scrollLimit = 3)
                clickTextWithScroll(appLabel, scrollLimit = 25)
                clickText("不优化")
                pressBack(); pressBack(); pressBack()
            }
        }
        successes.add("[Step 2/9] OnePlus 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    // ━━━━━━━━━━━━━━━━━ UI helpers(Task 2 引入,被 Task 3-8 共用)━━━━━━━━━━━━━━━━━

    /** 打开系统设置首页 */
    protected suspend fun openSettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_SETTINGS)
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "openSettings: ${e.message}")
        }
    }

    /** 打开 app 详情页 */
    protected suspend fun openAppDetails() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "openAppDetails: ${e.message}")
        }
    }

    protected fun pressBack() {
        try { service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK) } catch (_: Exception) {}
    }

    /** `#` 分隔符多级菜单导航(vendor clickVWithScroll) */
    protected suspend fun navigateByHashPath(path: String, scrollLimit: Int = 3) {
        for (segment in path.split("#")) {
            clickTextWithScroll(segment, scrollLimit = scrollLimit)
            kotlinx.coroutines.delay(500L)
        }
    }

    /** 文本点击(单层,不滚动)— 复用 Task 1 的 clickTextOnRoot */
    protected fun clickText(text: String): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return clickTextOnRoot(root, text)
    }

    /** 文本点击 + 未找到则 scroll 重试,最多 scrollLimit 次 */
    protected suspend fun clickTextWithScroll(text: String, scrollLimit: Int = 3): Boolean {
        repeat(scrollLimit + 1) {
            if (clickText(text)) return true
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
            try { root.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD) } catch (_: Exception) {}
            kotlinx.coroutines.delay(400L)
        }
        return false
    }

    /** 关闭名为 text 的 Switch(当前 checked=true 则 click 切为 false) */
    protected fun closeSwitch(text: String): Boolean = toggleSwitch(text, desiredChecked = false)

    /** 开启名为 text 的 Switch */
    protected fun openSwitch(text: String): Boolean = toggleSwitch(text, desiredChecked = true)

    private fun toggleSwitch(text: String, desiredChecked: Boolean): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null } ?: return false
        for (labelNode in nodes) {
            var p: android.view.accessibility.AccessibilityNodeInfo? = try { labelNode.parent } catch (_: Exception) { null }
            var depth = 0
            while (p != null && depth < 8) {
                val childCount = try { p.childCount } catch (_: Exception) { 0 }
                for (i in 0 until childCount) {
                    val sibling = try { p.getChild(i) } catch (_: Exception) { null } ?: continue
                    val clsName = try { sibling.className?.toString() ?: "" } catch (_: Exception) { "" }
                    if (clsName.endsWith("Switch") || clsName.endsWith("CheckBox") || clsName.endsWith("CompoundButton")) {
                        val isChecked = try { sibling.isChecked } catch (_: Exception) { false }
                        if (isChecked == desiredChecked) return true  // 已是目标状态
                        if (performClickOrAncestor(sibling)) return true
                    }
                }
                p = try { p.parent } catch (_: Exception) { null }
                depth++
            }
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 3 — 自启动 + 后台 ━━━━━━━━━━━━━━━━━

    /**
     * Step 3 — 自启动 + 后台运行。
     *
     * vendor m212318b6:SDK ≥ 35 走设置→应用→自启动管理;SDK < 35 走 openAppDetails + 多开关尝试。
     * 未找到自启动开关时 5 个 SafeCenter ComponentName 兜底(文档"权限 3")。
     */
    open suspend fun executeStep3AutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)) {
            logs.add("[Step 3/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs.add("[Step 3/9] ▶ 自启动 + 后台开始")

        val autoOK = runAutoStartSubSwitch(successes, failures, logs)
        val bgOK = runBackgroundSubSwitch(successes, failures, logs)

        if (autoOK && bgOK) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)
            successes.add("[Step 3/9] 自启动+后台整体完成")
        } else {
            logs.add("[Step 3/9] 部分失败,不 mark 整体(autoOK=$autoOK bgOK=$bgOK)")
        }
    }

    open suspend fun runAutoStartSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val viaSettings = tryOpenAutoStartViaSettings(successes, failures, logs)
        val ok = if (viaSettings) true else {
            logs.add("[Step 3/9] Settings 路径失败,尝试 SafeCenter 兜底")
            tryOpenAutoStartViaSafeCenter(successes, failures, logs)
        }
        if (ok) OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_SWITCH)
        return ok
    }

    open suspend fun tryOpenAutoStartViaSettings(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk >= 35) {
            openSettings()
            kotlinx.coroutines.delay(800L)
            clickTextWithScroll("应用", scrollLimit = 5)
            kotlinx.coroutines.delay(400L)
            navigateByHashPath(OppoBatteryPaths.AUTOSTART_ENTRY_PATH)
            kotlinx.coroutines.delay(1500L)
            clickTextWithScroll(appLabel, scrollLimit = 25)
            kotlinx.coroutines.delay(400L)
            return openSwitch(appLabel)
        } else {
            openAppDetails()
            kotlinx.coroutines.delay(800L)
            val switchTexts = listOf("允许自动启动", "允许应用自启动", "自动启动", "允许自启动", "开机自启动")
            for (s in switchTexts) { if (openSwitch(s)) return true }
            return false
        }
    }

    open suspend fun tryOpenAutoStartViaSafeCenter(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val components = listOf(
            android.content.ComponentName("com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"),
            android.content.ComponentName("com.oppo.safe",
                "com.oppo.safe.permission.startup.StartupAppListActivity"),
            android.content.ComponentName("com.oplus.safecenter",
                "com.oplus.safecenter.permission.startup.StartupAppListActivity"),
            android.content.ComponentName("com.coloros.safecenter",
                "com.coloros.safecenter.startupapp.view.StartupAppListActivity"),
            android.content.ComponentName("com.oplus.safecenter",
                "com.oplus.safecenter.startupapp.view.StartupAppListActivity")
        )
        for (c in components) {
            try {
                val i = android.content.Intent().setComponent(c)
                    .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(i)
                kotlinx.coroutines.delay(1200L)
                val pkg = try { service?.rootInActiveWindow?.packageName?.toString() ?: "" } catch (_: Exception) { "" }
                if (pkg.contains("safecenter") || pkg.contains("oppo.safe")) {
                    logs.add("[Step 3/9] SafeCenter 已开(${c.packageName})")
                    clickTextWithScroll(appLabel, scrollLimit = 25)
                    kotlinx.coroutines.delay(400L)
                    return openSwitch(appLabel)
                }
            } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (_: Exception) { continue }
        }
        failures.add("[Step 3/9] SafeCenter 全部 ComponentName 失败")
        return false
    }

    open suspend fun runBackgroundSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        openAppDetails()
        kotlinx.coroutines.delay(800L)
        val batteryEntries = listOf("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池")
        var entered = false
        for (t in batteryEntries) { if (clickText(t)) { entered = true; break } }
        if (!entered) {
            failures.add("[Step 3/9] 未找到耗电管理入口")
            return false
        }
        kotlinx.coroutines.delay(800L)
        val bgTexts = listOf(
            "完全允许后台行为", "允许应用后台行为", "允许完全后台行为",
            "允许后台运行", "完全后台行为", "后台运行", "允许后台活动"
        )
        for (t in bgTexts) {
            if (openSwitch(t)) {
                clickText("允许") || clickText("确定")
                OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_BACKGROUND)
                return true
            }
        }
        failures.add("[Step 3/9] 后台开关未找到")
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 4 — 悬浮窗 ━━━━━━━━━━━━━━━━━

    open suspend fun executeStep4Overlay(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)) {
            logs.add("[Step 4/9] ⏭ 24h 内已完成,跳过"); return
        }
        logs.add("[Step 4/9] ▶ 悬浮窗权限开始")
        if (canDrawOverlaysNow()) {
            logs.add("[Step 4/9] ✓ 系统 canDrawOverlays=true,已有权限")
            successes.add("[Step 4/9] 悬浮窗已授权(前置)")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY); return
        }
        launchOverlaySettings()
        kotlinx.coroutines.delay(1200L)
        val ok = tryOpenOverlaySwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        } else {
            failures.add("[Step 4/9] 悬浮窗开关未点中")
        }
    }

    open fun canDrawOverlaysNow(): Boolean = android.provider.Settings.canDrawOverlays(context)

    open suspend fun launchOverlaySettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "launchOverlaySettings: ${e.message}")
        }
    }

    open suspend fun tryOpenOverlaySwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        clickTextWithScroll(appLabel, scrollLimit = 25)
        kotlinx.coroutines.delay(600L)
        val texts = listOf(
            "授予悬浮窗权限", "允许在其他应用上层显示", "在其他应用上层显示", "显示在其他应用上层",
            "允许显示悬浮窗", "显示悬浮窗"
        )
        for (t in texts) {
            if (openSwitch(t)) {
                clickText("允许")
                successes.add("[Step 4/9] 悬浮窗已开启($t)")
                return true
            }
        }
        if (clickText("允许")) {
            successes.add("[Step 4/9] 悬浮窗 fallback 允许")
            return true
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 5 — 应用列表(ColorOS 独有)━━━━━━━━━━━━━━━━━

    open suspend fun executeStep5AppList(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST)) {
            logs.add("[Step 5/9] ⏭ 24h 内已完成,跳过"); return
        }
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < 31) {
            logs.add("[Step 5/9] SDK=$sdk<31 manifest 自动授予,直接 mark")
            successes.add("[Step 5/9] AppList 自动授予")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST); return
        }
        logs.add("[Step 5/9] ▶ 读取应用列表开始(SDK=$sdk)")
        openAppDetails()
        kotlinx.coroutines.delay(800L)
        val ok = tryOpenAppListSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST)
        } else {
            failures.add("[Step 5/9] AppList 开关未点中")
        }
    }

    open suspend fun tryOpenAppListSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        clickTextWithScroll("权限管理", scrollLimit = 3) || clickTextWithScroll("权限", scrollLimit = 3)
        kotlinx.coroutines.delay(600L)
        val texts = listOf("读取已安装应用列表", "读取已安装应用", "获取已安装应用", "查看已安装应用", "应用列表")
        for (t in texts) {
            if (clickTextWithScroll(t, scrollLimit = 10)) {
                kotlinx.coroutines.delay(400L)
                if (clickText("允许")) { successes.add("[Step 5/9] AppList 允许点中"); return true }
            }
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 6 — 所有文件访问 ━━━━━━━━━━━━━━━━━

    open suspend fun executeStep6FileAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)) {
            logs.add("[Step 6/9] ⏭ 24h 内已完成,跳过"); return
        }
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk < 30) { logs.add("[Step 6/9] SDK=$sdk<30 不需要,跳过"); return }
        if (isExternalStorageManagerNow()) {
            successes.add("[Step 6/9] 已有 MANAGE_EXTERNAL_STORAGE")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS); return
        }
        logs.add("[Step 6/9] ▶ 所有文件访问开始")
        launchFileAccessSettings()
        kotlinx.coroutines.delay(1500L)
        val ok = tryToggleFileAccess(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP6_FILEACCESS)
        } else {
            failures.add("[Step 6/9] 所有文件访问未开启")
        }
    }

    open fun isExternalStorageManagerNow(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= 30) android.os.Environment.isExternalStorageManager() else true
    }

    open suspend fun launchFileAccessSettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "launchFileAccessSettings: ${e.message}")
        }
    }

    open suspend fun tryToggleFileAccess(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val switches = listOf(
            "授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限",
            "允许访问所有文件", "允许管理所有文件"
        )
        var toggled = false
        for (s in switches) { if (openSwitch(s)) { toggled = true; break } }
        if (!toggled) {
            for (b in listOf("开启", "Enable", "Turn on")) { if (clickText(b)) { toggled = true; break } }
        }
        if (!toggled) return false
        kotlinx.coroutines.delay(800L)
        val sdk = android.os.Build.VERSION.SDK_INT
        when {
            sdk in 29..31 -> listOf("确定", "OK", "允许", "Allow", "我知道了", "Got it").any { clickText(it) }
            sdk == 32 -> listOf("确定", "应用", "允许").any { clickText(it) }
            sdk == 33 -> { clickText("确定"); kotlinx.coroutines.delay(400L); clickText("允许") }
            sdk >= 34 -> listOf("允许", "授予权限", "确定").any { clickText(it) }
            else -> clickText("确定")
        }
        kotlinx.coroutines.delay(800L)
        val granted = isExternalStorageManagerNow()
        if (granted) successes.add("[Step 6/9] MANAGE_EXTERNAL_STORAGE 已获取")
        return granted
    }

    // ━━━━━━━━━━━━━━━━━ Step 7 — 关 OFF 通知渠道 ━━━━━━━━━━━━━━━━━

    open suspend fun executeStep7Notification(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)) {
            logs.add("[Step 7/9] ⏭ 24h 内已完成,跳过"); return
        }
        logs.add("[Step 7/9] ▶ 关闭 OFF 通知渠道开始")
        launchChannelSettings("OFF")
        kotlinx.coroutines.delay(800L)
        val ok = tryCloseOffChannelSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        } else {
            failures.add("[Step 7/9] OFF 通知关闭失败")
        }
    }

    open suspend fun launchChannelSettings(channelId: String) {
        try {
            val i = android.content.Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                .putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                .putExtra("android.provider.extra.CHANNEL_ID", channelId)
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "launchChannelSettings: ${e.message}")
        }
    }

    open suspend fun tryCloseOffChannelSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        repeat(6) {
            if (closeSwitch("允许通知")) {
                pressBack()
                successes.add("[Step 7/9] 允许通知 已关闭")
                return true
            }
            kotlinx.coroutines.delay(500L)
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 8 — 最近任务锁定 ━━━━━━━━━━━━━━━━━

    open suspend fun executeStep8RecentTaskLock(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP8_APPLOCK)) {
            logs.add("[Step 8/9] ⏭ 24h 内已完成,跳过"); return
        }
        val svc = service ?: run { failures.add("[Step 8/9] service=null"); return }
        logs.add("[Step 8/9] ▶ 最近任务锁定开始")

        try {
            val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)
            launch?.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            launch?.let { context.startActivity(it) }
            kotlinx.coroutines.delay(500L)
        } catch (e: kotlinx.coroutines.CancellationException) { throw e } catch (_: Exception) {}

        try { svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS) } catch (_: Exception) {}
        kotlinx.coroutines.delay(1200L)

        horizontalSwipeToActivate()
        kotlinx.coroutines.delay(500L)

        val ok = tryLockAppCard(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP8_APPLOCK)
        } else {
            failures.add("[Step 8/9] 未能锁定 app 卡片")
        }
    }

    protected suspend fun horizontalSwipeToActivate() {
        val svc = service ?: return
        val w = try { (context.resources.displayMetrics.widthPixels) } catch (_: Exception) { 1080 }
        val h = try { (context.resources.displayMetrics.heightPixels) } catch (_: Exception) { 2400 }
        val fromX = w * 0.8f; val toX = w * 0.2f; val y = h * 0.4f
        try {
            val path = android.graphics.Path().apply { moveTo(fromX, y); lineTo(toX, y) }
            val stroke = android.accessibilityservice.GestureDescription.StrokeDescription(path, 0, 400)
            val gesture = android.accessibilityservice.GestureDescription.Builder().addStroke(stroke).build()
            svc.dispatchGesture(gesture, null, null)
        } catch (_: Exception) {}
    }

    open suspend fun tryLockAppCard(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val svc = service ?: return false
        val lockTexts = listOf("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기")
        val alreadyLocked = listOf("解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제",
            "已锁定", "已鎖定", "Locked", "LOCKED")

        repeat(4) {
            val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return@repeat
            val cards = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null } ?: emptyList()
            for (card in cards) {
                val cardRoot: android.view.accessibility.AccessibilityNodeInfo =
                    try { card.parent } catch (_: Exception) { null } ?: card
                val cardTexts = OppoPageDetector.collectTexts(cardRoot)
                if (alreadyLocked.any { t -> cardTexts.any { it.contains(t) } }) {
                    successes.add("[Step 8/9] 已锁定($appLabel)")
                    return true
                }
                val moreNodes = try { cardRoot.findAccessibilityNodeInfosByText("更多") } catch (_: Exception) { null } ?: emptyList()
                for (m in moreNodes) {
                    val t = try { m.text?.toString() ?: "" } catch (_: Exception) { "" }
                    val desc = try { m.contentDescription?.toString() ?: "" } catch (_: Exception) { "" }
                    if (t == "更多" || desc == "更多") {
                        performClickOrAncestor(m); kotlinx.coroutines.delay(800L); break
                    }
                }
                for (lt in lockTexts) {
                    val found = try { (svc.rootInActiveWindow ?: root).findAccessibilityNodeInfosByText(lt) } catch (_: Exception) { null } ?: emptyList()
                    for (n in found) {
                        val t = try { n.text?.toString() ?: "" } catch (_: Exception) { "" }
                        if ("解" !in t && "已" !in t && performClickOrAncestor(n)) {
                            successes.add("[Step 8/9] 锁定按钮点中 '$lt'")
                            return true
                        }
                    }
                }
            }
            kotlinx.coroutines.delay(600L)
        }
        return false
    }
}
