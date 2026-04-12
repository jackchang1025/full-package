package p000;

import java.math.BigInteger;

/* renamed from: qa */
/* loaded from: classes2.dex */
public class C1103qa implements InterfaceC0617ic {

    /* renamed from: g */
    private BigInteger f59453g;

    /* renamed from: p */
    private BigInteger f59454p;

    /* renamed from: q */
    private BigInteger f59455q;
    private C1104qb validation;

    public C1103qa(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f59453g = bigInteger3;
        this.f59454p = bigInteger;
        this.f59455q = bigInteger2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1103qa)) {
            return false;
        }
        C1103qa c1103qa = (C1103qa) obj;
        return c1103qa.getP().equals(this.f59454p) && c1103qa.getQ().equals(this.f59455q) && c1103qa.getG().equals(this.f59453g);
    }

    public BigInteger getG() {
        return this.f59453g;
    }

    public BigInteger getP() {
        return this.f59454p;
    }

    public BigInteger getQ() {
        return this.f59455q;
    }

    public C1104qb getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getQ().hashCode()) ^ getG().hashCode();
    }

    public C1103qa(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, C1104qb c1104qb) {
        this.f59453g = bigInteger3;
        this.f59454p = bigInteger;
        this.f59455q = bigInteger2;
        this.validation = c1104qb;
    }
}
