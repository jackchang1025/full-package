package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$attemptAutoClickSafe$2", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {618, 619}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$attemptAutoClickSafe$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52901a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0327b2 f52902a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$attemptAutoClickSafe$2(C0327b2 c0327b2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52902a2 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new WriteSettingsPermissionManager$attemptAutoClickSafe$2(this.f52902a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((WriteSettingsPermissionManager$attemptAutoClickSafe$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0042, code lost:
    
        if (com.storm.safe.rock.service.modules.C0327b2.m211693a0(r6, r5) == r0) goto L19;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52901a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52901a1 = 1;
            if (b81.m210571b1(1500L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        kg1.m213544f4(obj);
        if (this.f52902a2.f53169a3 && AbstractC1117qo.m214443d9(this.f52902a2.f53168a2)) {
            C0327b2 c0327b2 = this.f52902a2;
            this.f52901a1 = 2;
        }
        return C1351vv.f60710b1;
    }
}
