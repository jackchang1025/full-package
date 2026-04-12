package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class cd0 extends C1351vv {

    /* renamed from: b4 */
    public final float f46105b4;

    public cd0(float f) {
        super(0);
        this.f46105b4 = f - 0.001f;
    }

    @Override // p000.C1351vv
    /* renamed from: a7 */
    public final void mo210827a7(float f, float f2, float f3, k01 k01Var) {
        double d = this.f46105b4;
        float fSqrt = (float) ((Math.sqrt(2.0d) * d) / 2.0d);
        float fSqrt2 = (float) Math.sqrt(Math.pow(d, 2.0d) - Math.pow(fSqrt, 2.0d));
        k01Var.m213400a4(f2 - fSqrt, ((float) (-((Math.sqrt(2.0d) * d) - d))) + fSqrt2, 270.0f, 0.0f);
        k01Var.m213399a3(f2, (float) (-((Math.sqrt(2.0d) * d) - d)));
        k01Var.m213399a3(f2 + fSqrt, ((float) (-((Math.sqrt(2.0d) * d) - d))) + fSqrt2);
    }
}
