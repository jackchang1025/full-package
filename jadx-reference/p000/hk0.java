package p000;

import android.app.Notification;
import android.widget.RemoteViews;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class hk0 {
    /* renamed from: a0 */
    public static Notification.Action.Builder m213049a0(Notification.Action.Builder builder, boolean z) {
        return builder.setAllowGeneratedReplies(z);
    }

    /* renamed from: a1 */
    public static Notification.Builder m213050a1(Notification.Builder builder, RemoteViews remoteViews) {
        return builder.setCustomBigContentView(remoteViews);
    }

    /* renamed from: a2 */
    public static Notification.Builder m213051a2(Notification.Builder builder, RemoteViews remoteViews) {
        return builder.setCustomContentView(remoteViews);
    }

    /* renamed from: a3 */
    public static Notification.Builder m213052a3(Notification.Builder builder, RemoteViews remoteViews) {
        return builder.setCustomHeadsUpContentView(remoteViews);
    }

    /* renamed from: a4 */
    public static Notification.Builder m213053a4(Notification.Builder builder, CharSequence[] charSequenceArr) {
        return builder.setRemoteInputHistory(charSequenceArr);
    }
}
