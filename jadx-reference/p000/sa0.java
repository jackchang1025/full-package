package p000;

import android.animation.ObjectAnimator;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class sa0 extends AbstractC0395cy {

    /* renamed from: a9 */
    public static final C0556gt f59941a9 = new C0556gt(Float.class, "animationFraction", 12);

    /* renamed from: a3 */
    public ObjectAnimator f59942a3;

    /* renamed from: a4 */
    public final C1487yo f59943a4;

    /* renamed from: a5 */
    public final LinearProgressIndicatorSpec f59944a5;

    /* renamed from: a6 */
    public int f59945a6;

    /* renamed from: a7 */
    public boolean f59946a7;

    /* renamed from: a8 */
    public float f59947a8;

    public sa0(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(3);
        this.f59945a6 = 1;
        this.f59944a5 = linearProgressIndicatorSpec;
        this.f59943a4 = new C1487yo(1);
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a0 */
    public final void mo212538a0() {
        ObjectAnimator objectAnimator = this.f59942a3;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b3 */
    public final void mo212541b3() {
        this.f59946a7 = true;
        this.f59945a6 = 1;
        Arrays.fill((int[]) this.f55540a2, kj1.m213561a8(this.f59944a5.f55695a2[0], ((n50) this.f55538a0).f60300a9));
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b6 */
    public final void mo212544b6() {
        if (this.f59942a3 == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, f59941a9, 0.0f, 1.0f);
            this.f59942a3 = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(333L);
            this.f59942a3.setInterpolator(null);
            this.f59942a3.setRepeatCount(-1);
            this.f59942a3.addListener(new C0847m3(6, this));
        }
        this.f59946a7 = true;
        this.f59945a6 = 1;
        Arrays.fill((int[]) this.f55540a2, kj1.m213561a8(this.f59944a5.f55695a2[0], ((n50) this.f55538a0).f60300a9));
        this.f59942a3.start();
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b5 */
    public final void mo212543b5() {
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b8 */
    public final void mo212546b8() {
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b4 */
    public final void mo212542b4(C0410dc c0410dc) {
    }
}
