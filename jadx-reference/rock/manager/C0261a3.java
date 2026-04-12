package com.storm.safe.rock.manager;

import android.media.projection.MediaProjection;
import android.os.Build;
import com.storm.safe.rock.service.C0286a6;
import java.util.Iterator;
import kotlinx.coroutines.AbstractC0780a0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.manager.a3 */
/* loaded from: classes2.dex */
public final class C0261a3 extends MediaProjection.Callback {

    /* renamed from: a0 */
    public final /* synthetic */ C0262a4 f52126a0;

    public C0261a3(C0262a4 c0262a4) {
        this.f52126a0 = c0262a4;
    }

    @Override // android.media.projection.MediaProjection.Callback
    public final void onStop() {
        t60.m214726f4("SmartMediaProjection", "🛑 MediaProjection权限丢失");
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f52126a0.f52137a8;
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason = jCurrentTimeMillis < 2000 ? SmartMediaProjectionManager$LossReason.f52045a1 : jCurrentTimeMillis < 5000 ? SmartMediaProjectionManager$LossReason.f52044a0 : Build.VERSION.SDK_INT >= 35 ? SmartMediaProjectionManager$LossReason.f52046a2 : SmartMediaProjectionManager$LossReason.f52047a3;
        this.f52126a0.m211339a3();
        Iterator it = this.f52126a0.f52141b2.iterator();
        while (it.hasNext()) {
            try {
                ((C0286a6) it.next()).m211394a0(smartMediaProjectionManager$LossReason);
            } catch (Exception e) {
                t60.m214705c6("SmartMediaProjection", "❌ 通知权限丢失失败", e);
            }
        }
        C0262a4 c0262a4 = this.f52126a0;
        int iOrdinal = smartMediaProjectionManager$LossReason.ordinal();
        if (iOrdinal == 0) {
            c0262a4.f52134a5.set(0);
            c0262a4.f52135a6.set(0);
        } else if ((iOrdinal == 2 || iOrdinal == 3 || iOrdinal == 4) && !c0262a4.f52133a4.get()) {
            AbstractC0780a0.m213692a3(c0262a4.f52138a9, null, new SmartMediaProjectionManager$startSmartRecovery$1(c0262a4, null), 3);
        }
    }
}
