package org.bouncycastle.crypto.paddings;

import java.security.SecureRandom;

/* loaded from: classes.dex */
public class TBCPadding implements BlockCipherPadding {
    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i2) {
        int length = bArr.length - i2;
        int i3 = 255;
        if (i2 <= 0 ? (bArr[bArr.length - 1] & 1) != 0 : (bArr[i2 - 1] & 1) != 0) {
            i3 = 0;
        }
        byte b = (byte) i3;
        while (i2 < bArr.length) {
            bArr[i2] = b;
            i2++;
        }
        return length;
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "TBC";
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) {
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) {
        byte b = bArr[bArr.length - 1];
        int length = bArr.length - 1;
        while (length > 0 && bArr[length - 1] == b) {
            length--;
        }
        return bArr.length - length;
    }
}
