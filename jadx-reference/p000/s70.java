package p000;

import android.app.job.JobParameters;
import android.app.job.JobWorkItem;
import android.content.Intent;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class s70 implements q70 {

    /* renamed from: a0 */
    public final JobWorkItem f59894a0;

    /* renamed from: a1 */
    public final /* synthetic */ t70 f59895a1;

    public s70(t70 t70Var, JobWorkItem jobWorkItem) {
        this.f59895a1 = t70Var;
        this.f59894a0 = jobWorkItem;
    }

    @Override // p000.q70
    /* renamed from: a0 */
    public final void mo214241a0() {
        synchronized (this.f59895a1.f60184a1) {
            try {
                JobParameters jobParameters = this.f59895a1.f60185a2;
                if (jobParameters != null) {
                    jobParameters.completeWork(this.f59894a0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.q70
    public final Intent getIntent() {
        return this.f59894a0.getIntent();
    }
}
