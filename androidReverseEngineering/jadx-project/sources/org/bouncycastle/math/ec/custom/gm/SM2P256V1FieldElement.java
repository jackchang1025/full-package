package org.bouncycastle.math.ec.custom.gm;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.raw.Nat256;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SM2P256V1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f1431Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f1432x;

    public SM2P256V1FieldElement() {
        this.f1432x = Nat256.create();
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SM2P256V1Field.add(this.f1432x, ((SM2P256V1FieldElement) eCFieldElement).f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat256.create();
        SM2P256V1Field.addOne(this.f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SM2P256V1Field.inv(((SM2P256V1FieldElement) eCFieldElement).f1432x, create);
        SM2P256V1Field.multiply(create, this.f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SM2P256V1FieldElement) {
            return Nat256.eq(this.f1432x, ((SM2P256V1FieldElement) obj).f1432x);
        }
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SM2P256V1Field";
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return f1431Q.bitLength();
    }

    public int hashCode() {
        return f1431Q.hashCode() ^ Arrays.hashCode(this.f1432x, 0, 8);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat256.create();
        SM2P256V1Field.inv(this.f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat256.isOne(this.f1432x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat256.isZero(this.f1432x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SM2P256V1Field.multiply(this.f1432x, ((SM2P256V1FieldElement) eCFieldElement).f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat256.create();
        SM2P256V1Field.negate(this.f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.f1432x;
        if (Nat256.isZero(iArr) || Nat256.isOne(iArr)) {
            return this;
        }
        int[] create = Nat256.create();
        SM2P256V1Field.square(iArr, create);
        SM2P256V1Field.multiply(create, iArr, create);
        int[] create2 = Nat256.create();
        SM2P256V1Field.squareN(create, 2, create2);
        SM2P256V1Field.multiply(create2, create, create2);
        int[] create3 = Nat256.create();
        SM2P256V1Field.squareN(create2, 2, create3);
        SM2P256V1Field.multiply(create3, create, create3);
        SM2P256V1Field.squareN(create3, 6, create);
        SM2P256V1Field.multiply(create, create3, create);
        int[] create4 = Nat256.create();
        SM2P256V1Field.squareN(create, 12, create4);
        SM2P256V1Field.multiply(create4, create, create4);
        SM2P256V1Field.squareN(create4, 6, create);
        SM2P256V1Field.multiply(create, create3, create);
        SM2P256V1Field.square(create, create3);
        SM2P256V1Field.multiply(create3, iArr, create3);
        SM2P256V1Field.squareN(create3, 31, create4);
        SM2P256V1Field.multiply(create4, create3, create);
        SM2P256V1Field.squareN(create4, 32, create4);
        SM2P256V1Field.multiply(create4, create, create4);
        SM2P256V1Field.squareN(create4, 62, create4);
        SM2P256V1Field.multiply(create4, create, create4);
        SM2P256V1Field.squareN(create4, 4, create4);
        SM2P256V1Field.multiply(create4, create2, create4);
        SM2P256V1Field.squareN(create4, 32, create4);
        SM2P256V1Field.multiply(create4, iArr, create4);
        SM2P256V1Field.squareN(create4, 62, create4);
        SM2P256V1Field.square(create4, create2);
        if (Nat256.eq(iArr, create2)) {
            return new SM2P256V1FieldElement(create4);
        }
        return null;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat256.create();
        SM2P256V1Field.square(this.f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat256.create();
        SM2P256V1Field.subtract(this.f1432x, ((SM2P256V1FieldElement) eCFieldElement).f1432x, create);
        return new SM2P256V1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat256.getBit(this.f1432x, 0) == 1;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat256.toBigInteger(this.f1432x);
    }

    public SM2P256V1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f1431Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SM2P256V1FieldElement");
        }
        this.f1432x = SM2P256V1Field.fromBigInteger(bigInteger);
    }

    public SM2P256V1FieldElement(int[] iArr) {
        this.f1432x = iArr;
    }
}
