package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class RSAPrivateCrtKeyParameters extends RSAKeyParameters {
    private BigInteger dP;
    private BigInteger dQ;

    /* renamed from: e */
    private BigInteger f1331e;

    /* renamed from: p */
    private BigInteger f1332p;

    /* renamed from: q */
    private BigInteger f1333q;
    private BigInteger qInv;

    public RSAPrivateCrtKeyParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        super(true, bigInteger, bigInteger3);
        this.f1331e = bigInteger2;
        this.f1332p = bigInteger4;
        this.f1333q = bigInteger5;
        this.dP = bigInteger6;
        this.dQ = bigInteger7;
        this.qInv = bigInteger8;
    }

    public BigInteger getDP() {
        return this.dP;
    }

    public BigInteger getDQ() {
        return this.dQ;
    }

    public BigInteger getP() {
        return this.f1332p;
    }

    public BigInteger getPublicExponent() {
        return this.f1331e;
    }

    public BigInteger getQ() {
        return this.f1333q;
    }

    public BigInteger getQInv() {
        return this.qInv;
    }
}
