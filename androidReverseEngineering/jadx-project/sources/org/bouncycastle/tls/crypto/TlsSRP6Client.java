package org.bouncycastle.tls.crypto;

import java.math.BigInteger;

/* loaded from: classes.dex */
public interface TlsSRP6Client {
    BigInteger calculateSecret(BigInteger bigInteger);

    BigInteger generateClientCredentials(byte[] bArr, byte[] bArr2, byte[] bArr3);
}
