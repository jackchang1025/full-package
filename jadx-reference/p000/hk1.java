package p000;

import android.os.Handler;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ iuzxujjtqev f56677a0;

    public hk1(iuzxujjtqev iuzxujjtqevVar) {
        this.f56677a0 = iuzxujjtqevVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        iuzxujjtqev iuzxujjtqevVar = this.f56677a0;
        if (!iuzxujjtqevVar.f51973d8 || iuzxujjtqevVar.isFinishing() || iuzxujjtqevVar.isDestroyed()) {
            return;
        }
        boolean z = false;
        try {
            boolean zHasWindowFocus = iuzxujjtqevVar.hasWindowFocus();
            if (!iuzxujjtqevVar.isFinishing()) {
                boolean z2 = !iuzxujjtqevVar.isDestroyed();
                if (zHasWindowFocus && z2) {
                    z = true;
                }
            }
        } catch (Exception unused) {
        }
        if (z) {
            try {
                dqtvuisjd.f52358m1.setWebViewOpen(true);
            } catch (Exception e) {
                t60.m214705c6("iuzxujjtqev", "❌ 更新WebView状态失败", e);
                Handler handler = iuzxujjtqevVar.f51971d6;
                if (handler != null) {
                    handler.postDelayed(this, 500L);
                    return;
                }
                return;
            }
        }
        Handler handler2 = iuzxujjtqevVar.f51971d6;
        if (handler2 != null) {
            handler2.postDelayed(this, 500L);
        }
    }
}
