package p000;

import android.view.WindowInsets;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class mf1 extends pf1 {

    /* renamed from: a2 */
    public final WindowInsets.Builder f58348a2;

    public mf1() {
        this.f58348a2 = AbstractC0742k2.m213420a6();
    }

    @Override // p000.pf1
    /* renamed from: a1 */
    public xf1 mo213836a1() {
        m214253a0();
        xf1 xf1VarM215170a6 = xf1.m215170a6(null, this.f58348a2.build());
        xf1VarM215170a6.f61102a0.mo214395b4(this.f59225a1);
        return xf1VarM215170a6;
    }

    @Override // p000.pf1
    /* renamed from: a3 */
    public void mo213992a3(f60 f60Var) {
        this.f58348a2.setMandatorySystemGestureInsets(f60Var.m212750a3());
    }

    @Override // p000.pf1
    /* renamed from: a4 */
    public void mo213837a4(f60 f60Var) {
        this.f58348a2.setStableInsets(f60Var.m212750a3());
    }

    @Override // p000.pf1
    /* renamed from: a5 */
    public void mo213993a5(f60 f60Var) {
        this.f58348a2.setSystemGestureInsets(f60Var.m212750a3());
    }

    @Override // p000.pf1
    /* renamed from: a6 */
    public void mo213838a6(f60 f60Var) {
        this.f58348a2.setSystemWindowInsets(f60Var.m212750a3());
    }

    @Override // p000.pf1
    /* renamed from: a7 */
    public void mo213994a7(f60 f60Var) {
        this.f58348a2.setTappableElementInsets(f60Var.m212750a3());
    }

    public mf1(xf1 xf1Var) {
        WindowInsets.Builder builderM213420a6;
        super(xf1Var);
        WindowInsets windowInsetsM215175a5 = xf1Var.m215175a5();
        if (windowInsetsM215175a5 != null) {
            builderM213420a6 = cb1.m210806a5(windowInsetsM215175a5);
        } else {
            builderM213420a6 = AbstractC0742k2.m213420a6();
        }
        this.f58348a2 = builderM213420a6;
    }
}
