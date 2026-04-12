package p000;

import androidx.work.NetworkType;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class qj0 extends AbstractC0799kx {
    static {
        new pj0(null);
        t60.m214694b5(C1351vv.m214966b1("NetworkNotRoamingCtrlr"), "tagWithPrefix(\"NetworkNotRoamingCtrlr\")");
    }

    @Override // p000.AbstractC0799kx
    /* renamed from: a0 */
    public final boolean mo212609a0(wg1 wg1Var) {
        t60.m214695b6(wg1Var, "workSpec");
        return wg1Var.f60921a9.f58193a0 == NetworkType.f45519a3;
    }

    @Override // p000.AbstractC0799kx
    /* renamed from: a1 */
    public final boolean mo212610a1(Object obj) {
        rj0 rj0Var = (rj0) obj;
        t60.m214695b6(rj0Var, "value");
        return (rj0Var.f59779a0 && rj0Var.f59782a3) ? false : true;
    }
}
