package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class SecP256R1Field {

    /* renamed from: M */
    private static final long f1471M = 4294967295L;
    private static final int P7 = -1;
    private static final int PExt15s1 = Integer.MAX_VALUE;

    /* renamed from: P */
    static final int[] f1472P = {-1, -1, -1, 0, 0, 0, 1, -1};
    private static final int[] PExt = {1, 0, 0, -2, -1, -1, -2, 1, -2, 1, -2, 1, 1, -2, 2, -2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.add(iArr, iArr2, iArr3) != 0 || (iArr3[7] == -1 && Nat256.gte(iArr3, f1472P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.add(16, iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= PExt15s1 && Nat.gte(16, iArr3, PExt))) {
            Nat.subFrom(16, PExt, iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (Nat.inc(8, iArr, iArr2) != 0 || (iArr2[7] == -1 && Nat256.gte(iArr2, f1472P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
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
        long j6 = ((iArr[3] & 4294967295L) - 1) + j3;
        iArr[3] = (int) j6;
        long j7 = j6 >> 32;
        if (j7 != 0) {
            long j8 = j7 + (iArr[4] & 4294967295L);
            iArr[4] = (int) j8;
            long j9 = (j8 >> 32) + (iArr[5] & 4294967295L);
            iArr[5] = (int) j9;
            j7 = j9 >> 32;
        }
        long j10 = ((iArr[6] & 4294967295L) - 1) + j7;
        iArr[6] = (int) j10;
        iArr[7] = (int) ((4294967295L & iArr[7]) + 1 + (j10 >> 32));
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] fromBigInteger = Nat256.fromBigInteger(bigInteger);
        if (fromBigInteger[7] == -1) {
            int[] iArr = f1472P;
            if (Nat256.gte(fromBigInteger, iArr)) {
                Nat256.subFrom(iArr, fromBigInteger);
            }
        }
        return fromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            Nat.shiftDownBit(8, iArr, 0, iArr2);
        } else {
            Nat.shiftDownBit(8, iArr2, Nat256.add(iArr, f1472P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        Mod.checkedModOddInverse(f1472P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < 8; i3++) {
            i2 |= iArr[i3];
        }
        return (((i2 >>> 1) | (i2 & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] createExt = Nat256.createExt();
        Nat256.mul(iArr, iArr2, createExt);
        reduce(createExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.mulAddTo(iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= PExt15s1 && Nat.gte(16, iArr3, PExt))) {
            Nat.subFrom(16, PExt, iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            Nat256.sub(f1472P, iArr, iArr2);
        } else {
            int[] iArr3 = f1472P;
            Nat256.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            Pack.littleEndianToInt(bArr, 0, iArr, 0, 8);
        } while (Nat.lessThan(8, iArr, f1472P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j2 = iArr[9] & 4294967295L;
        long j3 = iArr[10] & 4294967295L;
        long j4 = iArr[11] & 4294967295L;
        long j5 = iArr[12] & 4294967295L;
        long j6 = iArr[13] & 4294967295L;
        long j7 = iArr[14] & 4294967295L;
        long j8 = iArr[15] & 4294967295L;
        long j9 = (iArr[8] & 4294967295L) - 6;
        long j10 = j9 + j2;
        long j11 = j2 + j3;
        long j12 = (j3 + j4) - j8;
        long j13 = j4 + j5;
        long j14 = j5 + j6;
        long j15 = j6 + j7;
        long j16 = j7 + j8;
        long j17 = j15 - j10;
        long j18 = (((iArr[0] & 4294967295L) - j13) - j17) + 0;
        iArr2[0] = (int) j18;
        long j19 = ((((iArr[1] & 4294967295L) + j11) - j14) - j16) + (j18 >> 32);
        iArr2[1] = (int) j19;
        long j20 = (((iArr[2] & 4294967295L) + j12) - j15) + (j19 >> 32);
        iArr2[2] = (int) j20;
        long j21 = ((((iArr[3] & 4294967295L) + (j13 << 1)) + j17) - j16) + (j20 >> 32);
        iArr2[3] = (int) j21;
        long j22 = ((((iArr[4] & 4294967295L) + (j14 << 1)) + j7) - j11) + (j21 >> 32);
        iArr2[4] = (int) j22;
        long j23 = (((iArr[5] & 4294967295L) + (j15 << 1)) - j12) + (j22 >> 32);
        iArr2[5] = (int) j23;
        long j24 = (iArr[6] & 4294967295L) + (j16 << 1) + j17 + (j23 >> 32);
        iArr2[6] = (int) j24;
        long j25 = (((((iArr[7] & 4294967295L) + (j8 << 1)) + j9) - j12) - j14) + (j24 >> 32);
        iArr2[7] = (int) j25;
        reduce32((int) ((j25 >> 32) + 6), iArr2);
    }

    public static void reduce32(int i2, int[] iArr) {
        long j2;
        if (i2 != 0) {
            long j3 = i2 & 4294967295L;
            long j4 = (iArr[0] & 4294967295L) + j3 + 0;
            iArr[0] = (int) j4;
            long j5 = j4 >> 32;
            if (j5 != 0) {
                long j6 = j5 + (iArr[1] & 4294967295L);
                iArr[1] = (int) j6;
                long j7 = (j6 >> 32) + (iArr[2] & 4294967295L);
                iArr[2] = (int) j7;
                j5 = j7 >> 32;
            }
            long j8 = ((iArr[3] & 4294967295L) - j3) + j5;
            iArr[3] = (int) j8;
            long j9 = j8 >> 32;
            if (j9 != 0) {
                long j10 = j9 + (iArr[4] & 4294967295L);
                iArr[4] = (int) j10;
                long j11 = (j10 >> 32) + (iArr[5] & 4294967295L);
                iArr[5] = (int) j11;
                j9 = j11 >> 32;
            }
            long j12 = ((iArr[6] & 4294967295L) - j3) + j9;
            iArr[6] = (int) j12;
            long j13 = (4294967295L & iArr[7]) + j3 + (j12 >> 32);
            iArr[7] = (int) j13;
            j2 = j13 >> 32;
        } else {
            j2 = 0;
        }
        if (j2 != 0 || (iArr[7] == -1 && Nat256.gte(iArr, f1472P))) {
            addPInvTo(iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] createExt = Nat256.createExt();
        Nat256.square(iArr, createExt);
        reduce(createExt, iArr2);
    }

    public static void squareN(int[] iArr, int i2, int[] iArr2) {
        int[] createExt = Nat256.createExt();
        Nat256.square(iArr, createExt);
        while (true) {
            reduce(createExt, iArr2);
            i2--;
            if (i2 <= 0) {
                return;
            } else {
                Nat256.square(iArr2, createExt);
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
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
        long j6 = (iArr[3] & 4294967295L) + 1 + j3;
        iArr[3] = (int) j6;
        long j7 = j6 >> 32;
        if (j7 != 0) {
            long j8 = j7 + (iArr[4] & 4294967295L);
            iArr[4] = (int) j8;
            long j9 = (j8 >> 32) + (iArr[5] & 4294967295L);
            iArr[5] = (int) j9;
            j7 = j9 >> 32;
        }
        long j10 = (iArr[6] & 4294967295L) + 1 + j7;
        iArr[6] = (int) j10;
        iArr[7] = (int) (((4294967295L & iArr[7]) - 1) + (j10 >> 32));
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.sub(iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.sub(16, iArr, iArr2, iArr3) != 0) {
            Nat.addTo(16, PExt, iArr3);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (Nat.shiftUpBit(8, iArr, 0, iArr2) != 0 || (iArr2[7] == -1 && Nat256.gte(iArr2, f1472P))) {
            addPInvTo(iArr2);
        }
    }
}
