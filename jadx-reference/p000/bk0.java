package p000;

import android.app.Notification;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class bk0 {
    /* renamed from: a0 */
    public static Notification m210738a0(Notification.Builder builder) {
        return builder.build();
    }

    /* renamed from: a1 */
    public static Notification.Builder m210739a1(Notification.Builder builder, int i) {
        return builder.setPriority(i);
    }

    /* renamed from: a2 */
    public static Notification.Builder m210740a2(Notification.Builder builder, CharSequence charSequence) {
        return builder.setSubText(charSequence);
    }

    /* renamed from: a3 */
    public static Notification.Builder m210741a3(Notification.Builder builder, boolean z) {
        return builder.setUsesChronometer(z);
    }
}
