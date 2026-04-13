package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

/* loaded from: classes.dex */
public class GOST3410PublicKeySpec implements KeySpec {

    /* renamed from: a */
    private BigInteger f1402a;

    /* renamed from: p */
    private BigInteger f1403p;

    /* renamed from: q */
    private BigInteger f1404q;

    /* renamed from: y */
    private BigInteger f1405y;

    public GOST3410PublicKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f1405y = bigInteger;
        this.f1403p = bigInteger2;
        this.f1404q = bigInteger3;
        this.f1402a = bigInteger4;
    }

    public BigInteger getA() {
        return this.f1402a;
    }

    public BigInteger getP() {
        return this.f1403p;
    }

    public BigInteger getQ() {
        return this.f1404q;
    }

    public BigInteger getY() {
        return this.f1405y;
    }
}
