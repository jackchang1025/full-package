package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ki */
/* loaded from: classes2.dex */
public abstract class AbstractC0759ki {

    /* renamed from: a0 */
    public static final AtomicReferenceFieldUpdater f57528a0 = AtomicReferenceFieldUpdater.newUpdater(AbstractC0759ki.class, Object.class, "_next");

    /* renamed from: a1 */
    public static final AtomicReferenceFieldUpdater f57529a1 = AtomicReferenceFieldUpdater.newUpdater(AbstractC0759ki.class, Object.class, "_prev");
    private volatile Object _next;
    private volatile Object _prev;

    public AbstractC0759ki(jz0 jz0Var) {
        this._prev = jz0Var;
    }

    /* renamed from: a0 */
    public final void m213553a0() {
        f57529a1.lazySet(this, null);
    }

    /* renamed from: a1 */
    public final AbstractC0759ki m213554a1() {
        Object obj = f57528a0.get(this);
        if (obj == AbstractC1117qo.f59537a1) {
            return null;
        }
        return (AbstractC0759ki) obj;
    }

    /* renamed from: a2 */
    public abstract boolean mo213361a2();

    /* renamed from: a3 */
    public final void m213555a3() {
        AbstractC0759ki abstractC0759kiM213554a1;
        if (m213554a1() == null) {
            return;
        }
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f57529a1;
            AbstractC0759ki abstractC0759ki = (AbstractC0759ki) atomicReferenceFieldUpdater.get(this);
            while (abstractC0759ki != null && abstractC0759ki.mo213361a2()) {
                abstractC0759ki = (AbstractC0759ki) atomicReferenceFieldUpdater.get(abstractC0759ki);
            }
            AbstractC0759ki abstractC0759kiM213554a12 = m213554a1();
            t60.m214692b3(abstractC0759kiM213554a12);
            while (abstractC0759kiM213554a12.mo213361a2() && (abstractC0759kiM213554a1 = abstractC0759kiM213554a12.m213554a1()) != null) {
                abstractC0759kiM213554a12 = abstractC0759kiM213554a1;
            }
            while (true) {
                Object obj = atomicReferenceFieldUpdater.get(abstractC0759kiM213554a12);
                AbstractC0759ki abstractC0759ki2 = ((AbstractC0759ki) obj) == null ? null : abstractC0759ki;
                while (!atomicReferenceFieldUpdater.compareAndSet(abstractC0759kiM213554a12, obj, abstractC0759ki2)) {
                    if (atomicReferenceFieldUpdater.get(abstractC0759kiM213554a12) != obj) {
                        break;
                    }
                }
            }
            if (abstractC0759ki != null) {
                f57528a0.set(abstractC0759ki, abstractC0759kiM213554a12);
            }
            if (!abstractC0759kiM213554a12.mo213361a2() || abstractC0759kiM213554a12.m213554a1() == null) {
                if (abstractC0759ki == null || !abstractC0759ki.mo213361a2()) {
                    return;
                }
            }
        }
    }
}
