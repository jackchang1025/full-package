package org.bouncycastle.crypto.modes.gcm;

/* loaded from: classes.dex */
public interface GCMExponentiator {
    void exponentiateX(long j2, byte[] bArr);

    void init(byte[] bArr);
}
