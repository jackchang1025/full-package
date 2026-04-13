package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public interface TlsSecret {
    byte[] calculateHMAC(int i2, byte[] bArr, int i3, int i4);

    TlsSecret deriveUsingPRF(int i2, String str, byte[] bArr, int i3);

    void destroy();

    byte[] encrypt(TlsEncryptor tlsEncryptor);

    byte[] extract();

    TlsSecret hkdfExpand(int i2, byte[] bArr, int i3);

    TlsSecret hkdfExtract(int i2, TlsSecret tlsSecret);

    boolean isAlive();
}
