package org.bouncycastle.pqc.crypto;

import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public interface MessageEncryptor {
    void init(boolean z2, CipherParameters cipherParameters);

    byte[] messageDecrypt(byte[] bArr);

    byte[] messageEncrypt(byte[] bArr);
}
