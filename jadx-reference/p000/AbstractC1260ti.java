package p000;

import kotlin.Result;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.internal.AbstractC0788a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ti */
/* loaded from: classes2.dex */
public abstract class AbstractC1260ti {
    /* renamed from: a0 */
    public static final void m214751a0(C0530gb c0530gb, InterfaceC0876mv interfaceC0876mv, boolean z) {
        Object objMo212917a6;
        Object obj = C0530gb.f56431a6.get(c0530gb);
        Throwable thMo212916a5 = c0530gb.mo212916a5(obj);
        if (thMo212916a5 != null) {
            int i = Result.f57558a1;
            objMo212917a6 = kg1.m213507a7(thMo212916a5);
        } else {
            int i2 = Result.f57558a1;
            objMo212917a6 = c0530gb.mo212917a6(obj);
        }
        if (!z) {
            interfaceC0876mv.resumeWith(objMo212917a6);
            return;
        }
        t60.m214693b4(interfaceC0876mv, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<T of kotlinx.coroutines.DispatchedTaskKt.resume>");
        C1257tf c1257tf = (C1257tf) interfaceC0876mv;
        ContinuationImpl continuationImpl = c1257tf.f60209a4;
        Object obj2 = c1257tf.f60211a6;
        InterfaceC0912ng context = continuationImpl.getContext();
        Object objM213735a1 = AbstractC0788a1.m213735a1(context, obj2);
        o81 o81VarM213695a6 = objM213735a1 != AbstractC0788a1.f57688a0 ? AbstractC0780a0.m213695a6(continuationImpl, context, objM213735a1) : null;
        try {
            continuationImpl.resumeWith(objMo212917a6);
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
}
