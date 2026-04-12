package com.storm.safe.rock.service;

import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$continueServiceInitialization$3", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$continueServiceInitialization$3 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52502a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$continueServiceInitialization$3(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52502a1 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$continueServiceInitialization$3(this.f52502a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$continueServiceInitialization$3 dqtvuisjd_continueserviceinitialization_3 = (dqtvuisjd$continueServiceInitialization$3) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_continueserviceinitialization_3.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8;
        dqtvuisjd dqtvuisjdVar = this.f52502a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            if (!dqtvuisjdVar.m211487i1() && (c0323a8 = dqtvuisjdVar.f52415e6) != null) {
                c0323a8.m211643a8();
                c0323a8.m211669d6();
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ [后台] 网络连接失败，启动重连", e);
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            C0323a8 c0323a82 = dqtvuisjdVar.f52415e6;
            if (c0323a82 != null) {
                c0323a82.m211643a8();
                t60.m214714d6("dqtvuisjd", "后台重连：已委托 NetworkManager 处理");
            } else {
                t60.m214726f4("dqtvuisjd", "后台重连：NetworkManager 未初始化，忽略");
            }
        }
        return C1351vv.f60710b1;
    }
}
