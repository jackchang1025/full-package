package org.bouncycastle.crypto.paddings;

import java.security.SecureRandom;

/* loaded from: classes.dex */
public interface BlockCipherPadding {
    int addPadding(byte[] bArr, int i2);

    String getPaddingName();

    void init(SecureRandom secureRandom);

    int padCount(byte[] bArr);
}
