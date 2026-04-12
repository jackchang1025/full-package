package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ta0 extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f60188a0;

    /* renamed from: a1 */
    public final /* synthetic */ ua0 f60189a1;

    public /* synthetic */ ta0(ua0 ua0Var, int i) {
        this.f60188a0 = i;
        this.f60189a1 = ua0Var;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        switch (this.f60188a0) {
            case 1:
                super.onAnimationEnd(animator);
                ua0 ua0Var = this.f60189a1;
                ua0Var.mo212538a0();
                C0410dc c0410dc = ua0Var.f60369b0;
                if (c0410dc != null) {
                    c0410dc.m212579a0((n50) ua0Var.f55538a0);
                    break;
                }
                break;
            default:
                super.onAnimationEnd(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
        switch (this.f60188a0) {
            case 0:
                super.onAnimationRepeat(animator);
                ua0 ua0Var = this.f60189a1;
                ua0Var.f60366a7 = (ua0Var.f60366a7 + 1) % ua0Var.f60365a6.f55695a2.length;
                ua0Var.f60367a8 = true;
                break;
            default:
                super.onAnimationRepeat(animator);
                break;
        }
    }
}
