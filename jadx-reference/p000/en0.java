package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class en0 {
    public /* synthetic */ en0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final fn0 getInstance(Context context) {
        fn0 fn0Var;
        t60.m214695b6(context, "context");
        fn0 fn0Var2 = fn0.f56300a3;
        if (fn0Var2 != null) {
            return fn0Var2;
        }
        synchronized (this) {
            fn0Var = fn0.f56300a3;
            if (fn0Var == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                fn0Var = new fn0(applicationContext);
                fn0.f56300a3 = fn0Var;
            }
        }
        return fn0Var;
    }

    private en0() {
    }
}
