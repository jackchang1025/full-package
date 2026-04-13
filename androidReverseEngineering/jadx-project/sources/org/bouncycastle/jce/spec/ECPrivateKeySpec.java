package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class ECPrivateKeySpec extends ECKeySpec {

    /* renamed from: d */
    private BigInteger f1389d;

    public ECPrivateKeySpec(BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.f1389d = bigInteger;
    }

    public BigInteger getD() {
        return this.f1389d;
    }
}
