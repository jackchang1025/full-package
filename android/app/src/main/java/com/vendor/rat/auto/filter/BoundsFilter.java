package com.vendor.rat.auto.filter;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.vendor.rat.auto.entity.UiNode;
import java.util.Locale;

public class BoundsFilter implements NodeFilter {
    public static final int TYPE_EQUALS = 0;
    public static final int TYPE_INSIDE = 1;
    public static final int TYPE_CONTAINS = 2;
    public static final int TYPE_NEARLY = 3;
    private Rect bounds;
    private int type;

    public BoundsFilter(Rect rect, int type) {
        this.bounds = rect;
        this.type = type;
    }

    @Override
    public boolean accept(UiNode node) {
        Rect boundsInScreen = node.boundsInScreen();
        if (boundsInScreen == null) return false;
        switch (this.type) {
            case TYPE_EQUALS:
                return boundsInScreen.equals(this.bounds);
            case TYPE_INSIDE:
                return this.bounds.contains(boundsInScreen);
            case TYPE_CONTAINS:
                return boundsInScreen.contains(this.bounds);
            case TYPE_NEARLY:
                return Math.abs(this.bounds.left - boundsInScreen.left) < 50
                    && Math.abs(this.bounds.right - boundsInScreen.right) < 50
                    && Math.abs(this.bounds.top - boundsInScreen.top) < 50
                    && Math.abs(this.bounds.bottom - boundsInScreen.bottom) < 50;
            default: return false;
        }
    }

    public Rect getBounds() { return bounds; }
    public int getType() { return type; }
    public void setBounds(Rect rect) { this.bounds = rect; }
    public void setType(int type) { this.type = type; }

    @NonNull
    @Override
    public String toString() {
        String typeName = type == 0 ? "Equal" : type == 1 ? "Inside" : "Contains";
        return String.format(Locale.getDefault(), "bounds%s(%d, %d, %d, %d)",
            typeName, bounds.left, bounds.top, bounds.right, bounds.bottom);
    }
}
