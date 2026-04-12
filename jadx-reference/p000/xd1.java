package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public abstract class xd1 {
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = {13, 41, 121, 337, 897, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    private static final AbstractC1341vl[] EMPTY_POINTS = new AbstractC1341vl[0];
    private static final int MAX_WIDTH = 16;
    public static final String PRECOMP_NAME = "bc_wnaf";

    /* renamed from: xd1$a0 */
    public static class C1411a0 implements zn0 {
        final /* synthetic */ int val$confWidth;

        public C1411a0(int i) {
            this.val$confWidth = i;
        }

        @Override // p000.zn0
        public ao0 precompute(ao0 ao0Var) {
            wd1 wd1Var = ao0Var instanceof wd1 ? (wd1) ao0Var : null;
            if (wd1Var != null && wd1Var.getConfWidth() == this.val$confWidth) {
                wd1Var.setPromotionCountdown(0);
                return wd1Var;
            }
            wd1 wd1Var2 = new wd1();
            wd1Var2.setPromotionCountdown(0);
            wd1Var2.setConfWidth(this.val$confWidth);
            if (wd1Var != null) {
                wd1Var2.setPreComp(wd1Var.getPreComp());
                wd1Var2.setPreCompNeg(wd1Var.getPreCompNeg());
                wd1Var2.setTwice(wd1Var.getTwice());
                wd1Var2.setWidth(wd1Var.getWidth());
            }
            return wd1Var2;
        }
    }

    /* renamed from: xd1$a1 */
    public static class C1412a1 implements zn0 {
        final /* synthetic */ AbstractC1316ux val$c;
        final /* synthetic */ boolean val$includeNegated;
        final /* synthetic */ int val$minWidth;
        final /* synthetic */ AbstractC1341vl val$p;

        public C1412a1(int i, boolean z, AbstractC1341vl abstractC1341vl, AbstractC1316ux abstractC1316ux) {
            this.val$minWidth = i;
            this.val$includeNegated = z;
            this.val$p = abstractC1341vl;
            this.val$c = abstractC1316ux;
        }

        private boolean checkExisting(wd1 wd1Var, int i, int i2, boolean z) {
            if (wd1Var == null || wd1Var.getWidth() < Math.max(wd1Var.getConfWidth(), i) || !checkTable(wd1Var.getPreComp(), i2)) {
                return false;
            }
            return !z || checkTable(wd1Var.getPreCompNeg(), i2);
        }

        private boolean checkTable(AbstractC1341vl[] abstractC1341vlArr, int i) {
            return abstractC1341vlArr != null && abstractC1341vlArr.length >= i;
        }

        /* JADX WARN: Removed duplicated region for block: B:45:0x00ef A[PHI: r14
          0x00ef: PHI (r14v6 vl) = (r14v4 vl), (r14v9 vl), (r14v9 vl), (r14v9 vl), (r14v9 vl) binds: [B:28:0x0091, B:30:0x009d, B:32:0x00a5, B:34:0x00af, B:40:0x00bd] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // p000.zn0
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public ao0 precompute(ao0 ao0Var) {
            AbstractC1341vl abstractC1341vlTwice;
            AbstractC1341vl[] abstractC1341vlArrResizeTable;
            AbstractC1341vl[] abstractC1341vlArrResizeTable2;
            int length;
            int i;
            AbstractC1341vl abstractC1341vlCreatePoint;
            int coordinateSystem;
            AbstractC1330va zCoord = null;
            wd1 wd1Var = ao0Var instanceof wd1 ? (wd1) ao0Var : null;
            int iMax = Math.max(2, Math.min(16, this.val$minWidth));
            if (checkExisting(wd1Var, iMax, 1 << (iMax - 2), this.val$includeNegated)) {
                wd1Var.decrementPromotionCountdown();
                return wd1Var;
            }
            wd1 wd1Var2 = new wd1();
            if (wd1Var != null) {
                wd1Var2.setPromotionCountdown(wd1Var.decrementPromotionCountdown());
                wd1Var2.setConfWidth(wd1Var.getConfWidth());
                abstractC1341vlArrResizeTable = wd1Var.getPreComp();
                abstractC1341vlArrResizeTable2 = wd1Var.getPreCompNeg();
                abstractC1341vlTwice = wd1Var.getTwice();
            } else {
                abstractC1341vlTwice = null;
                abstractC1341vlArrResizeTable = null;
                abstractC1341vlArrResizeTable2 = null;
            }
            int iMin = Math.min(16, Math.max(wd1Var2.getConfWidth(), iMax));
            int i2 = 1 << (iMin - 2);
            int length2 = 0;
            if (abstractC1341vlArrResizeTable == null) {
                abstractC1341vlArrResizeTable = xd1.EMPTY_POINTS;
                length = 0;
            } else {
                length = abstractC1341vlArrResizeTable.length;
            }
            if (length < i2) {
                abstractC1341vlArrResizeTable = xd1.resizeTable(abstractC1341vlArrResizeTable, i2);
                if (i2 == 1) {
                    abstractC1341vlArrResizeTable[0] = this.val$p.normalize();
                } else {
                    if (length == 0) {
                        abstractC1341vlArrResizeTable[0] = this.val$p;
                        i = 1;
                    } else {
                        i = length;
                    }
                    if (i2 == 2) {
                        abstractC1341vlArrResizeTable[1] = this.val$p.threeTimes();
                    } else {
                        AbstractC1341vl abstractC1341vlAdd = abstractC1341vlArrResizeTable[i - 1];
                        if (abstractC1341vlTwice == null) {
                            abstractC1341vlTwice = abstractC1341vlArrResizeTable[0].twice();
                            if (abstractC1341vlTwice.isInfinity() || !C1314uv.isFpCurve(this.val$c) || this.val$c.getFieldSize() < 64 || !((coordinateSystem = this.val$c.getCoordinateSystem()) == 2 || coordinateSystem == 3 || coordinateSystem == 4)) {
                                abstractC1341vlCreatePoint = abstractC1341vlTwice;
                            } else {
                                zCoord = abstractC1341vlTwice.getZCoord(0);
                                abstractC1341vlCreatePoint = this.val$c.createPoint(abstractC1341vlTwice.getXCoord().toBigInteger(), abstractC1341vlTwice.getYCoord().toBigInteger());
                                AbstractC1330va abstractC1330vaSquare = zCoord.square();
                                abstractC1341vlAdd = abstractC1341vlAdd.scaleX(abstractC1330vaSquare).scaleY(abstractC1330vaSquare.multiply(zCoord));
                                if (length == 0) {
                                    abstractC1341vlArrResizeTable[0] = abstractC1341vlAdd;
                                }
                            }
                            while (i < i2) {
                                abstractC1341vlAdd = abstractC1341vlAdd.add(abstractC1341vlCreatePoint);
                                abstractC1341vlArrResizeTable[i] = abstractC1341vlAdd;
                                i++;
                            }
                        }
                    }
                    this.val$c.normalizeAll(abstractC1341vlArrResizeTable, length, i2 - length, zCoord);
                }
            }
            if (this.val$includeNegated) {
                if (abstractC1341vlArrResizeTable2 == null) {
                    abstractC1341vlArrResizeTable2 = new AbstractC1341vl[i2];
                } else {
                    length2 = abstractC1341vlArrResizeTable2.length;
                    if (length2 < i2) {
                        abstractC1341vlArrResizeTable2 = xd1.resizeTable(abstractC1341vlArrResizeTable2, i2);
                    }
                }
                while (length2 < i2) {
                    abstractC1341vlArrResizeTable2[length2] = abstractC1341vlArrResizeTable[length2].negate();
                    length2++;
                }
            }
            wd1Var2.setPreComp(abstractC1341vlArrResizeTable);
            wd1Var2.setPreCompNeg(abstractC1341vlArrResizeTable2);
            wd1Var2.setTwice(abstractC1341vlTwice);
            wd1Var2.setWidth(iMin);
            return wd1Var2;
        }
    }

    /* renamed from: xd1$a2 */
    public static class C1413a2 implements zn0 {
        final /* synthetic */ wd1 val$fromWNaf;
        final /* synthetic */ boolean val$includeNegated;
        final /* synthetic */ InterfaceC1342vm val$pointMap;

        public C1413a2(wd1 wd1Var, boolean z, InterfaceC1342vm interfaceC1342vm) {
            this.val$fromWNaf = wd1Var;
            this.val$includeNegated = z;
            this.val$pointMap = interfaceC1342vm;
        }

        private boolean checkExisting(wd1 wd1Var, int i, int i2, boolean z) {
            if (wd1Var == null || wd1Var.getWidth() < i || !checkTable(wd1Var.getPreComp(), i2)) {
                return false;
            }
            return !z || checkTable(wd1Var.getPreCompNeg(), i2);
        }

        private boolean checkTable(AbstractC1341vl[] abstractC1341vlArr, int i) {
            return abstractC1341vlArr != null && abstractC1341vlArr.length >= i;
        }

        @Override // p000.zn0
        public ao0 precompute(ao0 ao0Var) {
            wd1 wd1Var = ao0Var instanceof wd1 ? (wd1) ao0Var : null;
            int width = this.val$fromWNaf.getWidth();
            if (checkExisting(wd1Var, width, this.val$fromWNaf.getPreComp().length, this.val$includeNegated)) {
                wd1Var.decrementPromotionCountdown();
                return wd1Var;
            }
            wd1 wd1Var2 = new wd1();
            wd1Var2.setPromotionCountdown(this.val$fromWNaf.getPromotionCountdown());
            AbstractC1341vl twice = this.val$fromWNaf.getTwice();
            if (twice != null) {
                wd1Var2.setTwice(this.val$pointMap.map(twice));
            }
            AbstractC1341vl[] preComp = this.val$fromWNaf.getPreComp();
            int length = preComp.length;
            AbstractC1341vl[] abstractC1341vlArr = new AbstractC1341vl[length];
            for (int i = 0; i < preComp.length; i++) {
                abstractC1341vlArr[i] = this.val$pointMap.map(preComp[i]);
            }
            wd1Var2.setPreComp(abstractC1341vlArr);
            wd1Var2.setWidth(width);
            if (this.val$includeNegated) {
                AbstractC1341vl[] abstractC1341vlArr2 = new AbstractC1341vl[length];
                for (int i2 = 0; i2 < length; i2++) {
                    abstractC1341vlArr2[i2] = abstractC1341vlArr[i2].negate();
                }
                wd1Var2.setPreCompNeg(abstractC1341vlArr2);
            }
            return wd1Var2;
        }
    }

    public static void configureBasepoint(AbstractC1341vl abstractC1341vl) {
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        if (curve == null) {
            return;
        }
        BigInteger order = curve.getOrder();
        curve.precompute(abstractC1341vl, PRECOMP_NAME, new C1411a0(Math.min(16, getWindowSize(order == null ? curve.getFieldSize() + 1 : order.bitLength()) + 3)));
    }

    public static int[] generateCompactNaf(BigInteger bigInteger) {
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        BigInteger bigIntegerAdd = bigInteger.shiftLeft(1).add(bigInteger);
        int iBitLength = bigIntegerAdd.bitLength();
        int i = iBitLength >> 1;
        int[] iArr = new int[i];
        BigInteger bigIntegerXor = bigIntegerAdd.xor(bigInteger);
        int i2 = iBitLength - 1;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i4 < i2) {
            if (bigIntegerXor.testBit(i4)) {
                iArr[i3] = i5 | ((bigInteger.testBit(i4) ? -1 : 1) << 16);
                i4++;
                i5 = 1;
                i3++;
            } else {
                i5++;
            }
            i4++;
        }
        int i6 = i3 + 1;
        iArr[i3] = 65536 | i5;
        return i > i6 ? trim(iArr, i6) : iArr;
    }

    public static int[] generateCompactWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateCompactNaf(bigInteger);
        }
        if (i < 2 || i > 16) {
            throw new IllegalArgumentException("'width' must be in the range [2, 16]");
        }
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        int iBitLength = (bigInteger.bitLength() / i) + 1;
        int[] iArr = new int[iBitLength];
        int i2 = 1 << i;
        int i3 = i2 - 1;
        int i4 = i2 >>> 1;
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i5 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i5) == z) {
                i5++;
            } else {
                bigInteger = bigInteger.shiftRight(i5);
                int iIntValue = bigInteger.intValue() & i3;
                if (z) {
                    iIntValue++;
                }
                z = (iIntValue & i4) != 0;
                if (z) {
                    iIntValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                iArr[i6] = i5 | (iIntValue << 16);
                i5 = i;
                i6++;
            }
        }
        return iBitLength > i6 ? trim(iArr, i6) : iArr;
    }

    public static byte[] generateJSF(BigInteger bigInteger, BigInteger bigInteger2) {
        int iMax = Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1;
        byte[] bArr = new byte[iMax];
        BigInteger bigIntegerShiftRight = bigInteger;
        BigInteger bigIntegerShiftRight2 = bigInteger2;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if ((i | i2) == 0 && bigIntegerShiftRight.bitLength() <= i3 && bigIntegerShiftRight2.bitLength() <= i3) {
                break;
            }
            int iIntValue = (bigIntegerShiftRight.intValue() >>> i3) + i;
            int i5 = iIntValue & 7;
            int iIntValue2 = (bigIntegerShiftRight2.intValue() >>> i3) + i2;
            int i6 = iIntValue2 & 7;
            int i7 = iIntValue & 1;
            if (i7 != 0) {
                i7 -= iIntValue & 2;
                if (i5 + i7 == 4 && (iIntValue2 & 3) == 2) {
                    i7 = -i7;
                }
            }
            int i8 = iIntValue2 & 1;
            if (i8 != 0) {
                i8 -= iIntValue2 & 2;
                if (i6 + i8 == 4 && (iIntValue & 3) == 2) {
                    i8 = -i8;
                }
            }
            if ((i << 1) == i7 + 1) {
                i ^= 1;
            }
            if ((i2 << 1) == i8 + 1) {
                i2 ^= 1;
            }
            i3++;
            if (i3 == 30) {
                bigIntegerShiftRight = bigIntegerShiftRight.shiftRight(30);
                bigIntegerShiftRight2 = bigIntegerShiftRight2.shiftRight(30);
                i3 = 0;
            }
            bArr[i4] = (byte) ((i8 & 15) | (i7 << 4));
            i4++;
        }
        return iMax > i4 ? trim(bArr, i4) : bArr;
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        BigInteger bigIntegerAdd = bigInteger.shiftLeft(1).add(bigInteger);
        int iBitLength = bigIntegerAdd.bitLength();
        int i = iBitLength - 1;
        byte[] bArr = new byte[i];
        BigInteger bigIntegerXor = bigIntegerAdd.xor(bigInteger);
        int i2 = 1;
        while (i2 < i) {
            if (bigIntegerXor.testBit(i2)) {
                bArr[i2 - 1] = (byte) (bigInteger.testBit(i2) ? -1 : 1);
                i2++;
            }
            i2++;
        }
        bArr[iBitLength - 2] = 1;
        return bArr;
    }

    public static byte[] generateWindowNaf(int i, BigInteger bigInteger) {
        if (i == 2) {
            return generateNaf(bigInteger);
        }
        if (i < 2 || i > 8) {
            throw new IllegalArgumentException("'width' must be in the range [2, 8]");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        int iBitLength = bigInteger.bitLength() + 1;
        byte[] bArr = new byte[iBitLength];
        int i2 = 1 << i;
        int i3 = i2 - 1;
        int i4 = i2 >>> 1;
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i5 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i5) == z) {
                i5++;
            } else {
                bigInteger = bigInteger.shiftRight(i5);
                int iIntValue = bigInteger.intValue() & i3;
                if (z) {
                    iIntValue++;
                }
                z = (iIntValue & i4) != 0;
                if (z) {
                    iIntValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                int i7 = i6 + i5;
                bArr[i7] = (byte) iIntValue;
                i6 = i7 + 1;
                i5 = i;
            }
        }
        return iBitLength > i6 ? trim(bArr, i6) : bArr;
    }

    public static int getNafWeight(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static wd1 getWNafPreCompInfo(AbstractC1341vl abstractC1341vl) {
        return getWNafPreCompInfo(abstractC1341vl.getCurve().getPreCompInfo(abstractC1341vl, PRECOMP_NAME));
    }

    public static int getWindowSize(int i) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, 16);
    }

    public static wd1 precompute(AbstractC1341vl abstractC1341vl, int i, boolean z) {
        AbstractC1316ux curve = abstractC1341vl.getCurve();
        return (wd1) curve.precompute(abstractC1341vl, PRECOMP_NAME, new C1412a1(i, z, abstractC1341vl, curve));
    }

    public static wd1 precomputeWithPointMap(AbstractC1341vl abstractC1341vl, InterfaceC1342vm interfaceC1342vm, wd1 wd1Var, boolean z) {
        return (wd1) abstractC1341vl.getCurve().precompute(abstractC1341vl, PRECOMP_NAME, new C1413a2(wd1Var, z, interfaceC1342vm));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC1341vl[] resizeTable(AbstractC1341vl[] abstractC1341vlArr, int i) {
        AbstractC1341vl[] abstractC1341vlArr2 = new AbstractC1341vl[i];
        System.arraycopy(abstractC1341vlArr, 0, abstractC1341vlArr2, 0, abstractC1341vlArr.length);
        return abstractC1341vlArr2;
    }

    private static byte[] trim(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public static wd1 getWNafPreCompInfo(ao0 ao0Var) {
        if (ao0Var instanceof wd1) {
            return (wd1) ao0Var;
        }
        return null;
    }

    public static int getWindowSize(int i, int i2) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, i2);
    }

    private static int[] trim(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    public static int getWindowSize(int i, int[] iArr) {
        return getWindowSize(i, iArr, 16);
    }

    public static int getWindowSize(int i, int[] iArr, int i2) {
        int i3 = 0;
        while (i3 < iArr.length && i >= iArr[i3]) {
            i3++;
        }
        return Math.max(2, Math.min(i2, i3 + 2));
    }
}
