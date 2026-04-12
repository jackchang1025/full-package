package p000;

import android.app.Notification;
import android.app.Service;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class t31 {
    /* renamed from: a0 */
    public static void m214685a0(Service service, int i, Notification notification, int i2) {
        service.startForeground(i, notification, i2);
    }
}
