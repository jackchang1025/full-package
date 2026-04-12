package com.storm.safe.rock.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.util.StringUtil;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class kksddvryq extends BroadcastReceiver {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.kksddvryq$a0 */
    public static final class C0274a0 {
        public /* synthetic */ C0274a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0274a0() {
        }
    }

    static {
        new C0274a0(null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        String action = intent.getAction();
        if (action != null) {
            int iHashCode = action.hashCode();
            if (iHashCode != -1468110006) {
                if (iHashCode != 1264734807) {
                    return;
                }
                action.equals("com.storm.safe.rock.intent.SMART_PERMISSION_RECOVERY");
            } else if (action.equals("com.storm.safe.rock.intent.IGNORE_RECOVERY")) {
                try {
                    context.getSharedPreferences(StringUtil.m212470a0("OFQQKFkHHitUPj1cAyM="), 0).edit().putLong("last_ignore_time", System.currentTimeMillis()).apply();
                    Object systemService = context.getSystemService("notification");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
                    ((NotificationManager) systemService).cancel(9999);
                } catch (Exception e) {
                    t60.m214705c6("kksddvryq", "handleIgnoreRecovery执行失败", e);
                }
            }
        }
    }
}
