package p000;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R$attr;
import com.google.android.material.R$drawable;
import com.google.android.material.R$string;
import com.google.android.material.internal.CheckableImageButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ix */
/* loaded from: classes2.dex */
public final class C0697ix extends AbstractC1416xg {

    /* renamed from: a4 */
    public final int f57238a4;

    /* renamed from: a5 */
    public final int f57239a5;

    /* renamed from: a6 */
    public final TimeInterpolator f57240a6;

    /* renamed from: a7 */
    public final TimeInterpolator f57241a7;

    /* renamed from: a8 */
    public EditText f57242a8;

    /* renamed from: a9 */
    public final ViewOnClickListenerC1203s1 f57243a9;

    /* renamed from: b0 */
    public final ViewOnFocusChangeListenerC0694iu f57244b0;

    /* renamed from: b1 */
    public AnimatorSet f57245b1;

    /* renamed from: b2 */
    public ValueAnimator f57246b2;

    public C0697ix(C1415xf c1415xf) {
        super(c1415xf);
        this.f57243a9 = new ViewOnClickListenerC1203s1(2, this);
        this.f57244b0 = new ViewOnFocusChangeListenerC0694iu(0, this);
        this.f57238a4 = kg1.m213536e3(c1415xf.getContext(), R$attr.motionDurationShort3, 100);
        this.f57239a5 = kg1.m213536e3(c1415xf.getContext(), R$attr.motionDurationShort3, 150);
        this.f57240a6 = kg1.m213537e4(c1415xf.getContext(), R$attr.motionEasingLinearInterpolator, AbstractC1249t7.f60178a0);
        this.f57241a7 = kg1.m213537e4(c1415xf.getContext(), R$attr.motionEasingEmphasizedInterpolator, AbstractC1249t7.f60181a3);
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a0 */
    public final void mo213189a0() {
        if (this.f61104a1.f61094b5 != null) {
            return;
        }
        m213199b8(m213200b9());
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a2 */
    public final int mo213190a2() {
        return R$string.clear_text_end_icon_content_description;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a3 */
    public final int mo213191a3() {
        return R$drawable.mtrl_ic_cancel;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a4 */
    public final View.OnFocusChangeListener mo213192a4() {
        return this.f57244b0;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a5 */
    public final View.OnClickListener mo213193a5() {
        return this.f57243a9;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: a6 */
    public final View.OnFocusChangeListener mo213194a6() {
        return this.f57244b0;
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b1 */
    public final void mo213195b1(EditText editText) {
        this.f57242a8 = editText;
        this.f61103a0.setEndIconVisible(m213200b9());
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b4 */
    public final void mo213196b4(boolean z) {
        if (this.f61104a1.f61094b5 == null) {
            return;
        }
        m213199b8(z);
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b6 */
    public final void mo213197b6() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.8f, 1.0f);
        valueAnimatorOfFloat.setInterpolator(this.f57241a7);
        valueAnimatorOfFloat.setDuration(this.f57239a5);
        final int i = 1;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: iv

            /* renamed from: a1 */
            public final /* synthetic */ C0697ix f57227a1;

            {
                this.f57227a1 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i) {
                    case 0:
                        C0697ix c0697ix = this.f57227a1;
                        c0697ix.getClass();
                        c0697ix.f61106a3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                    default:
                        C0697ix c0697ix2 = this.f57227a1;
                        c0697ix2.getClass();
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = c0697ix2.f61106a3;
                        checkableImageButton.setScaleX(fFloatValue);
                        checkableImageButton.setScaleY(fFloatValue);
                        break;
                }
            }
        });
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        TimeInterpolator timeInterpolator = this.f57240a6;
        valueAnimatorOfFloat2.setInterpolator(timeInterpolator);
        int i2 = this.f57238a4;
        valueAnimatorOfFloat2.setDuration(i2);
        final int i3 = 0;
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: iv

            /* renamed from: a1 */
            public final /* synthetic */ C0697ix f57227a1;

            {
                this.f57227a1 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i3) {
                    case 0:
                        C0697ix c0697ix = this.f57227a1;
                        c0697ix.getClass();
                        c0697ix.f61106a3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                    default:
                        C0697ix c0697ix2 = this.f57227a1;
                        c0697ix2.getClass();
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = c0697ix2.f61106a3;
                        checkableImageButton.setScaleX(fFloatValue);
                        checkableImageButton.setScaleY(fFloatValue);
                        break;
                }
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        this.f57245b1 = animatorSet;
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfFloat2);
        this.f57245b1.addListener(new C0696iw(this, i3));
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat3.setInterpolator(timeInterpolator);
        valueAnimatorOfFloat3.setDuration(i2);
        valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: iv

            /* renamed from: a1 */
            public final /* synthetic */ C0697ix f57227a1;

            {
                this.f57227a1 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i3) {
                    case 0:
                        C0697ix c0697ix = this.f57227a1;
                        c0697ix.getClass();
                        c0697ix.f61106a3.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        break;
                    default:
                        C0697ix c0697ix2 = this.f57227a1;
                        c0697ix2.getClass();
                        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        CheckableImageButton checkableImageButton = c0697ix2.f61106a3;
                        checkableImageButton.setScaleX(fFloatValue);
                        checkableImageButton.setScaleY(fFloatValue);
                        break;
                }
            }
        });
        this.f57246b2 = valueAnimatorOfFloat3;
        valueAnimatorOfFloat3.addListener(new C0696iw(this, i));
    }

    @Override // p000.AbstractC1416xg
    /* renamed from: b7 */
    public final void mo213198b7() {
        EditText editText = this.f57242a8;
        if (editText != null) {
            editText.post(new RunnableC0941o6(5, this));
        }
    }

    /* renamed from: b8 */
    public final void m213199b8(boolean z) {
        boolean z2 = this.f61104a1.m215158a2() == z;
        if (z && !this.f57245b1.isRunning()) {
            this.f57246b2.cancel();
            this.f57245b1.start();
            if (z2) {
                this.f57245b1.end();
                return;
            }
            return;
        }
        if (z) {
            return;
        }
        this.f57245b1.cancel();
        this.f57246b2.start();
        if (z2) {
            this.f57246b2.end();
        }
    }

    /* renamed from: b9 */
    public final boolean m213200b9() {
        EditText editText = this.f57242a8;
        if (editText != null) {
            return (editText.hasFocus() || this.f61106a3.hasFocus()) && this.f57242a8.getText().length() > 0;
        }
        return false;
    }
}
