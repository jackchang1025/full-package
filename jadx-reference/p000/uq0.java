package p000;

import android.util.SparseArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class uq0 {

    /* renamed from: a0 */
    public SparseArray f60504a0;

    /* renamed from: a1 */
    public int f60505a1;

    /* renamed from: a0 */
    public final tq0 m214860a0(int i) {
        SparseArray sparseArray = this.f60504a0;
        tq0 tq0Var = (tq0) sparseArray.get(i);
        if (tq0Var != null) {
            return tq0Var;
        }
        tq0 tq0Var2 = new tq0();
        sparseArray.put(i, tq0Var2);
        return tq0Var2;
    }
}
