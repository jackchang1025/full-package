package org.bouncycastle.jcajce.spec;

import java.math.BigInteger;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.crypto.params.DHParameters;
import org.bouncycastle.crypto.params.DHValidationParameters;

/* loaded from: classes.dex */
public class DHDomainParameterSpec extends DHParameterSpec {

    /* renamed from: j */
    private final BigInteger f1370j;

    /* renamed from: m */
    private final int f1371m;

    /* renamed from: q */
    private final BigInteger f1372q;
    private DHValidationParameters validationParameters;

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, null, 0);
    }

    public DHParameters getDomainParameters() {
        return new DHParameters(getP(), getG(), this.f1372q, this.f1371m, getL(), this.f1370j, this.validationParameters);
    }

    public BigInteger getJ() {
        return this.f1370j;
    }

    public int getM() {
        return this.f1371m;
    }

    public BigInteger getQ() {
        return this.f1372q;
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, null, i2);
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, bigInteger4, 0, i2);
    }

    public DHDomainParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, int i2, int i3) {
        super(bigInteger, bigInteger3, i3);
        this.f1372q = bigInteger2;
        this.f1370j = bigInteger4;
        this.f1371m = i2;
    }

    public DHDomainParameterSpec(DHParameters dHParameters) {
        this(dHParameters.getP(), dHParameters.getQ(), dHParameters.getG(), dHParameters.getJ(), dHParameters.getM(), dHParameters.getL());
        this.validationParameters = dHParameters.getValidationParameters();
    }
}
