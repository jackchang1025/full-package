package p000;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class j71 extends ce0 implements t51 {

    /* renamed from: c3 */
    public CharSequence f57289c3;

    /* renamed from: c4 */
    public final Context f57290c4;

    /* renamed from: c5 */
    public final Paint.FontMetrics f57291c5;

    /* renamed from: c6 */
    public final u51 f57292c6;

    /* renamed from: c7 */
    public final bi0 f57293c7;

    /* renamed from: c8 */
    public final Rect f57294c8;

    /* renamed from: c9 */
    public int f57295c9;

    /* renamed from: d0 */
    public int f57296d0;

    /* renamed from: d1 */
    public int f57297d1;

    /* renamed from: d2 */
    public int f57298d2;

    /* renamed from: d3 */
    public int f57299d3;

    /* renamed from: d4 */
    public int f57300d4;

    /* renamed from: d5 */
    public float f57301d5;

    /* renamed from: d6 */
    public float f57302d6;

    /* renamed from: d7 */
    public float f57303d7;

    /* renamed from: d8 */
    public float f57304d8;

    public j71(Context context, int i) {
        super(context, null, 0, i);
        this.f57291c5 = new Paint.FontMetrics();
        u51 u51Var = new u51(this);
        this.f57292c6 = u51Var;
        this.f57293c7 = new bi0(2, this);
        this.f57294c8 = new Rect();
        this.f57301d5 = 1.0f;
        this.f57302d6 = 1.0f;
        this.f57303d7 = 0.5f;
        this.f57304d8 = 1.0f;
        this.f57290c4 = context;
        float f = context.getResources().getDisplayMetrics().density;
        TextPaint textPaint = u51Var.f60328a0;
        textPaint.density = f;
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    /* renamed from: c2 */
    public final float m213217c2() {
        int i;
        Rect rect = this.f57294c8;
        if (((rect.right - getBounds().right) - this.f57300d4) - this.f57298d2 < 0) {
            i = ((rect.right - getBounds().right) - this.f57300d4) - this.f57298d2;
        } else {
            if (((rect.left - getBounds().left) - this.f57300d4) + this.f57298d2 <= 0) {
                return 0.0f;
            }
            i = ((rect.left - getBounds().left) - this.f57300d4) + this.f57298d2;
        }
        return i;
    }

    /* renamed from: c3 */
    public final uk0 m213218c3() {
        float f = -m213217c2();
        float fWidth = ((float) (getBounds().width() - (Math.sqrt(2.0d) * this.f57299d3))) / 2.0f;
        return new uk0(new cd0(this.f57299d3), Math.min(Math.max(f, -fWidth), fWidth));
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Canvas canvas2;
        canvas.save();
        float fM213217c2 = m213217c2();
        float f = (float) (-((Math.sqrt(2.0d) * this.f57299d3) - this.f57299d3));
        canvas.scale(this.f57301d5, this.f57302d6, (getBounds().width() * 0.5f) + getBounds().left, (getBounds().height() * this.f57303d7) + getBounds().top);
        canvas.translate(fM213217c2, f);
        super.draw(canvas);
        if (this.f57289c3 == null) {
            canvas2 = canvas;
        } else {
            float fCenterY = getBounds().centerY();
            u51 u51Var = this.f57292c6;
            TextPaint textPaint = u51Var.f60328a0;
            Paint.FontMetrics fontMetrics = this.f57291c5;
            textPaint.getFontMetrics(fontMetrics);
            int i = (int) (fCenterY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f));
            if (u51Var.f60333a5 != null) {
                textPaint.drawableState = getState();
                u51Var.f60333a5.m214488a4(this.f57290c4, u51Var.f60328a0, u51Var.f60329a1);
                textPaint.setAlpha((int) (this.f57304d8 * 255.0f));
            }
            CharSequence charSequence = this.f57289c3;
            canvas2 = canvas;
            canvas2.drawText(charSequence, 0, charSequence.length(), r0.centerX(), i, textPaint);
        }
        canvas2.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) Math.max(this.f57292c6.f60328a0.getTextSize(), this.f57297d1);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        float f = this.f57295c9 * 2;
        CharSequence charSequence = this.f57289c3;
        return (int) Math.max(f + (charSequence == null ? 0.0f : this.f57292c6.m214816a0(charSequence.toString())), this.f57296d0);
    }

    @Override // p000.ce0, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        xg1 xg1VarM17a6 = this.f46107a0.f45837a0.m17a6();
        xg1VarM17a6.f61135b0 = m213218c3();
        setShapeAppearanceModel(xg1VarM17a6.m215177a0());
    }
}
