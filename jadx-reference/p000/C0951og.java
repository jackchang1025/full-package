package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: og */
/* loaded from: classes.dex */
public final class C0951og extends b81 {

    /* renamed from: c2 */
    public double f58797c2;

    /* renamed from: c3 */
    public double[] f58798c3;

    @Override // p000.b81
    /* renamed from: c0 */
    public final double mo210516c0(double d) {
        return this.f58798c3[0];
    }

    @Override // p000.b81
    /* renamed from: c1 */
    public final void mo210517c1(double d, double[] dArr) {
        double[] dArr2 = this.f58798c3;
        System.arraycopy(dArr2, 0, dArr, 0, dArr2.length);
    }

    @Override // p000.b81
    /* renamed from: c2 */
    public final void mo210518c2(double d, float[] fArr) {
        int i = 0;
        while (true) {
            double[] dArr = this.f58798c3;
            if (i >= dArr.length) {
                return;
            }
            fArr[i] = (float) dArr[i];
            i++;
        }
    }

    @Override // p000.b81
    /* renamed from: c3 */
    public final double mo210519c3(double d) {
        return 0.0d;
    }

    @Override // p000.b81
    /* renamed from: c4 */
    public final void mo210520c4(double d, double[] dArr) {
        for (int i = 0; i < this.f58798c3.length; i++) {
            dArr[i] = 0.0d;
        }
    }

    @Override // p000.b81
    /* renamed from: c5 */
    public final double[] mo210521c5() {
        return new double[]{this.f58797c2};
    }
}
