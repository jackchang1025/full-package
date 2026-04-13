package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsSigner;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class JcaTlsDSSSigner implements TlsSigner {
    protected final String algorithmName;
    protected final short algorithmType;
    protected final JcaTlsCrypto crypto;
    protected final PrivateKey privateKey;

    public JcaTlsDSSSigner(JcaTlsCrypto jcaTlsCrypto, PrivateKey privateKey, short s2, String str) {
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
        if (signatureAndHashAlgorithm != null && signatureAndHashAlgorithm.getSignature() != this.algorithmType) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", signatureAndHashAlgorithm));
        }
        try {
            Signature createSignature = this.crypto.getHelper().createSignature(this.algorithmName);
            createSignature.initSign(this.privateKey, this.crypto.getSecureRandom());
            if (signatureAndHashAlgorithm == null) {
                createSignature.update(bArr, 16, 20);
            } else {
                createSignature.update(bArr, 0, bArr.length);
            }
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
