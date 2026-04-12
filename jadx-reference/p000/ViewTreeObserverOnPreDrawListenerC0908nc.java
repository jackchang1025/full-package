package p000;

import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nc */
/* loaded from: classes.dex */
public final class ViewTreeObserverOnPreDrawListenerC0908nc implements ViewTreeObserver.OnPreDrawListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f58490a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58491a1;

    public /* synthetic */ ViewTreeObserverOnPreDrawListenerC0908nc(int i, Object obj) {
        this.f58490a0 = i;
        this.f58491a1 = obj;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        switch (this.f58490a0) {
            case 0:
                ((CoordinatorLayout) this.f58491a1).m210067b5(0);
                break;
            default:
                AbstractC1535zy abstractC1535zy = (AbstractC1535zy) this.f58491a1;
                float rotation = abstractC1535zy.f61632c1.getRotation();
                if (abstractC1535zy.f61625b4 != rotation) {
                    abstractC1535zy.f61625b4 = rotation;
                    abstractC1535zy.mo9b6();
                    break;
                }
                break;
        }
        return true;
    }
}
