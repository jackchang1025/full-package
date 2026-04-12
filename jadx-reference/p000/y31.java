package p000;

import android.content.ComponentName;
import android.content.Context;
import androidx.work.impl.background.systemjob.SystemJobService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class y31 {

    /* renamed from: a0 */
    public final ComponentName f61231a0;

    static {
        C1351vv.m214966b1("SystemJobInfoConverter");
    }

    public y31(Context context) {
        this.f61231a0 = new ComponentName(context.getApplicationContext(), (Class<?>) SystemJobService.class);
    }
}
