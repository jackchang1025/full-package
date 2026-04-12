package p000;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ra0 extends AbstractC1298uf {

    /* renamed from: a2 */
    public float f59656a2;

    /* renamed from: a3 */
    public float f59657a3;

    /* renamed from: a4 */
    public float f59658a4;

    /* renamed from: a5 */
    public Path f59659a5;

    public ra0(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(linearProgressIndicatorSpec);
        this.f59656a2 = 300.0f;
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a0 */
    public final void mo213159a0(Canvas canvas, Rect rect, float f) {
        this.f59656a2 = rect.width();
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.f60420a0;
        float f2 = linearProgressIndicatorSpec.f55693a0;
        canvas.translate((rect.width() / 2.0f) + rect.left, Math.max(0.0f, (rect.height() - linearProgressIndicatorSpec.f55693a0) / 2.0f) + (rect.height() / 2.0f) + rect.top);
        if (linearProgressIndicatorSpec.f49693a8) {
            canvas.scale(-1.0f, 1.0f);
        }
        if ((this.f60421a1.m214796a3() && linearProgressIndicatorSpec.f55697a4 == 1) || (this.f60421a1.m214795a2() && linearProgressIndicatorSpec.f55698a5 == 2)) {
            canvas.scale(1.0f, -1.0f);
        }
        if (this.f60421a1.m214796a3() || this.f60421a1.m214795a2()) {
            canvas.translate(0.0f, ((f - 1.0f) * linearProgressIndicatorSpec.f55693a0) / 2.0f);
        }
        float f3 = this.f59656a2;
        canvas.clipRect((-f3) / 2.0f, (-f2) / 2.0f, f3 / 2.0f, f2 / 2.0f);
        this.f59657a3 = linearProgressIndicatorSpec.f55693a0 * f;
        this.f59658a4 = linearProgressIndicatorSpec.f55694a1 * f;
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a1 */
    public final void mo213160a1(Canvas canvas, Paint paint, float f, float f2, int i) {
        if (f == f2) {
            return;
        }
        float f3 = this.f59656a2;
        float f4 = (-f3) / 2.0f;
        float f5 = ((f * f3) + f4) - (this.f59658a4 * 2.0f);
        float f6 = (f2 * f3) + f4;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(i);
        canvas.save();
        canvas.clipPath(this.f59659a5);
        float f7 = this.f59657a3;
        RectF rectF = new RectF(f5, (-f7) / 2.0f, f6, f7 / 2.0f);
        float f8 = this.f59658a4;
        canvas.drawRoundRect(rectF, f8, f8, paint);
        canvas.restore();
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a2 */
    public final void mo213161a2(Canvas canvas, Paint paint) {
        int iM213561a8 = kj1.m213561a8(((LinearProgressIndicatorSpec) this.f60420a0).f55696a3, this.f60421a1.f60300a9);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(iM213561a8);
        Path path = new Path();
        this.f59659a5 = path;
        float f = this.f59656a2;
        float f2 = this.f59657a3;
        RectF rectF = new RectF((-f) / 2.0f, (-f2) / 2.0f, f / 2.0f, f2 / 2.0f);
        float f3 = this.f59658a4;
        path.addRoundRect(rectF, f3, f3, Path.Direction.CCW);
        canvas.drawPath(this.f59659a5, paint);
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a3 */
    public final int mo213162a3() {
        return ((LinearProgressIndicatorSpec) this.f60420a0).f55693a0;
    }

    @Override // p000.AbstractC1298uf
    /* renamed from: a4 */
    public final int mo213163a4() {
        return -1;
    }
}
