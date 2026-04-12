package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class iw0 {

    /* renamed from: P6 */
    private static final int f57237P6 = -1;
    private static final int PExt13 = -1;
    private static final int PInv33 = 6803;

    /* renamed from: P */
    static final int[] f57236P = {-6803, -2, -1, -1, -1, -1, -1};
    private static final int[] PExt = {46280809, 13606, 1, 0, 0, 0, 0, -13606, -3, -1, -1, -1, -1, -1};
    private static final int[] PExtInv = {-46280809, -13607, -2, -1, -1, -1, -1, 13605, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (rh0.add(iArr, iArr2, iArr3) != 0 || (iArr3[6] == -1 && rh0.gte(iArr3, f57236P))) {
            yh0.add33To(7, PInv33, iArr3);
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
        if (yh0.inc(7, iArr, iArr2) != 0 || (iArr2[6] == -1 && rh0.gte(iArr2, f57236P))) {
            yh0.add33To(7, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = rh0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[6] == -1 && rh0.gte(iArrFromBigInteger, f57236P)) {
            yh0.add33To(7, PInv33, iArrFromBigInteger);
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        if ((iArr[0] & 1) == 0) {
            yh0.shiftDownBit(7, iArr, 0, iArr2);
        } else {
            yh0.shiftDownBit(7, iArr2, rh0.add(iArr, f57236P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f57236P, iArr, iArr2);
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
            rh0.sub(f57236P, iArr, iArr2);
        } else {
            int[] iArr3 = f57236P;
            rh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[28];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 7);
        } while (yh0.lessThan(7, iArr, f57236P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (rh0.mul33DWordAdd(PInv33, rh0.mul33Add(PInv33, iArr, 7, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[6] == -1 && rh0.gte(iArr2, f57236P))) {
            yh0.add33To(7, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i == 0 || rh0.mul33WordAdd(PInv33, i, iArr, 0) == 0) && !(iArr[6] == -1 && rh0.gte(iArr, f57236P))) {
            return;
        }
        yh0.add33To(7, PInv33, iArr);
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

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (rh0.sub(iArr, iArr2, iArr3) != 0) {
            yh0.sub33From(7, PInv33, iArr3);
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
        if (yh0.shiftUpBit(7, iArr, 0, iArr2) != 0 || (iArr2[6] == -1 && rh0.gte(iArr2, f57236P))) {
            yh0.add33To(7, PInv33, iArr2);
        }
    }
}
