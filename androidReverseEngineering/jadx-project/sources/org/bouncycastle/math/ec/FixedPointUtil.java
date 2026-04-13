package org.bouncycastle.math.ec;

import java.math.BigInteger;

/* loaded from: classes.dex */
public class FixedPointUtil {
    public static final String PRECOMP_NAME = "bc_fixed_point";

    public static int getCombSize(ECCurve eCCurve) {
        BigInteger order = eCCurve.getOrder();
        return order == null ? eCCurve.getFieldSize() + 1 : order.bitLength();
    }

    public static FixedPointPreCompInfo getFixedPointPreCompInfo(PreCompInfo preCompInfo) {
        if (preCompInfo instanceof FixedPointPreCompInfo) {
            return (FixedPointPreCompInfo) preCompInfo;
        }
        return null;
    }

    public static FixedPointPreCompInfo precompute(final ECPoint eCPoint) {
        final ECCurve curve = eCPoint.getCurve();
        return (FixedPointPreCompInfo) curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: org.bouncycastle.math.ec.FixedPointUtil.1
            private boolean checkExisting(FixedPointPreCompInfo fixedPointPreCompInfo, int i2) {
                return fixedPointPreCompInfo != null && checkTable(fixedPointPreCompInfo.getLookupTable(), i2);
            }

            private boolean checkTable(ECLookupTable eCLookupTable, int i2) {
                return eCLookupTable != null && eCLookupTable.getSize() >= i2;
            }

            @Override // org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                FixedPointPreCompInfo fixedPointPreCompInfo = preCompInfo instanceof FixedPointPreCompInfo ? (FixedPointPreCompInfo) preCompInfo : null;
                int combSize = FixedPointUtil.getCombSize(ECCurve.this);
                int i2 = combSize > 250 ? 6 : 5;
                int i3 = 1 << i2;
                if (checkExisting(fixedPointPreCompInfo, i3)) {
                    return fixedPointPreCompInfo;
                }
                int i4 = ((combSize + i2) - 1) / i2;
                ECPoint[] eCPointArr = new ECPoint[i2 + 1];
                eCPointArr[0] = eCPoint;
                for (int i5 = 1; i5 < i2; i5++) {
                    eCPointArr[i5] = eCPointArr[i5 - 1].timesPow2(i4);
                }
                eCPointArr[i2] = eCPointArr[0].subtract(eCPointArr[1]);
                ECCurve.this.normalizeAll(eCPointArr);
                ECPoint[] eCPointArr2 = new ECPoint[i3];
                eCPointArr2[0] = eCPointArr[0];
                for (int i6 = i2 - 1; i6 >= 0; i6--) {
                    ECPoint eCPoint2 = eCPointArr[i6];
                    int i7 = 1 << i6;
                    for (int i8 = i7; i8 < i3; i8 += i7 << 1) {
                        eCPointArr2[i8] = eCPointArr2[i8 - i7].add(eCPoint2);
                    }
                }
                ECCurve.this.normalizeAll(eCPointArr2);
                FixedPointPreCompInfo fixedPointPreCompInfo2 = new FixedPointPreCompInfo();
                fixedPointPreCompInfo2.setLookupTable(ECCurve.this.createCacheSafeLookupTable(eCPointArr2, 0, i3));
                fixedPointPreCompInfo2.setOffset(eCPointArr[i2]);
                fixedPointPreCompInfo2.setWidth(i2);
                return fixedPointPreCompInfo2;
            }
        });
    }
}
