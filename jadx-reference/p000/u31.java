package p000;

import android.app.ForegroundServiceStartNotAllowedException;
import android.app.Notification;
import android.app.Service;
import androidx.work.impl.foreground.SystemForegroundService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class u31 {
    /* renamed from: a0 */
    public static void m214815a0(Service service, int i, Notification notification, int i2) {
        try {
            service.startForeground(i, notification, i2);
        } catch (ForegroundServiceStartNotAllowedException unused) {
            C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
            int i3 = SystemForegroundService.f45579a5;
            c1351vvM214963a5.getClass();
        }
    }
}
