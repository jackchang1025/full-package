package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class tv0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f60285Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFF"));

    /* renamed from: x */
    protected int[] f60286x;

    public tv0() {
        this.f60286x = ph0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        sv0.add(this.f60286x, ((tv0) abstractC1330va).f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = ph0.create();
        sv0.addOne(this.f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        sv0.inv(((tv0) abstractC1330va).f60286x, iArrCreate);
        sv0.multiply(iArrCreate, this.f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof tv0) {
            return ph0.m214292eq(this.f60286x, ((tv0) obj).f60286x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP160R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f60285Q.bitLength();
    }

    public int hashCode() {
        return f60285Q.hashCode() ^ C0133bg.hashCode(this.f60286x, 0, 5);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = ph0.create();
        sv0.inv(this.f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return ph0.isOne(this.f60286x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return ph0.isZero(this.f60286x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        sv0.multiply(this.f60286x, ((tv0) abstractC1330va).f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = ph0.create();
        sv0.negate(this.f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f60286x;
        if (ph0.isZero(iArr) || ph0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = ph0.create();
        sv0.square(iArr, iArrCreate);
        sv0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = ph0.create();
        sv0.squareN(iArrCreate, 2, iArrCreate2);
        sv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        sv0.squareN(iArrCreate2, 4, iArrCreate);
        sv0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        sv0.squareN(iArrCreate, 8, iArrCreate2);
        sv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        sv0.squareN(iArrCreate2, 16, iArrCreate);
        sv0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        sv0.squareN(iArrCreate, 32, iArrCreate2);
        sv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        sv0.squareN(iArrCreate2, 64, iArrCreate);
        sv0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        sv0.square(iArrCreate, iArrCreate2);
        sv0.multiply(iArrCreate2, iArr, iArrCreate2);
        sv0.squareN(iArrCreate2, 29, iArrCreate2);
        sv0.square(iArrCreate2, iArrCreate);
        if (ph0.m214292eq(iArr, iArrCreate)) {
            return new tv0(iArrCreate2);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = ph0.create();
        sv0.square(this.f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = ph0.create();
        sv0.subtract(this.f60286x, ((tv0) abstractC1330va).f60286x, iArrCreate);
        return new tv0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return ph0.getBit(this.f60286x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return ph0.toBigInteger(this.f60286x);
    }

    public tv0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f60285Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP160R1FieldElement");
        }
        this.f60286x = sv0.fromBigInteger(bigInteger);
    }

    public tv0(int[] iArr) {
        this.f60286x = iArr;
    }
}
