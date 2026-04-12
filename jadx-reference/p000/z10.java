package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class z10 {
    private int degree;
    private int polynomial;

    public z10(int i) {
        this.degree = 0;
        if (i >= 32) {
            throw new IllegalArgumentException(" Error: the degree of field is too large ");
        }
        if (i < 1) {
            throw new IllegalArgumentException(" Error: the degree of field is non-positive ");
        }
        this.degree = i;
        this.polynomial = tn0.getIrreduciblePolynomial(i);
    }

    private static String polyToString(int i) {
        if (i == 0) {
            return "0";
        }
        String str = ((byte) (i & 1)) == 1 ? "1" : "";
        int i2 = i >>> 1;
        int i3 = 1;
        while (i2 != 0) {
            if (((byte) (i2 & 1)) == 1) {
                str = str + "+x^" + i3;
            }
            i2 >>>= 1;
            i3++;
        }
        return str;
    }

    public int add(int i, int i2) {
        return i ^ i2;
    }

    public String elementToStr(int i) {
        String strM48c9 = "";
        for (int i2 = 0; i2 < this.degree; i2++) {
            strM48c9 = AbstractC0003a2.m48c9((((byte) i) & 1) == 0 ? "0" : "1", strM48c9);
            i >>>= 1;
        }
        return strM48c9;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof z10)) {
            z10 z10Var = (z10) obj;
            if (this.degree == z10Var.degree && this.polynomial == z10Var.polynomial) {
                return true;
            }
        }
        return false;
    }

    public int exp(int i, int i2) {
        if (i2 == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i2 < 0) {
            i = inverse(i);
            i2 = -i2;
        }
        int iMult = 1;
        while (i2 != 0) {
            if ((i2 & 1) == 1) {
                iMult = mult(iMult, i);
            }
            i = mult(i, i);
            i2 >>>= 1;
        }
        return iMult;
    }

    public int getDegree() {
        return this.degree;
    }

    public byte[] getEncoded() {
        return ub0.I2OSP(this.polynomial);
    }

    public int getPolynomial() {
        return this.polynomial;
    }

    public int getRandomElement(SecureRandom secureRandom) {
        return zp0.nextInt(secureRandom, 1 << this.degree);
    }

    public int getRandomNonZeroElement() {
        return getRandomNonZeroElement(C0929nx.getSecureRandom());
    }

    public int hashCode() {
        return this.polynomial;
    }

    public int inverse(int i) {
        return exp(i, (1 << this.degree) - 2);
    }

    public boolean isElementOfThisField(int i) {
        int i2 = this.degree;
        return i2 == 31 ? i >= 0 : i >= 0 && i < (1 << i2);
    }

    public int mult(int i, int i2) {
        return tn0.modMultiply(i, i2, this.polynomial);
    }

    public int sqRoot(int i) {
        for (int i2 = 1; i2 < this.degree; i2++) {
            i = mult(i, i);
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Finite Field GF(2^");
        sb.append(this.degree);
        sb.append(") = GF(2)[X]/<");
        return AbstractC0003a2.m35b6(sb, polyToString(this.polynomial), "> ");
    }

    public z10(int i, int i2) {
        this.degree = 0;
        if (i != tn0.degree(i2)) {
            throw new IllegalArgumentException(" Error: the degree is not correct");
        }
        if (!tn0.isIrreducible(i2)) {
            throw new IllegalArgumentException(" Error: given polynomial is reducible");
        }
        this.degree = i;
        this.polynomial = i2;
    }

    public int getRandomNonZeroElement(SecureRandom secureRandom) {
        int iNextInt = zp0.nextInt(secureRandom, 1 << this.degree);
        int i = 0;
        while (iNextInt == 0 && i < 1048576) {
            iNextInt = zp0.nextInt(secureRandom, 1 << this.degree);
            i++;
        }
        if (i == 1048576) {
            return 1;
        }
        return iNextInt;
    }

    public z10(z10 z10Var) {
        this.degree = 0;
        this.degree = z10Var.degree;
        this.polynomial = z10Var.polynomial;
    }

    public z10(byte[] bArr) {
        this.degree = 0;
        if (bArr.length != 4) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        int iOS2IP = ub0.OS2IP(bArr);
        this.polynomial = iOS2IP;
        if (!tn0.isIrreducible(iOS2IP)) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        this.degree = tn0.degree(this.polynomial);
    }
}
