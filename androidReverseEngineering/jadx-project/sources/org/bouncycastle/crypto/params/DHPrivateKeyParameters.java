package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class DHPrivateKeyParameters extends DHKeyParameters {

    /* renamed from: x */
    private BigInteger f1301x;

    public DHPrivateKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(true, dHParameters);
        this.f1301x = bigInteger;
    }

    @Override // org.bouncycastle.crypto.params.DHKeyParameters
    public boolean equals(Object obj) {
        return (obj instanceof DHPrivateKeyParameters) && ((DHPrivateKeyParameters) obj).getX().equals(this.f1301x) && super.equals(obj);
    }

    public BigInteger getX() {
        return this.f1301x;
    }

    @Override // org.bouncycastle.crypto.params.DHKeyParameters
    public int hashCode() {
        return this.f1301x.hashCode() ^ super.hashCode();
    }
}
