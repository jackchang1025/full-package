package p000;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import java.lang.reflect.InvocationTargetException;
import kotlin.Result;
import kotlinx.coroutines.android.C0785a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class w30 {
    private static volatile Choreographer choreographer;

    static {
        Object objM213507a7;
        try {
            int i = Result.f57558a1;
            objM213507a7 = new C0785a0(m215002a0(Looper.getMainLooper()), false);
        } catch (Throwable th) {
            int i2 = Result.f57558a1;
            objM213507a7 = kg1.m213507a7(th);
        }
        if (objM213507a7 instanceof Result.Failure) {
            objM213507a7 = null;
        }
    }

    /* renamed from: a0 */
    public static final Handler m215002a0(Looper looper) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 28) {
            Object objInvoke = Handler.class.getDeclaredMethod("createAsync", Looper.class).invoke(null, looper);
            t60.m214693b4(objInvoke, "null cannot be cast to non-null type android.os.Handler");
            return (Handler) objInvoke;
        }
        try {
            return (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
        } catch (NoSuchMethodException unused) {
            return new Handler(looper);
        }
    }
}
