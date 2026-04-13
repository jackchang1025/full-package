package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import org.bouncycastle.util.Arrays;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class BcTlsSM2Verifier extends BcTlsVerifier {
    protected final byte[] identifier;

    public BcTlsSM2Verifier(BcTlsCrypto bcTlsCrypto, ECPublicKeyParameters eCPublicKeyParameters, byte[] bArr) {
        super(bcTlsCrypto, eCPublicKeyParameters);
        this.identifier = Arrays.clone(bArr);
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsVerifier, org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        SignatureAndHashAlgorithm algorithm = digitallySigned.getAlgorithm();
        if (algorithm == null) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", algorithm));
        }
        ParametersWithID parametersWithID = new ParametersWithID(this.publicKey, this.identifier);
        SM2Signer sM2Signer = new SM2Signer();
        sM2Signer.init(false, parametersWithID);
        return new BcTlsStreamVerifier(sM2Signer, digitallySigned.getSignature());
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public boolean verifyRawSignature(DigitallySigned digitallySigned, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
