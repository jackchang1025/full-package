package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public interface TlsMAC {
    void calculateMAC(byte[] bArr, int i2);

    byte[] calculateMAC();

    int getMacLength();

    void reset();

    void setKey(byte[] bArr, int i2, int i3);

    void update(byte[] bArr, int i2, int i3);
}
