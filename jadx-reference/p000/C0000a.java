package p000;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Property;
import android.view.View;
import com.google.android.material.R$color;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: a */
/* loaded from: classes2.dex */
public final class C0000a extends AbstractC1535zy {

    /* renamed from: d9 */
    public StateListAnimator f0d9;

    @Override // p000.AbstractC1535zy
    /* renamed from: a4 */
    public final float mo0a4() {
        return this.f61632c1.getElevation();
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: a5 */
    public final void mo1a5(Rect rect) {
        if (((FloatingActionButton) this.f61633c2.f60218a1).f49509b0) {
            super.mo1a5(rect);
            return;
        }
        if (this.f61616a5) {
            FloatingActionButton floatingActionButton = this.f61632c1;
            int sizeDimension = floatingActionButton.getSizeDimension();
            int i = this.f61621b0;
            if (sizeDimension < i) {
                int sizeDimension2 = (i - floatingActionButton.getSizeDimension()) / 2;
                rect.set(sizeDimension2, sizeDimension2, sizeDimension2, sizeDimension2);
                return;
            }
        }
        rect.set(0, 0, 0, 0);
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: a6 */
    public final void mo2a6(ColorStateList colorStateList, PorterDuff.Mode mode, ColorStateList colorStateList2, int i) {
        Drawable layerDrawable;
        a01 a01Var = this.f61611a0;
        a01Var.getClass();
        C1537zz c1537zz = new C1537zz(a01Var);
        this.f61612a1 = c1537zz;
        c1537zz.setTintList(colorStateList);
        if (mode != null) {
            this.f61612a1.setTintMode(mode);
        }
        ce0 ce0Var = this.f61612a1;
        FloatingActionButton floatingActionButton = this.f61632c1;
        ce0Var.m210838b0(floatingActionButton.getContext());
        if (i > 0) {
            Context context = floatingActionButton.getContext();
            a01 a01Var2 = this.f61611a0;
            a01Var2.getClass();
            C0457ei c0457ei = new C0457ei(a01Var2);
            int iM214015a0 = AbstractC0871mq.m214015a0(context, R$color.design_fab_stroke_top_outer_color);
            int iM214015a02 = AbstractC0871mq.m214015a0(context, R$color.design_fab_stroke_top_inner_color);
            int iM214015a03 = AbstractC0871mq.m214015a0(context, R$color.design_fab_stroke_end_inner_color);
            int iM214015a04 = AbstractC0871mq.m214015a0(context, R$color.design_fab_stroke_end_outer_color);
            c0457ei.f56024a8 = iM214015a0;
            c0457ei.f56025a9 = iM214015a02;
            c0457ei.f56026b0 = iM214015a03;
            c0457ei.f56027b1 = iM214015a04;
            float f = i;
            if (c0457ei.f56023a7 != f) {
                c0457ei.f56023a7 = f;
                c0457ei.f56017a1.setStrokeWidth(f * 1.3333f);
                c0457ei.f56029b3 = true;
                c0457ei.invalidateSelf();
            }
            if (colorStateList != null) {
                c0457ei.f56028b2 = colorStateList.getColorForState(c0457ei.getState(), c0457ei.f56028b2);
            }
            c0457ei.f56031b5 = colorStateList;
            c0457ei.f56029b3 = true;
            c0457ei.invalidateSelf();
            this.f61614a3 = c0457ei;
            C0457ei c0457ei2 = this.f61614a3;
            c0457ei2.getClass();
            ce0 ce0Var2 = this.f61612a1;
            ce0Var2.getClass();
            layerDrawable = new LayerDrawable(new Drawable[]{c0457ei2, ce0Var2});
        } else {
            this.f61614a3 = null;
            layerDrawable = this.f61612a1;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(b81.m210594e5(colorStateList2), layerDrawable, null);
        this.f61613a2 = rippleDrawable;
        this.f61615a4 = rippleDrawable;
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: a8 */
    public final void mo4a8() {
        m215442b7();
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: b0 */
    public final void mo6b0(float f, float f2, float f3) {
        int i = Build.VERSION.SDK_INT;
        FloatingActionButton floatingActionButton = this.f61632c1;
        if (floatingActionButton.getStateListAnimator() == this.f0d9) {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(AbstractC1535zy.f61605d3, m10b8(f, f3));
            stateListAnimator.addState(AbstractC1535zy.f61606d4, m10b8(f, f2));
            stateListAnimator.addState(AbstractC1535zy.f61607d5, m10b8(f, f2));
            stateListAnimator.addState(AbstractC1535zy.f61608d6, m10b8(f, f2));
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(floatingActionButton, "elevation", f).setDuration(0L));
            if (i <= 24) {
                arrayList.add(ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.TRANSLATION_Z, floatingActionButton.getTranslationZ()).setDuration(100L));
            }
            arrayList.add(ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(100L));
            animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
            animatorSet.setInterpolator(AbstractC1535zy.f61600c8);
            stateListAnimator.addState(AbstractC1535zy.f61609d7, animatorSet);
            stateListAnimator.addState(AbstractC1535zy.f61610d8, m10b8(0.0f, 0.0f));
            this.f0d9 = stateListAnimator;
            floatingActionButton.setStateListAnimator(stateListAnimator);
        }
        if (mo8b5()) {
            m215442b7();
        }
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: b3 */
    public final void mo7b3(ColorStateList colorStateList) {
        Drawable drawable = this.f61613a2;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(b81.m210594e5(colorStateList));
        } else {
            super.mo7b3(colorStateList);
        }
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: b5 */
    public final boolean mo8b5() {
        if (((FloatingActionButton) this.f61633c2.f60218a1).f49509b0) {
            return true;
        }
        return this.f61616a5 && this.f61632c1.getSizeDimension() < this.f61621b0;
    }

    /* renamed from: b8 */
    public final AnimatorSet m10b8(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        float[] fArr = {f};
        FloatingActionButton floatingActionButton = this.f61632c1;
        animatorSet.play(ObjectAnimator.ofFloat(floatingActionButton, "elevation", fArr).setDuration(0L)).with(ObjectAnimator.ofFloat(floatingActionButton, (Property<FloatingActionButton, Float>) View.TRANSLATION_Z, f2).setDuration(100L));
        animatorSet.setInterpolator(AbstractC1535zy.f61600c8);
        return animatorSet;
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: a7 */
    public final void mo3a7() {
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: b6 */
    public final void mo9b6() {
    }

    @Override // p000.AbstractC1535zy
    /* renamed from: a9 */
    public final void mo5a9(int[] iArr) {
    }
}
