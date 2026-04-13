package android.support.v4.os;

import android.os.Build;

/* loaded from: classes.dex */
public class BuildCompat {
    private BuildCompat() {
    }

    @Deprecated
    public static boolean isAtLeastN() {
        return true;
    }

    @Deprecated
    public static boolean isAtLeastNMR1() {
        return true;
    }

    @Deprecated
    public static boolean isAtLeastO() {
        return true;
    }

    @Deprecated
    public static boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @Deprecated
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    public static boolean isAtLeastQ() {
        String str = Build.VERSION.CODENAME;
        return str.length() == 1 && str.charAt(0) >= 'Q' && str.charAt(0) <= 'Z';
    }
}
