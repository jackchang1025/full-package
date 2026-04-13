package org.bouncycastle.math.raw;

/* loaded from: classes.dex */
public abstract class Mont256 {

    /* renamed from: M */
    private static final long f1515M = 4294967295L;

    public static int inverse32(int i2) {
        int i3 = (2 - (i2 * i2)) * i2;
        int i4 = (2 - (i2 * i3)) * i3;
        int i5 = (2 - (i2 * i4)) * i4;
        return (2 - (i2 * i5)) * i5;
    }

    public static void multAdd(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i2) {
        char c = 0;
        long j2 = iArr2[0] & 4294967295L;
        int i3 = 0;
        int i4 = 0;
        while (i3 < 8) {
            long j3 = iArr[i3] & 4294967295L;
            long j4 = j3 * j2;
            long j5 = j2;
            long j6 = (((int) r10) * i2) & 4294967295L;
            int i5 = i3;
            int i6 = i4;
            long j7 = (iArr4[c] & 4294967295L) * j6;
            char c2 = ' ';
            long j8 = ((((j4 & 4294967295L) + (iArr3[c] & 4294967295L)) + (j7 & 4294967295L)) >>> 32) + (j4 >>> 32) + (j7 >>> 32);
            int i7 = 1;
            while (i7 < 8) {
                long j9 = (iArr2[i7] & 4294967295L) * j3;
                long j10 = (iArr4[i7] & 4294967295L) * j6;
                long j11 = (j9 & 4294967295L) + (j10 & 4294967295L) + (iArr3[i7] & 4294967295L) + j8;
                iArr3[i7 - 1] = (int) j11;
                j8 = (j11 >>> 32) + (j9 >>> 32) + (j10 >>> 32);
                i7++;
                c2 = ' ';
                j6 = j6;
            }
            long j12 = j8 + (i6 & 4294967295L);
            iArr3[7] = (int) j12;
            i4 = (int) (j12 >>> c2);
            i3 = i5 + 1;
            j2 = j5;
            c = 0;
        }
        if (i4 != 0 || Nat256.gte(iArr3, iArr4)) {
            Nat256.sub(iArr3, iArr4, iArr3);
        }
    }

    public static void multAddXF(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        char c = 0;
        long j2 = iArr2[0] & 4294967295L;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= 8) {
                break;
            }
            long j3 = iArr[i2] & 4294967295L;
            long j4 = (j3 * j2) + (iArr3[c] & 4294967295L);
            long j5 = j4 & 4294967295L;
            long j6 = (j4 >>> 32) + j5;
            int i4 = 1;
            for (int i5 = 8; i4 < i5; i5 = 8) {
                long j7 = j2;
                long j8 = (iArr2[i4] & 4294967295L) * j3;
                long j9 = (iArr4[i4] & 4294967295L) * j5;
                long j10 = (j8 & 4294967295L) + (j9 & 4294967295L) + (iArr3[i4] & 4294967295L) + j6;
                iArr3[i4 - 1] = (int) j10;
                j6 = (j10 >>> 32) + (j8 >>> 32) + (j9 >>> 32);
                i4++;
                j2 = j7;
                j3 = j3;
                j5 = j5;
            }
            long j11 = j6 + (i3 & 4294967295L);
            iArr3[7] = (int) j11;
            i3 = (int) (j11 >>> 32);
            i2++;
            j2 = j2;
            c = 0;
        }
        if (i3 != 0 || Nat256.gte(iArr3, iArr4)) {
            Nat256.sub(iArr3, iArr4, iArr3);
        }
    }

    public static void reduce(int[] iArr, int[] iArr2, int i2) {
        char c = 0;
        int i3 = 0;
        while (i3 < 8) {
            long j2 = (r5 * i2) & 4294967295L;
            long j3 = (((iArr2[c] & 4294967295L) * j2) + (iArr[c] & 4294967295L)) >>> 32;
            int i4 = 1;
            while (i4 < 8) {
                long j4 = ((iArr2[i4] & 4294967295L) * j2) + (iArr[i4] & 4294967295L) + j3;
                iArr[i4 - 1] = (int) j4;
                j3 = j4 >>> 32;
                i4++;
                i3 = i3;
            }
            iArr[7] = (int) j3;
            i3++;
            c = 0;
        }
        if (Nat256.gte(iArr, iArr2)) {
            Nat256.sub(iArr, iArr2, iArr);
        }
    }

    public static void reduceXF(int[] iArr, int[] iArr2) {
        for (int i2 = 0; i2 < 8; i2++) {
            long j2 = iArr[0] & 4294967295L;
            long j3 = j2;
            for (int i3 = 1; i3 < 8; i3++) {
                long j4 = ((iArr2[i3] & 4294967295L) * j2) + (iArr[i3] & 4294967295L) + j3;
                iArr[i3 - 1] = (int) j4;
                j3 = j4 >>> 32;
            }
            iArr[7] = (int) j3;
        }
        if (Nat256.gte(iArr, iArr2)) {
            Nat256.sub(iArr, iArr2, iArr);
        }
    }
}
