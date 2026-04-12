package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tg */
/* loaded from: classes2.dex */
public final class C1258tg extends hu0 {

    /* renamed from: a4 */
    public static final AtomicIntegerFieldUpdater f60215a4 = AtomicIntegerFieldUpdater.newUpdater(C1258tg.class, "_decision");
    private volatile int _decision;

    @Override // p000.hu0, p000.y70
    /* renamed from: a5 */
    public final void mo212674a5(Object obj) {
        mo213092a6(obj);
    }

    @Override // p000.hu0, p000.y70
    /* renamed from: a6 */
    public final void mo213092a6(Object obj) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        do {
            atomicIntegerFieldUpdater = f60215a4;
            int i = atomicIntegerFieldUpdater.get(this);
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("Already resumed");
                }
                b81.m210592e3(AbstractC0732jv.m213356a0(obj), kj1.m213575c2(this.f56754a3));
                return;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, 0, 2));
    }
}
