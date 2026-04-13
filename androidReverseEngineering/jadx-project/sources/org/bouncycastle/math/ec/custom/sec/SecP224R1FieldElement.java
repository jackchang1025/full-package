package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.raw.Mod;
import org.bouncycastle.math.raw.Nat;
import org.bouncycastle.math.raw.Nat224;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes.dex */
public class SecP224R1FieldElement extends ECFieldElement.AbstractFp {

    /* renamed from: Q */
    public static final BigInteger f1464Q = new BigInteger(1, Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001"));

    /* renamed from: x */
    protected int[] f1465x;

    public SecP224R1FieldElement() {
        this.f1465x = Nat224.create();
    }

    private static void RM(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, int[] iArr6, int[] iArr7) {
        SecP224R1Field.multiply(iArr5, iArr3, iArr7);
        SecP224R1Field.multiply(iArr7, iArr, iArr7);
        SecP224R1Field.multiply(iArr4, iArr2, iArr6);
        SecP224R1Field.add(iArr6, iArr7, iArr6);
        SecP224R1Field.multiply(iArr4, iArr3, iArr7);
        Nat224.copy(iArr6, iArr4);
        SecP224R1Field.multiply(iArr5, iArr2, iArr5);
        SecP224R1Field.add(iArr5, iArr7, iArr5);
        SecP224R1Field.square(iArr5, iArr6);
        SecP224R1Field.multiply(iArr6, iArr, iArr6);
    }

    private static void RP(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        Nat224.copy(iArr, iArr4);
        int[] create = Nat224.create();
        int[] create2 = Nat224.create();
        for (int i2 = 0; i2 < 7; i2++) {
            Nat224.copy(iArr2, create);
            Nat224.copy(iArr3, create2);
            int i3 = 1 << i2;
            while (true) {
                i3--;
                if (i3 >= 0) {
                    RS(iArr2, iArr3, iArr4, iArr5);
                }
            }
            RM(iArr, create, create2, iArr2, iArr3, iArr4, iArr5);
        }
    }

    private static void RS(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        SecP224R1Field.multiply(iArr2, iArr, iArr2);
        SecP224R1Field.twice(iArr2, iArr2);
        SecP224R1Field.square(iArr, iArr4);
        SecP224R1Field.add(iArr3, iArr4, iArr);
        SecP224R1Field.multiply(iArr3, iArr4, iArr3);
        SecP224R1Field.reduce32(Nat.shiftUpBits(7, iArr3, 2, 0), iArr3);
    }

    private static boolean isSquare(int[] iArr) {
        int[] create = Nat224.create();
        int[] create2 = Nat224.create();
        Nat224.copy(iArr, create);
        for (int i2 = 0; i2 < 7; i2++) {
            Nat224.copy(create, create2);
            SecP224R1Field.squareN(create, 1 << i2, create);
            SecP224R1Field.multiply(create, create2, create);
        }
        SecP224R1Field.squareN(create, 95, create);
        return Nat224.isOne(create);
    }

    private static boolean trySqrt(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = Nat224.create();
        Nat224.copy(iArr2, create);
        int[] create2 = Nat224.create();
        create2[0] = 1;
        int[] create3 = Nat224.create();
        RP(iArr, create, create2, create3, iArr3);
        int[] create4 = Nat224.create();
        int[] create5 = Nat224.create();
        for (int i2 = 1; i2 < 96; i2++) {
            Nat224.copy(create, create4);
            Nat224.copy(create2, create5);
            RS(create, create2, create3, iArr3);
            if (Nat224.isZero(create)) {
                SecP224R1Field.inv(create5, iArr3);
                SecP224R1Field.multiply(iArr3, create4, iArr3);
                return true;
            }
        }
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] create = Nat224.create();
        SecP224R1Field.add(this.f1465x, ((SecP224R1FieldElement) eCFieldElement).f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement addOne() {
        int[] create = Nat224.create();
        SecP224R1Field.addOne(this.f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] create = Nat224.create();
        SecP224R1Field.inv(((SecP224R1FieldElement) eCFieldElement).f1465x, create);
        SecP224R1Field.multiply(create, this.f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SecP224R1FieldElement) {
            return Nat224.eq(this.f1465x, ((SecP224R1FieldElement) obj).f1465x);
        }
        return false;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public String getFieldName() {
        return "SecP224R1Field";
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return f1464Q.bitLength();
    }

    public int hashCode() {
        return f1464Q.hashCode() ^ Arrays.hashCode(this.f1465x, 0, 7);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement invert() {
        int[] create = Nat224.create();
        SecP224R1Field.inv(this.f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return Nat224.isOne(this.f1465x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return Nat224.isZero(this.f1465x);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] create = Nat224.create();
        SecP224R1Field.multiply(this.f1465x, ((SecP224R1FieldElement) eCFieldElement).f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement negate() {
        int[] create = Nat224.create();
        SecP224R1Field.negate(this.f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement sqrt() {
        int[] iArr = this.f1465x;
        if (Nat224.isZero(iArr) || Nat224.isOne(iArr)) {
            return this;
        }
        int[] create = Nat224.create();
        SecP224R1Field.negate(iArr, create);
        int[] random = Mod.random(SecP224R1Field.f1463P);
        int[] create2 = Nat224.create();
        if (!isSquare(iArr)) {
            return null;
        }
        while (!trySqrt(create, random, create2)) {
            SecP224R1Field.addOne(random, random);
        }
        SecP224R1Field.square(create2, random);
        if (Nat224.eq(iArr, random)) {
            return new SecP224R1FieldElement(create2);
        }
        return null;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement square() {
        int[] create = Nat224.create();
        SecP224R1Field.square(this.f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] create = Nat224.create();
        SecP224R1Field.subtract(this.f1465x, ((SecP224R1FieldElement) eCFieldElement).f1465x, create);
        return new SecP224R1FieldElement(create);
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return Nat224.getBit(this.f1465x, 0) == 1;
    }

    @Override // org.bouncycastle.math.ec.ECFieldElement
    public BigInteger toBigInteger() {
        return Nat224.toBigInteger(this.f1465x);
    }

    public SecP224R1FieldElement(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f1464Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP224R1FieldElement");
        }
        this.f1465x = SecP224R1Field.fromBigInteger(bigInteger);
    }

    public SecP224R1FieldElement(int[] iArr) {
        this.f1465x = iArr;
    }
}
