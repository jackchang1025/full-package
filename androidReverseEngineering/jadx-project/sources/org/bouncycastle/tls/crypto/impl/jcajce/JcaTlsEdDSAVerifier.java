package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PublicKey;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import org.bouncycastle.tls.crypto.TlsVerifier;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class JcaTlsEdDSAVerifier implements TlsVerifier {
    protected final String algorithmName;
    protected final short algorithmType;
    protected final JcaTlsCrypto crypto;
    protected final PublicKey publicKey;

    public JcaTlsEdDSAVerifier(JcaTlsCrypto jcaTlsCrypto, PublicKey publicKey, short s2, String str) {
        if (jcaTlsCrypto == null) {
            throw new NullPointerException("crypto");
        }
        if (publicKey == null) {
            throw new NullPointerException("publicKey");
        }
        this.crypto = jcaTlsCrypto;
        this.publicKey = publicKey;
        this.algorithmType = s2;
        this.algorithmName = str;
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm != null && algorithm.getSignature() == this.algorithmType && algorithm.getHash() == 8) {
            return this.crypto.createStreamVerifier(this.algorithmName, null, digitallySigned.getSignature(), this.publicKey);
        }
        throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
