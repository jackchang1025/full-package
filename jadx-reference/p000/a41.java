package p000;

import android.app.job.JobParameters;
import android.net.Uri;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class a41 {
    /* renamed from: a0 */
    public static String[] m58a0(JobParameters jobParameters) {
        return jobParameters.getTriggeredContentAuthorities();
    }

    /* renamed from: a1 */
    public static Uri[] m59a1(JobParameters jobParameters) {
        return jobParameters.getTriggeredContentUris();
    }
}
