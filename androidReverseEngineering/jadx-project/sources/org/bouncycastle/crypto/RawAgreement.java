package org.bouncycastle.crypto;

/* loaded from: classes.dex */
public interface RawAgreement {
    void calculateAgreement(CipherParameters cipherParameters, byte[] bArr, int i2);

    int getAgreementSize();

    void init(CipherParameters cipherParameters);
}
