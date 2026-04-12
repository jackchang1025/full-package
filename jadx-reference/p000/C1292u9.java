package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.R$styleable;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: u9 */
/* loaded from: classes.dex */
public final class C1292u9 {

    /* renamed from: a0 */
    public int f60343a0;

    /* renamed from: a1 */
    public final Object f60344a1;

    /* renamed from: a2 */
    public final Object f60345a2;

    /* renamed from: a3 */
    public Object f60346a3;

    /* renamed from: a4 */
    public Object f60347a4;

    /* renamed from: a5 */
    public Object f60348a5;

    public C1292u9(View view) {
        this.f60343a0 = -1;
        this.f60344a1 = view;
        this.f60345a2 = C1398x1.m215095a0();
    }

    /* renamed from: a1 */
    public static C1292u9 m214819a1(Context context, int i) throws Resources.NotFoundException {
        b81.m210566a6(i != 0, "Cannot create a CalendarItemStyle with a styleResId of 0");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, R$styleable.MaterialCalendarItem);
        Rect rect = new Rect(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R$styleable.MaterialCalendarItem_android_insetLeft, 0), typedArrayObtainStyledAttributes.getDimensionPixelOffset(R$styleable.MaterialCalendarItem_android_insetTop, 0), typedArrayObtainStyledAttributes.getDimensionPixelOffset(R$styleable.MaterialCalendarItem_android_insetRight, 0), typedArrayObtainStyledAttributes.getDimensionPixelOffset(R$styleable.MaterialCalendarItem_android_insetBottom, 0));
        ColorStateList colorStateListM214428c4 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.MaterialCalendarItem_itemFillColor);
        ColorStateList colorStateListM214428c42 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.MaterialCalendarItem_itemTextColor);
        ColorStateList colorStateListM214428c43 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.MaterialCalendarItem_itemStrokeColor);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.MaterialCalendarItem_itemStrokeWidth, 0);
        a01 a01VarM215177a0 = a01.m11a0(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendarItem_itemShapeAppearance, 0), typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendarItem_itemShapeAppearanceOverlay, 0)).m215177a0();
        typedArrayObtainStyledAttributes.recycle();
        return new C1292u9(colorStateListM214428c4, colorStateListM214428c42, colorStateListM214428c43, dimensionPixelSize, a01VarM215177a0, rect);
    }

    /* renamed from: a0 */
    public void m214820a0() {
        View view = (View) this.f60344a1;
        Drawable background = view.getBackground();
        if (background != null) {
            if (((t61) this.f60346a3) != null) {
                if (((t61) this.f60348a5) == null) {
                    this.f60348a5 = new t61();
                }
                t61 t61Var = (t61) this.f60348a5;
                t61Var.f60174a0 = null;
                t61Var.f60177a3 = false;
                t61Var.f60175a1 = null;
                t61Var.f60176a2 = false;
                WeakHashMap weakHashMap = xa1.f61054a0;
                ColorStateList colorStateListM213807a6 = la1.m213807a6(view);
                if (colorStateListM213807a6 != null) {
                    t61Var.f60177a3 = true;
                    t61Var.f60174a0 = colorStateListM213807a6;
                }
                PorterDuff.Mode modeM213808a7 = la1.m213808a7(view);
                if (modeM213808a7 != null) {
                    t61Var.f60176a2 = true;
                    t61Var.f60175a1 = modeM213808a7;
                }
                if (t61Var.f60177a3 || t61Var.f60176a2) {
                    C1398x1.m215098a4(background, t61Var, view.getDrawableState());
                    return;
                }
            }
            t61 t61Var2 = (t61) this.f60347a4;
            if (t61Var2 != null) {
                C1398x1.m215098a4(background, t61Var2, view.getDrawableState());
                return;
            }
            t61 t61Var3 = (t61) this.f60346a3;
            if (t61Var3 != null) {
                C1398x1.m215098a4(background, t61Var3, view.getDrawableState());
            }
        }
    }

    /* renamed from: a2 */
    public ColorStateList m214821a2() {
        t61 t61Var = (t61) this.f60347a4;
        if (t61Var != null) {
            return t61Var.f60174a0;
        }
        return null;
    }

    /* renamed from: a3 */
    public PorterDuff.Mode m214822a3() {
        t61 t61Var = (t61) this.f60347a4;
        if (t61Var != null) {
            return t61Var.f60175a1;
        }
        return null;
    }

    /* renamed from: a4 */
    public void m214823a4(AttributeSet attributeSet, int i) {
        ColorStateList colorStateListM214663a5;
        View view = (View) this.f60344a1;
        pg1 pg1VarM214255d2 = pg1.m214255d2(view.getContext(), attributeSet, androidx.appcompat.R$styleable.ViewBackgroundHelper, i);
        TypedArray typedArray = (TypedArray) pg1VarM214255d2.f59230a2;
        View view2 = (View) this.f60344a1;
        xa1.m215151b3(view2, view2.getContext(), androidx.appcompat.R$styleable.ViewBackgroundHelper, attributeSet, (TypedArray) pg1VarM214255d2.f59230a2, i);
        try {
            if (typedArray.hasValue(androidx.appcompat.R$styleable.ViewBackgroundHelper_android_background)) {
                this.f60343a0 = typedArray.getResourceId(androidx.appcompat.R$styleable.ViewBackgroundHelper_android_background, -1);
                C1398x1 c1398x1 = (C1398x1) this.f60345a2;
                Context context = view.getContext();
                int i2 = this.f60343a0;
                synchronized (c1398x1) {
                    colorStateListM214663a5 = c1398x1.f60990a0.m214663a5(context, i2);
                }
                if (colorStateListM214663a5 != null) {
                    m214826a7(colorStateListM214663a5);
                }
            }
            if (typedArray.hasValue(androidx.appcompat.R$styleable.ViewBackgroundHelper_backgroundTint)) {
                la1.m213817b6(view, pg1VarM214255d2.m214276c0(androidx.appcompat.R$styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (typedArray.hasValue(androidx.appcompat.R$styleable.ViewBackgroundHelper_backgroundTintMode)) {
                la1.m213818b7(view, AbstractC1274tv.m214792a2(typedArray.getInt(androidx.appcompat.R$styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
            pg1VarM214255d2.m214288d4();
        } catch (Throwable th) {
            pg1VarM214255d2.m214288d4();
            throw th;
        }
    }

    /* renamed from: a5 */
    public void m214824a5() {
        this.f60343a0 = -1;
        m214826a7(null);
        m214820a0();
    }

    /* renamed from: a6 */
    public void m214825a6(int i) {
        ColorStateList colorStateListM214663a5;
        this.f60343a0 = i;
        C1398x1 c1398x1 = (C1398x1) this.f60345a2;
        if (c1398x1 != null) {
            Context context = ((View) this.f60344a1).getContext();
            synchronized (c1398x1) {
                colorStateListM214663a5 = c1398x1.f60990a0.m214663a5(context, i);
            }
        } else {
            colorStateListM214663a5 = null;
        }
        m214826a7(colorStateListM214663a5);
        m214820a0();
    }

    /* renamed from: a7 */
    public void m214826a7(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (((t61) this.f60346a3) == null) {
                this.f60346a3 = new t61();
            }
            t61 t61Var = (t61) this.f60346a3;
            t61Var.f60174a0 = colorStateList;
            t61Var.f60177a3 = true;
        } else {
            this.f60346a3 = null;
        }
        m214820a0();
    }

    /* renamed from: a8 */
    public void m214827a8(ColorStateList colorStateList) {
        if (((t61) this.f60347a4) == null) {
            this.f60347a4 = new t61();
        }
        t61 t61Var = (t61) this.f60347a4;
        t61Var.f60174a0 = colorStateList;
        t61Var.f60177a3 = true;
        m214820a0();
    }

    /* renamed from: a9 */
    public void m214828a9(PorterDuff.Mode mode) {
        if (((t61) this.f60347a4) == null) {
            this.f60347a4 = new t61();
        }
        t61 t61Var = (t61) this.f60347a4;
        t61Var.f60175a1 = mode;
        t61Var.f60176a2 = true;
        m214820a0();
    }

    /* renamed from: b0 */
    public void m214829b0(TextView textView) {
        ColorStateList colorStateList = (ColorStateList) this.f60345a2;
        ce0 ce0Var = new ce0();
        ce0 ce0Var2 = new ce0();
        a01 a01Var = (a01) this.f60348a5;
        ce0Var.setShapeAppearanceModel(a01Var);
        ce0Var2.setShapeAppearanceModel(a01Var);
        ce0Var.m210840b2((ColorStateList) this.f60346a3);
        float f = this.f60343a0;
        ColorStateList colorStateList2 = (ColorStateList) this.f60347a4;
        ce0Var.m210846b8(f);
        ce0Var.m210845b7(colorStateList2);
        textView.setTextColor(colorStateList);
        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList.withAlpha(30), ce0Var, ce0Var2);
        Rect rect = (Rect) this.f60344a1;
        InsetDrawable insetDrawable = new InsetDrawable((Drawable) rippleDrawable, rect.left, rect.top, rect.right, rect.bottom);
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212779b6(textView, insetDrawable);
    }

    public C1292u9(ColorStateList colorStateList, ColorStateList colorStateList2, ColorStateList colorStateList3, int i, a01 a01Var, Rect rect) {
        b81.m210567a7(rect.left);
        b81.m210567a7(rect.top);
        b81.m210567a7(rect.right);
        b81.m210567a7(rect.bottom);
        this.f60344a1 = rect;
        this.f60345a2 = colorStateList2;
        this.f60346a3 = colorStateList;
        this.f60347a4 = colorStateList3;
        this.f60343a0 = i;
        this.f60348a5 = a01Var;
    }
}
