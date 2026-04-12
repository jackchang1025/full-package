package com.storm.safe.rock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import p000.AbstractC1120qr;
import p000.al1;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hhymfsyujsj extends BroadcastReceiver {

    /* renamed from: a0 */
    public static final C0271a0 f52289a0 = new C0271a0(null);

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.hhymfsyujsj$a0 */
    public static final class C0271a0 {
        public /* synthetic */ C0271a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public static /* synthetic */ void scheduleHeartbeat$default(C0271a0 c0271a0, Context context, long j, int i, Object obj) {
            if ((i & 2) != 0) {
                j = 0;
            }
            c0271a0.scheduleHeartbeat(context, j);
        }

        public static /* synthetic */ void scheduleNextPing$default(C0271a0 c0271a0, Context context, long j, int i, Object obj) {
            if ((i & 2) != 0) {
                j = 0;
            }
            c0271a0.scheduleNextPing(context, j);
        }

        public static /* synthetic */ void scheduleRecovery$default(C0271a0 c0271a0, Context context, long j, int i, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                i = 0;
            }
            c0271a0.scheduleRecovery(context, j, i);
        }

        public final void cancelPing(Context context) {
            t60.m214695b6(context, "context");
        }

        public final void cancelRecovery(Context context) {
            t60.m214695b6(context, "context");
        }

        public final void scheduleDualRecovery(Context context) {
            t60.m214695b6(context, "context");
            scheduleHeartbeat$default(this, context, 0L, 2, null);
        }

        public final void scheduleHeartbeat(Context context, long j) {
            t60.m214695b6(context, "context");
        }

        public final void scheduleNextPing(Context context, long j) {
            t60.m214695b6(context, "context");
            scheduleHeartbeat(context, j);
        }

        public final void scheduleRecovery(Context context, long j, int i) {
            t60.m214695b6(context, "context");
            scheduleHeartbeat(context, j);
        }

        public final void startServices(Context context) {
            t60.m214695b6(context, "context");
            try {
                al1.f43714a5.getInstance(context).m209821a1();
            } catch (Exception e) {
                t60.m214705c6("hhymfsyujsj", "启动服务失败", e);
            }
            System.currentTimeMillis();
            C0271a0 c0271a0 = hhymfsyujsj.f52289a0;
        }

        private C0271a0() {
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214702c3("hhymfsyujsj", "收到广播");
        f52289a0.startServices(context);
    }
}
