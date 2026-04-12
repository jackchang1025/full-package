package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.TypedValue;
import com.google.android.material.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class r51 {

    /* renamed from: a0 */
    public final ColorStateList f59625a0;

    /* renamed from: a1 */
    public final String f59626a1;

    /* renamed from: a2 */
    public final int f59627a2;

    /* renamed from: a3 */
    public final int f59628a3;

    /* renamed from: a4 */
    public final float f59629a4;

    /* renamed from: a5 */
    public final float f59630a5;

    /* renamed from: a6 */
    public final float f59631a6;

    /* renamed from: a7 */
    public final boolean f59632a7;

    /* renamed from: a8 */
    public final float f59633a8;

    /* renamed from: a9 */
    public ColorStateList f59634a9;

    /* renamed from: b0 */
    public float f59635b0;

    /* renamed from: b1 */
    public final int f59636b1;

    /* renamed from: b2 */
    public boolean f59637b2 = false;

    /* renamed from: b3 */
    public Typeface f59638b3;

    public r51(Context context, int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, R$styleable.TextAppearance);
        this.f59635b0 = typedArrayObtainStyledAttributes.getDimension(R$styleable.TextAppearance_android_textSize, 0.0f);
        this.f59634a9 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_textColor);
        AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_textColorHint);
        AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_textColorLink);
        this.f59627a2 = typedArrayObtainStyledAttributes.getInt(R$styleable.TextAppearance_android_textStyle, 0);
        this.f59628a3 = typedArrayObtainStyledAttributes.getInt(R$styleable.TextAppearance_android_typeface, 1);
        int i2 = R$styleable.TextAppearance_fontFamily;
        i2 = typedArrayObtainStyledAttributes.hasValue(i2) ? i2 : R$styleable.TextAppearance_android_fontFamily;
        this.f59636b1 = typedArrayObtainStyledAttributes.getResourceId(i2, 0);
        this.f59626a1 = typedArrayObtainStyledAttributes.getString(i2);
        typedArrayObtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
        this.f59625a0 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.TextAppearance_android_shadowColor);
        this.f59629a4 = typedArrayObtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowDx, 0.0f);
        this.f59630a5 = typedArrayObtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowDy, 0.0f);
        this.f59631a6 = typedArrayObtainStyledAttributes.getFloat(R$styleable.TextAppearance_android_shadowRadius, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(i, R$styleable.MaterialTextAppearance);
        this.f59632a7 = typedArrayObtainStyledAttributes2.hasValue(R$styleable.MaterialTextAppearance_android_letterSpacing);
        this.f59633a8 = typedArrayObtainStyledAttributes2.getFloat(R$styleable.MaterialTextAppearance_android_letterSpacing, 0.0f);
        typedArrayObtainStyledAttributes2.recycle();
    }

    /* renamed from: a0 */
    public final void m214484a0() {
        String str;
        Typeface typeface = this.f59638b3;
        int i = this.f59627a2;
        if (typeface == null && (str = this.f59626a1) != null) {
            this.f59638b3 = Typeface.create(str, i);
        }
        if (this.f59638b3 == null) {
            int i2 = this.f59628a3;
            if (i2 == 1) {
                this.f59638b3 = Typeface.SANS_SERIF;
            } else if (i2 == 2) {
                this.f59638b3 = Typeface.SERIF;
            } else if (i2 != 3) {
                this.f59638b3 = Typeface.DEFAULT;
            } else {
                this.f59638b3 = Typeface.MONOSPACE;
            }
            this.f59638b3 = Typeface.create(this.f59638b3, i);
        }
    }

    /* renamed from: a1 */
    public final Typeface m214485a1(Context context) {
        if (this.f59637b2) {
            return this.f59638b3;
        }
        if (!context.isRestricted()) {
            try {
                Typeface typefaceM215304a0 = yr0.m215304a0(context, this.f59636b1);
                this.f59638b3 = typefaceM215304a0;
                if (typefaceM215304a0 != null) {
                    this.f59638b3 = Typeface.create(typefaceM215304a0, this.f59627a2);
                }
            } catch (Resources.NotFoundException | UnsupportedOperationException | Exception unused) {
            }
        }
        m214484a0();
        this.f59637b2 = true;
        return this.f59638b3;
    }

    /* renamed from: a2 */
    public final void m214486a2(Context context, cq0 cq0Var) {
        if (m214487a3(context)) {
            m214485a1(context);
        } else {
            m214484a0();
        }
        int i = this.f59636b1;
        if (i == 0) {
            this.f59637b2 = true;
        }
        if (this.f59637b2) {
            cq0Var.mo212510c8(this.f59638b3, true);
            return;
        }
        try {
            p51 p51Var = new p51(this, cq0Var);
            ThreadLocal threadLocal = yr0.f61364a0;
            if (context.isRestricted()) {
                p51Var.m212500a0(-4);
            } else {
                yr0.m215305a1(context, i, new TypedValue(), 0, p51Var, false, false);
            }
        } catch (Resources.NotFoundException unused) {
            this.f59637b2 = true;
            cq0Var.mo212508c6(1);
        } catch (Exception unused2) {
            this.f59637b2 = true;
            cq0Var.mo212508c6(-3);
        }
    }

    /* renamed from: a3 */
    public final boolean m214487a3(Context context) throws Resources.NotFoundException {
        Typeface typefaceM215305a1 = null;
        int i = this.f59636b1;
        if (i != 0) {
            ThreadLocal threadLocal = yr0.f61364a0;
            if (!context.isRestricted()) {
                typefaceM215305a1 = yr0.m215305a1(context, i, new TypedValue(), 0, null, false, true);
            }
        }
        return typefaceM215305a1 != null;
    }

    /* renamed from: a4 */
    public final void m214488a4(Context context, TextPaint textPaint, cq0 cq0Var) {
        m214489a5(context, textPaint, cq0Var);
        ColorStateList colorStateList = this.f59634a9;
        textPaint.setColor(colorStateList != null ? colorStateList.getColorForState(textPaint.drawableState, colorStateList.getDefaultColor()) : -16777216);
        ColorStateList colorStateList2 = this.f59625a0;
        textPaint.setShadowLayer(this.f59631a6, this.f59629a4, this.f59630a5, colorStateList2 != null ? colorStateList2.getColorForState(textPaint.drawableState, colorStateList2.getDefaultColor()) : 0);
    }

    /* renamed from: a5 */
    public final void m214489a5(Context context, TextPaint textPaint, cq0 cq0Var) {
        if (m214487a3(context)) {
            m214490a6(context, textPaint, m214485a1(context));
            return;
        }
        m214484a0();
        m214490a6(context, textPaint, this.f59638b3);
        m214486a2(context, new q51(this, context, textPaint, cq0Var));
    }

    /* renamed from: a6 */
    public final void m214490a6(Context context, TextPaint textPaint, Typeface typeface) {
        Typeface typefaceM214454f0 = AbstractC1117qo.m214454f0(context.getResources().getConfiguration(), typeface);
        if (typefaceM214454f0 != null) {
            typeface = typefaceM214454f0;
        }
        textPaint.setTypeface(typeface);
        int i = (~typeface.getStyle()) & this.f59627a2;
        textPaint.setFakeBoldText((i & 1) != 0);
        textPaint.setTextSkewX((i & 2) != 0 ? -0.25f : 0.0f);
        textPaint.setTextSize(this.f59635b0);
        if (this.f59632a7) {
            textPaint.setLetterSpacing(this.f59633a8);
        }
    }
}
