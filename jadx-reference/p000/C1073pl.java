package p000;

import java.math.BigInteger;
import javax.crypto.spec.DHParameterSpec;

/* renamed from: pl */
/* loaded from: classes2.dex */
public class C1073pl extends DHParameterSpec {

    /* renamed from: j */
    private final BigInteger f59302j;

    /* renamed from: m */
    private final int f59303m;

    /* renamed from: q */
    private final BigInteger f59304q;
    private C1075pn validationParameters;

    public C1073pl(C1074pm c1074pm) {
        this(c1074pm.getP(), c1074pm.getQ(), c1074pm.getG(), c1074pm.getJ(), c1074pm.getM(), c1074pm.getL());
        this.validationParameters = c1074pm.getValidationParameters();
    }

    public C1074pm getDomainParameters() {
        return new C1074pm(getP(), getG(), this.f59304q, this.f59303m, getL(), this.f59302j, this.validationParameters);
    }

    public BigInteger getJ() {
        return this.f59302j;
    }

    public int getM() {
        return this.f59303m;
    }

    public BigInteger getQ() {
        return this.f59304q;
    }

    public C1073pl(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, null, 0);
    }

    public C1073pl(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i) {
        this(bigInteger, bigInteger2, bigInteger3, null, i);
    }

    public C1073pl(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i) {
        this(bigInteger, bigInteger2, bigInteger3, bigInteger4, 0, i);
    }

    public C1073pl(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i, int i2) {
        super(bigInteger, bigInteger3, i2);
        this.f59304q = bigInteger2;
        this.f59302j = bigInteger4;
        this.f59303m = i;
    }
}
