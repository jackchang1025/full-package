package p000;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class rg1 {

    /* renamed from: a1 */
    public static final AtomicReferenceFieldUpdater f59771a1 = AtomicReferenceFieldUpdater.newUpdater(rg1.class, Object.class, "lastScheduledTask");

    /* renamed from: a2 */
    public static final AtomicIntegerFieldUpdater f59772a2 = AtomicIntegerFieldUpdater.newUpdater(rg1.class, "producerIndex");

    /* renamed from: a3 */
    public static final AtomicIntegerFieldUpdater f59773a3 = AtomicIntegerFieldUpdater.newUpdater(rg1.class, "consumerIndex");

    /* renamed from: a4 */
    public static final AtomicIntegerFieldUpdater f59774a4 = AtomicIntegerFieldUpdater.newUpdater(rg1.class, "blockingTasksInBuffer");

    /* renamed from: a0 */
    public final AtomicReferenceArray f59775a0 = new AtomicReferenceArray(128);
    private volatile int blockingTasksInBuffer;
    private volatile int consumerIndex;
    private volatile Object lastScheduledTask;
    private volatile int producerIndex;

    /* renamed from: a0 */
    public final i51 m214540a0() {
        i51 i51Var;
        while (true) {
            AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = f59773a3;
            int i = atomicIntegerFieldUpdater.get(this);
            if (i - f59772a2.get(this) == 0) {
                return null;
            }
            int i2 = i & 127;
            if (atomicIntegerFieldUpdater.compareAndSet(this, i, i + 1) && (i51Var = (i51) this.f59775a0.getAndSet(i2, null)) != null) {
                if (i51Var.f56799a1.f57268a0 == 1) {
                    f59774a4.decrementAndGet(this);
                }
                return i51Var;
            }
        }
    }

    /* renamed from: a1 */
    public final i51 m214541a1(int i, boolean z) {
        int i2 = i & 127;
        AtomicReferenceArray atomicReferenceArray = this.f59775a0;
        i51 i51Var = (i51) atomicReferenceArray.get(i2);
        if (i51Var != null) {
            if ((i51Var.f56799a1.f57268a0 == 1) == z) {
                while (!atomicReferenceArray.compareAndSet(i2, i51Var, null)) {
                    if (atomicReferenceArray.get(i2) != i51Var) {
                    }
                }
                if (z) {
                    f59774a4.decrementAndGet(this);
                }
                return i51Var;
            }
        }
        return null;
    }
}
