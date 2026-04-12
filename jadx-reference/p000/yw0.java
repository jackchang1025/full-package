package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class yw0 {

    /* renamed from: M */
    private static final long f61400M = 4294967295L;
    private static final int P11 = -1;
    private static final int PExt23 = -1;

    /* renamed from: P */
    static final int[] f61401P = {-1, 0, 0, -1, -2, -1, -1, -1, -1, -1, -1, -1};
    private static final int[] PExt = {1, -2, 0, 2, 0, -2, 0, 2, 1, 0, 0, 0, -2, 1, 0, -2, -3, -1, -1, -1, -1, -1, -1, -1};
    private static final int[] PExtInv = {-1, 1, -1, -3, -1, 1, -1, -3, -2, -1, -1, -1, 1, -2, -1, 1, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(12, iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && yh0.gte(12, iArr3, f61401P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(24, iArr, iArr2, iArr3) != 0 || (iArr3[23] == -1 && yh0.gte(24, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(24, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(12, iArr, iArr2) != 0 || (iArr2[11] == -1 && yh0.gte(12, iArr2, f61401P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (iArr[0] & f61400M) + 1;
        iArr[0] = (int) j;
        long j2 = ((iArr[1] & f61400M) - 1) + (j >> 32);
        iArr[1] = (int) j2;
        long j3 = j2 >> 32;
        if (j3 != 0) {
            long j4 = j3 + (iArr[2] & f61400M);
            iArr[2] = (int) j4;
            j3 = j4 >> 32;
        }
        long j5 = (iArr[3] & f61400M) + 1 + j3;
        iArr[3] = (int) j5;
        long j6 = (f61400M & iArr[4]) + 1 + (j5 >> 32);
        iArr[4] = (int) j6;
        if ((j6 >> 32) != 0) {
            yh0.incAt(12, iArr, 5);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = yh0.fromBigInteger(384, bigInteger);
        if (iArrFromBigInteger[11] == -1) {
            int[] iArr = f61401P;
            if (yh0.gte(12, iArrFromBigInteger, iArr)) {
                yh0.subFrom(12, iArr, iArrFromBigInteger);
            }
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(12, iArr, 0, iArr2);
        } else {
            yh0.shiftDownBit(12, iArr2, yh0.add(12, iArr, f61401P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f61401P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 12; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreate = yh0.create(24);
        uh0.mul(iArr, iArr2, iArrCreate);
        reduce(iArrCreate, iArr3);
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            yh0.sub(12, f61401P, iArr, iArr2);
        } else {
            int[] iArr3 = f61401P;
            yh0.sub(12, iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[48];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 12);
        } while (yh0.lessThan(12, iArr, f61401P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j = iArr[16] & f61400M;
        long j2 = iArr[17] & f61400M;
        long j3 = iArr[18] & f61400M;
        long j4 = iArr[19] & f61400M;
        long j5 = iArr[20] & f61400M;
        long j6 = iArr[21] & f61400M;
        long j7 = iArr[22] & f61400M;
        long j8 = iArr[23] & f61400M;
        long j9 = ((iArr[12] & f61400M) + j5) - 1;
        long j10 = (iArr[13] & f61400M) + j7;
        long j11 = (iArr[14] & f61400M) + j7 + j8;
        long j12 = (iArr[15] & f61400M) + j8;
        long j13 = j2 + j6;
        long j14 = j6 - j8;
        long j15 = j7 - j8;
        long j16 = j9 + j14;
        long j17 = (iArr[0] & f61400M) + j16;
        iArr2[0] = (int) j17;
        long j18 = (((iArr[1] & f61400M) + j8) - j9) + j10 + (j17 >> 32);
        iArr2[1] = (int) j18;
        long j19 = (((iArr[2] & f61400M) - j6) - j10) + j11 + (j18 >> 32);
        iArr2[2] = (int) j19;
        long j20 = ((iArr[3] & f61400M) - j11) + j12 + j16 + (j19 >> 32);
        iArr2[3] = (int) j20;
        long j21 = (((((iArr[4] & f61400M) + j) + j6) + j10) - j12) + j16 + (j20 >> 32);
        iArr2[4] = (int) j21;
        long j22 = ((iArr[5] & f61400M) - j) + j10 + j11 + j13 + (j21 >> 32);
        iArr2[5] = (int) j22;
        long j23 = (((iArr[6] & f61400M) + j3) - j2) + j11 + j12 + (j22 >> 32);
        iArr2[6] = (int) j23;
        long j24 = ((((iArr[7] & f61400M) + j) + j4) - j3) + j12 + (j23 >> 32);
        iArr2[7] = (int) j24;
        long j25 = (((((iArr[8] & f61400M) + j) + j2) + j5) - j4) + (j24 >> 32);
        iArr2[8] = (int) j25;
        long j26 = (((iArr[9] & f61400M) + j3) - j5) + j13 + (j25 >> 32);
        iArr2[9] = (int) j26;
        long j27 = ((((iArr[10] & f61400M) + j3) + j4) - j14) + j15 + (j26 >> 32);
        iArr2[10] = (int) j27;
        long j28 = ((((iArr[11] & f61400M) + j4) + j5) - j15) + (j27 >> 32);
        iArr2[11] = (int) j28;
        reduce32((int) ((j28 >> 32) + 1), iArr2);
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            long j2 = i & f61400M;
            long j3 = (iArr[0] & f61400M) + j2;
            iArr[0] = (int) j3;
            long j4 = ((iArr[1] & f61400M) - j2) + (j3 >> 32);
            iArr[1] = (int) j4;
            long j5 = j4 >> 32;
            if (j5 != 0) {
                long j6 = j5 + (iArr[2] & f61400M);
                iArr[2] = (int) j6;
                j5 = j6 >> 32;
            }
            long j7 = (iArr[3] & f61400M) + j2 + j5;
            iArr[3] = (int) j7;
            long j8 = (f61400M & iArr[4]) + j2 + (j7 >> 32);
            iArr[4] = (int) j8;
            j = j8 >> 32;
        } else {
            j = 0;
        }
        if ((j == 0 || yh0.incAt(12, iArr, 5) == 0) && !(iArr[11] == -1 && yh0.gte(12, iArr, f61401P))) {
            return;
        }
        addPInvTo(iArr);
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreate = yh0.create(24);
        uh0.square(iArr, iArrCreate);
        reduce(iArrCreate, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] iArrCreate = yh0.create(24);
        uh0.square(iArr, iArrCreate);
        while (true) {
            reduce(iArrCreate, iArr2);
            i--;
            if (i <= 0) {
                return;
            } else {
                uh0.square(iArr2, iArrCreate);
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j = (iArr[0] & f61400M) - 1;
        iArr[0] = (int) j;
        long j2 = (iArr[1] & f61400M) + 1 + (j >> 32);
        iArr[1] = (int) j2;
        long j3 = j2 >> 32;
        if (j3 != 0) {
            long j4 = j3 + (iArr[2] & f61400M);
            iArr[2] = (int) j4;
            j3 = j4 >> 32;
        }
        long j5 = ((iArr[3] & f61400M) - 1) + j3;
        iArr[3] = (int) j5;
        long j6 = ((f61400M & iArr[4]) - 1) + (j5 >> 32);
        iArr[4] = (int) j6;
        if ((j6 >> 32) != 0) {
            yh0.decAt(12, iArr, 5);
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(12, iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(24, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (yh0.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                yh0.decAt(24, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (yh0.shiftUpBit(12, iArr, 0, iArr2) != 0 || (iArr2[11] == -1 && yh0.gte(12, iArr2, f61401P))) {
            addPInvTo(iArr2);
        }
    }
}
