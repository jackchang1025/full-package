package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlinx.coroutines.internal.C0787a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class u70 extends C0787a0 implements InterfaceC1266tn, k50, h10 {

    /* renamed from: a3 */
    public y70 f60336a3;

    @Override // p000.k50
    /* renamed from: a0 */
    public final boolean mo213204a0() {
        return true;
    }

    @Override // p000.InterfaceC1266tn
    /* renamed from: a2 */
    public final void mo214761a2() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        y70 y70VarM214818b0 = m214818b0();
        while (true) {
            Object objM215262c4 = y70VarM214818b0.m215262c4();
            if (objM215262c4 instanceof u70) {
                if (objM215262c4 != this) {
                    return;
                }
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = y70.f61260a0;
                C1391wv c1391wv = t60.f60162b4;
                while (!atomicReferenceFieldUpdater2.compareAndSet(y70VarM214818b0, objM215262c4, c1391wv)) {
                    if (atomicReferenceFieldUpdater2.get(y70VarM214818b0) != objM215262c4) {
                        break;
                    }
                }
                return;
            }
            if (!(objM215262c4 instanceof k50) || ((k50) objM215262c4).mo213205a4() == null) {
                return;
            }
            while (true) {
                Object objM213731a7 = m213731a7();
                if (objM213731a7 instanceof kr0) {
                    return;
                }
                if (objM213731a7 == this) {
                    return;
                }
                t60.m214693b4(objM213731a7, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
                C0787a0 c0787a0 = (C0787a0) objM213731a7;
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3 = C0787a0.f57687a2;
                kr0 kr0Var = (kr0) atomicReferenceFieldUpdater3.get(c0787a0);
                if (kr0Var == null) {
                    kr0Var = new kr0(c0787a0);
                    atomicReferenceFieldUpdater3.lazySet(c0787a0, kr0Var);
                }
                do {
                    atomicReferenceFieldUpdater = C0787a0.f57685a0;
                    if (atomicReferenceFieldUpdater.compareAndSet(this, objM213731a7, kr0Var)) {
                        c0787a0.m213729a5();
                        return;
                    }
                } while (atomicReferenceFieldUpdater.get(this) == objM213731a7);
            }
        }
    }

    @Override // p000.k50
    /* renamed from: a4 */
    public final uj0 mo213205a4() {
        return null;
    }

    /* renamed from: b0 */
    public final y70 m214818b0() {
        y70 y70Var = this.f60336a3;
        if (y70Var != null) {
            return y70Var;
        }
        t60.m214724f2("job");
        throw null;
    }

    /* renamed from: b1 */
    public abstract void mo213037b1(Throwable th);

    @Override // kotlinx.coroutines.internal.C0787a0
    public final String toString() {
        return getClass().getSimpleName() + '@' + AbstractC1117qo.m214435d1(this) + "[job@" + AbstractC1117qo.m214435d1(m214818b0()) + ']';
    }
}
