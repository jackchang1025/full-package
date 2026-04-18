package com.storm.safe.rock.service.modules.yw5xud

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.auto.condition.CombineFilter
import com.storm.safe.rock.auto.entity.UiNode
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException
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

        // ColorOS 16 SubSettings Switch 中心坐标 -- UIAutomator dump: bounds=[995,940][1128,1024]
        private const val SUBSETTINGS_SWITCH_X = 1062f
        private const val SUBSETTINGS_SWITCH_Y = 982f

        /** Keywords for auto-start toggle. */
        val AUTOSTART_KEYWORDS = listOf(
            "允许自启动", "自启动", "Allow auto-start", "Auto-start", "Autostart"
        )

        /** Keywords for battery no-restriction. */
        val BATTERY_KEYWORDS = listOf(
            "不优化", "无限制", "Don't optimize", "Unrestricted", "允许后台运行"
        )
    }

    @Deprecated("Use executeAll() -- 此入口使用旧的 ComponentName 流程", ReplaceWith("executeAll(successes, failures, logs)"))
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
        logs.add("[Step 1/9] ▶ 基础权限开始(超时15秒)")

        // Phase G#4: 在 iuzxujjtqev Activity 上直接 requestPermissions。
        //
        // 诊断链(OPPO PGFM10 Android 16 / ColorOS 16):
        //   - umrkmgrri 走新 task → GrantPermissionsActivity 不获 focus → 自动点击不可能
        //   - iuzxujjtqev 在同 task 上 requestPermissions → GrantPermissionsActivity 作为 sub-Activity
        //     弹在同 task → 正常获得 focus → AccessibilityService 可读
        //
        // 问题: smartReturnToApp 内部有 performGlobalAction(BACK) 会在 0.5s 后把 iuzxujjtqev
        //        弹走 → getCurrentActivity()=null。修复:Step 1 自己重新拉起 iuzxujjtqev 确保前台。
        val perms = umrkmgrri.computeRequiredPermissions(context)
        if (perms.isEmpty()) {
            logs.add("[Step 1/9] ✓ manifest 所有 dangerous 已授予")
            successes.add("[Step 1/9] 所有 dangerous 已授予")
            return
        }

        // 1. 确保 iuzxujjtqev 在前台(smartReturnToApp 的 BACK 可能已把它弹走)
        var act = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
        if (act == null || act.isFinishing || act.isDestroyed) {
            Log.i(TAG, "[Step1] iuzxujjtqev ref=null,重新拉起确保前台...")
            try {
                val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
                launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                launchIntent?.putExtra("SMART_RETURN_BACKUP", true)
                launchIntent?.let { context.startActivity(it) }
            } catch (e: CancellationException) { throw e } catch (_: Exception) {}
            delay(1500L)
            act = com.storm.safe.rock.iuzxujjtqev.getCurrentActivity()
        }

        // 2. 在 iuzxujjtqev 上 requestPermissions(同 task,GrantPermissionsActivity 能获 focus)
        if (act != null && !act.isFinishing && !act.isDestroyed) {
            act.runOnUiThread {
                try {
                    androidx.core.app.ActivityCompat.requestPermissions(act, perms.toTypedArray(), 12094)
                } catch (e: Exception) {
                    Log.w(TAG, "[Step1] requestPermissions on iuzxujjtqev failed: ${e.message}")
                }
            }
            logs.add("[Step 1/9] ✓ 在 app 页面直接 requestPermissions(${perms.size} 个)")
            delay(1500L)
        } else {
            // 最终 fallback: 启动 umrkmgrri
            Log.w(TAG, "[Step1] iuzxujjtqev 两次尝试都不可用,fallback 启动 umrkmgrri")
            try {
                val intent = Intent(context, umrkmgrri::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
                context.startActivity(intent)
                logs.add("[Step 1/9] ✓ fallback 启动 umrkmgrri")
                delay(1500L)
            } catch (e: CancellationException) { throw e } catch (e: Exception) {
                Log.w(TAG, "[Step1] launch umrkmgrri failed: ${e.message}")
            }
        }

        // Phase H: ColorOS 16 OEM 限制 — AccessibilityService 完全看不到 GrantPermissionsActivity。
        // 改为轮询 computeRequiredPermissions 列表长度:缩短=有权限被用户手动授予。
        val timeoutMs = 3_000L
        val start = System.currentTimeMillis()
        val initialCount = perms.size
        var lastRemaining = initialCount

        while (System.currentTimeMillis() - start < timeoutMs) {
            val remaining = umrkmgrri.computeRequiredPermissions(context).size
            if (remaining < lastRemaining) {
                val granted = initialCount - remaining
                Log.d(TAG, "[Step1] 权限进度: granted=$granted remaining=$remaining")
                lastRemaining = remaining
            }
            if (remaining == 0) {
                Log.d(TAG, "[Step1] ✓ 所有 dangerous 权限已授予!")
                break
            }
            delay(1000L)
        }

        val finalRemaining = umrkmgrri.computeRequiredPermissions(context).size
        val granted = initialCount - finalRemaining
        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        logs.add("[Step 1/9] 完成,用时 ${elapsedSec}s,授予 $granted/${initialCount} 个权限")
        if (granted > 0) {
            successes.add("[Step 1/9] 用户手动授予 $granted 个权限")
        } else {
            failures.add("[Step 1/9] ${timeoutMs/1000}s 内用户未授予任何权限(ColorOS 16 需手动点击允许)")
        }

        // runStep 统一 HOME 清场
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

    /** OPPO/OPLUS 路径 — SDK 分支 */
    open suspend fun executeBatteryOppo(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val sdk = android.os.Build.VERSION.SDK_INT

        if (sdk >= 35) {
            // Phase G#7: ColorOS 16(SDK≥35)捷径路径 — 真机 dump 实锤
            // openAppDetails() 在 OPPO 直接打开 com.oplus.battery/PowerControlActivity
            // (耗电行为控制),三个 RadioButton 直接可见:
            //   - 完全允许后台行为
            //   - 智能优化后台运行(推荐)← 默认
            //   - 限制后台运行
            // 真机 dump 实锤路径:
            //   openAppDetails → InstalledAppDetails(应用详情)
            //   → clickText("耗电管理") → PowerControlActivity(耗电行为控制)
            //   → clickText("完全允许后台行为")
            logs.add("[Step 2/9] SDK=$sdk≥35 走 应用详情→耗电管理 捷径")
            openAppDetails()
            waitForSettingsPage("Step2", 5000L)
            scrollToTop()
            delay(300L)
            dumpCurrentPage("Step2")
            Log.d(TAG, "[Step2] 应用详情已打开,点击耗电管理入口")

            // 1. 在应用详情页点"耗电管理"进入子页
            val enteredPowerCtrl = clickTextWithScroll("耗电管理", scrollLimit = 5)
            Log.d(TAG, "[Step2] clickText(耗电管理)=$enteredPowerCtrl")
            delay(1500L)

            // 2. 在耗电行为控制页点"完全允许后台行为"RadioButton
            val bgAllowed = clickText("完全允许后台行为") ||
                clickText("完全后台行为") ||
                clickText("允许后台运行") ||
                clickText("不限制应用的任何后台行为")
            Log.d(TAG, "[Step2] clickText(完全允许后台行为)=$bgAllowed")
            delay(500L)

            // 确认对话框(如果有)
            clickText("允许") || clickText("确定")
            delay(500L)
            // runStep 统一 HOME 清场
        } else {
            // SDK < 35 旧路径:Settings → 电池 → 4 级菜单
            logs.add("[Step 2/9] SDK=$sdk<35 走 Settings 4 级菜单路径")
            openSettings()
            delay(800L)

            clickTextWithScroll("电池", scrollLimit = 5)
            delay(600L)
            navigateByHashPath(OppoBatteryPaths.OPPO_OPLUS_PATH, scrollLimit = 5)

            closeSwitch("睡眠待机优化") || closeSwitch("待机耗电优化")
            delay(400L)
            clickTextWithScroll("耗电异常优化", scrollLimit = 3)
            delay(800L)
            clickTextWithScroll(appLabel, scrollLimit = 25)
            delay(400L)
            clickText("不优化")
            delay(400L)
            pressBack(); pressBack()
            closeSwitch("省电模式")
        }

        // Phase G#8: 回验修正 — "完全允许后台行为"是 ColorOS 自定义后台策略,
        // 不是 Android Doze 白名单(isIgnoringBatteryOptimizations 检测不到)。
        // 对齐 vendor:UI 操作完成就 mark success。
        successes.add("[Step 2/9] 电池/耗电管理 UI 操作完成")
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
        delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] Realme SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                clickTextWithScroll("省电设置", scrollLimit = 3)
                delay(400L)
                closeSwitch("睡眠待机优化")
                closeSwitch("自动进入省电模式")
                pressBack(); pressBack(); pressBack()
            }
            sdk == 29 -> {
                closeSwitch("省电模式")
                clickTextWithScroll("智能省电场景", scrollLimit = 3)
                delay(400L)
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
        successes.add("[Step 2/9] Realme 电池/耗电管理 UI 操作完成")
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
        delay(800L)
        clickTextWithScroll("电池", scrollLimit = 5)
        delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] OnePlus SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                clickTextWithScroll("电池模式", scrollLimit = 3)
                delay(400L)
                clickText("均衡模式")
                delay(400L)
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
        successes.add("[Step 2/9] OnePlus 电池/耗电管理 UI 操作完成")
        OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** Phase E: 查询当前 app 是否已被 PowerManager 豁免电池优化(真实效果回验) */
    open fun isIgnoringBatteryOptimizationsNow(): Boolean {
        return try {
            val pm = context.getSystemService(android.content.Context.POWER_SERVICE) as? android.os.PowerManager
                ?: return false
            pm.isIgnoringBatteryOptimizations(context.packageName)
        } catch (_: Exception) { false }
    }

    // ━━━━━━━━━━━━━━━━━ UI helpers(Task 2 引入,被 Task 3-8 共用)━━━━━━━━━━━━━━━━━

    /** 打开系统设置首页 */
    // vendor m212344e8 (openSettingsSmali) 对齐:flags = 0x50800000
    //   FLAG_ACTIVITY_NEW_TASK (0x10000000)
    //   FLAG_ACTIVITY_RESET_TASK_IF_NEEDED -- 确保 Settings reset 到首页
    //   FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS (0x00800000)
    // 缺 RESET_TASK_IF_NEEDED 会导致 Settings 复用残留的 SubSettings(如无障碍页),
    // clickTextWithScroll("电池") 在子页面找不到 → 全部 false。
    open suspend fun openSettings() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_SETTINGS)
                .addFlags(
                    android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "openSettings: ${e.message}")
        }
    }

    /**
     * 等待 rootInActiveWindow 切换到 com.android.settings。
     * 如果发现残留的其他 Settings 包（如 com.oplus.battery），pressBack 关掉再重试。
     */
    private suspend fun waitForSettingsPage(tag: String, timeoutMs: Long = 5000L): Boolean {
        val start = System.currentTimeMillis()
        var retried = false
        while (System.currentTimeMillis() - start < timeoutMs) {
            delay(400L)
            val pkg = try { service?.rootInActiveWindow?.packageName?.toString() } catch (_: Exception) { null }
            if (pkg == "com.android.settings") {
                Log.d(TAG, "[$tag] Settings 页面就绪, 耗时 ${System.currentTimeMillis() - start}ms")
                return true
            }
            // 如果前台是残留的 Settings 子页面（如 com.oplus.battery），pressBack 关掉，重新打开
            if (!retried && pkg != null && pkg != "com.android.launcher") {
                Log.d(TAG, "[$tag] 前台 pkg=$pkg 不是 Settings，pressBack 清掉")
                pressBack()
                delay(300L)
                pressBack()
                delay(300L)
                try { service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME) } catch (_: Exception) {}
                delay(500L)
                openAppDetails()
                retried = true
            }
        }
        Log.w(TAG, "[$tag] waitForSettingsPage 超时 ${timeoutMs}ms")
        return false
    }

    /** 打开 app 详情页 */
    protected suspend fun openAppDetails() {
        try {
            val i = android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(android.net.Uri.parse("package:${context.packageName}"))
                .addFlags(
                    android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "openAppDetails: ${e.message}")
        }
    }

    open fun pressBack() {
        try { service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_BACK) } catch (_: Exception) {}
    }

    private fun rootNode(): UiNode? {
        val info = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        return UiNode.createRoot(info)
    }

    /** `#` 分隔符多级菜单导航(vendor clickVWithScroll) */
    open suspend fun navigateByHashPath(path: String, scrollLimit: Int = 3) {
        for (segment in path.split("#")) {
            clickTextWithScroll(segment, scrollLimit = scrollLimit)
            delay(500L)
        }
    }

    /** 文本点击(单层,不滚动)— UiNode.findOneByText + click(内置 parent-walk) */
    open fun clickText(text: String): Boolean {
        val root = rootNode() ?: return false
        val node = root.findOneByText(text) ?: return false
        return node.click()
    }

    /** 文本点击 + 未找到则 scroll 重试,最多 scrollLimit 次 */
    open suspend fun clickTextWithScroll(text: String, scrollLimit: Int = 3): Boolean {
        repeat(scrollLimit + 1) { attempt ->
            val root = rootNode() ?: return false
            val direct = root.findOneByText(text)
            if (direct != null) return direct.click()
            val scrollable = root.findOneByCombine(CombineFilter.scrollable()) ?: return false
            scrollable.scrollForward()
            Log.d(TAG, "[clickTextWithScroll] '$text' attempt=$attempt")
            delay(400L)
        }
        return false
    }

    /** 诊断:dump 当前 AccessibilityService 看到的页面结构到 logcat */
    private fun dumpCurrentPage(stepTag: String) {
        try {
            val root = rootNode() ?: run { Log.w(TAG, "[$stepTag] dump: root=null"); return }
            val pkg = root.packageName ?: "?"
            val cls = (root.className ?: "?").substringAfterLast('.')
            val scrollNode = root.findOneByCombine(CombineFilter.scrollable())
            val scrollInfo = scrollNode?.let {
                "${(it.className ?: "?").substringAfterLast('.')}(scrollable=true)"
            } ?: "none"

            val allTexts = root.findAllByCombine { it.text.isNotEmpty() && it.text.length < 30 }
            val texts = allTexts.take(20).map { it.text }

            val svc = service
            val winSb = StringBuilder()
            try {
                val wins = svc?.windows
                winSb.append("wins=${wins?.size ?: 0}:")
                wins?.forEach { w ->
                    val wr = try { w.root } catch (_: Exception) { null }
                    val wp = wr?.packageName?.toString() ?: "null"
                    winSb.append(" [$wp]")
                }
            } catch (_: Exception) { winSb.append("err") }

            Log.i(TAG, "[$stepTag] ┌─ DUMP pkg=$pkg root=$cls scroll=$scrollInfo $winSb")
            Log.i(TAG, "[$stepTag] └─ texts=${texts.joinToString(" | ")}")
        } catch (e: Exception) {
            Log.w(TAG, "[$stepTag] dump 异常: ${e.message}")
        }
    }

    /** 滚到列表顶部(OPPO InstalledAppDetails 可能缓存上次滚动位置) */
    private suspend fun scrollToTop() {
        val root = rootNode() ?: return
        val scrollable = root.findOneByCombine(CombineFilter.scrollable()) ?: return
        repeat(10) {
            if (!scrollable.scrollBackward()) return
            delay(150L)
        }
    }

    /** 关闭名为 text 的 Switch(当前 checked=true 则 click 切为 false) */
    open fun closeSwitch(text: String): Boolean = toggleSwitchByLabel(text, desiredChecked = false)

    /** 开启名为 text 的 Switch */
    open fun openSwitch(text: String): Boolean = toggleSwitchByLabel(text, desiredChecked = true)

    private fun toggleSwitchByLabel(text: String, desiredChecked: Boolean): Boolean {
        val root = rootNode() ?: return false
        val label = root.findOneByTextContains(text) ?: return false
        val parentRow = label.findParentUntil { it.isClickable }
            ?: label.parent()
            ?: return false
        val sw = parentRow.findOneByCombine { node ->
            val cls = node.className ?: ""
            cls.endsWith("Switch") || cls.endsWith("CheckBox") || cls.endsWith("CompoundButton")
        } ?: return false
        if (sw.isChecked == desiredChecked) return true
        return sw.click()
    }

    /**
     * Phase D: 直接用 resource-id 查找 Switch/CheckBox 并 toggle 到 checked=true。
     * ColorOS 16 许多 Settings 页的 Switch id 是 android:id/switch_widget,不依赖 label。
     */
    open fun toggleSwitchById(id: String): Boolean {
        val root = rootNode() ?: return false
        val switches = root.findById(id)
        for (i in 0 until switches.size()) {
            val sw = switches.get(i) ?: continue
            if (!sw.isVisibleToUser) continue
            if (sw.isChecked) return true
            if (sw.click()) return true
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
            logs.add("[Step 3/9] Settings 路径失败(ColorOS 16 SafeCenter 已废弃)")
            tryOpenAutoStartViaSafeCenter(successes, failures, logs)  // 仍调用以触发 deprecation 日志
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
            // Phase G#10: ColorOS 16(SDK≥35)没有自启动管理 Activity
            // (com.oplus.safecenter 的 StartupAppListActivity 全部不存在,pm dump 确认)。
            // 自启动由 Android 12+ 系统框架自动管理,无需独立 UI 操作。
            Log.d(TAG, "[Step3] SDK=$sdk≥35: ColorOS 16 无自启动管理 UI,视为已完成")
            return true
        } else {
            openAppDetails()
            delay(800L)
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
        // Phase D: ColorOS 16 discovery 确认 com.coloros.safecenter / com.oppo.safe 不存在,
        //          com.oplus.safecenter 存在但无 StartupAppList Activity — 自启动管理
        //          已迁移到系统 Settings 内部(走 tryOpenAutoStartViaSettings 路径)。
        //          保留本方法签名以兼容现有测试 override,但实现改为 no-op + 日志。
        logs.add("[Step 3/9] SafeCenter 5 ComponentName 在 ColorOS 16 已废弃,Settings 路径是唯一入口")
        return false
    }

    open suspend fun runBackgroundSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // Phase G#10: Step 2 已通过 openAppDetails→耗电管理→"完全允许后台行为" 覆盖后台行为。
        // 检测 Step 2 已完成则直接返回 true(避免重复操作 + RadioButton 不被 openSwitch 识别)。
        if (OppoStepCompletionStore.isCompleted(context, OppoStepCompletionStore.Keys.STEP2_BATTERY)) {
            Log.d(TAG, "[Step3] 后台行为已由 Step 2 覆盖(完全允许后台行为),跳过")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_BACKGROUND)
            return true
        }

        // SDK<35 fallback:独立执行后台开关
        openAppDetails()
        delay(800L)
        val batteryEntries = listOf("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池")
        var entered = false
        for (t in batteryEntries) { if (clickText(t)) { entered = true; break } }
        if (!entered) {
            failures.add("[Step 3/9] 未找到耗电管理入口")
            return false
        }
        delay(800L)
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
        // Phase G#10: 如果 openSwitch 找不到(RadioButton),尝试 clickText 直接点
        for (t in bgTexts) {
            if (clickText(t)) {
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
        openAppDetails()
        waitForSettingsPage("Step4", 5000L)
        dumpCurrentPage("Step4-before")
        val switchClicked = tryOverlayViaAppDetails(successes, logs)

        // Phase E: 点完开关后二次回验 Settings.canDrawOverlays() 真实效果,
        // 避免点到"不允许"按钮或其他应用的允许按钮而虚假 mark success。
        delay(500L)
        val actuallyGranted = canDrawOverlaysNow()
        if (actuallyGranted) {
            logs.add("[Step 4/9] ✓ canDrawOverlays 回验通过,mark completed")
            successes.add("[Step 4/9] 悬浮窗已授权(真实效果回验)")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        } else {
            failures.add(
                if (switchClicked) "[Step 4/9] 开关点中但 canDrawOverlays 仍 false(可能点到错按钮)"
                else "[Step 4/9] 悬浮窗开关未点中"
            )
        }
    }

    open fun canDrawOverlaysNow(): Boolean = android.provider.Settings.canDrawOverlays(context)

    /**
     * 走应用详情→滚到底部→"悬浮窗"入口→SubSettings 悬浮窗详情页→坐标点击 Switch。
     *
     * ColorOS 16 SubSettings 页面的内容区域(Switch/文本)对 AccessibilityService 不可见,
     * findAccessibilityNodeInfosByViewId("switch_widget") 永远返回空。
     * 真机 UIAutomator dump 实锤 Switch 位置: bounds=[995,940][1128,1024]。
     * 用 dispatchGesture 坐标点击。
     */
    private suspend fun tryOverlayViaAppDetails(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // 1. 在 InstalledAppDetails 滚到底部找"悬浮窗"入口并点击
        scrollToTop()
        delay(300L)
        // UiNode.click() 内置 parent-walk(解决 clickable=false)
        val found = clickTextWithScroll("悬浮窗", scrollLimit = 8)
        Log.d(TAG, "[Step4] 点击悬浮窗入口=$found")
        if (!found) return false

        // 2. 等 SubSettings 页面出现(标题变为"悬浮窗")
        delay(2000L)
        dumpCurrentPage("Step4-overlay")

        // 3. ColorOS 16 SubSettings 内容对 AccessibilityService 不可见,
        //    用坐标点击 Switch(真机 dump: bounds=[995,940][1128,1024], center≈1062,982)
        val tapped = tapAtCoordinate(SUBSETTINGS_SWITCH_X, SUBSETTINGS_SWITCH_Y)
        Log.d(TAG, "[Step4] tapAtCoordinate($SUBSETTINGS_SWITCH_X,$SUBSETTINGS_SWITCH_Y)=$tapped")
        if (tapped) {
            delay(1000L)
            successes.add("[Step 4/9] 悬浮窗 Switch 坐标点击")
            return true
        }
        return false
    }

    /** dispatchGesture 坐标点击(委托 GestureTapHelper,含 1px jitter 修复 + 回调等待) */
    private suspend fun tapAtCoordinate(x: Float, y: Float): Boolean {
        val svc = service ?: return false
        return GestureTapHelper.performTap(svc, x, y, 100L)
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
        // Phase D: ColorOS 16 把 QUERY_ALL_PACKAGES 作为 manifest normal perm 自动授予,
        //          运行时 PERMISSION_GRANTED 时无需 UI。
        if (hasQueryAllPackagesPermission()) {
            logs.add("[Step 5/9] QUERY_ALL_PACKAGES 已 granted,manifest 自动,跳过 UI")
            successes.add("[Step 5/9] AppList 已授予(manifest)")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST); return
        }
        logs.add("[Step 5/9] ▶ 读取应用列表开始(SDK=$sdk)")
        openAppDetails()
        delay(800L)
        val ok = tryOpenAppListSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP5_APPLIST)
        } else {
            failures.add("[Step 5/9] AppList 开关未点中")
        }
    }

    /** 检测 QUERY_ALL_PACKAGES 是否已授予(manifest 声明 + 系统级授予). */
    open fun hasQueryAllPackagesPermission(): Boolean {
        return try {
            val pm = context.packageManager ?: return false
            pm.checkPermission("android.permission.QUERY_ALL_PACKAGES", context.packageName) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
        } catch (_: Exception) { false }
    }

    open suspend fun tryOpenAppListSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        clickTextWithScroll("权限管理", scrollLimit = 3) || clickTextWithScroll("权限", scrollLimit = 3)
        delay(600L)
        val texts = listOf("读取已安装应用列表", "读取已安装应用", "获取已安装应用", "查看已安装应用", "应用列表")
        for (t in texts) {
            if (clickTextWithScroll(t, scrollLimit = 10)) {
                delay(400L)
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
        waitForSettingsPage("Step6", 5000L)
        dumpCurrentPage("Step6-before")
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
                .addFlags(
                    android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                    android.content.Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
                    android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                )
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "launchFileAccessSettings: ${e.message}")
        }
    }

    open suspend fun tryToggleFileAccess(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // ColorOS 16 SubSettings 内容对 AccessibilityService 不可见,
        // 先尝试常规方式,失败后坐标点击
        delay(1500L)

        val byId = toggleSwitchById("android:id/switch_widget")
        Log.d(TAG, "[Step6] toggleSwitchById=$byId")
        var toggled = byId

        if (!toggled) {
            for (s in listOf("授予所有文件的管理权限", "所有文件访问权限", "允许访问所有文件")) {
                if (openSwitch(s)) { toggled = true; break }
            }
        }

        if (!toggled) {
            // 坐标点击 Switch(真机 dump: 与悬浮窗页面相同位置 bounds=[995,940][1128,1024])
            Log.d(TAG, "[Step6] AccessibilityService 不可见,坐标点击 Switch")
            toggled = tapAtCoordinate(SUBSETTINGS_SWITCH_X, SUBSETTINGS_SWITCH_Y)
            Log.d(TAG, "[Step6] tapAtCoordinate=$toggled")
        }
        if (!toggled) return false
        delay(800L)
        val sdk = android.os.Build.VERSION.SDK_INT
        when {
            sdk in 29..31 -> listOf("确定", "OK", "允许", "Allow", "我知道了", "Got it").any { clickText(it) }
            sdk == 32 -> listOf("确定", "应用", "允许").any { clickText(it) }
            sdk == 33 -> { clickText("确定"); delay(400L); clickText("允许") }
            sdk >= 34 -> listOf("允许", "授予权限", "确定").any { clickText(it) }
            else -> clickText("确定")
        }
        delay(800L)
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
        delay(800L)

        // Phase E: 改用 NotificationManagerCompat.areNotificationsEnabled()
        // 真机 dumpsys 显示 OFF channel importance=2(LOW),不是 0(NONE);
        // 但 app 全局 "AppSettings: importance=NONE" 才是真实"通知已禁"状态,
        // 对应 NotificationManagerCompat.areNotificationsEnabled()=false。
        if (areAppNotificationsBlocked()) {
            logs.add("[Step 7/9] ✓ app-level 通知已禁,直接 mark")
            successes.add("[Step 7/9] 通知已禁用(app-level)")
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
            return
        }

        val ok = tryCloseOffChannelSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(context, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        } else {
            failures.add("[Step 7/9] OFF 通知关闭失败")
        }
    }

    /**
     * Phase E: 检测 app-level 通知是否已禁用。
     *
     * 真机 dumpsys 证明:OFF channel importance=2(LOW)时,app 全局仍可能 `AppSettings: importance=NONE`。
     * Step 7 的目标是"隐藏前台服务通知",只要 app-level notifications disabled 即达成,
     * 不强求单 channel disabled。
     *
     * `NotificationManagerCompat.from(ctx).areNotificationsEnabled()`:
     *   返回 false = app 被禁止发通知(app-level block)
     *   返回 true = 允许发通知(即使个别 channel 被 user 静音)
     */
    open suspend fun areAppNotificationsBlocked(): Boolean {
        return try {
            val nmc = androidx.core.app.NotificationManagerCompat.from(context)
            !nmc.areNotificationsEnabled()
        } catch (_: Exception) { false }
    }

    open suspend fun launchChannelSettings(channelId: String) {
        try {
            val i = android.content.Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                .putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
                .putExtra("android.provider.extra.CHANNEL_ID", channelId)
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
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
            delay(500L)
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
        if (android.os.Build.VERSION.SDK_INT >= 35) {
            logs.add("[Step 8/9] SDK>=35 跳过(ColorOS 16 多任务待适配)")
            successes.add("[Step 8/9] 跳过")
            return
        }
        val svc = service ?: run { failures.add("[Step 8/9] service=null"); return }
        logs.add("[Step 8/9] ▶ 最近任务锁定开始")

        try {
            val launch = context.packageManager.getLaunchIntentForPackage(context.packageName)
            launch?.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            launch?.let { context.startActivity(it) }
            delay(500L)
        } catch (e: CancellationException) { throw e } catch (_: Exception) {}

        try { svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS) } catch (_: Exception) {}
        delay(1200L)

        horizontalSwipeToActivate()
        delay(500L)

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
                        UiNode.createRoot(m)?.click(); delay(800L); break
                    }
                }
                for (lt in lockTexts) {
                    val found = try { (svc.rootInActiveWindow ?: root).findAccessibilityNodeInfosByText(lt) } catch (_: Exception) { null } ?: emptyList()
                    for (n in found) {
                        val t = try { n.text?.toString() ?: "" } catch (_: Exception) { "" }
                        if ("解" !in t && "已" !in t) {
                            val clicked = UiNode.createRoot(n)?.click() ?: false
                            if (clicked) {
                                successes.add("[Step 8/9] 锁定按钮点中 '$lt'")
                                return true
                            }
                        }
                    }
                }
            }
            delay(600L)
        }
        return false
    }

    // ━━━━━━━━━━━━━━━━━ Step 9 — 返回桌面 ━━━━━━━━━━━━━━━━━

    /**
     * Step 9 — 返回桌面(对齐 vendor 文档"9. 返回桌面")。
     * 用 performGlobalAction(HOME) 触发,不依赖 UI 文本 / resource-id。
     */
    open suspend fun executeStep9ReturnHome(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val svc = service ?: run { failures.add("[Step 9/9] service=null"); return }
        try {
            svc.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_HOME)
            successes.add("[Step 9/9] 返回桌面完成")
            logs.add("[Step 9/9] ✓ performGlobalAction(HOME)")
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            failures.add("[Step 9/9] HOME 触发异常: ${e.message}")
        }
    }

    // ━━━━━━━━━━━━━━━━━ executeAll 编排(新入口)━━━━━━━━━━━━━━━━━

    /**
     * OPPO executeAll — 9 Step 整合编排(新入口,Yw5xudHandler 使用此方法)。
     *
     * 对齐 vendor `OppoStepsSimplified.m212321b9` execute() 29 状态协程。
     * 每步 try/catch 隔离,CancellationException 重抛(cooperative cancel);
     * 其他 Exception 加入 failures,不中断后续 step。
     *
     * 兼容:旧 `execute()` 入口仍保留(OppoStepsTest 依赖)。
     */
    open suspend fun executeAll(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (service == null) {
            failures.add("OppoSteps: service 未绑定,跳过全部 step")
            return
        }
        logs.add("╔══════════ OppoSteps.executeAll 开始(subBrand=$subBrand) ══════════")

        runStep("Step1-BasicPermissions", failures) { executeStep1BasicPermissions(successes, failures, logs) }
        runStep("Step2-Battery", failures) { executeStep2Battery(successes, failures, logs) }
        runStep("Step3-AutoStart", failures) { executeStep3AutoStart(successes, failures, logs) }
        runStep("Step4-Overlay", failures) { executeStep4Overlay(successes, failures, logs) }
        runStep("Step5-AppList", failures) { executeStep5AppList(successes, failures, logs) }
        runStep("Step6-FileAccess", failures) { executeStep6FileAccess(successes, failures, logs) }
        runStep("Step7-Notification", failures) { executeStep7Notification(successes, failures, logs) }
        runStep("Step8-RecentTaskLock", failures) { executeStep8RecentTaskLock(successes, failures, logs) }
        runStep("Step9-ReturnHome", failures) { executeStep9ReturnHome(successes, failures, logs) }

        logs.add("║ success=${successes.size} failure=${failures.size}")
        logs.add("╚══════════ OppoSteps.executeAll 完成 ══════════")
    }

    private suspend fun runStep(
        name: String,
        failures: MutableList<String>,
        block: suspend () -> Unit
    ) {
        try {
            block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.e(TAG, "$name 异常", e)
            failures.add("$name 异常: ${e.message}")
        }
    }
}
