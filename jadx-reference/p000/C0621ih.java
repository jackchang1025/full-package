package p000;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ih */
/* loaded from: classes2.dex */
public final class C0621ih extends AbstractC1298uf {

    /* renamed from: a2 */
    public int f56887a2;

    /* renamed from: a3 */
    public float f56888a3;

    /* renamed from: a4 */
    public float f56889a4;

    /* renamed from: a5 */
    public float f56890a5;

    public C0621ih(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
        this.f56887a2 = 1;
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a0 */
    public final void mo213159a0(Canvas canvas, Rect rect, float f) {
        float fWidth = rect.width() / m213165a6();
        float fHeight = rect.height() / m213165a6();
        CircularProgressIndicatorSpec circularProgressIndicatorSpec = (CircularProgressIndicatorSpec) this.f60420a0;
        float f2 = (circularProgressIndicatorSpec.f49687a6 / 2.0f) + circularProgressIndicatorSpec.f49688a7;
        canvas.translate((f2 * fWidth) + rect.left, (f2 * fHeight) + rect.top);
        canvas.scale(fWidth, fHeight);
        canvas.rotate(-90.0f);
        float f3 = -f2;
        canvas.clipRect(f3, f3, f2, f2);
        this.f56887a2 = circularProgressIndicatorSpec.f49689a8 == 0 ? 1 : -1;
        this.f56888a3 = circularProgressIndicatorSpec.f55693a0 * f;
        this.f56889a4 = circularProgressIndicatorSpec.f55694a1 * f;
        this.f56890a5 = (circularProgressIndicatorSpec.f49687a6 - r9) / 2.0f;
        if ((this.f60421a1.m214796a3() && circularProgressIndicatorSpec.f55697a4 == 2) || (this.f60421a1.m214795a2() && circularProgressIndicatorSpec.f55698a5 == 1)) {
            this.f56890a5 = (((1.0f - f) * circularProgressIndicatorSpec.f55693a0) / 2.0f) + this.f56890a5;
        } else if ((this.f60421a1.m214796a3() && circularProgressIndicatorSpec.f55697a4 == 1) || (this.f60421a1.m214795a2() && circularProgressIndicatorSpec.f55698a5 == 2)) {
            this.f56890a5 -= ((1.0f - f) * circularProgressIndicatorSpec.f55693a0) / 2.0f;
        }
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a1 */
    public final void mo213160a1(Canvas canvas, Paint paint, float f, float f2, int i) {
        if (f == f2) {
            return;
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setStrokeWidth(this.f56888a3);
        float f3 = this.f56887a2;
        float f4 = f * 360.0f * f3;
        float f5 = (f2 >= f ? f2 - f : (1.0f + f2) - f) * 360.0f * f3;
        float f6 = this.f56890a5;
        float f7 = -f6;
        canvas.drawArc(new RectF(f7, f7, f6, f6), f4, f5, false, paint);
        if (this.f56889a4 <= 0.0f || Math.abs(f5) >= 360.0f) {
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        m213164a5(canvas, paint, this.f56888a3, this.f56889a4, f4);
        m213164a5(canvas, paint, this.f56888a3, this.f56889a4, f4 + f5);
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a2 */
    public final void mo213161a2(Canvas canvas, Paint paint) {
        int iM213561a8 = kj1.m213561a8(((CircularProgressIndicatorSpec) this.f60420a0).f55696a3, this.f60421a1.f60300a9);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setAntiAlias(true);
        paint.setColor(iM213561a8);
        paint.setStrokeWidth(this.f56888a3);
        float f = this.f56890a5;
        float f2 = -f;
        canvas.drawArc(new RectF(f2, f2, f, f), 0.0f, 360.0f, false, paint);
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a3 */
    public final int mo213162a3() {
        return m213165a6();
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a4 */
    public final int mo213163a4() {
        return m213165a6();
    }

    /* renamed from: a5 */
    public final void m213164a5(Canvas canvas, Paint paint, float f, float f2, float f3) {
        canvas.save();
        canvas.rotate(f3);
        float f4 = this.f56890a5;
        float f5 = f / 2.0f;
        canvas.drawRoundRect(new RectF(f4 - f5, f2, f4 + f5, -f2), f2, f2, paint);
        canvas.restore();
    }

    /* renamed from: a6 */
    public final int m213165a6() {
        AbstractC0411dd abstractC0411dd = this.f60420a0;
        return (((CircularProgressIndicatorSpec) abstractC0411dd).f49688a7 * 2) + ((CircularProgressIndicatorSpec) abstractC0411dd).f49687a6;
    }
}
