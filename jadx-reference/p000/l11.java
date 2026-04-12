package p000;

import android.app.Activity;
import android.os.Build;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class l11 {

    /* renamed from: a1 */
    public static final j11 f57819a1 = new j11(null);

    /* renamed from: a0 */
    public final jl0 f57820a0;

    public l11(Activity activity) {
        this.f57820a0 = Build.VERSION.SDK_INT >= 31 ? new k11(activity) : new jl0(activity);
    }
}
