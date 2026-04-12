package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: z4 */
/* loaded from: classes.dex */
public final class C1504z4 {

    /* renamed from: a0 */
    public final TextView f61442a0;

    /* renamed from: a1 */
    public t61 f61443a1;

    /* renamed from: a2 */
    public t61 f61444a2;

    /* renamed from: a3 */
    public t61 f61445a3;

    /* renamed from: a4 */
    public t61 f61446a4;

    /* renamed from: a5 */
    public t61 f61447a5;

    /* renamed from: a6 */
    public t61 f61448a6;

    /* renamed from: a7 */
    public t61 f61449a7;

    /* renamed from: a8 */
    public final C0024ad f61450a8;

    /* renamed from: a9 */
    public int f61451a9 = 0;

    /* renamed from: b0 */
    public int f61452b0 = -1;

    /* renamed from: b1 */
    public Typeface f61453b1;

    /* renamed from: b2 */
    public boolean f61454b2;

    public C1504z4(TextView textView) {
        this.f61442a0 = textView;
        this.f61450a8 = new C0024ad(textView);
    }

    /* renamed from: a2 */
    public static t61 m215344a2(Context context, C1398x1 c1398x1, int i) {
        ColorStateList colorStateListM214663a5;
        synchronized (c1398x1) {
            colorStateListM214663a5 = c1398x1.f60990a0.m214663a5(context, i);
        }
        if (colorStateListM214663a5 == null) {
            return null;
        }
        t61 t61Var = new t61();
        t61Var.f60177a3 = true;
        t61Var.f60174a0 = colorStateListM214663a5;
        return t61Var;
    }

    /* renamed from: a7 */
    public static void m215345a7(EditorInfo editorInfo, InputConnection inputConnection, TextView textView) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 30 || inputConnection == null) {
            return;
        }
        CharSequence text = textView.getText();
        if (i >= 30) {
            AbstractC1354vx.m214971a0(editorInfo, text);
            return;
        }
        text.getClass();
        if (i >= 30) {
            AbstractC1354vx.m214971a0(editorInfo, text);
            return;
        }
        int i2 = editorInfo.initialSelStart;
        int i3 = editorInfo.initialSelEnd;
        int i4 = i2 > i3 ? i3 : i2;
        if (i2 <= i3) {
            i2 = i3;
        }
        int length = text.length();
        if (i4 < 0 || i2 > length) {
            kg1.m213543f2(editorInfo, null, 0, 0);
            return;
        }
        int i5 = editorInfo.inputType & 4095;
        if (i5 == 129 || i5 == 225 || i5 == 18) {
            kg1.m213543f2(editorInfo, null, 0, 0);
            return;
        }
        if (length <= 2048) {
            kg1.m213543f2(editorInfo, text, i4, i2);
            return;
        }
        int i6 = i2 - i4;
        int i7 = i6 > 1024 ? 0 : i6;
        int i8 = 2048 - i7;
        int iMin = Math.min(text.length() - i2, i8 - Math.min(i4, (int) (i8 * 0.8d)));
        int iMin2 = Math.min(i4, i8 - iMin);
        int i9 = i4 - iMin2;
        if (Character.isLowSurrogate(text.charAt(i9))) {
            i9++;
            iMin2--;
        }
        if (Character.isHighSurrogate(text.charAt((i2 + iMin) - 1))) {
            iMin--;
        }
        int i10 = iMin2 + i7;
        kg1.m213543f2(editorInfo, i7 != i6 ? TextUtils.concat(text.subSequence(i9, i9 + iMin2), text.subSequence(i2, iMin + i2)) : text.subSequence(i9, i10 + iMin + i9), iMin2, i10);
    }

    /* renamed from: a0 */
    public final void m215346a0(Drawable drawable, t61 t61Var) {
        if (drawable == null || t61Var == null) {
            return;
        }
        C1398x1.m215098a4(drawable, t61Var, this.f61442a0.getDrawableState());
    }

    /* renamed from: a1 */
    public final void m215347a1() {
        t61 t61Var = this.f61443a1;
        TextView textView = this.f61442a0;
        if (t61Var != null || this.f61444a2 != null || this.f61445a3 != null || this.f61446a4 != null) {
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            m215346a0(compoundDrawables[0], this.f61443a1);
            m215346a0(compoundDrawables[1], this.f61444a2);
            m215346a0(compoundDrawables[2], this.f61445a3);
            m215346a0(compoundDrawables[3], this.f61446a4);
        }
        if (this.f61447a5 == null && this.f61448a6 == null) {
            return;
        }
        Drawable[] drawableArrM215328a0 = AbstractC1500z0.m215328a0(textView);
        m215346a0(drawableArrM215328a0[0], this.f61447a5);
        m215346a0(drawableArrM215328a0[2], this.f61448a6);
    }

    /* renamed from: a3 */
    public final ColorStateList m215348a3() {
        t61 t61Var = this.f61449a7;
        if (t61Var != null) {
            return t61Var.f60174a0;
        }
        return null;
    }

    /* renamed from: a4 */
    public final PorterDuff.Mode m215349a4() {
        t61 t61Var = this.f61449a7;
        if (t61Var != null) {
            return t61Var.f60175a1;
        }
        return null;
    }

    /* renamed from: a5 */
    public final void m215350a5(AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        boolean z;
        boolean z2;
        String string;
        String string2;
        float f;
        int i2;
        ColorStateList colorStateList;
        int resourceId;
        int resourceId2;
        TextView textView = this.f61442a0;
        Context context = textView.getContext();
        C1398x1 c1398x1M215095a0 = C1398x1.m215095a0();
        pg1 pg1VarM214255d2 = pg1.m214255d2(context, attributeSet, R$styleable.AppCompatTextHelper, i);
        xa1.m215151b3(textView, textView.getContext(), R$styleable.AppCompatTextHelper, attributeSet, (TypedArray) pg1VarM214255d2.f59230a2, i);
        int i3 = R$styleable.AppCompatTextHelper_android_textAppearance;
        TypedArray typedArray = (TypedArray) pg1VarM214255d2.f59230a2;
        int resourceId3 = typedArray.getResourceId(i3, -1);
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.f61443a1 = m215344a2(context, c1398x1M215095a0, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableTop)) {
            this.f61444a2 = m215344a2(context, c1398x1M215095a0, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableRight)) {
            this.f61445a3 = m215344a2(context, c1398x1M215095a0, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.f61446a4 = m215344a2(context, c1398x1M215095a0, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        int i4 = Build.VERSION.SDK_INT;
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableStart)) {
            this.f61447a5 = m215344a2(context, c1398x1M215095a0, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.f61448a6 = m215344a2(context, c1398x1M215095a0, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableEnd, 0));
        }
        pg1VarM214255d2.m214288d4();
        boolean z3 = textView.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId3 != -1) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(resourceId3, R$styleable.TextAppearance);
            pg1 pg1Var = new pg1(context, typedArrayObtainStyledAttributes);
            if (z3 || !typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_textAllCaps)) {
                z = false;
                z2 = false;
            } else {
                z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
                z2 = true;
            }
            m215357b3(context, pg1Var);
            string2 = typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_textLocale) ? typedArrayObtainStyledAttributes.getString(R$styleable.TextAppearance_textLocale) : null;
            string = (i4 < 26 || !typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_fontVariationSettings)) ? null : typedArrayObtainStyledAttributes.getString(R$styleable.TextAppearance_fontVariationSettings);
            pg1Var.m214288d4();
        } else {
            z = false;
            z2 = false;
            string = null;
            string2 = null;
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.TextAppearance, i, 0);
        pg1 pg1Var2 = new pg1(context, typedArrayObtainStyledAttributes2);
        if (!z3 && typedArrayObtainStyledAttributes2.hasValue(R$styleable.TextAppearance_textAllCaps)) {
            z = typedArrayObtainStyledAttributes2.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
            z2 = true;
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.TextAppearance_textLocale)) {
            string2 = typedArrayObtainStyledAttributes2.getString(R$styleable.TextAppearance_textLocale);
        }
        if (i4 >= 26 && typedArrayObtainStyledAttributes2.hasValue(R$styleable.TextAppearance_fontVariationSettings)) {
            string = typedArrayObtainStyledAttributes2.getString(R$styleable.TextAppearance_fontVariationSettings);
        }
        if (i4 >= 28 && typedArrayObtainStyledAttributes2.hasValue(R$styleable.TextAppearance_android_textSize) && typedArrayObtainStyledAttributes2.getDimensionPixelSize(R$styleable.TextAppearance_android_textSize, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        m215357b3(context, pg1Var2);
        pg1Var2.m214288d4();
        if (!z3 && z2) {
            textView.setAllCaps(z);
        }
        Typeface typeface = this.f61453b1;
        if (typeface != null) {
            if (this.f61452b0 == -1) {
                textView.setTypeface(typeface, this.f61451a9);
            } else {
                textView.setTypeface(typeface);
            }
        }
        if (string != null) {
            AbstractC1502z2.m215337a3(textView, string);
        }
        if (string2 != null) {
            AbstractC1501z1.m215332a1(textView, AbstractC1501z1.m215331a0(string2));
        }
        C0024ad c0024ad = this.f61450a8;
        Context context2 = c0024ad.f43621a9;
        TypedArray typedArrayObtainStyledAttributes3 = context2.obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView, i, 0);
        TextView textView2 = c0024ad.f43620a8;
        xa1.m215151b3(textView2, textView2.getContext(), R$styleable.AppCompatTextView, attributeSet, typedArrayObtainStyledAttributes3, i);
        if (typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeTextType)) {
            c0024ad.f43612a0 = typedArrayObtainStyledAttributes3.getInt(R$styleable.AppCompatTextView_autoSizeTextType, 0);
        }
        float dimension = typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeStepGranularity) ? typedArrayObtainStyledAttributes3.getDimension(R$styleable.AppCompatTextView_autoSizeStepGranularity, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeMinTextSize) ? typedArrayObtainStyledAttributes3.getDimension(R$styleable.AppCompatTextView_autoSizeMinTextSize, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeMaxTextSize) ? typedArrayObtainStyledAttributes3.getDimension(R$styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0f) : -1.0f;
        if (!typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizePresetSizes) || (resourceId2 = typedArrayObtainStyledAttributes3.getResourceId(R$styleable.AppCompatTextView_autoSizePresetSizes, 0)) <= 0) {
            f = -1.0f;
        } else {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes3.getResources().obtainTypedArray(resourceId2);
            int length = typedArrayObtainTypedArray.length();
            int[] iArr = new int[length];
            if (length > 0) {
                f = -1.0f;
                for (int i5 = 0; i5 < length; i5++) {
                    iArr[i5] = typedArrayObtainTypedArray.getDimensionPixelSize(i5, -1);
                }
                c0024ad.f43617a5 = C0024ad.m209780a1(iArr);
                c0024ad.m209787a7();
            } else {
                f = -1.0f;
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes3.recycle();
        if (!c0024ad.m209788a8()) {
            c0024ad.f43612a0 = 0;
        } else if (c0024ad.f43612a0 == 1) {
            if (!c0024ad.f43618a6) {
                DisplayMetrics displayMetrics = context2.getResources().getDisplayMetrics();
                if (dimension2 == f) {
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                }
                if (dimension3 == f) {
                    dimension3 = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                }
                if (dimension == f) {
                    dimension = 1.0f;
                }
                c0024ad.m209789a9(dimension2, dimension3, dimension);
            }
            c0024ad.m209786a6();
        }
        if (id1.f56870a1 && c0024ad.f43612a0 != 0) {
            int[] iArr2 = c0024ad.f43617a5;
            if (iArr2.length > 0) {
                if (AbstractC1502z2.m215334a0(textView) != f) {
                    AbstractC1502z2.m215335a1(textView, Math.round(c0024ad.f43615a3), Math.round(c0024ad.f43616a4), Math.round(c0024ad.f43614a2), 0);
                } else {
                    AbstractC1502z2.m215336a2(textView, iArr2, 0);
                }
            }
        }
        TypedArray typedArrayObtainStyledAttributes4 = context.obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView);
        int resourceId4 = typedArrayObtainStyledAttributes4.getResourceId(R$styleable.AppCompatTextView_drawableLeftCompat, -1);
        Drawable drawableM215099a1 = resourceId4 != -1 ? c1398x1M215095a0.m215099a1(context, resourceId4) : null;
        int resourceId5 = typedArrayObtainStyledAttributes4.getResourceId(R$styleable.AppCompatTextView_drawableTopCompat, -1);
        Drawable drawableM215099a12 = resourceId5 != -1 ? c1398x1M215095a0.m215099a1(context, resourceId5) : null;
        int resourceId6 = typedArrayObtainStyledAttributes4.getResourceId(R$styleable.AppCompatTextView_drawableRightCompat, -1);
        Drawable drawableM215099a13 = resourceId6 != -1 ? c1398x1M215095a0.m215099a1(context, resourceId6) : null;
        int resourceId7 = typedArrayObtainStyledAttributes4.getResourceId(R$styleable.AppCompatTextView_drawableBottomCompat, -1);
        Drawable drawableM215099a14 = resourceId7 != -1 ? c1398x1M215095a0.m215099a1(context, resourceId7) : null;
        int resourceId8 = typedArrayObtainStyledAttributes4.getResourceId(R$styleable.AppCompatTextView_drawableStartCompat, -1);
        Drawable drawableM215099a15 = resourceId8 != -1 ? c1398x1M215095a0.m215099a1(context, resourceId8) : null;
        int resourceId9 = typedArrayObtainStyledAttributes4.getResourceId(R$styleable.AppCompatTextView_drawableEndCompat, -1);
        Drawable drawableM215099a16 = resourceId9 != -1 ? c1398x1M215095a0.m215099a1(context, resourceId9) : null;
        if (drawableM215099a15 != null || drawableM215099a16 != null) {
            Drawable[] drawableArrM215328a0 = AbstractC1500z0.m215328a0(textView);
            if (drawableM215099a15 == null) {
                drawableM215099a15 = drawableArrM215328a0[0];
            }
            if (drawableM215099a12 == null) {
                drawableM215099a12 = drawableArrM215328a0[1];
            }
            if (drawableM215099a16 == null) {
                drawableM215099a16 = drawableArrM215328a0[2];
            }
            if (drawableM215099a14 == null) {
                drawableM215099a14 = drawableArrM215328a0[3];
            }
            AbstractC1500z0.m215329a1(textView, drawableM215099a15, drawableM215099a12, drawableM215099a16, drawableM215099a14);
        } else if (drawableM215099a1 != null || drawableM215099a12 != null || drawableM215099a13 != null || drawableM215099a14 != null) {
            Drawable[] drawableArrM215328a02 = AbstractC1500z0.m215328a0(textView);
            Drawable drawable = drawableArrM215328a02[0];
            if (drawable == null && drawableArrM215328a02[2] == null) {
                Drawable[] compoundDrawables = textView.getCompoundDrawables();
                if (drawableM215099a1 == null) {
                    drawableM215099a1 = compoundDrawables[0];
                }
                if (drawableM215099a12 == null) {
                    drawableM215099a12 = compoundDrawables[1];
                }
                if (drawableM215099a13 == null) {
                    drawableM215099a13 = compoundDrawables[2];
                }
                if (drawableM215099a14 == null) {
                    drawableM215099a14 = compoundDrawables[3];
                }
                textView.setCompoundDrawablesWithIntrinsicBounds(drawableM215099a1, drawableM215099a12, drawableM215099a13, drawableM215099a14);
            } else {
                if (drawableM215099a12 == null) {
                    drawableM215099a12 = drawableArrM215328a02[1];
                }
                Drawable drawable2 = drawableArrM215328a02[2];
                if (drawableM215099a14 == null) {
                    drawableM215099a14 = drawableArrM215328a02[3];
                }
                AbstractC1500z0.m215329a1(textView, drawable, drawableM215099a12, drawable2, drawableM215099a14);
            }
        }
        if (typedArrayObtainStyledAttributes4.hasValue(R$styleable.AppCompatTextView_drawableTint)) {
            int i6 = R$styleable.AppCompatTextView_drawableTint;
            if (!typedArrayObtainStyledAttributes4.hasValue(i6) || (resourceId = typedArrayObtainStyledAttributes4.getResourceId(i6, 0)) == 0 || (colorStateList = AbstractC1117qo.m214426c2(context, resourceId)) == null) {
                colorStateList = typedArrayObtainStyledAttributes4.getColorStateList(i6);
            }
            d61.m212555a5(textView, colorStateList);
        }
        if (typedArrayObtainStyledAttributes4.hasValue(R$styleable.AppCompatTextView_drawableTintMode)) {
            i2 = -1;
            d61.m212556a6(textView, AbstractC1274tv.m214792a2(typedArrayObtainStyledAttributes4.getInt(R$styleable.AppCompatTextView_drawableTintMode, -1), null));
        } else {
            i2 = -1;
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes4.getDimensionPixelSize(R$styleable.AppCompatTextView_firstBaselineToTopHeight, i2);
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(R$styleable.AppCompatTextView_lastBaselineToBottomHeight, i2);
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(R$styleable.AppCompatTextView_lineHeight, i2);
        typedArrayObtainStyledAttributes4.recycle();
        if (dimensionPixelSize != i2) {
            kg1.m213539e8(textView, dimensionPixelSize);
        }
        if (dimensionPixelSize2 != i2) {
            kg1.m213541f0(textView, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != i2) {
            b81.m210567a7(dimensionPixelSize3);
            if (dimensionPixelSize3 != textView.getPaint().getFontMetricsInt(null)) {
                textView.setLineSpacing(dimensionPixelSize3 - r1, 1.0f);
            }
        }
    }

    /* renamed from: a6 */
    public final void m215351a6(Context context, int i) throws Resources.NotFoundException {
        String string;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, R$styleable.TextAppearance);
        pg1 pg1Var = new pg1(context, typedArrayObtainStyledAttributes);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_textAllCaps);
        TextView textView = this.f61442a0;
        if (zHasValue) {
            textView.setAllCaps(typedArrayObtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false));
        }
        int i2 = Build.VERSION.SDK_INT;
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_android_textSize) && typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.TextAppearance_android_textSize, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        m215357b3(context, pg1Var);
        if (i2 >= 26 && typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_fontVariationSettings) && (string = typedArrayObtainStyledAttributes.getString(R$styleable.TextAppearance_fontVariationSettings)) != null) {
            AbstractC1502z2.m215337a3(textView, string);
        }
        pg1Var.m214288d4();
        Typeface typeface = this.f61453b1;
        if (typeface != null) {
            textView.setTypeface(typeface, this.f61451a9);
        }
    }

    /* renamed from: a8 */
    public final void m215352a8(int i, int i2, int i3, int i4) {
        C0024ad c0024ad = this.f61450a8;
        if (c0024ad.m209788a8()) {
            DisplayMetrics displayMetrics = c0024ad.f43621a9.getResources().getDisplayMetrics();
            c0024ad.m209789a9(TypedValue.applyDimension(i4, i, displayMetrics), TypedValue.applyDimension(i4, i2, displayMetrics), TypedValue.applyDimension(i4, i3, displayMetrics));
            if (c0024ad.m209786a6()) {
                c0024ad.m209782a0();
            }
        }
    }

    /* renamed from: a9 */
    public final void m215353a9(int[] iArr, int i) {
        C0024ad c0024ad = this.f61450a8;
        if (c0024ad.m209788a8()) {
            int length = iArr.length;
            if (length > 0) {
                int[] iArrCopyOf = new int[length];
                if (i == 0) {
                    iArrCopyOf = Arrays.copyOf(iArr, length);
                } else {
                    DisplayMetrics displayMetrics = c0024ad.f43621a9.getResources().getDisplayMetrics();
                    for (int i2 = 0; i2 < length; i2++) {
                        iArrCopyOf[i2] = Math.round(TypedValue.applyDimension(i, iArr[i2], displayMetrics));
                    }
                }
                c0024ad.f43617a5 = C0024ad.m209780a1(iArrCopyOf);
                if (!c0024ad.m209787a7()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(iArr));
                }
            } else {
                c0024ad.f43618a6 = false;
            }
            if (c0024ad.m209786a6()) {
                c0024ad.m209782a0();
            }
        }
    }

    /* renamed from: b0 */
    public final void m215354b0(int i) {
        C0024ad c0024ad = this.f61450a8;
        if (c0024ad.m209788a8()) {
            if (i == 0) {
                c0024ad.f43612a0 = 0;
                c0024ad.f43615a3 = -1.0f;
                c0024ad.f43616a4 = -1.0f;
                c0024ad.f43614a2 = -1.0f;
                c0024ad.f43617a5 = new int[0];
                c0024ad.f43613a1 = false;
                return;
            }
            if (i != 1) {
                throw new IllegalArgumentException(tz0.m214802a2(i, "Unknown auto-size text type: "));
            }
            DisplayMetrics displayMetrics = c0024ad.f43621a9.getResources().getDisplayMetrics();
            c0024ad.m209789a9(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
            if (c0024ad.m209786a6()) {
                c0024ad.m209782a0();
            }
        }
    }

    /* renamed from: b1 */
    public final void m215355b1(ColorStateList colorStateList) {
        if (this.f61449a7 == null) {
            this.f61449a7 = new t61();
        }
        t61 t61Var = this.f61449a7;
        t61Var.f60174a0 = colorStateList;
        t61Var.f60177a3 = colorStateList != null;
        this.f61443a1 = t61Var;
        this.f61444a2 = t61Var;
        this.f61445a3 = t61Var;
        this.f61446a4 = t61Var;
        this.f61447a5 = t61Var;
        this.f61448a6 = t61Var;
    }

    /* renamed from: b2 */
    public final void m215356b2(PorterDuff.Mode mode) {
        if (this.f61449a7 == null) {
            this.f61449a7 = new t61();
        }
        t61 t61Var = this.f61449a7;
        t61Var.f60175a1 = mode;
        t61Var.f60176a2 = mode != null;
        this.f61443a1 = t61Var;
        this.f61444a2 = t61Var;
        this.f61445a3 = t61Var;
        this.f61446a4 = t61Var;
        this.f61447a5 = t61Var;
        this.f61448a6 = t61Var;
    }

    /* renamed from: b3 */
    public final void m215357b3(Context context, pg1 pg1Var) {
        String string;
        int i = R$styleable.TextAppearance_android_textStyle;
        int i2 = this.f61451a9;
        TypedArray typedArray = (TypedArray) pg1Var.f59230a2;
        this.f61451a9 = typedArray.getInt(i, i2);
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 28) {
            int i4 = typedArray.getInt(R$styleable.TextAppearance_android_textFontWeight, -1);
            this.f61452b0 = i4;
            if (i4 != -1) {
                this.f61451a9 &= 2;
            }
        }
        if (!typedArray.hasValue(R$styleable.TextAppearance_android_fontFamily) && !typedArray.hasValue(R$styleable.TextAppearance_fontFamily)) {
            if (typedArray.hasValue(R$styleable.TextAppearance_android_typeface)) {
                this.f61454b2 = false;
                int i5 = typedArray.getInt(R$styleable.TextAppearance_android_typeface, 1);
                if (i5 == 1) {
                    this.f61453b1 = Typeface.SANS_SERIF;
                    return;
                } else if (i5 == 2) {
                    this.f61453b1 = Typeface.SERIF;
                    return;
                } else {
                    if (i5 != 3) {
                        return;
                    }
                    this.f61453b1 = Typeface.MONOSPACE;
                    return;
                }
            }
            return;
        }
        this.f61453b1 = null;
        int i6 = typedArray.hasValue(R$styleable.TextAppearance_fontFamily) ? R$styleable.TextAppearance_fontFamily : R$styleable.TextAppearance_android_fontFamily;
        int i7 = this.f61452b0;
        int i8 = this.f61451a9;
        if (!context.isRestricted()) {
            try {
                Typeface typefaceM214279c3 = pg1Var.m214279c3(i6, this.f61451a9, new C1449y9(this, i7, i8, new WeakReference(this.f61442a0)));
                if (typefaceM214279c3 != null) {
                    if (i3 < 28 || this.f61452b0 == -1) {
                        this.f61453b1 = typefaceM214279c3;
                    } else {
                        this.f61453b1 = AbstractC1503z3.m215338a0(Typeface.create(typefaceM214279c3, 0), this.f61452b0, (this.f61451a9 & 2) != 0);
                    }
                }
                this.f61454b2 = this.f61453b1 == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.f61453b1 != null || (string = typedArray.getString(i6)) == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 28 || this.f61452b0 == -1) {
            this.f61453b1 = Typeface.create(string, this.f61451a9);
        } else {
            this.f61453b1 = AbstractC1503z3.m215338a0(Typeface.create(string, 0), this.f61452b0, (this.f61451a9 & 2) != 0);
        }
    }
}
