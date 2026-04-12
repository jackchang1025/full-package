package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class gu0 extends C1347vr {

    /* renamed from: a4 */
    public double f56571a4;

    /* renamed from: a5 */
    public double f56572a5;

    @Override // p000.C1347vr
    /* renamed from: a0 */
    public final double mo210531a0(double d) {
        double d2 = this.f56571a4;
        double d3 = this.f56572a5;
        if (d < d3) {
            return (d3 * d) / (((d3 - d) * d2) + d);
        }
        return ((d - 1.0d) * (1.0d - d3)) / ((1.0d - d) - ((d3 - d) * d2));
    }

    @Override // p000.C1347vr
    /* renamed from: a1 */
    public final double mo210532a1(double d) {
        double d2 = this.f56571a4;
        double d3 = this.f56572a5;
        if (d < d3) {
            double d4 = d2 * d3 * d3;
            double d5 = ((d3 - d) * d2) + d;
            return d4 / (d5 * d5);
        }
        double d6 = d3 - 1.0d;
        double d7 = (((d3 - d) * (-d2)) - d) + 1.0d;
        return ((d6 * d2) * d6) / (d7 * d7);
    }
}
