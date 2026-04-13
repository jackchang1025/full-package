package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build;
import android.support.v4.text.AbstractC0065a;
import android.view.DisplayCutout;
import java.util.List;

/* loaded from: classes.dex */
public final class DisplayCutoutCompat {
    private final Object mDisplayCutout;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DisplayCutoutCompat(Rect rect, List<Rect> list) {
        this(r3);
        DisplayCutout displayCutout;
        if (Build.VERSION.SDK_INT >= 28) {
            AbstractC0065a.m232B();
            displayCutout = AbstractC0065a.m247m(rect, list);
        } else {
            displayCutout = null;
        }
    }

    public static DisplayCutoutCompat wrap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new DisplayCutoutCompat(obj);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DisplayCutoutCompat.class != obj.getClass()) {
            return false;
        }
        Object obj2 = this.mDisplayCutout;
        Object obj3 = ((DisplayCutoutCompat) obj).mDisplayCutout;
        return obj2 == null ? obj3 == null : obj2.equals(obj3);
    }

    public List<Rect> getBoundingRects() {
        List<Rect> boundingRects;
        if (Build.VERSION.SDK_INT < 28) {
            return null;
        }
        boundingRects = AbstractC0065a.m248n(this.mDisplayCutout).getBoundingRects();
        return boundingRects;
    }

    public int getSafeInsetBottom() {
        int safeInsetBottom;
        if (Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        safeInsetBottom = AbstractC0065a.m248n(this.mDisplayCutout).getSafeInsetBottom();
        return safeInsetBottom;
    }

    public int getSafeInsetLeft() {
        int safeInsetLeft;
        if (Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        safeInsetLeft = AbstractC0065a.m248n(this.mDisplayCutout).getSafeInsetLeft();
        return safeInsetLeft;
    }

    public int getSafeInsetRight() {
        int safeInsetRight;
        if (Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        safeInsetRight = AbstractC0065a.m248n(this.mDisplayCutout).getSafeInsetRight();
        return safeInsetRight;
    }

    public int getSafeInsetTop() {
        int safeInsetTop;
        if (Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        safeInsetTop = AbstractC0065a.m248n(this.mDisplayCutout).getSafeInsetTop();
        return safeInsetTop;
    }

    public int hashCode() {
        Object obj = this.mDisplayCutout;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public String toString() {
        return "DisplayCutoutCompat{" + this.mDisplayCutout + "}";
    }

    private DisplayCutoutCompat(Object obj) {
        this.mDisplayCutout = obj;
    }
}
