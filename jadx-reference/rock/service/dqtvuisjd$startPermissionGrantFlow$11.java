package com.storm.safe.rock.service;

import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$startPermissionGrantFlow$11", m214403f = "dqtvuisjd.kt", m214404l = {3294}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$startPermissionGrantFlow$11 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52719a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52720a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$startPermissionGrantFlow$11(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52720a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$startPermissionGrantFlow$11(this.f52720a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$startPermissionGrantFlow$11) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52719a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52719a1 = 1;
            if (b81.m210571b1(10000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
        dqtvuisjd dqtvuisjdVar = this.f52720a2;
        if (!dqtvuisjdVar.m211484h8()) {
            t60.m214726f4("dqtvuisjd", "⚠️ 10秒内未完成注册，唤醒NetworkManager重试");
            C0323a8 c0323a8 = dqtvuisjdVar.f52415e6;
            if (c0323a8 != null) {
                c0323a8.m211643a8();
                c0323a8.m211669d6();
            }
        }
        return C1351vv.f60710b1;
    }
}
