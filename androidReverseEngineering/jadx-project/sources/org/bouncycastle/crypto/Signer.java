package org.bouncycastle.crypto;

/* loaded from: classes.dex */
public interface Signer {
    byte[] generateSignature();

    void init(boolean z2, CipherParameters cipherParameters);

    void reset();

    void update(byte b);

    void update(byte[] bArr, int i2, int i3);

    boolean verifySignature(byte[] bArr);
}
