package org.bouncycastle.openssl;

/* loaded from: classes.dex */
public interface PEMEncryptor {
    byte[] encrypt(byte[] bArr);

    String getAlgorithm();

    byte[] getIV();
}
