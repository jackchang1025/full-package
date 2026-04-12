package p000;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yk */
/* loaded from: classes2.dex */
public final class C1483yk extends Drawable {

    /* renamed from: a0 */
    public final Drawable f61333a0;

    /* renamed from: a1 */
    public final Drawable f61334a1;

    /* renamed from: a2 */
    public final float[] f61335a2;

    /* renamed from: a3 */
    public float f61336a3;

    public C1483yk(Drawable drawable, Drawable drawable2) {
        this.f61333a0 = drawable.getConstantState().newDrawable().mutate();
        Drawable drawableMutate = drawable2.getConstantState().newDrawable().mutate();
        this.f61334a1 = drawableMutate;
        drawableMutate.setAlpha(0);
        this.f61335a2 = new float[2];
    }

    /* renamed from: a0 */
    public final void m215296a0(float f) {
        if (this.f61336a3 != f) {
            this.f61336a3 = f;
            float[] fArr = this.f61335a2;
            AbstractC1117qo.m214409a2(f, fArr);
            this.f61333a0.setAlpha((int) (fArr[0] * 255.0f));
            this.f61334a1.setAlpha((int) (fArr[1] * 255.0f));
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        this.f61333a0.draw(canvas);
        this.f61334a1.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return Math.max(this.f61333a0.getIntrinsicHeight(), this.f61334a1.getIntrinsicHeight());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return Math.max(this.f61333a0.getIntrinsicWidth(), this.f61334a1.getIntrinsicWidth());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumHeight() {
        return Math.max(this.f61333a0.getMinimumHeight(), this.f61334a1.getMinimumHeight());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getMinimumWidth() {
        return Math.max(this.f61333a0.getMinimumWidth(), this.f61334a1.getMinimumWidth());
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return this.f61333a0.isStateful() || this.f61334a1.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        float f = this.f61336a3;
        Drawable drawable = this.f61334a1;
        Drawable drawable2 = this.f61333a0;
        if (f <= 0.5f) {
            drawable2.setAlpha(i);
            drawable.setAlpha(0);
        } else {
            drawable2.setAlpha(0);
            drawable.setAlpha(i);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.f61333a0.setBounds(i, i2, i3, i4);
        this.f61334a1.setBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f61333a0.setColorFilter(colorFilter);
        this.f61334a1.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setState(int[] iArr) {
        return this.f61333a0.setState(iArr) || this.f61334a1.setState(iArr);
    }
}
