package p000;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v */
/* loaded from: classes.dex */
public final class RunnableC1319v implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f60537a0;

    /* renamed from: a1 */
    public final /* synthetic */ AbstractViewOnTouchListenerC1358w f60538a1;

    public /* synthetic */ RunnableC1319v(AbstractViewOnTouchListenerC1358w abstractViewOnTouchListenerC1358w, int i) {
        this.f60537a0 = i;
        this.f60538a1 = abstractViewOnTouchListenerC1358w;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f60537a0) {
            case 0:
                ViewParent parent = this.f60538a1.f60730a3.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
            default:
                AbstractViewOnTouchListenerC1358w abstractViewOnTouchListenerC1358w = this.f60538a1;
                abstractViewOnTouchListenerC1358w.m214973a0();
                View view = abstractViewOnTouchListenerC1358w.f60730a3;
                if (view.isEnabled() && !view.isLongClickable() && abstractViewOnTouchListenerC1358w.mo213949a2()) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                    view.onTouchEvent(motionEventObtain);
                    motionEventObtain.recycle();
                    abstractViewOnTouchListenerC1358w.f60733a6 = true;
                    break;
                }
                break;
        }
    }
}
