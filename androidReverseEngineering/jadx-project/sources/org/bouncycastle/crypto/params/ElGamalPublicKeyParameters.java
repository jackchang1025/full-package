package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class ElGamalPublicKeyParameters extends ElGamalKeyParameters {

    /* renamed from: y */
    private BigInteger f1319y;

    public ElGamalPublicKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(false, elGamalParameters);
        this.f1319y = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof ElGamalPublicKeyParameters) && ((ElGamalPublicKeyParameters) obj).getY().equals(this.f1319y) && super.equals(obj);
    }

    public BigInteger getY() {
        return this.f1319y;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public int hashCode() {
        return this.f1319y.hashCode() ^ super.hashCode();
    }
}
