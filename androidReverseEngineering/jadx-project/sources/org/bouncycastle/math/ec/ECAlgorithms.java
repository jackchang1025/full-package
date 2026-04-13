package org.bouncycastle.math.ec;

import android.sun.security.util.DerValue;
import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.endo.ECEndomorphism;
import org.bouncycastle.math.ec.endo.EndoUtil;
import org.bouncycastle.math.ec.endo.GLVEndomorphism;
import org.bouncycastle.math.field.FiniteField;
import org.bouncycastle.math.field.PolynomialExtensionField;
import org.bouncycastle.math.raw.Nat;

/* loaded from: classes.dex */
public class ECAlgorithms {
    public static ECPoint cleanPoint(ECCurve eCCurve, ECPoint eCPoint) {
        if (eCCurve.equals(eCPoint.getCurve())) {
            return eCCurve.decodePoint(eCPoint.getEncoded(false));
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    public static ECPoint implCheckResult(ECPoint eCPoint) {
        if (eCPoint.isValidPartial()) {
            return eCPoint;
        }
        throw new IllegalStateException("Invalid result");
    }

    private static ECPoint implShamirsTrickFixedPoint(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        ECPoint add;
        ECPoint offset;
        ECCurve curve = eCPoint.getCurve();
        int combSize = FixedPointUtil.getCombSize(curve);
        if (bigInteger.bitLength() > combSize || bigInteger2.bitLength() > combSize) {
            throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        FixedPointPreCompInfo precompute = FixedPointUtil.precompute(eCPoint);
        FixedPointPreCompInfo precompute2 = FixedPointUtil.precompute(eCPoint2);
        ECLookupTable lookupTable = precompute.getLookupTable();
        ECLookupTable lookupTable2 = precompute2.getLookupTable();
        int width = precompute.getWidth();
        if (width != precompute2.getWidth()) {
            FixedPointCombMultiplier fixedPointCombMultiplier = new FixedPointCombMultiplier();
            add = fixedPointCombMultiplier.multiply(eCPoint, bigInteger);
            offset = fixedPointCombMultiplier.multiply(eCPoint2, bigInteger2);
        } else {
            int i2 = ((combSize + width) - 1) / width;
            ECPoint infinity = curve.getInfinity();
            int i3 = width * i2;
            int[] fromBigInteger = Nat.fromBigInteger(i3, bigInteger);
            int[] fromBigInteger2 = Nat.fromBigInteger(i3, bigInteger2);
            int i4 = i3 - 1;
            for (int i5 = 0; i5 < i2; i5++) {
                int i6 = 0;
                int i7 = 0;
                for (int i8 = i4 - i5; i8 >= 0; i8 -= i2) {
                    int i9 = i8 >>> 5;
                    int i10 = i8 & 31;
                    int i11 = fromBigInteger[i9] >>> i10;
                    i6 = ((i6 ^ (i11 >>> 1)) << 1) ^ i11;
                    int i12 = fromBigInteger2[i9] >>> i10;
                    i7 = ((i7 ^ (i12 >>> 1)) << 1) ^ i12;
                }
                infinity = infinity.twicePlus(lookupTable.lookupVar(i6).add(lookupTable2.lookupVar(i7)));
            }
            add = infinity.add(precompute.getOffset());
            offset = precompute2.getOffset();
        }
        return add.add(offset);
    }

    public static ECPoint implShamirsTrickJsf(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        ECCurve curve = eCPoint.getCurve();
        ECPoint infinity = curve.getInfinity();
        ECPoint[] eCPointArr = {eCPoint2, eCPoint.subtract(eCPoint2), eCPoint, eCPoint.add(eCPoint2)};
        curve.normalizeAll(eCPointArr);
        ECPoint[] eCPointArr2 = {eCPointArr[3].negate(), eCPointArr[2].negate(), eCPointArr[1].negate(), eCPointArr[0].negate(), infinity, eCPointArr[0], eCPointArr[1], eCPointArr[2], eCPointArr[3]};
        byte[] generateJSF = WNafUtil.generateJSF(bigInteger, bigInteger2);
        int length = generateJSF.length;
        while (true) {
            length--;
            if (length < 0) {
                return infinity;
            }
            byte b = generateJSF[length];
            infinity = infinity.twicePlus(eCPointArr2[(((b << DerValue.tag_GeneralizedTime) >> 28) * 3) + 4 + ((b << DerValue.tag_UniversalString) >> 28)]);
        }
    }

    public static ECPoint implShamirsTrickWNaf(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        boolean z2 = bigInteger.signum() < 0;
        boolean z3 = bigInteger2.signum() < 0;
        BigInteger abs = bigInteger.abs();
        BigInteger abs2 = bigInteger2.abs();
        int windowSize = WNafUtil.getWindowSize(abs.bitLength(), 8);
        int windowSize2 = WNafUtil.getWindowSize(abs2.bitLength(), 8);
        WNafPreCompInfo precompute = WNafUtil.precompute(eCPoint, windowSize, true);
        WNafPreCompInfo precompute2 = WNafUtil.precompute(eCPoint2, windowSize2, true);
        int combSize = FixedPointUtil.getCombSize(eCPoint.getCurve());
        if (!z2 && !z3 && bigInteger.bitLength() <= combSize && bigInteger2.bitLength() <= combSize && precompute.isPromoted() && precompute2.isPromoted()) {
            return implShamirsTrickFixedPoint(eCPoint, bigInteger, eCPoint2, bigInteger2);
        }
        int min = Math.min(8, precompute.getWidth());
        int min2 = Math.min(8, precompute2.getWidth());
        return implShamirsTrickWNaf(z2 ? precompute.getPreCompNeg() : precompute.getPreComp(), z2 ? precompute.getPreComp() : precompute.getPreCompNeg(), WNafUtil.generateWindowNaf(min, abs), z3 ? precompute2.getPreCompNeg() : precompute2.getPreComp(), z3 ? precompute2.getPreComp() : precompute2.getPreCompNeg(), WNafUtil.generateWindowNaf(min2, abs2));
    }

    public static ECPoint implSumOfMultiplies(ECEndomorphism eCEndomorphism, ECPoint[] eCPointArr, BigInteger[] bigIntegerArr) {
        ECPoint[] eCPointArr2 = eCPointArr;
        int length = eCPointArr2.length;
        int i2 = length << 1;
        boolean[] zArr = new boolean[i2];
        WNafPreCompInfo[] wNafPreCompInfoArr = new WNafPreCompInfo[i2];
        byte[][] bArr = new byte[i2][];
        ECPointMap pointMap = eCEndomorphism.getPointMap();
        int i3 = 0;
        while (i3 < length) {
            int i4 = i3 << 1;
            int i5 = i4 + 1;
            BigInteger bigInteger = bigIntegerArr[i4];
            zArr[i4] = bigInteger.signum() < 0;
            BigInteger abs = bigInteger.abs();
            BigInteger bigInteger2 = bigIntegerArr[i5];
            zArr[i5] = bigInteger2.signum() < 0;
            BigInteger abs2 = bigInteger2.abs();
            int windowSize = WNafUtil.getWindowSize(Math.max(abs.bitLength(), abs2.bitLength()), 8);
            ECPoint eCPoint = eCPointArr2[i3];
            WNafPreCompInfo precompute = WNafUtil.precompute(eCPoint, windowSize, true);
            WNafPreCompInfo precomputeWithPointMap = WNafUtil.precomputeWithPointMap(EndoUtil.mapPoint(eCEndomorphism, eCPoint), pointMap, precompute, true);
            int min = Math.min(8, precompute.getWidth());
            int min2 = Math.min(8, precomputeWithPointMap.getWidth());
            wNafPreCompInfoArr[i4] = precompute;
            wNafPreCompInfoArr[i5] = precomputeWithPointMap;
            bArr[i4] = WNafUtil.generateWindowNaf(min, abs);
            bArr[i5] = WNafUtil.generateWindowNaf(min2, abs2);
            i3++;
            eCPointArr2 = eCPointArr;
        }
        return implSumOfMultiplies(zArr, wNafPreCompInfoArr, bArr);
    }

    public static ECPoint implSumOfMultipliesGLV(ECPoint[] eCPointArr, BigInteger[] bigIntegerArr, GLVEndomorphism gLVEndomorphism) {
        BigInteger order = eCPointArr[0].getCurve().getOrder();
        int length = eCPointArr.length;
        int i2 = length << 1;
        BigInteger[] bigIntegerArr2 = new BigInteger[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            BigInteger[] decomposeScalar = gLVEndomorphism.decomposeScalar(bigIntegerArr[i4].mod(order));
            int i5 = i3 + 1;
            bigIntegerArr2[i3] = decomposeScalar[0];
            i3 = i5 + 1;
            bigIntegerArr2[i5] = decomposeScalar[1];
        }
        if (gLVEndomorphism.hasEfficientPointMap()) {
            return implSumOfMultiplies(gLVEndomorphism, eCPointArr, bigIntegerArr2);
        }
        ECPoint[] eCPointArr2 = new ECPoint[i2];
        int i6 = 0;
        for (ECPoint eCPoint : eCPointArr) {
            ECPoint mapPoint = EndoUtil.mapPoint(gLVEndomorphism, eCPoint);
            int i7 = i6 + 1;
            eCPointArr2[i6] = eCPoint;
            i6 = i7 + 1;
            eCPointArr2[i7] = mapPoint;
        }
        return implSumOfMultiplies(eCPointArr2, bigIntegerArr2);
    }

    public static ECPoint importPoint(ECCurve eCCurve, ECPoint eCPoint) {
        if (eCCurve.equals(eCPoint.getCurve())) {
            return eCCurve.importPoint(eCPoint);
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    public static boolean isF2mCurve(ECCurve eCCurve) {
        return isF2mField(eCCurve.getField());
    }

    public static boolean isF2mField(FiniteField finiteField) {
        return finiteField.getDimension() > 1 && finiteField.getCharacteristic().equals(ECConstants.TWO) && (finiteField instanceof PolynomialExtensionField);
    }

    public static boolean isFpCurve(ECCurve eCCurve) {
        return isFpField(eCCurve.getField());
    }

    public static boolean isFpField(FiniteField finiteField) {
        return finiteField.getDimension() == 1;
    }

    public static void montgomeryTrick(ECFieldElement[] eCFieldElementArr, int i2, int i3) {
        montgomeryTrick(eCFieldElementArr, i2, i3, null);
    }

    public static ECPoint referenceMultiply(ECPoint eCPoint, BigInteger bigInteger) {
        BigInteger abs = bigInteger.abs();
        ECPoint infinity = eCPoint.getCurve().getInfinity();
        int bitLength = abs.bitLength();
        if (bitLength > 0) {
            if (abs.testBit(0)) {
                infinity = eCPoint;
            }
            for (int i2 = 1; i2 < bitLength; i2++) {
                eCPoint = eCPoint.twice();
                if (abs.testBit(i2)) {
                    infinity = infinity.add(eCPoint);
                }
            }
        }
        return bigInteger.signum() < 0 ? infinity.negate() : infinity;
    }

    public static ECPoint shamirsTrick(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        return implCheckResult(implShamirsTrickJsf(eCPoint, bigInteger, importPoint(eCPoint.getCurve(), eCPoint2), bigInteger2));
    }

    public static ECPoint sumOfMultiplies(ECPoint[] eCPointArr, BigInteger[] bigIntegerArr) {
        if (eCPointArr != null && bigIntegerArr != null && eCPointArr.length == bigIntegerArr.length) {
            if (eCPointArr.length >= 1) {
                int length = eCPointArr.length;
                if (length == 1) {
                    return eCPointArr[0].multiply(bigIntegerArr[0]);
                }
                if (length == 2) {
                    return sumOfTwoMultiplies(eCPointArr[0], bigIntegerArr[0], eCPointArr[1], bigIntegerArr[1]);
                }
                ECPoint eCPoint = eCPointArr[0];
                ECCurve curve = eCPoint.getCurve();
                ECPoint[] eCPointArr2 = new ECPoint[length];
                eCPointArr2[0] = eCPoint;
                for (int i2 = 1; i2 < length; i2++) {
                    eCPointArr2[i2] = importPoint(curve, eCPointArr[i2]);
                }
                ECEndomorphism endomorphism = curve.getEndomorphism();
                return endomorphism instanceof GLVEndomorphism ? implCheckResult(implSumOfMultipliesGLV(eCPointArr2, bigIntegerArr, (GLVEndomorphism) endomorphism)) : implCheckResult(implSumOfMultiplies(eCPointArr2, bigIntegerArr));
            }
        }
        throw new IllegalArgumentException("point and scalar arrays should be non-null, and of equal, non-zero, length");
    }

    public static ECPoint sumOfTwoMultiplies(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        ECPoint implSumOfMultipliesGLV;
        ECCurve curve = eCPoint.getCurve();
        ECPoint importPoint = importPoint(curve, eCPoint2);
        if ((curve instanceof ECCurve.AbstractF2m) && ((ECCurve.AbstractF2m) curve).isKoblitz()) {
            implSumOfMultipliesGLV = eCPoint.multiply(bigInteger).add(importPoint.multiply(bigInteger2));
        } else {
            ECEndomorphism endomorphism = curve.getEndomorphism();
            implSumOfMultipliesGLV = endomorphism instanceof GLVEndomorphism ? implSumOfMultipliesGLV(new ECPoint[]{eCPoint, importPoint}, new BigInteger[]{bigInteger, bigInteger2}, (GLVEndomorphism) endomorphism) : implShamirsTrickWNaf(eCPoint, bigInteger, importPoint, bigInteger2);
        }
        return implCheckResult(implSumOfMultipliesGLV);
    }

    public static ECPoint validatePoint(ECPoint eCPoint) {
        if (eCPoint.isValid()) {
            return eCPoint;
        }
        throw new IllegalStateException("Invalid point");
    }

    public static ECPoint implShamirsTrickWNaf(ECEndomorphism eCEndomorphism, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        boolean z2 = bigInteger.signum() < 0;
        boolean z3 = bigInteger2.signum() < 0;
        BigInteger abs = bigInteger.abs();
        BigInteger abs2 = bigInteger2.abs();
        WNafPreCompInfo precompute = WNafUtil.precompute(eCPoint, WNafUtil.getWindowSize(Math.max(abs.bitLength(), abs2.bitLength()), 8), true);
        WNafPreCompInfo precomputeWithPointMap = WNafUtil.precomputeWithPointMap(EndoUtil.mapPoint(eCEndomorphism, eCPoint), eCEndomorphism.getPointMap(), precompute, true);
        int min = Math.min(8, precompute.getWidth());
        int min2 = Math.min(8, precomputeWithPointMap.getWidth());
        return implShamirsTrickWNaf(z2 ? precompute.getPreCompNeg() : precompute.getPreComp(), z2 ? precompute.getPreComp() : precompute.getPreCompNeg(), WNafUtil.generateWindowNaf(min, abs), z3 ? precomputeWithPointMap.getPreCompNeg() : precomputeWithPointMap.getPreComp(), z3 ? precomputeWithPointMap.getPreComp() : precomputeWithPointMap.getPreCompNeg(), WNafUtil.generateWindowNaf(min2, abs2));
    }

    public static ECPoint implSumOfMultiplies(ECPoint[] eCPointArr, BigInteger[] bigIntegerArr) {
        int length = eCPointArr.length;
        boolean[] zArr = new boolean[length];
        WNafPreCompInfo[] wNafPreCompInfoArr = new WNafPreCompInfo[length];
        byte[][] bArr = new byte[length][];
        for (int i2 = 0; i2 < length; i2++) {
            BigInteger bigInteger = bigIntegerArr[i2];
            zArr[i2] = bigInteger.signum() < 0;
            BigInteger abs = bigInteger.abs();
            WNafPreCompInfo precompute = WNafUtil.precompute(eCPointArr[i2], WNafUtil.getWindowSize(abs.bitLength(), 8), true);
            int min = Math.min(8, precompute.getWidth());
            wNafPreCompInfoArr[i2] = precompute;
            bArr[i2] = WNafUtil.generateWindowNaf(min, abs);
        }
        return implSumOfMultiplies(zArr, wNafPreCompInfoArr, bArr);
    }

    public static void montgomeryTrick(ECFieldElement[] eCFieldElementArr, int i2, int i3, ECFieldElement eCFieldElement) {
        ECFieldElement[] eCFieldElementArr2 = new ECFieldElement[i3];
        int i4 = 0;
        eCFieldElementArr2[0] = eCFieldElementArr[i2];
        while (true) {
            i4++;
            if (i4 >= i3) {
                break;
            } else {
                eCFieldElementArr2[i4] = eCFieldElementArr2[i4 - 1].multiply(eCFieldElementArr[i2 + i4]);
            }
        }
        int i5 = i4 - 1;
        if (eCFieldElement != null) {
            eCFieldElementArr2[i5] = eCFieldElementArr2[i5].multiply(eCFieldElement);
        }
        ECFieldElement invert = eCFieldElementArr2[i5].invert();
        while (i5 > 0) {
            int i6 = i5 - 1;
            int i7 = i5 + i2;
            ECFieldElement eCFieldElement2 = eCFieldElementArr[i7];
            eCFieldElementArr[i7] = eCFieldElementArr2[i6].multiply(invert);
            invert = invert.multiply(eCFieldElement2);
            i5 = i6;
        }
        eCFieldElementArr[i2] = invert;
    }

    private static ECPoint implShamirsTrickWNaf(ECPoint[] eCPointArr, ECPoint[] eCPointArr2, byte[] bArr, ECPoint[] eCPointArr3, ECPoint[] eCPointArr4, byte[] bArr2) {
        ECPoint eCPoint;
        int max = Math.max(bArr.length, bArr2.length);
        ECPoint infinity = eCPointArr[0].getCurve().getInfinity();
        int i2 = max - 1;
        int i3 = 0;
        ECPoint eCPoint2 = infinity;
        while (i2 >= 0) {
            byte b = i2 < bArr.length ? bArr[i2] : (byte) 0;
            byte b2 = i2 < bArr2.length ? bArr2[i2] : (byte) 0;
            if ((b | b2) == 0) {
                i3++;
            } else {
                if (b != 0) {
                    eCPoint = infinity.add((b < 0 ? eCPointArr2 : eCPointArr)[Math.abs((int) b) >>> 1]);
                } else {
                    eCPoint = infinity;
                }
                if (b2 != 0) {
                    eCPoint = eCPoint.add((b2 < 0 ? eCPointArr4 : eCPointArr3)[Math.abs((int) b2) >>> 1]);
                }
                if (i3 > 0) {
                    eCPoint2 = eCPoint2.timesPow2(i3);
                    i3 = 0;
                }
                eCPoint2 = eCPoint2.twicePlus(eCPoint);
            }
            i2--;
        }
        return i3 > 0 ? eCPoint2.timesPow2(i3) : eCPoint2;
    }

    private static ECPoint implSumOfMultiplies(boolean[] zArr, WNafPreCompInfo[] wNafPreCompInfoArr, byte[][] bArr) {
        int length = bArr.length;
        int i2 = 0;
        for (byte[] bArr2 : bArr) {
            i2 = Math.max(i2, bArr2.length);
        }
        ECPoint infinity = wNafPreCompInfoArr[0].getPreComp()[0].getCurve().getInfinity();
        int i3 = i2 - 1;
        int i4 = 0;
        ECPoint eCPoint = infinity;
        while (i3 >= 0) {
            ECPoint eCPoint2 = infinity;
            for (int i5 = 0; i5 < length; i5++) {
                byte[] bArr3 = bArr[i5];
                byte b = i3 < bArr3.length ? bArr3[i3] : (byte) 0;
                if (b != 0) {
                    int abs = Math.abs((int) b);
                    WNafPreCompInfo wNafPreCompInfo = wNafPreCompInfoArr[i5];
                    eCPoint2 = eCPoint2.add(((b < 0) == zArr[i5] ? wNafPreCompInfo.getPreComp() : wNafPreCompInfo.getPreCompNeg())[abs >>> 1]);
                }
            }
            if (eCPoint2 == infinity) {
                i4++;
            } else {
                if (i4 > 0) {
                    eCPoint = eCPoint.timesPow2(i4);
                    i4 = 0;
                }
                eCPoint = eCPoint.twicePlus(eCPoint2);
            }
            i3--;
        }
        return i4 > 0 ? eCPoint.timesPow2(i4) : eCPoint;
    }
}
