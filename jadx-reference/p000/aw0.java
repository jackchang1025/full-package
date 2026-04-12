package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class aw0 {

    /* renamed from: P5 */
    private static final int f45652P5 = -1;
    private static final int PExt11 = -1;
    private static final int PInv33 = 4553;

    /* renamed from: P */
    static final int[] f45651P = {-4553, -2, -1, -1, -1, -1};
    private static final int[] PExt = {20729809, 9106, 1, 0, 0, 0, -9106, -3, -1, -1, -1, -1};
    private static final int[] PExtInv = {-20729809, -9107, -2, -1, -1, -1, 9105, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (qh0.add(iArr, iArr2, iArr3) != 0 || (iArr3[5] == -1 && qh0.gte(iArr3, f45651P))) {
            yh0.add33To(6, PInv33, iArr3);
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
        if (yh0.inc(6, iArr, iArr2) != 0 || (iArr2[5] == -1 && qh0.gte(iArr2, f45651P))) {
            yh0.add33To(6, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = qh0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[5] == -1) {
            int[] iArr = f45651P;
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
            yh0.shiftDownBit(6, iArr2, qh0.add(iArr, f45651P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f45651P, iArr, iArr2);
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
            qh0.sub(f45651P, iArr, iArr2);
        } else {
            int[] iArr3 = f45651P;
            qh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[24];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 6);
        } while (yh0.lessThan(6, iArr, f45651P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (qh0.mul33DWordAdd(PInv33, qh0.mul33Add(PInv33, iArr, 6, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[5] == -1 && qh0.gte(iArr2, f45651P))) {
            yh0.add33To(6, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i == 0 || qh0.mul33WordAdd(PInv33, i, iArr, 0) == 0) && !(iArr[5] == -1 && qh0.gte(iArr, f45651P))) {
            return;
        }
        yh0.add33To(6, PInv33, iArr);
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

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (qh0.sub(iArr, iArr2, iArr3) != 0) {
            yh0.sub33From(6, PInv33, iArr3);
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
        if (yh0.shiftUpBit(6, iArr, 0, iArr2) != 0 || (iArr2[5] == -1 && qh0.gte(iArr2, f45651P))) {
            yh0.add33To(6, PInv33, iArr2);
        }
    }
}
