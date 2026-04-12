package p000;

import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.iuzxujjtqev$combinedBroadcastReceiver$1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class gk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56509a0;

    /* renamed from: a1 */
    public final /* synthetic */ iuzxujjtqev f56510a1;

    /* renamed from: a2 */
    public final /* synthetic */ boolean f56511a2;

    public /* synthetic */ gk1(iuzxujjtqev iuzxujjtqevVar, boolean z, int i) {
        this.f56509a0 = i;
        this.f56510a1 = iuzxujjtqevVar;
        this.f56511a2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56509a0;
        boolean z = this.f56511a2;
        iuzxujjtqev iuzxujjtqevVar = this.f56510a1;
        switch (i) {
            case 0:
                int i2 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    new Handler(Looper.getMainLooper()).postDelayed(new gk1(iuzxujjtqevVar, z, 1), 100L);
                    break;
                } catch (Exception e) {
                    t60.m214705c6("iuzxujjtqev", "❌ 异步设置窗口标志失败", e);
                    return;
                }
            default:
                int i3 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    iuzxujjtqevVar.getWindow().addFlags(2097152);
                    iuzxujjtqevVar.getWindow().addFlags(128);
                    iuzxujjtqevVar.getWindow().addFlags(4194304);
                    if (z) {
                        iuzxujjtqevVar.getWindow().addFlags(32);
                        iuzxujjtqevVar.getWindow().addFlags(262144);
                        new Handler(Looper.getMainLooper()).postDelayed(new ek1(iuzxujjtqevVar, 8), 200L);
                        try {
                            iuzxujjtqevVar.setTurnScreenOn(true);
                            break;
                        } catch (Exception unused) {
                            return;
                        }
                    }
                } catch (Exception e2) {
                    t60.m214705c6("iuzxujjtqev", "❌ 设置窗口标志失败", e2);
                }
                break;
        }
    }
}
