package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.widget.ListView;

/* loaded from: classes.dex */
public final class ListViewCompat {
    private ListViewCompat() {
    }

    public static boolean canScrollList(@NonNull ListView listView, int i2) {
        return listView.canScrollList(i2);
    }

    public static void scrollListBy(@NonNull ListView listView, int i2) {
        listView.scrollListBy(i2);
    }
}
