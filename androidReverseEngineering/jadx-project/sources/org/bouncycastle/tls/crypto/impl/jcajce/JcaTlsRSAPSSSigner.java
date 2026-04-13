package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PrivateKey;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.crypto.TlsSigner;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class JcaTlsRSAPSSSigner implements TlsSigner {
    private final JcaTlsCrypto crypto;
    private final PrivateKey privateKey;
    private final int signatureScheme;

    public JcaTlsRSAPSSSigner(JcaTlsCrypto jcaTlsCrypto, PrivateKey privateKey, int i2) {
        if (jcaTlsCrypto == null) {
            throw new NullPointerException("crypto");
        }
        if (privateKey == null) {
            throw new NullPointerException("privateKey");
        }
        if (!SignatureScheme.isRSAPSS(i2)) {
            throw new IllegalArgumentException("signatureScheme");
        }
        this.crypto = jcaTlsCrypto;
        this.privateKey = privateKey;
        this.signatureScheme = i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public byte[] generateRawSignature(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public TlsStreamSigner getStreamSigner(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm != null) {
            int from = SignatureScheme.from(signatureAndHashAlgorithm);
            int i2 = this.signatureScheme;
            if (from == i2) {
                int cryptoHashAlgorithm = SignatureScheme.getCryptoHashAlgorithm(i2);
                String digestName = this.crypto.getDigestName(cryptoHashAlgorithm);
                return this.crypto.createStreamSigner(AbstractC0000a.m18n(new StringBuilder(), RSAUtil.getDigestSigAlgName(digestName), "WITHRSAANDMGF1"), RSAUtil.getPSSParameterSpec(cryptoHashAlgorithm, digestName, this.crypto.getHelper()), this.privateKey, true);
            }
        }
        throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", signatureAndHashAlgorithm));
    }
}
