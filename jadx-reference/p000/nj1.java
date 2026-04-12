package p000;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.impl.C0096a0;
import com.storm.safe.rock.service.modules.ScreenWakeWorker;
import com.storm.safe.rock.service.modules.zdcfpfxnz;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class nj1 {

    /* renamed from: a4 */
    public static final mj1 f58634a4 = new mj1(null);

    /* renamed from: a5 */
    public static volatile nj1 f58635a5;

    /* renamed from: a0 */
    public final Context f58636a0;

    /* renamed from: a1 */
    public boolean f58637a1;

    /* renamed from: a2 */
    public boolean f58638a2;

    /* renamed from: a3 */
    public long f58639a3 = 10;

    public nj1(Context context) {
        this.f58636a0 = context;
    }

    /* renamed from: a1 */
    public static void m214108a1(nj1 nj1Var) {
        nj1Var.getClass();
        nj1Var.f58639a3 = Math.max(10L, 1L);
        if (!nj1Var.f58637a1) {
            nj1Var.m214109a0((nj1Var.f58639a3 * 60000) + System.currentTimeMillis());
            nj1Var.f58637a1 = true;
        }
        long j = nj1Var.f58639a3;
        if (j >= 15) {
            NetworkType networkType = NetworkType.f45516a0;
            if (!nj1Var.f58638a2) {
                try {
                    long jMax = Math.max(j, 15L);
                    TimeUnit timeUnit = TimeUnit.MINUTES;
                    fl0 fl0Var = new fl0(ScreenWakeWorker.class, jMax);
                    ((wg1) fl0Var.f56867a2).f60921a9 = new C0836lv(networkType, false, false, false, false, -1L, -1L, AbstractC0715je.m213304j1(new LinkedHashSet()));
                    ((Set) fl0Var.f56868a3).add("screen_wake");
                    C0096a0.m210473g0(nj1Var.f58636a0).m210475f9("ScreenWakeWork", ExistingPeriodicWorkPolicy.f45508a1, (zm0) fl0Var.m213153a0());
                    nj1Var.f58638a2 = true;
                } catch (Exception e) {
                    t60.m214705c6("dhkgxdffvdxr", "❌ 启动 WorkManager 失败", e);
                }
            }
        }
        t60.m214714d6("dhkgxdffvdxr", "✅ 已启动，间隔 " + nj1Var.f58639a3 + " 分钟，时段 14:00-7:00");
    }

    /* renamed from: a0 */
    public final void m214109a0(long j) {
        Context context = this.f58636a0;
        try {
            Object systemService = context.getSystemService("alarm");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
            AlarmManager alarmManager = (AlarmManager) systemService;
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 20001, new Intent(context, (Class<?>) zdcfpfxnz.class), 201326592);
            t60.m214694b5(broadcast, "getBroadcast(\n          ….FLAG_IMMUTABLE\n        )");
            if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                alarmManager.setExactAndAllowWhileIdle(0, j, broadcast);
            } else {
                alarmManager.setAndAllowWhileIdle(0, j, broadcast);
            }
        } catch (Exception e) {
            t60.m214705c6("dhkgxdffvdxr", "❌ 设置 Alarm 失败", e);
        }
    }

    /* renamed from: a2 */
    public final void m214110a2() {
        boolean z = this.f58637a1;
        Context context = this.f58636a0;
        if (z) {
            try {
                Object systemService = context.getSystemService("alarm");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
                PendingIntent broadcast = PendingIntent.getBroadcast(context, 20001, new Intent(context, (Class<?>) zdcfpfxnz.class), 201326592);
                t60.m214694b5(broadcast, "getBroadcast(\n          ….FLAG_IMMUTABLE\n        )");
                ((AlarmManager) systemService).cancel(broadcast);
            } catch (Exception e) {
                t60.m214705c6("dhkgxdffvdxr", "❌ 取消 Alarm 失败", e);
            }
            this.f58637a1 = false;
        }
        if (this.f58638a2) {
            try {
                C0096a0 c0096a0M210473g0 = C0096a0.m210473g0(context);
                c0096a0M210473g0.f45560a7.m214272b6(new C0511fw(c0096a0M210473g0, "ScreenWakeWork", true));
            } catch (Exception e2) {
                t60.m214705c6("dhkgxdffvdxr", "❌ 停止 WorkManager 失败", e2);
            }
            this.f58638a2 = false;
        }
        t60.m214714d6("dhkgxdffvdxr", "⏹ 已停止");
    }
}
