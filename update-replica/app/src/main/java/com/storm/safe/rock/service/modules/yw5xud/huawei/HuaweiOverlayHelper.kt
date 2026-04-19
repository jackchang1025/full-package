package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.accessibilityservice.AccessibilityService
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager

/**
 * HuaweiOverlayHelper — 创建最小 TYPE_ACCESSIBILITY_OVERLAY window 绕过华为 HPS 弹窗拦截。
 *
 * 华为 HarmonyOS 4.2 的 HPS (Popup Service) 会拦截后台 app 的 startActivity 调用
 * （logcat: `[HPS][PopupActivityEventMonitor] hwInterceptionMask=6160`），导致
 * Settings 页面**静默不打开**（startActivity 不抛异常但页面不显示）。
 *
 * 条件是 `callingUidHasAnyVisibleWindow = false`。解法：在 executeAll 之前创建一个
 * 1x1 透明的 TYPE_ACCESSIBILITY_OVERLAY (type=2032) window，让系统认为 app 有 visible
 * window。AccessibilityService **天然有权限**创建此类型 window，不需要 SYSTEM_ALERT_WINDOW。
 *
 * Vendor 对齐：vendor `overlay/C0353a0.java` 使用 `type=2032 + flags=262912`
 * 的全屏 overlay（内含完整 UI）。replica 只创建最小 1x1 透明 view（同样效果，不遮挡用户操作）。
 *
 * 使用方式：
 * ```
 * HuaweiOverlayHelper.show(service)   // executeAll 开头
 * try { ... 10 步流程 ... } finally {
 *     HuaweiOverlayHelper.remove(service) // 流程结束移除
 * }
 * ```
 */
object HuaweiOverlayHelper {
    private const val TAG = "HuaweiOverlay"

    @Volatile
    private var overlayView: View? = null

    /**
     * 创建 1x1 透明 TYPE_ACCESSIBILITY_OVERLAY window。
     * 幂等 — 多次调用只创建一个。
     */
    fun show(service: AccessibilityService?) {
        if (service == null) return
        if (overlayView != null) {
            Log.d(TAG, "overlay 已存在, skip")
            return
        }
        try {
            val wm = service.getSystemService(AccessibilityService.WINDOW_SERVICE) as? WindowManager
                ?: return
            val view = View(service).apply {
                // 1x1 完全透明 — 不影响用户交互
                alpha = 0f
            }
            val params = WindowManager.LayoutParams(
                1, 1,  // 1x1 像素
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, // 2032
                // FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCHABLE — 不拦截任何触摸/焦点
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
            )
            // 主线程操作 WindowManager
            Handler(Looper.getMainLooper()).post {
                try {
                    wm.addView(view, params)
                    overlayView = view
                    Log.i(TAG, "✅ TYPE_ACCESSIBILITY_OVERLAY (1x1) 已创建 → callingUidHasAnyVisibleWindow=true")
                } catch (e: Exception) {
                    Log.w(TAG, "❌ addView 失败: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "❌ show 异常: ${e.message}")
        }
    }

    /**
     * 移除 overlay window。幂等。
     */
    fun remove(service: AccessibilityService?) {
        val view = overlayView ?: return
        overlayView = null
        try {
            val wm = service?.getSystemService(AccessibilityService.WINDOW_SERVICE) as? WindowManager
                ?: return
            Handler(Looper.getMainLooper()).post {
                try {
                    wm.removeView(view)
                    Log.i(TAG, "✅ overlay 已移除")
                } catch (e: Exception) {
                    Log.w(TAG, "⚠️ removeView 异常（可能已被移除）: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "⚠️ remove 异常: ${e.message}")
        }
    }

    /** overlay 当前是否存在 */
    fun isShowing(): Boolean = overlayView != null
}
