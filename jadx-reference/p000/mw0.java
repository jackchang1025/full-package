package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class mw0 {

    /* renamed from: M */
    private static final long f58405M = 4294967295L;

    /* renamed from: P6 */
    private static final int f58407P6 = -1;
    private static final int PExt13 = -1;

    /* renamed from: P */
    static final int[] f58406P = {1, 0, 0, -1, -1, -1, -1};
    private static final int[] PExt = {1, 0, 0, -2, -1, -1, 0, 2, 0, 0, -2, -1, -1, -1};
    private static final int[] PExtInv = {-1, -1, -1, 1, 0, 0, -1, -3, -1, -1, 1};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (rh0.add(iArr, iArr2, iArr3) != 0 || (iArr3[6] == -1 && rh0.gte(iArr3, f58406P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(14, iArr, iArr2, iArr3) != 0 || (iArr3[13] == -1 && yh0.gte(14, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(14, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(7, iArr, iArr2) != 0 || (iArr2[6] == -1 && rh0.gte(iArr2, f58406P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (iArr[0] & f58405M) - 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f58405M);
            iArr[1] = (int) j3;
            long j4 = (j3 >> 32) + (iArr[2] & f58405M);
            iArr[2] = (int) j4;
            j2 = j4 >> 32;
        }
        long j5 = (f58405M & iArr[3]) + 1 + j2;
        iArr[3] = (int) j5;
        if ((j5 >> 32) != 0) {
            yh0.incAt(7, iArr, 4);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = rh0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[6] == -1) {
            int[] iArr = f58406P;
            if (rh0.gte(iArrFromBigInteger, iArr)) {
                rh0.subFrom(iArr, iArrFromBigInteger);
            }
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(7, iArr, 0, iArr2);
        } else {
            yh0.shiftDownBit(7, iArr2, rh0.add(iArr, f58406P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f58406P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 7; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreateExt = rh0.createExt();
        rh0.mul(iArr, iArr2, iArrCreateExt);
        reduce(iArrCreateExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (rh0.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[13] == -1 && yh0.gte(14, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(14, iArr3, iArr4.length);
            }
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            rh0.sub(f58406P, iArr, iArr2);
        } else {
            int[] iArr3 = f58406P;
            rh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[28];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 7);
        } while (yh0.lessThan(7, iArr, f58406P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j = iArr[10] & f58405M;
        long j2 = iArr[11] & f58405M;
        long j3 = iArr[12] & f58405M;
        long j4 = iArr[13] & f58405M;
        long j5 = ((iArr[7] & f58405M) + j2) - 1;
        long j6 = (iArr[8] & f58405M) + j3;
        long j7 = (iArr[9] & f58405M) + j4;
        long j8 = (iArr[0] & f58405M) - j5;
        long j9 = j8 & f58405M;
        long j10 = ((iArr[1] & f58405M) - j6) + (j8 >> 32);
        int i = (int) j10;
        iArr2[1] = i;
        long j11 = ((iArr[2] & f58405M) - j7) + (j10 >> 32);
        int i2 = (int) j11;
        iArr2[2] = i2;
        long j12 = (((iArr[3] & f58405M) + j5) - j) + (j11 >> 32);
        long j13 = j12 & f58405M;
        long j14 = (((iArr[4] & f58405M) + j6) - j2) + (j12 >> 32);
        iArr2[4] = (int) j14;
        long j15 = (((iArr[5] & f58405M) + j7) - j3) + (j14 >> 32);
        iArr2[5] = (int) j15;
        long j16 = (((iArr[6] & f58405M) + j) - j4) + (j15 >> 32);
        iArr2[6] = (int) j16;
        long j17 = (j16 >> 32) + 1;
        long j18 = j13 + j17;
        long j19 = j9 - j17;
        iArr2[0] = (int) j19;
        long j20 = j19 >> 32;
        if (j20 != 0) {
            long j21 = j20 + (i & f58405M);
            iArr2[1] = (int) j21;
            long j22 = (j21 >> 32) + (i2 & f58405M);
            iArr2[2] = (int) j22;
            j18 += j22 >> 32;
        }
        iArr2[3] = (int) j18;
        if (((j18 >> 32) == 0 || yh0.incAt(7, iArr2, 4) == 0) && !(iArr2[6] == -1 && rh0.gte(iArr2, f58406P))) {
            return;
        }
        addPInvTo(iArr2);
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            long j2 = i & f58405M;
            long j3 = (iArr[0] & f58405M) - j2;
            iArr[0] = (int) j3;
            long j4 = j3 >> 32;
            if (j4 != 0) {
                long j5 = j4 + (iArr[1] & f58405M);
                iArr[1] = (int) j5;
                long j6 = (j5 >> 32) + (iArr[2] & f58405M);
                iArr[2] = (int) j6;
                j4 = j6 >> 32;
            }
            long j7 = (f58405M & iArr[3]) + j2 + j4;
            iArr[3] = (int) j7;
            j = j7 >> 32;
        } else {
            j = 0;
        }
        if ((j == 0 || yh0.incAt(7, iArr, 4) == 0) && !(iArr[6] == -1 && rh0.gte(iArr, f58406P))) {
            return;
        }
        addPInvTo(iArr);
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreateExt = rh0.createExt();
        rh0.square(iArr, iArrCreateExt);
        reduce(iArrCreateExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] iArrCreateExt = rh0.createExt();
        rh0.square(iArr, iArrCreateExt);
        while (true) {
            reduce(iArrCreateExt, iArr2);
            i--;
            if (i <= 0) {
                return;
            } else {
                rh0.square(iArr2, iArrCreateExt);
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j = (iArr[0] & f58405M) + 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f58405M);
            iArr[1] = (int) j3;
            long j4 = (j3 >> 32) + (iArr[2] & f58405M);
            iArr[2] = (int) j4;
            j2 = j4 >> 32;
        }
        long j5 = ((f58405M & iArr[3]) - 1) + j2;
        iArr[3] = (int) j5;
        if ((j5 >> 32) != 0) {
            yh0.decAt(7, iArr, 4);
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (rh0.sub(iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(14, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (yh0.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                yh0.decAt(14, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (yh0.shiftUpBit(7, iArr, 0, iArr2) != 0 || (iArr2[6] == -1 && rh0.gte(iArr2, f58406P))) {
            addPInvTo(iArr2);
        }
    }
}
