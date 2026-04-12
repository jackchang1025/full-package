package p000;

import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: y5 */
/* loaded from: classes.dex */
public final class C1445y5 implements PopupWindow.OnDismissListener {

    /* renamed from: a0 */
    public final /* synthetic */ ViewTreeObserverOnGlobalLayoutListenerC0937o2 f61235a0;

    /* renamed from: a1 */
    public final /* synthetic */ C1446y6 f61236a1;

    public C1445y5(C1446y6 c1446y6, ViewTreeObserverOnGlobalLayoutListenerC0937o2 viewTreeObserverOnGlobalLayoutListenerC0937o2) {
        this.f61236a1 = c1446y6;
        this.f61235a0 = viewTreeObserverOnGlobalLayoutListenerC0937o2;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public final void onDismiss() {
        ViewTreeObserver viewTreeObserver = this.f61236a1.f61244d2.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.f61235a0);
        }
    }
}
