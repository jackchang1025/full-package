package p000;

import java.math.BigInteger;

/* renamed from: zm */
/* loaded from: classes2.dex */
public class C1522zm extends AbstractC0485f7 {
    @Override // p000.AbstractC0485f7
    public AbstractC1341vl multiplyPositive(AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        if (bigInteger.bitLength() > C1524zo.getCombSize(curve)) {
            throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        C1523zn c1523znPrecompute = C1524zo.precompute(abstractC1341vl);
        InterfaceC1334ve lookupTable = c1523znPrecompute.getLookupTable();
        int width = c1523znPrecompute.getWidth();
        int i = ((r1 + width) - 1) / width;
        AbstractC1341vl infinity = curve.getInfinity();
        int i2 = width * i;
        int[] iArrFromBigInteger = yh0.fromBigInteger(i2, bigInteger);
        int i3 = i2 - 1;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = 0;
            for (int i6 = i3 - i4; i6 >= 0; i6 -= i) {
                int i7 = iArrFromBigInteger[i6 >>> 5] >>> (i6 & 31);
                i5 = ((i5 ^ (i7 >>> 1)) << 1) ^ i7;
            }
            infinity = infinity.twicePlus(lookupTable.lookup(i5));
        }
        return infinity.add(c1523znPrecompute.getOffset());
    }
}
