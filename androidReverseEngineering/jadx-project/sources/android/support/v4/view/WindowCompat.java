package android.support.v4.view;

import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

/* loaded from: classes.dex */
public final class WindowCompat {
    public static final int FEATURE_ACTION_BAR = 8;
    public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;

    private WindowCompat() {
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull Window window, @IdRes int i2) {
        View requireViewById;
        if (Build.VERSION.SDK_INT >= 28) {
            requireViewById = window.requireViewById(i2);
            return (T) requireViewById;
        }
        T t2 = (T) window.findViewById(i2);
        if (t2 != null) {
            return t2;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Window");
    }
}
