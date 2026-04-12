package p000;

import java.math.BigInteger;

/* renamed from: f7 */
/* loaded from: classes2.dex */
public abstract class AbstractC0485f7 implements InterfaceC1335vf {
    public AbstractC1341vl checkResult(AbstractC1341vl abstractC1341vl) {
        return C1314uv.implCheckResult(abstractC1341vl);
    }

    @Override // p000.InterfaceC1335vf
    public AbstractC1341vl multiply(AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        int iSignum = bigInteger.signum();
        if (iSignum == 0 || abstractC1341vl.isInfinity()) {
            return abstractC1341vl.getCurve().getInfinity();
        }
        AbstractC1341vl abstractC1341vlMultiplyPositive = multiplyPositive(abstractC1341vl, bigInteger.abs());
        if (iSignum <= 0) {
            abstractC1341vlMultiplyPositive = abstractC1341vlMultiplyPositive.negate();
        }
        return checkResult(abstractC1341vlMultiplyPositive);
    }

    public abstract AbstractC1341vl multiplyPositive(AbstractC1341vl abstractC1341vl, BigInteger bigInteger);
}
