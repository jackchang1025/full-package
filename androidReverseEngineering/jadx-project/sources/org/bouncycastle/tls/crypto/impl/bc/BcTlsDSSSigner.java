package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.digests.NullDigest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class BcTlsDSSSigner extends BcTlsSigner {
    public BcTlsDSSSigner(BcTlsCrypto bcTlsCrypto, AsymmetricKeyParameter asymmetricKeyParameter) {
        super(bcTlsCrypto, asymmetricKeyParameter);
    }

    public abstract DSA createDSAImpl(int i2);

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public byte[] generateRawSignature(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        if (signatureAndHashAlgorithm != null && signatureAndHashAlgorithm.getSignature() != getSignatureAlgorithm()) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", signatureAndHashAlgorithm));
        }
        DSADigestSigner dSADigestSigner = new DSADigestSigner(createDSAImpl(signatureAndHashAlgorithm == null ? 2 : TlsCryptoUtils.getHash(signatureAndHashAlgorithm.getHash())), new NullDigest());
        dSADigestSigner.init(true, new ParametersWithRandom(this.privateKey, this.crypto.getSecureRandom()));
        if (signatureAndHashAlgorithm == null) {
            dSADigestSigner.update(bArr, 16, 20);
        } else {
            dSADigestSigner.update(bArr, 0, bArr.length);
        }
        try {
            return dSADigestSigner.generateSignature();
        } catch (CryptoException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }

    public abstract short getSignatureAlgorithm();
}
