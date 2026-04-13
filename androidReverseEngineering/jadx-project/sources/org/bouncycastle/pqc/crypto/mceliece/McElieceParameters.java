package org.bouncycastle.pqc.crypto.mceliece;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;

/* loaded from: classes.dex */
public class McElieceParameters implements CipherParameters {
    public static final int DEFAULT_M = 11;
    public static final int DEFAULT_T = 50;
    private Digest digest;
    private int fieldPoly;

    /* renamed from: m */
    private int f1592m;

    /* renamed from: n */
    private int f1593n;

    /* renamed from: t */
    private int f1594t;

    public McElieceParameters() {
        this(11, 50);
    }

    public int getFieldPoly() {
        return this.fieldPoly;
    }

    public int getM() {
        return this.f1592m;
    }

    public int getN() {
        return this.f1593n;
    }

    public int getT() {
        return this.f1594t;
    }

    public McElieceParameters(int i2) {
        this(i2, (Digest) null);
    }

    public McElieceParameters(int i2, int i3) {
        this(i2, i3, (Digest) null);
    }

    public McElieceParameters(int i2, int i3, int i4) {
        this(i2, i3, i4, null);
    }

    public McElieceParameters(int i2, int i3, int i4, Digest digest) {
        this.f1592m = i2;
        if (i2 < 1) {
            throw new IllegalArgumentException("m must be positive");
        }
        if (i2 > 32) {
            throw new IllegalArgumentException(" m is too large");
        }
        int i5 = 1 << i2;
        this.f1593n = i5;
        this.f1594t = i3;
        if (i3 < 0) {
            throw new IllegalArgumentException("t must be positive");
        }
        if (i3 > i5) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
        }
        if (PolynomialRingGF2.degree(i4) != i2 || !PolynomialRingGF2.isIrreducible(i4)) {
            throw new IllegalArgumentException("polynomial is not a field polynomial for GF(2^m)");
        }
        this.fieldPoly = i4;
        this.digest = digest;
    }

    public McElieceParameters(int i2, int i3, Digest digest) {
        if (i2 < 1) {
            throw new IllegalArgumentException("m must be positive");
        }
        if (i2 > 32) {
            throw new IllegalArgumentException("m is too large");
        }
        this.f1592m = i2;
        int i4 = 1 << i2;
        this.f1593n = i4;
        if (i3 < 0) {
            throw new IllegalArgumentException("t must be positive");
        }
        if (i3 > i4) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
        }
        this.f1594t = i3;
        this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(i2);
        this.digest = digest;
    }

    public McElieceParameters(int i2, Digest digest) {
        if (i2 < 1) {
            throw new IllegalArgumentException("key size must be positive");
        }
        this.f1592m = 0;
        this.f1593n = 1;
        while (true) {
            int i3 = this.f1593n;
            if (i3 >= i2) {
                int i4 = i3 >>> 1;
                this.f1594t = i4;
                int i5 = this.f1592m;
                this.f1594t = i4 / i5;
                this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(i5);
                this.digest = digest;
                return;
            }
            this.f1593n = i3 << 1;
            this.f1592m++;
        }
    }

    public McElieceParameters(Digest digest) {
        this(11, 50, digest);
    }
}
