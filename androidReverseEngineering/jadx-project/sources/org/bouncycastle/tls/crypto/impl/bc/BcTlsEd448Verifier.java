package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.params.Ed448PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed448Signer;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class BcTlsEd448Verifier extends BcTlsVerifier {
    public BcTlsEd448Verifier(BcTlsCrypto bcTlsCrypto, Ed448PublicKeyParameters ed448PublicKeyParameters) {
        super(bcTlsCrypto, ed448PublicKeyParameters);
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsVerifier, org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm == null || SignatureScheme.from(algorithm) != 2056) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
        }
        Ed448Signer ed448Signer = new Ed448Signer(TlsUtils.EMPTY_BYTES);
        ed448Signer.init(false, this.publicKey);
        return new BcTlsStreamVerifier(ed448Signer, digitallySigned.getSignature());
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
