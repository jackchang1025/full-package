package p000;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ku */
/* loaded from: classes2.dex */
public final class C0796ku implements nz0 {

    /* renamed from: a0 */
    public final AtomicReference f57720a0;

    public C0796ku(C0722jl c0722jl) {
        this.f57720a0 = new AtomicReference(c0722jl);
    }

    @Override // p000.nz0
    public final Iterator iterator() {
        nz0 nz0Var = (nz0) this.f57720a0.getAndSet(null);
        if (nz0Var != null) {
            return nz0Var.iterator();
        }
        throw new IllegalStateException("This sequence can be consumed only once.");
    }
}
