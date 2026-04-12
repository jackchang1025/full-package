package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class c20 extends AbstractC0485f7 {
    protected final AbstractC1316ux curve;
    protected final b20 glvEndomorphism;

    public c20(AbstractC1316ux abstractC1316ux, b20 b20Var) {
        if (abstractC1316ux == null || abstractC1316ux.getOrder() == null) {
            throw new IllegalArgumentException("Need curve with known group order");
        }
        this.curve = abstractC1316ux;
        this.glvEndomorphism = b20Var;
    }

    @Override // p000.AbstractC0485f7
    public AbstractC1341vl multiplyPositive(AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        if (!this.curve.equals(abstractC1341vl.getCurve())) {
            throw new IllegalStateException();
        }
        BigInteger[] bigIntegerArrDecomposeScalar = this.glvEndomorphism.decomposeScalar(bigInteger.mod(abstractC1341vl.getCurve().getOrder()));
        BigInteger bigInteger2 = bigIntegerArrDecomposeScalar[0];
        BigInteger bigInteger3 = bigIntegerArrDecomposeScalar[1];
        return this.glvEndomorphism.hasEfficientPointMap() ? C1314uv.implShamirsTrickWNaf(this.glvEndomorphism, abstractC1341vl, bigInteger2, bigInteger3) : C1314uv.implShamirsTrickWNaf(abstractC1341vl, bigInteger2, AbstractC1418xi.mapPoint(this.glvEndomorphism, abstractC1341vl), bigInteger3);
    }
}
