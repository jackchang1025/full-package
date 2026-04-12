package p000;

import android.content.ComponentName;
import android.os.PowerManager;
import androidx.core.app.JobIntentService;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class o70 {

    /* renamed from: a0 */
    public final PowerManager.WakeLock f58747a0;

    /* renamed from: a1 */
    public final PowerManager.WakeLock f58748a1;

    /* renamed from: a2 */
    public boolean f58749a2;

    public o70(JobIntentService jobIntentService, ComponentName componentName) {
        jobIntentService.getApplicationContext();
        PowerManager powerManager = (PowerManager) jobIntentService.getSystemService("power");
        PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, componentName.getClassName() + ":launch");
        this.f58747a0 = wakeLockNewWakeLock;
        wakeLockNewWakeLock.setReferenceCounted(false);
        PowerManager.WakeLock wakeLockNewWakeLock2 = powerManager.newWakeLock(1, componentName.getClassName() + ":run");
        this.f58748a1 = wakeLockNewWakeLock2;
        wakeLockNewWakeLock2.setReferenceCounted(false);
    }

    /* renamed from: a0 */
    public final void m214160a0() {
        synchronized (this) {
            try {
                if (this.f58749a2) {
                    this.f58749a2 = false;
                    this.f58748a1.release();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
