package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.signers.DSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;

/* loaded from: classes.dex */
public class BcTlsDSASigner extends BcTlsDSSSigner {
    public BcTlsDSASigner(BcTlsCrypto bcTlsCrypto, DSAPrivateKeyParameters dSAPrivateKeyParameters) {
        super(bcTlsCrypto, dSAPrivateKeyParameters);
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsDSSSigner
    public DSA createDSAImpl(int i2) {
        return new DSASigner(new HMacDSAKCalculator(this.crypto.createDigest(i2)));
    }

    @Override // org.bouncycastle.tls.crypto.impl.bc.BcTlsDSSSigner
    public short getSignatureAlgorithm() {
        return (short) 2;
    }
}
