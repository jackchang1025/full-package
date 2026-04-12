package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class qw0 {

    /* renamed from: P7 */
    private static final int f59557P7 = -1;
    private static final int PExt15 = -1;
    private static final int PInv33 = 977;

    /* renamed from: P */
    static final int[] f59556P = {-977, -2, -1, -1, -1, -1, -1, -1};
    private static final int[] PExt = {954529, 1954, 1, 0, 0, 0, 0, 0, -1954, -3, -1, -1, -1, -1, -1, -1};
    private static final int[] PExtInv = {-954529, -1955, -2, -1, -1, -1, -1, -1, 1953, 2};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.add(iArr, iArr2, iArr3) != 0 || (iArr3[7] == -1 && sh0.gte(iArr3, f59556P))) {
            yh0.add33To(8, PInv33, iArr3);
        }
    }

    public static void addExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.add(16, iArr, iArr2, iArr3) != 0 || (iArr3[15] == -1 && yh0.gte(16, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(16, iArr3, iArr4.length);
            }
        }
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        if (yh0.inc(8, iArr, iArr2) != 0 || (iArr2[7] == -1 && sh0.gte(iArr2, f59556P))) {
            yh0.add33To(8, PInv33, iArr2);
        }
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = sh0.fromBigInteger(bigInteger);
        if (iArrFromBigInteger[7] == -1) {
            int[] iArr = f59556P;
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
            yh0.shiftDownBit(8, iArr2, sh0.add(iArr, f59556P, iArr2));
        }
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f59556P, iArr, iArr2);
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
        if (sh0.mulAddTo(iArr, iArr2, iArr3) != 0 || (iArr3[15] == -1 && yh0.gte(16, iArr3, PExt))) {
            int[] iArr4 = PExtInv;
            if (yh0.addTo(iArr4.length, iArr4, iArr3) != 0) {
                yh0.incAt(16, iArr3, iArr4.length);
            }
        }
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            sh0.sub(f59556P, iArr, iArr2);
        } else {
            int[] iArr3 = f59556P;
            sh0.sub(iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[32];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 8);
        } while (yh0.lessThan(8, iArr, f59556P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        if (sh0.mul33DWordAdd(PInv33, sh0.mul33Add(PInv33, iArr, 8, iArr, 0, iArr2, 0), iArr2, 0) != 0 || (iArr2[7] == -1 && sh0.gte(iArr2, f59556P))) {
            yh0.add33To(8, PInv33, iArr2);
        }
    }

    public static void reduce32(int i, int[] iArr) {
        if ((i == 0 || sh0.mul33WordAdd(PInv33, i, iArr, 0) == 0) && !(iArr[7] == -1 && sh0.gte(iArr, f59556P))) {
            return;
        }
        yh0.add33To(8, PInv33, iArr);
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

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        if (sh0.sub(iArr, iArr2, iArr3) != 0) {
            yh0.sub33From(8, PInv33, iArr3);
        }
    }

    public static void subtractExt(int[] iArr, int[] iArr2, int[] iArr3) {
        if (yh0.sub(16, iArr, iArr2, iArr3) != 0) {
            int[] iArr4 = PExtInv;
            if (yh0.subFrom(iArr4.length, iArr4, iArr3) != 0) {
                yh0.decAt(16, iArr3, iArr4.length);
            }
        }
    }

    public static void twice(int[] iArr, int[] iArr2) {
        if (yh0.shiftUpBit(8, iArr, 0, iArr2) != 0 || (iArr2[7] == -1 && sh0.gte(iArr2, f59556P))) {
            yh0.add33To(8, PInv33, iArr2);
        }
    }
}
