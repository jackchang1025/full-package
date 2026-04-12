package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class bw0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f46009Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37"));

    /* renamed from: x */
    protected int[] f46010x;

    public bw0() {
        this.f46010x = qh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        aw0.add(this.f46010x, ((bw0) abstractC1330va).f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = qh0.create();
        aw0.addOne(this.f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        aw0.inv(((bw0) abstractC1330va).f46010x, iArrCreate);
        aw0.multiply(iArrCreate, this.f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof bw0) {
            return qh0.m214399eq(this.f46010x, ((bw0) obj).f46010x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP192K1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f46009Q.bitLength();
    }

    public int hashCode() {
        return f46009Q.hashCode() ^ C0133bg.hashCode(this.f46010x, 0, 6);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = qh0.create();
        aw0.inv(this.f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return qh0.isOne(this.f46010x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return qh0.isZero(this.f46010x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        aw0.multiply(this.f46010x, ((bw0) abstractC1330va).f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = qh0.create();
        aw0.negate(this.f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f46010x;
        if (qh0.isZero(iArr) || qh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = qh0.create();
        aw0.square(iArr, iArrCreate);
        aw0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = qh0.create();
        aw0.square(iArrCreate, iArrCreate2);
        aw0.multiply(iArrCreate2, iArr, iArrCreate2);
        int[] iArrCreate3 = qh0.create();
        aw0.squareN(iArrCreate2, 3, iArrCreate3);
        aw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        aw0.squareN(iArrCreate3, 2, iArrCreate3);
        aw0.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        aw0.squareN(iArrCreate3, 8, iArrCreate);
        aw0.multiply(iArrCreate, iArrCreate3, iArrCreate);
        aw0.squareN(iArrCreate, 3, iArrCreate3);
        aw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        int[] iArrCreate4 = qh0.create();
        aw0.squareN(iArrCreate3, 16, iArrCreate4);
        aw0.multiply(iArrCreate4, iArrCreate, iArrCreate4);
        aw0.squareN(iArrCreate4, 35, iArrCreate);
        aw0.multiply(iArrCreate, iArrCreate4, iArrCreate);
        aw0.squareN(iArrCreate, 70, iArrCreate4);
        aw0.multiply(iArrCreate4, iArrCreate, iArrCreate4);
        aw0.squareN(iArrCreate4, 19, iArrCreate);
        aw0.multiply(iArrCreate, iArrCreate3, iArrCreate);
        aw0.squareN(iArrCreate, 20, iArrCreate);
        aw0.multiply(iArrCreate, iArrCreate3, iArrCreate);
        aw0.squareN(iArrCreate, 4, iArrCreate);
        aw0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        aw0.squareN(iArrCreate, 6, iArrCreate);
        aw0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        aw0.square(iArrCreate, iArrCreate);
        aw0.square(iArrCreate, iArrCreate2);
        if (qh0.m214399eq(iArr, iArrCreate2)) {
            return new bw0(iArrCreate);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = qh0.create();
        aw0.square(this.f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        aw0.subtract(this.f46010x, ((bw0) abstractC1330va).f46010x, iArrCreate);
        return new bw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return qh0.getBit(this.f46010x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return qh0.toBigInteger(this.f46010x);
    }

    public bw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f46009Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192K1FieldElement");
        }
        this.f46010x = aw0.fromBigInteger(bigInteger);
    }

    public bw0(int[] iArr) {
        this.f46010x = iArr;
    }
}
