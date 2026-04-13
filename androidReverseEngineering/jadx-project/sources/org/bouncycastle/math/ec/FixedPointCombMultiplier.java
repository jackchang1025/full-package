package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.raw.Nat;

/* loaded from: classes.dex */
public class FixedPointCombMultiplier extends AbstractECMultiplier {
    @Override // org.bouncycastle.math.ec.AbstractECMultiplier
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        ECCurve curve = eCPoint.getCurve();
        if (bigInteger.bitLength() > FixedPointUtil.getCombSize(curve)) {
            throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        FixedPointPreCompInfo precompute = FixedPointUtil.precompute(eCPoint);
        ECLookupTable lookupTable = precompute.getLookupTable();
        int width = precompute.getWidth();
        int i2 = ((r1 + width) - 1) / width;
        ECPoint infinity = curve.getInfinity();
        int i3 = width * i2;
        int[] fromBigInteger = Nat.fromBigInteger(i3, bigInteger);
        int i4 = i3 - 1;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = 0;
            for (int i7 = i4 - i5; i7 >= 0; i7 -= i2) {
                int i8 = fromBigInteger[i7 >>> 5] >>> (i7 & 31);
                i6 = ((i6 ^ (i8 >>> 1)) << 1) ^ i8;
            }
            infinity = infinity.twicePlus(lookupTable.lookup(i6));
        }
        return infinity.add(precompute.getOffset());
    }
}
