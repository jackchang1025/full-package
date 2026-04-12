package p000;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class b40 extends C0829lq {

    /* renamed from: h2 */
    public C0829lq[] f45711h2 = new C0829lq[4];

    /* renamed from: h3 */
    public int f45712h3 = 0;

    @Override // p000.C0829lq
    /* renamed from: a6 */
    public void mo210535a6(C0829lq c0829lq, HashMap map) {
        super.mo210535a6(c0829lq, map);
        b40 b40Var = (b40) c0829lq;
        this.f45712h3 = 0;
        int i = b40Var.f45712h3;
        for (int i2 = 0; i2 < i; i2++) {
            m210536e4((C0829lq) map.get(b40Var.f45711h2[i2]));
        }
    }

    /* renamed from: e4 */
    public final void m210536e4(C0829lq c0829lq) {
        if (c0829lq == this || c0829lq == null) {
            return;
        }
        int i = this.f45712h3 + 1;
        C0829lq[] c0829lqArr = this.f45711h2;
        if (i > c0829lqArr.length) {
            this.f45711h2 = (C0829lq[]) Arrays.copyOf(c0829lqArr, c0829lqArr.length * 2);
        }
        C0829lq[] c0829lqArr2 = this.f45711h2;
        int i2 = this.f45712h3;
        c0829lqArr2[i2] = c0829lq;
        this.f45712h3 = i2 + 1;
    }

    /* renamed from: e5 */
    public final void m210537e5(int i, qe1 qe1Var, ArrayList arrayList) {
        for (int i2 = 0; i2 < this.f45712h3; i2++) {
            C0829lq c0829lq = this.f45711h2[i2];
            ArrayList arrayList2 = qe1Var.f59485a0;
            if (!arrayList2.contains(c0829lq)) {
                arrayList2.add(c0829lq);
            }
        }
        for (int i3 = 0; i3 < this.f45712h3; i3++) {
            cq0.m212482b2(this.f45711h2[i3], i, arrayList, qe1Var);
        }
    }

    /* renamed from: e6 */
    public void mo210538e6() {
    }
}
