package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

/* loaded from: classes.dex */
public class McEliecePublicKeyParameters extends McElieceKeyParameters {

    /* renamed from: g */
    private GF2Matrix f1601g;

    /* renamed from: n */
    private int f1602n;

    /* renamed from: t */
    private int f1603t;

    public McEliecePublicKeyParameters(int i2, int i3, GF2Matrix gF2Matrix) {
        super(false, null);
        this.f1602n = i2;
        this.f1603t = i3;
        this.f1601g = new GF2Matrix(gF2Matrix);
    }

    public GF2Matrix getG() {
        return this.f1601g;
    }

    public int getK() {
        return this.f1601g.getNumRows();
    }

    public int getN() {
        return this.f1602n;
    }

    public int getT() {
        return this.f1603t;
    }
}
