package p000;

import android.graphics.Matrix;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class kd1 extends jd1 {
    @Override // p000.t60
    /* renamed from: d5 */
    public final float mo213494d5(View view) {
        return view.getTransitionAlpha();
    }

    @Override // p000.t60
    /* renamed from: e9 */
    public final void mo213495e9(View view, float f) {
        view.setTransitionAlpha(f);
    }

    @Override // p000.jd1, p000.t60
    /* renamed from: f0 */
    public final void mo213284f0(View view, int i) {
        view.setTransitionVisibility(i);
    }

    @Override // p000.jd1
    /* renamed from: f5 */
    public final void mo213285f5(View view, int i, int i2, int i3, int i4) {
        view.setLeftTopRightBottom(i, i2, i3, i4);
    }

    @Override // p000.jd1
    /* renamed from: f6 */
    public final void mo213286f6(View view, Matrix matrix) {
        view.transformMatrixToGlobal(matrix);
    }

    @Override // p000.jd1
    /* renamed from: f7 */
    public final void mo213287f7(View view, Matrix matrix) {
        view.transformMatrixToLocal(matrix);
    }
}
