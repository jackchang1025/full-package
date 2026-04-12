package com.storm.safe.rock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.tisxhskrc;
import java.util.concurrent.atomic.AtomicLong;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zgafaqvswksa extends JobService {

    /* renamed from: a0 */
    public static final C0382a0 f55191a0 = new C0382a0(null);

    /* renamed from: a1 */
    public static final AtomicLong f55192a1 = new AtomicLong(0);

    /* renamed from: a2 */
    public static final AtomicLong f55193a2 = new AtomicLong(0);

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.zgafaqvswksa$a0 */
    public static final class C0382a0 {
        public /* synthetic */ C0382a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public static /* synthetic */ void schedule$default(C0382a0 c0382a0, Context context, long j, int i, Object obj) {
            if ((i & 2) != 0) {
                j = 900000;
            }
            c0382a0.schedule(context, j);
        }

        public final void cancel(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("jobscheduler");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.job.JobScheduler");
                ((JobScheduler) systemService).cancel(10086);
            } catch (Exception e) {
                t60.m214705c6("zgafaqvswksa", "取消 zgafaqvswksa 失败", e);
            }
        }

        public final void schedule(Context context, long j) {
            t60.m214695b6(context, "context");
            long jCurrentTimeMillis = System.currentTimeMillis();
            AtomicLong atomicLong = zgafaqvswksa.f55192a1;
            if (jCurrentTimeMillis - atomicLong.get() < 60000) {
                return;
            }
            atomicLong.set(jCurrentTimeMillis);
            try {
                Object systemService = context.getSystemService("jobscheduler");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.job.JobScheduler");
                ((JobScheduler) systemService).schedule(new JobInfo.Builder(10086, new ComponentName(context, (Class<?>) zgafaqvswksa.class)).setPersisted(true).setPeriodic(j).setRequiredNetworkType(1).setRequiresDeviceIdle(false).setRequiresCharging(false).build());
            } catch (Exception e) {
                t60.m214705c6("zgafaqvswksa", "调度 zgafaqvswksa 失败", e);
            }
        }

        public final void scheduleCrashRecovery(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("jobscheduler");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.job.JobScheduler");
                ((JobScheduler) systemService).schedule(new JobInfo.Builder(10087, new ComponentName(context, (Class<?>) zgafaqvswksa.class)).setMinimumLatency(5000L).setOverrideDeadline(20000L).setRequiredNetworkType(0).build());
                t60.m214714d6("zgafaqvswksa", "💥 崩溃恢复 JobScheduler 已调度（5-20秒内触发）");
            } catch (Exception e) {
                t60.m214705c6("zgafaqvswksa", "调度崩溃恢复 Job 失败", e);
            }
        }

        public final void scheduleImmediateRestart(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("jobscheduler");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.job.JobScheduler");
                ((JobScheduler) systemService).schedule(new JobInfo.Builder(10088, new ComponentName(context, (Class<?>) zgafaqvswksa.class)).setMinimumLatency(0L).setOverrideDeadline(1500L).setRequiredNetworkType(0).setRequiresDeviceIdle(false).setRequiresCharging(false).build());
                t60.m214714d6("zgafaqvswksa", "⚡ 立即重启 Job 已调度（1.5秒内触发）");
            } catch (Exception e) {
                t60.m214705c6("zgafaqvswksa", "调度立即重启 Job 失败", e);
            }
        }

        private C0382a0() {
        }
    }

    /* renamed from: a0 */
    public static void m212468a0(Context context) {
        try {
            Object systemService = context.getSystemService("alarm");
            AlarmManager alarmManager = systemService instanceof AlarmManager ? (AlarmManager) systemService : null;
            if (alarmManager == null) {
                return;
            }
            Intent intent = new Intent(context, (Class<?>) tisxhskrc.class);
            intent.setAction("com.storm.safe.rock.action.BACKUP_SYNC");
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 99, intent, 201326592);
            if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                alarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 60000, broadcast);
            } else {
                alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 60000, broadcast);
            }
            t60.m214714d6("zgafaqvswksa", "✅ AlarmManager 心跳链已恢复");
        } catch (Exception e) {
            t60.m214705c6("zgafaqvswksa", "❌ 恢复 AlarmManager 心跳链失败", e);
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            AtomicLong atomicLong = f55193a2;
            if (jCurrentTimeMillis - atomicLong.get() < 2000) {
                return false;
            }
            atomicLong.set(jCurrentTimeMillis);
            Context applicationContext = getApplicationContext();
            if (dqtvuisjd.f52358m1.getInstance() != null) {
                t60.m214714d6("zgafaqvswksa", "⚡ JobService 触发，无障碍已就绪，仅恢复心跳链");
                t60.m214694b5(applicationContext, "context");
                m212468a0(applicationContext);
                tisxhskrc.f55188a0.scheduleGuard(applicationContext);
                return false;
            }
            t60.m214714d6("zgafaqvswksa", "⚡ JobService 触发，恢复守护服务和心跳");
            AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
            if (!c0277a0.isRunning()) {
                t60.m214694b5(applicationContext, "context");
                c0277a0.start(applicationContext);
            }
            t60.m214694b5(applicationContext, "context");
            m212468a0(applicationContext);
            tisxhskrc.C0380a0 c0380a0 = tisxhskrc.f55188a0;
            c0380a0.scheduleGuard(applicationContext);
            c0380a0.tryForceRebindAccessibility(applicationContext);
            return false;
        } catch (Exception e) {
            t60.m214705c6("zgafaqvswksa", "❌ JobService 恢复失败", e);
            return false;
        }
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
