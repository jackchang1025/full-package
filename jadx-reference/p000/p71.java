package p000;

import java.util.Iterator;
import kotlin.jvm.internal.Lambda;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class p71 implements Iterator, d80 {

    /* renamed from: a0 */
    public final Iterator f59164a0;

    /* renamed from: a1 */
    public final /* synthetic */ C1516zg f59165a1;

    public p71(C1516zg c1516zg) {
        this.f59165a1 = c1516zg;
        this.f59164a0 = ((nz0) c1516zg.f61543a1).iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.f59164a0.hasNext();
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [h10, kotlin.jvm.internal.Lambda] */
    @Override // java.util.Iterator
    public final Object next() {
        return ((Lambda) this.f59165a1.f61544a2).invoke(this.f59164a0.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
