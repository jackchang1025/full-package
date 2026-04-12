package p000;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class eh0 implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final hd0 f56007a0;

    /* renamed from: a1 */
    public final View[] f56008a1;

    public eh0(hd0 hd0Var, View... viewArr) {
        this.f56007a0 = hd0Var;
        this.f56008a1 = viewArr;
    }

    /* renamed from: a0 */
    public static eh0 m212676a0(View... viewArr) {
        return new eh0(new hd0(2), viewArr);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        for (View view : this.f56008a1) {
            switch (this.f56007a0.f56653a0) {
                case 1:
                    view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    break;
                case 2:
                    view.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    break;
                case 3:
                    Float f = (Float) valueAnimator.getAnimatedValue();
                    view.setScaleX(f.floatValue());
                    view.setScaleY(f.floatValue());
                    break;
                default:
                    view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    break;
            }
        }
    }
}
