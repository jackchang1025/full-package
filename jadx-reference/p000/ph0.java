package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public abstract class ph0 {

    /* renamed from: M */
    private static final long f59232M = 4294967295L;

    public static int add(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (iArr[0] & f59232M) + (iArr2[0] & f59232M);
        iArr3[0] = (int) j;
        long j2 = (iArr[1] & f59232M) + (iArr2[1] & f59232M) + (j >>> 32);
        iArr3[1] = (int) j2;
        long j3 = (iArr[2] & f59232M) + (iArr2[2] & f59232M) + (j2 >>> 32);
        iArr3[2] = (int) j3;
        long j4 = (iArr[3] & f59232M) + (iArr2[3] & f59232M) + (j3 >>> 32);
        iArr3[3] = (int) j4;
        long j5 = (iArr[4] & f59232M) + (iArr2[4] & f59232M) + (j4 >>> 32);
        iArr3[4] = (int) j5;
        return (int) (j5 >>> 32);
    }

    public static int addBothTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (iArr[0] & f59232M) + (iArr2[0] & f59232M) + (iArr3[0] & f59232M);
        iArr3[0] = (int) j;
        long j2 = (iArr[1] & f59232M) + (iArr2[1] & f59232M) + (iArr3[1] & f59232M) + (j >>> 32);
        iArr3[1] = (int) j2;
        long j3 = (iArr[2] & f59232M) + (iArr2[2] & f59232M) + (iArr3[2] & f59232M) + (j2 >>> 32);
        iArr3[2] = (int) j3;
        long j4 = (iArr[3] & f59232M) + (iArr2[3] & f59232M) + (iArr3[3] & f59232M) + (j3 >>> 32);
        iArr3[3] = (int) j4;
        long j5 = (iArr[4] & f59232M) + (iArr2[4] & f59232M) + (iArr3[4] & f59232M) + (j4 >>> 32);
        iArr3[4] = (int) j5;
        return (int) (j5 >>> 32);
    }

    public static int addTo(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        long j = (iArr[i] & f59232M) + (iArr2[i2] & f59232M) + (i3 & f59232M);
        iArr2[i2] = (int) j;
        long j2 = (iArr[i + 1] & f59232M) + (iArr2[r6] & f59232M) + (j >>> 32);
        iArr2[i2 + 1] = (int) j2;
        long j3 = (iArr[i + 2] & f59232M) + (iArr2[r6] & f59232M) + (j2 >>> 32);
        iArr2[i2 + 2] = (int) j3;
        long j4 = (iArr[i + 3] & f59232M) + (iArr2[r6] & f59232M) + (j3 >>> 32);
        iArr2[i2 + 3] = (int) j4;
        long j5 = (iArr[i + 4] & f59232M) + (f59232M & iArr2[r12]) + (j4 >>> 32);
        iArr2[i2 + 4] = (int) j5;
        return (int) (j5 >>> 32);
    }

    public static int addToEachOther(int[] iArr, int i, int[] iArr2, int i2) {
        long j = (iArr[i] & f59232M) + (iArr2[i2] & f59232M);
        int i3 = (int) j;
        iArr[i] = i3;
        iArr2[i2] = i3;
        long j2 = (iArr[r5] & f59232M) + (iArr2[r8] & f59232M) + (j >>> 32);
        int i4 = (int) j2;
        iArr[i + 1] = i4;
        iArr2[i2 + 1] = i4;
        long j3 = (iArr[r5] & f59232M) + (iArr2[r8] & f59232M) + (j2 >>> 32);
        int i5 = (int) j3;
        iArr[i + 2] = i5;
        iArr2[i2 + 2] = i5;
        long j4 = (iArr[r5] & f59232M) + (iArr2[r8] & f59232M) + (j3 >>> 32);
        int i6 = (int) j4;
        iArr[i + 3] = i6;
        iArr2[i2 + 3] = i6;
        long j5 = (iArr[r12] & f59232M) + (f59232M & iArr2[r14]) + (j4 >>> 32);
        int i7 = (int) j5;
        iArr[i + 4] = i7;
        iArr2[i2 + 4] = i7;
        return (int) (j5 >>> 32);
    }

    public static void copy(int[] iArr, int i, int[] iArr2, int i2) {
        iArr2[i2] = iArr[i];
        iArr2[i2 + 1] = iArr[i + 1];
        iArr2[i2 + 2] = iArr[i + 2];
        iArr2[i2 + 3] = iArr[i + 3];
        iArr2[i2 + 4] = iArr[i + 4];
    }

    public static int[] create() {
        return new int[5];
    }

    public static int[] createExt() {
        return new int[10];
    }

    public static boolean diff(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        boolean zGte = gte(iArr, i, iArr2, i2);
        if (zGte) {
            sub(iArr, i, iArr2, i2, iArr3, i3);
            return zGte;
        }
        sub(iArr2, i2, iArr, i, iArr3, i3);
        return zGte;
    }

    /* renamed from: eq */
    public static boolean m214292eq(int[] iArr, int[] iArr2) {
        for (int i = 4; i >= 0; i--) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 160) {
            throw new IllegalArgumentException();
        }
        int[] iArrCreate = create();
        for (int i = 0; i < 5; i++) {
            iArrCreate[i] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
        }
        return iArrCreate;
    }

    public static int getBit(int[] iArr, int i) {
        int i2;
        if (i == 0) {
            i2 = iArr[0];
        } else {
            int i3 = i >> 5;
            if (i3 < 0 || i3 >= 5) {
                return 0;
            }
            i2 = iArr[i3] >>> (i & 31);
        }
        return i2 & 1;
    }

    public static boolean gte(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 4; i3 >= 0; i3--) {
            int i4 = iArr[i + i3] ^ Integer.MIN_VALUE;
            int i5 = Integer.MIN_VALUE ^ iArr2[i2 + i3];
            if (i4 < i5) {
                return false;
            }
            if (i4 > i5) {
                return true;
            }
        }
        return true;
    }

    public static boolean isOne(int[] iArr) {
        if (iArr[0] != 1) {
            return false;
        }
        for (int i = 1; i < 5; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i = 0; i < 5; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = iArr2[i2] & f59232M;
        long j2 = iArr2[i2 + 1] & f59232M;
        long j3 = iArr2[i2 + 2] & f59232M;
        long j4 = iArr2[i2 + 3] & f59232M;
        long j5 = iArr2[i2 + 4] & f59232M;
        long j6 = iArr[i] & f59232M;
        long j7 = j6 * j;
        iArr3[i3] = (int) j7;
        long j8 = (j6 * j2) + (j7 >>> 32);
        iArr3[i3 + 1] = (int) j8;
        long j9 = (j6 * j3) + (j8 >>> 32);
        iArr3[i3 + 2] = (int) j9;
        long j10 = (j6 * j4) + (j9 >>> 32);
        iArr3[i3 + 3] = (int) j10;
        long j11 = (j6 * j5) + (j10 >>> 32);
        iArr3[i3 + 4] = (int) j11;
        iArr3[i3 + 5] = (int) (j11 >>> 32);
        int i4 = 1;
        int i5 = i3;
        while (i4 < 5) {
            int i6 = i5 + 1;
            long j12 = iArr[i + i4] & f59232M;
            long j13 = j;
            long j14 = (j12 * j) + (iArr3[i6] & f59232M);
            iArr3[i6] = (int) j14;
            long j15 = (j12 * j2) + (iArr3[r15] & f59232M) + (j14 >>> 32);
            int i7 = i5;
            iArr3[i5 + 2] = (int) j15;
            long j16 = (j12 * j3) + (iArr3[r2] & f59232M) + (j15 >>> 32);
            iArr3[i7 + 3] = (int) j16;
            long j17 = (j12 * j4) + (iArr3[r2] & f59232M) + (j16 >>> 32);
            iArr3[i7 + 4] = (int) j17;
            long j18 = (j12 * j5) + (iArr3[r2] & f59232M) + (j17 >>> 32);
            iArr3[i7 + 5] = (int) j18;
            iArr3[i7 + 6] = (int) (j18 >>> 32);
            i4++;
            i5 = i6;
            j = j13;
        }
    }

    public static long mul33Add(int i, int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j = i & f59232M;
        long j2 = iArr[i2] & f59232M;
        long j3 = (j * j2) + (iArr2[i3] & f59232M);
        iArr3[i4] = (int) j3;
        long j4 = iArr[i2 + 1] & f59232M;
        long j5 = (j * j4) + j2 + (iArr2[i3 + 1] & f59232M) + (j3 >>> 32);
        iArr3[i4 + 1] = (int) j5;
        long j6 = iArr[i2 + 2] & f59232M;
        long j7 = (j * j6) + j4 + (iArr2[i3 + 2] & f59232M) + (j5 >>> 32);
        iArr3[i4 + 2] = (int) j7;
        long j8 = iArr[i2 + 3] & f59232M;
        long j9 = (j * j8) + j6 + (iArr2[i3 + 3] & f59232M) + (j7 >>> 32);
        iArr3[i4 + 3] = (int) j9;
        long j10 = iArr[i2 + 4] & f59232M;
        long j11 = (j * j10) + j8 + (f59232M & iArr2[i3 + 4]) + (j9 >>> 32);
        iArr3[i4 + 4] = (int) j11;
        return (j11 >>> 32) + j10;
    }

    public static int mul33DWordAdd(int i, long j, int[] iArr, int i2) {
        long j2 = i & f59232M;
        long j3 = j & f59232M;
        long j4 = (j2 * j3) + (iArr[i2] & f59232M);
        iArr[i2] = (int) j4;
        long j5 = j >>> 32;
        long j6 = (j2 * j5) + j3;
        long j7 = j6 + (iArr[r4] & f59232M) + (j4 >>> 32);
        iArr[i2 + 1] = (int) j7;
        long j8 = j5 + (iArr[r4] & f59232M) + (j7 >>> 32);
        iArr[i2 + 2] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[r0] & f59232M);
        iArr[i2 + 3] = (int) j9;
        if ((j9 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(5, iArr, i2, 4);
    }

    public static int mul33WordAdd(int i, int i2, int[] iArr, int i3) {
        long j = i & f59232M;
        long j2 = i2 & f59232M;
        long j3 = (j * j2) + (iArr[i3] & f59232M);
        iArr[i3] = (int) j3;
        long j4 = j2 + (iArr[r5] & f59232M) + (j3 >>> 32);
        iArr[i3 + 1] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[r0] & f59232M);
        iArr[i3 + 2] = (int) j5;
        if ((j5 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(5, iArr, i3, 3);
    }

    public static int mulAddTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = iArr2[i2] & f59232M;
        long j2 = iArr2[i2 + 1] & f59232M;
        long j3 = iArr2[i2 + 2] & f59232M;
        long j4 = iArr2[i2 + 3] & f59232M;
        long j5 = iArr2[i2 + 4] & f59232M;
        int i4 = 0;
        long j6 = 0;
        int i5 = i3;
        while (i4 < 5) {
            long j7 = iArr[i + i4] & f59232M;
            long j8 = j;
            long j9 = (j7 * j) + (iArr3[i5] & f59232M);
            iArr3[i5] = (int) j9;
            int i6 = i5 + 1;
            long j10 = (j7 * j2) + (iArr3[i6] & f59232M) + (j9 >>> 32);
            iArr3[i6] = (int) j10;
            long j11 = j7 * j3;
            long j12 = j11 + (iArr3[r2] & f59232M) + (j10 >>> 32);
            iArr3[i5 + 2] = (int) j12;
            long j13 = j7 * j4;
            long j14 = j13 + (iArr3[r2] & f59232M) + (j12 >>> 32);
            iArr3[i5 + 3] = (int) j14;
            long j15 = j7 * j5;
            long j16 = j15 + (iArr3[r2] & f59232M) + (j14 >>> 32);
            iArr3[i5 + 4] = (int) j16;
            long j17 = (j16 >>> 32) + (iArr3[r12] & f59232M) + j6;
            iArr3[i5 + 5] = (int) j17;
            j6 = j17 >>> 32;
            i4++;
            i5 = i6;
            j = j8;
        }
        return (int) j6;
    }

    public static int mulWord(int i, int[] iArr, int[] iArr2, int i2) {
        long j = i & f59232M;
        long j2 = 0;
        int i3 = 0;
        do {
            long j3 = ((iArr[i3] & f59232M) * j) + j2;
            iArr2[i2 + i3] = (int) j3;
            j2 = j3 >>> 32;
            i3++;
        } while (i3 < 5);
        return (int) j2;
    }

    public static int mulWordAddExt(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        long j = i & f59232M;
        long j2 = ((iArr[i2] & f59232M) * j) + (iArr2[i3] & f59232M);
        iArr2[i3] = (int) j2;
        long j3 = ((iArr[i2 + 1] & f59232M) * j) + (iArr2[r8] & f59232M) + (j2 >>> 32);
        iArr2[i3 + 1] = (int) j3;
        long j4 = ((iArr[i2 + 2] & f59232M) * j) + (iArr2[r8] & f59232M) + (j3 >>> 32);
        iArr2[i3 + 2] = (int) j4;
        long j5 = ((iArr[i2 + 3] & f59232M) * j) + (iArr2[r8] & f59232M) + (j4 >>> 32);
        iArr2[i3 + 3] = (int) j5;
        long j6 = (j * (iArr[i2 + 4] & f59232M)) + (iArr2[r15] & f59232M) + (j5 >>> 32);
        iArr2[i3 + 4] = (int) j6;
        return (int) (j6 >>> 32);
    }

    public static int mulWordDwordAdd(int i, long j, int[] iArr, int i2) {
        long j2 = i & f59232M;
        long j3 = ((j & f59232M) * j2) + (iArr[i2] & f59232M);
        iArr[i2] = (int) j3;
        long j4 = j2 * (j >>> 32);
        long j5 = j4 + (iArr[r9] & f59232M) + (j3 >>> 32);
        iArr[i2 + 1] = (int) j5;
        long j6 = j5 >>> 32;
        long j7 = j6 + (iArr[r0] & f59232M);
        iArr[i2 + 2] = (int) j7;
        if ((j7 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(5, iArr, i2, 3);
    }

    public static int mulWordsAdd(int i, int i2, int[] iArr, int i3) {
        long j = ((i2 & f59232M) * (i & f59232M)) + (iArr[i3] & f59232M);
        iArr[i3] = (int) j;
        long j2 = (j >>> 32) + (f59232M & iArr[r1]);
        iArr[i3 + 1] = (int) j2;
        if ((j2 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(5, iArr, i3, 2);
    }

    public static void square(int[] iArr, int i, int[] iArr2, int i2) {
        long j = iArr[i] & f59232M;
        int i3 = 0;
        int i4 = 10;
        int i5 = 4;
        while (true) {
            int i6 = i5 - 1;
            long j2 = iArr[i + i5] & f59232M;
            long j3 = j2 * j2;
            iArr2[(i4 - 1) + i2] = (i3 << 31) | ((int) (j3 >>> 33));
            i4 -= 2;
            iArr2[i2 + i4] = (int) (j3 >>> 1);
            i3 = (int) j3;
            if (i6 <= 0) {
                long j4 = j * j;
                long j5 = (j4 >>> 33) | ((i3 << 31) & f59232M);
                iArr2[i2] = (int) j4;
                int i7 = ((int) (j4 >>> 32)) & 1;
                long j6 = iArr[i + 1] & f59232M;
                long j7 = iArr2[r8] & f59232M;
                long j8 = (j6 * j) + j5;
                int i8 = (int) j8;
                iArr2[i2 + 1] = (i8 << 1) | i7;
                long j9 = j7 + (j8 >>> 32);
                long j10 = iArr[i + 2] & f59232M;
                long j11 = iArr2[r9] & f59232M;
                long j12 = iArr2[r24] & f59232M;
                long j13 = (j10 * j) + j9;
                int i9 = (int) j13;
                iArr2[i2 + 2] = (i9 << 1) | (i8 >>> 31);
                long jM21a2 = AbstractC0003a2.m21a2(j10, j6, j13 >>> 32, j11);
                long j14 = j12 + (jM21a2 >>> 32);
                long j15 = jM21a2 & f59232M;
                long j16 = iArr[i + 3] & f59232M;
                long j17 = (iArr2[r6] & f59232M) + (j14 >>> 32);
                long j18 = j14 & f59232M;
                long j19 = (iArr2[r2] & f59232M) + (j17 >>> 32);
                long j20 = j17 & f59232M;
                long j21 = (j16 * j) + j15;
                int i10 = (int) j21;
                iArr2[i2 + 3] = (i9 >>> 31) | (i10 << 1);
                long jM21a22 = AbstractC0003a2.m21a2(j16, j6, j21 >>> 32, j18);
                long jM21a23 = AbstractC0003a2.m21a2(j16, j10, jM21a22 >>> 32, j20);
                long j22 = jM21a22 & f59232M;
                long j23 = j19 + (jM21a23 >>> 32);
                long j24 = jM21a23 & f59232M;
                long j25 = iArr[i + 4] & f59232M;
                long j26 = (iArr2[r6] & f59232M) + (j23 >>> 32);
                long j27 = j23 & f59232M;
                long j28 = (iArr2[r7] & f59232M) + (j26 >>> 32);
                long j29 = j26 & f59232M;
                long j30 = (j25 * j) + j22;
                int i11 = (int) j30;
                iArr2[i2 + 4] = (i10 >>> 31) | (i11 << 1);
                int i12 = i11 >>> 31;
                long jM21a24 = AbstractC0003a2.m21a2(j25, j6, j30 >>> 32, j24);
                long jM21a25 = AbstractC0003a2.m21a2(j25, j10, jM21a24 >>> 32, j27);
                long jM21a26 = AbstractC0003a2.m21a2(j25, j16, jM21a25 >>> 32, j29);
                long j31 = j28 + (jM21a26 >>> 32);
                int i13 = (int) jM21a24;
                iArr2[i2 + 5] = i12 | (i13 << 1);
                int i14 = i13 >>> 31;
                int i15 = (int) jM21a25;
                iArr2[i2 + 6] = i14 | (i15 << 1);
                int i16 = (int) jM21a26;
                iArr2[i2 + 7] = (i15 >>> 31) | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) j31;
                iArr2[i2 + 8] = i17 | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = i2 + 9;
                iArr2[i20] = ((iArr2[i20] + ((int) (j31 >>> 32))) << 1) | i19;
                return;
            }
            i5 = i6;
        }
    }

    public static int sub(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = (iArr[i] & f59232M) - (iArr2[i2] & f59232M);
        iArr3[i3] = (int) j;
        long j2 = ((iArr[i + 1] & f59232M) - (iArr2[i2 + 1] & f59232M)) + (j >> 32);
        iArr3[i3 + 1] = (int) j2;
        long j3 = ((iArr[i + 2] & f59232M) - (iArr2[i2 + 2] & f59232M)) + (j2 >> 32);
        iArr3[i3 + 2] = (int) j3;
        long j4 = ((iArr[i + 3] & f59232M) - (iArr2[i2 + 3] & f59232M)) + (j3 >> 32);
        iArr3[i3 + 3] = (int) j4;
        long j5 = ((iArr[i + 4] & f59232M) - (iArr2[i2 + 4] & f59232M)) + (j4 >> 32);
        iArr3[i3 + 4] = (int) j5;
        return (int) (j5 >> 32);
    }

    public static int subBothFrom(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = ((iArr3[0] & f59232M) - (iArr[0] & f59232M)) - (iArr2[0] & f59232M);
        iArr3[0] = (int) j;
        long j2 = (((iArr3[1] & f59232M) - (iArr[1] & f59232M)) - (iArr2[1] & f59232M)) + (j >> 32);
        iArr3[1] = (int) j2;
        long j3 = (((iArr3[2] & f59232M) - (iArr[2] & f59232M)) - (iArr2[2] & f59232M)) + (j2 >> 32);
        iArr3[2] = (int) j3;
        long j4 = (((iArr3[3] & f59232M) - (iArr[3] & f59232M)) - (iArr2[3] & f59232M)) + (j3 >> 32);
        iArr3[3] = (int) j4;
        long j5 = (((iArr3[4] & f59232M) - (iArr[4] & f59232M)) - (iArr2[4] & f59232M)) + (j4 >> 32);
        iArr3[4] = (int) j5;
        return (int) (j5 >> 32);
    }

    public static int subFrom(int[] iArr, int i, int[] iArr2, int i2) {
        long j = (iArr2[i2] & f59232M) - (iArr[i] & f59232M);
        iArr2[i2] = (int) j;
        long j2 = ((iArr2[r5] & f59232M) - (iArr[i + 1] & f59232M)) + (j >> 32);
        iArr2[i2 + 1] = (int) j2;
        long j3 = ((iArr2[r5] & f59232M) - (iArr[i + 2] & f59232M)) + (j2 >> 32);
        iArr2[i2 + 2] = (int) j3;
        long j4 = ((iArr2[r5] & f59232M) - (iArr[i + 3] & f59232M)) + (j3 >> 32);
        iArr2[i2 + 3] = (int) j4;
        long j5 = ((iArr2[r13] & f59232M) - (iArr[i + 4] & f59232M)) + (j4 >> 32);
        iArr2[i2 + 4] = (int) j5;
        return (int) (j5 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[20];
        for (int i = 0; i < 5; i++) {
            int i2 = iArr[i];
            if (i2 != 0) {
                wl0.intToBigEndian(i2, bArr, (4 - i) << 2);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static void zero(int[] iArr) {
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        iArr[4] = 0;
    }

    public static int addTo(int[] iArr, int[] iArr2) {
        long j = (iArr[0] & f59232M) + (iArr2[0] & f59232M);
        iArr2[0] = (int) j;
        long j2 = (iArr[1] & f59232M) + (iArr2[1] & f59232M) + (j >>> 32);
        iArr2[1] = (int) j2;
        long j3 = (iArr[2] & f59232M) + (iArr2[2] & f59232M) + (j2 >>> 32);
        iArr2[2] = (int) j3;
        long j4 = (iArr[3] & f59232M) + (iArr2[3] & f59232M) + (j3 >>> 32);
        iArr2[3] = (int) j4;
        long j5 = (iArr[4] & f59232M) + (f59232M & iArr2[4]) + (j4 >>> 32);
        iArr2[4] = (int) j5;
        return (int) (j5 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
        iArr2[4] = iArr[4];
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i = 4; i >= 0; i--) {
            int i2 = iArr[i] ^ Integer.MIN_VALUE;
            int i3 = Integer.MIN_VALUE ^ iArr2[i];
            if (i2 < i3) {
                return false;
            }
            if (i2 > i3) {
                return true;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = iArr2[0] & f59232M;
        long j2 = iArr2[1] & f59232M;
        long j3 = iArr2[2] & f59232M;
        long j4 = iArr2[3] & f59232M;
        long j5 = iArr2[4] & f59232M;
        long j6 = iArr[0] & f59232M;
        int i = 1;
        long j7 = j6 * j;
        iArr3[0] = (int) j7;
        char c = ' ';
        long j8 = (j7 >>> 32) + (j6 * j2);
        iArr3[1] = (int) j8;
        long j9 = (j8 >>> 32) + (j6 * j3);
        iArr3[2] = (int) j9;
        long j10 = (j9 >>> 32) + (j6 * j4);
        iArr3[3] = (int) j10;
        long j11 = (j6 * j5) + (j10 >>> 32);
        iArr3[4] = (int) j11;
        int i2 = 5;
        iArr3[5] = (int) (j11 >>> 32);
        while (true) {
            int i3 = i;
            if (i3 >= i2) {
                return;
            }
            long j12 = iArr[i3] & f59232M;
            long j13 = (j12 * j) + (iArr3[i3] & f59232M);
            iArr3[i3] = (int) j13;
            i = i3 + 1;
            char c2 = c;
            long j14 = (j12 * j2) + (iArr3[i] & f59232M) + (j13 >>> c);
            iArr3[i] = (int) j14;
            long j15 = j5;
            long j16 = (j12 * j3) + (iArr3[r8] & f59232M) + (j14 >>> c2);
            iArr3[i3 + 2] = (int) j16;
            long j17 = j16 >>> c2;
            long j18 = (j12 * j4) + (iArr3[r6] & f59232M) + j17;
            iArr3[i3 + 3] = (int) j18;
            long j19 = j18 >>> c2;
            long j20 = (j12 * j15) + (iArr3[r2] & f59232M) + j19;
            iArr3[i3 + 4] = (int) j20;
            iArr3[i3 + 5] = (int) (j20 >>> c2);
            c = c2;
            j5 = j15;
            i2 = 5;
        }
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        int i = 0;
        long j = iArr2[0];
        long j2 = f59232M;
        long j3 = j & f59232M;
        long j4 = iArr2[1] & f59232M;
        long j5 = iArr2[2] & f59232M;
        long j6 = iArr2[3] & f59232M;
        long j7 = iArr2[4] & f59232M;
        long j8 = 0;
        while (i < 5) {
            long j9 = j2;
            long j10 = iArr[i] & j9;
            int i2 = i;
            long j11 = (j10 * j3) + (iArr3[i] & j9);
            iArr3[i2] = (int) j11;
            int i3 = i2 + 1;
            long j12 = (j10 * j4) + (iArr3[i3] & j9) + (j11 >>> 32);
            iArr3[i3] = (int) j12;
            long j13 = (j10 * j5) + (iArr3[r15] & j9) + (j12 >>> 32);
            iArr3[i2 + 2] = (int) j13;
            long j14 = (j10 * j6) + (iArr3[r2] & j9) + (j13 >>> 32);
            iArr3[i2 + 3] = (int) j14;
            long j15 = (j10 * j7) + (iArr3[r2] & j9) + (j14 >>> 32);
            iArr3[i2 + 4] = (int) j15;
            long j16 = (j15 >>> 32) + (iArr3[r2] & j9) + j8;
            iArr3[i2 + 5] = (int) j16;
            j8 = j16 >>> 32;
            j2 = j9;
            j3 = j3;
            i = i3;
        }
        return (int) j8;
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j = iArr[0] & f59232M;
        int i = 10;
        int i2 = 0;
        int i3 = 4;
        while (true) {
            int i4 = i3 - 1;
            long j2 = iArr[i3] & f59232M;
            long j3 = j2 * j2;
            iArr2[i - 1] = (i2 << 31) | ((int) (j3 >>> 33));
            i -= 2;
            iArr2[i] = (int) (j3 >>> 1);
            i2 = (int) j3;
            if (i4 <= 0) {
                long j4 = j * j;
                long j5 = (j4 >>> 33) | ((i2 << 31) & f59232M);
                iArr2[0] = (int) j4;
                int i5 = ((int) (j4 >>> 32)) & 1;
                long j6 = iArr[1] & f59232M;
                long j7 = iArr2[2] & f59232M;
                long j8 = (j6 * j) + j5;
                int i6 = (int) j8;
                iArr2[1] = i5 | (i6 << 1);
                long j9 = iArr[2] & f59232M;
                long j10 = iArr2[3] & f59232M;
                long j11 = iArr2[4] & f59232M;
                long j12 = (j9 * j) + j7 + (j8 >>> 32);
                int i7 = (int) j12;
                iArr2[2] = (i7 << 1) | (i6 >>> 31);
                long jM21a2 = AbstractC0003a2.m21a2(j9, j6, j12 >>> 32, j10);
                long j13 = j11 + (jM21a2 >>> 32);
                long j14 = jM21a2 & f59232M;
                long j15 = iArr[3] & f59232M;
                long j16 = (iArr2[5] & f59232M) + (j13 >>> 32);
                long j17 = j13 & f59232M;
                long j18 = (iArr2[6] & f59232M) + (j16 >>> 32);
                long j19 = j16 & f59232M;
                long j20 = (j15 * j) + j14;
                int i8 = (int) j20;
                iArr2[3] = (i7 >>> 31) | (i8 << 1);
                int i9 = i8 >>> 31;
                long jM21a22 = AbstractC0003a2.m21a2(j15, j6, j20 >>> 32, j17);
                long jM21a23 = AbstractC0003a2.m21a2(j15, j9, jM21a22 >>> 32, j19);
                long j21 = jM21a22 & f59232M;
                long j22 = j18 + (jM21a23 >>> 32);
                long j23 = jM21a23 & f59232M;
                long j24 = iArr[4] & f59232M;
                long j25 = (iArr2[7] & f59232M) + (j22 >>> 32);
                long j26 = j22 & f59232M;
                long j27 = (iArr2[8] & f59232M) + (j25 >>> 32);
                long j28 = j25 & f59232M;
                long j29 = (j24 * j) + j21;
                int i10 = (int) j29;
                iArr2[4] = i9 | (i10 << 1);
                int i11 = i10 >>> 31;
                long jM21a24 = AbstractC0003a2.m21a2(j24, j6, j29 >>> 32, j23);
                long jM21a25 = AbstractC0003a2.m21a2(j24, j9, jM21a24 >>> 32, j26);
                long jM21a26 = AbstractC0003a2.m21a2(j24, j15, jM21a25 >>> 32, j28);
                long j30 = j27 + (jM21a26 >>> 32);
                int i12 = (int) jM21a24;
                iArr2[5] = (i12 << 1) | i11;
                int i13 = (int) jM21a25;
                iArr2[6] = (i12 >>> 31) | (i13 << 1);
                int i14 = i13 >>> 31;
                int i15 = (int) jM21a26;
                iArr2[7] = i14 | (i15 << 1);
                int i16 = i15 >>> 31;
                int i17 = (int) j30;
                iArr2[8] = i16 | (i17 << 1);
                iArr2[9] = ((iArr2[9] + ((int) (j30 >>> 32))) << 1) | (i17 >>> 31);
                return;
            }
            i3 = i4;
        }
    }

    public static int sub(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (iArr[0] & f59232M) - (iArr2[0] & f59232M);
        iArr3[0] = (int) j;
        long j2 = ((iArr[1] & f59232M) - (iArr2[1] & f59232M)) + (j >> 32);
        iArr3[1] = (int) j2;
        long j3 = ((iArr[2] & f59232M) - (iArr2[2] & f59232M)) + (j2 >> 32);
        iArr3[2] = (int) j3;
        long j4 = ((iArr[3] & f59232M) - (iArr2[3] & f59232M)) + (j3 >> 32);
        iArr3[3] = (int) j4;
        long j5 = ((iArr[4] & f59232M) - (iArr2[4] & f59232M)) + (j4 >> 32);
        iArr3[4] = (int) j5;
        return (int) (j5 >> 32);
    }

    public static int subFrom(int[] iArr, int[] iArr2) {
        long j = (iArr2[0] & f59232M) - (iArr[0] & f59232M);
        iArr2[0] = (int) j;
        long j2 = ((iArr2[1] & f59232M) - (iArr[1] & f59232M)) + (j >> 32);
        iArr2[1] = (int) j2;
        long j3 = ((iArr2[2] & f59232M) - (iArr[2] & f59232M)) + (j2 >> 32);
        iArr2[2] = (int) j3;
        long j4 = ((iArr2[3] & f59232M) - (iArr[3] & f59232M)) + (j3 >> 32);
        iArr2[3] = (int) j4;
        long j5 = ((iArr2[4] & f59232M) - (f59232M & iArr[4])) + (j4 >> 32);
        iArr2[4] = (int) j5;
        return (int) (j5 >> 32);
    }
}
