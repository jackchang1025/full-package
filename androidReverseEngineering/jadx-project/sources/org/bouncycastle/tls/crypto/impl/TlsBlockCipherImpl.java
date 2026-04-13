package org.bouncycastle.tls.crypto.impl;

/* loaded from: classes.dex */
public interface TlsBlockCipherImpl {
    int doFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4);

    int getBlockSize();

    void init(byte[] bArr, int i2, int i3);

    void setKey(byte[] bArr, int i2, int i3);
}
