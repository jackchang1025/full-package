package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class ElGamalPrivateKeySpec extends ElGamalKeySpec {

    /* renamed from: x */
    private BigInteger f1393x;

    public ElGamalPrivateKeySpec(BigInteger bigInteger, ElGamalParameterSpec elGamalParameterSpec) {
        super(elGamalParameterSpec);
        this.f1393x = bigInteger;
    }

    public BigInteger getX() {
        return this.f1393x;
    }
}
