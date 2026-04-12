package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* renamed from: od */
/* loaded from: classes2.dex */
public class C0948od {

    /* renamed from: M */
    private static final long f58781M = 4294967295L;
    private static final int PInv = 19;

    /* renamed from: P7 */
    private static final int f58783P7 = Integer.MAX_VALUE;

    /* renamed from: P */
    static final int[] f58782P = {-19, -1, -1, -1, -1, -1, -1, f58783P7};
    private static final int[] PExt = {361, 0, 0, 0, 0, 0, 0, 0, -19, -1, -1, -1, -1, -1, -1, 1073741823};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        sh0.add(iArr, iArr2, iArr3);
        if (sh0.gte(iArr3, f58782P)) {
            subPFrom(iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        yh0.add(16, iArr, iArr2, iArr3);
        if (yh0.gte(16, iArr3, PExt)) {
            subPExtFrom(iArr3);
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        yh0.inc(8, iArr, iArr2);
        if (sh0.gte(iArr2, f58782P)) {
            subPFrom(iArr2);
        }
    }

    private static int addPExtTo(int[] iArr) {
        long j = iArr[0] & f58781M;
        int[] iArr2 = PExt;
        long j2 = j + (iArr2[0] & f58781M);
        iArr[0] = (int) j2;
        long jIncAt = j2 >> 32;
        if (jIncAt != 0) {
            jIncAt = yh0.incAt(8, iArr, 1);
        }
        long j3 = ((iArr[8] & f58781M) - 19) + jIncAt;
        iArr[8] = (int) j3;
        long jDecAt = j3 >> 32;
        if (jDecAt != 0) {
            jDecAt = yh0.decAt(15, iArr, 9);
        }
        long j4 = (iArr[15] & f58781M) + (f58781M & (iArr2[15] + 1)) + jDecAt;
        iArr[15] = (int) j4;
        return (int) (j4 >> 32);
    }

    private static int addPTo(int[] iArr) {
        long j = (iArr[0] & f58781M) - 19;
        iArr[0] = (int) j;
        long jDecAt = j >> 32;
        if (jDecAt != 0) {
            jDecAt = yh0.decAt(7, iArr, 1);
        }
        long j2 = (f58781M & iArr[7]) + 2147483648L + jDecAt;
        iArr[7] = (int) j2;
        return (int) (j2 >> 32);
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = sh0.fromBigInteger(bigInteger);
        while (true) {
            int[] iArr = f58782P;
            if (!sh0.gte(iArrFromBigInteger, iArr)) {
                return iArrFromBigInteger;
            }
            sh0.subFrom(iArr, iArrFromBigInteger);
        }
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(8, iArr, 0, iArr2);
        } else {
            sh0.add(iArr, f58782P, iArr2);
            yh0.shiftDownBit(8, iArr2, 0);
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f58782P, iArr, iArr2);
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
        sh0.mulAddTo(iArr, iArr2, iArr3);
        if (yh0.gte(16, iArr3, PExt)) {
            subPExtFrom(iArr3);
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            sh0.sub(f58782P, iArr, iArr2);
        } else {
            int[] iArr3 = f58782P;
            sh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 8);
            iArr[7] = iArr[7] & f58783P7;
        } while (yh0.lessThan(8, iArr, f58782P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        int i = iArr[7];
        yh0.shiftUpBit(8, iArr, 8, i, iArr2, 0);
        int iMulByWordAddTo = sh0.mulByWordAddTo(19, iArr, iArr2) << 1;
        int i2 = iArr2[7];
        int i3 = ((i2 >>> 31) - (i >>> 31)) + iMulByWordAddTo;
        iArr2[7] = yh0.addWordTo(7, i3 * 19, iArr2) + (f58783P7 & i2);
        if (sh0.gte(iArr2, f58782P)) {
            subPFrom(iArr2);
        }
    }

    public static void reduce27(int i, int[] iArr) {
        int i2 = iArr[7];
        int i3 = (i << 1) | (i2 >>> 31);
        iArr[7] = yh0.addWordTo(7, i3 * 19, iArr) + (i2 & f58783P7);
        if (sh0.gte(iArr, f58782P)) {
            subPFrom(iArr);
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

    private static int subPExtFrom(int[] iArr) {
        long j = iArr[0] & f58781M;
        int[] iArr2 = PExt;
        long j2 = j - (iArr2[0] & f58781M);
        iArr[0] = (int) j2;
        long jDecAt = j2 >> 32;
        if (jDecAt != 0) {
            jDecAt = yh0.decAt(8, iArr, 1);
        }
        long j3 = (iArr[8] & f58781M) + 19 + jDecAt;
        iArr[8] = (int) j3;
        long jIncAt = j3 >> 32;
        if (jIncAt != 0) {
            jIncAt = yh0.incAt(15, iArr, 9);
        }
        long j4 = ((iArr[15] & f58781M) - (f58781M & (iArr2[15] + 1))) + jIncAt;
        iArr[15] = (int) j4;
        return (int) (j4 >> 32);
    }

    private static int subPFrom(int[] iArr) {
        long j = (iArr[0] & f58781M) + 19;
        iArr[0] = (int) j;
        long jIncAt = j >> 32;
        if (jIncAt != 0) {
            jIncAt = yh0.incAt(7, iArr, 1);
        }
        long j2 = ((f58781M & iArr[7]) - 2147483648L) + jIncAt;
        iArr[7] = (int) j2;
        return (int) (j2 >> 32);
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.sub(iArr, iArr2, iArr3) != 0) {
            addPTo(iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(16, iArr, iArr2, iArr3) != 0) {
            addPExtTo(iArr3);
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        yh0.shiftUpBit(8, iArr, 0, iArr2);
        if (sh0.gte(iArr2, f58782P)) {
            subPFrom(iArr2);
        }
    }
}
