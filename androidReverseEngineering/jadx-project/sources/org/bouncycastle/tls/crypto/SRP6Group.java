package org.bouncycastle.tls.crypto;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class SRP6Group {

    /* renamed from: N */
    private BigInteger f1640N;

    /* renamed from: g */
    private BigInteger f1641g;

    public SRP6Group(BigInteger bigInteger, BigInteger bigInteger2) {
        this.f1640N = bigInteger;
        this.f1641g = bigInteger2;
    }

    public BigInteger getG() {
        return this.f1641g;
    }

    public BigInteger getN() {
        return this.f1640N;
    }
}
