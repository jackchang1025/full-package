package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class DSAParameters implements CipherParameters {

    /* renamed from: g */
    private BigInteger f1305g;

    /* renamed from: p */
    private BigInteger f1306p;

    /* renamed from: q */
    private BigInteger f1307q;
    private DSAValidationParameters validation;

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.f1305g = bigInteger3;
        this.f1306p = bigInteger;
        this.f1307q = bigInteger2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DSAParameters)) {
            return false;
        }
        DSAParameters dSAParameters = (DSAParameters) obj;
        return dSAParameters.getP().equals(this.f1306p) && dSAParameters.getQ().equals(this.f1307q) && dSAParameters.getG().equals(this.f1305g);
    }

    public BigInteger getG() {
        return this.f1305g;
    }

    public BigInteger getP() {
        return this.f1306p;
    }

    public BigInteger getQ() {
        return this.f1307q;
    }

    public DSAValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getQ().hashCode()) ^ getG().hashCode();
    }

    public DSAParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, DSAValidationParameters dSAValidationParameters) {
        this.f1305g = bigInteger3;
        this.f1306p = bigInteger;
        this.f1307q = bigInteger2;
        this.validation = dSAValidationParameters;
    }
}
