package p000;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: un */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1306un implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f60480a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f60481a1;

    public /* synthetic */ C1306un(int i, Object obj) {
        this.f60480a0 = i;
        this.f60481a1 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.f60480a0) {
            case 0:
                C1309uq c1309uq = (C1309uq) this.f60481a1;
                c1309uq.getClass();
                c1309uq.f61106a3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            case 1:
                ud0 ud0Var = (ud0) this.f60481a1;
                ud0Var.getClass();
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ud0Var.f60393a9.setAlpha((int) (255.0f * fFloatValue));
                ud0Var.f60407c3 = fFloatValue;
                break;
            case 2:
                C1279ty c1279ty = (C1279ty) this.f60481a1;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                if (c1279ty.f60310a8 != animatedFraction) {
                    c1279ty.f60310a8 = animatedFraction;
                    c1279ty.invalidateSelf();
                    break;
                }
                break;
            case 3:
                ((C1483yk) this.f60481a1).m215296a0(valueAnimator.getAnimatedFraction());
                break;
            default:
                ((View) ((ze1) ((jl0) this.f60481a1).f57345a0).f61519a9.getParent()).invalidate();
                break;
        }
    }

    public /* synthetic */ C1306un(jl0 jl0Var, View view) {
        this.f60480a0 = 4;
        this.f60481a1 = jl0Var;
    }
}
