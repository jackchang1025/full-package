package com.storm.safe.rock.service.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.storm.safe.rock.receiver.hhymfsyujsj;
import p000.AbstractC1120qr;
import p000.mj1;
import p000.nj1;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zdcfpfxnz extends BroadcastReceiver {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.modules.zdcfpfxnz$a0 */
    public static final class C0374a0 {
        public /* synthetic */ C0374a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0374a0() {
        }
    }

    static {
        new C0374a0(null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        try {
            hhymfsyujsj.f52289a0.startServices(context);
        } catch (Exception unused) {
        }
        mj1 mj1Var = nj1.f58634a4;
        mj1.wakeScreen$default(mj1Var, context, false, 2, null);
        nj1 mj1Var2 = mj1Var.getInstance(context);
        C0323a8 lj0Var = C0323a8.f53097e0.getInstance();
        boolean z = lj0Var != null ? lj0Var.f53103a3 : false;
        long j = !z ? 300000L : mj1Var2.f58639a3 * 60000;
        mj1Var2.m214109a0(System.currentTimeMillis() + j);
        if (z) {
            return;
        }
        t60.m214714d6("zdcfpfxnz", "⚡ WS 断开，" + (j / 1000) + "s 后再次唤醒（setAlarmClock 不限流）");
    }
}
