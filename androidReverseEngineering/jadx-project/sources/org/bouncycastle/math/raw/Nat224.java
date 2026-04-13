package org.bouncycastle.math.raw;

import java.math.BigInteger;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class Nat224 {

    /* renamed from: M */
    private static final long f1520M = 4294967295L;

    public static int add(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = (iArr[i2 + 0] & 4294967295L) + (iArr2[i3 + 0] & 4294967295L) + 0;
        iArr3[i4 + 0] = (int) j2;
        long j3 = (iArr[i2 + 1] & 4294967295L) + (iArr2[i3 + 1] & 4294967295L) + (j2 >>> 32);
        iArr3[i4 + 1] = (int) j3;
        long j4 = (iArr[i2 + 2] & 4294967295L) + (iArr2[i3 + 2] & 4294967295L) + (j3 >>> 32);
        iArr3[i4 + 2] = (int) j4;
        long j5 = (iArr[i2 + 3] & 4294967295L) + (iArr2[i3 + 3] & 4294967295L) + (j4 >>> 32);
        iArr3[i4 + 3] = (int) j5;
        long j6 = (iArr[i2 + 4] & 4294967295L) + (iArr2[i3 + 4] & 4294967295L) + (j5 >>> 32);
        iArr3[i4 + 4] = (int) j6;
        long j7 = (iArr[i2 + 5] & 4294967295L) + (iArr2[i3 + 5] & 4294967295L) + (j6 >>> 32);
        iArr3[i4 + 5] = (int) j7;
        long j8 = (iArr[i2 + 6] & 4294967295L) + (iArr2[i3 + 6] & 4294967295L) + (j7 >>> 32);
        iArr3[i4 + 6] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int addBothTo(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = (iArr[i2 + 0] & 4294967295L) + (iArr2[i3 + 0] & 4294967295L) + (iArr3[r4] & 4294967295L) + 0;
        iArr3[i4 + 0] = (int) j2;
        long j3 = (iArr[i2 + 1] & 4294967295L) + (iArr2[i3 + 1] & 4294967295L) + (iArr3[r7] & 4294967295L) + (j2 >>> 32);
        iArr3[i4 + 1] = (int) j3;
        long j4 = (iArr[i2 + 2] & 4294967295L) + (iArr2[i3 + 2] & 4294967295L) + (iArr3[r7] & 4294967295L) + (j3 >>> 32);
        iArr3[i4 + 2] = (int) j4;
        long j5 = (iArr[i2 + 3] & 4294967295L) + (iArr2[i3 + 3] & 4294967295L) + (iArr3[r7] & 4294967295L) + (j4 >>> 32);
        iArr3[i4 + 3] = (int) j5;
        long j6 = (iArr[i2 + 4] & 4294967295L) + (iArr2[i3 + 4] & 4294967295L) + (iArr3[r7] & 4294967295L) + (j5 >>> 32);
        iArr3[i4 + 4] = (int) j6;
        long j7 = (iArr[i2 + 5] & 4294967295L) + (iArr2[i3 + 5] & 4294967295L) + (iArr3[r7] & 4294967295L) + (j6 >>> 32);
        iArr3[i4 + 5] = (int) j7;
        long j8 = (iArr[i2 + 6] & 4294967295L) + (iArr2[i3 + 6] & 4294967295L) + (iArr3[r15] & 4294967295L) + (j7 >>> 32);
        iArr3[i4 + 6] = (int) j8;
        return (int) (j8 >>> 32);
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
        long j7 = (iArr[i2 + 5] & 4294967295L) + (iArr2[r6] & 4294967295L) + (j6 >>> 32);
        iArr2[i3 + 5] = (int) j7;
        long j8 = (iArr[i2 + 6] & 4294967295L) + (4294967295L & iArr2[r12]) + (j7 >>> 32);
        iArr2[i3 + 6] = (int) j8;
        return (int) (j8 >>> 32);
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
        long j7 = (iArr[r5] & 4294967295L) + (iArr2[r8] & 4294967295L) + (j6 >>> 32);
        int i9 = (int) j7;
        iArr[i2 + 5] = i9;
        iArr2[i3 + 5] = i9;
        long j8 = (iArr[r12] & 4294967295L) + (4294967295L & iArr2[r14]) + (j7 >>> 32);
        int i10 = (int) j8;
        iArr[i2 + 6] = i10;
        iArr2[i3 + 6] = i10;
        return (int) (j8 >>> 32);
    }

    public static void copy(int[] iArr, int i2, int[] iArr2, int i3) {
        iArr2[i3 + 0] = iArr[i2 + 0];
        iArr2[i3 + 1] = iArr[i2 + 1];
        iArr2[i3 + 2] = iArr[i2 + 2];
        iArr2[i3 + 3] = iArr[i2 + 3];
        iArr2[i3 + 4] = iArr[i2 + 4];
        iArr2[i3 + 5] = iArr[i2 + 5];
        iArr2[i3 + 6] = iArr[i2 + 6];
    }

    public static int[] create() {
        return new int[7];
    }

    public static int[] createExt() {
        return new int[14];
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
        for (int i2 = 6; i2 >= 0; i2--) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 224) {
            throw new IllegalArgumentException();
        }
        int[] create = create();
        for (int i2 = 0; i2 < 7; i2++) {
            create[i2] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
        }
        return create;
    }

    public static int getBit(int[] iArr, int i2) {
        int i3;
        if (i2 == 0) {
            i3 = iArr[0];
        } else {
            int i4 = i2 >> 5;
            if (i4 < 0 || i4 >= 7) {
                return 0;
            }
            i3 = iArr[i4] >>> (i2 & 31);
        }
        return i3 & 1;
    }

    public static boolean gte(int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 6; i4 >= 0; i4--) {
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
        for (int i2 = 1; i2 < 7; i2++) {
            if (iArr[i2] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i2 = 0; i2 < 7; i2++) {
            if (iArr[i2] != 0) {
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
        long j15 = j14 >>> 32;
        long j16 = iArr2[i3 + 6] & 4294967295L;
        long j17 = (j8 * j16) + j15;
        iArr3[i4 + 6] = (int) j17;
        iArr3[i4 + 7] = (int) (j17 >>> 32);
        int i5 = 1;
        int i6 = i4;
        int i7 = 1;
        while (i7 < 7) {
            i6 += i5;
            long j18 = iArr[i2 + i7] & 4294967295L;
            long j19 = (j18 * j2) + (iArr3[r26] & 4294967295L) + 0;
            int i8 = i7;
            iArr3[i6 + 0] = (int) j19;
            long j20 = j16;
            long j21 = (j18 * j3) + (iArr3[r27] & 4294967295L) + (j19 >>> 32);
            iArr3[i6 + 1] = (int) j21;
            long j22 = (j18 * j4) + (iArr3[r25] & 4294967295L) + (j21 >>> 32);
            iArr3[i6 + 2] = (int) j22;
            long j23 = (j18 * j5) + (iArr3[r1] & 4294967295L) + (j22 >>> 32);
            iArr3[i6 + 3] = (int) j23;
            long j24 = (j18 * j6) + (iArr3[r1] & 4294967295L) + (j23 >>> 32);
            iArr3[i6 + 4] = (int) j24;
            long j25 = (j18 * j7) + (iArr3[r1] & 4294967295L) + (j24 >>> 32);
            iArr3[i6 + 5] = (int) j25;
            long j26 = (j18 * j20) + (iArr3[r1] & 4294967295L) + (j25 >>> 32);
            iArr3[i6 + 6] = (int) j26;
            iArr3[i6 + 7] = (int) (j26 >>> 32);
            i7 = i8 + 1;
            j4 = j4;
            j16 = j20;
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
        long j14 = (j2 * j13) + j11 + (iArr2[i4 + 5] & 4294967295L) + (j12 >>> 32);
        iArr3[i5 + 5] = (int) j14;
        long j15 = iArr[i3 + 6] & 4294967295L;
        long j16 = (j2 * j15) + j13 + (4294967295L & iArr2[i4 + 6]) + (j14 >>> 32);
        iArr3[i5 + 6] = (int) j16;
        return (j16 >>> 32) + j15;
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
        return Nat.incAt(7, iArr, i3, 4);
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
        return Nat.incAt(7, iArr, i4, 3);
    }

    public static int mulAddTo(int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j2 = iArr2[i3 + 0] & 4294967295L;
        long j3 = iArr2[i3 + 1] & 4294967295L;
        long j4 = iArr2[i3 + 2] & 4294967295L;
        long j5 = iArr2[i3 + 3] & 4294967295L;
        long j6 = iArr2[i3 + 4] & 4294967295L;
        long j7 = iArr2[i3 + 5] & 4294967295L;
        long j8 = iArr2[i3 + 6] & 4294967295L;
        int i5 = 0;
        long j9 = 0;
        int i6 = i4;
        while (i5 < 7) {
            int i7 = i5;
            long j10 = iArr[i2 + i5] & 4294967295L;
            long j11 = j2;
            long j12 = (j10 * j2) + (iArr3[r26] & 4294967295L) + 0;
            long j13 = j8;
            iArr3[i6 + 0] = (int) j12;
            int i8 = i6 + 1;
            long j14 = (j10 * j3) + (iArr3[i8] & 4294967295L) + (j12 >>> 32);
            iArr3[i8] = (int) j14;
            long j15 = (j10 * j4) + (iArr3[r5] & 4294967295L) + (j14 >>> 32);
            iArr3[i6 + 2] = (int) j15;
            long j16 = (j10 * j5) + (iArr3[r5] & 4294967295L) + (j15 >>> 32);
            iArr3[i6 + 3] = (int) j16;
            long j17 = (j10 * j6) + (iArr3[r5] & 4294967295L) + (j16 >>> 32);
            iArr3[i6 + 4] = (int) j17;
            long j18 = (j10 * j7) + (iArr3[r5] & 4294967295L) + (j17 >>> 32);
            iArr3[i6 + 5] = (int) j18;
            long j19 = (j10 * j13) + (iArr3[r5] & 4294967295L) + (j18 >>> 32);
            iArr3[i6 + 6] = (int) j19;
            long j20 = (j19 >>> 32) + (iArr3[r16] & 4294967295L) + j9;
            iArr3[i6 + 7] = (int) j20;
            j9 = j20 >>> 32;
            i5 = i7 + 1;
            i6 = i8;
            j8 = j13;
            j2 = j11;
            j3 = j3;
        }
        return (int) j9;
    }

    public static int mulByWord(int i2, int[] iArr) {
        long j2 = i2 & 4294967295L;
        long j3 = ((iArr[0] & 4294967295L) * j2) + 0;
        iArr[0] = (int) j3;
        long j4 = ((iArr[1] & 4294967295L) * j2) + (j3 >>> 32);
        iArr[1] = (int) j4;
        long j5 = ((iArr[2] & 4294967295L) * j2) + (j4 >>> 32);
        iArr[2] = (int) j5;
        long j6 = ((iArr[3] & 4294967295L) * j2) + (j5 >>> 32);
        iArr[3] = (int) j6;
        long j7 = ((iArr[4] & 4294967295L) * j2) + (j6 >>> 32);
        iArr[4] = (int) j7;
        long j8 = ((iArr[5] & 4294967295L) * j2) + (j7 >>> 32);
        iArr[5] = (int) j8;
        long j9 = (j2 * (4294967295L & iArr[6])) + (j8 >>> 32);
        iArr[6] = (int) j9;
        return (int) (j9 >>> 32);
    }

    public static int mulByWordAddTo(int i2, int[] iArr, int[] iArr2) {
        long j2 = i2 & 4294967295L;
        long j3 = ((iArr2[0] & 4294967295L) * j2) + (iArr[0] & 4294967295L) + 0;
        iArr2[0] = (int) j3;
        long j4 = ((iArr2[1] & 4294967295L) * j2) + (iArr[1] & 4294967295L) + (j3 >>> 32);
        iArr2[1] = (int) j4;
        long j5 = ((iArr2[2] & 4294967295L) * j2) + (iArr[2] & 4294967295L) + (j4 >>> 32);
        iArr2[2] = (int) j5;
        long j6 = ((iArr2[3] & 4294967295L) * j2) + (iArr[3] & 4294967295L) + (j5 >>> 32);
        iArr2[3] = (int) j6;
        long j7 = ((iArr2[4] & 4294967295L) * j2) + (iArr[4] & 4294967295L) + (j6 >>> 32);
        iArr2[4] = (int) j7;
        long j8 = ((iArr2[5] & 4294967295L) * j2) + (iArr[5] & 4294967295L) + (j7 >>> 32);
        iArr2[5] = (int) j8;
        long j9 = (j2 * (iArr2[6] & 4294967295L)) + (4294967295L & iArr[6]) + (j8 >>> 32);
        iArr2[6] = (int) j9;
        return (int) (j9 >>> 32);
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
        } while (i4 < 7);
        return (int) j3;
    }

    public static int mulWordAddTo(int i2, int[] iArr, int i3, int[] iArr2, int i4) {
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
        long j8 = ((iArr[i3 + 5] & 4294967295L) * j2) + (iArr2[r8] & 4294967295L) + (j7 >>> 32);
        iArr2[i4 + 5] = (int) j8;
        long j9 = (j2 * (iArr[i3 + 6] & 4294967295L)) + (iArr2[r15] & 4294967295L) + (j8 >>> 32);
        iArr2[i4 + 6] = (int) j9;
        return (int) (j9 >>> 32);
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
        return Nat.incAt(7, iArr, i3, 3);
    }

    public static void square(int[] iArr, int i2, int[] iArr2, int i3) {
        long j2 = iArr[i2 + 0] & 4294967295L;
        int i4 = 0;
        int i5 = 14;
        int i6 = 6;
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
                long j19 = (iArr2[r44] & 4294967295L) + (j18 >>> 32);
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
                long j26 = (iArr2[r48] & 4294967295L) + (j25 >>> 32);
                long j27 = j25 & 4294967295L;
                long j28 = (j24 * j2) + (m1007a4 & 4294967295L);
                int i14 = (int) j28;
                iArr2[i3 + 5] = (i13 >>> 31) | (i14 << 1);
                int i15 = i14 >>> 31;
                long m1007a7 = AbstractC0413b.m1007a(j24, j7, j28 >>> 32, j21);
                long m1007a8 = AbstractC0413b.m1007a(j24, j9, m1007a7 >>> 32, j23);
                long m1007a9 = AbstractC0413b.m1007a(j24, j12, m1007a8 >>> 32, j22 & 4294967295L);
                long j29 = m1007a8 & 4294967295L;
                long m1007a10 = AbstractC0413b.m1007a(j24, j17, m1007a9 >>> 32, j27);
                long j30 = m1007a9 & 4294967295L;
                long j31 = j26 + (m1007a10 >>> 32);
                long j32 = m1007a10 & 4294967295L;
                long j33 = iArr[i2 + 6] & 4294967295L;
                long j34 = (iArr2[r9] & 4294967295L) + (j31 >>> 32);
                long j35 = j31 & 4294967295L;
                long j36 = (j2 * j33) + (m1007a7 & 4294967295L);
                int i16 = (int) j36;
                iArr2[i3 + 6] = i15 | (i16 << 1);
                int i17 = i16 >>> 31;
                long m1007a11 = AbstractC0413b.m1007a(j7, j33, j36 >>> 32, j29);
                long m1007a12 = AbstractC0413b.m1007a(j33, j9, m1007a11 >>> 32, j30);
                long m1007a13 = AbstractC0413b.m1007a(j33, j12, m1007a12 >>> 32, j32);
                long m1007a14 = AbstractC0413b.m1007a(j33, j17, m1007a13 >>> 32, j35);
                long m1007a15 = AbstractC0413b.m1007a(j33, j24, m1007a14 >>> 32, j34 & 4294967295L);
                long j37 = (iArr2[r50] & 4294967295L) + (j34 >>> 32) + (m1007a15 >>> 32);
                int i18 = (int) m1007a11;
                iArr2[i3 + 7] = (i18 << 1) | i17;
                int i19 = (int) m1007a12;
                iArr2[i3 + 8] = (i18 >>> 31) | (i19 << 1);
                int i20 = i19 >>> 31;
                int i21 = (int) m1007a13;
                iArr2[i3 + 9] = i20 | (i21 << 1);
                int i22 = i21 >>> 31;
                int i23 = (int) m1007a14;
                iArr2[i3 + 10] = i22 | (i23 << 1);
                int i24 = i23 >>> 31;
                int i25 = (int) m1007a15;
                iArr2[i3 + 11] = i24 | (i25 << 1);
                int i26 = i25 >>> 31;
                int i27 = (int) j37;
                iArr2[i3 + 12] = i26 | (i27 << 1);
                int i28 = i27 >>> 31;
                int i29 = i3 + 13;
                iArr2[i29] = i28 | ((iArr2[i29] + ((int) (j37 >>> 32))) << 1);
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
        long j8 = ((iArr[i2 + 6] & 4294967295L) - (iArr2[i3 + 6] & 4294967295L)) + (j7 >> 32);
        iArr3[i4 + 6] = (int) j8;
        return (int) (j8 >> 32);
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
        long j8 = (((iArr3[6] & 4294967295L) - (iArr[6] & 4294967295L)) - (iArr2[6] & 4294967295L)) + (j7 >> 32);
        iArr3[6] = (int) j8;
        return (int) (j8 >> 32);
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
        long j7 = ((iArr2[r5] & 4294967295L) - (iArr[i2 + 5] & 4294967295L)) + (j6 >> 32);
        iArr2[i3 + 5] = (int) j7;
        long j8 = ((iArr2[r13] & 4294967295L) - (iArr[i2 + 6] & 4294967295L)) + (j7 >> 32);
        iArr2[i3 + 6] = (int) j8;
        return (int) (j8 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[28];
        for (int i2 = 0; i2 < 7; i2++) {
            int i3 = iArr[i2];
            if (i3 != 0) {
                Pack.intToBigEndian(i3, bArr, (6 - i2) << 2);
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
    }

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
        long j8 = (iArr[6] & 4294967295L) + (iArr2[6] & 4294967295L) + (j7 >>> 32);
        iArr3[6] = (int) j8;
        return (int) (j8 >>> 32);
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
        long j8 = (iArr[6] & 4294967295L) + (iArr2[6] & 4294967295L) + (iArr3[6] & 4294967295L) + (j7 >>> 32);
        iArr3[6] = (int) j8;
        return (int) (j8 >>> 32);
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
        long j7 = (iArr[5] & 4294967295L) + (iArr2[5] & 4294967295L) + (j6 >>> 32);
        iArr2[5] = (int) j7;
        long j8 = (iArr[6] & 4294967295L) + (4294967295L & iArr2[6]) + (j7 >>> 32);
        iArr2[6] = (int) j8;
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
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i2 = 6; i2 >= 0; i2--) {
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
        long j8 = iArr2[6] & 4294967295L;
        long j9 = iArr[0] & 4294967295L;
        long j10 = (j9 * j2) + 0;
        iArr3[0] = (int) j10;
        long j11 = (j9 * j3) + (j10 >>> 32);
        iArr3[1] = (int) j11;
        long j12 = (j9 * j4) + (j11 >>> 32);
        iArr3[2] = (int) j12;
        long j13 = (j9 * j5) + (j12 >>> 32);
        iArr3[3] = (int) j13;
        long j14 = (j9 * j6) + (j13 >>> 32);
        iArr3[4] = (int) j14;
        long j15 = (j9 * j7) + (j14 >>> 32);
        iArr3[5] = (int) j15;
        long j16 = (j9 * j8) + (j15 >>> 32);
        iArr3[6] = (int) j16;
        iArr3[7] = (int) (j16 >>> 32);
        int i2 = 1;
        for (int i3 = 7; i2 < i3; i3 = 7) {
            long j17 = iArr[i2] & 4294967295L;
            long j18 = j2;
            long j19 = (j17 * j2) + (iArr3[r3] & 4294967295L) + 0;
            iArr3[i2 + 0] = (int) j19;
            int i4 = i2 + 1;
            long j20 = j3;
            long j21 = (j17 * j3) + (iArr3[i4] & 4294967295L) + (j19 >>> 32);
            iArr3[i4] = (int) j21;
            long j22 = (j17 * j4) + (iArr3[r6] & 4294967295L) + (j21 >>> 32);
            iArr3[i2 + 2] = (int) j22;
            long j23 = (j17 * j5) + (iArr3[r6] & 4294967295L) + (j22 >>> 32);
            iArr3[i2 + 3] = (int) j23;
            long j24 = (j17 * j6) + (iArr3[r6] & 4294967295L) + (j23 >>> 32);
            iArr3[i2 + 4] = (int) j24;
            long j25 = (j17 * j7) + (iArr3[r6] & 4294967295L) + (j24 >>> 32);
            iArr3[i2 + 5] = (int) j25;
            long j26 = j25 >>> 32;
            long j27 = (j17 * j8) + (iArr3[r3] & 4294967295L) + j26;
            iArr3[i2 + 6] = (int) j27;
            iArr3[i2 + 7] = (int) (j27 >>> 32);
            i2 = i4;
            j2 = j18;
            j3 = j20;
        }
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j2 = iArr2[0] & 4294967295L;
        long j3 = iArr2[1] & 4294967295L;
        long j4 = iArr2[2] & 4294967295L;
        long j5 = iArr2[3] & 4294967295L;
        long j6 = iArr2[4] & 4294967295L;
        long j7 = iArr2[5] & 4294967295L;
        long j8 = iArr2[6] & 4294967295L;
        long j9 = 0;
        int i2 = 0;
        while (i2 < 7) {
            long j10 = j8;
            long j11 = iArr[i2] & 4294967295L;
            long j12 = j7;
            long j13 = (j11 * j2) + (iArr3[r25] & 4294967295L) + 0;
            iArr3[i2 + 0] = (int) j13;
            int i3 = i2 + 1;
            long j14 = j3;
            long j15 = (j11 * j3) + (iArr3[i3] & 4294967295L) + (j13 >>> 32);
            iArr3[i3] = (int) j15;
            long j16 = (j11 * j4) + (iArr3[r25] & 4294967295L) + (j15 >>> 32);
            iArr3[i2 + 2] = (int) j16;
            long j17 = (j11 * j5) + (iArr3[r8] & 4294967295L) + (j16 >>> 32);
            iArr3[i2 + 3] = (int) j17;
            long j18 = (j11 * j6) + (iArr3[r8] & 4294967295L) + (j17 >>> 32);
            iArr3[i2 + 4] = (int) j18;
            long j19 = (j11 * j12) + (iArr3[r8] & 4294967295L) + (j18 >>> 32);
            iArr3[i2 + 5] = (int) j19;
            long j20 = (j11 * j10) + (iArr3[r8] & 4294967295L) + (j19 >>> 32);
            iArr3[i2 + 6] = (int) j20;
            long j21 = (j20 >>> 32) + (iArr3[r2] & 4294967295L) + j9;
            iArr3[i2 + 7] = (int) j21;
            j9 = j21 >>> 32;
            i2 = i3;
            j8 = j10;
            j7 = j12;
            j4 = j4;
            j3 = j14;
        }
        return (int) j9;
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j2 = iArr[0] & 4294967295L;
        int i2 = 14;
        int i3 = 6;
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
                long j30 = (j25 * j2) + (m1007a4 & 4294967295L);
                int i13 = (int) j30;
                iArr2[5] = (i12 >>> 31) | (i13 << 1);
                long m1007a7 = AbstractC0413b.m1007a(j25, j7, j30 >>> 32, j23);
                long m1007a8 = AbstractC0413b.m1007a(j25, j9, m1007a7 >>> 32, m1007a6 & 4294967295L);
                long m1007a9 = AbstractC0413b.m1007a(j25, j12, m1007a8 >>> 32, j27);
                long j31 = m1007a8 & 4294967295L;
                long m1007a10 = AbstractC0413b.m1007a(j25, j17, m1007a9 >>> 32, j29);
                long j32 = j28 + (m1007a10 >>> 32);
                long j33 = m1007a10 & 4294967295L;
                long j34 = iArr[6] & 4294967295L;
                long j35 = (iArr2[11] & 4294967295L) + (j32 >>> 32);
                long j36 = (iArr2[12] & 4294967295L) + (j35 >>> 32);
                long j37 = j35 & 4294967295L;
                long j38 = (j2 * j34) + (m1007a7 & 4294967295L);
                int i14 = (int) j38;
                iArr2[6] = (i13 >>> 31) | (i14 << 1);
                int i15 = i14 >>> 31;
                long m1007a11 = AbstractC0413b.m1007a(j7, j34, j38 >>> 32, j31);
                long m1007a12 = AbstractC0413b.m1007a(j34, j9, m1007a11 >>> 32, m1007a9 & 4294967295L);
                long m1007a13 = AbstractC0413b.m1007a(j34, j12, m1007a12 >>> 32, j33);
                long m1007a14 = AbstractC0413b.m1007a(j34, j17, m1007a13 >>> 32, j32 & 4294967295L);
                long m1007a15 = AbstractC0413b.m1007a(j34, j25, m1007a14 >>> 32, j37);
                long j39 = j36 + (m1007a15 >>> 32);
                int i16 = (int) m1007a11;
                iArr2[7] = i15 | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) m1007a12;
                iArr2[8] = i17 | (i18 << 1);
                int i19 = i18 >>> 31;
                int i20 = (int) m1007a13;
                iArr2[9] = i19 | (i20 << 1);
                int i21 = (int) m1007a14;
                iArr2[10] = (i21 << 1) | (i20 >>> 31);
                int i22 = (int) m1007a15;
                iArr2[11] = (i21 >>> 31) | (i22 << 1);
                int i23 = i22 >>> 31;
                int i24 = (int) j39;
                iArr2[12] = i23 | (i24 << 1);
                iArr2[13] = (i24 >>> 31) | ((iArr2[13] + ((int) (j39 >>> 32))) << 1);
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
        long j8 = ((iArr[6] & 4294967295L) - (iArr2[6] & 4294967295L)) + (j7 >> 32);
        iArr3[6] = (int) j8;
        return (int) (j8 >> 32);
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
        long j7 = ((iArr2[5] & 4294967295L) - (iArr[5] & 4294967295L)) + (j6 >> 32);
        iArr2[5] = (int) j7;
        long j8 = ((iArr2[6] & 4294967295L) - (4294967295L & iArr[6])) + (j7 >> 32);
        iArr2[6] = (int) j8;
        return (int) (j8 >> 32);
    }
}
