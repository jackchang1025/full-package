package p000;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ls0 extends Drawable {

    /* renamed from: a0 */
    public float f58163a0;

    /* renamed from: a1 */
    public final Paint f58164a1;

    /* renamed from: a2 */
    public final RectF f58165a2;

    /* renamed from: a3 */
    public final Rect f58166a3;

    /* renamed from: a4 */
    public float f58167a4;

    /* renamed from: a7 */
    public ColorStateList f58170a7;

    /* renamed from: a8 */
    public PorterDuffColorFilter f58171a8;

    /* renamed from: a9 */
    public ColorStateList f58172a9;

    /* renamed from: a5 */
    public boolean f58168a5 = false;

    /* renamed from: a6 */
    public boolean f58169a6 = true;

    /* renamed from: b0 */
    public PorterDuff.Mode f58173b0 = PorterDuff.Mode.SRC_IN;

    public ls0(ColorStateList colorStateList, float f) {
        this.f58163a0 = f;
        Paint paint = new Paint(5);
        this.f58164a1 = paint;
        colorStateList = colorStateList == null ? ColorStateList.valueOf(0) : colorStateList;
        this.f58170a7 = colorStateList;
        paint.setColor(colorStateList.getColorForState(getState(), this.f58170a7.getDefaultColor()));
        this.f58165a2 = new RectF();
        this.f58166a3 = new Rect();
    }

    /* renamed from: a0 */
    public final PorterDuffColorFilter m213927a0(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    /* renamed from: a1 */
    public final void m213928a1(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        float f = rect.left;
        float f2 = rect.top;
        float f3 = rect.right;
        float f4 = rect.bottom;
        RectF rectF = this.f58165a2;
        rectF.set(f, f2, f3, f4);
        Rect rect2 = this.f58166a3;
        rect2.set(rect);
        if (this.f58168a5) {
            rect2.inset((int) Math.ceil(ms0.m214020a0(this.f58167a4, this.f58163a0, this.f58169a6)), (int) Math.ceil(ms0.m214021a1(this.f58167a4, this.f58163a0, this.f58169a6)));
            rectF.set(rect2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean z;
        PorterDuffColorFilter porterDuffColorFilter = this.f58171a8;
        Paint paint = this.f58164a1;
        if (porterDuffColorFilter == null || paint.getColorFilter() != null) {
            z = false;
        } else {
            paint.setColorFilter(this.f58171a8);
            z = true;
        }
        RectF rectF = this.f58165a2;
        float f = this.f58163a0;
        canvas.drawRoundRect(rectF, f, f, paint);
        if (z) {
            paint.setColorFilter(null);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        outline.setRoundRect(this.f58166a3, this.f58163a0);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList = this.f58172a9;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        ColorStateList colorStateList2 = this.f58170a7;
        return (colorStateList2 != null && colorStateList2.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        m213928a1(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.f58170a7;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        Paint paint = this.f58164a1;
        boolean z = colorForState != paint.getColor();
        if (z) {
            paint.setColor(colorForState);
        }
        ColorStateList colorStateList2 = this.f58172a9;
        if (colorStateList2 == null || (mode = this.f58173b0) == null) {
            return z;
        }
        this.f58171a8 = m213927a0(colorStateList2, mode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.f58164a1.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f58164a1.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        this.f58172a9 = colorStateList;
        this.f58171a8 = m213927a0(colorStateList, this.f58173b0);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        this.f58173b0 = mode;
        this.f58171a8 = m213927a0(this.f58172a9, mode);
        invalidateSelf();
    }
}
