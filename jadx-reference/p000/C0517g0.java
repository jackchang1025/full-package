package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g0 */
/* loaded from: classes2.dex */
public final class C0517g0 extends AbstractC1117qo {

    /* renamed from: a8 */
    public final AtomicReferenceFieldUpdater f56352a8;

    /* renamed from: a9 */
    public final AtomicReferenceFieldUpdater f56353a9;

    /* renamed from: b0 */
    public final AtomicReferenceFieldUpdater f56354b0;

    /* renamed from: b1 */
    public final AtomicReferenceFieldUpdater f56355b1;

    /* renamed from: b2 */
    public final AtomicReferenceFieldUpdater f56356b2;

    public C0517g0(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
        this.f56352a8 = atomicReferenceFieldUpdater;
        this.f56353a9 = atomicReferenceFieldUpdater2;
        this.f56354b0 = atomicReferenceFieldUpdater3;
        this.f56355b1 = atomicReferenceFieldUpdater4;
        this.f56356b2 = atomicReferenceFieldUpdater5;
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: a4 */
    public final boolean mo212871a4(AbstractC0521g4 abstractC0521g4, C0487f9 c0487f9, C0487f9 c0487f92) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.f56355b1;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractC0521g4, c0487f9, c0487f92)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractC0521g4) == c0487f9);
        return false;
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: a5 */
    public final boolean mo212872a5(AbstractC0521g4 abstractC0521g4, Object obj, Object obj2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.f56356b2;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractC0521g4, obj, obj2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractC0521g4) == obj);
        return false;
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: a6 */
    public final boolean mo212873a6(AbstractC0521g4 abstractC0521g4, C0520g3 c0520g3, C0520g3 c0520g32) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.f56354b0;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractC0521g4, c0520g3, c0520g32)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractC0521g4) == c0520g3);
        return false;
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: f4 */
    public final void mo212874f4(C0520g3 c0520g3, C0520g3 c0520g32) {
        this.f56353a9.lazySet(c0520g3, c0520g32);
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: f5 */
    public final void mo212875f5(C0520g3 c0520g3, Thread thread) {
        this.f56352a8.lazySet(c0520g3, thread);
    }
}
