package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.google.android.material.chip.Chip;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hq */
/* loaded from: classes2.dex */
public final class C0590hq extends ce0 implements Drawable.Callback, t51 {

    /* renamed from: i3 */
    public static final int[] f56687i3 = {R.attr.state_enabled};

    /* renamed from: i4 */
    public static final ShapeDrawable f56688i4 = new ShapeDrawable(new OvalShape());

    /* renamed from: c3 */
    public ColorStateList f56689c3;

    /* renamed from: c4 */
    public ColorStateList f56690c4;

    /* renamed from: c5 */
    public float f56691c5;

    /* renamed from: c6 */
    public float f56692c6;

    /* renamed from: c7 */
    public ColorStateList f56693c7;

    /* renamed from: c8 */
    public float f56694c8;

    /* renamed from: c9 */
    public ColorStateList f56695c9;

    /* renamed from: d0 */
    public CharSequence f56696d0;

    /* renamed from: d1 */
    public boolean f56697d1;

    /* renamed from: d2 */
    public Drawable f56698d2;

    /* renamed from: d3 */
    public ColorStateList f56699d3;

    /* renamed from: d4 */
    public float f56700d4;

    /* renamed from: d5 */
    public boolean f56701d5;

    /* renamed from: d6 */
    public boolean f56702d6;

    /* renamed from: d7 */
    public Drawable f56703d7;

    /* renamed from: d8 */
    public RippleDrawable f56704d8;

    /* renamed from: d9 */
    public ColorStateList f56705d9;

    /* renamed from: e0 */
    public float f56706e0;

    /* renamed from: e1 */
    public SpannableStringBuilder f56707e1;

    /* renamed from: e2 */
    public boolean f56708e2;

    /* renamed from: e3 */
    public boolean f56709e3;

    /* renamed from: e4 */
    public Drawable f56710e4;

    /* renamed from: e5 */
    public ColorStateList f56711e5;

    /* renamed from: e6 */
    public yg0 f56712e6;

    /* renamed from: e7 */
    public yg0 f56713e7;

    /* renamed from: e8 */
    public float f56714e8;

    /* renamed from: e9 */
    public float f56715e9;

    /* renamed from: f0 */
    public float f56716f0;

    /* renamed from: f1 */
    public float f56717f1;

    /* renamed from: f2 */
    public float f56718f2;

    /* renamed from: f3 */
    public float f56719f3;

    /* renamed from: f4 */
    public float f56720f4;

    /* renamed from: f5 */
    public float f56721f5;

    /* renamed from: f6 */
    public final Context f56722f6;

    /* renamed from: f7 */
    public final Paint f56723f7;

    /* renamed from: f8 */
    public final Paint.FontMetrics f56724f8;

    /* renamed from: f9 */
    public final RectF f56725f9;

    /* renamed from: g0 */
    public final PointF f56726g0;

    /* renamed from: g1 */
    public final Path f56727g1;

    /* renamed from: g2 */
    public final u51 f56728g2;

    /* renamed from: g3 */
    public int f56729g3;

    /* renamed from: g4 */
    public int f56730g4;

    /* renamed from: g5 */
    public int f56731g5;

    /* renamed from: g6 */
    public int f56732g6;

    /* renamed from: g7 */
    public int f56733g7;

    /* renamed from: g8 */
    public int f56734g8;

    /* renamed from: g9 */
    public boolean f56735g9;

    /* renamed from: h0 */
    public int f56736h0;

    /* renamed from: h1 */
    public int f56737h1;

    /* renamed from: h2 */
    public ColorFilter f56738h2;

    /* renamed from: h3 */
    public PorterDuffColorFilter f56739h3;

    /* renamed from: h4 */
    public ColorStateList f56740h4;

    /* renamed from: h5 */
    public PorterDuff.Mode f56741h5;

    /* renamed from: h6 */
    public int[] f56742h6;

    /* renamed from: h7 */
    public ColorStateList f56743h7;

    /* renamed from: h8 */
    public WeakReference f56744h8;

    /* renamed from: h9 */
    public TextUtils.TruncateAt f56745h9;

    /* renamed from: i0 */
    public boolean f56746i0;

    /* renamed from: i1 */
    public int f56747i1;

    /* renamed from: i2 */
    public boolean f56748i2;

    public C0590hq(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, Chip.f49328c3);
        this.f56692c6 = -1.0f;
        this.f56723f7 = new Paint(1);
        this.f56724f8 = new Paint.FontMetrics();
        this.f56725f9 = new RectF();
        this.f56726g0 = new PointF();
        this.f56727g1 = new Path();
        this.f56737h1 = v10.MASK;
        this.f56741h5 = PorterDuff.Mode.SRC_IN;
        this.f56744h8 = new WeakReference(null);
        m210838b0(context);
        this.f56722f6 = context;
        u51 u51Var = new u51(this);
        this.f56728g2 = u51Var;
        this.f56696d0 = "";
        u51Var.f60328a0.density = context.getResources().getDisplayMetrics().density;
        int[] iArr = f56687i3;
        setState(iArr);
        if (!Arrays.equals(this.f56742h6, iArr)) {
            this.f56742h6 = iArr;
            if (m213089f3()) {
                m213066d0(getState(), iArr);
            }
        }
        this.f56746i0 = true;
        f56688i4.setTint(-1);
    }

    /* renamed from: c7 */
    public static boolean m213057c7(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    /* renamed from: c8 */
    public static boolean m213058c8(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    /* renamed from: f4 */
    public static void m213059f4(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    @Override // p000.ce0, p000.t51
    /* renamed from: a0 */
    public final void mo210828a0() {
        m213065c9();
        invalidateSelf();
    }

    /* renamed from: c2 */
    public final void m213060c2(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setCallback(this);
        AbstractC1271ts.m214779a1(drawable, AbstractC1271ts.m214778a0(this));
        drawable.setLevel(getLevel());
        drawable.setVisible(isVisible(), false);
        if (drawable == this.f56703d7) {
            if (drawable.isStateful()) {
                drawable.setState(this.f56742h6);
            }
            AbstractC1270tr.m214774a7(drawable, this.f56705d9);
            return;
        }
        Drawable drawable2 = this.f56698d2;
        if (drawable == drawable2 && this.f56701d5) {
            AbstractC1270tr.m214774a7(drawable2, this.f56699d3);
        }
        if (drawable.isStateful()) {
            drawable.setState(getState());
        }
    }

    /* renamed from: c3 */
    public final void m213061c3(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (m213088f2() || m213087f1()) {
            float f = this.f56714e8 + this.f56715e9;
            Drawable drawable = this.f56735g9 ? this.f56710e4 : this.f56698d2;
            float intrinsicWidth = this.f56700d4;
            if (intrinsicWidth <= 0.0f && drawable != null) {
                intrinsicWidth = drawable.getIntrinsicWidth();
            }
            if (AbstractC1271ts.m214778a0(this) == 0) {
                float f2 = rect.left + f;
                rectF.left = f2;
                rectF.right = f2 + intrinsicWidth;
            } else {
                float f3 = rect.right - f;
                rectF.right = f3;
                rectF.left = f3 - intrinsicWidth;
            }
            Drawable drawable2 = this.f56735g9 ? this.f56710e4 : this.f56698d2;
            float fCeil = this.f56700d4;
            if (fCeil <= 0.0f && drawable2 != null) {
                fCeil = (float) Math.ceil(AbstractC1117qo.m214422b8(this.f56722f6, 24));
                if (drawable2.getIntrinsicHeight() <= fCeil) {
                    fCeil = drawable2.getIntrinsicHeight();
                }
            }
            float fExactCenterY = rect.exactCenterY() - (fCeil / 2.0f);
            rectF.top = fExactCenterY;
            rectF.bottom = fExactCenterY + fCeil;
        }
    }

    /* renamed from: c4 */
    public final float m213062c4() {
        if (!m213088f2() && !m213087f1()) {
            return 0.0f;
        }
        float f = this.f56715e9;
        Drawable drawable = this.f56735g9 ? this.f56710e4 : this.f56698d2;
        float intrinsicWidth = this.f56700d4;
        if (intrinsicWidth <= 0.0f && drawable != null) {
            intrinsicWidth = drawable.getIntrinsicWidth();
        }
        return intrinsicWidth + f + this.f56716f0;
    }

    /* renamed from: c5 */
    public final float m213063c5() {
        if (m213089f3()) {
            return this.f56719f3 + this.f56706e0 + this.f56720f4;
        }
        return 0.0f;
    }

    /* renamed from: c6 */
    public final float m213064c6() {
        return this.f56748i2 ? m210836a8() : this.f56692c6;
    }

    /* renamed from: c9 */
    public final void m213065c9() {
        InterfaceC0589hp interfaceC0589hp = (InterfaceC0589hp) this.f56744h8.get();
        if (interfaceC0589hp != null) {
            Chip chip = (Chip) interfaceC0589hp;
            chip.m210988a2(chip.f49344b6);
            chip.requestLayout();
            chip.invalidateOutline();
        }
    }

    /* renamed from: d0 */
    public final boolean m213066d0(int[] iArr, int[] iArr2) {
        boolean z;
        boolean z2;
        ColorStateList colorStateList;
        boolean zOnStateChange = super.onStateChange(iArr);
        ColorStateList colorStateList2 = this.f56689c3;
        int iM210830a2 = m210830a2(colorStateList2 != null ? colorStateList2.getColorForState(iArr, this.f56729g3) : 0);
        boolean state = true;
        if (this.f56729g3 != iM210830a2) {
            this.f56729g3 = iM210830a2;
            zOnStateChange = true;
        }
        ColorStateList colorStateList3 = this.f56690c4;
        int iM210830a22 = m210830a2(colorStateList3 != null ? colorStateList3.getColorForState(iArr, this.f56730g4) : 0);
        if (this.f56730g4 != iM210830a22) {
            this.f56730g4 = iM210830a22;
            zOnStateChange = true;
        }
        int iM213332a2 = AbstractC0724jn.m213332a2(iM210830a22, iM210830a2);
        if ((this.f56731g5 != iM213332a2) | (this.f46107a0.f45839a2 == null)) {
            this.f56731g5 = iM213332a2;
            m210840b2(ColorStateList.valueOf(iM213332a2));
            zOnStateChange = true;
        }
        ColorStateList colorStateList4 = this.f56693c7;
        int colorForState = colorStateList4 != null ? colorStateList4.getColorForState(iArr, this.f56732g6) : 0;
        if (this.f56732g6 != colorForState) {
            this.f56732g6 = colorForState;
            zOnStateChange = true;
        }
        int colorForState2 = (this.f56743h7 == null || !b81.m210597f0(iArr)) ? 0 : this.f56743h7.getColorForState(iArr, this.f56733g7);
        if (this.f56733g7 != colorForState2) {
            this.f56733g7 = colorForState2;
        }
        r51 r51Var = this.f56728g2.f60333a5;
        int colorForState3 = (r51Var == null || (colorStateList = r51Var.f59634a9) == null) ? 0 : colorStateList.getColorForState(iArr, this.f56734g8);
        if (this.f56734g8 != colorForState3) {
            this.f56734g8 = colorForState3;
            zOnStateChange = true;
        }
        int[] state2 = getState();
        if (state2 == null) {
            z = false;
        } else {
            int length = state2.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (state2[i] != 16842912) {
                    i++;
                } else if (this.f56708e2) {
                    z = true;
                }
            }
            z = false;
        }
        if (this.f56735g9 == z || this.f56710e4 == null) {
            z2 = false;
        } else {
            float fM213062c4 = m213062c4();
            this.f56735g9 = z;
            if (fM213062c4 != m213062c4()) {
                zOnStateChange = true;
                z2 = true;
            } else {
                z2 = false;
                zOnStateChange = true;
            }
        }
        ColorStateList colorStateList5 = this.f56740h4;
        int colorForState4 = colorStateList5 != null ? colorStateList5.getColorForState(iArr, this.f56736h0) : 0;
        if (this.f56736h0 != colorForState4) {
            this.f56736h0 = colorForState4;
            ColorStateList colorStateList6 = this.f56740h4;
            PorterDuff.Mode mode = this.f56741h5;
            this.f56739h3 = (colorStateList6 == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList6.getColorForState(getState(), 0), mode);
        } else {
            state = zOnStateChange;
        }
        if (m213058c8(this.f56698d2)) {
            state |= this.f56698d2.setState(iArr);
        }
        if (m213058c8(this.f56710e4)) {
            state |= this.f56710e4.setState(iArr);
        }
        if (m213058c8(this.f56703d7)) {
            int[] iArr3 = new int[iArr.length + iArr2.length];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
            state |= this.f56703d7.setState(iArr3);
        }
        if (m213058c8(this.f56704d8)) {
            state |= this.f56704d8.setState(iArr2);
        }
        if (state) {
            invalidateSelf();
        }
        if (z2) {
            m213065c9();
        }
        return state;
    }

    /* renamed from: d1 */
    public final void m213067d1(boolean z) {
        if (this.f56708e2 != z) {
            this.f56708e2 = z;
            float fM213062c4 = m213062c4();
            if (!z && this.f56735g9) {
                this.f56735g9 = false;
            }
            float fM213062c42 = m213062c4();
            invalidateSelf();
            if (fM213062c4 != fM213062c42) {
                m213065c9();
            }
        }
    }

    /* renamed from: d2 */
    public final void m213068d2(Drawable drawable) {
        if (this.f56710e4 != drawable) {
            float fM213062c4 = m213062c4();
            this.f56710e4 = drawable;
            float fM213062c42 = m213062c4();
            m213059f4(this.f56710e4);
            m213060c2(this.f56710e4);
            invalidateSelf();
            if (fM213062c4 != fM213062c42) {
                m213065c9();
            }
        }
    }

    /* renamed from: d3 */
    public final void m213069d3(ColorStateList colorStateList) {
        Drawable drawable;
        if (this.f56711e5 != colorStateList) {
            this.f56711e5 = colorStateList;
            if (this.f56709e3 && (drawable = this.f56710e4) != null && this.f56708e2) {
                AbstractC1270tr.m214774a7(drawable, colorStateList);
            }
            onStateChange(getState());
        }
    }

    /* renamed from: d4 */
    public final void m213070d4(boolean z) {
        if (this.f56709e3 != z) {
            boolean zM213087f1 = m213087f1();
            this.f56709e3 = z;
            boolean zM213087f12 = m213087f1();
            if (zM213087f1 != zM213087f12) {
                if (zM213087f12) {
                    m213060c2(this.f56710e4);
                } else {
                    m213059f4(this.f56710e4);
                }
                invalidateSelf();
                m213065c9();
            }
        }
    }

    /* renamed from: d5 */
    public final void m213071d5(float f) {
        if (this.f56692c6 != f) {
            this.f56692c6 = f;
            xg1 xg1VarM17a6 = this.f46107a0.f45837a0.m17a6();
            xg1VarM17a6.m215188b1(f);
            setShapeAppearanceModel(xg1VarM17a6.m215177a0());
        }
    }

    /* renamed from: d6 */
    public final void m213072d6(Drawable drawable) {
        Drawable drawable2 = this.f56698d2;
        Drawable drawableM213594e1 = drawable2 != null ? kj1.m213594e1(drawable2) : null;
        if (drawableM213594e1 != drawable) {
            float fM213062c4 = m213062c4();
            this.f56698d2 = drawable != null ? drawable.mutate() : null;
            float fM213062c42 = m213062c4();
            m213059f4(drawableM213594e1);
            if (m213088f2()) {
                m213060c2(this.f56698d2);
            }
            invalidateSelf();
            if (fM213062c4 != fM213062c42) {
                m213065c9();
            }
        }
    }

    /* renamed from: d7 */
    public final void m213073d7(float f) {
        if (this.f56700d4 != f) {
            float fM213062c4 = m213062c4();
            this.f56700d4 = f;
            float fM213062c42 = m213062c4();
            invalidateSelf();
            if (fM213062c4 != fM213062c42) {
                m213065c9();
            }
        }
    }

    /* renamed from: d8 */
    public final void m213074d8(ColorStateList colorStateList) {
        this.f56701d5 = true;
        if (this.f56699d3 != colorStateList) {
            this.f56699d3 = colorStateList;
            if (m213088f2()) {
                AbstractC1270tr.m214774a7(this.f56698d2, colorStateList);
            }
            onStateChange(getState());
        }
    }

    /* renamed from: d9 */
    public final void m213075d9(boolean z) {
        if (this.f56697d1 != z) {
            boolean zM213088f2 = m213088f2();
            this.f56697d1 = z;
            boolean zM213088f22 = m213088f2();
            if (zM213088f2 != zM213088f22) {
                if (zM213088f22) {
                    m213060c2(this.f56698d2);
                } else {
                    m213059f4(this.f56698d2);
                }
                invalidateSelf();
                m213065c9();
            }
        }
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        Canvas canvas2;
        int iSaveLayerAlpha;
        int i2;
        Rect bounds = getBounds();
        if (bounds.isEmpty() || (i = this.f56737h1) == 0) {
            return;
        }
        if (i < 255) {
            canvas2 = canvas;
            iSaveLayerAlpha = canvas2.saveLayerAlpha(bounds.left, bounds.top, bounds.right, bounds.bottom, i);
        } else {
            canvas2 = canvas;
            iSaveLayerAlpha = 0;
        }
        boolean z = this.f56748i2;
        Paint paint = this.f56723f7;
        RectF rectF = this.f56725f9;
        if (!z) {
            paint.setColor(this.f56729g3);
            paint.setStyle(Paint.Style.FILL);
            rectF.set(bounds);
            canvas2.drawRoundRect(rectF, m213064c6(), m213064c6(), paint);
        }
        if (!this.f56748i2) {
            paint.setColor(this.f56730g4);
            paint.setStyle(Paint.Style.FILL);
            ColorFilter colorFilter = this.f56738h2;
            if (colorFilter == null) {
                colorFilter = this.f56739h3;
            }
            paint.setColorFilter(colorFilter);
            rectF.set(bounds);
            canvas2.drawRoundRect(rectF, m213064c6(), m213064c6(), paint);
        }
        if (this.f56748i2) {
            super.draw(canvas);
        }
        if (this.f56694c8 > 0.0f && !this.f56748i2) {
            paint.setColor(this.f56732g6);
            paint.setStyle(Paint.Style.STROKE);
            if (!this.f56748i2) {
                ColorFilter colorFilter2 = this.f56738h2;
                if (colorFilter2 == null) {
                    colorFilter2 = this.f56739h3;
                }
                paint.setColorFilter(colorFilter2);
            }
            float f = bounds.left;
            float f2 = this.f56694c8 / 2.0f;
            rectF.set(f + f2, bounds.top + f2, bounds.right - f2, bounds.bottom - f2);
            float f3 = this.f56692c6 - (this.f56694c8 / 2.0f);
            canvas2.drawRoundRect(rectF, f3, f3, paint);
        }
        paint.setColor(this.f56733g7);
        paint.setStyle(Paint.Style.FILL);
        rectF.set(bounds);
        if (this.f56748i2) {
            RectF rectF2 = new RectF(bounds);
            be0 be0Var = this.f46107a0;
            a01 a01Var = be0Var.f45837a0;
            float f4 = be0Var.f45845a8;
            tg0 tg0Var = this.f46123b6;
            c01 c01Var = this.f46124b7;
            Path path = this.f56727g1;
            c01Var.m210755a0(a01Var, f4, rectF2, tg0Var, path);
            m210832a4(canvas2, paint, path, this.f46107a0.f45837a0, m210834a6());
        } else {
            canvas2.drawRoundRect(rectF, m213064c6(), m213064c6(), paint);
        }
        if (m213088f2()) {
            m213061c3(bounds, rectF);
            float f5 = rectF.left;
            float f6 = rectF.top;
            canvas2.translate(f5, f6);
            this.f56698d2.setBounds(0, 0, (int) rectF.width(), (int) rectF.height());
            this.f56698d2.draw(canvas2);
            canvas2.translate(-f5, -f6);
        }
        if (m213087f1()) {
            m213061c3(bounds, rectF);
            float f7 = rectF.left;
            float f8 = rectF.top;
            canvas2.translate(f7, f8);
            this.f56710e4.setBounds(0, 0, (int) rectF.width(), (int) rectF.height());
            this.f56710e4.draw(canvas2);
            canvas2.translate(-f7, -f8);
        }
        if (this.f56746i0 && this.f56696d0 != null) {
            PointF pointF = this.f56726g0;
            pointF.set(0.0f, 0.0f);
            Paint.Align align = Paint.Align.LEFT;
            CharSequence charSequence = this.f56696d0;
            u51 u51Var = this.f56728g2;
            if (charSequence != null) {
                float fM213062c4 = m213062c4() + this.f56714e8 + this.f56717f1;
                if (AbstractC1271ts.m214778a0(this) == 0) {
                    pointF.x = bounds.left + fM213062c4;
                } else {
                    pointF.x = bounds.right - fM213062c4;
                    align = Paint.Align.RIGHT;
                }
                float fCenterY = bounds.centerY();
                TextPaint textPaint = u51Var.f60328a0;
                Paint.FontMetrics fontMetrics = this.f56724f8;
                textPaint.getFontMetrics(fontMetrics);
                pointF.y = fCenterY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f);
            }
            rectF.setEmpty();
            if (this.f56696d0 != null) {
                float fM213062c42 = m213062c4() + this.f56714e8 + this.f56717f1;
                float fM213063c5 = m213063c5() + this.f56721f5 + this.f56718f2;
                if (AbstractC1271ts.m214778a0(this) == 0) {
                    rectF.left = bounds.left + fM213062c42;
                    rectF.right = bounds.right - fM213063c5;
                } else {
                    rectF.left = bounds.left + fM213063c5;
                    rectF.right = bounds.right - fM213062c42;
                }
                rectF.top = bounds.top;
                rectF.bottom = bounds.bottom;
            }
            r51 r51Var = u51Var.f60333a5;
            TextPaint textPaint2 = u51Var.f60328a0;
            if (r51Var != null) {
                textPaint2.drawableState = getState();
                u51Var.f60333a5.m214488a4(this.f56722f6, textPaint2, u51Var.f60329a1);
            }
            textPaint2.setTextAlign(align);
            boolean z2 = Math.round(u51Var.m214816a0(this.f56696d0.toString())) > Math.round(rectF.width());
            if (z2) {
                int iSave = canvas2.save();
                canvas2.clipRect(rectF);
                i2 = iSave;
            } else {
                i2 = 0;
            }
            CharSequence charSequenceEllipsize = this.f56696d0;
            if (z2 && this.f56745h9 != null) {
                charSequenceEllipsize = TextUtils.ellipsize(charSequenceEllipsize, textPaint2, rectF.width(), this.f56745h9);
            }
            canvas.drawText(charSequenceEllipsize, 0, charSequenceEllipsize.length(), pointF.x, pointF.y, textPaint2);
            canvas2 = canvas;
            if (z2) {
                canvas2.restoreToCount(i2);
            }
        }
        if (m213089f3()) {
            rectF.setEmpty();
            if (m213089f3()) {
                float f9 = this.f56721f5 + this.f56720f4;
                if (AbstractC1271ts.m214778a0(this) == 0) {
                    float f10 = bounds.right - f9;
                    rectF.right = f10;
                    rectF.left = f10 - this.f56706e0;
                } else {
                    float f11 = bounds.left + f9;
                    rectF.left = f11;
                    rectF.right = f11 + this.f56706e0;
                }
                float fExactCenterY = bounds.exactCenterY();
                float f12 = this.f56706e0;
                float f13 = fExactCenterY - (f12 / 2.0f);
                rectF.top = f13;
                rectF.bottom = f13 + f12;
            }
            float f14 = rectF.left;
            float f15 = rectF.top;
            canvas2.translate(f14, f15);
            this.f56703d7.setBounds(0, 0, (int) rectF.width(), (int) rectF.height());
            this.f56704d8.setBounds(this.f56703d7.getBounds());
            this.f56704d8.jumpToCurrentState();
            this.f56704d8.draw(canvas2);
            canvas2.translate(-f14, -f15);
        }
        if (this.f56737h1 < 255) {
            canvas2.restoreToCount(iSaveLayerAlpha);
        }
    }

    /* renamed from: e0 */
    public final void m213076e0(ColorStateList colorStateList) {
        if (this.f56693c7 != colorStateList) {
            this.f56693c7 = colorStateList;
            if (this.f56748i2) {
                m210845b7(colorStateList);
            }
            onStateChange(getState());
        }
    }

    /* renamed from: e1 */
    public final void m213077e1(float f) {
        if (this.f56694c8 != f) {
            this.f56694c8 = f;
            this.f56723f7.setStrokeWidth(f);
            if (this.f56748i2) {
                m210846b8(f);
            }
            invalidateSelf();
        }
    }

    /* renamed from: e2 */
    public final void m213078e2(Drawable drawable) {
        Drawable drawable2 = this.f56703d7;
        Drawable drawableM213594e1 = drawable2 != null ? kj1.m213594e1(drawable2) : null;
        if (drawableM213594e1 != drawable) {
            float fM213063c5 = m213063c5();
            this.f56703d7 = drawable != null ? drawable.mutate() : null;
            this.f56704d8 = new RippleDrawable(b81.m210594e5(this.f56695c9), this.f56703d7, f56688i4);
            float fM213063c52 = m213063c5();
            m213059f4(drawableM213594e1);
            if (m213089f3()) {
                m213060c2(this.f56703d7);
            }
            invalidateSelf();
            if (fM213063c5 != fM213063c52) {
                m213065c9();
            }
        }
    }

    /* renamed from: e3 */
    public final void m213079e3(float f) {
        if (this.f56720f4 != f) {
            this.f56720f4 = f;
            invalidateSelf();
            if (m213089f3()) {
                m213065c9();
            }
        }
    }

    /* renamed from: e4 */
    public final void m213080e4(float f) {
        if (this.f56706e0 != f) {
            this.f56706e0 = f;
            invalidateSelf();
            if (m213089f3()) {
                m213065c9();
            }
        }
    }

    /* renamed from: e5 */
    public final void m213081e5(float f) {
        if (this.f56719f3 != f) {
            this.f56719f3 = f;
            invalidateSelf();
            if (m213089f3()) {
                m213065c9();
            }
        }
    }

    /* renamed from: e6 */
    public final void m213082e6(ColorStateList colorStateList) {
        if (this.f56705d9 != colorStateList) {
            this.f56705d9 = colorStateList;
            if (m213089f3()) {
                AbstractC1270tr.m214774a7(this.f56703d7, colorStateList);
            }
            onStateChange(getState());
        }
    }

    /* renamed from: e7 */
    public final void m213083e7(boolean z) {
        if (this.f56702d6 != z) {
            boolean zM213089f3 = m213089f3();
            this.f56702d6 = z;
            boolean zM213089f32 = m213089f3();
            if (zM213089f3 != zM213089f32) {
                if (zM213089f32) {
                    m213060c2(this.f56703d7);
                } else {
                    m213059f4(this.f56703d7);
                }
                invalidateSelf();
                m213065c9();
            }
        }
    }

    /* renamed from: e8 */
    public final void m213084e8(float f) {
        if (this.f56716f0 != f) {
            float fM213062c4 = m213062c4();
            this.f56716f0 = f;
            float fM213062c42 = m213062c4();
            invalidateSelf();
            if (fM213062c4 != fM213062c42) {
                m213065c9();
            }
        }
    }

    /* renamed from: e9 */
    public final void m213085e9(float f) {
        if (this.f56715e9 != f) {
            float fM213062c4 = m213062c4();
            this.f56715e9 = f;
            float fM213062c42 = m213062c4();
            invalidateSelf();
            if (fM213062c4 != fM213062c42) {
                m213065c9();
            }
        }
    }

    /* renamed from: f0 */
    public final void m213086f0(ColorStateList colorStateList) {
        if (this.f56695c9 != colorStateList) {
            this.f56695c9 = colorStateList;
            this.f56743h7 = null;
            onStateChange(getState());
        }
    }

    /* renamed from: f1 */
    public final boolean m213087f1() {
        return this.f56709e3 && this.f56710e4 != null && this.f56735g9;
    }

    /* renamed from: f2 */
    public final boolean m213088f2() {
        return this.f56697d1 && this.f56698d2 != null;
    }

    /* renamed from: f3 */
    public final boolean m213089f3() {
        return this.f56702d6 && this.f56703d7 != null;
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.f56737h1;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.f56738h2;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) this.f56691c5;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.min(Math.round(m213063c5() + this.f56728g2.m214816a0(this.f56696d0.toString()) + m213062c4() + this.f56714e8 + this.f56717f1 + this.f56718f2 + this.f56721f5), this.f56747i1);
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        Outline outline2;
        if (this.f56748i2) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            outline2 = outline;
            outline2.setRoundRect(0, 0, getIntrinsicWidth(), (int) this.f56691c5, this.f56692c6);
        } else {
            outline.setRoundRect(bounds, this.f56692c6);
            outline2 = outline;
        }
        outline2.setAlpha(this.f56737h1 / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList;
        if (m213057c7(this.f56689c3) || m213057c7(this.f56690c4) || m213057c7(this.f56693c7)) {
            return true;
        }
        r51 r51Var = this.f56728g2.f60333a5;
        if (r51Var == null || (colorStateList = r51Var.f59634a9) == null || !colorStateList.isStateful()) {
            return (this.f56709e3 && this.f56710e4 != null && this.f56708e2) || m213058c8(this.f56698d2) || m213058c8(this.f56710e4) || m213057c7(this.f56740h4);
        }
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        boolean zOnLayoutDirectionChanged = super.onLayoutDirectionChanged(i);
        if (m213088f2()) {
            zOnLayoutDirectionChanged |= AbstractC1271ts.m214779a1(this.f56698d2, i);
        }
        if (m213087f1()) {
            zOnLayoutDirectionChanged |= AbstractC1271ts.m214779a1(this.f56710e4, i);
        }
        if (m213089f3()) {
            zOnLayoutDirectionChanged |= AbstractC1271ts.m214779a1(this.f56703d7, i);
        }
        if (!zOnLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        boolean zOnLevelChange = super.onLevelChange(i);
        if (m213088f2()) {
            zOnLevelChange |= this.f56698d2.setLevel(i);
        }
        if (m213087f1()) {
            zOnLevelChange |= this.f56710e4.setLevel(i);
        }
        if (m213089f3()) {
            zOnLevelChange |= this.f56703d7.setLevel(i);
        }
        if (zOnLevelChange) {
            invalidateSelf();
        }
        return zOnLevelChange;
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        if (this.f56748i2) {
            super.onStateChange(iArr);
        }
        return m213066d0(iArr, this.f56742h6);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.f56737h1 != i) {
            this.f56737h1 = i;
            invalidateSelf();
        }
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        if (this.f56738h2 != colorFilter) {
            this.f56738h2 = colorFilter;
            invalidateSelf();
        }
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        if (this.f56740h4 != colorStateList) {
            this.f56740h4 = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        if (this.f56741h5 != mode) {
            this.f56741h5 = mode;
            ColorStateList colorStateList = this.f56740h4;
            this.f56739h3 = (colorStateList == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (m213088f2()) {
            visible |= this.f56698d2.setVisible(z, z2);
        }
        if (m213087f1()) {
            visible |= this.f56710e4.setVisible(z, z2);
        }
        if (m213089f3()) {
            visible |= this.f56703d7.setVisible(z, z2);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
