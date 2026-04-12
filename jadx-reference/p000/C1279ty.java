package p000;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$style;
import androidx.appcompat.R$styleable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ty */
/* loaded from: classes.dex */
public final class C1279ty extends Drawable {

    /* renamed from: b1 */
    public static final float f60301b1 = (float) Math.toRadians(45.0d);

    /* renamed from: a0 */
    public final Paint f60302a0;

    /* renamed from: a1 */
    public final float f60303a1;

    /* renamed from: a2 */
    public final float f60304a2;

    /* renamed from: a3 */
    public final float f60305a3;

    /* renamed from: a4 */
    public final float f60306a4;

    /* renamed from: a5 */
    public final boolean f60307a5;

    /* renamed from: a6 */
    public final Path f60308a6;

    /* renamed from: a7 */
    public final int f60309a7;

    /* renamed from: a8 */
    public float f60310a8;

    /* renamed from: a9 */
    public final float f60311a9;

    /* renamed from: b0 */
    public final int f60312b0;

    public C1279ty(Context context) {
        Paint paint = new Paint();
        this.f60302a0 = paint;
        this.f60308a6 = new Path();
        this.f60312b0 = 2;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R$styleable.DrawerArrowToggle, R$attr.drawerArrowStyle, R$style.Base_Widget_AppCompat_DrawerArrowToggle);
        int color = typedArrayObtainStyledAttributes.getColor(R$styleable.DrawerArrowToggle_color, 0);
        if (color != paint.getColor()) {
            paint.setColor(color);
            invalidateSelf();
        }
        float dimension = typedArrayObtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_thickness, 0.0f);
        if (paint.getStrokeWidth() != dimension) {
            paint.setStrokeWidth(dimension);
            this.f60311a9 = (float) (Math.cos(f60301b1) * (dimension / 2.0f));
            invalidateSelf();
        }
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R$styleable.DrawerArrowToggle_spinBars, true);
        if (this.f60307a5 != z) {
            this.f60307a5 = z;
            invalidateSelf();
        }
        float fRound = Math.round(typedArrayObtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_gapBetweenBars, 0.0f));
        if (fRound != this.f60306a4) {
            this.f60306a4 = fRound;
            invalidateSelf();
        }
        this.f60309a7 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.DrawerArrowToggle_drawableSize, 0);
        this.f60304a2 = Math.round(typedArrayObtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_barLength, 0.0f));
        this.f60303a1 = Math.round(typedArrayObtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_arrowHeadLength, 0.0f));
        this.f60305a3 = typedArrayObtainStyledAttributes.getDimension(R$styleable.DrawerArrowToggle_arrowShaftLength, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* renamed from: a0 */
    public static float m214799a0(float f, float f2, float f3) {
        return AbstractC0003a2.m19a0(f2, f, f3, f);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect bounds = getBounds();
        boolean z = false;
        int i = this.f60312b0;
        if (i != 0 && (i == 1 || (i == 3 ? AbstractC1271ts.m214778a0(this) == 0 : AbstractC1271ts.m214778a0(this) == 1))) {
            z = true;
        }
        float f = this.f60303a1;
        float fSqrt = (float) Math.sqrt(f * f * 2.0f);
        float f2 = this.f60310a8;
        float f3 = this.f60304a2;
        float fM214799a0 = m214799a0(f3, fSqrt, f2);
        float fM214799a02 = m214799a0(f3, this.f60305a3, this.f60310a8);
        float fRound = Math.round(m214799a0(0.0f, this.f60311a9, this.f60310a8));
        float fM214799a03 = m214799a0(0.0f, f60301b1, this.f60310a8);
        float fM214799a04 = m214799a0(z ? 0.0f : -180.0f, z ? 180.0f : 0.0f, this.f60310a8);
        double d = fM214799a0;
        double d2 = fM214799a03;
        float fRound2 = Math.round(Math.cos(d2) * d);
        float fRound3 = Math.round(Math.sin(d2) * d);
        Path path = this.f60308a6;
        path.rewind();
        float f4 = this.f60306a4;
        Paint paint = this.f60302a0;
        float fM214799a05 = m214799a0(f4 + paint.getStrokeWidth(), -this.f60311a9, this.f60310a8);
        float f5 = (-fM214799a02) / 2.0f;
        path.moveTo(f5 + fRound, 0.0f);
        path.rLineTo(fM214799a02 - (fRound * 2.0f), 0.0f);
        path.moveTo(f5, fM214799a05);
        path.rLineTo(fRound2, fRound3);
        path.moveTo(f5, -fM214799a05);
        path.rLineTo(fRound2, -fRound3);
        path.close();
        canvas.save();
        float strokeWidth = paint.getStrokeWidth();
        float fHeight = bounds.height() - (3.0f * strokeWidth);
        canvas.translate(bounds.centerX(), (strokeWidth * 1.5f) + this.f60306a4 + ((((int) (fHeight - (r7 * 2.0f))) / 4) * 2));
        if (this.f60307a5) {
            canvas.rotate(fM214799a04 * (z ? -1 : 1));
        } else if (z) {
            canvas.rotate(180.0f);
        }
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.f60309a7;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.f60309a7;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        Paint paint = this.f60302a0;
        if (i != paint.getAlpha()) {
            paint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f60302a0.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
