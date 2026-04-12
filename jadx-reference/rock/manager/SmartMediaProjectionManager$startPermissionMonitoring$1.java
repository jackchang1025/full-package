package com.storm.safe.rock.manager;

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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.SmartMediaProjectionManager$startPermissionMonitoring$1", m214403f = "SmartMediaProjectionManager.kt", m214404l = {414}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SmartMediaProjectionManager$startPermissionMonitoring$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52057a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52058a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0262a4 f52059a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMediaProjectionManager$startPermissionMonitoring$1(C0262a4 c0262a4, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52059a3 = c0262a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        SmartMediaProjectionManager$startPermissionMonitoring$1 smartMediaProjectionManager$startPermissionMonitoring$1 = new SmartMediaProjectionManager$startPermissionMonitoring$1(this.f52059a3, interfaceC0876mv);
        smartMediaProjectionManager$startPermissionMonitoring$1.f52058a2 = obj;
        return smartMediaProjectionManager$startPermissionMonitoring$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartMediaProjectionManager$startPermissionMonitoring$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0036 -> B:13:0x0023). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        InterfaceC0920no interfaceC0920no;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52057a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            interfaceC0920no = (InterfaceC0920no) this.f52058a2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            interfaceC0920no = (InterfaceC0920no) this.f52058a2;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e) {
                t60.m214705c6("SmartMediaProjection", "❌ 权限监控异常", e);
            }
        }
        while (AbstractC1117qo.m214443d9(interfaceC0920no)) {
            this.f52058a2 = interfaceC0920no;
            this.f52057a1 = 1;
            if (b81.m210571b1(10000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return C1351vv.f60710b1;
    }
}
