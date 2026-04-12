package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class fw0 extends AbstractC1330va.a1 {

    /* renamed from: Q */
    public static final BigInteger f56338Q = new BigInteger(1, c40.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"));

    /* renamed from: x */
    protected int[] f56339x;

    public fw0() {
        this.f56339x = qh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        ew0.add(this.f56339x, ((fw0) abstractC1330va).f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = qh0.create();
        ew0.addOne(this.f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        ew0.inv(((fw0) abstractC1330va).f56339x, iArrCreate);
        ew0.multiply(iArrCreate, this.f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof fw0) {
            return qh0.m214399eq(this.f56339x, ((fw0) obj).f56339x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecP192R1Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f56338Q.bitLength();
    }

    public int hashCode() {
        return f56338Q.hashCode() ^ C0133bg.hashCode(this.f56339x, 0, 6);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = qh0.create();
        ew0.inv(this.f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return qh0.isOne(this.f56339x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return qh0.isZero(this.f56339x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        ew0.multiply(this.f56339x, ((fw0) abstractC1330va).f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = qh0.create();
        ew0.negate(this.f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f56339x;
        if (qh0.isZero(iArr) || qh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = qh0.create();
        int[] iArrCreate2 = qh0.create();
        ew0.square(iArr, iArrCreate);
        ew0.multiply(iArrCreate, iArr, iArrCreate);
        ew0.squareN(iArrCreate, 2, iArrCreate2);
        ew0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        ew0.squareN(iArrCreate2, 4, iArrCreate);
        ew0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        ew0.squareN(iArrCreate, 8, iArrCreate2);
        ew0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        ew0.squareN(iArrCreate2, 16, iArrCreate);
        ew0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        ew0.squareN(iArrCreate, 32, iArrCreate2);
        ew0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        ew0.squareN(iArrCreate2, 64, iArrCreate);
        ew0.multiply(iArrCreate, iArrCreate2, iArrCreate);
        ew0.squareN(iArrCreate, 62, iArrCreate);
        ew0.square(iArrCreate, iArrCreate2);
        if (qh0.m214399eq(iArr, iArrCreate2)) {
            return new fw0(iArrCreate);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = qh0.create();
        ew0.square(this.f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = qh0.create();
        ew0.subtract(this.f56339x, ((fw0) abstractC1330va).f56339x, iArrCreate);
        return new fw0(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return qh0.getBit(this.f56339x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return qh0.toBigInteger(this.f56339x);
    }

    public fw0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f56338Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for SecP192R1FieldElement");
        }
        this.f56339x = ew0.fromBigInteger(bigInteger);
    }

    public fw0(int[] iArr) {
        this.f56339x = iArr;
    }
}
