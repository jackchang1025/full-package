package org.bouncycastle.util;

import android.sun.security.util.DerValue;
import org.bouncycastle.asn1.cmc.BodyPartID;

/* loaded from: classes.dex */
public abstract class Pack {
    public static int bigEndianToInt(byte[] bArr, int i2) {
        int i3 = bArr[i2] << DerValue.tag_GeneralizedTime;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = i4 + 1;
        return (bArr[i6 + 1] & 255) | i5 | ((bArr[i6] & 255) << 8);
    }

    public static long bigEndianToLong(byte[] bArr, int i2) {
        int bigEndianToInt = bigEndianToInt(bArr, i2);
        return (bigEndianToInt(bArr, i2 + 4) & BodyPartID.bodyIdMax) | ((bigEndianToInt & BodyPartID.bodyIdMax) << 32);
    }

    public static short bigEndianToShort(byte[] bArr, int i2) {
        return (short) ((bArr[i2 + 1] & 255) | ((bArr[i2] & 255) << 8));
    }

    public static void intToBigEndian(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >>> 24);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 16);
        int i5 = i4 + 1;
        bArr[i5] = (byte) (i2 >>> 8);
        bArr[i5 + 1] = (byte) i2;
    }

    public static void intToLittleEndian(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i5 = i4 + 1;
        bArr[i5] = (byte) (i2 >>> 16);
        bArr[i5 + 1] = (byte) (i2 >>> 24);
    }

    public static int littleEndianToInt(byte[] bArr, int i2) {
        int i3 = bArr[i2] & 255;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 8);
        int i6 = i4 + 1;
        return (bArr[i6 + 1] << DerValue.tag_GeneralizedTime) | i5 | ((bArr[i6] & 255) << 16);
    }

    public static long littleEndianToLong(byte[] bArr, int i2) {
        return ((littleEndianToInt(bArr, i2 + 4) & BodyPartID.bodyIdMax) << 32) | (littleEndianToInt(bArr, i2) & BodyPartID.bodyIdMax);
    }

    public static short littleEndianToShort(byte[] bArr, int i2) {
        return (short) (((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255));
    }

    public static void longToBigEndian(long j2, byte[] bArr, int i2) {
        intToBigEndian((int) (j2 >>> 32), bArr, i2);
        intToBigEndian((int) (j2 & BodyPartID.bodyIdMax), bArr, i2 + 4);
    }

    public static void longToLittleEndian(long j2, byte[] bArr, int i2) {
        intToLittleEndian((int) (BodyPartID.bodyIdMax & j2), bArr, i2);
        intToLittleEndian((int) (j2 >>> 32), bArr, i2 + 4);
    }

    public static void shortToBigEndian(short s2, byte[] bArr, int i2) {
        bArr[i2] = (byte) (s2 >>> 8);
        bArr[i2 + 1] = (byte) s2;
    }

    public static void shortToLittleEndian(short s2, byte[] bArr, int i2) {
        bArr[i2] = (byte) s2;
        bArr[i2 + 1] = (byte) (s2 >>> 8);
    }

    public static void bigEndianToInt(byte[] bArr, int i2, int[] iArr) {
        for (int i3 = 0; i3 < iArr.length; i3++) {
            iArr[i3] = bigEndianToInt(bArr, i2);
            i2 += 4;
        }
    }

    public static void bigEndianToLong(byte[] bArr, int i2, long[] jArr) {
        for (int i3 = 0; i3 < jArr.length; i3++) {
            jArr[i3] = bigEndianToLong(bArr, i2);
            i2 += 8;
        }
    }

    public static void intToBigEndian(int[] iArr, int i2, int i3, byte[] bArr, int i4) {
        for (int i5 = 0; i5 < i3; i5++) {
            intToBigEndian(iArr[i2 + i5], bArr, i4);
            i4 += 4;
        }
    }

    public static void intToLittleEndian(int[] iArr, int i2, int i3, byte[] bArr, int i4) {
        for (int i5 = 0; i5 < i3; i5++) {
            intToLittleEndian(iArr[i2 + i5], bArr, i4);
            i4 += 4;
        }
    }

    public static void littleEndianToInt(byte[] bArr, int i2, int[] iArr) {
        for (int i3 = 0; i3 < iArr.length; i3++) {
            iArr[i3] = littleEndianToInt(bArr, i2);
            i2 += 4;
        }
    }

    public static void littleEndianToLong(byte[] bArr, int i2, long[] jArr) {
        for (int i3 = 0; i3 < jArr.length; i3++) {
            jArr[i3] = littleEndianToLong(bArr, i2);
            i2 += 8;
        }
    }

    public static void longToBigEndian(long j2, byte[] bArr, int i2, int i3) {
        for (int i4 = i3 - 1; i4 >= 0; i4--) {
            bArr[i4 + i2] = (byte) (255 & j2);
            j2 >>>= 8;
        }
    }

    public static void longToLittleEndian(long[] jArr, int i2, int i3, byte[] bArr, int i4) {
        for (int i5 = 0; i5 < i3; i5++) {
            longToLittleEndian(jArr[i2 + i5], bArr, i4);
            i4 += 8;
        }
    }

    public static byte[] shortToBigEndian(short s2) {
        byte[] bArr = new byte[2];
        shortToBigEndian(s2, bArr, 0);
        return bArr;
    }

    public static byte[] shortToLittleEndian(short s2) {
        byte[] bArr = new byte[2];
        shortToLittleEndian(s2, bArr, 0);
        return bArr;
    }

    public static void bigEndianToInt(byte[] bArr, int i2, int[] iArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            iArr[i3 + i5] = bigEndianToInt(bArr, i2);
            i2 += 4;
        }
    }

    public static void bigEndianToLong(byte[] bArr, int i2, long[] jArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            jArr[i3 + i5] = bigEndianToLong(bArr, i2);
            i2 += 8;
        }
    }

    public static void intToBigEndian(int[] iArr, byte[] bArr, int i2) {
        for (int i3 : iArr) {
            intToBigEndian(i3, bArr, i2);
            i2 += 4;
        }
    }

    public static void intToLittleEndian(int[] iArr, byte[] bArr, int i2) {
        for (int i3 : iArr) {
            intToLittleEndian(i3, bArr, i2);
            i2 += 4;
        }
    }

    public static void littleEndianToInt(byte[] bArr, int i2, int[] iArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            iArr[i3 + i5] = littleEndianToInt(bArr, i2);
            i2 += 4;
        }
    }

    public static void littleEndianToLong(byte[] bArr, int i2, long[] jArr, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            jArr[i3 + i5] = littleEndianToLong(bArr, i2);
            i2 += 8;
        }
    }

    public static void longToBigEndian(long[] jArr, int i2, int i3, byte[] bArr, int i4) {
        for (int i5 = 0; i5 < i3; i5++) {
            longToBigEndian(jArr[i2 + i5], bArr, i4);
            i4 += 8;
        }
    }

    public static void longToLittleEndian(long[] jArr, byte[] bArr, int i2) {
        for (long j2 : jArr) {
            longToLittleEndian(j2, bArr, i2);
            i2 += 8;
        }
    }

    public static byte[] intToBigEndian(int i2) {
        byte[] bArr = new byte[4];
        intToBigEndian(i2, bArr, 0);
        return bArr;
    }

    public static byte[] intToLittleEndian(int i2) {
        byte[] bArr = new byte[4];
        intToLittleEndian(i2, bArr, 0);
        return bArr;
    }

    public static int[] littleEndianToInt(byte[] bArr, int i2, int i3) {
        int[] iArr = new int[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            iArr[i4] = littleEndianToInt(bArr, i2);
            i2 += 4;
        }
        return iArr;
    }

    public static void longToBigEndian(long[] jArr, byte[] bArr, int i2) {
        for (long j2 : jArr) {
            longToBigEndian(j2, bArr, i2);
            i2 += 8;
        }
    }

    public static byte[] longToLittleEndian(long j2) {
        byte[] bArr = new byte[8];
        longToLittleEndian(j2, bArr, 0);
        return bArr;
    }

    public static byte[] intToBigEndian(int[] iArr) {
        byte[] bArr = new byte[iArr.length * 4];
        intToBigEndian(iArr, bArr, 0);
        return bArr;
    }

    public static byte[] intToLittleEndian(int[] iArr) {
        byte[] bArr = new byte[iArr.length * 4];
        intToLittleEndian(iArr, bArr, 0);
        return bArr;
    }

    public static byte[] longToBigEndian(long j2) {
        byte[] bArr = new byte[8];
        longToBigEndian(j2, bArr, 0);
        return bArr;
    }

    public static byte[] longToLittleEndian(long[] jArr) {
        byte[] bArr = new byte[jArr.length * 8];
        longToLittleEndian(jArr, bArr, 0);
        return bArr;
    }

    public static byte[] longToBigEndian(long[] jArr) {
        byte[] bArr = new byte[jArr.length * 8];
        longToBigEndian(jArr, bArr, 0);
        return bArr;
    }
}
