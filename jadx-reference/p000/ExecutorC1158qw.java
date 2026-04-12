package p000;

import java.util.concurrent.Executor;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.AbstractC0784a4;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qw */
/* loaded from: classes2.dex */
public final class ExecutorC1158qw extends AbstractC0784a4 implements Executor {

    /* renamed from: a2 */
    public static final ExecutorC1158qw f59554a2 = new ExecutorC1158qw();

    /* renamed from: a3 */
    public static final AbstractC0781a1 f59555a3;

    static {
        AbstractC0781a1 oa0Var = u81.f60342a2;
        int i = q41.f59384a0;
        if (64 >= i) {
            i = 64;
        }
        int iM213591d8 = kj1.m213591d8(i, 12, "kotlinx.coroutines.io.parallelism");
        oa0Var.getClass();
        if (iM213591d8 < 1) {
            throw new IllegalArgumentException(tz0.m214802a2(iM213591d8, "Expected positive parallelism level, but got ").toString());
        }
        if (iM213591d8 < l51.f57833a3) {
            if (iM213591d8 < 1) {
                throw new IllegalArgumentException(tz0.m214802a2(iM213591d8, "Expected positive parallelism level, but got ").toString());
            }
            oa0Var = new oa0(oa0Var, iM213591d8);
        }
        f59555a3 = oa0Var;
    }

    @Override // kotlinx.coroutines.AbstractC0781a1
    /* renamed from: c6 */
    public final void mo212723c6(InterfaceC0912ng interfaceC0912ng, Runnable runnable) {
        f59555a3.mo212723c6(interfaceC0912ng, runnable);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO");
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        mo212723c6(EmptyCoroutineContext.f57605a0, runnable);
    }

    @Override // kotlinx.coroutines.AbstractC0781a1
    public final String toString() {
        return "Dispatchers.IO";
    }
}
