package p000;

import java.math.BigInteger;

/* renamed from: vj */
/* loaded from: classes2.dex */
public class C1339vj extends C1317uy {
    private C0160c5 name;

    public C1339vj(C0160c5 c0160c5, AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        this(c0160c5, abstractC1316ux, abstractC1341vl, bigInteger, InterfaceC1315uw.ONE, null);
    }

    public C0160c5 getName() {
        return this.name;
    }

    public C1339vj(C0160c5 c0160c5, AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2) {
        this(c0160c5, abstractC1316ux, abstractC1341vl, bigInteger, bigInteger2, null);
    }

    public C1339vj(C0160c5 c0160c5, AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr) {
        super(abstractC1316ux, abstractC1341vl, bigInteger, bigInteger2, bArr);
        this.name = c0160c5;
    }

    public C1339vj(C0160c5 c0160c5, C1317uy c1317uy) {
        super(c1317uy.getCurve(), c1317uy.getG(), c1317uy.getN(), c1317uy.getH(), c1317uy.getSeed());
        this.name = c0160c5;
    }

    public C1339vj(C0160c5 c0160c5, bi1 bi1Var) {
        super(bi1Var);
        this.name = c0160c5;
    }
}
