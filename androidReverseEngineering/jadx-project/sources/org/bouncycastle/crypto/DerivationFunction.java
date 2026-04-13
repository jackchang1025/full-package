package org.bouncycastle.crypto;

/* loaded from: classes.dex */
public interface DerivationFunction {
    int generateBytes(byte[] bArr, int i2, int i3);

    void init(DerivationParameters derivationParameters);
}
