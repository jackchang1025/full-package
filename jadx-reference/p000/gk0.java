package p000;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.drawable.Icon;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class gk0 {
    /* renamed from: a0 */
    public static Notification.Action.Builder m212964a0(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
        return new Notification.Action.Builder(icon, charSequence, pendingIntent);
    }

    /* renamed from: a1 */
    public static Notification.Builder m212965a1(Notification.Builder builder, Object obj) {
        return builder.setSmallIcon((Icon) obj);
    }
}
