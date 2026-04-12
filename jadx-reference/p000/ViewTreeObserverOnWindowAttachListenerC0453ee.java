package p000;

import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.RelativeLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ee */
/* loaded from: classes2.dex */
public final class ViewTreeObserverOnWindowAttachListenerC0453ee implements ViewTreeObserver.OnWindowAttachListener {

    /* renamed from: a0 */
    public final /* synthetic */ RelativeLayout f55973a0;

    public ViewTreeObserverOnWindowAttachListenerC0453ee(RelativeLayout relativeLayout) {
        this.f55973a0 = relativeLayout;
    }

    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
    public final void onWindowAttached() {
        RelativeLayout relativeLayout = this.f55973a0;
        try {
            WindowInsetsController windowInsetsController = relativeLayout.getWindowInsetsController();
            if (windowInsetsController != null) {
                windowInsetsController.hide(WindowInsets.Type.statusBars());
                windowInsetsController.setSystemBarsBehavior(2);
            }
        } catch (Exception e) {
            tz0.m214810b0("WindowInsetsController 设置失败: ", e.getMessage(), "BlackScreenOverlay");
        }
        relativeLayout.getViewTreeObserver().removeOnWindowAttachListener(this);
    }

    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
    public final void onWindowDetached() {
    }
}
