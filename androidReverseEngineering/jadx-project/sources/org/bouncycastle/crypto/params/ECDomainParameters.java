package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ECDomainParameters implements ECConstants {

    /* renamed from: G */
    private final ECPoint f1310G;
    private final ECCurve curve;

    /* renamed from: h */
    private final BigInteger f1311h;
    private BigInteger hInv;

    /* renamed from: n */
    private final BigInteger f1312n;
    private final byte[] seed;

    public ECDomainParameters(X9ECParameters x9ECParameters) {
        this(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
    }

    public static ECPoint validatePublicPoint(ECCurve eCCurve, ECPoint eCPoint) {
        if (eCPoint == null) {
            throw new NullPointerException("Point cannot be null");
        }
        ECPoint normalize = ECAlgorithms.importPoint(eCCurve, eCPoint).normalize();
        if (normalize.isInfinity()) {
            throw new IllegalArgumentException("Point at infinity");
        }
        if (normalize.isValid()) {
            return normalize;
        }
        throw new IllegalArgumentException("Point not on curve");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ECDomainParameters)) {
            return false;
        }
        ECDomainParameters eCDomainParameters = (ECDomainParameters) obj;
        return this.curve.equals(eCDomainParameters.curve) && this.f1310G.equals(eCDomainParameters.f1310G) && this.f1312n.equals(eCDomainParameters.f1312n);
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public ECPoint getG() {
        return this.f1310G;
    }

    public BigInteger getH() {
        return this.f1311h;
    }

    public synchronized BigInteger getHInv() {
        if (this.hInv == null) {
            this.hInv = BigIntegers.modOddInverseVar(this.f1312n, this.f1311h);
        }
        return this.hInv;
    }

    public BigInteger getN() {
        return this.f1312n;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    public int hashCode() {
        return ((((this.curve.hashCode() ^ 1028) * 257) ^ this.f1310G.hashCode()) * 257) ^ this.f1312n.hashCode();
    }

    public BigInteger validatePrivateScalar(BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new NullPointerException("Scalar cannot be null");
        }
        if (bigInteger.compareTo(ECConstants.ONE) < 0 || bigInteger.compareTo(getN()) >= 0) {
            throw new IllegalArgumentException("Scalar is not in the interval [1, n - 1]");
        }
        return bigInteger;
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, ECConstants.ONE, null);
    }

    public ECPoint validatePublicPoint(ECPoint eCPoint) {
        return validatePublicPoint(getCurve(), eCPoint);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public ECDomainParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.hInv = null;
        if (eCCurve == null) {
            throw new NullPointerException("curve");
        }
        if (bigInteger == null) {
            throw new NullPointerException("n");
        }
        this.curve = eCCurve;
        this.f1310G = validatePublicPoint(eCCurve, eCPoint);
        this.f1312n = bigInteger;
        this.f1311h = bigInteger2;
        this.seed = Arrays.clone(bArr);
    }
}
