package p000;

import java.util.concurrent.locks.LockSupport;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: eg */
/* loaded from: classes2.dex */
public final class C0455eg extends AbstractC0482f4 {

    /* renamed from: a3 */
    public final Thread f56003a3;

    /* renamed from: a4 */
    public final AbstractC1424xo f56004a4;

    public C0455eg(InterfaceC0912ng interfaceC0912ng, Thread thread, AbstractC1424xo abstractC1424xo) {
        super(interfaceC0912ng, true);
        this.f56003a3 = thread;
        this.f56004a4 = abstractC1424xo;
    }

    @Override // p000.y70
    /* renamed from: a5 */
    public final void mo212674a5(Object obj) {
        Thread threadCurrentThread = Thread.currentThread();
        Thread thread = this.f56003a3;
        if (t60.m214686a2(threadCurrentThread, thread)) {
            return;
        }
        LockSupport.unpark(thread);
    }
}
