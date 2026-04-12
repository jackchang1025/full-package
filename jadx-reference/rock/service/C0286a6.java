package com.storm.safe.rock.service;

import com.storm.safe.rock.manager.SmartMediaProjectionManager$LossReason;
import kotlinx.coroutines.AbstractC0780a0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.a6 */
/* loaded from: classes2.dex */
public final class C0286a6 {

    /* renamed from: a0 */
    public final /* synthetic */ dqtvuisjd f52349a0;

    public C0286a6(dqtvuisjd dqtvuisjdVar) {
        this.f52349a0 = dqtvuisjdVar;
    }

    /* renamed from: a0 */
    public final void m211394a0(SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason) {
        t60.m214726f4("dqtvuisjd", "⚠️ 智能管理器检测到权限丢失: " + smartMediaProjectionManager$LossReason);
        dqtvuisjd dqtvuisjdVar = this.f52349a0;
        AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleSmartPermissionLoss$1(smartMediaProjectionManager$LossReason, dqtvuisjdVar, null), 3);
    }

    /* renamed from: a1 */
    public final void m211395a1() {
        t60.m214714d6("dqtvuisjd", "✅ 智能管理器权限已恢复");
        dqtvuisjd dqtvuisjdVar = this.f52349a0;
        AbstractC0780a0.m213692a3(dqtvuisjdVar.f52378a9, null, new dqtvuisjd$handleSmartPermissionRecovery$1(dqtvuisjdVar, null), 3);
    }
}
