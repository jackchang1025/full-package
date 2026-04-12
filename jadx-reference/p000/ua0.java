package p000;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.google.android.material.R$anim;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ua0 extends AbstractC0395cy {

    /* renamed from: b1 */
    public static final int[] f60359b1 = {533, 567, 850, 750};

    /* renamed from: b2 */
    public static final int[] f60360b2 = {1267, 1000, 333, 0};

    /* renamed from: b3 */
    public static final C0556gt f60361b3 = new C0556gt(Float.class, "animationFraction", 13);

    /* renamed from: a3 */
    public ObjectAnimator f60362a3;

    /* renamed from: a4 */
    public ObjectAnimator f60363a4;

    /* renamed from: a5 */
    public final Interpolator[] f60364a5;

    /* renamed from: a6 */
    public final LinearProgressIndicatorSpec f60365a6;

    /* renamed from: a7 */
    public int f60366a7;

    /* renamed from: a8 */
    public boolean f60367a8;

    /* renamed from: a9 */
    public float f60368a9;

    /* renamed from: b0 */
    public C0410dc f60369b0;

    public ua0(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(2);
        this.f60366a7 = 0;
        this.f60369b0 = null;
        this.f60365a6 = linearProgressIndicatorSpec;
        this.f60364a5 = new Interpolator[]{AnimationUtils.loadInterpolator(context, R$anim.linear_indeterminate_line1_head_interpolator), AnimationUtils.loadInterpolator(context, R$anim.linear_indeterminate_line1_tail_interpolator), AnimationUtils.loadInterpolator(context, R$anim.linear_indeterminate_line2_head_interpolator), AnimationUtils.loadInterpolator(context, R$anim.linear_indeterminate_line2_tail_interpolator)};
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: a0 */
    public final void mo212538a0() {
        ObjectAnimator objectAnimator = this.f60362a3;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b3 */
    public final void mo212541b3() {
        this.f60366a7 = 0;
        int iM213561a8 = kj1.m213561a8(this.f60365a6.f55695a2[0], ((n50) this.f55538a0).f60300a9);
        int[] iArr = (int[]) this.f55540a2;
        iArr[0] = iM213561a8;
        iArr[1] = iM213561a8;
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b4 */
    public final void mo212542b4(C0410dc c0410dc) {
        this.f60369b0 = c0410dc;
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b5 */
    public final void mo212543b5() {
        ObjectAnimator objectAnimator = this.f60363a4;
        if (objectAnimator == null || objectAnimator.isRunning()) {
            return;
        }
        mo212538a0();
        if (((n50) this.f55538a0).isVisible()) {
            this.f60363a4.setFloatValues(this.f60368a9, 1.0f);
            this.f60363a4.setDuration((long) ((1.0f - this.f60368a9) * 1800.0f));
            this.f60363a4.start();
        }
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b6 */
    public final void mo212544b6() {
        ObjectAnimator objectAnimator = this.f60362a3;
        int i = 0;
        C0556gt c0556gt = f60361b3;
        if (objectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, c0556gt, 0.0f, 1.0f);
            this.f60362a3 = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(1800L);
            this.f60362a3.setInterpolator(null);
            this.f60362a3.setRepeatCount(-1);
            this.f60362a3.addListener(new ta0(this, i));
        }
        int i2 = 1;
        if (this.f60363a4 == null) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, c0556gt, 1.0f);
            this.f60363a4 = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setDuration(1800L);
            this.f60363a4.setInterpolator(null);
            this.f60363a4.addListener(new ta0(this, i2));
        }
        this.f60366a7 = 0;
        int iM213561a8 = kj1.m213561a8(this.f60365a6.f55695a2[0], ((n50) this.f55538a0).f60300a9);
        int[] iArr = (int[]) this.f55540a2;
        iArr[0] = iM213561a8;
        iArr[1] = iM213561a8;
        this.f60362a3.start();
    }

    @Override // p000.AbstractC0395cy
    /* renamed from: b8 */
    public final void mo212546b8() {
        this.f60369b0 = null;
    }
}
