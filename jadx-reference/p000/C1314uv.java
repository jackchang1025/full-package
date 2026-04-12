package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;

/* renamed from: uv */
/* loaded from: classes2.dex */
public class C1314uv {
    public static AbstractC1341vl cleanPoint(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl) {
        if (abstractC1316ux.equals(abstractC1341vl.getCurve())) {
            return abstractC1316ux.decodePoint(abstractC1341vl.getEncoded(false));
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    public static AbstractC1341vl implCheckResult(AbstractC1341vl abstractC1341vl) {
        if (abstractC1341vl.isValidPartial()) {
            return abstractC1341vl;
        }
        throw new IllegalStateException("Invalid result");
    }

    private static AbstractC1341vl implShamirsTrickFixedPoint(AbstractC1341vl abstractC1341vl, BigInteger bigInteger, AbstractC1341vl abstractC1341vl2, BigInteger bigInteger2) {
        AbstractC1341vl abstractC1341vlAdd;
        AbstractC1341vl offset;
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        int combSize = C1524zo.getCombSize(curve);
        if (bigInteger.bitLength() > combSize || bigInteger2.bitLength() > combSize) {
            throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        C1523zn c1523znPrecompute = C1524zo.precompute(abstractC1341vl);
        C1523zn c1523znPrecompute2 = C1524zo.precompute(abstractC1341vl2);
        InterfaceC1334ve lookupTable = c1523znPrecompute.getLookupTable();
        InterfaceC1334ve lookupTable2 = c1523znPrecompute2.getLookupTable();
        int width = c1523znPrecompute.getWidth();
        if (width != c1523znPrecompute2.getWidth()) {
            C1522zm c1522zm = new C1522zm();
            abstractC1341vlAdd = c1522zm.multiply(abstractC1341vl, bigInteger);
            offset = c1522zm.multiply(abstractC1341vl2, bigInteger2);
        } else {
            int i = ((combSize + width) - 1) / width;
            AbstractC1341vl infinity = curve.getInfinity();
            int i2 = width * i;
            int[] iArrFromBigInteger = yh0.fromBigInteger(i2, bigInteger);
            int[] iArrFromBigInteger2 = yh0.fromBigInteger(i2, bigInteger2);
            int i3 = i2 - 1;
            for (int i4 = 0; i4 < i; i4++) {
                int i5 = 0;
                int i6 = 0;
                for (int i7 = i3 - i4; i7 >= 0; i7 -= i) {
                    int i8 = i7 >>> 5;
                    int i9 = i7 & 31;
                    int i10 = iArrFromBigInteger[i8] >>> i9;
                    i5 = ((i5 ^ (i10 >>> 1)) << 1) ^ i10;
                    int i11 = iArrFromBigInteger2[i8] >>> i9;
                    i6 = ((i6 ^ (i11 >>> 1)) << 1) ^ i11;
                }
                infinity = infinity.twicePlus(lookupTable.lookupVar(i5).add(lookupTable2.lookupVar(i6)));
            }
            abstractC1341vlAdd = infinity.add(c1523znPrecompute.getOffset());
            offset = c1523znPrecompute2.getOffset();
        }
        return abstractC1341vlAdd.add(offset);
    }

    public static AbstractC1341vl implShamirsTrickJsf(AbstractC1341vl abstractC1341vl, BigInteger bigInteger, AbstractC1341vl abstractC1341vl2, BigInteger bigInteger2) {
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        AbstractC1341vl infinity = curve.getInfinity();
        AbstractC1341vl[] abstractC1341vlArr = {abstractC1341vl2, abstractC1341vl.subtract(abstractC1341vl2), abstractC1341vl, abstractC1341vl.add(abstractC1341vl2)};
        curve.normalizeAll(abstractC1341vlArr);
        AbstractC1341vl[] abstractC1341vlArr2 = {abstractC1341vlArr[3].negate(), abstractC1341vlArr[2].negate(), abstractC1341vlArr[1].negate(), abstractC1341vlArr[0].negate(), infinity, abstractC1341vlArr[0], abstractC1341vlArr[1], abstractC1341vlArr[2], abstractC1341vlArr[3]};
        byte[] bArrGenerateJSF = xd1.generateJSF(bigInteger, bigInteger2);
        int length = bArrGenerateJSF.length;
        while (true) {
            length--;
            if (length < 0) {
                return infinity;
            }
            byte b = bArrGenerateJSF[length];
            infinity = infinity.twicePlus(abstractC1341vlArr2[(((b << 24) >> 28) * 3) + 4 + ((b << 28) >> 28)]);
        }
    }

    public static AbstractC1341vl implShamirsTrickWNaf(InterfaceC1318uz interfaceC1318uz, AbstractC1341vl abstractC1341vl, BigInteger bigInteger, BigInteger bigInteger2) {
        boolean z = bigInteger.signum() < 0;
        boolean z2 = bigInteger2.signum() < 0;
        BigInteger bigIntegerAbs = bigInteger.abs();
        BigInteger bigIntegerAbs2 = bigInteger2.abs();
        wd1 wd1VarPrecompute = xd1.precompute(abstractC1341vl, xd1.getWindowSize(Math.max(bigIntegerAbs.bitLength(), bigIntegerAbs2.bitLength()), 8), true);
        wd1 wd1VarPrecomputeWithPointMap = xd1.precomputeWithPointMap(AbstractC1418xi.mapPoint(interfaceC1318uz, abstractC1341vl), interfaceC1318uz.getPointMap(), wd1VarPrecompute, true);
        int iMin = Math.min(8, wd1VarPrecompute.getWidth());
        int iMin2 = Math.min(8, wd1VarPrecomputeWithPointMap.getWidth());
        return implShamirsTrickWNaf(z ? wd1VarPrecompute.getPreCompNeg() : wd1VarPrecompute.getPreComp(), z ? wd1VarPrecompute.getPreComp() : wd1VarPrecompute.getPreCompNeg(), xd1.generateWindowNaf(iMin, bigIntegerAbs), z2 ? wd1VarPrecomputeWithPointMap.getPreCompNeg() : wd1VarPrecomputeWithPointMap.getPreComp(), z2 ? wd1VarPrecomputeWithPointMap.getPreComp() : wd1VarPrecomputeWithPointMap.getPreCompNeg(), xd1.generateWindowNaf(iMin2, bigIntegerAbs2));
    }

    public static AbstractC1341vl implSumOfMultiplies(InterfaceC1318uz interfaceC1318uz, AbstractC1341vl[] abstractC1341vlArr, BigInteger[] bigIntegerArr) {
        AbstractC1341vl[] abstractC1341vlArr2 = abstractC1341vlArr;
        int length = abstractC1341vlArr2.length;
        int i = length << 1;
        boolean[] zArr = new boolean[i];
        wd1[] wd1VarArr = new wd1[i];
        byte[][] bArr = new byte[i][];
        InterfaceC1342vm pointMap = interfaceC1318uz.getPointMap();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 << 1;
            int i4 = i3 + 1;
            BigInteger bigInteger = bigIntegerArr[i3];
            zArr[i3] = bigInteger.signum() < 0;
            BigInteger bigIntegerAbs = bigInteger.abs();
            BigInteger bigInteger2 = bigIntegerArr[i4];
            zArr[i4] = bigInteger2.signum() < 0;
            BigInteger bigIntegerAbs2 = bigInteger2.abs();
            int windowSize = xd1.getWindowSize(Math.max(bigIntegerAbs.bitLength(), bigIntegerAbs2.bitLength()), 8);
            AbstractC1341vl abstractC1341vl = abstractC1341vlArr2[i2];
            wd1 wd1VarPrecompute = xd1.precompute(abstractC1341vl, windowSize, true);
            wd1 wd1VarPrecomputeWithPointMap = xd1.precomputeWithPointMap(AbstractC1418xi.mapPoint(interfaceC1318uz, abstractC1341vl), pointMap, wd1VarPrecompute, true);
            int iMin = Math.min(8, wd1VarPrecompute.getWidth());
            int iMin2 = Math.min(8, wd1VarPrecomputeWithPointMap.getWidth());
            wd1VarArr[i3] = wd1VarPrecompute;
            wd1VarArr[i4] = wd1VarPrecomputeWithPointMap;
            bArr[i3] = xd1.generateWindowNaf(iMin, bigIntegerAbs);
            bArr[i4] = xd1.generateWindowNaf(iMin2, bigIntegerAbs2);
            i2++;
            abstractC1341vlArr2 = abstractC1341vlArr;
        }
        return implSumOfMultiplies(zArr, wd1VarArr, bArr);
    }

    public static AbstractC1341vl implSumOfMultipliesGLV(AbstractC1341vl[] abstractC1341vlArr, BigInteger[] bigIntegerArr, b20 b20Var) {
        BigInteger order = abstractC1341vlArr[0].getCurve().getOrder();
        int length = abstractC1341vlArr.length;
        int i = length << 1;
        BigInteger[] bigIntegerArr2 = new BigInteger[i];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            BigInteger[] bigIntegerArrDecomposeScalar = b20Var.decomposeScalar(bigIntegerArr[i3].mod(order));
            int i4 = i2 + 1;
            bigIntegerArr2[i2] = bigIntegerArrDecomposeScalar[0];
            i2 += 2;
            bigIntegerArr2[i4] = bigIntegerArrDecomposeScalar[1];
        }
        if (b20Var.hasEfficientPointMap()) {
            return implSumOfMultiplies(b20Var, abstractC1341vlArr, bigIntegerArr2);
        }
        AbstractC1341vl[] abstractC1341vlArr2 = new AbstractC1341vl[i];
        int i5 = 0;
        for (AbstractC1341vl abstractC1341vl : abstractC1341vlArr) {
            AbstractC1341vl abstractC1341vlMapPoint = AbstractC1418xi.mapPoint(b20Var, abstractC1341vl);
            int i6 = i5 + 1;
            abstractC1341vlArr2[i5] = abstractC1341vl;
            i5 += 2;
            abstractC1341vlArr2[i6] = abstractC1341vlMapPoint;
        }
        return implSumOfMultiplies(abstractC1341vlArr2, bigIntegerArr2);
    }

    public static AbstractC1341vl importPoint(AbstractC1316ux abstractC1316ux, AbstractC1341vl abstractC1341vl) {
        if (abstractC1316ux.equals(abstractC1341vl.getCurve())) {
            return abstractC1316ux.importPoint(abstractC1341vl);
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    public static boolean isF2mCurve(AbstractC1316ux abstractC1316ux) {
        return isF2mField(abstractC1316ux.getField());
    }

    public static boolean isF2mField(InterfaceC1519zj interfaceC1519zj) {
        return interfaceC1519zj.getDimension() > 1 && interfaceC1519zj.getCharacteristic().equals(InterfaceC1315uw.TWO) && (interfaceC1519zj instanceof rn0);
    }

    public static boolean isFpCurve(AbstractC1316ux abstractC1316ux) {
        return isFpField(abstractC1316ux.getField());
    }

    public static boolean isFpField(InterfaceC1519zj interfaceC1519zj) {
        return interfaceC1519zj.getDimension() == 1;
    }

    public static void montgomeryTrick(AbstractC1330va[] abstractC1330vaArr, int i, int i2) {
        montgomeryTrick(abstractC1330vaArr, i, i2, null);
    }

    public static AbstractC1341vl referenceMultiply(AbstractC1341vl abstractC1341vl, BigInteger bigInteger) {
        BigInteger bigIntegerAbs = bigInteger.abs();
        AbstractC1341vl infinity = abstractC1341vl.getCurve().getInfinity();
        int iBitLength = bigIntegerAbs.bitLength();
        if (iBitLength > 0) {
            if (bigIntegerAbs.testBit(0)) {
                infinity = abstractC1341vl;
            }
            for (int i = 1; i < iBitLength; i++) {
                abstractC1341vl = abstractC1341vl.twice();
                if (bigIntegerAbs.testBit(i)) {
                    infinity = infinity.add(abstractC1341vl);
                }
            }
        }
        return bigInteger.signum() < 0 ? infinity.negate() : infinity;
    }

    public static AbstractC1341vl shamirsTrick(AbstractC1341vl abstractC1341vl, BigInteger bigInteger, AbstractC1341vl abstractC1341vl2, BigInteger bigInteger2) {
        return implCheckResult(implShamirsTrickJsf(abstractC1341vl, bigInteger, importPoint(abstractC1341vl.getCurve(), abstractC1341vl2), bigInteger2));
    }

    public static AbstractC1341vl sumOfMultiplies(AbstractC1341vl[] abstractC1341vlArr, BigInteger[] bigIntegerArr) {
        if (abstractC1341vlArr != null && bigIntegerArr != null && abstractC1341vlArr.length == bigIntegerArr.length) {
            if (abstractC1341vlArr.length >= 1) {
                int length = abstractC1341vlArr.length;
                if (length == 1) {
                    return abstractC1341vlArr[0].multiply(bigIntegerArr[0]);
                }
                if (length == 2) {
                    return sumOfTwoMultiplies(abstractC1341vlArr[0], bigIntegerArr[0], abstractC1341vlArr[1], bigIntegerArr[1]);
                }
                AbstractC1341vl abstractC1341vl = abstractC1341vlArr[0];
                AbstractC1316ux curve = abstractC1341vl.getCurve();
                AbstractC1341vl[] abstractC1341vlArr2 = new AbstractC1341vl[length];
                abstractC1341vlArr2[0] = abstractC1341vl;
                for (int i = 1; i < length; i++) {
                    abstractC1341vlArr2[i] = importPoint(curve, abstractC1341vlArr[i]);
                }
                InterfaceC1318uz endomorphism = curve.getEndomorphism();
                return endomorphism instanceof b20 ? implCheckResult(implSumOfMultipliesGLV(abstractC1341vlArr2, bigIntegerArr, (b20) endomorphism)) : implCheckResult(implSumOfMultiplies(abstractC1341vlArr2, bigIntegerArr));
            }
        }
        throw new IllegalArgumentException("point and scalar arrays should be non-null, and of equal, non-zero, length");
    }

    public static AbstractC1341vl sumOfTwoMultiplies(AbstractC1341vl abstractC1341vl, BigInteger bigInteger, AbstractC1341vl abstractC1341vl2, BigInteger bigInteger2) {
        AbstractC1341vl abstractC1341vlImplSumOfMultipliesGLV;
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        AbstractC1341vl abstractC1341vlImportPoint = importPoint(curve, abstractC1341vl2);
        if ((curve instanceof AbstractC1316ux.a1) && ((AbstractC1316ux.a1) curve).isKoblitz()) {
            abstractC1341vlImplSumOfMultipliesGLV = abstractC1341vl.multiply(bigInteger).add(abstractC1341vlImportPoint.multiply(bigInteger2));
        } else {
            InterfaceC1318uz endomorphism = curve.getEndomorphism();
            abstractC1341vlImplSumOfMultipliesGLV = endomorphism instanceof b20 ? implSumOfMultipliesGLV(new AbstractC1341vl[]{abstractC1341vl, abstractC1341vlImportPoint}, new BigInteger[]{bigInteger, bigInteger2}, (b20) endomorphism) : implShamirsTrickWNaf(abstractC1341vl, bigInteger, abstractC1341vlImportPoint, bigInteger2);
        }
        return implCheckResult(abstractC1341vlImplSumOfMultipliesGLV);
    }

    public static AbstractC1341vl validatePoint(AbstractC1341vl abstractC1341vl) {
        if (abstractC1341vl.isValid()) {
            return abstractC1341vl;
        }
        throw new IllegalStateException("Invalid point");
    }

    public static AbstractC1341vl implShamirsTrickWNaf(AbstractC1341vl abstractC1341vl, BigInteger bigInteger, AbstractC1341vl abstractC1341vl2, BigInteger bigInteger2) {
        boolean z = bigInteger.signum() < 0;
        boolean z2 = bigInteger2.signum() < 0;
        BigInteger bigIntegerAbs = bigInteger.abs();
        BigInteger bigIntegerAbs2 = bigInteger2.abs();
        int windowSize = xd1.getWindowSize(bigIntegerAbs.bitLength(), 8);
        int windowSize2 = xd1.getWindowSize(bigIntegerAbs2.bitLength(), 8);
        wd1 wd1VarPrecompute = xd1.precompute(abstractC1341vl, windowSize, true);
        wd1 wd1VarPrecompute2 = xd1.precompute(abstractC1341vl2, windowSize2, true);
        int combSize = C1524zo.getCombSize(abstractC1341vl.getCurve());
        if (!z && !z2 && bigInteger.bitLength() <= combSize && bigInteger2.bitLength() <= combSize && wd1VarPrecompute.isPromoted() && wd1VarPrecompute2.isPromoted()) {
            return implShamirsTrickFixedPoint(abstractC1341vl, bigInteger, abstractC1341vl2, bigInteger2);
        }
        int iMin = Math.min(8, wd1VarPrecompute.getWidth());
        int iMin2 = Math.min(8, wd1VarPrecompute2.getWidth());
        return implShamirsTrickWNaf(z ? wd1VarPrecompute.getPreCompNeg() : wd1VarPrecompute.getPreComp(), z ? wd1VarPrecompute.getPreComp() : wd1VarPrecompute.getPreCompNeg(), xd1.generateWindowNaf(iMin, bigIntegerAbs), z2 ? wd1VarPrecompute2.getPreCompNeg() : wd1VarPrecompute2.getPreComp(), z2 ? wd1VarPrecompute2.getPreComp() : wd1VarPrecompute2.getPreCompNeg(), xd1.generateWindowNaf(iMin2, bigIntegerAbs2));
    }

    public static AbstractC1341vl implSumOfMultiplies(AbstractC1341vl[] abstractC1341vlArr, BigInteger[] bigIntegerArr) {
        int length = abstractC1341vlArr.length;
        boolean[] zArr = new boolean[length];
        wd1[] wd1VarArr = new wd1[length];
        byte[][] bArr = new byte[length][];
        for (int i = 0; i < length; i++) {
            BigInteger bigInteger = bigIntegerArr[i];
            zArr[i] = bigInteger.signum() < 0;
            BigInteger bigIntegerAbs = bigInteger.abs();
            wd1 wd1VarPrecompute = xd1.precompute(abstractC1341vlArr[i], xd1.getWindowSize(bigIntegerAbs.bitLength(), 8), true);
            int iMin = Math.min(8, wd1VarPrecompute.getWidth());
            wd1VarArr[i] = wd1VarPrecompute;
            bArr[i] = xd1.generateWindowNaf(iMin, bigIntegerAbs);
        }
        return implSumOfMultiplies(zArr, wd1VarArr, bArr);
    }

    public static void montgomeryTrick(AbstractC1330va[] abstractC1330vaArr, int i, int i2, AbstractC1330va abstractC1330va) {
        AbstractC1330va[] abstractC1330vaArr2 = new AbstractC1330va[i2];
        int i3 = 0;
        abstractC1330vaArr2[0] = abstractC1330vaArr[i];
        while (true) {
            int i4 = i3 + 1;
            if (i4 >= i2) {
                break;
            }
            abstractC1330vaArr2[i4] = abstractC1330vaArr2[i3].multiply(abstractC1330vaArr[i + i4]);
            i3 = i4;
        }
        if (abstractC1330va != null) {
            abstractC1330vaArr2[i3] = abstractC1330vaArr2[i3].multiply(abstractC1330va);
        }
        AbstractC1330va abstractC1330vaInvert = abstractC1330vaArr2[i3].invert();
        while (i3 > 0) {
            int i5 = i3 - 1;
            int i6 = i3 + i;
            AbstractC1330va abstractC1330va2 = abstractC1330vaArr[i6];
            abstractC1330vaArr[i6] = abstractC1330vaArr2[i5].multiply(abstractC1330vaInvert);
            abstractC1330vaInvert = abstractC1330vaInvert.multiply(abstractC1330va2);
            i3 = i5;
        }
        abstractC1330vaArr[i] = abstractC1330vaInvert;
    }

    private static AbstractC1341vl implShamirsTrickWNaf(AbstractC1341vl[] abstractC1341vlArr, AbstractC1341vl[] abstractC1341vlArr2, byte[] bArr, AbstractC1341vl[] abstractC1341vlArr3, AbstractC1341vl[] abstractC1341vlArr4, byte[] bArr2) {
        AbstractC1341vl abstractC1341vlAdd;
        int iMax = Math.max(bArr.length, bArr2.length);
        AbstractC1341vl infinity = abstractC1341vlArr[0].getCurve().getInfinity();
        int i = iMax - 1;
        int i2 = 0;
        AbstractC1341vl abstractC1341vlTwicePlus = infinity;
        while (i >= 0) {
            byte b = i < bArr.length ? bArr[i] : (byte) 0;
            byte b2 = i < bArr2.length ? bArr2[i] : (byte) 0;
            if ((b | b2) == 0) {
                i2++;
            } else {
                if (b != 0) {
                    abstractC1341vlAdd = infinity.add((b < 0 ? abstractC1341vlArr2 : abstractC1341vlArr)[Math.abs((int) b) >>> 1]);
                } else {
                    abstractC1341vlAdd = infinity;
                }
                if (b2 != 0) {
                    abstractC1341vlAdd = abstractC1341vlAdd.add((b2 < 0 ? abstractC1341vlArr4 : abstractC1341vlArr3)[Math.abs((int) b2) >>> 1]);
                }
                if (i2 > 0) {
                    abstractC1341vlTwicePlus = abstractC1341vlTwicePlus.timesPow2(i2);
                    i2 = 0;
                }
                abstractC1341vlTwicePlus = abstractC1341vlTwicePlus.twicePlus(abstractC1341vlAdd);
            }
            i--;
        }
        return i2 > 0 ? abstractC1341vlTwicePlus.timesPow2(i2) : abstractC1341vlTwicePlus;
    }

    private static AbstractC1341vl implSumOfMultiplies(boolean[] zArr, wd1[] wd1VarArr, byte[][] bArr) {
        int length = bArr.length;
        int iMax = 0;
        for (byte[] bArr2 : bArr) {
            iMax = Math.max(iMax, bArr2.length);
        }
        AbstractC1341vl infinity = wd1VarArr[0].getPreComp()[0].getCurve().getInfinity();
        int i = iMax - 1;
        int i2 = 0;
        AbstractC1341vl abstractC1341vlTwicePlus = infinity;
        while (i >= 0) {
            AbstractC1341vl abstractC1341vlAdd = infinity;
            for (int i3 = 0; i3 < length; i3++) {
                byte[] bArr3 = bArr[i3];
                byte b = i < bArr3.length ? bArr3[i] : (byte) 0;
                if (b != 0) {
                    int iAbs = Math.abs((int) b);
                    wd1 wd1Var = wd1VarArr[i3];
                    abstractC1341vlAdd = abstractC1341vlAdd.add(((b < 0) == zArr[i3] ? wd1Var.getPreComp() : wd1Var.getPreCompNeg())[iAbs >>> 1]);
                }
            }
            if (abstractC1341vlAdd == infinity) {
                i2++;
            } else {
                if (i2 > 0) {
                    abstractC1341vlTwicePlus = abstractC1341vlTwicePlus.timesPow2(i2);
                    i2 = 0;
                }
                abstractC1341vlTwicePlus = abstractC1341vlTwicePlus.twicePlus(abstractC1341vlAdd);
            }
            i--;
        }
        return i2 > 0 ? abstractC1341vlTwicePlus.timesPow2(i2) : abstractC1341vlTwicePlus;
    }
}
