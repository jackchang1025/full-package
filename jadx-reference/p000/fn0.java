package p000;

import android.content.Context;
import android.os.Build;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0778a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class fn0 {

    /* renamed from: a2 */
    public static final en0 f56299a2 = new en0(null);

    /* renamed from: a3 */
    public static volatile fn0 f56300a3;

    /* renamed from: a0 */
    public final AtomicBoolean f56301a0 = new AtomicBoolean(false);

    /* renamed from: a1 */
    public final C0873ms f56302a1;

    public fn0(Context context) {
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        y21 y21Var = new y21();
        c1180rh.getClass();
        this.f56302a1 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c1180rh, y21Var));
    }

    /* renamed from: a0 */
    public final void m212841a0() {
        fn0 fn0Var = this;
        AtomicBoolean atomicBoolean = fn0Var.f56301a0;
        if (atomicBoolean.get()) {
            atomicBoolean.set(false);
            Pair pair = new Pair("isMonitoring", Boolean.valueOf(atomicBoolean.get()));
            Pair pair2 = new Pair("totalChecks", 0);
            Pair pair3 = new Pair("healthyChecks", 0);
            Pair pair4 = new Pair("healthRate", 100);
            Pair pair5 = new Pair("permissionLossEvents", 0);
            Pair pair6 = new Pair("successfulRecoveries", 0);
            Pair pair7 = new Pair("failedRecoveries", 0);
            Pair pair8 = new Pair("recoveryRate", 100);
            int i = Build.VERSION.SDK_INT;
            Map mapM213614f9 = AbstractC0770a1.m213614f9(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, new Pair("androidVersion", Integer.valueOf(i)), new Pair("checkInterval", Long.valueOf(i >= 35 ? 15000L : 30000L)));
            Object obj = mapM213614f9.get("isMonitoring");
            t60.m214693b4(obj, "null cannot be cast to non-null type kotlin.Boolean");
            String str = ((Boolean) obj).booleanValue() ? "运行中" : "已停止";
            Object obj2 = mapM213614f9.get("totalChecks");
            Object obj3 = mapM213614f9.get("healthyChecks");
            Object obj4 = mapM213614f9.get("healthRate");
            Object obj5 = mapM213614f9.get("permissionLossEvents");
            Object obj6 = mapM213614f9.get("successfulRecoveries");
            Object obj7 = mapM213614f9.get("failedRecoveries");
            Object obj8 = mapM213614f9.get("recoveryRate");
            Object obj9 = mapM213614f9.get("androidVersion");
            Object obj10 = mapM213614f9.get("checkInterval");
            t60.m214693b4(obj10, "null cannot be cast to non-null type kotlin.Long");
            t60.m214714d6("PermissionHealthMonitor", AbstractC0778a0.m213649a1("\n            📊 权限健康监控统计报告:\n            ========================================\n            • 监控状态: " + str + "\n            • 总检查次数: " + obj2 + "\n            • 健康检查次数: " + obj3 + "\n            • 健康率: " + obj4 + "%\n            • 权限丢失事件: " + obj5 + "\n            • 成功恢复次数: " + obj6 + "\n            • 失败恢复次数: " + obj7 + "\n            • 恢复成功率: " + obj8 + "%\n            • Android版本: " + obj9 + "\n            • 检查间隔: " + (((Long) obj10).longValue() / 1000) + "秒\n            ========================================\n        "));
            fn0Var = this;
        }
        AbstractC1117qo.m214410a3(fn0Var.f56302a1);
        f56300a3 = null;
    }
}
