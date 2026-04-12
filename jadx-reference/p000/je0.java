package p000;

/* loaded from: classes2.dex */
public class je0 extends he0 {
    private z10 field;
    private sn0 goppaPoly;

    /* renamed from: h */
    private w10 f57324h;

    /* renamed from: k */
    private int f57325k;

    /* renamed from: n */
    private int f57326n;

    /* renamed from: p */
    private kn0 f57327p;
    private sn0[] qInv;

    public je0(int i, int i2, z10 z10Var, sn0 sn0Var, w10 w10Var, kn0 kn0Var, String str) {
        super(true, str);
        this.f57326n = i;
        this.f57325k = i2;
        this.field = z10Var;
        this.goppaPoly = sn0Var;
        this.f57324h = w10Var;
        this.f57327p = kn0Var;
        this.qInv = new un0(z10Var, sn0Var).getSquareRootMatrix();
    }

    public z10 getField() {
        return this.field;
    }

    public sn0 getGoppaPoly() {
        return this.goppaPoly;
    }

    public w10 getH() {
        return this.f57324h;
    }

    public int getK() {
        return this.f57325k;
    }

    public int getN() {
        return this.f57326n;
    }

    public kn0 getP() {
        return this.f57327p;
    }

    public sn0[] getQInv() {
        return this.qInv;
    }

    public int getT() {
        return this.goppaPoly.getDegree();
    }

    public je0(int i, int i2, z10 z10Var, sn0 sn0Var, kn0 kn0Var, String str) {
        this(i, i2, z10Var, sn0Var, k30.createCanonicalCheckMatrix(z10Var, sn0Var), kn0Var, str);
    }
}
