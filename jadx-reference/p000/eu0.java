package p000;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import kotlinx.coroutines.AbstractC0784a4;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class eu0 extends AbstractC0784a4 {

    /* renamed from: a2 */
    public ExecutorC0919nn f56109a2;

    @Override // kotlinx.coroutines.AbstractC0781a1
    /* renamed from: c6 */
    public final void mo212723c6(InterfaceC0912ng interfaceC0912ng, Runnable runnable) {
        ExecutorC0919nn executorC0919nn = this.f56109a2;
        AtomicLongFieldUpdater atomicLongFieldUpdater = ExecutorC0919nn.f58664a7;
        executorC0919nn.m214130a5(runnable, l51.f57836a6);
    }
}
