package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ah0 {

    /* renamed from: a0 */
    public final int f43656a0;

    /* renamed from: a1 */
    public final float f43657a1;

    /* renamed from: a2 */
    public final int f43658a2;

    /* renamed from: a3 */
    public final int f43659a3;

    /* renamed from: a4 */
    public final float f43660a4;

    /* renamed from: a5 */
    public final float f43661a5;

    /* renamed from: a6 */
    public final int f43662a6;

    /* renamed from: a7 */
    public final float f43663a7;

    /* JADX WARN: Removed duplicated region for block: B:36:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ah0(int i, float f, float f2, float f3, int i2, float f4, int i3, float f5, int i4, float f6) {
        float fAbs;
        this.f43656a0 = i;
        float fM212475a3 = cq0.m212475a3(f, f2, f3);
        this.f43657a1 = fM212475a3;
        this.f43658a2 = i2;
        this.f43660a4 = f4;
        this.f43659a3 = i3;
        this.f43661a5 = f5;
        this.f43662a6 = i4;
        float f7 = i4;
        float f8 = i3;
        float f9 = i2;
        float f10 = f6 - ((fM212475a3 * f9) + ((f4 * f8) + (f5 * f7)));
        if (i2 > 0 && f10 > 0.0f) {
            this.f43657a1 = Math.min(f10 / f9, f3 - fM212475a3) + fM212475a3;
        } else if (i2 > 0 && f10 < 0.0f) {
            this.f43657a1 = Math.max(f10 / f9, f2 - fM212475a3) + fM212475a3;
        }
        float f11 = this.f43657a1;
        float f12 = f8 / 2.0f;
        float f13 = (f6 - ((f9 + f12) * (i2 > 0 ? f11 : 0.0f))) / (f12 + f7);
        this.f43661a5 = f13;
        float f14 = (f11 + f13) / 2.0f;
        this.f43660a4 = f14;
        if (i3 > 0 && f13 != f5) {
            float f15 = (f5 - f13) * f7;
            float fMin = Math.min(Math.abs(f15), f14 * 0.1f * f8);
            if (f15 > 0.0f) {
                this.f43660a4 -= fMin / f8;
                this.f43661a5 = (fMin / f7) + this.f43661a5;
            } else {
                this.f43660a4 = (fMin / f8) + this.f43660a4;
                this.f43661a5 -= fMin / f7;
            }
        }
        if (i4 <= 0 || i2 <= 0 || i3 <= 0) {
            fAbs = (i4 <= 0 || i2 <= 0 || this.f43661a5 > this.f43657a1) ? i * Math.abs(f5 - this.f43661a5) : Float.MAX_VALUE;
        } else {
            float f16 = this.f43661a5;
            float f17 = this.f43660a4;
            if (f16 <= f17 || f17 <= this.f43657a1) {
            }
        }
        this.f43663a7 = fAbs;
    }

    public final String toString() {
        return "Arrangement [priority=" + this.f43656a0 + ", smallCount=" + this.f43658a2 + ", smallSize=" + this.f43657a1 + ", mediumCount=" + this.f43659a3 + ", mediumSize=" + this.f43660a4 + ", largeCount=" + this.f43662a6 + ", largeSize=" + this.f43661a5 + ", cost=" + this.f43663a7 + "]";
    }
}
