package p000;

import android.app.Activity;
import android.app.Application;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ko0 {
    /* renamed from: a0 */
    public static final void m213606a0(Activity activity, Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        t60.m214695b6(activity, "activity");
        t60.m214695b6(activityLifecycleCallbacks, "callback");
        activity.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }
}
