package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class ew0 {

    /* renamed from: M */
    private static final long f56115M = 4294967295L;

    /* renamed from: P5 */
    private static final int f56117P5 = -1;
    private static final int PExt11 = -1;

    /* renamed from: P */
    static final int[] f56116P = {-1, -1, -2, -1, -1, -1};
    private static final int[] PExt = {1, 0, 2, 0, 1, 0, -2, -1, -3, -1, -1, -1};
    private static final int[] PExtInv = {-1, -1, -3, -1, -2, -1, 1, 0, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (qh0.add(iArr, iArr2, iArr3) != 0 || (iArr3[5] == -1 && qh0.gte(iArr3, f56116P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(12, iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && yh0.gte(12, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(12, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(6, iArr, iArr2) != 0 || (iArr2[5] == -1 && qh0.gte(iArr2, f56116P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (iArr[0] & f56115M) + 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f56115M);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = (f56115M & iArr[2]) + 1 + j2;
        iArr[2] = (int) j4;
        if ((j4 >> 32) != 0) {
            yh0.incAt(6, iArr, 3);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = qh0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[5] == -1) {
            int[] iArr = f56116P;
            if (qh0.gte(iArrFromBigInteger, iArr)) {
                qh0.subFrom(iArr, iArrFromBigInteger);
            }
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(6, iArr, 0, iArr2);
        } else {
            yh0.shiftDownBit(6, iArr2, qh0.add(iArr, f56116P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f56116P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreateExt = qh0.createExt();
        qh0.mul(iArr, iArr2, iArrCreateExt);
        reduce(iArrCreateExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (qh0.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[11] == -1 && yh0.gte(12, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(12, iArr3, iArr4.length);
            }
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            qh0.sub(f56116P, iArr, iArr2);
        } else {
            int[] iArr3 = f56116P;
            qh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[24];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 6);
        } while (yh0.lessThan(6, iArr, f56116P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j = iArr[6] & f56115M;
        long j2 = iArr[7] & f56115M;
        long j3 = iArr[8] & f56115M;
        long j4 = iArr[9] & f56115M;
        long j5 = (iArr[10] & f56115M) + j;
        long j6 = (iArr[11] & f56115M) + j2;
        long j7 = (iArr[0] & f56115M) + j5;
        int i = (int) j7;
        long j8 = (iArr[1] & f56115M) + j6 + (j7 >> 32);
        int i2 = (int) j8;
        iArr2[1] = i2;
        long j9 = j5 + j3;
        long j10 = j6 + j4;
        long j11 = (iArr[2] & f56115M) + j9 + (j8 >> 32);
        long j12 = j11 & f56115M;
        long j13 = (iArr[3] & f56115M) + j10 + (j11 >> 32);
        iArr2[3] = (int) j13;
        long j14 = (iArr[4] & f56115M) + (j9 - j) + (j13 >> 32);
        iArr2[4] = (int) j14;
        long j15 = (iArr[5] & f56115M) + (j10 - j2) + (j14 >> 32);
        iArr2[5] = (int) j15;
        long j16 = j15 >> 32;
        long j17 = j12 + j16;
        long j18 = j16 + (i & f56115M);
        iArr2[0] = (int) j18;
        long j19 = j18 >> 32;
        if (j19 != 0) {
            long j20 = j19 + (i2 & f56115M);
            iArr2[1] = (int) j20;
            j17 += j20 >> 32;
        }
        iArr2[2] = (int) j17;
        if (((j17 >> 32) == 0 || yh0.incAt(6, iArr2, 3) == 0) && !(iArr2[5] == -1 && qh0.gte(iArr2, f56116P))) {
            return;
        }
        addPInvTo(iArr2);
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            long j2 = i & f56115M;
            long j3 = (iArr[0] & f56115M) + j2;
            iArr[0] = (int) j3;
            long j4 = j3 >> 32;
            if (j4 != 0) {
                long j5 = j4 + (iArr[1] & f56115M);
                iArr[1] = (int) j5;
                j4 = j5 >> 32;
            }
            long j6 = (f56115M & iArr[2]) + j2 + j4;
            iArr[2] = (int) j6;
            j = j6 >> 32;
        } else {
            j = 0;
        }
        if ((j == 0 || yh0.incAt(6, iArr, 3) == 0) && !(iArr[5] == -1 && qh0.gte(iArr, f56116P))) {
            return;
        }
        addPInvTo(iArr);
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreateExt = qh0.createExt();
        qh0.square(iArr, iArrCreateExt);
        reduce(iArrCreateExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] iArrCreateExt = qh0.createExt();
        qh0.square(iArr, iArrCreateExt);
        while (true) {
            reduce(iArrCreateExt, iArr2);
            i--;
            if (i <= 0) {
                return;
            } else {
                qh0.square(iArr2, iArrCreateExt);
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j = (iArr[0] & f56115M) - 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f56115M);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = ((f56115M & iArr[2]) - 1) + j2;
        iArr[2] = (int) j4;
        if ((j4 >> 32) != 0) {
            yh0.decAt(6, iArr, 3);
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (qh0.sub(iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(12, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (yh0.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                yh0.decAt(12, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (yh0.shiftUpBit(6, iArr, 0, iArr2) != 0 || (iArr2[5] == -1 && qh0.gte(iArr2, f56116P))) {
            addPInvTo(iArr2);
        }
    }
}
