package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class vw0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f60715Q = new BigInteger(1, c40.decodeStrict("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f60716x;

    public vw0() {
        this.f60716x = sh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        uw0.add(this.f60716x, ((vw0) abstractC1330va).f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = sh0.create();
        uw0.addOne(this.f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        uw0.inv(((vw0) abstractC1330va).f60716x, iArrCreate);
        uw0.multiply(iArrCreate, this.f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof vw0) {
            return sh0.m214618eq(this.f60716x, ((vw0) obj).f60716x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP256R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f60715Q.bitLength();
    }

    public int hashCode() {
        return f60715Q.hashCode() ^ C0133bg.hashCode(this.f60716x, 0, 8);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = sh0.create();
        uw0.inv(this.f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return sh0.isOne(this.f60716x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return sh0.isZero(this.f60716x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        uw0.multiply(this.f60716x, ((vw0) abstractC1330va).f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = sh0.create();
        uw0.negate(this.f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f60716x;
        if (sh0.isZero(iArr) || sh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        uw0.square(iArr, iArrCreate);
        uw0.multiply(iArrCreate, iArr, iArrCreate);
        uw0.squareN(iArrCreate, 2, iArrCreate2);
        uw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        uw0.squareN(iArrCreate2, 4, iArrCreate);
        uw0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        uw0.squareN(iArrCreate, 8, iArrCreate2);
        uw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        uw0.squareN(iArrCreate2, 16, iArrCreate);
        uw0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        uw0.squareN(iArrCreate, 32, iArrCreate);
        uw0.multiply(iArrCreate, iArr, iArrCreate);
        uw0.squareN(iArrCreate, 96, iArrCreate);
        uw0.multiply(iArrCreate, iArr, iArrCreate);
        uw0.squareN(iArrCreate, 94, iArrCreate);
        uw0.square(iArrCreate, iArrCreate2);
        if (sh0.m214618eq(iArr, iArrCreate2)) {
            return new vw0(iArrCreate);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = sh0.create();
        uw0.square(this.f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        uw0.subtract(this.f60716x, ((vw0) abstractC1330va).f60716x, iArrCreate);
        return new vw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return sh0.getBit(this.f60716x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return sh0.toBigInteger(this.f60716x);
    }

    public vw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f60715Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
        }
        this.f60716x = uw0.fromBigInteger(bigInteger);
    }

    public vw0(int[] iArr) {
        this.f60716x = iArr;
    }
}
