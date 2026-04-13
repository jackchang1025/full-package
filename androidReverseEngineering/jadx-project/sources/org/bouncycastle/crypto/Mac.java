package org.bouncycastle.crypto;

/* loaded from: classes.dex */
public interface Mac {
    int doFinal(byte[] bArr, int i2);

    String getAlgorithmName();

    int getMacSize();

    void init(CipherParameters cipherParameters);

    void reset();

    void update(byte b);

    void update(byte[] bArr, int i2, int i3);
}
