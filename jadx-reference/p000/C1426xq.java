package p000;

import java.lang.reflect.Method;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlinx.coroutines.AbstractC0784a4;
import kotlinx.coroutines.RunnableC0782a2;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xq */
/* loaded from: classes2.dex */
public final class C1426xq extends AbstractC0784a4 implements InterfaceC1191rs {

    /* renamed from: a2 */
    public final ExecutorService f61169a2;

    public C1426xq(ExecutorService executorService) {
        Method method;
        this.f61169a2 = executorService;
        Method method2 = AbstractC0758kh.f57527a0;
        try {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = executorService instanceof ScheduledThreadPoolExecutor ? (ScheduledThreadPoolExecutor) executorService : null;
            if (scheduledThreadPoolExecutor != null && (method = AbstractC0758kh.f57527a0) != null) {
                method.invoke(scheduledThreadPoolExecutor, Boolean.TRUE);
            }
        } catch (Throwable unused) {
        }
    }

    @Override // p000.InterfaceC1191rs
    /* renamed from: a5 */
    public final InterfaceC1266tn mo213699a5(long j, r61 r61Var, InterfaceC0912ng interfaceC0912ng) {
        ExecutorService executorService = this.f61169a2;
        ScheduledFuture<?> scheduledFutureSchedule = null;
        ScheduledExecutorService scheduledExecutorService = executorService instanceof ScheduledExecutorService ? (ScheduledExecutorService) executorService : null;
        if (scheduledExecutorService != null) {
            try {
                scheduledFutureSchedule = scheduledExecutorService.schedule(r61Var, j, TimeUnit.MILLISECONDS);
            } catch (RejectedExecutionException e) {
                CancellationException cancellationException = new CancellationException("The task was rejected");
                cancellationException.initCause(e);
                k70 k70Var = (k70) interfaceC0912ng.mo212745b4(C1351vv.f60702a3);
                if (k70Var != null) {
                    ((y70) k70Var).m215254a8(cancellationException);
                }
            }
        }
        return scheduledFutureSchedule != null ? new C1265tm(scheduledFutureSchedule) : RunnableC0782a2.f57657a9.mo213699a5(j, r61Var, interfaceC0912ng);
    }

    @Override // p000.InterfaceC1191rs
    /* renamed from: a7 */
    public final void mo213703a7(long j, C0530gb c0530gb) {
        ExecutorService executorService = this.f61169a2;
        ScheduledFuture<?> scheduledFutureSchedule = null;
        ScheduledExecutorService scheduledExecutorService = executorService instanceof ScheduledExecutorService ? (ScheduledExecutorService) executorService : null;
        if (scheduledExecutorService != null) {
            RunnableC0884n2 runnableC0884n2 = new RunnableC0884n2(this, c0530gb, 10, false);
            InterfaceC0912ng interfaceC0912ng = c0530gb.f56434a4;
            try {
                scheduledFutureSchedule = scheduledExecutorService.schedule(runnableC0884n2, j, TimeUnit.MILLISECONDS);
            } catch (RejectedExecutionException e) {
                CancellationException cancellationException = new CancellationException("The task was rejected");
                cancellationException.initCause(e);
                k70 k70Var = (k70) interfaceC0912ng.mo212745b4(C1351vv.f60702a3);
                if (k70Var != null) {
                    ((y70) k70Var).m215254a8(cancellationException);
                }
            }
        }
        if (scheduledFutureSchedule != null) {
            c0530gb.m212928b8(new C0509fu(0, scheduledFutureSchedule));
        } else {
            RunnableC0782a2.f57657a9.mo213703a7(j, c0530gb);
        }
    }

    @Override // kotlinx.coroutines.AbstractC0781a1
    /* renamed from: c6 */
    public final void mo212723c6(InterfaceC0912ng interfaceC0912ng, Runnable runnable) {
        try {
            this.f61169a2.execute(runnable);
        } catch (RejectedExecutionException e) {
            CancellationException cancellationException = new CancellationException("The task was rejected");
            cancellationException.initCause(e);
            k70 k70Var = (k70) interfaceC0912ng.mo212745b4(C1351vv.f60702a3);
            if (k70Var != null) {
                ((y70) k70Var).m215254a8(cancellationException);
            }
            AbstractC1262tj.f60234a1.mo212723c6(interfaceC0912ng, runnable);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        ExecutorService executorService = this.f61169a2;
        if (executorService == null) {
            executorService = null;
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C1426xq) && ((C1426xq) obj).f61169a2 == this.f61169a2;
    }

    public final int hashCode() {
        return System.identityHashCode(this.f61169a2);
    }

    @Override // kotlinx.coroutines.AbstractC0781a1
    public final String toString() {
        return this.f61169a2.toString();
    }
}
