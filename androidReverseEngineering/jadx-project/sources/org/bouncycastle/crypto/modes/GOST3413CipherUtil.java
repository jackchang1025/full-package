package org.bouncycastle.crypto.modes;

import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
class GOST3413CipherUtil {
    public static byte[] LSB(byte[] bArr, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, bArr.length - i2, bArr2, 0, i2);
        return bArr2;
    }

    public static byte[] MSB(byte[] bArr, int i2) {
        return Arrays.copyOf(bArr, i2);
    }

    public static byte[] copyFromInput(byte[] bArr, int i2, int i3) {
        if (bArr.length < i2 + i3) {
            i2 = bArr.length - i3;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i3, bArr2, 0, i2);
        return bArr2;
    }

    public static byte[] sum(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
        return bArr3;
    }
}
