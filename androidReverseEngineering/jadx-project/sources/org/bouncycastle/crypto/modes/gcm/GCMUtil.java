package org.bouncycastle.crypto.modes.gcm;

import android.sun.security.util.DerValue;
import org.bouncycastle.math.raw.Interleave;
import org.bouncycastle.util.Longs;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public abstract class GCMUtil {
    private static final int E1 = -520093696;
    private static final long E1L = -2233785415175766016L;
    public static final int SIZE_BYTES = 16;
    public static final int SIZE_INTS = 4;
    public static final int SIZE_LONGS = 2;

    public static byte areEqual(byte[] bArr, byte[] bArr2) {
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            i2 |= bArr[i3] ^ bArr2[i3];
        }
        return (byte) ((((i2 >>> 1) | (i2 & 1)) - 1) >> 31);
    }

    public static void asBytes(int[] iArr, byte[] bArr) {
        Pack.intToBigEndian(iArr, 0, 4, bArr, 0);
    }

    public static void asInts(byte[] bArr, int[] iArr) {
        Pack.bigEndianToInt(bArr, 0, iArr, 0, 4);
    }

    public static void asLongs(byte[] bArr, long[] jArr) {
        Pack.bigEndianToLong(bArr, 0, jArr, 0, 2);
    }

    public static void copy(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < 16; i2++) {
            bArr2[i2] = bArr[i2];
        }
    }

    public static void divideP(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j2 >> 63;
        jArr2[0] = ((j2 ^ (E1L & j4)) << 1) | (j3 >>> 63);
        jArr2[1] = (j3 << 1) | (-j4);
    }

    private static long implMul64(long j2, long j3) {
        long j4 = j2 & 1229782938247303441L;
        long j5 = j2 & 2459565876494606882L;
        long j6 = j2 & 4919131752989213764L;
        long j7 = j2 & (-8608480567731124088L);
        long j8 = j3 & 1229782938247303441L;
        long j9 = j3 & 2459565876494606882L;
        long j10 = j3 & 4919131752989213764L;
        long j11 = j3 & (-8608480567731124088L);
        long j12 = (((j4 * j8) ^ (j5 * j11)) ^ (j6 * j10)) ^ (j7 * j9);
        long j13 = (((j4 * j9) ^ (j5 * j8)) ^ (j6 * j11)) ^ (j7 * j10);
        long j14 = (((j4 * j10) ^ (j5 * j9)) ^ (j6 * j8)) ^ (j7 * j11);
        return (j12 & 1229782938247303441L) | (j13 & 2459565876494606882L) | (j14 & 4919131752989213764L) | (((((j4 * j11) ^ (j5 * j10)) ^ (j6 * j9)) ^ (j7 * j8)) & (-8608480567731124088L));
    }

    public static void multiply(byte[] bArr, byte[] bArr2) {
        long[] asLongs = asLongs(bArr);
        multiply(asLongs, asLongs(bArr2));
        asBytes(asLongs, bArr);
    }

    public static void multiplyP(int[] iArr) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        iArr[0] = (((i5 << 31) >> 31) & E1) ^ (i2 >>> 1);
        iArr[1] = (i3 >>> 1) | (i2 << 31);
        iArr[2] = (i4 >>> 1) | (i3 << 31);
        iArr[3] = (i5 >>> 1) | (i4 << 31);
    }

    public static void multiplyP3(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j3 << 61;
        jArr2[0] = (j4 >>> 7) ^ ((((j2 >>> 3) ^ j4) ^ (j4 >>> 1)) ^ (j4 >>> 2));
        jArr2[1] = (j2 << 61) | (j3 >>> 3);
    }

    public static void multiplyP4(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j3 << 60;
        jArr2[0] = (j4 >>> 7) ^ ((((j2 >>> 4) ^ j4) ^ (j4 >>> 1)) ^ (j4 >>> 2));
        jArr2[1] = (j2 << 60) | (j3 >>> 4);
    }

    public static void multiplyP7(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j3 << 57;
        jArr2[0] = (j4 >>> 7) ^ ((((j2 >>> 7) ^ j4) ^ (j4 >>> 1)) ^ (j4 >>> 2));
        jArr2[1] = (j2 << 57) | (j3 >>> 7);
    }

    public static void multiplyP8(int[] iArr) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        int i6 = i5 << 24;
        iArr[0] = (i6 >>> 7) ^ ((((i2 >>> 8) ^ i6) ^ (i6 >>> 1)) ^ (i6 >>> 2));
        iArr[1] = (i3 >>> 8) | (i2 << 24);
        iArr[2] = (i4 >>> 8) | (i3 << 24);
        iArr[3] = (i5 >>> 8) | (i4 << 24);
    }

    public static byte[] oneAsBytes() {
        byte[] bArr = new byte[16];
        bArr[0] = DerValue.TAG_CONTEXT;
        return bArr;
    }

    public static int[] oneAsInts() {
        int[] iArr = new int[4];
        iArr[0] = Integer.MIN_VALUE;
        return iArr;
    }

    public static long[] oneAsLongs() {
        return new long[]{Long.MIN_VALUE, 0};
    }

    public static long[] pAsLongs() {
        return new long[]{4611686018427387904L, 0};
    }

    public static void square(long[] jArr, long[] jArr2) {
        long[] jArr3 = new long[4];
        Interleave.expand64To128Rev(jArr[0], jArr3, 0);
        Interleave.expand64To128Rev(jArr[1], jArr3, 2);
        long j2 = jArr3[0];
        long j3 = jArr3[1];
        long j4 = jArr3[2];
        long j5 = jArr3[3];
        long j6 = j4 ^ ((j5 << 57) ^ ((j5 << 63) ^ (j5 << 62)));
        jArr2[0] = j2 ^ ((((j6 >>> 1) ^ j6) ^ (j6 >>> 2)) ^ (j6 >>> 7));
        jArr2[1] = (j3 ^ ((((j5 >>> 1) ^ j5) ^ (j5 >>> 2)) ^ (j5 >>> 7))) ^ ((j6 << 57) ^ ((j6 << 63) ^ (j6 << 62)));
    }

    public static void xor(byte[] bArr, int i2, byte[] bArr2, int i3, int i4) {
        while (true) {
            i4--;
            if (i4 < 0) {
                return;
            }
            int i5 = i2 + i4;
            bArr[i5] = (byte) (bArr[i5] ^ bArr2[i3 + i4]);
        }
    }

    public static int areEqual(int[] iArr, int[] iArr2) {
        int i2 = (iArr[3] ^ iArr2[3]) | 0 | (iArr[0] ^ iArr2[0]) | (iArr[1] ^ iArr2[1]) | (iArr2[2] ^ iArr[2]);
        return (((i2 & 1) | (i2 >>> 1)) - 1) >> 31;
    }

    public static void asBytes(long[] jArr, byte[] bArr) {
        Pack.longToBigEndian(jArr, 0, 2, bArr, 0);
    }

    public static int[] asInts(byte[] bArr) {
        int[] iArr = new int[4];
        Pack.bigEndianToInt(bArr, 0, iArr, 0, 4);
        return iArr;
    }

    public static long[] asLongs(byte[] bArr) {
        long[] jArr = new long[2];
        Pack.bigEndianToLong(bArr, 0, jArr, 0, 2);
        return jArr;
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
    }

    public static void multiply(int[] iArr, int[] iArr2) {
        int i2 = iArr2[0];
        int i3 = iArr2[1];
        int i4 = iArr2[2];
        int i5 = iArr2[3];
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < 4; i10++) {
            int i11 = iArr[i10];
            for (int i12 = 0; i12 < 32; i12++) {
                int i13 = i11 >> 31;
                i11 <<= 1;
                i6 ^= i2 & i13;
                i7 ^= i3 & i13;
                i8 ^= i4 & i13;
                i9 ^= i13 & i5;
                int i14 = (i5 << 31) >> 8;
                i5 = (i5 >>> 1) | (i4 << 31);
                i4 = (i4 >>> 1) | (i3 << 31);
                i3 = (i3 >>> 1) | (i2 << 31);
                i2 = (i2 >>> 1) ^ (i14 & E1);
            }
        }
        iArr[0] = i6;
        iArr[1] = i7;
        iArr[2] = i8;
        iArr[3] = i9;
    }

    public static void multiplyP(int[] iArr, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        iArr2[0] = (((i5 << 31) >> 31) & E1) ^ (i2 >>> 1);
        iArr2[1] = (i3 >>> 1) | (i2 << 31);
        iArr2[2] = (i4 >>> 1) | (i3 << 31);
        iArr2[3] = (i5 >>> 1) | (i4 << 31);
    }

    public static void multiplyP8(int[] iArr, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        int i6 = i5 << 24;
        iArr2[0] = (i6 >>> 7) ^ ((((i2 >>> 8) ^ i6) ^ (i6 >>> 1)) ^ (i6 >>> 2));
        iArr2[1] = (i3 >>> 8) | (i2 << 24);
        iArr2[2] = (i4 >>> 8) | (i3 << 24);
        iArr2[3] = (i5 >>> 8) | (i4 << 24);
    }

    public static void xor(byte[] bArr, int i2, byte[] bArr2, int i3, byte[] bArr3, int i4) {
        int i5 = 0;
        do {
            bArr3[i4 + i5] = (byte) (bArr[i2 + i5] ^ bArr2[i3 + i5]);
            int i6 = i5 + 1;
            bArr3[i4 + i6] = (byte) (bArr[i2 + i6] ^ bArr2[i3 + i6]);
            int i7 = i6 + 1;
            bArr3[i4 + i7] = (byte) (bArr[i2 + i7] ^ bArr2[i3 + i7]);
            int i8 = i7 + 1;
            bArr3[i4 + i8] = (byte) (bArr[i2 + i8] ^ bArr2[i3 + i8]);
            i5 = i8 + 1;
        } while (i5 < 16);
    }

    public static long areEqual(long[] jArr, long[] jArr2) {
        long j2 = (jArr2[1] ^ jArr[1]) | (jArr[0] ^ jArr2[0]) | 0;
        return (((j2 & 1) | (j2 >>> 1)) - 1) >> 63;
    }

    public static byte[] asBytes(int[] iArr) {
        byte[] bArr = new byte[16];
        Pack.intToBigEndian(iArr, 0, 4, bArr, 0);
        return bArr;
    }

    public static void copy(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
    }

    public static void multiply(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = jArr2[0];
        long j5 = jArr2[1];
        long reverse = Longs.reverse(j2);
        long reverse2 = Longs.reverse(j3);
        long reverse3 = Longs.reverse(j4);
        long reverse4 = Longs.reverse(j5);
        long reverse5 = Longs.reverse(implMul64(reverse, reverse3));
        long implMul64 = implMul64(j2, j4) << 1;
        long reverse6 = Longs.reverse(implMul64(reverse2, reverse4));
        long implMul642 = implMul64(j3, j5) << 1;
        long reverse7 = Longs.reverse(implMul64(reverse ^ reverse2, reverse3 ^ reverse4));
        long implMul643 = ((implMul64(j2 ^ j3, j4 ^ j5) << 1) ^ ((reverse6 ^ implMul64) ^ implMul642)) ^ ((implMul642 << 62) ^ (implMul642 << 57));
        jArr[0] = reverse5 ^ ((implMul643 >>> 7) ^ ((implMul643 ^ (implMul643 >>> 1)) ^ (implMul643 >>> 2)));
        jArr[1] = ((implMul643 << 57) ^ ((implMul643 << 63) ^ (implMul643 << 62))) ^ ((reverse7 ^ ((implMul64 ^ reverse5) ^ reverse6)) ^ (((implMul642 ^ (implMul642 >>> 1)) ^ (implMul642 >>> 2)) ^ (implMul642 >>> 7)));
    }

    public static void multiplyP(long[] jArr) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        jArr[0] = (((j3 << 63) >> 63) & E1L) ^ (j2 >>> 1);
        jArr[1] = (j2 << 63) | (j3 >>> 1);
    }

    public static void multiplyP8(long[] jArr) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j3 << 56;
        jArr[0] = (j4 >>> 7) ^ ((((j2 >>> 8) ^ j4) ^ (j4 >>> 1)) ^ (j4 >>> 2));
        jArr[1] = (j2 << 56) | (j3 >>> 8);
    }

    public static void xor(byte[] bArr, byte[] bArr2) {
        int i2 = 0;
        do {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
            int i3 = i2 + 1;
            bArr[i3] = (byte) (bArr[i3] ^ bArr2[i3]);
            int i4 = i3 + 1;
            bArr[i4] = (byte) (bArr[i4] ^ bArr2[i4]);
            int i5 = i4 + 1;
            bArr[i5] = (byte) (bArr[i5] ^ bArr2[i5]);
            i2 = i5 + 1;
        } while (i2 < 16);
    }

    public static byte[] asBytes(long[] jArr) {
        byte[] bArr = new byte[16];
        Pack.longToBigEndian(jArr, 0, 2, bArr, 0);
        return bArr;
    }

    public static void multiplyP(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        jArr2[0] = (((j3 << 63) >> 63) & E1L) ^ (j2 >>> 1);
        jArr2[1] = (j2 << 63) | (j3 >>> 1);
    }

    public static void multiplyP8(long[] jArr, long[] jArr2) {
        long j2 = jArr[0];
        long j3 = jArr[1];
        long j4 = j3 << 56;
        jArr2[0] = (j4 >>> 7) ^ ((((j2 >>> 8) ^ j4) ^ (j4 >>> 1)) ^ (j4 >>> 2));
        jArr2[1] = (j2 << 56) | (j3 >>> 8);
    }

    public static void xor(byte[] bArr, byte[] bArr2, int i2) {
        int i3 = 0;
        do {
            bArr[i3] = (byte) (bArr[i3] ^ bArr2[i2 + i3]);
            int i4 = i3 + 1;
            bArr[i4] = (byte) (bArr[i4] ^ bArr2[i2 + i4]);
            int i5 = i4 + 1;
            bArr[i5] = (byte) (bArr[i5] ^ bArr2[i2 + i5]);
            int i6 = i5 + 1;
            bArr[i6] = (byte) (bArr[i6] ^ bArr2[i2 + i6]);
            i3 = i6 + 1;
        } while (i3 < 16);
    }

    public static void xor(byte[] bArr, byte[] bArr2, int i2, int i3) {
        while (true) {
            i3--;
            if (i3 < 0) {
                return;
            } else {
                bArr[i3] = (byte) (bArr[i3] ^ bArr2[i2 + i3]);
            }
        }
    }

    public static void xor(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int i2 = 0;
        do {
            bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
            int i3 = i2 + 1;
            bArr3[i3] = (byte) (bArr[i3] ^ bArr2[i3]);
            int i4 = i3 + 1;
            bArr3[i4] = (byte) (bArr[i4] ^ bArr2[i4]);
            int i5 = i4 + 1;
            bArr3[i5] = (byte) (bArr[i5] ^ bArr2[i5]);
            i2 = i5 + 1;
        } while (i2 < 16);
    }

    public static void xor(int[] iArr, int[] iArr2) {
        iArr[0] = iArr[0] ^ iArr2[0];
        iArr[1] = iArr[1] ^ iArr2[1];
        iArr[2] = iArr[2] ^ iArr2[2];
        iArr[3] = iArr2[3] ^ iArr[3];
    }

    public static void xor(int[] iArr, int[] iArr2, int[] iArr3) {
        iArr3[0] = iArr[0] ^ iArr2[0];
        iArr3[1] = iArr[1] ^ iArr2[1];
        iArr3[2] = iArr[2] ^ iArr2[2];
        iArr3[3] = iArr[3] ^ iArr2[3];
    }

    public static void xor(long[] jArr, long[] jArr2) {
        jArr[0] = jArr[0] ^ jArr2[0];
        jArr[1] = jArr[1] ^ jArr2[1];
    }

    public static void xor(long[] jArr, long[] jArr2, long[] jArr3) {
        jArr3[0] = jArr[0] ^ jArr2[0];
        jArr3[1] = jArr2[1] ^ jArr[1];
    }
}
