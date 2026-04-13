package org.bouncycastle.math.raw;

/* loaded from: classes.dex */
public abstract class Bits {
    public static int bitPermuteStep(int i2, int i3, int i4) {
        int i5 = i3 & ((i2 >>> i4) ^ i2);
        return i2 ^ (i5 ^ (i5 << i4));
    }

    public static int bitPermuteStepSimple(int i2, int i3, int i4) {
        return ((i2 >>> i4) & i3) | ((i2 & i3) << i4);
    }

    public static long bitPermuteStep(long j2, long j3, int i2) {
        long j4 = j3 & ((j2 >>> i2) ^ j2);
        return j2 ^ (j4 ^ (j4 << i2));
    }

    public static long bitPermuteStepSimple(long j2, long j3, int i2) {
        return ((j2 >>> i2) & j3) | ((j2 & j3) << i2);
    }
}
