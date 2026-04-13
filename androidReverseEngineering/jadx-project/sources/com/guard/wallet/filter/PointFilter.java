package com.guard.wallet.filter;

import android.graphics.Rect;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;

/* loaded from: classes.dex */
public class PointFilter implements Filter {
    public static final int TYPE_CONTAINS = 1;
    public static final int TYPE_NEARLY = 2;
    private final Point point;
    private final int type;

    public PointFilter(Point point, int i2) {
        this.point = point;
        this.type = i2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        Rect boundsInScreen;
        boolean z2 = false;
        if (this.type == 1 && uiObject.childCount() == 0) {
            Rect boundsInScreen2 = uiObject.boundsInScreen();
            if (boundsInScreen2 != null && boundsInScreen2.contains((int) this.point.getX(), (int) this.point.getY())) {
                z2 = true;
            }
            return Boolean.valueOf(z2);
        }
        if (this.type != 2 || uiObject.childCount() != 0 || (boundsInScreen = uiObject.boundsInScreen()) == null) {
            return Boolean.FALSE;
        }
        int i2 = boundsInScreen.right - boundsInScreen.left;
        int i3 = boundsInScreen.bottom - boundsInScreen.top;
        Point centerInScreen = uiObject.centerInScreen();
        if (Math.abs(this.point.getX() - centerInScreen.getX()) <= i2 / 2.0f && Math.abs(this.point.getY() - centerInScreen.getY()) <= i3 / 2.0f) {
            z2 = true;
        }
        return Boolean.valueOf(z2);
    }
}
