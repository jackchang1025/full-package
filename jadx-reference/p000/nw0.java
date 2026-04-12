package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class nw0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f58699Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001"));

    /* renamed from: x */
    protected int[] f58700x;

    public nw0() {
        this.f58700x = rh0.create();
    }

    /* renamed from: RM */
    private static void m214145RM(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, int[] iArr6, int[] iArr7) {
        mw0.multiply(iArr5, iArr3, iArr7);
        mw0.multiply(iArr7, iArr, iArr7);
        mw0.multiply(iArr4, iArr2, iArr6);
        mw0.add(iArr6, iArr7, iArr6);
        mw0.multiply(iArr4, iArr3, iArr7);
        rh0.copy(iArr6, iArr4);
        mw0.multiply(iArr5, iArr2, iArr5);
        mw0.add(iArr5, iArr7, iArr5);
        mw0.square(iArr5, iArr6);
        mw0.multiply(iArr6, iArr, iArr6);
    }

    /* renamed from: RP */
    private static void m214146RP(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        rh0.copy(iArr, iArr4);
        int[] iArrCreate = rh0.create();
        int[] iArrCreate2 = rh0.create();
        for (int i = 0; i < 7; i++) {
            rh0.copy(iArr2, iArrCreate);
            rh0.copy(iArr3, iArrCreate2);
            int i2 = 1 << i;
            while (true) {
                i2--;
                if (i2 >= 0) {
                    m214147RS(iArr2, iArr3, iArr4, iArr5);
                }
            }
            m214145RM(iArr, iArrCreate, iArrCreate2, iArr2, iArr3, iArr4, iArr5);
        }
    }

    /* renamed from: RS */
    private static void m214147RS(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        mw0.multiply(iArr2, iArr, iArr2);
        mw0.twice(iArr2, iArr2);
        mw0.square(iArr, iArr4);
        mw0.add(iArr3, iArr4, iArr);
        mw0.multiply(iArr3, iArr4, iArr3);
        mw0.reduce32(yh0.shiftUpBits(7, iArr3, 2, 0), iArr3);
    }

    private static boolean isSquare(int[] iArr) {
        int[] iArrCreate = rh0.create();
        int[] iArrCreate2 = rh0.create();
        rh0.copy(iArr, iArrCreate);
        for (int i = 0; i < 7; i++) {
            rh0.copy(iArrCreate, iArrCreate2);
            mw0.squareN(iArrCreate, 1 << i, iArrCreate);
            mw0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        }
        mw0.squareN(iArrCreate, 95, iArrCreate);
        return rh0.isOne(iArrCreate);
    }

    private static boolean trySqrt(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrCreate = rh0.create();
        rh0.copy(iArr2, iArrCreate);
        int[] iArrCreate2 = rh0.create();
        iArrCreate2[0] = 1;
        int[] iArrCreate3 = rh0.create();
        m214146RP(iArr, iArrCreate, iArrCreate2, iArrCreate3, iArr3);
        int[] iArrCreate4 = rh0.create();
        int[] iArrCreate5 = rh0.create();
        for (int i = 1; i < 96; i++) {
            rh0.copy(iArrCreate, iArrCreate4);
            rh0.copy(iArrCreate2, iArrCreate5);
            m214147RS(iArrCreate, iArrCreate2, iArrCreate3, iArr3);
            if (rh0.isZero(iArrCreate)) {
                mw0.inv(iArrCreate5, iArr3);
                mw0.multiply(iArr3, iArrCreate4, iArr3);
                return true;
            }
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        mw0.add(this.f58700x, ((nw0) abstractC1330va).f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = rh0.create();
        mw0.addOne(this.f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        mw0.inv(((nw0) abstractC1330va).f58700x, iArrCreate);
        mw0.multiply(iArrCreate, this.f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof nw0) {
            return rh0.m214542eq(this.f58700x, ((nw0) obj).f58700x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP224R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f58699Q.bitLength();
    }

    public int hashCode() {
        return f58699Q.hashCode() ^ C0133bg.hashCode(this.f58700x, 0, 7);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = rh0.create();
        mw0.inv(this.f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return rh0.isOne(this.f58700x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return rh0.isZero(this.f58700x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        mw0.multiply(this.f58700x, ((nw0) abstractC1330va).f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = rh0.create();
        mw0.negate(this.f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f58700x;
        if (rh0.isZero(iArr) || rh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = rh0.create();
        mw0.negate(iArr, iArrCreate);
        int[] iArrRandom = ig0.random(mw0.f58406P);
        int[] iArrCreate2 = rh0.create();
        if (!isSquare(iArr)) {
            return null;
        }
        while (!trySqrt(iArrCreate, iArrRandom, iArrCreate2)) {
            mw0.addOne(iArrRandom, iArrRandom);
        }
        mw0.square(iArrCreate2, iArrRandom);
        if (rh0.m214542eq(iArr, iArrRandom)) {
            return new nw0(iArrCreate2);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = rh0.create();
        mw0.square(this.f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        mw0.subtract(this.f58700x, ((nw0) abstractC1330va).f58700x, iArrCreate);
        return new nw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return rh0.getBit(this.f58700x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return rh0.toBigInteger(this.f58700x);
    }

    public nw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f58699Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP224R1FieldElement");
        }
        this.f58700x = mw0.fromBigInteger(bigInteger);
    }

    public nw0(int[] iArr) {
        this.f58700x = iArr;
    }
}
