package p000;

import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.CoroutinesInternalError;
import kotlinx.coroutines.internal.AbstractC0788a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: th */
/* loaded from: classes2.dex */
public abstract class AbstractC1259th extends i51 {

    /* renamed from: a2 */
    public int f60222a2;

    public AbstractC1259th(int i) {
        super(0L, l51.f57836a6);
        this.f60222a2 = i;
    }

    /* renamed from: a1 */
    public abstract void mo212914a1(Object obj, CancellationException cancellationException);

    /* renamed from: a4 */
    public abstract InterfaceC0876mv mo212915a4();

    /* renamed from: a5 */
    public Throwable mo212916a5(Object obj) {
        C0730jt c0730jt = obj instanceof C0730jt ? (C0730jt) obj : null;
        if (c0730jt != null) {
            return c0730jt.f57378a0;
        }
        return null;
    }

    /* renamed from: a7 */
    public final void m214750a7(Throwable th, Throwable th2) {
        if (th == null && th2 == null) {
            return;
        }
        if (th != null && th2 != null) {
            kj1.m213556a3(th, th2);
        }
        if (th == null) {
            th = th2;
        }
        t60.m214692b3(th);
        kj1.m213574c1(mo212915a4().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th));
    }

    /* renamed from: a8 */
    public abstract Object mo212918a8();

    /* JADX WARN: Removed duplicated region for block: B:22:0x004e  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        k70 k70Var;
        Object objM213507a7 = C1351vv.f60710b1;
        j51 j51Var = this.f56799a1;
        try {
            InterfaceC0876mv interfaceC0876mvMo212915a4 = mo212915a4();
            t60.m214693b4(interfaceC0876mvMo212915a4, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<T of kotlinx.coroutines.DispatchedTask>");
            C1257tf c1257tf = (C1257tf) interfaceC0876mvMo212915a4;
            ContinuationImpl continuationImpl = c1257tf.f60209a4;
            Object obj = c1257tf.f60211a6;
            InterfaceC0912ng context = continuationImpl.getContext();
            Object objM213735a1 = AbstractC0788a1.m213735a1(context, obj);
            o81 o81VarM213695a6 = objM213735a1 != AbstractC0788a1.f57688a0 ? AbstractC0780a0.m213695a6(continuationImpl, context, objM213735a1) : null;
            try {
                InterfaceC0912ng context2 = continuationImpl.getContext();
                Object objMo212918a8 = mo212918a8();
                Throwable thMo212916a5 = mo212916a5(objMo212918a8);
                if (thMo212916a5 == null) {
                    int i = this.f60222a2;
                    boolean z = true;
                    if (i != 1 && i != 2) {
                        z = false;
                    }
                    k70Var = z ? (k70) context2.mo212745b4(C1351vv.f60702a3) : null;
                }
                if (k70Var != null && !k70Var.mo213470a0()) {
                    CancellationException cancellationExceptionM215259b8 = ((y70) k70Var).m215259b8();
                    mo212914a1(objMo212918a8, cancellationExceptionM215259b8);
                    int i2 = Result.f57558a1;
                    continuationImpl.resumeWith(kg1.m213507a7(cancellationExceptionM215259b8));
                } else if (thMo212916a5 != null) {
                    int i3 = Result.f57558a1;
                    continuationImpl.resumeWith(kg1.m213507a7(thMo212916a5));
                } else {
                    int i4 = Result.f57558a1;
                    continuationImpl.resumeWith(mo212917a6(objMo212918a8));
                }
                if (o81VarM213695a6 == null || o81VarM213695a6.m214164e1()) {
                    AbstractC0788a1.m213734a0(context, objM213735a1);
                }
                try {
                    j51Var.getClass();
                } catch (Throwable th) {
                    int i5 = Result.f57558a1;
                    objM213507a7 = kg1.m213507a7(th);
                }
                m214750a7(null, Result.m213607a0(objM213507a7));
            } catch (Throwable th2) {
                if (o81VarM213695a6 == null || o81VarM213695a6.m214164e1()) {
                    AbstractC0788a1.m213734a0(context, objM213735a1);
                }
                throw th2;
            }
        } catch (Throwable th3) {
            try {
                int i6 = Result.f57558a1;
                j51Var.getClass();
            } catch (Throwable th4) {
                int i7 = Result.f57558a1;
                objM213507a7 = kg1.m213507a7(th4);
            }
            m214750a7(th3, Result.m213607a0(objM213507a7));
        }
    }

    /* renamed from: a6 */
    public Object mo212917a6(Object obj) {
        return obj;
    }
}
