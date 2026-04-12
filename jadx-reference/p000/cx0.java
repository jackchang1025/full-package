package p000;

import java.math.BigInteger;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class cx0 {
    private static final int P16 = 511;

    /* renamed from: P */
    static final int[] f55537P = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, P16};

    public static void add(int[] iArr, int[] iArr2, int[] iArr3) {
        int iAdd = yh0.add(16, iArr, iArr2, iArr3) + iArr[16] + iArr2[16];
        if (iAdd > P16 || (iAdd == P16 && yh0.m215288eq(16, iArr3, f55537P))) {
            iAdd = (yh0.inc(16, iArr3) + iAdd) & P16;
        }
        iArr3[16] = iAdd;
    }

    public static void addOne(int[] iArr, int[] iArr2) {
        int iInc = yh0.inc(16, iArr, iArr2) + iArr[16];
        if (iInc > P16 || (iInc == P16 && yh0.m215288eq(16, iArr2, f55537P))) {
            iInc = (yh0.inc(16, iArr2) + iInc) & P16;
        }
        iArr2[16] = iInc;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        int[] iArrFromBigInteger = yh0.fromBigInteger(521, bigInteger);
        if (yh0.m215288eq(17, iArrFromBigInteger, f55537P)) {
            yh0.zero(17, iArrFromBigInteger);
        }
        return iArrFromBigInteger;
    }

    public static void half(int[] iArr, int[] iArr2) {
        int i = iArr[16];
        iArr2[16] = (yh0.shiftDownBit(16, iArr, i, iArr2) >>> 23) | (i >>> 1);
    }

    public static void implMultiply(int[] iArr, int[] iArr2, int[] iArr3) {
        wh0.mul(iArr, iArr2, iArr3);
        int i = iArr[16];
        int i2 = iArr2[16];
        iArr3[32] = (i * i2) + yh0.mul31BothAdd(16, i, iArr2, i2, iArr, iArr3, 16);
    }

    public static void implSquare(int[] iArr, int[] iArr2) {
        wh0.square(iArr, iArr2);
        int i = iArr[16];
        iArr2[32] = (i * i) + yh0.mulWordAddTo(16, i << 1, iArr, 0, iArr2, 16);
    }

    public static void inv(int[] iArr, int[] iArr2) {
        ig0.checkedModOddInverse(f55537P, iArr, iArr2);
    }

    public static int isZero(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < 17; i2++) {
            i |= iArr[i2];
        }
        return (((i >>> 1) | (i & 1)) - 1) >> 31;
    }

    public static void multiply(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreate = yh0.create(33);
        implMultiply(iArr, iArr2, iArrCreate);
        reduce(iArrCreate, iArr3);
    }

    public static void negate(int[] iArr, int[] iArr2) {
        if (isZero(iArr) == 0) {
            yh0.sub(17, f55537P, iArr, iArr2);
        } else {
            int[] iArr3 = f55537P;
            yh0.sub(17, iArr3, iArr3, iArr2);
        }
    }

    public static void random(SecureRandom secureRandom, int[] iArr) {
        byte[] bArr = new byte[68];
        do {
            secureRandom.nextBytes(bArr);
            wl0.littleEndianToInt(bArr, 0, iArr, 0, 17);
            iArr[16] = iArr[16] & P16;
        } while (yh0.lessThan(17, iArr, f55537P) == 0);
    }

    public static void randomMult(SecureRandom secureRandom, int[] iArr) {
        do {
            random(secureRandom, iArr);
        } while (isZero(iArr) != 0);
    }

    public static void reduce(int[] iArr, int[] iArr2) {
        int i = iArr[32];
        int iAddTo = yh0.addTo(16, iArr, iArr2) + (yh0.shiftDownBits(16, iArr, 16, 9, i, iArr2, 0) >>> 23) + (i >>> 9);
        if (iAddTo > P16 || (iAddTo == P16 && yh0.m215288eq(16, iArr2, f55537P))) {
            iAddTo = (yh0.inc(16, iArr2) + iAddTo) & P16;
        }
        iArr2[16] = iAddTo;
    }

    public static void reduce23(int[] iArr) {
        int i = iArr[16];
        int iAddWordTo = yh0.addWordTo(16, i >>> 9, iArr) + (i & P16);
        if (iAddWordTo > P16 || (iAddWordTo == P16 && yh0.m215288eq(16, iArr, f55537P))) {
            iAddWordTo = (yh0.inc(16, iArr) + iAddWordTo) & P16;
        }
        iArr[16] = iAddWordTo;
    }

    public static void square(int[] iArr, int[] iArr2) {
        int[] iArrCreate = yh0.create(33);
        implSquare(iArr, iArrCreate);
        reduce(iArrCreate, iArr2);
    }

    public static void squareN(int[] iArr, int i, int[] iArr2) {
        int[] iArrCreate = yh0.create(33);
        implSquare(iArr, iArrCreate);
        while (true) {
            reduce(iArrCreate, iArr2);
            i--;
            if (i <= 0) {
                return;
            } else {
                implSquare(iArr2, iArrCreate);
            }
        }
    }

    public static void subtract(int[] iArr, int[] iArr2, int[] iArr3) {
        int iSub = (yh0.sub(16, iArr, iArr2, iArr3) + iArr[16]) - iArr2[16];
        if (iSub < 0) {
            iSub = (yh0.dec(16, iArr3) + iSub) & P16;
        }
        iArr3[16] = iSub;
    }

    public static void twice(int[] iArr, int[] iArr2) {
        int i = iArr[16];
        iArr2[16] = (yh0.shiftUpBit(16, iArr, i << 23, iArr2) | (i << 1)) & P16;
    }
}
