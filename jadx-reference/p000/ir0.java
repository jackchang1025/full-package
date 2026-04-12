package p000;

import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ir0 {
    public /* synthetic */ ir0(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final jr0 getInstance(Context context) {
        jr0 jr0Var;
        t60.m214695b6(context, "context");
        jr0 jr0Var2 = jr0.f57364a2;
        if (jr0Var2 != null) {
            return jr0Var2;
        }
        synchronized (this) {
            jr0Var = jr0.f57364a2;
            if (jr0Var == null) {
                t60.m214694b5(context.getApplicationContext(), "context.applicationContext");
                jr0Var = new jr0();
                jr0.f57364a2 = jr0Var;
            }
        }
        return jr0Var;
    }

    public final void releaseInstance() {
        jr0 jr0Var = jr0.f57364a2;
        if (jr0Var != null) {
            try {
                AbstractC1117qo.m214410a3(jr0Var.f57365a0);
                jr0.f57364a2 = null;
            } catch (Exception unused) {
            }
        }
    }

    private ir0() {
    }
}
