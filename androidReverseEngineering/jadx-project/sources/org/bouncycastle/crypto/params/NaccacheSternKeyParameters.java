package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class NaccacheSternKeyParameters extends AsymmetricKeyParameter {

    /* renamed from: g */
    private BigInteger f1329g;
    int lowerSigmaBound;

    /* renamed from: n */
    private BigInteger f1330n;

    public NaccacheSternKeyParameters(boolean z2, BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        super(z2);
        this.f1329g = bigInteger;
        this.f1330n = bigInteger2;
        this.lowerSigmaBound = i2;
    }

    public BigInteger getG() {
        return this.f1329g;
    }

    public int getLowerSigmaBound() {
        return this.lowerSigmaBound;
    }

    public BigInteger getModulus() {
        return this.f1330n;
    }
}
