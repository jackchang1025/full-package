package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class ct0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f55517Q = new BigInteger(1, c40.decodeStrict("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f55518x;

    public ct0() {
        this.f55518x = sh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        bt0.add(this.f55518x, ((ct0) abstractC1330va).f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = sh0.create();
        bt0.addOne(this.f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        bt0.inv(((ct0) abstractC1330va).f55518x, iArrCreate);
        bt0.multiply(iArrCreate, this.f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ct0) {
            return sh0.m214618eq(this.f55518x, ((ct0) obj).f55518x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SM2P256V1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f55517Q.bitLength();
    }

    public int hashCode() {
        return f55517Q.hashCode() ^ C0133bg.hashCode(this.f55518x, 0, 8);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = sh0.create();
        bt0.inv(this.f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return sh0.isOne(this.f55518x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return sh0.isZero(this.f55518x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        bt0.multiply(this.f55518x, ((ct0) abstractC1330va).f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = sh0.create();
        bt0.negate(this.f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f55518x;
        if (sh0.isZero(iArr) || sh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = sh0.create();
        bt0.square(iArr, iArrCreate);
        bt0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = sh0.create();
        bt0.squareN(iArrCreate, 2, iArrCreate2);
        bt0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        int[] iArrCreate3 = sh0.create();
        bt0.squareN(iArrCreate2, 2, iArrCreate3);
        bt0.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        bt0.squareN(iArrCreate3, 6, iArrCreate);
        bt0.multiply(iArrCreate, iArrCreate3, iArrCreate);
        int[] iArrCreate4 = sh0.create();
        bt0.squareN(iArrCreate, 12, iArrCreate4);
        bt0.multiply(iArrCreate4, iArrCreate, iArrCreate4);
        bt0.squareN(iArrCreate4, 6, iArrCreate);
        bt0.multiply(iArrCreate, iArrCreate3, iArrCreate);
        bt0.square(iArrCreate, iArrCreate3);
        bt0.multiply(iArrCreate3, iArr, iArrCreate3);
        bt0.squareN(iArrCreate3, 31, iArrCreate4);
        bt0.multiply(iArrCreate4, iArrCreate3, iArrCreate);
        bt0.squareN(iArrCreate4, 32, iArrCreate4);
        bt0.multiply(iArrCreate4, iArrCreate, iArrCreate4);
        bt0.squareN(iArrCreate4, 62, iArrCreate4);
        bt0.multiply(iArrCreate4, iArrCreate, iArrCreate4);
        bt0.squareN(iArrCreate4, 4, iArrCreate4);
        bt0.multiply(iArrCreate4, iArrCreate2, iArrCreate4);
        bt0.squareN(iArrCreate4, 32, iArrCreate4);
        bt0.multiply(iArrCreate4, iArr, iArrCreate4);
        bt0.squareN(iArrCreate4, 62, iArrCreate4);
        bt0.square(iArrCreate4, iArrCreate2);
        if (sh0.m214618eq(iArr, iArrCreate2)) {
            return new ct0(iArrCreate4);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = sh0.create();
        bt0.square(this.f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        bt0.subtract(this.f55518x, ((ct0) abstractC1330va).f55518x, iArrCreate);
        return new ct0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return sh0.getBit(this.f55518x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return sh0.toBigInteger(this.f55518x);
    }

    public ct0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f55517Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SM2P256V1FieldElement");
        }
        this.f55518x = bt0.fromBigInteger(bigInteger);
    }

    public ct0(int[] iArr) {
        this.f55518x = iArr;
    }
}
