package p000;

import android.animation.ValueAnimator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ci0 implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ float f46141a0;

    /* renamed from: a1 */
    public final /* synthetic */ ei0 f46142a1;

    public ci0(ei0 ei0Var, float f) {
        this.f46142a1 = ei0Var;
        this.f46141a0 = f;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f46142a1.m212683a3(((Float) valueAnimator.getAnimatedValue()).floatValue(), this.f46141a0);
    }
}
