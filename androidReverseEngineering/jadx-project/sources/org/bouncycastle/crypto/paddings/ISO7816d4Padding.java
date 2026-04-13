package org.bouncycastle.crypto.paddings;

import android.sun.security.util.DerValue;
import java.security.SecureRandom;
import org.bouncycastle.crypto.InvalidCipherTextException;

/* loaded from: classes.dex */
public class ISO7816d4Padding implements BlockCipherPadding {
    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int addPadding(byte[] bArr, int i2) {
        int length = bArr.length - i2;
        bArr[i2] = DerValue.TAG_CONTEXT;
        while (true) {
            i2++;
            if (i2 >= bArr.length) {
                return length;
            }
            bArr[i2] = 0;
        }
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public String getPaddingName() {
        return "ISO7816-4";
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public void init(SecureRandom secureRandom) {
    }

    @Override // org.bouncycastle.crypto.paddings.BlockCipherPadding
    public int padCount(byte[] bArr) {
        int length = bArr.length - 1;
        while (length > 0 && bArr[length] == 0) {
            length--;
        }
        if (bArr[length] == Byte.MIN_VALUE) {
            return bArr.length - length;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
