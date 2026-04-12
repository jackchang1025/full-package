package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class zw0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f61594Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF"));

    /* renamed from: x */
    protected int[] f61595x;

    public zw0() {
        this.f61595x = yh0.create(12);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(12);
        yw0.add(this.f61595x, ((zw0) abstractC1330va).f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = yh0.create(12);
        yw0.addOne(this.f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(12);
        yw0.inv(((zw0) abstractC1330va).f61595x, iArrCreate);
        yw0.multiply(iArrCreate, this.f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zw0) {
            return yh0.m215288eq(12, this.f61595x, ((zw0) obj).f61595x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP384R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f61594Q.bitLength();
    }

    public int hashCode() {
        return f61594Q.hashCode() ^ C0133bg.hashCode(this.f61595x, 0, 12);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = yh0.create(12);
        yw0.inv(this.f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return yh0.isOne(12, this.f61595x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return yh0.isZero(12, this.f61595x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(12);
        yw0.multiply(this.f61595x, ((zw0) abstractC1330va).f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = yh0.create(12);
        yw0.negate(this.f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f61595x;
        if (yh0.isZero(12, iArr) || yh0.isOne(12, iArr)) {
            return this;
        }
        int[] iArrCreate = yh0.create(12);
        int[] iArrCreate2 = yh0.create(12);
        int[] iArrCreate3 = yh0.create(12);
        int[] iArrCreate4 = yh0.create(12);
        yw0.square(iArr, iArrCreate);
        yw0.multiply(iArrCreate, iArr, iArrCreate);
        yw0.squareN(iArrCreate, 2, iArrCreate2);
        yw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        yw0.square(iArrCreate2, iArrCreate2);
        yw0.multiply(iArrCreate2, iArr, iArrCreate2);
        yw0.squareN(iArrCreate2, 5, iArrCreate3);
        yw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        yw0.squareN(iArrCreate3, 5, iArrCreate4);
        yw0.multiply(iArrCreate4, iArrCreate2, iArrCreate4);
        yw0.squareN(iArrCreate4, 15, iArrCreate2);
        yw0.multiply(iArrCreate2, iArrCreate4, iArrCreate2);
        yw0.squareN(iArrCreate2, 2, iArrCreate3);
        yw0.multiply(iArrCreate, iArrCreate3, iArrCreate);
        yw0.squareN(iArrCreate3, 28, iArrCreate3);
        yw0.multiply(iArrCreate2, iArrCreate3, iArrCreate2);
        yw0.squareN(iArrCreate2, 60, iArrCreate3);
        yw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        yw0.squareN(iArrCreate3, 120, iArrCreate2);
        yw0.multiply(iArrCreate2, iArrCreate3, iArrCreate2);
        yw0.squareN(iArrCreate2, 15, iArrCreate2);
        yw0.multiply(iArrCreate2, iArrCreate4, iArrCreate2);
        yw0.squareN(iArrCreate2, 33, iArrCreate2);
        yw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        yw0.squareN(iArrCreate2, 64, iArrCreate2);
        yw0.multiply(iArrCreate2, iArr, iArrCreate2);
        yw0.squareN(iArrCreate2, 30, iArrCreate);
        yw0.square(iArrCreate, iArrCreate2);
        if (yh0.m215288eq(12, iArr, iArrCreate2)) {
            return new zw0(iArrCreate);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = yh0.create(12);
        yw0.square(this.f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(12);
        yw0.subtract(this.f61595x, ((zw0) abstractC1330va).f61595x, iArrCreate);
        return new zw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return yh0.getBit(this.f61595x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return yh0.toBigInteger(12, this.f61595x);
    }

    public zw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f61594Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP384R1FieldElement");
        }
        this.f61595x = yw0.fromBigInteger(bigInteger);
    }

    public zw0(int[] iArr) {
        this.f61595x = iArr;
    }
}
