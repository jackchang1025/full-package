package p000;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ro */
/* loaded from: classes.dex */
public final class C1187ro extends t60 {

    /* renamed from: c6 */
    public final Object f59796c6 = new Object();

    /* renamed from: c7 */
    public final ExecutorService f59797c7 = Executors.newFixedThreadPool(4, new ThreadFactoryC1185rm());

    /* renamed from: c8 */
    public volatile Handler f59798c8;

    /* renamed from: f5 */
    public static Handler m214545f5(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return AbstractC1186rn.m214543a0(looper);
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException unused) {
            return new Handler(looper);
        } catch (InvocationTargetException unused2) {
            return new Handler(looper);
        }
    }
}
