package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class ElGamalPublicKeySpec extends ElGamalKeySpec {

    /* renamed from: y */
    private BigInteger f1394y;

    public ElGamalPublicKeySpec(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        super(elGamalParameterSpec);
        this.f1394y = bigInteger;
    }

    public BigInteger getY() {
        return this.f1394y;
    }
}
