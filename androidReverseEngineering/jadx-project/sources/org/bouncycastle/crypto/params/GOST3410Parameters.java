package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class GOST3410Parameters implements CipherParameters {

    /* renamed from: a */
    private BigInteger f1320a;

    /* renamed from: p */
    private BigInteger f1321p;

    /* renamed from: q */
    private BigInteger f1322q;
    private GOST3410ValidationParameters validation;

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f1321p = bigInteger;
        this.f1322q = bigInteger2;
        this.f1320a = bigInteger3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410Parameters)) {
            return false;
        }
        GOST3410Parameters gOST3410Parameters = (GOST3410Parameters) obj;
        return gOST3410Parameters.getP().equals(this.f1321p) && gOST3410Parameters.getQ().equals(this.f1322q) && gOST3410Parameters.getA().equals(this.f1320a);
    }

    public BigInteger getA() {
        return this.f1320a;
    }

    public BigInteger getP() {
        return this.f1321p;
    }

    public BigInteger getQ() {
        return this.f1322q;
    }

    public GOST3410ValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (this.f1321p.hashCode() ^ this.f1322q.hashCode()) ^ this.f1320a.hashCode();
    }

    public GOST3410Parameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, GOST3410ValidationParameters gOST3410ValidationParameters) {
        this.f1320a = bigInteger3;
        this.f1321p = bigInteger;
        this.f1322q = bigInteger2;
        this.validation = gOST3410ValidationParameters;
    }
}
