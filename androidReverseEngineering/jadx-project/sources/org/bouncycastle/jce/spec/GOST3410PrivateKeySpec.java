package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

/* loaded from: classes.dex */
public class GOST3410PrivateKeySpec implements KeySpec {

    /* renamed from: a */
    private BigInteger f1395a;

    /* renamed from: p */
    private BigInteger f1396p;

    /* renamed from: q */
    private BigInteger f1397q;

    /* renamed from: x */
    private BigInteger f1398x;

    public GOST3410PrivateKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
        this.f1398x = bigInteger;
        this.f1396p = bigInteger2;
        this.f1397q = bigInteger3;
        this.f1395a = bigInteger4;
    }

    public BigInteger getA() {
        return this.f1395a;
    }

    public BigInteger getP() {
        return this.f1396p;
    }

    public BigInteger getQ() {
        return this.f1397q;
    }

    public BigInteger getX() {
        return this.f1398x;
    }
}
