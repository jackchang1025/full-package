package p000;

import android.view.animation.Interpolator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class oc0 implements Interpolator {

    /* renamed from: a0 */
    public final float[] f58779a0;

    /* renamed from: a1 */
    public final float f58780a1;

    public oc0(float[] fArr) {
        this.f58779a0 = fArr;
        this.f58780a1 = 1.0f / (fArr.length - 1);
    }

    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        float[] fArr = this.f58779a0;
        int iMin = Math.min((int) ((fArr.length - 1) * f), fArr.length - 2);
        float f2 = this.f58780a1;
        float f3 = (f - (iMin * f2)) / f2;
        float f4 = fArr[iMin];
        return AbstractC0003a2.m19a0(fArr[iMin + 1], f4, f3, f4);
    }
}
