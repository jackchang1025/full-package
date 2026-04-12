package p000;

import android.content.Intent;
import androidx.core.app.JobIntentService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class p70 implements q70 {

    /* renamed from: a0 */
    public final Intent f59161a0;

    /* renamed from: a1 */
    public final int f59162a1;

    /* renamed from: a2 */
    public final /* synthetic */ JobIntentService f59163a2;

    public p70(JobIntentService jobIntentService, Intent intent, int i) {
        this.f59163a2 = jobIntentService;
        this.f59161a0 = intent;
        this.f59162a1 = i;
    }

    @Override // p000.q70
    /* renamed from: a0 */
    public final void mo214241a0() {
        this.f59163a2.stopSelf(this.f59162a1);
    }

    @Override // p000.q70
    public final Intent getIntent() {
        return this.f59161a0;
    }
}
