package p000;

import android.app.Activity;
import android.content.Intent;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ai0 {
    /* renamed from: a0 */
    public static Intent m209801a0(Activity activity) {
        return activity.getParentActivityIntent();
    }

    /* renamed from: a1 */
    public static boolean m209802a1(Activity activity, Intent intent) {
        return activity.navigateUpTo(intent);
    }

    /* renamed from: a2 */
    public static boolean m209803a2(Activity activity, Intent intent) {
        return activity.shouldUpRecreateTask(intent);
    }
}
