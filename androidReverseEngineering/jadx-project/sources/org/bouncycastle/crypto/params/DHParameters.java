package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.util.Properties;

/* loaded from: classes.dex */
public class DHParameters implements CipherParameters {
    private static final int DEFAULT_MINIMUM_LENGTH = 160;

    /* renamed from: g */
    private BigInteger f1295g;

    /* renamed from: j */
    private BigInteger f1296j;

    /* renamed from: l */
    private int f1297l;

    /* renamed from: m */
    private int f1298m;

    /* renamed from: p */
    private BigInteger f1299p;

    /* renamed from: q */
    private BigInteger f1300q;
    private DHValidationParameters validation;

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null, 0);
    }

    private static int getDefaultMParam(int i2) {
        if (i2 != 0 && i2 < 160) {
            return i2;
        }
        return 160;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHParameters)) {
            return false;
        }
        DHParameters dHParameters = (DHParameters) obj;
        if (getQ() != null) {
            if (!getQ().equals(dHParameters.getQ())) {
                return false;
            }
        } else if (dHParameters.getQ() != null) {
            return false;
        }
        return dHParameters.getP().equals(this.f1299p) && dHParameters.getG().equals(this.f1295g);
    }

    public BigInteger getG() {
        return this.f1295g;
    }

    public BigInteger getJ() {
        return this.f1296j;
    }

    public int getL() {
        return this.f1297l;
    }

    public int getM() {
        return this.f1298m;
    }

    public BigInteger getP() {
        return this.f1299p;
    }

    public BigInteger getQ() {
        return this.f1300q;
    }

    public DHValidationParameters getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) ^ (getQ() != null ? getQ().hashCode() : 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, 0);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, getDefaultMParam(i2), i2, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2, int i3) {
        this(bigInteger, bigInteger2, bigInteger3, i2, i3, null, null);
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2, int i3, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        if (i3 != 0) {
            if (i3 > bigInteger.bitLength()) {
                throw new IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
            }
            if (i3 < i2) {
                throw new IllegalArgumentException("when l value specified, it may not be less than m value");
            }
        }
        if (i2 > bigInteger.bitLength() && !Properties.isOverrideSet("org.bouncycastle.dh.allow_unsafe_p_value")) {
            throw new IllegalArgumentException("unsafe p value so small specific l required");
        }
        this.f1295g = bigInteger2;
        this.f1299p = bigInteger;
        this.f1300q = bigInteger3;
        this.f1298m = i2;
        this.f1297l = i3;
        this.f1296j = bigInteger4;
        this.validation = dHValidationParameters;
    }

    public DHParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParameters dHValidationParameters) {
        this(bigInteger, bigInteger2, bigInteger3, 160, 0, bigInteger4, dHValidationParameters);
    }
}
