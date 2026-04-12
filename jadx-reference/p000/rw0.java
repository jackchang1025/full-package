package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class rw0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f59826Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F"));

    /* renamed from: x */
    protected int[] f59827x;

    public rw0() {
        this.f59827x = sh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        qw0.add(this.f59827x, ((rw0) abstractC1330va).f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = sh0.create();
        qw0.addOne(this.f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        qw0.inv(((rw0) abstractC1330va).f59827x, iArrCreate);
        qw0.multiply(iArrCreate, this.f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof rw0) {
            return sh0.m214618eq(this.f59827x, ((rw0) obj).f59827x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP256K1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f59826Q.bitLength();
    }

    public int hashCode() {
        return f59826Q.hashCode() ^ C0133bg.hashCode(this.f59827x, 0, 8);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = sh0.create();
        qw0.inv(this.f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return sh0.isOne(this.f59827x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return sh0.isZero(this.f59827x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        qw0.multiply(this.f59827x, ((rw0) abstractC1330va).f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = sh0.create();
        qw0.negate(this.f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f59827x;
        if (sh0.isZero(iArr) || sh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = sh0.create();
        qw0.square(iArr, iArrCreate);
        qw0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = sh0.create();
        qw0.square(iArrCreate, iArrCreate2);
        qw0.multiply(iArrCreate2, iArr, iArrCreate2);
        int[] iArrCreate3 = sh0.create();
        qw0.squareN(iArrCreate2, 3, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        qw0.squareN(iArrCreate3, 3, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        qw0.squareN(iArrCreate3, 2, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        int[] iArrCreate4 = sh0.create();
        qw0.squareN(iArrCreate3, 11, iArrCreate4);
        qw0.multiply(iArrCreate4, iArrCreate3, iArrCreate4);
        qw0.squareN(iArrCreate4, 22, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate4, iArrCreate3);
        int[] iArrCreate5 = sh0.create();
        qw0.squareN(iArrCreate3, 44, iArrCreate5);
        qw0.multiply(iArrCreate5, iArrCreate3, iArrCreate5);
        int[] iArrCreate6 = sh0.create();
        qw0.squareN(iArrCreate5, 88, iArrCreate6);
        qw0.multiply(iArrCreate6, iArrCreate5, iArrCreate6);
        qw0.squareN(iArrCreate6, 44, iArrCreate5);
        qw0.multiply(iArrCreate5, iArrCreate3, iArrCreate5);
        qw0.squareN(iArrCreate5, 3, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        qw0.squareN(iArrCreate3, 23, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate4, iArrCreate3);
        qw0.squareN(iArrCreate3, 6, iArrCreate3);
        qw0.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        qw0.squareN(iArrCreate3, 2, iArrCreate3);
        qw0.square(iArrCreate3, iArrCreate);
        if (sh0.m214618eq(iArr, iArrCreate)) {
            return new rw0(iArrCreate3);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = sh0.create();
        qw0.square(this.f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        qw0.subtract(this.f59827x, ((rw0) abstractC1330va).f59827x, iArrCreate);
        return new rw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return sh0.getBit(this.f59827x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return sh0.toBigInteger(this.f59827x);
    }

    public rw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f59826Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256K1FieldElement");
        }
        this.f59827x = qw0.fromBigInteger(bigInteger);
    }

    public rw0(int[] iArr) {
        this.f59827x = iArr;
    }
}
