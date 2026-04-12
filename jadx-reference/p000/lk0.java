package p000;

import android.app.Notification;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class lk0 {
    /* renamed from: a0 */
    public static Notification.Action.Builder m213854a0(Notification.Action.Builder builder, boolean z) {
        return builder.setAuthenticationRequired(z);
    }

    /* renamed from: a1 */
    public static Notification.Builder m213855a1(Notification.Builder builder, int i) {
        return builder.setForegroundServiceBehavior(i);
    }
}
