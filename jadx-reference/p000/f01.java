package p000;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class f01 extends j01 {

    /* renamed from: a2 */
    public final h01 f56128a2;

    /* renamed from: a3 */
    public final float f56129a3;

    /* renamed from: a4 */
    public final float f56130a4;

    public f01(h01 h01Var, float f, float f2) {
        this.f56128a2 = h01Var;
        this.f56129a3 = f;
        this.f56130a4 = f2;
    }

    @Override // p000.j01
    /* renamed from: a0 */
    public final void mo212547a0(Matrix matrix, yz0 yz0Var, int i, Canvas canvas) {
        h01 h01Var = this.f56128a2;
        float f = h01Var.f56593a2;
        float f2 = this.f56130a4;
        float f3 = h01Var.f56592a1;
        float f4 = this.f56129a3;
        RectF rectF = new RectF(0.0f, 0.0f, (float) Math.hypot(f - f2, f3 - f4), 0.0f);
        Matrix matrix2 = this.f57254a0;
        matrix2.set(matrix);
        matrix2.preTranslate(f4, f2);
        matrix2.preRotate(m212731a1());
        yz0Var.getClass();
        rectF.bottom += i;
        rectF.offset(0.0f, -i);
        int i2 = yz0Var.f61415a5;
        int[] iArr = yz0.f61406a8;
        iArr[0] = i2;
        iArr[1] = yz0Var.f61414a4;
        iArr[2] = yz0Var.f61413a3;
        Paint paint = yz0Var.f61412a2;
        float f5 = rectF.left;
        paint.setShader(new LinearGradient(f5, rectF.top, f5, rectF.bottom, iArr, yz0.f61407a9, Shader.TileMode.CLAMP));
        canvas.save();
        canvas.concat(matrix2);
        canvas.drawRect(rectF, paint);
        canvas.restore();
    }

    /* renamed from: a1 */
    public final float m212731a1() {
        h01 h01Var = this.f56128a2;
        return (float) Math.toDegrees(Math.atan((h01Var.f56593a2 - this.f56130a4) / (h01Var.f56592a1 - this.f56129a3)));
    }
}
