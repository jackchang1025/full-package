package com.vendor.rat.auto.filter;

import android.graphics.Rect;
import com.vendor.rat.auto.entity.UiNode;

public class PointFilter implements NodeFilter {
    public static final int TYPE_CONTAINS = 1;
    public static final int TYPE_NEARLY = 2;
    private final float x;
    private final float y;
    private final int type;

    public PointFilter(float x, float y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    @Override
    public boolean accept(UiNode node) {
        if (type == TYPE_CONTAINS && node.getChildCount() == 0) {
            Rect bounds = node.boundsInScreen();
            return bounds != null && bounds.contains((int) x, (int) y);
        }
        if (type == TYPE_NEARLY && node.getChildCount() == 0) {
            Rect bounds = node.boundsInScreen();
            if (bounds == null) return false;
            int w = bounds.right - bounds.left;
            int h = bounds.bottom - bounds.top;
            float cx = (bounds.left + bounds.right) / 2.0f;
            float cy = (bounds.top + bounds.bottom) / 2.0f;
            return Math.abs(x - cx) <= w / 2.0f
                && Math.abs(y - cy) <= h / 2.0f;
        }
        return false;
    }
}
