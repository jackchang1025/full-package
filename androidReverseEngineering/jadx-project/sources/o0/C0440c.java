package o0;

import android.animation.ValueAnimator;

/* renamed from: o0.c */
/* loaded from: classes.dex */
public final class C0440c implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a */
    public final /* synthetic */ C0443f f987a;

    /* renamed from: b */
    public final /* synthetic */ C0445h f988b;

    public C0440c(C0445h c0445h, C0443f c0443f) {
        this.f988b = c0445h;
        this.f987a = c0443f;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f987a.f1003a = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.f988b.invalidate();
    }
}
