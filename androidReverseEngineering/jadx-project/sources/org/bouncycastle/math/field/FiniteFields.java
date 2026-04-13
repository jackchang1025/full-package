package org.bouncycastle.math.field;

import java.math.BigInteger;

/* loaded from: classes.dex */
public abstract class FiniteFields {
    static final FiniteField GF_2 = new PrimeField(BigInteger.valueOf(2));
    static final FiniteField GF_3 = new PrimeField(BigInteger.valueOf(3));

    public static PolynomialExtensionField getBinaryExtensionField(int[] iArr) {
        if (iArr[0] != 0) {
            throw new IllegalArgumentException("Irreducible polynomials in GF(2) must have constant term");
        }
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2] <= iArr[i2 - 1]) {
                throw new IllegalArgumentException("Polynomial exponents must be monotonically increasing");
            }
        }
        return new GenericPolynomialExtensionField(GF_2, new GF2Polynomial(iArr));
    }

    public static FiniteField getPrimeField(BigInteger bigInteger) {
        int bitLength = bigInteger.bitLength();
        if (bigInteger.signum() <= 0 || bitLength < 2) {
            throw new IllegalArgumentException("'characteristic' must be >= 2");
        }
        if (bitLength < 3) {
            int intValue = bigInteger.intValue();
            if (intValue == 2) {
                return GF_2;
            }
            if (intValue == 3) {
                return GF_3;
            }
        }
        return new PrimeField(bigInteger);
    }
}
