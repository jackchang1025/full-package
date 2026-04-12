package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlinx.coroutines.AbstractC0781a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class oa0 extends AbstractC0781a1 implements InterfaceC1191rs {

    /* renamed from: a7 */
    public static final AtomicIntegerFieldUpdater f58771a7 = AtomicIntegerFieldUpdater.newUpdater(oa0.class, "runningWorkers");

    /* renamed from: a2 */
    public final AbstractC0781a1 f58772a2;

    /* renamed from: a3 */
    public final int f58773a3;

    /* renamed from: a4 */
    public final /* synthetic */ InterfaceC1191rs f58774a4;

    /* renamed from: a5 */
    public final fc0 f58775a5;

    /* renamed from: a6 */
    public final Object f58776a6;
    private volatile int runningWorkers;

    /* JADX WARN: Multi-variable type inference failed */
    public oa0(AbstractC0781a1 abstractC0781a1, int i) {
        this.f58772a2 = abstractC0781a1;
        this.f58773a3 = i;
        InterfaceC1191rs interfaceC1191rs = abstractC0781a1 instanceof InterfaceC1191rs ? (InterfaceC1191rs) abstractC0781a1 : null;
        this.f58774a4 = interfaceC1191rs == null ? AbstractC1156qu.f59549a0 : interfaceC1191rs;
        this.f58775a5 = new fc0();
        this.f58776a6 = new Object();
    }

    @Override // p000.InterfaceC1191rs
    /* renamed from: a5 */
    public final InterfaceC1266tn mo213699a5(long j, r61 r61Var, InterfaceC0912ng interfaceC0912ng) {
        return this.f58774a4.mo213699a5(j, r61Var, interfaceC0912ng);
    }

    @Override // p000.InterfaceC1191rs
    /* renamed from: a7 */
    public final void mo213703a7(long j, C0530gb c0530gb) {
        this.f58774a4.mo213703a7(j, c0530gb);
    }

    @Override // kotlinx.coroutines.AbstractC0781a1
    /* renamed from: c6 */
    public final void mo212723c6(InterfaceC0912ng interfaceC0912ng, Runnable runnable) {
        this.f58775a5.m212784a0(runnable);
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f58771a7;
        if (atomicIntegerFieldUpdater.get(this) < this.f58773a3) {
            synchronized (this.f58776a6) {
                if (atomicIntegerFieldUpdater.get(this) >= this.f58773a3) {
                    return;
                }
                atomicIntegerFieldUpdater.incrementAndGet(this);
                Runnable runnableM214170c8 = m214170c8();
                if (runnableM214170c8 == null) {
                    return;
                }
                this.f58772a2.mo212723c6(this, new RunnableC0884n2(this, 8, runnableM214170c8));
            }
        }
    }

    /* renamed from: c8 */
    public final Runnable m214170c8() {
        while (true) {
            Runnable runnable = (Runnable) this.f58775a5.m212787a3();
            if (runnable != null) {
                return runnable;
            }
            synchronized (this.f58776a6) {
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f58771a7;
                atomicIntegerFieldUpdater.decrementAndGet(this);
                if (this.f58775a5.m212786a2() == 0) {
                    return null;
                }
                atomicIntegerFieldUpdater.incrementAndGet(this);
            }
        }
    }
}
