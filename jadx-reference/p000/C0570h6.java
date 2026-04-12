package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: h6 */
/* loaded from: classes.dex */
public final class C0570h6 extends t60 {

    /* renamed from: c6 */
    public final AtomicReferenceFieldUpdater f56618c6;

    /* renamed from: c7 */
    public final AtomicReferenceFieldUpdater f56619c7;

    /* renamed from: c8 */
    public final AtomicReferenceFieldUpdater f56620c8;

    /* renamed from: c9 */
    public final AtomicReferenceFieldUpdater f56621c9;

    /* renamed from: d0 */
    public final AtomicReferenceFieldUpdater f56622d0;

    public C0570h6(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
        this.f56618c6 = atomicReferenceFieldUpdater;
        this.f56619c7 = atomicReferenceFieldUpdater2;
        this.f56620c8 = atomicReferenceFieldUpdater3;
        this.f56621c9 = atomicReferenceFieldUpdater4;
        this.f56622d0 = atomicReferenceFieldUpdater5;
    }

    @Override // p000.t60
    /* renamed from: b0 */
    public final boolean mo212999b0(AbstractC0573h9 abstractC0573h9, C0569h5 c0569h5, C0569h5 c0569h52) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.f56621c9;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractC0573h9, c0569h5, c0569h52)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractC0573h9) == c0569h5);
        return false;
    }

    @Override // p000.t60
    /* renamed from: b1 */
    public final boolean mo213000b1(AbstractC0573h9 abstractC0573h9, Object obj, Object obj2) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.f56622d0;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractC0573h9, obj, obj2)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractC0573h9) == obj);
        return false;
    }

    @Override // p000.t60
    /* renamed from: b2 */
    public final boolean mo213001b2(AbstractC0573h9 abstractC0573h9, C0572h8 c0572h8, C0572h8 c0572h82) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        do {
            atomicReferenceFieldUpdater = this.f56620c8;
            if (atomicReferenceFieldUpdater.compareAndSet(abstractC0573h9, c0572h8, c0572h82)) {
                return true;
            }
        } while (atomicReferenceFieldUpdater.get(abstractC0573h9) == c0572h8);
        return false;
    }

    @Override // p000.t60
    /* renamed from: e3 */
    public final void mo213002e3(C0572h8 c0572h8, C0572h8 c0572h82) {
        this.f56619c7.lazySet(c0572h8, c0572h82);
    }

    @Override // p000.t60
    /* renamed from: e4 */
    public final void mo213003e4(C0572h8 c0572h8, Thread thread) {
        this.f56618c6.lazySet(c0572h8, thread);
    }
}
