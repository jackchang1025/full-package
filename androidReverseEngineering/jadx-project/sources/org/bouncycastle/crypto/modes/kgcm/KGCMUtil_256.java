package org.bouncycastle.crypto.modes.kgcm;

import org.bouncycastle.math.raw.Interleave;

/* loaded from: classes.dex */
public class KGCMUtil_256 {
    public static final int SIZE = 4;

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr2[3] ^ jArr[3];
    }

    public static void copy(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
    }

    public static boolean equal(long[] jArr, long[] jArr2) {
        return ((jArr2[3] ^ jArr[3]) | ((((jArr[0] ^ jArr2[0]) | 0) | (jArr[1] ^ jArr2[1])) | (jArr[2] ^ jArr2[2]))) == 0;
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        int i2;
        long j2;
        long j3 = jArr[0];
        long j4 = jArr[1];
        long j5 = jArr[2];
        long j6 = jArr[3];
        long j7 = jArr2[0];
        long j8 = jArr2[1];
        long j9 = jArr2[2];
        long j10 = jArr2[3];
        long j11 = 0;
        int i3 = 0;
        long j12 = 0;
        long j13 = 0;
        long j14 = 0;
        long j15 = 0;
        while (true) {
            j2 = j5;
            if (i3 >= 64) {
                break;
            }
            long j16 = -(j3 & 1);
            j11 ^= j7 & j16;
            long j17 = -(j4 & 1);
            j4 >>>= 1;
            j12 = (j12 ^ (j8 & j16)) ^ (j7 & j17);
            j13 = (j13 ^ (j9 & j16)) ^ (j8 & j17);
            j14 = (j14 ^ (j10 & j16)) ^ (j9 & j17);
            j15 ^= j10 & j17;
            long j18 = j10 >> 63;
            j10 = (j10 << 1) | (j9 >>> 63);
            j9 = (j9 << 1) | (j8 >>> 63);
            j8 = (j7 >>> 63) | (j8 << 1);
            j7 = (j7 << 1) ^ (j18 & 1061);
            i3++;
            j3 >>>= 1;
            j5 = j2;
        }
        long j19 = (((j10 >>> 62) ^ j7) ^ (j10 >>> 59)) ^ (j10 >>> 54);
        long j20 = j9;
        int i4 = 0;
        long j21 = j8;
        long j22 = j6;
        long j23 = ((j10 ^ (j10 << 2)) ^ (j10 << 5)) ^ (j10 << 10);
        long j24 = j2;
        for (i2 = 64; i4 < i2; i2 = 64) {
            long j25 = -(j24 & 1);
            j24 >>>= 1;
            j11 ^= j23 & j25;
            long j26 = j23;
            long j27 = -(j22 & 1);
            j22 >>>= 1;
            j12 = (j12 ^ (j19 & j25)) ^ (j26 & j27);
            j13 = (j13 ^ (j21 & j25)) ^ (j19 & j27);
            j14 = (j14 ^ (j20 & j25)) ^ (j21 & j27);
            j15 ^= j20 & j27;
            long j28 = j20 >> 63;
            j20 = (j20 << 1) | (j21 >>> 63);
            j21 = (j19 >>> 63) | (j21 << 1);
            j19 = (j19 << 1) | (j26 >>> 63);
            j23 = (j26 << 1) ^ (j28 & 1061);
            i4++;
        }
        jArr3[0] = j11 ^ (((j15 ^ (j15 << 2)) ^ (j15 << 5)) ^ (j15 << 10));
        jArr3[1] = j12 ^ (((j15 >>> 62) ^ (j15 >>> 59)) ^ (j15 >>> 54));
        jArr3[2] = j13;
        jArr3[3] = j14;
    }

    public static void multiplyX(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        jArr2[0] = ((j5 >> 63) & 1061) ^ (j2 << 1);
        jArr2[1] = (j2 >>> 63) | (j3 << 1);
        jArr2[2] = (j4 << 1) | (j3 >>> 63);
        jArr2[3] = (j5 << 1) | (j4 >>> 63);
    }

    public static void multiplyX8(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = j5 >>> 56;
        jArr2[0] = ((((j2 << 8) ^ j6) ^ (j6 << 2)) ^ (j6 << 5)) ^ (j6 << 10);
        jArr2[1] = (j2 >>> 56) | (j3 << 8);
        jArr2[2] = (j4 << 8) | (j3 >>> 56);
        jArr2[3] = (j5 << 8) | (j4 >>> 56);
    }

    public static void one(long[] jArr) {
        jArr[0] = 1;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
    }

    public static void square(long[] jArr, long[] jArr2) {
        int i2 = 8;
        long[] jArr3 = new long[8];
        for (int i3 = 0; i3 < 4; i3++) {
            Interleave.expand64To128(jArr[i3], jArr3, i3 << 1);
        }
        while (true) {
            i2--;
            if (i2 < 4) {
                copy(jArr3, jArr2);
                return;
            }
            long j2 = jArr3[i2];
            int i4 = i2 - 4;
            jArr3[i4] = jArr3[i4] ^ ((((j2 << 2) ^ j2) ^ (j2 << 5)) ^ (j2 << 10));
            int i5 = i4 + 1;
            jArr3[i5] = ((j2 >>> 54) ^ ((j2 >>> 62) ^ (j2 >>> 59))) ^ jArr3[i5];
        }
    }

    /* renamed from: x */
    public static void m1226x(long[] jArr) {
        jArr[0] = 2;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
    }

    public static void zero(long[] jArr) {
        jArr[0] = 0;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
    }
}
