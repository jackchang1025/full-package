package p000;

import java.math.BigInteger;

/* renamed from: zk */
/* loaded from: classes2.dex */
public abstract class AbstractC1520zk {
    static final InterfaceC1519zj GF_2 = new fo0(BigInteger.valueOf(2));
    static final InterfaceC1519zj GF_3 = new fo0(BigInteger.valueOf(3));

    public static rn0 getBinaryExtensionField(int[] iArr) {
        if (iArr[0] != 0) {
            throw new IllegalArgumentException("Irreducible polynomials in GF(2) must have constant term");
        }
        for (int i = 1; i < iArr.length; i++) {
            if (iArr[i] <= iArr[i - 1]) {
                throw new IllegalArgumentException("Polynomial exponents must be monotonically increasing");
            }
        }
        return new u20(GF_2, new x10(iArr));
    }

    public static InterfaceC1519zj getPrimeField(BigInteger bigInteger) {
        int iBitLength = bigInteger.bitLength();
        if (bigInteger.signum() <= 0 || iBitLength < 2) {
            throw new IllegalArgumentException("'characteristic' must be >= 2");
        }
        if (iBitLength < 3) {
            int iIntValue = bigInteger.intValue();
            if (iIntValue == 2) {
                return GF_2;
            }
            if (iIntValue == 3) {
                return GF_3;
            }
        }
        return new fo0(bigInteger);
    }
}
