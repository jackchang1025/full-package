package p000;

import java.math.BigInteger;
import p000.AbstractC1316ux;
import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class w61 {
    private static final BigInteger MINUS_ONE;
    private static final BigInteger MINUS_THREE;
    private static final BigInteger MINUS_TWO;
    public static final byte POW_2_WIDTH = 16;
    public static final byte WIDTH = 4;
    public static final jj1[] alpha0;
    public static final byte[][] alpha0Tnaf;
    public static final jj1[] alpha1;
    public static final byte[][] alpha1Tnaf;

    static {
        BigInteger bigInteger = InterfaceC1315uw.ONE;
        BigInteger bigIntegerNegate = bigInteger.negate();
        MINUS_ONE = bigIntegerNegate;
        MINUS_TWO = InterfaceC1315uw.TWO.negate();
        BigInteger bigIntegerNegate2 = InterfaceC1315uw.THREE.negate();
        MINUS_THREE = bigIntegerNegate2;
        BigInteger bigInteger2 = InterfaceC1315uw.ZERO;
        alpha0 = new jj1[]{null, new jj1(bigInteger, bigInteger2), null, new jj1(bigIntegerNegate2, bigIntegerNegate), null, new jj1(bigIntegerNegate, bigIntegerNegate), null, new jj1(bigInteger, bigIntegerNegate), null};
        alpha0Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
        alpha1 = new jj1[]{null, new jj1(bigInteger, bigInteger2), null, new jj1(bigIntegerNegate2, bigInteger), null, new jj1(bigIntegerNegate, bigInteger), null, new jj1(bigInteger, bigInteger), null};
        alpha1Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};
    }

    public static u01 approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b, int i, int i2) {
        BigInteger bigIntegerMultiply = bigInteger2.multiply(bigInteger.shiftRight(((i - r0) - 2) + b));
        BigInteger bigIntegerAdd = bigIntegerMultiply.add(bigInteger3.multiply(bigIntegerMultiply.shiftRight(i)));
        int i3 = (((i + 5) / 2) + i2) - i2;
        BigInteger bigIntegerShiftRight = bigIntegerAdd.shiftRight(i3);
        if (bigIntegerAdd.testBit(i3 - 1)) {
            bigIntegerShiftRight = bigIntegerShiftRight.add(InterfaceC1315uw.ONE);
        }
        return new u01(bigIntegerShiftRight, i2);
    }

    public static BigInteger[] getLucas(byte b, int i, boolean z) {
        BigInteger bigInteger;
        BigInteger bigIntegerSubtract;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        if (z) {
            bigInteger = InterfaceC1315uw.TWO;
            bigIntegerSubtract = BigInteger.valueOf(b);
        } else {
            bigInteger = InterfaceC1315uw.ZERO;
            bigIntegerSubtract = InterfaceC1315uw.ONE;
        }
        int i2 = 1;
        while (i2 < i) {
            i2++;
            BigInteger bigInteger2 = bigIntegerSubtract;
            bigIntegerSubtract = (b == 1 ? bigIntegerSubtract : bigIntegerSubtract.negate()).subtract(bigInteger.shiftLeft(1));
            bigInteger = bigInteger2;
        }
        return new BigInteger[]{bigInteger, bigIntegerSubtract};
    }

    public static byte getMu(int i) {
        return (byte) (i == 0 ? -1 : 1);
    }

    public static AbstractC1341vl.a1[] getPreComp(AbstractC1341vl.a1 a1Var, byte b) {
        byte[][] bArr = b == 0 ? alpha0Tnaf : alpha1Tnaf;
        AbstractC1341vl.a1[] a1VarArr = new AbstractC1341vl.a1[(bArr.length + 1) >>> 1];
        a1VarArr[0] = a1Var;
        int length = bArr.length;
        for (int i = 3; i < length; i += 2) {
            a1VarArr[i >>> 1] = multiplyFromTnaf(a1Var, bArr[i]);
        }
        a1Var.getCurve().normalizeAll(a1VarArr);
        return a1VarArr;
    }

    public static int getShiftsForCofactor(BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(InterfaceC1315uw.TWO)) {
                return 1;
            }
            if (bigInteger.equals(InterfaceC1315uw.FOUR)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static BigInteger[] getSi(int i, int i2, BigInteger bigInteger) {
        byte mu = getMu(i2);
        int shiftsForCofactor = getShiftsForCofactor(bigInteger);
        BigInteger[] lucas = getLucas(mu, (i + 3) - i2, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        BigInteger bigInteger2 = InterfaceC1315uw.ONE;
        return new BigInteger[]{bigInteger2.add(lucas[1]).shiftRight(shiftsForCofactor), bigInteger2.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    public static BigInteger getTw(byte b, int i) {
        if (i == 4) {
            return b == 1 ? BigInteger.valueOf(6L) : BigInteger.valueOf(10L);
        }
        BigInteger[] lucas = getLucas(b, i, false);
        BigInteger bit = InterfaceC1315uw.ZERO.setBit(i);
        return InterfaceC1315uw.TWO.multiply(lucas[0]).multiply(lucas[1].modInverse(bit)).mod(bit);
    }

    public static AbstractC1341vl.a1 multiplyFromTnaf(AbstractC1341vl.a1 a1Var, byte[] bArr) {
        AbstractC1341vl.a1 a1Var2 = (AbstractC1341vl.a1) a1Var.getCurve().getInfinity();
        AbstractC1341vl.a1 a1Var3 = (AbstractC1341vl.a1) a1Var.negate();
        int i = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i++;
            byte b = bArr[length];
            if (b != 0) {
                a1Var2 = (AbstractC1341vl.a1) a1Var2.tauPow(i).add(b > 0 ? a1Var : a1Var3);
                i = 0;
            }
        }
        return i > 0 ? a1Var2.tauPow(i) : a1Var2;
    }

    public static AbstractC1341vl.a1 multiplyRTnaf(AbstractC1341vl.a1 a1Var, BigInteger bigInteger) {
        AbstractC1316ux.a1 a1Var2 = (AbstractC1316ux.a1) a1Var.getCurve();
        int fieldSize = a1Var2.getFieldSize();
        int iIntValue = a1Var2.getA().toBigInteger().intValue();
        return multiplyTnaf(a1Var, partModReduction(bigInteger, fieldSize, (byte) iIntValue, a1Var2.getSi(), getMu(iIntValue), (byte) 10));
    }

    public static AbstractC1341vl.a1 multiplyTnaf(AbstractC1341vl.a1 a1Var, jj1 jj1Var) {
        return multiplyFromTnaf(a1Var, tauAdicNaf(getMu(((AbstractC1316ux.a1) a1Var.getCurve()).getA()), jj1Var));
    }

    public static u01 norm(byte b, u01 u01Var, u01 u01Var2) {
        u01 u01VarSubtract;
        u01 u01VarMultiply = u01Var.multiply(u01Var);
        u01 u01VarMultiply2 = u01Var.multiply(u01Var2);
        u01 u01VarShiftLeft = u01Var2.multiply(u01Var2).shiftLeft(1);
        if (b == 1) {
            u01VarSubtract = u01VarMultiply.add(u01VarMultiply2);
        } else {
            if (b != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            u01VarSubtract = u01VarMultiply.subtract(u01VarMultiply2);
        }
        return u01VarSubtract.add(u01VarShiftLeft);
    }

    public static jj1 partModReduction(BigInteger bigInteger, int i, byte b, BigInteger[] bigIntegerArr, byte b2, byte b3) {
        BigInteger bigIntegerAdd = b2 == 1 ? bigIntegerArr[0].add(bigIntegerArr[1]) : bigIntegerArr[0].subtract(bigIntegerArr[1]);
        BigInteger bigInteger2 = getLucas(b2, i, true)[1];
        jj1 jj1VarRound = round(approximateDivisionByN(bigInteger, bigIntegerArr[0], bigInteger2, b, i, b3), approximateDivisionByN(bigInteger, bigIntegerArr[1], bigInteger2, b, i, b3), b2);
        return new jj1(bigInteger.subtract(bigIntegerAdd.multiply(jj1VarRound.f57339u)).subtract(BigInteger.valueOf(2L).multiply(bigIntegerArr[1]).multiply(jj1VarRound.f57340v)), bigIntegerArr[1].multiply(jj1VarRound.f57339u).subtract(bigIntegerArr[0].multiply(jj1VarRound.f57340v)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0081, code lost:
    
        if (r5.compareTo(r9) >= 0) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static jj1 round(u01 u01Var, u01 u01Var2, byte b) {
        u01 u01VarAdd;
        u01 u01VarSubtract;
        if (u01Var2.getScale() != u01Var.getScale()) {
            throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
        }
        int i = -1;
        int i2 = 1;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        BigInteger bigIntegerRound = u01Var.round();
        BigInteger bigIntegerRound2 = u01Var2.round();
        u01 u01VarSubtract2 = u01Var.subtract(bigIntegerRound);
        u01 u01VarSubtract3 = u01Var2.subtract(bigIntegerRound2);
        u01 u01VarAdd2 = u01VarSubtract2.add(u01VarSubtract2);
        u01 u01VarAdd3 = b == 1 ? u01VarAdd2.add(u01VarSubtract3) : u01VarAdd2.subtract(u01VarSubtract3);
        u01 u01VarAdd4 = u01VarSubtract3.add(u01VarSubtract3).add(u01VarSubtract3);
        u01 u01VarAdd5 = u01VarAdd4.add(u01VarSubtract3);
        if (b == 1) {
            u01VarAdd = u01VarSubtract2.subtract(u01VarAdd4);
            u01VarSubtract = u01VarSubtract2.add(u01VarAdd5);
        } else {
            u01VarAdd = u01VarSubtract2.add(u01VarAdd4);
            u01VarSubtract = u01VarSubtract2.subtract(u01VarAdd5);
        }
        BigInteger bigInteger = InterfaceC1315uw.ONE;
        byte b2 = 0;
        if (u01VarAdd3.compareTo(bigInteger) >= 0) {
            if (u01VarAdd.compareTo(MINUS_ONE) < 0) {
                i2 = 0;
                b2 = b;
            }
        } else if (u01VarSubtract.compareTo(InterfaceC1315uw.TWO) < 0) {
            i2 = 0;
        }
        if (u01VarAdd3.compareTo(MINUS_ONE) >= 0) {
            if (u01VarSubtract.compareTo(MINUS_TWO) < 0) {
            }
            i = i2;
            return new jj1(bigIntegerRound.add(BigInteger.valueOf(i)), bigIntegerRound2.add(BigInteger.valueOf(b2)));
        }
        b2 = (byte) (-b);
        i = i2;
        return new jj1(bigIntegerRound.add(BigInteger.valueOf(i)), bigIntegerRound2.add(BigInteger.valueOf(b2)));
    }

    public static AbstractC1341vl.a1 tau(AbstractC1341vl.a1 a1Var) {
        return a1Var.tau();
    }

    public static byte[] tauAdicNaf(byte b, jj1 jj1Var) {
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int iBitLength = norm(b, jj1Var).bitLength();
        byte[] bArr = new byte[iBitLength > 30 ? iBitLength + 4 : 34];
        BigInteger bigIntegerClearBit = jj1Var.f57339u;
        BigInteger bigInteger = jj1Var.f57340v;
        int i = 0;
        int i2 = 0;
        while (true) {
            BigInteger bigInteger2 = InterfaceC1315uw.ZERO;
            if (bigIntegerClearBit.equals(bigInteger2) && bigInteger.equals(bigInteger2)) {
                int i3 = i + 1;
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                return bArr2;
            }
            if (bigIntegerClearBit.testBit(0)) {
                byte bIntValue = (byte) InterfaceC1315uw.TWO.subtract(bigIntegerClearBit.subtract(bigInteger.shiftLeft(1)).mod(InterfaceC1315uw.FOUR)).intValue();
                bArr[i2] = bIntValue;
                bigIntegerClearBit = bIntValue == 1 ? bigIntegerClearBit.clearBit(0) : bigIntegerClearBit.add(InterfaceC1315uw.ONE);
                i = i2;
            } else {
                bArr[i2] = 0;
            }
            BigInteger bigIntegerShiftRight = bigIntegerClearBit.shiftRight(1);
            BigInteger bigIntegerAdd = b == 1 ? bigInteger.add(bigIntegerShiftRight) : bigInteger.subtract(bigIntegerShiftRight);
            BigInteger bigIntegerNegate = bigIntegerClearBit.shiftRight(1).negate();
            i2++;
            bigIntegerClearBit = bigIntegerAdd;
            bigInteger = bigIntegerNegate;
        }
    }

    public static byte[] tauAdicWNaf(byte b, jj1 jj1Var, byte b2, BigInteger bigInteger, BigInteger bigInteger2, jj1[] jj1VarArr) {
        boolean z;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int iBitLength = norm(b, jj1Var).bitLength();
        byte[] bArr = new byte[iBitLength > 30 ? iBitLength + 4 + b2 : b2 + 34];
        BigInteger bigIntegerShiftRight = bigInteger.shiftRight(1);
        BigInteger bigIntegerAdd = jj1Var.f57339u;
        BigInteger bigIntegerAdd2 = jj1Var.f57340v;
        int i = 0;
        while (true) {
            BigInteger bigInteger3 = InterfaceC1315uw.ZERO;
            if (bigIntegerAdd.equals(bigInteger3) && bigIntegerAdd2.equals(bigInteger3)) {
                return bArr;
            }
            if (bigIntegerAdd.testBit(0)) {
                BigInteger bigIntegerMod = bigIntegerAdd.add(bigIntegerAdd2.multiply(bigInteger2)).mod(bigInteger);
                if (bigIntegerMod.compareTo(bigIntegerShiftRight) >= 0) {
                    bigIntegerMod = bigIntegerMod.subtract(bigInteger);
                }
                byte bIntValue = (byte) bigIntegerMod.intValue();
                bArr[i] = bIntValue;
                if (bIntValue < 0) {
                    bIntValue = (byte) (-bIntValue);
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    bigIntegerAdd = bigIntegerAdd.subtract(jj1VarArr[bIntValue].f57339u);
                    bigIntegerAdd2 = bigIntegerAdd2.subtract(jj1VarArr[bIntValue].f57340v);
                } else {
                    bigIntegerAdd = bigIntegerAdd.add(jj1VarArr[bIntValue].f57339u);
                    bigIntegerAdd2 = bigIntegerAdd2.add(jj1VarArr[bIntValue].f57340v);
                }
            } else {
                bArr[i] = 0;
            }
            BigInteger bigIntegerShiftRight2 = bigIntegerAdd.shiftRight(1);
            BigInteger bigIntegerAdd3 = b == 1 ? bigIntegerAdd2.add(bigIntegerShiftRight2) : bigIntegerAdd2.subtract(bigIntegerShiftRight2);
            BigInteger bigIntegerNegate = bigIntegerAdd.shiftRight(1).negate();
            i++;
            bigIntegerAdd = bigIntegerAdd3;
            bigIntegerAdd2 = bigIntegerNegate;
        }
    }

    public static byte getMu(AbstractC1316ux.a1 a1Var) {
        if (a1Var.isKoblitz()) {
            return a1Var.getA().isZero() ? (byte) -1 : (byte) 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static BigInteger[] getSi(AbstractC1316ux.a1 a1Var) {
        if (!a1Var.isKoblitz()) {
            throw new IllegalArgumentException("si is defined for Koblitz curves only");
        }
        int fieldSize = a1Var.getFieldSize();
        int iIntValue = a1Var.getA().toBigInteger().intValue();
        byte mu = getMu(iIntValue);
        int shiftsForCofactor = getShiftsForCofactor(a1Var.getCofactor());
        BigInteger[] lucas = getLucas(mu, (fieldSize + 3) - iIntValue, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        BigInteger bigInteger = InterfaceC1315uw.ONE;
        return new BigInteger[]{bigInteger.add(lucas[1]).shiftRight(shiftsForCofactor), bigInteger.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    public static BigInteger norm(byte b, jj1 jj1Var) {
        BigInteger bigIntegerSubtract;
        BigInteger bigInteger = jj1Var.f57339u;
        BigInteger bigIntegerMultiply = bigInteger.multiply(bigInteger);
        BigInteger bigIntegerMultiply2 = jj1Var.f57339u.multiply(jj1Var.f57340v);
        BigInteger bigInteger2 = jj1Var.f57340v;
        BigInteger bigIntegerShiftLeft = bigInteger2.multiply(bigInteger2).shiftLeft(1);
        if (b == 1) {
            bigIntegerSubtract = bigIntegerMultiply.add(bigIntegerMultiply2);
        } else {
            if (b != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            bigIntegerSubtract = bigIntegerMultiply.subtract(bigIntegerMultiply2);
        }
        return bigIntegerSubtract.add(bigIntegerShiftLeft);
    }

    public static byte getMu(AbstractC1330va abstractC1330va) {
        return (byte) (abstractC1330va.isZero() ? -1 : 1);
    }
}
