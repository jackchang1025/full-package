package p000;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.CompletionHandlerException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gb */
/* loaded from: classes2.dex */
public final class C0530gb extends AbstractC1259th implements InterfaceC0529ga, InterfaceC0921np, fe1 {

    /* renamed from: a5 */
    public static final AtomicIntegerFieldUpdater f56430a5 = AtomicIntegerFieldUpdater.newUpdater(C0530gb.class, "_decisionAndIndex");

    /* renamed from: a6 */
    public static final AtomicReferenceFieldUpdater f56431a6 = AtomicReferenceFieldUpdater.newUpdater(C0530gb.class, Object.class, "_state");

    /* renamed from: a7 */
    public static final AtomicReferenceFieldUpdater f56432a7 = AtomicReferenceFieldUpdater.newUpdater(C0530gb.class, Object.class, "_parentHandle");
    private volatile int _decisionAndIndex;
    private volatile Object _parentHandle;
    private volatile Object _state;

    /* renamed from: a3 */
    public final InterfaceC0876mv f56433a3;

    /* renamed from: a4 */
    public final InterfaceC0912ng f56434a4;

    public C0530gb(int i, InterfaceC0876mv interfaceC0876mv) {
        super(i);
        this.f56433a3 = interfaceC0876mv;
        this.f56434a4 = interfaceC0876mv.getContext();
        this._decisionAndIndex = 536870911;
        this._state = C0905n9.f58465a0;
    }

    /* renamed from: c2 */
    public static void m212912c2(wj0 wj0Var, Object obj) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + wj0Var + ", already has " + obj).toString());
    }

    /* renamed from: c7 */
    public static Object m212913c7(wj0 wj0Var, Object obj, int i, h10 h10Var) {
        if (obj instanceof C0730jt) {
            return obj;
        }
        if (i != 1 && i != 2) {
            return obj;
        }
        if (h10Var != null || (wj0Var instanceof C0509fu)) {
            return new C0728jr(obj, wj0Var instanceof C0509fu ? (C0509fu) wj0Var : null, h10Var, (CancellationException) null, 16);
        }
        return obj;
    }

    @Override // p000.fe1
    /* renamed from: a0 */
    public final void mo212795a0(jz0 jz0Var, int i) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i2;
        do {
            atomicIntegerFieldUpdater = f56430a5;
            i2 = atomicIntegerFieldUpdater.get(this);
            if ((i2 & 536870911) != 536870911) {
                throw new IllegalStateException("invokeOnCancellation should be called at most once");
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i2, ((i2 >> 29) << 29) + i));
        m212929b9(jz0Var);
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a1 */
    public final void mo212914a1(Object obj, CancellationException cancellationException) {
        CancellationException cancellationException2;
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56431a6;
            Object obj2 = atomicReferenceFieldUpdater.get(this);
            if (obj2 instanceof wj0) {
                throw new IllegalStateException("Not completed");
            }
            if (obj2 instanceof C0730jt) {
                return;
            }
            if (!(obj2 instanceof C0728jr)) {
                cancellationException2 = cancellationException;
                C0728jr c0728jr = new C0728jr(obj2, (C0509fu) null, (h10) null, cancellationException2, 14);
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj2, c0728jr)) {
                    if (atomicReferenceFieldUpdater.get(this) != obj2) {
                        break;
                    }
                }
                return;
            }
            C0728jr c0728jr2 = (C0728jr) obj2;
            if (c0728jr2.f57362a4 != null) {
                throw new IllegalStateException("Must be called at most once");
            }
            C0728jr c0728jrM213338a0 = C0728jr.m213338a0(c0728jr2, null, cancellationException, 15);
            while (!atomicReferenceFieldUpdater.compareAndSet(this, obj2, c0728jrM213338a0)) {
                if (atomicReferenceFieldUpdater.get(this) != obj2) {
                    cancellationException2 = cancellationException;
                }
            }
            C0509fu c0509fu = c0728jr2.f57359a1;
            if (c0509fu != null) {
                m212919a9(c0509fu, cancellationException);
            }
            h10 h10Var = c0728jr2.f57360a2;
            if (h10Var != null) {
                m212920b0(h10Var, cancellationException);
                return;
            }
            return;
            cancellationException = cancellationException2;
        }
    }

    @Override // p000.InterfaceC0529ga
    /* renamed from: a2 */
    public final C1347vr mo212899a2(h10 h10Var) {
        C1351vv c1351vv = C1351vv.f60710b1;
        C1347vr c1347vr = kg1.f57523a0;
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56431a6;
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (!(obj instanceof wj0)) {
                return null;
            }
            Object objM212913c7 = m212913c7((wj0) obj, c1351vv, this.f60222a2, h10Var);
            while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, objM212913c7)) {
                if (atomicReferenceFieldUpdater.get(this) != obj) {
                    break;
                }
            }
            if (!m212931c1()) {
                m212923b3();
            }
            return c1347vr;
        }
    }

    @Override // p000.InterfaceC0529ga
    /* renamed from: a3 */
    public final void mo212900a3(Object obj) {
        m212924b4(this.f60222a2);
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a4 */
    public final InterfaceC0876mv mo212915a4() {
        return this.f56433a3;
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a5 */
    public final Throwable mo212916a5(Object obj) {
        Throwable thMo212916a5 = super.mo212916a5(obj);
        if (thMo212916a5 != null) {
            return thMo212916a5;
        }
        return null;
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a6 */
    public final Object mo212917a6(Object obj) {
        return obj instanceof C0728jr ? ((C0728jr) obj).f57358a0 : obj;
    }

    @Override // p000.AbstractC1259th
    /* renamed from: a8 */
    public final Object mo212918a8() {
        return f56431a6.get(this);
    }

    /* renamed from: a9 */
    public final void m212919a9(C0509fu c0509fu, Throwable th) {
        try {
            c0509fu.m212864a0(th);
        } catch (Throwable th2) {
            kj1.m213574c1(this.f56434a4, new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    /* renamed from: b0 */
    public final void m212920b0(h10 h10Var, Throwable th) {
        try {
            h10Var.invoke(th);
        } catch (Throwable th2) {
            kj1.m213574c1(this.f56434a4, new CompletionHandlerException("Exception in resume onCancellation handler for " + this, th2));
        }
    }

    /* renamed from: b1 */
    public final void m212921b1(jz0 jz0Var, Throwable th) {
        InterfaceC0912ng interfaceC0912ng = this.f56434a4;
        int i = f56430a5.get(this) & 536870911;
        if (i == 536870911) {
            throw new IllegalStateException("The index for Segment.onCancellation(..) is broken");
        }
        try {
            jz0Var.mo213020a6(i, interfaceC0912ng);
        } catch (Throwable th2) {
            kj1.m213574c1(interfaceC0912ng, new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, th2));
        }
    }

    /* renamed from: b2 */
    public final void m212922b2(Throwable th) {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56431a6;
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof wj0) {
                C0534gf c0534gf = new C0534gf(this, th, (obj instanceof C0509fu) || (obj instanceof jz0));
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, c0534gf)) {
                    if (atomicReferenceFieldUpdater.get(this) != obj) {
                        break;
                    }
                }
                wj0 wj0Var = (wj0) obj;
                if (wj0Var instanceof C0509fu) {
                    m212919a9((C0509fu) obj, th);
                } else if (wj0Var instanceof jz0) {
                    m212921b1((jz0) obj, th);
                }
                if (!m212931c1()) {
                    m212923b3();
                }
                m212924b4(this.f60222a2);
                return;
            }
            return;
        }
    }

    /* renamed from: b3 */
    public final void m212923b3() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56432a7;
        InterfaceC1266tn interfaceC1266tn = (InterfaceC1266tn) atomicReferenceFieldUpdater.get(this);
        if (interfaceC1266tn == null) {
            return;
        }
        interfaceC1266tn.mo214761a2();
        atomicReferenceFieldUpdater.set(this, vj0.f60645a0);
    }

    /* renamed from: b4 */
    public final void m212924b4(int i) {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i2;
        do {
            atomicIntegerFieldUpdater = f56430a5;
            i2 = atomicIntegerFieldUpdater.get(this);
            int i3 = i2 >> 29;
            if (i3 != 0) {
                if (i3 != 1) {
                    throw new IllegalStateException("Already resumed");
                }
                boolean z = i == 4;
                InterfaceC0876mv interfaceC0876mv = this.f56433a3;
                if (!z && (interfaceC0876mv instanceof C1257tf)) {
                    boolean z2 = i == 1 || i == 2;
                    int i4 = this.f60222a2;
                    if (z2 == (i4 == 1 || i4 == 2)) {
                        C1257tf c1257tf = (C1257tf) interfaceC0876mv;
                        AbstractC0781a1 abstractC0781a1 = c1257tf.f60208a3;
                        InterfaceC0912ng context = c1257tf.f60209a4.getContext();
                        if (abstractC0781a1.mo213698c7()) {
                            abstractC0781a1.mo212723c6(context, this);
                            return;
                        }
                        AbstractC1424xo abstractC1424xoM213943a0 = m61.m213943a0();
                        if (abstractC1424xoM213943a0.f61166a2 >= 4294967296L) {
                            C0127ba c0127ba = abstractC1424xoM213943a0.f61168a4;
                            if (c0127ba == null) {
                                c0127ba = new C0127ba();
                                abstractC1424xoM213943a0.f61168a4 = c0127ba;
                            }
                            c0127ba.addLast(this);
                            return;
                        }
                        abstractC1424xoM213943a0.m215201d0(true);
                        try {
                            AbstractC1260ti.m214751a0(this, interfaceC0876mv, true);
                            do {
                            } while (abstractC1424xoM213943a0.m215202d2());
                        } finally {
                            try {
                                return;
                            } finally {
                            }
                        }
                        return;
                    }
                }
                AbstractC1260ti.m214751a0(this, interfaceC0876mv, z);
                return;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i2, 1073741824 + (536870911 & i2)));
    }

    /* renamed from: b5 */
    public final Object m212925b5() {
        AtomicIntegerFieldUpdater atomicIntegerFieldUpdater;
        int i;
        k70 k70Var;
        boolean zM212931c1 = m212931c1();
        do {
            atomicIntegerFieldUpdater = f56430a5;
            i = atomicIntegerFieldUpdater.get(this);
            int i2 = i >> 29;
            if (i2 != 0) {
                if (i2 != 2) {
                    throw new IllegalStateException("Already suspended");
                }
                if (zM212931c1) {
                    m212932c3();
                }
                Object obj = f56431a6.get(this);
                if (obj instanceof C0730jt) {
                    throw ((C0730jt) obj).f57378a0;
                }
                int i3 = this.f60222a2;
                if ((i3 != 1 && i3 != 2) || (k70Var = (k70) this.f56434a4.mo212745b4(C1351vv.f60702a3)) == null || k70Var.mo213470a0()) {
                    return mo212917a6(obj);
                }
                CancellationException cancellationExceptionM215259b8 = ((y70) k70Var).m215259b8();
                mo212914a1(obj, cancellationExceptionM215259b8);
                throw cancellationExceptionM215259b8;
            }
        } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, 536870912 + (536870911 & i)));
        if (((InterfaceC1266tn) f56432a7.get(this)) == null) {
            m212927b7();
        }
        if (zM212931c1) {
            m212932c3();
        }
        return CoroutineSingletons.f57606a0;
    }

    /* renamed from: b6 */
    public final void m212926b6() {
        InterfaceC1266tn interfaceC1266tnM212927b7 = m212927b7();
        if (interfaceC1266tnM212927b7 == null || (f56431a6.get(this) instanceof wj0)) {
            return;
        }
        interfaceC1266tnM212927b7.mo214761a2();
        f56432a7.set(this, vj0.f60645a0);
    }

    /* renamed from: b7 */
    public final InterfaceC1266tn m212927b7() {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        k70 k70Var = (k70) this.f56434a4.mo212745b4(C1351vv.f60702a3);
        if (k70Var == null) {
            return null;
        }
        InterfaceC1266tn interfaceC1266tnM215264c9 = ((y70) k70Var).m215264c9((2 & 1) == 0, (2 & 2) != 0, new C0580hg(this));
        do {
            atomicReferenceFieldUpdater = f56432a7;
            if (atomicReferenceFieldUpdater.compareAndSet(this, null, interfaceC1266tnM215264c9)) {
                break;
            }
        } while (atomicReferenceFieldUpdater.get(this) == null);
        return interfaceC1266tnM215264c9;
    }

    /* renamed from: b8 */
    public final void m212928b8(h10 h10Var) {
        m212929b9(h10Var instanceof C0509fu ? (C0509fu) h10Var : new C0509fu(2, h10Var));
    }

    /* renamed from: b9 */
    public final void m212929b9(wj0 wj0Var) {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56431a6;
            Object obj = atomicReferenceFieldUpdater.get(this);
            if (obj instanceof C0905n9) {
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj, wj0Var)) {
                    if (atomicReferenceFieldUpdater.get(this) != obj) {
                        break;
                    }
                }
                return;
            }
            boolean z = true;
            if (obj instanceof C0509fu ? true : obj instanceof jz0) {
                m212912c2(wj0Var, obj);
                throw null;
            }
            if (obj instanceof C0730jt) {
                C0730jt c0730jt = (C0730jt) obj;
                if (!C0730jt.f57377a1.compareAndSet(c0730jt, 0, 1)) {
                    m212912c2(wj0Var, obj);
                    throw null;
                }
                if (obj instanceof C0534gf) {
                    Throwable th = c0730jt.f57378a0;
                    if (wj0Var instanceof C0509fu) {
                        m212919a9((C0509fu) wj0Var, th);
                        return;
                    } else {
                        m212921b1((jz0) wj0Var, th);
                        return;
                    }
                }
                return;
            }
            if (obj instanceof C0728jr) {
                C0728jr c0728jr = (C0728jr) obj;
                if (c0728jr.f57359a1 != null) {
                    m212912c2(wj0Var, obj);
                    throw null;
                }
                if (wj0Var instanceof jz0) {
                    return;
                }
                C0509fu c0509fu = (C0509fu) wj0Var;
                Throwable th2 = c0728jr.f57362a4;
                if (th2 != null) {
                    m212919a9(c0509fu, th2);
                    return;
                }
                C0728jr c0728jrM213338a0 = C0728jr.m213338a0(c0728jr, c0509fu, null, 29);
                while (true) {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0728jrM213338a0)) {
                        break;
                    } else if (atomicReferenceFieldUpdater.get(this) != obj) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    return;
                }
            } else {
                if (wj0Var instanceof jz0) {
                    return;
                }
                C0728jr c0728jr2 = new C0728jr(obj, (C0509fu) wj0Var, (h10) null, (CancellationException) null, 28);
                while (true) {
                    if (atomicReferenceFieldUpdater.compareAndSet(this, obj, c0728jr2)) {
                        break;
                    } else if (atomicReferenceFieldUpdater.get(this) != obj) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    return;
                }
            }
        }
    }

    /* renamed from: c0 */
    public final boolean m212930c0() {
        return f56431a6.get(this) instanceof wj0;
    }

    /* renamed from: c1 */
    public final boolean m212931c1() {
        if (this.f60222a2 != 2) {
            return false;
        }
        InterfaceC0876mv interfaceC0876mv = this.f56433a3;
        t60.m214693b4(interfaceC0876mv, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
        return C1257tf.f60207a7.get((C1257tf) interfaceC0876mv) != null;
    }

    /* renamed from: c3 */
    public final void m212932c3() {
        InterfaceC0876mv interfaceC0876mv = this.f56433a3;
        Throwable th = null;
        C1257tf c1257tf = interfaceC0876mv instanceof C1257tf ? (C1257tf) interfaceC0876mv : null;
        if (c1257tf != null) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C1257tf.f60207a7;
            loop0: while (true) {
                Object obj = atomicReferenceFieldUpdater.get(c1257tf);
                C1347vr c1347vr = b81.f45734a5;
                if (obj == c1347vr) {
                    while (!atomicReferenceFieldUpdater.compareAndSet(c1257tf, c1347vr, this)) {
                        if (atomicReferenceFieldUpdater.get(c1257tf) != c1347vr) {
                            break;
                        }
                    }
                    break loop0;
                } else {
                    if (!(obj instanceof Throwable)) {
                        throw new IllegalStateException(("Inconsistent state " + obj).toString());
                    }
                    while (!atomicReferenceFieldUpdater.compareAndSet(c1257tf, obj, null)) {
                        if (atomicReferenceFieldUpdater.get(c1257tf) != obj) {
                            throw new IllegalArgumentException("Failed requirement.");
                        }
                    }
                    th = (Throwable) obj;
                }
            }
            if (th == null) {
                return;
            }
            m212923b3();
            m212922b2(th);
        }
    }

    /* renamed from: c4 */
    public final void m212933c4(Object obj, h10 h10Var) {
        m212934c5(obj, this.f60222a2, h10Var);
    }

    /* renamed from: c5 */
    public final void m212934c5(Object obj, int i, h10 h10Var) {
        while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f56431a6;
            Object obj2 = atomicReferenceFieldUpdater.get(this);
            if (obj2 instanceof wj0) {
                Object objM212913c7 = m212913c7((wj0) obj2, obj, i, h10Var);
                while (!atomicReferenceFieldUpdater.compareAndSet(this, obj2, objM212913c7)) {
                    if (atomicReferenceFieldUpdater.get(this) != obj2) {
                        break;
                    }
                }
                if (!m212931c1()) {
                    m212923b3();
                }
                m212924b4(i);
                return;
            }
            if (obj2 instanceof C0534gf) {
                C0534gf c0534gf = (C0534gf) obj2;
                if (C0534gf.f56453a2.compareAndSet(c0534gf, 0, 1)) {
                    if (h10Var != null) {
                        m212920b0(h10Var, c0534gf.f57378a0);
                        return;
                    }
                    return;
                }
            }
            throw new IllegalStateException(("Already resumed, but proposed with update " + obj).toString());
        }
    }

    /* renamed from: c6 */
    public final void m212935c6(AbstractC0781a1 abstractC0781a1) {
        C1351vv c1351vv = C1351vv.f60710b1;
        InterfaceC0876mv interfaceC0876mv = this.f56433a3;
        C1257tf c1257tf = interfaceC0876mv instanceof C1257tf ? (C1257tf) interfaceC0876mv : null;
        m212934c5(c1351vv, (c1257tf != null ? c1257tf.f60208a3 : null) == abstractC0781a1 ? 4 : this.f60222a2, null);
    }

    @Override // p000.InterfaceC0921np
    public final InterfaceC0921np getCallerFrame() {
        InterfaceC0876mv interfaceC0876mv = this.f56433a3;
        if (interfaceC0876mv instanceof InterfaceC0921np) {
            return (InterfaceC0921np) interfaceC0876mv;
        }
        return null;
    }

    @Override // p000.InterfaceC0876mv
    public final InterfaceC0912ng getContext() {
        return this.f56434a4;
    }

    @Override // p000.InterfaceC0876mv
    public final void resumeWith(Object obj) {
        Throwable thM213607a0 = Result.m213607a0(obj);
        if (thM213607a0 != null) {
            obj = new C0730jt(thM213607a0, false);
        }
        m212934c5(obj, this.f60222a2, null);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CancellableContinuation(");
        sb.append(AbstractC1117qo.m214462g1(this.f56433a3));
        sb.append("){");
        Object obj = f56431a6.get(this);
        sb.append(obj instanceof wj0 ? "Active" : obj instanceof C0534gf ? "Cancelled" : "Completed");
        sb.append("}@");
        sb.append(AbstractC1117qo.m214435d1(this));
        return sb.toString();
    }
}
