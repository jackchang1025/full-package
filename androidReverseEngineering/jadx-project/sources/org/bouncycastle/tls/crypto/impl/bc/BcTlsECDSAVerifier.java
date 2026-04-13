package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;

/* loaded from: classes.dex */
public class BcTlsECDSAVerifier extends BcTlsDSSVerifier {
    public BcTlsECDSAVerifier(BcTlsCrypto bcTlsCrypto, ECPublicKeyParameters eCPublicKeyParameters) {
        super(bcTlsCrypto, eCPublicKeyParameters);
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsDSSVerifier
    public DSA createDSAImpl(int i2) {
        return new ECDSASigner(new HMacDSAKCalculator(this.crypto.createDigest(i2)));
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsDSSVerifier
    public short getSignatureAlgorithm() {
        return (short) 3;
    }
}
