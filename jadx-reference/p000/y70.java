package p000;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CompletionHandlerException;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.internal.C0787a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class y70 implements k70, jm0 {

    /* renamed from: a0 */
    public static final AtomicReferenceFieldUpdater f61260a0 = AtomicReferenceFieldUpdater.newUpdater(y70.class, Object.class, "_state");

    /* renamed from: a1 */
    public static final AtomicReferenceFieldUpdater f61261a1 = AtomicReferenceFieldUpdater.newUpdater(y70.class, Object.class, "_parentHandle");
    private volatile Object _parentHandle;
    private volatile Object _state;

    public y70(boolean z) {
        this._state = z ? t60.f60162b4 : t60.f60161b3;
    }

    /* renamed from: d3 */
    public static C0582hi m215250d3(C0787a0 c0787a0) {
        while (c0787a0.mo213733a9()) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0787a0.f57686a1;
            C0787a0 c0787a0M213729a5 = c0787a0.m213729a5();
            if (c0787a0M213729a5 == null) {
                Object obj = atomicReferenceFieldUpdater.get(c0787a0);
                while (true) {
                    c0787a0 = (C0787a0) obj;
                    if (!c0787a0.mo213733a9()) {
                        break;
                    }
                    obj = atomicReferenceFieldUpdater.get(c0787a0);
                }
            } else {
                c0787a0 = c0787a0M213729a5;
            }
        }
        while (true) {
            c0787a0 = c0787a0.m213732a8();
            if (!c0787a0.mo213733a9()) {
                if (c0787a0 instanceof C0582hi) {
                    return (C0582hi) c0787a0;
                }
                if (c0787a0 instanceof uj0) {
                    return null;
                }
            }
        }
    }

    /* renamed from: d8 */
    public static String m215251d8(Object obj) {
        if (!(obj instanceof w70)) {
            return obj instanceof k50 ? ((k50) obj).mo213204a0() ? "Active" : "New" : obj instanceof C0730jt ? "Cancelled" : "Completed";
        }
        w70 w70Var = (w70) obj;
        return w70Var.m215012a3() ? "Cancelling" : w70Var.m215013a5() ? "Completing" : "Active";
    }

    @Override // p000.k70
    /* renamed from: a0 */
    public boolean mo213470a0() {
        Object objM215262c4 = m215262c4();
        return (objM215262c4 instanceof k50) && ((k50) objM215262c4).mo213204a0();
    }

    /* renamed from: a4 */
    public final boolean m215252a4(k50 k50Var, uj0 uj0Var, u70 u70Var) {
        C0787a0 c0787a0M213729a5;
        x70 x70Var = new x70(u70Var, this, k50Var);
        loop0: while (true) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0787a0.f57686a1;
            c0787a0M213729a5 = uj0Var.m213729a5();
            if (c0787a0M213729a5 == null) {
                Object obj = atomicReferenceFieldUpdater.get(uj0Var);
                while (true) {
                    c0787a0M213729a5 = (C0787a0) obj;
                    if (!c0787a0M213729a5.mo213733a9()) {
                        break;
                    }
                    obj = atomicReferenceFieldUpdater.get(c0787a0M213729a5);
                }
            }
            C0787a0.f57686a1.lazySet(u70Var, c0787a0M213729a5);
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = C0787a0.f57685a0;
            atomicReferenceFieldUpdater2.lazySet(u70Var, uj0Var);
            x70Var.f61029a2 = uj0Var;
            while (!atomicReferenceFieldUpdater2.compareAndSet(c0787a0M213729a5, uj0Var, x70Var)) {
                if (atomicReferenceFieldUpdater2.get(c0787a0M213729a5) != uj0Var) {
                    break;
                }
            }
        }
        return x70Var.mo210735a0(c0787a0M213729a5) == null;
    }

    /* renamed from: a6 */
    public void mo213092a6(Object obj) {
        mo212674a5(obj);
    }

    /* renamed from: a7 */
    public final void m215253a7(CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new JobCancellationException(mo212739b1(), null, this);
        }
        m215254a8(cancellationException);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x005e, code lost:
    
        r0 = r10;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a A[PHI: r0
      0x003a: PHI (r0v1 java.lang.Object) = (r0v0 java.lang.Object), (r0v12 java.lang.Object) binds: [B:3:0x0008, B:16:0x0036] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m215254a8(Object obj) {
        C1347vr c1347vr;
        Object objM215269d9 = t60.f60156a8;
        if (mo213946c2()) {
            do {
                Object objM215262c4 = m215262c4();
                if (!(objM215262c4 instanceof k50) || ((objM215262c4 instanceof w70) && ((w70) objM215262c4).m215013a5())) {
                    objM215269d9 = t60.f60156a8;
                    break;
                }
                objM215269d9 = m215269d9(objM215262c4, new C0730jt(m215257b6(obj), false));
            } while (objM215269d9 == t60.f60158b0);
            if (objM215269d9 != t60.f60157a9) {
                if (objM215269d9 == t60.f60156a8) {
                    Throwable thM215257b6 = null;
                    loop1: while (true) {
                        Object objM215262c42 = m215262c4();
                        if (!(objM215262c42 instanceof w70)) {
                            if (!(objM215262c42 instanceof k50)) {
                                c1347vr = t60.f60159b1;
                                break;
                            }
                            if (thM215257b6 == null) {
                                thM215257b6 = m215257b6(obj);
                            }
                            k50 k50Var = (k50) objM215262c42;
                            if (k50Var.mo213204a0()) {
                                uj0 uj0VarM215261c3 = m215261c3(k50Var);
                                if (uj0VarM215261c3 != null) {
                                    w70 w70Var = new w70(uj0VarM215261c3, thM215257b6);
                                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f61260a0;
                                    while (!atomicReferenceFieldUpdater.compareAndSet(this, k50Var, w70Var)) {
                                        if (atomicReferenceFieldUpdater.get(this) != k50Var) {
                                            break;
                                        }
                                    }
                                    m215266d4(uj0VarM215261c3, thM215257b6);
                                    c1347vr = t60.f60156a8;
                                    break loop1;
                                }
                                continue;
                            } else {
                                Object objM215269d92 = m215269d9(objM215262c42, new C0730jt(thM215257b6, false));
                                if (objM215269d92 == t60.f60156a8) {
                                    throw new IllegalStateException(("Cannot happen in " + objM215262c42).toString());
                                }
                                if (objM215269d92 != t60.f60158b0) {
                                    objM215269d9 = objM215269d92;
                                    break;
                                }
                            }
                        } else {
                            synchronized (objM215262c42) {
                                if (w70.f60799a3.get((w70) objM215262c42) == t60.f60160b2) {
                                    c1347vr = t60.f60159b1;
                                } else {
                                    boolean zM215012a3 = ((w70) objM215262c42).m215012a3();
                                    if (thM215257b6 == null) {
                                        thM215257b6 = m215257b6(obj);
                                    }
                                    ((w70) objM215262c42).m215010a1(thM215257b6);
                                    Throwable thM215011a2 = zM215012a3 ? null : ((w70) objM215262c42).m215011a2();
                                    if (thM215011a2 != null) {
                                        m215266d4(((w70) objM215262c42).f60800a0, thM215011a2);
                                    }
                                    c1347vr = t60.f60156a8;
                                }
                            }
                        }
                    }
                }
                if (objM215269d9 != t60.f60156a8 && objM215269d9 != t60.f60157a9) {
                    if (objM215269d9 == t60.f60159b1) {
                        return false;
                    }
                    mo212674a5(objM215269d9);
                    return true;
                }
            }
        }
        return true;
    }

    /* renamed from: a9 */
    public final boolean m215255a9(Throwable th) {
        if (mo213093d0()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        InterfaceC0581hh interfaceC0581hh = (InterfaceC0581hh) f61261a1.get(this);
        return (interfaceC0581hh == null || interfaceC0581hh == vj0.f60645a0) ? z : interfaceC0581hh.mo213038a3(th) || z;
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: b0 */
    public final Object mo212743b0(Object obj, l10 l10Var) {
        t60.m214695b6(l10Var, "operation");
        return l10Var.invoke(obj, this);
    }

    /* renamed from: b1 */
    public String mo212739b1() {
        return "Job was cancelled";
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: b2 */
    public final InterfaceC0912ng mo212744b2(InterfaceC0912ng interfaceC0912ng) {
        return AbstractC0775a0.m213638a1(this, interfaceC0912ng);
    }

    /* renamed from: b3 */
    public boolean mo215235b3(Throwable th) {
        if (th instanceof CancellationException) {
            return true;
        }
        return m215254a8(th) && mo213945c1();
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: b4 */
    public final InterfaceC0910ne mo212745b4(InterfaceC0911nf interfaceC0911nf) {
        t60.m214695b6(interfaceC0911nf, "key");
        if (t60.m214686a2(C1351vv.f60702a3, interfaceC0911nf)) {
            return this;
        }
        return null;
    }

    /* renamed from: b5 */
    public final void m215256b5(k50 k50Var, Object obj) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f61261a1;
        InterfaceC0581hh interfaceC0581hh = (InterfaceC0581hh) atomicReferenceFieldUpdater.get(this);
        if (interfaceC0581hh != null) {
            interfaceC0581hh.mo214761a2();
            atomicReferenceFieldUpdater.set(this, vj0.f60645a0);
        }
        CompletionHandlerException completionHandlerException = null;
        C0730jt c0730jt = obj instanceof C0730jt ? (C0730jt) obj : null;
        Throwable th = c0730jt != null ? c0730jt.f57378a0 : null;
        if (k50Var instanceof u70) {
            try {
                ((u70) k50Var).mo213037b1(th);
                return;
            } catch (Throwable th2) {
                mo212740c7(new CompletionHandlerException("Exception in completion handler " + k50Var + " for " + this, th2));
                return;
            }
        }
        uj0 uj0VarMo213205a4 = k50Var.mo213205a4();
        if (uj0VarMo213205a4 != null) {
            Object objM213731a7 = uj0VarMo213205a4.m213731a7();
            t60.m214693b4(objM213731a7, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
            for (C0787a0 c0787a0M213732a8 = (C0787a0) objM213731a7; !c0787a0M213732a8.equals(uj0VarMo213205a4); c0787a0M213732a8 = c0787a0M213732a8.m213732a8()) {
                if (c0787a0M213732a8 instanceof u70) {
                    u70 u70Var = (u70) c0787a0M213732a8;
                    try {
                        u70Var.mo213037b1(th);
                    } catch (Throwable th3) {
                        if (completionHandlerException != null) {
                            kj1.m213556a3(completionHandlerException, th3);
                        } else {
                            completionHandlerException = new CompletionHandlerException("Exception in completion handler " + u70Var + " for " + this, th3);
                        }
                    }
                }
            }
            if (completionHandlerException != null) {
                mo212740c7(completionHandlerException);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.Throwable] */
    /* renamed from: b6 */
    public final Throwable m215257b6(Object obj) {
        CancellationException cancellationExceptionM215011a2;
        if (obj instanceof Throwable) {
            return (Throwable) obj;
        }
        y70 y70Var = (y70) ((jm0) obj);
        Object objM215262c4 = y70Var.m215262c4();
        if (objM215262c4 instanceof w70) {
            cancellationExceptionM215011a2 = ((w70) objM215262c4).m215011a2();
        } else if (objM215262c4 instanceof C0730jt) {
            cancellationExceptionM215011a2 = ((C0730jt) objM215262c4).f57378a0;
        } else {
            if (objM215262c4 instanceof k50) {
                throw new IllegalStateException(("Cannot be cancelling child in this state: " + objM215262c4).toString());
            }
            cancellationExceptionM215011a2 = null;
        }
        CancellationException cancellationException = cancellationExceptionM215011a2 instanceof CancellationException ? cancellationExceptionM215011a2 : null;
        return cancellationException == null ? new JobCancellationException("Parent job is ".concat(m215251d8(objM215262c4)), cancellationExceptionM215011a2, y70Var) : cancellationException;
    }

    /* renamed from: b7 */
    public final Object m215258b7(w70 w70Var, Object obj) {
        Throwable thM215260b9;
        C0730jt c0730jt = obj instanceof C0730jt ? (C0730jt) obj : null;
        Throwable th = c0730jt != null ? c0730jt.f57378a0 : null;
        synchronized (w70Var) {
            w70Var.m215012a3();
            ArrayList arrayListM215014a6 = w70Var.m215014a6(th);
            thM215260b9 = m215260b9(w70Var, arrayListM215014a6);
            if (thM215260b9 != null && arrayListM215014a6.size() > 1) {
                Set setNewSetFromMap = Collections.newSetFromMap(new IdentityHashMap(arrayListM215014a6.size()));
                int size = arrayListM215014a6.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayListM215014a6.get(i);
                    i++;
                    Throwable th2 = (Throwable) obj2;
                    if (th2 != thM215260b9 && th2 != thM215260b9 && !(th2 instanceof CancellationException) && setNewSetFromMap.add(th2)) {
                        kj1.m213556a3(thM215260b9, th2);
                    }
                }
            }
        }
        if (thM215260b9 != null && thM215260b9 != th) {
            obj = new C0730jt(thM215260b9, false);
        }
        if (thM215260b9 != null && (m215255a9(thM215260b9) || mo214814c6(thM215260b9))) {
            t60.m214693b4(obj, "null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
            C0730jt.f57377a1.compareAndSet((C0730jt) obj, 0, 1);
        }
        mo212741d5(obj);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f61260a0;
        Object l50Var = obj instanceof k50 ? new l50((k50) obj) : obj;
        while (!atomicReferenceFieldUpdater.compareAndSet(this, w70Var, l50Var) && atomicReferenceFieldUpdater.get(this) == w70Var) {
        }
        m215256b5(w70Var, obj);
        return obj;
    }

    /* renamed from: b8 */
    public final CancellationException m215259b8() {
        CancellationException cancellationException;
        Object objM215262c4 = m215262c4();
        if (!(objM215262c4 instanceof w70)) {
            if (objM215262c4 instanceof k50) {
                throw new IllegalStateException(("Job is still new or active: " + this).toString());
            }
            if (!(objM215262c4 instanceof C0730jt)) {
                return new JobCancellationException(getClass().getSimpleName().concat(" has completed normally"), null, this);
            }
            Throwable th = ((C0730jt) objM215262c4).f57378a0;
            cancellationException = th instanceof CancellationException ? (CancellationException) th : null;
            return cancellationException == null ? new JobCancellationException(mo212739b1(), th, this) : cancellationException;
        }
        Throwable thM215011a2 = ((w70) objM215262c4).m215011a2();
        if (thM215011a2 == null) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        }
        String strConcat = getClass().getSimpleName().concat(" is cancelling");
        cancellationException = thM215011a2 instanceof CancellationException ? (CancellationException) thM215011a2 : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        if (strConcat == null) {
            strConcat = mo212739b1();
        }
        return new JobCancellationException(strConcat, thM215011a2, this);
    }

    /* renamed from: b9 */
    public final Throwable m215260b9(w70 w70Var, ArrayList arrayList) {
        Object obj;
        Object obj2 = null;
        if (arrayList.isEmpty()) {
            if (w70Var.m215012a3()) {
                return new JobCancellationException(mo212739b1(), null, this);
            }
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i2);
            i2++;
            if (!(((Throwable) obj) instanceof CancellationException)) {
                break;
            }
        }
        Throwable th = (Throwable) obj;
        if (th != null) {
            return th;
        }
        Throwable th2 = (Throwable) arrayList.get(0);
        if (th2 instanceof TimeoutCancellationException) {
            int size2 = arrayList.size();
            while (true) {
                if (i >= size2) {
                    break;
                }
                Object obj3 = arrayList.get(i);
                i++;
                Throwable th3 = (Throwable) obj3;
                if (th3 != th2 && (th3 instanceof TimeoutCancellationException)) {
                    obj2 = obj3;
                    break;
                }
            }
            Throwable th4 = (Throwable) obj2;
            if (th4 != null) {
                return th4;
            }
        }
        return th2;
    }

    @Override // p000.InterfaceC0912ng
    /* renamed from: c0 */
    public final InterfaceC0912ng mo212746c0(InterfaceC0911nf interfaceC0911nf) {
        return AbstractC0775a0.m213637a0(this, interfaceC0911nf);
    }

    /* renamed from: c1 */
    public boolean mo213945c1() {
        return true;
    }

    /* renamed from: c2 */
    public boolean mo213946c2() {
        return false;
    }

    /* renamed from: c3 */
    public final uj0 m215261c3(k50 k50Var) {
        uj0 uj0VarMo213205a4 = k50Var.mo213205a4();
        if (uj0VarMo213205a4 != null) {
            return uj0VarMo213205a4;
        }
        if (k50Var instanceof C1391wv) {
            return new uj0();
        }
        if (k50Var instanceof u70) {
            m215268d7((u70) k50Var);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + k50Var).toString());
    }

    /* renamed from: c4 */
    public final Object m215262c4() {
        while (true) {
            Object obj = f61260a0.get(this);
            if (!(obj instanceof il0)) {
                return obj;
            }
            ((il0) obj).mo210735a0(this);
        }
    }

    /* renamed from: c6 */
    public boolean mo214814c6(Throwable th) {
        return false;
    }

    /* renamed from: c8 */
    public final void m215263c8(k70 k70Var) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f61261a1;
        vj0 vj0Var = vj0.f60645a0;
        if (k70Var == null) {
            atomicReferenceFieldUpdater.set(this, vj0Var);
            return;
        }
        y70 y70Var = (y70) k70Var;
        loop0: while (true) {
            Object objM215262c4 = y70Var.m215262c4();
            boolean z = objM215262c4 instanceof C1391wv;
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f61260a0;
            if (!z) {
                if (!(objM215262c4 instanceof j50)) {
                    break;
                }
                uj0 uj0Var = ((j50) objM215262c4).f57267a0;
                while (!atomicReferenceFieldUpdater2.compareAndSet(y70Var, objM215262c4, uj0Var)) {
                    if (atomicReferenceFieldUpdater2.get(y70Var) != objM215262c4) {
                        break;
                    }
                }
                y70Var.getClass();
                break loop0;
            }
            if (!((C1391wv) objM215262c4).f60973a0) {
                C1391wv c1391wv = t60.f60162b4;
                while (!atomicReferenceFieldUpdater2.compareAndSet(y70Var, objM215262c4, c1391wv)) {
                    if (atomicReferenceFieldUpdater2.get(y70Var) != objM215262c4) {
                        break;
                    }
                }
                y70Var.getClass();
                break loop0;
            }
            break;
        }
        InterfaceC0581hh interfaceC0581hh = (InterfaceC0581hh) y70Var.m215264c9((2 & 1) == 0, (2 & 2) != 0, new C0582hi(this));
        atomicReferenceFieldUpdater.set(this, interfaceC0581hh);
        if (m215262c4() instanceof k50) {
            return;
        }
        interfaceC0581hh.mo214761a2();
        atomicReferenceFieldUpdater.set(this, vj0Var);
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x00b4 A[SYNTHETIC] */
    /* renamed from: c9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final InterfaceC1266tn m215264c9(boolean z, boolean z2, h10 h10Var) {
        u70 c1267to;
        Throwable thM215011a2;
        if (z) {
            c1267to = h10Var instanceof l70 ? (l70) h10Var : null;
            if (c1267to == null) {
                c1267to = new z60(h10Var);
            }
        } else {
            c1267to = h10Var instanceof u70 ? (u70) h10Var : null;
            if (c1267to == null) {
                c1267to = new C1267to(1, h10Var);
            }
        }
        c1267to.f60336a3 = this;
        loop0: while (true) {
            Object objM215262c4 = m215262c4();
            if (objM215262c4 instanceof C1391wv) {
                C1391wv c1391wv = (C1391wv) objM215262c4;
                if (c1391wv.f60973a0) {
                    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f61260a0;
                    while (!atomicReferenceFieldUpdater.compareAndSet(this, objM215262c4, c1267to)) {
                        if (atomicReferenceFieldUpdater.get(this) != objM215262c4) {
                            break;
                        }
                    }
                    break loop0;
                }
                uj0 uj0Var = new uj0();
                k50 j50Var = c1391wv.f60973a0 ? uj0Var : new j50(uj0Var);
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f61260a0;
                while (!atomicReferenceFieldUpdater2.compareAndSet(this, c1391wv, j50Var) && atomicReferenceFieldUpdater2.get(this) == c1391wv) {
                }
            } else {
                if (!(objM215262c4 instanceof k50)) {
                    if (z2) {
                        C0730jt c0730jt = objM215262c4 instanceof C0730jt ? (C0730jt) objM215262c4 : null;
                        h10Var.invoke(c0730jt != null ? c0730jt.f57378a0 : null);
                    }
                    return vj0.f60645a0;
                }
                k50 k50Var = (k50) objM215262c4;
                uj0 uj0VarMo213205a4 = k50Var.mo213205a4();
                if (uj0VarMo213205a4 == null) {
                    m215268d7((u70) objM215262c4);
                } else {
                    InterfaceC1266tn interfaceC1266tn = vj0.f60645a0;
                    if (z && (objM215262c4 instanceof w70)) {
                        synchronized (objM215262c4) {
                            try {
                                thM215011a2 = ((w70) objM215262c4).m215011a2();
                                if (thM215011a2 == null || ((h10Var instanceof C0582hi) && !((w70) objM215262c4).m215013a5())) {
                                    if (m215252a4((k50) objM215262c4, uj0VarMo213205a4, c1267to)) {
                                        if (thM215011a2 == null) {
                                            return c1267to;
                                        }
                                        interfaceC1266tn = c1267to;
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (thM215011a2 == null) {
                        }
                    } else {
                        thM215011a2 = null;
                        if (thM215011a2 == null) {
                            if (z2) {
                                h10Var.invoke(thM215011a2);
                            }
                            return interfaceC1266tn;
                        }
                        if (m215252a4(k50Var, uj0VarMo213205a4, c1267to)) {
                            break;
                        }
                    }
                }
            }
        }
        return c1267to;
    }

    /* renamed from: d0 */
    public boolean mo213093d0() {
        return this instanceof C0455eg;
    }

    /* renamed from: d1 */
    public final Object m215265d1(Object obj) {
        Object objM215269d9;
        do {
            objM215269d9 = m215269d9(m215262c4(), obj);
            if (objM215269d9 == t60.f60156a8) {
                String str = "Job " + this + " is already complete or completing, but is being completed with " + obj;
                C0730jt c0730jt = obj instanceof C0730jt ? (C0730jt) obj : null;
                throw new IllegalStateException(str, c0730jt != null ? c0730jt.f57378a0 : null);
            }
        } while (objM215269d9 == t60.f60158b0);
        return objM215269d9;
    }

    /* renamed from: d2 */
    public String mo214491d2() {
        return getClass().getSimpleName();
    }

    /* renamed from: d4 */
    public final void m215266d4(uj0 uj0Var, Throwable th) {
        Object objM213731a7 = uj0Var.m213731a7();
        t60.m214693b4(objM213731a7, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
        CompletionHandlerException completionHandlerException = null;
        for (C0787a0 c0787a0M213732a8 = (C0787a0) objM213731a7; !c0787a0M213732a8.equals(uj0Var); c0787a0M213732a8 = c0787a0M213732a8.m213732a8()) {
            if (c0787a0M213732a8 instanceof l70) {
                u70 u70Var = (u70) c0787a0M213732a8;
                try {
                    u70Var.mo213037b1(th);
                } catch (Throwable th2) {
                    if (completionHandlerException != null) {
                        kj1.m213556a3(completionHandlerException, th2);
                    } else {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + u70Var + " for " + this, th2);
                    }
                }
            }
        }
        if (completionHandlerException != null) {
            mo212740c7(completionHandlerException);
        }
        m215255a9(th);
    }

    /* renamed from: d7 */
    public final void m215268d7(u70 u70Var) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        uj0 uj0Var = new uj0();
        u70Var.getClass();
        C0787a0.f57686a1.lazySet(uj0Var, u70Var);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = C0787a0.f57685a0;
        atomicReferenceFieldUpdater2.lazySet(uj0Var, u70Var);
        loop0: while (true) {
            if (u70Var.m213731a7() == u70Var) {
                while (!atomicReferenceFieldUpdater2.compareAndSet(u70Var, u70Var, uj0Var)) {
                    if (atomicReferenceFieldUpdater2.get(u70Var) != u70Var) {
                        break;
                    }
                }
                uj0Var.m213730a6(u70Var);
                break loop0;
            }
            break;
        }
        C0787a0 c0787a0M213732a8 = u70Var.m213732a8();
        do {
            atomicReferenceFieldUpdater = f61260a0;
            if (atomicReferenceFieldUpdater.compareAndSet(this, u70Var, c0787a0M213732a8)) {
                return;
            }
        } while (atomicReferenceFieldUpdater.get(this) == u70Var);
    }

    /* renamed from: d9 */
    public final Object m215269d9(Object obj, Object obj2) {
        if (!(obj instanceof k50)) {
            return t60.f60156a8;
        }
        if (((obj instanceof C1391wv) || (obj instanceof u70)) && !(obj instanceof C0582hi) && !(obj2 instanceof C0730jt)) {
            k50 k50Var = (k50) obj;
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = f61260a0;
            Object l50Var = obj2 instanceof k50 ? new l50((k50) obj2) : obj2;
            while (!atomicReferenceFieldUpdater.compareAndSet(this, k50Var, l50Var)) {
                if (atomicReferenceFieldUpdater.get(this) != k50Var) {
                    return t60.f60158b0;
                }
            }
            mo212741d5(obj2);
            m215256b5(k50Var, obj2);
            return obj2;
        }
        k50 k50Var2 = (k50) obj;
        uj0 uj0VarM215261c3 = m215261c3(k50Var2);
        if (uj0VarM215261c3 == null) {
            return t60.f60158b0;
        }
        C0582hi c0582hiM215250d3 = null;
        w70 w70Var = k50Var2 instanceof w70 ? (w70) k50Var2 : null;
        if (w70Var == null) {
            w70Var = new w70(uj0VarM215261c3, null);
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        synchronized (w70Var) {
            if (w70Var.m215013a5()) {
                return t60.f60156a8;
            }
            w70.f60797a1.set(w70Var, 1);
            if (w70Var != k50Var2) {
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2 = f61260a0;
                while (!atomicReferenceFieldUpdater2.compareAndSet(this, k50Var2, w70Var)) {
                    if (atomicReferenceFieldUpdater2.get(this) != k50Var2) {
                        return t60.f60158b0;
                    }
                }
            }
            boolean zM215012a3 = w70Var.m215012a3();
            C0730jt c0730jt = obj2 instanceof C0730jt ? (C0730jt) obj2 : null;
            if (c0730jt != null) {
                w70Var.m215010a1(c0730jt.f57378a0);
            }
            Throwable thM215011a2 = w70Var.m215011a2();
            if (zM215012a3) {
                thM215011a2 = null;
            }
            ref$ObjectRef.f57626a0 = thM215011a2;
            if (thM215011a2 != null) {
                m215266d4(uj0VarM215261c3, thM215011a2);
            }
            C0582hi c0582hi = k50Var2 instanceof C0582hi ? (C0582hi) k50Var2 : null;
            if (c0582hi == null) {
                uj0 uj0VarMo213205a4 = k50Var2.mo213205a4();
                if (uj0VarMo213205a4 != null) {
                    c0582hiM215250d3 = m215250d3(uj0VarMo213205a4);
                }
            } else {
                c0582hiM215250d3 = c0582hi;
            }
            if (c0582hiM215250d3 != null) {
                while (c0582hiM215250d3.f56672a4.m215264c9((2 & 1) == 0, (2 & 2) != 0, new v70(this, w70Var, c0582hiM215250d3, obj2)) == vj0.f60645a0) {
                    c0582hiM215250d3 = m215250d3(c0582hiM215250d3);
                    if (c0582hiM215250d3 == null) {
                    }
                }
                return t60.f60157a9;
            }
            return m215258b7(w70Var, obj2);
        }
    }

    @Override // p000.InterfaceC0910ne
    public final InterfaceC0911nf getKey() {
        return C1351vv.f60702a3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mo214491d2() + '{' + m215251d8(m215262c4()) + '}');
        sb.append('@');
        sb.append(AbstractC1117qo.m214435d1(this));
        return sb.toString();
    }

    /* renamed from: d6 */
    public void m215267d6() {
    }

    /* renamed from: a5 */
    public void mo212674a5(Object obj) {
    }

    /* renamed from: c7 */
    public void mo212740c7(CompletionHandlerException completionHandlerException) {
        throw completionHandlerException;
    }

    /* renamed from: d5 */
    public void mo212741d5(Object obj) {
    }
}
