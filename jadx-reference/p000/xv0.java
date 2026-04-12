package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class xv0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f61196Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73"));

    /* renamed from: x */
    protected int[] f61197x;

    public xv0() {
        this.f61197x = ph0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        wv0.add(this.f61197x, ((xv0) abstractC1330va).f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = ph0.create();
        wv0.addOne(this.f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        wv0.inv(((xv0) abstractC1330va).f61197x, iArrCreate);
        wv0.multiply(iArrCreate, this.f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof xv0) {
            return ph0.m214292eq(this.f61197x, ((xv0) obj).f61197x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP160R2Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f61196Q.bitLength();
    }

    public int hashCode() {
        return f61196Q.hashCode() ^ C0133bg.hashCode(this.f61197x, 0, 5);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = ph0.create();
        wv0.inv(this.f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return ph0.isOne(this.f61197x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return ph0.isZero(this.f61197x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        wv0.multiply(this.f61197x, ((xv0) abstractC1330va).f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = ph0.create();
        wv0.negate(this.f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f61197x;
        if (ph0.isZero(iArr) || ph0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = ph0.create();
        wv0.square(iArr, iArrCreate);
        wv0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = ph0.create();
        wv0.square(iArrCreate, iArrCreate2);
        wv0.multiply(iArrCreate2, iArr, iArrCreate2);
        int[] iArrCreate3 = ph0.create();
        wv0.square(iArrCreate2, iArrCreate3);
        wv0.multiply(iArrCreate3, iArr, iArrCreate3);
        int[] iArrCreate4 = ph0.create();
        wv0.squareN(iArrCreate3, 3, iArrCreate4);
        wv0.multiply(iArrCreate4, iArrCreate2, iArrCreate4);
        wv0.squareN(iArrCreate4, 7, iArrCreate3);
        wv0.multiply(iArrCreate3, iArrCreate4, iArrCreate3);
        wv0.squareN(iArrCreate3, 3, iArrCreate4);
        wv0.multiply(iArrCreate4, iArrCreate2, iArrCreate4);
        int[] iArrCreate5 = ph0.create();
        wv0.squareN(iArrCreate4, 14, iArrCreate5);
        wv0.multiply(iArrCreate5, iArrCreate3, iArrCreate5);
        wv0.squareN(iArrCreate5, 31, iArrCreate3);
        wv0.multiply(iArrCreate3, iArrCreate5, iArrCreate3);
        wv0.squareN(iArrCreate3, 62, iArrCreate5);
        wv0.multiply(iArrCreate5, iArrCreate3, iArrCreate5);
        wv0.squareN(iArrCreate5, 3, iArrCreate3);
        wv0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        wv0.squareN(iArrCreate3, 18, iArrCreate3);
        wv0.multiply(iArrCreate3, iArrCreate4, iArrCreate3);
        wv0.squareN(iArrCreate3, 2, iArrCreate3);
        wv0.multiply(iArrCreate3, iArr, iArrCreate3);
        wv0.squareN(iArrCreate3, 3, iArrCreate3);
        wv0.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        wv0.squareN(iArrCreate3, 6, iArrCreate3);
        wv0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        wv0.squareN(iArrCreate3, 2, iArrCreate3);
        wv0.multiply(iArrCreate3, iArr, iArrCreate3);
        wv0.square(iArrCreate3, iArrCreate);
        if (ph0.m214292eq(iArr, iArrCreate)) {
            return new xv0(iArrCreate3);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = ph0.create();
        wv0.square(this.f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        wv0.subtract(this.f61197x, ((xv0) abstractC1330va).f61197x, iArrCreate);
        return new xv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return ph0.getBit(this.f61197x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return ph0.toBigInteger(this.f61197x);
    }

    public xv0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f61196Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP160R2FieldElement");
        }
        this.f61197x = wv0.fromBigInteger(bigInteger);
    }

    public xv0(int[] iArr) {
        this.f61197x = iArr;
    }
}
