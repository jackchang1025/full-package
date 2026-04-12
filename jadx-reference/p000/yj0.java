package p000;

import android.app.Notification;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class yj0 {
    /* renamed from: a0 */
    public static Notification.BigTextStyle m215292a0(Notification.BigTextStyle bigTextStyle, CharSequence charSequence) {
        return bigTextStyle.bigText(charSequence);
    }

    /* renamed from: a1 */
    public static Notification.BigTextStyle m215293a1(Notification.Builder builder) {
        return new Notification.BigTextStyle(builder);
    }

    /* renamed from: a2 */
    public static Notification.BigTextStyle m215294a2(Notification.BigTextStyle bigTextStyle, CharSequence charSequence) {
        return bigTextStyle.setBigContentTitle(charSequence);
    }

    /* renamed from: a3 */
    public static Notification.BigTextStyle m215295a3(Notification.BigTextStyle bigTextStyle, CharSequence charSequence) {
        return bigTextStyle.setSummaryText(charSequence);
    }
}
