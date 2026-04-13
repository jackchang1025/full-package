package org.bouncycastle.pqc.math.linearalgebra;

/* loaded from: classes.dex */
public final class BigEndianConversions {
    private BigEndianConversions() {
    }

    public static void I2OSP(int i2, byte[] bArr, int i3) {
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 24);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 16);
        bArr[i5] = (byte) (i2 >>> 8);
        bArr[i5 + 1] = (byte) i2;
    }

    public static int OS2IP(byte[] bArr) {
        if (bArr.length > 4) {
            throw new ArithmeticException("invalid input length");
        }
        if (bArr.length == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i2 |= (bArr[i3] & 255) << (((bArr.length - 1) - i3) * 8);
        }
        return i2;
    }

    public static long OS2LIP(byte[] bArr, int i2) {
        long j2 = (bArr[i2] & 255) << 56;
        int i3 = i2 + 1 + 1 + 1;
        long j3 = j2 | ((bArr[r0] & 255) << 48) | ((bArr[r8] & 255) << 40);
        long j4 = j3 | ((bArr[i3] & 255) << 32);
        long j5 = j4 | ((255 & bArr[r8]) << 24);
        long j6 = j5 | ((bArr[r2] & 255) << 16);
        int i4 = i3 + 1 + 1 + 1 + 1;
        return (bArr[i4] & 255) | j6 | ((bArr[r8] & 255) << 8);
    }

    public static byte[] toByteArray(int[] iArr) {
        byte[] bArr = new byte[iArr.length << 2];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            I2OSP(iArr[i2], bArr, i2 << 2);
        }
        return bArr;
    }

    public static int[] toIntArray(byte[] bArr) {
        int length = (bArr.length + 3) / 4;
        int length2 = bArr.length & 3;
        int[] iArr = new int[length];
        int i2 = 0;
        int i3 = 0;
        while (i2 <= length - 2) {
            iArr[i2] = OS2IP(bArr, i3);
            i2++;
            i3 += 4;
        }
        int i4 = length - 1;
        if (length2 != 0) {
            iArr[i4] = OS2IP(bArr, i3, length2);
        } else {
            iArr[i4] = OS2IP(bArr, i3);
        }
        return iArr;
    }

    public static void I2OSP(int i2, byte[] bArr, int i3, int i4) {
        int i5 = i4 - 1;
        for (int i6 = i5; i6 >= 0; i6--) {
            bArr[i3 + i6] = (byte) (i2 >>> ((i5 - i6) * 8));
        }
    }

    public static int OS2IP(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i2] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        return (bArr[i6] & 255) | i5 | ((bArr[i4] & 255) << 8);
    }

    public static byte[] toByteArray(int[] iArr, int i2) {
        int length = iArr.length;
        byte[] bArr = new byte[i2];
        int i3 = 0;
        int i4 = 0;
        while (i3 <= length - 2) {
            I2OSP(iArr[i3], bArr, i4);
            i3++;
            i4 += 4;
        }
        I2OSP(iArr[length - 1], bArr, i4, i2 - i4);
        return bArr;
    }

    public static void I2OSP(long j2, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) (j2 >>> 56);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (j2 >>> 48);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (j2 >>> 40);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (j2 >>> 32);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (j2 >>> 24);
        int i8 = i7 + 1;
        bArr[i7] = (byte) (j2 >>> 16);
        bArr[i8] = (byte) (j2 >>> 8);
        bArr[i8 + 1] = (byte) j2;
    }

    public static int OS2IP(byte[] bArr, int i2, int i3) {
        if (bArr.length == 0 || bArr.length < (i2 + i3) - 1) {
            return 0;
        }
        int i4 = 0;
        for (int i5 = 0; i5 < i3; i5++) {
            i4 |= (bArr[i2 + i5] & 255) << (((i3 - i5) - 1) * 8);
        }
        return i4;
    }

    public static byte[] I2OSP(int i2) {
        return new byte[]{(byte) (i2 >>> 24), (byte) (i2 >>> 16), (byte) (i2 >>> 8), (byte) i2};
    }

    public static byte[] I2OSP(int i2, int i3) {
        if (i2 < 0) {
            return null;
        }
        int ceilLog256 = IntegerFunctions.ceilLog256(i2);
        if (ceilLog256 > i3) {
            throw new ArithmeticException("Cannot encode given integer into specified number of octets.");
        }
        byte[] bArr = new byte[i3];
        int i4 = i3 - 1;
        for (int i5 = i4; i5 >= i3 - ceilLog256; i5--) {
            bArr[i5] = (byte) (i2 >>> ((i4 - i5) * 8));
        }
        return bArr;
    }

    public static byte[] I2OSP(long j2) {
        return new byte[]{(byte) (j2 >>> 56), (byte) (j2 >>> 48), (byte) (j2 >>> 40), (byte) (j2 >>> 32), (byte) (j2 >>> 24), (byte) (j2 >>> 16), (byte) (j2 >>> 8), (byte) j2};
    }
}
