package p000;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class w11 extends LinearLayout {

    /* renamed from: a0 */
    public final TextInputLayout f60740a0;

    /* renamed from: a1 */
    public final AppCompatTextView f60741a1;

    /* renamed from: a2 */
    public CharSequence f60742a2;

    /* renamed from: a3 */
    public final CheckableImageButton f60743a3;

    /* renamed from: a4 */
    public ColorStateList f60744a4;

    /* renamed from: a5 */
    public PorterDuff.Mode f60745a5;

    /* renamed from: a6 */
    public int f60746a6;

    /* renamed from: a7 */
    public ImageView.ScaleType f60747a7;

    /* renamed from: a8 */
    public View.OnLongClickListener f60748a8;

    /* renamed from: a9 */
    public boolean f60749a9;

    public w11(TextInputLayout textInputLayout, pg1 pg1Var) {
        CharSequence text;
        super(textInputLayout.getContext());
        this.f60740a0 = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388611));
        CheckableImageButton checkableImageButton = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R$layout.design_text_input_start_icon, (ViewGroup) this, false);
        this.f60743a3 = checkableImageButton;
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), null);
        this.f60741a1 = appCompatTextView;
        if (AbstractC1117qo.m214445e1(getContext())) {
            bd0.m210670a6((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams(), 0);
        }
        View.OnLongClickListener onLongClickListener = this.f60748a8;
        checkableImageButton.setOnClickListener(null);
        b81.m210595e8(checkableImageButton, onLongClickListener);
        this.f60748a8 = null;
        checkableImageButton.setOnLongClickListener(null);
        b81.m210595e8(checkableImageButton, null);
        int i = R$styleable.TextInputLayout_startIconTint;
        TypedArray typedArray = (TypedArray) pg1Var.f59230a2;
        if (typedArray.hasValue(i)) {
            this.f60744a4 = AbstractC1117qo.m214427c3(getContext(), pg1Var, R$styleable.TextInputLayout_startIconTint);
        }
        if (typedArray.hasValue(R$styleable.TextInputLayout_startIconTintMode)) {
            this.f60745a5 = AbstractC1117qo.m214456f3(typedArray.getInt(R$styleable.TextInputLayout_startIconTintMode, -1), null);
        }
        if (typedArray.hasValue(R$styleable.TextInputLayout_startIconDrawable)) {
            m214977a0(pg1Var.m214277c1(R$styleable.TextInputLayout_startIconDrawable));
            if (typedArray.hasValue(R$styleable.TextInputLayout_startIconContentDescription) && checkableImageButton.getContentDescription() != (text = typedArray.getText(R$styleable.TextInputLayout_startIconContentDescription))) {
                checkableImageButton.setContentDescription(text);
            }
            checkableImageButton.setCheckable(typedArray.getBoolean(R$styleable.TextInputLayout_startIconCheckable, true));
        }
        int dimensionPixelSize = typedArray.getDimensionPixelSize(R$styleable.TextInputLayout_startIconMinSize, getResources().getDimensionPixelSize(R$dimen.mtrl_min_touch_target_size));
        if (dimensionPixelSize < 0) {
            throw new IllegalArgumentException("startIconSize cannot be less than 0");
        }
        if (dimensionPixelSize != this.f60746a6) {
            this.f60746a6 = dimensionPixelSize;
            checkableImageButton.setMinimumWidth(dimensionPixelSize);
            checkableImageButton.setMinimumHeight(dimensionPixelSize);
        }
        if (typedArray.hasValue(R$styleable.TextInputLayout_startIconScaleType)) {
            ImageView.ScaleType scaleTypeM210569a9 = b81.m210569a9(typedArray.getInt(R$styleable.TextInputLayout_startIconScaleType, -1));
            this.f60747a7 = scaleTypeM210569a9;
            checkableImageButton.setScaleType(scaleTypeM210569a9);
        }
        appCompatTextView.setVisibility(8);
        appCompatTextView.setId(R$id.textinput_prefix_text);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        WeakHashMap weakHashMap = xa1.f61054a0;
        ia1.m213145a5(appCompatTextView, 1);
        appCompatTextView.setTextAppearance(typedArray.getResourceId(R$styleable.TextInputLayout_prefixTextAppearance, 0));
        if (typedArray.hasValue(R$styleable.TextInputLayout_prefixTextColor)) {
            appCompatTextView.setTextColor(pg1Var.m214276c0(R$styleable.TextInputLayout_prefixTextColor));
        }
        CharSequence text2 = typedArray.getText(R$styleable.TextInputLayout_prefixText);
        this.f60742a2 = TextUtils.isEmpty(text2) ? null : text2;
        appCompatTextView.setText(text2);
        m214980a3();
        addView(checkableImageButton);
        addView(appCompatTextView);
    }

    /* renamed from: a0 */
    public final void m214977a0(Drawable drawable) {
        CheckableImageButton checkableImageButton = this.f60743a3;
        checkableImageButton.setImageDrawable(drawable);
        if (drawable != null) {
            ColorStateList colorStateList = this.f60744a4;
            PorterDuff.Mode mode = this.f60745a5;
            TextInputLayout textInputLayout = this.f60740a0;
            b81.m210561a1(textInputLayout, checkableImageButton, colorStateList, mode);
            m214978a1(true);
            b81.m210591e2(textInputLayout, checkableImageButton, this.f60744a4);
            return;
        }
        m214978a1(false);
        View.OnLongClickListener onLongClickListener = this.f60748a8;
        checkableImageButton.setOnClickListener(null);
        b81.m210595e8(checkableImageButton, onLongClickListener);
        this.f60748a8 = null;
        checkableImageButton.setOnLongClickListener(null);
        b81.m210595e8(checkableImageButton, null);
        if (checkableImageButton.getContentDescription() != null) {
            checkableImageButton.setContentDescription(null);
        }
    }

    /* renamed from: a1 */
    public final void m214978a1(boolean z) {
        CheckableImageButton checkableImageButton = this.f60743a3;
        if ((checkableImageButton.getVisibility() == 0) != z) {
            checkableImageButton.setVisibility(z ? 0 : 8);
            m214979a2();
            m214980a3();
        }
    }

    /* renamed from: a2 */
    public final void m214979a2() throws Resources.NotFoundException {
        int iM212906a5;
        EditText editText = this.f60740a0.f49936a3;
        if (editText == null) {
            return;
        }
        if (this.f60743a3.getVisibility() == 0) {
            iM212906a5 = 0;
        } else {
            WeakHashMap weakHashMap = xa1.f61054a0;
            iM212906a5 = ga1.m212906a5(editText);
        }
        int compoundPaddingTop = editText.getCompoundPaddingTop();
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.material_input_text_to_prefix_suffix_padding);
        int compoundPaddingBottom = editText.getCompoundPaddingBottom();
        WeakHashMap weakHashMap2 = xa1.f61054a0;
        ga1.m212911b0(this.f60741a1, iM212906a5, compoundPaddingTop, dimensionPixelSize, compoundPaddingBottom);
    }

    /* renamed from: a3 */
    public final void m214980a3() {
        int i = (this.f60742a2 == null || this.f60749a9) ? 8 : 0;
        setVisibility((this.f60743a3.getVisibility() == 0 || i == 0) ? 0 : 8);
        this.f60741a1.setVisibility(i);
        this.f60740a0.m211153b5();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) throws Resources.NotFoundException {
        super.onMeasure(i, i2);
        m214979a2();
    }
}
