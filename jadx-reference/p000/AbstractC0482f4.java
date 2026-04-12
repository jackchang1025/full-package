package p000;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlinx.coroutines.CompletionHandlerException;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.internal.AbstractC0788a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: f4 */
/* loaded from: classes2.dex */
public abstract class AbstractC0482f4 extends y70 implements InterfaceC0876mv, InterfaceC0920no {

    /* renamed from: a2 */
    public final InterfaceC0912ng f56146a2;

    public AbstractC0482f4(InterfaceC0912ng interfaceC0912ng, boolean z) {
        super(z);
        m215263c8((k70) interfaceC0912ng.mo212745b4(C1351vv.f60702a3));
        this.f56146a2 = interfaceC0912ng.mo212744b2(this);
    }

    @Override // p000.InterfaceC0920no
    /* renamed from: a1 */
    public final InterfaceC0912ng mo210226a1() {
        return this.f56146a2;
    }

    @Override // p000.y70
    /* renamed from: b1 */
    public final String mo212739b1() {
        return getClass().getSimpleName().concat(" was cancelled");
    }

    @Override // p000.y70
    /* renamed from: c7 */
    public final void mo212740c7(CompletionHandlerException completionHandlerException) {
        kj1.m213574c1(this.f56146a2, completionHandlerException);
    }

    @Override // p000.y70
    /* renamed from: d5 */
    public final void mo212741d5(Object obj) {
        if (obj instanceof C0730jt) {
            C0730jt.f57377a1.get((C0730jt) obj);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: e0 */
    public final void m212742e0(CoroutineStart coroutineStart, AbstractC0482f4 abstractC0482f4, l10 l10Var) {
        int iOrdinal = coroutineStart.ordinal();
        if (iOrdinal == 0) {
            AbstractC0531gc.m212938a0(l10Var, abstractC0482f4, this);
            return;
        }
        if (iOrdinal != 1) {
            if (iOrdinal == 2) {
                InterfaceC0876mv interfaceC0876mvM213575c2 = kj1.m213575c2(((BaseContinuationImpl) l10Var).create(abstractC0482f4, this));
                int i = Result.f57558a1;
                interfaceC0876mvM213575c2.resumeWith(C1351vv.f60710b1);
                return;
            }
            if (iOrdinal != 3) {
                throw new NoWhenBranchMatchedException();
            }
            try {
                InterfaceC0912ng interfaceC0912ng = this.f56146a2;
                Object objM213735a1 = AbstractC0788a1.m213735a1(interfaceC0912ng, null);
                try {
                    b81.m210564a4(l10Var);
                    Object objInvoke = l10Var.invoke(abstractC0482f4, this);
                    if (objInvoke != CoroutineSingletons.f57606a0) {
                        int i2 = Result.f57558a1;
                        resumeWith(objInvoke);
                    }
                } finally {
                    AbstractC0788a1.m213734a0(interfaceC0912ng, objM213735a1);
                }
            } catch (Throwable th) {
                int i3 = Result.f57558a1;
                resumeWith(kg1.m213507a7(th));
            }
        }
    }

    @Override // p000.InterfaceC0876mv
    public final InterfaceC0912ng getContext() {
        return this.f56146a2;
    }

    @Override // p000.InterfaceC0876mv
    public final void resumeWith(Object obj) {
        Throwable thM213607a0 = Result.m213607a0(obj);
        if (thM213607a0 != null) {
            obj = new C0730jt(thM213607a0, false);
        }
        Object objM215265d1 = m215265d1(obj);
        if (objM215265d1 == t60.f60157a9) {
            return;
        }
        mo213092a6(objM215265d1);
    }
}
