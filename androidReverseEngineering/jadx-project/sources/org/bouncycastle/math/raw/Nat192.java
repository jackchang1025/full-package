package org.bouncycastle.math.raw;

import java.math.BigInteger;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class Nat192 {

    /* renamed from: M */
    private static final long f1519M = 4294967295L;

    public static int add(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L) + 0;
        iArr3[0] = (int) j2;
        long j3 = (iArr[1] & 4294967295L) + (iArr2[1] & 4294967295L) + (j2 >>> 32);
        iArr3[1] = (int) j3;
        long j4 = (iArr[2] & 4294967295L) + (iArr2[2] & 4294967295L) + (j3 >>> 32);
        iArr3[2] = (int) j4;
        long j5 = (iArr[3] & 4294967295L) + (iArr2[3] & 4294967295L) + (j4 >>> 32);
        iArr3[3] = (int) j5;
        long j6 = (iArr[4] & 4294967295L) + (iArr2[4] & 4294967295L) + (j5 >>> 32);
        iArr3[4] = (int) j6;
        long j7 = (iArr[5] & 4294967295L) + (iArr2[5] & 4294967295L) + (j6 >>> 32);
        iArr3[5] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addBothTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L) + (iArr3[0] & 4294967295L) + 0;
        iArr3[0] = (int) j2;
        long j3 = (iArr[1] & 4294967295L) + (iArr2[1] & 4294967295L) + (iArr3[1] & 4294967295L) + (j2 >>> 32);
        iArr3[1] = (int) j3;
        long j4 = (iArr[2] & 4294967295L) + (iArr2[2] & 4294967295L) + (iArr3[2] & 4294967295L) + (j3 >>> 32);
        iArr3[2] = (int) j4;
        long j5 = (iArr[3] & 4294967295L) + (iArr2[3] & 4294967295L) + (iArr3[3] & 4294967295L) + (j4 >>> 32);
        iArr3[3] = (int) j5;
        long j6 = (iArr[4] & 4294967295L) + (iArr2[4] & 4294967295L) + (iArr3[4] & 4294967295L) + (j5 >>> 32);
        iArr3[4] = (int) j6;
        long j7 = (iArr[5] & 4294967295L) + (iArr2[5] & 4294967295L) + (iArr3[5] & 4294967295L) + (j6 >>> 32);
        iArr3[5] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addTo(int[] iArr, int i2, int[] iArr2, int i3, int i4) {
        long j2 = (iArr[i2 + 0] & 4294967295L) + (iArr2[r13] & 4294967295L) + (i4 & 4294967295L);
        iArr2[i3 + 0] = (int) j2;
        long j3 = (iArr[i2 + 1] & 4294967295L) + (iArr2[r6] & 4294967295L) + (j2 >>> 32);
        iArr2[i3 + 1] = (int) j3;
        long j4 = (iArr[i2 + 2] & 4294967295L) + (iArr2[r6] & 4294967295L) + (j3 >>> 32);
        iArr2[i3 + 2] = (int) j4;
        long j5 = (iArr[i2 + 3] & 4294967295L) + (iArr2[r6] & 4294967295L) + (j4 >>> 32);
        iArr2[i3 + 3] = (int) j5;
        long j6 = (iArr[i2 + 4] & 4294967295L) + (iArr2[r6] & 4294967295L) + (j5 >>> 32);
        iArr2[i3 + 4] = (int) j6;
        long j7 = (iArr[i2 + 5] & 4294967295L) + (4294967295L & iArr2[r12]) + (j6 >>> 32);
        iArr2[i3 + 5] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static int addToEachOther(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = (iArr[r0] & 4294967295L) + (iArr2[r5] & 4294967295L) + 0;
        int i4 = (int) j2;
        iArr[i2 + 0] = i4;
        iArr2[i3 + 0] = i4;
        long j3 = (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L) + (j2 >>> 32);
        int i5 = (int) j3;
        iArr[i2 + 1] = i5;
        iArr2[i3 + 1] = i5;
        long j4 = (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L) + (j3 >>> 32);
        int i6 = (int) j4;
        iArr[i2 + 2] = i6;
        iArr2[i3 + 2] = i6;
        long j5 = (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L) + (j4 >>> 32);
        int i7 = (int) j5;
        iArr[i2 + 3] = i7;
        iArr2[i3 + 3] = i7;
        long j6 = (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L) + (j5 >>> 32);
        int i8 = (int) j6;
        iArr[i2 + 4] = i8;
        iArr2[i3 + 4] = i8;
        long j7 = (iArr[r12] & 4294967295L) + (4294967295L & iArr2[r14]) + (j6 >>> 32);
        int i9 = (int) j7;
        iArr[i2 + 5] = i9;
        iArr2[i3 + 5] = i9;
        return (int) (j7 >>> 32);
    }

    public static void copy(int[] iArr, int i2, int[] iArr2, int i3) {
        iArr2[i3 + 0] = iArr[i2 + 0];
        iArr2[i3 + 1] = iArr[i2 + 1];
        iArr2[i3 + 2] = iArr[i2 + 2];
        iArr2[i3 + 3] = iArr[i2 + 3];
        iArr2[i3 + 4] = iArr[i2 + 4];
        iArr2[i3 + 5] = iArr[i2 + 5];
    }

    public static void copy64(long[] jArr, int i2, long[] jArr2, int i3) {
        jArr2[i3 + 0] = jArr[i2 + 0];
        jArr2[i3 + 1] = jArr[i2 + 1];
        jArr2[i3 + 2] = jArr[i2 + 2];
    }

    public static int[] create() {
        return new int[6];
    }

    public static long[] create64() {
        return new long[3];
    }

    public static int[] createExt() {
        return new int[12];
    }

    public static long[] createExt64() {
        return new long[6];
    }

    public static boolean diff(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        boolean gte = gte(iArr, i2, iArr2, i3);
        if (gte) {
            sub(iArr, i2, iArr2, i3, iArr3, i4);
        } else {
            sub(iArr2, i3, iArr, i2, iArr3, i4);
        }
        return gte;
    }

    public static boolean eq(int[] iArr, int[] iArr2) {
        for (int i2 = 5; i2 >= 0; i2--) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static boolean eq64(long[] jArr, long[] jArr2) {
        for (int i2 = 2; i2 >= 0; i2--) {
            if (jArr[i2] != jArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 192) {
            throw new IllegalArgumentException();
        }
        int[] create = create();
        for (int i2 = 0; i2 < 6; i2++) {
            create[i2] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
        }
        return create;
    }

    public static long[] fromBigInteger64(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 192) {
            throw new IllegalArgumentException();
        }
        long[] create64 = create64();
        for (int i2 = 0; i2 < 3; i2++) {
            create64[i2] = bigInteger.longValue();
            bigInteger = bigInteger.shiftRight(64);
        }
        return create64;
    }

    public static int getBit(int[] iArr, int i2) {
        int i3;
        if (i2 == 0) {
            i3 = iArr[0];
        } else {
            int i4 = i2 >> 5;
            if (i4 < 0 || i4 >= 6) {
                return 0;
            }
            i3 = iArr[i4] >>> (i2 & 31);
        }
        return i3 & 1;
    }

    public static boolean gte(int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 5; i4 >= 0; i4--) {
            int i5 = iArr[i2 + i4] ^ Integer.MIN_VALUE;
            int i6 = Integer.MIN_VALUE ^ iArr2[i3 + i4];
            if (i5 < i6) {
                return false;
            }
            if (i5 > i6) {
                return true;
            }
        }
        return true;
    }

    public static boolean isOne(int[] iArr) {
        if (iArr[0] != 1) {
            return false;
        }
        for (int i2 = 1; i2 < 6; i2++) {
            if (iArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOne64(long[] jArr) {
        if (jArr[0] != 1) {
            return false;
        }
        for (int i2 = 1; i2 < 3; i2++) {
            if (jArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i2 = 0; i2 < 6; i2++) {
            if (iArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero64(long[] jArr) {
        for (int i2 = 0; i2 < 3; i2++) {
            if (jArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = iArr2[i3 + 0] & 4294967295L;
        long j3 = iArr2[i3 + 1] & 4294967295L;
        long j4 = iArr2[i3 + 2] & 4294967295L;
        long j5 = iArr2[i3 + 3] & 4294967295L;
        long j6 = iArr2[i3 + 4] & 4294967295L;
        long j7 = iArr2[i3 + 5] & 4294967295L;
        long j8 = iArr[i2 + 0] & 4294967295L;
        long j9 = (j8 * j2) + 0;
        iArr3[i4 + 0] = (int) j9;
        long j10 = (j8 * j3) + (j9 >>> 32);
        iArr3[i4 + 1] = (int) j10;
        long j11 = (j8 * j4) + (j10 >>> 32);
        iArr3[i4 + 2] = (int) j11;
        long j12 = (j8 * j5) + (j11 >>> 32);
        iArr3[i4 + 3] = (int) j12;
        long j13 = (j8 * j6) + (j12 >>> 32);
        iArr3[i4 + 4] = (int) j13;
        long j14 = (j8 * j7) + (j13 >>> 32);
        iArr3[i4 + 5] = (int) j14;
        iArr3[i4 + 6] = (int) (j14 >>> 32);
        int i5 = 1;
        int i6 = i4;
        int i7 = 1;
        while (i7 < 6) {
            i6 += i5;
            long j15 = iArr[i2 + i7] & 4294967295L;
            long j16 = (j15 * j2) + (iArr3[r24] & 4294967295L) + 0;
            iArr3[i6 + 0] = (int) j16;
            long j17 = j7;
            long j18 = (j15 * j3) + (iArr3[r15] & 4294967295L) + (j16 >>> 32);
            iArr3[i6 + 1] = (int) j18;
            long j19 = (j15 * j4) + (iArr3[r15] & 4294967295L) + (j18 >>> 32);
            iArr3[i6 + 2] = (int) j19;
            long j20 = (j15 * j5) + (iArr3[r1] & 4294967295L) + (j19 >>> 32);
            iArr3[i6 + 3] = (int) j20;
            long j21 = (j15 * j6) + (iArr3[r1] & 4294967295L) + (j20 >>> 32);
            iArr3[i6 + 4] = (int) j21;
            long j22 = (j15 * j17) + (iArr3[r1] & 4294967295L) + (j21 >>> 32);
            iArr3[i6 + 5] = (int) j22;
            iArr3[i6 + 6] = (int) (j22 >>> 32);
            i7++;
            j4 = j4;
            j7 = j17;
            i5 = 1;
        }
    }

    public static long mul33Add(int i2, int[] iArr, int i3, int[] iArr2, int i4, int[] iArr3, int i5) {
        long j2 = i2 & 4294967295L;
        long j3 = iArr[i3 + 0] & 4294967295L;
        long j4 = (j2 * j3) + (iArr2[i4 + 0] & 4294967295L) + 0;
        iArr3[i5 + 0] = (int) j4;
        long j5 = iArr[i3 + 1] & 4294967295L;
        long j6 = (j2 * j5) + j3 + (iArr2[i4 + 1] & 4294967295L) + (j4 >>> 32);
        iArr3[i5 + 1] = (int) j6;
        long j7 = iArr[i3 + 2] & 4294967295L;
        long j8 = (j2 * j7) + j5 + (iArr2[i4 + 2] & 4294967295L) + (j6 >>> 32);
        iArr3[i5 + 2] = (int) j8;
        long j9 = iArr[i3 + 3] & 4294967295L;
        long j10 = (j2 * j9) + j7 + (iArr2[i4 + 3] & 4294967295L) + (j8 >>> 32);
        iArr3[i5 + 3] = (int) j10;
        long j11 = iArr[i3 + 4] & 4294967295L;
        long j12 = (j2 * j11) + j9 + (iArr2[i4 + 4] & 4294967295L) + (j10 >>> 32);
        iArr3[i5 + 4] = (int) j12;
        long j13 = iArr[i3 + 5] & 4294967295L;
        long j14 = (j2 * j13) + j11 + (4294967295L & iArr2[i4 + 5]) + (j12 >>> 32);
        iArr3[i5 + 5] = (int) j14;
        return (j14 >>> 32) + j13;
    }

    public static int mul33DWordAdd(int i2, long j2, int[] iArr, int i3) {
        long j3 = i2 & 4294967295L;
        long j4 = j2 & 4294967295L;
        long j5 = (j3 * j4) + (iArr[r1] & 4294967295L) + 0;
        iArr[i3 + 0] = (int) j5;
        long j6 = j2 >>> 32;
        long j7 = (j3 * j6) + j4;
        long j8 = j7 + (iArr[r7] & 4294967295L) + (j5 >>> 32);
        iArr[i3 + 1] = (int) j8;
        long j9 = j6 + (iArr[r7] & 4294967295L) + (j8 >>> 32);
        iArr[i3 + 2] = (int) j9;
        long j10 = (j9 >>> 32) + (4294967295L & iArr[r7]);
        iArr[i3 + 3] = (int) j10;
        if ((j10 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(6, iArr, i3, 4);
    }

    public static int mul33WordAdd(int i2, int i3, int[] iArr, int i4) {
        long j2 = i2 & 4294967295L;
        long j3 = i3 & 4294967295L;
        long j4 = (j2 * j3) + (iArr[r4] & 4294967295L) + 0;
        iArr[i4 + 0] = (int) j4;
        long j5 = j3 + (iArr[r7] & 4294967295L) + (j4 >>> 32);
        iArr[i4 + 1] = (int) j5;
        long j6 = (j5 >>> 32) + (iArr[r0] & 4294967295L);
        iArr[i4 + 2] = (int) j6;
        if ((j6 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(6, iArr, i4, 3);
    }

    public static int mulAddTo(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = iArr2[i3 + 0] & 4294967295L;
        long j3 = iArr2[i3 + 1] & 4294967295L;
        long j4 = iArr2[i3 + 2] & 4294967295L;
        long j5 = iArr2[i3 + 3] & 4294967295L;
        long j6 = iArr2[i3 + 4] & 4294967295L;
        long j7 = iArr2[i3 + 5] & 4294967295L;
        int i5 = 0;
        long j8 = 0;
        int i6 = i4;
        while (i5 < 6) {
            long j9 = iArr[i2 + i5] & 4294967295L;
            long j10 = j7;
            long j11 = (j9 * j2) + (iArr3[r16] & 4294967295L) + 0;
            iArr3[i6 + 0] = (int) j11;
            int i7 = i6 + 1;
            long j12 = j3;
            long j13 = (j9 * j3) + (iArr3[i7] & 4294967295L) + (j11 >>> 32);
            iArr3[i7] = (int) j13;
            long j14 = (j9 * j4) + (iArr3[r16] & 4294967295L) + (j13 >>> 32);
            iArr3[i6 + 2] = (int) j14;
            long j15 = j4;
            long j16 = (j9 * j5) + (iArr3[r1] & 4294967295L) + (j14 >>> 32);
            iArr3[i6 + 3] = (int) j16;
            long j17 = (j9 * j6) + (iArr3[r1] & 4294967295L) + (j16 >>> 32);
            iArr3[i6 + 4] = (int) j17;
            long j18 = (j9 * j10) + (iArr3[r1] & 4294967295L) + (j17 >>> 32);
            iArr3[i6 + 5] = (int) j18;
            long j19 = (j18 >>> 32) + (iArr3[r14] & 4294967295L) + j8;
            iArr3[i6 + 6] = (int) j19;
            j8 = j19 >>> 32;
            i5++;
            j7 = j10;
            j2 = j2;
            i6 = i7;
            j3 = j12;
            j4 = j15;
        }
        return (int) j8;
    }

    public static int mulWord(int i2, int[] iArr, int[] iArr2, int i3) {
        long j2 = i2 & 4294967295L;
        long j3 = 0;
        int i4 = 0;
        do {
            long j4 = ((iArr[i4] & 4294967295L) * j2) + j3;
            iArr2[i3 + i4] = (int) j4;
            j3 = j4 >>> 32;
            i4++;
        } while (i4 < 6);
        return (int) j3;
    }

    public static int mulWordAddExt(int i2, int[] iArr, int i3, int[] iArr2, int i4) {
        long j2 = i2 & 4294967295L;
        long j3 = ((iArr[i3 + 0] & 4294967295L) * j2) + (iArr2[r11] & 4294967295L) + 0;
        iArr2[i4 + 0] = (int) j3;
        long j4 = ((iArr[i3 + 1] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L) + (j3 >>> 32);
        iArr2[i4 + 1] = (int) j4;
        long j5 = ((iArr[i3 + 2] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L) + (j4 >>> 32);
        iArr2[i4 + 2] = (int) j5;
        long j6 = ((iArr[i3 + 3] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L) + (j5 >>> 32);
        iArr2[i4 + 3] = (int) j6;
        long j7 = ((iArr[i3 + 4] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L) + (j6 >>> 32);
        iArr2[i4 + 4] = (int) j7;
        long j8 = (j2 * (iArr[i3 + 5] & 4294967295L)) + (iArr2[r15] & 4294967295L) + (j7 >>> 32);
        iArr2[i4 + 5] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int mulWordDwordAdd(int i2, long j2, int[] iArr, int i3) {
        long j3 = i2 & 4294967295L;
        long j4 = ((j2 & 4294967295L) * j3) + (iArr[r10] & 4294967295L) + 0;
        iArr[i3 + 0] = (int) j4;
        long j5 = j3 * (j2 >>> 32);
        long j6 = j5 + (iArr[r11] & 4294967295L) + (j4 >>> 32);
        iArr[i3 + 1] = (int) j6;
        long j7 = j6 >>> 32;
        long j8 = j7 + (iArr[r0] & 4294967295L);
        iArr[i3 + 2] = (int) j8;
        if ((j8 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(6, iArr, i3, 3);
    }

    public static void square(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = iArr[i2 + 0] & 4294967295L;
        int i4 = 0;
        int i5 = 12;
        int i6 = 5;
        while (true) {
            int i7 = i6 - 1;
            long j3 = iArr[i2 + i6] & 4294967295L;
            long j4 = j3 * j3;
            int i8 = i5 - 1;
            iArr2[i3 + i8] = (i4 << 31) | ((int) (j4 >>> 33));
            i5 = i8 - 1;
            iArr2[i3 + i5] = (int) (j4 >>> 1);
            i4 = (int) j4;
            if (i7 <= 0) {
                long j5 = j2 * j2;
                long j6 = ((i4 << 31) & 4294967295L) | (j5 >>> 33);
                iArr2[i3 + 0] = (int) j5;
                int i9 = 1 & ((int) (j5 >>> 32));
                long j7 = iArr[i2 + 1] & 4294967295L;
                long j8 = (j7 * j2) + j6;
                int i10 = (int) j8;
                iArr2[i3 + 1] = i9 | (i10 << 1);
                long j9 = iArr[i2 + 2] & 4294967295L;
                long j10 = (j9 * j2) + (iArr2[r10] & 4294967295L) + (j8 >>> 32);
                int i11 = (int) j10;
                iArr2[i3 + 2] = (i10 >>> 31) | (i11 << 1);
                long m1007a = AbstractC0413b.m1007a(j9, j7, j10 >>> 32, iArr2[r9] & 4294967295L);
                long j11 = (iArr2[r21] & 4294967295L) + (m1007a >>> 32);
                long j12 = iArr[i2 + 3] & 4294967295L;
                long j13 = (iArr2[r32] & 4294967295L) + (j11 >>> 32);
                long j14 = (j12 * j2) + (m1007a & 4294967295L);
                int i12 = (int) j14;
                iArr2[i3 + 3] = (i11 >>> 31) | (i12 << 1);
                long m1007a2 = AbstractC0413b.m1007a(j12, j7, j14 >>> 32, j11 & 4294967295L);
                long m1007a3 = AbstractC0413b.m1007a(j12, j9, m1007a2 >>> 32, j13 & 4294967295L);
                long j15 = (iArr2[r33] & 4294967295L) + (j13 >>> 32) + (m1007a3 >>> 32);
                long j16 = m1007a3 & 4294967295L;
                long j17 = iArr[i2 + 4] & 4294967295L;
                long j18 = (iArr2[r9] & 4294967295L) + (j15 >>> 32);
                long j19 = (iArr2[r42] & 4294967295L) + (j18 >>> 32);
                long j20 = (j17 * j2) + (m1007a2 & 4294967295L);
                int i13 = (int) j20;
                iArr2[i3 + 4] = (i13 << 1) | (i12 >>> 31);
                long m1007a4 = AbstractC0413b.m1007a(j17, j7, j20 >>> 32, j16);
                long m1007a5 = AbstractC0413b.m1007a(j17, j9, m1007a4 >>> 32, j15 & 4294967295L);
                long m1007a6 = AbstractC0413b.m1007a(j17, j12, m1007a5 >>> 32, j18 & 4294967295L);
                long j21 = m1007a5 & 4294967295L;
                long j22 = j19 + (m1007a6 >>> 32);
                long j23 = m1007a6 & 4294967295L;
                long j24 = iArr[i2 + 5] & 4294967295L;
                long j25 = (iArr2[r9] & 4294967295L) + (j22 >>> 32);
                long j26 = (j2 * j24) + (m1007a4 & 4294967295L);
                int i14 = (int) j26;
                iArr2[i3 + 5] = (i13 >>> 31) | (i14 << 1);
                int i15 = i14 >>> 31;
                long m1007a7 = AbstractC0413b.m1007a(j7, j24, j26 >>> 32, j21);
                long m1007a8 = AbstractC0413b.m1007a(j24, j9, m1007a7 >>> 32, j23);
                long m1007a9 = AbstractC0413b.m1007a(j24, j12, m1007a8 >>> 32, j22 & 4294967295L);
                long m1007a10 = AbstractC0413b.m1007a(j24, j17, m1007a9 >>> 32, j25 & 4294967295L);
                long j27 = (iArr2[r44] & 4294967295L) + (j25 >>> 32) + (m1007a10 >>> 32);
                int i16 = (int) m1007a7;
                iArr2[i3 + 6] = (i16 << 1) | i15;
                int i17 = (int) m1007a8;
                iArr2[i3 + 7] = (i16 >>> 31) | (i17 << 1);
                int i18 = i17 >>> 31;
                int i19 = (int) m1007a9;
                iArr2[i3 + 8] = i18 | (i19 << 1);
                int i20 = i19 >>> 31;
                int i21 = (int) m1007a10;
                iArr2[i3 + 9] = i20 | (i21 << 1);
                int i22 = i21 >>> 31;
                int i23 = (int) j27;
                iArr2[i3 + 10] = i22 | (i23 << 1);
                int i24 = i23 >>> 31;
                int i25 = i3 + 11;
                iArr2[i25] = i24 | ((iArr2[i25] + ((int) (j27 >>> 32))) << 1);
                return;
            }
            i6 = i7;
        }
    }

    public static int sub(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = ((iArr[i2 + 0] & 4294967295L) - (iArr2[i3 + 0] & 4294967295L)) + 0;
        iArr3[i4 + 0] = (int) j2;
        long j3 = ((iArr[i2 + 1] & 4294967295L) - (iArr2[i3 + 1] & 4294967295L)) + (j2 >> 32);
        iArr3[i4 + 1] = (int) j3;
        long j4 = ((iArr[i2 + 2] & 4294967295L) - (iArr2[i3 + 2] & 4294967295L)) + (j3 >> 32);
        iArr3[i4 + 2] = (int) j4;
        long j5 = ((iArr[i2 + 3] & 4294967295L) - (iArr2[i3 + 3] & 4294967295L)) + (j4 >> 32);
        iArr3[i4 + 3] = (int) j5;
        long j6 = ((iArr[i2 + 4] & 4294967295L) - (iArr2[i3 + 4] & 4294967295L)) + (j5 >> 32);
        iArr3[i4 + 4] = (int) j6;
        long j7 = ((iArr[i2 + 5] & 4294967295L) - (iArr2[i3 + 5] & 4294967295L)) + (j6 >> 32);
        iArr3[i4 + 5] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int subBothFrom(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = (((iArr3[0] & 4294967295L) - (iArr[0] & 4294967295L)) - (iArr2[0] & 4294967295L)) + 0;
        iArr3[0] = (int) j2;
        long j3 = (((iArr3[1] & 4294967295L) - (iArr[1] & 4294967295L)) - (iArr2[1] & 4294967295L)) + (j2 >> 32);
        iArr3[1] = (int) j3;
        long j4 = (((iArr3[2] & 4294967295L) - (iArr[2] & 4294967295L)) - (iArr2[2] & 4294967295L)) + (j3 >> 32);
        iArr3[2] = (int) j4;
        long j5 = (((iArr3[3] & 4294967295L) - (iArr[3] & 4294967295L)) - (iArr2[3] & 4294967295L)) + (j4 >> 32);
        iArr3[3] = (int) j5;
        long j6 = (((iArr3[4] & 4294967295L) - (iArr[4] & 4294967295L)) - (iArr2[4] & 4294967295L)) + (j5 >> 32);
        iArr3[4] = (int) j6;
        long j7 = (((iArr3[5] & 4294967295L) - (iArr[5] & 4294967295L)) - (iArr2[5] & 4294967295L)) + (j6 >> 32);
        iArr3[5] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int subFrom(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = ((iArr2[r0] & 4294967295L) - (iArr[i2 + 0] & 4294967295L)) + 0;
        iArr2[i3 + 0] = (int) j2;
        long j3 = ((iArr2[r5] & 4294967295L) - (iArr[i2 + 1] & 4294967295L)) + (j2 >> 32);
        iArr2[i3 + 1] = (int) j3;
        long j4 = ((iArr2[r5] & 4294967295L) - (iArr[i2 + 2] & 4294967295L)) + (j3 >> 32);
        iArr2[i3 + 2] = (int) j4;
        long j5 = ((iArr2[r5] & 4294967295L) - (iArr[i2 + 3] & 4294967295L)) + (j4 >> 32);
        iArr2[i3 + 3] = (int) j5;
        long j6 = ((iArr2[r5] & 4294967295L) - (iArr[i2 + 4] & 4294967295L)) + (j5 >> 32);
        iArr2[i3 + 4] = (int) j6;
        long j7 = ((iArr2[r13] & 4294967295L) - (iArr[i2 + 5] & 4294967295L)) + (j6 >> 32);
        iArr2[i3 + 5] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[24];
        for (int i2 = 0; i2 < 6; i2++) {
            int i3 = iArr[i2];
            if (i3 != 0) {
                Pack.intToBigEndian(i3, bArr, (5 - i2) << 2);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static BigInteger toBigInteger64(long[] jArr) {
        byte[] bArr = new byte[24];
        for (int i2 = 0; i2 < 3; i2++) {
            long j2 = jArr[i2];
            if (j2 != 0) {
                Pack.longToBigEndian(j2, bArr, (2 - i2) << 3);
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
    }

    public static int addTo(int[] iArr, int[] iArr2) {
        long j2 = (iArr[0] & 4294967295L) + (iArr2[0] & 4294967295L) + 0;
        iArr2[0] = (int) j2;
        long j3 = (iArr[1] & 4294967295L) + (iArr2[1] & 4294967295L) + (j2 >>> 32);
        iArr2[1] = (int) j3;
        long j4 = (iArr[2] & 4294967295L) + (iArr2[2] & 4294967295L) + (j3 >>> 32);
        iArr2[2] = (int) j4;
        long j5 = (iArr[3] & 4294967295L) + (iArr2[3] & 4294967295L) + (j4 >>> 32);
        iArr2[3] = (int) j5;
        long j6 = (iArr[4] & 4294967295L) + (iArr2[4] & 4294967295L) + (j5 >>> 32);
        iArr2[4] = (int) j6;
        long j7 = (iArr[5] & 4294967295L) + (4294967295L & iArr2[5]) + (j6 >>> 32);
        iArr2[5] = (int) j7;
        return (int) (j7 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
        iArr2[4] = iArr[4];
        iArr2[5] = iArr[5];
    }

    public static void copy64(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i2 = 5; i2 >= 0; i2--) {
            int i3 = iArr[i2] ^ Integer.MIN_VALUE;
            int i4 = Integer.MIN_VALUE ^ iArr2[i2];
            if (i3 < i4) {
                return false;
            }
            if (i3 > i4) {
                return true;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = iArr2[0] & 4294967295L;
        long j3 = iArr2[1] & 4294967295L;
        long j4 = iArr2[2] & 4294967295L;
        long j5 = iArr2[3] & 4294967295L;
        long j6 = iArr2[4] & 4294967295L;
        long j7 = iArr2[5] & 4294967295L;
        long j8 = iArr[0] & 4294967295L;
        long j9 = (j8 * j2) + 0;
        iArr3[0] = (int) j9;
        long j10 = (j8 * j3) + (j9 >>> 32);
        iArr3[1] = (int) j10;
        long j11 = (j8 * j4) + (j10 >>> 32);
        iArr3[2] = (int) j11;
        long j12 = (j8 * j5) + (j11 >>> 32);
        iArr3[3] = (int) j12;
        long j13 = (j8 * j6) + (j12 >>> 32);
        iArr3[4] = (int) j13;
        long j14 = (j8 * j7) + (j13 >>> 32);
        iArr3[5] = (int) j14;
        iArr3[6] = (int) (j14 >>> 32);
        int i2 = 1;
        for (int i3 = 6; i2 < i3; i3 = 6) {
            long j15 = iArr[i2] & 4294967295L;
            long j16 = j2;
            long j17 = (j15 * j2) + (iArr3[r3] & 4294967295L) + 0;
            iArr3[i2 + 0] = (int) j17;
            int i4 = i2 + 1;
            long j18 = j3;
            long j19 = (j15 * j3) + (iArr3[i4] & 4294967295L) + (j17 >>> 32);
            iArr3[i4] = (int) j19;
            long j20 = (j15 * j4) + (iArr3[r6] & 4294967295L) + (j19 >>> 32);
            iArr3[i2 + 2] = (int) j20;
            long j21 = (j15 * j5) + (iArr3[r6] & 4294967295L) + (j20 >>> 32);
            iArr3[i2 + 3] = (int) j21;
            long j22 = (j15 * j6) + (iArr3[r6] & 4294967295L) + (j21 >>> 32);
            iArr3[i2 + 4] = (int) j22;
            long j23 = j22 >>> 32;
            long j24 = (j15 * j7) + (iArr3[r3] & 4294967295L) + j23;
            iArr3[i2 + 5] = (int) j24;
            iArr3[i2 + 6] = (int) (j24 >>> 32);
            i2 = i4;
            j2 = j16;
            j3 = j18;
        }
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        int i2 = 0;
        long j2 = 4294967295L;
        long j3 = iArr2[0] & 4294967295L;
        long j4 = iArr2[1] & 4294967295L;
        long j5 = iArr2[2] & 4294967295L;
        long j6 = iArr2[3] & 4294967295L;
        long j7 = iArr2[4] & 4294967295L;
        long j8 = iArr2[5] & 4294967295L;
        long j9 = 0;
        while (i2 < 6) {
            long j10 = j8;
            long j11 = iArr[i2] & j2;
            long j12 = j3;
            long j13 = (j11 * j3) + (iArr3[r23] & j2) + 0;
            iArr3[i2 + 0] = (int) j13;
            int i3 = i2 + 1;
            long j14 = (j11 * j4) + (iArr3[i3] & 4294967295L) + (j13 >>> 32);
            iArr3[i3] = (int) j14;
            long j15 = (j11 * j5) + (iArr3[r23] & 4294967295L) + (j14 >>> 32);
            iArr3[i2 + 2] = (int) j15;
            long j16 = (j11 * j6) + (iArr3[r6] & 4294967295L) + (j15 >>> 32);
            iArr3[i2 + 3] = (int) j16;
            long j17 = (j11 * j7) + (iArr3[r6] & 4294967295L) + (j16 >>> 32);
            iArr3[i2 + 4] = (int) j17;
            long j18 = j17 >>> 32;
            long j19 = (j11 * j10) + (iArr3[r4] & 4294967295L) + j18;
            iArr3[i2 + 5] = (int) j19;
            long j20 = (j19 >>> 32) + (iArr3[r0] & 4294967295L) + j9;
            iArr3[i2 + 6] = (int) j20;
            j9 = j20 >>> 32;
            i2 = i3;
            j2 = 4294967295L;
            j8 = j10;
            j3 = j12;
            j4 = j4;
        }
        return (int) j9;
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j2 = iArr[0] & 4294967295L;
        int i2 = 12;
        int i3 = 5;
        int i4 = 0;
        while (true) {
            int i5 = i3 - 1;
            long j3 = iArr[i3] & 4294967295L;
            long j4 = j3 * j3;
            int i6 = i2 - 1;
            iArr2[i6] = (i4 << 31) | ((int) (j4 >>> 33));
            i2 = i6 - 1;
            iArr2[i2] = (int) (j4 >>> 1);
            int i7 = (int) j4;
            if (i5 <= 0) {
                long j5 = j2 * j2;
                long j6 = ((i7 << 31) & 4294967295L) | (j5 >>> 33);
                iArr2[0] = (int) j5;
                long j7 = iArr[1] & 4294967295L;
                long j8 = (j7 * j2) + j6;
                int i8 = (int) j8;
                iArr2[1] = (((int) (j5 >>> 32)) & 1) | (i8 << 1);
                long j9 = iArr[2] & 4294967295L;
                long j10 = (j9 * j2) + (iArr2[2] & 4294967295L) + (j8 >>> 32);
                int i9 = (int) j10;
                iArr2[2] = (i8 >>> 31) | (i9 << 1);
                long m1007a = AbstractC0413b.m1007a(j9, j7, j10 >>> 32, iArr2[3] & 4294967295L);
                long j11 = (iArr2[4] & 4294967295L) + (m1007a >>> 32);
                long j12 = iArr[3] & 4294967295L;
                long j13 = (iArr2[5] & 4294967295L) + (j11 >>> 32);
                long j14 = (j12 * j2) + (m1007a & 4294967295L);
                int i10 = (int) j14;
                iArr2[3] = (i9 >>> 31) | (i10 << 1);
                int i11 = i10 >>> 31;
                long m1007a2 = AbstractC0413b.m1007a(j12, j7, j14 >>> 32, j11 & 4294967295L);
                long m1007a3 = AbstractC0413b.m1007a(j12, j9, m1007a2 >>> 32, j13 & 4294967295L);
                long j15 = (iArr2[6] & 4294967295L) + (j13 >>> 32) + (m1007a3 >>> 32);
                long j16 = m1007a3 & 4294967295L;
                long j17 = iArr[4] & 4294967295L;
                long j18 = (iArr2[7] & 4294967295L) + (j15 >>> 32);
                long j19 = j15 & 4294967295L;
                long j20 = (iArr2[8] & 4294967295L) + (j18 >>> 32);
                long j21 = j18 & 4294967295L;
                long j22 = (j17 * j2) + (m1007a2 & 4294967295L);
                int i12 = (int) j22;
                iArr2[4] = (i12 << 1) | i11;
                long m1007a4 = AbstractC0413b.m1007a(j17, j7, j22 >>> 32, j16);
                long m1007a5 = AbstractC0413b.m1007a(j17, j9, m1007a4 >>> 32, j19);
                long m1007a6 = AbstractC0413b.m1007a(j17, j12, m1007a5 >>> 32, j21);
                long j23 = m1007a5 & 4294967295L;
                long j24 = j20 + (m1007a6 >>> 32);
                long j25 = iArr[5] & 4294967295L;
                long j26 = (iArr2[9] & 4294967295L) + (j24 >>> 32);
                long j27 = j24 & 4294967295L;
                long j28 = (iArr2[10] & 4294967295L) + (j26 >>> 32);
                long j29 = j26 & 4294967295L;
                long j30 = (j2 * j25) + (m1007a4 & 4294967295L);
                int i13 = (int) j30;
                iArr2[5] = (i12 >>> 31) | (i13 << 1);
                int i14 = i13 >>> 31;
                long m1007a7 = AbstractC0413b.m1007a(j7, j25, j30 >>> 32, j23);
                long m1007a8 = AbstractC0413b.m1007a(j25, j9, m1007a7 >>> 32, m1007a6 & 4294967295L);
                long m1007a9 = AbstractC0413b.m1007a(j25, j12, m1007a8 >>> 32, j27);
                long m1007a10 = AbstractC0413b.m1007a(j25, j17, m1007a9 >>> 32, j29);
                long j31 = j28 + (m1007a10 >>> 32);
                int i15 = (int) m1007a7;
                iArr2[6] = i14 | (i15 << 1);
                int i16 = (int) m1007a8;
                iArr2[7] = (i15 >>> 31) | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) m1007a9;
                iArr2[8] = i17 | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = (int) m1007a10;
                iArr2[9] = i19 | (i20 << 1);
                int i21 = i20 >>> 31;
                int i22 = (int) j31;
                iArr2[10] = i21 | (i22 << 1);
                iArr2[11] = ((iArr2[11] + ((int) (j31 >>> 32))) << 1) | (i22 >>> 31);
                return;
            }
            i3 = i5;
            i4 = i7;
        }
    }

    public static int sub(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = ((iArr[0] & 4294967295L) - (iArr2[0] & 4294967295L)) + 0;
        iArr3[0] = (int) j2;
        long j3 = ((iArr[1] & 4294967295L) - (iArr2[1] & 4294967295L)) + (j2 >> 32);
        iArr3[1] = (int) j3;
        long j4 = ((iArr[2] & 4294967295L) - (iArr2[2] & 4294967295L)) + (j3 >> 32);
        iArr3[2] = (int) j4;
        long j5 = ((iArr[3] & 4294967295L) - (iArr2[3] & 4294967295L)) + (j4 >> 32);
        iArr3[3] = (int) j5;
        long j6 = ((iArr[4] & 4294967295L) - (iArr2[4] & 4294967295L)) + (j5 >> 32);
        iArr3[4] = (int) j6;
        long j7 = ((iArr[5] & 4294967295L) - (iArr2[5] & 4294967295L)) + (j6 >> 32);
        iArr3[5] = (int) j7;
        return (int) (j7 >> 32);
    }

    public static int subFrom(int[] iArr, int[] iArr2) {
        long j2 = ((iArr2[0] & 4294967295L) - (iArr[0] & 4294967295L)) + 0;
        iArr2[0] = (int) j2;
        long j3 = ((iArr2[1] & 4294967295L) - (iArr[1] & 4294967295L)) + (j2 >> 32);
        iArr2[1] = (int) j3;
        long j4 = ((iArr2[2] & 4294967295L) - (iArr[2] & 4294967295L)) + (j3 >> 32);
        iArr2[2] = (int) j4;
        long j5 = ((iArr2[3] & 4294967295L) - (iArr[3] & 4294967295L)) + (j4 >> 32);
        iArr2[3] = (int) j5;
        long j6 = ((iArr2[4] & 4294967295L) - (iArr[4] & 4294967295L)) + (j5 >> 32);
        iArr2[4] = (int) j6;
        long j7 = ((iArr2[5] & 4294967295L) - (4294967295L & iArr[5])) + (j6 >> 32);
        iArr2[5] = (int) j7;
        return (int) (j7 >> 32);
    }
}
