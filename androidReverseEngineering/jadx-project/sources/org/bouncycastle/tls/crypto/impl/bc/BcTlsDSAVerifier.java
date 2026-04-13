package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;

/* loaded from: classes.dex */
public class BcTlsDSAVerifier extends BcTlsDSSVerifier {
    public BcTlsDSAVerifier(BcTlsCrypto bcTlsCrypto, DSAPublicKeyParameters dSAPublicKeyParameters) {
        super(bcTlsCrypto, dSAPublicKeyParameters);
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsDSSVerifier
    public DSA createDSAImpl(int i2) {
        return new DSASigner(new HMacDSAKCalculator(this.crypto.createDigest(i2)));
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsDSSVerifier
    public short getSignatureAlgorithm() {
        return (short) 2;
    }
}
