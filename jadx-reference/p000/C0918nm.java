package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nm */
/* loaded from: classes2.dex */
public final class C0918nm extends Thread {

    /* renamed from: a8 */
    public static final AtomicIntegerFieldUpdater f58648a8 = AtomicIntegerFieldUpdater.newUpdater(C0918nm.class, "workerCtl");

    /* renamed from: a0 */
    public final rg1 f58649a0;

    /* renamed from: a1 */
    public final Ref$ObjectRef f58650a1;

    /* renamed from: a2 */
    public CoroutineScheduler$WorkerState f58651a2;

    /* renamed from: a3 */
    public long f58652a3;

    /* renamed from: a4 */
    public long f58653a4;

    /* renamed from: a5 */
    public int f58654a5;

    /* renamed from: a6 */
    public boolean f58655a6;

    /* renamed from: a7 */
    public final /* synthetic */ ExecutorC0919nn f58656a7;
    private volatile int indexInArray;
    private volatile Object nextParkedWorker;
    private volatile int workerCtl;

    public C0918nm(ExecutorC0919nn executorC0919nn, int i) {
        this.f58656a7 = executorC0919nn;
        setDaemon(true);
        this.f58649a0 = new rg1();
        this.f58650a1 = new Ref$ObjectRef();
        this.f58651a2 = CoroutineScheduler$WorkerState.f57695a3;
        this.nextParkedWorker = ExecutorC0919nn.f58667b0;
        aq0.f45594a0.getClass();
        this.f58654a5 = aq0.f45595a1.mo210497a1();
        m214117a5(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0043, code lost:
    
        r12 = p000.rg1.f59773a3.get(r9);
        r0 = p000.rg1.f59772a2.get(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
    
        if (r12 == r0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0057, code lost:
    
        if (p000.rg1.f59774a4.get(r9) != 0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005a, code lost:
    
        r0 = r0 - 1;
        r1 = r9.m214541a1(r0, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0060, code lost:
    
        if (r1 == null) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0062, code lost:
    
        r7 = r1;
     */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final i51 m214112a0(boolean z) {
        i51 i51VarM214116a4;
        i51 i51VarM214116a42;
        long j;
        CoroutineScheduler$WorkerState coroutineScheduler$WorkerState = this.f58651a2;
        ExecutorC0919nn executorC0919nn = this.f58656a7;
        i51 i51Var = null;
        rg1 rg1Var = this.f58649a0;
        CoroutineScheduler$WorkerState coroutineScheduler$WorkerState2 = CoroutineScheduler$WorkerState.f57692a0;
        if (coroutineScheduler$WorkerState != coroutineScheduler$WorkerState2) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = ExecutorC0919nn.f58665a8;
            do {
                j = atomicLongFieldUpdater.get(executorC0919nn);
                if (((int) ((9223367638808264704L & j) >> 42)) == 0) {
                    rg1Var.getClass();
                    loop1: while (true) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = rg1.f59771a1;
                        i51 i51Var2 = (i51) atomicReferenceFieldUpdater.get(rg1Var);
                        if (i51Var2 == null || i51Var2.f56799a1.f57268a0 != 1) {
                            break;
                        }
                        while (!atomicReferenceFieldUpdater.compareAndSet(rg1Var, i51Var2, null)) {
                            if (atomicReferenceFieldUpdater.get(rg1Var) != i51Var2) {
                                break;
                            }
                        }
                        i51Var = i51Var2;
                        break loop1;
                    }
                    if (i51Var != null) {
                        return i51Var;
                    }
                    i51 i51Var3 = (i51) executorC0919nn.f58673a5.m212787a3();
                    return i51Var3 == null ? m214120a8(1) : i51Var3;
                }
            } while (!ExecutorC0919nn.f58665a8.compareAndSet(executorC0919nn, j, j - 4398046511104L));
            this.f58651a2 = coroutineScheduler$WorkerState2;
        }
        if (z) {
            boolean z2 = m214115a3(executorC0919nn.f58668a0 * 2) == 0;
            if (z2 && (i51VarM214116a42 = m214116a4()) != null) {
                return i51VarM214116a42;
            }
            rg1Var.getClass();
            i51 i51VarM214540a0 = (i51) rg1.f59771a1.getAndSet(rg1Var, null);
            if (i51VarM214540a0 == null) {
                i51VarM214540a0 = rg1Var.m214540a0();
            }
            if (i51VarM214540a0 != null) {
                return i51VarM214540a0;
            }
            if (!z2 && (i51VarM214116a4 = m214116a4()) != null) {
                return i51VarM214116a4;
            }
        } else {
            i51 i51VarM214116a43 = m214116a4();
            if (i51VarM214116a43 != null) {
                return i51VarM214116a43;
            }
        }
        return m214120a8(3);
    }

    /* renamed from: a1 */
    public final int m214113a1() {
        return this.indexInArray;
    }

    /* renamed from: a2 */
    public final Object m214114a2() {
        return this.nextParkedWorker;
    }

    /* renamed from: a3 */
    public final int m214115a3(int i) {
        int i2 = this.f58654a5;
        int i3 = i2 ^ (i2 << 13);
        int i4 = i3 ^ (i3 >> 17);
        int i5 = i4 ^ (i4 << 5);
        this.f58654a5 = i5;
        int i6 = i - 1;
        return (i6 & i) == 0 ? i5 & i6 : (i5 & Integer.MAX_VALUE) % i;
    }

    /* renamed from: a4 */
    public final i51 m214116a4() {
        int iM214115a3 = m214115a3(2);
        ExecutorC0919nn executorC0919nn = this.f58656a7;
        if (iM214115a3 == 0) {
            i51 i51Var = (i51) executorC0919nn.f58672a4.m212787a3();
            return i51Var != null ? i51Var : (i51) executorC0919nn.f58673a5.m212787a3();
        }
        i51 i51Var2 = (i51) executorC0919nn.f58673a5.m212787a3();
        return i51Var2 != null ? i51Var2 : (i51) executorC0919nn.f58672a4.m212787a3();
    }

    /* renamed from: a5 */
    public final void m214117a5(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f58656a7.f58671a3);
        sb.append("-worker-");
        sb.append(i == 0 ? "TERMINATED" : String.valueOf(i));
        setName(sb.toString());
        this.indexInArray = i;
    }

    /* renamed from: a6 */
    public final void m214118a6(Object obj) {
        this.nextParkedWorker = obj;
    }

    /* renamed from: a7 */
    public final boolean m214119a7(CoroutineScheduler$WorkerState coroutineScheduler$WorkerState) {
        CoroutineScheduler$WorkerState coroutineScheduler$WorkerState2 = this.f58651a2;
        boolean z = coroutineScheduler$WorkerState2 == CoroutineScheduler$WorkerState.f57692a0;
        if (z) {
            ExecutorC0919nn.f58665a8.addAndGet(this.f58656a7, 4398046511104L);
        }
        if (coroutineScheduler$WorkerState2 != coroutineScheduler$WorkerState) {
            this.f58651a2 = coroutineScheduler$WorkerState;
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x006b, code lost:
    
        r7 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a1, code lost:
    
        r7 = -2;
        r5 = r4;
     */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final i51 m214120a8(int i) {
        long j;
        i51 i51VarM214541a1;
        long j2;
        long j3;
        i51 i51Var;
        AtomicLongFieldUpdater atomicLongFieldUpdater = ExecutorC0919nn.f58665a8;
        ExecutorC0919nn executorC0919nn = this.f58656a7;
        int i2 = (int) (atomicLongFieldUpdater.get(executorC0919nn) & 2097151);
        i51 i51Var2 = null;
        if (i2 < 2) {
            return null;
        }
        int iM214115a3 = m214115a3(i2);
        int i3 = 0;
        long jMin = Long.MAX_VALUE;
        while (i3 < i2) {
            iM214115a3++;
            if (iM214115a3 > i2) {
                iM214115a3 = 1;
            }
            C0918nm c0918nm = (C0918nm) executorC0919nn.f58674a6.m214332a1(iM214115a3);
            if (c0918nm != null && c0918nm != this) {
                rg1 rg1Var = c0918nm.f58649a0;
                if (i != 3) {
                    rg1Var.getClass();
                    int i4 = rg1.f59773a3.get(rg1Var);
                    int i5 = rg1.f59772a2.get(rg1Var);
                    boolean z = i == 1;
                    while (true) {
                        if (i4 == i5) {
                            j = 0;
                            break;
                        }
                        j = 0;
                        if (!z || rg1.f59774a4.get(rg1Var) != 0) {
                            int i6 = i4 + 1;
                            i51VarM214541a1 = rg1Var.m214541a1(i4, z);
                            if (i51VarM214541a1 != null) {
                                break;
                            }
                            i4 = i6;
                        } else {
                            break;
                        }
                    }
                } else {
                    i51VarM214541a1 = rg1Var.m214540a0();
                    j = 0;
                }
                Ref$ObjectRef ref$ObjectRef = this.f58650a1;
                if (i51VarM214541a1 != null) {
                    ref$ObjectRef.f57626a0 = i51VarM214541a1;
                    i51Var = i51Var2;
                    j3 = -1;
                    j2 = -1;
                } else {
                    while (true) {
                        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = rg1.f59771a1;
                        i51 i51Var3 = (i51) atomicReferenceFieldUpdater.get(rg1Var);
                        if (i51Var3 == null) {
                            j2 = -1;
                            break;
                        }
                        j2 = -1;
                        if (((i51Var3.f56799a1.f57268a0 == 1 ? 1 : 2) & i) == 0) {
                            break;
                        }
                        l51.f57835a5.getClass();
                        rg1 rg1Var2 = rg1Var;
                        long jNanoTime = System.nanoTime() - i51Var3.f56798a0;
                        long j4 = l51.f57831a1;
                        if (jNanoTime < j4) {
                            j3 = j4 - jNanoTime;
                            i51Var = null;
                            break;
                        }
                        do {
                            i51Var = null;
                            if (atomicReferenceFieldUpdater.compareAndSet(rg1Var2, i51Var3, null)) {
                                ref$ObjectRef.f57626a0 = i51Var3;
                                j3 = -1;
                                break;
                            }
                        } while (atomicReferenceFieldUpdater.get(rg1Var2) == i51Var3);
                        rg1Var = rg1Var2;
                        i51Var2 = null;
                    }
                }
                if (j3 == j2) {
                    i51 i51Var4 = (i51) ref$ObjectRef.f57626a0;
                    ref$ObjectRef.f57626a0 = i51Var;
                    return i51Var4;
                }
                if (j3 > j) {
                    jMin = Math.min(jMin, j3);
                }
            }
            i3++;
            i51Var2 = null;
        }
        if (jMin == Long.MAX_VALUE) {
            jMin = 0;
        }
        this.f58653a4 = jMin;
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0004, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0004, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0004, code lost:
    
        continue;
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        long j;
        loop0: while (true) {
            boolean z = false;
            while (ExecutorC0919nn.f58666a9.get(this.f58656a7) == 0) {
                CoroutineScheduler$WorkerState coroutineScheduler$WorkerState = this.f58651a2;
                CoroutineScheduler$WorkerState coroutineScheduler$WorkerState2 = CoroutineScheduler$WorkerState.f57696a4;
                if (coroutineScheduler$WorkerState == coroutineScheduler$WorkerState2) {
                    break loop0;
                }
                i51 i51VarM214112a0 = m214112a0(this.f58655a6);
                if (i51VarM214112a0 != null) {
                    this.f58653a4 = 0L;
                    CoroutineScheduler$WorkerState coroutineScheduler$WorkerState3 = CoroutineScheduler$WorkerState.f57693a1;
                    ExecutorC0919nn executorC0919nn = this.f58656a7;
                    int i = i51VarM214112a0.f56799a1.f57268a0;
                    this.f58652a3 = 0L;
                    if (this.f58651a2 == CoroutineScheduler$WorkerState.f57694a2) {
                        this.f58651a2 = coroutineScheduler$WorkerState3;
                    }
                    if (i != 0 && m214119a7(coroutineScheduler$WorkerState3) && !executorC0919nn.m214133b2() && !executorC0919nn.m214132b0(ExecutorC0919nn.f58665a8.get(executorC0919nn))) {
                        executorC0919nn.m214133b2();
                    }
                    try {
                        i51VarM214112a0.run();
                    } catch (Throwable th) {
                        Thread threadCurrentThread = Thread.currentThread();
                        threadCurrentThread.getUncaughtExceptionHandler().uncaughtException(threadCurrentThread, th);
                    }
                    if (i != 0) {
                        ExecutorC0919nn.f58665a8.addAndGet(executorC0919nn, -2097152L);
                        if (this.f58651a2 != coroutineScheduler$WorkerState2) {
                            this.f58651a2 = CoroutineScheduler$WorkerState.f57695a3;
                        }
                    }
                } else {
                    this.f58655a6 = false;
                    if (this.f58653a4 == 0) {
                        Object obj = this.nextParkedWorker;
                        C1347vr c1347vr = ExecutorC0919nn.f58667b0;
                        if (obj != c1347vr) {
                            f58648a8.set(this, -1);
                            while (this.nextParkedWorker != ExecutorC0919nn.f58667b0) {
                                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f58648a8;
                                if (atomicIntegerFieldUpdater.get(this) == -1) {
                                    ExecutorC0919nn executorC0919nn2 = this.f58656a7;
                                    AtomicIntegerFieldUpdater atomicIntegerFieldUpdater2 = ExecutorC0919nn.f58666a9;
                                    if (atomicIntegerFieldUpdater2.get(executorC0919nn2) != 0) {
                                        break;
                                    }
                                    CoroutineScheduler$WorkerState coroutineScheduler$WorkerState4 = this.f58651a2;
                                    CoroutineScheduler$WorkerState coroutineScheduler$WorkerState5 = CoroutineScheduler$WorkerState.f57696a4;
                                    if (coroutineScheduler$WorkerState4 == coroutineScheduler$WorkerState5) {
                                        break;
                                    }
                                    m214119a7(CoroutineScheduler$WorkerState.f57694a2);
                                    Thread.interrupted();
                                    if (this.f58652a3 == 0) {
                                        j = 2097151;
                                        this.f58652a3 = System.nanoTime() + this.f58656a7.f58670a2;
                                    } else {
                                        j = 2097151;
                                    }
                                    LockSupport.parkNanos(this.f58656a7.f58670a2);
                                    if (System.nanoTime() - this.f58652a3 >= 0) {
                                        this.f58652a3 = 0L;
                                        ExecutorC0919nn executorC0919nn3 = this.f58656a7;
                                        synchronized (executorC0919nn3.f58674a6) {
                                            try {
                                                if (!(atomicIntegerFieldUpdater2.get(executorC0919nn3) != 0)) {
                                                    AtomicLongFieldUpdater atomicLongFieldUpdater = ExecutorC0919nn.f58665a8;
                                                    if (((int) (atomicLongFieldUpdater.get(executorC0919nn3) & j)) > executorC0919nn3.f58668a0) {
                                                        if (atomicIntegerFieldUpdater.compareAndSet(this, -1, 1)) {
                                                            int i2 = this.indexInArray;
                                                            m214117a5(0);
                                                            executorC0919nn3.m214131a7(this, i2, 0);
                                                            int andDecrement = (int) (atomicLongFieldUpdater.getAndDecrement(executorC0919nn3) & j);
                                                            if (andDecrement != i2) {
                                                                Object objM214332a1 = executorC0919nn3.f58674a6.m214332a1(andDecrement);
                                                                t60.m214692b3(objM214332a1);
                                                                C0918nm c0918nm = (C0918nm) objM214332a1;
                                                                executorC0919nn3.f58674a6.m214333a2(i2, c0918nm);
                                                                c0918nm.m214117a5(i2);
                                                                executorC0919nn3.m214131a7(c0918nm, andDecrement, i2);
                                                            }
                                                            executorC0919nn3.f58674a6.m214333a2(andDecrement, null);
                                                            this.f58651a2 = coroutineScheduler$WorkerState5;
                                                        }
                                                    }
                                                }
                                            } catch (Throwable th2) {
                                                throw th2;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            ExecutorC0919nn executorC0919nn4 = this.f58656a7;
                            if (this.nextParkedWorker == c1347vr) {
                                AtomicLongFieldUpdater atomicLongFieldUpdater2 = ExecutorC0919nn.f58664a7;
                                while (true) {
                                    long j2 = atomicLongFieldUpdater2.get(executorC0919nn4);
                                    int i3 = this.indexInArray;
                                    this.nextParkedWorker = executorC0919nn4.f58674a6.m214332a1((int) (j2 & 2097151));
                                    ExecutorC0919nn executorC0919nn5 = executorC0919nn4;
                                    if (ExecutorC0919nn.f58664a7.compareAndSet(executorC0919nn5, j2, ((j2 + 2097152) & (-2097152)) | i3)) {
                                        break;
                                    } else {
                                        executorC0919nn4 = executorC0919nn5;
                                    }
                                }
                            }
                        }
                    } else if (z) {
                        m214119a7(CoroutineScheduler$WorkerState.f57694a2);
                        Thread.interrupted();
                        LockSupport.parkNanos(this.f58653a4);
                        this.f58653a4 = 0L;
                    } else {
                        z = true;
                    }
                }
            }
            break loop0;
        }
        m214119a7(CoroutineScheduler$WorkerState.f57696a4);
    }
}
