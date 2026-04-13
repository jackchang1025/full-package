package com.guard.wallet.filter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.guard.wallet.entity.UiObject;
import java.util.Locale;

/* loaded from: classes.dex */
public class BoundsFilter implements Filter {
    public static final int TYPE_CONTAINS = 2;
    public static final int TYPE_EQUALS = 0;
    public static final int TYPE_INSIDE = 1;
    public static final int TYPE_NEARLY = 3;
    private Rect bounds;
    private int type;

    public BoundsFilter(Rect rect, int i2) {
        this.bounds = rect;
        this.type = i2;
    }

    @Override // com.guard.wallet.filter.Filter
    public Boolean filter(UiObject uiObject) {
        boolean contains;
        Rect boundsInScreen = uiObject.boundsInScreen();
        if (boundsInScreen != null) {
            int i2 = this.type;
            if (i2 == 2) {
                contains = boundsInScreen.contains(this.bounds);
            } else {
                if (i2 == 0) {
                    return Boolean.valueOf(boundsInScreen == this.bounds);
                }
                if (i2 == 1) {
                    contains = this.bounds.contains(boundsInScreen);
                } else if (i2 == 3) {
                    if (Math.abs(this.bounds.left - boundsInScreen.left) < 50 && Math.abs(this.bounds.right - boundsInScreen.right) < 50 && Math.abs(this.bounds.top - boundsInScreen.top) < 50 && Math.abs(this.bounds.bottom - boundsInScreen.bottom) < 50) {
                        r1 = true;
                    }
                    return Boolean.valueOf(r1);
                }
            }
            return Boolean.valueOf(contains);
        }
        return Boolean.FALSE;
    }

    public Rect getBounds() {
        return this.bounds;
    }

    public int getType() {
        return this.type;
    }

    public void setBounds(Rect rect) {
        this.bounds = rect;
    }

    public void setType(int i2) {
        this.type = i2;
    }

    @NonNull
    public String toString() {
        int i2 = this.type;
        return String.format(Locale.getDefault(), "bounds%s(%d, %d, %d, %d)", i2 == 0 ? "Equal" : i2 == 1 ? "Inside" : "Contains", Integer.valueOf(this.bounds.left), Integer.valueOf(this.bounds.top), Integer.valueOf(this.bounds.right), Integer.valueOf(this.bounds.bottom));
    }
}
