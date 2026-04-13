package org.bouncycastle.math.raw;

import android.support.v4.view.MotionEventCompat;

/* loaded from: classes.dex */
public class Interleave {
    private static final long M32 = 1431655765;
    private static final long M64 = 6148914691236517205L;
    private static final long M64R = -6148914691236517206L;

    public static int expand16to32(int i2) {
        int i3 = i2 & 65535;
        int i4 = (i3 | (i3 << 8)) & 16711935;
        int i5 = (i4 | (i4 << 4)) & 252645135;
        int i6 = (i5 | (i5 << 2)) & 858993459;
        return (i6 | (i6 << 1)) & 1431655765;
    }

    public static long expand32to64(int i2) {
        return (((r6 >>> 1) & M32) << 32) | (M32 & Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(i2, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 8), 15728880, 4), 202116108, 2), 572662306, 1));
    }

    public static void expand64To128(long j2, long[] jArr, int i2) {
        long bitPermuteStep = Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 4294901760L, 16), 280375465148160L, 8), 67555025218437360L, 4), 868082074056920076L, 2), 2459565876494606882L, 1);
        jArr[i2] = bitPermuteStep & M64;
        jArr[i2 + 1] = (bitPermuteStep >>> 1) & M64;
    }

    public static void expand64To128Rev(long j2, long[] jArr, int i2) {
        long bitPermuteStep = Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 4294901760L, 16), 280375465148160L, 8), 67555025218437360L, 4), 868082074056920076L, 2), 2459565876494606882L, 1);
        jArr[i2] = bitPermuteStep & M64R;
        jArr[i2 + 1] = (bitPermuteStep << 1) & M64R;
    }

    public static int expand8to16(int i2) {
        int i3 = i2 & 255;
        int i4 = (i3 | (i3 << 4)) & 3855;
        int i5 = (i4 | (i4 << 2)) & 13107;
        return (i5 | (i5 << 1)) & 21845;
    }

    public static int shuffle(int i2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(i2, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 8), 15728880, 4), 202116108, 2), 572662306, 1);
    }

    public static int shuffle2(int i2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(i2, 11141290, 7), 52428, 14), 15728880, 4), MotionEventCompat.ACTION_POINTER_INDEX_MASK, 8);
    }

    public static long shuffle3(long j2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 47851476196393130L, 7), 225176545447116L, 14), 4042322160L, 28);
    }

    public static int unshuffle(int i2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(i2, 572662306, 1), 202116108, 2), 15728880, 4), MotionEventCompat.ACTION_POINTER_INDEX_MASK, 8);
    }

    public static int unshuffle2(int i2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(i2, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 8), 15728880, 4), 52428, 14), 11141290, 7);
    }

    public static long unshuffle3(long j2) {
        return shuffle3(j2);
    }

    public static void expand64To128(long[] jArr, int i2, int i3, long[] jArr2, int i4) {
        for (int i5 = 0; i5 < i3; i5++) {
            expand64To128(jArr[i2 + i5], jArr2, i4);
            i4 += 2;
        }
    }

    public static long shuffle(long j2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 4294901760L, 16), 280375465148160L, 8), 67555025218437360L, 4), 868082074056920076L, 2), 2459565876494606882L, 1);
    }

    public static long shuffle2(long j2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 4278255360L, 24), 57421771435671756L, 6), 264913582878960L, 12), 723401728380766730L, 3);
    }

    public static long unshuffle(long j2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 2459565876494606882L, 1), 868082074056920076L, 2), 67555025218437360L, 4), 280375465148160L, 8), 4294901760L, 16);
    }

    public static long unshuffle2(long j2) {
        return Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(Bits.bitPermuteStep(j2, 723401728380766730L, 3), 264913582878960L, 12), 57421771435671756L, 6), 4278255360L, 24);
    }
}
