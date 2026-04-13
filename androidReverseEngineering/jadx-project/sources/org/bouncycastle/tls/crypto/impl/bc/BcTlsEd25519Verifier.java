package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class BcTlsEd25519Verifier extends BcTlsVerifier {
    public BcTlsEd25519Verifier(BcTlsCrypto bcTlsCrypto, Ed25519PublicKeyParameters ed25519PublicKeyParameters) {
        super(bcTlsCrypto, ed25519PublicKeyParameters);
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsVerifier, org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm == null || SignatureScheme.from(algorithm) != 2055) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
        }
        Ed25519Signer ed25519Signer = new Ed25519Signer();
        ed25519Signer.init(false, this.publicKey);
        return new BcTlsStreamVerifier(ed25519Signer, digitallySigned.getSignature());
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
