package org.bouncycastle.operator;

import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

/* loaded from: classes.dex */
public abstract class AsymmetricKeyWrapper implements KeyWrapper {
    private AlgorithmIdentifier algorithmId;

    public AsymmetricKeyWrapper(AlgorithmIdentifier algorithmIdentifier) {
        this.algorithmId = algorithmIdentifier;
    }

    @Override // org.bouncycastle.operator.KeyWrapper
    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return this.algorithmId;
    }
}
