package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat224;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class SecP224R1Field {

    /* renamed from: M */
    private static final long f1462M = 4294967295L;
    private static final int P6 = -1;
    private static final int PExt13 = -1;

    /* renamed from: P */
    static final int[] f1463P = {1, 0, 0, -1, -1, -1, -1};
    private static final int[] PExt = {1, 0, 0, -2, -1, -1, 0, 2, 0, 0, -2, -1, -1, -1};
    private static final int[] PExtInv = {-1, -1, -1, 1, 0, 0, -1, -3, -1, -1, 1};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat224.add(iArr, iArr2, iArr3) != 0 || (iArr3[6] == -1 && Nat224.gte(iArr3, f1463P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.add(14, iArr, iArr2, iArr3) != 0 || (iArr3[13] == -1 && Nat.gte(14, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (Nat.addTo(iArr4.length, iArr4, iArr3) != 0) {
                Nat.incAt(14, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (Nat.inc(7, iArr, iArr2) != 0 || (iArr2[6] == -1 && Nat224.gte(iArr2, f1463P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j2 = (iArr[0] & 4294967295L) - 1;
        iArr[0] = (int) j2;
        long j3 = j2 >> 32;
        if (j3 != 0) {
            long j4 = j3 + (iArr[1] & 4294967295L);
            iArr[1] = (int) j4;
            long j5 = (j4 >> 32) + (iArr[2] & 4294967295L);
            iArr[2] = (int) j5;
            j3 = j5 >> 32;
        }
        long j6 = (4294967295L & iArr[3]) + 1 + j3;
        iArr[3] = (int) j6;
        if ((j6 >> 32) != 0) {
            Nat.incAt(7, iArr, 4);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] fromBigInteger = Nat224.fromBigInteger(bigInteger);
        if (fromBigInteger[6] == -1) {
            int[] iArr = f1463P;
            if (Nat224.gte(fromBigInteger, iArr)) {
                Nat224.subFrom(iArr, fromBigInteger);
            }
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            Nat.shiftDownBit(7, iArr, 0, iArr2);
        } else {
            Nat.shiftDownBit(7, iArr2, Nat224.add(iArr, f1463P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        Mod.checkedModOddInverse(f1463P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 7; i3++) {
            i2 |= iArr[i3];
        }
        return (((i2 >>> 1) | (i2 & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = Nat224.createExt();
        Nat224.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat224.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[13] == -1 && Nat.gte(14, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (Nat.addTo(iArr4.length, iArr4, iArr3) != 0) {
                Nat.incAt(14, iArr3, iArr4.length);
            }
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            Nat224.sub(f1463P, iArr, iArr2);
        } else {
            int[] iArr3 = f1463P;
            Nat224.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[28];
        do {
            secureRandom.nextBytes(bArr);
            Pack.littleEndianToInt(bArr, 0, iArr, 0, 7);
        } while (Nat.lessThan(7, iArr, f1463P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j2 = iArr[10] & 4294967295L;
        long j3 = iArr[11] & 4294967295L;
        long j4 = iArr[12] & 4294967295L;
        long j5 = iArr[13] & 4294967295L;
        long j6 = ((iArr[7] & 4294967295L) + j3) - 1;
        long j7 = (iArr[8] & 4294967295L) + j4;
        long j8 = (iArr[9] & 4294967295L) + j5;
        long j9 = ((iArr[0] & 4294967295L) - j6) + 0;
        long j10 = j9 & 4294967295L;
        long j11 = ((iArr[1] & 4294967295L) - j7) + (j9 >> 32);
        int i2 = (int) j11;
        iArr2[1] = i2;
        long j12 = ((iArr[2] & 4294967295L) - j8) + (j11 >> 32);
        int i3 = (int) j12;
        iArr2[2] = i3;
        long j13 = (((iArr[3] & 4294967295L) + j6) - j2) + (j12 >> 32);
        long j14 = (((iArr[4] & 4294967295L) + j7) - j3) + (j13 >> 32);
        iArr2[4] = (int) j14;
        long j15 = (((iArr[5] & 4294967295L) + j8) - j4) + (j14 >> 32);
        iArr2[5] = (int) j15;
        long j16 = (((iArr[6] & 4294967295L) + j2) - j5) + (j15 >> 32);
        iArr2[6] = (int) j16;
        long j17 = (j16 >> 32) + 1;
        long j18 = (j13 & 4294967295L) + j17;
        long j19 = j10 - j17;
        iArr2[0] = (int) j19;
        long j20 = j19 >> 32;
        if (j20 != 0) {
            long j21 = j20 + (i2 & 4294967295L);
            iArr2[1] = (int) j21;
            long j22 = (j21 >> 32) + (4294967295L & i3);
            iArr2[2] = (int) j22;
            j18 += j22 >> 32;
        }
        iArr2[3] = (int) j18;
        if (((j18 >> 32) == 0 || Nat.incAt(7, iArr2, 4) == 0) && !(iArr2[6] == -1 && Nat224.gte(iArr2, f1463P))) {
            return;
        }
        addPInvTo(iArr2);
    }

    public static void reduce32(int i2, int[] iArr) {
        long j2;
        if (i2 != 0) {
            long j3 = i2 & 4294967295L;
            long j4 = ((iArr[0] & 4294967295L) - j3) + 0;
            iArr[0] = (int) j4;
            long j5 = j4 >> 32;
            if (j5 != 0) {
                long j6 = j5 + (iArr[1] & 4294967295L);
                iArr[1] = (int) j6;
                long j7 = (j6 >> 32) + (iArr[2] & 4294967295L);
                iArr[2] = (int) j7;
                j5 = j7 >> 32;
            }
            long j8 = (4294967295L & iArr[3]) + j3 + j5;
            iArr[3] = (int) j8;
            j2 = j8 >> 32;
        } else {
            j2 = 0;
        }
        if ((j2 == 0 || Nat.incAt(7, iArr, 4) == 0) && !(iArr[6] == -1 && Nat224.gte(iArr, f1463P))) {
            return;
        }
        addPInvTo(iArr);
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = Nat224.createExt();
        Nat224.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i2, int[] iArr2) {
        int[] createExt = Nat224.createExt();
        Nat224.square(iArr, createExt);
        while (true) {
            reduce(createExt, iArr2);
            i2--;
            if (i2 <= 0) {
                return;
            } else {
                Nat224.square(iArr2, createExt);
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j2 = (iArr[0] & 4294967295L) + 1;
        iArr[0] = (int) j2;
        long j3 = j2 >> 32;
        if (j3 != 0) {
            long j4 = j3 + (iArr[1] & 4294967295L);
            iArr[1] = (int) j4;
            long j5 = (j4 >> 32) + (iArr[2] & 4294967295L);
            iArr[2] = (int) j5;
            j3 = j5 >> 32;
        }
        long j6 = ((4294967295L & iArr[3]) - 1) + j3;
        iArr[3] = (int) j6;
        if ((j6 >> 32) != 0) {
            Nat.decAt(7, iArr, 4);
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat224.sub(iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.sub(14, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (Nat.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                Nat.decAt(14, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (Nat.shiftUpBit(7, iArr, 0, iArr2) != 0 || (iArr2[6] == -1 && Nat224.gte(iArr2, f1463P))) {
            addPInvTo(iArr2);
        }
    }
}
