package org.bouncycastle.crypto.modes.kgcm;

import org.bouncycastle.math.raw.Interleave;

/* loaded from: classes.dex */
public class KGCMUtil_128 {
    public static final int SIZE = 2;

    public static void add(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr2[1] ^ jArr[1];
    }

    public static void copy(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
    }

    public static boolean equal(long[] jArr, long[] jArr2) {
        return ((jArr2[1] ^ jArr[1]) | ((jArr[0] ^ jArr2[0]) | 0)) == 0;
    }

    public static void multiply(long[] jArr, long[] jArr2, long[] jArr3) {
        int i2 = 0;
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr2[0];
        long j5 = jArr2[1];
        long j6 = 0;
        long j7 = 0;
        long j8 = 0;
        while (i2 < 64) {
            long j9 = j7;
            long j10 = -(j2 & 1);
            j2 >>>= 1;
            j6 ^= j4 & j10;
            long j11 = (j10 & j5) ^ j8;
            long j12 = -(j3 & 1);
            j3 >>>= 1;
            long j13 = j11 ^ (j4 & j12);
            long j14 = j9 ^ (j12 & j5);
            long j15 = j5 >> 63;
            j5 = (j5 << 1) | (j4 >>> 63);
            j4 = (j4 << 1) ^ (j15 & 135);
            i2++;
            j7 = j14;
            j8 = j13;
        }
        long j16 = j7;
        jArr3[0] = (((j16 ^ (j16 << 1)) ^ (j16 << 2)) ^ (j16 << 7)) ^ j6;
        jArr3[1] = (((j16 >>> 63) ^ (j16 >>> 62)) ^ (j16 >>> 57)) ^ j8;
    }

    public static void multiplyX(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        jArr2[0] = ((j3 >> 63) & 135) ^ (j2 << 1);
        jArr2[1] = (j2 >>> 63) | (j3 << 1);
    }

    public static void multiplyX8(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j3 >>> 56;
        jArr2[0] = (j4 << 7) ^ ((((j2 << 8) ^ j4) ^ (j4 << 1)) ^ (j4 << 2));
        jArr2[1] = (j2 >>> 56) | (j3 << 8);
    }

    public static void one(long[] jArr) {
        jArr[0] = 1;
        jArr[1] = 0;
    }

    public static void square(long[] jArr, long[] jArr2) {
        long[] jArr3 = new long[4];
        Interleave.expand64To128(jArr[0], jArr3, 0);
        Interleave.expand64To128(jArr[1], jArr3, 2);
        long j2 = jArr3[0];
        long j3 = jArr3[1];
        long j4 = jArr3[2];
        long j5 = jArr3[3];
        long j6 = j4 ^ ((j5 >>> 57) ^ ((j5 >>> 63) ^ (j5 >>> 62)));
        jArr2[0] = j2 ^ ((((j6 << 1) ^ j6) ^ (j6 << 2)) ^ (j6 << 7));
        jArr2[1] = (j3 ^ ((((j5 << 1) ^ j5) ^ (j5 << 2)) ^ (j5 << 7))) ^ ((j6 >>> 57) ^ ((j6 >>> 63) ^ (j6 >>> 62)));
    }

    /* renamed from: x */
    public static void m1225x(long[] jArr) {
        jArr[0] = 2;
        jArr[1] = 0;
    }

    public static void zero(long[] jArr) {
        jArr[0] = 0;
        jArr[1] = 0;
    }
}
