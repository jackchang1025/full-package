package p000;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

/* renamed from: vk */
/* loaded from: classes2.dex */
public class C1340vk implements AlgorithmParameterSpec {

    /* renamed from: G */
    private AbstractC1341vl f60649G;
    private AbstractC1316ux curve;

    /* renamed from: h */
    private BigInteger f60650h;

    /* renamed from: n */
    private BigInteger f60651n;
    private byte[] seed;

    public C1340vk(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        this.curve = abstractC1316ux;
        this.f60649G = abstractC1341vl.normalize();
        this.f60651n = bigInteger;
        this.f60650h = BigInteger.valueOf(1L);
        this.seed = null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1340vk)) {
            return false;
        }
        C1340vk c1340vk = (C1340vk) obj;
        return getCurve().equals(c1340vk.getCurve()) && getG().equals(c1340vk.getG());
    }

    public AbstractC1316ux getCurve() {
        return this.curve;
    }

    public AbstractC1341vl getG() {
        return this.f60649G;
    }

    public BigInteger getH() {
        return this.f60650h;
    }

    public BigInteger getN() {
        return this.f60651n;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public int hashCode() {
        return getCurve().hashCode() ^ getG().hashCode();
    }

    public C1340vk(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2) {
        this.curve = abstractC1316ux;
        this.f60649G = abstractC1341vl.normalize();
        this.f60651n = bigInteger;
        this.f60650h = bigInteger2;
        this.seed = null;
    }

    public C1340vk(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        this.curve = abstractC1316ux;
        this.f60649G = abstractC1341vl.normalize();
        this.f60651n = bigInteger;
        this.f60650h = bigInteger2;
        this.seed = bArr;
    }
}
