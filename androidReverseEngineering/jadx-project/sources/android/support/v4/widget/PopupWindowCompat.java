package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class PopupWindowCompat {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Method sGetWindowLayoutTypeMethod;
    private static boolean sGetWindowLayoutTypeMethodAttempted;
    private static Field sOverlapAnchorField;
    private static boolean sOverlapAnchorFieldAttempted;
    private static Method sSetWindowLayoutTypeMethod;
    private static boolean sSetWindowLayoutTypeMethodAttempted;

    private PopupWindowCompat() {
    }

    public static boolean getOverlapAnchor(@NonNull PopupWindow popupWindow) {
        return popupWindow.getOverlapAnchor();
    }

    public static int getWindowLayoutType(@NonNull PopupWindow popupWindow) {
        return popupWindow.getWindowLayoutType();
    }

    public static void setOverlapAnchor(@NonNull PopupWindow popupWindow, boolean z2) {
        popupWindow.setOverlapAnchor(z2);
    }

    public static void setWindowLayoutType(@NonNull PopupWindow popupWindow, int i2) {
        popupWindow.setWindowLayoutType(i2);
    }

    public static void showAsDropDown(@NonNull PopupWindow popupWindow, @NonNull View view, int i2, int i3, int i4) {
        popupWindow.showAsDropDown(view, i2, i3, i4);
    }
}
