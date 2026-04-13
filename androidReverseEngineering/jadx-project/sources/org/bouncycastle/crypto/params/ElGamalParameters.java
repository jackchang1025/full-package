package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes.dex */
public class ElGamalParameters implements CipherParameters {

    /* renamed from: g */
    private BigInteger f1315g;

    /* renamed from: l */
    private int f1316l;

    /* renamed from: p */
    private BigInteger f1317p;

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, 0);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ElGamalParameters)) {
            return false;
        }
        ElGamalParameters elGamalParameters = (ElGamalParameters) obj;
        return elGamalParameters.getP().equals(this.f1317p) && elGamalParameters.getG().equals(this.f1315g) && elGamalParameters.getL() == this.f1316l;
    }

    public BigInteger getG() {
        return this.f1315g;
    }

    public int getL() {
        return this.f1316l;
    }

    public BigInteger getP() {
        return this.f1317p;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) + this.f1316l;
    }

    public ElGamalParameters(BigInteger bigInteger, BigInteger bigInteger2, int i2) {
        this.f1315g = bigInteger2;
        this.f1317p = bigInteger;
        this.f1316l = i2;
    }
}
