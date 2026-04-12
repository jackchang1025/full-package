package p000;

/* loaded from: classes2.dex */
public class le0 extends he0 {
    private w10 matrixG;

    /* renamed from: n */
    private int f57898n;

    /* renamed from: t */
    private int f57899t;

    public le0(int i, int i2, w10 w10Var, String str) {
        super(false, str);
        this.f57898n = i;
        this.f57899t = i2;
        this.matrixG = new w10(w10Var);
    }

    public w10 getG() {
        return this.matrixG;
    }

    public int getK() {
        return this.matrixG.getNumRows();
    }

    public int getN() {
        return this.f57898n;
    }

    public int getT() {
        return this.f57899t;
    }
}
