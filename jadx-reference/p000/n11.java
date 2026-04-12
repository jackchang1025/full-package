package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class n11 {

    /* renamed from: a0 */
    public double f58419a0;

    /* renamed from: a1 */
    public double f58420a1;

    /* renamed from: a2 */
    public boolean f58421a2;

    /* renamed from: a3 */
    public double f58422a3;

    /* renamed from: a4 */
    public double f58423a4;

    /* renamed from: a5 */
    public double f58424a5;

    /* renamed from: a6 */
    public double f58425a6;

    /* renamed from: a7 */
    public double f58426a7;

    /* renamed from: a8 */
    public double f58427a8;

    /* renamed from: a9 */
    public final C1311us f58428a9;

    public n11() {
        this.f58419a0 = Math.sqrt(1500.0d);
        this.f58420a1 = 0.5d;
        this.f58421a2 = false;
        this.f58427a8 = Double.MAX_VALUE;
        this.f58428a9 = new C1311us();
    }

    /* renamed from: a0 */
    public final C1311us m214028a0(double d, double d2, long j) {
        double dSin;
        double dCos;
        if (!this.f58421a2) {
            if (this.f58427a8 == Double.MAX_VALUE) {
                throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
            }
            double d3 = this.f58420a1;
            if (d3 > 1.0d) {
                double d4 = this.f58419a0;
                this.f58424a5 = (Math.sqrt((d3 * d3) - 1.0d) * d4) + ((-d3) * d4);
                double d5 = this.f58420a1;
                double d6 = this.f58419a0;
                this.f58425a6 = ((-d5) * d6) - (Math.sqrt((d5 * d5) - 1.0d) * d6);
            } else if (d3 >= 0.0d && d3 < 1.0d) {
                this.f58426a7 = Math.sqrt(1.0d - (d3 * d3)) * this.f58419a0;
            }
            this.f58421a2 = true;
        }
        double d7 = j / 1000.0d;
        double d8 = d - this.f58427a8;
        double d9 = this.f58420a1;
        if (d9 > 1.0d) {
            double d10 = this.f58425a6;
            double d11 = ((d10 * d8) - d2) / (d10 - this.f58424a5);
            double d12 = d8 - d11;
            dSin = (Math.pow(2.718281828459045d, this.f58424a5 * d7) * d11) + (Math.pow(2.718281828459045d, d10 * d7) * d12);
            double d13 = this.f58425a6;
            double dPow = Math.pow(2.718281828459045d, d13 * d7) * d12 * d13;
            double d14 = this.f58424a5;
            dCos = (Math.pow(2.718281828459045d, d14 * d7) * d11 * d14) + dPow;
        } else if (d9 == 1.0d) {
            double d15 = this.f58419a0;
            double d16 = (d15 * d8) + d2;
            double d17 = (d16 * d7) + d8;
            double dPow2 = Math.pow(2.718281828459045d, (-d15) * d7) * d17;
            double dPow3 = Math.pow(2.718281828459045d, (-this.f58419a0) * d7) * d17;
            double d18 = -this.f58419a0;
            dCos = (Math.pow(2.718281828459045d, d18 * d7) * d16) + (dPow3 * d18);
            dSin = dPow2;
        } else {
            double d19 = 1.0d / this.f58426a7;
            double d20 = this.f58419a0;
            double d21 = ((d9 * d20 * d8) + d2) * d19;
            dSin = ((Math.sin(this.f58426a7 * d7) * d21) + (Math.cos(this.f58426a7 * d7) * d8)) * Math.pow(2.718281828459045d, (-d9) * d20 * d7);
            double d22 = this.f58419a0;
            double d23 = this.f58420a1;
            double d24 = (-d22) * dSin * d23;
            double dPow4 = Math.pow(2.718281828459045d, (-d23) * d22 * d7);
            double d25 = this.f58426a7;
            double dSin2 = Math.sin(d25 * d7) * (-d25) * d8;
            double d26 = this.f58426a7;
            dCos = (((Math.cos(d26 * d7) * d21 * d26) + dSin2) * dPow4) + d24;
        }
        float f = (float) (dSin + this.f58427a8);
        C1311us c1311us = this.f58428a9;
        c1311us.f60506a0 = f;
        c1311us.f60507a1 = (float) dCos;
        return c1311us;
    }

    public n11(float f) {
        this.f58419a0 = Math.sqrt(1500.0d);
        this.f58420a1 = 0.5d;
        this.f58421a2 = false;
        this.f58428a9 = new C1311us();
        this.f58427a8 = f;
    }
}
