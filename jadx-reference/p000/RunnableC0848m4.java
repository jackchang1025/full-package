package p000;

import androidx.appcompat.widget.ActionBarOverlayLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: m4 */
/* loaded from: classes.dex */
public final class RunnableC0848m4 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f58258a0;

    /* renamed from: a1 */
    public final /* synthetic */ ActionBarOverlayLayout f58259a1;

    public /* synthetic */ RunnableC0848m4(ActionBarOverlayLayout actionBarOverlayLayout, int i) {
        this.f58258a0 = i;
        this.f58259a1 = actionBarOverlayLayout;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f58258a0) {
            case 0:
                ActionBarOverlayLayout actionBarOverlayLayout = this.f58259a1;
                actionBarOverlayLayout.m209864a7();
                actionBarOverlayLayout.f43858c2 = actionBarOverlayLayout.f43839a3.animate().translationY(0.0f).setListener(actionBarOverlayLayout.f43859c3);
                break;
            default:
                ActionBarOverlayLayout actionBarOverlayLayout2 = this.f58259a1;
                actionBarOverlayLayout2.m209864a7();
                actionBarOverlayLayout2.f43858c2 = actionBarOverlayLayout2.f43839a3.animate().translationY(-actionBarOverlayLayout2.f43839a3.getHeight()).setListener(actionBarOverlayLayout2.f43859c3);
                break;
        }
    }
}
