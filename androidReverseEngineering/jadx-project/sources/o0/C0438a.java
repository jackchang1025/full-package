package o0;

import android.animation.ValueAnimator;

/* renamed from: o0.a */
/* loaded from: classes.dex */
public final class C0438a implements ValueAnimator.AnimatorUpdateListener {

    /* renamed from: a */
    public final /* synthetic */ C0443f f978a;

    /* renamed from: b */
    public final /* synthetic */ float f979b;

    /* renamed from: c */
    public final /* synthetic */ float f980c;

    /* renamed from: d */
    public final /* synthetic */ float f981d;

    /* renamed from: e */
    public final /* synthetic */ float f982e;

    /* renamed from: f */
    public final /* synthetic */ C0445h f983f;

    public C0438a(C0445h c0445h, C0443f c0443f, float f2, float f3, float f4, float f5) {
        this.f983f = c0445h;
        this.f978a = c0443f;
        this.f979b = f2;
        this.f980c = f3;
        this.f981d = f4;
        this.f982e = f5;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f2 = 1.0f - floatValue;
        float f3 = (this.f980c * floatValue) + (this.f979b * f2);
        C0443f c0443f = this.f978a;
        c0443f.f1005c = f3;
        c0443f.f1006d = (floatValue * this.f982e) + (f2 * this.f981d);
        this.f983f.invalidate();
    }
}
