package org.bouncycastle.crypto.params;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class CramerShoupPublicKeyParameters extends CramerShoupKeyParameters {

    /* renamed from: c */
    private BigInteger f1292c;

    /* renamed from: d */
    private BigInteger f1293d;

    /* renamed from: h */
    private BigInteger f1294h;

    public CramerShoupPublicKeyParameters(CramerShoupParameters cramerShoupParameters, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        super(false, cramerShoupParameters);
        this.f1292c = bigInteger;
        this.f1293d = bigInteger2;
        this.f1294h = bigInteger3;
    }

    @Override // org.bouncycastle.crypto.params.CramerShoupKeyParameters
    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupPublicKeyParameters)) {
            return false;
        }
        CramerShoupPublicKeyParameters cramerShoupPublicKeyParameters = (CramerShoupPublicKeyParameters) obj;
        return cramerShoupPublicKeyParameters.getC().equals(this.f1292c) && cramerShoupPublicKeyParameters.getD().equals(this.f1293d) && cramerShoupPublicKeyParameters.getH().equals(this.f1294h) && super.equals(obj);
    }

    public BigInteger getC() {
        return this.f1292c;
    }

    public BigInteger getD() {
        return this.f1293d;
    }

    public BigInteger getH() {
        return this.f1294h;
    }

    @Override // org.bouncycastle.crypto.params.CramerShoupKeyParameters
    public int hashCode() {
        return ((this.f1292c.hashCode() ^ this.f1293d.hashCode()) ^ this.f1294h.hashCode()) ^ super.hashCode();
    }
}
