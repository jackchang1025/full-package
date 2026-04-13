package com.guard.wallet.utils;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import com.guard.wallet.entity.Point;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.List;

/**
 * 手势执行工具类 — 通过 AccessibilityService.dispatchGesture 执行手势。
 */
public final class GestureUtils {
    private GestureUtils() {}

    /** g.S(Long, Long, Point...) — 分发路径手势 */
    public static boolean dispatchGesture(long startTime, long duration, Point... points) {
        if (points == null || points.length == 0) return false;

        Path path = new Path();
        path.moveTo(points[0].getX(), points[0].getY());
        for (int i = 1; i < points.length; i++) {
            path.lineTo(points[i].getX(), points[i].getY());
        }

        GestureDescription.StrokeDescription stroke = new GestureDescription.StrokeDescription(path, startTime, duration);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        builder.addStroke(stroke);

        MyAccessibilityService svc = MyAccessibilityService.P();
        if (svc == null) return false;
        return svc.dispatchGesture(builder.build(), null, null);
    }

    /** g.G0(Integer, Integer, Long) — 点击指定坐标 */
    public static boolean clickAtPosition(int x, int y, long duration) {
        return dispatchGesture(16L, duration, new Point((float) x, (float) y));
    }

    /** g.s(Integer, Integer) — 快速点击 */
    public static boolean tap(int x, int y) {
        return clickAtPosition(x, y, 50L);
    }

    /** g.t(List<Point>) — 批量点击 */
    public static boolean clickMultiplePoints(List<Point> points) {
        if (points == null || points.isEmpty()) return false;
        boolean success = true;
        for (Point p : points) {
            if (!tap((int) p.getX(), (int) p.getY())) {
                success = false;
            }
        }
        return success;
    }

    /** g.s(x1,y1,x2,y2) — swipe from start to end with default duration */
    public static boolean swipe(int x1, int y1, int x2, int y2) {
        return dispatchGesture(0L, 300L, new Point((float) x1, (float) y1), new Point((float) x2, (float) y2));
    }
}
