package p000;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$string;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xf */
/* loaded from: classes2.dex */
public final class C1415xf extends LinearLayout {

    /* renamed from: a0 */
    public final TextInputLayout f61079a0;

    /* renamed from: a1 */
    public final FrameLayout f61080a1;

    /* renamed from: a2 */
    public final CheckableImageButton f61081a2;

    /* renamed from: a3 */
    public ColorStateList f61082a3;

    /* renamed from: a4 */
    public PorterDuff.Mode f61083a4;

    /* renamed from: a5 */
    public View.OnLongClickListener f61084a5;

    /* renamed from: a6 */
    public final CheckableImageButton f61085a6;

    /* renamed from: a7 */
    public final C1414xe f61086a7;

    /* renamed from: a8 */
    public int f61087a8;

    /* renamed from: a9 */
    public final LinkedHashSet f61088a9;

    /* renamed from: b0 */
    public ColorStateList f61089b0;

    /* renamed from: b1 */
    public PorterDuff.Mode f61090b1;

    /* renamed from: b2 */
    public int f61091b2;

    /* renamed from: b3 */
    public ImageView.ScaleType f61092b3;

    /* renamed from: b4 */
    public View.OnLongClickListener f61093b4;

    /* renamed from: b5 */
    public CharSequence f61094b5;

    /* renamed from: b6 */
    public final AppCompatTextView f61095b6;

    /* renamed from: b7 */
    public boolean f61096b7;

    /* renamed from: b8 */
    public EditText f61097b8;

    /* renamed from: b9 */
    public final AccessibilityManager f61098b9;

    /* renamed from: c0 */
    public InterfaceC0702j1 f61099c0;

    /* renamed from: c1 */
    public final C1409xc f61100c1;

    public C1415xf(TextInputLayout textInputLayout, pg1 pg1Var) {
        CharSequence text;
        super(textInputLayout.getContext());
        this.f61087a8 = 0;
        this.f61088a9 = new LinkedHashSet();
        this.f61100c1 = new C1409xc(this);
        C1410xd c1410xd = new C1410xd(this);
        this.f61098b9 = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.f61079a0 = textInputLayout;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388613));
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.f61080a1 = frameLayout;
        frameLayout.setVisibility(8);
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        CheckableImageButton checkableImageButtonM215156a0 = m215156a0(this, layoutInflaterFrom, R$id.text_input_error_icon);
        this.f61081a2 = checkableImageButtonM215156a0;
        CheckableImageButton checkableImageButtonM215156a02 = m215156a0(frameLayout, layoutInflaterFrom, R$id.text_input_end_icon);
        this.f61085a6 = checkableImageButtonM215156a02;
        this.f61086a7 = new C1414xe(this, pg1Var);
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), null);
        this.f61095b6 = appCompatTextView;
        int i = R$styleable.TextInputLayout_errorIconTint;
        TypedArray typedArray = (TypedArray) pg1Var.f59230a2;
        if (typedArray.hasValue(i)) {
            this.f61082a3 = AbstractC1117qo.m214427c3(getContext(), pg1Var, R$styleable.TextInputLayout_errorIconTint);
        }
        if (typedArray.hasValue(R$styleable.TextInputLayout_errorIconTintMode)) {
            this.f61083a4 = AbstractC1117qo.m214456f3(typedArray.getInt(R$styleable.TextInputLayout_errorIconTintMode, -1), null);
        }
        if (typedArray.hasValue(R$styleable.TextInputLayout_errorIconDrawable)) {
            m215163a7(pg1Var.m214277c1(R$styleable.TextInputLayout_errorIconDrawable));
        }
        checkableImageButtonM215156a0.setContentDescription(getResources().getText(R$string.error_icon_content_description));
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212781b8(checkableImageButtonM215156a0, 2);
        checkableImageButtonM215156a0.setClickable(false);
        checkableImageButtonM215156a0.setPressable(false);
        checkableImageButtonM215156a0.setFocusable(false);
        if (!typedArray.hasValue(R$styleable.TextInputLayout_passwordToggleEnabled)) {
            if (typedArray.hasValue(R$styleable.TextInputLayout_endIconTint)) {
                this.f61089b0 = AbstractC1117qo.m214427c3(getContext(), pg1Var, R$styleable.TextInputLayout_endIconTint);
            }
            if (typedArray.hasValue(R$styleable.TextInputLayout_endIconTintMode)) {
                this.f61090b1 = AbstractC1117qo.m214456f3(typedArray.getInt(R$styleable.TextInputLayout_endIconTintMode, -1), null);
            }
        }
        int i2 = 1;
        if (typedArray.hasValue(R$styleable.TextInputLayout_endIconMode)) {
            m215161a5(typedArray.getInt(R$styleable.TextInputLayout_endIconMode, 0));
            if (typedArray.hasValue(R$styleable.TextInputLayout_endIconContentDescription) && checkableImageButtonM215156a02.getContentDescription() != (text = typedArray.getText(R$styleable.TextInputLayout_endIconContentDescription))) {
                checkableImageButtonM215156a02.setContentDescription(text);
            }
            checkableImageButtonM215156a02.setCheckable(typedArray.getBoolean(R$styleable.TextInputLayout_endIconCheckable, true));
        } else if (typedArray.hasValue(R$styleable.TextInputLayout_passwordToggleEnabled)) {
            if (typedArray.hasValue(R$styleable.TextInputLayout_passwordToggleTint)) {
                this.f61089b0 = AbstractC1117qo.m214427c3(getContext(), pg1Var, R$styleable.TextInputLayout_passwordToggleTint);
            }
            if (typedArray.hasValue(R$styleable.TextInputLayout_passwordToggleTintMode)) {
                this.f61090b1 = AbstractC1117qo.m214456f3(typedArray.getInt(R$styleable.TextInputLayout_passwordToggleTintMode, -1), null);
            }
            m215161a5(typedArray.getBoolean(R$styleable.TextInputLayout_passwordToggleEnabled, false) ? 1 : 0);
            CharSequence text2 = typedArray.getText(R$styleable.TextInputLayout_passwordToggleContentDescription);
            if (checkableImageButtonM215156a02.getContentDescription() != text2) {
                checkableImageButtonM215156a02.setContentDescription(text2);
            }
        }
        int dimensionPixelSize = typedArray.getDimensionPixelSize(R$styleable.TextInputLayout_endIconMinSize, getResources().getDimensionPixelSize(R$dimen.mtrl_min_touch_target_size));
        if (dimensionPixelSize < 0) {
            throw new IllegalArgumentException("endIconSize cannot be less than 0");
        }
        if (dimensionPixelSize != this.f61091b2) {
            this.f61091b2 = dimensionPixelSize;
            checkableImageButtonM215156a02.setMinimumWidth(dimensionPixelSize);
            checkableImageButtonM215156a02.setMinimumHeight(dimensionPixelSize);
            checkableImageButtonM215156a0.setMinimumWidth(dimensionPixelSize);
            checkableImageButtonM215156a0.setMinimumHeight(dimensionPixelSize);
        }
        if (typedArray.hasValue(R$styleable.TextInputLayout_endIconScaleType)) {
            ImageView.ScaleType scaleTypeM210569a9 = b81.m210569a9(typedArray.getInt(R$styleable.TextInputLayout_endIconScaleType, -1));
            this.f61092b3 = scaleTypeM210569a9;
            checkableImageButtonM215156a02.setScaleType(scaleTypeM210569a9);
            checkableImageButtonM215156a0.setScaleType(scaleTypeM210569a9);
        }
        appCompatTextView.setVisibility(8);
        appCompatTextView.setId(R$id.textinput_suffix_text);
        appCompatTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 80.0f));
        ia1.m213145a5(appCompatTextView, 1);
        appCompatTextView.setTextAppearance(typedArray.getResourceId(R$styleable.TextInputLayout_suffixTextAppearance, 0));
        if (typedArray.hasValue(R$styleable.TextInputLayout_suffixTextColor)) {
            appCompatTextView.setTextColor(pg1Var.m214276c0(R$styleable.TextInputLayout_suffixTextColor));
        }
        CharSequence text3 = typedArray.getText(R$styleable.TextInputLayout_suffixText);
        this.f61094b5 = TextUtils.isEmpty(text3) ? null : text3;
        appCompatTextView.setText(text3);
        m215168b2();
        frameLayout.addView(checkableImageButtonM215156a02);
        addView(appCompatTextView);
        addView(frameLayout);
        addView(checkableImageButtonM215156a0);
        textInputLayout.f49984f1.add(c1410xd);
        if (textInputLayout.f49936a3 != null) {
            c1410xd.m215155a0(textInputLayout);
        }
        addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC0539gk(i2, this));
    }

    /* renamed from: a0 */
    public final CheckableImageButton m215156a0(ViewGroup viewGroup, LayoutInflater layoutInflater, int i) {
        CheckableImageButton checkableImageButton = (CheckableImageButton) layoutInflater.inflate(R$layout.design_text_input_end_icon, viewGroup, false);
        checkableImageButton.setId(i);
        if (AbstractC1117qo.m214445e1(getContext())) {
            bd0.m210671a7((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams(), 0);
        }
        return checkableImageButton;
    }

    /* renamed from: a1 */
    public final AbstractC1416xg m215157a1() {
        AbstractC1416xg c0952oh;
        int i = this.f61087a8;
        C1414xe c1414xe = this.f61086a7;
        SparseArray sparseArray = c1414xe.f61073a0;
        AbstractC1416xg abstractC1416xg = (AbstractC1416xg) sparseArray.get(i);
        if (abstractC1416xg != null) {
            return abstractC1416xg;
        }
        C1415xf c1415xf = c1414xe.f61074a1;
        if (i == -1) {
            c0952oh = new C0952oh(c1415xf, 0);
        } else if (i == 0) {
            c0952oh = new C0952oh(c1415xf, 1);
        } else if (i == 1) {
            c0952oh = new om0(c1415xf, c1414xe.f61076a3);
        } else if (i == 2) {
            c0952oh = new C0697ix(c1415xf);
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(tz0.m214802a2(i, "Invalid end icon mode: "));
            }
            c0952oh = new C1309uq(c1415xf);
        }
        sparseArray.append(i, c0952oh);
        return c0952oh;
    }

    /* renamed from: a2 */
    public final boolean m215158a2() {
        return this.f61080a1.getVisibility() == 0 && this.f61085a6.getVisibility() == 0;
    }

    /* renamed from: a3 */
    public final boolean m215159a3() {
        return this.f61081a2.getVisibility() == 0;
    }

    /* renamed from: a4 */
    public final void m215160a4(boolean z) {
        boolean z2;
        boolean zIsActivated;
        boolean z3;
        AbstractC1416xg abstractC1416xgM215157a1 = m215157a1();
        boolean zMo214232a9 = abstractC1416xgM215157a1.mo214232a9();
        CheckableImageButton checkableImageButton = this.f61085a6;
        boolean z4 = true;
        if (!zMo214232a9 || (z3 = checkableImageButton.f49538a3) == abstractC1416xgM215157a1.mo214233b0()) {
            z2 = false;
        } else {
            checkableImageButton.setChecked(!z3);
            z2 = true;
        }
        if (!(abstractC1416xgM215157a1 instanceof C1309uq) || (zIsActivated = checkableImageButton.isActivated()) == ((C1309uq) abstractC1416xgM215157a1).f60497b1) {
            z4 = z2;
        } else {
            checkableImageButton.setActivated(!zIsActivated);
        }
        if (z || z4) {
            b81.m210591e2(this.f61079a0, checkableImageButton, this.f61089b0);
        }
    }

    /* renamed from: a5 */
    public final void m215161a5(int i) {
        if (this.f61087a8 == i) {
            return;
        }
        AbstractC1416xg abstractC1416xgM215157a1 = m215157a1();
        InterfaceC0702j1 interfaceC0702j1 = this.f61099c0;
        AccessibilityManager accessibilityManager = this.f61098b9;
        if (interfaceC0702j1 != null && accessibilityManager != null) {
            AbstractC0701j0.m213202a1(accessibilityManager, interfaceC0702j1);
        }
        this.f61099c0 = null;
        abstractC1416xgM215157a1.mo213198b7();
        this.f61087a8 = i;
        Iterator it = this.f61088a9.iterator();
        if (it.hasNext()) {
            throw AbstractC0003a2.m25a6(it);
        }
        m215162a6(i != 0);
        AbstractC1416xg abstractC1416xgM215157a12 = m215157a1();
        int iMo213191a3 = this.f61086a7.f61075a2;
        if (iMo213191a3 == 0) {
            iMo213191a3 = abstractC1416xgM215157a12.mo213191a3();
        }
        Drawable drawableM210576b7 = iMo213191a3 != 0 ? b81.m210576b7(getContext(), iMo213191a3) : null;
        CheckableImageButton checkableImageButton = this.f61085a6;
        checkableImageButton.setImageDrawable(drawableM210576b7);
        TextInputLayout textInputLayout = this.f61079a0;
        if (drawableM210576b7 != null) {
            b81.m210561a1(textInputLayout, checkableImageButton, this.f61089b0, this.f61090b1);
            b81.m210591e2(textInputLayout, checkableImageButton, this.f61089b0);
        }
        int iMo213190a2 = abstractC1416xgM215157a12.mo213190a2();
        CharSequence text = iMo213190a2 != 0 ? getResources().getText(iMo213190a2) : null;
        if (checkableImageButton.getContentDescription() != text) {
            checkableImageButton.setContentDescription(text);
        }
        checkableImageButton.setCheckable(abstractC1416xgM215157a12.mo214232a9());
        if (!abstractC1416xgM215157a12.mo214855a8(textInputLayout.getBoxBackgroundMode())) {
            throw new IllegalStateException("The current box background mode " + textInputLayout.getBoxBackgroundMode() + " is not supported by the end icon mode " + i);
        }
        abstractC1416xgM215157a12.mo213197b6();
        InterfaceC0702j1 interfaceC0702j1Mo214854a7 = abstractC1416xgM215157a12.mo214854a7();
        this.f61099c0 = interfaceC0702j1Mo214854a7;
        if (interfaceC0702j1Mo214854a7 != null && accessibilityManager != null) {
            WeakHashMap weakHashMap = xa1.f61054a0;
            if (ia1.m213141a1(this)) {
                AbstractC0701j0.m213201a0(accessibilityManager, this.f61099c0);
            }
        }
        View.OnClickListener onClickListenerMo213193a5 = abstractC1416xgM215157a12.mo213193a5();
        View.OnLongClickListener onLongClickListener = this.f61093b4;
        checkableImageButton.setOnClickListener(onClickListenerMo213193a5);
        b81.m210595e8(checkableImageButton, onLongClickListener);
        EditText editText = this.f61097b8;
        if (editText != null) {
            abstractC1416xgM215157a12.mo213195b1(editText);
            m215164a8(abstractC1416xgM215157a12);
        }
        b81.m210561a1(textInputLayout, checkableImageButton, this.f61089b0, this.f61090b1);
        m215160a4(true);
    }

    /* renamed from: a6 */
    public final void m215162a6(boolean z) {
        if (m215158a2() != z) {
            this.f61085a6.setVisibility(z ? 0 : 8);
            m215165a9();
            m215167b1();
            this.f61079a0.m211153b5();
        }
    }

    /* renamed from: a7 */
    public final void m215163a7(Drawable drawable) {
        CheckableImageButton checkableImageButton = this.f61081a2;
        checkableImageButton.setImageDrawable(drawable);
        m215166b0();
        b81.m210561a1(this.f61079a0, checkableImageButton, this.f61082a3, this.f61083a4);
    }

    /* renamed from: a8 */
    public final void m215164a8(AbstractC1416xg abstractC1416xg) {
        if (this.f61097b8 == null) {
            return;
        }
        if (abstractC1416xg.mo213192a4() != null) {
            this.f61097b8.setOnFocusChangeListener(abstractC1416xg.mo213192a4());
        }
        if (abstractC1416xg.mo213194a6() != null) {
            this.f61085a6.setOnFocusChangeListener(abstractC1416xg.mo213194a6());
        }
    }

    /* renamed from: a9 */
    public final void m215165a9() {
        this.f61080a1.setVisibility((this.f61085a6.getVisibility() != 0 || m215159a3()) ? 8 : 0);
        setVisibility((m215158a2() || m215159a3() || !((this.f61094b5 == null || this.f61096b7) ? 8 : false)) ? 0 : 8);
    }

    /* renamed from: b0 */
    public final void m215166b0() {
        CheckableImageButton checkableImageButton = this.f61081a2;
        Drawable drawable = checkableImageButton.getDrawable();
        TextInputLayout textInputLayout = this.f61079a0;
        checkableImageButton.setVisibility((drawable != null && textInputLayout.f49942a9.f59403b6 && textInputLayout.m211150b2()) ? 0 : 8);
        m215165a9();
        m215167b1();
        if (this.f61087a8 != 0) {
            return;
        }
        textInputLayout.m211153b5();
    }

    /* renamed from: b1 */
    public final void m215167b1() {
        int iM212905a4;
        TextInputLayout textInputLayout = this.f61079a0;
        if (textInputLayout.f49936a3 == null) {
            return;
        }
        if (m215158a2() || m215159a3()) {
            iM212905a4 = 0;
        } else {
            EditText editText = textInputLayout.f49936a3;
            WeakHashMap weakHashMap = xa1.f61054a0;
            iM212905a4 = ga1.m212905a4(editText);
        }
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R$dimen.material_input_text_to_prefix_suffix_padding);
        int paddingTop = textInputLayout.f49936a3.getPaddingTop();
        int paddingBottom = textInputLayout.f49936a3.getPaddingBottom();
        WeakHashMap weakHashMap2 = xa1.f61054a0;
        ga1.m212911b0(this.f61095b6, dimensionPixelSize, paddingTop, iM212905a4, paddingBottom);
    }

    /* renamed from: b2 */
    public final void m215168b2() {
        AppCompatTextView appCompatTextView = this.f61095b6;
        int visibility = appCompatTextView.getVisibility();
        int i = (this.f61094b5 == null || this.f61096b7) ? 8 : 0;
        if (visibility != i) {
            m215157a1().mo213196b4(i == 0);
        }
        m215165a9();
        appCompatTextView.setVisibility(i);
        this.f61079a0.m211153b5();
    }
}
