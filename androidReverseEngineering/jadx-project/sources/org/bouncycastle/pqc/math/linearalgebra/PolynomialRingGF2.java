package org.bouncycastle.pqc.math.linearalgebra;

import java.io.PrintStream;
import org.bouncycastle.asn1.cmc.BodyPartID;

/* loaded from: classes.dex */
public final class PolynomialRingGF2 {
    private PolynomialRingGF2() {
    }

    public static int add(int i2, int i3) {
        return i2 ^ i3;
    }

    public static int degree(int i2) {
        int i3 = -1;
        while (i2 != 0) {
            i3++;
            i2 >>>= 1;
        }
        return i3;
    }

    public static int gcd(int i2, int i3) {
        while (true) {
            int i4 = i3;
            int i5 = i2;
            i2 = i4;
            if (i2 == 0) {
                return i5;
            }
            i3 = remainder(i5, i2);
        }
    }

    public static int getIrreduciblePolynomial(int i2) {
        PrintStream printStream;
        String str;
        if (i2 < 0) {
            printStream = System.err;
            str = "The Degree is negative";
        } else {
            if (i2 <= 31) {
                if (i2 == 0) {
                    return 1;
                }
                int i3 = 1 << (i2 + 1);
                for (int i4 = (1 << i2) + 1; i4 < i3; i4 += 2) {
                    if (isIrreducible(i4)) {
                        return i4;
                    }
                }
                return 0;
            }
            printStream = System.err;
            str = "The Degree is more then 31";
        }
        printStream.println(str);
        return 0;
    }

    public static boolean isIrreducible(int i2) {
        if (i2 == 0) {
            return false;
        }
        int degree = degree(i2) >>> 1;
        int i3 = 2;
        for (int i4 = 0; i4 < degree; i4++) {
            i3 = modMultiply(i3, i3, i2);
            if (gcd(i3 ^ 2, i2) != 1) {
                return false;
            }
        }
        return true;
    }

    public static int modMultiply(int i2, int i3, int i4) {
        int remainder = remainder(i2, i4);
        int remainder2 = remainder(i3, i4);
        int i5 = 0;
        if (remainder2 != 0) {
            int degree = 1 << degree(i4);
            while (remainder != 0) {
                if (((byte) (remainder & 1)) == 1) {
                    i5 ^= remainder2;
                }
                remainder >>>= 1;
                remainder2 <<= 1;
                if (remainder2 >= degree) {
                    remainder2 ^= i4;
                }
            }
        }
        return i5;
    }

    public static long multiply(int i2, int i3) {
        long j2 = 0;
        if (i3 != 0) {
            long j3 = i3 & BodyPartID.bodyIdMax;
            while (i2 != 0) {
                if (((byte) (i2 & 1)) == 1) {
                    j2 ^= j3;
                }
                i2 >>>= 1;
                j3 <<= 1;
            }
        }
        return j2;
    }

    public static int remainder(int i2, int i3) {
        if (i3 == 0) {
            System.err.println("Error: to be divided by 0");
            return 0;
        }
        while (degree(i2) >= degree(i3)) {
            i2 ^= i3 << (degree(i2) - degree(i3));
        }
        return i2;
    }

    public static int rest(long j2, int i2) {
        if (i2 == 0) {
            System.err.println("Error: to be divided by 0");
            return 0;
        }
        long j3 = i2 & BodyPartID.bodyIdMax;
        while ((j2 >>> 32) != 0) {
            j2 ^= j3 << (degree(j2) - degree(j3));
        }
        int i3 = (int) (j2 & (-1));
        while (degree(i3) >= degree(i2)) {
            i3 ^= i2 << (degree(i3) - degree(i2));
        }
        return i3;
    }

    public static int degree(long j2) {
        int i2 = 0;
        while (j2 != 0) {
            i2++;
            j2 >>>= 1;
        }
        return i2 - 1;
    }
}
