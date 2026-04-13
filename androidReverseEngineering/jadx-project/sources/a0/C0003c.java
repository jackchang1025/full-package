package a0;

import android.app.job.JobScheduler;
import android.content.Context;

/* renamed from: a0.c */
/* loaded from: classes.dex */
public final class C0003c {

    /* renamed from: a */
    public final JobScheduler f5a;

    public C0003c(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        this.f5a = jobScheduler;
        jobScheduler.cancelAll();
    }
}
