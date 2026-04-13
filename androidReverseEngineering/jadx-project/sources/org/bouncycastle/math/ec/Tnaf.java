package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes.dex */
class Tnaf {
    private static final BigInteger MINUS_ONE;
    private static final BigInteger MINUS_THREE;
    private static final BigInteger MINUS_TWO;
    public static final byte POW_2_WIDTH = 16;
    public static final byte WIDTH = 4;
    public static final ZTauElement[] alpha0;
    public static final byte[][] alpha0Tnaf;
    public static final ZTauElement[] alpha1;
    public static final byte[][] alpha1Tnaf;

    static {
        BigInteger bigInteger = ECConstants.ONE;
        BigInteger negate = bigInteger.negate();
        MINUS_ONE = negate;
        MINUS_TWO = ECConstants.TWO.negate();
        BigInteger negate2 = ECConstants.THREE.negate();
        MINUS_THREE = negate2;
        BigInteger bigInteger2 = ECConstants.ZERO;
        alpha0 = new ZTauElement[]{null, new ZTauElement(bigInteger, bigInteger2), null, new ZTauElement(negate2, negate), null, new ZTauElement(negate, negate), null, new ZTauElement(bigInteger, negate), null};
        alpha0Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
        alpha1 = new ZTauElement[]{null, new ZTauElement(bigInteger, bigInteger2), null, new ZTauElement(negate2, bigInteger), null, new ZTauElement(negate, bigInteger), null, new ZTauElement(bigInteger, bigInteger), null};
        alpha1Tnaf = new byte[][]{null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};
    }

    public static SimpleBigDecimal approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte b, int i2, int i3) {
        BigInteger multiply = bigInteger2.multiply(bigInteger.shiftRight(((i2 - r0) - 2) + b));
        BigInteger add = multiply.add(bigInteger3.multiply(multiply.shiftRight(i2)));
        int i4 = (((i2 + 5) / 2) + i3) - i3;
        BigInteger shiftRight = add.shiftRight(i4);
        if (add.testBit(i4 - 1)) {
            shiftRight = shiftRight.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(shiftRight, i3);
    }

    public static BigInteger[] getLucas(byte b, int i2, boolean z2) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        if (z2) {
            bigInteger = ECConstants.TWO;
            bigInteger2 = BigInteger.valueOf(b);
        } else {
            bigInteger = ECConstants.ZERO;
            bigInteger2 = ECConstants.ONE;
        }
        int i3 = 1;
        while (i3 < i2) {
            i3++;
            BigInteger bigInteger3 = bigInteger2;
            bigInteger2 = (b == 1 ? bigInteger2 : bigInteger2.negate()).subtract(bigInteger.shiftLeft(1));
            bigInteger = bigInteger3;
        }
        return new BigInteger[]{bigInteger, bigInteger2};
    }

    public static byte getMu(int i2) {
        return (byte) (i2 == 0 ? -1 : 1);
    }

    public static ECPoint.AbstractF2m[] getPreComp(ECPoint.AbstractF2m abstractF2m, byte b) {
        byte[][] bArr = b == 0 ? alpha0Tnaf : alpha1Tnaf;
        ECPoint.AbstractF2m[] abstractF2mArr = new ECPoint.AbstractF2m[(bArr.length + 1) >>> 1];
        abstractF2mArr[0] = abstractF2m;
        int length = bArr.length;
        for (int i2 = 3; i2 < length; i2 += 2) {
            abstractF2mArr[i2 >>> 1] = multiplyFromTnaf(abstractF2m, bArr[i2]);
        }
        abstractF2m.getCurve().normalizeAll(abstractF2mArr);
        return abstractF2mArr;
    }

    public static int getShiftsForCofactor(BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(ECConstants.TWO)) {
                return 1;
            }
            if (bigInteger.equals(ECConstants.FOUR)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static BigInteger[] getSi(int i2, int i3, BigInteger bigInteger) {
        byte mu = getMu(i3);
        int shiftsForCofactor = getShiftsForCofactor(bigInteger);
        BigInteger[] lucas = getLucas(mu, (i2 + 3) - i3, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        BigInteger bigInteger2 = ECConstants.ONE;
        return new BigInteger[]{bigInteger2.add(lucas[1]).shiftRight(shiftsForCofactor), bigInteger2.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    public static BigInteger getTw(byte b, int i2) {
        if (i2 == 4) {
            return b == 1 ? BigInteger.valueOf(6L) : BigInteger.valueOf(10L);
        }
        BigInteger[] lucas = getLucas(b, i2, false);
        BigInteger bit = ECConstants.ZERO.setBit(i2);
        return ECConstants.TWO.multiply(lucas[0]).multiply(lucas[1].modInverse(bit)).mod(bit);
    }

    public static ECPoint.AbstractF2m multiplyFromTnaf(ECPoint.AbstractF2m abstractF2m, byte[] bArr) {
        ECPoint.AbstractF2m abstractF2m2 = (ECPoint.AbstractF2m) abstractF2m.getCurve().getInfinity();
        ECPoint.AbstractF2m abstractF2m3 = (ECPoint.AbstractF2m) abstractF2m.negate();
        int i2 = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i2++;
            byte b = bArr[length];
            if (b != 0) {
                abstractF2m2 = (ECPoint.AbstractF2m) abstractF2m2.tauPow(i2).add(b > 0 ? abstractF2m : abstractF2m3);
                i2 = 0;
            }
        }
        return i2 > 0 ? abstractF2m2.tauPow(i2) : abstractF2m2;
    }

    public static ECPoint.AbstractF2m multiplyRTnaf(ECPoint.AbstractF2m abstractF2m, BigInteger bigInteger) {
        ECCurve.AbstractF2m abstractF2m2 = (ECCurve.AbstractF2m) abstractF2m.getCurve();
        int fieldSize = abstractF2m2.getFieldSize();
        int intValue = abstractF2m2.getA().toBigInteger().intValue();
        return multiplyTnaf(abstractF2m, partModReduction(bigInteger, fieldSize, (byte) intValue, abstractF2m2.getSi(), getMu(intValue), (byte) 10));
    }

    public static ECPoint.AbstractF2m multiplyTnaf(ECPoint.AbstractF2m abstractF2m, ZTauElement zTauElement) {
        return multiplyFromTnaf(abstractF2m, tauAdicNaf(getMu(((ECCurve.AbstractF2m) abstractF2m.getCurve()).getA()), zTauElement));
    }

    public static BigInteger norm(byte b, ZTauElement zTauElement) {
        BigInteger subtract;
        BigInteger bigInteger = zTauElement.f1421u;
        BigInteger multiply = bigInteger.multiply(bigInteger);
        BigInteger multiply2 = zTauElement.f1421u.multiply(zTauElement.f1422v);
        BigInteger bigInteger2 = zTauElement.f1422v;
        BigInteger shiftLeft = bigInteger2.multiply(bigInteger2).shiftLeft(1);
        if (b == 1) {
            subtract = multiply.add(multiply2);
        } else {
            if (b != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            subtract = multiply.subtract(multiply2);
        }
        return subtract.add(shiftLeft);
    }

    public static ZTauElement partModReduction(BigInteger bigInteger, int i2, byte b, BigInteger[] bigIntegerArr, byte b2, byte b3) {
        BigInteger add = b2 == 1 ? bigIntegerArr[0].add(bigIntegerArr[1]) : bigIntegerArr[0].subtract(bigIntegerArr[1]);
        BigInteger bigInteger2 = getLucas(b2, i2, true)[1];
        ZTauElement round = round(approximateDivisionByN(bigInteger, bigIntegerArr[0], bigInteger2, b, i2, b3), approximateDivisionByN(bigInteger, bigIntegerArr[1], bigInteger2, b, i2, b3), b2);
        return new ZTauElement(bigInteger.subtract(add.multiply(round.f1421u)).subtract(BigInteger.valueOf(2L).multiply(bigIntegerArr[1]).multiply(round.f1422v)), bigIntegerArr[1].multiply(round.f1421u).subtract(bigIntegerArr[0].multiply(round.f1422v)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0066, code lost:
    
        if (r5.compareTo(org.bouncycastle.math.ec.Tnaf.MINUS_ONE) < 0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0081, code lost:
    
        if (r5.compareTo(r9) >= 0) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x008a, code lost:
    
        if (r8.compareTo(org.bouncycastle.math.ec.Tnaf.MINUS_TWO) < 0) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ZTauElement round(SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2, byte b) {
        SimpleBigDecimal add;
        SimpleBigDecimal subtract;
        if (simpleBigDecimal2.getScale() != simpleBigDecimal.getScale()) {
            throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
        }
        int i2 = -1;
        int i3 = 1;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        BigInteger round = simpleBigDecimal.round();
        BigInteger round2 = simpleBigDecimal2.round();
        SimpleBigDecimal subtract2 = simpleBigDecimal.subtract(round);
        SimpleBigDecimal subtract3 = simpleBigDecimal2.subtract(round2);
        SimpleBigDecimal add2 = subtract2.add(subtract2);
        SimpleBigDecimal add3 = b == 1 ? add2.add(subtract3) : add2.subtract(subtract3);
        SimpleBigDecimal add4 = subtract3.add(subtract3).add(subtract3);
        SimpleBigDecimal add5 = add4.add(subtract3);
        if (b == 1) {
            add = subtract2.subtract(add4);
            subtract = subtract2.add(add5);
        } else {
            add = subtract2.add(add4);
            subtract = subtract2.subtract(add5);
        }
        BigInteger bigInteger = ECConstants.ONE;
        byte b2 = 0;
        if (add3.compareTo(bigInteger) < 0) {
            if (subtract.compareTo(ECConstants.TWO) < 0) {
                i3 = 0;
            }
            i3 = 0;
            b2 = b;
        }
        if (add3.compareTo(MINUS_ONE) < 0) {
        }
        b2 = (byte) (-b);
        i2 = i3;
        return new ZTauElement(round.add(BigInteger.valueOf(i2)), round2.add(BigInteger.valueOf(b2)));
    }

    public static ECPoint.AbstractF2m tau(ECPoint.AbstractF2m abstractF2m) {
        return abstractF2m.tau();
    }

    public static byte[] tauAdicNaf(byte b, ZTauElement zTauElement) {
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int bitLength = norm(b, zTauElement).bitLength();
        byte[] bArr = new byte[bitLength > 30 ? bitLength + 4 : 34];
        BigInteger bigInteger = zTauElement.f1421u;
        BigInteger bigInteger2 = zTauElement.f1422v;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            BigInteger bigInteger3 = ECConstants.ZERO;
            if (bigInteger.equals(bigInteger3) && bigInteger2.equals(bigInteger3)) {
                int i4 = i2 + 1;
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, 0, bArr2, 0, i4);
                return bArr2;
            }
            if (bigInteger.testBit(0)) {
                byte intValue = (byte) ECConstants.TWO.subtract(bigInteger.subtract(bigInteger2.shiftLeft(1)).mod(ECConstants.FOUR)).intValue();
                bArr[i3] = intValue;
                bigInteger = intValue == 1 ? bigInteger.clearBit(0) : bigInteger.add(ECConstants.ONE);
                i2 = i3;
            } else {
                bArr[i3] = 0;
            }
            BigInteger shiftRight = bigInteger.shiftRight(1);
            BigInteger add = b == 1 ? bigInteger2.add(shiftRight) : bigInteger2.subtract(shiftRight);
            BigInteger negate = bigInteger.shiftRight(1).negate();
            i3++;
            bigInteger = add;
            bigInteger2 = negate;
        }
    }

    public static byte[] tauAdicWNaf(byte b, ZTauElement zTauElement, byte b2, BigInteger bigInteger, BigInteger bigInteger2, ZTauElement[] zTauElementArr) {
        boolean z2;
        if (b != 1 && b != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int bitLength = norm(b, zTauElement).bitLength();
        byte[] bArr = new byte[bitLength > 30 ? bitLength + 4 + b2 : b2 + 34];
        BigInteger shiftRight = bigInteger.shiftRight(1);
        BigInteger bigInteger3 = zTauElement.f1421u;
        BigInteger bigInteger4 = zTauElement.f1422v;
        int i2 = 0;
        while (true) {
            BigInteger bigInteger5 = ECConstants.ZERO;
            if (bigInteger3.equals(bigInteger5) && bigInteger4.equals(bigInteger5)) {
                return bArr;
            }
            if (bigInteger3.testBit(0)) {
                BigInteger mod = bigInteger3.add(bigInteger4.multiply(bigInteger2)).mod(bigInteger);
                if (mod.compareTo(shiftRight) >= 0) {
                    mod = mod.subtract(bigInteger);
                }
                byte intValue = (byte) mod.intValue();
                bArr[i2] = intValue;
                if (intValue < 0) {
                    intValue = (byte) (-intValue);
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z2) {
                    bigInteger3 = bigInteger3.subtract(zTauElementArr[intValue].f1421u);
                    bigInteger4 = bigInteger4.subtract(zTauElementArr[intValue].f1422v);
                } else {
                    bigInteger3 = bigInteger3.add(zTauElementArr[intValue].f1421u);
                    bigInteger4 = bigInteger4.add(zTauElementArr[intValue].f1422v);
                }
            } else {
                bArr[i2] = 0;
            }
            BigInteger shiftRight2 = bigInteger3.shiftRight(1);
            BigInteger add = b == 1 ? bigInteger4.add(shiftRight2) : bigInteger4.subtract(shiftRight2);
            BigInteger negate = bigInteger3.shiftRight(1).negate();
            i2++;
            bigInteger3 = add;
            bigInteger4 = negate;
        }
    }

    public static byte getMu(ECCurve.AbstractF2m abstractF2m) {
        if (abstractF2m.isKoblitz()) {
            return abstractF2m.getA().isZero() ? (byte) -1 : (byte) 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static BigInteger[] getSi(ECCurve.AbstractF2m abstractF2m) {
        if (!abstractF2m.isKoblitz()) {
            throw new IllegalArgumentException("si is defined for Koblitz curves only");
        }
        int fieldSize = abstractF2m.getFieldSize();
        int intValue = abstractF2m.getA().toBigInteger().intValue();
        byte mu = getMu(intValue);
        int shiftsForCofactor = getShiftsForCofactor(abstractF2m.getCofactor());
        BigInteger[] lucas = getLucas(mu, (fieldSize + 3) - intValue, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        BigInteger bigInteger = ECConstants.ONE;
        return new BigInteger[]{bigInteger.add(lucas[1]).shiftRight(shiftsForCofactor), bigInteger.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    public static SimpleBigDecimal norm(byte b, SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2) {
        SimpleBigDecimal subtract;
        SimpleBigDecimal multiply = simpleBigDecimal.multiply(simpleBigDecimal);
        SimpleBigDecimal multiply2 = simpleBigDecimal.multiply(simpleBigDecimal2);
        SimpleBigDecimal shiftLeft = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
        if (b == 1) {
            subtract = multiply.add(multiply2);
        } else {
            if (b != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            subtract = multiply.subtract(multiply2);
        }
        return subtract.add(shiftLeft);
    }

    public static byte getMu(ECFieldElement eCFieldElement) {
        return (byte) (eCFieldElement.isZero() ? -1 : 1);
    }
}
