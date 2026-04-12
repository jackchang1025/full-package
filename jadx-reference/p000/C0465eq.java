package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: eq */
/* loaded from: classes2.dex */
public final class C0465eq extends C1351vv implements Cloneable {

    /* renamed from: b4 */
    public float f56094b4;

    /* renamed from: b5 */
    public float f56095b5;

    /* renamed from: b6 */
    public float f56096b6;

    /* renamed from: b7 */
    public float f56097b7;

    /* renamed from: b8 */
    public float f56098b8;

    /* renamed from: b9 */
    public float f56099b9;

    @Override // p000.C1351vv
    /* renamed from: a7 */
    public final void mo210827a7(float f, float f2, float f3, k01 k01Var) {
        float f4;
        float f5;
        float f6 = this.f56096b6;
        if (f6 == 0.0f) {
            k01Var.m213399a3(f, 0.0f);
            return;
        }
        float f7 = ((this.f56095b5 * 2.0f) + f6) / 2.0f;
        float f8 = f3 * this.f56094b4;
        float f9 = f2 + this.f56098b8;
        float fM19a0 = AbstractC0003a2.m19a0(1.0f, f3, f7, this.f56097b7 * f3);
        if (fM19a0 / f7 >= 1.0f) {
            k01Var.m213399a3(f, 0.0f);
            return;
        }
        float f10 = this.f56099b9;
        float f11 = f10 * f3;
        boolean z = f10 == -1.0f || Math.abs((f10 * 2.0f) - f6) < 0.1f;
        if (z) {
            f4 = fM19a0;
            f5 = 0.0f;
        } else {
            f5 = 1.75f;
            f4 = 0.0f;
        }
        float f12 = f7 + f8;
        float f13 = f4 + f8;
        float fSqrt = (float) Math.sqrt((f12 * f12) - (f13 * f13));
        float f14 = f9 - fSqrt;
        float f15 = f9 + fSqrt;
        float degrees = (float) Math.toDegrees(Math.atan(fSqrt / f13));
        float f16 = (90.0f - degrees) + f5;
        k01Var.m213399a3(f14, 0.0f);
        float f17 = f14 - f8;
        float f18 = f14 + f8;
        float f19 = f8 * 2.0f;
        k01Var.m213396a0(f17, 0.0f, f18, f19, 270.0f, degrees);
        if (z) {
            k01Var.m213396a0(f9 - f7, (-f7) - f4, f9 + f7, f7 - f4, 180.0f - f16, (f16 * 2.0f) - 180.0f);
        } else {
            float f20 = this.f56095b5;
            float f21 = f11 * 2.0f;
            float f22 = f20 + f21;
            float f23 = f9 - f7;
            k01Var.m213396a0(f23, -(f11 + f20), f22 + f23, f20 + f11, 180.0f - f16, ((f16 * 2.0f) - 180.0f) / 2.0f);
            float f24 = f9 + f7;
            float f25 = this.f56095b5;
            k01Var.m213399a3(f24 - ((f25 / 2.0f) + f11), f25 + f11);
            float f26 = this.f56095b5;
            k01Var.m213396a0(f24 - (f21 + f26), -(f11 + f26), f24, f26 + f11, 90.0f, f16 - 90.0f);
        }
        k01Var.m213396a0(f15 - f8, 0.0f, f15 + f8, f19, 270.0f - degrees, degrees);
        k01Var.m213399a3(f, 0.0f);
    }

    /* renamed from: b2 */
    public final void m212720b2(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.f56097b7 = f;
    }
}
