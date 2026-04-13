package org.bouncycastle.tls.crypto.impl;

/* loaded from: classes.dex */
public interface TlsAEADCipherImpl {
    int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4);

    int getOutputSize(int i2);

    void init(byte[] bArr, int i2, byte[] bArr2);

    void setKey(byte[] bArr, int i2, int i3);
}
