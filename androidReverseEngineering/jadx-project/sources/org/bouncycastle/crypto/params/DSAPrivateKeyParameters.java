package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class DSAPrivateKeyParameters extends DSAKeyParameters {

    /* renamed from: x */
    private BigInteger f1308x;

    public DSAPrivateKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(true, dSAParameters);
        this.f1308x = bigInteger;
    }

    public BigInteger getX() {
        return this.f1308x;
    }
}
