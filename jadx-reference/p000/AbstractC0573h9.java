package p000;

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
/* renamed from: h9 */
/* loaded from: classes.dex */
public abstract class AbstractC0573h9 implements ob0 {

    /* renamed from: a3 */
    public static final boolean f56628a3 = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));

    /* renamed from: a4 */
    public static final Logger f56629a4 = Logger.getLogger(AbstractC0573h9.class.getName());

    /* renamed from: a5 */
    public static final t60 f56630a5;

    /* renamed from: a6 */
    public static final Object f56631a6;

    /* renamed from: a0 */
    public volatile Object f56632a0;

    /* renamed from: a1 */
    public volatile C0569h5 f56633a1;

    /* renamed from: a2 */
    public volatile C0572h8 f56634a2;

    static {
        t60 c0571h7;
        try {
            c0571h7 = new C0570h6(AtomicReferenceFieldUpdater.newUpdater(C0572h8.class, Thread.class, "a0"), AtomicReferenceFieldUpdater.newUpdater(C0572h8.class, C0572h8.class, "a1"), AtomicReferenceFieldUpdater.newUpdater(AbstractC0573h9.class, C0572h8.class, "a2"), AtomicReferenceFieldUpdater.newUpdater(AbstractC0573h9.class, C0569h5.class, "a1"), AtomicReferenceFieldUpdater.newUpdater(AbstractC0573h9.class, Object.class, "a0"));
            th = null;
        } catch (Throwable th) {
            th = th;
            c0571h7 = new C0571h7();
        }
        f56630a5 = c0571h7;
        if (th != null) {
            f56629a4.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        f56631a6 = new Object();
    }

    /* renamed from: a2 */
    public static void m213009a2(AbstractC0573h9 abstractC0573h9) {
        C0572h8 c0572h8;
        C0569h5 c0569h5;
        C0569h5 c0569h52;
        C0569h5 c0569h53;
        do {
            c0572h8 = abstractC0573h9.f56634a2;
        } while (!f56630a5.mo213001b2(abstractC0573h9, c0572h8, C0572h8.f56625a2));
        while (true) {
            c0569h5 = null;
            if (c0572h8 == null) {
                break;
            }
            Thread thread = c0572h8.f56626a0;
            if (thread != null) {
                c0572h8.f56626a0 = null;
                LockSupport.unpark(thread);
            }
            c0572h8 = c0572h8.f56627a1;
        }
        do {
            c0569h52 = abstractC0573h9.f56633a1;
        } while (!f56630a5.mo212999b0(abstractC0573h9, c0569h52, C0569h5.f56609a3));
        while (true) {
            c0569h53 = c0569h5;
            c0569h5 = c0569h52;
            if (c0569h5 == null) {
                break;
            }
            c0569h52 = c0569h5.f56612a2;
            c0569h5.f56612a2 = c0569h53;
        }
        while (c0569h53 != null) {
            C0569h5 c0569h54 = c0569h53.f56612a2;
            m213010a3(c0569h53.f56610a0, c0569h53.f56611a1);
            c0569h53 = c0569h54;
        }
    }

    /* renamed from: a3 */
    public static void m213010a3(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            f56629a4.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    /* renamed from: a4 */
    public static Object m213011a4(Object obj) throws ExecutionException {
        if (obj instanceof C0567h3) {
            Throwable th = ((C0567h3) obj).f56600a0;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        }
        if (obj instanceof AbstractC0568h4) {
            throw new ExecutionException((Throwable) null);
        }
        if (obj == f56631a6) {
            return null;
        }
        return obj;
    }

    /* renamed from: a5 */
    public static Object m213012a5(AbstractC0573h9 abstractC0573h9) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = abstractC0573h9.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    @Override // p000.ob0
    /* renamed from: a0 */
    public final void mo210459a0(Runnable runnable, Executor executor) {
        executor.getClass();
        C0569h5 c0569h5 = this.f56633a1;
        C0569h5 c0569h52 = C0569h5.f56609a3;
        if (c0569h5 != c0569h52) {
            C0569h5 c0569h53 = new C0569h5(runnable, executor);
            do {
                c0569h53.f56612a2 = c0569h5;
                if (f56630a5.mo212999b0(this, c0569h5, c0569h53)) {
                    return;
                } else {
                    c0569h5 = this.f56633a1;
                }
            } while (c0569h5 != c0569h52);
        }
        m213010a3(runnable, executor);
    }

    /* renamed from: a1 */
    public final void m213013a1(StringBuilder sb) {
        try {
            Object objM213012a5 = m213012a5(this);
            sb.append("SUCCESS, result=[");
            sb.append(objM213012a5 == this ? "this future" : String.valueOf(objM213012a5));
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e.getClass());
            sb.append(" thrown from get()]");
        } catch (ExecutionException e2) {
            sb.append("FAILURE, cause=[");
            sb.append(e2.getCause());
            sb.append("]");
        }
    }

    /* renamed from: a6 */
    public final void m213014a6(C0572h8 c0572h8) {
        c0572h8.f56626a0 = null;
        while (true) {
            C0572h8 c0572h82 = this.f56634a2;
            if (c0572h82 == C0572h8.f56625a2) {
                return;
            }
            C0572h8 c0572h83 = null;
            while (c0572h82 != null) {
                C0572h8 c0572h84 = c0572h82.f56627a1;
                if (c0572h82.f56626a0 != null) {
                    c0572h83 = c0572h82;
                } else if (c0572h83 != null) {
                    c0572h83.f56627a1 = c0572h84;
                    if (c0572h83.f56626a0 == null) {
                        break;
                    }
                } else if (!f56630a5.mo213001b2(this, c0572h82, c0572h84)) {
                    break;
                }
                c0572h82 = c0572h84;
            }
            return;
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        Object obj = this.f56632a0;
        if (obj != null) {
            return false;
        }
        if (!f56630a5.mo213000b1(this, obj, f56628a3 ? new C0567h3(new CancellationException("Future.cancel() was called."), z) : z ? C0567h3.f56598a1 : C0567h3.f56599a2)) {
            return false;
        }
        m213009a2(this);
        return true;
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException {
        C0572h8 c0572h8 = C0572h8.f56625a2;
        long nanos = timeUnit.toNanos(j);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj = this.f56632a0;
        if (obj != null) {
            return m213011a4(obj);
        }
        long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
        if (nanos >= 1000) {
            C0572h8 c0572h82 = this.f56634a2;
            if (c0572h82 != c0572h8) {
                C0572h8 c0572h83 = new C0572h8();
                do {
                    t60 t60Var = f56630a5;
                    t60Var.mo213002e3(c0572h83, c0572h82);
                    if (t60Var.mo213001b2(this, c0572h82, c0572h83)) {
                        do {
                            LockSupport.parkNanos(this, nanos);
                            if (Thread.interrupted()) {
                                m213014a6(c0572h83);
                                throw new InterruptedException();
                            }
                            Object obj2 = this.f56632a0;
                            if (obj2 != null) {
                                return m213011a4(obj2);
                            }
                            nanos = jNanoTime - System.nanoTime();
                        } while (nanos >= 1000);
                        m213014a6(c0572h83);
                    } else {
                        c0572h82 = this.f56634a2;
                    }
                } while (c0572h82 != c0572h8);
            }
            return m213011a4(this.f56632a0);
        }
        while (nanos > 0) {
            Object obj3 = this.f56632a0;
            if (obj3 != null) {
                return m213011a4(obj3);
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
            boolean z = jConvert == 0 || nanos2 > 1000;
            if (jConvert > 0) {
                String strM32b33 = strM32b32 + jConvert + " " + lowerCase;
                if (z) {
                    strM32b33 = AbstractC0003a2.m32b3(strM32b33, ",");
                }
                strM32b32 = AbstractC0003a2.m32b3(strM32b33, " ");
            }
            if (z) {
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
        return this.f56632a0 instanceof C0567h3;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return this.f56632a0 != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (this.f56632a0 instanceof C0567h3) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            m213013a1(sb);
        } else {
            try {
                if (this instanceof ScheduledFuture) {
                    str = "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
                } else {
                    str = null;
                }
            } catch (RuntimeException e) {
                str = "Exception thrown from implementation: " + e.getClass();
            }
            if (str != null && !str.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append("]");
            } else if (isDone()) {
                m213013a1(sb);
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
        C0572h8 c0572h8 = C0572h8.f56625a2;
        if (!Thread.interrupted()) {
            Object obj2 = this.f56632a0;
            if (obj2 != null) {
                return m213011a4(obj2);
            }
            C0572h8 c0572h82 = this.f56634a2;
            if (c0572h82 != c0572h8) {
                C0572h8 c0572h83 = new C0572h8();
                do {
                    t60 t60Var = f56630a5;
                    t60Var.mo213002e3(c0572h83, c0572h82);
                    if (t60Var.mo213001b2(this, c0572h82, c0572h83)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.f56632a0;
                            } else {
                                m213014a6(c0572h83);
                                throw new InterruptedException();
                            }
                        } while (obj == null);
                        return m213011a4(obj);
                    }
                    c0572h82 = this.f56634a2;
                } while (c0572h82 != c0572h8);
            }
            return m213011a4(this.f56632a0);
        }
        throw new InterruptedException();
    }
}
