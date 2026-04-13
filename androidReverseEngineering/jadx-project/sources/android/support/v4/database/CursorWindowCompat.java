package android.support.v4.database;

import android.database.CursorWindow;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.AbstractC0073a;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class CursorWindowCompat {
    private CursorWindowCompat() {
    }

    @NonNull
    public static CursorWindow create(@Nullable String str, long j2) {
        if (Build.VERSION.SDK_INT < 28) {
            return new CursorWindow(str);
        }
        AbstractC0000a.m2C();
        return AbstractC0073a.m267b(str, j2);
    }
}
