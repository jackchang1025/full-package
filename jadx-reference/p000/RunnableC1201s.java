package p000;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.ApplicationExitInfo;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteAccessPermException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteTableLockedException;
import android.os.Build;
import android.text.TextUtils;
import androidx.work.WorkInfo$State;
import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabase_Impl;
import androidx.work.impl.utils.ForceStopRunnable$BroadcastReceiver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: s */
/* loaded from: classes2.dex */
public final class RunnableC1201s implements Runnable {

    /* renamed from: a4 */
    public static final long f59839a4;

    /* renamed from: a0 */
    public final Context f59840a0;

    /* renamed from: a1 */
    public final C0096a0 f59841a1;

    /* renamed from: a2 */
    public final d50 f59842a2;

    /* renamed from: a3 */
    public int f59843a3 = 0;

    static {
        C1351vv.m214966b1("ForceStopRunnable");
        f59839a4 = TimeUnit.DAYS.toMillis(3650L);
    }

    public RunnableC1201s(Context context, C0096a0 c0096a0) {
        this.f59840a0 = context.getApplicationContext();
        this.f59841a1 = c0096a0;
        this.f59842a2 = c0096a0.f45563b0;
    }

    /* renamed from: a1 */
    public static void m214552a1(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        int i = Build.VERSION.SDK_INT >= 31 ? 167772160 : 134217728;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context, (Class<?>) ForceStopRunnable$BroadcastReceiver.class));
        intent.setAction("ACTION_FORCE_STOP_RESCHEDULE");
        PendingIntent broadcast = PendingIntent.getBroadcast(context, -1, intent, i);
        long jCurrentTimeMillis = System.currentTimeMillis() + f59839a4;
        if (alarmManager != null) {
            alarmManager.setExact(0, jCurrentTimeMillis, broadcast);
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:135:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0202  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m214553a0() {
        boolean z;
        WorkDatabase workDatabase;
        int i;
        PendingIntent broadcast;
        d50 d50Var = this.f59842a2;
        int i2 = z31.f61437a4;
        Context context = this.f59840a0;
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        ArrayList arrayListM215341a3 = z31.m215341a3(context, jobScheduler);
        C0096a0 c0096a0 = this.f59841a1;
        x31 x31VarMo210462b6 = c0096a0.f45559a6.mo210462b6();
        x31VarMo210462b6.getClass();
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT DISTINCT work_spec_id FROM SystemIdInfo", 0);
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) x31VarMo210462b6.f61012a0;
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                arrayList.add(cursorM213580c7.isNull(0) ? null : cursorM213580c7.getString(0));
            }
            HashSet hashSet = new HashSet(arrayListM215341a3 != null ? arrayListM215341a3.size() : 0);
            if (arrayListM215341a3 != null && !arrayListM215341a3.isEmpty()) {
                int size = arrayListM215341a3.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayListM215341a3.get(i3);
                    i3++;
                    JobInfo jobInfo = (JobInfo) obj;
                    jg1 jg1VarM215342a4 = z31.m215342a4(jobInfo);
                    if (jg1VarM215342a4 != null) {
                        hashSet.add(jg1VarM215342a4.f57334a0);
                    } else {
                        z31.m215340a1(jobScheduler, jobInfo.getId());
                    }
                }
            }
            int size2 = arrayList.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size2) {
                    z = false;
                    break;
                }
                Object obj2 = arrayList.get(i4);
                i4++;
                if (!hashSet.contains((String) obj2)) {
                    C1351vv.m214963a5().getClass();
                    z = true;
                    break;
                }
            }
            if (z) {
                workDatabase = c0096a0.f45559a6;
                workDatabase.m212858a2();
                try {
                    xg1 xg1VarMo210465b9 = workDatabase.mo210465b9();
                    int size3 = arrayList.size();
                    int i5 = 0;
                    while (i5 < size3) {
                        Object obj3 = arrayList.get(i5);
                        i5++;
                        xg1VarMo210465b9.m215187b0((String) obj3, -1L);
                    }
                    workDatabase.m212863b2();
                    workDatabase.m212860a9();
                } catch (Throwable th) {
                    throw th;
                }
            }
            workDatabase = c0096a0.f45559a6;
            xg1 xg1VarMo210465b92 = workDatabase.mo210465b9();
            pg1 pg1VarMo210464b8 = workDatabase.mo210464b8();
            workDatabase.m212858a2();
            try {
                ArrayList arrayListM215181a4 = xg1VarMo210465b92.m215181a4();
                boolean zIsEmpty = arrayListM215181a4.isEmpty();
                if (!zIsEmpty) {
                    int size4 = arrayListM215181a4.size();
                    int i6 = 0;
                    while (i6 < size4) {
                        Object obj4 = arrayListM215181a4.get(i6);
                        i6++;
                        wg1 wg1Var = (wg1) obj4;
                        xg1VarMo210465b92.m215191b4(WorkInfo$State.f45526a0, wg1Var.f60912a0);
                        xg1VarMo210465b92.m215187b0(wg1Var.f60912a0, -1L);
                    }
                }
                WorkDatabase_Impl workDatabase_Impl2 = (WorkDatabase_Impl) pg1VarMo210464b8.f59229a1;
                workDatabase_Impl2.m212857a1();
                w31 w31Var = (w31) pg1VarMo210464b8.f59231a3;
                u00 u00VarM210428a0 = w31Var.m210428a0();
                workDatabase_Impl2.m212858a2();
                try {
                    u00VarM210428a0.m214812a0();
                    workDatabase_Impl2.m212863b2();
                    workDatabase_Impl2.m212860a9();
                    w31Var.m210431a3(u00VarM210428a0);
                    workDatabase.m212863b2();
                    workDatabase.m212860a9();
                    boolean z2 = !zIsEmpty || z;
                    Long lM212711b5 = c0096a0.f45563b0.f55563a0.mo210461b5().m212711b5("reschedule_needed");
                    if (lM212711b5 != null && lM212711b5.longValue() == 1) {
                        C1351vv.m214963a5().getClass();
                        c0096a0.m210477g3();
                        d50 d50Var2 = c0096a0.f45563b0;
                        d50Var2.getClass();
                        d50Var2.f55563a0.mo210461b5().m212712b6(new do0("reschedule_needed", 0L));
                        return;
                    }
                    try {
                        i = Build.VERSION.SDK_INT;
                        int i7 = i >= 31 ? 570425344 : 536870912;
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(context, (Class<?>) ForceStopRunnable$BroadcastReceiver.class));
                        intent.setAction("ACTION_FORCE_STOP_RESCHEDULE");
                        broadcast = PendingIntent.getBroadcast(context, -1, intent, i7);
                    } catch (IllegalArgumentException | SecurityException unused) {
                        C1351vv.m214963a5().getClass();
                    }
                    if (i >= 30) {
                        if (broadcast != null) {
                            broadcast.cancel();
                        }
                        List historicalProcessExitReasons = ((ActivityManager) context.getSystemService("activity")).getHistoricalProcessExitReasons(null, 0, 0);
                        if (historicalProcessExitReasons != null && !historicalProcessExitReasons.isEmpty()) {
                            Long lM212711b52 = d50Var.f55563a0.mo210461b5().m212711b5("last_force_stop_ms");
                            long jLongValue = lM212711b52 != null ? lM212711b52.longValue() : 0L;
                            for (int i8 = 0; i8 < historicalProcessExitReasons.size(); i8++) {
                                ApplicationExitInfo applicationExitInfoM213371a6 = AbstractC0740k0.m213371a6(historicalProcessExitReasons.get(i8));
                                if (applicationExitInfoM213371a6.getReason() == 10 && applicationExitInfoM213371a6.getTimestamp() >= jLongValue) {
                                    C1351vv.m214963a5().getClass();
                                    c0096a0.m210477g3();
                                    long jCurrentTimeMillis = System.currentTimeMillis();
                                    d50Var.getClass();
                                    d50Var.f55563a0.mo210461b5().m212712b6(new do0("last_force_stop_ms", Long.valueOf(jCurrentTimeMillis)));
                                    return;
                                }
                            }
                        }
                        if (z2) {
                            return;
                        }
                        C1351vv.m214963a5().getClass();
                        fu0.m212865a0(c0096a0.f45558a5, c0096a0.f45559a6, c0096a0.f45561a8);
                        return;
                    }
                    if (broadcast == null) {
                        m214552a1(context);
                        C1351vv.m214963a5().getClass();
                        c0096a0.m210477g3();
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        d50Var.getClass();
                        d50Var.f55563a0.mo210461b5().m212712b6(new do0("last_force_stop_ms", Long.valueOf(jCurrentTimeMillis2)));
                        return;
                    }
                    if (z2) {
                    }
                } catch (Throwable th2) {
                    workDatabase_Impl2.m212860a9();
                    w31Var.m210431a3(u00VarM210428a0);
                    throw th2;
                }
            } finally {
                workDatabase.m212860a9();
            }
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zM214465a0;
        C0096a0 c0096a0 = this.f59841a1;
        try {
            C0793kr c0793kr = c0096a0.f45558a5;
            c0793kr.getClass();
            boolean zIsEmpty = TextUtils.isEmpty(null);
            Context context = this.f59840a0;
            if (zIsEmpty) {
                C1351vv.m214963a5().getClass();
                zM214465a0 = true;
            } else {
                zM214465a0 = qo0.m214465a0(context, c0793kr);
                C1351vv.m214963a5().getClass();
            }
            if (!zM214465a0) {
                return;
            }
            while (true) {
                try {
                    t60.m214716d8(context);
                    C1351vv.m214963a5().getClass();
                    try {
                        m214553a0();
                        return;
                    } catch (SQLiteAccessPermException | SQLiteCantOpenDatabaseException | SQLiteConstraintException | SQLiteDatabaseCorruptException | SQLiteDatabaseLockedException | SQLiteDiskIOException | SQLiteTableLockedException e) {
                        int i = this.f59843a3 + 1;
                        this.f59843a3 = i;
                        if (i >= 3) {
                            C1351vv.m214963a5().getClass();
                            IllegalStateException illegalStateException = new IllegalStateException("The file system on the device is in a bad state. WorkManager cannot access the app's internal data store.", e);
                            c0096a0.f45558a5.getClass();
                            throw illegalStateException;
                        }
                        C1351vv.m214963a5().getClass();
                        try {
                            Thread.sleep(this.f59843a3 * 300);
                        } catch (InterruptedException unused) {
                        }
                    }
                } catch (SQLiteException e2) {
                    C1351vv.m214963a5().getClass();
                    IllegalStateException illegalStateException2 = new IllegalStateException("Unexpected SQLite exception during migrations", e2);
                    c0096a0.f45558a5.getClass();
                    throw illegalStateException2;
                }
            }
        } finally {
            c0096a0.m210476g2();
        }
    }
}
