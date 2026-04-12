package p000;

import androidx.work.impl.utils.futures.C0099a0;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g4 */
/* loaded from: classes2.dex */
public abstract class AbstractC0521g4 implements ob0 {

    /* renamed from: a3 */
    public static final boolean f56377a3 = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));

    /* renamed from: a4 */
    public static final Logger f56378a4 = Logger.getLogger(AbstractC0521g4.class.getName());

    /* renamed from: a5 */
    public static final AbstractC1117qo f56379a5;

    /* renamed from: a6 */
    public static final Object f56380a6;

    /* renamed from: a0 */
    public volatile Object f56381a0;

    /* renamed from: a1 */
    public volatile C0487f9 f56382a1;

    /* renamed from: a2 */
    public volatile C0520g3 f56383a2;

    static {
        AbstractC1117qo c0519g2;
        try {
            c0519g2 = new C0517g0(AtomicReferenceFieldUpdater.newUpdater(C0520g3.class, Thread.class, "a0"), AtomicReferenceFieldUpdater.newUpdater(C0520g3.class, C0520g3.class, "a1"), AtomicReferenceFieldUpdater.newUpdater(AbstractC0521g4.class, C0520g3.class, "a2"), AtomicReferenceFieldUpdater.newUpdater(AbstractC0521g4.class, C0487f9.class, "a1"), AtomicReferenceFieldUpdater.newUpdater(AbstractC0521g4.class, Object.class, "a0"));
            th = null;
        } catch (Throwable th) {
            th = th;
            c0519g2 = new C0519g2();
        }
        f56379a5 = c0519g2;
        if (th != null) {
            f56378a4.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        f56380a6 = new Object();
    }

    /* renamed from: a2 */
    public static void m212884a2(AbstractC0521g4 abstractC0521g4) {
        C0487f9 c0487f9;
        C0487f9 c0487f92;
        C0487f9 c0487f93 = null;
        while (true) {
            C0520g3 c0520g3 = abstractC0521g4.f56383a2;
            if (f56379a5.mo212873a6(abstractC0521g4, c0520g3, C0520g3.f56369a2)) {
                while (c0520g3 != null) {
                    Thread thread = c0520g3.f56370a0;
                    if (thread != null) {
                        c0520g3.f56370a0 = null;
                        LockSupport.unpark(thread);
                    }
                    c0520g3 = c0520g3.f56371a1;
                }
                do {
                    c0487f9 = abstractC0521g4.f56382a1;
                } while (!f56379a5.mo212871a4(abstractC0521g4, c0487f9, C0487f9.f56185a3));
                while (true) {
                    c0487f92 = c0487f93;
                    c0487f93 = c0487f9;
                    if (c0487f93 == null) {
                        break;
                    }
                    c0487f9 = c0487f93.f56188a2;
                    c0487f93.f56188a2 = c0487f92;
                }
                while (c0487f92 != null) {
                    c0487f93 = c0487f92.f56188a2;
                    Runnable runnable = c0487f92.f56186a0;
                    if (runnable instanceof RunnableC0518g1) {
                        RunnableC0518g1 runnableC0518g1 = (RunnableC0518g1) runnable;
                        abstractC0521g4 = runnableC0518g1.f56364a0;
                        if (abstractC0521g4.f56381a0 == runnableC0518g1) {
                            if (f56379a5.mo212872a5(abstractC0521g4, runnableC0518g1, m212887a5(runnableC0518g1.f56365a1))) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        m212885a3(runnable, c0487f92.f56187a1);
                    }
                    c0487f92 = c0487f93;
                }
                return;
            }
        }
    }

    /* renamed from: a3 */
    public static void m212885a3(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            f56378a4.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    /* renamed from: a4 */
    public static Object m212886a4(Object obj) throws ExecutionException {
        if (obj instanceof C0486f8) {
            Throwable th = ((C0486f8) obj).f56177a1;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        }
        if (obj instanceof C0099a0) {
            throw new ExecutionException(((C0099a0) obj).f45587a0);
        }
        if (obj == f56380a6) {
            return null;
        }
        return obj;
    }

    /* renamed from: a5 */
    public static Object m212887a5(ob0 ob0Var) {
        Object obj;
        if (ob0Var instanceof AbstractC0521g4) {
            Object obj2 = ((AbstractC0521g4) ob0Var).f56381a0;
            if (!(obj2 instanceof C0486f8)) {
                return obj2;
            }
            C0486f8 c0486f8 = (C0486f8) obj2;
            return c0486f8.f56176a0 ? c0486f8.f56177a1 != null ? new C0486f8(c0486f8.f56177a1, false) : C0486f8.f56175a3 : obj2;
        }
        boolean zIsCancelled = ob0Var.isCancelled();
        boolean z = true;
        if ((!f56377a3) && zIsCancelled) {
            return C0486f8.f56175a3;
        }
        boolean z2 = false;
        while (true) {
            try {
                try {
                    obj = ob0Var.get();
                    break;
                } catch (InterruptedException unused) {
                    z2 = z;
                } catch (Throwable th) {
                    if (z2) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            } catch (CancellationException e) {
                if (zIsCancelled) {
                    return new C0486f8(e, false);
                }
                return new C0099a0(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + ob0Var, e));
            } catch (ExecutionException e2) {
                return new C0099a0(e2.getCause());
            } catch (Throwable th2) {
                return new C0099a0(th2);
            }
        }
        if (z2) {
            Thread.currentThread().interrupt();
        }
        return obj == null ? f56380a6 : obj;
    }

    @Override // p000.ob0
    /* renamed from: a0 */
    public final void mo210459a0(Runnable runnable, Executor executor) {
        executor.getClass();
        C0487f9 c0487f9 = this.f56382a1;
        C0487f9 c0487f92 = C0487f9.f56185a3;
        if (c0487f9 != c0487f92) {
            C0487f9 c0487f93 = new C0487f9(runnable, executor);
            do {
                c0487f93.f56188a2 = c0487f9;
                if (f56379a5.mo212871a4(this, c0487f9, c0487f93)) {
                    return;
                } else {
                    c0487f9 = this.f56382a1;
                }
            } while (c0487f9 != c0487f92);
        }
        m212885a3(runnable, executor);
    }

    /* renamed from: a1 */
    public final void m212888a1(StringBuilder sb) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                try {
                    obj = get();
                    break;
                } catch (InterruptedException unused) {
                    z = true;
                } catch (Throwable th) {
                    if (z) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            } catch (CancellationException unused2) {
                sb.append("CANCELLED");
                return;
            } catch (RuntimeException e) {
                sb.append("UNKNOWN, cause=[");
                sb.append(e.getClass());
                sb.append(" thrown from get()]");
                return;
            } catch (ExecutionException e2) {
                sb.append("FAILURE, cause=[");
                sb.append(e2.getCause());
                sb.append("]");
                return;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        sb.append("SUCCESS, result=[");
        sb.append(obj == this ? "this future" : String.valueOf(obj));
        sb.append("]");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a6 */
    public final String m212889a6() {
        Object obj = this.f56381a0;
        if (obj instanceof RunnableC0518g1) {
            StringBuilder sb = new StringBuilder("setFuture=[");
            ob0 ob0Var = ((RunnableC0518g1) obj).f56365a1;
            return AbstractC0003a2.m35b6(sb, ob0Var == this ? "this future" : String.valueOf(ob0Var), "]");
        }
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    /* renamed from: a7 */
    public final void m212890a7(C0520g3 c0520g3) {
        c0520g3.f56370a0 = null;
        while (true) {
            C0520g3 c0520g32 = this.f56383a2;
            if (c0520g32 == C0520g3.f56369a2) {
                return;
            }
            C0520g3 c0520g33 = null;
            while (c0520g32 != null) {
                C0520g3 c0520g34 = c0520g32.f56371a1;
                if (c0520g32.f56370a0 != null) {
                    c0520g33 = c0520g32;
                } else if (c0520g33 != null) {
                    c0520g33.f56371a1 = c0520g34;
                    if (c0520g33.f56370a0 == null) {
                        break;
                    }
                } else if (!f56379a5.mo212873a6(this, c0520g32, c0520g34)) {
                    break;
                }
                c0520g32 = c0520g34;
            }
            return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0053, code lost:
    
        return true;
     */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean cancel(boolean z) {
        Object obj = this.f56381a0;
        if (!(obj == null) && !(obj instanceof RunnableC0518g1)) {
            return false;
        }
        C0486f8 c0486f8 = f56377a3 ? new C0486f8(new CancellationException("Future.cancel() was called."), z) : z ? C0486f8.f56174a2 : C0486f8.f56175a3;
        AbstractC0521g4 abstractC0521g4 = this;
        boolean z2 = false;
        while (true) {
            if (f56379a5.mo212872a5(abstractC0521g4, obj, c0486f8)) {
                m212884a2(abstractC0521g4);
                if (!(obj instanceof RunnableC0518g1)) {
                    break;
                }
                ob0 ob0Var = ((RunnableC0518g1) obj).f56365a1;
                if (!(ob0Var instanceof AbstractC0521g4)) {
                    ob0Var.cancel(z);
                    break;
                }
                abstractC0521g4 = (AbstractC0521g4) ob0Var;
                obj = abstractC0521g4.f56381a0;
                if (!(obj == null) && !(obj instanceof RunnableC0518g1)) {
                    break;
                }
                z2 = true;
            } else {
                obj = abstractC0521g4.f56381a0;
                if (!(obj instanceof RunnableC0518g1)) {
                    return z2;
                }
            }
        }
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        boolean z;
        C0520g3 c0520g3 = C0520g3.f56369a2;
        long nanos = timeUnit.toNanos(j);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.f56381a0;
        if ((obj != null) && (!(obj instanceof RunnableC0518g1))) {
            return m212886a4(obj);
        }
        long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            C0520g3 c0520g32 = this.f56383a2;
            if (c0520g32 != c0520g3) {
                C0520g3 c0520g33 = new C0520g3();
                z = true;
                do {
                    AbstractC1117qo abstractC1117qo = f56379a5;
                    abstractC1117qo.mo212874f4(c0520g33, c0520g32);
                    if (abstractC1117qo.mo212873a6(this, c0520g32, c0520g33)) {
                        do {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                m212890a7(c0520g33);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.f56381a0;
                            if ((obj2 != null) && (!(obj2 instanceof RunnableC0518g1))) {
                                return m212886a4(obj2);
                            }
                            nanos = jNanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        m212890a7(c0520g33);
                    } else {
                        c0520g32 = this.f56383a2;
                    }
                } while (c0520g32 != c0520g3);
            }
            return m212886a4(this.f56381a0);
        }
        z = true;
        while (nanos > 0) {
            Object obj3 = this.f56381a0;
            if ((obj3 != null ? z : false) && (!(obj3 instanceof RunnableC0518g1))) {
                return m212886a4(obj3);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            nanos = jNanoTime - System.nanoTime();
        }
        String string = toString();
        String string2 = timeUnit.toString();
        Locale locale = Locale.ROOT;
        String lowerCase = string2.toLowerCase(locale);
        String strM32b3 = "Waited " + j + " " + timeUnit.toString().toLowerCase(locale);
        if (nanos + 1000 < 0) {
            String strM32b32 = AbstractC0003a2.m32b3(strM32b3, " (plus ");
            long j2 = -nanos;
            long jConvert = timeUnit.convert(j2, TimeUnit.NANOSECONDS);
            long nanos2 = j2 - timeUnit.toNanos(jConvert);
            boolean z2 = (jConvert == 0 || nanos2 > 1000) ? z : false;
            if (jConvert > 0) {
                String strM32b33 = strM32b32 + jConvert + " " + lowerCase;
                if (z2) {
                    strM32b33 = AbstractC0003a2.m32b3(strM32b33, ",");
                }
                strM32b32 = AbstractC0003a2.m32b3(strM32b33, " ");
            }
            if (z2) {
                strM32b32 = strM32b32 + nanos2 + " nanoseconds ";
            }
            strM32b3 = AbstractC0003a2.m32b3(strM32b32, "delay)");
        }
        if (isDone()) {
            throw new TimeoutException(AbstractC0003a2.m32b3(strM32b3, " but future completed as timeout expired"));
        }
        throw new TimeoutException(strM32b3 + " for " + string);
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.f56381a0 instanceof C0486f8;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return (!(r0 instanceof RunnableC0518g1)) & (this.f56381a0 != null);
    }

    public final String toString() {
        String strM212889a6;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (this.f56381a0 instanceof C0486f8) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            m212888a1(sb);
        } else {
            try {
                strM212889a6 = m212889a6();
            } catch (RuntimeException e) {
                strM212889a6 = "Exception thrown from implementation: " + e.getClass();
            }
            if (strM212889a6 != null && !strM212889a6.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(strM212889a6);
                sb.append("]");
            } else if (isDone()) {
                m212888a1(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // java.util.concurrent.Future
    public final Object get() throws InterruptedException {
        Object obj;
        C0520g3 c0520g3 = C0520g3.f56369a2;
        if (!Thread.interrupted()) {
            Object obj2 = this.f56381a0;
            if ((obj2 != null) & (!(obj2 instanceof RunnableC0518g1))) {
                return m212886a4(obj2);
            }
            C0520g3 c0520g32 = this.f56383a2;
            if (c0520g32 != c0520g3) {
                C0520g3 c0520g33 = new C0520g3();
                do {
                    AbstractC1117qo abstractC1117qo = f56379a5;
                    abstractC1117qo.mo212874f4(c0520g33, c0520g32);
                    if (abstractC1117qo.mo212873a6(this, c0520g32, c0520g33)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.f56381a0;
                            } else {
                                m212890a7(c0520g33);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof RunnableC0518g1))));
                        return m212886a4(obj);
                    }
                    c0520g32 = this.f56383a2;
                } while (c0520g32 != c0520g3);
            }
            return m212886a4(this.f56381a0);
        }
        throw new InterruptedException();
    }
}
