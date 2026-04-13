package org.bouncycastle.crypto.digests;

import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class XofUtils {
    public static byte[] encode(byte b) {
        return Arrays.concatenate(leftEncode(8L), new byte[]{b});
    }

    public static byte[] leftEncode(long j2) {
        long j3 = j2;
        byte b = 1;
        while (true) {
            j3 >>= 8;
            if (j3 == 0) {
                break;
            }
            b = (byte) (b + 1);
        }
        byte[] bArr = new byte[b + 1];
        bArr[0] = b;
        for (int i2 = 1; i2 <= b; i2++) {
            bArr[i2] = (byte) (j2 >> ((b - i2) * 8));
        }
        return bArr;
    }

    public static byte[] rightEncode(long j2) {
        long j3 = j2;
        byte b = 1;
        while (true) {
            j3 >>= 8;
            if (j3 == 0) {
                break;
            }
            b = (byte) (b + 1);
        }
        byte[] bArr = new byte[b + 1];
        bArr[b] = b;
        for (int i2 = 0; i2 < b; i2++) {
            bArr[i2] = (byte) (j2 >> (((b - i2) - 1) * 8));
        }
        return bArr;
    }

    public static byte[] encode(byte[] bArr, int i2, int i3) {
        return bArr.length == i3 ? Arrays.concatenate(leftEncode(i3 * 8), bArr) : Arrays.concatenate(leftEncode(i3 * 8), Arrays.copyOfRange(bArr, i2, i3 + i2));
    }
}
