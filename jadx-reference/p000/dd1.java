package p000;

import android.view.View;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class dd1 implements fd1 {

    /* renamed from: a0 */
    public final /* synthetic */ boolean f55709a0;

    /* renamed from: a1 */
    public final /* synthetic */ boolean f55710a1;

    /* renamed from: a2 */
    public final /* synthetic */ boolean f55711a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0460el f55712a3;

    public dd1(boolean z, boolean z2, boolean z3, C0460el c0460el) {
        this.f55709a0 = z;
        this.f55710a1 = z2;
        this.f55711a2 = z3;
        this.f55712a3 = c0460el;
    }

    @Override // p000.fd1
    /* renamed from: b5 */
    public final xf1 mo212585b5(View view, xf1 xf1Var, gd1 gd1Var) {
        if (this.f55709a0) {
            gd1Var.f56448a3 = xf1Var.m215171a0() + gd1Var.f56448a3;
        }
        boolean zM214447e3 = AbstractC1117qo.m214447e3(view);
        if (this.f55710a1) {
            if (zM214447e3) {
                gd1Var.f56447a2 = xf1Var.m215172a1() + gd1Var.f56447a2;
            } else {
                gd1Var.f56445a0 = xf1Var.m215172a1() + gd1Var.f56445a0;
            }
        }
        if (this.f55711a2) {
            if (zM214447e3) {
                gd1Var.f56445a0 = xf1Var.m215173a2() + gd1Var.f56445a0;
            } else {
                gd1Var.f56447a2 = xf1Var.m215173a2() + gd1Var.f56447a2;
            }
        }
        int i = gd1Var.f56445a0;
        int i2 = gd1Var.f56446a1;
        int i3 = gd1Var.f56447a2;
        int i4 = gd1Var.f56448a3;
        WeakHashMap weakHashMap = xa1.f61054a0;
        ga1.m212911b0(view, i, i2, i3, i4);
        this.f55712a3.mo212585b5(view, xf1Var, gd1Var);
        return xf1Var;
    }
}
