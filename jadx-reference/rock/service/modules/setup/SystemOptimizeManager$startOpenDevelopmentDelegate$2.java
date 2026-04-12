package com.storm.safe.rock.service.modules.setup;

import android.accessibilityservice.AccessibilityService;
import com.storm.safe.rock.service.dqtvuisjd;
import kotlin.jvm.internal.Lambda;
import p000.C0763km;
import p000.C1351vv;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class SystemOptimizeManager$startOpenDevelopmentDelegate$2 extends Lambda implements h10 {

    /* renamed from: a0 */
    public final /* synthetic */ C0360a2 f53785a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemOptimizeManager$startOpenDevelopmentDelegate$2(C0360a2 c0360a2) {
        super(1);
        this.f53785a0 = c0360a2;
    }

    @Override // p000.h10
    public final Object invoke(Object obj) {
        C0763km c0763kmM211469g3;
        String str = (String) obj;
        t60.m214695b6(str, "reason");
        t60.m214714d6("SystemOptimize", "OpenDevelopmentDelegate 回调 onFailed: ".concat(str));
        this.f53785a0.f53820a5.set(SystemOptimizeManager$DevOptState.ENABLE_DEV_OPT_FAIL);
        C0360a2 c0360a2 = this.f53785a0;
        t60.m214704c5("SystemOptimize", "系统优化流程失败: ".concat(str));
        try {
            AccessibilityService accessibilityService = c0360a2.f53815a0;
            dqtvuisjd dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
            if (dqtvuisjdVar != null && (c0763kmM211469g3 = dqtvuisjdVar.m211469g3()) != null) {
                c0763kmM211469g3.m213600a0();
            }
            t60.m214714d6("SystemOptimize", "适配流程失败，已隐藏无障碍遮盖");
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "隐藏无障碍遮盖失败", e);
        }
        c0360a2.m212043d0();
        h10 h10Var = c0360a2.f53830b5;
        if (h10Var != null) {
            h10Var.invoke(str);
        }
        return C1351vv.f60710b1;
    }
}
