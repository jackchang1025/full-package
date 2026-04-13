package org.bouncycastle.operator.bc;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.signers.RSADigestSigner;

/* loaded from: classes.dex */
public class BcRSAContentSignerBuilder extends BcContentSignerBuilder {
    public BcRSAContentSignerBuilder(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        super(algorithmIdentifier, algorithmIdentifier2);
    }

    @Override // org.bouncycastle.operator.bc.BcContentSignerBuilder
    public Signer createSigner(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        return new RSADigestSigner(this.digestProvider.get(algorithmIdentifier2));
    }
}
