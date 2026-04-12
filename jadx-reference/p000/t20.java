package p000;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class t20 implements Iterator, d80 {

    /* renamed from: a0 */
    public Object f60129a0;

    /* renamed from: a1 */
    public int f60130a1 = -2;

    /* renamed from: a2 */
    public final /* synthetic */ C1516zg f60131a2;

    public t20(C1516zg c1516zg) {
        this.f60131a2 = c1516zg;
    }

    /* renamed from: a0 */
    public final void m214684a0() {
        Object objInvoke;
        int i = this.f60130a1;
        C1516zg c1516zg = this.f60131a2;
        if (i == -2) {
            objInvoke = ((w00) c1516zg.f61543a1).invoke();
        } else {
            h10 h10Var = (h10) c1516zg.f61544a2;
            Object obj = this.f60129a0;
            t60.m214692b3(obj);
            objInvoke = h10Var.invoke(obj);
        }
        this.f60129a0 = objInvoke;
        this.f60130a1 = objInvoke == null ? 0 : 1;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f60130a1 < 0) {
            m214684a0();
        }
        return this.f60130a1 == 1;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (this.f60130a1 < 0) {
            m214684a0();
        }
        if (this.f60130a1 == 0) {
            throw new NoSuchElementException();
        }
        Object obj = this.f60129a0;
        t60.m214693b4(obj, "null cannot be cast to non-null type T of kotlin.sequences.GeneratorSequence");
        this.f60130a1 = -1;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
