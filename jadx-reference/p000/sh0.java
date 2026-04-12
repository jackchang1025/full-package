package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public abstract class sh0 {

    /* renamed from: M */
    private static final long f59987M = 4294967295L;

    public static int add(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = (iArr[i] & f59987M) + (iArr2[i2] & f59987M);
        iArr3[i3] = (int) j;
        long j2 = (iArr[i + 1] & f59987M) + (iArr2[i2 + 1] & f59987M) + (j >>> 32);
        iArr3[i3 + 1] = (int) j2;
        long j3 = (iArr[i + 2] & f59987M) + (iArr2[i2 + 2] & f59987M) + (j2 >>> 32);
        iArr3[i3 + 2] = (int) j3;
        long j4 = (iArr[i + 3] & f59987M) + (iArr2[i2 + 3] & f59987M) + (j3 >>> 32);
        iArr3[i3 + 3] = (int) j4;
        long j5 = (iArr[i + 4] & f59987M) + (iArr2[i2 + 4] & f59987M) + (j4 >>> 32);
        iArr3[i3 + 4] = (int) j5;
        long j6 = (iArr[i + 5] & f59987M) + (iArr2[i2 + 5] & f59987M) + (j5 >>> 32);
        iArr3[i3 + 5] = (int) j6;
        long j7 = (iArr[i + 6] & f59987M) + (iArr2[i2 + 6] & f59987M) + (j6 >>> 32);
        iArr3[i3 + 6] = (int) j7;
        long j8 = (iArr[i + 7] & f59987M) + (iArr2[i2 + 7] & f59987M) + (j7 >>> 32);
        iArr3[i3 + 7] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int addBothTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = (iArr[i] & f59987M) + (iArr2[i2] & f59987M) + (iArr3[i3] & f59987M);
        iArr3[i3] = (int) j;
        long j2 = (iArr[i + 1] & f59987M) + (iArr2[i2 + 1] & f59987M) + (iArr3[r7] & f59987M) + (j >>> 32);
        iArr3[i3 + 1] = (int) j2;
        long j3 = (iArr[i + 2] & f59987M) + (iArr2[i2 + 2] & f59987M) + (iArr3[r7] & f59987M) + (j2 >>> 32);
        iArr3[i3 + 2] = (int) j3;
        long j4 = (iArr[i + 3] & f59987M) + (iArr2[i2 + 3] & f59987M) + (iArr3[r7] & f59987M) + (j3 >>> 32);
        iArr3[i3 + 3] = (int) j4;
        long j5 = (iArr[i + 4] & f59987M) + (iArr2[i2 + 4] & f59987M) + (iArr3[r7] & f59987M) + (j4 >>> 32);
        iArr3[i3 + 4] = (int) j5;
        long j6 = (iArr[i + 5] & f59987M) + (iArr2[i2 + 5] & f59987M) + (iArr3[r7] & f59987M) + (j5 >>> 32);
        iArr3[i3 + 5] = (int) j6;
        long j7 = (iArr[i + 6] & f59987M) + (iArr2[i2 + 6] & f59987M) + (iArr3[r7] & f59987M) + (j6 >>> 32);
        iArr3[i3 + 6] = (int) j7;
        long j8 = (iArr[i + 7] & f59987M) + (iArr2[i2 + 7] & f59987M) + (iArr3[r15] & f59987M) + (j7 >>> 32);
        iArr3[i3 + 7] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int addTo(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        long j = (iArr[i] & f59987M) + (iArr2[i2] & f59987M) + (i3 & f59987M);
        iArr2[i2] = (int) j;
        long j2 = (iArr[i + 1] & f59987M) + (iArr2[r6] & f59987M) + (j >>> 32);
        iArr2[i2 + 1] = (int) j2;
        long j3 = (iArr[i + 2] & f59987M) + (iArr2[r6] & f59987M) + (j2 >>> 32);
        iArr2[i2 + 2] = (int) j3;
        long j4 = (iArr[i + 3] & f59987M) + (iArr2[r6] & f59987M) + (j3 >>> 32);
        iArr2[i2 + 3] = (int) j4;
        long j5 = (iArr[i + 4] & f59987M) + (iArr2[r6] & f59987M) + (j4 >>> 32);
        iArr2[i2 + 4] = (int) j5;
        long j6 = (iArr[i + 5] & f59987M) + (iArr2[r6] & f59987M) + (j5 >>> 32);
        iArr2[i2 + 5] = (int) j6;
        long j7 = (iArr[i + 6] & f59987M) + (iArr2[r6] & f59987M) + (j6 >>> 32);
        iArr2[i2 + 6] = (int) j7;
        long j8 = (iArr[i + 7] & f59987M) + (f59987M & iArr2[r12]) + (j7 >>> 32);
        iArr2[i2 + 7] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int addToEachOther(int[] iArr, int i, int[] iArr2, int i2) {
        long j = (iArr[i] & f59987M) + (iArr2[i2] & f59987M);
        int i3 = (int) j;
        iArr[i] = i3;
        iArr2[i2] = i3;
        long j2 = (iArr[r5] & f59987M) + (iArr2[r8] & f59987M) + (j >>> 32);
        int i4 = (int) j2;
        iArr[i + 1] = i4;
        iArr2[i2 + 1] = i4;
        long j3 = (iArr[r5] & f59987M) + (iArr2[r8] & f59987M) + (j2 >>> 32);
        int i5 = (int) j3;
        iArr[i + 2] = i5;
        iArr2[i2 + 2] = i5;
        long j4 = (iArr[r5] & f59987M) + (iArr2[r8] & f59987M) + (j3 >>> 32);
        int i6 = (int) j4;
        iArr[i + 3] = i6;
        iArr2[i2 + 3] = i6;
        long j5 = (iArr[r5] & f59987M) + (iArr2[r8] & f59987M) + (j4 >>> 32);
        int i7 = (int) j5;
        iArr[i + 4] = i7;
        iArr2[i2 + 4] = i7;
        long j6 = (iArr[r5] & f59987M) + (iArr2[r8] & f59987M) + (j5 >>> 32);
        int i8 = (int) j6;
        iArr[i + 5] = i8;
        iArr2[i2 + 5] = i8;
        long j7 = (iArr[r5] & f59987M) + (iArr2[r8] & f59987M) + (j6 >>> 32);
        int i9 = (int) j7;
        iArr[i + 6] = i9;
        iArr2[i2 + 6] = i9;
        long j8 = (iArr[r12] & f59987M) + (f59987M & iArr2[r14]) + (j7 >>> 32);
        int i10 = (int) j8;
        iArr[i + 7] = i10;
        iArr2[i2 + 7] = i10;
        return (int) (j8 >>> 32);
    }

    public static void copy(int[] iArr, int i, int[] iArr2, int i2) {
        iArr2[i2] = iArr[i];
        iArr2[i2 + 1] = iArr[i + 1];
        iArr2[i2 + 2] = iArr[i + 2];
        iArr2[i2 + 3] = iArr[i + 3];
        iArr2[i2 + 4] = iArr[i + 4];
        iArr2[i2 + 5] = iArr[i + 5];
        iArr2[i2 + 6] = iArr[i + 6];
        iArr2[i2 + 7] = iArr[i + 7];
    }

    public static void copy64(long[] jArr, int i, long[] jArr2, int i2) {
        jArr2[i2] = jArr[i];
        jArr2[i2 + 1] = jArr[i + 1];
        jArr2[i2 + 2] = jArr[i + 2];
        jArr2[i2 + 3] = jArr[i + 3];
    }

    public static int[] create() {
        return new int[8];
    }

    public static long[] create64() {
        return new long[4];
    }

    public static int[] createExt() {
        return new int[16];
    }

    public static long[] createExt64() {
        return new long[8];
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
    public static boolean m214618eq(int[] iArr, int[] iArr2) {
        for (int i = 7; i >= 0; i--) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean eq64(long[] jArr, long[] jArr2) {
        for (int i = 3; i >= 0; i--) {
            if (jArr[i] != jArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 256) {
            throw new IllegalArgumentException();
        }
        int[] iArrCreate = create();
        for (int i = 0; i < 8; i++) {
            iArrCreate[i] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
        }
        return iArrCreate;
    }

    public static long[] fromBigInteger64(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 256) {
            throw new IllegalArgumentException();
        }
        long[] jArrCreate64 = create64();
        for (int i = 0; i < 4; i++) {
            jArrCreate64[i] = bigInteger.longValue();
            bigInteger = bigInteger.shiftRight(64);
        }
        return jArrCreate64;
    }

    public static int getBit(int[] iArr, int i) {
        int i2;
        if (i == 0) {
            i2 = iArr[0];
        } else {
            if ((i & v10.MASK) != i) {
                return 0;
            }
            i2 = iArr[i >>> 5] >>> (i & 31);
        }
        return i2 & 1;
    }

    public static boolean gte(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 7; i3 >= 0; i3--) {
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
        for (int i = 1; i < 8; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOne64(long[] jArr) {
        if (jArr[0] != 1) {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            if (jArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i = 0; i < 8; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero64(long[] jArr) {
        for (int i = 0; i < 4; i++) {
            if (jArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = iArr2[i2] & f59987M;
        long j2 = iArr2[i2 + 1] & f59987M;
        long j3 = iArr2[i2 + 2] & f59987M;
        long j4 = iArr2[i2 + 3] & f59987M;
        long j5 = iArr2[i2 + 4] & f59987M;
        long j6 = iArr2[i2 + 5] & f59987M;
        long j7 = iArr2[i2 + 6] & f59987M;
        long j8 = iArr2[i2 + 7] & f59987M;
        long j9 = iArr[i] & f59987M;
        long j10 = j9 * j;
        iArr3[i3] = (int) j10;
        long j11 = (j9 * j2) + (j10 >>> 32);
        iArr3[i3 + 1] = (int) j11;
        long j12 = (j9 * j3) + (j11 >>> 32);
        iArr3[i3 + 2] = (int) j12;
        long j13 = (j9 * j4) + (j12 >>> 32);
        iArr3[i3 + 3] = (int) j13;
        long j14 = (j9 * j5) + (j13 >>> 32);
        iArr3[i3 + 4] = (int) j14;
        long j15 = (j9 * j6) + (j14 >>> 32);
        iArr3[i3 + 5] = (int) j15;
        long j16 = (j9 * j7) + (j15 >>> 32);
        iArr3[i3 + 6] = (int) j16;
        long j17 = (j9 * j8) + (j16 >>> 32);
        iArr3[i3 + 7] = (int) j17;
        iArr3[i3 + 8] = (int) (j17 >>> 32);
        int i4 = 1;
        int i5 = i3;
        while (i4 < 8) {
            int i6 = i5 + 1;
            int i7 = i5;
            long j18 = iArr[i + i4] & f59987M;
            long j19 = (j18 * j) + (iArr3[i6] & f59987M);
            iArr3[i6] = (int) j19;
            long j20 = (j18 * j2) + (iArr3[r3] & f59987M) + (j19 >>> 32);
            iArr3[i7 + 2] = (int) j20;
            long j21 = (j18 * j3) + (iArr3[r24] & f59987M) + (j20 >>> 32);
            iArr3[i7 + 3] = (int) j21;
            long j22 = (j18 * j4) + (iArr3[r24] & f59987M) + (j21 >>> 32);
            iArr3[i7 + 4] = (int) j22;
            long j23 = (j18 * j5) + (iArr3[r24] & f59987M) + (j22 >>> 32);
            iArr3[i7 + 5] = (int) j23;
            long j24 = (j18 * j6) + (iArr3[r24] & f59987M) + (j23 >>> 32);
            iArr3[i7 + 6] = (int) j24;
            long j25 = (j18 * j7) + (iArr3[r24] & f59987M) + (j24 >>> 32);
            iArr3[i7 + 7] = (int) j25;
            long j26 = (j18 * j8) + (iArr3[r24] & f59987M) + (j25 >>> 32);
            iArr3[i7 + 8] = (int) j26;
            iArr3[i7 + 9] = (int) (j26 >>> 32);
            i4++;
            i5 = i6;
        }
    }

    public static long mul33Add(int i, int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j = i & f59987M;
        long j2 = iArr[i2] & f59987M;
        long j3 = (j * j2) + (iArr2[i3] & f59987M);
        iArr3[i4] = (int) j3;
        long j4 = iArr[i2 + 1] & f59987M;
        long j5 = (j * j4) + j2 + (iArr2[i3 + 1] & f59987M) + (j3 >>> 32);
        iArr3[i4 + 1] = (int) j5;
        long j6 = iArr[i2 + 2] & f59987M;
        long j7 = (j * j6) + j4 + (iArr2[i3 + 2] & f59987M) + (j5 >>> 32);
        iArr3[i4 + 2] = (int) j7;
        long j8 = iArr[i2 + 3] & f59987M;
        long j9 = (j * j8) + j6 + (iArr2[i3 + 3] & f59987M) + (j7 >>> 32);
        iArr3[i4 + 3] = (int) j9;
        long j10 = iArr[i2 + 4] & f59987M;
        long j11 = (j * j10) + j8 + (iArr2[i3 + 4] & f59987M) + (j9 >>> 32);
        iArr3[i4 + 4] = (int) j11;
        long j12 = iArr[i2 + 5] & f59987M;
        long j13 = (j * j12) + j10 + (iArr2[i3 + 5] & f59987M) + (j11 >>> 32);
        iArr3[i4 + 5] = (int) j13;
        long j14 = iArr[i2 + 6] & f59987M;
        long j15 = (j * j14) + j12 + (iArr2[i3 + 6] & f59987M) + (j13 >>> 32);
        iArr3[i4 + 6] = (int) j15;
        long j16 = iArr[i2 + 7] & f59987M;
        long j17 = (j * j16) + j14 + (f59987M & iArr2[i3 + 7]) + (j15 >>> 32);
        iArr3[i4 + 7] = (int) j17;
        return (j17 >>> 32) + j16;
    }

    public static int mul33DWordAdd(int i, long j, int[] iArr, int i2) {
        long j2 = i & f59987M;
        long j3 = j & f59987M;
        long j4 = (j2 * j3) + (iArr[i2] & f59987M);
        iArr[i2] = (int) j4;
        long j5 = j >>> 32;
        long j6 = (j2 * j5) + j3;
        long j7 = j6 + (iArr[r4] & f59987M) + (j4 >>> 32);
        iArr[i2 + 1] = (int) j7;
        long j8 = j5 + (iArr[r4] & f59987M) + (j7 >>> 32);
        iArr[i2 + 2] = (int) j8;
        long j9 = (j8 >>> 32) + (iArr[r0] & f59987M);
        iArr[i2 + 3] = (int) j9;
        if ((j9 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(8, iArr, i2, 4);
    }

    public static int mul33WordAdd(int i, int i2, int[] iArr, int i3) {
        long j = i & f59987M;
        long j2 = i2 & f59987M;
        long j3 = (j * j2) + (iArr[i3] & f59987M);
        iArr[i3] = (int) j3;
        long j4 = j2 + (iArr[r5] & f59987M) + (j3 >>> 32);
        iArr[i3 + 1] = (int) j4;
        long j5 = (j4 >>> 32) + (iArr[r0] & f59987M);
        iArr[i3 + 2] = (int) j5;
        if ((j5 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(8, iArr, i3, 3);
    }

    public static int mulAddTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = iArr2[i2] & f59987M;
        long j2 = iArr2[i2 + 1] & f59987M;
        long j3 = iArr2[i2 + 2] & f59987M;
        long j4 = iArr2[i2 + 3] & f59987M;
        long j5 = iArr2[i2 + 4] & f59987M;
        long j6 = iArr2[i2 + 5] & f59987M;
        long j7 = iArr2[i2 + 6] & f59987M;
        long j8 = iArr2[i2 + 7] & f59987M;
        int i4 = i3;
        int i5 = 0;
        long j9 = 0;
        while (i5 < 8) {
            long j10 = j2;
            long j11 = iArr[i + i5] & f59987M;
            long j12 = (j11 * j) + (iArr3[i4] & f59987M);
            iArr3[i4] = (int) j12;
            int i6 = i4 + 1;
            long j13 = (j11 * j10) + (iArr3[i6] & f59987M) + (j12 >>> 32);
            iArr3[i6] = (int) j13;
            int i7 = i5;
            long j14 = (j11 * j3) + (iArr3[r5] & f59987M) + (j13 >>> 32);
            iArr3[i4 + 2] = (int) j14;
            long j15 = (j11 * j4) + (iArr3[r0] & f59987M) + (j14 >>> 32);
            iArr3[i4 + 3] = (int) j15;
            long j16 = (j11 * j5) + (iArr3[r0] & f59987M) + (j15 >>> 32);
            iArr3[i4 + 4] = (int) j16;
            long j17 = (j11 * j6) + (iArr3[r0] & f59987M) + (j16 >>> 32);
            iArr3[i4 + 5] = (int) j17;
            long j18 = (j11 * j7) + (iArr3[r0] & f59987M) + (j17 >>> 32);
            iArr3[i4 + 6] = (int) j18;
            long j19 = (j11 * j8) + (iArr3[r0] & f59987M) + (j18 >>> 32);
            iArr3[i4 + 7] = (int) j19;
            long j20 = (j19 >>> 32) + (iArr3[r16] & f59987M) + j9;
            iArr3[i4 + 8] = (int) j20;
            j9 = j20 >>> 32;
            i5 = i7 + 1;
            j2 = j10;
            i4 = i6;
        }
        return (int) j9;
    }

    public static int mulByWord(int i, int[] iArr) {
        long j = i & f59987M;
        long j2 = (iArr[0] & f59987M) * j;
        iArr[0] = (int) j2;
        long j3 = ((iArr[1] & f59987M) * j) + (j2 >>> 32);
        iArr[1] = (int) j3;
        long j4 = ((iArr[2] & f59987M) * j) + (j3 >>> 32);
        iArr[2] = (int) j4;
        long j5 = ((iArr[3] & f59987M) * j) + (j4 >>> 32);
        iArr[3] = (int) j5;
        long j6 = ((iArr[4] & f59987M) * j) + (j5 >>> 32);
        iArr[4] = (int) j6;
        long j7 = ((iArr[5] & f59987M) * j) + (j6 >>> 32);
        iArr[5] = (int) j7;
        long j8 = ((iArr[6] & f59987M) * j) + (j7 >>> 32);
        iArr[6] = (int) j8;
        long j9 = (j * (f59987M & iArr[7])) + (j8 >>> 32);
        iArr[7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int mulByWordAddTo(int i, int[] iArr, int[] iArr2) {
        long j = i & f59987M;
        long j2 = ((iArr2[0] & f59987M) * j) + (iArr[0] & f59987M);
        iArr2[0] = (int) j2;
        long j3 = ((iArr2[1] & f59987M) * j) + (iArr[1] & f59987M) + (j2 >>> 32);
        iArr2[1] = (int) j3;
        long j4 = ((iArr2[2] & f59987M) * j) + (iArr[2] & f59987M) + (j3 >>> 32);
        iArr2[2] = (int) j4;
        long j5 = ((iArr2[3] & f59987M) * j) + (iArr[3] & f59987M) + (j4 >>> 32);
        iArr2[3] = (int) j5;
        long j6 = ((iArr2[4] & f59987M) * j) + (iArr[4] & f59987M) + (j5 >>> 32);
        iArr2[4] = (int) j6;
        long j7 = ((iArr2[5] & f59987M) * j) + (iArr[5] & f59987M) + (j6 >>> 32);
        iArr2[5] = (int) j7;
        long j8 = ((iArr2[6] & f59987M) * j) + (iArr[6] & f59987M) + (j7 >>> 32);
        iArr2[6] = (int) j8;
        long j9 = (j * (iArr2[7] & f59987M)) + (f59987M & iArr[7]) + (j8 >>> 32);
        iArr2[7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int mulWord(int i, int[] iArr, int[] iArr2, int i2) {
        long j = i & f59987M;
        long j2 = 0;
        int i3 = 0;
        do {
            long j3 = ((iArr[i3] & f59987M) * j) + j2;
            iArr2[i2 + i3] = (int) j3;
            j2 = j3 >>> 32;
            i3++;
        } while (i3 < 8);
        return (int) j2;
    }

    public static int mulWordAddTo(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        long j = i & f59987M;
        long j2 = ((iArr[i2] & f59987M) * j) + (iArr2[i3] & f59987M);
        iArr2[i3] = (int) j2;
        long j3 = ((iArr[i2 + 1] & f59987M) * j) + (iArr2[r8] & f59987M) + (j2 >>> 32);
        iArr2[i3 + 1] = (int) j3;
        long j4 = ((iArr[i2 + 2] & f59987M) * j) + (iArr2[r8] & f59987M) + (j3 >>> 32);
        iArr2[i3 + 2] = (int) j4;
        long j5 = ((iArr[i2 + 3] & f59987M) * j) + (iArr2[r8] & f59987M) + (j4 >>> 32);
        iArr2[i3 + 3] = (int) j5;
        long j6 = ((iArr[i2 + 4] & f59987M) * j) + (iArr2[r8] & f59987M) + (j5 >>> 32);
        iArr2[i3 + 4] = (int) j6;
        long j7 = ((iArr[i2 + 5] & f59987M) * j) + (iArr2[r8] & f59987M) + (j6 >>> 32);
        iArr2[i3 + 5] = (int) j7;
        long j8 = ((iArr[i2 + 6] & f59987M) * j) + (iArr2[r8] & f59987M) + (j7 >>> 32);
        iArr2[i3 + 6] = (int) j8;
        long j9 = (j * (iArr[i2 + 7] & f59987M)) + (iArr2[r15] & f59987M) + (j8 >>> 32);
        iArr2[i3 + 7] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int mulWordDwordAdd(int i, long j, int[] iArr, int i2) {
        long j2 = i & f59987M;
        long j3 = ((j & f59987M) * j2) + (iArr[i2] & f59987M);
        iArr[i2] = (int) j3;
        long j4 = j2 * (j >>> 32);
        long j5 = j4 + (iArr[r9] & f59987M) + (j3 >>> 32);
        iArr[i2 + 1] = (int) j5;
        long j6 = j5 >>> 32;
        long j7 = j6 + (iArr[r0] & f59987M);
        iArr[i2 + 2] = (int) j7;
        if ((j7 >>> 32) == 0) {
            return 0;
        }
        return yh0.incAt(8, iArr, i2, 3);
    }

    public static void square(int[] iArr, int i, int[] iArr2, int i2) {
        long j = iArr[i] & f59987M;
        int i3 = 0;
        int i4 = 16;
        int i5 = 7;
        while (true) {
            int i6 = i5 - 1;
            long j2 = iArr[i + i5] & f59987M;
            long j3 = j2 * j2;
            iArr2[(i4 - 1) + i2] = (i3 << 31) | ((int) (j3 >>> 33));
            i4 -= 2;
            iArr2[i2 + i4] = (int) (j3 >>> 1);
            i3 = (int) j3;
            if (i6 <= 0) {
                long j4 = j * j;
                long j5 = (j4 >>> 33) | ((i3 << 31) & f59987M);
                iArr2[i2] = (int) j4;
                int i7 = ((int) (j4 >>> 32)) & 1;
                long j6 = iArr[i + 1] & f59987M;
                long j7 = iArr2[r8] & f59987M;
                long j8 = (j6 * j) + j5;
                int i8 = (int) j8;
                iArr2[i2 + 1] = (i8 << 1) | i7;
                long j9 = j7 + (j8 >>> 32);
                long j10 = iArr[i + 2] & f59987M;
                long j11 = iArr2[r9] & f59987M;
                long j12 = iArr2[r24] & f59987M;
                long j13 = (j10 * j) + j9;
                int i9 = (int) j13;
                iArr2[i2 + 2] = (i9 << 1) | (i8 >>> 31);
                long jM21a2 = AbstractC0003a2.m21a2(j10, j6, j13 >>> 32, j11);
                long j14 = j12 + (jM21a2 >>> 32);
                long j15 = jM21a2 & f59987M;
                long j16 = iArr[i + 3] & f59987M;
                long j17 = (iArr2[r6] & f59987M) + (j14 >>> 32);
                long j18 = j14 & f59987M;
                long j19 = (iArr2[r2] & f59987M) + (j17 >>> 32);
                long j20 = j17 & f59987M;
                long j21 = (j16 * j) + j15;
                int i10 = (int) j21;
                iArr2[i2 + 3] = (i9 >>> 31) | (i10 << 1);
                long jM21a22 = AbstractC0003a2.m21a2(j16, j6, j21 >>> 32, j18);
                long jM21a23 = AbstractC0003a2.m21a2(j16, j10, jM21a22 >>> 32, j20);
                long j22 = jM21a22 & f59987M;
                long j23 = j19 + (jM21a23 >>> 32);
                long j24 = jM21a23 & f59987M;
                long j25 = iArr[i + 4] & f59987M;
                long j26 = (iArr2[r4] & f59987M) + (j23 >>> 32);
                long j27 = j23 & f59987M;
                long j28 = (iArr2[r5] & f59987M) + (j26 >>> 32);
                long j29 = j26 & f59987M;
                long j30 = (j25 * j) + j22;
                int i11 = (int) j30;
                iArr2[i2 + 4] = (i10 >>> 31) | (i11 << 1);
                int i12 = i11 >>> 31;
                long jM21a24 = AbstractC0003a2.m21a2(j25, j6, j30 >>> 32, j24);
                long jM21a25 = AbstractC0003a2.m21a2(j25, j10, jM21a24 >>> 32, j27);
                long j31 = jM21a24 & f59987M;
                long jM21a26 = AbstractC0003a2.m21a2(j25, j16, jM21a25 >>> 32, j29);
                long j32 = jM21a25 & f59987M;
                long j33 = j28 + (jM21a26 >>> 32);
                long j34 = jM21a26 & f59987M;
                long j35 = iArr[i + 5] & f59987M;
                long j36 = (iArr2[r10] & f59987M) + (j33 >>> 32);
                long j37 = j33 & f59987M;
                long j38 = (iArr2[r6] & f59987M) + (j36 >>> 32);
                long j39 = j36 & f59987M;
                long j40 = (j35 * j) + j31;
                int i13 = (int) j40;
                iArr2[i2 + 5] = i12 | (i13 << 1);
                int i14 = i13 >>> 31;
                long jM21a27 = AbstractC0003a2.m21a2(j35, j6, j40 >>> 32, j32);
                long jM21a28 = AbstractC0003a2.m21a2(j35, j10, jM21a27 >>> 32, j34);
                long j41 = jM21a27 & f59987M;
                long jM21a29 = AbstractC0003a2.m21a2(j35, j16, jM21a28 >>> 32, j37);
                long j42 = jM21a28 & f59987M;
                long jM21a210 = AbstractC0003a2.m21a2(j35, j25, jM21a29 >>> 32, j39);
                long j43 = jM21a29 & f59987M;
                long j44 = j38 + (jM21a210 >>> 32);
                long j45 = jM21a210 & f59987M;
                long j46 = iArr[i + 6] & f59987M;
                long j47 = (iArr2[r9] & f59987M) + (j44 >>> 32);
                long j48 = j44 & f59987M;
                long j49 = (iArr2[r11] & f59987M) + (j47 >>> 32);
                long j50 = j47 & f59987M;
                long j51 = (j46 * j) + j41;
                int i15 = (int) j51;
                iArr2[i2 + 6] = (i15 << 1) | i14;
                int i16 = i15 >>> 31;
                long jM21a211 = AbstractC0003a2.m21a2(j46, j6, j51 >>> 32, j42);
                long jM21a212 = AbstractC0003a2.m21a2(j46, j10, jM21a211 >>> 32, j43);
                long j52 = jM21a211 & f59987M;
                long jM21a213 = AbstractC0003a2.m21a2(j46, j16, jM21a212 >>> 32, j45);
                long j53 = jM21a212 & f59987M;
                long jM21a214 = AbstractC0003a2.m21a2(j46, j25, jM21a213 >>> 32, j48);
                long j54 = jM21a213 & f59987M;
                long jM21a215 = AbstractC0003a2.m21a2(j46, j35, jM21a214 >>> 32, j50);
                long j55 = jM21a214 & f59987M;
                long j56 = j49 + (jM21a215 >>> 32);
                long j57 = jM21a215 & f59987M;
                long j58 = iArr[i + 7] & f59987M;
                long j59 = (iArr2[r3] & f59987M) + (j56 >>> 32);
                long j60 = j56 & f59987M;
                long j61 = (iArr2[r2] & f59987M) + (j59 >>> 32);
                long j62 = j59 & f59987M;
                long j63 = (j58 * j) + j52;
                int i17 = (int) j63;
                iArr2[i2 + 7] = (i17 << 1) | i16;
                int i18 = i17 >>> 31;
                long jM21a216 = AbstractC0003a2.m21a2(j58, j6, j63 >>> 32, j53);
                long jM21a217 = AbstractC0003a2.m21a2(j58, j10, jM21a216 >>> 32, j54);
                long jM21a218 = AbstractC0003a2.m21a2(j58, j16, jM21a217 >>> 32, j55);
                long jM21a219 = AbstractC0003a2.m21a2(j58, j25, jM21a218 >>> 32, j57);
                long jM21a220 = AbstractC0003a2.m21a2(j58, j35, jM21a219 >>> 32, j60);
                long jM21a221 = AbstractC0003a2.m21a2(j58, j46, jM21a220 >>> 32, j62);
                long j64 = j61 + (jM21a221 >>> 32);
                int i19 = (int) jM21a216;
                iArr2[i2 + 8] = (i19 << 1) | i18;
                int i20 = (int) jM21a217;
                iArr2[i2 + 9] = (i19 >>> 31) | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) jM21a218;
                iArr2[i2 + 10] = i21 | (i22 << 1);
                int i23 = i22 >>> 31;
                int i24 = (int) jM21a219;
                iArr2[i2 + 11] = i23 | (i24 << 1);
                int i25 = i24 >>> 31;
                int i26 = (int) jM21a220;
                iArr2[i2 + 12] = i25 | (i26 << 1);
                int i27 = i26 >>> 31;
                int i28 = (int) jM21a221;
                iArr2[i2 + 13] = i27 | (i28 << 1);
                int i29 = i28 >>> 31;
                int i30 = (int) j64;
                iArr2[i2 + 14] = i29 | (i30 << 1);
                int i31 = i30 >>> 31;
                int i32 = i2 + 15;
                iArr2[i32] = i31 | ((iArr2[i32] + ((int) (j64 >>> 32))) << 1);
                return;
            }
            i5 = i6;
        }
    }

    public static int sub(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = (iArr[i] & f59987M) - (iArr2[i2] & f59987M);
        iArr3[i3] = (int) j;
        long j2 = ((iArr[i + 1] & f59987M) - (iArr2[i2 + 1] & f59987M)) + (j >> 32);
        iArr3[i3 + 1] = (int) j2;
        long j3 = ((iArr[i + 2] & f59987M) - (iArr2[i2 + 2] & f59987M)) + (j2 >> 32);
        iArr3[i3 + 2] = (int) j3;
        long j4 = ((iArr[i + 3] & f59987M) - (iArr2[i2 + 3] & f59987M)) + (j3 >> 32);
        iArr3[i3 + 3] = (int) j4;
        long j5 = ((iArr[i + 4] & f59987M) - (iArr2[i2 + 4] & f59987M)) + (j4 >> 32);
        iArr3[i3 + 4] = (int) j5;
        long j6 = ((iArr[i + 5] & f59987M) - (iArr2[i2 + 5] & f59987M)) + (j5 >> 32);
        iArr3[i3 + 5] = (int) j6;
        long j7 = ((iArr[i + 6] & f59987M) - (iArr2[i2 + 6] & f59987M)) + (j6 >> 32);
        iArr3[i3 + 6] = (int) j7;
        long j8 = ((iArr[i + 7] & f59987M) - (iArr2[i2 + 7] & f59987M)) + (j7 >> 32);
        iArr3[i3 + 7] = (int) j8;
        return (int) (j8 >> 32);
    }

    public static int subBothFrom(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = ((iArr3[0] & f59987M) - (iArr[0] & f59987M)) - (iArr2[0] & f59987M);
        iArr3[0] = (int) j;
        long j2 = (((iArr3[1] & f59987M) - (iArr[1] & f59987M)) - (iArr2[1] & f59987M)) + (j >> 32);
        iArr3[1] = (int) j2;
        long j3 = (((iArr3[2] & f59987M) - (iArr[2] & f59987M)) - (iArr2[2] & f59987M)) + (j2 >> 32);
        iArr3[2] = (int) j3;
        long j4 = (((iArr3[3] & f59987M) - (iArr[3] & f59987M)) - (iArr2[3] & f59987M)) + (j3 >> 32);
        iArr3[3] = (int) j4;
        long j5 = (((iArr3[4] & f59987M) - (iArr[4] & f59987M)) - (iArr2[4] & f59987M)) + (j4 >> 32);
        iArr3[4] = (int) j5;
        long j6 = (((iArr3[5] & f59987M) - (iArr[5] & f59987M)) - (iArr2[5] & f59987M)) + (j5 >> 32);
        iArr3[5] = (int) j6;
        long j7 = (((iArr3[6] & f59987M) - (iArr[6] & f59987M)) - (iArr2[6] & f59987M)) + (j6 >> 32);
        iArr3[6] = (int) j7;
        long j8 = (((iArr3[7] & f59987M) - (iArr[7] & f59987M)) - (iArr2[7] & f59987M)) + (j7 >> 32);
        iArr3[7] = (int) j8;
        return (int) (j8 >> 32);
    }

    public static int subFrom(int[] iArr, int i, int[] iArr2, int i2) {
        long j = (iArr2[i2] & f59987M) - (iArr[i] & f59987M);
        iArr2[i2] = (int) j;
        long j2 = ((iArr2[r5] & f59987M) - (iArr[i + 1] & f59987M)) + (j >> 32);
        iArr2[i2 + 1] = (int) j2;
        long j3 = ((iArr2[r5] & f59987M) - (iArr[i + 2] & f59987M)) + (j2 >> 32);
        iArr2[i2 + 2] = (int) j3;
        long j4 = ((iArr2[r5] & f59987M) - (iArr[i + 3] & f59987M)) + (j3 >> 32);
        iArr2[i2 + 3] = (int) j4;
        long j5 = ((iArr2[r5] & f59987M) - (iArr[i + 4] & f59987M)) + (j4 >> 32);
        iArr2[i2 + 4] = (int) j5;
        long j6 = ((iArr2[r5] & f59987M) - (iArr[i + 5] & f59987M)) + (j5 >> 32);
        iArr2[i2 + 5] = (int) j6;
        long j7 = ((iArr2[r5] & f59987M) - (iArr[i + 6] & f59987M)) + (j6 >> 32);
        iArr2[i2 + 6] = (int) j7;
        long j8 = ((iArr2[r13] & f59987M) - (iArr[i + 7] & f59987M)) + (j7 >> 32);
        iArr2[i2 + 7] = (int) j8;
        return (int) (j8 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[32];
        for (int i = 0; i < 8; i++) {
            int i2 = iArr[i];
            if (i2 != 0) {
                wl0.intToBigEndian(i2, bArr, (7 - i) << 2);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static BigInteger toBigInteger64(long[] jArr) {
        byte[] bArr = new byte[32];
        for (int i = 0; i < 4; i++) {
            long j = jArr[i];
            if (j != 0) {
                wl0.longToBigEndian(j, bArr, (3 - i) << 3);
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
        iArr[5] = 0;
        iArr[6] = 0;
        iArr[7] = 0;
    }

    public static int add(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (iArr[0] & f59987M) + (iArr2[0] & f59987M);
        iArr3[0] = (int) j;
        long j2 = (iArr[1] & f59987M) + (iArr2[1] & f59987M) + (j >>> 32);
        iArr3[1] = (int) j2;
        long j3 = (iArr[2] & f59987M) + (iArr2[2] & f59987M) + (j2 >>> 32);
        iArr3[2] = (int) j3;
        long j4 = (iArr[3] & f59987M) + (iArr2[3] & f59987M) + (j3 >>> 32);
        iArr3[3] = (int) j4;
        long j5 = (iArr[4] & f59987M) + (iArr2[4] & f59987M) + (j4 >>> 32);
        iArr3[4] = (int) j5;
        long j6 = (iArr[5] & f59987M) + (iArr2[5] & f59987M) + (j5 >>> 32);
        iArr3[5] = (int) j6;
        long j7 = (iArr[6] & f59987M) + (iArr2[6] & f59987M) + (j6 >>> 32);
        iArr3[6] = (int) j7;
        long j8 = (iArr[7] & f59987M) + (iArr2[7] & f59987M) + (j7 >>> 32);
        iArr3[7] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int addBothTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (iArr[0] & f59987M) + (iArr2[0] & f59987M) + (iArr3[0] & f59987M);
        iArr3[0] = (int) j;
        long j2 = (iArr[1] & f59987M) + (iArr2[1] & f59987M) + (iArr3[1] & f59987M) + (j >>> 32);
        iArr3[1] = (int) j2;
        long j3 = (iArr[2] & f59987M) + (iArr2[2] & f59987M) + (iArr3[2] & f59987M) + (j2 >>> 32);
        iArr3[2] = (int) j3;
        long j4 = (iArr[3] & f59987M) + (iArr2[3] & f59987M) + (iArr3[3] & f59987M) + (j3 >>> 32);
        iArr3[3] = (int) j4;
        long j5 = (iArr[4] & f59987M) + (iArr2[4] & f59987M) + (iArr3[4] & f59987M) + (j4 >>> 32);
        iArr3[4] = (int) j5;
        long j6 = (iArr[5] & f59987M) + (iArr2[5] & f59987M) + (iArr3[5] & f59987M) + (j5 >>> 32);
        iArr3[5] = (int) j6;
        long j7 = (iArr[6] & f59987M) + (iArr2[6] & f59987M) + (iArr3[6] & f59987M) + (j6 >>> 32);
        iArr3[6] = (int) j7;
        long j8 = (iArr[7] & f59987M) + (iArr2[7] & f59987M) + (iArr3[7] & f59987M) + (j7 >>> 32);
        iArr3[7] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int addTo(int[] iArr, int[] iArr2) {
        long j = (iArr[0] & f59987M) + (iArr2[0] & f59987M);
        iArr2[0] = (int) j;
        long j2 = (iArr[1] & f59987M) + (iArr2[1] & f59987M) + (j >>> 32);
        iArr2[1] = (int) j2;
        long j3 = (iArr[2] & f59987M) + (iArr2[2] & f59987M) + (j2 >>> 32);
        iArr2[2] = (int) j3;
        long j4 = (iArr[3] & f59987M) + (iArr2[3] & f59987M) + (j3 >>> 32);
        iArr2[3] = (int) j4;
        long j5 = (iArr[4] & f59987M) + (iArr2[4] & f59987M) + (j4 >>> 32);
        iArr2[4] = (int) j5;
        long j6 = (iArr[5] & f59987M) + (iArr2[5] & f59987M) + (j5 >>> 32);
        iArr2[5] = (int) j6;
        long j7 = (iArr[6] & f59987M) + (iArr2[6] & f59987M) + (j6 >>> 32);
        iArr2[6] = (int) j7;
        long j8 = (iArr[7] & f59987M) + (f59987M & iArr2[7]) + (j7 >>> 32);
        iArr2[7] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
        iArr2[4] = iArr[4];
        iArr2[5] = iArr[5];
        iArr2[6] = iArr[6];
        iArr2[7] = iArr[7];
    }

    public static void copy64(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i = 7; i >= 0; i--) {
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
        long j = iArr2[0] & f59987M;
        long j2 = iArr2[1] & f59987M;
        long j3 = iArr2[2] & f59987M;
        long j4 = iArr2[3] & f59987M;
        long j5 = iArr2[4] & f59987M;
        long j6 = iArr2[5] & f59987M;
        long j7 = iArr2[6] & f59987M;
        long j8 = iArr2[7] & f59987M;
        long j9 = iArr[0] & f59987M;
        long j10 = j9 * j;
        iArr3[0] = (int) j10;
        long j11 = (j9 * j2) + (j10 >>> 32);
        iArr3[1] = (int) j11;
        long j12 = (j9 * j3) + (j11 >>> 32);
        iArr3[2] = (int) j12;
        long j13 = (j9 * j4) + (j12 >>> 32);
        iArr3[3] = (int) j13;
        long j14 = (j9 * j5) + (j13 >>> 32);
        iArr3[4] = (int) j14;
        long j15 = (j9 * j6) + (j14 >>> 32);
        iArr3[5] = (int) j15;
        long j16 = (j9 * j7) + (j15 >>> 32);
        iArr3[6] = (int) j16;
        long j17 = (j9 * j8) + (j16 >>> 32);
        iArr3[7] = (int) j17;
        int i = (int) (j17 >>> 32);
        iArr3[8] = i;
        int i2 = 1;
        for (int i3 = 8; i2 < i3; i3 = 8) {
            long j18 = iArr[i2] & f59987M;
            long j19 = j5;
            long j20 = (j18 * j) + (iArr3[i2] & f59987M);
            iArr3[i2] = (int) j20;
            int i4 = i2 + 1;
            long j21 = (j18 * j2) + (iArr3[i4] & f59987M) + (j20 >>> 32);
            iArr3[i4] = (int) j21;
            long j22 = (j18 * j3) + (iArr3[r25] & f59987M) + (j21 >>> 32);
            iArr3[i2 + 2] = (int) j22;
            long j23 = (j18 * j4) + (iArr3[r25] & f59987M) + (j22 >>> 32);
            iArr3[i2 + 3] = (int) j23;
            long j24 = (j18 * j19) + (iArr3[r25] & f59987M) + (j23 >>> 32);
            iArr3[i2 + 4] = (int) j24;
            long j25 = (j18 * j6) + (iArr3[r25] & f59987M) + (j24 >>> 32);
            iArr3[i2 + 5] = (int) j25;
            long j26 = (j18 * j7) + (iArr3[r25] & f59987M) + (j25 >>> 32);
            iArr3[i2 + 6] = (int) j26;
            long j27 = j26 >>> 32;
            long j28 = (j18 * j8) + (iArr3[r14] & f59987M) + j27;
            iArr3[i2 + 7] = (int) j28;
            iArr3[i2 + 8] = (int) (j28 >>> 32);
            i2 = i4;
            j5 = j19;
        }
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = iArr2[0] & f59987M;
        long j2 = iArr2[1] & f59987M;
        long j3 = iArr2[2] & f59987M;
        long j4 = iArr2[3] & f59987M;
        long j5 = iArr2[4] & f59987M;
        long j6 = iArr2[5] & f59987M;
        long j7 = iArr2[6] & f59987M;
        long j8 = iArr2[7] & f59987M;
        long j9 = 0;
        int i = 0;
        while (i < 8) {
            long j10 = iArr[i] & f59987M;
            long j11 = (j10 * j) + (iArr3[i] & f59987M);
            int i2 = i;
            iArr3[i2] = (int) j11;
            int i3 = i2 + 1;
            long j12 = (j10 * j2) + (iArr3[i3] & f59987M) + (j11 >>> 32);
            iArr3[i3] = (int) j12;
            long j13 = (j10 * j3) + (iArr3[r4] & f59987M) + (j12 >>> 32);
            iArr3[i2 + 2] = (int) j13;
            long j14 = (j10 * j4) + (iArr3[r4] & f59987M) + (j13 >>> 32);
            iArr3[i2 + 3] = (int) j14;
            long j15 = (j10 * j5) + (iArr3[r4] & f59987M) + (j14 >>> 32);
            iArr3[i2 + 4] = (int) j15;
            long j16 = (j10 * j6) + (iArr3[r4] & f59987M) + (j15 >>> 32);
            iArr3[i2 + 5] = (int) j16;
            long j17 = (j10 * j7) + (iArr3[r4] & f59987M) + (j16 >>> 32);
            iArr3[i2 + 6] = (int) j17;
            long j18 = (j10 * j8) + (iArr3[r4] & f59987M) + (j17 >>> 32);
            iArr3[i2 + 7] = (int) j18;
            long j19 = (j18 >>> 32) + (iArr3[r4] & f59987M) + j9;
            iArr3[i2 + 8] = (int) j19;
            j9 = j19 >>> 32;
            i = i3;
        }
        return (int) j9;
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j = iArr[0] & f59987M;
        int i = 16;
        int i2 = 0;
        int i3 = 7;
        while (true) {
            int i4 = i3 - 1;
            long j2 = iArr[i3] & f59987M;
            long j3 = j2 * j2;
            iArr2[i - 1] = (i2 << 31) | ((int) (j3 >>> 33));
            i -= 2;
            iArr2[i] = (int) (j3 >>> 1);
            i2 = (int) j3;
            if (i4 <= 0) {
                long j4 = j * j;
                long j5 = (j4 >>> 33) | ((i2 << 31) & f59987M);
                iArr2[0] = (int) j4;
                int i5 = ((int) (j4 >>> 32)) & 1;
                long j6 = iArr[1] & f59987M;
                long j7 = iArr2[2] & f59987M;
                long j8 = (j6 * j) + j5;
                int i6 = (int) j8;
                iArr2[1] = i5 | (i6 << 1);
                long j9 = iArr[2] & f59987M;
                long j10 = iArr2[3] & f59987M;
                long j11 = iArr2[4] & f59987M;
                long j12 = (j9 * j) + j7 + (j8 >>> 32);
                int i7 = (int) j12;
                iArr2[2] = (i7 << 1) | (i6 >>> 31);
                long jM21a2 = AbstractC0003a2.m21a2(j9, j6, j12 >>> 32, j10);
                long j13 = j11 + (jM21a2 >>> 32);
                long j14 = jM21a2 & f59987M;
                long j15 = iArr[3] & f59987M;
                long j16 = (iArr2[5] & f59987M) + (j13 >>> 32);
                long j17 = j13 & f59987M;
                long j18 = (iArr2[6] & f59987M) + (j16 >>> 32);
                long j19 = j16 & f59987M;
                long j20 = (j15 * j) + j14;
                int i8 = (int) j20;
                iArr2[3] = (i7 >>> 31) | (i8 << 1);
                int i9 = i8 >>> 31;
                long jM21a22 = AbstractC0003a2.m21a2(j15, j6, j20 >>> 32, j17);
                long jM21a23 = AbstractC0003a2.m21a2(j15, j9, jM21a22 >>> 32, j19);
                long j21 = jM21a22 & f59987M;
                long j22 = j18 + (jM21a23 >>> 32);
                long j23 = jM21a23 & f59987M;
                long j24 = iArr[4] & f59987M;
                long j25 = (iArr2[7] & f59987M) + (j22 >>> 32);
                long j26 = j22 & f59987M;
                long j27 = (iArr2[8] & f59987M) + (j25 >>> 32);
                long j28 = j25 & f59987M;
                long j29 = (j24 * j) + j21;
                int i10 = (int) j29;
                iArr2[4] = i9 | (i10 << 1);
                long jM21a24 = AbstractC0003a2.m21a2(j24, j6, j29 >>> 32, j23);
                long jM21a25 = AbstractC0003a2.m21a2(j24, j9, jM21a24 >>> 32, j26);
                long j30 = jM21a24 & f59987M;
                long jM21a26 = AbstractC0003a2.m21a2(j24, j15, jM21a25 >>> 32, j28);
                long j31 = jM21a25 & f59987M;
                long j32 = j27 + (jM21a26 >>> 32);
                long j33 = jM21a26 & f59987M;
                long j34 = iArr[5] & f59987M;
                long j35 = (iArr2[9] & f59987M) + (j32 >>> 32);
                long j36 = j32 & f59987M;
                long j37 = (iArr2[10] & f59987M) + (j35 >>> 32);
                long j38 = j35 & f59987M;
                long j39 = (j34 * j) + j30;
                int i11 = (int) j39;
                iArr2[5] = (i10 >>> 31) | (i11 << 1);
                int i12 = i11 >>> 31;
                long jM21a27 = AbstractC0003a2.m21a2(j34, j6, j39 >>> 32, j31);
                long jM21a28 = AbstractC0003a2.m21a2(j34, j9, jM21a27 >>> 32, j33);
                long j40 = jM21a27 & f59987M;
                long jM21a29 = AbstractC0003a2.m21a2(j34, j15, jM21a28 >>> 32, j36);
                long j41 = jM21a28 & f59987M;
                long jM21a210 = AbstractC0003a2.m21a2(j34, j24, jM21a29 >>> 32, j38);
                long j42 = jM21a29 & f59987M;
                long j43 = j37 + (jM21a210 >>> 32);
                long j44 = jM21a210 & f59987M;
                long j45 = iArr[6] & f59987M;
                long j46 = (iArr2[11] & f59987M) + (j43 >>> 32);
                long j47 = j43 & f59987M;
                long j48 = (iArr2[12] & f59987M) + (j46 >>> 32);
                long j49 = j46 & f59987M;
                long j50 = (j45 * j) + j40;
                int i13 = (int) j50;
                iArr2[6] = i12 | (i13 << 1);
                int i14 = i13 >>> 31;
                long jM21a211 = AbstractC0003a2.m21a2(j45, j6, j50 >>> 32, j41);
                long jM21a212 = AbstractC0003a2.m21a2(j45, j9, jM21a211 >>> 32, j42);
                long j51 = jM21a211 & f59987M;
                long jM21a213 = AbstractC0003a2.m21a2(j45, j15, jM21a212 >>> 32, j44);
                long j52 = jM21a212 & f59987M;
                long jM21a214 = AbstractC0003a2.m21a2(j45, j24, jM21a213 >>> 32, j47);
                long j53 = jM21a213 & f59987M;
                long jM21a215 = AbstractC0003a2.m21a2(j45, j34, jM21a214 >>> 32, j49);
                long j54 = jM21a214 & f59987M;
                long j55 = j48 + (jM21a215 >>> 32);
                long j56 = jM21a215 & f59987M;
                long j57 = iArr[7] & f59987M;
                long j58 = (iArr2[13] & f59987M) + (j55 >>> 32);
                long j59 = j55 & f59987M;
                long j60 = (iArr2[14] & f59987M) + (j58 >>> 32);
                long j61 = j58 & f59987M;
                long j62 = (j57 * j) + j51;
                int i15 = (int) j62;
                iArr2[7] = (i15 << 1) | i14;
                int i16 = i15 >>> 31;
                long jM21a216 = AbstractC0003a2.m21a2(j57, j6, j62 >>> 32, j52);
                long jM21a217 = AbstractC0003a2.m21a2(j57, j9, jM21a216 >>> 32, j53);
                long jM21a218 = AbstractC0003a2.m21a2(j57, j15, jM21a217 >>> 32, j54);
                long jM21a219 = AbstractC0003a2.m21a2(j57, j24, jM21a218 >>> 32, j56);
                long jM21a220 = AbstractC0003a2.m21a2(j57, j34, jM21a219 >>> 32, j59);
                long jM21a221 = AbstractC0003a2.m21a2(j57, j45, jM21a220 >>> 32, j61);
                long j63 = j60 + (jM21a221 >>> 32);
                int i17 = (int) jM21a216;
                iArr2[8] = (i17 << 1) | i16;
                int i18 = (int) jM21a217;
                iArr2[9] = (i17 >>> 31) | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = (int) jM21a218;
                iArr2[10] = i19 | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) jM21a219;
                iArr2[11] = i21 | (i22 << 1);
                int i23 = i22 >>> 31;
                int i24 = (int) jM21a220;
                iArr2[12] = i23 | (i24 << 1);
                int i25 = i24 >>> 31;
                int i26 = (int) jM21a221;
                iArr2[13] = i25 | (i26 << 1);
                int i27 = i26 >>> 31;
                int i28 = (int) j63;
                iArr2[14] = i27 | (i28 << 1);
                iArr2[15] = ((iArr2[15] + ((int) (j63 >>> 32))) << 1) | (i28 >>> 31);
                return;
            }
            i3 = i4;
        }
    }

    public static int sub(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = (iArr[0] & f59987M) - (iArr2[0] & f59987M);
        iArr3[0] = (int) j;
        long j2 = ((iArr[1] & f59987M) - (iArr2[1] & f59987M)) + (j >> 32);
        iArr3[1] = (int) j2;
        long j3 = ((iArr[2] & f59987M) - (iArr2[2] & f59987M)) + (j2 >> 32);
        iArr3[2] = (int) j3;
        long j4 = ((iArr[3] & f59987M) - (iArr2[3] & f59987M)) + (j3 >> 32);
        iArr3[3] = (int) j4;
        long j5 = ((iArr[4] & f59987M) - (iArr2[4] & f59987M)) + (j4 >> 32);
        iArr3[4] = (int) j5;
        long j6 = ((iArr[5] & f59987M) - (iArr2[5] & f59987M)) + (j5 >> 32);
        iArr3[5] = (int) j6;
        long j7 = ((iArr[6] & f59987M) - (iArr2[6] & f59987M)) + (j6 >> 32);
        iArr3[6] = (int) j7;
        long j8 = ((iArr[7] & f59987M) - (iArr2[7] & f59987M)) + (j7 >> 32);
        iArr3[7] = (int) j8;
        return (int) (j8 >> 32);
    }

    public static int subFrom(int[] iArr, int[] iArr2) {
        long j = (iArr2[0] & f59987M) - (iArr[0] & f59987M);
        iArr2[0] = (int) j;
        long j2 = ((iArr2[1] & f59987M) - (iArr[1] & f59987M)) + (j >> 32);
        iArr2[1] = (int) j2;
        long j3 = ((iArr2[2] & f59987M) - (iArr[2] & f59987M)) + (j2 >> 32);
        iArr2[2] = (int) j3;
        long j4 = ((iArr2[3] & f59987M) - (iArr[3] & f59987M)) + (j3 >> 32);
        iArr2[3] = (int) j4;
        long j5 = ((iArr2[4] & f59987M) - (iArr[4] & f59987M)) + (j4 >> 32);
        iArr2[4] = (int) j5;
        long j6 = ((iArr2[5] & f59987M) - (iArr[5] & f59987M)) + (j5 >> 32);
        iArr2[5] = (int) j6;
        long j7 = ((iArr2[6] & f59987M) - (iArr[6] & f59987M)) + (j6 >> 32);
        iArr2[6] = (int) j7;
        long j8 = ((iArr2[7] & f59987M) - (f59987M & iArr[7])) + (j7 >> 32);
        iArr2[7] = (int) j8;
        return (int) (j8 >> 32);
    }
}
