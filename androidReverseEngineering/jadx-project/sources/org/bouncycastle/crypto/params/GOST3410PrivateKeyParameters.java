package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class GOST3410PrivateKeyParameters extends GOST3410KeyParameters {

    /* renamed from: x */
    private BigInteger f1323x;

    public GOST3410PrivateKeyParameters(BigInteger bigInteger, GOST3410Parameters gOST3410Parameters) {
        super(true, gOST3410Parameters);
        this.f1323x = bigInteger;
    }

    public BigInteger getX() {
        return this.f1323x;
    }
}
