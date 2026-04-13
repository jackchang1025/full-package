package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PrivateKey;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.crypto.TlsSigner;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class JcaTlsEdDSASigner implements TlsSigner {
    protected final String algorithmName;
    protected final short algorithmType;
    protected final JcaTlsCrypto crypto;
    protected final PrivateKey privateKey;

    public JcaTlsEdDSASigner(JcaTlsCrypto jcaTlsCrypto, PrivateKey privateKey, short s2, String str) {
        if (jcaTlsCrypto == null) {
            throw new NullPointerException("crypto");
        }
        if (privateKey == null) {
            throw new NullPointerException("privateKey");
        }
        this.crypto = jcaTlsCrypto;
        this.privateKey = privateKey;
        this.algorithmType = s2;
        this.algorithmName = str;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public byte[] generateRawSignature(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public TlsStreamSigner getStreamSigner(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm != null && signatureAndHashAlgorithm.getSignature() == this.algorithmType && signatureAndHashAlgorithm.getHash() == 8) {
            return this.crypto.createStreamSigner(this.algorithmName, null, this.privateKey, false);
        }
        throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", signatureAndHashAlgorithm));
    }
}
