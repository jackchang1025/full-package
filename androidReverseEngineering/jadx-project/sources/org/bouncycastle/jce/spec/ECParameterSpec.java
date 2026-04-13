package org.bouncycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes.dex */
public class ECParameterSpec implements AlgorithmParameterSpec {

    /* renamed from: G */
    private ECPoint f1386G;
    private ECCurve curve;

    /* renamed from: h */
    private BigInteger f1387h;

    /* renamed from: n */
    private BigInteger f1388n;
    private byte[] seed;

    public ECParameterSpec(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this.curve = eCCurve;
        this.f1386G = eCPoint.normalize();
        this.f1388n = bigInteger;
        this.f1387h = BigInteger.valueOf(1L);
        this.seed = null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ECParameterSpec)) {
            return false;
        }
        ECParameterSpec eCParameterSpec = (ECParameterSpec) obj;
        return getCurve().equals(eCParameterSpec.getCurve()) && getG().equals(eCParameterSpec.getG());
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public ECPoint getG() {
        return this.f1386G;
    }

    public BigInteger getH() {
        return this.f1387h;
    }

    public BigInteger getN() {
        return this.f1388n;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public int hashCode() {
        return getCurve().hashCode() ^ getG().hashCode();
    }

    public ECParameterSpec(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this.curve = eCCurve;
        this.f1386G = eCPoint.normalize();
        this.f1388n = bigInteger;
        this.f1387h = bigInteger2;
        this.seed = null;
    }

    public ECParameterSpec(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.curve = eCCurve;
        this.f1386G = eCPoint.normalize();
        this.f1388n = bigInteger;
        this.f1387h = bigInteger2;
        this.seed = bArr;
    }
}
