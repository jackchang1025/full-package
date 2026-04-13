package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecP256R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f1473Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f1474x;

    public SecP256R1FieldElement() {
        this.f1474x = Nat256.create();
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.add(this.f1474x, ((SecP256R1FieldElement) eCFieldElement).f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat256.create();
        SecP256R1Field.addOne(this.f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.inv(((SecP256R1FieldElement) eCFieldElement).f1474x, create);
        SecP256R1Field.multiply(create, this.f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP256R1FieldElement) {
            return Nat256.eq(this.f1474x, ((SecP256R1FieldElement) obj).f1474x);
        }
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP256R1Field";
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return f1473Q.bitLength();
    }

    public int hashCode() {
        return f1473Q.hashCode() ^ Arrays.hashCode(this.f1474x, 0, 8);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat256.create();
        SecP256R1Field.inv(this.f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat256.isOne(this.f1474x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat256.isZero(this.f1474x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.multiply(this.f1474x, ((SecP256R1FieldElement) eCFieldElement).f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat256.create();
        SecP256R1Field.negate(this.f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.f1474x;
        if (Nat256.isZero(iArr) || Nat256.isOne(iArr)) {
            return this;
        }
        int[] create = Nat256.create();
        int[] create2 = Nat256.create();
        SecP256R1Field.square(iArr, create);
        SecP256R1Field.multiply(create, iArr, create);
        SecP256R1Field.squareN(create, 2, create2);
        SecP256R1Field.multiply(create2, create, create2);
        SecP256R1Field.squareN(create2, 4, create);
        SecP256R1Field.multiply(create, create2, create);
        SecP256R1Field.squareN(create, 8, create2);
        SecP256R1Field.multiply(create2, create, create2);
        SecP256R1Field.squareN(create2, 16, create);
        SecP256R1Field.multiply(create, create2, create);
        SecP256R1Field.squareN(create, 32, create);
        SecP256R1Field.multiply(create, iArr, create);
        SecP256R1Field.squareN(create, 96, create);
        SecP256R1Field.multiply(create, iArr, create);
        SecP256R1Field.squareN(create, 94, create);
        SecP256R1Field.square(create, create2);
        if (Nat256.eq(iArr, create2)) {
            return new SecP256R1FieldElement(create);
        }
        return null;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat256.create();
        SecP256R1Field.square(this.f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SecP256R1Field.subtract(this.f1474x, ((SecP256R1FieldElement) eCFieldElement).f1474x, create);
        return new SecP256R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat256.getBit(this.f1474x, 0) == 1;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat256.toBigInteger(this.f1474x);
    }

    public SecP256R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f1473Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
        }
        this.f1474x = SecP256R1Field.fromBigInteger(bigInteger);
    }

    public SecP256R1FieldElement(int[] iArr) {
        this.f1474x = iArr;
    }
}
