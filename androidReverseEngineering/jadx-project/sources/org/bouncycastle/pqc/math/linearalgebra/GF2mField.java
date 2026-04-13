package org.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class GF2mField {
    private int degree;
    private int polynomial;

    public GF2mField(int i2) {
        this.degree = 0;
        if (i2 >= 32) {
            throw new IllegalArgumentException(" Error: the degree of field is too large ");
        }
        if (i2 < 1) {
            throw new IllegalArgumentException(" Error: the degree of field is non-positive ");
        }
        this.degree = i2;
        this.polynomial = PolynomialRingGF2.getIrreduciblePolynomial(i2);
    }

    private static String polyToString(int i2) {
        if (i2 == 0) {
            return "0";
        }
        String str = ((byte) (i2 & 1)) == 1 ? "1" : BuildConfig.FLAVOR;
        int i3 = i2 >>> 1;
        int i4 = 1;
        while (i3 != 0) {
            if (((byte) (i3 & 1)) == 1) {
                str = str + "+x^" + i4;
            }
            i3 >>>= 1;
            i4++;
        }
        return str;
    }

    public int add(int i2, int i3) {
        return i2 ^ i3;
    }

    public String elementToStr(int i2) {
        String str = BuildConfig.FLAVOR;
        for (int i3 = 0; i3 < this.degree; i3++) {
            StringBuilder sb = (((byte) i2) & 1) == 0 ? new StringBuilder("0") : new StringBuilder("1");
            sb.append(str);
            str = sb.toString();
            i2 >>>= 1;
        }
        return str;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof GF2mField)) {
            GF2mField gF2mField = (GF2mField) obj;
            if (this.degree == gF2mField.degree && this.polynomial == gF2mField.polynomial) {
                return true;
            }
        }
        return false;
    }

    public int exp(int i2, int i3) {
        if (i3 == 0) {
            return 1;
        }
        if (i2 == 0) {
            return 0;
        }
        if (i2 == 1) {
            return 1;
        }
        if (i3 < 0) {
            i2 = inverse(i2);
            i3 = -i3;
        }
        int i4 = 1;
        while (i3 != 0) {
            if ((i3 & 1) == 1) {
                i4 = mult(i4, i2);
            }
            i2 = mult(i2, i2);
            i3 >>>= 1;
        }
        return i4;
    }

    public int getDegree() {
        return this.degree;
    }

    public byte[] getEncoded() {
        return LittleEndianConversions.I2OSP(this.polynomial);
    }

    public int getPolynomial() {
        return this.polynomial;
    }

    public int getRandomElement(SecureRandom secureRandom) {
        return RandUtils.nextInt(secureRandom, 1 << this.degree);
    }

    public int getRandomNonZeroElement() {
        return getRandomNonZeroElement(CryptoServicesRegistrar.getSecureRandom());
    }

    public int hashCode() {
        return this.polynomial;
    }

    public int inverse(int i2) {
        return exp(i2, (1 << this.degree) - 2);
    }

    public boolean isElementOfThisField(int i2) {
        int i3 = this.degree;
        return i3 == 31 ? i2 >= 0 : i2 >= 0 && i2 < (1 << i3);
    }

    public int mult(int i2, int i3) {
        return PolynomialRingGF2.modMultiply(i2, i3, this.polynomial);
    }

    public int sqRoot(int i2) {
        for (int i3 = 1; i3 < this.degree; i3++) {
            i2 = mult(i2, i2);
        }
        return i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Finite Field GF(2^");
        sb.append(this.degree);
        sb.append(") = GF(2)[X]/<");
        return AbstractC0000a.m18n(sb, polyToString(this.polynomial), "> ");
    }

    public GF2mField(int i2, int i3) {
        this.degree = 0;
        if (i2 != PolynomialRingGF2.degree(i3)) {
            throw new IllegalArgumentException(" Error: the degree is not correct");
        }
        if (!PolynomialRingGF2.isIrreducible(i3)) {
            throw new IllegalArgumentException(" Error: given polynomial is reducible");
        }
        this.degree = i2;
        this.polynomial = i3;
    }

    public int getRandomNonZeroElement(SecureRandom secureRandom) {
        int nextInt = RandUtils.nextInt(secureRandom, 1 << this.degree);
        int i2 = 0;
        while (nextInt == 0 && i2 < 1048576) {
            nextInt = RandUtils.nextInt(secureRandom, 1 << this.degree);
            i2++;
        }
        if (i2 == 1048576) {
            return 1;
        }
        return nextInt;
    }

    public GF2mField(GF2mField gF2mField) {
        this.degree = 0;
        this.degree = gF2mField.degree;
        this.polynomial = gF2mField.polynomial;
    }

    public GF2mField(byte[] bArr) {
        this.degree = 0;
        if (bArr.length != 4) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        int OS2IP = LittleEndianConversions.OS2IP(bArr);
        this.polynomial = OS2IP;
        if (!PolynomialRingGF2.isIrreducible(OS2IP)) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        this.degree = PolynomialRingGF2.degree(this.polynomial);
    }
}
