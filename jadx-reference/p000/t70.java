package p000;

import android.app.job.JobParameters;
import android.app.job.JobServiceEngine;
import androidx.core.app.JobIntentService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class t70 extends JobServiceEngine {

    /* renamed from: a0 */
    public final JobIntentService f60183a0;

    /* renamed from: a1 */
    public final Object f60184a1;

    /* renamed from: a2 */
    public JobParameters f60185a2;

    public t70(JobIntentService jobIntentService) {
        super(jobIntentService);
        this.f60184a1 = new Object();
        this.f60183a0 = jobIntentService;
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        this.f60185a2 = jobParameters;
        this.f60183a0.m210075a0(false);
        return true;
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        n70 n70Var = this.f60183a0.f44832a2;
        if (n70Var != null) {
            n70Var.cancel(false);
        }
        synchronized (this.f60184a1) {
            this.f60185a2 = null;
        }
        return true;
    }
}
