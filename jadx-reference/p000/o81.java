package p000;

import kotlin.Pair;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.AbstractC0781a1;
import kotlinx.coroutines.internal.AbstractC0788a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class o81 extends hu0 {

    /* renamed from: a4 */
    public final ThreadLocal f58760a4;
    private volatile boolean threadLocalIsSet;

    /* JADX WARN: Illegal instructions before constructor call */
    public o81(InterfaceC0912ng interfaceC0912ng, InterfaceC0876mv interfaceC0876mv) {
        p81 p81Var = p81.f59166a0;
        super(interfaceC0912ng.mo212745b4(p81Var) == null ? interfaceC0912ng.mo212744b2(p81Var) : interfaceC0912ng, interfaceC0876mv);
        this.f58760a4 = new ThreadLocal();
        if (interfaceC0876mv.getContext().mo212745b4(C1351vv.f60700a1) instanceof AbstractC0781a1) {
            return;
        }
        Object objM213735a1 = AbstractC0788a1.m213735a1(interfaceC0912ng, null);
        AbstractC0788a1.m213734a0(interfaceC0912ng, objM213735a1);
        m214165e2(interfaceC0912ng, objM213735a1);
    }

    @Override // p000.hu0, p000.y70
    /* renamed from: a6 */
    public final void mo213092a6(Object obj) {
        if (this.threadLocalIsSet) {
            Pair pair = (Pair) this.f58760a4.get();
            if (pair != null) {
                AbstractC0788a1.m213734a0((InterfaceC0912ng) pair.f57556a0, pair.f57557a1);
            }
            this.f58760a4.remove();
        }
        Object objM213356a0 = AbstractC0732jv.m213356a0(obj);
        InterfaceC0876mv interfaceC0876mv = this.f56754a3;
        InterfaceC0912ng context = interfaceC0876mv.getContext();
        Object objM213735a1 = AbstractC0788a1.m213735a1(context, null);
        o81 o81VarM213695a6 = objM213735a1 != AbstractC0788a1.f57688a0 ? AbstractC0780a0.m213695a6(interfaceC0876mv, context, objM213735a1) : null;
        try {
            this.f56754a3.resumeWith(objM213356a0);
            if (o81VarM213695a6 == null || o81VarM213695a6.m214164e1()) {
                AbstractC0788a1.m213734a0(context, objM213735a1);
            }
        } catch (Throwable th) {
            if (o81VarM213695a6 == null || o81VarM213695a6.m214164e1()) {
                AbstractC0788a1.m213734a0(context, objM213735a1);
            }
            throw th;
        }
    }

    /* renamed from: e1 */
    public final boolean m214164e1() {
        boolean z = this.threadLocalIsSet && this.f58760a4.get() == null;
        this.f58760a4.remove();
        return !z;
    }

    /* renamed from: e2 */
    public final void m214165e2(InterfaceC0912ng interfaceC0912ng, Object obj) {
        this.threadLocalIsSet = true;
        this.f58760a4.set(new Pair(interfaceC0912ng, obj));
    }
}
