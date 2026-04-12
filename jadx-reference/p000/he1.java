package p000;

import android.content.Context;
import android.os.PowerManager;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class he1 {

    /* renamed from: a0 */
    public static final /* synthetic */ int f56661a0 = 0;

    static {
        t60.m214694b5(C1351vv.m214966b1("WakeLocks"), "tagWithPrefix(\"WakeLocks\")");
    }

    /* renamed from: a0 */
    public static final PowerManager.WakeLock m213032a0(Context context, String str) {
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "tag");
        Object systemService = context.getApplicationContext().getSystemService("power");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
        String strConcat = "WorkManager: ".concat(str);
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) systemService).newWakeLock(1, strConcat);
        synchronized (ie1.f56876a0) {
        }
        t60.m214694b5(wakeLockNewWakeLock, "wakeLock");
        return wakeLockNewWakeLock;
    }
}
