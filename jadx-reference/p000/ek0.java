package p000;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.os.Bundle;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ek0 {
    /* renamed from: a0 */
    public static Notification.Builder m212685a0(Notification.Builder builder, Notification.Action action) {
        return builder.addAction(action);
    }

    /* renamed from: a1 */
    public static Notification.Action.Builder m212686a1(Notification.Action.Builder builder, Bundle bundle) {
        return builder.addExtras(bundle);
    }

    /* renamed from: a2 */
    public static Notification.Action.Builder m212687a2(Notification.Action.Builder builder, RemoteInput remoteInput) {
        return builder.addRemoteInput(remoteInput);
    }

    /* renamed from: a3 */
    public static Notification.Action m212688a3(Notification.Action.Builder builder) {
        return builder.build();
    }

    /* renamed from: a4 */
    public static Notification.Action.Builder m212689a4(int i, CharSequence charSequence, PendingIntent pendingIntent) {
        return new Notification.Action.Builder(i, charSequence, pendingIntent);
    }

    /* renamed from: a5 */
    public static String m212690a5(Notification notification) {
        return notification.getGroup();
    }

    /* renamed from: a6 */
    public static Notification.Builder m212691a6(Notification.Builder builder, String str) {
        return builder.setGroup(str);
    }

    /* renamed from: a7 */
    public static Notification.Builder m212692a7(Notification.Builder builder, boolean z) {
        return builder.setGroupSummary(z);
    }

    /* renamed from: a8 */
    public static Notification.Builder m212693a8(Notification.Builder builder, boolean z) {
        return builder.setLocalOnly(z);
    }

    /* renamed from: a9 */
    public static Notification.Builder m212694a9(Notification.Builder builder, String str) {
        return builder.setSortKey(str);
    }
}
