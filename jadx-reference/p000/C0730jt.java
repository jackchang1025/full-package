package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jt */
/* loaded from: classes2.dex */
public class C0730jt {

    /* renamed from: a1 */
    public static final AtomicIntegerFieldUpdater f57377a1 = AtomicIntegerFieldUpdater.newUpdater(C0730jt.class, "_handled");
    private volatile int _handled;

    /* renamed from: a0 */
    public final Throwable f57378a0;

    public C0730jt(Throwable th, boolean z) {
        this.f57378a0 = th;
        this._handled = z ? 1 : 0;
    }

    public final String toString() {
        return getClass().getSimpleName() + '[' + this.f57378a0 + ']';
    }
}
