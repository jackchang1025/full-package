package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.digests.NullDigest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.signers.DSADigestSigner;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class BcTlsDSSVerifier extends BcTlsVerifier {
    public BcTlsDSSVerifier(BcTlsCrypto bcTlsCrypto, AsymmetricKeyParameter asymmetricKeyParameter) {
        super(bcTlsCrypto, asymmetricKeyParameter);
    }

    public abstract DSA createDSAImpl(int i2);

    public abstract short getSignatureAlgorithm();

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm != null && algorithm.getSignature() != getSignatureAlgorithm()) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
        }
        DSADigestSigner dSADigestSigner = new DSADigestSigner(createDSAImpl(algorithm == null ? 2 : TlsCryptoUtils.getHash(algorithm.getHash())), new NullDigest());
        dSADigestSigner.init(false, this.publicKey);
        if (algorithm == null) {
            dSADigestSigner.update(bArr, 16, 20);
        } else {
            dSADigestSigner.update(bArr, 0, bArr.length);
        }
        return dSADigestSigner.verifySignature(digitallySigned.getSignature());
    }
}
