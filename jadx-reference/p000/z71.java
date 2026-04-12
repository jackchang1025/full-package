package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class z71 {

    /* renamed from: a3 */
    public static z71 f61464a3;

    /* renamed from: a0 */
    public long f61465a0;

    /* renamed from: a1 */
    public long f61466a1;

    /* renamed from: a2 */
    public int f61467a2;

    /* renamed from: a0 */
    public final void m215376a0(double d, double d2, long j) {
        double d3 = (0.01720197f * ((j - 946728000000L) / 8.64E7f)) + 6.24006f;
        double dSin = (Math.sin(r3 * 3.0f) * 5.236000106378924E-6d) + (Math.sin(2.0f * r3) * 3.4906598739326E-4d) + (Math.sin(d3) * 0.03341960161924362d) + d3 + 1.796593063d + 3.141592653589793d;
        double dSin2 = (Math.sin(2.0d * dSin) * (-0.0069d)) + (Math.sin(d3) * 0.0053d) + Math.round((r2 - 9.0E-4f) - r6) + 9.0E-4f + ((-d2) / 360.0d);
        double dAsin = Math.asin(Math.sin(0.4092797040939331d) * Math.sin(dSin));
        double d4 = 0.01745329238474369d * d;
        double dSin3 = (Math.sin(-0.10471975803375244d) - (Math.sin(dAsin) * Math.sin(d4))) / (Math.cos(dAsin) * Math.cos(d4));
        if (dSin3 >= 1.0d) {
            this.f61467a2 = 1;
            this.f61465a0 = -1L;
            this.f61466a1 = -1L;
        } else {
            if (dSin3 <= -1.0d) {
                this.f61467a2 = 0;
                this.f61465a0 = -1L;
                this.f61466a1 = -1L;
                return;
            }
            double dAcos = (float) (Math.acos(dSin3) / 6.283185307179586d);
            this.f61465a0 = Math.round((dSin2 + dAcos) * 8.64E7d) + 946728000000L;
            long jRound = Math.round((dSin2 - dAcos) * 8.64E7d) + 946728000000L;
            this.f61466a1 = jRound;
            if (jRound >= j || this.f61465a0 <= j) {
                this.f61467a2 = 1;
            } else {
                this.f61467a2 = 0;
            }
        }
    }
}
