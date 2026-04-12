package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: vq */
/* loaded from: classes.dex */
public final class C1346vq extends C1347vr {

    /* renamed from: a4 */
    public final double f60663a4;

    /* renamed from: a5 */
    public final double f60664a5;

    /* renamed from: a6 */
    public final double f60665a6;

    /* renamed from: a7 */
    public final double f60666a7;

    public C1346vq(String str) {
        this.f60678a1 = str;
        int iIndexOf = str.indexOf(40);
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        this.f60663a4 = Double.parseDouble(str.substring(iIndexOf + 1, iIndexOf2).trim());
        int i = iIndexOf2 + 1;
        int iIndexOf3 = str.indexOf(44, i);
        this.f60664a5 = Double.parseDouble(str.substring(i, iIndexOf3).trim());
        int i2 = iIndexOf3 + 1;
        int iIndexOf4 = str.indexOf(44, i2);
        this.f60665a6 = Double.parseDouble(str.substring(i2, iIndexOf4).trim());
        int i3 = iIndexOf4 + 1;
        this.f60666a7 = Double.parseDouble(str.substring(i3, str.indexOf(41, i3)).trim());
    }

    @Override // p000.C1347vr
    /* renamed from: a0 */
    public final double mo210531a0(double d) {
        if (d <= 0.0d) {
            return 0.0d;
        }
        if (d >= 1.0d) {
            return 1.0d;
        }
        double d2 = 0.5d;
        double d3 = 0.5d;
        while (d2 > 0.01d) {
            d2 *= 0.5d;
            d3 = m214936a3(d3) < d ? d3 + d2 : d3 - d2;
        }
        double d4 = d3 - d2;
        double dM214936a3 = m214936a3(d4);
        double d5 = d3 + d2;
        double dM214936a32 = m214936a3(d5);
        double dM214937a4 = m214937a4(d4);
        return (((d - dM214936a3) * (m214937a4(d5) - dM214937a4)) / (dM214936a32 - dM214936a3)) + dM214937a4;
    }

    @Override // p000.C1347vr
    /* renamed from: a1 */
    public final double mo210532a1(double d) {
        double d2 = 0.5d;
        double d3 = 0.5d;
        while (d2 > 1.0E-4d) {
            d2 *= 0.5d;
            d3 = m214936a3(d3) < d ? d3 + d2 : d3 - d2;
        }
        double d4 = d3 - d2;
        double d5 = d3 + d2;
        return (m214937a4(d5) - m214937a4(d4)) / (m214936a3(d5) - m214936a3(d4));
    }

    /* renamed from: a3 */
    public final double m214936a3(double d) {
        double d2 = 1.0d - d;
        double d3 = 3.0d * d2;
        double d4 = d2 * d3 * d;
        double d5 = d3 * d * d;
        return (this.f60665a6 * d5) + (this.f60663a4 * d4) + (d * d * d);
    }

    /* renamed from: a4 */
    public final double m214937a4(double d) {
        double d2 = 1.0d - d;
        double d3 = 3.0d * d2;
        double d4 = d2 * d3 * d;
        double d5 = d3 * d * d;
        return (this.f60666a7 * d5) + (this.f60664a5 * d4) + (d * d * d);
    }
}
