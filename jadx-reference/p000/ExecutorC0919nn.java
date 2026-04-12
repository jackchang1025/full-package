package p000;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.LockSupport;
import kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nn */
/* loaded from: classes2.dex */
public final class ExecutorC0919nn implements Executor, Closeable {

    /* renamed from: a7 */
    public static final AtomicLongFieldUpdater f58664a7;

    /* renamed from: a8 */
    public static final AtomicLongFieldUpdater f58665a8;

    /* renamed from: a9 */
    public static final AtomicIntegerFieldUpdater f58666a9;

    /* renamed from: b0 */
    public static final C1347vr f58667b0;
    private volatile int _isTerminated;

    /* renamed from: a0 */
    public final int f58668a0;

    /* renamed from: a1 */
    public final int f58669a1;

    /* renamed from: a2 */
    public final long f58670a2;

    /* renamed from: a3 */
    public final String f58671a3;

    /* renamed from: a4 */
    public final i30 f58672a4;

    /* renamed from: a5 */
    public final i30 f58673a5;

    /* renamed from: a6 */
    public final pr0 f58674a6;
    private volatile long controlState;
    private volatile long parkedWorkersStack;

    static {
        new C0917nl(null);
        f58664a7 = AtomicLongFieldUpdater.newUpdater(ExecutorC0919nn.class, "parkedWorkersStack");
        f58665a8 = AtomicLongFieldUpdater.newUpdater(ExecutorC0919nn.class, "controlState");
        f58666a9 = AtomicIntegerFieldUpdater.newUpdater(ExecutorC0919nn.class, "_isTerminated");
        f58667b0 = new C1347vr("NOT_IN_STACK");
    }

    public ExecutorC0919nn(int i, int i2, long j, String str) {
        this.f58668a0 = i;
        this.f58669a1 = i2;
        this.f58670a2 = j;
        this.f58671a3 = str;
        if (i < 1) {
            throw new IllegalArgumentException(AbstractC0003a2.m30b1("Core pool size ", i, " should be at least 1").toString());
        }
        if (i2 < i) {
            throw new IllegalArgumentException(("Max pool size " + i2 + " should be greater than or equals to core pool size " + i).toString());
        }
        if (i2 > 2097150) {
            throw new IllegalArgumentException(AbstractC0003a2.m30b1("Max pool size ", i2, " should not exceed maximal supported number of threads 2097150").toString());
        }
        if (j <= 0) {
            throw new IllegalArgumentException(("Idle worker keep alive time " + j + " must be positive").toString());
        }
        this.f58672a4 = new i30();
        this.f58673a5 = new i30();
        this.f58674a6 = new pr0((i + 1) * 2);
        this.controlState = i << 42;
        this._isTerminated = 0;
    }

    /* renamed from: a0 */
    public final int m214129a0() {
        synchronized (this.f58674a6) {
            try {
                if (f58666a9.get(this) != 0) {
                    return -1;
                }
                AtomicLongFieldUpdater atomicLongFieldUpdater = f58665a8;
                long j = atomicLongFieldUpdater.get(this);
                int i = (int) (j & 2097151);
                int i2 = i - ((int) ((j & 4398044413952L) >> 21));
                if (i2 < 0) {
                    i2 = 0;
                }
                if (i2 >= this.f58668a0) {
                    return 0;
                }
                if (i >= this.f58669a1) {
                    return 0;
                }
                int i3 = ((int) (atomicLongFieldUpdater.get(this) & 2097151)) + 1;
                if (i3 <= 0 || this.f58674a6.m214332a1(i3) != null) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                C0918nm c0918nm = new C0918nm(this, i3);
                this.f58674a6.m214333a2(i3, c0918nm);
                if (i3 != ((int) (2097151 & atomicLongFieldUpdater.incrementAndGet(this)))) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
                int i4 = i2 + 1;
                c0918nm.start();
                return i4;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: a5 */
    public final void m214130a5(Runnable runnable, j51 j51Var) {
        i51 k51Var;
        CoroutineScheduler$WorkerState coroutineScheduler$WorkerState;
        l51.f57835a5.getClass();
        long jNanoTime = System.nanoTime();
        if (runnable instanceof i51) {
            k51Var = (i51) runnable;
            k51Var.f56798a0 = jNanoTime;
            k51Var.f56799a1 = j51Var;
        } else {
            k51Var = new k51(runnable, jNanoTime, j51Var);
        }
        boolean z = k51Var.f56799a1.f57268a0 == 1;
        AtomicLongFieldUpdater atomicLongFieldUpdater = f58665a8;
        long jAddAndGet = z ? atomicLongFieldUpdater.addAndGet(this, 2097152L) : 0L;
        Thread threadCurrentThread = Thread.currentThread();
        C0918nm c0918nm = threadCurrentThread instanceof C0918nm ? (C0918nm) threadCurrentThread : null;
        if (c0918nm == null || !t60.m214686a2(c0918nm.f58656a7, this)) {
            c0918nm = null;
        }
        if (c0918nm != null && (coroutineScheduler$WorkerState = c0918nm.f58651a2) != CoroutineScheduler$WorkerState.f57696a4 && (k51Var.f56799a1.f57268a0 != 0 || coroutineScheduler$WorkerState != CoroutineScheduler$WorkerState.f57693a1)) {
            c0918nm.f58655a6 = true;
            rg1 rg1Var = c0918nm.f58649a0;
            rg1Var.getClass();
            k51Var = (i51) rg1.f59771a1.getAndSet(rg1Var, k51Var);
            if (k51Var == null) {
                k51Var = null;
            } else {
                AtomicReferenceArray atomicReferenceArray = rg1Var.f59775a0;
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = rg1.f59772a2;
                if (atomicIntegerFieldUpdater.get(rg1Var) - rg1.f59773a3.get(rg1Var) != 127) {
                    if (k51Var.f56799a1.f57268a0 == 1) {
                        rg1.f59774a4.incrementAndGet(rg1Var);
                    }
                    int i = atomicIntegerFieldUpdater.get(rg1Var) & 127;
                    while (atomicReferenceArray.get(i) != null) {
                        Thread.yield();
                    }
                    atomicReferenceArray.lazySet(i, k51Var);
                    atomicIntegerFieldUpdater.incrementAndGet(rg1Var);
                    k51Var = null;
                }
            }
        }
        if (k51Var != null) {
            if (!(k51Var.f56799a1.f57268a0 == 1 ? this.f58673a5.m212784a0(k51Var) : this.f58672a4.m212784a0(k51Var))) {
                throw new RejectedExecutionException(AbstractC0003a2.m35b6(new StringBuilder(), this.f58671a3, " was terminated"));
            }
        }
        if (z) {
            if (m214133b2() || m214132b0(jAddAndGet)) {
                return;
            }
            m214133b2();
            return;
        }
        if (m214133b2() || m214132b0(atomicLongFieldUpdater.get(this))) {
            return;
        }
        m214133b2();
    }

    /* renamed from: a7 */
    public final void m214131a7(C0918nm c0918nm, int i, int i2) {
        while (true) {
            long j = f58664a7.get(this);
            int i3 = (int) (2097151 & j);
            long j2 = (2097152 + j) & (-2097152);
            if (i3 == i) {
                if (i2 == 0) {
                    Object objM214114a2 = c0918nm.m214114a2();
                    while (true) {
                        if (objM214114a2 == f58667b0) {
                            i3 = -1;
                            break;
                        }
                        if (objM214114a2 == null) {
                            i3 = 0;
                            break;
                        }
                        C0918nm c0918nm2 = (C0918nm) objM214114a2;
                        int iM214113a1 = c0918nm2.m214113a1();
                        if (iM214113a1 != 0) {
                            i3 = iM214113a1;
                            break;
                        }
                        objM214114a2 = c0918nm2.m214114a2();
                    }
                } else {
                    i3 = i2;
                }
            }
            if (i3 >= 0) {
                if (f58664a7.compareAndSet(this, j, i3 | j2)) {
                    return;
                }
            }
        }
    }

    /* renamed from: b0 */
    public final boolean m214132b0(long j) {
        int i = ((int) (2097151 & j)) - ((int) ((j & 4398044413952L) >> 21));
        if (i < 0) {
            i = 0;
        }
        int i2 = this.f58668a0;
        if (i < i2) {
            int iM214129a0 = m214129a0();
            if (iM214129a0 == 1 && i2 > 1) {
                m214129a0();
            }
            if (iM214129a0 > 0) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b2 */
    public final boolean m214133b2() {
        C1347vr c1347vr;
        int iM214113a1;
        while (true) {
            long j = f58664a7.get(this);
            C0918nm c0918nm = (C0918nm) this.f58674a6.m214332a1((int) (2097151 & j));
            if (c0918nm == null) {
                c0918nm = null;
            } else {
                long j2 = (2097152 + j) & (-2097152);
                Object objM214114a2 = c0918nm.m214114a2();
                while (true) {
                    c1347vr = f58667b0;
                    if (objM214114a2 == c1347vr) {
                        iM214113a1 = -1;
                        break;
                    }
                    if (objM214114a2 == null) {
                        iM214113a1 = 0;
                        break;
                    }
                    C0918nm c0918nm2 = (C0918nm) objM214114a2;
                    iM214113a1 = c0918nm2.m214113a1();
                    if (iM214113a1 != 0) {
                        break;
                    }
                    objM214114a2 = c0918nm2.m214114a2();
                }
                if (iM214113a1 >= 0) {
                    if (f58664a7.compareAndSet(this, j, iM214113a1 | j2)) {
                        c0918nm.m214118a6(c1347vr);
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            if (c0918nm == null) {
                return false;
            }
            if (C0918nm.f58648a8.compareAndSet(c0918nm, -1, 0)) {
                LockSupport.unpark(c0918nm);
                return true;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0088  */
    @Override // java.io.Closeable, java.lang.AutoCloseable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void close() throws InterruptedException {
        int i;
        i51 i51VarM214112a0;
        if (f58666a9.compareAndSet(this, 0, 1)) {
            Thread threadCurrentThread = Thread.currentThread();
            C0918nm c0918nm = threadCurrentThread instanceof C0918nm ? (C0918nm) threadCurrentThread : null;
            if (c0918nm == null || !t60.m214686a2(c0918nm.f58656a7, this)) {
                c0918nm = null;
            }
            synchronized (this.f58674a6) {
                i = (int) (f58665a8.get(this) & 2097151);
            }
            if (1 <= i) {
                int i2 = 1;
                while (true) {
                    Object objM214332a1 = this.f58674a6.m214332a1(i2);
                    t60.m214692b3(objM214332a1);
                    C0918nm c0918nm2 = (C0918nm) objM214332a1;
                    if (c0918nm2 != c0918nm) {
                        while (c0918nm2.isAlive()) {
                            LockSupport.unpark(c0918nm2);
                            c0918nm2.join(10000L);
                        }
                        rg1 rg1Var = c0918nm2.f58649a0;
                        i30 i30Var = this.f58673a5;
                        rg1Var.getClass();
                        i51 i51Var = (i51) rg1.f59771a1.getAndSet(rg1Var, null);
                        if (i51Var != null) {
                            i30Var.m212784a0(i51Var);
                        }
                        while (true) {
                            i51 i51VarM214540a0 = rg1Var.m214540a0();
                            if (i51VarM214540a0 == null) {
                                break;
                            } else {
                                i30Var.m212784a0(i51VarM214540a0);
                            }
                        }
                    }
                    if (i2 == i) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
            this.f58673a5.m212785a1();
            this.f58672a4.m212785a1();
            while (true) {
                if (c0918nm == null) {
                    i51VarM214112a0 = (i51) this.f58672a4.m212787a3();
                    if (i51VarM214112a0 == null && (i51VarM214112a0 = (i51) this.f58673a5.m212787a3()) == null) {
                        break;
                    }
                } else {
                    i51VarM214112a0 = c0918nm.m214112a0(true);
                    if (i51VarM214112a0 == null) {
                    }
                }
                try {
                    i51VarM214112a0.run();
                } catch (Throwable th) {
                    Thread threadCurrentThread2 = Thread.currentThread();
                    threadCurrentThread2.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread2, th);
                }
            }
            if (c0918nm != null) {
                c0918nm.m214119a7(CoroutineScheduler$WorkerState.f57696a4);
            }
            f58664a7.set(this, 0L);
            f58665a8.set(this, 0L);
        }
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        m214130a5(runnable, l51.f57836a6);
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        pr0 pr0Var = this.f58674a6;
        int iM214331a0 = pr0Var.m214331a0();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 1; i6 < iM214331a0; i6++) {
            C0918nm c0918nm = (C0918nm) pr0Var.m214332a1(i6);
            if (c0918nm != null) {
                rg1 rg1Var = c0918nm.f58649a0;
                rg1Var.getClass();
                int i7 = rg1.f59771a1.get(rg1Var) != null ? (rg1.f59772a2.get(rg1Var) - rg1.f59773a3.get(rg1Var)) + 1 : rg1.f59772a2.get(rg1Var) - rg1.f59773a3.get(rg1Var);
                int iOrdinal = c0918nm.f58651a2.ordinal();
                if (iOrdinal == 0) {
                    i++;
                    StringBuilder sb = new StringBuilder();
                    sb.append(i7);
                    sb.append('c');
                    arrayList.add(sb.toString());
                } else if (iOrdinal == 1) {
                    i2++;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(i7);
                    sb2.append('b');
                    arrayList.add(sb2.toString());
                } else if (iOrdinal == 2) {
                    i3++;
                } else if (iOrdinal == 3) {
                    i4++;
                    if (i7 > 0) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(i7);
                        sb3.append('d');
                        arrayList.add(sb3.toString());
                    }
                } else if (iOrdinal == 4) {
                    i5++;
                }
            }
        }
        long j = f58665a8.get(this);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.f58671a3);
        sb4.append('@');
        sb4.append(AbstractC1117qo.m214435d1(this));
        sb4.append("[Pool Size {core = ");
        int i8 = this.f58668a0;
        sb4.append(i8);
        sb4.append(", max = ");
        sb4.append(this.f58669a1);
        sb4.append("}, Worker States {CPU = ");
        sb4.append(i);
        sb4.append(", blocking = ");
        sb4.append(i2);
        sb4.append(", parked = ");
        sb4.append(i3);
        sb4.append(", dormant = ");
        sb4.append(i4);
        sb4.append(", terminated = ");
        sb4.append(i5);
        sb4.append("}, running workers queues = ");
        sb4.append(arrayList);
        sb4.append(", global CPU queue size = ");
        sb4.append(this.f58672a4.m212786a2());
        sb4.append(", global blocking queue size = ");
        sb4.append(this.f58673a5.m212786a2());
        sb4.append(", Control State {created workers= ");
        sb4.append((int) (2097151 & j));
        sb4.append(", blocking tasks = ");
        sb4.append((int) ((4398044413952L & j) >> 21));
        sb4.append(", CPUs acquired = ");
        sb4.append(i8 - ((int) ((j & 9223367638808264704L) >> 42)));
        sb4.append("}]");
        return sb4.toString();
    }
}
