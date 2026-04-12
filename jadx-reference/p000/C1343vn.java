package p000;

import java.math.BigInteger;

/* renamed from: vn */
/* loaded from: classes2.dex */
public class C1343vn extends C1333vd {

    /* renamed from: d */
    private final BigInteger f60659d;

    public C1343vn(BigInteger bigInteger, C1317uy c1317uy) {
        super(true, c1317uy);
        this.f60659d = c1317uy.validatePrivateScalar(bigInteger);
    }

    public BigInteger getD() {
        return this.f60659d;
    }
}
