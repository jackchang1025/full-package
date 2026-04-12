package p000;

import android.util.SparseArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ag0 {

    /* renamed from: a0 */
    public final SparseArray f43653a0;

    /* renamed from: a1 */
    public C1384wo f43654a1;

    public ag0(int i) {
        this.f43653a0 = new SparseArray(i);
    }

    /* renamed from: a0 */
    public final void m209800a0(C1384wo c1384wo, int i, int i2) {
        int iM215083a0 = c1384wo.m215083a0(i);
        SparseArray sparseArray = this.f43653a0;
        ag0 ag0Var = sparseArray == null ? null : (ag0) sparseArray.get(iM215083a0);
        if (ag0Var == null) {
            ag0Var = new ag0(1);
            sparseArray.put(c1384wo.m215083a0(i), ag0Var);
        }
        if (i2 > i) {
            ag0Var.m209800a0(c1384wo, i + 1, i2);
        } else {
            ag0Var.f43654a1 = c1384wo;
        }
    }
}
