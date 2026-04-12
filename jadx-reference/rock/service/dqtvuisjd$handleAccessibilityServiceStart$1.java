package com.storm.safe.rock.service;

import com.storm.safe.rock.service.dqtvuisjd;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$handleAccessibilityServiceStart$1", m214403f = "dqtvuisjd.kt", m214404l = {5463}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$handleAccessibilityServiceStart$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52544a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52545a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$handleAccessibilityServiceStart$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52545a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$handleAccessibilityServiceStart$1(this.f52545a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$handleAccessibilityServiceStart$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52544a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                t60.m214714d6("dqtvuisjd", "🔄 重新初始化无障碍服务");
                dqtvuisjd dqtvuisjdVar = this.f52545a2;
                this.f52544a1 = 1;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                if (dqtvuisjdVar.m211479h3(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
            t60.m214714d6("dqtvuisjd", "✅ 无障碍服务重新初始化完成");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 无障碍服务重新初始化失败", e);
        }
        return C1351vv.f60710b1;
    }
}
