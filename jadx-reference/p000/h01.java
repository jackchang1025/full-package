package p000;

import android.graphics.Matrix;
import android.graphics.Path;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class h01 extends i01 {

    /* renamed from: a1 */
    public float f56592a1;

    /* renamed from: a2 */
    public float f56593a2;

    @Override // p000.i01
    /* renamed from: a0 */
    public final void mo212877a0(Matrix matrix, Path path) {
        Matrix matrix2 = this.f56780a0;
        matrix.invert(matrix2);
        path.transform(matrix2);
        path.lineTo(this.f56592a1, this.f56593a2);
        path.transform(matrix);
    }
}
