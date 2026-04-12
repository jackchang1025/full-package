package p000;

import java.math.BigInteger;

/* renamed from: uy */
/* loaded from: classes2.dex */
public class C1317uy implements InterfaceC1315uw {

    /* renamed from: G */
    private final AbstractC1341vl f60533G;
    private final AbstractC1316ux curve;

    /* renamed from: h */
    private final BigInteger f60534h;
    private BigInteger hInv;

    /* renamed from: n */
    private final BigInteger f60535n;
    private final byte[] seed;

    public C1317uy(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        this(abstractC1316ux, abstractC1341vl, bigInteger, InterfaceC1315uw.ONE, null);
    }

    public static AbstractC1341vl validatePublicPoint(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl) {
        if (abstractC1341vl == null) {
            throw new NullPointerException("Point cannot be null");
        }
        AbstractC1341vl abstractC1341vlNormalize = C1314uv.importPoint(abstractC1316ux, abstractC1341vl).normalize();
        if (abstractC1341vlNormalize.isInfinity()) {
            throw new IllegalArgumentException("Point at infinity");
        }
        if (abstractC1341vlNormalize.isValid()) {
            return abstractC1341vlNormalize;
        }
        throw new IllegalArgumentException("Point not on curve");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1317uy)) {
            return false;
        }
        C1317uy c1317uy = (C1317uy) obj;
        return this.curve.equals(c1317uy.curve) && this.f60533G.equals(c1317uy.f60533G) && this.f60535n.equals(c1317uy.f60535n);
    }

    public AbstractC1316ux getCurve() {
        return this.curve;
    }

    public AbstractC1341vl getG() {
        return this.f60533G;
    }

    public BigInteger getH() {
        return this.f60534h;
    }

    public synchronized BigInteger getHInv() {
        try {
            if (this.hInv == null) {
                this.hInv = C0427ds.modOddInverseVar(this.f60535n, this.f60534h);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.hInv;
    }

    public BigInteger getN() {
        return this.f60535n;
    }

    public byte[] getSeed() {
        return C0133bg.clone(this.seed);
    }

    public int hashCode() {
        return ((((this.curve.hashCode() ^ 1028) * 257) ^ this.f60533G.hashCode()) * 257) ^ this.f60535n.hashCode();
    }

    public BigInteger validatePrivateScalar(BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new NullPointerException("Scalar cannot be null");
        }
        if (bigInteger.compareTo(InterfaceC1315uw.ONE) < 0 || bigInteger.compareTo(getN()) >= 0) {
            throw new IllegalArgumentException("Scalar is not in the interval [1, n - 1]");
        }
        return bigInteger;
    }

    public C1317uy(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2) {
        this(abstractC1316ux, abstractC1341vl, bigInteger, bigInteger2, null);
    }

    public AbstractC1341vl validatePublicPoint(AbstractC1341vl abstractC1341vl) {
        return validatePublicPoint(getCurve(), abstractC1341vl);
    }

    public C1317uy(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.hInv = null;
        if (abstractC1316ux == null) {
            throw new NullPointerException("curve");
        }
        if (bigInteger == null) {
            throw new NullPointerException("n");
        }
        this.curve = abstractC1316ux;
        this.f60533G = validatePublicPoint(abstractC1316ux, abstractC1341vl);
        this.f60535n = bigInteger;
        this.f60534h = bigInteger2;
        this.seed = C0133bg.clone(bArr);
    }

    public C1317uy(bi1 bi1Var) {
        this(bi1Var.getCurve(), bi1Var.getG(), bi1Var.getN(), bi1Var.getH(), bi1Var.getSeed());
    }
}
