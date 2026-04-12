package p000;

import android.app.Notification;
import android.content.Context;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ik0 {
    /* renamed from: a0 */
    public static Notification.Builder m213167a0(Context context, String str) {
        return new Notification.Builder(context, str);
    }

    /* renamed from: a1 */
    public static Notification.Builder m213168a1(Notification.Builder builder, int i) {
        return builder.setBadgeIconType(i);
    }

    /* renamed from: a2 */
    public static Notification.Builder m213169a2(Notification.Builder builder, boolean z) {
        return builder.setColorized(z);
    }

    /* renamed from: a3 */
    public static Notification.Builder m213170a3(Notification.Builder builder, int i) {
        return builder.setGroupAlertBehavior(i);
    }

    /* renamed from: a4 */
    public static Notification.Builder m213171a4(Notification.Builder builder, CharSequence charSequence) {
        return builder.setSettingsText(charSequence);
    }

    /* renamed from: a5 */
    public static Notification.Builder m213172a5(Notification.Builder builder, String str) {
        return builder.setShortcutId(str);
    }

    /* renamed from: a6 */
    public static Notification.Builder m213173a6(Notification.Builder builder, long j) {
        return builder.setTimeoutAfter(j);
    }
}
