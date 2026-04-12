package p000;

import android.content.Context;
import androidx.work.WorkerParameters;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dh1 {
    static {
        C1351vv.m214966b1("WorkerFactory");
    }

    /* renamed from: a0 */
    public static tb0 m212607a0(Context context, String str, WorkerParameters workerParameters) {
        Class clsAsSubclass;
        tb0 tb0Var = null;
        try {
            clsAsSubclass = Class.forName(str).asSubclass(tb0.class);
        } catch (Throwable unused) {
            C1351vv.m214963a5().getClass();
            clsAsSubclass = null;
        }
        if (clsAsSubclass != null) {
            try {
                tb0Var = (tb0) clsAsSubclass.getDeclaredConstructor(Context.class, WorkerParameters.class).newInstance(context, workerParameters);
            } catch (Throwable unused2) {
                C1351vv.m214963a5().getClass();
            }
        }
        if (tb0Var == null || !tb0Var.f60193a3) {
            return tb0Var;
        }
        throw new IllegalStateException(AbstractC0003a2.m34b5("WorkerFactory (", dh1.class.getName(), ") returned an instance of a ListenableWorker (", str, ") which has already been invoked. createWorker() must always return a new instance of a ListenableWorker."));
    }
}
