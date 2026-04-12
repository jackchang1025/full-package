package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zx */
/* loaded from: classes2.dex */
public abstract class AbstractC1534zx extends AnimatorListenerAdapter implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public boolean f61596a0;

    /* renamed from: a1 */
    public float f61597a1;

    /* renamed from: a2 */
    public float f61598a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0000a f61599a3;

    public AbstractC1534zx(C0000a c0000a) {
        this.f61599a3 = c0000a;
    }

    /* renamed from: a0 */
    public abstract float mo215434a0();

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public final void onAnimationEnd(Animator animator) {
        float f = (int) this.f61598a2;
        ce0 ce0Var = this.f61599a3.f61612a1;
        if (ce0Var != null) {
            ce0Var.m210839b1(f);
        }
        this.f61596a0 = false;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        boolean z = this.f61596a0;
        C0000a c0000a = this.f61599a3;
        if (!z) {
            ce0 ce0Var = c0000a.f61612a1;
            this.f61597a1 = ce0Var == null ? 0.0f : ce0Var.f46107a0.f45849b2;
            this.f61598a2 = mo215434a0();
            this.f61596a0 = true;
        }
        float f = this.f61597a1;
        float animatedFraction = (int) ((valueAnimator.getAnimatedFraction() * (this.f61598a2 - f)) + f);
        ce0 ce0Var2 = c0000a.f61612a1;
        if (ce0Var2 != null) {
            ce0Var2.m210839b1(animatedFraction);
        }
    }
}
