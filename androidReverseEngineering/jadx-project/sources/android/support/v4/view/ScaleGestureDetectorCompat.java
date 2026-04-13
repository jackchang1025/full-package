package android.support.v4.view;

import android.view.ScaleGestureDetector;

/* loaded from: classes.dex */
public final class ScaleGestureDetectorCompat {
    private ScaleGestureDetectorCompat() {
    }

    public static boolean isQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector) {
        return scaleGestureDetector.isQuickScaleEnabled();
    }

    public static void setQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector, boolean z2) {
        scaleGestureDetector.setQuickScaleEnabled(z2);
    }

    @Deprecated
    public static boolean isQuickScaleEnabled(Object obj) {
        return isQuickScaleEnabled((ScaleGestureDetector) obj);
    }

    @Deprecated
    public static void setQuickScaleEnabled(Object obj, boolean z2) {
        setQuickScaleEnabled((ScaleGestureDetector) obj, z2);
    }
}
