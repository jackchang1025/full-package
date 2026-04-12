package p000;

import android.app.Activity;
import android.app.SharedElementCallback;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o8 */
/* loaded from: classes.dex */
public abstract class AbstractC0943o8 {
    /* renamed from: a0 */
    public static void m214161a0(Object obj) {
        ((SharedElementCallback.OnSharedElementsReadyListener) obj).onSharedElementsReady();
    }

    /* renamed from: a1 */
    public static void m214162a1(Activity activity, String[] strArr, int i) {
        activity.requestPermissions(strArr, i);
    }

    /* renamed from: a2 */
    public static boolean m214163a2(Activity activity, String str) {
        return activity.shouldShowRequestPermissionRationale(str);
    }
}
