package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class uw0 {

    /* renamed from: M */
    private static final long f60521M = 4294967295L;

    /* renamed from: P7 */
    private static final int f60523P7 = -1;
    private static final int PExt15s1 = Integer.MAX_VALUE;

    /* renamed from: P */
    static final int[] f60522P = {-1, -1, -1, 0, 0, 0, 1, -1};
    private static final int[] PExt = {1, 0, 0, -2, -1, -1, -2, 1, -2, 1, -2, 1, 1, -2, 2, -2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.add(iArr, iArr2, iArr3) != 0 || (iArr3[7] == -1 && sh0.gte(iArr3, f60522P))) {
            addPInvTo(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(16, iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= PExt15s1 && yh0.gte(16, iArr3, PExt))) {
            yh0.subFrom(16, PExt, iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(8, iArr, iArr2) != 0 || (iArr2[7] == -1 && sh0.gte(iArr2, f60522P))) {
            addPInvTo(iArr2);
        }
    }

    private static void addPInvTo(int[] iArr) {
        long j = (iArr[0] & f60521M) + 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f60521M);
            iArr[1] = (int) j3;
            long j4 = (j3 >> 32) + (iArr[2] & f60521M);
            iArr[2] = (int) j4;
            j2 = j4 >> 32;
        }
        long j5 = ((iArr[3] & f60521M) - 1) + j2;
        iArr[3] = (int) j5;
        long j6 = j5 >> 32;
        if (j6 != 0) {
            long j7 = j6 + (iArr[4] & f60521M);
            iArr[4] = (int) j7;
            long j8 = (j7 >> 32) + (iArr[5] & f60521M);
            iArr[5] = (int) j8;
            j6 = j8 >> 32;
        }
        long j9 = ((iArr[6] & f60521M) - 1) + j6;
        iArr[6] = (int) j9;
        iArr[7] = (int) ((f60521M & iArr[7]) + 1 + (j9 >> 32));
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = sh0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[7] == -1) {
            int[] iArr = f60522P;
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
            yh0.shiftDownBit(8, iArr2, sh0.add(iArr, f60522P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f60522P, iArr, iArr2);
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
        if (sh0.mulAddTo(iArr, iArr2, iArr3) != 0 || ((iArr3[15] >>> 1) >= PExt15s1 && yh0.gte(16, iArr3, PExt))) {
            yh0.subFrom(16, PExt, iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            sh0.sub(f60522P, iArr, iArr2);
        } else {
            int[] iArr3 = f60522P;
            sh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 8);
        } while (yh0.lessThan(8, iArr, f60522P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j = iArr[8] & f60521M;
        long j2 = iArr[9] & f60521M;
        long j3 = iArr[10] & f60521M;
        long j4 = iArr[11] & f60521M;
        long j5 = iArr[12] & f60521M;
        long j6 = iArr[13] & f60521M;
        long j7 = iArr[14] & f60521M;
        long j8 = iArr[15] & f60521M;
        long j9 = j - 6;
        long j10 = j9 + j2;
        long j11 = j2 + j3;
        long j12 = (j3 + j4) - j8;
        long j13 = j4 + j5;
        long j14 = j5 + j6;
        long j15 = j6 + j7;
        long j16 = j7 + j8;
        long j17 = j15 - j10;
        long j18 = ((iArr[0] & f60521M) - j13) - j17;
        iArr2[0] = (int) j18;
        long j19 = ((((iArr[1] & f60521M) + j11) - j14) - j16) + (j18 >> 32);
        iArr2[1] = (int) j19;
        long j20 = (((iArr[2] & f60521M) + j12) - j15) + (j19 >> 32);
        iArr2[2] = (int) j20;
        long j21 = ((((iArr[3] & f60521M) + (j13 << 1)) + j17) - j16) + (j20 >> 32);
        iArr2[3] = (int) j21;
        long j22 = ((((iArr[4] & f60521M) + (j14 << 1)) + j7) - j11) + (j21 >> 32);
        iArr2[4] = (int) j22;
        long j23 = (((iArr[5] & f60521M) + (j15 << 1)) - j12) + (j22 >> 32);
        iArr2[5] = (int) j23;
        long j24 = (iArr[6] & f60521M) + (j16 << 1) + j17 + (j23 >> 32);
        iArr2[6] = (int) j24;
        long j25 = (((((iArr[7] & f60521M) + (j8 << 1)) + j9) - j12) - j14) + (j24 >> 32);
        iArr2[7] = (int) j25;
        reduce32((int) ((j25 >> 32) + 6), iArr2);
    }

    public static void reduce32(int i, int[] iArr) {
        long j;
        if (i != 0) {
            long j2 = i & f60521M;
            long j3 = (iArr[0] & f60521M) + j2;
            iArr[0] = (int) j3;
            long j4 = j3 >> 32;
            if (j4 != 0) {
                long j5 = j4 + (iArr[1] & f60521M);
                iArr[1] = (int) j5;
                long j6 = (j5 >> 32) + (iArr[2] & f60521M);
                iArr[2] = (int) j6;
                j4 = j6 >> 32;
            }
            long j7 = ((iArr[3] & f60521M) - j2) + j4;
            iArr[3] = (int) j7;
            long j8 = j7 >> 32;
            if (j8 != 0) {
                long j9 = j8 + (iArr[4] & f60521M);
                iArr[4] = (int) j9;
                long j10 = (j9 >> 32) + (iArr[5] & f60521M);
                iArr[5] = (int) j10;
                j8 = j10 >> 32;
            }
            long j11 = ((iArr[6] & f60521M) - j2) + j8;
            iArr[6] = (int) j11;
            long j12 = (f60521M & iArr[7]) + j2 + (j11 >> 32);
            iArr[7] = (int) j12;
            j = j12 >> 32;
        } else {
            j = 0;
        }
        if (j != 0 || (iArr[7] == -1 && sh0.gte(iArr, f60522P))) {
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
        long j = (iArr[0] & f60521M) - 1;
        iArr[0] = (int) j;
        long j2 = j >> 32;
        if (j2 != 0) {
            long j3 = j2 + (iArr[1] & f60521M);
            iArr[1] = (int) j3;
            long j4 = (j3 >> 32) + (iArr[2] & f60521M);
            iArr[2] = (int) j4;
            j2 = j4 >> 32;
        }
        long j5 = (iArr[3] & f60521M) + 1 + j2;
        iArr[3] = (int) j5;
        long j6 = j5 >> 32;
        if (j6 != 0) {
            long j7 = j6 + (iArr[4] & f60521M);
            iArr[4] = (int) j7;
            long j8 = (j7 >> 32) + (iArr[5] & f60521M);
            iArr[5] = (int) j8;
            j6 = j8 >> 32;
        }
        long j9 = (iArr[6] & f60521M) + 1 + j6;
        iArr[6] = (int) j9;
        iArr[7] = (int) (((f60521M & iArr[7]) - 1) + (j9 >> 32));
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
        if (yh0.shiftUpBit(8, iArr, 0, iArr2) != 0 || (iArr2[7] == -1 && sh0.gte(iArr2, f60522P))) {
            addPInvTo(iArr2);
        }
    }
}
