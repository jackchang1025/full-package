package p000;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ic0 {

    /* renamed from: a4 */
    public static final gc0 f56856a4 = new gc0(null);

    /* renamed from: a5 */
    public static final AtomicReferenceFieldUpdater f56857a5 = AtomicReferenceFieldUpdater.newUpdater(ic0.class, Object.class, "_next");

    /* renamed from: a6 */
    public static final AtomicLongFieldUpdater f56858a6 = AtomicLongFieldUpdater.newUpdater(ic0.class, "_state");

    /* renamed from: a7 */
    public static final C1347vr f56859a7 = new C1347vr("REMOVE_FROZEN");
    private volatile Object _next;
    private volatile long _state;

    /* renamed from: a0 */
    public final int f56860a0;

    /* renamed from: a1 */
    public final boolean f56861a1;

    /* renamed from: a2 */
    public final int f56862a2;

    /* renamed from: a3 */
    public final AtomicReferenceArray f56863a3;

    public ic0(int i, boolean z) {
        this.f56860a0 = i;
        this.f56861a1 = z;
        int i2 = i - 1;
        this.f56862a2 = i2;
        this.f56863a3 = new AtomicReferenceArray(i);
        if (i2 > 1073741823) {
            throw new IllegalStateException("Check failed.");
        }
        if ((i & i2) != 0) {
            throw new IllegalStateException("Check failed.");
        }
    }

    /* renamed from: a0 */
    public final int m213149a0(Object obj) {
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f56858a6;
            long j = atomicLongFieldUpdater.get(this);
            long j2 = 3458764513820540928L & j;
            gc0 gc0Var = f56856a4;
            if (j2 != 0) {
                return gc0Var.addFailReason(j);
            }
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            int i3 = this.f56862a2;
            if (((i2 + 2) & i3) == (i & i3)) {
                return 1;
            }
            boolean z = this.f56861a1;
            AtomicReferenceArray atomicReferenceArray = this.f56863a3;
            if (z || atomicReferenceArray.get(i2 & i3) == null) {
                if (f56858a6.compareAndSet(this, j, gc0Var.updateTail(j, (i2 + 1) & 1073741823))) {
                    atomicReferenceArray.set(i2 & i3, obj);
                    ic0 ic0VarM213151a2 = this;
                    while ((atomicLongFieldUpdater.get(ic0VarM213151a2) & 1152921504606846976L) != 0) {
                        ic0VarM213151a2 = ic0VarM213151a2.m213151a2();
                        AtomicReferenceArray atomicReferenceArray2 = ic0VarM213151a2.f56863a3;
                        int i4 = ic0VarM213151a2.f56862a2 & i2;
                        Object obj2 = atomicReferenceArray2.get(i4);
                        if ((obj2 instanceof hc0) && ((hc0) obj2).f56648a0 == i2) {
                            atomicReferenceArray2.set(i4, obj);
                        } else {
                            ic0VarM213151a2 = null;
                        }
                        if (ic0VarM213151a2 == null) {
                            return 0;
                        }
                    }
                    return 0;
                }
            } else {
                int i5 = this.f56860a0;
                if (i5 < 1024 || ((i2 - i) & 1073741823) > (i5 >> 1)) {
                    return 1;
                }
            }
        }
    }

    /* renamed from: a1 */
    public final boolean m213150a1() {
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j;
        do {
            atomicLongFieldUpdater = f56858a6;
            j = atomicLongFieldUpdater.get(this);
            if ((j & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j) != 0) {
                return false;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(this, j, 2305843009213693952L | j));
        return true;
    }

    /* renamed from: a2 */
    public final ic0 m213151a2() {
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j;
        ic0 ic0Var;
        while (true) {
            atomicLongFieldUpdater = f56858a6;
            j = atomicLongFieldUpdater.get(this);
            if ((j & 1152921504606846976L) != 0) {
                ic0Var = this;
                break;
            }
            long j2 = j | 1152921504606846976L;
            ic0Var = this;
            if (atomicLongFieldUpdater.compareAndSet(ic0Var, j, j2)) {
                j = j2;
                break;
            }
        }
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56857a5;
            ic0 ic0Var2 = (ic0) atomicReferenceFieldUpdater.get(this);
            if (ic0Var2 != null) {
                return ic0Var2;
            }
            ic0 ic0Var3 = new ic0(ic0Var.f56860a0 * 2, ic0Var.f56861a1);
            int i = (int) (1073741823 & j);
            int i2 = (int) ((1152921503533105152L & j) >> 30);
            while (true) {
                int i3 = ic0Var.f56862a2;
                int i4 = i & i3;
                if (i4 == (i3 & i2)) {
                    break;
                }
                Object hc0Var = ic0Var.f56863a3.get(i4);
                if (hc0Var == null) {
                    hc0Var = new hc0(i);
                }
                ic0Var3.f56863a3.set(ic0Var3.f56862a2 & i, hc0Var);
                i++;
            }
            atomicLongFieldUpdater.set(ic0Var3, f56856a4.m212939wo(j, 1152921504606846976L));
            while (!atomicReferenceFieldUpdater.compareAndSet(this, null, ic0Var3) && atomicReferenceFieldUpdater.get(this) == null) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        return null;
     */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m213152a3() {
        ic0 ic0VarM213151a2 = this;
        while (true) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = f56858a6;
            long j = atomicLongFieldUpdater.get(ic0VarM213151a2);
            if ((j & 1152921504606846976L) != 0) {
                return f56859a7;
            }
            int i = (int) (j & 1073741823);
            int i2 = ic0VarM213151a2.f56862a2;
            int i3 = i & i2;
            if ((((int) ((1152921503533105152L & j) >> 30)) & i2) == i3) {
                break;
            }
            AtomicReferenceArray atomicReferenceArray = ic0VarM213151a2.f56863a3;
            Object obj = atomicReferenceArray.get(i3);
            boolean z = ic0VarM213151a2.f56861a1;
            if (obj == null) {
                if (z) {
                    break;
                }
            } else {
                if (obj instanceof hc0) {
                    break;
                }
                int i4 = 1073741823 & (i + 1);
                AtomicLongFieldUpdater atomicLongFieldUpdater2 = f56858a6;
                gc0 gc0Var = f56856a4;
                if (atomicLongFieldUpdater2.compareAndSet(ic0VarM213151a2, j, gc0Var.updateHead(j, i4))) {
                    atomicReferenceArray.set(i3, null);
                    return obj;
                }
                ic0VarM213151a2 = this;
                if (z) {
                    while (true) {
                        long j2 = atomicLongFieldUpdater.get(ic0VarM213151a2);
                        int i5 = (int) (j2 & 1073741823);
                        if ((j2 & 1152921504606846976L) != 0) {
                            ic0VarM213151a2 = ic0VarM213151a2.m213151a2();
                        } else {
                            ic0 ic0Var = ic0VarM213151a2;
                            if (f56858a6.compareAndSet(ic0Var, j2, gc0Var.updateHead(j2, i4))) {
                                ic0Var.f56863a3.set(i5 & ic0Var.f56862a2, null);
                                ic0VarM213151a2 = null;
                            } else {
                                ic0VarM213151a2 = ic0Var;
                            }
                        }
                        if (ic0VarM213151a2 == null) {
                            return obj;
                        }
                    }
                }
            }
        }
    }
}
