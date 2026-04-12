package p000;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.PersistableBundle;
import androidx.work.BackoffPolicy;
import androidx.work.NetworkType;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo$State;
import androidx.work.impl.C0096a0;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkDatabase_Impl;
import androidx.work.impl.background.systemjob.SystemJobService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class z31 implements du0 {

    /* renamed from: a4 */
    public static final /* synthetic */ int f61437a4 = 0;

    /* renamed from: a0 */
    public final Context f61438a0;

    /* renamed from: a1 */
    public final JobScheduler f61439a1;

    /* renamed from: a2 */
    public final C0096a0 f61440a2;

    /* renamed from: a3 */
    public final y31 f61441a3;

    static {
        C1351vv.m214966b1("SystemJobScheduler");
    }

    public z31(Context context, C0096a0 c0096a0) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        y31 y31Var = new y31(context);
        this.f61438a0 = context;
        this.f61440a2 = c0096a0;
        this.f61439a1 = jobScheduler;
        this.f61441a3 = y31Var;
    }

    /* renamed from: a1 */
    public static void m215340a1(JobScheduler jobScheduler, int i) {
        try {
            jobScheduler.cancel(i);
        } catch (Throwable unused) {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            String.format(Locale.getDefault(), "Exception while trying to cancel job (%d)", Integer.valueOf(i));
            c1351vvM214963a5.getClass();
        }
    }

    /* renamed from: a3 */
    public static ArrayList m215341a3(Context context, JobScheduler jobScheduler) {
        List<JobInfo> allPendingJobs;
        try {
            allPendingJobs = jobScheduler.getAllPendingJobs();
        } catch (Throwable unused) {
            C1351vv.m214963a5().getClass();
            allPendingJobs = null;
        }
        if (allPendingJobs == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(allPendingJobs.size());
        ComponentName componentName = new ComponentName(context, (Class<?>) SystemJobService.class);
        for (JobInfo jobInfo : allPendingJobs) {
            if (componentName.equals(jobInfo.getService())) {
                arrayList.add(jobInfo);
            }
        }
        return arrayList;
    }

    /* renamed from: a4 */
    public static jg1 m215342a4(JobInfo jobInfo) {
        PersistableBundle extras = jobInfo.getExtras();
        if (extras == null) {
            return null;
        }
        try {
            if (!extras.containsKey("EXTRA_WORK_SPEC_ID")) {
                return null;
            }
            return new jg1(extras.getString("EXTRA_WORK_SPEC_ID"), extras.getInt("EXTRA_WORK_SPEC_GENERATION", 0));
        } catch (NullPointerException unused) {
            return null;
        }
    }

    @Override // p000.du0
    /* renamed from: a0 */
    public final void mo212640a0(String str) {
        ArrayList arrayList;
        Context context = this.f61438a0;
        JobScheduler jobScheduler = this.f61439a1;
        ArrayList arrayListM215341a3 = m215341a3(context, jobScheduler);
        int i = 0;
        if (arrayListM215341a3 == null) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList(2);
            int size = arrayListM215341a3.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayListM215341a3.get(i2);
                i2++;
                JobInfo jobInfo = (JobInfo) obj;
                jg1 jg1VarM215342a4 = m215342a4(jobInfo);
                if (jg1VarM215342a4 != null && str.equals(jg1VarM215342a4.f57334a0)) {
                    arrayList2.add(Integer.valueOf(jobInfo.getId()));
                }
            }
            arrayList = arrayList2;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj2 = arrayList.get(i);
            i++;
            m215340a1(jobScheduler, ((Integer) obj2).intValue());
        }
        x31 x31VarMo210462b6 = this.f61440a2.f45559a6.mo210462b6();
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) x31VarMo210462b6.f61012a0;
        workDatabase_Impl.m212857a1();
        w31 w31Var = (w31) x31VarMo210462b6.f61015a3;
        u00 u00VarM210428a0 = w31Var.m210428a0();
        if (str == null) {
            u00VarM210428a0.mo213343a9(1);
        } else {
            u00VarM210428a0.mo213341a6(1, str);
        }
        workDatabase_Impl.m212858a2();
        try {
            u00VarM210428a0.m214812a0();
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
            w31Var.m210431a3(u00VarM210428a0);
        }
    }

    @Override // p000.du0
    /* renamed from: a2 */
    public final void mo212641a2(wg1... wg1VarArr) {
        int iIntValue;
        C0096a0 c0096a0 = this.f61440a2;
        WorkDatabase workDatabase = c0096a0.f45559a6;
        final d50 d50Var = new d50(workDatabase, 0);
        for (wg1 wg1Var : wg1VarArr) {
            workDatabase.m212858a2();
            try {
                wg1 wg1VarM215185a8 = workDatabase.mo210465b9().m215185a8(wg1Var.f60912a0);
                if (wg1VarM215185a8 != null && wg1VarM215185a8.f60913a1 == WorkInfo$State.f45526a0) {
                    jg1 jg1VarM212483b3 = cq0.m212483b3(wg1Var);
                    v31 v31VarM215110a2 = workDatabase.mo210462b6().m215110a2(jg1VarM212483b3);
                    if (v31VarM215110a2 != null) {
                        iIntValue = v31VarM215110a2.f60573a2;
                    } else {
                        c0096a0.f45558a5.getClass();
                        final int i = c0096a0.f45558a5.f57707a0;
                        Object objM212862b1 = d50Var.f55563a0.m212862b1(new Callable() { // from class: c50
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                WorkDatabase workDatabase2 = d50Var.f55563a0;
                                Long lM212711b5 = workDatabase2.mo210461b5().m212711b5("next_job_scheduler_id");
                                int i2 = 0;
                                int iLongValue = lM212711b5 != null ? (int) lM212711b5.longValue() : 0;
                                workDatabase2.mo210461b5().m212712b6(new do0("next_job_scheduler_id", Long.valueOf(iLongValue == Integer.MAX_VALUE ? 0 : iLongValue + 1)));
                                if (iLongValue < 0 || iLongValue > i) {
                                    workDatabase2.mo210461b5().m212712b6(new do0("next_job_scheduler_id", Long.valueOf(1)));
                                } else {
                                    i2 = iLongValue;
                                }
                                return Integer.valueOf(i2);
                            }
                        });
                        t60.m214694b5(objM212862b1, "workDatabase.runInTransa…            id\n        })");
                        iIntValue = ((Number) objM212862b1).intValue();
                    }
                    if (v31VarM215110a2 == null) {
                        c0096a0.f45559a6.mo210462b6().m215111a3(new v31(jg1VarM212483b3.f57334a0, jg1VarM212483b3.f57335a1, iIntValue));
                    }
                    m215343a6(wg1Var, iIntValue);
                    workDatabase.m212863b2();
                } else {
                    C1351vv.m214963a5().getClass();
                    workDatabase.m212863b2();
                }
            } finally {
                workDatabase.m212860a9();
            }
        }
    }

    @Override // p000.du0
    /* renamed from: a5 */
    public final boolean mo212642a5() {
        return true;
    }

    /* renamed from: a6 */
    public final void m215343a6(wg1 wg1Var, int i) {
        int i2;
        JobScheduler jobScheduler = this.f61439a1;
        y31 y31Var = this.f61441a3;
        y31Var.getClass();
        C0836lv c0836lv = wg1Var.f60921a9;
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("EXTRA_WORK_SPEC_ID", wg1Var.f60912a0);
        persistableBundle.putInt("EXTRA_WORK_SPEC_GENERATION", wg1Var.f60931b9);
        persistableBundle.putBoolean("EXTRA_IS_PERIODIC", wg1Var.m215068a3());
        JobInfo.Builder builder = new JobInfo.Builder(i, y31Var.f61231a0);
        boolean z = c0836lv.f58194a1;
        Set<C0834lt> set = c0836lv.f58200a7;
        JobInfo.Builder requiresCharging = builder.setRequiresCharging(z);
        boolean z2 = c0836lv.f58195a2;
        JobInfo.Builder extras = requiresCharging.setRequiresDeviceIdle(z2).setExtras(persistableBundle);
        NetworkType networkType = c0836lv.f58193a0;
        int i3 = Build.VERSION.SDK_INT;
        if (i3 < 30 || networkType != NetworkType.f45521a5) {
            int iOrdinal = networkType.ordinal();
            if (iOrdinal == 0) {
                i2 = 0;
            } else if (iOrdinal != 1) {
                i2 = 2;
                if (iOrdinal != 2) {
                    i2 = 3;
                    if (iOrdinal != 3) {
                        i2 = 4;
                        if (iOrdinal != 4 || i3 < 26) {
                            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                            networkType.toString();
                            c1351vvM214963a5.getClass();
                            i2 = 1;
                        }
                    }
                }
            } else {
                i2 = 1;
            }
            extras.setRequiredNetworkType(i2);
        } else {
            extras.setRequiredNetwork(new NetworkRequest.Builder().addCapability(25).build());
        }
        if (!z2) {
            extras.setBackoffCriteria(wg1Var.f60924b2, wg1Var.f60923b1 == BackoffPolicy.f45496a1 ? 0 : 1);
        }
        long jMax = Math.max(wg1Var.m215066a0() - System.currentTimeMillis(), 0L);
        if (i3 <= 28 || jMax > 0) {
            extras.setMinimumLatency(jMax);
        } else if (!wg1Var.f60928b6) {
            extras.setImportantWhileForeground(true);
        }
        if (!set.isEmpty()) {
            for (C0834lt c0834lt : set) {
                extras.addTriggerContentUri(new JobInfo.TriggerContentUri(c0834lt.f58174a0, c0834lt.f58175a1 ? 1 : 0));
            }
            extras.setTriggerContentUpdateDelay(c0836lv.f58198a5);
            extras.setTriggerContentMaxDelay(c0836lv.f58199a6);
        }
        extras.setPersisted(false);
        int i4 = Build.VERSION.SDK_INT;
        if (i4 >= 26) {
            extras.setRequiresBatteryNotLow(c0836lv.f58196a3);
            extras.setRequiresStorageNotLow(c0836lv.f58197a4);
        }
        boolean z3 = wg1Var.f60922b0 > 0;
        boolean z4 = jMax > 0;
        if (i4 >= 31 && wg1Var.f60928b6 && !z3 && !z4) {
            extras.setExpedited(true);
        }
        JobInfo jobInfoBuild = extras.build();
        C1351vv.m214963a5().getClass();
        try {
            if (jobScheduler.schedule(jobInfoBuild) == 0) {
                C1351vv.m214963a5().getClass();
                if (wg1Var.f60928b6 && wg1Var.f60929b7 == OutOfQuotaPolicy.f45523a0) {
                    wg1Var.f60928b6 = false;
                    C1351vv.m214963a5().getClass();
                    m215343a6(wg1Var, i);
                }
            }
        } catch (IllegalStateException e) {
            ArrayList arrayListM215341a3 = m215341a3(this.f61438a0, jobScheduler);
            int size = arrayListM215341a3 != null ? arrayListM215341a3.size() : 0;
            Locale locale = Locale.getDefault();
            Integer numValueOf = Integer.valueOf(size);
            C0096a0 c0096a0 = this.f61440a2;
            String str = String.format(locale, "JobScheduler 100 job limit exceeded.  We count %d WorkManager jobs in JobScheduler; we have %d tracked jobs in our DB; our Configuration limit is %d.", numValueOf, Integer.valueOf(c0096a0.f45559a6.mo210465b9().m215182a5().size()), Integer.valueOf(c0096a0.f45558a5.f57708a1));
            C1351vv.m214963a5().getClass();
            IllegalStateException illegalStateException = new IllegalStateException(str, e);
            c0096a0.f45558a5.getClass();
            throw illegalStateException;
        } catch (Throwable unused) {
            C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
            wg1Var.toString();
            c1351vvM214963a52.getClass();
        }
    }
}
