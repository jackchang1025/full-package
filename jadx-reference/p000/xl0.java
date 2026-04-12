package p000;

import android.content.ComponentName;
import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class xl0 {
    static {
        C1351vv.m214966b1("PackageManagerHelper");
    }

    /* renamed from: a0 */
    public static void m215196a0(Context context, Class cls, boolean z) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, cls.getName()), z ? 1 : 2, 1);
            C1351vv.m214963a5().getClass();
        } catch (Exception unused) {
            C1351vv.m214963a5().getClass();
        }
    }
}
