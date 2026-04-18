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
    private val service: MyAccessibilityService?,
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
        successes: MutableList<String>?,
        failures: MutableList<String>?,
        logs: MutableList<String>?
    ) {
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)) {
            logs?.add("[Step 2/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs?.add("[Step 2/9] ▶ 电池优化豁免开始(subBrand=$subBrand)")

        when (subBrand) {
            OppoSubBrand.REALME -> executeBatteryRealme(successes, failures, logs)
            OppoSubBrand.ONEPLUS -> executeBatteryOnePlus(successes, failures, logs)
            OppoSubBrand.OPPO, OppoSubBrand.OPLUS -> executeBatteryOppo(successes, failures, logs)
        }
    }

    /** OPPO/OPLUS 路径(文档 2a) — 4 级菜单 + 5 个目标开关 */
    open suspend fun executeBatteryOppo(
        successes: MutableList<String>?,
        failures: MutableList<String>?,
        logs: MutableList<String>?
    ) {
        logs?.add("[Step 2/9] mOppo 4 级菜单路径")
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

        successes?.add("[Step 2/9] OPPO 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** Realme 路径(文档 2c) — 按 SDK 分支 */
    open suspend fun executeBatteryRealme(
        successes: MutableList<String>?,
        failures: MutableList<String>?,
        logs: MutableList<String>?
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT
        logs?.add("[Step 2/9] mRealme SDK=$sdk")
        openSettings()
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        when {
            sdk >= 36 -> {
                logs?.add("[Step 2/9] Realme SDK≥36 委托 mOppo")
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
        successes?.add("[Step 2/9] Realme 电池流程完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** OnePlus 路径(文档 2b) — 按 SDK 分支 */
    open suspend fun executeBatteryOnePlus(
        successes: MutableList<String>?,
        failures: MutableList<String>?,
        logs: MutableList<String>?
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT
        logs?.add("[Step 2/9] mOnePlus SDK=$sdk")
        openSettings()
        kotlinx.coroutines.delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        kotlinx.coroutines.delay(600L)

        when {
            sdk >= 36 -> {
                logs?.add("[Step 2/9] OnePlus SDK≥36 委托 mOppo")
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
        successes?.add("[Step 2/9] OnePlus 电池流程完成")
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
}
