package p000;

import android.app.AppOpsManager;
import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: aq */
/* loaded from: classes.dex */
public abstract class AbstractC0105aq {
    /* renamed from: a0 */
    public static int m210493a0(AppOpsManager appOpsManager, String str, int i, String str2) {
        if (appOpsManager == null) {
            return 1;
        }
        return appOpsManager.checkOpNoThrow(str, i, str2);
    }

    /* renamed from: a1 */
    public static String m210494a1(Context context) {
        return context.getOpPackageName();
    }

    /* renamed from: a2 */
    public static AppOpsManager m210495a2(Context context) {
        return (AppOpsManager) context.getSystemService(AppOpsManager.class);
    }
}
