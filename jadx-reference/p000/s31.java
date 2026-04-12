package p000;

import android.app.Notification;
import android.os.Build;
import androidx.work.impl.foreground.SystemForegroundService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class s31 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59858a0;

    /* renamed from: a1 */
    public final /* synthetic */ Notification f59859a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f59860a2;

    /* renamed from: a3 */
    public final /* synthetic */ SystemForegroundService f59861a3;

    public s31(SystemForegroundService systemForegroundService, int i, Notification notification, int i2) {
        this.f59861a3 = systemForegroundService;
        this.f59858a0 = i;
        this.f59859a1 = notification;
        this.f59860a2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = Build.VERSION.SDK_INT;
        int i2 = this.f59860a2;
        Notification notification = this.f59859a1;
        int i3 = this.f59858a0;
        SystemForegroundService systemForegroundService = this.f59861a3;
        if (i >= 31) {
            u31.m214815a0(systemForegroundService, i3, notification, i2);
        } else if (i >= 29) {
            t31.m214685a0(systemForegroundService, i3, notification, i2);
        } else {
            systemForegroundService.startForeground(i3, notification);
        }
    }
}
