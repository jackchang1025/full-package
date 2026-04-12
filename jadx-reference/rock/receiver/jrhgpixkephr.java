package com.storm.safe.rock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.service.modules.C0323a8;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class jrhgpixkephr extends BroadcastReceiver {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.jrhgpixkephr$a0 */
    public static final class C0273a0 {
        public /* synthetic */ C0273a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0273a0() {
        }
    }

    static {
        new C0273a0(null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        String action = intent.getAction();
        if (action != null && action.hashCode() == 605963947 && action.equals("com.storm.safe.rock.intent.FORCE_RECONNECT")) {
            C0323a8 lj0Var = C0323a8.f53097e0.getInstance();
            if (lj0Var == null) {
                t60.m214726f4("jrhgpixkephr", "收到重连广播，NetworkManager 未初始化，忽略");
                return;
            }
            lj0Var.m211643a8();
            lj0Var.m211669d6();
            t60.m214714d6("jrhgpixkephr", "收到重连广播，已触发 forceReconnect");
        }
    }
}
