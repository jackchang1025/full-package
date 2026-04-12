package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class nv0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f58697Q = new BigInteger(1, c40.decodeStrict("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f58698x;

    public nv0() {
        this.f58698x = oh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = oh0.create();
        mv0.add(this.f58698x, ((nv0) abstractC1330va).f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = oh0.create();
        mv0.addOne(this.f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = oh0.create();
        mv0.inv(((nv0) abstractC1330va).f58698x, iArrCreate);
        mv0.multiply(iArrCreate, this.f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof nv0) {
            return oh0.m214215eq(this.f58698x, ((nv0) obj).f58698x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP128R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f58697Q.bitLength();
    }

    public int hashCode() {
        return f58697Q.hashCode() ^ C0133bg.hashCode(this.f58698x, 0, 4);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = oh0.create();
        mv0.inv(this.f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return oh0.isOne(this.f58698x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return oh0.isZero(this.f58698x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = oh0.create();
        mv0.multiply(this.f58698x, ((nv0) abstractC1330va).f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = oh0.create();
        mv0.negate(this.f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f58698x;
        if (oh0.isZero(iArr) || oh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = oh0.create();
        mv0.square(iArr, iArrCreate);
        mv0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = oh0.create();
        mv0.squareN(iArrCreate, 2, iArrCreate2);
        mv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        int[] iArrCreate3 = oh0.create();
        mv0.squareN(iArrCreate2, 4, iArrCreate3);
        mv0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        mv0.squareN(iArrCreate3, 2, iArrCreate2);
        mv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        mv0.squareN(iArrCreate2, 10, iArrCreate);
        mv0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        mv0.squareN(iArrCreate, 10, iArrCreate3);
        mv0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        mv0.square(iArrCreate3, iArrCreate2);
        mv0.multiply(iArrCreate2, iArr, iArrCreate2);
        mv0.squareN(iArrCreate2, 95, iArrCreate2);
        mv0.square(iArrCreate2, iArrCreate3);
        if (oh0.m214215eq(iArr, iArrCreate3)) {
            return new nv0(iArrCreate2);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = oh0.create();
        mv0.square(this.f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = oh0.create();
        mv0.subtract(this.f58698x, ((nv0) abstractC1330va).f58698x, iArrCreate);
        return new nv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return oh0.getBit(this.f58698x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return oh0.toBigInteger(this.f58698x);
    }

    public nv0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f58697Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP128R1FieldElement");
        }
        this.f58698x = mv0.fromBigInteger(bigInteger);
    }

    public nv0(int[] iArr) {
        this.f58698x = iArr;
    }
}
