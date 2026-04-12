package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class jw0 extends AbstractC1330va.a1 {

    /* renamed from: x */
    protected int[] f57390x;

    /* renamed from: Q */
    public static final BigInteger f57389Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFE56D"));
    private static final int[] PRECOMP_POW2 = {868209154, -587542221, 579297866, -1014948952, -1470801668, 514782679, -1897982644};

    public jw0() {
        this.f57390x = rh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        iw0.add(this.f57390x, ((jw0) abstractC1330va).f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = rh0.create();
        iw0.addOne(this.f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        iw0.inv(((jw0) abstractC1330va).f57390x, iArrCreate);
        iw0.multiply(iArrCreate, this.f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof jw0) {
            return rh0.m214542eq(this.f57390x, ((jw0) obj).f57390x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP224K1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f57389Q.bitLength();
    }

    public int hashCode() {
        return f57389Q.hashCode() ^ C0133bg.hashCode(this.f57390x, 0, 7);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = rh0.create();
        iw0.inv(this.f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return rh0.isOne(this.f57390x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return rh0.isZero(this.f57390x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        iw0.multiply(this.f57390x, ((jw0) abstractC1330va).f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = rh0.create();
        iw0.negate(this.f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f57390x;
        if (rh0.isZero(iArr) || rh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = rh0.create();
        iw0.square(iArr, iArrCreate);
        iw0.multiply(iArrCreate, iArr, iArrCreate);
        iw0.square(iArrCreate, iArrCreate);
        iw0.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = rh0.create();
        iw0.square(iArrCreate, iArrCreate2);
        iw0.multiply(iArrCreate2, iArr, iArrCreate2);
        int[] iArrCreate3 = rh0.create();
        iw0.squareN(iArrCreate2, 4, iArrCreate3);
        iw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        int[] iArrCreate4 = rh0.create();
        iw0.squareN(iArrCreate3, 3, iArrCreate4);
        iw0.multiply(iArrCreate4, iArrCreate, iArrCreate4);
        iw0.squareN(iArrCreate4, 8, iArrCreate4);
        iw0.multiply(iArrCreate4, iArrCreate3, iArrCreate4);
        iw0.squareN(iArrCreate4, 4, iArrCreate3);
        iw0.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        iw0.squareN(iArrCreate3, 19, iArrCreate2);
        iw0.multiply(iArrCreate2, iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = rh0.create();
        iw0.squareN(iArrCreate2, 42, iArrCreate5);
        iw0.multiply(iArrCreate5, iArrCreate2, iArrCreate5);
        iw0.squareN(iArrCreate5, 23, iArrCreate2);
        iw0.multiply(iArrCreate2, iArrCreate3, iArrCreate2);
        iw0.squareN(iArrCreate2, 84, iArrCreate3);
        iw0.multiply(iArrCreate3, iArrCreate5, iArrCreate3);
        iw0.squareN(iArrCreate3, 20, iArrCreate3);
        iw0.multiply(iArrCreate3, iArrCreate4, iArrCreate3);
        iw0.squareN(iArrCreate3, 3, iArrCreate3);
        iw0.multiply(iArrCreate3, iArr, iArrCreate3);
        iw0.squareN(iArrCreate3, 2, iArrCreate3);
        iw0.multiply(iArrCreate3, iArr, iArrCreate3);
        iw0.squareN(iArrCreate3, 4, iArrCreate3);
        iw0.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        iw0.square(iArrCreate3, iArrCreate3);
        iw0.square(iArrCreate3, iArrCreate5);
        if (rh0.m214542eq(iArr, iArrCreate5)) {
            return new jw0(iArrCreate3);
        }
        iw0.multiply(iArrCreate3, PRECOMP_POW2, iArrCreate3);
        iw0.square(iArrCreate3, iArrCreate5);
        if (rh0.m214542eq(iArr, iArrCreate5)) {
            return new jw0(iArrCreate3);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = rh0.create();
        iw0.square(this.f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = rh0.create();
        iw0.subtract(this.f57390x, ((jw0) abstractC1330va).f57390x, iArrCreate);
        return new jw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return rh0.getBit(this.f57390x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return rh0.toBigInteger(this.f57390x);
    }

    public jw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f57389Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP224K1FieldElement");
        }
        this.f57390x = iw0.fromBigInteger(bigInteger);
    }

    public jw0(int[] iArr) {
        this.f57390x = iArr;
    }
}
