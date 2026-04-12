package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import java.util.BitSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class ce0 extends Drawable implements l01 {

    /* renamed from: c2 */
    public static final Paint f46106c2;

    /* renamed from: a0 */
    public be0 f46107a0;

    /* renamed from: a1 */
    public final j01[] f46108a1;

    /* renamed from: a2 */
    public final j01[] f46109a2;

    /* renamed from: a3 */
    public final BitSet f46110a3;

    /* renamed from: a4 */
    public boolean f46111a4;

    /* renamed from: a5 */
    public final Matrix f46112a5;

    /* renamed from: a6 */
    public final Path f46113a6;

    /* renamed from: a7 */
    public final Path f46114a7;

    /* renamed from: a8 */
    public final RectF f46115a8;

    /* renamed from: a9 */
    public final RectF f46116a9;

    /* renamed from: b0 */
    public final Region f46117b0;

    /* renamed from: b1 */
    public final Region f46118b1;

    /* renamed from: b2 */
    public a01 f46119b2;

    /* renamed from: b3 */
    public final Paint f46120b3;

    /* renamed from: b4 */
    public final Paint f46121b4;

    /* renamed from: b5 */
    public final yz0 f46122b5;

    /* renamed from: b6 */
    public final tg0 f46123b6;

    /* renamed from: b7 */
    public final c01 f46124b7;

    /* renamed from: b8 */
    public PorterDuffColorFilter f46125b8;

    /* renamed from: b9 */
    public PorterDuffColorFilter f46126b9;

    /* renamed from: c0 */
    public final RectF f46127c0;

    /* renamed from: c1 */
    public boolean f46128c1;

    static {
        Paint paint = new Paint(1);
        f46106c2 = paint;
        paint.setColor(-1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public ce0() {
        this(new a01());
    }

    /* renamed from: a0 */
    public void mo210828a0() {
        invalidateSelf();
    }

    /* renamed from: a1 */
    public final void m210829a1(RectF rectF, Path path) {
        be0 be0Var = this.f46107a0;
        this.f46124b7.m210755a0(be0Var.f45837a0, be0Var.f45845a8, rectF, this.f46123b6, path);
        if (this.f46107a0.f45844a7 != 1.0f) {
            Matrix matrix = this.f46112a5;
            matrix.reset();
            float f = this.f46107a0.f45844a7;
            matrix.setScale(f, f, rectF.width() / 2.0f, rectF.height() / 2.0f);
            path.transform(matrix);
        }
        path.computeBounds(this.f46127c0, true);
    }

    /* renamed from: a2 */
    public final int m210830a2(int i) {
        be0 be0Var = this.f46107a0;
        float f = be0Var.f45849b2 + 0.0f + be0Var.f45848b1;
        C1357vz c1357vz = be0Var.f45838a1;
        return c1357vz != null ? c1357vz.m214972a0(f, i) : i;
    }

    /* renamed from: a3 */
    public final void m210831a3(Canvas canvas) {
        this.f46110a3.cardinality();
        int i = this.f46107a0.f45852b5;
        Path path = this.f46113a6;
        yz0 yz0Var = this.f46122b5;
        if (i != 0) {
            canvas.drawPath(path, yz0Var.f61410a0);
        }
        for (int i2 = 0; i2 < 4; i2++) {
            j01 j01Var = this.f46108a1[i2];
            int i3 = this.f46107a0.f45851b4;
            Matrix matrix = j01.f57253a1;
            j01Var.mo212547a0(matrix, yz0Var, i3, canvas);
            this.f46109a2[i2].mo212547a0(matrix, yz0Var, this.f46107a0.f45851b4, canvas);
        }
        if (this.f46128c1) {
            be0 be0Var = this.f46107a0;
            int iSin = (int) (Math.sin(Math.toRadians(be0Var.f45853b6)) * be0Var.f45852b5);
            int iM210835a7 = m210835a7();
            canvas.translate(-iSin, -iM210835a7);
            canvas.drawPath(path, f46106c2);
            canvas.translate(iSin, iM210835a7);
        }
    }

    /* renamed from: a4 */
    public final void m210832a4(Canvas canvas, Paint paint, Path path, a01 a01Var, RectF rectF) {
        if (!a01Var.m16a5(rectF)) {
            canvas.drawPath(path, paint);
        } else {
            float fMo212732a0 = a01Var.f12a5.mo212732a0(rectF) * this.f46107a0.f45845a8;
            canvas.drawRoundRect(rectF, fMo212732a0, fMo212732a0, paint);
        }
    }

    /* renamed from: a5 */
    public void mo210833a5(Canvas canvas) {
        a01 a01Var = this.f46119b2;
        RectF rectFM210834a6 = m210834a6();
        RectF rectF = this.f46116a9;
        rectF.set(rectFM210834a6);
        boolean zM210837a9 = m210837a9();
        Paint paint = this.f46121b4;
        float strokeWidth = zM210837a9 ? paint.getStrokeWidth() / 2.0f : 0.0f;
        rectF.inset(strokeWidth, strokeWidth);
        m210832a4(canvas, paint, this.f46114a7, a01Var, rectF);
    }

    /* renamed from: a6 */
    public final RectF m210834a6() {
        Rect bounds = getBounds();
        RectF rectF = this.f46115a8;
        rectF.set(bounds);
        return rectF;
    }

    /* renamed from: a7 */
    public final int m210835a7() {
        be0 be0Var = this.f46107a0;
        return (int) (Math.cos(Math.toRadians(be0Var.f45853b6)) * be0Var.f45852b5);
    }

    /* renamed from: a8 */
    public final float m210836a8() {
        return this.f46107a0.f45837a0.f11a4.mo212732a0(m210834a6());
    }

    /* renamed from: a9 */
    public final boolean m210837a9() {
        Paint.Style style = this.f46107a0.f45854b7;
        return (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.f46121b4.getStrokeWidth() > 0.0f;
    }

    /* renamed from: b0 */
    public final void m210838b0(Context context) {
        this.f46107a0.f45838a1 = new C1357vz(context);
        m210849c1();
    }

    /* renamed from: b1 */
    public final void m210839b1(float f) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45849b2 != f) {
            be0Var.f45849b2 = f;
            m210849c1();
        }
    }

    /* renamed from: b2 */
    public final void m210840b2(ColorStateList colorStateList) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45839a2 != colorStateList) {
            be0Var.f45839a2 = colorStateList;
            onStateChange(getState());
        }
    }

    /* renamed from: b3 */
    public final void m210841b3(float f) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45845a8 != f) {
            be0Var.f45845a8 = f;
            this.f46111a4 = true;
            invalidateSelf();
        }
    }

    /* renamed from: b4 */
    public final void m210842b4() {
        this.f46107a0.f45854b7 = Paint.Style.FILL;
        super.invalidateSelf();
    }

    /* renamed from: b5 */
    public final void m210843b5() {
        this.f46122b5.m215327a0(-12303292);
        this.f46107a0.getClass();
        super.invalidateSelf();
    }

    /* renamed from: b6 */
    public final void m210844b6(int i) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45850b3 != i) {
            be0Var.f45850b3 = i;
            super.invalidateSelf();
        }
    }

    /* renamed from: b7 */
    public final void m210845b7(ColorStateList colorStateList) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45840a3 != colorStateList) {
            be0Var.f45840a3 = colorStateList;
            onStateChange(getState());
        }
    }

    /* renamed from: b8 */
    public final void m210846b8(float f) {
        this.f46107a0.f45846a9 = f;
        invalidateSelf();
    }

    /* renamed from: b9 */
    public final boolean m210847b9(int[] iArr) {
        boolean z;
        Paint paint;
        int color;
        int colorForState;
        Paint paint2;
        int color2;
        int colorForState2;
        if (this.f46107a0.f45839a2 == null || color2 == (colorForState2 = this.f46107a0.f45839a2.getColorForState(iArr, (color2 = (paint2 = this.f46120b3).getColor())))) {
            z = false;
        } else {
            paint2.setColor(colorForState2);
            z = true;
        }
        if (this.f46107a0.f45840a3 == null || color == (colorForState = this.f46107a0.f45840a3.getColorForState(iArr, (color = (paint = this.f46121b4).getColor())))) {
            return z;
        }
        paint.setColor(colorForState);
        return true;
    }

    /* renamed from: c0 */
    public final boolean m210848c0() {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = this.f46125b8;
        PorterDuffColorFilter porterDuffColorFilter3 = this.f46126b9;
        be0 be0Var = this.f46107a0;
        ColorStateList colorStateList = be0Var.f45841a4;
        PorterDuff.Mode mode = be0Var.f45842a5;
        if (colorStateList == null || mode == null) {
            int color = this.f46120b3.getColor();
            int iM210830a2 = m210830a2(color);
            porterDuffColorFilter = iM210830a2 != color ? new PorterDuffColorFilter(iM210830a2, PorterDuff.Mode.SRC_IN) : null;
        } else {
            porterDuffColorFilter = new PorterDuffColorFilter(m210830a2(colorStateList.getColorForState(getState(), 0)), mode);
        }
        this.f46125b8 = porterDuffColorFilter;
        this.f46107a0.getClass();
        this.f46126b9 = null;
        this.f46107a0.getClass();
        return (tk0.m214759a0(porterDuffColorFilter2, this.f46125b8) && tk0.m214759a0(porterDuffColorFilter3, this.f46126b9)) ? false : true;
    }

    /* renamed from: c1 */
    public final void m210849c1() {
        be0 be0Var = this.f46107a0;
        float f = be0Var.f45849b2 + 0.0f;
        be0Var.f45851b4 = (int) Math.ceil(0.75f * f);
        this.f46107a0.f45852b5 = (int) Math.ceil(f * 0.25f);
        m210848c0();
        super.invalidateSelf();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00ba  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void draw(Canvas canvas) {
        PorterDuffColorFilter porterDuffColorFilter = this.f46125b8;
        Paint paint = this.f46120b3;
        paint.setColorFilter(porterDuffColorFilter);
        int alpha = paint.getAlpha();
        int i = this.f46107a0.f45847b0;
        paint.setAlpha(((i + (i >>> 7)) * alpha) >>> 8);
        PorterDuffColorFilter porterDuffColorFilter2 = this.f46126b9;
        Paint paint2 = this.f46121b4;
        paint2.setColorFilter(porterDuffColorFilter2);
        paint2.setStrokeWidth(this.f46107a0.f45846a9);
        int alpha2 = paint2.getAlpha();
        int i2 = this.f46107a0.f45847b0;
        paint2.setAlpha(((i2 + (i2 >>> 7)) * alpha2) >>> 8);
        boolean z = this.f46111a4;
        Path path = this.f46113a6;
        if (z) {
            a01 a01VarM18a7 = this.f46107a0.f45837a0.m18a7(new ae0(-(m210837a9() ? paint2.getStrokeWidth() / 2.0f : 0.0f)));
            this.f46119b2 = a01VarM18a7;
            float f = this.f46107a0.f45845a8;
            RectF rectFM210834a6 = m210834a6();
            RectF rectF = this.f46116a9;
            rectF.set(rectFM210834a6);
            float strokeWidth = m210837a9() ? paint2.getStrokeWidth() / 2.0f : 0.0f;
            rectF.inset(strokeWidth, strokeWidth);
            this.f46124b7.m210755a0(a01VarM18a7, f, rectF, null, this.f46114a7);
            m210829a1(m210834a6(), path);
            this.f46111a4 = false;
        }
        be0 be0Var = this.f46107a0;
        int i3 = be0Var.f45850b3;
        if (i3 != 1 && be0Var.f45851b4 > 0) {
            if (i3 != 2) {
                int i4 = Build.VERSION.SDK_INT;
                if (!be0Var.f45837a0.m16a5(m210834a6()) && !path.isConvex() && i4 < 29) {
                    canvas.save();
                    be0 be0Var2 = this.f46107a0;
                    canvas.translate((int) (Math.sin(Math.toRadians(be0Var2.f45853b6)) * be0Var2.f45852b5), m210835a7());
                    if (this.f46128c1) {
                        RectF rectF2 = this.f46127c0;
                        int iWidth = (int) (rectF2.width() - getBounds().width());
                        int iHeight = (int) (rectF2.height() - getBounds().height());
                        if (iWidth < 0 || iHeight < 0) {
                            throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
                        }
                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((this.f46107a0.f45851b4 * 2) + ((int) rectF2.width()) + iWidth, (this.f46107a0.f45851b4 * 2) + ((int) rectF2.height()) + iHeight, Bitmap.Config.ARGB_8888);
                        Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                        float f2 = (getBounds().left - this.f46107a0.f45851b4) - iWidth;
                        float f3 = (getBounds().top - this.f46107a0.f45851b4) - iHeight;
                        canvas2.translate(-f2, -f3);
                        m210831a3(canvas2);
                        canvas.drawBitmap(bitmapCreateBitmap, f2, f3, (Paint) null);
                        bitmapCreateBitmap.recycle();
                        canvas.restore();
                    } else {
                        m210831a3(canvas);
                        canvas.restore();
                    }
                }
            }
        }
        be0 be0Var3 = this.f46107a0;
        Paint.Style style = be0Var3.f45854b7;
        if (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.FILL) {
            m210832a4(canvas, paint, path, be0Var3.f45837a0, m210834a6());
        }
        if (m210837a9()) {
            mo210833a5(canvas);
        }
        paint.setAlpha(alpha);
        paint2.setAlpha(alpha2);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.f46107a0.f45847b0;
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.f46107a0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45850b3 == 2) {
            return;
        }
        if (be0Var.f45837a0.m16a5(m210834a6())) {
            outline.setRoundRect(getBounds(), m210836a8() * this.f46107a0.f45845a8);
        } else {
            RectF rectFM210834a6 = m210834a6();
            Path path = this.f46113a6;
            m210829a1(rectFM210834a6, path);
            AbstractC1117qo.m214460f9(outline, path);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        Rect rect2 = this.f46107a0.f45843a6;
        if (rect2 == null) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final Region getTransparentRegion() {
        Rect bounds = getBounds();
        Region region = this.f46117b0;
        region.set(bounds);
        RectF rectFM210834a6 = m210834a6();
        Path path = this.f46113a6;
        m210829a1(rectFM210834a6, path);
        Region region2 = this.f46118b1;
        region2.setPath(path, region);
        region.op(region2, Region.Op.DIFFERENCE);
        return region;
    }

    @Override // android.graphics.drawable.Drawable
    public final void invalidateSelf() {
        this.f46111a4 = true;
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        ColorStateList colorStateList = this.f46107a0.f45841a4;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        this.f46107a0.getClass();
        ColorStateList colorStateList2 = this.f46107a0.f45840a3;
        if (colorStateList2 != null && colorStateList2.isStateful()) {
            return true;
        }
        ColorStateList colorStateList3 = this.f46107a0.f45839a2;
        return colorStateList3 != null && colorStateList3.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        this.f46107a0 = new be0(this.f46107a0);
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.f46111a4 = true;
        super.onBoundsChange(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean z = m210847b9(iArr) || m210848c0();
        if (z) {
            invalidateSelf();
        }
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45847b0 != i) {
            be0Var.f45847b0 = i;
            super.invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.f46107a0.getClass();
        super.invalidateSelf();
    }

    @Override // p000.l01
    public final void setShapeAppearanceModel(a01 a01Var) {
        this.f46107a0.f45837a0 = a01Var;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.f46107a0.f45841a4 = colorStateList;
        m210848c0();
        super.invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        be0 be0Var = this.f46107a0;
        if (be0Var.f45842a5 != mode) {
            be0Var.f45842a5 = mode;
            m210848c0();
            super.invalidateSelf();
        }
    }

    public ce0(a01 a01Var) {
        this(new be0(a01Var));
    }

    public ce0(be0 be0Var) {
        c01 c01Var;
        this.f46108a1 = new j01[4];
        this.f46109a2 = new j01[4];
        this.f46110a3 = new BitSet(8);
        this.f46112a5 = new Matrix();
        this.f46113a6 = new Path();
        this.f46114a7 = new Path();
        this.f46115a8 = new RectF();
        this.f46116a9 = new RectF();
        this.f46117b0 = new Region();
        this.f46118b1 = new Region();
        Paint paint = new Paint(1);
        this.f46120b3 = paint;
        Paint paint2 = new Paint(1);
        this.f46121b4 = paint2;
        this.f46122b5 = new yz0();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            c01Var = b01.f45676a0;
        } else {
            c01Var = new c01();
        }
        this.f46124b7 = c01Var;
        this.f46127c0 = new RectF();
        this.f46128c1 = true;
        this.f46107a0 = be0Var;
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        m210848c0();
        m210847b9(getState());
        this.f46123b6 = new tg0(25, this);
    }

    public ce0(Context context, AttributeSet attributeSet, int i, int i2) {
        this(a01.m14a3(context, attributeSet, i, i2).m215177a0());
    }
}
