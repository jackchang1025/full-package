package p000;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class g01 extends i01 {

    /* renamed from: a7 */
    public static final RectF f56357a7 = new RectF();

    /* renamed from: a1 */
    public final float f56358a1;

    /* renamed from: a2 */
    public final float f56359a2;

    /* renamed from: a3 */
    public final float f56360a3;

    /* renamed from: a4 */
    public final float f56361a4;

    /* renamed from: a5 */
    public float f56362a5;

    /* renamed from: a6 */
    public float f56363a6;

    public g01(float f, float f2, float f3, float f4) {
        this.f56358a1 = f;
        this.f56359a2 = f2;
        this.f56360a3 = f3;
        this.f56361a4 = f4;
    }

    @Override // p000.i01
    /* renamed from: a0 */
    public final void mo212877a0(Matrix matrix, Path path) {
        Matrix matrix2 = this.f56780a0;
        matrix.invert(matrix2);
        path.transform(matrix2);
        float f = this.f56360a3;
        float f2 = this.f56361a4;
        RectF rectF = f56357a7;
        rectF.set(this.f56358a1, this.f56359a2, f, f2);
        path.arcTo(rectF, this.f56362a5, this.f56363a6, false);
        path.transform(matrix);
    }
}
