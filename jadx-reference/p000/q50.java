package p000;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class q50 {

    /* renamed from: a0 */
    public final int f59387a0;

    /* renamed from: a1 */
    public final int f59388a1;

    /* renamed from: a2 */
    public final int f59389a2;

    /* renamed from: a3 */
    public final TimeInterpolator f59390a3;

    /* renamed from: a4 */
    public final TimeInterpolator f59391a4;

    /* renamed from: a5 */
    public final TimeInterpolator f59392a5;

    /* renamed from: a6 */
    public final Context f59393a6;

    /* renamed from: a7 */
    public final TextInputLayout f59394a7;

    /* renamed from: a8 */
    public LinearLayout f59395a8;

    /* renamed from: a9 */
    public int f59396a9;

    /* renamed from: b0 */
    public FrameLayout f59397b0;

    /* renamed from: b1 */
    public AnimatorSet f59398b1;

    /* renamed from: b2 */
    public final float f59399b2;

    /* renamed from: b3 */
    public int f59400b3;

    /* renamed from: b4 */
    public int f59401b4;

    /* renamed from: b5 */
    public CharSequence f59402b5;

    /* renamed from: b6 */
    public boolean f59403b6;

    /* renamed from: b7 */
    public AppCompatTextView f59404b7;

    /* renamed from: b8 */
    public CharSequence f59405b8;

    /* renamed from: b9 */
    public int f59406b9;

    /* renamed from: c0 */
    public int f59407c0;

    /* renamed from: c1 */
    public ColorStateList f59408c1;

    /* renamed from: c2 */
    public CharSequence f59409c2;

    /* renamed from: c3 */
    public boolean f59410c3;

    /* renamed from: c4 */
    public AppCompatTextView f59411c4;

    /* renamed from: c5 */
    public int f59412c5;

    /* renamed from: c6 */
    public ColorStateList f59413c6;

    /* renamed from: c7 */
    public Typeface f59414c7;

    public q50(TextInputLayout textInputLayout) {
        Context context = textInputLayout.getContext();
        this.f59393a6 = context;
        this.f59394a7 = textInputLayout;
        this.f59399b2 = context.getResources().getDimensionPixelSize(R$dimen.design_textinput_caption_translate_y);
        this.f59387a0 = kg1.m213536e3(context, R$attr.motionDurationShort4, 217);
        this.f59388a1 = kg1.m213536e3(context, R$attr.motionDurationMedium4, 167);
        this.f59389a2 = kg1.m213536e3(context, R$attr.motionDurationShort4, 167);
        this.f59390a3 = kg1.m213537e4(context, R$attr.motionEasingEmphasizedDecelerateInterpolator, AbstractC1249t7.f60181a3);
        int i = R$attr.motionEasingEmphasizedDecelerateInterpolator;
        LinearInterpolator linearInterpolator = AbstractC1249t7.f60178a0;
        this.f59391a4 = kg1.m213537e4(context, i, linearInterpolator);
        this.f59392a5 = kg1.m213537e4(context, R$attr.motionEasingLinearInterpolator, linearInterpolator);
    }

    /* renamed from: a0 */
    public final void m214351a0(AppCompatTextView appCompatTextView, int i) {
        if (this.f59395a8 == null && this.f59397b0 == null) {
            Context context = this.f59393a6;
            LinearLayout linearLayout = new LinearLayout(context);
            this.f59395a8 = linearLayout;
            linearLayout.setOrientation(0);
            LinearLayout linearLayout2 = this.f59395a8;
            TextInputLayout textInputLayout = this.f59394a7;
            textInputLayout.addView(linearLayout2, -1, -2);
            this.f59397b0 = new FrameLayout(context);
            this.f59395a8.addView(this.f59397b0, new LinearLayout.LayoutParams(0, -2, 1.0f));
            if (textInputLayout.getEditText() != null) {
                m214352a1();
            }
        }
        if (i == 0 || i == 1) {
            this.f59397b0.setVisibility(0);
            this.f59397b0.addView(appCompatTextView);
        } else {
            this.f59395a8.addView(appCompatTextView, new LinearLayout.LayoutParams(-2, -2));
        }
        this.f59395a8.setVisibility(0);
        this.f59396a9++;
    }

    /* renamed from: a1 */
    public final void m214352a1() {
        if (this.f59395a8 != null) {
            TextInputLayout textInputLayout = this.f59394a7;
            if (textInputLayout.getEditText() != null) {
                EditText editText = textInputLayout.getEditText();
                Context context = this.f59393a6;
                boolean zM214445e1 = AbstractC1117qo.m214445e1(context);
                LinearLayout linearLayout = this.f59395a8;
                int i = R$dimen.material_helper_text_font_1_3_padding_horizontal;
                WeakHashMap weakHashMap = xa1.f61054a0;
                int iM212906a5 = ga1.m212906a5(editText);
                if (zM214445e1) {
                    iM212906a5 = context.getResources().getDimensionPixelSize(i);
                }
                int i2 = R$dimen.material_helper_text_font_1_3_padding_top;
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.material_helper_text_default_padding_top);
                if (zM214445e1) {
                    dimensionPixelSize = context.getResources().getDimensionPixelSize(i2);
                }
                int i3 = R$dimen.material_helper_text_font_1_3_padding_horizontal;
                int iM212905a4 = ga1.m212905a4(editText);
                if (zM214445e1) {
                    iM212905a4 = context.getResources().getDimensionPixelSize(i3);
                }
                ga1.m212911b0(linearLayout, iM212906a5, dimensionPixelSize, iM212905a4, 0);
            }
        }
    }

    /* renamed from: a2 */
    public final void m214353a2() {
        AnimatorSet animatorSet = this.f59398b1;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
    }

    /* renamed from: a3 */
    public final void m214354a3(ArrayList arrayList, boolean z, AppCompatTextView appCompatTextView, int i, int i2, int i3) {
        if (appCompatTextView == null || !z) {
            return;
        }
        if (i == i3 || i == i2) {
            boolean z2 = i3 == i;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(appCompatTextView, (Property<AppCompatTextView, Float>) View.ALPHA, z2 ? 1.0f : 0.0f);
            int i4 = this.f59389a2;
            objectAnimatorOfFloat.setDuration(z2 ? this.f59388a1 : i4);
            objectAnimatorOfFloat.setInterpolator(z2 ? this.f59391a4 : this.f59392a5);
            if (i == i3 && i2 != 0) {
                objectAnimatorOfFloat.setStartDelay(i4);
            }
            arrayList.add(objectAnimatorOfFloat);
            if (i3 != i || i2 == 0) {
                return;
            }
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(appCompatTextView, (Property<AppCompatTextView, Float>) View.TRANSLATION_Y, -this.f59399b2, 0.0f);
            objectAnimatorOfFloat2.setDuration(this.f59387a0);
            objectAnimatorOfFloat2.setInterpolator(this.f59390a3);
            objectAnimatorOfFloat2.setStartDelay(i4);
            arrayList.add(objectAnimatorOfFloat2);
        }
    }

    /* renamed from: a4 */
    public final TextView m214355a4(int i) {
        if (i == 1) {
            return this.f59404b7;
        }
        if (i != 2) {
            return null;
        }
        return this.f59411c4;
    }

    /* renamed from: a5 */
    public final void m214356a5() {
        this.f59402b5 = null;
        m214353a2();
        if (this.f59400b3 == 1) {
            if (!this.f59410c3 || TextUtils.isEmpty(this.f59409c2)) {
                this.f59401b4 = 0;
            } else {
                this.f59401b4 = 2;
            }
        }
        m214359a8(this.f59400b3, this.f59401b4, m214358a7(this.f59404b7, ""));
    }

    /* renamed from: a6 */
    public final void m214357a6(AppCompatTextView appCompatTextView, int i) {
        FrameLayout frameLayout;
        LinearLayout linearLayout = this.f59395a8;
        if (linearLayout == null) {
            return;
        }
        if ((i == 0 || i == 1) && (frameLayout = this.f59397b0) != null) {
            frameLayout.removeView(appCompatTextView);
        } else {
            linearLayout.removeView(appCompatTextView);
        }
        int i2 = this.f59396a9 - 1;
        this.f59396a9 = i2;
        LinearLayout linearLayout2 = this.f59395a8;
        if (i2 == 0) {
            linearLayout2.setVisibility(8);
        }
    }

    /* renamed from: a7 */
    public final boolean m214358a7(AppCompatTextView appCompatTextView, CharSequence charSequence) {
        WeakHashMap weakHashMap = xa1.f61054a0;
        TextInputLayout textInputLayout = this.f59394a7;
        if (ia1.m213142a2(textInputLayout) && textInputLayout.isEnabled()) {
            return (this.f59401b4 == this.f59400b3 && appCompatTextView != null && TextUtils.equals(appCompatTextView.getText(), charSequence)) ? false : true;
        }
        return false;
    }

    /* renamed from: a8 */
    public final void m214359a8(int i, int i2, boolean z) {
        TextView textViewM214355a4;
        TextView textViewM214355a42;
        if (i == i2) {
            return;
        }
        if (z) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.f59398b1 = animatorSet;
            ArrayList arrayList = new ArrayList();
            m214354a3(arrayList, this.f59410c3, this.f59411c4, 2, i, i2);
            m214354a3(arrayList, this.f59403b6, this.f59404b7, 1, i, i2);
            t60.m214718e2(animatorSet, arrayList);
            animatorSet.addListener(new o50(this, i2, m214355a4(i), i, m214355a4(i2)));
            animatorSet.start();
        } else if (i != i2) {
            if (i2 != 0 && (textViewM214355a42 = m214355a4(i2)) != null) {
                textViewM214355a42.setVisibility(0);
                textViewM214355a42.setAlpha(1.0f);
            }
            if (i != 0 && (textViewM214355a4 = m214355a4(i)) != null) {
                textViewM214355a4.setVisibility(4);
                if (i == 1) {
                    textViewM214355a4.setText((CharSequence) null);
                }
            }
            this.f59400b3 = i2;
        }
        TextInputLayout textInputLayout = this.f59394a7;
        textInputLayout.m211154b6();
        textInputLayout.m211157b9(z, false);
        textInputLayout.m211160c2();
    }
}
