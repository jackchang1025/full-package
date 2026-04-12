package p000;

import java.math.BigInteger;

/* renamed from: xi */
/* loaded from: classes2.dex */
public abstract class AbstractC1418xi {
    public static final String PRECOMP_NAME = "bc_endo";

    /* renamed from: xi$a0 */
    public static class a0 implements zn0 {
        final /* synthetic */ InterfaceC1318uz val$endomorphism;
        final /* synthetic */ AbstractC1341vl val$p;

        public a0(InterfaceC1318uz interfaceC1318uz, AbstractC1341vl abstractC1341vl) {
            this.val$endomorphism = interfaceC1318uz;
            this.val$p = abstractC1341vl;
        }

        private boolean checkExisting(C1417xh c1417xh, InterfaceC1318uz interfaceC1318uz) {
            return (c1417xh == null || c1417xh.getEndomorphism() != interfaceC1318uz || c1417xh.getMappedPoint() == null) ? false : true;
        }

        @Override // p000.zn0
        public ao0 precompute(ao0 ao0Var) {
            C1417xh c1417xh = ao0Var instanceof C1417xh ? (C1417xh) ao0Var : null;
            if (checkExisting(c1417xh, this.val$endomorphism)) {
                return c1417xh;
            }
            AbstractC1341vl map = this.val$endomorphism.getPointMap().map(this.val$p);
            C1417xh c1417xh2 = new C1417xh();
            c1417xh2.setEndomorphism(this.val$endomorphism);
            c1417xh2.setMappedPoint(map);
            return c1417xh2;
        }
    }

    private static BigInteger calculateB(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        boolean z = bigInteger2.signum() < 0;
        BigInteger bigIntegerMultiply = bigInteger.multiply(bigInteger2.abs());
        boolean zTestBit = bigIntegerMultiply.testBit(i - 1);
        BigInteger bigIntegerShiftRight = bigIntegerMultiply.shiftRight(i);
        if (zTestBit) {
            bigIntegerShiftRight = bigIntegerShiftRight.add(InterfaceC1315uw.ONE);
        }
        return z ? bigIntegerShiftRight.negate() : bigIntegerShiftRight;
    }

    public static BigInteger[] decomposeScalar(bu0 bu0Var, BigInteger bigInteger) {
        int bits = bu0Var.getBits();
        BigInteger bigIntegerCalculateB = calculateB(bigInteger, bu0Var.getG1(), bits);
        BigInteger bigIntegerCalculateB2 = calculateB(bigInteger, bu0Var.getG2(), bits);
        return new BigInteger[]{bigInteger.subtract(bigIntegerCalculateB.multiply(bu0Var.getV1A()).add(bigIntegerCalculateB2.multiply(bu0Var.getV2A()))), bigIntegerCalculateB.multiply(bu0Var.getV1B()).add(bigIntegerCalculateB2.multiply(bu0Var.getV2B())).negate()};
    }

    public static AbstractC1341vl mapPoint(InterfaceC1318uz interfaceC1318uz, AbstractC1341vl abstractC1341vl) {
        return ((C1417xh) abstractC1341vl.getCurve().precompute(abstractC1341vl, PRECOMP_NAME, new a0(interfaceC1318uz, abstractC1341vl))).getMappedPoint();
    }
}
