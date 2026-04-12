package p000;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zs */
/* loaded from: classes2.dex */
public final class C1528zs implements TypeEvaluator {

    /* renamed from: a0 */
    public final float[] f61569a0 = new float[9];

    /* renamed from: a1 */
    public final float[] f61570a1 = new float[9];

    /* renamed from: a2 */
    public final Matrix f61571a2 = new Matrix();

    /* renamed from: a3 */
    public final /* synthetic */ AbstractC1535zy f61572a3;

    public C1528zs(AbstractC1535zy abstractC1535zy) {
        this.f61572a3 = abstractC1535zy;
    }

    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        this.f61572a3.f61626b5 = f;
        float[] fArr = this.f61569a0;
        ((Matrix) obj).getValues(fArr);
        float[] fArr2 = this.f61570a1;
        ((Matrix) obj2).getValues(fArr2);
        for (int i = 0; i < 9; i++) {
            float f2 = fArr2[i];
            float f3 = fArr[i];
            fArr2[i] = AbstractC0003a2.m19a0(f2, f3, f, f3);
        }
        Matrix matrix = this.f61571a2;
        matrix.setValues(fArr2);
        return matrix;
    }
}
