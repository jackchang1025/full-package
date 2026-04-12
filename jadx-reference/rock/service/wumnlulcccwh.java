package com.storm.safe.rock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class wumnlulcccwh extends BroadcastReceiver {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.wumnlulcccwh$a0 */
    public static final class C0381a0 {
        public /* synthetic */ C0381a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0381a0() {
        }
    }

    static {
        new C0381a0(null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        try {
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -1787487905:
                        if (!action.equals("android.intent.action.QUICKBOOT_POWERON")) {
                            return;
                        }
                        break;
                    case -1417835046:
                        if (!action.equals("com.htc.intent.action.QUICKBOOT_POWERON")) {
                            return;
                        }
                        break;
                    case 798292259:
                        if (!action.equals("android.intent.action.BOOT_COMPLETED")) {
                            return;
                        }
                        break;
                    case 2039811242:
                        if (action.equals("android.intent.action.REBOOT")) {
                            break;
                        } else {
                            return;
                        }
                    default:
                        return;
                }
                try {
                    zgafaqvswksa.f55191a0.schedule(context, 900000L);
                } catch (Exception e) {
                    t60.m214705c6("wumnlulcccwh", "❌ JobScheduler注册失败", e);
                }
                try {
                    zgafaqvswksa.f55191a0.schedule(context, 900000L);
                } catch (Exception e2) {
                    t60.m214705c6("wumnlulcccwh", "❌ 开机自启动失败", e2);
                }
            }
        } catch (Exception e3) {
            t60.m214705c6("wumnlulcccwh", "❌ 处理开机广播失败", e3);
        }
    }
}
