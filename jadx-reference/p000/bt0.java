package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class bt0 {

    /* renamed from: M */
    private static final long f45998M = 4294967295L;
    private static final int P7s1 = Integer.MAX_VALUE;
    private static final int PExt15s1 = Integer.MAX_VALUE;

    /* renamed from: P */
    static final int[] f45999P = {-1, -1, 0, -1, -1, -1, -1, -2};
    private static final int[] PExt = {1, 0, -2, 1, 1, -2, 0, 2, -2, -3, 3, -2, -1, -1, 0, -2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.add(iArr, iArr2, iArr3) != 0 || ((iArr3[7] >>> 1) >= Integer.MAX_VALUE && sh0.gte(iArr3, f45999P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(16, iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= Integer.MAX_VALUE && yh0.gte(16, iArr3, PExt))) {
            yh0.subFrom(16, PExt, iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(8, iArr, iArr2) != 0 || ((iArr2[7] >>> 1) >= Integer.MAX_VALUE && sh0.gte(iArr2, f45999P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (iArr[0] & f45998M) + 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f45998M);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = ((iArr[2] & f45998M) - 1) + j2;
        iArr[2] = (int) j4;
        long j5 = (iArr[3] & f45998M) + 1 + (j4 >> 32);
        iArr[3] = (int) j5;
        long j6 = j5 >> 32;
        if (j6 != 0) {
            long j7 = j6 + (iArr[4] & f45998M);
            iArr[4] = (int) j7;
            long j8 = (j7 >> 32) + (iArr[5] & f45998M);
            iArr[5] = (int) j8;
            long j9 = (j8 >> 32) + (iArr[6] & f45998M);
            iArr[6] = (int) j9;
            j6 = j9 >> 32;
        }
        iArr[7] = (int) ((f45998M & iArr[7]) + 1 + j6);
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = sh0.fromBigInteger(bigInteger);
        if ((iArrFromBigInteger[7] >>> 1) >= Integer.MAX_VALUE) {
            int[] iArr = f45999P;
            if (sh0.gte(iArrFromBigInteger, iArr)) {
                sh0.subFrom(iArr, iArrFromBigInteger);
            }
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(8, iArr, 0, iArr2);
        } else {
            yh0.shiftDownBit(8, iArr2, sh0.add(iArr, f45999P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f45999P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreateExt = sh0.createExt();
        sh0.mul(iArr, iArr2, iArrCreateExt);
        reduce(iArrCreateExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.mulAddTo(iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= Integer.MAX_VALUE && yh0.gte(16, iArr3, PExt))) {
            yh0.subFrom(16, PExt, iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            sh0.sub(f45999P, iArr, iArr2);
        } else {
            int[] iArr3 = f45999P;
            sh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 8);
        } while (yh0.lessThan(8, iArr, f45999P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j = iArr[8] & f45998M;
        long j2 = iArr[9] & f45998M;
        long j3 = iArr[10] & f45998M;
        long j4 = iArr[11] & f45998M;
        long j5 = iArr[12] & f45998M;
        long j6 = iArr[13] & f45998M;
        long j7 = iArr[14] & f45998M;
        long j8 = iArr[15] & f45998M;
        long j9 = j3 + j4;
        long j10 = j6 + j7;
        long j11 = j10 + (j8 << 1);
        long j12 = j + j2 + j10;
        long j13 = j9 + j5 + j8 + j12;
        long j14 = (iArr[0] & f45998M) + j13 + j6 + j7 + j8;
        iArr2[0] = (int) j14;
        long j15 = (((iArr[1] & f45998M) + j13) - j) + j7 + j8 + (j14 >> 32);
        iArr2[1] = (int) j15;
        long j16 = ((iArr[2] & f45998M) - j12) + (j15 >> 32);
        iArr2[2] = (int) j16;
        long j17 = ((((iArr[3] & f45998M) + j13) - j2) - j3) + j6 + (j16 >> 32);
        iArr2[3] = (int) j17;
        long j18 = ((((iArr[4] & f45998M) + j13) - j9) - j) + j7 + (j17 >> 32);
        iArr2[4] = (int) j18;
        long j19 = (iArr[5] & f45998M) + j11 + j3 + (j18 >> 32);
        iArr2[5] = (int) j19;
        long j20 = (iArr[6] & f45998M) + j4 + j7 + j8 + (j19 >> 32);
        iArr2[6] = (int) j20;
        long j21 = (iArr[7] & f45998M) + j13 + j11 + j5 + (j20 >> 32);
        iArr2[7] = (int) j21;
        reduce32((int) (j21 >> 32), iArr2);
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            long j2 = i & f45998M;
            long j3 = (iArr[0] & f45998M) + j2;
            iArr[0] = (int) j3;
            long j4 = j3 >> 32;
            if (j4 != 0) {
                long j5 = j4 + (iArr[1] & f45998M);
                iArr[1] = (int) j5;
                j4 = j5 >> 32;
            }
            long j6 = ((iArr[2] & f45998M) - j2) + j4;
            iArr[2] = (int) j6;
            long j7 = (iArr[3] & f45998M) + j2 + (j6 >> 32);
            iArr[3] = (int) j7;
            long j8 = j7 >> 32;
            if (j8 != 0) {
                long j9 = j8 + (iArr[4] & f45998M);
                iArr[4] = (int) j9;
                long j10 = (j9 >> 32) + (iArr[5] & f45998M);
                iArr[5] = (int) j10;
                long j11 = (j10 >> 32) + (iArr[6] & f45998M);
                iArr[6] = (int) j11;
                j8 = j11 >> 32;
            }
            long j12 = (f45998M & iArr[7]) + j2 + j8;
            iArr[7] = (int) j12;
            j = j12 >> 32;
        } else {
            j = 0;
        }
        if (j != 0 || ((iArr[7] >>> 1) >= Integer.MAX_VALUE && sh0.gte(iArr, f45999P))) {
            addPInvTo(iArr);
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreateExt = sh0.createExt();
        sh0.square(iArr, iArrCreateExt);
        reduce(iArrCreateExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] iArrCreateExt = sh0.createExt();
        sh0.square(iArr, iArrCreateExt);
        while (true) {
            reduce(iArrCreateExt, iArr2);
            i--;
            if (i <= 0) {
                return;
            } else {
                sh0.square(iArr2, iArrCreateExt);
            }
        }
    }

    private static void subPInvFrom(int[] iArr) {
        long j = (iArr[0] & f45998M) - 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f45998M);
            iArr[1] = (int) j3;
            j2 = j3 >> 32;
        }
        long j4 = (iArr[2] & f45998M) + 1 + j2;
        iArr[2] = (int) j4;
        long j5 = ((iArr[3] & f45998M) - 1) + (j4 >> 32);
        iArr[3] = (int) j5;
        long j6 = j5 >> 32;
        if (j6 != 0) {
            long j7 = j6 + (iArr[4] & f45998M);
            iArr[4] = (int) j7;
            long j8 = (j7 >> 32) + (iArr[5] & f45998M);
            iArr[5] = (int) j8;
            long j9 = (j8 >> 32) + (iArr[6] & f45998M);
            iArr[6] = (int) j9;
            j6 = j9 >> 32;
        }
        iArr[7] = (int) (((f45998M & iArr[7]) - 1) + j6);
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.sub(iArr, iArr2, iArr3) != 0) {
            subPInvFrom(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(16, iArr, iArr2, iArr3) != 0) {
            yh0.addTo(16, PExt, iArr3);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (yh0.shiftUpBit(8, iArr, 0, iArr2) != 0 || ((iArr2[7] >>> 1) >= Integer.MAX_VALUE && sh0.gte(iArr2, f45999P))) {
            addPInvTo(iArr2);
        }
    }
}
