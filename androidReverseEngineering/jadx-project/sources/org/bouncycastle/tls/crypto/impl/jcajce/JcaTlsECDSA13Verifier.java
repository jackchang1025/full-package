package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import org.bouncycastle.tls.crypto.TlsVerifier;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class JcaTlsECDSA13Verifier implements TlsVerifier {
    private final JcaTlsCrypto crypto;
    private final PublicKey publicKey;
    private final int signatureScheme;

    public JcaTlsECDSA13Verifier(JcaTlsCrypto jcaTlsCrypto, PublicKey publicKey, int i2) {
        if (jcaTlsCrypto == null) {
            throw new NullPointerException("crypto");
        }
        if (publicKey == null) {
            throw new NullPointerException("publicKey");
        }
        if (!SignatureScheme.isECDSA(i2)) {
            throw new IllegalArgumentException("signatureScheme");
        }
        this.crypto = jcaTlsCrypto;
        this.publicKey = publicKey;
        this.signatureScheme = i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        return null;
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm == null || SignatureScheme.from(algorithm) != this.signatureScheme) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
        }
        try {
            Signature createSignature = this.crypto.getHelper().createSignature("NoneWithECDSA");
            createSignature.initVerify(this.publicKey);
            createSignature.update(bArr, 0, bArr.length);
            return createSignature.verify(digitallySigned.getSignature());
        } catch (GeneralSecurityException e2) {
            throw Exceptions.illegalStateException(AbstractC0000a.m19o(e2, new StringBuilder("unable to process signature: ")), e2);
        }
    }
}
