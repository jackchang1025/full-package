package org.bouncycastle.crypto.params;

import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes.dex */
public class ECPublicKeyParameters extends ECKeyParameters {

    /* renamed from: q */
    private final ECPoint f1314q;

    public ECPublicKeyParameters(ECPoint eCPoint, ECDomainParameters eCDomainParameters) {
        super(false, eCDomainParameters);
        this.f1314q = eCDomainParameters.validatePublicPoint(eCPoint);
    }

    public ECPoint getQ() {
        return this.f1314q;
    }
}
