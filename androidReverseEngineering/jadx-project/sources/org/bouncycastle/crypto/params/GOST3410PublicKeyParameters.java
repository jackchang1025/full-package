package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class GOST3410PublicKeyParameters extends GOST3410KeyParameters {

    /* renamed from: y */
    private BigInteger f1324y;

    public GOST3410PublicKeyParameters(BigInteger bigInteger, GOST3410Parameters gOST3410Parameters) {
        super(false, gOST3410Parameters);
        this.f1324y = bigInteger;
    }

    public BigInteger getY() {
        return this.f1324y;
    }
}
