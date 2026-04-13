package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

/* loaded from: classes.dex */
public class McElieceCCA2PublicKeyParameters extends McElieceCCA2KeyParameters {
    private GF2Matrix matrixG;

    /* renamed from: n */
    private int f1578n;

    /* renamed from: t */
    private int f1579t;

    public McElieceCCA2PublicKeyParameters(int i2, int i3, GF2Matrix gF2Matrix, String str) {
        super(false, str);
        this.f1578n = i2;
        this.f1579t = i3;
        this.matrixG = new GF2Matrix(gF2Matrix);
    }

    public GF2Matrix getG() {
        return this.matrixG;
    }

    public int getK() {
        return this.matrixG.getNumRows();
    }

    public int getN() {
        return this.f1578n;
    }

    public int getT() {
        return this.f1579t;
    }
}
