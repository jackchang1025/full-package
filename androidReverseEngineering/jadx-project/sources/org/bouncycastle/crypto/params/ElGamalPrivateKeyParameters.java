package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class ElGamalPrivateKeyParameters extends ElGamalKeyParameters {

    /* renamed from: x */
    private BigInteger f1318x;

    public ElGamalPrivateKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(true, elGamalParameters);
        this.f1318x = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public boolean equals(Object obj) {
        if ((obj instanceof ElGamalPrivateKeyParameters) && ((ElGamalPrivateKeyParameters) obj).getX().equals(this.f1318x)) {
            return super.equals(obj);
        }
        return false;
    }

    public BigInteger getX() {
        return this.f1318x;
    }

    @Override // org.bouncycastle.crypto.params.ElGamalKeyParameters
    public int hashCode() {
        return getX().hashCode();
    }
}
