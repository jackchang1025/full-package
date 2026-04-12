package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class o11 implements c21 {

    /* renamed from: a0 */
    public double f58709a0;

    /* renamed from: a1 */
    public double f58710a1;

    /* renamed from: a2 */
    public double f58711a2;

    /* renamed from: a3 */
    public float f58712a3;

    /* renamed from: a4 */
    public float f58713a4;

    /* renamed from: a5 */
    public float f58714a5;

    /* renamed from: a6 */
    public float f58715a6;

    /* renamed from: a7 */
    public float f58716a7;

    /* renamed from: a8 */
    public int f58717a8;

    @Override // p000.c21
    /* renamed from: a0 */
    public final boolean mo210757a0() {
        double d = this.f58713a4 - this.f58711a2;
        double d2 = this.f58710a1;
        double d3 = this.f58714a5;
        return Math.sqrt((((d2 * d) * d) + ((d3 * d3) * ((double) this.f58715a6))) / d2) <= ((double) this.f58716a7);
    }

    @Override // p000.c21
    /* renamed from: a1 */
    public final float mo210758a1() {
        return 0.0f;
    }

    @Override // p000.c21
    public final float getInterpolation(float f) {
        double d = f - this.f58712a3;
        double d2 = this.f58710a1;
        double d3 = this.f58709a0;
        int iSqrt = (int) ((9.0d / ((Math.sqrt(d2 / this.f58715a6) * d) * 4.0d)) + 1.0d);
        double d4 = d / iSqrt;
        int i = 0;
        while (i < iSqrt) {
            double d5 = this.f58713a4;
            double d6 = this.f58711a2;
            double d7 = d4;
            double d8 = this.f58714a5;
            double d9 = this.f58715a6;
            double d10 = ((((((-d2) * (d5 - d6)) - (d3 * d8)) / d9) * d7) / 2.0d) + d8;
            double d11 = ((((-((((d7 * d10) / 2.0d) + d5) - d6)) * d2) - (d10 * d3)) / d9) * d7;
            double d12 = (d11 / 2.0d) + d8;
            float f2 = (float) (d8 + d11);
            this.f58714a5 = f2;
            float f3 = (float) ((d12 * d7) + d5);
            this.f58713a4 = f3;
            int i2 = this.f58717a8;
            if (i2 > 0) {
                if (f3 < 0.0f && (i2 & 1) == 1) {
                    this.f58713a4 = -f3;
                    this.f58714a5 = -f2;
                }
                float f4 = this.f58713a4;
                if (f4 > 1.0f && (i2 & 2) == 2) {
                    this.f58713a4 = 2.0f - f4;
                    this.f58714a5 = -this.f58714a5;
                }
            }
            i++;
            d4 = d7;
        }
        this.f58712a3 = f;
        return this.f58713a4;
    }
}
