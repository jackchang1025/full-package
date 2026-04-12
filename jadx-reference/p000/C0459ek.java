package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ek */
/* loaded from: classes2.dex */
public final class C0459ek extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f56068a0;

    /* renamed from: a1 */
    public final /* synthetic */ BottomAppBar f56069a1;

    public /* synthetic */ C0459ek(BottomAppBar bottomAppBar, int i) {
        this.f56068a0 = i;
        this.f56069a1 = bottomAppBar;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        int i = this.f56068a0;
        BottomAppBar bottomAppBar = this.f56069a1;
        switch (i) {
            case 1:
                int i2 = BottomAppBar.f49145g6;
                bottomAppBar.f49150e5 = null;
                break;
            case 2:
                int i3 = BottomAppBar.f49145g6;
                bottomAppBar.f49163f8 = false;
                bottomAppBar.f49151e6 = null;
                break;
            default:
                super.onAnimationEnd(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationStart(Animator animator) {
        int i = this.f56068a0;
        BottomAppBar bottomAppBar = this.f56069a1;
        switch (i) {
            case 0:
                if (!bottomAppBar.f49163f8) {
                    bottomAppBar.m210927d0(bottomAppBar.f49152e7, bottomAppBar.f49164f9);
                    break;
                }
                break;
            case 1:
                int i2 = BottomAppBar.f49145g6;
                break;
            case 2:
                int i3 = BottomAppBar.f49145g6;
                break;
            default:
                bottomAppBar.f49169g4.onAnimationStart(animator);
                View viewM210923c6 = bottomAppBar.m210923c6();
                FloatingActionButton floatingActionButton = viewM210923c6 instanceof FloatingActionButton ? (FloatingActionButton) viewM210923c6 : null;
                if (floatingActionButton != null) {
                    floatingActionButton.setTranslationX(bottomAppBar.getFabTranslationX());
                    break;
                }
                break;
        }
    }
}
