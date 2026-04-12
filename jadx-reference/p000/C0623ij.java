package p000;

import android.animation.ObjectAnimator;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ij */
/* loaded from: classes2.dex */
public final class C0623ij extends AbstractC0395cy {

    /* renamed from: b1 */
    public static final int[] f56893b1 = {0, 1350, 2700, 4050};

    /* renamed from: b2 */
    public static final int[] f56894b2 = {667, 2017, 3367, 4717};

    /* renamed from: b3 */
    public static final int[] f56895b3 = {1000, 2350, 3700, 5050};

    /* renamed from: b4 */
    public static final C0556gt f56896b4 = new C0556gt(Float.class, "animationFraction", 5);

    /* renamed from: b5 */
    public static final C0556gt f56897b5 = new C0556gt(Float.class, "completeEndFraction", 6);

    /* renamed from: a3 */
    public ObjectAnimator f56898a3;

    /* renamed from: a4 */
    public ObjectAnimator f56899a4;

    /* renamed from: a5 */
    public final C1487yo f56900a5;

    /* renamed from: a6 */
    public final CircularProgressIndicatorSpec f56901a6;

    /* renamed from: a7 */
    public int f56902a7;

    /* renamed from: a8 */
    public float f56903a8;

    /* renamed from: a9 */
    public float f56904a9;

    /* renamed from: b0 */
    public C0410dc f56905b0;

    public C0623ij(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.f56902a7 = 0;
        this.f56905b0 = null;
        this.f56901a6 = circularProgressIndicatorSpec;
        this.f56900a5 = new C1487yo(1);
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a0 */
    public final void mo212538a0() {
        ObjectAnimator objectAnimator = this.f56898a3;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b3 */
    public final void mo212541b3() {
        this.f56902a7 = 0;
        ((int[]) this.f55540a2)[0] = kj1.m213561a8(this.f56901a6.f55695a2[0], ((n50) this.f55538a0).f60300a9);
        this.f56904a9 = 0.0f;
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b4 */
    public final void mo212542b4(C0410dc c0410dc) {
        this.f56905b0 = c0410dc;
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b5 */
    public final void mo212543b5() {
        ObjectAnimator objectAnimator = this.f56899a4;
        if (objectAnimator == null || objectAnimator.isRunning()) {
            return;
        }
        if (((n50) this.f55538a0).isVisible()) {
            this.f56899a4.start();
        } else {
            mo212538a0();
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b6 */
    public final void mo212544b6() {
        if (this.f56898a3 == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, f56896b4, 0.0f, 1.0f);
            this.f56898a3 = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(5400L);
            this.f56898a3.setInterpolator(null);
            this.f56898a3.setRepeatCount(-1);
            this.f56898a3.addListener(new C0622ii(this, 0));
        }
        if (this.f56899a4 == null) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, f56897b5, 0.0f, 1.0f);
            this.f56899a4 = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(333L);
            this.f56899a4.setInterpolator(this.f56900a5);
            this.f56899a4.addListener(new C0622ii(this, 1));
        }
        this.f56902a7 = 0;
        ((int[]) this.f55540a2)[0] = kj1.m213561a8(this.f56901a6.f55695a2[0], ((n50) this.f55538a0).f60300a9);
        this.f56904a9 = 0.0f;
        this.f56898a3.start();
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b8 */
    public final void mo212546b8() {
        this.f56905b0 = null;
    }
}
