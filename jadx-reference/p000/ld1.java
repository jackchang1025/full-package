package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ld1 {

    /* renamed from: b0 */
    public static final ld1 f57886b0;

    /* renamed from: a0 */
    public final float f57887a0;

    /* renamed from: a1 */
    public final float f57888a1;

    /* renamed from: a2 */
    public final float f57889a2;

    /* renamed from: a3 */
    public final float f57890a3;

    /* renamed from: a4 */
    public final float f57891a4;

    /* renamed from: a5 */
    public final float f57892a5;

    /* renamed from: a6 */
    public final float[] f57893a6;

    /* renamed from: a7 */
    public final float f57894a7;

    /* renamed from: a8 */
    public final float f57895a8;

    /* renamed from: a9 */
    public final float f57896a9;

    static {
        float[] fArr = b81.f45731a2;
        float fM210601f5 = (float) ((b81.m210601f5() * 63.66197723675813d) / 100.0d);
        float[][] fArr2 = b81.f45729a0;
        float f = fArr[0];
        float[] fArr3 = fArr2[0];
        float f2 = fArr3[0] * f;
        float f3 = fArr[1];
        float f4 = (fArr3[1] * f3) + f2;
        float f5 = fArr[2];
        float f6 = (fArr3[2] * f5) + f4;
        float[] fArr4 = fArr2[1];
        float f7 = (fArr4[2] * f5) + (fArr4[1] * f3) + (fArr4[0] * f);
        float[] fArr5 = fArr2[2];
        float f8 = (f5 * fArr5[2]) + (f3 * fArr5[1]) + (f * fArr5[0]);
        float f9 = ((double) 1.0f) >= 0.9d ? 0.69f : 0.655f;
        float fExp = (1.0f - (((float) Math.exp(((-fM210601f5) - 42.0f) / 92.0f)) * 0.2777778f)) * 1.0f;
        double d = fExp;
        if (d > 1.0d) {
            fExp = 1.0f;
        } else if (d < 0.0d) {
            fExp = 0.0f;
        }
        float f10 = 1.0f / ((5.0f * fM210601f5) + 1.0f);
        float f11 = f10 * f10 * f10 * f10;
        float f12 = 1.0f - f11;
        float fCbrt = (0.1f * f12 * f12 * ((float) Math.cbrt(fM210601f5 * 5.0d))) + (f11 * fM210601f5);
        float fM210601f52 = b81.m210601f5() / fArr[1];
        double d2 = fM210601f52;
        float fSqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float fPow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float[] fArr6 = {(float) Math.pow(((r2[0] * fCbrt) * f6) / 100.0d, 0.42d), (float) Math.pow(((r2[1] * fCbrt) * f7) / 100.0d, 0.42d), (float) Math.pow(((r2[2] * fCbrt) * f8) / 100.0d, 0.42d)};
        float f13 = fArr6[0];
        float f14 = (f13 * 400.0f) / (f13 + 27.13f);
        float f15 = fArr6[1];
        float f16 = (f15 * 400.0f) / (f15 + 27.13f);
        float f17 = fArr6[2];
        float[] fArr7 = {f14, f16, (400.0f * f17) / (f17 + 27.13f)};
        f57886b0 = new ld1(fM210601f52, ((fArr7[2] * 0.05f) + (fArr7[0] * 2.0f) + fArr7[1]) * fPow, fPow, fPow, f9, 1.0f, new float[]{(((100.0f / f6) * fExp) + 1.0f) - fExp, (((100.0f / f7) * fExp) + 1.0f) - fExp, (((100.0f / f8) * fExp) + 1.0f) - fExp}, fCbrt, (float) Math.pow(fCbrt, 0.25d), fSqrt);
    }

    public ld1(float f, float f2, float f3, float f4, float f5, float f6, float[] fArr, float f7, float f8, float f9) {
        this.f57892a5 = f;
        this.f57887a0 = f2;
        this.f57888a1 = f3;
        this.f57889a2 = f4;
        this.f57890a3 = f5;
        this.f57891a4 = f6;
        this.f57893a6 = fArr;
        this.f57894a7 = f7;
        this.f57895a8 = f8;
        this.f57896a9 = f9;
    }
}
