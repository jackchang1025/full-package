package p000;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mr */
/* loaded from: classes.dex */
public abstract class AbstractC0872mr {
    /* renamed from: a0 */
    public static Intent m214018a0(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i) {
        if ((i & 4) == 0 || str != null) {
            return context.registerReceiver(broadcastReceiver, intentFilter, str, handler, i & 1);
        }
        String str2 = context.getPackageName() + ".DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION";
        if (cq0.m212474a2(context, str2) == 0) {
            return context.registerReceiver(broadcastReceiver, intentFilter, str2, handler);
        }
        throw new RuntimeException(AbstractC0003a2.m33b4("Permission ", str2, " is required by your application to receive broadcasts, please add it to your manifest"));
    }

    /* renamed from: a1 */
    public static ComponentName m214019a1(Context context, Intent intent) {
        return context.startForegroundService(intent);
    }
}
