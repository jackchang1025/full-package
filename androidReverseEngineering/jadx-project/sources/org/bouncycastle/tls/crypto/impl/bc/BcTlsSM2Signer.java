package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithID;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import org.bouncycastle.util.Arrays;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class BcTlsSM2Signer extends BcTlsSigner {
    protected final byte[] identifier;

    public BcTlsSM2Signer(BcTlsCrypto bcTlsCrypto, ECPrivateKeyParameters eCPrivateKeyParameters, byte[] bArr) {
        super(bcTlsCrypto, eCPrivateKeyParameters);
        this.identifier = Arrays.clone(bArr);
    }

    @Override // org.bouncycastle.tls.crypto.TlsSigner
    public byte[] generateRawSignature(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsSigner, org.bouncycastle.tls.crypto.TlsSigner
    public TlsStreamSigner getStreamSigner(SignatureAndHashAlgorithm signatureAndHashAlgorithm) {
        if (signatureAndHashAlgorithm == null) {
            throw new IllegalStateException(AbstractC0413b.m1011e("Invalid algorithm: ", signatureAndHashAlgorithm));
        }
        ParametersWithID parametersWithID = new ParametersWithID(new ParametersWithRandom(this.privateKey, this.crypto.getSecureRandom()), this.identifier);
        SM2Signer sM2Signer = new SM2Signer();
        sM2Signer.init(true, parametersWithID);
        return new BcTlsStreamSigner(sM2Signer);
    }
}
