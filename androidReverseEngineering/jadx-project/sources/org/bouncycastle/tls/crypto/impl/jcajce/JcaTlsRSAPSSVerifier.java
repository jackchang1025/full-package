package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.PublicKey;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import org.bouncycastle.tls.crypto.TlsVerifier;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class JcaTlsRSAPSSVerifier implements TlsVerifier {
    private final JcaTlsCrypto crypto;
    private final PublicKey publicKey;
    private final int signatureScheme;

    public JcaTlsRSAPSSVerifier(JcaTlsCrypto jcaTlsCrypto, PublicKey publicKey, int i2) {
        if (jcaTlsCrypto == null) {
            throw new NullPointerException("crypto");
        }
        if (publicKey == null) {
            throw new NullPointerException("publicKey");
        }
        if (!SignatureScheme.isRSAPSS(i2)) {
            throw new IllegalArgumentException("signatureScheme");
        }
        this.crypto = jcaTlsCrypto;
        this.publicKey = publicKey;
        this.signatureScheme = i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm != null) {
            int from = SignatureScheme.from(algorithm);
            int i2 = this.signatureScheme;
            if (from == i2) {
                int cryptoHashAlgorithm = SignatureScheme.getCryptoHashAlgorithm(i2);
                String digestName = this.crypto.getDigestName(cryptoHashAlgorithm);
                return this.crypto.createStreamVerifier(AbstractC0000a.m18n(new StringBuilder(), RSAUtil.getDigestSigAlgName(digestName), "WITHRSAANDMGF1"), RSAUtil.getPSSParameterSpec(cryptoHashAlgorithm, digestName, this.crypto.getHelper()), digitallySigned.getSignature(), this.publicKey);
            }
        }
        throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
