package org.bouncycastle.jce.spec;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class GOST3410PublicKeyParameterSetSpec {

    /* renamed from: a */
    private BigInteger f1399a;

    /* renamed from: p */
    private BigInteger f1400p;

    /* renamed from: q */
    private BigInteger f1401q;

    public GOST3410PublicKeyParameterSetSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f1400p = bigInteger;
        this.f1401q = bigInteger2;
        this.f1399a = bigInteger3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410PublicKeyParameterSetSpec)) {
            return false;
        }
        GOST3410PublicKeyParameterSetSpec gOST3410PublicKeyParameterSetSpec = (GOST3410PublicKeyParameterSetSpec) obj;
        return this.f1399a.equals(gOST3410PublicKeyParameterSetSpec.f1399a) && this.f1400p.equals(gOST3410PublicKeyParameterSetSpec.f1400p) && this.f1401q.equals(gOST3410PublicKeyParameterSetSpec.f1401q);
    }

    public BigInteger getA() {
        return this.f1399a;
    }

    public BigInteger getP() {
        return this.f1400p;
    }

    public BigInteger getQ() {
        return this.f1401q;
    }

    public int hashCode() {
        return (this.f1399a.hashCode() ^ this.f1400p.hashCode()) ^ this.f1401q.hashCode();
    }
}
