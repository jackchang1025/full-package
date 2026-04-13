package org.bouncycastle.tls;

/* loaded from: classes.dex */
public interface TlsPSKIdentityManager {
    byte[] getHint();

    byte[] getPSK(byte[] bArr);
}
