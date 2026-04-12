package p000;

import android.app.job.JobParameters;
import android.app.job.JobWorkItem;
import android.os.AsyncTask;
import androidx.core.app.JobIntentService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class n70 extends AsyncTask {

    /* renamed from: a0 */
    public final /* synthetic */ JobIntentService f58462a0;

    public n70(JobIntentService jobIntentService) {
        this.f58462a0 = jobIntentService;
    }

    @Override // android.os.AsyncTask
    public final Object doInBackground(Object[] objArr) {
        q70 s70Var;
        while (true) {
            JobIntentService jobIntentService = this.f58462a0;
            t70 t70Var = jobIntentService.f44830a0;
            if (t70Var != null) {
                synchronized (t70Var.f60184a1) {
                    try {
                        JobParameters jobParameters = t70Var.f60185a2;
                        if (jobParameters != null) {
                            JobWorkItem jobWorkItemDequeueWork = jobParameters.dequeueWork();
                            if (jobWorkItemDequeueWork != null) {
                                jobWorkItemDequeueWork.getIntent().setExtrasClassLoader(t70Var.f60183a0.getClassLoader());
                                s70Var = new s70(t70Var, jobWorkItemDequeueWork);
                            }
                        }
                    } finally {
                    }
                }
            } else {
                synchronized (jobIntentService.f44834a4) {
                    try {
                        s70Var = jobIntentService.f44834a4.size() > 0 ? (q70) jobIntentService.f44834a4.remove(0) : null;
                    } finally {
                    }
                }
            }
            if (s70Var == null) {
                return null;
            }
            JobIntentService jobIntentService2 = this.f58462a0;
            s70Var.getIntent();
            jobIntentService2.m210076a1();
            s70Var.mo214241a0();
        }
    }

    @Override // android.os.AsyncTask
    public final void onCancelled(Object obj) {
        this.f58462a0.m210077a2();
    }

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        this.f58462a0.m210077a2();
    }
}
