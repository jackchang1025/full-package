package p000;

import java.math.BigInteger;

/* renamed from: pm */
/* loaded from: classes2.dex */
public class C1074pm implements InterfaceC0617ic {
    private static final int DEFAULT_MINIMUM_LENGTH = 160;

    /* renamed from: g */
    private BigInteger f59306g;

    /* renamed from: j */
    private BigInteger f59307j;

    /* renamed from: l */
    private int f59308l;

    /* renamed from: m */
    private int f59309m;

    /* renamed from: p */
    private BigInteger f59310p;

    /* renamed from: q */
    private BigInteger f59311q;
    private C1075pn validation;

    public C1074pm(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null, 0);
    }

    private static int getDefaultMParam(int i) {
        return (i != 0 && i < DEFAULT_MINIMUM_LENGTH) ? i : DEFAULT_MINIMUM_LENGTH;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1074pm)) {
            return false;
        }
        C1074pm c1074pm = (C1074pm) obj;
        if (getQ() != null) {
            if (!getQ().equals(c1074pm.getQ())) {
                return false;
            }
        } else if (c1074pm.getQ() != null) {
            return false;
        }
        return c1074pm.getP().equals(this.f59310p) && c1074pm.getG().equals(this.f59306g);
    }

    public BigInteger getG() {
        return this.f59306g;
    }

    public BigInteger getJ() {
        return this.f59307j;
    }

    public int getL() {
        return this.f59308l;
    }

    public int getM() {
        return this.f59309m;
    }

    public BigInteger getP() {
        return this.f59310p;
    }

    public BigInteger getQ() {
        return this.f59311q;
    }

    public C1075pn getValidationParameters() {
        return this.validation;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) ^ (getQ() != null ? getQ().hashCode() : 0);
    }

    public C1074pm(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, 0);
    }

    public C1074pm(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i) {
        this(bigInteger, bigInteger2, bigInteger3, getDefaultMParam(i), i, null, null);
    }

    public C1074pm(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, i, i2, null, null);
    }

    public C1074pm(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i, int i2, BigInteger bigInteger4, C1075pn c1075pn) {
        if (i2 != 0) {
            if (i2 > bigInteger.bitLength()) {
                throw new IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
            }
            if (i2 < i) {
                throw new IllegalArgumentException("when l value specified, it may not be less than m value");
            }
        }
        if (i > bigInteger.bitLength() && !ap0.isOverrideSet("org.bouncycastle.dh.allow_unsafe_p_value")) {
            throw new IllegalArgumentException("unsafe p value so small specific l required");
        }
        this.f59306g = bigInteger2;
        this.f59310p = bigInteger;
        this.f59311q = bigInteger3;
        this.f59309m = i;
        this.f59308l = i2;
        this.f59307j = bigInteger4;
        this.validation = c1075pn;
    }

    public C1074pm(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, C1075pn c1075pn) {
        this(bigInteger, bigInteger2, bigInteger3, DEFAULT_MINIMUM_LENGTH, 0, bigInteger4, c1075pn);
    }
}
