package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public interface TlsCredentialedDecryptor extends TlsCredentials {
    TlsSecret decrypt(TlsCryptoParameters tlsCryptoParameters, byte[] bArr);
}
