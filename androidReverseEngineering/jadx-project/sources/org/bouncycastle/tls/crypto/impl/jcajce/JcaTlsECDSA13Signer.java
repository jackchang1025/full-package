package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsSigner;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class JcaTlsECDSA13Signer implements TlsSigner {
    private final JcaTlsCrypto crypto;
    private final PrivateKey privateKey;
    private final int signatureScheme;

    public JcaTlsECDSA13Signer(JcaTlsCrypto jcaTlsCrypto, PrivateKey privateKey, int i2) {
        if (jcaTlsCrypto == null) {
            throw new NullPointerException("crypto");
        }
        if (privateKey == null) {
            throw new NullPointerException("privateKey");
        }
        if (!SignatureScheme.isECDSA(i2)) {
            throw new IllegalArgumentException("signatureScheme");
        }
        this.crypto = jcaTlsCrypto;
        this.privateKey = privateKey;
        this.signatureScheme = i2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public byte[] generateRawSignature(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        if (signatureAndHashAlgorithm == null || SignatureScheme.from(signatureAndHashAlgorithm) != this.signatureScheme) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", signatureAndHashAlgorithm));
        }
        try {
            Signature createSignature = this.crypto.getHelper().createSignature("NoneWithECDSA");
            createSignature.initSign(this.privateKey, this.crypto.getSecureRandom());
            createSignature.update(bArr, 0, bArr.length);
            return createSignature.sign();
        } catch (GeneralSecurityException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public TlsStreamSigner getStreamSigner(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        return null;
    }
}
