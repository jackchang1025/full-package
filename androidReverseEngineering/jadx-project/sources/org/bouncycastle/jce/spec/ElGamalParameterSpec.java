package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes.dex */
public class ElGamalParameterSpec implements AlgorithmParameterSpec {

    /* renamed from: g */
    private BigInteger f1391g;

    /* renamed from: p */
    private BigInteger f1392p;

    public ElGamalParameterSpec(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f1392p = bigInteger;
        this.f1391g = bigInteger2;
    }

    public BigInteger getG() {
        return this.f1391g;
    }

    public BigInteger getP() {
        return this.f1392p;
    }
}
