package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListPopupWindow;

/* loaded from: classes.dex */
public final class ListPopupWindowCompat {
    private ListPopupWindowCompat() {
    }

    @Nullable
    public static View.OnTouchListener createDragToOpenListener(@NonNull ListPopupWindow listPopupWindow, @NonNull View view) {
        return listPopupWindow.createDragToOpenListener(view);
    }

    @Deprecated
    public static View.OnTouchListener createDragToOpenListener(Object obj, View view) {
        return createDragToOpenListener((ListPopupWindow) obj, view);
    }
}
