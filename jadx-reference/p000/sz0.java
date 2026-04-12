package p000;

import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sz0 implements Iterable, d80 {

    /* renamed from: a0 */
    public final /* synthetic */ C1198ry f60104a0;

    public sz0(C1198ry c1198ry) {
        this.f60104a0 = c1198ry;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new C1197rx(this.f60104a0);
    }
}
