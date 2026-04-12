package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: at */
/* loaded from: classes.dex */
public final class C0108at {

    /* renamed from: b8 */
    public static final double[] f45621b8 = new double[91];

    /* renamed from: a0 */
    public double[] f45622a0;

    /* renamed from: a1 */
    public double f45623a1;

    /* renamed from: a2 */
    public double f45624a2;

    /* renamed from: a3 */
    public double f45625a3;

    /* renamed from: a4 */
    public double f45626a4;

    /* renamed from: a5 */
    public double f45627a5;

    /* renamed from: a6 */
    public double f45628a6;

    /* renamed from: a7 */
    public double f45629a7;

    /* renamed from: a8 */
    public double f45630a8;

    /* renamed from: a9 */
    public double f45631a9;

    /* renamed from: b0 */
    public double f45632b0;

    /* renamed from: b1 */
    public double f45633b1;

    /* renamed from: b2 */
    public double f45634b2;

    /* renamed from: b3 */
    public double f45635b3;

    /* renamed from: b4 */
    public double f45636b4;

    /* renamed from: b5 */
    public double f45637b5;

    /* renamed from: b6 */
    public boolean f45638b6;

    /* renamed from: b7 */
    public boolean f45639b7;

    /* renamed from: a0 */
    public final double m210509a0() {
        double d = this.f45631a9 * this.f45637b5;
        double dHypot = this.f45635b3 / Math.hypot(d, (-this.f45632b0) * this.f45636b4);
        return this.f45638b6 ? (-d) * dHypot : d * dHypot;
    }

    /* renamed from: a1 */
    public final double m210510a1() {
        double d = this.f45631a9 * this.f45637b5;
        double d2 = (-this.f45632b0) * this.f45636b4;
        double dHypot = this.f45635b3 / Math.hypot(d, d2);
        return this.f45638b6 ? (-d2) * dHypot : d2 * dHypot;
    }

    /* renamed from: a2 */
    public final double m210511a2(double d) {
        double d2 = (d - this.f45624a2) * this.f45630a8;
        double d3 = this.f45626a4;
        return ((this.f45627a5 - d3) * d2) + d3;
    }

    /* renamed from: a3 */
    public final double m210512a3(double d) {
        double d2 = (d - this.f45624a2) * this.f45630a8;
        double d3 = this.f45628a6;
        return ((this.f45629a7 - d3) * d2) + d3;
    }

    /* renamed from: a4 */
    public final double m210513a4() {
        return (this.f45631a9 * this.f45636b4) + this.f45633b1;
    }

    /* renamed from: a5 */
    public final double m210514a5() {
        return (this.f45632b0 * this.f45637b5) + this.f45634b2;
    }

    /* renamed from: a6 */
    public final void m210515a6(double d) {
        double d2 = (this.f45638b6 ? this.f45625a3 - d : d - this.f45624a2) * this.f45630a8;
        double d3 = 0.0d;
        if (d2 > 0.0d) {
            d3 = 1.0d;
            if (d2 < 1.0d) {
                double[] dArr = this.f45622a0;
                double length = d2 * (dArr.length - 1);
                int i = (int) length;
                double d4 = dArr[i];
                d3 = ((dArr[i + 1] - d4) * (length - i)) + d4;
            }
        }
        double d5 = d3 * 1.5707963267948966d;
        this.f45636b4 = Math.sin(d5);
        this.f45637b5 = Math.cos(d5);
    }
}
