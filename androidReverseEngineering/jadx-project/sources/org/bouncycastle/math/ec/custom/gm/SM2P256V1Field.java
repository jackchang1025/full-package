package org.bouncycastle.math.ec.custom.gm;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class SM2P256V1Field {

    /* renamed from: M */
    private static final long f1429M = 4294967295L;
    private static final int P7s1 = Integer.MAX_VALUE;
    private static final int PExt15s1 = Integer.MAX_VALUE;

    /* renamed from: P */
    static final int[] f1430P = {-1, -1, 0, -1, -1, -1, -1, -2};
    private static final int[] PExt = {1, 0, -2, 1, 1, -2, 0, 2, -2, -3, 3, -2, -1, -1, 0, -2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat256.add(iArr, iArr2, iArr3) != 0 || ((iArr3[7] >>> 1) >= Integer.MAX_VALUE && Nat256.gte(iArr3, f1430P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (Nat.add(16, iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= Integer.MAX_VALUE && Nat.gte(16, iArr3, PExt))) {
            Nat.subFrom(16, PExt, iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (Nat.inc(8, iArr, iArr2) != 0 || ((iArr2[7] >>> 1) >= Integer.MAX_VALUE && Nat256.gte(iArr2, f1430P))) {
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
            j3 = j4 >> 32;
        }
        long j5 = ((iArr[2] & 4294967295L) - 1) + j3;
        iArr[2] = (int) j5;
        long j6 = (iArr[3] & 4294967295L) + 1 + (j5 >> 32);
        iArr[3] = (int) j6;
        long j7 = j6 >> 32;
        if (j7 != 0) {
            long j8 = j7 + (iArr[4] & 4294967295L);
            iArr[4] = (int) j8;
            long j9 = (j8 >> 32) + (iArr[5] & 4294967295L);
            iArr[5] = (int) j9;
            long j10 = (j9 >> 32) + (iArr[6] & 4294967295L);
            iArr[6] = (int) j10;
            j7 = j10 >> 32;
        }
        iArr[7] = (int) ((4294967295L & iArr[7]) + 1 + j7);
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] fromBigInteger = Nat256.fromBigInteger(bigInteger);
        if ((fromBigInteger[7] >>> 1) >= Integer.MAX_VALUE) {
            int[] iArr = f1430P;
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
            Nat.shiftDownBit(8, iArr2, Nat256.add(iArr, f1430P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        Mod.checkedModOddInverse(f1430P, iArr, iArr2);
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
        if (Nat256.mulAddTo(iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= Integer.MAX_VALUE && Nat.gte(16, iArr3, PExt))) {
            Nat.subFrom(16, PExt, iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            Nat256.sub(f1430P, iArr, iArr2);
        } else {
            int[] iArr3 = f1430P;
            Nat256.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            Pack.littleEndianToInt(bArr, 0, iArr, 0, 8);
        } while (Nat.lessThan(8, iArr, f1430P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j2 = iArr[8] & 4294967295L;
        long j3 = iArr[9] & 4294967295L;
        long j4 = iArr[10] & 4294967295L;
        long j5 = iArr[11] & 4294967295L;
        long j6 = iArr[12] & 4294967295L;
        long j7 = iArr[13] & 4294967295L;
        long j8 = iArr[14] & 4294967295L;
        long j9 = iArr[15] & 4294967295L;
        long j10 = j4 + j5;
        long j11 = j7 + j8;
        long j12 = j11 + (j9 << 1);
        long j13 = j2 + j3 + j11;
        long j14 = j10 + j6 + j9 + j13;
        long j15 = (iArr[0] & 4294967295L) + j14 + j7 + j8 + j9 + 0;
        iArr2[0] = (int) j15;
        long j16 = (((iArr[1] & 4294967295L) + j14) - j2) + j8 + j9 + (j15 >> 32);
        iArr2[1] = (int) j16;
        long j17 = ((iArr[2] & 4294967295L) - j13) + (j16 >> 32);
        iArr2[2] = (int) j17;
        long j18 = ((((iArr[3] & 4294967295L) + j14) - j3) - j4) + j7 + (j17 >> 32);
        iArr2[3] = (int) j18;
        long j19 = ((((iArr[4] & 4294967295L) + j14) - j10) - j2) + j8 + (j18 >> 32);
        iArr2[4] = (int) j19;
        long j20 = (iArr[5] & 4294967295L) + j12 + j4 + (j19 >> 32);
        iArr2[5] = (int) j20;
        long j21 = (iArr[6] & 4294967295L) + j5 + j8 + j9 + (j20 >> 32);
        iArr2[6] = (int) j21;
        long j22 = (4294967295L & iArr[7]) + j14 + j12 + j6 + (j21 >> 32);
        iArr2[7] = (int) j22;
        reduce32((int) (j22 >> 32), iArr2);
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
                j5 = j6 >> 32;
            }
            long j7 = ((iArr[2] & 4294967295L) - j3) + j5;
            iArr[2] = (int) j7;
            long j8 = (iArr[3] & 4294967295L) + j3 + (j7 >> 32);
            iArr[3] = (int) j8;
            long j9 = j8 >> 32;
            if (j9 != 0) {
                long j10 = j9 + (iArr[4] & 4294967295L);
                iArr[4] = (int) j10;
                long j11 = (j10 >> 32) + (iArr[5] & 4294967295L);
                iArr[5] = (int) j11;
                long j12 = (j11 >> 32) + (iArr[6] & 4294967295L);
                iArr[6] = (int) j12;
                j9 = j12 >> 32;
            }
            long j13 = (4294967295L & iArr[7]) + j3 + j9;
            iArr[7] = (int) j13;
            j2 = j13 >> 32;
        } else {
            j2 = 0;
        }
        if (j2 != 0 || ((iArr[7] >>> 1) >= Integer.MAX_VALUE && Nat256.gte(iArr, f1430P))) {
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
            j3 = j4 >> 32;
        }
        long j5 = (iArr[2] & 4294967295L) + 1 + j3;
        iArr[2] = (int) j5;
        long j6 = ((iArr[3] & 4294967295L) - 1) + (j5 >> 32);
        iArr[3] = (int) j6;
        long j7 = j6 >> 32;
        if (j7 != 0) {
            long j8 = j7 + (iArr[4] & 4294967295L);
            iArr[4] = (int) j8;
            long j9 = (j8 >> 32) + (iArr[5] & 4294967295L);
            iArr[5] = (int) j9;
            long j10 = (j9 >> 32) + (iArr[6] & 4294967295L);
            iArr[6] = (int) j10;
            j7 = j10 >> 32;
        }
        iArr[7] = (int) (((4294967295L & iArr[7]) - 1) + j7);
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
        if (Nat.shiftUpBit(8, iArr, 0, iArr2) != 0 || ((iArr2[7] >>> 1) >= Integer.MAX_VALUE && Nat256.gte(iArr2, f1430P))) {
            addPInvTo(iArr2);
        }
    }
}
