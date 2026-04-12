package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class sv0 {

    /* renamed from: M */
    private static final long f60100M = 4294967295L;

    /* renamed from: P4 */
    private static final int f60102P4 = -1;
    private static final int PExt9 = -1;
    private static final int PInv = -2147483647;

    /* renamed from: P */
    static final int[] f60101P = {Integer.MAX_VALUE, -1, -1, -1, -1};
    private static final int[] PExt = {1, 1073741825, 0, 0, 0, -2, -2, -1, -1, -1};
    private static final int[] PExtInv = {-1, -1073741826, -1, -1, -1, 1, 1};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (ph0.add(iArr, iArr2, iArr3) != 0 || (iArr3[4] == -1 && ph0.gte(iArr3, f60101P))) {
            yh0.addWordTo(5, PInv, iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(10, iArr, iArr2, iArr3) != 0 || (iArr3[9] == -1 && yh0.gte(10, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(10, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(5, iArr, iArr2) != 0 || (iArr2[4] == -1 && ph0.gte(iArr2, f60101P))) {
            yh0.addWordTo(5, PInv, iArr2);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = ph0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[4] == -1) {
            int[] iArr = f60101P;
            if (ph0.gte(iArrFromBigInteger, iArr)) {
                ph0.subFrom(iArr, iArrFromBigInteger);
            }
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(5, iArr, 0, iArr2);
        } else {
            yh0.shiftDownBit(5, iArr2, ph0.add(iArr, f60101P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f60101P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreateExt = ph0.createExt();
        ph0.mul(iArr, iArr2, iArrCreateExt);
        reduce(iArrCreateExt, iArr3);
    }

    public static void multiplyAddToExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (ph0.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[9] == -1 && yh0.gte(10, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(10, iArr3, iArr4.length);
            }
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            ph0.sub(f60101P, iArr, iArr2);
        } else {
            int[] iArr3 = f60101P;
            ph0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[20];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 5);
        } while (yh0.lessThan(5, iArr, f60101P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        long j = iArr[5] & f60100M;
        long j2 = iArr[6] & f60100M;
        long j3 = iArr[7] & f60100M;
        long j4 = iArr[8] & f60100M;
        long j5 = iArr[9] & f60100M;
        long j6 = (iArr[0] & f60100M) + j + (j << 31);
        iArr2[0] = (int) j6;
        long j7 = (iArr[1] & f60100M) + j2 + (j2 << 31) + (j6 >>> 32);
        iArr2[1] = (int) j7;
        long j8 = (iArr[2] & f60100M) + j3 + (j3 << 31) + (j7 >>> 32);
        iArr2[2] = (int) j8;
        long j9 = (iArr[3] & f60100M) + j4 + (j4 << 31) + (j8 >>> 32);
        iArr2[3] = (int) j9;
        long j10 = (f60100M & iArr[4]) + j5 + (j5 << 31) + (j9 >>> 32);
        iArr2[4] = (int) j10;
        reduce32((int) (j10 >>> 32), iArr2);
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i == 0 || ph0.mulWordsAdd(PInv, i, iArr, 0) == 0) && !(iArr[4] == -1 && ph0.gte(iArr, f60101P))) {
            return;
        }
        yh0.addWordTo(5, PInv, iArr);
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreateExt = ph0.createExt();
        ph0.square(iArr, iArrCreateExt);
        reduce(iArrCreateExt, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] iArrCreateExt = ph0.createExt();
        ph0.square(iArr, iArrCreateExt);
        while (true) {
            reduce(iArrCreateExt, iArr2);
            i--;
            if (i <= 0) {
                return;
            } else {
                ph0.square(iArr2, iArrCreateExt);
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (ph0.sub(iArr, iArr2, iArr3) != 0) {
            yh0.subWordFrom(5, PInv, iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(10, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (yh0.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                yh0.decAt(10, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (yh0.shiftUpBit(5, iArr, 0, iArr2) != 0 || (iArr2[4] == -1 && ph0.gte(iArr2, f60101P))) {
            yh0.addWordTo(5, PInv, iArr2);
        }
    }
}
