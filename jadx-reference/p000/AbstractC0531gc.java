package p000;

import kotlin.Result;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gc */
/* loaded from: classes2.dex */
public abstract class AbstractC0531gc {
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a0 */
    public static void m212938a0(l10 l10Var, AbstractC0482f4 abstractC0482f4, AbstractC0482f4 abstractC0482f42) {
        try {
            InterfaceC0876mv interfaceC0876mvM213575c2 = kj1.m213575c2(((BaseContinuationImpl) l10Var).create(abstractC0482f4, abstractC0482f42));
            int i = Result.f57558a1;
            b81.m210592e3(C1351vv.f60710b1, interfaceC0876mvM213575c2);
        } catch (Throwable th) {
            int i2 = Result.f57558a1;
            abstractC0482f42.resumeWith(kg1.m213507a7(th));
            throw th;
        }
    }
}
