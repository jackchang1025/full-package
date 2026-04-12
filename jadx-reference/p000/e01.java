package p000;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class e01 extends j01 {

    /* renamed from: a2 */
    public final g01 f55895a2;

    public e01(g01 g01Var) {
        this.f55895a2 = g01Var;
    }

    @Override // p000.j01
    /* renamed from: a0 */
    public final void mo212547a0(Matrix matrix, yz0 yz0Var, int i, Canvas canvas) {
        float f;
        g01 g01Var = this.f55895a2;
        float f2 = g01Var.f56362a5;
        float f3 = g01Var.f56363a6;
        RectF rectF = new RectF(g01Var.f56358a1, g01Var.f56359a2, g01Var.f56360a3, g01Var.f56361a4);
        Paint paint = yz0Var.f61411a1;
        boolean z = f3 < 0.0f;
        Path path = yz0Var.f61416a6;
        int[] iArr = yz0.f61408b0;
        if (z) {
            iArr[0] = 0;
            iArr[1] = yz0Var.f61415a5;
            iArr[2] = yz0Var.f61414a4;
            iArr[3] = yz0Var.f61413a3;
            f = 0.0f;
        } else {
            path.rewind();
            f = 0.0f;
            path.moveTo(rectF.centerX(), rectF.centerY());
            path.arcTo(rectF, f2, f3);
            path.close();
            float f4 = -i;
            rectF.inset(f4, f4);
            iArr[0] = 0;
            iArr[1] = yz0Var.f61413a3;
            iArr[2] = yz0Var.f61414a4;
            iArr[3] = yz0Var.f61415a5;
        }
        float fWidth = rectF.width() / 2.0f;
        if (fWidth <= f) {
            return;
        }
        float f5 = 1.0f - (i / fWidth);
        float[] fArr = yz0.f61409b1;
        fArr[1] = f5;
        fArr[2] = ((1.0f - f5) / 2.0f) + f5;
        paint.setShader(new RadialGradient(rectF.centerX(), rectF.centerY(), fWidth, iArr, fArr, Shader.TileMode.CLAMP));
        canvas.save();
        canvas.concat(matrix);
        canvas.scale(1.0f, rectF.height() / rectF.width());
        if (!z) {
            canvas.clipPath(path, Region.Op.DIFFERENCE);
            canvas.drawPath(path, yz0Var.f61417a7);
        }
        canvas.drawArc(rectF, f2, f3, true, paint);
        canvas.restore();
    }
}
