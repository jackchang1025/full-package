package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class dx0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f55889Q = new BigInteger(1, c40.decodeStrict("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f55890x;

    public dx0() {
        this.f55890x = yh0.create(17);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(17);
        cx0.add(this.f55890x, ((dx0) abstractC1330va).f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = yh0.create(17);
        cx0.addOne(this.f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(17);
        cx0.inv(((dx0) abstractC1330va).f55890x, iArrCreate);
        cx0.multiply(iArrCreate, this.f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof dx0) {
            return yh0.m215288eq(17, this.f55890x, ((dx0) obj).f55890x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP521R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f55889Q.bitLength();
    }

    public int hashCode() {
        return f55889Q.hashCode() ^ C0133bg.hashCode(this.f55890x, 0, 17);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = yh0.create(17);
        cx0.inv(this.f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return yh0.isOne(17, this.f55890x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return yh0.isZero(17, this.f55890x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(17);
        cx0.multiply(this.f55890x, ((dx0) abstractC1330va).f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = yh0.create(17);
        cx0.negate(this.f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f55890x;
        if (yh0.isZero(17, iArr) || yh0.isOne(17, iArr)) {
            return this;
        }
        int[] iArrCreate = yh0.create(17);
        int[] iArrCreate2 = yh0.create(17);
        cx0.squareN(iArr, 519, iArrCreate);
        cx0.square(iArrCreate, iArrCreate2);
        if (yh0.m215288eq(17, iArr, iArrCreate2)) {
            return new dx0(iArrCreate);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = yh0.create(17);
        cx0.square(this.f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = yh0.create(17);
        cx0.subtract(this.f55890x, ((dx0) abstractC1330va).f55890x, iArrCreate);
        return new dx0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return yh0.getBit(this.f55890x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return yh0.toBigInteger(17, this.f55890x);
    }

    public dx0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f55889Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP521R1FieldElement");
        }
        this.f55890x = cx0.fromBigInteger(bigInteger);
    }

    public dx0(int[] iArr) {
        this.f55890x = iArr;
    }
}
