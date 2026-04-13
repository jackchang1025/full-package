package org.bouncycastle.pqc.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;

/* loaded from: classes.dex */
public class McElieceCCA2KeyGenParameterSpec implements AlgorithmParameterSpec {
    public static final int DEFAULT_M = 11;
    public static final int DEFAULT_T = 50;
    public static final String SHA1 = "SHA-1";
    public static final String SHA224 = "SHA-224";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";
    private final String digest;
    private int fieldPoly;

    /* renamed from: m */
    private final int f1620m;

    /* renamed from: n */
    private final int f1621n;

    /* renamed from: t */
    private final int f1622t;

    public McElieceCCA2KeyGenParameterSpec() {
        this(11, 50, "SHA-256");
    }

    public String getDigest() {
        return this.digest;
    }

    public int getFieldPoly() {
        return this.fieldPoly;
    }

    public int getM() {
        return this.f1620m;
    }

    public int getN() {
        return this.f1621n;
    }

    public int getT() {
        return this.f1622t;
    }

    public McElieceCCA2KeyGenParameterSpec(int i2) {
        this(i2, "SHA-256");
    }

    public McElieceCCA2KeyGenParameterSpec(int i2, int i3) {
        this(i2, i3, "SHA-256");
    }

    public McElieceCCA2KeyGenParameterSpec(int i2, int i3, int i4) {
        this(i2, i3, i4, "SHA-256");
    }

    public McElieceCCA2KeyGenParameterSpec(int i2, int i3, int i4, String str) {
        this.f1620m = i2;
        if (i2 < 1) {
            throw new IllegalArgumentException("m must be positive");
        }
        if (i2 > 32) {
            throw new IllegalArgumentException(" m is too large");
        }
        int i5 = 1 << i2;
        this.f1621n = i5;
        this.f1622t = i3;
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
        this.digest = str;
    }

    public McElieceCCA2KeyGenParameterSpec(int i2, int i3, String str) {
        if (i2 < 1) {
            throw new IllegalArgumentException("m must be positive");
        }
        if (i2 > 32) {
            throw new IllegalArgumentException("m is too large");
        }
        this.f1620m = i2;
        int i4 = 1 << i2;
        this.f1621n = i4;
        if (i3 < 0) {
            throw new IllegalArgumentException("t must be positive");
        }
        if (i3 > i4) {
            throw new IllegalArgumentException("t must be less than n = 2^m");
        }
        this.f1622t = i3;
        this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(i2);
        this.digest = str;
    }

    public McElieceCCA2KeyGenParameterSpec(int i2, String str) {
        int i3 = 1;
        if (i2 < 1) {
            throw new IllegalArgumentException("key size must be positive");
        }
        int i4 = 0;
        while (i3 < i2) {
            i3 <<= 1;
            i4++;
        }
        this.f1622t = (i3 >>> 1) / i4;
        this.f1620m = i4;
        this.f1621n = i3;
        this.fieldPoly = PolynomialRingGF2.getIrreduciblePolynomial(i4);
        this.digest = str;
    }
}
