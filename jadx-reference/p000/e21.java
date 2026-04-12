package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e21 implements c21 {

    /* renamed from: a0 */
    public float f55908a0;

    /* renamed from: a1 */
    public float f55909a1;

    /* renamed from: a2 */
    public float f55910a2;

    /* renamed from: a3 */
    public float f55911a3;

    /* renamed from: a4 */
    public float f55912a4;

    /* renamed from: a5 */
    public float f55913a5;

    /* renamed from: a6 */
    public float f55914a6;

    /* renamed from: a7 */
    public float f55915a7;

    /* renamed from: a8 */
    public float f55916a8;

    /* renamed from: a9 */
    public int f55917a9;

    /* renamed from: b0 */
    public boolean f55918b0;

    /* renamed from: b1 */
    public float f55919b1;

    /* renamed from: b2 */
    public float f55920b2;

    @Override // p000.c21
    /* renamed from: a0 */
    public final boolean mo210757a0() {
        return mo210758a1() < 1.0E-5f && Math.abs(this.f55916a8 - this.f55920b2) < 1.0E-5f;
    }

    @Override // p000.c21
    /* renamed from: a1 */
    public final float mo210758a1() {
        return this.f55918b0 ? -m212654a2(this.f55920b2) : m212654a2(this.f55920b2);
    }

    /* renamed from: a2 */
    public final float m212654a2(float f) {
        float f2;
        float f3;
        float f4 = this.f55911a3;
        if (f <= f4) {
            f2 = this.f55908a0;
            f3 = this.f55909a1;
        } else {
            int i = this.f55917a9;
            if (i == 1) {
                return 0.0f;
            }
            f -= f4;
            f4 = this.f55912a4;
            if (f >= f4) {
                if (i == 2) {
                    return this.f55915a7;
                }
                float f5 = f - f4;
                float f6 = this.f55913a5;
                if (f5 >= f6) {
                    return this.f55916a8;
                }
                float f7 = this.f55910a2;
                return f7 - ((f5 * f7) / f6);
            }
            f2 = this.f55909a1;
            f3 = this.f55910a2;
        }
        return (((f3 - f2) * f) / f4) + f2;
    }

    /* renamed from: a3 */
    public final void m212655a3(float f, float f2, float f3, float f4, float f5) {
        if (f == 0.0f) {
            f = 1.0E-4f;
        }
        this.f55908a0 = f;
        float f6 = f / f3;
        float f7 = (f6 * f) / 2.0f;
        if (f < 0.0f) {
            float fSqrt = (float) Math.sqrt((f2 - ((((-f) / f3) * f) / 2.0f)) * f3);
            if (fSqrt < f4) {
                this.f55917a9 = 2;
                this.f55908a0 = f;
                this.f55909a1 = fSqrt;
                this.f55910a2 = 0.0f;
                float f8 = (fSqrt - f) / f3;
                this.f55911a3 = f8;
                this.f55912a4 = fSqrt / f3;
                this.f55914a6 = ((f + fSqrt) * f8) / 2.0f;
                this.f55915a7 = f2;
                this.f55916a8 = f2;
                return;
            }
            this.f55917a9 = 3;
            this.f55908a0 = f;
            this.f55909a1 = f4;
            this.f55910a2 = f4;
            float f9 = (f4 - f) / f3;
            this.f55911a3 = f9;
            float f10 = f4 / f3;
            this.f55913a5 = f10;
            float f11 = ((f + f4) * f9) / 2.0f;
            float f12 = (f10 * f4) / 2.0f;
            this.f55912a4 = ((f2 - f11) - f12) / f4;
            this.f55914a6 = f11;
            this.f55915a7 = f2 - f12;
            this.f55916a8 = f2;
            return;
        }
        if (f7 >= f2) {
            this.f55917a9 = 1;
            this.f55908a0 = f;
            this.f55909a1 = 0.0f;
            this.f55914a6 = f2;
            this.f55911a3 = (2.0f * f2) / f;
            return;
        }
        float f13 = f2 - f7;
        float f14 = f13 / f;
        if (f14 + f6 < f5) {
            this.f55917a9 = 2;
            this.f55908a0 = f;
            this.f55909a1 = f;
            this.f55910a2 = 0.0f;
            this.f55914a6 = f13;
            this.f55915a7 = f2;
            this.f55911a3 = f14;
            this.f55912a4 = f6;
            return;
        }
        float fSqrt2 = (float) Math.sqrt(((f * f) / 2.0f) + (f3 * f2));
        float f15 = (fSqrt2 - f) / f3;
        this.f55911a3 = f15;
        float f16 = fSqrt2 / f3;
        this.f55912a4 = f16;
        if (fSqrt2 < f4) {
            this.f55917a9 = 2;
            this.f55908a0 = f;
            this.f55909a1 = fSqrt2;
            this.f55910a2 = 0.0f;
            this.f55911a3 = f15;
            this.f55912a4 = f16;
            this.f55914a6 = ((f + fSqrt2) * f15) / 2.0f;
            this.f55915a7 = f2;
            return;
        }
        this.f55917a9 = 3;
        this.f55908a0 = f;
        this.f55909a1 = f4;
        this.f55910a2 = f4;
        float f17 = (f4 - f) / f3;
        this.f55911a3 = f17;
        float f18 = f4 / f3;
        this.f55913a5 = f18;
        float f19 = ((f + f4) * f17) / 2.0f;
        float f20 = (f18 * f4) / 2.0f;
        this.f55912a4 = ((f2 - f19) - f20) / f4;
        this.f55914a6 = f19;
        this.f55915a7 = f2 - f20;
        this.f55916a8 = f2;
    }

    @Override // p000.c21
    public final float getInterpolation(float f) {
        float f2;
        float f3 = this.f55911a3;
        if (f <= f3) {
            float f4 = this.f55908a0;
            f2 = ((((this.f55909a1 - f4) * f) * f) / (f3 * 2.0f)) + (f4 * f);
        } else {
            int i = this.f55917a9;
            if (i == 1) {
                f2 = this.f55914a6;
            } else {
                float f5 = f - f3;
                float f6 = this.f55912a4;
                if (f5 < f6) {
                    float f7 = this.f55914a6;
                    float f8 = this.f55909a1;
                    f2 = ((((this.f55910a2 - f8) * f5) * f5) / (f6 * 2.0f)) + (f8 * f5) + f7;
                } else if (i == 2) {
                    f2 = this.f55915a7;
                } else {
                    float f9 = f5 - f6;
                    float f10 = this.f55913a5;
                    if (f9 <= f10) {
                        float f11 = this.f55915a7;
                        float f12 = this.f55910a2 * f9;
                        f2 = (f11 + f12) - ((f12 * f9) / (f10 * 2.0f));
                    } else {
                        f2 = this.f55916a8;
                    }
                }
            }
        }
        this.f55920b2 = f;
        return this.f55918b0 ? this.f55919b1 - f2 : this.f55919b1 + f2;
    }
}
