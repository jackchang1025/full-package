package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat128;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecP128R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f1436Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f1437x;

    public SecP128R1FieldElement() {
        this.f1437x = Nat128.create();
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat128.create();
        SecP128R1Field.add(this.f1437x, ((SecP128R1FieldElement) eCFieldElement).f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat128.create();
        SecP128R1Field.addOne(this.f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat128.create();
        SecP128R1Field.inv(((SecP128R1FieldElement) eCFieldElement).f1437x, create);
        SecP128R1Field.multiply(create, this.f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP128R1FieldElement) {
            return Nat128.eq(this.f1437x, ((SecP128R1FieldElement) obj).f1437x);
        }
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP128R1Field";
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return f1436Q.bitLength();
    }

    public int hashCode() {
        return f1436Q.hashCode() ^ Arrays.hashCode(this.f1437x, 0, 4);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat128.create();
        SecP128R1Field.inv(this.f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat128.isOne(this.f1437x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat128.isZero(this.f1437x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat128.create();
        SecP128R1Field.multiply(this.f1437x, ((SecP128R1FieldElement) eCFieldElement).f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat128.create();
        SecP128R1Field.negate(this.f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.f1437x;
        if (Nat128.isZero(iArr) || Nat128.isOne(iArr)) {
            return this;
        }
        int[] create = Nat128.create();
        SecP128R1Field.square(iArr, create);
        SecP128R1Field.multiply(create, iArr, create);
        int[] create2 = Nat128.create();
        SecP128R1Field.squareN(create, 2, create2);
        SecP128R1Field.multiply(create2, create, create2);
        int[] create3 = Nat128.create();
        SecP128R1Field.squareN(create2, 4, create3);
        SecP128R1Field.multiply(create3, create2, create3);
        SecP128R1Field.squareN(create3, 2, create2);
        SecP128R1Field.multiply(create2, create, create2);
        SecP128R1Field.squareN(create2, 10, create);
        SecP128R1Field.multiply(create, create2, create);
        SecP128R1Field.squareN(create, 10, create3);
        SecP128R1Field.multiply(create3, create2, create3);
        SecP128R1Field.square(create3, create2);
        SecP128R1Field.multiply(create2, iArr, create2);
        SecP128R1Field.squareN(create2, 95, create2);
        SecP128R1Field.square(create2, create3);
        if (Nat128.eq(iArr, create3)) {
            return new SecP128R1FieldElement(create2);
        }
        return null;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat128.create();
        SecP128R1Field.square(this.f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat128.create();
        SecP128R1Field.subtract(this.f1437x, ((SecP128R1FieldElement) eCFieldElement).f1437x, create);
        return new SecP128R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat128.getBit(this.f1437x, 0) == 1;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat128.toBigInteger(this.f1437x);
    }

    public SecP128R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f1436Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP128R1FieldElement");
        }
        this.f1437x = SecP128R1Field.fromBigInteger(bigInteger);
    }

    public SecP128R1FieldElement(int[] iArr) {
        this.f1437x = iArr;
    }
}
