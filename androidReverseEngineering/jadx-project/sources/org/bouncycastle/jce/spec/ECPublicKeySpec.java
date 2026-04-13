package org.bouncycastle.jce.spec;

import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes.dex */
public class ECPublicKeySpec extends ECKeySpec {

    /* renamed from: q */
    private ECPoint f1390q;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f1390q = eCPoint.getCurve() != null ? eCPoint.normalize() : eCPoint;
    }

    public ECPoint getQ() {
        return this.f1390q;
    }
}
