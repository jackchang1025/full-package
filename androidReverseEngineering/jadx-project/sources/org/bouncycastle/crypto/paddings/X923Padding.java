package org.bouncycastle.crypto.paddings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.InvalidCipherTextException;

/* loaded from: classes.dex */
public class X923Padding implements BlockCipherPadding {
    SecureRandom random = null;

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i2) {
        byte length = (byte) (bArr.length - i2);
        while (i2 < bArr.length - 1) {
            SecureRandom secureRandom = this.random;
            if (secureRandom == null) {
                bArr[i2] = 0;
            } else {
                bArr[i2] = (byte) secureRandom.nextInt();
            }
            i2++;
        }
        bArr[i2] = length;
        return length;
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "X9.23";
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) {
        int i2 = bArr[bArr.length - 1] & 255;
        if (i2 <= bArr.length) {
            return i2;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
