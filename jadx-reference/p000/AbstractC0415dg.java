package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dg */
/* loaded from: classes2.dex */
public abstract class AbstractC0415dg extends FrameLayout {

    /* renamed from: a8 */
    public static final ViewOnTouchListenerC0414df f55722a8 = new ViewOnTouchListenerC0414df(0);

    /* renamed from: a0 */
    public final a01 f55723a0;

    /* renamed from: a1 */
    public int f55724a1;

    /* renamed from: a2 */
    public final float f55725a2;

    /* renamed from: a3 */
    public final float f55726a3;

    /* renamed from: a4 */
    public final int f55727a4;

    /* renamed from: a5 */
    public final int f55728a5;

    /* renamed from: a6 */
    public ColorStateList f55729a6;

    /* renamed from: a7 */
    public PorterDuff.Mode f55730a7;

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractC0415dg(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        GradientDrawable gradientDrawable;
        super(ee0.m212666a0(context, attributeSet, 0, 0), attributeSet);
        Context context2 = getContext();
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.SnackbarLayout_elevation)) {
            float dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SnackbarLayout_elevation, 0);
            WeakHashMap weakHashMap = xa1.f61054a0;
            la1.m213819b8(this, dimensionPixelSize);
        }
        this.f55724a1 = typedArrayObtainStyledAttributes.getInt(R$styleable.SnackbarLayout_animationMode, 0);
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.SnackbarLayout_shapeAppearance) || typedArrayObtainStyledAttributes.hasValue(R$styleable.SnackbarLayout_shapeAppearanceOverlay)) {
            this.f55723a0 = a01.m14a3(context2, attributeSet, 0, 0).m215177a0();
        }
        this.f55725a2 = typedArrayObtainStyledAttributes.getFloat(R$styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1.0f);
        setBackgroundTintList(AbstractC1117qo.m214428c4(context2, typedArrayObtainStyledAttributes, R$styleable.SnackbarLayout_backgroundTint));
        setBackgroundTintMode(AbstractC1117qo.m214456f3(typedArrayObtainStyledAttributes.getInt(R$styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
        this.f55726a3 = typedArrayObtainStyledAttributes.getFloat(R$styleable.SnackbarLayout_actionTextColorAlpha, 1.0f);
        this.f55727a4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SnackbarLayout_android_maxWidth, -1);
        this.f55728a5 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SnackbarLayout_maxActionInlineWidth, -1);
        typedArrayObtainStyledAttributes.recycle();
        setOnTouchListener(f55722a8);
        setFocusable(true);
        if (getBackground() == null) {
            int iM213577c4 = kj1.m213577c4(kj1.m213568b5(this, R$attr.colorSurface), getBackgroundOverlayColorAlpha(), kj1.m213568b5(this, R$attr.colorOnSurface));
            a01 a01Var = this.f55723a0;
            if (a01Var != null) {
                int i = AbstractC0416dh.f55749a0;
                ce0 ce0Var = new ce0(a01Var);
                ce0Var.m210840b2(ColorStateList.valueOf(iM213577c4));
                gradientDrawable = ce0Var;
            } else {
                Resources resources = getResources();
                int i2 = AbstractC0416dh.f55749a0;
                float dimension = resources.getDimension(R$dimen.mtrl_snackbar_background_corner_radius);
                GradientDrawable gradientDrawable2 = new GradientDrawable();
                gradientDrawable2.setShape(0);
                gradientDrawable2.setCornerRadius(dimension);
                gradientDrawable2.setColor(iM213577c4);
                gradientDrawable = gradientDrawable2;
            }
            ColorStateList colorStateList = this.f55729a6;
            if (colorStateList != null) {
                AbstractC1270tr.m214774a7(gradientDrawable, colorStateList);
            }
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            fa1.m212779b6(this, gradientDrawable);
        }
    }

    public float getActionTextColorAlpha() {
        return this.f55726a3;
    }

    public int getAnimationMode() {
        return this.f55724a1;
    }

    public float getBackgroundOverlayColorAlpha() {
        return this.f55725a2;
    }

    public int getMaxInlineActionWidth() {
        return this.f55728a5;
    }

    public int getMaxWidth() {
        return this.f55727a4;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        WeakHashMap weakHashMap = xa1.f61054a0;
        ja1.m213282a2(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int i3 = this.f55727a4;
        if (i3 <= 0 || getMeasuredWidth() <= i3) {
            return;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), i2);
    }

    public void setAnimationMode(int i) {
        this.f55724a1 = i;
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable != null && this.f55729a6 != null) {
            drawable = drawable.mutate();
            AbstractC1270tr.m214774a7(drawable, this.f55729a6);
            AbstractC1270tr.m214775a8(drawable, this.f55730a7);
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundTintList(ColorStateList colorStateList) {
        this.f55729a6 = colorStateList;
        if (getBackground() != null) {
            Drawable drawableMutate = getBackground().mutate();
            AbstractC1270tr.m214774a7(drawableMutate, colorStateList);
            AbstractC1270tr.m214775a8(drawableMutate, this.f55730a7);
            if (drawableMutate != getBackground()) {
                super.setBackgroundDrawable(drawableMutate);
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.f55730a7 = mode;
        if (getBackground() != null) {
            Drawable drawableMutate = getBackground().mutate();
            AbstractC1270tr.m214775a8(drawableMutate, mode);
            if (drawableMutate != getBackground()) {
                super.setBackgroundDrawable(drawableMutate);
            }
        }
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        super.setLayoutParams(layoutParams);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            new Rect(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        }
    }

    @Override // android.view.View
    public void setOnClickListener(View.OnClickListener onClickListener) {
        setOnTouchListener(onClickListener != null ? null : f55722a8);
        super.setOnClickListener(onClickListener);
    }

    private void setBaseTransientBottomBar(AbstractC0416dh abstractC0416dh) {
    }
}
