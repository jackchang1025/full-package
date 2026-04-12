package p000;

import android.app.Application;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class kb1 {
    public /* synthetic */ kb1(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final nb1 defaultFactory$lifecycle_viewmodel_release(sb1 sb1Var) {
        t60.m214695b6(sb1Var, "owner");
        return sb1Var instanceof x30 ? ((x30) sb1Var).mo209827a2() : pb1.f59187a0.getInstance();
    }

    public final lb1 getInstance(Application application) {
        t60.m214695b6(application, "application");
        if (lb1.f57871a5 == null) {
            lb1.f57871a5 = new lb1(application);
        }
        lb1 lb1Var = lb1.f57871a5;
        t60.m214692b3(lb1Var);
        return lb1Var;
    }

    private kb1() {
    }
}
