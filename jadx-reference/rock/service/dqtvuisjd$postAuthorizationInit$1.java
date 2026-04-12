package com.storm.safe.rock.service;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$postAuthorizationInit$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$postAuthorizationInit$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52662a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$postAuthorizationInit$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52662a1 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$postAuthorizationInit$1(this.f52662a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$postAuthorizationInit$1 dqtvuisjd_postauthorizationinit_1 = (dqtvuisjd$postAuthorizationInit$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_postauthorizationinit_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        dqtvuisjd dqtvuisjdVar = this.f52662a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        t60.m214714d6("dqtvuisjd", "🔧 [授权后初始化] 开始注册延迟组件...");
        try {
            dqtvuisjd.m211420b9(dqtvuisjdVar);
        } catch (Exception unused) {
        }
        try {
            dqtvuisjd.m211421c0(dqtvuisjdVar);
        } catch (Exception unused2) {
        }
        try {
            dqtvuisjdVar.m211506k2();
        } catch (Exception unused3) {
        }
        try {
            dqtvuisjd.m211418b7(dqtvuisjdVar);
        } catch (Exception unused4) {
        }
        t60.m214714d6("dqtvuisjd", "✅ [授权后初始化] 延迟组件注册完成");
        return C1351vv.f60710b1;
    }
}
