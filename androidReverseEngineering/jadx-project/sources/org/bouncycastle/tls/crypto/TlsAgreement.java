package org.bouncycastle.tls.crypto;

/* loaded from: classes.dex */
public interface TlsAgreement {
    TlsSecret calculateSecret();

    byte[] generateEphemeral();

    void receivePeerValue(byte[] bArr);
}
