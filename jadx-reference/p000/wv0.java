package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class wv0 {

    /* renamed from: P4 */
    private static final int f60975P4 = -1;
    private static final int PExt9 = -1;
    private static final int PInv33 = 21389;

    /* renamed from: P */
    static final int[] f60974P = {-21389, -2, -1, -1, -1};
    private static final int[] PExt = {457489321, 42778, 1, 0, 0, -42778, -3, -1, -1, -1};
    private static final int[] PExtInv = {-457489321, -42779, -2, -1, -1, 42777, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (ph0.add(iArr, iArr2, iArr3) != 0 || (iArr3[4] == -1 && ph0.gte(iArr3, f60974P))) {
            yh0.add33To(5, PInv33, iArr3);
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
        if (yh0.inc(5, iArr, iArr2) != 0 || (iArr2[4] == -1 && ph0.gte(iArr2, f60974P))) {
            yh0.add33To(5, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = ph0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[4] == -1) {
            int[] iArr = f60974P;
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
            yh0.shiftDownBit(5, iArr2, ph0.add(iArr, f60974P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f60974P, iArr, iArr2);
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
            ph0.sub(f60974P, iArr, iArr2);
        } else {
            int[] iArr3 = f60974P;
            ph0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[20];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 5);
        } while (yh0.lessThan(5, iArr, f60974P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (ph0.mul33DWordAdd(PInv33, ph0.mul33Add(PInv33, iArr, 5, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[4] == -1 && ph0.gte(iArr2, f60974P))) {
            yh0.add33To(5, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i == 0 || ph0.mul33WordAdd(PInv33, i, iArr, 0) == 0) && !(iArr[4] == -1 && ph0.gte(iArr, f60974P))) {
            return;
        }
        yh0.add33To(5, PInv33, iArr);
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
            yh0.sub33From(5, PInv33, iArr3);
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
        if (yh0.shiftUpBit(5, iArr, 0, iArr2) != 0 || (iArr2[4] == -1 && ph0.gte(iArr2, f60974P))) {
            yh0.add33To(5, PInv33, iArr2);
        }
    }
}
