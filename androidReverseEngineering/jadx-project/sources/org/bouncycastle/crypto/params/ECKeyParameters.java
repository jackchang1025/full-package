package org.bouncycastle.crypto.params;

/* loaded from: classes.dex */
public class ECKeyParameters extends AsymmetricKeyParameter {
    private final ECDomainParameters parameters;

    public ECKeyParameters(boolean z2, ECDomainParameters eCDomainParameters) {
        super(z2);
        if (eCDomainParameters == null) {
            throw new NullPointerException("'parameters' cannot be null");
        }
        this.parameters = eCDomainParameters;
    }

    public ECDomainParameters getParameters() {
        return this.parameters;
    }
}
