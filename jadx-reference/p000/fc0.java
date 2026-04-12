package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class fc0 {

    /* renamed from: a0 */
    public static final AtomicReferenceFieldUpdater f56195a0 = AtomicReferenceFieldUpdater.newUpdater(fc0.class, Object.class, "_cur");
    private volatile Object _cur = new ic0(8, false);

    /* renamed from: a0 */
    public final boolean m212784a0(Runnable runnable) {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56195a0;
            ic0 ic0Var = (ic0) atomicReferenceFieldUpdater.get(this);
            int iM213149a0 = ic0Var.m213149a0(runnable);
            if (iM213149a0 == 0) {
                return true;
            }
            if (iM213149a0 == 1) {
                ic0 ic0VarM213151a2 = ic0Var.m213151a2();
                while (!atomicReferenceFieldUpdater.compareAndSet(this, ic0Var, ic0VarM213151a2) && atomicReferenceFieldUpdater.get(this) == ic0Var) {
                }
            } else if (iM213149a0 == 2) {
                return false;
            }
        }
    }

    /* renamed from: a1 */
    public final void m212785a1() {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56195a0;
            ic0 ic0Var = (ic0) atomicReferenceFieldUpdater.get(this);
            if (ic0Var.m213150a1()) {
                return;
            }
            ic0 ic0VarM213151a2 = ic0Var.m213151a2();
            while (!atomicReferenceFieldUpdater.compareAndSet(this, ic0Var, ic0VarM213151a2) && atomicReferenceFieldUpdater.get(this) == ic0Var) {
            }
        }
    }

    /* renamed from: a2 */
    public final int m212786a2() {
        ic0 ic0Var = (ic0) f56195a0.get(this);
        ic0Var.getClass();
        long j = ic0.f56858a6.get(ic0Var);
        return (((int) ((j & 1152921503533105152L) >> 30)) - ((int) (1073741823 & j))) & 1073741823;
    }

    /* renamed from: a3 */
    public final Object m212787a3() {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56195a0;
            ic0 ic0Var = (ic0) atomicReferenceFieldUpdater.get(this);
            Object objM213152a3 = ic0Var.m213152a3();
            if (objM213152a3 != ic0.f56859a7) {
                return objM213152a3;
            }
            ic0 ic0VarM213151a2 = ic0Var.m213151a2();
            while (!atomicReferenceFieldUpdater.compareAndSet(this, ic0Var, ic0VarM213151a2) && atomicReferenceFieldUpdater.get(this) == ic0Var) {
            }
        }
    }
}
