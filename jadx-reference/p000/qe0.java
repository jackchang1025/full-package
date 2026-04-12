package p000;

/* loaded from: classes2.dex */
public class qe0 extends ne0 {
    private z10 field;
    private sn0 goppaPoly;

    /* renamed from: h */
    private w10 f59479h;

    /* renamed from: k */
    private int f59480k;

    /* renamed from: n */
    private int f59481n;
    private String oid;

    /* renamed from: p1 */
    private kn0 f59482p1;

    /* renamed from: p2 */
    private kn0 f59483p2;
    private sn0[] qInv;
    private w10 sInv;

    public qe0(int i, int i2, z10 z10Var, sn0 sn0Var, kn0 kn0Var, kn0 kn0Var2, w10 w10Var) {
        super(true, null);
        this.f59480k = i2;
        this.f59481n = i;
        this.field = z10Var;
        this.goppaPoly = sn0Var;
        this.sInv = w10Var;
        this.f59482p1 = kn0Var;
        this.f59483p2 = kn0Var2;
        this.f59479h = k30.createCanonicalCheckMatrix(z10Var, sn0Var);
        this.qInv = new un0(z10Var, sn0Var).getSquareRootMatrix();
    }

    public z10 getField() {
        return this.field;
    }

    public sn0 getGoppaPoly() {
        return this.goppaPoly;
    }

    public w10 getH() {
        return this.f59479h;
    }

    public int getK() {
        return this.f59480k;
    }

    public int getN() {
        return this.f59481n;
    }

    public kn0 getP1() {
        return this.f59482p1;
    }

    public kn0 getP2() {
        return this.f59483p2;
    }

    public sn0[] getQInv() {
        return this.qInv;
    }

    public w10 getSInv() {
        return this.sInv;
    }

    public qe0(int i, int i2, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, byte[] bArr6, byte[][] bArr7) {
        super(true, null);
        this.f59481n = i;
        this.f59480k = i2;
        z10 z10Var = new z10(bArr);
        this.field = z10Var;
        this.goppaPoly = new sn0(z10Var, bArr2);
        this.sInv = new w10(bArr3);
        this.f59482p1 = new kn0(bArr4);
        this.f59483p2 = new kn0(bArr5);
        this.f59479h = new w10(bArr6);
        this.qInv = new sn0[bArr7.length];
        for (int i3 = 0; i3 < bArr7.length; i3++) {
            this.qInv[i3] = new sn0(this.field, bArr7[i3]);
        }
    }
}
