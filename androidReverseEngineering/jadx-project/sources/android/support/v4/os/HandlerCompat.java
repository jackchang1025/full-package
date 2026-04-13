package android.support.v4.os;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public final class HandlerCompat {
    private HandlerCompat() {
    }

    public static boolean postDelayed(@NonNull Handler handler, @NonNull Runnable runnable, @Nullable Object obj, long j2) {
        boolean postDelayed;
        if (Build.VERSION.SDK_INT >= 28) {
            postDelayed = handler.postDelayed(runnable, obj, j2);
            return postDelayed;
        }
        Message obtain = Message.obtain(handler, runnable);
        obtain.obj = obj;
        return handler.sendMessageDelayed(obtain, j2);
    }
}
