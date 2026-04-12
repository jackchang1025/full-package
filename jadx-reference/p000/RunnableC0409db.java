package p000;

import android.os.SystemClock;
import com.google.android.material.progressindicator.AbstractC0217a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: db */
/* loaded from: classes2.dex */
public final class RunnableC0409db implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f55676a0;

    /* renamed from: a1 */
    public final /* synthetic */ AbstractC0217a0 f55677a1;

    public /* synthetic */ RunnableC0409db(AbstractC0217a0 abstractC0217a0, int i) {
        this.f55676a0 = i;
        this.f55677a1 = abstractC0217a0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f55676a0) {
            case 0:
                AbstractC0217a0 abstractC0217a0 = this.f55677a1;
                if (abstractC0217a0.f49698a3 > 0) {
                    SystemClock.uptimeMillis();
                }
                abstractC0217a0.setVisibility(0);
                break;
            default:
                AbstractC0217a0 abstractC0217a02 = this.f55677a1;
                ((AbstractC1277tx) abstractC0217a02.getCurrentDrawable()).m214797a4(false, false, true);
                if ((abstractC0217a02.getProgressDrawable() == null || !abstractC0217a02.getProgressDrawable().isVisible()) && (abstractC0217a02.getIndeterminateDrawable() == null || !abstractC0217a02.getIndeterminateDrawable().isVisible())) {
                    abstractC0217a02.setVisibility(4);
                }
                abstractC0217a02.getClass();
                break;
        }
    }
}
