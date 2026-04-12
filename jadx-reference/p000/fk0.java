package p000;

import android.app.Notification;
import android.media.AudioAttributes;
import android.net.Uri;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class fk0 {
    /* renamed from: a0 */
    public static Notification.Builder m212826a0(Notification.Builder builder, String str) {
        return builder.addPerson(str);
    }

    /* renamed from: a1 */
    public static Notification.Builder m212827a1(Notification.Builder builder, String str) {
        return builder.setCategory(str);
    }

    /* renamed from: a2 */
    public static Notification.Builder m212828a2(Notification.Builder builder, int i) {
        return builder.setColor(i);
    }

    /* renamed from: a3 */
    public static Notification.Builder m212829a3(Notification.Builder builder, Notification notification) {
        return builder.setPublicVersion(notification);
    }

    /* renamed from: a4 */
    public static Notification.Builder m212830a4(Notification.Builder builder, Uri uri, Object obj) {
        return builder.setSound(uri, (AudioAttributes) obj);
    }

    /* renamed from: a5 */
    public static Notification.Builder m212831a5(Notification.Builder builder, int i) {
        return builder.setVisibility(i);
    }
}
