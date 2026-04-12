package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class vd1 extends AbstractC0485f7 {
    @Override // p000.AbstractC0485f7
    public AbstractC1341vl multiplyPositive(AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        AbstractC1341vl abstractC1341vlAdd;
        wd1 wd1VarPrecompute = xd1.precompute(abstractC1341vl, xd1.getWindowSize(bigInteger.bitLength()), true);
        AbstractC1341vl[] preComp = wd1VarPrecompute.getPreComp();
        AbstractC1341vl[] preCompNeg = wd1VarPrecompute.getPreCompNeg();
        int width = wd1VarPrecompute.getWidth();
        int[] iArrGenerateCompactWindowNaf = xd1.generateCompactWindowNaf(width, bigInteger);
        AbstractC1341vl infinity = abstractC1341vl.getCurve().getInfinity();
        int length = iArrGenerateCompactWindowNaf.length;
        if (length > 1) {
            length--;
            int i = iArrGenerateCompactWindowNaf[length];
            int i2 = i >> 16;
            int i3 = i & 65535;
            int iAbs = Math.abs(i2);
            AbstractC1341vl[] abstractC1341vlArr = i2 < 0 ? preCompNeg : preComp;
            if ((iAbs << 2) < (1 << width)) {
                int iNumberOfLeadingZeros = q60.numberOfLeadingZeros(iAbs);
                int i4 = width - (32 - iNumberOfLeadingZeros);
                abstractC1341vlAdd = abstractC1341vlArr[((1 << (width - 1)) - 1) >>> 1].add(abstractC1341vlArr[(((iAbs ^ (1 << (31 - iNumberOfLeadingZeros))) << i4) + 1) >>> 1]);
                i3 -= i4;
            } else {
                abstractC1341vlAdd = abstractC1341vlArr[iAbs >>> 1];
            }
            infinity = abstractC1341vlAdd.timesPow2(i3);
        }
        while (length > 0) {
            length--;
            int i5 = iArrGenerateCompactWindowNaf[length];
            int i6 = i5 >> 16;
            infinity = infinity.twicePlus((i6 < 0 ? preCompNeg : preComp)[Math.abs(i6) >>> 1]).timesPow2(i5 & 65535);
        }
        return infinity;
    }
}
