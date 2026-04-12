package p000;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hg */
/* loaded from: classes2.dex */
public final class C0580hg extends l70 {

    /* renamed from: a4 */
    public final C0530gb f56666a4;

    public C0580hg(C0530gb c0530gb) {
        this.f56666a4 = c0530gb;
    }

    @Override // p000.u70
    /* renamed from: b1 */
    public final void mo213037b1(Throwable th) {
        CancellationException cancellationExceptionM215259b8 = m214818b0().m215259b8();
        C0530gb c0530gb = this.f56666a4;
        if (c0530gb.m212931c1()) {
            InterfaceC0876mv interfaceC0876mv = c0530gb.f56433a3;
            t60.m214693b4(interfaceC0876mv, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
            C1257tf c1257tf = (C1257tf) interfaceC0876mv;
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C1257tf.f60207a7;
            loop0: while (true) {
                Object obj = atomicReferenceFieldUpdater.get(c1257tf);
                C1347vr c1347vr = b81.f45734a5;
                if (!t60.m214686a2(obj, c1347vr)) {
                    if (!(obj instanceof Throwable)) {
                        while (!atomicReferenceFieldUpdater.compareAndSet(c1257tf, obj, null)) {
                            if (atomicReferenceFieldUpdater.get(c1257tf) != obj) {
                                break;
                            }
                        }
                        break loop0;
                    }
                    return;
                }
                while (!atomicReferenceFieldUpdater.compareAndSet(c1257tf, c1347vr, cancellationExceptionM215259b8)) {
                    if (atomicReferenceFieldUpdater.get(c1257tf) != c1347vr) {
                        break;
                    }
                }
                return;
            }
        }
        c0530gb.m212922b2(cancellationExceptionM215259b8);
        if (c0530gb.m212931c1()) {
            return;
        }
        c0530gb.m212923b3();
    }

    @Override // p000.h10
    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        mo213037b1((Throwable) obj);
        return C1351vv.f60710b1;
    }
}
