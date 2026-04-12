package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class z60 extends l70 {

    /* renamed from: a5 */
    public static final AtomicIntegerFieldUpdater f61462a5 = AtomicIntegerFieldUpdater.newUpdater(z60.class, "_invoked");
    private volatile int _invoked;

    /* renamed from: a4 */
    public final h10 f61463a4;

    public z60(h10 h10Var) {
        this.f61463a4 = h10Var;
    }

    @Override // p000.u70
    /* renamed from: b1 */
    public final void mo213037b1(Throwable th) {
        if (f61462a5.compareAndSet(this, 0, 1)) {
            this.f61463a4.invoke(th);
        }
    }

    @Override // p000.h10
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        mo213037b1((Throwable) obj);
        return C1351vv.f60710b1;
    }
}
