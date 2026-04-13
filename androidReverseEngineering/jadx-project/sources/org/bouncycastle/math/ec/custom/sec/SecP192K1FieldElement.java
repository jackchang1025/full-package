package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat192;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecP192K1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f1450Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37"));

    /* renamed from: x */
    protected int[] f1451x;

    public SecP192K1FieldElement() {
        this.f1451x = Nat192.create();
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192K1Field.add(this.f1451x, ((SecP192K1FieldElement) eCFieldElement).f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat192.create();
        SecP192K1Field.addOne(this.f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192K1Field.inv(((SecP192K1FieldElement) eCFieldElement).f1451x, create);
        SecP192K1Field.multiply(create, this.f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP192K1FieldElement) {
            return Nat192.eq(this.f1451x, ((SecP192K1FieldElement) obj).f1451x);
        }
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP192K1Field";
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return f1450Q.bitLength();
    }

    public int hashCode() {
        return f1450Q.hashCode() ^ Arrays.hashCode(this.f1451x, 0, 6);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat192.create();
        SecP192K1Field.inv(this.f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat192.isOne(this.f1451x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat192.isZero(this.f1451x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192K1Field.multiply(this.f1451x, ((SecP192K1FieldElement) eCFieldElement).f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat192.create();
        SecP192K1Field.negate(this.f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.f1451x;
        if (Nat192.isZero(iArr) || Nat192.isOne(iArr)) {
            return this;
        }
        int[] create = Nat192.create();
        SecP192K1Field.square(iArr, create);
        SecP192K1Field.multiply(create, iArr, create);
        int[] create2 = Nat192.create();
        SecP192K1Field.square(create, create2);
        SecP192K1Field.multiply(create2, iArr, create2);
        int[] create3 = Nat192.create();
        SecP192K1Field.squareN(create2, 3, create3);
        SecP192K1Field.multiply(create3, create2, create3);
        SecP192K1Field.squareN(create3, 2, create3);
        SecP192K1Field.multiply(create3, create, create3);
        SecP192K1Field.squareN(create3, 8, create);
        SecP192K1Field.multiply(create, create3, create);
        SecP192K1Field.squareN(create, 3, create3);
        SecP192K1Field.multiply(create3, create2, create3);
        int[] create4 = Nat192.create();
        SecP192K1Field.squareN(create3, 16, create4);
        SecP192K1Field.multiply(create4, create, create4);
        SecP192K1Field.squareN(create4, 35, create);
        SecP192K1Field.multiply(create, create4, create);
        SecP192K1Field.squareN(create, 70, create4);
        SecP192K1Field.multiply(create4, create, create4);
        SecP192K1Field.squareN(create4, 19, create);
        SecP192K1Field.multiply(create, create3, create);
        SecP192K1Field.squareN(create, 20, create);
        SecP192K1Field.multiply(create, create3, create);
        SecP192K1Field.squareN(create, 4, create);
        SecP192K1Field.multiply(create, create2, create);
        SecP192K1Field.squareN(create, 6, create);
        SecP192K1Field.multiply(create, create2, create);
        SecP192K1Field.square(create, create);
        SecP192K1Field.square(create, create2);
        if (Nat192.eq(iArr, create2)) {
            return new SecP192K1FieldElement(create);
        }
        return null;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat192.create();
        SecP192K1Field.square(this.f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat192.create();
        SecP192K1Field.subtract(this.f1451x, ((SecP192K1FieldElement) eCFieldElement).f1451x, create);
        return new SecP192K1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat192.getBit(this.f1451x, 0) == 1;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat192.toBigInteger(this.f1451x);
    }

    public SecP192K1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f1450Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192K1FieldElement");
        }
        this.f1451x = SecP192K1Field.fromBigInteger(bigInteger);
    }

    public SecP192K1FieldElement(int[] iArr) {
        this.f1451x = iArr;
    }
}
