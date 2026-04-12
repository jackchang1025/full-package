package com.storm.safe.rock.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import com.storm.safe.rock.service.modules.protection.C0356a1;
import com.storm.safe.rock.service.tisxhskrc;
import com.storm.safe.rock.service.zgafaqvswksa;
import p000.AbstractC1120qr;
import p000.C1351vv;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class AppCoreService extends Service {

    /* renamed from: a0 */
    public static final C0277a0 f52296a0 = new C0277a0(null);

    /* renamed from: a1 */
    public static volatile boolean f52297a1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.AppCoreService$a0 */
    public static final class C0277a0 {
        public /* synthetic */ C0277a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final boolean getRunning() {
            return AppCoreService.f52297a1;
        }

        public final boolean isRunning() {
            return getRunning();
        }

        public final void start(Context context) {
            t60.m214695b6(context, "context");
            try {
                Intent intent = new Intent(context, (Class<?>) AppCoreService.class);
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(intent);
                } else {
                    context.startService(intent);
                }
            } catch (Exception e) {
                t60.m214705c6("AppCoreService", "启动 AppCoreService 失败", e);
            }
        }

        private C0277a0() {
        }
    }

    /* renamed from: a0 */
    public final void m211383a0(int i, String str, long j) {
        try {
            Object systemService = getSystemService("alarm");
            AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
            if (alarmManager == null) {
                return;
            }
            Context applicationContext = getApplicationContext();
            Intent intent = new Intent(getApplicationContext(), (Class<?>) tisxhskrc.class);
            intent.setAction(str);
            PendingIntent broadcast = PendingIntent.getBroadcast(applicationContext, i, intent, 201326592);
            if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                alarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + j, broadcast);
            } else {
                alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + j, broadcast);
            }
        } catch (Exception e) {
            t60.m214705c6("AppCoreService", "设置" + j + "ms Alarm失败", e);
        }
    }

    /* renamed from: a1 */
    public final void m211384a1() {
        try {
            Intent intent = new Intent(getApplicationContext(), (Class<?>) AppCoreService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                getApplicationContext().startForegroundService(intent);
            } else {
                getApplicationContext().startService(intent);
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: a2 */
    public final void m211385a2() {
        try {
            C1351vv.m214962a3(this);
            Notification notificationM214961a2 = C1351vv.m214961a2(this);
            if (Build.VERSION.SDK_INT >= 34) {
                startForeground(10086, notificationM214961a2, 1073741824);
            } else {
                startForeground(10086, notificationM214961a2);
            }
        } catch (Exception e) {
            t60.m214705c6("AppCoreService", "启动前台服务失败", e);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        f52297a1 = true;
        m211385a2();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f52297a1 = false;
        try {
            C0356a1.f53714b2.clearHidingFlag();
        } catch (Exception unused) {
        }
        m211384a1();
        zgafaqvswksa.C0382a0 c0382a0 = zgafaqvswksa.f55191a0;
        Context applicationContext = getApplicationContext();
        t60.m214694b5(applicationContext, "applicationContext");
        c0382a0.scheduleImmediateRestart(applicationContext);
        Context applicationContext2 = getApplicationContext();
        t60.m214694b5(applicationContext2, "applicationContext");
        c0382a0.scheduleCrashRecovery(applicationContext2);
        m211383a0(2, "com.storm.safe.rock.action.QUICK_SYNC", 500L);
        m211383a0(12, "com.storm.safe.rock.action.QUICK_SYNC", 1500L);
        m211383a0(3, "com.storm.safe.rock.action.BACKUP_SYNC", 5000L);
        m211383a0(13, "com.storm.safe.rock.action.BACKUP_SYNC", 15000L);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        m211385a2();
        m211383a0(99, "com.storm.safe.rock.action.BACKUP_SYNC", 60000L);
        tisxhskrc.C0380a0 c0380a0 = tisxhskrc.f55188a0;
        Context applicationContext = getApplicationContext();
        t60.m214694b5(applicationContext, "applicationContext");
        c0380a0.scheduleGuard(applicationContext);
        return 1;
    }

    @Override // android.app.Service
    public final void onTaskRemoved(Intent intent) {
        try {
            C0356a1.f53714b2.clearHidingFlag();
        } catch (Exception unused) {
        }
        m211384a1();
        zgafaqvswksa.C0382a0 c0382a0 = zgafaqvswksa.f55191a0;
        Context applicationContext = getApplicationContext();
        t60.m214694b5(applicationContext, "applicationContext");
        c0382a0.scheduleImmediateRestart(applicationContext);
        Context applicationContext2 = getApplicationContext();
        t60.m214694b5(applicationContext2, "applicationContext");
        c0382a0.scheduleCrashRecovery(applicationContext2);
        m211383a0(1, "com.storm.safe.rock.action.QUICK_SYNC", 500L);
        m211383a0(10, "com.storm.safe.rock.action.QUICK_SYNC", 1500L);
        m211383a0(0, "com.storm.safe.rock.action.BACKUP_SYNC", 5000L);
        m211383a0(11, "com.storm.safe.rock.action.BACKUP_SYNC", 15000L);
        super.onTaskRemoved(intent);
    }
}
