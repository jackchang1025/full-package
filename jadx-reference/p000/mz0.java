package p000;

import java.util.concurrent.atomic.AtomicReferenceArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mz0 extends jz0 {

    /* renamed from: a4 */
    public final AtomicReferenceArray f58410a4;

    public mz0(long j, mz0 mz0Var, int i) {
        super(j, mz0Var, i);
        this.f58410a4 = new AtomicReferenceArray(lz0.f58210a5);
    }

    @Override // p000.jz0
    /* renamed from: a5 */
    public final int mo213019a5() {
        return lz0.f58210a5;
    }

    @Override // p000.jz0
    /* renamed from: a6 */
    public final void mo213020a6(int i, InterfaceC0912ng interfaceC0912ng) {
        this.f58410a4.set(i, lz0.f58209a4);
        m213363a7();
    }

    public final String toString() {
        return "SemaphoreSegment[id=" + this.f57401a2 + ", hashCode=" + hashCode() + ']';
    }
}
