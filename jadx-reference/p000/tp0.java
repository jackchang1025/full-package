package p000;

/* loaded from: classes2.dex */
public class tp0 extends qp0 {
    private short[][] A1inv;
    private short[][] A2inv;

    /* renamed from: b1 */
    private short[] f60246b1;

    /* renamed from: b2 */
    private short[] f60247b2;
    private w90[] layers;

    /* renamed from: vi */
    private int[] f60248vi;

    public tp0(short[][] sArr, short[] sArr2, short[][] sArr3, short[] sArr4, int[] iArr, w90[] w90VarArr) {
        super(true, iArr[iArr.length - 1] - iArr[0]);
        this.A1inv = sArr;
        this.f60246b1 = sArr2;
        this.A2inv = sArr3;
        this.f60247b2 = sArr4;
        this.f60248vi = iArr;
        this.layers = w90VarArr;
    }

    public short[] getB1() {
        return this.f60246b1;
    }

    public short[] getB2() {
        return this.f60247b2;
    }

    public short[][] getInvA1() {
        return this.A1inv;
    }

    public short[][] getInvA2() {
        return this.A2inv;
    }

    public w90[] getLayers() {
        return this.layers;
    }

    public int[] getVi() {
        return this.f60248vi;
    }
}
