package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

/* loaded from: classes.dex */
public class McElieceCCA2PrivateKeyParameters extends McElieceCCA2KeyParameters {
    private GF2mField field;
    private PolynomialGF2mSmallM goppaPoly;

    /* renamed from: h */
    private GF2Matrix f1574h;

    /* renamed from: k */
    private int f1575k;

    /* renamed from: n */
    private int f1576n;

    /* renamed from: p */
    private Permutation f1577p;
    private PolynomialGF2mSmallM[] qInv;

    public McElieceCCA2PrivateKeyParameters(int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, GF2Matrix gF2Matrix, Permutation permutation, String str) {
        super(true, str);
        this.f1576n = i2;
        this.f1575k = i3;
        this.field = gF2mField;
        this.goppaPoly = polynomialGF2mSmallM;
        this.f1574h = gF2Matrix;
        this.f1577p = permutation;
        this.qInv = new PolynomialRingGF2m(gF2mField, polynomialGF2mSmallM).getSquareRootMatrix();
    }

    public GF2mField getField() {
        return this.field;
    }

    public PolynomialGF2mSmallM getGoppaPoly() {
        return this.goppaPoly;
    }

    public GF2Matrix getH() {
        return this.f1574h;
    }

    public int getK() {
        return this.f1575k;
    }

    public int getN() {
        return this.f1576n;
    }

    public Permutation getP() {
        return this.f1577p;
    }

    public PolynomialGF2mSmallM[] getQInv() {
        return this.qInv;
    }

    public int getT() {
        return this.goppaPoly.getDegree();
    }

    public McElieceCCA2PrivateKeyParameters(int i2, int i3, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, Permutation permutation, String str) {
        this(i2, i3, gF2mField, polynomialGF2mSmallM, GoppaCode.createCanonicalCheckMatrix(gF2mField, polynomialGF2mSmallM), permutation, str);
    }
}
