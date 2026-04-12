package p000;

import java.security.spec.KeySpec;

/* loaded from: classes2.dex */
public class up0 implements KeySpec {
    private short[][] A1inv;
    private short[][] A2inv;

    /* renamed from: b1 */
    private short[] f60487b1;

    /* renamed from: b2 */
    private short[] f60488b2;
    private w90[] layers;

    /* renamed from: vi */
    private int[] f60489vi;

    public up0(short[][] sArr, short[] sArr2, short[][] sArr3, short[] sArr4, int[] iArr, w90[] w90VarArr) {
        this.A1inv = sArr;
        this.f60487b1 = sArr2;
        this.A2inv = sArr3;
        this.f60488b2 = sArr4;
        this.f60489vi = iArr;
        this.layers = w90VarArr;
    }

    public short[] getB1() {
        return this.f60487b1;
    }

    public short[] getB2() {
        return this.f60488b2;
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
        return this.f60489vi;
    }
}
