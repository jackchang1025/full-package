package p000;

import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transformation.ExpandableBehavior;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xr */
/* loaded from: classes2.dex */
public final class ViewTreeObserverOnPreDrawListenerC1427xr implements ViewTreeObserver.OnPreDrawListener {

    /* renamed from: a0 */
    public final /* synthetic */ View f61171a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f61172a1;

    /* renamed from: a2 */
    public final /* synthetic */ InterfaceC1428xs f61173a2;

    /* renamed from: a3 */
    public final /* synthetic */ ExpandableBehavior f61174a3;

    public ViewTreeObserverOnPreDrawListenerC1427xr(ExpandableBehavior expandableBehavior, View view, int i, InterfaceC1428xs interfaceC1428xs) {
        this.f61174a3 = expandableBehavior;
        this.f61171a0 = view;
        this.f61172a1 = i;
        this.f61173a2 = interfaceC1428xs;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        View view = this.f61171a0;
        view.getViewTreeObserver().removeOnPreDrawListener(this);
        ExpandableBehavior expandableBehavior = this.f61174a3;
        if (expandableBehavior.f50053a0 == this.f61172a1) {
            Object obj = this.f61173a2;
            expandableBehavior.mo211168b8((View) obj, view, ((FloatingActionButton) obj).f49513b4.f56131a0, false);
        }
        return false;
    }
}
