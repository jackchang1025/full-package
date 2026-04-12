package p000;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ei */
/* loaded from: classes2.dex */
public final class C0457ei extends Drawable {

    /* renamed from: a1 */
    public final Paint f56017a1;

    /* renamed from: a7 */
    public float f56023a7;

    /* renamed from: a8 */
    public int f56024a8;

    /* renamed from: a9 */
    public int f56025a9;

    /* renamed from: b0 */
    public int f56026b0;

    /* renamed from: b1 */
    public int f56027b1;

    /* renamed from: b2 */
    public int f56028b2;

    /* renamed from: b4 */
    public a01 f56030b4;

    /* renamed from: b5 */
    public ColorStateList f56031b5;

    /* renamed from: a0 */
    public final c01 f56016a0 = b01.f45676a0;

    /* renamed from: a2 */
    public final Path f56018a2 = new Path();

    /* renamed from: a3 */
    public final Rect f56019a3 = new Rect();

    /* renamed from: a4 */
    public final RectF f56020a4 = new RectF();

    /* renamed from: a5 */
    public final RectF f56021a5 = new RectF();

    /* renamed from: a6 */
    public final C1245t3 f56022a6 = new C1245t3(this);

    /* renamed from: b3 */
    public boolean f56029b3 = true;

    public C0457ei(a01 a01Var) {
        this.f56030b4 = a01Var;
        Paint paint = new Paint(1);
        this.f56017a1 = paint;
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        boolean z = this.f56029b3;
        Rect rect = this.f56019a3;
        Paint paint = this.f56017a1;
        if (z) {
            copyBounds(rect);
            float fHeight = this.f56023a7 / rect.height();
            paint.setShader(new LinearGradient(0.0f, rect.top, 0.0f, rect.bottom, new int[]{AbstractC0724jn.m213332a2(this.f56024a8, this.f56028b2), AbstractC0724jn.m213332a2(this.f56025a9, this.f56028b2), AbstractC0724jn.m213332a2(AbstractC0724jn.m213334a4(this.f56025a9, 0), this.f56028b2), AbstractC0724jn.m213332a2(AbstractC0724jn.m213334a4(this.f56027b1, 0), this.f56028b2), AbstractC0724jn.m213332a2(this.f56027b1, this.f56028b2), AbstractC0724jn.m213332a2(this.f56026b0, this.f56028b2)}, new float[]{0.0f, fHeight, 0.5f, 0.5f, 1.0f - fHeight, 1.0f}, Shader.TileMode.CLAMP));
            this.f56029b3 = false;
        }
        float strokeWidth = paint.getStrokeWidth() / 2.0f;
        copyBounds(rect);
        RectF rectF = this.f56020a4;
        rectF.set(rect);
        InterfaceC0909nd interfaceC0909nd = this.f56030b4.f11a4;
        Rect bounds = getBounds();
        RectF rectF2 = this.f56021a5;
        rectF2.set(bounds);
        float fMin = Math.min(interfaceC0909nd.mo212732a0(rectF2), rectF.width() / 2.0f);
        a01 a01Var = this.f56030b4;
        rectF2.set(getBounds());
        if (a01Var.m16a5(rectF2)) {
            rectF.inset(strokeWidth, strokeWidth);
            canvas.drawRoundRect(rectF, fMin, fMin, paint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.f56022a6;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return this.f56023a7 > 0.0f ? -3 : -2;
    }

    @Override // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        a01 a01Var = this.f56030b4;
        Rect bounds = getBounds();
        RectF rectF = this.f56021a5;
        rectF.set(bounds);
        if (a01Var.m16a5(rectF)) {
            InterfaceC0909nd interfaceC0909nd = this.f56030b4.f11a4;
            rectF.set(getBounds());
            outline.setRoundRect(getBounds(), interfaceC0909nd.mo212732a0(rectF));
            return;
        }
        Rect rect = this.f56019a3;
        copyBounds(rect);
        RectF rectF2 = this.f56020a4;
        rectF2.set(rect);
        a01 a01Var2 = this.f56030b4;
        c01 c01Var = this.f56016a0;
        Path path = this.f56018a2;
        c01Var.m210755a0(a01Var2, 1.0f, rectF2, null, path);
        AbstractC1117qo.m214460f9(outline, path);
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean getPadding(Rect rect) {
        a01 a01Var = this.f56030b4;
        Rect bounds = getBounds();
        RectF rectF = this.f56021a5;
        rectF.set(bounds);
        if (!a01Var.m16a5(rectF)) {
            return true;
        }
        int iRound = Math.round(this.f56023a7);
        rect.set(iRound, iRound, iRound, iRound);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        ColorStateList colorStateList = this.f56031b5;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        this.f56029b3 = true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onStateChange(int[] iArr) {
        int colorForState;
        ColorStateList colorStateList = this.f56031b5;
        if (colorStateList != null && (colorForState = colorStateList.getColorForState(iArr, this.f56028b2)) != this.f56028b2) {
            this.f56029b3 = true;
            this.f56028b2 = colorForState;
        }
        if (this.f56029b3) {
            invalidateSelf();
        }
        return this.f56029b3;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.f56017a1.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.f56017a1.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
