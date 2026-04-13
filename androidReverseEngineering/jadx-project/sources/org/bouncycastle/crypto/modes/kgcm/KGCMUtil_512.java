package org.bouncycastle.crypto.modes.kgcm;

import org.bouncycastle.math.raw.Interleave;

/* loaded from: classes.dex */
public class KGCMUtil_512 {
    public static final int SIZE = 8;

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr[1] ^ jArr2[1];
        jArr3[2] = jArr[2] ^ jArr2[2];
        jArr3[3] = jArr[3] ^ jArr2[3];
        jArr3[4] = jArr[4] ^ jArr2[4];
        jArr3[5] = jArr[5] ^ jArr2[5];
        jArr3[6] = jArr[6] ^ jArr2[6];
        jArr3[7] = jArr2[7] ^ jArr[7];
    }

    public static void copy(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
        jArr2[2] = jArr[2];
        jArr2[3] = jArr[3];
        jArr2[4] = jArr[4];
        jArr2[5] = jArr[5];
        jArr2[6] = jArr[6];
        jArr2[7] = jArr[7];
    }

    public static boolean equal(long[] jArr, long[] jArr2) {
        return ((jArr2[7] ^ jArr[7]) | ((((((((jArr[0] ^ jArr2[0]) | 0) | (jArr[1] ^ jArr2[1])) | (jArr[2] ^ jArr2[2])) | (jArr[3] ^ jArr2[3])) | (jArr[4] ^ jArr2[4])) | (jArr[5] ^ jArr2[5])) | (jArr[6] ^ jArr2[6]))) == 0;
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        int i2 = 0;
        long j2 = jArr2[0];
        long j3 = jArr2[1];
        char c = 2;
        long j4 = jArr2[2];
        long j5 = jArr2[3];
        long j6 = jArr2[4];
        long j7 = jArr2[5];
        long j8 = jArr2[6];
        long j9 = jArr2[7];
        long j10 = 0;
        int i3 = 0;
        long j11 = 0;
        long j12 = 0;
        long j13 = 0;
        long j14 = 0;
        long j15 = 0;
        long j16 = 0;
        long j17 = 0;
        long j18 = 0;
        while (i3 < 8) {
            long j19 = jArr[i3];
            long j20 = jArr[i3 + 1];
            long j21 = j3;
            long j22 = j8;
            j8 = j7;
            j7 = j6;
            j6 = j5;
            j5 = j4;
            long j23 = j21;
            while (i2 < 64) {
                long j24 = j23;
                long j25 = -(j19 & 1);
                j19 >>>= 1;
                j10 ^= j2 & j25;
                long j26 = j5;
                long j27 = -(j20 & 1);
                j20 >>>= 1;
                j12 = (j12 ^ (j24 & j25)) ^ (j2 & j27);
                j13 = (j13 ^ (j5 & j25)) ^ (j24 & j27);
                j14 = (j14 ^ (j6 & j25)) ^ (j26 & j27);
                j15 = (j15 ^ (j7 & j25)) ^ (j6 & j27);
                j16 = (j16 ^ (j8 & j25)) ^ (j7 & j27);
                j17 = (j17 ^ (j22 & j25)) ^ (j8 & j27);
                j18 = (j18 ^ (j9 & j25)) ^ (j22 & j27);
                j11 ^= j9 & j27;
                long j28 = j9 >> 63;
                j9 = (j9 << 1) | (j22 >>> 63);
                j22 = (j22 << 1) | (j8 >>> 63);
                j8 = (j8 << 1) | (j7 >>> 63);
                j7 = (j7 << 1) | (j6 >>> 63);
                j6 = (j6 << 1) | (j26 >>> 63);
                long j29 = (j24 << 1) | (j2 >>> 63);
                j2 = (j2 << 1) ^ (j28 & 293);
                i2++;
                j23 = j29;
                j5 = (j26 << 1) | (j24 >>> 63);
            }
            long j30 = ((j2 ^ (j9 >>> 62)) ^ (j9 >>> 59)) ^ (j9 >>> 56);
            long j31 = ((j9 ^ (j9 << 2)) ^ (j9 << 5)) ^ (j9 << 8);
            i3 += 2;
            j9 = j22;
            j3 = j30;
            i2 = 0;
            c = 2;
            j2 = j31;
            j4 = j23;
        }
        jArr3[0] = j10 ^ (((j11 ^ (j11 << c)) ^ (j11 << 5)) ^ (j11 << 8));
        jArr3[1] = j12 ^ (((j11 >>> 62) ^ (j11 >>> 59)) ^ (j11 >>> 56));
        jArr3[2] = j13;
        jArr3[3] = j14;
        jArr3[4] = j15;
        jArr3[5] = j16;
        jArr3[6] = j17;
        jArr3[7] = j18;
    }

    public static void multiplyX(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = jArr[4];
        long j7 = jArr[5];
        long j8 = jArr[6];
        long j9 = jArr[7];
        jArr2[0] = (j2 << 1) ^ ((j9 >> 63) & 293);
        jArr2[1] = (j3 << 1) | (j2 >>> 63);
        jArr2[2] = (j4 << 1) | (j3 >>> 63);
        jArr2[3] = (j5 << 1) | (j4 >>> 63);
        jArr2[4] = (j6 << 1) | (j5 >>> 63);
        jArr2[5] = (j7 << 1) | (j6 >>> 63);
        jArr2[6] = (j8 << 1) | (j7 >>> 63);
        jArr2[7] = (j9 << 1) | (j8 >>> 63);
    }

    public static void multiplyX8(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr[2];
        long j5 = jArr[3];
        long j6 = jArr[4];
        long j7 = jArr[5];
        long j8 = jArr[6];
        long j9 = jArr[7];
        long j10 = j9 >>> 56;
        jArr2[0] = ((((j2 << 8) ^ j10) ^ (j10 << 2)) ^ (j10 << 5)) ^ (j10 << 8);
        jArr2[1] = (j3 << 8) | (j2 >>> 56);
        jArr2[2] = (j4 << 8) | (j3 >>> 56);
        jArr2[3] = (j5 << 8) | (j4 >>> 56);
        jArr2[4] = (j6 << 8) | (j5 >>> 56);
        jArr2[5] = (j7 << 8) | (j6 >>> 56);
        jArr2[6] = (j8 << 8) | (j7 >>> 56);
        jArr2[7] = (j9 << 8) | (j8 >>> 56);
    }

    public static void one(long[] jArr) {
        jArr[0] = 1;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
        jArr[4] = 0;
        jArr[5] = 0;
        jArr[6] = 0;
        jArr[7] = 0;
    }

    public static void square(long[] jArr, long[] jArr2) {
        int i2 = 16;
        long[] jArr3 = new long[16];
        for (int i3 = 0; i3 < 8; i3++) {
            Interleave.expand64To128(jArr[i3], jArr3, i3 << 1);
        }
        while (true) {
            i2--;
            if (i2 < 8) {
                copy(jArr3, jArr2);
                return;
            }
            long j2 = jArr3[i2];
            int i4 = i2 - 8;
            jArr3[i4] = jArr3[i4] ^ ((((j2 << 2) ^ j2) ^ (j2 << 5)) ^ (j2 << 8));
            int i5 = i4 + 1;
            jArr3[i5] = ((j2 >>> 56) ^ ((j2 >>> 62) ^ (j2 >>> 59))) ^ jArr3[i5];
        }
    }

    /* renamed from: x */
    public static void m1227x(long[] jArr) {
        jArr[0] = 2;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
        jArr[4] = 0;
        jArr[5] = 0;
        jArr[6] = 0;
        jArr[7] = 0;
    }

    public static void zero(long[] jArr) {
        jArr[0] = 0;
        jArr[1] = 0;
        jArr[2] = 0;
        jArr[3] = 0;
        jArr[4] = 0;
        jArr[5] = 0;
        jArr[6] = 0;
        jArr[7] = 0;
    }
}
