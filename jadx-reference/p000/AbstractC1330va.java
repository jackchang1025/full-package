package p000;

import java.math.BigInteger;
import java.util.Random;

/* renamed from: va */
/* loaded from: classes2.dex */
public abstract class AbstractC1330va implements InterfaceC1315uw {

    /* renamed from: va$a0 */
    public static abstract class a0 extends AbstractC1330va {
        public AbstractC1330va halfTrace() {
            int fieldSize = getFieldSize();
            if ((fieldSize & 1) == 0) {
                throw new IllegalStateException("Half-trace only defined for odd m");
            }
            int i = (fieldSize + 1) >>> 1;
            int iNumberOfLeadingZeros = 31 - q60.numberOfLeadingZeros(i);
            AbstractC1330va abstractC1330vaAdd = this;
            int i2 = 1;
            while (iNumberOfLeadingZeros > 0) {
                abstractC1330vaAdd = abstractC1330vaAdd.squarePow(i2 << 1).add(abstractC1330vaAdd);
                iNumberOfLeadingZeros--;
                i2 = i >>> iNumberOfLeadingZeros;
                if ((i2 & 1) != 0) {
                    abstractC1330vaAdd = abstractC1330vaAdd.squarePow(2).add(this);
                }
            }
            return abstractC1330vaAdd;
        }

        public boolean hasFastTrace() {
            return false;
        }

        public int trace() {
            int fieldSize = getFieldSize();
            int iNumberOfLeadingZeros = 31 - q60.numberOfLeadingZeros(fieldSize);
            AbstractC1330va abstractC1330vaAdd = this;
            int i = 1;
            while (iNumberOfLeadingZeros > 0) {
                abstractC1330vaAdd = abstractC1330vaAdd.squarePow(i).add(abstractC1330vaAdd);
                iNumberOfLeadingZeros--;
                i = fieldSize >>> iNumberOfLeadingZeros;
                if ((i & 1) != 0) {
                    abstractC1330vaAdd = abstractC1330vaAdd.square().add(this);
                }
            }
            if (abstractC1330vaAdd.isZero()) {
                return 0;
            }
            if (abstractC1330vaAdd.isOne()) {
                return 1;
            }
            throw new IllegalStateException("Internal error in trace calculation");
        }
    }

    /* renamed from: va$a1 */
    public static abstract class a1 extends AbstractC1330va {
    }

    /* renamed from: va$a2 */
    public static class a2 extends a0 {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;

        /* renamed from: ks */
        private int[] f60611ks;

        /* renamed from: m */
        private int f60612m;
        private int representation;

        /* renamed from: x */
        lc0 f60613x;

        public a2(int i, int i2, int i3, int i4, BigInteger bigInteger) {
            if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > i) {
                throw new IllegalArgumentException("x value invalid in F2m field element");
            }
            if (i3 == 0 && i4 == 0) {
                this.representation = 2;
                this.f60611ks = new int[]{i2};
            } else {
                if (i3 >= i4) {
                    throw new IllegalArgumentException("k2 must be smaller than k3");
                }
                if (i3 <= 0) {
                    throw new IllegalArgumentException("k2 must be larger than 0");
                }
                this.representation = 3;
                this.f60611ks = new int[]{i2, i3, i4};
            }
            this.f60612m = i;
            this.f60613x = new lc0(bigInteger);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va add(AbstractC1330va abstractC1330va) {
            lc0 lc0Var = (lc0) this.f60613x.clone();
            lc0Var.addShiftedByWords(((a2) abstractC1330va).f60613x, 0);
            return new a2(this.f60612m, this.f60611ks, lc0Var);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va addOne() {
            return new a2(this.f60612m, this.f60611ks, this.f60613x.addOne());
        }

        @Override // p000.AbstractC1330va
        public int bitLength() {
            return this.f60613x.degree();
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
            return multiply(abstractC1330va.invert());
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a2)) {
                return false;
            }
            a2 a2Var = (a2) obj;
            return this.f60612m == a2Var.f60612m && this.representation == a2Var.representation && C0133bg.areEqual(this.f60611ks, a2Var.f60611ks) && this.f60613x.equals(a2Var.f60613x);
        }

        @Override // p000.AbstractC1330va
        public String getFieldName() {
            return "F2m";
        }

        @Override // p000.AbstractC1330va
        public int getFieldSize() {
            return this.f60612m;
        }

        public int getK1() {
            return this.f60611ks[0];
        }

        public int getK2() {
            int[] iArr = this.f60611ks;
            if (iArr.length >= 2) {
                return iArr[1];
            }
            return 0;
        }

        public int getK3() {
            int[] iArr = this.f60611ks;
            if (iArr.length >= 3) {
                return iArr[2];
            }
            return 0;
        }

        public int getM() {
            return this.f60612m;
        }

        public int getRepresentation() {
            return this.representation;
        }

        public int hashCode() {
            return (this.f60613x.hashCode() ^ this.f60612m) ^ C0133bg.hashCode(this.f60611ks);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va invert() {
            int i = this.f60612m;
            int[] iArr = this.f60611ks;
            return new a2(i, iArr, this.f60613x.modInverse(i, iArr));
        }

        @Override // p000.AbstractC1330va
        public boolean isOne() {
            return this.f60613x.isOne();
        }

        @Override // p000.AbstractC1330va
        public boolean isZero() {
            return this.f60613x.isZero();
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
            int i = this.f60612m;
            int[] iArr = this.f60611ks;
            return new a2(i, iArr, this.f60613x.modMultiply(((a2) abstractC1330va).f60613x, i, iArr));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
            return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
            lc0 lc0Var = this.f60613x;
            lc0 lc0Var2 = ((a2) abstractC1330va).f60613x;
            lc0 lc0Var3 = ((a2) abstractC1330va2).f60613x;
            lc0 lc0Var4 = ((a2) abstractC1330va3).f60613x;
            lc0 lc0VarMultiply = lc0Var.multiply(lc0Var2, this.f60612m, this.f60611ks);
            lc0 lc0VarMultiply2 = lc0Var3.multiply(lc0Var4, this.f60612m, this.f60611ks);
            if (lc0VarMultiply == lc0Var || lc0VarMultiply == lc0Var2) {
                lc0VarMultiply = (lc0) lc0VarMultiply.clone();
            }
            lc0VarMultiply.addShiftedByWords(lc0VarMultiply2, 0);
            lc0VarMultiply.reduce(this.f60612m, this.f60611ks);
            return new a2(this.f60612m, this.f60611ks, lc0VarMultiply);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va sqrt() {
            return (this.f60613x.isZero() || this.f60613x.isOne()) ? this : squarePow(this.f60612m - 1);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va square() {
            int i = this.f60612m;
            int[] iArr = this.f60611ks;
            return new a2(i, iArr, this.f60613x.modSquare(i, iArr));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            return squarePlusProduct(abstractC1330va, abstractC1330va2);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            lc0 lc0Var = this.f60613x;
            lc0 lc0Var2 = ((a2) abstractC1330va).f60613x;
            lc0 lc0Var3 = ((a2) abstractC1330va2).f60613x;
            lc0 lc0VarSquare = lc0Var.square(this.f60612m, this.f60611ks);
            lc0 lc0VarMultiply = lc0Var2.multiply(lc0Var3, this.f60612m, this.f60611ks);
            if (lc0VarSquare == lc0Var) {
                lc0VarSquare = (lc0) lc0VarSquare.clone();
            }
            lc0VarSquare.addShiftedByWords(lc0VarMultiply, 0);
            lc0VarSquare.reduce(this.f60612m, this.f60611ks);
            return new a2(this.f60612m, this.f60611ks, lc0VarSquare);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va squarePow(int i) {
            if (i < 1) {
                return this;
            }
            int i2 = this.f60612m;
            int[] iArr = this.f60611ks;
            return new a2(i2, iArr, this.f60613x.modSquareN(i, i2, iArr));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
            return add(abstractC1330va);
        }

        @Override // p000.AbstractC1330va
        public boolean testBitZero() {
            return this.f60613x.testBitZero();
        }

        @Override // p000.AbstractC1330va
        public BigInteger toBigInteger() {
            return this.f60613x.toBigInteger();
        }

        public a2(int i, int[] iArr, lc0 lc0Var) {
            this.f60612m = i;
            this.representation = iArr.length == 1 ? 2 : 3;
            this.f60611ks = iArr;
            this.f60613x = lc0Var;
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va negate() {
            return this;
        }
    }

    /* renamed from: va$a3 */
    public static class a3 extends a1 {

        /* renamed from: q */
        BigInteger f60614q;

        /* renamed from: r */
        BigInteger f60615r;

        /* renamed from: x */
        BigInteger f60616x;

        public a3(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            if (bigInteger3 == null || bigInteger3.signum() < 0 || bigInteger3.compareTo(bigInteger) >= 0) {
                throw new IllegalArgumentException("x value invalid in Fp field element");
            }
            this.f60614q = bigInteger;
            this.f60615r = bigInteger2;
            this.f60616x = bigInteger3;
        }

        public static BigInteger calculateResidue(BigInteger bigInteger) {
            int iBitLength = bigInteger.bitLength();
            if (iBitLength < 96 || bigInteger.shiftRight(iBitLength - 64).longValue() != -1) {
                return null;
            }
            return InterfaceC1315uw.ONE.shiftLeft(iBitLength).subtract(bigInteger);
        }

        private AbstractC1330va checkSqrt(AbstractC1330va abstractC1330va) {
            if (abstractC1330va.square().equals(this)) {
                return abstractC1330va;
            }
            return null;
        }

        private BigInteger[] lucasSequence(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            int iBitLength = bigInteger3.bitLength();
            int lowestSetBit = bigInteger3.getLowestSetBit();
            BigInteger bigIntegerModMult = InterfaceC1315uw.ONE;
            BigInteger bigIntegerModReduce = bigInteger;
            BigInteger bigIntegerModMult2 = bigIntegerModMult;
            BigInteger bigIntegerModReduce2 = InterfaceC1315uw.TWO;
            BigInteger bigIntegerModMult3 = bigIntegerModMult2;
            for (int i = iBitLength - 1; i >= lowestSetBit + 1; i--) {
                bigIntegerModMult = modMult(bigIntegerModMult, bigIntegerModMult3);
                if (bigInteger3.testBit(i)) {
                    bigIntegerModMult3 = modMult(bigIntegerModMult, bigInteger2);
                    bigIntegerModMult2 = modMult(bigIntegerModMult2, bigIntegerModReduce);
                    bigIntegerModReduce2 = modReduce(bigIntegerModReduce.multiply(bigIntegerModReduce2).subtract(bigInteger.multiply(bigIntegerModMult)));
                    bigIntegerModReduce = modReduce(bigIntegerModReduce.multiply(bigIntegerModReduce).subtract(bigIntegerModMult3.shiftLeft(1)));
                } else {
                    BigInteger bigIntegerModReduce3 = modReduce(bigIntegerModMult2.multiply(bigIntegerModReduce2).subtract(bigIntegerModMult));
                    BigInteger bigIntegerModReduce4 = modReduce(bigIntegerModReduce.multiply(bigIntegerModReduce2).subtract(bigInteger.multiply(bigIntegerModMult)));
                    bigIntegerModReduce2 = modReduce(bigIntegerModReduce2.multiply(bigIntegerModReduce2).subtract(bigIntegerModMult.shiftLeft(1)));
                    bigIntegerModReduce = bigIntegerModReduce4;
                    bigIntegerModMult2 = bigIntegerModReduce3;
                    bigIntegerModMult3 = bigIntegerModMult;
                }
            }
            BigInteger bigIntegerModMult4 = modMult(bigIntegerModMult, bigIntegerModMult3);
            BigInteger bigIntegerModMult5 = modMult(bigIntegerModMult4, bigInteger2);
            BigInteger bigIntegerModReduce5 = modReduce(bigIntegerModMult2.multiply(bigIntegerModReduce2).subtract(bigIntegerModMult4));
            BigInteger bigIntegerModReduce6 = modReduce(bigIntegerModReduce.multiply(bigIntegerModReduce2).subtract(bigInteger.multiply(bigIntegerModMult4)));
            BigInteger bigIntegerModMult6 = modMult(bigIntegerModMult4, bigIntegerModMult5);
            for (int i2 = 1; i2 <= lowestSetBit; i2++) {
                bigIntegerModReduce5 = modMult(bigIntegerModReduce5, bigIntegerModReduce6);
                bigIntegerModReduce6 = modReduce(bigIntegerModReduce6.multiply(bigIntegerModReduce6).subtract(bigIntegerModMult6.shiftLeft(1)));
                bigIntegerModMult6 = modMult(bigIntegerModMult6, bigIntegerModMult6);
            }
            return new BigInteger[]{bigIntegerModReduce5, bigIntegerModReduce6};
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va add(AbstractC1330va abstractC1330va) {
            return new a3(this.f60614q, this.f60615r, modAdd(this.f60616x, abstractC1330va.toBigInteger()));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va addOne() {
            BigInteger bigIntegerAdd = this.f60616x.add(InterfaceC1315uw.ONE);
            if (bigIntegerAdd.compareTo(this.f60614q) == 0) {
                bigIntegerAdd = InterfaceC1315uw.ZERO;
            }
            return new a3(this.f60614q, this.f60615r, bigIntegerAdd);
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
            return new a3(this.f60614q, this.f60615r, modMult(this.f60616x, modInverse(abstractC1330va.toBigInteger())));
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof a3)) {
                return false;
            }
            a3 a3Var = (a3) obj;
            return this.f60614q.equals(a3Var.f60614q) && this.f60616x.equals(a3Var.f60616x);
        }

        @Override // p000.AbstractC1330va
        public String getFieldName() {
            return "Fp";
        }

        @Override // p000.AbstractC1330va
        public int getFieldSize() {
            return this.f60614q.bitLength();
        }

        public BigInteger getQ() {
            return this.f60614q;
        }

        public int hashCode() {
            return this.f60614q.hashCode() ^ this.f60616x.hashCode();
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va invert() {
            return new a3(this.f60614q, this.f60615r, modInverse(this.f60616x));
        }

        public BigInteger modAdd(BigInteger bigInteger, BigInteger bigInteger2) {
            BigInteger bigIntegerAdd = bigInteger.add(bigInteger2);
            return bigIntegerAdd.compareTo(this.f60614q) >= 0 ? bigIntegerAdd.subtract(this.f60614q) : bigIntegerAdd;
        }

        public BigInteger modDouble(BigInteger bigInteger) {
            BigInteger bigIntegerShiftLeft = bigInteger.shiftLeft(1);
            return bigIntegerShiftLeft.compareTo(this.f60614q) >= 0 ? bigIntegerShiftLeft.subtract(this.f60614q) : bigIntegerShiftLeft;
        }

        public BigInteger modHalf(BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.f60614q.add(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        public BigInteger modHalfAbs(BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.f60614q.subtract(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        public BigInteger modInverse(BigInteger bigInteger) {
            return C0427ds.modOddInverse(this.f60614q, bigInteger);
        }

        public BigInteger modMult(BigInteger bigInteger, BigInteger bigInteger2) {
            return modReduce(bigInteger.multiply(bigInteger2));
        }

        public BigInteger modReduce(BigInteger bigInteger) {
            if (this.f60615r == null) {
                return bigInteger.mod(this.f60614q);
            }
            boolean z = bigInteger.signum() < 0;
            if (z) {
                bigInteger = bigInteger.abs();
            }
            int iBitLength = this.f60614q.bitLength();
            boolean zEquals = this.f60615r.equals(InterfaceC1315uw.ONE);
            while (bigInteger.bitLength() > iBitLength + 1) {
                BigInteger bigIntegerShiftRight = bigInteger.shiftRight(iBitLength);
                BigInteger bigIntegerSubtract = bigInteger.subtract(bigIntegerShiftRight.shiftLeft(iBitLength));
                if (!zEquals) {
                    bigIntegerShiftRight = bigIntegerShiftRight.multiply(this.f60615r);
                }
                bigInteger = bigIntegerShiftRight.add(bigIntegerSubtract);
            }
            while (bigInteger.compareTo(this.f60614q) >= 0) {
                bigInteger = bigInteger.subtract(this.f60614q);
            }
            return (!z || bigInteger.signum() == 0) ? bigInteger : this.f60614q.subtract(bigInteger);
        }

        public BigInteger modSubtract(BigInteger bigInteger, BigInteger bigInteger2) {
            BigInteger bigIntegerSubtract = bigInteger.subtract(bigInteger2);
            return bigIntegerSubtract.signum() < 0 ? bigIntegerSubtract.add(this.f60614q) : bigIntegerSubtract;
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
            return new a3(this.f60614q, this.f60615r, modMult(this.f60616x, abstractC1330va.toBigInteger()));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
            BigInteger bigInteger = this.f60616x;
            BigInteger bigInteger2 = abstractC1330va.toBigInteger();
            BigInteger bigInteger3 = abstractC1330va2.toBigInteger();
            BigInteger bigInteger4 = abstractC1330va3.toBigInteger();
            return new a3(this.f60614q, this.f60615r, modReduce(bigInteger.multiply(bigInteger2).subtract(bigInteger3.multiply(bigInteger4))));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
            BigInteger bigInteger = this.f60616x;
            BigInteger bigInteger2 = abstractC1330va.toBigInteger();
            BigInteger bigInteger3 = abstractC1330va2.toBigInteger();
            BigInteger bigInteger4 = abstractC1330va3.toBigInteger();
            return new a3(this.f60614q, this.f60615r, modReduce(bigInteger.multiply(bigInteger2).add(bigInteger3.multiply(bigInteger4))));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va negate() {
            if (this.f60616x.signum() == 0) {
                return this;
            }
            BigInteger bigInteger = this.f60614q;
            return new a3(bigInteger, this.f60615r, bigInteger.subtract(this.f60616x));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va sqrt() {
            if (isZero() || isOne()) {
                return this;
            }
            if (!this.f60614q.testBit(0)) {
                throw new RuntimeException("not done yet");
            }
            if (this.f60614q.testBit(1)) {
                BigInteger bigIntegerAdd = this.f60614q.shiftRight(2).add(InterfaceC1315uw.ONE);
                BigInteger bigInteger = this.f60614q;
                return checkSqrt(new a3(bigInteger, this.f60615r, this.f60616x.modPow(bigIntegerAdd, bigInteger)));
            }
            if (this.f60614q.testBit(2)) {
                BigInteger bigIntegerModPow = this.f60616x.modPow(this.f60614q.shiftRight(3), this.f60614q);
                BigInteger bigIntegerModMult = modMult(bigIntegerModPow, this.f60616x);
                if (modMult(bigIntegerModMult, bigIntegerModPow).equals(InterfaceC1315uw.ONE)) {
                    return checkSqrt(new a3(this.f60614q, this.f60615r, bigIntegerModMult));
                }
                return checkSqrt(new a3(this.f60614q, this.f60615r, modMult(bigIntegerModMult, InterfaceC1315uw.TWO.modPow(this.f60614q.shiftRight(2), this.f60614q))));
            }
            BigInteger bigIntegerShiftRight = this.f60614q.shiftRight(1);
            BigInteger bigIntegerModPow2 = this.f60616x.modPow(bigIntegerShiftRight, this.f60614q);
            BigInteger bigInteger2 = InterfaceC1315uw.ONE;
            if (!bigIntegerModPow2.equals(bigInteger2)) {
                return null;
            }
            BigInteger bigInteger3 = this.f60616x;
            BigInteger bigIntegerModDouble = modDouble(modDouble(bigInteger3));
            BigInteger bigIntegerAdd2 = bigIntegerShiftRight.add(bigInteger2);
            BigInteger bigIntegerSubtract = this.f60614q.subtract(bigInteger2);
            Random random = new Random();
            while (true) {
                BigInteger bigInteger4 = new BigInteger(this.f60614q.bitLength(), random);
                if (bigInteger4.compareTo(this.f60614q) < 0 && modReduce(bigInteger4.multiply(bigInteger4).subtract(bigIntegerModDouble)).modPow(bigIntegerShiftRight, this.f60614q).equals(bigIntegerSubtract)) {
                    BigInteger[] bigIntegerArrLucasSequence = lucasSequence(bigInteger4, bigInteger3, bigIntegerAdd2);
                    BigInteger bigInteger5 = bigIntegerArrLucasSequence[0];
                    BigInteger bigInteger6 = bigIntegerArrLucasSequence[1];
                    if (modMult(bigInteger6, bigInteger6).equals(bigIntegerModDouble)) {
                        return new a3(this.f60614q, this.f60615r, modHalfAbs(bigInteger6));
                    }
                    if (!bigInteger5.equals(InterfaceC1315uw.ONE) && !bigInteger5.equals(bigIntegerSubtract)) {
                        return null;
                    }
                }
            }
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va square() {
            BigInteger bigInteger = this.f60614q;
            BigInteger bigInteger2 = this.f60615r;
            BigInteger bigInteger3 = this.f60616x;
            return new a3(bigInteger, bigInteger2, modMult(bigInteger3, bigInteger3));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            BigInteger bigInteger = this.f60616x;
            BigInteger bigInteger2 = abstractC1330va.toBigInteger();
            BigInteger bigInteger3 = abstractC1330va2.toBigInteger();
            return new a3(this.f60614q, this.f60615r, modReduce(bigInteger.multiply(bigInteger).subtract(bigInteger2.multiply(bigInteger3))));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            BigInteger bigInteger = this.f60616x;
            BigInteger bigInteger2 = abstractC1330va.toBigInteger();
            BigInteger bigInteger3 = abstractC1330va2.toBigInteger();
            return new a3(this.f60614q, this.f60615r, modReduce(bigInteger.multiply(bigInteger).add(bigInteger2.multiply(bigInteger3))));
        }

        @Override // p000.AbstractC1330va
        public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
            return new a3(this.f60614q, this.f60615r, modSubtract(this.f60616x, abstractC1330va.toBigInteger()));
        }

        @Override // p000.AbstractC1330va
        public BigInteger toBigInteger() {
            return this.f60616x;
        }
    }

    public abstract AbstractC1330va add(AbstractC1330va abstractC1330va);

    public abstract AbstractC1330va addOne();

    public int bitLength() {
        return toBigInteger().bitLength();
    }

    public abstract AbstractC1330va divide(AbstractC1330va abstractC1330va);

    public byte[] getEncoded() {
        return C0427ds.asUnsignedByteArray((getFieldSize() + 7) / 8, toBigInteger());
    }

    public abstract String getFieldName();

    public abstract int getFieldSize();

    public abstract AbstractC1330va invert();

    public boolean isOne() {
        return bitLength() == 1;
    }

    public boolean isZero() {
        return toBigInteger().signum() == 0;
    }

    public abstract AbstractC1330va multiply(AbstractC1330va abstractC1330va);

    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiply(abstractC1330va).subtract(abstractC1330va2.multiply(abstractC1330va3));
    }

    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiply(abstractC1330va).add(abstractC1330va2.multiply(abstractC1330va3));
    }

    public abstract AbstractC1330va negate();

    public abstract AbstractC1330va sqrt();

    public abstract AbstractC1330va square();

    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return square().subtract(abstractC1330va.multiply(abstractC1330va2));
    }

    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return square().add(abstractC1330va.multiply(abstractC1330va2));
    }

    public AbstractC1330va squarePow(int i) {
        AbstractC1330va abstractC1330vaSquare = this;
        for (int i2 = 0; i2 < i; i2++) {
            abstractC1330vaSquare = abstractC1330vaSquare.square();
        }
        return abstractC1330vaSquare;
    }

    public abstract AbstractC1330va subtract(AbstractC1330va abstractC1330va);

    public boolean testBitZero() {
        return toBigInteger().testBit(0);
    }

    public abstract BigInteger toBigInteger();

    public String toString() {
        return toBigInteger().toString(16);
    }
}
