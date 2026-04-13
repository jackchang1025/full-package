package org.bouncycastle.math.ec;

import java.math.BigInteger;

/* loaded from: classes.dex */
public abstract class WNafUtil {
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = {13, 41, 121, 337, 897, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    private static final ECPoint[] EMPTY_POINTS = new ECPoint[0];
    private static final int MAX_WIDTH = 16;
    public static final String PRECOMP_NAME = "bc_wnaf";

    public static void configureBasepoint(ECPoint eCPoint) {
        ECCurve curve = eCPoint.getCurve();
        if (curve == null) {
            return;
        }
        BigInteger order = curve.getOrder();
        final int min = Math.min(16, getWindowSize(order == null ? curve.getFieldSize() + 1 : order.bitLength()) + 3);
        curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: org.bouncycastle.math.ec.WNafUtil.1
            @Override // org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                if (wNafPreCompInfo != null && wNafPreCompInfo.getConfWidth() == min) {
                    wNafPreCompInfo.setPromotionCountdown(0);
                    return wNafPreCompInfo;
                }
                WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                wNafPreCompInfo2.setPromotionCountdown(0);
                wNafPreCompInfo2.setConfWidth(min);
                if (wNafPreCompInfo != null) {
                    wNafPreCompInfo2.setPreComp(wNafPreCompInfo.getPreComp());
                    wNafPreCompInfo2.setPreCompNeg(wNafPreCompInfo.getPreCompNeg());
                    wNafPreCompInfo2.setTwice(wNafPreCompInfo.getTwice());
                    wNafPreCompInfo2.setWidth(wNafPreCompInfo.getWidth());
                }
                return wNafPreCompInfo2;
            }
        });
    }

    public static int[] generateCompactNaf(BigInteger bigInteger) {
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength();
        int i2 = bitLength >> 1;
        int[] iArr = new int[i2];
        BigInteger xor = add.xor(bigInteger);
        int i3 = bitLength - 1;
        int i4 = 0;
        int i5 = 1;
        int i6 = 0;
        while (i5 < i3) {
            if (xor.testBit(i5)) {
                iArr[i4] = i6 | ((bigInteger.testBit(i5) ? -1 : 1) << 16);
                i5++;
                i6 = 1;
                i4++;
            } else {
                i6++;
            }
            i5++;
        }
        int i7 = i4 + 1;
        iArr[i4] = 65536 | i6;
        return i2 > i7 ? trim(iArr, i7) : iArr;
    }

    public static int[] generateCompactWindowNaf(int i2, BigInteger bigInteger) {
        if (i2 == 2) {
            return generateCompactNaf(bigInteger);
        }
        if (i2 < 2 || i2 > 16) {
            throw new IllegalArgumentException("'width' must be in the range [2, 16]");
        }
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        int bitLength = (bigInteger.bitLength() / i2) + 1;
        int[] iArr = new int[bitLength];
        int i3 = 1 << i2;
        int i4 = i3 - 1;
        int i5 = i3 >>> 1;
        int i6 = 0;
        int i7 = 0;
        boolean z2 = false;
        while (i6 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i6) == z2) {
                i6++;
            } else {
                bigInteger = bigInteger.shiftRight(i6);
                int intValue = bigInteger.intValue() & i4;
                if (z2) {
                    intValue++;
                }
                z2 = (intValue & i5) != 0;
                if (z2) {
                    intValue -= i3;
                }
                if (i7 > 0) {
                    i6--;
                }
                iArr[i7] = i6 | (intValue << 16);
                i6 = i2;
                i7++;
            }
        }
        return bitLength > i7 ? trim(iArr, i7) : iArr;
    }

    public static byte[] generateJSF(BigInteger bigInteger, BigInteger bigInteger2) {
        int max = Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1;
        byte[] bArr = new byte[max];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if ((i2 | i3) == 0 && bigInteger.bitLength() <= i4 && bigInteger2.bitLength() <= i4) {
                break;
            }
            int intValue = ((bigInteger.intValue() >>> i4) + i2) & 7;
            int intValue2 = ((bigInteger2.intValue() >>> i4) + i3) & 7;
            int i6 = intValue & 1;
            if (i6 != 0) {
                i6 -= intValue & 2;
                if (intValue + i6 == 4 && (intValue2 & 3) == 2) {
                    i6 = -i6;
                }
            }
            int i7 = intValue2 & 1;
            if (i7 != 0) {
                i7 -= intValue2 & 2;
                if (intValue2 + i7 == 4 && (intValue & 3) == 2) {
                    i7 = -i7;
                }
            }
            if ((i2 << 1) == i6 + 1) {
                i2 ^= 1;
            }
            if ((i3 << 1) == i7 + 1) {
                i3 ^= 1;
            }
            i4++;
            if (i4 == 30) {
                bigInteger = bigInteger.shiftRight(30);
                bigInteger2 = bigInteger2.shiftRight(30);
                i4 = 0;
            }
            bArr[i5] = (byte) ((i6 << 4) | (i7 & 15));
            i5++;
        }
        return max > i5 ? trim(bArr, i5) : bArr;
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength() - 1;
        byte[] bArr = new byte[bitLength];
        BigInteger xor = add.xor(bigInteger);
        int i2 = 1;
        while (i2 < bitLength) {
            if (xor.testBit(i2)) {
                bArr[i2 - 1] = (byte) (bigInteger.testBit(i2) ? -1 : 1);
                i2++;
            }
            i2++;
        }
        bArr[bitLength - 1] = 1;
        return bArr;
    }

    public static byte[] generateWindowNaf(int i2, BigInteger bigInteger) {
        if (i2 == 2) {
            return generateNaf(bigInteger);
        }
        if (i2 < 2 || i2 > 8) {
            throw new IllegalArgumentException("'width' must be in the range [2, 8]");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        int bitLength = bigInteger.bitLength() + 1;
        byte[] bArr = new byte[bitLength];
        int i3 = 1 << i2;
        int i4 = i3 - 1;
        int i5 = i3 >>> 1;
        int i6 = 0;
        int i7 = 0;
        boolean z2 = false;
        while (i6 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i6) == z2) {
                i6++;
            } else {
                bigInteger = bigInteger.shiftRight(i6);
                int intValue = bigInteger.intValue() & i4;
                if (z2) {
                    intValue++;
                }
                z2 = (intValue & i5) != 0;
                if (z2) {
                    intValue -= i3;
                }
                if (i7 > 0) {
                    i6--;
                }
                int i8 = i7 + i6;
                bArr[i8] = (byte) intValue;
                i7 = i8 + 1;
                i6 = i2;
            }
        }
        return bitLength > i7 ? trim(bArr, i7) : bArr;
    }

    public static int getNafWeight(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static WNafPreCompInfo getWNafPreCompInfo(ECPoint eCPoint) {
        return getWNafPreCompInfo(eCPoint.getCurve().getPreCompInfo(eCPoint, PRECOMP_NAME));
    }

    public static int getWindowSize(int i2) {
        return getWindowSize(i2, DEFAULT_WINDOW_SIZE_CUTOFFS, 16);
    }

    public static WNafPreCompInfo precompute(final ECPoint eCPoint, final int i2, final boolean z2) {
        final ECCurve curve = eCPoint.getCurve();
        return (WNafPreCompInfo) curve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: org.bouncycastle.math.ec.WNafUtil.2
            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo, int i3, int i4, boolean z3) {
                return wNafPreCompInfo != null && wNafPreCompInfo.getWidth() >= Math.max(wNafPreCompInfo.getConfWidth(), i3) && checkTable(wNafPreCompInfo.getPreComp(), i4) && (!z3 || checkTable(wNafPreCompInfo.getPreCompNeg(), i4));
            }

            private boolean checkTable(ECPoint[] eCPointArr, int i3) {
                return eCPointArr != null && eCPointArr.length >= i3;
            }

            /* JADX WARN: Removed duplicated region for block: B:43:0x00f2 A[LOOP:0: B:42:0x00f0->B:43:0x00f2, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:54:0x0117 A[LOOP:1: B:53:0x0115->B:54:0x0117, LOOP_END] */
            @Override // org.bouncycastle.math.ec.PreCompCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                ECPoint eCPoint2;
                ECPoint[] eCPointArr;
                ECPoint[] eCPointArr2;
                int length;
                ECPoint[] resizeTable;
                int i3;
                ECPoint eCPoint3;
                int coordinateSystem;
                ECFieldElement eCFieldElement = null;
                WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                int max = Math.max(2, Math.min(16, i2));
                if (checkExisting(wNafPreCompInfo, max, 1 << (max - 2), z2)) {
                    wNafPreCompInfo.decrementPromotionCountdown();
                    return wNafPreCompInfo;
                }
                WNafPreCompInfo wNafPreCompInfo2 = new WNafPreCompInfo();
                if (wNafPreCompInfo != null) {
                    wNafPreCompInfo2.setPromotionCountdown(wNafPreCompInfo.decrementPromotionCountdown());
                    wNafPreCompInfo2.setConfWidth(wNafPreCompInfo.getConfWidth());
                    eCPointArr = wNafPreCompInfo.getPreComp();
                    eCPointArr2 = wNafPreCompInfo.getPreCompNeg();
                    eCPoint2 = wNafPreCompInfo.getTwice();
                } else {
                    eCPoint2 = null;
                    eCPointArr = null;
                    eCPointArr2 = null;
                }
                int min = Math.min(16, Math.max(wNafPreCompInfo2.getConfWidth(), max));
                int i4 = 1 << (min - 2);
                int i5 = 0;
                if (eCPointArr == null) {
                    eCPointArr = WNafUtil.EMPTY_POINTS;
                    length = 0;
                } else {
                    length = eCPointArr.length;
                }
                if (length < i4) {
                    eCPointArr = WNafUtil.resizeTable(eCPointArr, i4);
                    if (i4 == 1) {
                        eCPointArr[0] = eCPoint.normalize();
                    } else {
                        if (length == 0) {
                            eCPointArr[0] = eCPoint;
                            i3 = 1;
                        } else {
                            i3 = length;
                        }
                        if (i4 == 2) {
                            eCPointArr[1] = eCPoint.threeTimes();
                        } else {
                            ECPoint eCPoint4 = eCPointArr[i3 - 1];
                            if (eCPoint2 == null) {
                                eCPoint2 = eCPointArr[0].twice();
                                if (!eCPoint2.isInfinity() && ECAlgorithms.isFpCurve(curve) && curve.getFieldSize() >= 64 && ((coordinateSystem = curve.getCoordinateSystem()) == 2 || coordinateSystem == 3 || coordinateSystem == 4)) {
                                    eCFieldElement = eCPoint2.getZCoord(0);
                                    eCPoint3 = curve.createPoint(eCPoint2.getXCoord().toBigInteger(), eCPoint2.getYCoord().toBigInteger());
                                    ECFieldElement square = eCFieldElement.square();
                                    eCPoint4 = eCPoint4.scaleX(square).scaleY(square.multiply(eCFieldElement));
                                    if (length == 0) {
                                        eCPointArr[0] = eCPoint4;
                                    }
                                    while (i3 < i4) {
                                        eCPoint4 = eCPoint4.add(eCPoint3);
                                        eCPointArr[i3] = eCPoint4;
                                        i3++;
                                    }
                                }
                            }
                            eCPoint3 = eCPoint2;
                            while (i3 < i4) {
                            }
                        }
                        curve.normalizeAll(eCPointArr, length, i4 - length, eCFieldElement);
                    }
                }
                if (z2) {
                    if (eCPointArr2 == null) {
                        resizeTable = new ECPoint[i4];
                    } else {
                        i5 = eCPointArr2.length;
                        if (i5 < i4) {
                            resizeTable = WNafUtil.resizeTable(eCPointArr2, i4);
                        }
                        while (i5 < i4) {
                            eCPointArr2[i5] = eCPointArr[i5].negate();
                            i5++;
                        }
                    }
                    eCPointArr2 = resizeTable;
                    while (i5 < i4) {
                    }
                }
                wNafPreCompInfo2.setPreComp(eCPointArr);
                wNafPreCompInfo2.setPreCompNeg(eCPointArr2);
                wNafPreCompInfo2.setTwice(eCPoint2);
                wNafPreCompInfo2.setWidth(min);
                return wNafPreCompInfo2;
            }
        });
    }

    public static WNafPreCompInfo precomputeWithPointMap(ECPoint eCPoint, final ECPointMap eCPointMap, final WNafPreCompInfo wNafPreCompInfo, final boolean z2) {
        return (WNafPreCompInfo) eCPoint.getCurve().precompute(eCPoint, PRECOMP_NAME, new PreCompCallback() { // from class: org.bouncycastle.math.ec.WNafUtil.3
            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo2, int i2, int i3, boolean z3) {
                return wNafPreCompInfo2 != null && wNafPreCompInfo2.getWidth() >= i2 && checkTable(wNafPreCompInfo2.getPreComp(), i3) && (!z3 || checkTable(wNafPreCompInfo2.getPreCompNeg(), i3));
            }

            private boolean checkTable(ECPoint[] eCPointArr, int i2) {
                return eCPointArr != null && eCPointArr.length >= i2;
            }

            @Override // org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                WNafPreCompInfo wNafPreCompInfo2 = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo) preCompInfo : null;
                int width = WNafPreCompInfo.this.getWidth();
                if (checkExisting(wNafPreCompInfo2, width, WNafPreCompInfo.this.getPreComp().length, z2)) {
                    wNafPreCompInfo2.decrementPromotionCountdown();
                    return wNafPreCompInfo2;
                }
                WNafPreCompInfo wNafPreCompInfo3 = new WNafPreCompInfo();
                wNafPreCompInfo3.setPromotionCountdown(WNafPreCompInfo.this.getPromotionCountdown());
                ECPoint twice = WNafPreCompInfo.this.getTwice();
                if (twice != null) {
                    wNafPreCompInfo3.setTwice(eCPointMap.map(twice));
                }
                ECPoint[] preComp = WNafPreCompInfo.this.getPreComp();
                int length = preComp.length;
                ECPoint[] eCPointArr = new ECPoint[length];
                for (int i2 = 0; i2 < preComp.length; i2++) {
                    eCPointArr[i2] = eCPointMap.map(preComp[i2]);
                }
                wNafPreCompInfo3.setPreComp(eCPointArr);
                wNafPreCompInfo3.setWidth(width);
                if (z2) {
                    ECPoint[] eCPointArr2 = new ECPoint[length];
                    for (int i3 = 0; i3 < length; i3++) {
                        eCPointArr2[i3] = eCPointArr[i3].negate();
                    }
                    wNafPreCompInfo3.setPreCompNeg(eCPointArr2);
                }
                return wNafPreCompInfo3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ECPoint[] resizeTable(ECPoint[] eCPointArr, int i2) {
        ECPoint[] eCPointArr2 = new ECPoint[i2];
        System.arraycopy(eCPointArr, 0, eCPointArr2, 0, eCPointArr.length);
        return eCPointArr2;
    }

    private static byte[] trim(byte[] bArr, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        return bArr2;
    }

    public static WNafPreCompInfo getWNafPreCompInfo(PreCompInfo preCompInfo) {
        if (preCompInfo instanceof WNafPreCompInfo) {
            return (WNafPreCompInfo) preCompInfo;
        }
        return null;
    }

    public static int getWindowSize(int i2, int i3) {
        return getWindowSize(i2, DEFAULT_WINDOW_SIZE_CUTOFFS, i3);
    }

    private static int[] trim(int[] iArr, int i2) {
        int[] iArr2 = new int[i2];
        System.arraycopy(iArr, 0, iArr2, 0, i2);
        return iArr2;
    }

    public static int getWindowSize(int i2, int[] iArr) {
        return getWindowSize(i2, iArr, 16);
    }

    public static int getWindowSize(int i2, int[] iArr, int i3) {
        int i4 = 0;
        while (i4 < iArr.length && i2 >= iArr[i4]) {
            i4++;
        }
        return Math.max(2, Math.min(i3, i4 + 2));
    }
}
