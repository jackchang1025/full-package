package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public class WNafL2RMultiplier extends AbstractECMultiplier {
    @Override // org.bouncycastle.math.ec.AbstractECMultiplier
    public ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        ECPoint eCPoint2;
        WNafPreCompInfo precompute = WNafUtil.precompute(eCPoint, WNafUtil.getWindowSize(bigInteger.bitLength()), true);
        ECPoint[] preComp = precompute.getPreComp();
        ECPoint[] preCompNeg = precompute.getPreCompNeg();
        int width = precompute.getWidth();
        int[] generateCompactWindowNaf = WNafUtil.generateCompactWindowNaf(width, bigInteger);
        ECPoint infinity = eCPoint.getCurve().getInfinity();
        int length = generateCompactWindowNaf.length;
        if (length > 1) {
            length--;
            int i2 = generateCompactWindowNaf[length];
            int i3 = i2 >> 16;
            int i4 = i2 & 65535;
            int abs = Math.abs(i3);
            ECPoint[] eCPointArr = i3 < 0 ? preCompNeg : preComp;
            if ((abs << 2) < (1 << width)) {
                int numberOfLeadingZeros = 32 - Integers.numberOfLeadingZeros(abs);
                int i5 = width - numberOfLeadingZeros;
                eCPoint2 = eCPointArr[((1 << (width - 1)) - 1) >>> 1].add(eCPointArr[(((abs ^ (1 << (numberOfLeadingZeros - 1))) << i5) + 1) >>> 1]);
                i4 -= i5;
            } else {
                eCPoint2 = eCPointArr[abs >>> 1];
            }
            infinity = eCPoint2.timesPow2(i4);
        }
        while (length > 0) {
            length--;
            int i6 = generateCompactWindowNaf[length];
            int i7 = i6 >> 16;
            infinity = infinity.twicePlus((i7 < 0 ? preCompNeg : preComp)[Math.abs(i7) >>> 1]).timesPow2(i6 & 65535);
        }
        return infinity;
    }
}
