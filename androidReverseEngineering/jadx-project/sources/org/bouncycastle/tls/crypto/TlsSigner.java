package org.bouncycastle.tls.crypto;

import org.bouncycastle.tls.SignatureAndHashAlgorithm;

/* loaded from: classes.dex */
public interface TlsSigner {
    byte[] generateRawSignature(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr);

    TlsStreamSigner getStreamSigner(SignatureAndHashAlgorithm signatureAndHashAlgorithm);
}
