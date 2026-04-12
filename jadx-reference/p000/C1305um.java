package p000;

import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: um */
/* loaded from: classes2.dex */
public final class C1305um implements nz0 {

    /* renamed from: a0 */
    public final nz0 f60472a0;

    /* renamed from: a1 */
    public final int f60473a1;

    public C1305um(nz0 nz0Var, int i) {
        this.f60472a0 = nz0Var;
        this.f60473a1 = i;
        if (i >= 0) {
            return;
        }
        throw new IllegalArgumentException(("count must be non-negative, but was " + i + '.').toString());
    }

    @Override // p000.nz0
    public final Iterator iterator() {
        return new C0523g6(this);
    }
}
