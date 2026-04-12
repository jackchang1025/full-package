package com.storm.safe.rock.service;

import java.util.concurrent.CancellationException;
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
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$startWebViewStatusCheckTask$1", m214403f = "dqtvuisjd.kt", m214404l = {1876, 1878, 1884}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$startWebViewStatusCheckTask$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52721a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52722a2;

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        dqtvuisjd$startWebViewStatusCheckTask$1 dqtvuisjd_startwebviewstatuschecktask_1 = new dqtvuisjd$startWebViewStatusCheckTask$1(2, interfaceC0876mv);
        dqtvuisjd_startwebviewstatuschecktask_1.f52722a2 = obj;
        return dqtvuisjd_startwebviewstatuschecktask_1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$startWebViewStatusCheckTask$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0093 -> B:18:0x0037). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52721a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52722a2;
        } else if (i == 1 || i == 2) {
            interfaceC0920no = (InterfaceC0920no) this.f52722a2;
            try {
                kg1.m213544f4(obj);
            } catch (CancellationException e) {
                throw e;
            } catch (Exception e2) {
                t60.m214705c6("dqtvuisjd", "❌ WebView状态检查任务失败", e2);
                this.f52722a2 = interfaceC0920no;
                this.f52721a1 = 3;
                if (b81.m210571b1(2000L, this) != coroutineSingletons) {
                }
            }
        } else {
            if (i != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            interfaceC0920no = (InterfaceC0920no) this.f52722a2;
            kg1.m213544f4(obj);
        }
        while (AbstractC1117qo.m214443d9(interfaceC0920no)) {
            if (dqtvuisjd.f52360m3) {
                long jCurrentTimeMillis = System.currentTimeMillis() - dqtvuisjd.f52358m1.getLastWebViewStatusTime();
                if (jCurrentTimeMillis > 500) {
                    dqtvuisjd.f52360m3 = false;
                    t60.m214714d6("dqtvuisjd", "📡 [定时检查] WebView状态过期(" + jCurrentTimeMillis + "ms)，已重置为关闭状态");
                }
                this.f52722a2 = interfaceC0920no;
                this.f52721a1 = 1;
                if (b81.m210571b1(200L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                this.f52722a2 = interfaceC0920no;
                this.f52721a1 = 2;
                if (b81.m210571b1(2000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        }
        return C1351vv.f60710b1;
    }
}
