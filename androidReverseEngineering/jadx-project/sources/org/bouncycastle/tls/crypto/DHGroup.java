package org.bouncycastle.tls.crypto;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class DHGroup {

    /* renamed from: g */
    private final BigInteger f1636g;

    /* renamed from: l */
    private final int f1637l;

    /* renamed from: p */
    private final BigInteger f1638p;

    /* renamed from: q */
    private final BigInteger f1639q;

    public DHGroup(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2) {
        this.f1638p = bigInteger;
        this.f1636g = bigInteger3;
        this.f1639q = bigInteger2;
        this.f1637l = i2;
    }

    public BigInteger getG() {
        return this.f1636g;
    }

    public int getL() {
        return this.f1637l;
    }

    public BigInteger getP() {
        return this.f1638p;
    }

    public BigInteger getQ() {
        return this.f1639q;
    }
}
