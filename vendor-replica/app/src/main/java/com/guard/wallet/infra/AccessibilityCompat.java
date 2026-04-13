package com.guard.wallet.infra;
import com.guard.wallet.core.AppUtils;

import android.graphics.Rect;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityEvent;

/**
 * 无障碍兼容工具 — 提供 AccessibilityEvent 复制和 WindowMetrics 获取的版本兼容封装。
 * 包含 API 30+ 的 WindowMetrics 操作和 AccessibilityEvent 深拷贝。
 *
 * vendor 原始路径: a0/h.java
 */
public class AccessibilityCompat {

    /**
     * 获取当前窗口度量信息 (API 30+)。
     * @param wm WindowManager 实例
     * @return WindowMetrics 或 null（低版本 API）
     */
    public static WindowMetrics getWindowMetrics(WindowManager wm) {
        if (wm == null) return null;
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            return wm.getCurrentWindowMetrics();
        }
        return null;
    }

    /**
     * 从 WindowMetrics 提取边界矩形 (API 30+)。
     * @param metrics WindowMetrics 实例
     * @return 边界 Rect，低版本返回空 Rect
     */
    public static Rect getMetricsBounds(WindowMetrics metrics) {
        if (metrics == null) return new Rect();
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            return metrics.getBounds();
        }
        return new Rect();
    }

    /**
     * 深拷贝 AccessibilityEvent (API 30+ 兼容)。
     * 复制事件类型、时间、包名、类名、内容描述和文本列表。
     */
    public static AccessibilityEvent copyEvent(AccessibilityEvent event) {
        if (event == null) return null;
        try {
            // On API 30+, create new event via constructor
            AccessibilityEvent copy = new AccessibilityEvent();
            copy.setEventType(event.getEventType());
            copy.setEventTime(event.getEventTime());
            copy.setPackageName(event.getPackageName());
            copy.setClassName(event.getClassName());
            copy.setContentDescription(event.getContentDescription());
            copy.getText().addAll(event.getText());
            return copy;
        } catch (Exception e) {
            AppUtils.s("AccessibilityCompat", e);
            return event;
        }
    }

    /**
     * 获取活动窗口边界 (API 30+, 用于 pending 代码)。
     * vendor 原始方法: a0.h.b(AccessibilityWindowInfo)
     */
    public static Rect getActiveWindowBounds(android.view.accessibility.AccessibilityWindowInfo windowInfo) {
        if (windowInfo == null) return new Rect();
        try {
            Rect bounds = new Rect();
            windowInfo.getBoundsInScreen(bounds);
            return bounds;
        } catch (Exception e) {
            AppUtils.s("AccessibilityCompat", e);
            return new Rect();
        }
    }
}
