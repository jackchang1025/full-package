package org.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
public abstract class ECFieldElement implements ECConstants {

    public static abstract class AbstractF2m extends ECFieldElement {
        public ECFieldElement halfTrace() {
            int fieldSize = getFieldSize();
            if ((fieldSize & 1) == 0) {
                throw new IllegalStateException("Half-trace only defined for odd m");
            }
            int i2 = (fieldSize + 1) >>> 1;
            int numberOfLeadingZeros = 31 - Integers.numberOfLeadingZeros(i2);
            ECFieldElement eCFieldElement = this;
            int i3 = 1;
            while (numberOfLeadingZeros > 0) {
                eCFieldElement = eCFieldElement.squarePow(i3 << 1).add(eCFieldElement);
                numberOfLeadingZeros--;
                i3 = i2 >>> numberOfLeadingZeros;
                if ((i3 & 1) != 0) {
                    eCFieldElement = eCFieldElement.squarePow(2).add(this);
                }
            }
            return eCFieldElement;
        }

        public boolean hasFastTrace() {
            return false;
        }

        public int trace() {
            int fieldSize = getFieldSize();
            int numberOfLeadingZeros = 31 - Integers.numberOfLeadingZeros(fieldSize);
            ECFieldElement eCFieldElement = this;
            int i2 = 1;
            while (numberOfLeadingZeros > 0) {
                eCFieldElement = eCFieldElement.squarePow(i2).add(eCFieldElement);
                numberOfLeadingZeros--;
                i2 = fieldSize >>> numberOfLeadingZeros;
                if ((i2 & 1) != 0) {
                    eCFieldElement = eCFieldElement.square().add(this);
                }
            }
            if (eCFieldElement.isZero()) {
                return 0;
            }
            if (eCFieldElement.isOne()) {
                return 1;
            }
            throw new IllegalStateException("Internal error in trace calculation");
        }
    }

    public static abstract class AbstractFp extends ECFieldElement {
    }

    public static class F2m extends AbstractF2m {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;
        private int[] ks;

        /* renamed from: m */
        private int f1414m;
        private int representation;

        /* renamed from: x */
        LongArray f1415x;

        public F2m(int i2, int i3, int i4, int i5, BigInteger bigInteger) {
            if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > i2) {
                throw new IllegalArgumentException("x value invalid in F2m field element");
            }
            if (i4 == 0 && i5 == 0) {
                this.representation = 2;
                this.ks = new int[]{i3};
            } else {
                if (i4 >= i5) {
                    throw new IllegalArgumentException("k2 must be smaller than k3");
                }
                if (i4 <= 0) {
                    throw new IllegalArgumentException("k2 must be larger than 0");
                }
                this.representation = 3;
                this.ks = new int[]{i3, i4, i5};
            }
            this.f1414m = i2;
            this.f1415x = new LongArray(bigInteger);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement add(ECFieldElement eCFieldElement) {
            LongArray longArray = (LongArray) this.f1415x.clone();
            longArray.addShiftedByWords(((F2m) eCFieldElement).f1415x, 0);
            return new F2m(this.f1414m, this.ks, longArray);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement addOne() {
            return new F2m(this.f1414m, this.ks, this.f1415x.addOne());
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public int bitLength() {
            return this.f1415x.degree();
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return multiply(eCFieldElement.invert());
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof F2m)) {
                return false;
            }
            F2m f2m = (F2m) obj;
            return this.f1414m == f2m.f1414m && this.representation == f2m.representation && Arrays.areEqual(this.ks, f2m.ks) && this.f1415x.equals(f2m.f1415x);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public String getFieldName() {
            return "F2m";
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.f1414m;
        }

        public int getK1() {
            return this.ks[0];
        }

        public int getK2() {
            int[] iArr = this.ks;
            if (iArr.length >= 2) {
                return iArr[1];
            }
            return 0;
        }

        public int getK3() {
            int[] iArr = this.ks;
            if (iArr.length >= 3) {
                return iArr[2];
            }
            return 0;
        }

        public int getM() {
            return this.f1414m;
        }

        public int getRepresentation() {
            return this.representation;
        }

        public int hashCode() {
            return (this.f1415x.hashCode() ^ this.f1414m) ^ Arrays.hashCode(this.ks);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement invert() {
            int i2 = this.f1414m;
            int[] iArr = this.ks;
            return new F2m(i2, iArr, this.f1415x.modInverse(i2, iArr));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public boolean isOne() {
            return this.f1415x.isOne();
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public boolean isZero() {
            return this.f1415x.isZero();
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            int i2 = this.f1414m;
            int[] iArr = this.ks;
            return new F2m(i2, iArr, this.f1415x.modMultiply(((F2m) eCFieldElement).f1415x, i2, iArr));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            LongArray longArray = this.f1415x;
            LongArray longArray2 = ((F2m) eCFieldElement).f1415x;
            LongArray longArray3 = ((F2m) eCFieldElement2).f1415x;
            LongArray longArray4 = ((F2m) eCFieldElement3).f1415x;
            LongArray multiply = longArray.multiply(longArray2, this.f1414m, this.ks);
            LongArray multiply2 = longArray3.multiply(longArray4, this.f1414m, this.ks);
            if (multiply == longArray || multiply == longArray2) {
                multiply = (LongArray) multiply.clone();
            }
            multiply.addShiftedByWords(multiply2, 0);
            multiply.reduce(this.f1414m, this.ks);
            return new F2m(this.f1414m, this.ks, multiply);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement negate() {
            return this;
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement sqrt() {
            return (this.f1415x.isZero() || this.f1415x.isOne()) ? this : squarePow(this.f1414m - 1);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement square() {
            int i2 = this.f1414m;
            int[] iArr = this.ks;
            return new F2m(i2, iArr, this.f1415x.modSquare(i2, iArr));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            return squarePlusProduct(eCFieldElement, eCFieldElement2);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            LongArray longArray = this.f1415x;
            LongArray longArray2 = ((F2m) eCFieldElement).f1415x;
            LongArray longArray3 = ((F2m) eCFieldElement2).f1415x;
            LongArray square = longArray.square(this.f1414m, this.ks);
            LongArray multiply = longArray2.multiply(longArray3, this.f1414m, this.ks);
            if (square == longArray) {
                square = (LongArray) square.clone();
            }
            square.addShiftedByWords(multiply, 0);
            square.reduce(this.f1414m, this.ks);
            return new F2m(this.f1414m, this.ks, square);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squarePow(int i2) {
            if (i2 < 1) {
                return this;
            }
            int i3 = this.f1414m;
            int[] iArr = this.ks;
            return new F2m(i3, iArr, this.f1415x.modSquareN(i2, i3, iArr));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return add(eCFieldElement);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public boolean testBitZero() {
            return this.f1415x.testBitZero();
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public BigInteger toBigInteger() {
            return this.f1415x.toBigInteger();
        }

        public F2m(int i2, int[] iArr, LongArray longArray) {
            this.f1414m = i2;
            this.representation = iArr.length == 1 ? 2 : 3;
            this.ks = iArr;
            this.f1415x = longArray;
        }
    }

    public static class Fp extends AbstractFp {

        /* renamed from: q */
        BigInteger f1416q;

        /* renamed from: r */
        BigInteger f1417r;

        /* renamed from: x */
        BigInteger f1418x;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            if (bigInteger3 == null || bigInteger3.signum() < 0 || bigInteger3.compareTo(bigInteger) >= 0) {
                throw new IllegalArgumentException("x value invalid in Fp field element");
            }
            this.f1416q = bigInteger;
            this.f1417r = bigInteger2;
            this.f1418x = bigInteger3;
        }

        public static BigInteger calculateResidue(BigInteger bigInteger) {
            int bitLength = bigInteger.bitLength();
            if (bitLength < 96 || bigInteger.shiftRight(bitLength - 64).longValue() != -1) {
                return null;
            }
            return ECConstants.ONE.shiftLeft(bitLength).subtract(bigInteger);
        }

        private ECFieldElement checkSqrt(ECFieldElement eCFieldElement) {
            if (eCFieldElement.square().equals(this)) {
                return eCFieldElement;
            }
            return null;
        }

        private BigInteger[] lucasSequence(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            int bitLength = bigInteger3.bitLength();
            int lowestSetBit = bigInteger3.getLowestSetBit();
            BigInteger bigInteger4 = ECConstants.ONE;
            BigInteger bigInteger5 = bigInteger;
            BigInteger bigInteger6 = bigInteger4;
            BigInteger bigInteger7 = ECConstants.TWO;
            BigInteger bigInteger8 = bigInteger6;
            for (int i2 = bitLength - 1; i2 >= lowestSetBit + 1; i2--) {
                bigInteger4 = modMult(bigInteger4, bigInteger8);
                if (bigInteger3.testBit(i2)) {
                    bigInteger8 = modMult(bigInteger4, bigInteger2);
                    bigInteger6 = modMult(bigInteger6, bigInteger5);
                    bigInteger7 = modReduce(bigInteger5.multiply(bigInteger7).subtract(bigInteger.multiply(bigInteger4)));
                    bigInteger5 = modReduce(bigInteger5.multiply(bigInteger5).subtract(bigInteger8.shiftLeft(1)));
                } else {
                    BigInteger modReduce = modReduce(bigInteger6.multiply(bigInteger7).subtract(bigInteger4));
                    BigInteger modReduce2 = modReduce(bigInteger5.multiply(bigInteger7).subtract(bigInteger.multiply(bigInteger4)));
                    bigInteger7 = modReduce(bigInteger7.multiply(bigInteger7).subtract(bigInteger4.shiftLeft(1)));
                    bigInteger5 = modReduce2;
                    bigInteger6 = modReduce;
                    bigInteger8 = bigInteger4;
                }
            }
            BigInteger modMult = modMult(bigInteger4, bigInteger8);
            BigInteger modMult2 = modMult(modMult, bigInteger2);
            BigInteger modReduce3 = modReduce(bigInteger6.multiply(bigInteger7).subtract(modMult));
            BigInteger modReduce4 = modReduce(bigInteger5.multiply(bigInteger7).subtract(bigInteger.multiply(modMult)));
            BigInteger modMult3 = modMult(modMult, modMult2);
            for (int i3 = 1; i3 <= lowestSetBit; i3++) {
                modReduce3 = modMult(modReduce3, modReduce4);
                modReduce4 = modReduce(modReduce4.multiply(modReduce4).subtract(modMult3.shiftLeft(1)));
                modMult3 = modMult(modMult3, modMult3);
            }
            return new BigInteger[]{modReduce3, modReduce4};
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement add(ECFieldElement eCFieldElement) {
            return new Fp(this.f1416q, this.f1417r, modAdd(this.f1418x, eCFieldElement.toBigInteger()));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement addOne() {
            BigInteger add = this.f1418x.add(ECConstants.ONE);
            if (add.compareTo(this.f1416q) == 0) {
                add = ECConstants.ZERO;
            }
            return new Fp(this.f1416q, this.f1417r, add);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return new Fp(this.f1416q, this.f1417r, modMult(this.f1418x, modInverse(eCFieldElement.toBigInteger())));
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Fp)) {
                return false;
            }
            Fp fp = (Fp) obj;
            return this.f1416q.equals(fp.f1416q) && this.f1418x.equals(fp.f1418x);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public String getFieldName() {
            return "Fp";
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.f1416q.bitLength();
        }

        public BigInteger getQ() {
            return this.f1416q;
        }

        public int hashCode() {
            return this.f1416q.hashCode() ^ this.f1418x.hashCode();
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement invert() {
            return new Fp(this.f1416q, this.f1417r, modInverse(this.f1418x));
        }

        public BigInteger modAdd(BigInteger bigInteger, BigInteger bigInteger2) {
            BigInteger add = bigInteger.add(bigInteger2);
            return add.compareTo(this.f1416q) >= 0 ? add.subtract(this.f1416q) : add;
        }

        public BigInteger modDouble(BigInteger bigInteger) {
            BigInteger shiftLeft = bigInteger.shiftLeft(1);
            return shiftLeft.compareTo(this.f1416q) >= 0 ? shiftLeft.subtract(this.f1416q) : shiftLeft;
        }

        public BigInteger modHalf(BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.f1416q.add(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        public BigInteger modHalfAbs(BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.f1416q.subtract(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        public BigInteger modInverse(BigInteger bigInteger) {
            return BigIntegers.modOddInverse(this.f1416q, bigInteger);
        }

        public BigInteger modMult(BigInteger bigInteger, BigInteger bigInteger2) {
            return modReduce(bigInteger.multiply(bigInteger2));
        }

        public BigInteger modReduce(BigInteger bigInteger) {
            if (this.f1417r == null) {
                return bigInteger.mod(this.f1416q);
            }
            boolean z2 = bigInteger.signum() < 0;
            if (z2) {
                bigInteger = bigInteger.abs();
            }
            int bitLength = this.f1416q.bitLength();
            boolean equals = this.f1417r.equals(ECConstants.ONE);
            while (bigInteger.bitLength() > bitLength + 1) {
                BigInteger shiftRight = bigInteger.shiftRight(bitLength);
                BigInteger subtract = bigInteger.subtract(shiftRight.shiftLeft(bitLength));
                if (!equals) {
                    shiftRight = shiftRight.multiply(this.f1417r);
                }
                bigInteger = shiftRight.add(subtract);
            }
            while (bigInteger.compareTo(this.f1416q) >= 0) {
                bigInteger = bigInteger.subtract(this.f1416q);
            }
            return (!z2 || bigInteger.signum() == 0) ? bigInteger : this.f1416q.subtract(bigInteger);
        }

        public BigInteger modSubtract(BigInteger bigInteger, BigInteger bigInteger2) {
            BigInteger subtract = bigInteger.subtract(bigInteger2);
            return subtract.signum() < 0 ? subtract.add(this.f1416q) : subtract;
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            return new Fp(this.f1416q, this.f1417r, modMult(this.f1418x, eCFieldElement.toBigInteger()));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            BigInteger bigInteger = this.f1418x;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            BigInteger bigInteger4 = eCFieldElement3.toBigInteger();
            return new Fp(this.f1416q, this.f1417r, modReduce(bigInteger.multiply(bigInteger2).subtract(bigInteger3.multiply(bigInteger4))));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            BigInteger bigInteger = this.f1418x;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            BigInteger bigInteger4 = eCFieldElement3.toBigInteger();
            return new Fp(this.f1416q, this.f1417r, modReduce(bigInteger.multiply(bigInteger2).add(bigInteger3.multiply(bigInteger4))));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement negate() {
            if (this.f1418x.signum() == 0) {
                return this;
            }
            BigInteger bigInteger = this.f1416q;
            return new Fp(bigInteger, this.f1417r, bigInteger.subtract(this.f1418x));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement sqrt() {
            if (isZero() || isOne()) {
                return this;
            }
            if (!this.f1416q.testBit(0)) {
                throw new RuntimeException("not done yet");
            }
            if (this.f1416q.testBit(1)) {
                BigInteger add = this.f1416q.shiftRight(2).add(ECConstants.ONE);
                BigInteger bigInteger = this.f1416q;
                return checkSqrt(new Fp(bigInteger, this.f1417r, this.f1418x.modPow(add, bigInteger)));
            }
            if (this.f1416q.testBit(2)) {
                BigInteger modPow = this.f1418x.modPow(this.f1416q.shiftRight(3), this.f1416q);
                BigInteger modMult = modMult(modPow, this.f1418x);
                if (modMult(modMult, modPow).equals(ECConstants.ONE)) {
                    return checkSqrt(new Fp(this.f1416q, this.f1417r, modMult));
                }
                return checkSqrt(new Fp(this.f1416q, this.f1417r, modMult(modMult, ECConstants.TWO.modPow(this.f1416q.shiftRight(2), this.f1416q))));
            }
            BigInteger shiftRight = this.f1416q.shiftRight(1);
            BigInteger modPow2 = this.f1418x.modPow(shiftRight, this.f1416q);
            BigInteger bigInteger2 = ECConstants.ONE;
            if (!modPow2.equals(bigInteger2)) {
                return null;
            }
            BigInteger bigInteger3 = this.f1418x;
            BigInteger modDouble = modDouble(modDouble(bigInteger3));
            BigInteger add2 = shiftRight.add(bigInteger2);
            BigInteger subtract = this.f1416q.subtract(bigInteger2);
            Random random = new Random();
            while (true) {
                BigInteger bigInteger4 = new BigInteger(this.f1416q.bitLength(), random);
                if (bigInteger4.compareTo(this.f1416q) < 0 && modReduce(bigInteger4.multiply(bigInteger4).subtract(modDouble)).modPow(shiftRight, this.f1416q).equals(subtract)) {
                    BigInteger[] lucasSequence = lucasSequence(bigInteger4, bigInteger3, add2);
                    BigInteger bigInteger5 = lucasSequence[0];
                    BigInteger bigInteger6 = lucasSequence[1];
                    if (modMult(bigInteger6, bigInteger6).equals(modDouble)) {
                        return new Fp(this.f1416q, this.f1417r, modHalfAbs(bigInteger6));
                    }
                    if (!bigInteger5.equals(ECConstants.ONE) && !bigInteger5.equals(subtract)) {
                        return null;
                    }
                }
            }
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement square() {
            BigInteger bigInteger = this.f1416q;
            BigInteger bigInteger2 = this.f1417r;
            BigInteger bigInteger3 = this.f1418x;
            return new Fp(bigInteger, bigInteger2, modMult(bigInteger3, bigInteger3));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            BigInteger bigInteger = this.f1418x;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            return new Fp(this.f1416q, this.f1417r, modReduce(bigInteger.multiply(bigInteger).subtract(bigInteger2.multiply(bigInteger3))));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            BigInteger bigInteger = this.f1418x;
            BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            return new Fp(this.f1416q, this.f1417r, modReduce(bigInteger.multiply(bigInteger).add(bigInteger2.multiply(bigInteger3))));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return new Fp(this.f1416q, this.f1417r, modSubtract(this.f1418x, eCFieldElement.toBigInteger()));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public BigInteger toBigInteger() {
            return this.f1418x;
        }
    }

    public abstract ECFieldElement add(ECFieldElement eCFieldElement);

    public abstract ECFieldElement addOne();

    public int bitLength() {
        return toBigInteger().bitLength();
    }

    public abstract ECFieldElement divide(ECFieldElement eCFieldElement);

    public byte[] getEncoded() {
        return BigIntegers.asUnsignedByteArray((getFieldSize() + 7) / 8, toBigInteger());
    }

    public abstract String getFieldName();

    public abstract int getFieldSize();

    public abstract ECFieldElement invert();

    public boolean isOne() {
        return bitLength() == 1;
    }

    public boolean isZero() {
        return toBigInteger().signum() == 0;
    }

    public abstract ECFieldElement multiply(ECFieldElement eCFieldElement);

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiply(eCFieldElement).subtract(eCFieldElement2.multiply(eCFieldElement3));
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return multiply(eCFieldElement).add(eCFieldElement2.multiply(eCFieldElement3));
    }

    public abstract ECFieldElement negate();

    public abstract ECFieldElement sqrt();

    public abstract ECFieldElement square();

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return square().subtract(eCFieldElement.multiply(eCFieldElement2));
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return square().add(eCFieldElement.multiply(eCFieldElement2));
    }

    public ECFieldElement squarePow(int i2) {
        ECFieldElement eCFieldElement = this;
        for (int i3 = 0; i3 < i2; i3++) {
            eCFieldElement = eCFieldElement.square();
        }
        return eCFieldElement;
    }

    public abstract ECFieldElement subtract(ECFieldElement eCFieldElement);

    public boolean testBitZero() {
        return toBigInteger().testBit(0);
    }

    public abstract BigInteger toBigInteger();

    public String toString() {
        return toBigInteger().toString(16);
    }
}
